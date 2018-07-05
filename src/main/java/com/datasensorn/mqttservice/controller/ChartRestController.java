package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.service.ChartSarvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 服务器的图表
 */
@RestController
@RequestMapping(value = "/app/chart")
public class ChartRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartRestController.class);

    @Autowired
    private ChartSarvice chartSarvice;
    /**
     * 得到一天的数据
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.POST)
    public Result getday() {
        LOGGER.info("called the method is getday");
        ResultGenerator resultGenerator = new ResultGenerator();
        chartSarvice.getChartSerial("15919829955","0",new Date(),new Date());
        return  resultGenerator.genSuccessResult();
    }
}
