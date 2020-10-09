package tpsql.dao;

import java.util.List;
import java.util.Map;

import tpsql.collection.DataTable;

public interface IDataDao {
	
	void setDbConfig(String dbConfig);
	
	/**
	 * 通过表名获取主健Id
	 * @param tableName
	 * @return
	 */
	Long getId(String tableName);
	
	/**
	 * 通过表名获取主健Id
	 * @param tableName
	 * @return
	 */
	Long getId(String tableName,String pk);
	
	/**
	 * 通过表名获取主健Id
	 * @param tableName
	 * @param num
	 * @return
	 */
	Long[] getId(String tableName,int num);
	
	/**
	 * 查询数据
	 * @param sqlId 
	 * @param conditions
	 * @return
	 */
	DataTable queryData(String sqlId,Object ... conditions);
	
	/**
	 * 查询数据
	 * @param sqlId
	 * @param pageSize 第页多少记录
	 * @param pageNum 第几页
	 * @param conditions
	 * @return
	 */
	DataTable queryData(String sqlId,int pageSize,int pageNum,Object ... conditions);

	/**
	 * 查询返回第一行数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Map<String,Object> queryMap(String sqlId,Object ... conditions);

	/**
	 * 查询返回MapList数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<Map<String,Object>> queryMapList(String sqlId,Object ... conditions);
	
	/**
	 * 执行一条Sql
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public int execute(String sqlId,Object ... conditions);
	
	/**
	 * 批量执行Sql
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public int[] executes(String sqlId,List<?> conditions);
	
	/**
	 * 查询总数
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	int queryCount(String sqlId,Object ... conditions);
	
	/**
	 * 查询
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	Object queryValue(String sqlId,Object ... conditions);
	
	/**
	 * 取第一行，第一列
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	Object queryFirstValue(String sqlId,Object ... conditions);
	
	/**
	 * 查询整型值
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	Integer queryInt(String sqlId,Object ... conditions);
	
	/**
	 * 取第一行，第一列
	 * @return
	 */
	Integer queryFirstInt(String sqlId,Object ... conditions);
	
	/**
	 * 查询长江整型值
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	Long queryLong(String sqlId,Object ... conditions);
	
	/**
	 * 查询值列表
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	List<?> queryValueList(String sqlId,Object ... conditions);
	
	/**
	 * 更新数据
	 * @param datatable
	 * @param pkey
	 */
	void updateData(DataTable datatable,String pkey);
	
	/**
	 * 新增数据，主键未生成，需要自动生成
	 * @param datatable
	 * @param pkey
	 */
	void insertData(DataTable datatable,String pkey);

	/**
	 * 新增数据,主键已有值，不需要生成
	 * @param datatable
	 */
	void insertData(DataTable datatable);
	
}
