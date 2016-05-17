package org.zhiqsyr.framework.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class DateUtils {
	
	/**
	 * utc 时间转换成北京时间
	 *
	 * @param utcTime
	 * @return
	 */
	public static Date transferUtcTime2BjTime(Date utcTime) {
		if (utcTime == null) {
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = dateFormat.getCalendar();

		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
    	int dstOffset = cal.get(Calendar.DST_OFFSET);
    	return new Date(utcTime.getTime() + zoneOffset + dstOffset);
	}
	
	/**
	 * 北京时间转换成 utc 时间
	 *
	 * @param bjTime
	 * @return
	 */
	public static Date transferBjTime2UtcTime(Date bjTime) {
		if (bjTime == null) {
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	Calendar cal = dateFormat.getCalendar();
    	int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
    	int dstOffset = cal.get(Calendar.DST_OFFSET);
    	return new Date(bjTime.getTime() - (zoneOffset + dstOffset));
	}
	
	/**
	 * <b>Function: <b>计算 src 指定 amount 前（负数）/后（正数）的时间
	 *
	 * @param src	参考时间，java.util.Date
	 * @param field	时间域，如Calendar.DAY_OF_MONTH、Calendar.HOUR_OF_DAY、Calendar.MINUTE
	 * @param amount
	 * @return
	 */
	public static Date calcByFieldAmount(Date src, int field, int amount) {
		Calendar cal = Calendar.getInstance();
    	cal.setTime(src);
    	cal.add(field, amount);
    	return cal.getTime();
	}
	
	/**
	 * <b>Function: <b>计算 src 指定 amount 前（负数）/后（正数）的时间
	 *
	 * @param src		参考时间，String
	 * @param pattern	src 时间格式，如：“yyyy-MM-dd HH:mm:ss”
	 * @param field	时间域，如Calendar.DAY_OF_MONTH、Calendar.HOUR_OF_DAY、Calendar.MINUTE
	 * @param amount
	 * @return
	 */
	public static Date calcByFieldAmount(String src, String pattern, int field, int amount) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			Date date = dateFormat.parse(src);
		
			return calcByFieldAmount(date, field, amount);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * <b>Function: <b>计算间隔天数
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public static Integer calcDays(Date start, Date end) {
		return (int) ((end.getTime() - start.getTime())/1000/60/60/24) + 1;
	}
	
	/**
	 * <b>Function: <b>date 转换成 pattern 类型字符串
	 *
	 * @param src
	 * @param pattern	例如：“yyyy-MM-dd HH:mm:ss”
	 * @return
	 */
	public static String transferDate2Str(Date src, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(src);
	}
	
	/**
	 * <b>Function: <b>date 转换成 标准  “yyyy-MM-dd HH:mm:ss” 类型字符串
	 *
	 * @param src
	 * @return
	 */
	public static String transferDate2StaStr(Date src) {
		return transferDate2Str(src, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * <b>Function: <b>date 转换成  “yyyyMMddHHmmss” 类型字符串
	 *
	 * @param src
	 * @return
	 */
	public static String transferDate2yyyyMMddHHmmss(Date src) {
		return transferDate2Str(src, "yyyyMMddHHmmss");
	}	
	
	/**
	 * <b>Function: <b>pattern 时间格式的字符串 src，解析成 Date
	 *
	 * @param src	如：“2015-05-22 16:39:30”
	 * @param pattern	如：“yyyy-MM-dd HH:mm:ss”
	 * @return
	 */
	public static Date parseStr2Date(String src, String pattern) {
		if (StringUtils.isBlank(src)) {
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(src);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * <b>Function: <b>按照 pattern 格式，构成 from ~ to 样式
	 *
	 * @param from
	 * @param to
	 * @param pattern
	 * @return
	 */
	public static String buildFromToString(Date from, Date to, String pattern) {
		return transferDate2Str(from, pattern) + "~" + transferDate2Str(to, pattern);
	}
	
	/**
     * 处理北京时间的“月”字符串，将英文缩写转换成数字
     * 
     * @param monthEng
     * @return
     */
    public static String transferMonthEng2No(String monthEng){
    	String monthNo = "";
    	
    	if(monthEng.startsWith("JAN"))    	//一月：Jan.
    		monthNo = "01";
        else if(monthEng.startsWith("FEB"))	//二月：Feb.
        	monthNo = "02";
        else if(monthEng.startsWith("MAR"))	//三月：Mar.
        	monthNo = "03";
        else if(monthEng.startsWith("APR"))	//四月：Apr.
        	monthNo = "04";
        else if(monthEng.startsWith("MAY"))	//五月：May.
        	monthNo = "05";
        else if(monthEng.startsWith("JUN"))	//六月:Jun.
        	monthNo = "06";
        else if(monthEng.startsWith("JUL"))	//七月:Jul.
        	monthNo = "07";
        else if(monthEng.startsWith("AUG"))	//八月:Aug.
        	monthNo = "08";
        else if(monthEng.startsWith("SEP"))	//九月:Sept.
        	monthNo = "09";
        else if(monthEng.startsWith("OCT"))	//十月:Oct.
        	monthNo = "10";
        else if(monthEng.startsWith("NOV"))	//十一月:Nov.
        	monthNo = "11";
        else if(monthEng.startsWith("DEC"))	//十二月：Dec.
        	monthNo = "12";
    	
        return monthNo;
    }
	
}
