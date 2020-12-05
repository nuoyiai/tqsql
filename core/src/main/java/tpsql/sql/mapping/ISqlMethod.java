package tpsql.sql.mapping;

import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;

/**
 * SqlMap 自定义函数接口
 */
public interface ISqlMethod {
	
	/**
	 * 得到自定义函数的名称
	 * @return
	 */
	String getName();
	
	/**
	 * 生成Sql语句对像
	 * @param dialect 数据库方言
	 * @param args 自定义函数参数
	 * @return Sql语句对像
	 */
	SqlString invoke(IDbConfig config, IDialect dialect, Object ... args);
}
