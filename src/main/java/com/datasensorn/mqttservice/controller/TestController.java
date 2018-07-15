package com.datasensorn.mqttservice.controller;

//import com.datasensorn.mqttservice.handle.SendDataHandler;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试试用
 */
@Controller
@EnableAutoConfiguration
@Deprecated
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
//
//    @Autowired
//    SendDataHandler sendDataHandler;

//    @Autowired
//    private InfluxDBTemplate<Point> influxDBTemplate;

    @RequestMapping("/")
    @ResponseBody
    String home() {

        return "hello";
    }
}
