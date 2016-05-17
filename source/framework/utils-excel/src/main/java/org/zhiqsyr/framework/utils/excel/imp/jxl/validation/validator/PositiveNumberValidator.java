package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dylan
 * @date 2012-8-30 下午2:34:39
 */
@Scope("prototype")
@Component("posNumberVal")
public class PositiveNumberValidator extends AbstractColumnValidator {

	@Override
	protected boolean doValidate(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		return ValidateUtils.isNumberNoZero(value);
	}
	
	@Override
	protected String getDefaultErrMsg() {
		return "值应该为正数";
	}
	
	@Override
	protected String getDefaultErrMsgKey() {
		return "postiveNumber";
	}

}
