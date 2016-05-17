package org.zhiqsyr.framework.dao.common.exception;

/**
 * dao 层异常
 * 
 * @author dongbz 2015-5-19
 */
@SuppressWarnings("serial")
public class DaoException extends RuntimeException {

	public DaoException(String message) {
		super(message);
    }
    
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
