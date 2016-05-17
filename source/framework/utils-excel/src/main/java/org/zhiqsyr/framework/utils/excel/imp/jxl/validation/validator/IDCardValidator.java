package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.util.Validators;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @author Lucas
 * @date 2014-3-19 下午3:40:11
 */
@Component("IDCardVal")
@Scope("prototype")
public class IDCardValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return Validators.getIDCardValidate(value.toLowerCase());
//				|| Validators.getIDCardValidateWithOutLastNumber(value.toLowerCase());
	}

	@Override
	protected String getDefaultErrMsg() {
		return "身份证无效,不是合法的身份证号码.";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "idCard";
	}
}
