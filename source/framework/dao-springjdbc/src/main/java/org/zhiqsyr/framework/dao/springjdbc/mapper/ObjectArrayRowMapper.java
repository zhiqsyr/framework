package org.zhiqsyr.framework.dao.springjdbc.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * RowMapper实现对于查询的结果集进行映射<br>
 * 对于一行记录，映射成一个Object[]；但是如果查询结果集只有一列时，则返回Object，也就是该列的值
 * 
 * @author dongbz 2015-5-19
 */
@SuppressWarnings("rawtypes")
public class ObjectArrayRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		if (columnCount < 2) {
		    return JdbcUtils.getResultSetValue(rs, 1);
		}
	
		Object[] results = new Object[columnCount];
		for (int i = 0; i < columnCount; i++) {
		    results[i] = JdbcUtils.getResultSetValue(rs, i + 1);
		}
		return results;
    }
}
