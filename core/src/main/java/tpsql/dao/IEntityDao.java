package tpsql.dao;

import tpsql.dao.pagesize.IPageSize;

import java.util.List;

public interface IEntityDao<E> {
	
	Class<?> getEntityType();
	
	/**
	 * 查询数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	<T> List<T> queryEntities(String sqlId,Object ... conditions);
	
	/**
	 * 查询数据
	 * @param sqlId
	 * @param pageSize 第页多少记录
	 * @param pageNum 第几页
	 * @param conditions
	 * @return
	 */
	<T> List<T> queryEntities(String sqlId,int pageSize,int pageNum,Object ... conditions);
	
	/**
	 * 查询数据
	 * @param sqlId
	 * @param page 分页对像
	 * @param conditions
	 * @return
	 */
	<T> List<T> queryPageEntities(String sqlId, IPageSize page, Object ... conditions);
	
	/**
	 * 前几条
	 * @param sqlId
	 * @param top
	 * @param conditions
	 * @return
	 */
	<T> List<T> queryTopEntities(String sqlId,int top,Object ... conditions);
	
	/**
	 * 
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	Object queryEntity(String sqlId,Object ... conditions);
	
	/**
	 * 产生一个新的数字Id
	 * @return
	 */
	Long newId();

	/**
	 * 产生一个新的数字Id
	 * @return
	 */
	Long newId(Class<?> clazz);
	
	/**
	 * 产生多个新的数字Id
	 * @return
	 */
	Long[] newId(int num);
	
	/**
	 * 产生一个新的唯一id
	 * @param tableName	表名
	 * @return
	 */
	Long newId(String tableName,String pk);
	
}
