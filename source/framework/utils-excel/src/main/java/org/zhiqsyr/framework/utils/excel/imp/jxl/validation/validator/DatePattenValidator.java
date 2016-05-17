package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @author Lucas
 * @date 2014-3-20 上午9:47:35
 */
@Component("datePattenVal")
@Scope("prototype")
public class DatePattenValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isDate(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "date.patten";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "日期格式不正确.";
	}
}
