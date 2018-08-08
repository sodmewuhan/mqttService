package com.datasensorn.mqttservice.model.biz;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class HourWeatherInfos {

    private List<HourWeatherInfo> hourList;

}
