package org.zhiqsyr.framework.dao.common.dialect;

import org.zhiqsyr.framework.model.page.OrderablePagination;

/**
 * SQL Server 方言
 * 
 * @author dongbz 2015-5-19
 */
public class MySqlDialect implements SqlDialect {

	@Override
	public String genOrderablePaginationSql(String sql,
			OrderablePagination pagination) {
		long totalSize, offset, surplus, limit;
		
		totalSize = pagination.getTotalSize();								// 总数
		offset = pagination.getActiveIndex() * pagination.getPageSize();	// 已查出数量
		// 可能查询条件变化，导致 totalSize 变化；当小于已查出数量时，查出第一页结果
		if (totalSize < offset) {											
			pagination.setActiveIndex(0);
			offset = pagination.getActiveIndex() * pagination.getPageSize();
		}
		// 最后一页时，特殊处理
		surplus = pagination.getTotalSize() - offset;						// 后续页面剩余记录总数
		limit = surplus > pagination.getPageSize() ? pagination.getPageSize() : surplus;

		return sql + " " + pagination.getForwardOrderBy() + " limit " + offset + "," + limit;
	}

	@Override
	public String genCurDbTimeSql() {
		return "select now() crtdate";
	}
}