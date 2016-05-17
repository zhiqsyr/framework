package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自然数校验
 * @author dylan
 * @date 2013-6-8 下午2:25:41
 */
@Scope("prototype")
@Component("nonNeIntVal")
public class NonNeIntegerValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNonNeInteger(value);
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return "值应该为自然数";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "nonNeInteger";
	}

}
