package org.zhiqsyr.framework.dao.springjdbc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.zhiqsyr.framework.dao.common.dialect.SqlDialect;
import org.zhiqsyr.framework.dao.common.dialect.SqlServerDialect;
import org.zhiqsyr.framework.dao.common.exception.DaoException;
import org.zhiqsyr.framework.dao.common.utils.SqlUtils;
import org.zhiqsyr.framework.dao.springjdbc.mapper.ObjectArrayRowMapper;
import org.zhiqsyr.framework.model.page.OrderablePagination;

/**
 * Spring JDBC 执行类
 * 
 * @author dongbz 2015-5-20
 */
public class SpringJdbcExecution implements SqlExecution {

	protected JdbcTemplate jdbcTemplate;
	
	private SqlDialect sqlDialect = new SqlServerDialect();
	
	public SpringJdbcExecution(DataSource dataSource) {
		Assert.notNull(dataSource);
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public SqlDialect getSqlDialect() {
		return sqlDialect;
	}

	public void setSqlDialect(SqlDialect sqlDialect) {
		this.sqlDialect = sqlDialect;
	}

	@Override
	public <T> T retrieveUniqueObjectBySql(Class<T> type, String sql,
			Object[] params, T defaultValue) {
		List<T> result = retrieveBeansBySql(type, sql, params);
		
		if (result.size() == 0) {
			return defaultValue;
		} else if (result.size() == 1) {
			return result.get(0);
		} else {
			throw new DaoException("There should be only one record. ");
		}
	}

	@Override
	public <T> T retrieveUniqueObjectBySql(Class<T> type, String sql,
			Object... params) {
		return retrieveUniqueObjectBySql(type, sql, params, null);
	}

	@Override
	public <T> T retrieveUniqueObjectBySql(Class<T> type, String sql) {
		return retrieveUniqueObjectBySql(type, sql, EMPTY_OBJECT_ARRAY, null);
	}

	@Override
	public <T> T retrieveWrapBeanBySql(Class<T> type, String sql,
			Object[] params, T defaultValue) {
		List<T> result = retrieveWrapBeansBySql(type, sql, params);
		
		if (result.size() == 0) {
			return defaultValue;
		} else if (result.size() == 1) {
			return result.get(0);
		} else {
			throw new DaoException("There should be only one record. ");
		}
	}

	@Override
	public <T> T retrieveWrapBeanBySql(Class<T> type, String sql,
			Object... params) {
		return retrieveWrapBeanBySql(type, sql, params, null);
	}

	@Override
	public <T> T retrieveWrapBeanBySql(Class<T> type, String sql) {
		return retrieveWrapBeanBySql(type, sql, EMPTY_OBJECT_ARRAY, null);
	}

	@SuppressWarnings("rawtypes")
	private static RowMapper OBJECT_ARRAY_MAPPER = new ObjectArrayRowMapper();
	
	@Override
	public <T> List<T> retrieveBeansBySql(Class<T> type, String sql,
			Object[] params, RowMapper<T> rowMapper, OrderablePagination pagination) {
	    if (params == null) {
	    	params = EMPTY_OBJECT_ARRAY;
	    }		
		
		try {
			if (pagination == null) {
				return jdbcTemplate.query(sql, params, rowMapper);
			} else {
				// 获得总数
				Number recordCount = retrieveUniqueObjectBySql(Number.class, SqlUtils.getCountSql(sql), params);
				if (recordCount != null) {
					pagination.setTotalSize(recordCount.intValue());
				}
				
				// 获得当前页记录列表
				String select = sqlDialect.genOrderablePaginationSql(sql, pagination);
				
				List<T> result = jdbcTemplate.query(select, params, rowMapper);
				return result;
			}
		} catch (RuntimeException e) {
		    throw new DaoException("Execute query sql[" + sql + "], and params is[" + Arrays.asList(params) + "], pagination is [" + pagination + "] error.", e);
		}
	}
	
	@Override
	public <T> List<T> retrieveBeansBySql(Class<T> type, String sql,
			Object[] params, RowMapper<T> rowMapper) {
		return retrieveBeansBySql(type, sql, params, rowMapper, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> retrieveBeansBySql(Class<T> type, String sql,
			Object... params) {
		return retrieveBeansBySql(type, sql, params, OBJECT_ARRAY_MAPPER, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> retrieveBeansBySql(Class<T> type, String sql) {
		return retrieveBeansBySql(type, sql, EMPTY_OBJECT_ARRAY, OBJECT_ARRAY_MAPPER, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T> retrieveWrapBeansBySql(Class<T> type, String sql,
			Object[] params, OrderablePagination pagination) {
		return retrieveBeansBySql(type, sql, params, new BeanPropertyRowMapper(type), pagination);
	}

	@Override
	public <T> List<T> retrieveWrapBeansBySql(Class<T> type, String sql,
			Object... params) {
		return retrieveWrapBeansBySql(type, sql, params, null);
	}

	@Override
	public <T> List<T> retrieveWrapBeansBySql(Class<T> type, String sql) {
		return retrieveWrapBeansBySql(type, sql, EMPTY_OBJECT_ARRAY, null);
	}

	@Override
	public int executeSql(String sql, Object... params) {
		if (params == null) {
		    params = EMPTY_OBJECT_ARRAY;
		}
		
		try {
			return jdbcTemplate.update(sql, params);
		} catch (RuntimeException e) {
		    throw new DaoException("Execute sql[" + sql + "], and params is[" + Arrays.asList(params) + "] error.", e);
		}		
	}

	@Override
	public int executeSql(String sql) {
		return executeSql(sql, EMPTY_OBJECT_ARRAY);
	}

	@Override
	public int[] executeBatchSql(String[] sqls) {
		if(ObjectUtils.isEmpty(sqls)) {
		    return null;
		}
		
		try {
			return jdbcTemplate.batchUpdate(sqls);
		} catch (RuntimeException e) {
		    throw new DaoException("Execute sqls[" + Arrays.asList(sqls) + "] error.", e);
		} 
	}

	@Override
	public int[] executeBatchSql(Collection<String> sqls) {
		if(CollectionUtils.isEmpty(sqls)) {
		    return null;
		}
		
		return executeBatchSql(sqls.toArray(new String[sqls.size()]));
	}

	@Override
	public int[] executeBatchSql(String sql, BatchPreparedStatementSetter bpss) {
		try {
			return jdbcTemplate.batchUpdate(sql, bpss);
		} catch (RuntimeException e) {
		    throw new DaoException("Execute sql[" + sql + "] error.", e);
		} 
	}

	@Override
	public Object executeProcedure(String call,
			CallableStatementCallback<?> cscb) {
		try {
			return jdbcTemplate.execute(call, cscb);
		} catch (RuntimeException e) {
		    throw new DaoException("Execute procedure[" + call + "] error.", e);
		} 
	}

	@Override
	public Date getCurrentDbTime() {
		return retrieveUniqueObjectBySql(Date.class, sqlDialect.genCurDbTimeSql());
	}

	private static final Object[] EMPTY_OBJECT_ARRAY = {};
	
}
