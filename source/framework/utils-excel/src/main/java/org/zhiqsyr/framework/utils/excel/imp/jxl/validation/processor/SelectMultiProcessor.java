package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.util.StringHelper;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 从多个列中选择不为空的值作为当前列的值,多个列之间以逗号分隔
 * 
 * @author dylan
 * @date 2013-5-15 下午2:00:50
 */
@Component("selectMultiPro")
@Scope("prototype")
public class SelectMultiProcessor extends AbstractColumnProcessor {

	private String PARAM_NAME_SOURCE = "s";
	//TODO 是否将0当作null处理，对于1,0选择的输入框，把0当作null来处理
	private boolean zeroAsNull=true;

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String param = getParameter(PARAM_NAME_SOURCE, null);
		if (StringHelper.isNotEmpty(param)) {
			StringBuilder values = new StringBuilder();
			for (String c : ValidateUtils.extractPositions(param)) {
				Object val = findColumnValue(c, row);
				if (val != null) {
					if (values.length() > 0) {
						values.append(",");
					}
					values.append(val);
				}
			}
			cell.setValue(values.toString());
		}
	}
	

	private Object findColumnValue(String c, RowDataModel row) {
		Object val = null;
		for (ColumnConfig columnConfig : reportConfig.getColumnConfigs()) {
			if (c.equalsIgnoreCase(columnConfig.getDataPosition())) {
				val = row.getValue(columnConfig.getColumnName());
				break;
			}
		}
		if (val != null && ((val instanceof String
				&& StringHelper.isEmpty((String) val)) || (zeroAsNull && "0".equals(val.toString().trim())))) {
			// 确保当前格式不是空白的字符串
			return null;
		}
		return val;
	}

}
