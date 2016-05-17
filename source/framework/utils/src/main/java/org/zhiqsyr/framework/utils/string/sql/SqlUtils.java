package org.zhiqsyr.framework.utils.string.sql;

public class SqlUtils {

	/**
	 * 为拼接的查询sql中的各个字段添加别名，命名规范同于java属性命名规范（较高耦合）
	 *
	 * @param fieldSql，例如id, report_id, alias_name, column_name, data_position, data_type, processor_and_validator
	 * @return id, report_id reportId, alias_name aliasName, column_name columnName, data_position dataPosition, data_type dataType, processor_and_validator processorAndValidator
	 *
	 * @author dongbz, 2015-12-9
	 */
	public static String addAlias2Fields(String fieldSql) {
		String[] fields = fieldSql.replaceAll(" ", "").split(",");
		
		StringBuilder result = new StringBuilder();
		StringBuilder alias;
		int b,		// 上一个 _ 位置  
			e;		// 当前 _ 位置  		
		String uc;	// 大写字符
		for (String f : fields) {
			b = 0;	
			e = 0;	
			alias = new StringBuilder();
			while ((e = f.indexOf("_", b)) != -1) {
				alias.append(f.substring(b, e));
				
				if (f.length() == e + 1) {	// _ 是最后一个字符
					break;
				}
				
				uc = f.substring(e + 1, e + 2).toUpperCase();
				alias.append(uc);
				b = e + 2;
				
				if (f.indexOf("_", b) == -1) {
					alias.append(f.substring(b));
				}
			}
			
			result.append(f);
			if (alias.length() != 0) {
				result.append(" " + alias);
			}
			result.append(", ");
		}
		
		return result.substring(0, result.length() - 2);
	}
	
}
