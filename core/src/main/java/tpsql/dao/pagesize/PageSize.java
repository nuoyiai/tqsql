package tpsql.dao.pagesize;

public class PageSize implements IPageSize {
	protected int pageNum;
	protected int pageSize;
	protected int offset;
	protected int limit;
	protected int total;
	protected boolean hasCount;
	
	public PageSize(){
		this.pageNum = 1;
		this.pageSize = 25;
		this.total = 0;
		this.hasCount = true;
	}
	
	public PageSize(int pageSize,int pageNum){
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = 0;
		this.hasCount = true;
	}
	
	public PageSize(int pageSize,int pageNum,int total)
	{
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		this.hasCount = true;
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
	public int getTotal() {
		return total;
	}

	@Override
	public void setTotal(int total) {
		this.total = total;
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
