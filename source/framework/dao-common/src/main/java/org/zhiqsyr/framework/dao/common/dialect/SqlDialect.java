package org.zhiqsyr.framework.dao.common.dialect;

import org.zhiqsyr.framework.model.page.OrderablePagination;

/**
 * SQL 方言
 * 
 * @author dongbz 2015-5-19
 */
public interface SqlDialect {

	/**
	 * <b>Function: <b>生成给定 SQL 的分页排序 SQL
	 *
	 * @param sql
	 * @param pagination	分页排序参数
	 * @return
	 */
    String genOrderablePaginationSql(String sql, OrderablePagination pagination);
    
    /**
     * <b>Function: <b>生成查询数据库当前时间的 SQL
     *
     * @return
     */
    String genCurDbTimeSql();

}