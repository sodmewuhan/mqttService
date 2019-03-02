package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.biz.PoolCurrentStatus;

/**
 * 鱼塘服务
 */
public interface PoolService {

    /**
     * 根据盒子ID，得到当前盒子上报指标信息
     * @param boxid
     */
    PoolCurrentStatus getCurrentStatus(String boxid);
}
