package org.zhiqsyr.framework.dao.hibernate;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.type.Type;

/**
 * 自定义匹配条件，value = null 时，不做匹配
 * 
 * @author dongbz 2015-5-22
 */
public class CustomizedRestrictions {

    public static Criterion idEq(Object value)
    {
		return value == null ? null : Restrictions.idEq(value);
    }

    public static SimpleExpression eq(String propertyName, Object value)
    {
    	return value == null ? null : Restrictions.eq(propertyName, value);
    }

    public static SimpleExpression ne(String propertyName, Object value)
    {
    	return value == null ? null : Restrictions.ne(propertyName, value);
    }

    /**
     * <b>Function: <b> propertyName like value，一般 value 传入 "%" + value + "%"
     *
     * @param propertyName
     * @param value
     * @return
     */
    public static SimpleExpression like(String propertyName, Object value)
    {
    	return value == null ? null : Restrictions.like(propertyName, "%" + value + "%");
    }

    public static SimpleExpression like(String propertyName, String value, MatchMode matchMode)
    {
        return value == null ? null : Restrictions.like(propertyName, "%" + value + "%", matchMode);
    }

    public static SimpleExpression gt(String propertyName, Object value)
    {
        return value == null ? null : Restrictions.gt(propertyName, value);
    }

    public static SimpleExpression lt(String propertyName, Object value)
    {
    	return value == null ? null : Restrictions.lt(propertyName, value);
    }

    public static SimpleExpression le(String propertyName, Object value)
    {
    	return value == null ? null : Restrictions.le(propertyName, value);
    }

    public static SimpleExpression ge(String propertyName, Object value)
    {
    	return value == null ? null : Restrictions.ge(propertyName, value);
    }

    public static Criterion between(String propertyName, Object lo, Object hi)
    {
        return (lo == null || hi == null) ? null : Restrictions.between(propertyName, lo, hi);
    }

    public static Criterion in(String propertyName, Object values[])
    {
        return values == null ? null : Restrictions.in(propertyName, values);
    }

    public static Criterion in(String propertyName, @SuppressWarnings("rawtypes") Collection values)
    {
        return values == null ? null : Restrictions.in(propertyName, values);
    }

    public static Criterion sqlRestriction(String sql, Object values[], Type types[])
    {
        return values == null ? null : Restrictions.sqlRestriction(sql, values, types);
    }

    public static Criterion sqlRestriction(String sql, Object value, Type type)
    {
        return value == null ? null : Restrictions.sqlRestriction(sql, value, type);
    }

}
