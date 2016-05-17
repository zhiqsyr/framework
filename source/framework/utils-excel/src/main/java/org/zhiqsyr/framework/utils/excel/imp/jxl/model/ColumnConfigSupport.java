package org.zhiqsyr.framework.utils.excel.imp.jxl.model;

import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;

/**
 * ColumnConfig关联支持类，统一设置和获取的接口
 * @see ColumnConfig
 * @author dylan
 * @date 2013-1-22 下午2:17:18
 */
public interface ColumnConfigSupport {
	/**
	 * 设置列配置模型
	 * @param columnConfig
	 */
	public void setColumnConfig(ColumnConfig columnConfig);

	/**
	 * 获取列配置
	 * @return
	 */
	public ColumnConfig getColumnConfig();
}
