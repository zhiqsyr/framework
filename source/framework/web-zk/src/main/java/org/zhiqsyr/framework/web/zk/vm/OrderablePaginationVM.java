package org.zhiqsyr.framework.web.zk.vm;

import org.zhiqsyr.framework.model.page.Order;
import org.zhiqsyr.framework.model.page.OrderablePagination;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Listbox;

/**
 * 分页与排序 基础VM
 * 
 * @author dongbz 2014-12-23
 */
public abstract class OrderablePaginationVM extends BaseVM {

	protected OrderablePagination sortPage = new OrderablePagination();
	
	/**
	 * <b>Function: <b>查询，不做重画 listbox 操作
	 *
	 */
	public abstract void onSearch();
	/**
	 * <b>Function: <b>查询，返回结果，并重画 listbox，避免 listhead 不做自适应（有滚动条出现时）
	 *
	 * @param resultLb
	 */
	@Command
	@NotifyChange({"result", "sortPage"})
	public void onResearch(@BindingParam("resultLb")Listbox resultLb) {
		sortPage.setActiveIndex(0);

		onPagingResearch(resultLb);
	}
	/**
	 * <b>Function: <b>响应分页标签，返回结果，并重画 listbox，避免 listhead 不做自适应（有滚动条出现时）
	 *
	 * @param resultLb
	 */
	@Command
	@NotifyChange({"result", "sortPage"})
	public void onPagingResearch(@BindingParam("resultLb")Listbox resultLb) {
		onSearch();
		
		resultLb.invalidate();
	}
	
	/**
	 * 排序
	 * 
	 * @param event
	 */
	@Command
	@NotifyChange({"result", "sortPage"})
	public void onSort(@BindingParam("prop")String propertyName, @BindingParam("asc")boolean asc) {
		Order order = new Order(propertyName, asc);
		sortPage.setOrders(sortPage.addOrderToOrders(sortPage.getOrders(), order));

		onSearch();
	}

	public OrderablePagination getSortPage() {
		return sortPage;
	}

	public void setSortPage(OrderablePagination sortPage) {
		this.sortPage = sortPage;
	}
	
}
