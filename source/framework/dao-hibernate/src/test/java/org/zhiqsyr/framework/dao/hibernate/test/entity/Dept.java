package org.zhiqsyr.framework.dao.hibernate.test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zhiqsyr.framework.model.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "dept")
/*
 * SENSE Hibernate二级缓存的并发访问策略（具体取决二级缓存提供者是否支持）
        只读（read-only)： 对于永远不会被修改的数据可以采用这种并发访问策略，它的并发性能是最高的。但必须保证数据不会被修改，否则就会出错。
        非严格读写（nonstrict-read-write): 非严格读写不能保证缓存与数据库中数据的一致性，如果存在两个事务并发地访问缓存数据的可能，则应该为该数据配置一个很短的过期时间，以减少读脏数据的可能。对于极少被修改，并且可以容忍偶尔脏读的数据可以采用这种并发策略。
        读写（read-write)： 读写策略提供了“read committed”数据库隔离级别。对于经常被读但很少修改的数据可以采用这种策略，它可以防止读脏数据。
        事务（transactional）： 它提供了Repeatable Read事务隔离级别。它可以防止脏读和不可重复读这类的并发问题。
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dept extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Serializable getEntityId() {
		return id;
	}

}
