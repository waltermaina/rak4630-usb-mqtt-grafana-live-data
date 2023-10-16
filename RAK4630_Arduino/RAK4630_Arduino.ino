/**
   @file RAK4630_LIVE_DATA.ino
   @author waltermaina76@gmail.com
   @brief An Arduino script for a device made up of RAK4630 and RAK1906, it does the following:
      1. Reading of RAK1906 environment sensor data.
      2. Sending the temperature, humidity, air pressure and air quality data through usb to a computer.
      3. The data is sent out every one second.
      4. The data is meant for display on gauges using Grafana and for upload to a web application.
      5. The data is stored and displayed using this web application: https://environment-sensors.onrender.com/
   @version 1.0.0
   @date 2023-10-13

   @copyright Copyright (c) 2023

   @note RAK4631 GPIO mapping to nRF52840 GPIO ports
   RAK4631    <->  nRF52840
   WB_IO1     <->  P0.17 (GPIO 17)
   WB_IO2     <->  P1.02 (GPIO 34)
   WB_IO3     <->  P0.21 (GPIO 21)
   WB_IO4     <->  P0.04 (GPIO 4)
   WB_IO5     <->  P0.09 (GPIO 9)
   WB_IO6     <->  P0.10 (GPIO 10)
   WB_SW1     <->  P0.01 (GPIO 1)
   WB_A0      <->  P0.04/AIN2 (AnalogIn A2)
   WB_A1      <->  P0.31/AIN7 (AnalogIn A7)
*/

#include "bsec.h"
#include <ArduinoJson.h>

// Helper functions declarations
void checkIaqSensorStatus(void);
void errLeds(void);

// Create an object of the class Bsec
Bsec iaqSensor;

// General purpose output string
String output;

// Allocate the JSON document, for creating json data
StaticJsonDocument<1024> doc;

// A unique id for each data set created
unsigned long record_id = 0;

// Some commands
#define CMD_SEND_DATA         1                 // Instructs the computer to upload the data

// Sensor reading timer variables
unsigned long sensorReadTimepoint = 0;
#define SENSOR_READ_INTERVAL  1000              // Every 1 seconds

// Entry point for the example
void setup(void)
{
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);

  // Initialize Serial for usb data output
  Serial.begin(9600);

  time_t serial_timeout = millis();
  // On nRF52840 the USB serial is not available immediately
  while (!Serial)
  {
    if ((millis() - serial_timeout) < 5000)
    {
      delay(100);
      // Blink green LED while we wait
      //digitalWrite(LED_BUILTIN, !digitalRead(LED_BUILTIN));
    }
    else
    {
      break;
    }
  }

  Serial.println("==========================================");
  Serial.println("Welcome to RAK4630 USB live data sender!!!");
  Serial.println("==========================================");


  // Initialize the sensor
  iaqSensor.begin(BME68X_I2C_ADDR_LOW, Wire);
  output = "\nBSEC library version " + String(iaqSensor.version.major) + "." + String(iaqSensor.version.minor) + "." + String(iaqSensor.version.major_bugfix) + "." + String(iaqSensor.version.minor_bugfix);
  Serial.println(output);
  checkIaqSensorStatus();

  bsec_virtual_sensor_t sensorList[13] = {
    BSEC_OUTPUT_IAQ,
    BSEC_OUTPUT_STATIC_IAQ,
    BSEC_OUTPUT_CO2_EQUIVALENT,
    BSEC_OUTPUT_BREATH_VOC_EQUIVALENT,
    BSEC_OUTPUT_RAW_TEMPERATURE,
    BSEC_OUTPUT_RAW_PRESSURE,
    BSEC_OUTPUT_RAW_HUMIDITY,
    BSEC_OUTPUT_RAW_GAS,
    BSEC_OUTPUT_STABILIZATION_STATUS,
    BSEC_OUTPUT_RUN_IN_STATUS,
    BSEC_OUTPUT_SENSOR_HEAT_COMPENSATED_TEMPERATURE,
    BSEC_OUTPUT_SENSOR_HEAT_COMPENSATED_HUMIDITY,
    BSEC_OUTPUT_GAS_PERCENTAGE
  };

  iaqSensor.updateSubscription(sensorList, 13, BSEC_SAMPLE_RATE_LP);
  checkIaqSensorStatus();

  // Print the header
  output = "Timestamp [ms], IAQ, IAQ accuracy, Static IAQ, CO2 equivalent, breath VOC equivalent, raw temp[°C], pressure [hPa], raw relative humidity [%], gas [Ohm], Stab Status, run in status, comp temp[°C], comp humidity [%], gas percentage";
  Serial.println(output);
}

// Function that is looped forever
void loop(void)
{
  // Read the sensors after a set interval.
  if (millis() - sensorReadTimepoint > SENSOR_READ_INTERVAL) {
    sensorReadTimepoint = millis();

    if (iaqSensor.run()) { // If new data is available
      digitalWrite(LED_BUILTIN, LOW);

      // Clear the JsonDocument
      doc.clear();

      // Increment record id
      record_id = record_id + 1;

      // Add values in the JSON document
      doc["command"] = CMD_SEND_DATA;
      doc["timeRecorded"] = 0;
      doc["recordId"] = record_id;
      doc["iaq"] = iaqSensor.iaq;
      doc["iaqAccuracy"] = iaqSensor.iaqAccuracy;
      doc["staticIaq"] = iaqSensor.staticIaq;
      doc["co2Equivalent"] = iaqSensor.co2Equivalent;
      doc["breathVocEquivalent"] = iaqSensor.breathVocEquivalent;
      doc["rawTemperature"] = iaqSensor.rawTemperature;
      doc["pressure"] = iaqSensor.pressure;
      doc["rawHumidity"] = iaqSensor.rawHumidity;
      doc["gasResistance"] = iaqSensor.gasResistance;
      doc["stabStatus"] = iaqSensor.stabStatus;
      doc["runInStatus"] = iaqSensor.runInStatus;
      doc["temperature"] = iaqSensor.temperature;
      doc["humidity"] = iaqSensor.humidity;
      doc["gasPercentage"] = iaqSensor.gasPercentage;

      // Serialize JSON to a buffer
      char buffer[512];  // Adjust the size as needed
      size_t bytesWritten = serializeJson(doc, buffer);

      // Send data through USB
      Serial.println(buffer);
      
      //digitalWrite(LED_BUILTIN, HIGH);
    } else {
      checkIaqSensorStatus();
    }
  }
}

// Helper function definitions
void checkIaqSensorStatus(void)
{
  if (iaqSensor.bsecStatus != BSEC_OK) {
    if (iaqSensor.bsecStatus < BSEC_OK) {
      output = "BSEC error code : " + String(iaqSensor.bsecStatus);
      Serial.println(output);
      for (;;)
        errLeds(); /* Halt in case of failure */
    } else {
      output = "BSEC warning code : " + String(iaqSensor.bsecStatus);
      Serial.println(output);
    }
  }

  if (iaqSensor.bme68xStatus != BME68X_OK) {
    if (iaqSensor.bme68xStatus < BME68X_OK) {
      output = "BME68X error code : " + String(iaqSensor.bme68xStatus);
      Serial.println(output);
      for (;;)
        errLeds(); /* Halt in case of failure */
    } else {
      output = "BME68X warning code : " + String(iaqSensor.bme68xStatus);
      Serial.println(output);
    }
  }
}

void errLeds(void)
{
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
  delay(100);
  digitalWrite(LED_BUILTIN, LOW);
  delay(100);
}
