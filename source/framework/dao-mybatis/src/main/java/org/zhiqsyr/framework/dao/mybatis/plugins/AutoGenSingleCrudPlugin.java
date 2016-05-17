package org.zhiqsyr.framework.dao.mybatis.plugins;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.zhiqsyr.framework.dao.mybatis.utils.GenSqlUtils;

/**
 * 自动生成单表单条记录 CRUD SQL （Mybatis dao 方法名要求：insert/update/delete/select，cs；查/改/删需要给定主键） <br>
 * 大致原理：拦截StatementHandler的prepare方法，根据参数parameterObject的注解信息，自动生成sql语句（签名里拦截的类型只能是接口）
 * 
 * @author dongbingze 2016-5-17
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class AutoGenSingleCrudPlugin implements Interceptor {
    
	private static final Log logger = LogFactory.getLog(AutoGenSingleCrudPlugin.class);
	
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        
        // 分离代理对象链
        while (metaStatementHandler.hasGetter("h")) {
        	Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
        	Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        }
        
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
        
        // 没有指定脚本时，才会自动生成脚本
        if (null == originalSql || "".equals(originalSql.trim())) {
        	String genSql = "";
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            
            String id = mappedStatement.getId();
            id = id.substring(id.lastIndexOf(".") + 1);
            if ("insert".equals(id)) {
                genSql = GenSqlUtils.buildInsertSql(parameterObject);
            } else if ("update".equals(id)) {
                genSql = GenSqlUtils.buildUpdateSql(parameterObject);
            } else if ("delete".equals(id)) {
                genSql = GenSqlUtils.buildDeleteSql(parameterObject);
            } else if ("select".equals(id)) {
                genSql = GenSqlUtils.buildSelectSql(parameterObject);
            }
            logger.debug("Auto generated sql: " + genSql);
            
            SqlSource sqlSource = buildSqlSource(configuration, genSql, parameterObject.getClass());
            List<ParameterMapping> parameterMappings = sqlSource.getBoundSql(parameterObject).getParameterMappings();
            metaStatementHandler.setValue("delegate.boundSql.sql", sqlSource.getBoundSql(parameterObject).getSql());
            metaStatementHandler.setValue("delegate.boundSql.parameterMappings", parameterMappings);
        }
        
        // 调用原始statementHandler的prepare方法完成原本的逻辑
        statementHandler = (StatementHandler) metaStatementHandler.getOriginalObject();
        statementHandler.prepare((Connection) invocation.getArgs()[0]);
        
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

    private SqlSource buildSqlSource(Configuration configuration, String originalSql, Class<?> parameterType) {
        SqlSourceBuilder builder = new SqlSourceBuilder(configuration);
        return builder.parse(originalSql, parameterType, null);
    }
}
