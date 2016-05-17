package org.zhiqsyr.framework.utils.string.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * 中文转换拼音
 * 
 * @author dongbz 2015-9-23
 */
public class PinYinUtils {
	
	/**
	 * <b>Function: <b>返回中文对应全拼
	 *
	 * @param src
	 * @param caseType	大写/小写，HanyuPinyinCaseType.UPPERCASE/HanyuPinyinCaseType.LOWERCASE
	 * @return
	 */
	public static String getPinYin(String src, HanyuPinyinCaseType caseType) {
		char[] t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(caseType);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += java.lang.Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * <b>Function: <b>返回汉字大写首字母
	 *
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = null;
			try {
				pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word,
						new HanyuPinyinOutputFormat());
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert.toUpperCase();
	}

	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {
		String cnStr = "哈喽沃德";
		System.out.println(getPinYin(cnStr, HanyuPinyinCaseType.UPPERCASE));
		System.out.println(getPinYinHeadChar(cnStr));
	}
}
