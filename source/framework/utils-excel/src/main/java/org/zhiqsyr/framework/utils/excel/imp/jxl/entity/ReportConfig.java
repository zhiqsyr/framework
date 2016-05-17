package org.zhiqsyr.framework.utils.excel.imp.jxl.entity;

import java.io.Serializable;
import java.util.List;

public class ReportConfig implements Serializable {

	private static final long serialVersionUID = 162810334755629834L;

	private String id;
	private String reportName;
	private String tableName;

	private String sheetName;		// 解析的sheet页
	private Integer startRow;
	private Integer endRow;
	private Integer startCol;
	private Integer endCol;
	private String serialNumCol;

	private String reportType;
	private String templateVersion;	// 模板文件版本号
	private String templatePath;	// 模板文件存放的路径

	private List<ColumnConfig> columnConfigs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getStartCol() {
		return startCol;
	}

	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}

	public Integer getEndCol() {
		return endCol;
	}

	public void setEndCol(Integer endCol) {
		this.endCol = endCol;
	}

	public String getSerialNumCol() {
		return serialNumCol;
	}

	public void setSerialNumCol(String serialNumCol) {
		this.serialNumCol = serialNumCol;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public List<ColumnConfig> getColumnConfigs() {
		return columnConfigs;
	}

	public void setColumnConfigs(List<ColumnConfig> columnConfigs) {
		this.columnConfigs = columnConfigs;
	}

}
