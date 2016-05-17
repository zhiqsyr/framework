package org.zhiqsyr.framework.utils.excel.imp.jxl.validation.converter;

import org.apache.commons.lang.StringUtils;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.util.DataType;
import org.zhiqsyr.framework.utils.excel.imp.jxl.util.StringHelper;
import org.springframework.stereotype.Component;


@Component("defaultDataCv")
public class DefaultDataConverter implements ColumnConverter {

	@Override
	public Object convert(ColumnConfig columnConfig, String strValue)
			throws DataConvertException {
		if (StringUtils.isBlank(strValue)) {
			return null;
		}
		strValue = strValue.trim();
		String dataType = columnConfig.getDataType();
		int type = DataType.nameToType(dataType);
		switch (type) {
		case DataType.BYTE:
		case DataType.SHORT:
		case DataType.INT:
			try {
				return Integer.parseInt(strValue);
			} catch (NumberFormatException e) {
				throw new DataConvertException("数据类型不是整型，转换失败.");
			}
		case DataType.LONG:
			try {
				return Long.parseLong(strValue);
			} catch (NumberFormatException e) {
				throw new DataConvertException("数据类型不是整型，转换失败.");
			}
		case DataType.FLOAT:
		case DataType.BIGDECIMAL:
		case DataType.DOUBLE:
			try {
				return Double.parseDouble(strValue);
			} catch (NumberFormatException e) {
				throw new DataConvertException("数据类型不是数字型，转换失败.");
			}
		case DataType.BOOLEAN:
			return StringHelper.parseBoolean(strValue);
		case DataType.UNKNOWN:
		case DataType.TIME:
		case DataType.DATE:
		case DataType.DATETIME:
		case DataType.STRING:
		default:
			return strValue;
		}
	}

}
