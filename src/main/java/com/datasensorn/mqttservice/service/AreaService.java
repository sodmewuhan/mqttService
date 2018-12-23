package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.controller.model.ProvCityDTO;

import java.util.List;

/**
 * 地区服务
 */
public interface AreaService {

    /**
     * 得到省
     * @return
     */
    List<ProvCityDTO> getProv() throws  Exception;

    /**
     * 得到地市
     * @param prov 省
     * @return
     */
    List<ProvCityDTO> getCity(String prov) throws  Exception;


}
