package org.zhiqsyr.framework.dao.hibernate.test.service.impl;

import org.apache.commons.lang.RandomStringUtils;
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
		baseDao.getDbExec().get(Dept.class, id);	// 一级缓存生效
		
		Dept dtf = baseDao.getDbExec().findByProperty(Dept.class, "name", "buYC99ztB7");
		Dept dts = baseDao.getDbExec().findByProperty(Dept.class, "name", "buYC99ztB7");	// 二级缓存生效
		dtf.setName(RandomStringUtils.randomAlphanumeric(10));
		System.err.println(dts.getName());			// SENSE 同一事务，各类唯一查询返回po地址一样（怀疑集合查询也是），vo=dtf=fts
		
		/*
		// SENSE 一级缓存，即 session 缓存，同一 session 有效（即便处于不同事务）
		Session session = baseDao.getDbExec().getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Dept df = (Dept) session.get(Dept.class, id);
		tx.commit();

		tx = session.beginTransaction();
		Dept ds = (Dept) session.get(Dept.class, id);
		tx.commit();
		System.err.println(df == ds);
		*/
		
		return vo;
	}
	
	@Override
	public void doCreate() {
		Dept po = new Dept();
		po.setName(RandomStringUtils.randomAlphanumeric(10));
		
		baseDao.doSave(po);
	}
	
	@Override
	public void doModify() {
		Dept vo = getById(2);
		
		vo.setName(RandomStringUtils.randomAlphanumeric(10));
		baseDao.doUpdate(vo);
		
//		throw new RuntimeException("on purpose");
	}
	
}
