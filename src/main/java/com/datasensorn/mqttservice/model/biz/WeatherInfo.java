package com.datasensorn.mqttservice.model.biz;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 天气预报信息
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class WeatherInfo {

    private String showapi_res_error;

    private String showapi_res_id;

    private String showapi_res_code;

    private HourWeatherInfos showapi_res_body;

}
