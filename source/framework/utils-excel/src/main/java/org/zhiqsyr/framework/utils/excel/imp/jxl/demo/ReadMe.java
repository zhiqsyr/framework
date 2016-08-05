package org.zhiqsyr.framework.utils.excel.imp.jxl.demo;

import java.io.FileInputStream;

import org.apache.commons.lang3.StringUtils;
import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.ParameterSet;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.CellDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.ReportConfigSupportParser;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.ExcelTemplateParser.ParseMode;
import org.zhiqsyr.framework.utils.excel.imp.jxl.parse.model.TableModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.service.ReportConfigService;
import org.zhiqsyr.framework.utils.excel.imp.jxl.service.store.DataStoreService;
import org.zhiqsyr.framework.utils.excel.imp.jxl.validation.ValidateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/org/cloud/excel/imp/jxl/demo/applicationContext_common.xml"})
public class ReadMe {
	
	@Autowired
	private ReportConfigService reportConfigService;
	@Autowired
	private ValidateService validateService;
	@Autowired
	private DataStoreService dataStoreService;
	
	/**
	 * TODO 导入Excel执行步骤，实践之中改善，不尽之处持续补充
	 *
	 * @throws Exception
	 *
	 * @author dongbz, 2015-12-10
	 */
	@Test
	public void test() throws Exception {
		// 1、根据 reportType 获取 Excel 整体配置，因此需要事先配好 ReportConfig
		ReportConfig reportConfig = reportConfigService.findByReportType("ZZHXX");
		
		FileInputStream fis = new FileInputStream(getClass().getResource("").getPath() + "/种植户信息.xls");

		// 2、补充配置，例如逐行/逐列解析，是否解析标注/样式等等
		ReportConfigSupportParser excelParser = new ReportConfigSupportParser();
		excelParser.setResource(fis);	// Excel文件输入流
		excelParser.setReportConfig(reportConfig);
		excelParser.setSheetName(reportConfig.getSheetName());
		excelParser.setParseMode(ParseMode.rowAfterRow);// 按行进行解析	
		excelParser.setStartRow(reportConfig.getStartRow());
		excelParser.setEndRow(reportConfig.getEndRow());
		excelParser.setStartCol(reportConfig.getStartCol());
		excelParser.setEndCol(reportConfig.getEndCol());	
		excelParser.setNeedParseComment(false);			// 不解析标注信息
		excelParser.setNeedParseStyle(false);			// 不解析样式信息
		
		// 3、解析并生成 tableModel（表模型，真实反映excel内容，包括坐标、值、长宽、样式等等）
		TableModel tabelModel = excelParser.parseTemplate();
		
		// 4、处理与校验各个字段的值，此时根据 reportId 获得对应 columnConfigs 配置，包括处理器与验证器的配置
		ParameterSet ps = new ParameterSet();
		UploadDataModel dataBase = validateService.processAndValidate(reportConfig, tabelModel, ps);
		
		// 5、获得校验的结果，如果返回错误信息，终止导入
		StringBuilder errorMessage = new StringBuilder();
		for (RowDataModel row : dataBase.getRowDatas()) {
			if (!row.getValidateResult().isSuccess()) {
				errorMessage.append("第 " + (row.getRow() + 1) + " 行验证未通过：\n");
				
				for (CellDataModel cell : row.getCellDatas()) {
					if (!cell.getValidateResult().isSuccess()) {
						errorMessage.append(cell.getAliasName() + "：" + cell.getValidateResult().getMsg() + "\n");
					}
				}
			}
		}
		if (StringUtils.isNotBlank(errorMessage.toString())) {
			System.out.println(errorMessage.toString());
			return;
		}	
		
		// 6、补充处理对应表的其他字段值
		
		// 7、批量插入
		dataStoreService.storeData(dataBase, ps);
	}
	
}
