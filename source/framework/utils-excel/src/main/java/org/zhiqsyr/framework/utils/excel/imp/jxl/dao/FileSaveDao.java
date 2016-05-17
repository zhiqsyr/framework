package org.zhiqsyr.framework.utils.excel.imp.jxl.dao;


import java.util.UUID;

import org.zhiqsyr.framework.utils.excel.imp.jxl.entity.FileSave;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class FileSaveDao extends JxlBaseDao {

	public void save(FileSave vo) {
		vo.setId(UUID.randomUUID().toString());
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO excel_file_save (id, file_name, file_save_path, report_type, is_success, user_name, upload_time) ");
		sql.append(" VALUES (:id, :fileName, :fileSavePath, :reportType, :isSuccess, :userName, :uploadTime) ");

		SqlParameterSource ps = new BeanPropertySqlParameterSource(vo);
		
		namedParameterJdbcTemplate.update(sql.toString(), ps);
	}
	
}
