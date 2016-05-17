package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.ValidateResult;

/**
 * 数据列级别的数据校验
 * 
 * @author dylan
 * @date 2012-8-13 下午3:49:32
 */
public interface ColumnValidator {
	/**
	 * 对单元格数据进行校验
	 * 
	 * @param row
	 * @param data
	 * @return
	 */
	public ValidateResult validate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value);

	/**
	 * 当前列的配置信息
	 * 
	 * @return
	 */
	public ColumnConfig getColumnConfig();

}
