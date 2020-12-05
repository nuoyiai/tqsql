package tpsql.dao.metadata;

import tpsql.sql.meta.Column;
import tpsql.sql.meta.Table;

public interface IDbMetaStore {

	/**
	 * 
	 * @param tableName
	 * @return
	 */
	Table getTable(String tableName);
	
	/**
	 * 得到例信息
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	Column getColumn(String tableName,String columnName);

	/**
	 * 清除元数据缓存
	 * @param tableName
	 */
	void clear(String tableName);
}
