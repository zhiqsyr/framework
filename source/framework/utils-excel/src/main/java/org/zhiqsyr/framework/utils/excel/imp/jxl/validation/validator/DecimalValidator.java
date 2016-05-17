package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @author Lucas
 * @date 2014-1-7 下午4:27:35
 */
@Component("decimalVal")
@Scope("prototype")
public class DecimalValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNumeric(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "decimal";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "值应该由数字组成!";
	}
}
