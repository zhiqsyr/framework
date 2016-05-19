package org.zhiqsyr.framework.dao.hibernate.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zhiqsyr.framework.dao.hibernate.base.BaseDao;
import org.zhiqsyr.framework.dao.hibernate.test.entity.Dept;
import org.zhiqsyr.framework.dao.hibernate.test.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Dept getById(Integer id) {
		Dept vo = baseDao.getDbExec().get(Dept.class, id);
		
		vo = baseDao.getDbExec().get(Dept.class, id);
		
		return vo;
	}
	
}
