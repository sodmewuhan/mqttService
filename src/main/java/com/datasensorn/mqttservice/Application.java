package com.datasensorn.mqttservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * spring boot 启动类
 */
@SpringBootApplication
@EnableAutoConfiguration
//@EnableScheduling
@ComponentScan( basePackages = "${application.base-packages}" )
public class Application extends SpringBootServletInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /**
     * Load the Spring Integration Application Context
     *
     * @param args - command line arguments
     */
    public static void main(final String... args) {

        SpringApplication.run(Application.class, args);

        LOGGER.info("Service is started!");
    }

}
