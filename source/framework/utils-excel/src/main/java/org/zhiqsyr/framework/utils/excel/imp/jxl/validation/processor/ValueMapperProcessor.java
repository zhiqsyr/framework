package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor;

import org.apache.commons.lang3.StringUtils;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfigHelper;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.ValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 将值映射为valueMapper中定义的key
 * 
 * @author dylan
 * @date 2013-5-14 下午4:41:11
 */
@Component("valueMapperPro")
@Scope("prototype")
public class ValueMapperProcessor extends AbstractColumnProcessor {

	@Autowired
	ColumnConfigHelper configHelper;

	@Override
	protected void doProcess(UploadDataModel model, RowDataModel row,
			CellDataModel cell, String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
			for (ValueMapper vm : configHelper.getValueMappers(columnConfig)) {
				if (value.equals(vm.getLabel())) {
					cell.setValue(vm.getValue());
					break;
				}
			}
		}
	}

}
