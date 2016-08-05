package org.zhiqsyr.framework.utils.throwable;

import java.util.Collection;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Custom assertion utility class that assists in validating arguments.
 * 
 * @date 2016-6-20
 */
public class Assert extends org.springframework.util.Assert{

    public static void isNull(Object object, int errorCode) {
    	isNull(object, errorCode, String.valueOf(errorCode));
    }
	/**
	 * object 不为 null 时抛出异常
	 * 
	 * @param object		被验证的对象
	 * @param errorCode		错误代码，例如 Global.EC_SERVER_EXCEPTION、HttpStatus.value()
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void isNull(Object object, int errorCode, String message) {
    	if (object != null) {
			throw getInstance(errorCode, message);
		}
    }
    
    public static void isNull(Object object, HttpStatus httpStatus) {
    	isNull(object, httpStatus, httpStatus.name());
    } 
	/**
	 * object 不为 null 时抛出异常
	 * 
	 * @param object		被验证的对象
	 * @param httpStatus	org.springframework.http.HttpStatus
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void isNull(Object object, HttpStatus httpStatus, String message) {
    	if (object != null) {
			throw getInstance(httpStatus, message);
		}
    }  	
	
    /**
     * object = null 时抛出异常，错误码 400(HttpStatus.BAD_REQUEST)
     *
     * @param object
     */
    public static void notNull(Object object) {
		notNull(object, HttpStatus.BAD_REQUEST, "This object must not be null");
    }  
    public static void notNull(Object... object) {
		notNull(object, HttpStatus.BAD_REQUEST, "These objects must not be null");
    }
    public static void notNull(Object object, int errorCode) {
		notNull(object, errorCode, String.valueOf(errorCode));
    }
	/**
	 * object = null 时抛出异常
	 * 
	 * @param object		被验证的对象
	 * @param errorCode		错误代码，例如 Global.EC_SERVER_EXCEPTION、HttpStatus.value()
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void notNull(Object object, int errorCode, String message) {
    	if (object == null) {
			throw getInstance(errorCode, message);
		}
    }
    
    public static void notNull(Object object, HttpStatus httpStatus) {
    	notNull(object, httpStatus, httpStatus.name());
    } 
	/**
	 * object = null 时抛出异常
	 * 
	 * @param object		被验证的对象
	 * @param httpStatus	org.springframework.http.HttpStatus
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void notNull(Object object, HttpStatus httpStatus, String message) {
    	if (object == null) {
			throw getInstance(httpStatus, message);
		}
    }  
    
    public static void notNull(Object entity, String... field) {
        BeanWrapperImpl bw = new BeanWrapperImpl(entity);
        for (String f : field) {
        	notNull(bw.getPropertyValue(f), HttpStatus.BAD_REQUEST, f + " must not be null");
        }
    }

    /**
     * str is blank 时抛出异常，错误码 400(HttpStatus.BAD_REQUEST)
     *
     * @param str
     */
    public static void notBlank(String str) {
    	notBlank(str, HttpStatus.BAD_REQUEST, "This string must not be blank");
    }
    public static void notBlank(String str, int errorCode) {
    	notBlank(str, errorCode, String.valueOf(errorCode));
    }
	/**
	 * str is blank 时抛出异常
	 * 
	 * @param str			被验证的字符串
	 * @param errorCode		错误代码，例如 Global.EC_SERVER_EXCEPTION、HttpStatus.value()
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void notBlank(String str, int errorCode, String message) {
    	if (!StringUtils.hasText(str)) {
			throw getInstance(errorCode, message);
		}
    }
    
    public static void notBlank(String str, HttpStatus httpStatus) {
    	notBlank(str, httpStatus, httpStatus.name());
    } 
	/**
	 * str is blank 时抛出异常
	 * 
	 * @param str			被验证的字符串
	 * @param httpStatus	org.springframework.http.HttpStatus
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void notBlank(String str, HttpStatus httpStatus, String message) {
    	if (!StringUtils.hasText(str)) {
			throw getInstance(httpStatus, message);
		}
    }    
    
    public static void notBlank(Object entity, String... field) {
        BeanWrapperImpl bw = new BeanWrapperImpl(entity);
        for (String f : field) {
        	notNull(bw.getPropertyValue(f), HttpStatus.BAD_REQUEST, f + " must not be null");
        	notBlank(String.valueOf(bw.getPropertyValue(f)), HttpStatus.BAD_REQUEST, f + " must not be blank");
        }
    }
    
    public static void notEmpty(Collection<?> col, int errorCode) {
    	notEmpty(col, errorCode, String.valueOf(errorCode));
    }
	/**
	 * 集合为空时抛出异常
	 * 
	 * @param col			被验证的集合
	 * @param errorCode		错误代码，例如 Global.EC_SERVER_EXCEPTION、HttpStatus.value()
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void notEmpty(Collection<?> col, int errorCode, String message) {
    	if (CollectionUtils.isEmpty(col)) {
			throw getInstance(errorCode, message);
		}
    }
    
    public static void notEmpty(Collection<?> col, HttpStatus httpStatus) {
    	notEmpty(col, httpStatus, httpStatus.name());
    } 
	/**
	 * 集合为空时抛出异常
	 * 
	 * @param col			被验证的集合
	 * @param httpStatus	org.springframework.http.HttpStatus
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void notEmpty(Collection<?> col, HttpStatus httpStatus, String message) {
    	if (CollectionUtils.isEmpty(col)) {
			throw getInstance(httpStatus, message);
		}
    }
    
    public static void isTrue(boolean expr, int errorCode) {
    	isTrue(expr, errorCode, String.valueOf(errorCode));
    }
	/**
	 * expr = false 时抛出异常
	 * 
	 * @param expr			被验证的表达式
	 * @param errorCode		错误代码，例如 Global.EC_SERVER_EXCEPTION、HttpStatus.value()
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void isTrue(boolean expr, int errorCode, String message) {
    	if (!expr) {
			throw getInstance(errorCode, message);
		}
    }
    
    public static void isTrue(boolean expr, HttpStatus httpStatus) {
    	isTrue(expr, httpStatus, httpStatus.name());
    } 
	/**
	 * expr = false 时抛出异常
	 * 
	 * @param expr			被验证的表达式
	 * @param httpStatus	org.springframework.http.HttpStatus
	 * @param message		错误信息，说明性的文字，例如 spaceId must not be null
	 */    
    public static void isTrue(boolean expr, HttpStatus httpStatus, String message) {
    	if (!expr) {
			throw getInstance(httpStatus, message);
		}
    }    
    
	private static RuntimeException getInstance(int errorCode, String message) {
		return new BusinessException(errorCode, message);
	}
	private static RuntimeException getInstance(HttpStatus httpStatus, String message) {
		return new BusinessException(httpStatus, message);
	}
    
}

