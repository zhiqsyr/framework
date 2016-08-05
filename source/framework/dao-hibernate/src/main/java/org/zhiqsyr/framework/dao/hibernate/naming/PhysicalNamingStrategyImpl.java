package org.zhiqsyr.framework.dao.hibernate.naming;

import java.io.Serializable;
import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Hibernate PhysicalNamingStrategy Wholesale 实现
 * 
 * @author dongbz 2016-08-02
 */
@SuppressWarnings("serial")
public class PhysicalNamingStrategyImpl extends PhysicalNamingStrategyStandardImpl implements Serializable {

	public static final PhysicalNamingStrategyImpl INSTANCE = new PhysicalNamingStrategyImpl();

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	/**
	 * 添加下划线
	 * 
	 * @param name
	 * @return
	 * @author dongbz 2016-08-02
	 */
	protected static String addUnderscores(String name) {
		final StringBuilder buf = new StringBuilder(name);
		for (int i = 1; i < buf.length() - 1; i++) {
			// userName -> user_name
			// userN 	-> usern，因此避免此种命名格式
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i)) && Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase(Locale.ROOT);
	}
	
}
