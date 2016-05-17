package org.zhiqsyr.framework.utils.excel.imp.jxl.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.string.sql.SqlUtils;


@Repository
public class ColumnConfigDao extends JxlBaseDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ColumnConfig> findByReportId(String reportId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(SqlUtils.addAlias2Fields("id, report_id, alias_name, column_name, " +
				"data_position, data_type, processor_and_validator, script_validator, " +
				"column_converter, need_save, need_show, value_mapper, serial, ordinal"));
		sql.append(" FROM excel_column_config ");
		sql.append(" WHERE report_id = :reportId ");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("reportId", reportId);
		
		return namedParameterJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper(ColumnConfig.class));
	}
	
	/**
	 * 根据reportType和columnName唯一返回ColumnConfig
	 *
	 * @param reportType excel_report_config.report_type
	 * @param columnName column_name
	 * @return
	 *
	 * @author dongbz, 2015-12-9
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ColumnConfig findByReportTypeAndColumnName(String reportType, String columnName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(SqlUtils.addAlias2Fields("c.id, report_id, alias_name, column_name, " +
				"data_position, data_type, processor_and_validator, script_validator, " +
				"column_converter, need_save, need_show, value_mapper, serial, ordinal"));
		sql.append(" FROM excel_column_config c ");
		sql.append(" JOIN excel_report_config r on c.report_id = r.id ");
		sql.append(" WHERE r.report_type = :reportType ");
		sql.append(" AND c.column_name = :columnName ");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("report_type", reportType);
		paramMap.put("column_name", columnName);
		
		return (ColumnConfig) namedParameterJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper(ColumnConfig.class)).get(0);
	}
	
}
