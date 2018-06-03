package com.datasensorn.mqttservice.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class SpringContextUtil {

    private static ApplicationContext context = null;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        SpringContextUtil.context = context;
    }

    /**
     * 根据beanId获得对象
     *
     * @param beanId
     *            beanID
     * @return 返回配置的对象
     */
    public static Object getBean(String beanId) {
        return context.getBean(beanId);
    }

    public static void rollBack() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
