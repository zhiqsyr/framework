package org.zhiqsyr.framework.utils.excel.imp.jxl.service.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zhiqsyr.framework.utils.excel.imp.jxl.common.model.ParameterSet;
import org.zhiqsyr.framework.utils.excel.imp.jxl.dao.ColumnConfigDao;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ColumnConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.ReportConfig;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.RowDataModel;
import org.zhiqsyr.framework.utils.excel.imp.jxl.model.UploadDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * 默认的数据保存服务类
 * 
 * @author dylan
 * @date 2013-1-22 下午2:29:55
 */
@Service("dataStoreService")
public class DefaultDataStoreService implements DataStoreService {
	protected Log logger = LogFactory.getLog(getClass());
	public static final String DEFAULT_DATA_STORE_SERVICE_BEAN_NAME = "upload.defaultDataStoreService";

	@Autowired
	private ColumnConfigDao columnConfigDao;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public void storeData(UploadDataModel model, ParameterSet parameters) {
		ReportConfig reportConfig = model.getReportConfig();
		
		List<ColumnConfig> columnConfigs = columnConfigDao.findByReportId(reportConfig.getId());
		reportConfig.setColumnConfigs(columnConfigs);
		
		// 要进行更新和保存的数据
		List<MapSqlParameterSource> insertMap = new ArrayList<MapSqlParameterSource>();
		for (RowDataModel row : model.getRowDatas()) {
			insertMap.add(buildInsertMap(reportConfig, row));
		}
		// 新增的记录
		namedJdbcTemplate.batchUpdate(buildInsertSql(reportConfig),
				insertMap.toArray(new MapSqlParameterSource[insertMap.size()]));
	}

	/**
	 * 构造insert语句,除了业务字段之外
	 * 
	 * @param reportConfig
	 * @return
	 */
	protected String buildInsertSql(ReportConfig reportConfig) {
		List<String> columns = new ArrayList<String>();
		for (ColumnConfig cc : reportConfig.getColumnConfigs()) {
			if (cc.getNeedSave()) {
				columns.add(cc.getColumnName());
			}
		}

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ").append(reportConfig.getTableName())
				.append(" (");
		for (String columnName : columns) {
			sql.append(columnName).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") values (");
		for (String columnName : columns) {
			sql.append(":").append(columnName).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		logger.debug("INSERT SQL:" + sql.toString());
		return sql.toString();
	}

	protected MapSqlParameterSource buildInsertMap(ReportConfig reportConfig,
			RowDataModel row) {
//		System.out.println("rowNum:"+row.getRow());
		MapSqlParameterSource map = new MapSqlParameterSource();
		for (ColumnConfig cc : reportConfig.getColumnConfigs()) {
			if (cc.getNeedSave()) {
//				System.out.println(cc.getColumnName()+":"+row.getValue(cc.getColumnName()));
				map.addValue(cc.getColumnName(),
						row.getValue(cc.getColumnName()));
			}
		}
		return map;
	}

}
