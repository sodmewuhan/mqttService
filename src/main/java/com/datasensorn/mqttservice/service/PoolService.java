package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.biz.FishpondInfo;
import com.datasensorn.mqttservice.model.biz.PoolCurrentStatus;

/**
 * 鱼塘服务
 */
public interface PoolService {

    /**
     * 添加鱼塘
     * @param fishpondInfo
     * @return
     */
    @Deprecated
    public int addFishpond(FishpondInfo fishpondInfo);

    /**
     * 根据盒子ID，得到当前盒子上报指标信息
     * @param boxid
     */
    PoolCurrentStatus getCurrentStatus(String boxid);
}
