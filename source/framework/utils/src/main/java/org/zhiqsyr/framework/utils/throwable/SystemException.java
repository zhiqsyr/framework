package org.zhiqsyr.framework.utils.throwable;

@SuppressWarnings("serial")
public class SystemException extends RuntimeException {

    private String message;			// 异常主体信息
    
    public SystemException() {
    	this("System Exception");
    }
    
    public SystemException(String message) {
        super(message);
        this.message = message;
    }
    
	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }        
    
}
