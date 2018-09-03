package com.datasensorn.mqttservice.model.biz;

import lombok.Data;

/**
 * 设备状态
 */
@Data
public class BoxStatus {

    private Integer id;

    /**
     * 盒子编号
     */
    private Integer boxNumber;

    /**
     * 设备ID
     */
    private Integer deviceId;

    /**
     * 设备状态：0 关闭； 1 打开
     */
    private Integer status;
}
