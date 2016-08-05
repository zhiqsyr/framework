package org.zhiqsyr.framework.web.common.aop.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zhiqsyr.framework.web.common.utils.ApplicationContextHolder;

/**
 * 额外封装 applicationContext，用以获得 bean
 * 
 * @author dongbz 2016-08-03
 */
public class ContextInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		ApplicationContextHolder.setApplicationContext(applicationContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
    
}

