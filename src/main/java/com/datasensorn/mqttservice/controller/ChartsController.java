package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.model.biz.AxisDatas;
import com.datasensorn.mqttservice.service.ChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 曲线图显示
 */
@Controller
@Deprecated
public class ChartsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartsController.class);

    @Autowired
    ChartService chartService;

    @RequestMapping(value = "/index")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("/index");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }

    /**
     * 得到溶氧量
     * @return
     */
    @RequestMapping(value = "/oxygen",method = RequestMethod.POST)
    @ResponseBody
    public AxisDatas getOxygenSerier(@RequestParam("days") int days) {

        return chartService.getOxygenData(days);
    }
}
