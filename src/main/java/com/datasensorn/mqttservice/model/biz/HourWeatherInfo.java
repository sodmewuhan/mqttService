package com.datasensorn.mqttservice.model.biz;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class HourWeatherInfo {

    private String time;

    private String weather_code;

    private String temperature;

    private String areaid;

    private String wind_direction;

    private String wind_power;

    private String area;

    private String weather;
}
