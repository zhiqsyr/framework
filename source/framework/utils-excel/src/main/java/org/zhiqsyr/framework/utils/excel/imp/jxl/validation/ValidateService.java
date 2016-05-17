package org.zhiqsyr.framework.utils.excel.imp.jxl.validation;

import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.ParameterSet;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.model.TableModel;

/**
 * 数据校验服务类
 * 
 * @author dylan
 * @date 2012-9-28 上午10:15:27
 */
public interface ValidateService {
	/**
	 * 进行数据处理和数据校验
	 * 
	 * @param reportConfig
	 * @param tableData
	 * @param parameters
	 * @return
	 */
	public UploadDataModel processAndValidate(ReportConfig reportConfig,
			TableModel tableData, ParameterSet parameters);
}
