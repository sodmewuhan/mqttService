package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.biz.WeatherInfo;

/**
 * 集成第三方服务
 */
public interface ThirdPartService {

    /**
     * 集成天气预报服务
     * 说明：https://market.aliyun.com/products/57096001/cmapi010812.html?spm=5176.2020520132.101.5.KCyq35#sku=yuncode481200005
     * @return
     */
    WeatherInfo getWeatherInfo();
}
