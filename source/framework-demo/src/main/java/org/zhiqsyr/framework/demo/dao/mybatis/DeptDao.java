package org.zhiqsyr.framework.demo.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cloud.dao.framework.mybatis.MyBatisExecution;
import org.cloud.demo.entity.Dept;
import org.cloud.model.page.OrderablePagination;

public interface DeptDao extends MyBatisExecution {

	void save(Dept po);
	
	List<Dept> find(@Param("query") Dept query, OrderablePagination op);
	
}
