package org.zhiqsyr.framework.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;
import org.zhiqsyr.framework.dao.mybatis.annotation.FieldMapperAnnotation;
import org.zhiqsyr.framework.dao.mybatis.annotation.TableMapperAnnotation;
import org.zhiqsyr.framework.dao.mybatis.annotation.UniqueKeyType;
import org.zhiqsyr.framework.model.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "dept")
@TableMapperAnnotation(tableName = "dept", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "id")  
public class Dept extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER)
	private Integer id;
	
	@Column(name = "name")
	@FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR) 
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
