package com.datasensorn.mqttservice.conf;

import com.datasensorn.mqttservice.handle.InputHandler;
import com.datasensorn.mqttservice.model.MqttSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class MqttInputConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqttInputConfig.class);

    @Autowired
    private MqttSettings mqttSettings;

    @Autowired
    private InputHandler inputHandler;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                mqttSettings.getBrokerUrl(),
                mqttSettings.getSubscriberName(),
                mqttSettings.getTopic() );
        adapter.setOutputChannel( mqttInputChannel() );
        return adapter;
    }

    /**
     * 接受到消息
     * @return
     */
    @Bean
    @ServiceActivator( inputChannel = "mqttInputChannel" )
    public MessageHandler handler() {
        return inputHandler;
    }
}
