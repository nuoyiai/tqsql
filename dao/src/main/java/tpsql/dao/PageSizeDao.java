package tpsql.dao;

import java.util.List;

import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.PageList;
import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.PageList;

public class PageSizeDao<T> extends EntityDao<T> implements IPageSizeDao<T> {

	@Override
	public IPageList<?> queryEntities(String dataSqlId, String countSqlId,
                                      IPageSize page, Object... conditions) {
		if(page.getHasCount()){			//判断是否要计算总数
			int count = this.queryInt(countSqlId, conditions);
			page.setTotal(count);
		}
		List<?> list = queryPageEntities(dataSqlId,page,conditions);
		return new PageList<Object>(list,page);
	}
	
	@Override
	public IPageList<?> queryEntities(String sqlId, IPageSize page, Object... conditions) {
		if(page.getHasCount()){
			int count = this.queryCount(sqlId, conditions);
			page.setTotal(count);
		}
		List<?> list = queryPageEntities(sqlId,page,conditions);
		return new PageList<Object>(list,page);
	}
	
}

