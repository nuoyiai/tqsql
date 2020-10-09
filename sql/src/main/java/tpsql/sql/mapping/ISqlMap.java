package tpsql.sql.mapping;

import java.util.Collection;
import java.util.Set;

import tpsql.sql.builder.ISqlBuilder;
import tpsql.sql.builder.SqlString;
import tpsql.sql.cache.CacheModel;

/**
 * Sql配置接口
 */
public interface ISqlMap {
	/**
	 * 通过配置名称,得到一个Sql构造接口
	 * @param id
	 * @return
	 */
	ISqlBuilder get(String id);

	/**
	 * 得到缓存
	 * @param id
	 * @return
	 */
	CacheModel getCache(String id);

	/**
	 * 得到聚合
	 * @param id
	 * @return
	 */
	SqlPoly getPoly(String id);

	/**
	 * 设置Sql配置文件
	 * @param contexts
	 */
	void setContexts(Set<SqlContext> contexts);

	/**
	 *
	 * @return
	 */
	Collection<SqlItem> getSqlItems();

	/**
	 * 初始化配置
	 */
	void onit();

	/**
	 * 添加动态Sql
	 * @param sqlId
	 * @param sqlString
	 */
	SqlItem addDynamicSql(String sqlId,String sqlString);

	/**
	 * 添加临时求总数sql,统计sql临时时间用的
	 * @param sqlId
	 * @param sqlString
	 */
	SqlItem addCountSql(String sqlId,SqlString sqlString);

}
