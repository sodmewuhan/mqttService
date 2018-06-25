package com.datasensorn.mqttservice.conf;

import com.datasensorn.mqttservice.handle.InputHandler;
import com.datasensorn.mqttservice.listener.SpringContextUtil;
import com.datasensorn.mqttservice.model.MqttSettings;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
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

//    private MqttPahoMessageDrivenChannelAdapter adapter_topic;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {

        //得到所有需要监听的消息队列
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                "siSampleConsumer",
                mqttClientFactory(),
                mqttSettings.getTopic()
        );
        adapter.setOutputChannel( mqttInputChannel() );

        return adapter;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions connOptions = new MqttConnectOptions();
        connOptions.setServerURIs(new String[] {mqttSettings.getBrokerUrl()});
        connOptions.setUserName(mqttSettings.getUsername());
        connOptions.setPassword(mqttSettings.getPassword().toCharArray());

        factory.setConnectionOptions(connOptions);
        return factory;
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
