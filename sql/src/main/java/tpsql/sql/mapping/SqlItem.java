package tpsql.sql.mapping;

import java.util.*;

import tpsql.sql.builder.ISqlBuilder;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;

/**
 * Sql语句配置
 */
public class SqlItem implements ISqlBuilder,ISqlItemCount {
	/** 标识 */
	private String id;
	/** 缓存 */
	private String cacheId;
	/** 文本和子节点组成的内容对像 */
	private SqlItemContent content;
	/** 结果实体应射 */
	private SqlResultMap result;
	/** 结果数量类型 */
	private String resultClass;
	/** 外联Sql分组 */
	private Map<String,SqlJoinGroup> joinGroupMap;
	/** 是否动态加入 */
	private boolean dynamic = false;
	/** 动态Xml */
	private String dynamicXml;

	/** 执行次数 */
	private long execCount;
	/** 最大执行时间  */
	private long maxTime;
	/** 最小执行时间 */
	private long minTime;
	/** 总共执行时间 */
	private long totalTime;
	/** 最后执行时间 */
	private long lastTime;
	/** Sql是否Select语句 */
	private boolean isSelect;
	
	/** 得到标识 */
	public String getId()
	{
		return this.id;
	}
	
	/** 设置标识 */
	public void setId(String id)
	{
		this.id = id;
	}
	
	/** 得到缓存标识 */
	public String getCacheId() {
		return cacheId;
	}

	/** 设置缓存标识 */
	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	/** 得到结果类型 */
	public String getResultClass() {
		return resultClass;
	}

	/** 设置结果类型 */
	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

	/** 得到结果类型信息 */
	public SqlResultMap getResult()
	{
		return this.result;
	}
	
	/** 设置结果类型信息 */
	public void setResult(SqlResultMap result)
	{
		this.result = result;
	}
	
	/** 得到内容 */
	public SqlItemContent getContent()
	{
		return this.content;
	}
	
	/** 设置内容 */
	public void setContent(SqlItemContent content)
	{
		this.content = content;
	}
	
	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public String getDynamicXml() {
		return dynamicXml;
	}

	public void setDynamicXml(String dynamicXml) {
		this.dynamicXml = dynamicXml;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}

	public Collection<SqlJoinGroup> getPolyGroups() {
		return joinGroupMap.values();
	}

	public SqlItem()
	{
		this("");
	}
	
	public SqlItem(String id)
	{
		this.id = id;
		this.content = new SqlItemContent();
		this.content.setItem(this);
		this.result = null;
		this.dynamicXml = "";
		this.joinGroupMap = new HashMap<String, SqlJoinGroup>();
		this.isSelect = true;
	}

	/**
	 * 添加SQL外联
	 * @param sqlJoin
	 */
	public void addSqlJoin(SqlJoin sqlJoin){
		if(sqlJoin !=null) {
			String key = sqlJoin.getSqlId() + "_" + sqlJoin.getFkey();
			if (!joinGroupMap.containsKey(key)) {
				joinGroupMap.put(key, new SqlJoinGroup(sqlJoin.getSqlId(), sqlJoin.getFkey()));
			}
			joinGroupMap.get(key).addSqlJoin(sqlJoin);
		}
	}

	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(Object ... params) {
		return this.toSqlString(null,params);
	}

	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(IDialect dialect, Object ... params) {
		SqlItemArgs  args = new SqlItemArgs(params);
		args.setDialect(dialect);
		return this.content.toString(args);
	}
	
	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(IDbConfig config, IDialect dialect, Object ... params) {
		SqlItemArgs args = new SqlItemArgs(params);
		args.setDialect(dialect);
		args.setDbConfig(config);
		return this.content.toString(args);
	}
	
	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(IDbConfig config,IDialect dialect,Map<String,Object> threadMap,Object ... params) {
		SqlItemArgs args = new SqlItemArgs(params);
		args.setVariables(threadMap);
		args.setDialect(dialect);
		args.setDbConfig(config);
		return this.content.toString(args);
	}

	/**
	 * 执行Sql执行时间
	 * @param sqlString
	 */
	public void updateExecSqlTime(SqlString sqlString){
		long time = sqlString.getExceTime();
		if(this.maxTime<time) {
			this.maxTime = time;
		}
		if(this.minTime>time) {
			this.minTime = time;
		}
		this.totalTime+=time;
		this.execCount++;
		this.lastTime=time;
	}

	/**
	 * 计算Sql执行时间
	 * @param sqlStrings
	 */
	public void updateExecSqlTime(SqlString[] sqlStrings){
		if(sqlStrings.length>0){
			long time = sqlStrings[0].getExceTime()/sqlStrings.length;
			if(this.maxTime<time) {
				this.maxTime = time;
			}
			if(this.minTime>time) {
				this.minTime = time;
			}
			this.totalTime+=sqlStrings[0].getExceTime();
			this.execCount+=sqlStrings.length;
			this.lastTime=time;
		}
	}

	/**
	 * 平均执行时间
	 * @return
	 */
	@Override
	public long getAverageTime(){
		if(this.execCount>0){
			return this.totalTime / this.execCount;
		}
		return 0;
	}

	/**
	 * 最执执行时间
	 * @return
	 */
	@Override
	public long getMaxTime(){
		return this.maxTime;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public long getMinTime(){
		return this.minTime;
	}

	/**
	 * 执行次数
	 * @return
	 */
	@Override
	public long getExceCount(){
		return this.execCount;
	}

	/**
	 * 总行执行时间
	 * @return
	 */
	@Override
	public long getTotalTime(){
		return this.totalTime;
	}

	@Override
	public long getLastTime(){
		return this.lastTime;
	}



	/**
	 * 执行SqlId
	 * @return
	 */
	@Override
	public String getSqlId(){
		return this.id;
	}

}
