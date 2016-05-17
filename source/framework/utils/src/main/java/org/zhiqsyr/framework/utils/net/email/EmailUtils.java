package org.zhiqsyr.framework.utils.net.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.zhiqsyr.framework.utils.common.CommonConstants;
import org.zhiqsyr.framework.utils.i18n.properties.PropertiesUtils;


/**
 * 电子邮件工具类
 * 
 * @author dongbz 2015-5-22
 */
public class EmailUtils {

	/**
	 * <b>Function: <b>发送纯文本邮件
	 *
	 * @param toAddr	收信人邮件地址
	 * @param subject	邮件标题
	 * @param msg		邮件内容
	 */
	public static void sendTextMail(String toAddr, String subject, String msg) {
		SimpleEmail email = new SimpleEmail();
		email.setCharset(CommonConstants.DEFAULT_WEB_ENCODING);
		email.setHostName(PropertiesUtils.getValueInSystem("email.smtp.host"));
		try {
			email.addTo(toAddr);
			email.setFrom(PropertiesUtils.getValueInSystem("email.sender.addr"), 
					PropertiesUtils.getValueInSystem("email.sender.name"));
			email.setAuthentication(PropertiesUtils.getValueInSystem("email.user.name"), 
					PropertiesUtils.getValueInSystem("email.user.pwd"));
			email.setSubject(subject);
			email.setMsg(msg);
			
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * <b>Function: <b>向管理员发送纯文本邮件
	 *
	 * @param subject
	 * @param msg
	 */
	public static void sendTextMail2Admin(String subject, String msg) {
		sendTextMail(PropertiesUtils.getValueInSystem("email.admin.addr"), subject, msg);
	}
	
}
