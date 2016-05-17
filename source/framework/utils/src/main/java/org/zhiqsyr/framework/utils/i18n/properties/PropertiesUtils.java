package org.zhiqsyr.framework.utils.i18n.properties;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;


/**
 * 读取配置信息，注意配置文件路径：i18n/system.properties i18n/enums.properties
 * 
 * @author dongbz 2015-11-20
 */
public class PropertiesUtils {

	private static final ResourceBundle system = ResourceBundle.getBundle("i18n/system");
	private static final ResourceBundle enums = ResourceBundle.getBundle("i18n/enums");
	
	/**
	 * i18n/system.properties
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueInSystem(String key) {
		return getValue(system, key);
	}
	
	/**
	 * i18n/enums.properties
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueInEnums(String key) {
		return getValue(enums, key);
	}
	
	/**
	 * i18n/enums.properties
	 * 
	 * @param key
	 * @return
	 */
	public static <E extends Enum<E>> String getValueInEnums(E e) {
		return getValue(enums, e.getClass().getName() + "." + e.name());
	}	
	
	private static String getValue(ResourceBundle rb, String key) {
		String value = rb.getString(key);
		
		try {
			return new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}
