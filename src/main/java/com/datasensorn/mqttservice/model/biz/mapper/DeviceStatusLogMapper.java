package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.DeviceStatusLog;

public interface DeviceStatusLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceStatusLog record);

    int insertSelective(DeviceStatusLog record);

    DeviceStatusLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceStatusLog record);

    int updateByPrimaryKey(DeviceStatusLog record);
}