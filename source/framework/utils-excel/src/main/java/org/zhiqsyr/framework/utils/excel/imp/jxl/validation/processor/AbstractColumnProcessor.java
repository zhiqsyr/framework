package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.ParameterSet;
import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.Parameterizable;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;

/**
 * 数据列的处理器
 * @author dylan
 * @date 2012-9-28 下午1:33:57
 */
public abstract class AbstractColumnProcessor implements ColumnProcessor, Parameterizable {
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 配置的参数信息
	 */
	protected ParameterSet parameters;
	/**
	 * 当前列的列配置信息
	 */
	protected ColumnConfig columnConfig;
	/**
	 * 当前补贴项目的配置信息
	 */
	protected ReportConfig reportConfig;
	@Override
	public ParameterSet parameters() {
		return parameters;
	}

	@Override
	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * 获取参数值,如果结果为null,使用默认结果
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getParameter(String key, String defaultValue) {
		String value = null;
		if (parameters == null
				|| (value = parameters.getParameter(key)) == null) {
			value = defaultValue;
		}
		return value;
	}

	@Override
	public void process(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		this.reportConfig = model.getReportConfig();
		this.columnConfig = cell.getColumnConfig();
		doProcess(model, row, cell, value);
	}
	
	protected abstract void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value);

}
