package org.zhiqsyr.framework.utils.excel.imp.jxl.validation;

import java.util.Collections;
import java.util.List;

import jxl.CellReferenceHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.ParameterSet;
import org.zhiqsyr.framework.utils.excel.imp.jxl.dao.ColumnConfigDao;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfigHelper;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.model.CellModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.model.RowModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.model.TableModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.util.JxlUtil;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.converter.ColumnConverter;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.converter.DataConvertException;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.converter.DefaultDataConverter;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.processor.ColumnProcessor;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.util.ValidateResourceBundle;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.validator.ColumnValidator;
import org.zhiqsyr.framework.utils.excel.spring.ApplicationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 默认的数据处理和数据校验服务类
 * 
 * @author dylan
 * @date 2012-9-28 下午1:24:50
 */
@Service("validateService")
public class DefaultValidateService implements ValidateService {
	@Autowired
	protected ValidateResourceBundle validateResourceBundle;

	@Autowired
	private ColumnConfigDao columnConfigDao;
	
	@Autowired
	protected ColumnConfigHelper columnConfigHelper;
	protected Log logger = LogFactory.getLog(getClass());

	@Override
	public UploadDataModel processAndValidate(ReportConfig reportConfig, 
			TableModel tableModel, ParameterSet parameters) {
		logger.debug("处理和校验过程开始,reportType=" + reportConfig.getReportType()
				+ ",parameters=" + parameters + "...");
		UploadDataModel dataModel = new UploadDataModel();
		dataModel.setProperties(parameters);

		dataModel.setReportConfig(reportConfig);
		if (tableModel == null) {
			logger.warn("处理和校验过程异常终止,没有任何可处理的数据,reportType="
					+ reportConfig.getReportType() + ",parameters="
					+ parameters + "...");
			dataModel.setValidateResult(new ValidateResult(false,
					validateResourceBundle.getMessage("global.nodata",
							"数据解析失败，未解析到任何数据.")));
			dataModel.setGlobalError(true);
			return dataModel;
		}
		// 开始进行数据处理和校验
		dataModel.setSheet(tableModel);
		// 进行全局的数据校验，如果全局校验失败，将不再进行后续的数据处理和校验
		ValidateResult validateResult = globalValidate(reportConfig, tableModel);
		dataModel.setValidateResult(validateResult);
		if (!validateResult.isSuccess()) {
			logger.warn("处理和校验过程异常终止,出现全局错误:" + validateResult.getMsg());
			dataModel.setGlobalError(true);
			return dataModel;
		}
		// 继续数据处理
		List<ColumnConfig> columnConfigs = columnConfigDao.findByReportId(reportConfig.getId());
		Collections.sort(columnConfigs);
		for (RowModel row : tableModel.rows()) {
			RowDataModel rowData = processRow(dataModel, row, columnConfigs);
			dataModel.addRow(rowData);
			ValidateResult rValidateResult = rowData.getValidateResult();
			if (rValidateResult != null && !rValidateResult.isSuccess()) {
				dataModel.getValidateResult().setSuccess(false);
			}
		}
		logger.debug("处理和校验过程结束,reportType=" + reportConfig.getReportType()
				+ ",parameters=" + parameters + "...");
		return dataModel;
	}

