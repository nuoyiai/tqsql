package tpsql.dao.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tpsql.sql.command.IDataReader;
import tpsql.sql.meta.Column;
import tpsql.sql.meta.Table;
import tpsql.sql.util.ISqlHelper;
import tpsql.sql.util.SqlHelper;
import org.springframework.stereotype.Component;

@Component
public class DbMetaStore implements IDbMetaStore {
	private Map<String,Table> tableMap;
	private Map<String,Column> columnMap;
	
	public DbMetaStore(){
		this.tableMap = new HashMap<String,Table>();
		this.columnMap = new HashMap<String,Column>();
	}

	public Table getTable(String tableName) {
		if(!tableMap.containsKey(tableName)) {
			this.initTableMeta(tableName);
		}
		return tableMap.get(tableName);
	}

	/**
	 * 得到例信息
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public Column getColumn(String tableName, String columnName) {
		if(!tableMap.containsKey(tableName)) {
			this.initTableMeta(tableName);
		}
		String key = getColumnKey(tableName,columnName); 
		return columnMap.get(key);
	}
	
	private synchronized void initTableMeta(String tableName){
		String sqlString  = "SELECT * FROM "+tableName+" WHERE 1=0";
		ISqlHelper ish = SqlHelper.instance();
		IDataReader reader = ish.getDataReader(sqlString);
		Table table = new Table();
		table.setName(tableName);
		List<Column> columns = reader.getColumns();
		if(columns!=null && columns.size()>0){
			for(Column column : columns){
				String key = getColumnKey(tableName,column.getName());
				columnMap.put(key, column);
			}
			table.setColumns(columns);
		}
		tableMap.put(tableName, table);
		reader.close();
		ish.dispose();
	}
	
	private String getColumnKey(String tableName, String columnName){
		return tableName+"_"+columnName;
	}

	/**
	 * 清除元数据缓存
	 * @param tableName
	 */
	public void clear(String tableName){
		tableMap.remove(tableName);
	}
}
