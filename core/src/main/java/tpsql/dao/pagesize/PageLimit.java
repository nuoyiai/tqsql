package tpsql.dao.pagesize;

public class PageLimit implements IPageSize  {
	protected int offset;
	protected int limit;
	protected int pageNum;
	protected int pageSize;
	protected int total;
	protected boolean hasCount;
	
	public PageLimit(int offset,int limit){
		this.offset = offset;
		this.limit = limit;
		this.hasCount = true;
	}
	
	public PageLimit(int offset,int limit,boolean hasCount){
		this.offset = offset;
		this.limit = limit;
		this.hasCount = hasCount;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public void setLimit(int limit) {
		this.limit = limit;
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
	public boolean getHasCount() {
		return hasCount;
	}

	@Override
	public void setHasCount(boolean hasCount) {
		this.hasCount = hasCount;
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


}
