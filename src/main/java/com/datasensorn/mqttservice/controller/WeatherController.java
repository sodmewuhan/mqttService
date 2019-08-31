package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.annotation.LoginRequired;
import com.datasensorn.mqttservice.controller.model.ProvCityDTO;
import com.datasensorn.mqttservice.model.Request.RequestParam;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.WeatherInfo;
import com.datasensorn.mqttservice.service.ThirdPartService;
import com.datasensorn.mqttservice.service.impl.AreaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 天气预报服务
 */
@RestController
@RequestMapping(value="/api/area")
public class WeatherController {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    ThirdPartService thirdPartService;

    @Autowired
    AreaServiceImpl areaService;

    /**
     * 获取省份信息
     * @return
     */
    @RequestMapping(value = "getProv",method = RequestMethod.POST)
    @LoginRequired
    public Result getProv() {
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            List<ProvCityDTO> list = areaService.getProv();
            return resultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            LOGGER.error("the interface is error ",e);
        }
        return resultGenerator.genFailResult("获取省份信息失败");
    }

    /**
     * 得到城市信息
     * @param prov
     * @return
     */
    @RequestMapping(value = "getCity", method = RequestMethod.POST)
    public Result getCity(@RequestBody String prov) {
        LOGGER.info("the parameter is " + prov);
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            List<ProvCityDTO> list = areaService.getCity(prov);
            return resultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            LOGGER.error("the interface is error ",e);
        }
        return resultGenerator.genFailResult("获取市县信息失败");
    }
    /**
     * 根据地区ID编号，得到当地的3天内天气预报
     * @param areaId
     * @return
     */
    @RequestMapping(value = "getWeather", method=RequestMethod.POST)
    public Result getWeather(@RequestBody RequestParam requestParam) {
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            WeatherInfo weatherInfo = thirdPartService.getWeatherInfo(requestParam.getId());
            return resultGenerator.genSuccessResult(weatherInfo);
        } catch (Exception e) {
            LOGGER.error("",e);
        }
        return resultGenerator.genFailResult("天气预报失败");
    }
}