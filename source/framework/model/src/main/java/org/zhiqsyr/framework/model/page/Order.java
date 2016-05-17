package org.zhiqsyr.framework.model.page;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {

	public static final String ASC = "asc";
    public static final String DESC = "desc";
    
    private String propertyName;
    
    private boolean ascending;

    public Order() {

    }
    
    /**
     * @param propertyName	数据库字段别名
     * @param ascending	true=ASC，false=DESC
     */
    public Order(String propertyName, boolean ascending) {
    	this.propertyName = propertyName;
    	this.ascending = ascending;
    }
    
    public static Order asc(String propertyName) {
    	return new Order(propertyName, true);
    }
    public static Order desc(String propertyName) {
    	return new Order(propertyName, false);
    }    
    
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
	
}
