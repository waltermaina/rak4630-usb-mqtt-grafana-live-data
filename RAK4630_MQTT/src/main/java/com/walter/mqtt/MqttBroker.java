/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.walter.mqtt;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the MQTT Broker class.
 *
 * @author Walter
 */
public class MqttBroker extends Server {

    // Server object
    Server server;

    IResourceLoader classpathLoader = new ClasspathResourceLoader();
    final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);

    public MqttBroker() {
    }

    /**
     * Listens for published information.
     */
    static class PublisherListener extends AbstractInterceptHandler {

        @Override
        public String getID() {
            return "EmbeddedLauncherPublishListener";
        }

        @Override
        public void onPublish(InterceptPublishMessage msg) {
            final String decodedPayload = msg.getPayload().toString(UTF_8);
            System.out.println("Received on topic: " + msg.getTopicName() + " content: " + decodedPayload);
        }

        @Override
        public void onSessionLoopError(Throwable error) {
            System.out.println("Session event loop reported error: " + error);
        }
    }

    /**
     * Starts the broker
     */
    public void start() {
        server = new Server();
        try {
            List<? extends InterceptHandler> userHandlers = Collections.singletonList(new PublisherListener());
            server.startServer(classPathConfig, userHandlers);
        } catch (IOException ex) {
            Logger.getLogger(MqttBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Stops the broker.
     */
    public void stop() {
        server.stopServer();
    }
}
