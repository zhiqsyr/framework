package org.zhiqsyr.framework.utils.throwable;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

    private String message;			// 异常主体信息

    private int errorCode;			// 错误代码
    private HttpStatus httpStatus;	// 返回的 httpStatus 信息

    public BusinessException() {
    	this(HttpStatus.BAD_REQUEST);
    }
    
    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
    
    public BusinessException(int errorCode) {
    	this(errorCode, String.valueOf(errorCode));
    	this.errorCode = errorCode;
    }
    
    /**
     * @param errorCode		错误代码，例如 Global.EC_SERVER_EXCEPTION、HttpStatus.value()
     * @param message		错误信息，说明性的文字，例如 spaceId must not be null
     */
    public BusinessException(int errorCode, String message) {
        super(String.valueOf(errorCode).intern());
        this.errorCode = errorCode;
        this.message = message;
    }
    
    public BusinessException(HttpStatus httpStatus) {
    	this(httpStatus, httpStatus.name());
    }
    
    /**
     * @param httpStatus	org.springframework.http.HttpStatus
     * @param message		错误说明信息，例如 spaceId must not be null
     */
    public BusinessException(HttpStatus httpStatus, String message) {
    	this(httpStatus.value(), message);
    }
    
	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }        
    
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

    public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
