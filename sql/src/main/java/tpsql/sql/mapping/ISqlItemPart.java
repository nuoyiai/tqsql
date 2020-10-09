package tpsql.sql.mapping;

import tpsql.sql.builder.SqlString;
import tpsql.sql.builder.SqlString;
import tpsql.sql.builder.SqlString;

/**
 * SqlMap 元素对像接口
 */
public interface ISqlItemPart {
	/**
	 * 构造Sql语句
	 * @param arg 构造Sql语句参数对像
	 * @return Sql语句对像
	 */
	SqlString toString(SqlItemArgs arg);
}
