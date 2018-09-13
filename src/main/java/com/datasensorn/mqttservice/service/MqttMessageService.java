package com.datasensorn.mqttservice.service;

import org.springframework.messaging.Message;

/**
 * mqtt 消息的处理服务
 */
public interface MqttMessageService {

    /**
     * 处理消息
     * @param message
     */
    void handleMsg(Message<?> message);
}
