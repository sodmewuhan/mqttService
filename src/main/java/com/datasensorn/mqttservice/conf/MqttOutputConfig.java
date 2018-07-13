package com.datasensorn.mqttservice.conf;

import com.datasensorn.mqttservice.model.MqttSettings;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttOutputConfig {

    @Autowired
    private MqttSettings mqttSettings;

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator( inputChannel = "mqttOutboundChannel" )
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                mqttSettings.getPublisherName(),
                mqttClientFactory() );
        messageHandler.setAsync( true );
        messageHandler.setDefaultTopic( mqttSettings.getTopic() );
        return messageHandler;
    }

    private MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs( mqttSettings.getBrokerUrl() );
        return factory;
    }

    @MessagingGateway( defaultRequestChannel = "mqttOutboundChannel" )
    public interface DeviceGateway {
        void sendToMqtt(String data);
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        //注意：在同一个clientId 重复启动的时候，mqtt存在掉线的问题
        MqttClient mqttClient = new MqttClient(
                mqttSettings.getBrokerUrl(),
                mqttSettings.getPublisherName());
        MqttConnectOptions connOptions = new MqttConnectOptions();
        connOptions.setUserName( mqttSettings.getUsername() );
        connOptions.setPassword( mqttSettings.getPassword().toCharArray() );

        //设置超时时间
        connOptions.setConnectionTimeout(mqttSettings.getTimeout());
        // 设置会话心跳时间
        connOptions.setKeepAliveInterval(mqttSettings.getHeartbeat());
        mqttClient.connect( connOptions );
        return mqttClient;
    }

}
