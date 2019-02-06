package com.datasensorn.mqttservice.controller.model;

import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import lombok.Data;

import java.util.List;

/**
 * 用户首页的设备和水质的返回对象
 */
@Data
public class BoxAndWaterStatusDTO {

    private BoxInfoDTO boxInfoDTO;

    private Float oxygen;  //溶氧量

    private Float ph; // PH值

    private Float temp; //温度

    private Float other;  //其他

    /**
     * 管理的设备状态
     */
    private List<BoxStatusDTO> boxStatusDTOS;
}
