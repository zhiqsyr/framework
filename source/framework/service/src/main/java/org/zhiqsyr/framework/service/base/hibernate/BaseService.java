package org.zhiqsyr.framework.service.base.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.zhiqsyr.framework.model.entity.BaseEntity;
import org.zhiqsyr.framework.model.enums.Validity;
import org.zhiqsyr.framework.model.page.Order;

/**
 * 基础操作接口
 * 
 * @author dongbz 2015-5-21
 */
public interface BaseService {

	<T> T get(Class<T> entityClass, Serializable id);
	/**
	 * <b>Function: <b>propertyName value 精确查询
	 *
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	<T> T queryByProperty(final Class<T> entityClass, final String propertyName, final Object value);
	
	<T> List<T> queryAll(Class<T> entityClass);
	<T> List<T> queryAll(Class<T> entityClass, Validity validity);
	<T> List<T> queryAll(Class<T> entityClass, Validity validity, Order order);
	
	<T> List<T> queryAllByProperty(final Class<T> entityClass, final String propertyName, final Object value);
	/**
	 * <b>Function: <b>propertyName value 精确查询
	 *
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param order
	 * @return
	 */
	<T> List<T> queryAllByProperty(final Class<T> entityClass, final String propertyName, final Object value, final Order order);

	<T> List<T> queryAllValidByProperty(final Class<T> entityClass, final String propertyName, final Object value);
	/**
	 * <b>Function: <b>propertyName value 精确查询
	 *
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param order
	 * @return
	 */
	<T> List<T> queryAllValidByProperty(final Class<T> entityClass, final String propertyName, final Object value, final Order order);
	
	
	<T> List<T> queryByExample(T exampleEntity);
	
	Serializable doSave(BaseEntity baseEntity);
	
	void doUpdate(BaseEntity baseEntity);
	
	void doSaveOrUpdate(BaseEntity baseEntity);
	
	<T> void doDeleteById(Class<T> entityClass, Serializable id);
	void doDelete(BaseEntity baseEntity);	
	
	/**
	 * <b>Function: <b>返回数据库服务器当前时间
	 *
	 * @return
	 */
	Date getCurrentTime();
	
}
