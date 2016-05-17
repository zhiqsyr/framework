package org.zhiqsyr.framework.web.zk.vm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zhiqsyr.framework.utils.common.CommonConstants;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class BaseVM {
	
	private final Log log = LogFactory.getLog(getClass());
	
	public static final String PARENT_WIN = "_parent_win";

	public Window showModal(String url) {
		return showModal(url, null, new HashMap<String, Object>(), null, null, true, true, true, false);
    }
	
	public Window showModal(String url, Map<String, Object> arg) {
		return showModal(url, null, arg, null, null, true, true, true, false);
    }
	
	public Window showModal(String url, Component parent, Map<String, Object> arg) {
		return showModal(url, parent, arg, null, null, true, true, true, false);
    }
	
	public Window showModal(String url, Component parent, Map<String, Object> arg, String width, String height, boolean closeable, boolean sizeable, boolean maximizable,
		    boolean maximized) {
		// 父窗口对象放入PARENT_WIN的变量值里面
		Window win = (Window) Executions.createComponents(url, parent, arg);

		if (!StringUtils.isBlank(width)) {
		    win.setWidth(width);
		}

		if (!StringUtils.isBlank(height)) {
		    win.setHeight(height);
		}

		win.setClosable(closeable);
		win.setSizable(sizeable);
		win.setMaximizable(maximizable);
		win.setMaximized(maximized);
		
		win.doModal();
		return win;
    }
	
	@SuppressWarnings("unchecked")
    protected <T> T getArgValue(Class<T> type, String paramName) {
		Map<String, Object> arg = (Map<String, Object>) Executions.getCurrent().getArg();
		return arg == null ? null : (T) arg.get(paramName);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getRequestAttribute(Class<T> type, String paramName) {
    	return (T) Executions.getCurrent().getAttribute(paramName);
    }

    protected String getRequestParameter(String paramName) {
    	return Executions.getCurrent().getParameter(paramName);
    }

    protected String[] getRequestParameterValues(String paramName) {
    	return Executions.getCurrent().getParameterValues(paramName);
    }

	public void showInformationBox(String message) {
		try {
		    Messagebox.show(message, CommonConstants.PROJECT_NAME, Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }

    public void showErrorBox(String message) {
		try {
		    Messagebox.show(message, CommonConstants.PROJECT_NAME, Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }

    public void showWarningBox(String message) {
		try {
		    Messagebox.show(message, CommonConstants.PROJECT_NAME, Messagebox.OK, Messagebox.EXCLAMATION);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }
    
    public boolean showQuestionBox(String message) {
		try {
		    return Messagebox.show(message, CommonConstants.PROJECT_NAME, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES;
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
    }
    
    /**
     * 加载页面时创建一个原始对象，用于点击"关闭"按钮时返回未修改的实体对象
     * @author Lucas
     * @param type
     * @param obj
     * @return
     */
    public <T> T copyEntity(Class<T> clazz, T obj){
    	T t = null;
    	if(obj == null) return t;
		try {
			t = clazz.newInstance();
			
			for (Method m : clazz.getMethods()) {
				String mName = m.getName();
				if (!mName.startsWith("set")) continue;
				String key = mName.substring(3);
				
				try {
					Method med = clazz.getMethod("get"+key);
					m.invoke(t, med.invoke(obj));
				} catch (IllegalArgumentException e) {
					log.warn(e.getMessage());
				} catch (InvocationTargetException e) {
					log.warn(e.getMessage());
				} catch (Exception e) {
					log.warn(e.getMessage());
				}
			}
			
		} catch (InstantiationException e) {
			log.warn("BaseVM.java,反射实体对象失败.原因:"+e.getMessage());
		} catch (IllegalAccessException e) {
			log.warn("BaseVM.java,反射实体对象失败.原因:"+e.getMessage());
		}
		
    	return t;
    }
    
    /**
     * 取消对象属性值变化
     * @param clazz
     * @param prototype
     * @param transienttype
     */
    public <T> void cancelRecord(Class<T> clazz, T prototype, T transienttype){
		for (Method m : clazz.getMethods()) {
			String mName = m.getName();
			if (!mName.startsWith("set")) continue;
			String key = mName.substring(3);
			
			try {
				Method med = clazz.getMethod("get"+key);
				m.invoke(prototype, med.invoke(transienttype));
			} catch (IllegalArgumentException e) {
				log.warn(e.getMessage());
			} catch (InvocationTargetException e) {
				log.warn(e.getMessage());
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
		}
    }

}
