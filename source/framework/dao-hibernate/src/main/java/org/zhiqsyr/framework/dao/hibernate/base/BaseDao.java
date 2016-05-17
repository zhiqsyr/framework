package org.zhiqsyr.framework.dao.hibernate.base;

import java.io.Serializable;

import org.zhiqsyr.framework.dao.hibernate.DefaultHibernateExecution;
import org.zhiqsyr.framework.model.entity.BaseEntity;

/**
 * 基础数据库交互类，子类通过 dbExec 调用绝大多数 CRUD API；<br>
 * 另外提供 dbExec.getHibernateTemplate()、dbExec.getJdbcTemplate() 获得 Hibernate、Spring JDBC 操作模板
 * 
 * @author dongbz 2015-5-22
 */
public class BaseDao {

	// 注释以兼容非Hibernate框架，使用Hibernate时，显式注入
//	@Resource
	protected DefaultHibernateExecution dbExec;

	public BaseDao() {
		System.out.println("--BaseDao--");
	}
	
	public Serializable doSave(BaseEntity baseEntity) {
		return getDbExec().save(baseEntity);
	}

	public void doUpdate(BaseEntity baseEntity) {
		getDbExec().update(baseEntity);
	}

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

	public <T> void doDeleteById(Class<T> entityClass, Serializable id) {
		getDbExec().deleteById(entityClass, id);
	}

	public void doDelete(BaseEntity baseEntity) {
		getDbExec().delete(baseEntity);
	}

	public void setDbExec(DefaultHibernateExecution dbExec) {
		this.dbExec = dbExec;
	}	

	public DefaultHibernateExecution getDbExec() {
		return dbExec;
	}
	
}