	/**
	 * 处理行数据
	 * 
	 * @param dataModel
	 * @param row
	 * @return
	 */
	protected RowDataModel processRow(UploadDataModel dataModel, RowModel row,
			List<ColumnConfig> columnConfigs) {

		logger.debug("处理和校验过程，开始处理行" + row.getSerial() + "...");
		RowDataModel rowData = new RowDataModel();
		rowData.setRowData(row);
		ValidateResult cValidateResult;
		CellDataModel cellData;
		CellModel cell;
		String position;
		for (ColumnConfig columnConfig : columnConfigs) {
			cell = null;
			position = columnConfig.getDataPosition();
			if (StringUtils.isNotBlank(position)) {
				position = position.trim();
				if (JxlUtil.isCellName(position)) {// 特定的单元格
					cell = dataModel.getSheet().getCell(
							CellReferenceHelper.getRow(position),
							CellReferenceHelper.getColumn(position));
				} else if (JxlUtil.isColName(position)) {// 普通列
					cell = row.getCell(CellReferenceHelper.getColumn(position));
				} else {
					Integer col = Integer.parseInt(position.trim());
					cell = row.getCell(col-1);
				}
			}
			cellData = processCell(dataModel, rowData, columnConfig, cell);
			cValidateResult = cellData.getValidateResult();
			if (cValidateResult != null && !cValidateResult.isSuccess()) {
				rowData.getValidateResult().setSuccess(false);
			}
			rowData.addCell(cellData);
		}

		logger.debug("处理和校验过程，处理行" + row.getSerial() + "结果:"
				+ rowData.getCellDatas());
		return rowData;
	}

	/**
	 * 对单元格的数据进行处理，返回数据处理的结果
	 * 
	 * @param dataModel
	 * @param rowData
	 * @param columnConfig
	 * @return
	 */
	protected CellDataModel processCell(UploadDataModel dataModel,
			RowDataModel rowData, ColumnConfig columnConfig, CellModel cell) {
		String cellValue = cell == null ? null : StringUtils.trim(cell.getContent());
		CellDataModel cellData = new CellDataModel(columnConfig);
		cellData.setCell(cell);
		cellData.setStrValue(cellValue);
		List<Object> beans = extractColumnProcessorAndValidators(columnConfig, cell);
		ValidateResult vr;
		for (Object bean : beans) {
			if (bean instanceof ColumnValidator) {
				vr = ((ColumnValidator) bean).validate(dataModel, rowData,
						cellData, cellData.getStrValue());
				if (!vr.isSuccess()) {
					cellData.setValidateResult(vr);
					break;
				}
			} else if (bean instanceof ColumnProcessor) {
				((ColumnProcessor) bean).process(dataModel, rowData, cellData,
						cellData.getStrValue());
			}
		}
		if(cellData.getValidateResult().isSuccess()){//数据校验不成功，不进行数据类型的转换
			convertCellValue(columnConfig, cellData);
		}
		return cellData;
	}
	
	protected List<Object> extractColumnProcessorAndValidators(ColumnConfig columnConfig, CellModel cell){
		return columnConfigHelper
				.getProcessorAndValidators(columnConfig);
	}

	/**
	 * 结果校验和处理完成之后，将字符串类型的值转换实际的值. 在转换中，如果单元格的值已经存在，则不进行转换。
	 * 
	 * @param columnConfig
	 * @param cellData
	 */
	protected void convertCellValue(ColumnConfig columnConfig,
			CellDataModel cellData) {
		ColumnConverter cConverter = columnConfigHelper
				.getColumnConverterBean(columnConfig);
		if (cConverter == null) {
			cConverter = ApplicationContextHolder.getBean(DefaultDataConverter.class);
		}
		Object obj;
		try {
			if (cellData.getValue() == null && cellData.getStrValue() != null) {
				obj = cConverter.convert(columnConfig, cellData.getStrValue());
				cellData.setValue(obj);
			}
		} catch (DataConvertException e) {
			ValidateResult vr = cellData.getValidateResult();
			vr.setSuccess(false);
			vr.setMsg(e.getMessage());
		}
	}

	/**
	 * 全局性的数据校验，比如对workbook版本号等信息进行校验（暂时不做验证）
	 * 
	 * @param reportConfig
	 * @param workbook
	 * @return
	 */
	protected ValidateResult globalValidate(ReportConfig reportConfig,
			TableModel tableModel) {
		return new ValidateResult();
	}

	public ValidateResourceBundle getValidateResourceBundle() {
		return validateResourceBundle;
	}

	public void setValidateResourceBundle(
			ValidateResourceBundle validateResourceBundle) {
		this.validateResourceBundle = validateResourceBundle;
	}

}
