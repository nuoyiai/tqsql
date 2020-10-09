package tpsql.codetable;

import java.util.ArrayList;
import java.util.List;

import tpsql.dao.IDataDao;
import tpsql.collection.DataRow;
import tpsql.collection.DataTable;
import tpsql.dao.IDataDao;
import tpsql.dao.IDataDao;

public class DataCodeTable extends AbstractCodeTable {
	private IDataDao dao;

	public void setDao(IDataDao dao) {
		this.dao = dao;
	}

	
	@Override
	public List<IItem> getItems(Object ... conditions) {
		if(this.dao!=null){
			DataTable table = this.dao.queryData(sqlId,conditions);
			List<IItem> items = new ArrayList<IItem>();
			for(DataRow row : table){
				IItem item = this.getItem(row);
				items.add(item);
			}

			return items;
		}else {
			throw new RuntimeException("DataCodeTable没有配置Dao");
		}
	}

}
