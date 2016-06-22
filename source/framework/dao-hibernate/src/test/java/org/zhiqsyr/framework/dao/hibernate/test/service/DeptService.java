package org.zhiqsyr.framework.dao.hibernate.test.service;

import java.util.List;

import org.zhiqsyr.framework.dao.hibernate.test.entity.Dept;

public interface DeptService {

	Dept getById(Integer id);
	
	List<Dept> queryAll();
	
	void doCreate(Dept po);
	
	void doModify(Dept po);
	
}
