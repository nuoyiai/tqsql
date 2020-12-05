package tpsql.sql.util;

import java.util.*;

import tpsql.core.collection.DataRow;
import tpsql.core.collection.DataTable;
import tpsql.core.spring.SpringContext;
import tpsql.dao.entity.IEntityMap;
import tpsql.sql.SqlExecuteException;
import tpsql.sql.builder.SqlString;
import tpsql.sql.cache.CacheKey;
import tpsql.sql.cache.CacheModel;
import tpsql.sql.command.IDataReader;
import tpsql.sql.command.SqlCommandResult;
import tpsql.sql.driver.DataProviderBuilder;
import tpsql.sql.driver.IDataProviderBuilder;
import tpsql.sql.mapping.*;
import tpsql.core.util.MathUtil;
import tpsql.core.util.ReflectorUtil;
import tpsql.core.util.StringUtil;
import tpsql.core.util.TypeUtil;
import tpsql.sql.driver.IDataProvider;
import tpsql.sql.event.SqlExecuteEvent;
import tpsql.sql.mapping.SqlPoly.PolyColumn;

public class SqlEngine extends SqlHelper implements ISqlEngine {
	protected static final String SqlMapKey = "SqlMap";
	protected static IEntityMap entityMap;
	protected static ThreadLocal<Map<String,Object>> sqlVariableThreadLocal = new ThreadLocal<Map<String,Object>>();

	protected ISqlMap getSqlMap(){
		return (ISqlMap)SpringContext.instance().get(SqlMapKey);
	}

	public SqlEngine(IDataProvider provider){
		super(provider);
	}

	public static IEntityMap getEntityMap(){
		if(entityMap==null) {
			entityMap = (IEntityMap) SpringContext.instance().get(IEntityMap.class);
		}
		return entityMap;
	}

	public static ISqlEngine instance(){
		IDataProvider provider = getBuilder().build();
		return new SqlEngine(provider);
	}

	public static ISqlEngine instance(String configName){
		IDataProvider provider = getBuilder().build(configName);
		return new SqlEngine(provider);
	}

