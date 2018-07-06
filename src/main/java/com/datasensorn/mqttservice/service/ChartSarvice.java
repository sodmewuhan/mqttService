package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.biz.ChartSerial;

import java.util.Date;

/**
 * 手机端的图表服务
 */
public interface ChartSarvice {

    /**
     *  得到图表的序列信息
     * @param boxId    盒子编号
     * @param days  天数
     * @return
     */
    public ChartSerial getChartSerial(String boxId,String deviceId,int days);

}
