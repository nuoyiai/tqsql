package tpsql.dao;

import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;

public interface IPageSizeDao<T> extends IEntityDao<T> {

	public IPageList<?> queryEntities(String dataSqlId, String countSqlId, IPageSize page, Object ... conditions);
	
	public IPageList<?> queryEntities(String sqlId,IPageSize page, Object... conditions);
	
}
