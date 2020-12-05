package tpsql.dao;

import java.util.List;
import java.util.Map;

/**
 * 动态代理,为动态自定义字段
 * @author zhusw
 *
 */
public interface IEntityProxyDao<E> {
	
	/**
	 * 查询数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	List<?> queryEntities(String sqlId,Map<String,String> PropertyMap,Object ... conditions);
	
	/**
	 * 查询数据
	 * @param sqlId
	 * @param pageSize 第页多少记录
	 * @param pageNum 第几页
	 * @param conditions
	 * @return
	 */
	List<?> queryEntities(String sqlId,int pageSize,int pageNum,Map<String,String> propertyMap,Object ... conditions);
	
	/**
	 * 
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	Object queryEntity(String sqlId,Map<String,String> propertyMap,Object ... conditions);

}
