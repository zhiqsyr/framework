package org.zhiqsyr.framework.utils.cache;

import java.util.Arrays;
import java.util.List;

import org.zhiqsyr.framework.utils.i18n.properties.PropertiesUtils;


/**
 * 通用常量集合
 * 
 * @author dongbz 2015-5-6
 */
public class Constants {

	public static final String PROJECT_NAME = PropertiesUtils.getValueInSystem("project.name");
	public static final String DEFAULT_WEB_ENCODING = PropertiesUtils.getValueInSystem("default.web.encoding");
	public static final String DEFAULT_DATE_FORMAT = PropertiesUtils.getValueInSystem("default.date.format");
	public static final String DEFAULT_DATETIME_FORMAT = PropertiesUtils.getValueInSystem("default.datetime.format");
	
	public static final String LABEL_OF_NULL = PropertiesUtils.getValueInSystem("lable.of.null");
	
	public static final String POINT = ".";
	
    /** 简单类型及其封装类，String、enum 等特殊类 */
    public static final List<String> SIMPLE_CLASSES = Arrays.asList(
    	"boolean", "Boolean", "char", "Charater", "byte", "Byte",
    	"short", "Short", "int", "Integer", "long", "Long", 
    	"float", "Float", "double", "Double", "String", "enum"
    );
    
}
