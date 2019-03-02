package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.datasensorn.mqttservice.Utils.HttpUtils;
import com.datasensorn.mqttservice.conf.WeatherServiceConfig;
import com.datasensorn.mqttservice.model.biz.WeatherInfo;
import com.datasensorn.mqttservice.service.ThirdPartService;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ThirdPartServiceImpl implements ThirdPartService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThirdPartServiceImpl.class);

    @Autowired
    WeatherServiceConfig weatherServiceConfig;

    private static final String AUTHORIZATION = "Authorization";

    private static final String APPCODE = "APPCODE "; //最后的空格不能去掉

    @Override
    public WeatherInfo getWeatherInfo(String areaId) throws Exception{

        WeatherInfo weatherInfo = null;

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AUTHORIZATION, APPCODE + weatherServiceConfig.getAppCode());
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("areaid", areaId);

        HttpResponse response = HttpUtils.doGet(weatherServiceConfig.getHost(),
                weatherServiceConfig.getPath(), HttpMethod.GET.name(), headers, querys);

        String JSON_STR = EntityUtils.toString(response.getEntity());
        weatherInfo = JSON.parseObject(JSON_STR, new TypeReference<WeatherInfo>() {});

        return weatherInfo;
    }
}
