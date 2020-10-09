package tpsql.dao;

import java.util.List;
import java.util.Map;

import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.PageList;
import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.PageList;
import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.PageList;

public class PageSizeProxyDao<E> extends EntityProxyDao<E> implements IPageSizeDao<E>,IPageSizeProxyDao<E> {

	@Override
	public IPageList<?> queryEntities(String dataSqlId, String countSqlId,
                                      IPageSize page, Object... conditions) {
		int count = this.queryInt(countSqlId, conditions);
		List<?> list = queryEntities(dataSqlId,page.getPageSize(),page.getPageNum(),conditions);
		page.setTotal(count);
		return new PageList<Object>(list,page);
	}
	
	@Override
	public IPageList<?> queryEntities(String sqlId, IPageSize page, Object... conditions) {
		int count = this.queryCount(sqlId, conditions);
		List<?> list = queryEntities(sqlId,page.getPageSize(),page.getPageNum(),conditions);
		page.setTotal(count);
		return new PageList<Object>(list,page);
	}
	
	@Override
	public IPageList<?> queryEntities(String dataSqlId, String countSqlId,
									  IPageSize page, Map<String,String> propertyMap, Object... conditions) {
		int count = this.queryInt(countSqlId, conditions);
		List<?> list = queryEntities(dataSqlId,page.getPageSize(),page.getPageNum(),propertyMap,conditions);
		page.setTotal(count);
		return new PageList<Object>(list,page);
	}
	
	@Override
	public IPageList<?> queryEntities(String sqlId, IPageSize page, Map<String,String> propertyMap, Object... conditions) {
		int count = this.queryCount(sqlId, conditions);
		List<?> list = queryEntities(sqlId,page.getPageSize(),page.getPageNum(),propertyMap,conditions);
		page.setTotal(count);
		return new PageList<Object>(list,page);
	}
	
}
