package com.datasensorn.mqttservice.scheduleing;

import com.alibaba.fastjson.JSON;
import com.datasensorn.mqttservice.model.MqttSettings;
import com.datasensorn.mqttservice.model.biz.HourWeatherInfo;
import com.datasensorn.mqttservice.model.biz.WeatherInfo;
import com.datasensorn.mqttservice.mqtt.MiddlewareMqttClient;
import com.datasensorn.mqttservice.service.ThirdPartService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 测试试用
 */
@Component
public class MqttPublishingSchedular {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqttPublishingSchedular.class);

    @Autowired
    MiddlewareMqttClient middlewareMqttClient;

    @Autowired
    MqttSettings mqttSettings;

    @Autowired
    ThirdPartService thirdPartService;

    @Scheduled( fixedRateString = "${application.scheduler.rate}" )
    public void scheduledMqttPublishingTask() throws MqttPersistenceException, MqttException {

        WeatherInfo weatherInfo = thirdPartService.getWeatherInfo();
        HourWeatherInfo hourWeatherInfo = weatherInfo.getShowapi_res_body().getHourList().get(3);

        LOGGER.info(JSON.toJSONString(hourWeatherInfo));
        try {
            middlewareMqttClient.publishMessage(
                    //mqttSettings.getTopic(),
                    "fish/15919829955",
                    JSON.toJSONString(hourWeatherInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
}
