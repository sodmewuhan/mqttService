package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.WeatherArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 得到地区信息
 */
//@Mapper
@Repository
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

    /**
     * 根据地区名称，得到地区信息
     * @param nameen
     * @return
     */
    WeatherArea getAreaIdByName(@Param("nameen") String nameen);
}