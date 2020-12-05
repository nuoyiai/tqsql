package tpsql.dao.codetable;

import java.util.ArrayList;
import java.util.List;

import tpsql.dao.IDataDao;
import tpsql.core.collection.DataRow;
import tpsql.core.collection.DataTable;
import tpsql.dao.support.ObjectDao;

public class DataCodeTable extends AbstractCodeTable {
	private IDataDao dao;

	public void setDao(IDataDao dao) {
		this.dao = dao;
	}

	public List<IItem> getItems(Object ... conditions) {
		if(this.dao==null){
			this.dao = new ObjectDao();
		}
		DataTable table = this.dao.queryData(sqlId,conditions);
		List<IItem> items = new ArrayList<IItem>();
		for(DataRow row : table){
			IItem item = this.getItem(row);
			items.add(item);
		}

		return items;
	}

}
