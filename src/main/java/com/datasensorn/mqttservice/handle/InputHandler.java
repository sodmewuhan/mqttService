package com.datasensorn.mqttservice.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class InputHandler implements MessageHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(InputHandler.class);

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        LOGGER.info( "Recevied the list Topic: {} >>> Recevied Message: {}",
                message.getHeaders().get( "mqtt_topic" ), message.getPayload() );
    }
}
