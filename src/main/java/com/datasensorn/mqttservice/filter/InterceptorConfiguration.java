package com.datasensorn.mqttservice.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器
        AjaxDomainInterceptor ajaxDomain = new AjaxDomainInterceptor();
        registry.addInterceptor(ajaxDomain).addPathPatterns("/**");

        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**");
    }
}
