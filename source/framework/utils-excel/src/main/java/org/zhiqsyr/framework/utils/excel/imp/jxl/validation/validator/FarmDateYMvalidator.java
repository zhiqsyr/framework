package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @author Lucas
 * @date 2014-3-20 上午10:01:38
 */
@Component("ymPattenVal")
@Scope("prototype")
public class FarmDateYMvalidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isYMDate(value);
	}

	@Override
	protected String getDefaultErrMsgKey() {
		return "birth.patten";
	}

	@Override
	protected String getDefaultErrMsg() {
		return "年月格式不正确.";
	}
}
