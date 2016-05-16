package org.zhiqsyr.model.page;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LabelValueBean<T> implements Comparable<LabelValueBean<T>>, Serializable {

    private String label;

    private T value;
    
    /** 复选框时标记是否选中 */
    private boolean checked;

    public LabelValueBean() {
        super();
    }

    public LabelValueBean(String label, T value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int compareTo(LabelValueBean<T> o) {
        return this.getLabel().compareTo(o.getLabel());
    }
    
    @Override
    public boolean equals(Object obj) {
		if(obj == null) {
		    return false;
		}
		if(!(obj instanceof LabelValueBean<?>)) {
		    return false;
		}
		LabelValueBean<?> other = (LabelValueBean<?>) obj;
		if (getValue() == null && other.getValue() == null) {
		    return true;
		}
		if (getValue() == null || other.getValue() == null) {
		    return false;
		}
		return this.getValue().equals(other.getValue());
    }

    @Override
    public int hashCode() {
    	int result = 17;
    	result = result + (value == null ? 0 : value.hashCode());
    	return result;
    }

    @Override
    public String toString() {
    	return "[label:" + this.getLabel() + ",value:" + this.getValue() + "]";
    }
    
}
