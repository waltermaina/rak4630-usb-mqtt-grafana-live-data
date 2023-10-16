/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.walter.mqtt;

import com.walter.rak4630data.SensorData;
import org.eclipse.paho.client.mqttv3.*;


/**
 * This is the MQTT publisher.
 * It publishes data received from the com port to the local MQTT broker.
 * 
 * @author Walter
 */
public class MqttPublisher extends Thread{
    
    private String sensorData;

    // Constructor to initialize the data
    public MqttPublisher(String sensorData) {
        this.sensorData = sensorData;
    }
    
    /**
     * Publishes the sensor data
     */
    public  void publishSensorData() {
        //String broker = "tcp://mqtt.eclipse.org:1883"; // Replace with your MQTT broker URL
        String broker = "tcp://localhost:1883"; // Replace with your MQTT broker URL
        String clientId = "PublisherClient";
        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            
            client.connect(connOpts);
            
            String topic = "rak4630/sensordata";
            String content = sensorData;
            int qos = 1;
            
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            
            client.publish(topic, message);
            
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This is the run method of the MqttPublisher thread.
     */
    public void run() {
        // Code to be executed by the thread
        publishSensorData();
    }
}

