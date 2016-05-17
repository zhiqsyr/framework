package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import java.util.Date;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 将数据值设置为当前时间
 * 
 * @author dylan
 * @date 2013-1-22 下午4:44:52
 */
@Component("curTimePro")
@Scope("prototype")
public class CurrentTimeProcessor extends AbstractColumnProcessor {

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		cell.setValue(new Date());
	}

}
