package org.zhiqsyr.framework.utils.excel.imp.jxl.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.util.SqlBuildUtils;


@Repository
public class ReportConfigDao extends JxlBaseDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ReportConfig findByReportType(String reportType) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(SqlBuildUtils.addAlias2Fields("id, report_name, table_name, sheet_name, " +
				"start_row, end_row, start_col, end_col, serial_num_col, report_type, template_version, template_path"));
		sql.append(" FROM excel_report_config ");
		sql.append(" WHERE report_type = :reportType ");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("reportType", reportType);
		
		return (ReportConfig) namedParameterJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper(ReportConfig.class)).get(0);
	}
	
}
