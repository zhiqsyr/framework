package org.zhiqsyr.dao.mybatis.plugins;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.zhiqsyr.dao.mybatis.utils.SqlBuilder;

/**
 * 自动生成单表 CRUD SQL
 * 
 * @author dongbingze 2016-5-13
 */
public class AutoGenSingleCrudPlugin implements Interceptor {

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAUT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler stmtHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaHandler = MetaObject.forObject(stmtHandler, DEFAULT_OBJECT_FACTORY, DEFAUT_OBJECT_WRAPPER_FACTORY);
		
		Object obj = null;
		while (metaHandler.hasGetter("h")) {		// 分离代理链对象，得到最后一个
			obj = metaHandler.getValue("h");
		}
		while (metaHandler.hasGetter("target")) {	// 分离得到最后一个代理对象的目标类
			obj = metaHandler.getValue("target");
		}
		metaHandler = MetaObject.forObject(obj, DEFAULT_OBJECT_FACTORY, DEFAUT_OBJECT_WRAPPER_FACTORY);
		
		String oriSql = (String) metaHandler.getValue("delegate.boundSql.sql");					// 原始SQL
		Configuration config = (Configuration) metaHandler.getValue("delegate.configuration");
		Object parameter = metaHandler.getValue("delegate.boundSql.parameterObject");
		
		if (oriSql == null || oriSql.trim().equals("")) {
			MappedStatement mappedStmt = (MappedStatement) metaHandler.getValue("delegate.mappedStatement");
			
			// 根据 id 生成相应类型的 sql （id需剔除namespace信息） 
			String id = mappedStmt.getId();
			id = id.substring(id.lastIndexOf(".") + 1);
			String genSql = "";
			if ("insert".equals(id)) {  
				genSql = SqlBuilder.buildInsertSql(parameter);  
            } else if ("update".equals(id)) {  
            	genSql = SqlBuilder.buildUpdateSql(parameter);  
            } else if ("delete".equals(id)) {  
            	genSql = SqlBuilder.buildDeleteSql(parameter);  
            } else if ("select".equals(id)) {  
            	genSql = SqlBuilder.buildSelectSql(parameter);  
            }  

			SqlSource sqlSource = buildSqlSource(config, genSql, parameter.getClass());  
            List<ParameterMapping> parameterMappings = sqlSource.getBoundSql(parameter).getParameterMappings();  
            metaHandler.setValue("delegate.boundSql.sql", sqlSource.getBoundSql(parameter).getSql());  
            metaHandler.setValue("delegate.boundSql.parameterMappings", parameterMappings);  
        }  
        
		// 调用原始StatementHandler的prepare方法  
        stmtHandler = (StatementHandler) metaHandler.getOriginalObject();  
        stmtHandler.prepare((Connection) invocation.getArgs()[0]);  

        // 传递给下一个拦截器处理  
        return invocation.proceed(); 
	}

	@Override
	public Object plugin(Object target) {
        if (target instanceof StatementHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        } 
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	
	private SqlSource buildSqlSource(Configuration config,
			String oriSql, Class<?> parameterType) {
		SqlSourceBuilder builder = new SqlSourceBuilder(config);
		return builder.parse(oriSql, parameterType, null);
	}

}
