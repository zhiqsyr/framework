package org.zhiqsyr.framework.utils.excel.imp.jxl.service.store;

import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.ParameterSet;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;

/**
 * 上报的数据保存服务
 * 
 * @author dylan
 * @date 2012-10-10 上午9:41:18
 */
public interface DataStoreService {
	/**
	 * 数据保存
	 * 
	 * @param model
	 * @param paramters
	 */
	public void storeData(UploadDataModel model, ParameterSet paramters);
}
