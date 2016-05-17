package org.zhiqsyr.framework.dao.mybatis.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.zhiqsyr.framework.dao.common.dialect.MySqlDialect;
import org.zhiqsyr.framework.dao.common.dialect.OracleDialect;
import org.zhiqsyr.framework.dao.common.dialect.SqlDialect;
import org.zhiqsyr.framework.dao.common.dialect.SqlServerDialect;
import org.zhiqsyr.framework.dao.common.utils.SqlUtils;
import org.zhiqsyr.framework.model.page.OrderablePagination;
import org.zhiqsyr.framework.utils.reflect.ReflectUtils;

/**
 * MyBatis的分页查询插件，通过拦截StatementHandler的prepare方法来实现
 * 
 * 1）只有在参数列表中包括 OrderablePagination 类型的参数时才进行分页查询；
 * 2）在多参数的情况下，只对第一个 OrderablePagination 类型的参数生效
 * 
 * 另外，在参数列表中，OrderablePagination 类型的参数无需用@Param来标注 
 * 
 * @author dongbz 2016-1-14
 */
@SuppressWarnings("unchecked")
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class OrderablePaginationPlugin implements Interceptor {
	
	private static final String MYSQL = "mysql",
								SQLSERVER = "sqlserver",
								ORACLE = "oracle";

	private static String dialect;			// 数据库类型
	private static SqlDialect sqlDialect;

	@SuppressWarnings("rawtypes")
	public Object intercept(Invocation inv) throws Throwable {
		if (inv.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler handler = (RoutingStatementHandler) inv.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtils.getValueByFieldName(handler, "delegate");
			MappedStatement stmt = (MappedStatement) ReflectUtils.getValueByFieldName(delegate, "mappedStatement");
			
			BoundSql boundSql = delegate.getBoundSql();
			Object param = boundSql.getParameterObject();		// 分页SQL <select> 中parameterType属性对应的实体参数，即 dao 中分页查询方法的参数		
			if (param == null) {
				return inv.proceed();
				
			} else {
				OrderablePagination op = null;
				if (param instanceof OrderablePagination) {		// 参数就是 OrderablePagination 实体
					op = (OrderablePagination) param;
				} else if (param instanceof Map) {				// 多个参数，自动封装成Map，判定是否含有OrderablePagination
					for (Entry entry : (Set<Entry>) ((Map) param).entrySet()) {
						if (entry.getValue() instanceof OrderablePagination) {
							op = (OrderablePagination) entry.getValue();
							break;
						}
					}
				} else {										// 参数为某个实体，判定是否拥有OrderablePagination属性
					op = ReflectUtils.getValueByFieldType(param, OrderablePagination.class);
					if (op == null) {
						return inv.proceed();
					}
				}
				
				if (op == null) {
					return inv.proceed();
				}
					
				Connection conn = (Connection) inv.getArgs()[0];
				
				String sql = boundSql.getSql();								// 原始sql 
				setTotalSize(sql, conn, stmt, boundSql, param, op);			// 设置记录总数

				String opSql = sqlDialect.genOrderablePaginationSql(sql, op);	// 生成分页排序sql
				ReflectUtils.setValueByFieldName(boundSql, "sql", opSql); 		// 将sql反射回BoundSql
			}
		}
		return inv.proceed();
	}

	/**
	 * 查询并设置记录总数
	 *
	 * @param sql
	 * @param conn
	 * @param mappedStmt
	 * @param boundSql
	 * @param param
	 * @param op
	 * @throws SQLException
	 *
	 * @author dongbz, 2016-1-15
	 */
	private void setTotalSize(String sql, Connection conn, MappedStatement mappedStmt,
            BoundSql boundSql, Object param, OrderablePagination op) throws SQLException {
    	PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			String countSql = SqlUtils.getCountSql(sql);					// 总记录数
			countStmt = conn.prepareStatement(countSql);
			
			ReflectUtils.setValueByFieldName(boundSql, "sql", countSql);	// 写入SQL
			DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStmt, param, boundSql);
			parameterHandler.setParameters(countStmt);
			
			rs = countStmt.executeQuery();
			int totalSize = 0;
			if (rs.next()) {
				totalSize = ((Number) rs.getObject(1)).intValue();
			}
			op.setTotalSize(totalSize);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			try {
				rs.close();
				countStmt.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
    }
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (dialect == null || dialect.trim().equals("")) {
			throw new RuntimeException("Please assign the property dialect.");
		} 
		
		switch (dialect) {
		case MYSQL:
			sqlDialect = new MySqlDialect();
			break;
		case SQLSERVER:
			sqlDialect = new SqlServerDialect();
			break;
		case ORACLE:
			sqlDialect = new OracleDialect();
			break;			
			
		default:
			throw new IllegalArgumentException("The property dialect is illegal.");
		}
	}
	
}
