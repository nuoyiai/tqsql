package tpsql.dao.entity;

import java.util.Map;

public interface IEntityMap {
	
	/**
	 * 得到属性字段映射
	 * @param type
	 * @return
	 */
	Map<String,String> getMap(Class<?> type);
	
	/**
	 * 得到表名
	 * @param type
	 * @return
	 */
	String getTable(Class<?> type);
	
	/**
	 * 得到类
	 * @param tableName 表名
	 * @return
	 */
	Class<?> getType(String tableName);
	
	/**
	 * 通过属性名
	 * @param type
	 * @param columnName
	 * @return
	 */
	String getProperty(Class<?> type,String columnName);
	
	/**
	 * 得到列名
	 * @param type
	 * @param propertyName
	 * @return
	 */
	String getColumn(Class<?> type,String propertyName);
	
	/**
	 * 得到主键列
	 * @param type
	 * @return
	 */
	String getIdColumn(Class<?> type);

	/**
	 * 得到主键属性
	 * @param type
	 * @return
	 */
	String getIdProperty(Class<?> type);
	
	/**
	 * 得到主键列
	 * @param tableName
	 * @return
	 */
	String getIdColumn(String tableName);
	
	/**
	 * 是否包含实体映射类
	 * @param key
	 * @return
	 */
	boolean containKey(String key);
	
	/**
	 * 是否包含实体映射类或子类
	 * @param type
	 * @return
	 */
	boolean containKey(Class<?> type);
	
}
