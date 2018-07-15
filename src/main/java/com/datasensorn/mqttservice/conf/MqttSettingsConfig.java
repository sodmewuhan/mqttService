package com.datasensorn.mqttservice.conf;

import com.datasensorn.mqttservice.model.MqttSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttSettingsConfig {

    @Value( "${application.mqtt.host}" )
    private String mqttHost;

    @Value( "${application.mqtt.publisher}" )
    private String mqttPublisher;

    @Value( "${application.mqtt.subscriber}" )
    private String mqttSubscriber;

    @Value( "${application.mqtt.topic}" )
    private String mqttTopic;

    @Value("${application.mqtt.username}")
    private String username;

    @Value("${application.mqtt.password}")
    private String password;

    @Value("${application.mqtt.timeout}")
    private Integer timeout;

    @Value("${application.mqtt.heartbeat}")
    private Integer heartbeat;


    @Bean
    public MqttSettings mqttSettings() {
        return new MqttSettings( mqttHost, mqttPublisher, mqttSubscriber,mqttTopic,
                username,password,timeout,heartbeat);
    }

}
