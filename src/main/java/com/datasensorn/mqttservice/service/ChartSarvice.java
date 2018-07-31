package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.Request.ChartData;
import com.datasensorn.mqttservice.model.Request.ChartRequest;

/**
 * 手机端的图表服务
 */
public interface ChartSarvice {

    /**
     * 获取图
     * @param chartRequest
     * @return
     */
    public ChartData getChartSerial(ChartRequest chartRequest);

}
