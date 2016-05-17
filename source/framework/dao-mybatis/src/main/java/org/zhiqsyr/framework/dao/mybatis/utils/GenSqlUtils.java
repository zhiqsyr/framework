package org.zhiqsyr.framework.dao.mybatis.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.zhiqsyr.framework.dao.mybatis.annotation.FieldMapper;
import org.zhiqsyr.framework.dao.mybatis.annotation.FieldMapperAnnotation;
import org.zhiqsyr.framework.dao.mybatis.annotation.TableMapper;
import org.zhiqsyr.framework.dao.mybatis.annotation.TableMapperAnnotation;
import org.zhiqsyr.framework.dao.mybatis.annotation.UniqueKeyType;

/**
 * 通过注解生成sql
 * 
 * @author david
 */
public class GenSqlUtils {
    /**
     * 缓存TableMapper
     */
    private static HashMap<Class<?>, TableMapper> tableMapperCache = new HashMap<Class<?>, TableMapper>(128);

    /**
     * 由传入的dto对象的class构建TableMapper对象，构建好的对象存入缓存中，以后使用时直接从缓存中获取
     * 
     * @param dtoClass
     * @return TableMapper
     */
    private static TableMapper buildTableMapper(Class<?> dtoClass) {
        synchronized (tableMapperCache) {
        	TableMapper tableMapper = tableMapperCache.get(dtoClass);
            if (tableMapper != null) {
                return tableMapper;
            }
            
            Annotation[] classAnnotations = dtoClass.getDeclaredAnnotations();
            if (classAnnotations.length == 0) {
                throw new RuntimeException("Class " + dtoClass.getName() + " has no annotation, I can't build 'TableMapper' for it.");
            }
            
            tableMapper = new TableMapper();
            for (Annotation an : classAnnotations) {
                if (an instanceof TableMapperAnnotation) {
                    tableMapper.setTableMapperAnnotation(an);
                }
            }
            if (tableMapper.getTableMapperAnnotation() == null) {
                throw new RuntimeException("Class " + dtoClass.getName() + " has no 'TableMapperAnnotation', which has the database table information, I can't build 'TableMapper' for it.");
            }
            
            Field[] fields = dtoClass.getDeclaredFields();
            HashMap<String, FieldMapper> fieldMapperCache = new HashMap<String, FieldMapper>();
            ArrayList<FieldMapper> fieldMapperList = new ArrayList<FieldMapper>();
            
            Annotation[] fieldAnnotations;
            FieldMapper fieldMapper;
            FieldMapperAnnotation fieldMapperAnnotation;
            for (Field field : fields) {
                fieldAnnotations = field.getDeclaredAnnotations();
                if (fieldAnnotations.length == 0) {
                    continue;
                }
                for (Annotation an : fieldAnnotations) {
                    if (an instanceof FieldMapperAnnotation) {
                        fieldMapperAnnotation = (FieldMapperAnnotation) an;
                        fieldMapper = new FieldMapper();
                        fieldMapper.setFieldName(field.getName());
                        fieldMapper.setDbFieldName(fieldMapperAnnotation.dbFieldName());
                        fieldMapper.setJdbcType(fieldMapperAnnotation.jdbcType());
                        
                        fieldMapperCache.put(fieldMapperAnnotation.dbFieldName(), fieldMapper);
                        fieldMapperList.add(fieldMapper);
                    }
                }
            }
            
            tableMapper.setFieldMapperCache(fieldMapperCache);
            tableMapper.setFieldMapperList(fieldMapperList);

            tableMapperCache.put(dtoClass, tableMapper);
            return tableMapper;
        }
    }

    /**
     * 从注解里获取唯一键信息
     * 
     * @param tableMapper
     * @return
     */
    private static String[] buildUniqueKey(TableMapper tableMapper) {
        String[] uniqueKeyNames;
        
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        if (tma.uniqueKeyType().equals(UniqueKeyType.Single)) {
            uniqueKeyNames = new String[1];
            uniqueKeyNames[0] = tma.uniqueKey();
        } else {
            uniqueKeyNames = tma.uniqueKey().split(",");
        }
        
        return uniqueKeyNames;
    }

