package org.zhiqsyr.framework.utils.excel.imp.jxl.parse;

import jxl.CellReferenceHelper;

import org.apache.commons.lang.StringUtils;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.ReportConfigSupport;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.model.RowModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.util.JxlUtil;


/**
 * 通过reportConfig中配置的serialNumCol列数据是否为空来决定是否进行后续的内容解析
 * 
 * @author dylan
 * @date 2013-5-9 下午4:34:08
 */
public class ReportConfigSupportParser extends ExcelTemplateParser implements
		ReportConfigSupport {
	
	private ReportConfig reportConfig;

	@Override
	protected int beforeAddRow(RowModel tr) {
		String serailCol = reportConfig.getSerialNumCol();
		if (StringUtils.isBlank(serailCol)) {
			return super.beforeAddRow(tr);
		} else {
			String content;
			if (JxlUtil.isColName(serailCol)) {
				// 列名
				content = tr.getCellContent(CellReferenceHelper
						.getColumn(serailCol));
			} else {
				int col = Integer.parseInt(serailCol.trim());
				content = tr.getCellContent(col - 1);
			}
			return StringUtils.isBlank(content) ? SKIP_SHEET : PARSE_CONTINUE;
		}
	}
	
	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

}
