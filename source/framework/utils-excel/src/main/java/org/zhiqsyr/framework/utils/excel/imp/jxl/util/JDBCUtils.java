package org.zhiqsyr.framework.utils.excel.imp.jxl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JDBCUtils {

	/**
	 * 建立SQL Server数据库连接
	 *
	 * @param dbUrl
	 * @return
	 */
	public static Connection getSqlServerConnection(String dbUrl){
    	Connection con = null;
    	
    	try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		con = DriverManager.getConnection(dbUrl);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
    
    	return con;
    }
	
	/**
	 * 执行查询，最后关闭connection
	 *
	 * @param con
	 * @param selectSql
	 * @return
	 */
	public static List<Map<String,Object>> getSqlList(Connection con, String selectSql) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			if(con != null){
				stmt = con.createStatement();
				rs = stmt.executeQuery(selectSql);
				ResultSetMetaData md = rs.getMetaData();
				
				Map<String,Object> rowData = null;
				while (rs.next()) {
					rowData = new HashMap<String,Object>();
					for(int i = 1; i <= md.getColumnCount(); i ++){
						rowData.put(md.getColumnName(i), rs.getObject(i));
					}
					result.add(rowData);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		
		return result;
    }	
	
	/**
	 * <b>Function: <b>生成给定 SQL 的 count sql
	 *
	 * @param sql
	 * @return
	 */
    public static String getCountSql(String sql) {
    	return "select count(1) from (" + removeOrders(sql) + ") fin";
    }	
	
    /**
     * <b>Function: <b>截断 order by 及其后面字符
     *
     * @param sql
     * @return
     */
    public static String removeOrders(String sql) {
		Matcher m = ORDER_PATTERN.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
		    m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
    }    
    
    public final static Pattern ORDER_PATTERN = Pattern.compile("order\\s+by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);    
    
}