	public DataTable getDataTable(String sqlId, Object... conditions) {
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof DataTable){
				return (DataTable)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.joinTable(sqlItem,table,sqlId);
					}
					this.provider.getConnection().close();
					cache.putObject(key, table);
					//SqlExecuteEvent.raise(sqlId);
					return table;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.joinTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				return table;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	public DataTable getDataTable(String sqlId,int top,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof DataTable){
				return (DataTable)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString,top);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.joinTable(sqlItem,table,sqlId);
					}
					cache.putObject(key, table);
					//SqlExecuteEvent.raise(sqlId);
					return table;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString,top);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.joinTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				return table;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	public DataTable getDataTable(String sqlId, int offset, int limit,
								  Object... conditions) {
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
//        System.out.println(sqlString.toString());
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof DataTable){
				return (DataTable)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString,offset,limit);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.joinTable(sqlItem,table,sqlId);
					}
					cache.putObject(key, table);
					//SqlExecuteEvent.raise(sqlId);
					return table;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString,offset,limit);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.joinTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				return table;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	public List<?> getProxyList(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof List){
				return (List)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.joinTable(sqlItem,table,sqlId);
					}
					this.provider.getConnection().close();
					List<?> list = getProxyListByDataTable(table);
					cache.putObject(key, list);
					//SqlExecuteEvent.raise(sqlId);
					return list;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.joinTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				List<?> list = getProxyListByDataTable(table);
				return list;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	public List<?> getProxyList(String sqlId,int top,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof List){
				return (List)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString,top);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.joinTable(sqlItem,table,sqlId);
					}
					List<?> list = getProxyListByDataTable(table);
					cache.putObject(key, list);
					//SqlExecuteEvent.raise(sqlId);
					return list;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString,top);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.joinTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				List<?> list = getProxyListByDataTable(table);
				return list;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	public List<?> getProxyList(String sqlId,int offset,int limit,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
//        System.out.println(sqlString.toString());
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())){
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof List){
				return (List)obj;
			}else{
				try{
					IDataReader reader = this.getDataReader(sqlString,offset,limit);
					sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
					DataTable table = this.getDataTable("",reader);
					if(table.rowSize()>0){
						this.joinTable(sqlItem,table,sqlId);
					}
					List<?> list = getProxyListByDataTable(table);
					cache.putObject(key, list);
					//SqlExecuteEvent.raise(sqlId);
					return list;
				}catch(SqlExecuteException e){
					throw e;
				}finally{
					this.provider.getConnection().close();
				}
			}
		}else{
			try{
				IDataReader reader = this.getDataReader(sqlString,offset,limit);
				sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
				DataTable table = this.getDataTable("",reader);
				if(table.rowSize()>0){
					this.joinTable(sqlItem,table,sqlId);
				}
				//SqlExecuteEvent.raise(sqlId);
				List<?> list = getProxyListByDataTable(table);
				return list;
			}catch(SqlExecuteException e){
				throw e;
			}finally{
				this.provider.getConnection().close();
			}
		}
	}

	/**
	 * 查询返回第一行数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Map<String,Object> getMap(String sqlId,Object ... conditions){
		DataTable table = this.getDataTable(sqlId,conditions);
		if(table.rowSize()>0){
			return table.getRow(0).toMap();
		}
		return null;
	}

	/**
	 * 查询返回MapList数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<Map<String,Object>> getMapList(String sqlId,Object ... conditions){
		DataTable table = this.getDataTable(sqlId,conditions);
		return table.toMapList();
	}

	/**
	 * 查询值
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryValue(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())) {
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if (obj != null) {
				return obj;
			} else {
				Object result = this.executeScalar(sqlString);
				sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
				//SqlExecuteEvent.raise(sqlId);
				if (StringUtil.isNotEmpty(sqlItem.getResultClass())) {
					Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
					result = TypeUtil.changeType(result, clazz);
				}
				cache.putObject(key, result);
				return result;
			}
		}else {
			Object result = this.executeScalar(sqlString);
			sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
			//SqlExecuteEvent.raise(sqlId);
			if (StringUtil.isNotEmpty(sqlItem.getResultClass())) {
				Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
				return TypeUtil.changeType(result, clazz);
			} else {
				return result;
			}
		}
	}

	/**
	 * 第一行，第一列
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryFirstValue(String sqlId, Object... conditions) {
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())) {
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if (obj != null) {
				return obj;
			} else {
				SqlString firstSqlString = this.getDialect().getTopString(sqlString, 1);
				Object result = this.executeScalar(firstSqlString);
				sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
				SqlExecuteEvent.raise(sqlId);
				if (StringUtil.isNotEmpty(sqlItem.getResultClass())) {
					Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
					result = TypeUtil.changeType(result, clazz);
				}
				cache.putObject(key, result);
				return result;
			}
		}else {
			SqlString firstSqlString = this.getDialect().getTopString(sqlString, 1);
			Object result = this.executeScalar(firstSqlString);
			sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
			SqlExecuteEvent.raise(sqlId);
			if (StringUtil.isNotEmpty(sqlItem.getResultClass())) {
				Class<?> clazz = ReflectorUtil.getClassForName(sqlItem.getResultClass());
				return TypeUtil.changeType(result, clazz);
			} else {
				return result;
			}
		}
	}

	public Integer queryInt(String sqlId,Object ... conditions){
		Object result = this.queryValue(sqlId, conditions);
		if(result!=null){
			return (Integer)TypeUtil.changeType(result,Integer.class);
		}
		return null;
	}

	/**
	 * 查询值列表
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<?> queryValueList(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())) {
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(sqlString.toString(), sqlString.getParamValues());
			Object obj = cache.getObject(key);
			if(obj!=null && obj instanceof List){
				return (List)obj;
			}else{
				List<Object> result = this.executeScalars(sqlString);
				sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
				cache.putObject(key, result);
				return result;
			}
		}else {
			List<Object> result = this.executeScalars(sqlString);
			sqlItem.updateExecSqlTime(sqlString);        //统计执行时间
			return result;
		}
	}

	/**
	 * 查询总数
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public int queryCount(String sqlId,Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		SqlString countSqlString = this.getDialect().getCountSqlString(sqlString);
		if(StringUtil.isNotEmpty(sqlItem.getCacheId())) {
			CacheModel cache = this.getSqlMap().getCache(sqlItem.getCacheId());
			CacheKey key = this.getCacheKey(countSqlString.toString(), countSqlString.getParamValues());
			Object obj = cache.getObject(key);
			if (obj != null && obj instanceof Integer) {
				return (Integer)obj;
			}else{
				Integer count = (Integer) TypeUtil.changeType(this.executeScalar(countSqlString), Integer.class);
				SqlExecuteEvent.raise(sqlId);
				cache.putObject(key, count);
				return count.intValue();
			}
		}else {
			Integer count = (Integer) TypeUtil.changeType(this.executeScalar(countSqlString), Integer.class);
			SqlExecuteEvent.raise(sqlId);
			return count.intValue();
		}
	}

	/**
	 * 执行Sql语句,返回引响行数
	 */
	public SqlCommandResult executeNoneQuery(String sqlId, Object ... conditions){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlString sqlString = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions);
		boolean autoIncrementFlag = (!this.getDialect().supportsSequences() && sqlItem.isAutoIncrement());
		SqlCommandResult result = this.executeNoneQuery(sqlString,autoIncrementFlag);
		sqlItem.updateExecSqlTime(sqlString);		//统计执行时间
		SqlExecuteEvent.raise(sqlId);
		if(autoIncrementFlag){
			setAutoIncrementId(conditions,result);
		}
		return result;
	}

	public SqlCommandResult executeNoneQuerys(String sqlId,List<?> conditions){
		return executeNoneQuerys(sqlId,conditions,true);
	}

	public SqlCommandResult executeNoneQuerys(String sqlId,List<?> conditions,boolean batchCommit){
		SqlItem sqlItem = (SqlItem) this.getSqlMap().get(sqlId);
		SqlCommandResult result = new SqlCommandResult();
		if(conditions!=null && conditions.size()>0){
			SqlString[] sqlStrings = new SqlString[conditions.size()];
			for(int i=0;i<conditions.size();i++){
				sqlStrings[i] = sqlItem.toSqlString(this.getConfig(),this.getDialect(),sqlVariableThreadLocal.get(),conditions.get(i));
			}
			if(batchCommit) {
				result = this.executeNoneQuery(sqlStrings);
				sqlItem.updateExecSqlTime(sqlStrings);        //统计执行时间
				SqlExecuteEvent.raise(sqlId);
			}else{
				int[] obj = new int[sqlStrings.length];
				for(int i=0;i<sqlStrings.length;i++){
					obj[i] = this.executeNoneQuery(sqlStrings[i]).getRowFirst();
				}
				sqlItem.updateExecSqlTime(sqlStrings);        //统计执行时间
				SqlExecuteEvent.raise(sqlId);
				result.setResultRow(obj);
			}
		}
		return result;
	}

	/**
	 * 设置返回的自增ID
	 * @param objs
	 * @param result
	 */
	private void setAutoIncrementId(Object[] objs,SqlCommandResult result){
		if(objs.length>0 && result.getResultList()!=null && result.getResultList().size()>0) {
			Object obj = objs[0];
			Integer id = (Integer)TypeUtil.changeType(result.getResultList().get(0),Integer.class);
			if (obj != null && getEntityMap().containKey(obj.getClass())) {
				String idProperty = getEntityMap().getIdProperty(obj.getClass());
				if(StringUtil.isNotEmpty(idProperty)){
					ReflectorUtil.setPropertyValue(obj,idProperty,id);
				}
			}
		}
	}

	private CacheKey getCacheKey(String sqlString,Object[] params){
		CacheKey cacheKey = new CacheKey();
		cacheKey.update(sqlString);
		if(params!=null){
			for(Object param : params){
				cacheKey.update(param);
			}
		}
		return cacheKey;
	}

	/**
	 * 连接外部表
	 * @param sqlItem
	 * @param table
	 * @param sqlId
	 */
	private void joinTable(SqlItem sqlItem,DataTable table,String sqlId){
		if(sqlItem.getPolyGroups()!=null && sqlItem.getPolyGroups().size()>0){
			Map<String,DataTable> joinTableMap = new HashMap<String,DataTable>();
			Map<String,Set<Object>> pksMap = new HashMap<String,Set<Object>>();
			for(SqlJoinGroup polyGroup : sqlItem.getPolyGroups()){
				String groupKey = polyGroup.getKey();
				for(SqlJoin poly : polyGroup.getJoins()) {
					if (!table.contains(poly.getPkey()))
						throw new RuntimeException(StringUtil.format("在 sql {0} 的返回结果中,找不到 poly列{1}", sqlId, poly.getPkey()));
				}
				if(!pksMap.containsKey(groupKey))pksMap.put(groupKey,new HashSet<Object>());
				for (DataRow row : table) {
					for(SqlJoin join : polyGroup.getJoins()) {
						String pkey = join.getPkey();
						if (StringUtil.isNotEmpty(join.getSplit())) {
							Object pkVals = row.get(pkey);
							if (pkVals != null && !"".equals(pkVals)) {
								String[] vs = pkVals.toString().split(join.getSplit(), -1);
								for (String v : vs) {
									if (!"".equals(v) && !pksMap.get(groupKey).contains(v)) pksMap.get(groupKey).add(v);
								}
							}
						} else {
							Object pkVal = row.get(pkey);
							if (pkVal != null && !"".equals(pkVal) && !pksMap.get(groupKey).contains(pkVal))
								pksMap.get(groupKey).add(pkVal);
						}
					}
				}

				if(!joinTableMap.containsKey(groupKey)){
					if(pksMap.get(groupKey).size()>0){
						DataTable polyTable = this.getDataTable(polyGroup.getSqlId(),new Object[]{pksMap.get(groupKey)});
						joinTableMap.put(groupKey, polyTable);
					}
				}

				for(SqlJoin join : polyGroup.getJoins()) {
					if (joinTableMap.containsKey(groupKey)){
						if(join instanceof SqlPoly) {
							this.polyTable(table, (SqlPoly)join, joinTableMap.get(groupKey), sqlId);
						}else if(join instanceof SqlLink){
							this.linkTable(table, (SqlLink)join, joinTableMap.get(groupKey), sqlId);
						}
					}
				}
			}

		}
	}

	/**
	 * 连接外部表
	 * @param table
	 * @param poly
	 * @param joinTable
	 * @param sqlId
	 */
	private void polyTable(DataTable table, SqlPoly poly, DataTable joinTable, String sqlId){
		String pkey = poly.getPkey();
		String fkey = poly.getFkey();
		if(!joinTable.contains(fkey))throw new RuntimeException(StringUtil.format("在 sql {0} 的返回结果中,找不到 列{1}",poly.getSqlId(),fkey));
		Set<String> addCols = new HashSet<String>();
		for(PolyColumn col : poly.getColumns()){
			if(!table.contains(col.getColumnName()))addCols.add(col.getColumnName());			//非重名列
		}
		Map<Object,Object[]> map = new HashMap<Object,Object[]>();
		for(DataRow row : joinTable){
			Object fkVal = row.get(fkey);
			if(fkVal!=null){
				if(!map.containsKey(fkVal))map.put(fkVal,new Object[poly.getColumns().size()]);
				for(int i=0;i<poly.getColumns().size();i++){
					String colName = poly.getColumns().get(i).getName();
					if(joinTable.contains(colName)){
						Object val = this.getPolyColumnValue(row.get(colName),map.get(fkVal)[i],poly.getColumns().get(i));
						map.get(fkVal)[i]=val;
					}
				}
			}
		}

		for (DataRow row : table) {
			if(StringUtil.isNotEmpty(poly.getSplit())){
				Object pkVals = row.get(pkey);
				if(pkVals!=null && !"".equals(pkVals)){
					String[] vs = pkVals.toString().split(poly.getSplit(),-1);
					for(int i=0;i<poly.getColumns().size();i++){
						String colName = poly.getColumns().get(i).getColumnName();
						if(addCols.contains(colName)){
							String text = "";
							for(String v : vs){
								if(map.containsKey(v)){
									Object val = map.get(v)[i];
									text+=(text.length()>0)?poly.getSplit()+val.toString():val.toString();
								}
							}
							row.set(colName,text);
						}
					}
				}
			}else{
				Object pkVal = row.get(pkey);
				if(map.containsKey(pkVal)){
					for(int i=0;i<poly.getColumns().size();i++){
						String colName = poly.getColumns().get(i).getColumnName();
						if(addCols.contains(colName)){
							if(poly.getColumns().get(i).getOp()==SqlPoly.OP.AVERAGE){
								Object[] vals = (Object[])map.get(pkVal)[i];
								Object val = MathUtil.average(vals[0],(Integer)vals[1]);
								row.set(colName,val);
							}else{
								Object val = map.get(pkVal)[i];
								row.set(colName,val);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 连接外部表
	 * @param table
	 * @param link
	 * @param joinTable
	 * @param sqlId
	 */
	private void linkTable(DataTable table, SqlLink link, DataTable joinTable, String sqlId){
		String pkey = link.getPkey();
		String fkey = link.getFkey();
		if(!joinTable.contains(fkey))throw new RuntimeException(StringUtil.format("在 sql {0} 的返回结果中,找不到 列{1}",link.getSqlId(),fkey));
		Map<Object,List> map = new HashMap<Object,List>();
		for(DataRow row : joinTable){
			Object fkVal = row.get(fkey);
			if(fkVal!=null){
				if(!map.containsKey(fkVal))map.put(fkVal,new ArrayList());
				map.get(fkVal).add(row);
			}
		}

		for (DataRow row : table) {
			Object pkVal = row.get(pkey);
			if(map.containsKey(pkVal)){
				String colName = link.getColumn();
				Object val = map.get(pkVal);
				row.set(colName,val);
			}
		}
	}

	/**
	 * 计算连接外部表列的值
	 * @param val
	 * @param oldVal
	 * @param column
	 * @return
	 */
	private Object getPolyColumnValue(Object val,Object oldVal,SqlPoly.PolyColumn column){
		if(column.getOp()!=null){
			switch(column.getOp()){
				case JOIN:{
					String ov = (oldVal!=null)?(String)TypeUtil.changeType(oldVal, String.class):"";
					if(val!=null){
						String v = (String)TypeUtil.changeType(val, String.class);
						ov+=(!"".equals(ov))?column.getSplit()+v:v;
					}
					return ov;
				}
				case SUM:
					return MathUtil.add(val, oldVal);
				case AVERAGE:
					Object[] av = new Object[2];
					if(oldVal!=null){
						av[0] = MathUtil.add(val,((Object[])oldVal)[0]);
						av[1] = ((Integer)((Object[])oldVal)[0])+1;
					}else{
						av[0] = val;
						av[1] = 1;
					}
					return av;
				case MAX:
					if(TypeUtil.compareTo(val,oldVal)>0)return val;
				case MIN:
					if(TypeUtil.compareTo(val,oldVal)<0)return val;
				case FIRST:
					if(oldVal!=null)return oldVal;
				case LAST:
					if(val!=null)return val;
			}
		}
		return val;
	}
	
	/**
	 * 设置额外的Sql参数，当前线程有效
	 * @param key
	 * @param value
	 */
	public static void setSqlVariable(String key,Object value){
		if(sqlVariableThreadLocal.get()==null) {
			sqlVariableThreadLocal.set(new HashMap<String,Object>());
		}
		Map<String,Object> map = sqlVariableThreadLocal.get();
		map.put(key, value);
		sqlVariableThreadLocal.set(map);
	}
	
	public static void removeSqlVariables(){
		sqlVariableThreadLocal.remove();
	}

}
