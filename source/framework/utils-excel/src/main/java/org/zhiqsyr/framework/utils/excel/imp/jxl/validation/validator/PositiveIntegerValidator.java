package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 正整数校验
 * 
 * @author dylan
 * @date 2012-8-30 下午2:49:49
 */
@Scope("prototype")
@Component("posIntegerVal")
public class PositiveIntegerValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isPositiveInteger(value);
	}

	@Override
	protected String getDefaultErrMsg() {
		return "值应该为正整数";
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "positiveInteger";
	}

}
