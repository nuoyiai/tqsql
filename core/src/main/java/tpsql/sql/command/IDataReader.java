package tpsql.sql.command;

import java.util.List;

import tpsql.sql.meta.Column;

/**
 * 数据读取接口
 */
public interface IDataReader {
	/**
	 * 关闭读取,并关闭连接
	 */
	void close();
	
	/**
	 * 读取下一行
	 * @return
	 */
	boolean read();

	/**
	 * 游标
	 * @return
	 */
	int current();
	
	/**
	 * 得到列的值
	 * @param columnName
	 * @return
	 */
	Object get(String columnName);
	
	/**
	 * 得到列的值
	 * @param columnIndex
	 * @return
	 */
	Object get(int columnIndex);

	/**
	 * 找到列
	 * @param columnName
	 * @return
	 */
	int findColumn(String columnName);
	
	/**
	 * 读取返回数据集的列名
	 * @return
	 */
	String[] getColumnNames();
	
	/**
	 * 读取返回数据集的列信息
	 * @return
	 */
	List<Column> getColumns();
	
}
