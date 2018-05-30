package com.datasensorn.mqttservice.scheduleing;

import com.datasensorn.mqttservice.model.MqttSettings;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MqttPublishingSchedular {

    @Autowired
    MqttClient mqttClient;

    @Autowired
    MqttSettings mqttSettings;

    @Scheduled( fixedRateString = "${application.scheduler.rate}" )
    public void scheduledMqttPublishingTask() throws MqttPersistenceException, MqttException {
        String message = "Test from sodmewuhan @ " + new Date().toString();
        mqttClient.publish( mqttSettings.getTopic(), new MqttMessage( message.getBytes() ) );
    }
}
