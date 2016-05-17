package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 
 * @author dylan
 * @date 2013-1-23 上午10:18:39
 */
@Component("batchIdPro")
@Scope("prototype")
public class UploadBatchIdProcessor extends AbstractColumnProcessor{

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		String batchId = model.properties().getParameter("batchId");
		cell.setValue(batchId);
	}

}
