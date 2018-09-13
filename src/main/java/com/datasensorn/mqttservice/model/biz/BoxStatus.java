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
    private String boxNumber;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备状态：0 关闭； 1 打开
     */
    private String status;

    public BoxStatus(String boxNumber, String deviceId, String status) {
        this.boxNumber = boxNumber;
        this.deviceId = deviceId;
        this.status = status;
    }
}
