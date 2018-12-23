package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.controller.model.ProvCityDTO;
import com.datasensorn.mqttservice.model.biz.WeatherArea;
import com.datasensorn.mqttservice.model.biz.mapper.WeatherAreaMapper;
import com.datasensorn.mqttservice.service.AreaService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private WeatherAreaMapper weatherAreaMapper;

    @Override
    public List<ProvCityDTO> getProv() throws  Exception {

        List<WeatherArea> list = weatherAreaMapper.getAllProv();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<ProvCityDTO> retnList = Lists.newArrayList();
        for(WeatherArea weatherArea : list) {
            ProvCityDTO dto = new ProvCityDTO();
            BeanUtils.copyProperties(dto,weatherArea);
            retnList.add(dto);
        }
        return retnList;
    }

    @Override
    public List<ProvCityDTO> getCity(String prov) throws Exception{
        Assert.notNull(prov,"the parameter prov is empty");
        List<WeatherArea> list = weatherAreaMapper.getCityByProv(prov);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<ProvCityDTO> retnList = Lists.newArrayList();
        for(WeatherArea weatherArea : list) {
            ProvCityDTO dto = new ProvCityDTO();
            BeanUtils.copyProperties(dto,weatherArea);
            retnList.add(dto);
        }
        return retnList;
    }
}
