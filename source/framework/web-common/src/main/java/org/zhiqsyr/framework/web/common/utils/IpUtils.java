package org.zhiqsyr.framework.web.common.utils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

	/**
	 * <b>Function: <b>返回客户端真实 IP 地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    
	    if (ip.contains(",")) {
			ip = ip.substring(0, ip.indexOf(","));
		}
	    return ip;  
	}  
	
}
