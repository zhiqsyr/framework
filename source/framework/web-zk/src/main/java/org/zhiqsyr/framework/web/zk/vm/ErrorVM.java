package org.zhiqsyr.framework.web.zk.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class ErrorVM extends BaseVM {

	private static final Logger logger = LoggerFactory.getLogger(ErrorVM.class);
	
	@Init
	public void init() {
		RuntimeException throwable = (RuntimeException) Executions.getCurrent().getAttribute("javax.servlet.error.exception");
		
		if (throwable != null) {
			logger.error(throwable.getMessage(), throwable);
		}
	}
	
}
