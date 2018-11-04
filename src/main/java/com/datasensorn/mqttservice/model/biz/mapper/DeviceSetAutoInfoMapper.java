package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.DeviceSetAutoInfo;

public interface DeviceSetAutoInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceSetAutoInfo record);

    int insertSelective(DeviceSetAutoInfo record);

    DeviceSetAutoInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceSetAutoInfo record);

    int updateByPrimaryKey(DeviceSetAutoInfo record);
}