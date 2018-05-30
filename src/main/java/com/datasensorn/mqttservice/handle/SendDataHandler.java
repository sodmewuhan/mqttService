package com.datasensorn.mqttservice.handle;

import com.datasensorn.mqttservice.model.MqttSettings;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 发送消息
 */
@Component
public class SendDataHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SendDataHandler.class);

    @Autowired
    MqttClient mqttClient;

    @Autowired
    MqttSettings mqttSettings;

    public void mqttPublishingTask() throws MqttPersistenceException, MqttException {
        String message = "Test from sodmewuhan not scheudle @ " + new Date().toString();
        mqttClient.publish( mqttSettings.getTopic(), new MqttMessage( message.getBytes() ) );
    }
}

