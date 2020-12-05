package tpsql.dao.pagesize;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> extends ArrayList<T> implements IPageList<T> {

	private static final long serialVersionUID = -7671096683461709474L;

	private int total;
	private int pageNum;
	private int pageSize;
	protected int offset;
	protected int limit;
	private boolean hasCount;
	
	public PageList(int total){
		this.total = total;
		this.pageNum = -1;
		this.pageSize = -1;
		this.hasCount = true;
	}
	
	public PageList(List<? extends T> list, int totalCount){
		this(list,totalCount,-1,-1);
	}
	
	public PageList(List<? extends T> list,IPageSize page){
		this(list,page.getTotal(),page.getPageNum(),page.getPageSize());
	}
	
	public PageList(List<? extends T> list, int totalCount,int pageNum,int pageSize){
		this.addAll(list);
		this.total = totalCount;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	@Override
	public int getTotal() {
		return total;
	}

	@Override
	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public int getPageNum() {
		return pageNum;
	}

	@Override
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int getLimit() {
		if(this.total>0){
			if((this.pageSize * this.pageNum) < this.total){
				return this.pageSize * this.pageNum;
			}else{
				return this.total;
			}
		}else{
			return this.pageSize * this.pageNum;
		}
	}
	
	@Override
	public void setLimit(int limit){
		this.limit = limit;
	}

	@Override
	public int getOffset(){
		return this.pageSize * (this.pageNum - 1);
	}
	
	@Override
	public void setOffset(int offset){
		this.offset = offset;
	}
	
	@Override
	public boolean getHasCount() {
		return hasCount;
	}

	@Override
	public void setHasCount(boolean hasCount) {
		this.hasCount = hasCount;
	}
}
