package org.zhiqsyr.framework.service.base.impl.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.zhiqsyr.framework.dao.hibernate.DefaultHibernateExecution;
import org.zhiqsyr.framework.dao.hibernate.base.spring.BaseDao;
import org.zhiqsyr.framework.model.entity.BaseEntity;
import org.zhiqsyr.framework.model.enums.Validity;
import org.zhiqsyr.framework.model.page.Order;
import org.zhiqsyr.framework.service.base.hibernate.BaseService;

public class BaseServiceImpl implements BaseService {

	// 注释以兼容非Hibernate框架，使用Hibernate时，显式注入
//	@Resource(name = "baseDao", type = BaseDao.class)
	private BaseDao baseDao;
	
	public DefaultHibernateExecution getDbExec() {
		return baseDao.getDbExec();
	}
	
	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		return baseDao.getDbExec().get(entityClass, id);
	}

	@Override
	public <T> T queryByProperty(Class<T> entityClass, String propertyName,
			Object value) {
		return baseDao.getDbExec().getByProperty(entityClass, propertyName, value);
	}

	@Override
	public <T> List<T> queryAll(Class<T> entityClass) {
		return baseDao.getDbExec().findAll(entityClass);
	}
	
	@Override
	public <T> List<T> queryAll(Class<T> entityClass, Validity validity) {
		return baseDao.getDbExec().findAll(entityClass, validity);
	}
    
	@Override
	public <T> List<T> queryAll(Class<T> entityClass, Validity validity,
			Order order) {
		return baseDao.getDbExec().findAll(entityClass, validity, order);
	}

	@Override
	public <T> List<T> queryAllByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return baseDao.getDbExec().findAllByProperty(entityClass, propertyName, value);
	}

	@Override
	public <T> List<T> queryAllByProperty(Class<T> entityClass,
			String propertyName, Object value, Order order) {
		return baseDao.getDbExec().findAllByProperty(entityClass, propertyName, value, order);
	}

	@Override
	public <T> List<T> queryAllValidByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return baseDao.getDbExec().findAllValidByProperty(entityClass, propertyName, value);
	}
	
	@Override
	public <T> List<T> queryAllValidByProperty(Class<T> entityClass,
			String propertyName, Object value, Order order) {
		return baseDao.getDbExec().findAllValidByProperty(entityClass, propertyName, value, order);
	}
	
	@Override
	public <T> List<T> queryByExample(T exampleEntity) {
		return baseDao.getDbExec().findByExample(exampleEntity);
	}
	
	@Override
	public Serializable doSave(BaseEntity baseEntity) {
		return baseDao.getDbExec().save(baseEntity);
	}

	@Override
	public void doUpdate(BaseEntity baseEntity) {
		baseDao.getDbExec().update(baseEntity);
	}

	@Override
	public void doSaveOrUpdate(BaseEntity baseEntity) {
		if (baseEntity == null) {
			return;
		}
		
		if (baseEntity.getEntityId() == null) {
			doSave(baseEntity);
		} else {
			doUpdate(baseEntity);
		}
	}

	@Override
	public <T> void doDeleteById(Class<T> entityClass, Serializable id) {
		baseDao.getDbExec().deleteById(entityClass, id);
	}

	@Override
	public void doDelete(BaseEntity baseEntity) {
		baseDao.getDbExec().delete(baseEntity);
	}	

	@Override
	public Date getCurrentTime() {
		return baseDao.getDbExec().getCurrentDbTime();
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}
	
}
