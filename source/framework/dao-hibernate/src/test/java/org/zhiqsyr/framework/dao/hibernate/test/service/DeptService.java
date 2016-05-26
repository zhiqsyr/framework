package org.zhiqsyr.framework.dao.hibernate.test.service;

import org.zhiqsyr.framework.dao.hibernate.test.entity.Dept;

public interface DeptService {

	Dept getById(Integer id);
	
	void doCreate();
	
	void doModify();
	
}
