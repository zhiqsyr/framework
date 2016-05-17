package org.zhiqsyr.framework.utils.excel.imp.jxl.service;

import java.io.InputStream;

import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.ParameterSet;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;


/**
 * 上报数据的数据处理服务类，该服务类负责对数据进行解析和校验
 * 
 * @author dylan
 * @date 2012-9-28 下午1:40:16
 */
public interface UploadProcessService {
	/**
	 * 对用户上报的数据进行解析和校验，并将结果封装为UploadDataModel数据模型返回
	 * 
	 * @param reportConfig
	 * @param inputStream
	 *            excel文件的输入流
	 * @param parameters
	 * @return
	 */
	public UploadDataModel process(ReportConfig reportConfig,
			InputStream inputStream, ParameterSet parameters);
}
