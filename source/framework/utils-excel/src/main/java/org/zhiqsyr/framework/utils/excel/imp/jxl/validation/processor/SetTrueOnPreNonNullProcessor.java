package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import org.apache.commons.lang3.StringUtils;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 当前面的一个格式不为null时，设置当前格子为true, 用在“是否农民专业合作社”上，合作社示范范围不为空时，这个格子的值应该为true
 * 
 * @author dylan
 * @date 2013-5-13 下午4:51:01
 */
@Component("setTrueOnPreNonNullPro")
@Scope("prototype")
public class SetTrueOnPreNonNullProcessor extends AbstractColumnProcessor {
	private static final String PARAM_NAME_P = "p";

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String preCol = getParameter(PARAM_NAME_P, "");
		Object preColValue = row.getValue(preCol);
		if (preColValue != null && StringUtils.isNotBlank(preColValue.toString())) {
			cell.setValue(true);
		}
	}

}
