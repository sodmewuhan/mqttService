package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.model.Request.ChartRequest;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.AxisDatas;
import com.datasensorn.mqttservice.service.ChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 服务器的图表
 */
@RestController
@RequestMapping(value = "/api/chart")
public class ChartRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartRestController.class);

    @Autowired
    private ChartService chartSarvice;

    /**
     * 得到一天的数据
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.POST)
    //@ResponseBody
    public Result getday(@RequestBody ChartRequest chartRequest) {
        LOGGER.info("called the method is getday");
        ResultGenerator resultGenerator = new ResultGenerator();
        AxisDatas chartSerial = chartSarvice.getChartSerial(chartRequest);
        return  resultGenerator.genSuccessResult(chartSerial);
    }
}
