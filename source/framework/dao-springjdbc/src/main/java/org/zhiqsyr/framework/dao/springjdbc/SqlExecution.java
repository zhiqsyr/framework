package org.zhiqsyr.framework.dao.springjdbc;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.zhiqsyr.framework.model.page.OrderablePagination;

/**
 * SQL 操作接口
 * 
 * @author dongbz 2015-5-19
 */
public interface SqlExecution {

	/**
	 * <b>Function: <b>执行给定的 SQL 语句，该语句返回单个 T 对象
	 *
	 * @param type	对象类型；仅支持未封装类型（如Long，Long，String，Object[]等）
	 * @param sql	给定的 SQL
	 * @param params	实参
	 * @param defaultValue	当 SQL 结果为空时，返回的默认值
	 * @return
	 */
	<T> T retrieveUniqueObjectBySql(Class<T> type, String sql, Object[] params, T defaultValue);
	<T> T retrieveUniqueObjectBySql(Class<T> type, String sql, Object... params);
    <T> T retrieveUniqueObjectBySql(Class<T> type, String sql);

	/**
	 * <b>Function: <b>执行给定的 SQL 语句，该语句返回单个 T 对象；
	 * <b>SQL 里 select 字段的别名必须与 T 对应的属性名相同，并且表字段的类型也必须与 T 对应的属性的类型一致
	 *
	 * @param type	对象类型
	 * @param sql	给定的 SQL
	 * @param params	实参
	 * @param defaultValue	当 SQL 结果为空时，返回的默认值
	 * @return
	 */
    <T> T retrieveWrapBeanBySql(Class<T> type, String sql, Object[] params, T defaultValue);
    <T> T retrieveWrapBeanBySql(Class<T> type, String sql, Object... params);
    <T> T retrieveWrapBeanBySql(Class<T> type, String sql);
    
    /**
     * <b>Function: <b>执行给定的 SQL 语句，该语句返回  T 对象集合
     *
	 * @param type	对象类型
	 * @param sql	给定的 SQL
	 * @param params	实参
     * @param rowMapper	SQL 里 select 字段的别名与 T 对应的属性名映射关系	
     * @param pagination	分页（可以包含排序）
     * @return
     */
    <T> List<T> retrieveBeansBySql(Class<T> type, String sql, Object[] params, RowMapper<T> rowMapper, OrderablePagination pagination);
    <T> List<T> retrieveBeansBySql(Class<T> type, String sql, Object[] params, RowMapper<T> rowMapper);
    <T> List<T> retrieveBeansBySql(Class<T> type, String sql, Object... params);
    <T> List<T> retrieveBeansBySql(Class<T> type, String sql);
    
	/**
	 * <b>Function: <b>执行给定的 SQL 语句，该语句返回  T 对象集合；
	 * <b>SQL 里 select 字段的别名必须与 T 对应的属性名相同，并且表字段的类型也必须与 T 对应的属性的类型一致
	 *
	 * @param type	对象类型
	 * @param sql	给定的 SQL
	 * @param params	实参
	 * @param pagination	分页（可以包含排序）
	 * @return
	 */
    <T> List<T> retrieveWrapBeansBySql(Class<T> type, String sql, Object[] params, OrderablePagination pagination);
    <T> List<T> retrieveWrapBeansBySql(Class<T> type, String sql, Object... params);
    <T> List<T> retrieveWrapBeansBySql(Class<T> type, String sql);
 
    /**
     * <b>Function: <b>执行给定的 SQL 语句，返回该语句影响的记录条数
     *
     * @param sql	给定的 SQL ，可以是任意 DML 等
     * @param params	实参
     * @return
     */
    int executeSql(String sql, Object... params);
    int executeSql(String sql);
    
    /**
     * <b>Function: <b>批量执行给定的 SQL 语句，返回该语句影响的记录条数
     *
     * @param sqls	给定的 SQL 列表，可以是任意 DML 等
     * @return
     */
    int[] executeBatchSql(String[] sqls);
    int[] executeBatchSql(Collection<String> sqls);
    /**
     * <b>Function: <b>批量执行给定的 SQL 语句，返回该语句影响的记录条数
     *
     * @param sql	给定的 SQL 列表，可以是任意 CRUD 等
     * @param bpss
     * @see org.springframework.jdbc.core.BatchPreparedStatementSetter
     * @return
     */    
    int[] executeBatchSql(String sql, BatchPreparedStatementSetter bpss);
    
    /**
     * <b>Function: <b>执行给定的存储过程 call 语句
     *
     * @param call
     * @param cscb
     * @see org.springframework.jdbc.core.CallableStatementCallback
     * @return
     */
    Object executeProcedure(String call, CallableStatementCallback<?> cscb);    
    
    /**
     * <b>Function: <b>获得数据库服务器当前时间
     *
     * @return
     */
    Date getCurrentDbTime();
    
}
