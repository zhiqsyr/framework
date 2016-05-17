package org.zhiqsyr.framework.demo.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zhiqsyr.framework.dao.mybatis.MyBatisExecution;
import org.zhiqsyr.framework.demo.entity.Dept;
import org.zhiqsyr.framework.model.page.OrderablePagination;

public interface DeptDao extends MyBatisExecution {

	void insert(Dept po);
	void update(Dept po);
	void delete(Dept po);
	
	List<Dept> select(Dept query);
	
	List<Dept> select(@Param("query") Dept query, OrderablePagination op);
	
}
