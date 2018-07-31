package com.datasensorn.mqttservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 曲线图显示
 */
@Controller
@Deprecated
public class ChartsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartsController.class);

    @RequestMapping(value = "/index")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("/index");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }
}
