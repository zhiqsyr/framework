package org.zhiqsyr.framework.dao.common.dialect;

import org.zhiqsyr.framework.model.page.OrderablePagination;

/**
 * SQL Server 方言
 * 
 * @author dongbz 2015-5-19
 */
public class SqlServerDialect implements SqlDialect {

	@Override
	public String genOrderablePaginationSql(String sql,
			OrderablePagination pagination) {
		int totalSize, offset, surplus, limit;
		
		totalSize = Long.valueOf(pagination.getTotalSize()).intValue();		// 总数
		offset = pagination.getActiveIndex() * pagination.getPageSize();	// 已查出数量
		if (totalSize < offset) {	// 可能查询条件变化，导致 totalSize 变化；当小于已查出数量时，查出第一页结果
			pagination.setActiveIndex(0);
			offset = pagination.getActiveIndex() * pagination.getPageSize();
		}
		// 最后一页时，特殊处理
		surplus = Long.valueOf(pagination.getTotalSize()).intValue() - offset;	// 后续页面剩余记录总数
		limit = surplus > pagination.getPageSize() ? pagination.getPageSize() : surplus;

		StringBuilder select = new StringBuilder();
		select.append(" SELECT * FROM ( ");
		select.append(" 	SELECT TOP " + limit + " * FROM ( ");
		select.append(" 		SELECT TOP " + (offset + limit));
		select.append("				" + sql.substring(sql.toLowerCase().indexOf("select") + 6));
		select.append("				" + pagination.getForwardOrderBy());
		select.append(" 	) fir " + pagination.getReverseOrderBy());
		select.append(" ) fin " + pagination.getForwardOrderBy());
		
		return select.toString();
	}

	@Override
	public String genCurDbTimeSql() {
		return "select getdate() crtdate";
	}
}