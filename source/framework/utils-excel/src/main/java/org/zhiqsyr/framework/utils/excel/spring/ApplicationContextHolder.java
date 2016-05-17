package org.zhiqsyr.framework.utils.excel.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	/**
	 * <b>Function: <b>返回给定 beanName 对应 bean 实例，byName
	 *
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}

	/**
	 * <b>Function: <b>返回给定 beanClass 对应 bean 实例，byType
	 *
	 * @param beanClass
	 * @return
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return (T) applicationContext.getBean(beanClass);
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if (applicationContext != null) {
			throw new BeanCreationException("ApplicationContextHolder already holded 'applicationContext'.");
		}
		
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("'applicationContext' property is null, ApplicationContextHolder not yet init.");
		}
			
		return applicationContext;
	}

}
