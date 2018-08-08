package com.datasensorn.mqttservice.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 关于密码方面的信息
 */
@Getter
@Configuration
public class WeatherServiceConfig {

    /**
     * 天气预报使用的appCode
     */
    @Value( "${application.weather.AppCode}" )
    private String appCode;

    @Value( "${application.weather.host}" )
    private String host;

    @Value( "${application.weather.path}" )
    private String path;
}
