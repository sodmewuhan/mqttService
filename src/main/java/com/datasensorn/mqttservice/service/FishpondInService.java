package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.biz.FishpondInfo;

/**
 * 鱼塘服务
 */
public interface FishpondInService {

    /**
     * 添加鱼塘
     * @param fishpondInfo
     * @return
     */
    public int addFishpond(FishpondInfo fishpondInfo);
}
