package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.conf.MqttInputConfig;
import com.datasensorn.mqttservice.model.MqttSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    MqttSettings mqttSettings;

    @RequestMapping("/")
    @ResponseBody
    String home() {

        LOGGER.info( "Started to publish MQTT messages to " + mqttSettings.getTopic() );

        return "hello";
    }
}
