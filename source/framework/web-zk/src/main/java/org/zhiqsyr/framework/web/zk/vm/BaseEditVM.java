package org.zhiqsyr.framework.web.zk.vm;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

/**
 * 编辑VM 公共父类，作用：
 * 1）处理缓存导致的没有保存却显示修改后数据
 * 
 * @author dongbz 2015-10-23
 */
public class BaseEditVM<T> extends BaseVM {

	private T temp;

	/**
	 * <b>Function: <b>缓存初始 edit，以便还原
	 *
	 * @param clazz	类
	 * @param src	缓存的实例
	 */
	public void copyProperties(Class<T> clazz, T src) {
		try {
			temp = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			LoggerFactory.getLogger(getClass()).error(e.getMessage());
		} 
		
		BeanUtils.copyProperties(src, temp);
	}
	
	@Command
	public void onClose(@BindingParam("win")Window win, @BindingParam("tar")T tar) {
		BeanUtils.copyProperties(temp, tar);
		
		win.detach();
	} 
	
}
