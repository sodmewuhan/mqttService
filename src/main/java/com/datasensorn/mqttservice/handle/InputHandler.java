package com.datasensorn.mqttservice.handle;

import com.datasensorn.mqttservice.listener.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class InputHandler implements MessageHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(InputHandler.class);


    @Async("executorService")
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        //主题
        LOGGER.info("topic is " + message.getHeaders().get("mqtt_topic"));

        LOGGER.info( "Recevied the list Topic: {} >>> Recevied Message: {}",
                message.getHeaders().get("mqtt_topic"), message.getPayload() );
    }
}
