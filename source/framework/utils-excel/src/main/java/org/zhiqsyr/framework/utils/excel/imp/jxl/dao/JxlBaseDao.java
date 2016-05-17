package org.zhiqsyr.framework.utils.excel.imp.jxl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * jxl 导入 excel dao 基类
 * 
 * @author dongbz 2015-12-9
 */
public class JxlBaseDao {

	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
}
