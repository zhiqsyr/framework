package org.zhiqsyr.framework.utils.string;

import java.text.MessageFormat;

public class StringFormatUtils {
	
	public static String format(String message, Object... param) {
		MessageFormat msgformat = new MessageFormat(message);
		String msg = msgformat.format(param);
		return msg;
	}
	
	public static void main(String[] args) {
		StringFormatUtils.format("删除{0}机场失败！", new Object[]{"虹桥"});
	}
	
}
