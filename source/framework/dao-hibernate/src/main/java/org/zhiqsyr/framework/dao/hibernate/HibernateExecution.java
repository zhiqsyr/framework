package org.zhiqsyr.framework.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.zhiqsyr.framework.dao.springjdbc.SqlExecution;
import org.zhiqsyr.framework.model.entity.BaseEntity;
import org.zhiqsyr.framework.model.enums.Validity;
import org.zhiqsyr.framework.model.page.Order;
import org.zhiqsyr.framework.model.page.OrderablePagination;

/**
 * Hibernate 操作接口
 * 
 * @author dongbz 2015-5-20
 */
public interface HibernateExecution extends SqlExecution {

	<T> T get(Class<T> entityClass, Serializable id);
	<T> T getByProperty(final Class<T> entityClass, final String propertyName, final Object value);
	
	<T> List<T> findAll(Class<T> entityClass);
	<T> List<T> findAll(Class<T> entityClass, Validity validity);
	<T> List<T> findAll(Class<T> entityClass, Validity validity, Order order);
	
	<T> List<T> findAllByProperty(final Class<T> entityClass, final String propertyName, final Object value);
	<T> List<T> findAllByProperty(final Class<T> entityClass, final String propertyName, final Object value, final Order order);

	<T> List<T> findAllValidByProperty(final Class<T> entityClass, final String propertyName, final Object value);
	<T> List<T> findAllValidByProperty(final Class<T> entityClass, final String propertyName, final Object value, final Order order);
	
	
	<T> List<T> findByExample(T exampleEntity);
	
	<T> List<T> findByCriteria(DetachedCriteria criteria);
	<T> List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);
	<T> List<T> findByCriteria(CustomizedDetachedCriteria criteria, OrderablePagination pagination);
	
	Serializable save(BaseEntity baseEntity);
	
	void update(BaseEntity baseEntity);
	
	void saveOrUpdate(BaseEntity baseEntity);
	
	<T> void deleteById(Class<T> entityClass, Serializable id);
	void delete(BaseEntity baseEntity);
	
}
