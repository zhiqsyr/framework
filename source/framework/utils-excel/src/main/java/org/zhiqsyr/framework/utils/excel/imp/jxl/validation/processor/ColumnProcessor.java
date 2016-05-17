package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;

/**
 * 数据列处理器
 * @author dylan
 * @date 2012-9-28 下午1:32:43
 */
public interface ColumnProcessor {
	/**
	 * 对单元格数据进行处理
	 * @param model 
	 * @param row
	 * @param cell 当前单元格的模型
	 * @param value 单元格目前的值
	 */
	public void process(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value);
}
