package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器的图表
 */
@RestController
@RequestMapping(value = "/app/chart")
public class ChartRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartRestController.class);

    /**
     * 得到一天的数据
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.POST)
    public Result getday() {
        LOGGER.info("called the method is getday");
        ResultGenerator resultGenerator = new ResultGenerator();

        return  resultGenerator.genSuccessResult();
    }
}
