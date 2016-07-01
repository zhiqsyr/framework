package org.zhiqsyr.framework.dao.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.util.ObjectUtils;

@SuppressWarnings("serial")
public class CustomizedDetachedCriteria extends DetachedCriteria {

	protected CustomizedDetachedCriteria(String entityName) {
		super(entityName);
	}
   
	protected CustomizedDetachedCriteria(String entityName, String alias) {
		super(entityName, alias);
    }	

    public static CustomizedDetachedCriteria forEntityName(String entityName) {
    	return new CustomizedDetachedCriteria(entityName);
    }

    public static CustomizedDetachedCriteria forEntityName(String entityName, String alias) {
    	return new CustomizedDetachedCriteria(entityName, alias);
    }	
    
	@Override
	public CustomizedDetachedCriteria add(Criterion criterion) {
		if (criterion != null) {
			super.add(criterion);
		}
		return this;
	}
	
	public CustomizedDetachedCriteria addCriterions(Criterion... criterions) {
		if (!ObjectUtils.isEmpty(criterions)) {
		    for (Criterion criterion : criterions) {
		    	this.add(criterion);
		    }
		}
		return this;
    }	
	
    @Override
    public CustomizedDetachedCriteria addOrder(Order order) {
		if (order != null) {
		    super.addOrder(order);
		}
		return this;
    }
    
    public CustomizedDetachedCriteria addOrders(Order[] orders) {
		if (!ObjectUtils.isEmpty(orders)) {
		    for (Order order : orders) {
		    	super.addOrder(order);
		    }
		}
		return this;
    }	
    
    /**
     * <b>Function: <b>order 自定义排序类
     *
     * @param order
     * @return
     */
    public CustomizedDetachedCriteria addOrder(org.zhiqsyr.framework.model.page.Order order) {
		if (order != null) {
		    addOrder(order.isAscending() ? Order.asc(order.getPropertyName()) : Order.desc(order.getPropertyName()));	    
		}
		return this;
    }
    
    public CustomizedDetachedCriteria addOrders(org.zhiqsyr.framework.model.page.Order[] orders) {
		if (!ObjectUtils.isEmpty(orders)) {
		    for (org.zhiqsyr.framework.model.page.Order order : orders) {
		    	addOrder(order);
		    }
		}
		return this;
    }
    
    /**
     * 加入排序，如果 orders 为空，则用 defaultOrders 代替
     * 
     * @param orders
     * @param defaultOrders
     * @return
     */
    public CustomizedDetachedCriteria addOrders(Order[] orders, Order[] defaultOrders) {
		Order[] addOrders = orders;
		if (ObjectUtils.isEmpty(addOrders)) {
		    addOrders = defaultOrders;
		}
		addOrders(addOrders);
		return this;
    }    
    
}
