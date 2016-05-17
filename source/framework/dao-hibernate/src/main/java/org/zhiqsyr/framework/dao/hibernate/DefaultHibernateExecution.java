package org.zhiqsyr.framework.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.CollectionUtils;
import org.zhiqsyr.framework.dao.springjdbc.SpringJdbcExecution;
import org.zhiqsyr.framework.model.entity.BaseEntity;
import org.zhiqsyr.framework.model.enums.Validity;
import org.zhiqsyr.framework.model.page.Order;
import org.zhiqsyr.framework.model.page.OrderablePagination;

public class DefaultHibernateExecution extends SpringJdbcExecution implements HibernateExecution {

	protected HibernateTemplate hibernateTemplate;
	
	public DefaultHibernateExecution(DataSource dataSource, SessionFactory sessionFactory) {
		super(dataSource);
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		return hibernateTemplate.get(entityClass, id);
	}

	@Override
	public <T> T findByProperty(final Class<T> entityClass, final String propertyName,
			final Object value) {

		return hibernateTemplate.execute(new HibernateCallback<T>() {

			@SuppressWarnings({ "unchecked" })
			@Override
			public T doInHibernate(Session session) throws HibernateException {
				return (T) session.createCriteria(entityClass)
						.add(Restrictions.eq(propertyName, value))
						.uniqueResult();
			}
		});
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
	  	return hibernateTemplate.loadAll(entityClass);
	
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, Validity validity) {
		return findAll(entityClass, validity, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(Class<T> entityClass, Validity validity,
			Order order) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		
		if (validity != null) {
			dc.add(Restrictions.eq("validity", validity));
		}
		
		if (order != null && order.isAscending()) {
			dc.addOrder(org.hibernate.criterion.Order.asc(order.getPropertyName()));
		} else if (order != null && !order.isAscending()) {
			dc.addOrder(org.hibernate.criterion.Order.desc(order.getPropertyName()));
		}
		return (List<T>) hibernateTemplate.findByCriteria(dc);
	}

	@Override
	public <T> List<T> findAllByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return findAllByProperty(entityClass, propertyName, value, null);
	}

	@Override
	public <T> List<T> findAllByProperty(final Class<T> entityClass,
			final String propertyName, final Object value, final Order order) {

		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria c = session.createCriteria(entityClass);
				c.add(Restrictions.eq(propertyName, value));
				if (order != null && order.isAscending()) {
					c.addOrder(org.hibernate.criterion.Order.asc(order.getPropertyName()));
				} else if (order != null && !order.isAscending()) {
					c.addOrder(org.hibernate.criterion.Order.desc(order.getPropertyName()));
				}
				
				return c.list();
			}
		});
	}
	
	@Override
	public <T> List<T> findAllValidByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return findAllValidByProperty(entityClass, propertyName, value, null);
	}
	
	@Override
	public <T> List<T> findAllValidByProperty(final Class<T> entityClass,
			final String propertyName, final Object value, final Order order) {
		
		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria c = session.createCriteria(entityClass);
				c.add(Restrictions.eq(propertyName, value));
				c.add(Restrictions.eq("validity", Validity.VALID));
				if (order != null && order.isAscending()) {
					c.addOrder(org.hibernate.criterion.Order.asc(order.getPropertyName()));
				} else if (order != null && !order.isAscending()) {
					c.addOrder(org.hibernate.criterion.Order.desc(order.getPropertyName()));
				}
				
				return c.list();
			}
		});
	}
	
	@Override
	public <T> List<T> findByExample(T exampleEntity) {
		return hibernateTemplate.findByExample(exampleEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByCriteria(DetachedCriteria criteria) {
		return (List<T>) hibernateTemplate.findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByCriteria(DetachedCriteria criteria, int firstResult,
			int maxResults) {
		return (List<T>) hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);
	}
	
	@Override
	public <T> List<T> findByCriteria(CustomizedDetachedCriteria criteria,
			OrderablePagination pagination) {
		if (pagination == null) {
			return findByCriteria(criteria);
		}
		
		criteria.setProjection(Projections.rowCount());
		pagination.setTotalSize((Long) findByCriteria(criteria).listIterator().next());
		
		criteria.setProjection(null);
		if (!CollectionUtils.isEmpty(pagination.getOrders())) {
			for (org.zhiqsyr.framework.model.page.Order order : pagination.getOrders()) {
				criteria.addOrder(order);
			}
		}
		
		int totalSize, offset;
		totalSize = Long.valueOf(pagination.getTotalSize()).intValue();		// 总数
		offset = pagination.getActiveIndex() * pagination.getPageSize();	// 已查出数量
		if (totalSize < offset) {	// 可能查询条件变化，导致 totalSize 变化；当小于已查出数量时，查出第一页结果
			pagination.setActiveIndex(0);
		}
		
		return findByCriteria(criteria, pagination.getActiveIndex() * pagination.getPageSize(), pagination.getPageSize());
	}
	
	@Override
	public Serializable save(BaseEntity baseEntity) {
		return hibernateTemplate.save(baseEntity);
	}

	@Override
	public void update(BaseEntity baseEntity) {
		hibernateTemplate.update(baseEntity);
	}

	@Override
	public void saveOrUpdate(BaseEntity baseEntity) {
		hibernateTemplate.saveOrUpdate(baseEntity);
	}

	@Override
	public <T> void deleteById(Class<T> entityClass, Serializable id) {
        if (id == null) {  
            return;  
        }
        
        BaseEntity entity = (BaseEntity) get(entityClass, id);  
        if (entity == null) {  
            return;  
        }
        
        delete(entity);  
	}

	@Override
	public void delete(BaseEntity baseEntity) {
		if (baseEntity != null) {
			hibernateTemplate.delete(baseEntity);
		}
	}

}
