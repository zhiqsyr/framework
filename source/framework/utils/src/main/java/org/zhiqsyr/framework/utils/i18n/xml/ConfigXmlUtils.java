package org.zhiqsyr.framework.utils.i18n.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * xml 配置文件工具类
 *
 * @author dongbz
 * @date 2014-5-8
 */
public class ConfigXmlUtils {

	public static void main(String[] args) {
		System.out.println(getConfigMultiValue("system", "email_sender_name"));
	}
	
	/**
	 * 获取 config_multi.xml 参数值
	 *
	 * @param confName
	 * @param keyName
	 * @return
	 */
	public static String getConfigMultiValue(String confName, String keyName) {
		return getConfigValue("config_multi.xml", confName, keyName);
	}
	
	/**
	 * 获取配置文件参数值
	 *
	 * @param fileName	配置文件名称
	 * @param confName	节点名
	 * @param keyName	键名
	 * @return
	 */
    public static String getConfigValue(String fileName, String confName, String keyName){
        String result = "";	
        
    	try {
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        	
        	// config 文件夹置于工程同级目录
        	// new File("/") --> D:\
        	// new File("")  --> D:\Program-Files-Dongbz\workspace\framework
        	// new File(".") --> D:\Program-Files-Dongbz\workspace\framework\.
        	Document doc = docBuilder.parse(new File(new File(".").getPath() + "/config/" + fileName));
        	Element element = doc.getDocumentElement();
        	NodeList nlRoot = element.getElementsByTagName("conf");
        	
        	for(int i = 0;i < nlRoot.getLength();i ++){
        		Element node = (Element) nlRoot.item(i);
        		if(node.getAttribute("type").equals(confName)){
        			NodeList childNodes = node.getElementsByTagName("key");
        			for(int j = 0;j < childNodes.getLength();j ++){
        				Element childNode = (Element)childNodes.item(j);
        				if(childNode.getAttribute("type").equals(keyName)){
        					result = childNode.getTextContent();
        				}
        			}
        		}
        	}
    	} catch (Exception e) {
    		e.printStackTrace();
    		
    		throw new RuntimeException(e.getMessage(), e);
    	}
    	
    	return result;
    }
	
}
