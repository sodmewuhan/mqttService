package com.datasensorn.mqttservice.mqtt;


import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    /**
     * 发送mqtt消息
     * @param data
     * @param topic
     */
    void sendToMqtt(String data,@Header(MqttHeaders.TOPIC) String topic);
}
