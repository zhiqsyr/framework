package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Scope("prototype")
@Component("numberSequenceVal")
public class NumberSequenceValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNumberSequence(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "numberSequence";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "值应该由数字组成.";
	}

}