    /**
     * 根据传入对象注解信息，自动生成insert sql
     * 
     * @param object
     * @return
     * @throws Exception
     * @author dbz
     */
    public static String buildInsertSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry. I refuse to build sql for a null object.");
        }
        
        Map<String, Object> dtoFieldMap = PropertyUtils.describe(object);
        
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        StringBuffer tableSql = new StringBuffer();
        StringBuffer valueSql = new StringBuffer();

        tableSql.append("insert into ").append(tableName).append("(");
        valueSql.append("values(");

        boolean allFieldNull = true;
        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(dbFieldName);
            String fieldName = fieldMapper.getFieldName();
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                continue;
            }
            allFieldNull = false;
            tableSql.append(dbFieldName).append(",");
            valueSql.append("#{").append(fieldName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("},");
        }
        if (allFieldNull) {
            throw new RuntimeException("Are you joking? Object " + object.getClass().getName()
                    + "'s all fields are null, how can i build sql for it?!");
        }
        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        valueSql.delete(valueSql.lastIndexOf(","), valueSql.lastIndexOf(",") + 1);
        return tableSql.append(") ").append(valueSql).append(")").toString();
    }

    /**
     * 由传入的对象生成update sql语句
     * 
     * @param object
     * @return
     * @throws Exception
     * @author dbz
     */
    public static String buildUpdateSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry, I refuse to build sql for a null object!");
        }
        
        Map<String, Object> dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        
        String tableName = tma.tableName();
        String[] uniqueKeyNames = buildUniqueKey(tableMapper);

        StringBuffer tableSql = new StringBuffer();
        StringBuffer whereSql = new StringBuffer(" where ");

        tableSql.append("update ").append(tableName).append(" set ");

        boolean allFieldNull = true;
        FieldMapper fieldMapper;
        String fieldName;
        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
            fieldMapper = tableMapper.getFieldMapperCache().get(dbFieldName);
            fieldName = fieldMapper.getFieldName();
            
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                continue;
            }
            
            allFieldNull = false;
            tableSql.append(dbFieldName).append("=#{").append(fieldName).append(",").append("jdbcType=").append(fieldMapper.getJdbcType().toString()).append("},");
        }
        if (allFieldNull) {
            throw new RuntimeException("Are you joking? Object " + object.getClass().getName() + "'s all fields are null, how can I build sql for it?");
        }
        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        
        for (int i = 0; i < uniqueKeyNames.length; i++) {
            fieldMapper = tableMapper.getFieldMapperCache().get(uniqueKeyNames[i]);
            fieldName = fieldMapper.getFieldName();
            
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                throw new RuntimeException("Unique key '" + uniqueKeyNames[i] + "' can't be null, build update sql failed!");
            }
            
            whereSql.append(uniqueKeyNames[i]);
            whereSql.append("=#{").append(fieldName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("} and ");
        }
        whereSql.delete(whereSql.lastIndexOf("and"), whereSql.lastIndexOf("and") + 3);
        
        return tableSql.append(whereSql).toString();
    }

    /**
     * 由传入的对象生成delete sql语句
     * 
     * @param object
     * @return
     * @throws Exception
     * @author dbz
     */
    public static String buildDeleteSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry, I refuse to build sql for a null object!");
        }
        
        Map<String, Object> dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        String[] uniqueKeyNames = buildUniqueKey(tableMapper);

        StringBuffer sql = new StringBuffer();

        sql.append("delete from ").append(tableName).append(" where ");
        for (int i = 0; i < uniqueKeyNames.length; i++) {
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(uniqueKeyNames[i]);
            String fieldName = fieldMapper.getFieldName();
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                throw new RuntimeException("Unique key '" + uniqueKeyNames[i] + "' can't be null, build update sql failed!");
            }
            
            sql.append(uniqueKeyNames[i]);
            sql.append("=#{").append(fieldName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("} and ");
        }
        sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3);

        return sql.toString();
    }

    /**
     * 由传入的对象生成query sql语句
     * 
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildSelectSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry, I refuse to build sql for a null object!");
        }
        
        Map<String, Object> dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        String[] uniqueKeyNames = buildUniqueKey(tableMapper);

        StringBuffer selectSql = new StringBuffer();
        selectSql.append("select ");
        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
            selectSql.append(dbFieldName).append(",");
        }
        selectSql.delete(selectSql.lastIndexOf(","), selectSql.lastIndexOf(",") + 3);
        selectSql.append(" from ").append(tableName);

        StringBuffer whereSql = new StringBuffer(" where ");
        for (int i = 0; i < uniqueKeyNames.length; i++) {
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(uniqueKeyNames[i]);
            String fieldName = fieldMapper.getFieldName();
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                throw new RuntimeException("Unique key '" + uniqueKeyNames[i] + "' can't be null, build query sql failed!");
            }
            
            whereSql.append(uniqueKeyNames[i]);
            whereSql.append("=#{").append(fieldName)	// getId返回类型与jdbcType不一致时，报错It was either not specified and/or could not be found for the javaType / jdbcType combination specified.
            		.append(",").append("jdbcType=").append(fieldMapper.getJdbcType().toString())
            		.append("} and ");
        }
        whereSql.delete(whereSql.lastIndexOf("and"), whereSql.lastIndexOf("and") + 3);
       
        return selectSql.append(whereSql).toString();
    }

}
