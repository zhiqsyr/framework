package org.zhiqsyr.model.page;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Sortor implements Serializable {

	public static final String ASC = "asc";
    public static final String DESC = "desc";
    
    private String propertyName;
    
    private boolean ascending;

    public Sortor() {

    }
    
    /**
     * @param propertyName	数据库字段别名
     * @param ascending	true=ASC，false=DESC
     */
    public Sortor(String propertyName, boolean ascending) {
    	this.propertyName = propertyName;
    	this.ascending = ascending;
    }
    
    public static Sortor asc(String propertyName) {
    	return new Sortor(propertyName, true);
    }
    public static Sortor desc(String propertyName) {
    	return new Sortor(propertyName, false);
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
