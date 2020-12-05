package tpsql.sql.util;

import java.util.List;
import java.util.Map;

import tpsql.core.collection.DataTable;
import tpsql.core.IDisposable;
import tpsql.sql.builder.SqlString;
import tpsql.sql.command.IDataReader;
import tpsql.sql.command.SqlCommandResult;
import tpsql.sql.meta.Table;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.driver.IDataProvider;
import tpsql.sql.meta.Column;
import tpsql.sql.meta.IDbMetaAccess;

/**
 * Sql封装类
 */
public interface ISqlHelper extends IDisposable {
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @return
	 */
	IDataReader getDataReader(String sqlString);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param top
	 * @return
	 */
	IDataReader getDataReader(String sqlString,int top);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param offset
	 * @param limit
	 * @return
	 */
	IDataReader getDataReader(String sqlString,int offset,int limit);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @return
	 */
	IDataReader getDataReader(SqlString sqlString);
	
	/**
	 * 返回一个数据库数据读取接口
	 * @param sqlString
	 * @param offset
	 * @param limit
	 * @return
	 */
	IDataReader getDataReader(SqlString sqlString,int offset,int limit);

	/**
	 * 执行Sql语句返回第一行第一个对像
	 * @param sqlString
	 * @return
	 */
    Object executeScalar(String sqlString);
    
	/**
	 * 执行Sql语句返回第一行第一个对像
	 * @param sqlString
	 * @return
	 */
    Object executeScalar(SqlString sqlString);
    
    /**
     * 执行Sql语句返回第一个列
     */
	public List<Object> executeScalars(String sqlString);

    /**
     * 执行Sql语句返回第一列
     */
	public List<Object> executeScalars(SqlString sqlString);
    
    /**
     * 执行Sql语句,返回引响行数
     * @param sqlString
     * @return
     */
    SqlCommandResult executeNoneQuery(String sqlString);

	/**
	 * 执行Sql语句,返回引响行数和自增id
	 */
	SqlCommandResult executeNoneQuery(SqlString sqlString,boolean autoIncrementFlag);
    
    /**
     * 批量执行Sql语句,返回引响行数
     * @param sqlStrings
     * @return
     */
	SqlCommandResult executeNoneQuery(String[] sqlStrings);

	/**
	 * 批量执行Sql语句,返回引响行数
	 */
	SqlCommandResult executeNoneQuery(String[] sqlStrings,boolean batchCommit);
    
    /**
     * 执行Sql语句,返回引响行数
     * @param sqlString
     * @return
     */
	SqlCommandResult executeNoneQuery(SqlString sqlString);
    
    /**
     * 批量执行Sql语句,返回引响行数
     * @param sqlStrings
     * @return
     */
	SqlCommandResult executeNoneQuery(SqlString[] sqlStrings);

	/**
	 * 批量执行Sql语句,返回引响行数
	 * @param sqlStrings
	 * @return
	 */
	SqlCommandResult executeNoneQuery(SqlString[] sqlStrings,boolean batchCommit);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @return
     */
    DataTable getDataTable(String sqlString);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @return
     */
    DataTable getDataTable(String sqlString,int offset,int limit);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @return
     */
    DataTable getDataTable(SqlString sqlString);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param offset
     * @param limit
     * @return
     */
    DataTable getDataTable(SqlString sqlString,int offset,int limit);

    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param tableName
     * @return
     */
    DataTable getDataTable(String sqlString,String tableName);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param offset
     * @param limit
     * @param tableName
     * @return
     */
    DataTable getDataTable(String sqlString,int offset,int limit,String tableName);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param tableName
     * @return
     */
    DataTable getDataTable(SqlString sqlString, String tableName);
    
    /**
     * 执行Sql语句,返回一个数据结果集
     * @param sqlString
     * @param offset
     * @param limit
     * @param tableName
     * @return
     */
    DataTable getDataTable(SqlString sqlString,int offset,int limit,String tableName);
    
    /**
	 * 执行Sql语句,返回第一行的Map数据对像
	 * @param sqlString
	 * @return
	 */
    Map<String,Object> getMap(String sqlString);
    
    /**
	 * 执行Sql语句,返回第一行的Map数据对像
	 * @param sqlString
	 * @return
	 */
    Map<String,Object> getMap(SqlString sqlString);
	
    /**
     * 执行Sql语句,返回MapList数据对像
     * @param sqlString
     * @return
     */
	List<Map<String,Object>> getMapList(String sqlString);
	
	/**
	 * 执行Sql语句,返回MapList数据对像
	 * @param sqlString
	 * @return
	 */
	List<Map<String,Object>> getMapList(SqlString sqlString);

	/**
	 * 获取表的元数据
	 * @param tableName
	 * @return
	 */
	Table getMetaTable(String tableName);

	/**
	 * 获取表的元数据集合
	 * @return
	 */
	List<Table> getMetaTableList();
	
	/**
	 * 通过Sql返回结果集列信息
	 * @return
	 */
	List<Column> getResultColumns(String sqlString);
	
	/**
	 * 通过表名获取列信息
	 * @param tableName
	 * @return
	 */
	List<Column> getTableColumns(String tableName);

    /**
     * 得到当前访问的数据库方言接口
     * @return
     */
    IDialect getDialect();
    
    /**
     * 得到当前访问的数据库接口
     */
    IDataProvider getDriver();
    
    /**
     * 得到当前访问的数据库元数据接口
     * @return
     */
    IDbMetaAccess getDbMeta();

	/**
	 * 开始事务处理
	 */
	void beginTransaction();

	/**
	 * 结束事务处理
	 */
    void endTransaction(boolean errorFlag);

	/**
	 * 得到数据库元数据
	 * @return
	 */
	Map<String,Object> getMetaDataMap();

}
