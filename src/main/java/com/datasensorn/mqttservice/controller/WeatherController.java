package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.model.biz.WeatherInfo;
import com.datasensorn.mqttservice.service.ThirdPartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 天气预报服务
 */
@RestController
@RequestMapping(value="/api")
public class WeatherController {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);


    @Autowired
    ThirdPartService thirdPartService;

    @RequestMapping(value = "/weatherInfo" ,method=RequestMethod.GET)
    public WeatherInfo getWeatherInfo() {

        WeatherInfo retn = new WeatherInfo();
        thirdPartService.getWeatherInfo();
        return retn;
    }
}