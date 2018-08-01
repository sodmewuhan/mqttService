package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.Request.ChartRequest;
import com.datasensorn.mqttservice.model.biz.AxisDatas;

/**
 * 手机端的图表服务
 */
public interface ChartSarvice {

    /**
     * 获取图
     * @param chartRequest
     * @return
     */
    public AxisDatas getChartSerial(ChartRequest chartRequest);

}
