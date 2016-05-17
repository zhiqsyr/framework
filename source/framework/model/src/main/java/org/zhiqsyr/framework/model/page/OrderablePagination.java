package org.zhiqsyr.framework.model.page;

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
	
	private List<Order> orders = new ArrayList<Order>();
	
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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * <b>Function: <b>orders 后添加 order ；orders 为空时，新建
	 *
	 * @param _orders
	 */
	public OrderablePagination addOrders(Order... _orders) {
		if (orders == null) {
			orders = new ArrayList<Order>();
		}
		for (Order _order : _orders) {
			orders.add(_order);
		}
		
		return this;
	}	
	
	/**
	 * 向orders的最前列添加order；假如orders中存在相同的propertyName，删除
	 * 
	 * @param orders
	 * @param order
	 * @return
	 */
	public List<Order> addOrderToOrders(List<Order> orders, Order order) {
		for (Order _order : orders) {
			if (_order.getPropertyName().equals(order.getPropertyName())) {
				orders.remove(_order);
				break;
			}
		}
		
		List<Order> result = new ArrayList<Order>(); 
		result.add(order);
		result.addAll(orders);
		
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
		
		Order order;
		for (int i = 0; i < orders.size(); i++) {
			if (i == 0) {
				forward = new StringBuffer(" order by ");
			}
			
			order = orders.get(i);
			if (order.isAscending()) {
				forward.append(" " + order.getPropertyName() + " asc,");
			} else {
				forward.append(" " + order.getPropertyName() + " desc,");
			}
			
			if (i == orders.size() - 1) {
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
		
		Order order;
		for (int i = 0; i < orders.size(); i++) {
			if (i == 0) {
				reverse = new StringBuffer("order by");
			}
			order = orders.get(i);
			if (order.isAscending()) {
				reverse.append(" " + order.getPropertyName() + " desc,");
			} else {
				reverse.append(" " + order.getPropertyName() + " asc,");
			}
			if (i == orders.size() - 1) {
				orderBy = reverse.substring(0, reverse.length() - 1);
			}
		}	
		
		return orderBy;
	}	
	
}
