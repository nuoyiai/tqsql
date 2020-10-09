package tpsql.dao;

import java.util.Map;

import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;

/**
 * 生成代理类
 * @author Administrator
 *
 * @param <E>
 */
public interface IPageSizeProxyDao<E> {
	
	/**
	 * 
	 * @param dataSqlId
	 * @param countSqlId
	 * @param page
	 * @param propertyMap
	 * @param conditions
	 * @return
	 */
	IPageList<?> queryEntities(String dataSqlId, String countSqlId, IPageSize page, Map<String,String> propertyMap, Object... conditions);

	/**
	 * 
	 * @param sqlId
	 * @param page
	 * @param propertyMap
	 * @param conditions
	 * @return
	 */
	IPageList<?> queryEntities(String sqlId,IPageSize page,Map<String,String> propertyMap,Object... conditions);
	
}
