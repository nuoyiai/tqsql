package tpsql.dao.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tpsql.core.collection.DataRow;
import tpsql.core.collection.DataTable;
import tpsql.dao.IEntityProxyDao;
import tpsql.dao.entity.EntityUtil;

/**
 * 动态代理,为动态自定义字段
 * @author zhusw
 */
public abstract class EntityProxyDao<E> extends EntityDao<E> implements IEntityProxyDao<E> {

	/**
	 * 查询数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<?> queryEntities(String sqlId, Map<String,String> PropertyMap, Object ... conditions){
		return this.queryEntities(sqlId, -1, -1,PropertyMap,conditions);
	}
	
	/**
	 * 查询数据
	 * @param sqlId
	 * @param pageSize 第页多少记录
	 * @param pageNum 第几页
	 * @param conditions
	 * @return
	 */
	public List<?> queryEntities(String sqlId, int pageSize, int pageNum, Map<String,String> propertyMap, Object ... conditions){
		if(propertyMap!=null && propertyMap.size()>0){
			DataTable table = (pageSize > 0 && pageNum > 0) ? this.queryData(sqlId,
					pageSize, pageNum, conditions) : this.queryData(sqlId,
					conditions);
			Class<?> entityType = (this.getResultClass(sqlId) != null) ? this
					.getResultClass(sqlId) : this.getEntityType();
			List<Object> list = new ArrayList<Object>();
			Map<String,String> proxyMap = getProxyPropertyMap(sqlId,propertyMap);
			for (DataRow row : table) {
				Object object = EntityUtil.newProxy(entityType,row,proxyMap);
				list.add(object);
			}
			CodeTableHelper.linkCodeTable(list);
			return list;
		}else{
			return super.queryEntities(sqlId, pageSize, pageNum, conditions);
		}
	}

	
	/**
	 * 
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryEntity(String sqlId, Map<String,String> propertyMap, Object ... conditions){
		if(propertyMap!=null && propertyMap.size()>0){
			DataTable table = this.queryData(sqlId, conditions);
			if (table.rowSize() > 0) {
				Class<?> entityType = (this.getResultClass(sqlId) != null) ? this
						.getResultClass(sqlId) : this.getEntityType();
				DataRow row = table.getRow(0);
				Map<String,String> proxyMap = getProxyPropertyMap(sqlId,propertyMap);
				Object object = EntityUtil.newProxy(entityType,row,proxyMap);
				CodeTableHelper.linkCodeTable(object);
				return object;
			} else {
				return null;
			}
		}else{
			return super.queryEntity(sqlId, conditions);
		}
	}
	
	/**
	 * 返回代理类的,扩展映射
	 * @param sqlId
	 * @return
	 */
	protected Map<String,String> getProxyPropertyMap(String sqlId,Map<String,String> propertyMap){
		Map<String,String> proxyMap = new HashMap<String,String>();
		Map<String,String> superMap = super.getProxyPropertyMap(sqlId);
		if(superMap!=null) {
			proxyMap.putAll(superMap);
		}
		if(propertyMap!=null) {
			proxyMap.putAll(propertyMap);
		}
		return proxyMap;
	}
	
}
