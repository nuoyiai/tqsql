package tpsql.sql.dialect;

import tpsql.sql.command.ValueType;

public interface IMetaSql {
	
	/**
	 * 得到创建Sequence的Sql语句
	 * @param sequenceName
	 * @param value
	 * @return
	 */
	String[] getCreateSequenceSql(String sequenceName,Long value);
	
	/**
	 *
	 * @return
	 */
	String getSequencesSql();

	/**
	 * 得到查询自增ID
	 * @return
	 */
	String getAutoIncrementSql();
	
	/**
	 * 
	 * @param sequenceName
	 * @return
	 */
	String getSequenceSql(String sequenceName);
	
	/**
	 * 
	 * @param sequenceName
	 * @return
	 */
	String getSequenceNextSql(String sequenceName);
	
	/**
	 * 
	 * @param sequenceName
	 * @return
	 */
	String getSequenceNextSql(String sequenceName,int num);
	
	/**
	 * 
	 * @param sequenceName
	 * @return
	 */
	String getDropSequenceSql(String sequenceName);
	
	/**
	 * 得到数据库表的Sql语句
	 * @return
	 */
	String getTablesSql();
	
	/**
	 * 得到数据库表的Sql语句
	 * @return
	 */
	String getTablesSql(String tableName);

	/**
	 * 得到数据库表的Sql语句
	 * @return
	 */
	String getTablesSql(String tableName,String schemaName);
	
	/**
	 * 得到创建数据库表的Sql语句
	 * @param tableName
	 * @param pk
	 * @return
	 */
	String getCreateTableSql(String tableName,String pk,String[] fks);
	
	/**
	 * 得到数据库表的Sql语句
	 * @param tableName
	 * @return
	 */
	String getFieldsSql(String tableName);
	
	/**
	 * 修改表字段名
	 * @param tableName
	 * @param oldColumnName
	 * @param newColumnName
	 * @param fieldType
	 * @return
	 */
	String getRenameTableColumnSql(String tableName, String oldColumnName, String newColumnName, String fieldType);
	
	/**
	 * 添加数据库字段
	 * @param tableName
	 * @param columnName
	 * @param length
	 * @param type
	 * @return
	 */
	String getAddTableColumnSql(String tableName,String columnName,Integer length,Integer scale,ValueType type);
	
	/**
	 * 删除数据库字段
	 * @return
	 */
	String getDeteleTableColumnSql(String tableName,String columnName);
	
	/**
	 * 修改例注释
	 * @param tableName
	 * @param columnName
	 * @param comments
	 * @return
	 */
	String getCommentTableColumnSql(String tableName,String columnName,String comments);

	/**
	 * 得到函数
	 * @return
	 */
	String getFunctionsSql();

	/**
	 * 得到函数信息
	 * @param functionName
	 * @return
	 */
	String getFunctionsSql(String functionName);
	
	/**
	 * 得到数据类型
	 * @param fieldType
	 * @return
	 */
	ValueType getType(int fieldType);

}
