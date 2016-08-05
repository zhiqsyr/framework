/*
 * Copyright 2008-2014 the original author or authors.
 * 
 *      http://www.iclassmate.net
 *
 * Project WeiKe
 */
package org.zhiqsyr.framework.web.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * 订制化 Spring 容器
 * 
 * @author dongbz 2016-08-03
 */
public class ApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
	
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        String[] names = applicationContext.getBeanNamesForType(clazz);
        
        if (names == null || names.length == 0) {
            return null;
        } else {
            return (T) applicationContext.getBean(names[0], clazz);
        }
    }

}
