package org.zhiqsyr.framework.utils.excel.imp.jxl.service;


import org.zhiqsyr.framework.utils.excel.imp.jxl.dao.ColumnConfigDao;
import org.zhiqsyr.framework.utils.excel.imp.jxl.dao.ReportConfigDao;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author dylan
 * @date 2013-5-14 上午10:27:01
 */
@Service("reportConfigService")
public class ReportConfigService {

	@Autowired
	private ColumnConfigDao columnConfigDao;
	@Autowired
	private ReportConfigDao reportConfigDao;

	public ReportConfig findByReportType(String reportType) {
		return reportConfigDao.findByReportType(reportType);
	}
	
	public ColumnConfig findByReportTypeAndName(String reportType,
			String columnName) {
		return columnConfigDao.findByReportTypeAndColumnName(reportType, columnName);
	}

	public ColumnConfigDao getColumnConfigDao() {
		return columnConfigDao;
	}

	public ReportConfigDao getReportConfigDao() {
		return reportConfigDao;
	}
	
}
