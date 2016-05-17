package org.zhiqsyr.framework.utils.reflect;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具
 * 
 * @author dongbz 2016-1-14
 */
public class ReflectUtils {
	
	private static Logger log = LoggerFactory.getLogger(ReflectUtils.class);
	
	/**
	 * 获取 实例obj 的 属性fieldName 对应 Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		if (obj == null || fieldName == null) {
			return null;
		}
		
		Field[] fields;
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				if (fieldName.equals(field.getName())) {
					return field;
				}
			}
		}
		
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) {
		Object value = null;
		try {
			Field field = getFieldByFieldName(obj, fieldName);
			if (field != null) {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return value;
	}

	/**
	 * 获取obj对象中属性类型为 fieldType 的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
		Object value = null;
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field[] fields = superClass.getDeclaredFields();
				for (Field f : fields) {
					if (f.getType() == fieldType) {
						if (f.isAccessible()) {
							value = f.get(obj);
							break;
						} else {
							f.setAccessible(true);
							value = f.get(obj);
							f.setAccessible(false);
							break;
						}
					}
				}
				if (value != null) {
					break;
				}
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
		}
		return (T) value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setValueByFieldName(Object obj, String fieldName, Object value) {
		try {
			// 返回类中的指定字段的Field对象
			Field field = obj.getClass().getDeclaredField(fieldName);

			if (field.isAccessible()) {		// 获取此对象的 accessible 标志的值
				field.set(obj, value);		// 将指定对象变量上此 Field 对象表示的字段设置为指定的新值
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());

			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
