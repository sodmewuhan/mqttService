package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.model.biz.FishpondInfo;
import com.datasensorn.mqttservice.model.biz.PoolCurrentStatus;
import com.datasensorn.mqttservice.service.PoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PoolServiceImpl implements PoolService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PoolServiceImpl.class);

    @Deprecated
    @Override
    public int addFishpond(FishpondInfo fishpondInfo) {
        return 0;
    }

    @Override
    public PoolCurrentStatus getCurrentStatus(String boxid) {

        PoolCurrentStatus poolCurrentStatus = new PoolCurrentStatus();

        poolCurrentStatus.setOxygen(55.6F);
        return poolCurrentStatus;
    }
}
