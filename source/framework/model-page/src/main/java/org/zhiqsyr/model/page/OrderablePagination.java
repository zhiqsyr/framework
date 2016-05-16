package org.zhiqsyr.model.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * 
 * @author dongbz 2015-5-19
 */
public class OrderablePagination {

	private int pageSize = 20;	// 每页记录条数
	
	private long totalSize;		// 总记录条数
	
	private int activeIndex;	// 当前页数，从 0 开始
	
	private List<Sortor> sortors = new ArrayList<Sortor>();
	
	public OrderablePagination() {

	}
	
	public OrderablePagination(long totalSize, int activeIndex) {
		this.totalSize = totalSize;
		this.activeIndex = activeIndex;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public List<Sortor> getSortors() {
		return sortors;
	}

	public void setSortors(List<Sortor> sortors) {
		this.sortors = sortors;
	}
	
	/**
	 * <b>Function: <b>sortors 后添加 sortor ；sortors 为空时，新建
	 *
	 * @param _sortors
	 */
	public OrderablePagination addSortors(Sortor... _sortors) {
		if (sortors == null) {
			sortors = new ArrayList<Sortor>();
		}
		for (Sortor _sortor : _sortors) {
			sortors.add(_sortor);
		}
		
		return this;
	}	
	
	/**
	 * 向sortors的最前列添加sortor；假如sortors中存在相同的propertyName，删除
	 * 
	 * @param sortors
	 * @param sortor
	 * @return
	 */
	public List<Sortor> addSortorToSortors(List<Sortor> sortors, Sortor sortor) {
		for (Sortor _sortor : sortors) {
			if (_sortor.getPropertyName().equals(sortor.getPropertyName())) {
				sortors.remove(_sortor);
				break;
			}
		}
		
		List<Sortor> result = new ArrayList<Sortor>(); 
		result.add(sortor);
		result.addAll(sortors);
		
		return result;
	}
	
	/**
	 * <b>Function: <b>正向拼接排序字段
	 *
	 * @return
	 */
	public String getForwardOrderBy() {
		StringBuffer forward = null;
		String orderBy = "";
		
		Sortor sortor;
		for (int i = 0; i < sortors.size(); i++) {
			if (i == 0) {
				forward = new StringBuffer(" order by ");
			}
			
			sortor = sortors.get(i);
			if (sortor.isAscending()) {
				forward.append(" " + sortor.getPropertyName() + " asc,");
			} else {
				forward.append(" " + sortor.getPropertyName() + " desc,");
			}
			
			if (i == sortors.size() - 1) {
				// ascBuilder.length() - 几，与 " desc," 空格数量有关
				orderBy = forward.substring(0, forward.length() - 1);	
			}
		}		
		
		return orderBy;
	}

	/**
	 * <b>Function: <b>反向拼接排序字段
	 *
	 * @return
	 */
	public String getReverseOrderBy() {
		StringBuffer reverse = null;
		String orderBy = "";
		
		Sortor sortor;
		for (int i = 0; i < sortors.size(); i++) {
			if (i == 0) {
				reverse = new StringBuffer("order by");
			}
			sortor = sortors.get(i);
			if (sortor.isAscending()) {
				reverse.append(" " + sortor.getPropertyName() + " desc,");
			} else {
				reverse.append(" " + sortor.getPropertyName() + " asc,");
			}
			if (i == sortors.size() - 1) {
				orderBy = reverse.substring(0, reverse.length() - 1);
			}
		}	
		
		return orderBy;
	}	
	
}
