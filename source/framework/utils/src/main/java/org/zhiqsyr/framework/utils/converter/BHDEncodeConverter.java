package org.zhiqsyr.framework.utils.converter;

import org.apache.commons.lang3.StringUtils;

/**
 * 进制（编码）转换器
 * 
 * @author dongbz 2014-9-24
 */
public class BHDEncodeConverter {

	/**
	 * 把二进制字符串转换成16进制，不足length位右靠左补0
	 *
	 * @param binary 二进制字符串
	 * @param length 要求16进制长度
	 * @return
	 *
	 * @author dongbz, 2014-9-24
	 */
	public static String transferBinaryToHex(String binary, int length) {
		if (binary == null) {
			return null;
		}
		
		String preBinary = null;	// 预处理的二进制字符串

		// 字符串长度不是8的整数倍，左边补0
		int remain = binary.length() % 8;
		if (remain == 0) {	
			preBinary = binary;
		} else {
			preBinary = StringUtils.leftPad(binary, binary.length() + remain, '0');
		}
		
		StringBuffer result = new StringBuffer();
		String eight;
		int value;
		for (int i = 8; i <= preBinary.length(); i += 8) {
			eight = preBinary.substring(i - 8, i);	// 每次取8位进行转换
			value = Integer.parseInt(eight, 2);	// 8位二进制转换成十进制
			result.append(String.format("%02X", value));	// 转换成16进制，使用大写字母，不足两位左补0
		}
		
		// 假如result长度小于要求长度length，左边补0
		if (length > result.length()) {
			return StringUtils.leftPad(binary, binary.length() + remain, '0');
		}
		
		return result.toString();
	}
	
	/**
	 * 把字符串转换成16进制的ASCII编码，不足length位右靠左补0
	 *
	 * @param src 原字符串
	 * @param length 要求16进制长度
	 * @return
	 *
	 * @author dongbz, 2014-9-24
	 */
	public static String transferStringToAsciiHex(String src, int length) {
		if (src == null) {
			return null;
		}
		
		byte[] srcArr = src.getBytes();
		StringBuffer result = new StringBuffer();
		
		int value;
		String s;
		for (byte b : srcArr) {
			value = b & 0xFF;	// 获得ASCII编码的10进制值
			s = String.format("%02X", value);	// 转换成16进制，使用大写字母，不足两位左补0
			result.append(s);
		}
		
		// 假如result长度小于要求长度length，左边补0
		if (length > result.length()) {
			return StringUtils.leftPad(result.toString(), length, '0');
		}
		
		return result.toString();
	}
	
}
