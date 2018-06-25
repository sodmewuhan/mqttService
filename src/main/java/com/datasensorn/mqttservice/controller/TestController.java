package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.conf.MqttInputConfig;
import com.datasensorn.mqttservice.handle.SendDataHandler;
import com.datasensorn.mqttservice.model.MqttSettings;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * 测试试用
 */
@Controller
@EnableAutoConfiguration
@Deprecated
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    SendDataHandler sendDataHandler;

    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    @RequestMapping("/")
    @ResponseBody
    String home() {

        try {
            //sendDataHandler.mqttPublishingTask();

            influxDBTemplate.createDatabase();
            final Point p = Point.measurement("disk")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("tenant", "default")
                    .addField("used", 80L)
                    .addField("free", 1L)
                    .build();

            influxDBTemplate.write(p);

        } catch (Exception e) {

        }

        return "hello";
    }
}
