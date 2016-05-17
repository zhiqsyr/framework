package org.zhiqsyr.framework.utils.excel.imp.jxl.entity;


import java.io.Serializable;
import java.util.List;

import org.zhiqsyr.framework.utils.excel.imp.jxl.model.ValueMapper;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.converter.ColumnConverter;

public class ColumnConfig implements Serializable, Comparable<ColumnConfig> {
	private static final long serialVersionUID = -3137094100706706628L;

	private String id;
	private String reportId;
	private String columnName;
	private String aliasName;
	private String dataPosition;
	private String dataType;
	
	private String processorAndValidator;
	private String scriptValidator;
	private String columnConverter;

	private Boolean needSave;
	private Boolean needShow;
	
	private String valueMapper;		// 对于select,checkbox,radio等类型的数据，配置其数据来源
	private String serial;			//合作社组织情况统计报表中的序号
	private Integer ordinal;
	
	private ReportConfig reportConfig;

	List<Object> processorAndValidators;
	ColumnConverter columnConverterBean;
	List<ValueMapper> valueMappers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getDataPosition() {
		return dataPosition;
	}

	public void setDataPosition(String dataPosition) {
		this.dataPosition = dataPosition;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getProcessorAndValidator() {
		return processorAndValidator;
	}

	public void setProcessorAndValidator(String processorAndValidator) {
		this.processorAndValidator = processorAndValidator;
	}

	public String getScriptValidator() {
		return scriptValidator;
	}

	public void setScriptValidator(String scriptValidator) {
		this.scriptValidator = scriptValidator;
	}

	public String getColumnConverter() {
		return columnConverter;
	}

	public void setColumnConverter(String columnConverter) {
		this.columnConverter = columnConverter;
	}

	public Boolean getNeedSave() {
		return needSave;
	}

	public void setNeedSave(Boolean needSave) {
		this.needSave = needSave;
	}

	public Boolean getNeedShow() {
		return needShow;
	}

	public void setNeedShow(Boolean needShow) {
		this.needShow = needShow;
	}

	public String getValueMapper() {
		return valueMapper;
	}

	public void setValueMapper(String valueMapper) {
		this.valueMapper = valueMapper;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	@Override
	public int compareTo(ColumnConfig other) {
		if (this.ordinal == null) {
			return 1;
		}
		if (other.ordinal == null) {
			return -1;
		}
		return this.ordinal < other.ordinal ? -1 : 1;
	}

}
