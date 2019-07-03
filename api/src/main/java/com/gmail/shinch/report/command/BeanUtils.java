package com.gmail.shinch.report.command;

import org.springframework.context.ApplicationContext;

public class BeanUtils {
    public static <T> T getBean(String beanId, Class<T> requiredType) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        if( applicationContext == null ) {
            throw new NullPointerException("Spring의 ApplicationContext초기화 안됨");
        }
        return applicationContext.getBean(beanId, requiredType);
    }
}
