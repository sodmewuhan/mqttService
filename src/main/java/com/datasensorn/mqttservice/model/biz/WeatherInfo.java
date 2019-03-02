package com.datasensorn.mqttservice.model.biz;

import lombok.Data;

/**
 * 天气预报信息
 */
@Data
public class WeatherInfo {

    private String showapi_res_error;

    private String showapi_res_id;

    private String showapi_res_code;

    private WeatherResBody showapi_res_body;

}
