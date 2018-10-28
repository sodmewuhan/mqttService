package com.datasensorn.mqttservice;

import com.datasensorn.mqttservice.Utils.Constant;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * spring boot 启动类
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ServletComponentScan
@EnableAsync(proxyTargetClass = true)
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

    /**
     * 解决tomcat 8 上传文件限制在10M之内的限制
     * @return
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(Constant.APP_FILELENGTH);
            }
        });
        return tomcat;
    }
}
