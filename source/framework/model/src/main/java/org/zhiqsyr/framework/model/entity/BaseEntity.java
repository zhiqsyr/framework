package org.zhiqsyr.framework.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {

	public abstract Serializable getEntityId();
	
    @Override
    public boolean equals(Object obj) {
		if (obj == null) {
	 	    return false;
	 	}

		if (this == obj) {
		    return true;
		}
	
	 	if (!(getClass().equals(obj.getClass()))) {
	 	    return false;
	 	}
	 	BaseEntity other = (BaseEntity) obj;
	 	Serializable id = getEntityId();
	 	Serializable otherId = other.getEntityId();
	 	if (id == null && otherId == null) {
	 	    return true;
	 	}
	 	if (id == null || otherId == null) {
	 	    return false;
	 	}
	 	return id.equals(otherId);
    }

    @Override
    public int hashCode() {
	 	int hasCode = 23;
	 	Serializable id = getEntityId();
	 	hasCode = hasCode + (id == null ? 0 : id.hashCode());
	 	return hasCode;
    }

    @Override
    public String toString() {
    	return getClass() + "[id=" + getEntityId() + "]";
    }
    
}