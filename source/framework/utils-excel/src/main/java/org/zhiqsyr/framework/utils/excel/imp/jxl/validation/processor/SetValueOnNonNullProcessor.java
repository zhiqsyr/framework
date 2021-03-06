package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import org.apache.commons.lang3.StringUtils;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 当前单元格的值不为空时，设置为指定的值.用法:#setValueOnNonNullPro?v=23
 * @author dylan
 * @date 2013-5-13 下午4:51:01
 */
@Component("setValueOnNonNullPro")
@Scope("prototype")
public class SetValueOnNonNullProcessor extends AbstractColumnProcessor {
	private static final String PARAM_NAME_V = "v";
	//TODO 是否将0当作null处理，对于1,0选择的输入框，把0当作null来处理
	private boolean zeroAsNull=true;

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		if (StringUtils.isNotBlank(value) && (zeroAsNull && !"0".equals(value.trim()))) {
			cell.setValue(getParameter(PARAM_NAME_V, null));
		}
	}

}
