package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.WeatherArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 得到地区信息
 */
@Mapper
public interface WeatherAreaMapper {
    int deleteByPrimaryKey(String areaid);

    int insert(WeatherArea record);

    int insertSelective(WeatherArea record);

    WeatherArea selectByPrimaryKey(String areaid);

    int updateByPrimaryKeySelective(WeatherArea record);

    int updateByPrimaryKey(WeatherArea record);

    /**
     * 得到省份的信息
     */
    List<WeatherArea> getAllProv();

    /**
     * 根据省信息得到市县信息
     * @param prov
     * @return
     */
    List<WeatherArea> getCityByProv(String prov);
}