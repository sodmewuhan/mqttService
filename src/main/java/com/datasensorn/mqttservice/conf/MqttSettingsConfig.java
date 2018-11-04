package com.datasensorn.mqttservice.conf;

import com.datasensorn.mqttservice.service.MqttMessageService;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.stream.CharacterStreamReadingMessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
@IntegrationComponentScan
public class MqttSettingsConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqttSettingsConfig.class);

    @Autowired
    MqttMessageService mqttMessageService;

    @Value( "${application.mqtt.hostUrl}" )
    private String mqttHostUrl;

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

    @Value("${application.mqtt.client.inBoundId}")
    private String inBoundId;

    @Value("${application.mqtt.client.outBoundId}")
    private String outBoundId;

    @Bean
    public IntegrationFlow mqttOutFlow() {
        return IntegrationFlows.from(CharacterStreamReadingMessageSource.stdin(),
                e -> e.poller(Pollers.fixedDelay(1000)))
                .transform(p -> p + " sent to MQTT")
                .handle(mqttOutbound())
                .get();
    }

    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{mqttHostUrl});
        mqttConnectOptions.setKeepAliveInterval(60*60*24);
        return mqttConnectOptions;
    }
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(outBoundId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttTopic);
        return messageHandler;

    }
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * mqtt inbound 配置项目
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        mqttHostUrl,
                        inBoundId,
                        mqttClientFactory(),
                        mqttTopic);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * 接受消息
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                LOGGER.info("********** mqtt handle recive the message " + message.getPayload());
                mqttMessageService.handleMsg(message);
            }
        };
    }

    @Bean
    public ApplicationListener<?> eventListener() {
        return new ApplicationListener<MqttConnectionFailedEvent>() {
            @Override
            public void onApplicationEvent(MqttConnectionFailedEvent event) {
                LOGGER.info("mqtt 连接发生错误 " + event.getCause().getMessage(),event.getCause());
            }
        };
    }
}
