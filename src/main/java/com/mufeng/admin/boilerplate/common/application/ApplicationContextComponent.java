package com.mufeng.admin.boilerplate.common.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author HuangTianyu
 * @Date 2019-11-24 19:38
 * @Version 1.0
 */
@Component
public class ApplicationContextComponent implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> c) {
        if (applicationContext == null) return null;
        return applicationContext.getBean(c);
    }

    public static <T> T getBean(String name, Class<T> c) {
        if (applicationContext == null) return null;
        return applicationContext.getBean(name, c);
    }
}
