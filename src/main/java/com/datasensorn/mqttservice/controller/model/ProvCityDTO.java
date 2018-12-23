package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

/**
 * 省和市的传递对象
 */
@Data
public class  ProvCityDTO {

    // 省 自治区
    private String proven;

    private String provcn;

    // 地市
    private String districten;

    private String districtcn;
}
