/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.walter.rak4630;

import com.walter.commports.HotplugListener;
import com.walter.mqtt.MqttBroker;
import javax.usb.*;
import javax.usb.event.*;


/**
 * This class contains the entry point of the application.
 * 
 * @author User
 */
public class RAK4630 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UsbException, InterruptedException {
        
        // Create usb listener
        UsbServices services = UsbHostManager.getUsbServices();
        services.addUsbServicesListener(new HotplugListener());
        
        // Start the MQTT broker
        MqttBroker mqttBroker = new MqttBroker();
        mqttBroker.start();
        
        // Keep this program from exiting
        while(true){}
    }
}
