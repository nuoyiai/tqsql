package tpsql.dao.support;

import tpsql.dao.IDataDao;
import tpsql.dao.metadata.IDbMetaStore;
import tpsql.dao.pagesize.IPageSize;
import tpsql.dao.pagesize.PageSizeHelper;
import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.builder.SqlStringBuilder;
import tpsql.core.collection.DataRow;
import tpsql.core.collection.DataTable;
import tpsql.core.spring.SpringContext;
import tpsql.sql.mapping.ISqlMap;
import tpsql.sql.meta.Column;
import tpsql.sql.util.ISqlEngine;
import tpsql.sql.util.SqlEngine;
import tpsql.core.util.TypeUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DataDao implements IDataDao {
	protected String dbConfig = "";

	protected IdGenerator getIdGenerator() {
		return (IdGenerator) SpringContext.instance().get(IdGenerator.class);
	}

	public void setDbConfig(String dbConfig){
		this.dbConfig = dbConfig;
	}
	
	protected ISqlMap getSqlMap(){
		return (ISqlMap)SpringContext.instance().get(ISqlMap.class);
	}
	
	protected IDbMetaStore getDbMetaStore(){
		return (IDbMetaStore)SpringContext.instance().get(IDbMetaStore.class);
	}
	
	/**
	 * 通过表名获取主健Id
	 * @param tableName
	 * @return
	 */
	public Long getId(String tableName){
		return getIdGenerator().getId(tableName);
	}
	
	/**
	 * 通过表名获取主健Id
	 * @param tableName
	 * @return
	 */
	public Long getId(String tableName, String pk){
		return getIdGenerator().getPkId(tableName,pk);
	}
	
	/**
	 * 通过表名获取主健Id
	 * @param tableName
	 * @param num
	 * @return
	 */
	public Long[] getId(String tableName, int num){
		return getIdGenerator().getId(tableName,num);
	}

	/**
	 * 查询总数
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public int queryCount(String sqlId, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		Integer count = ish.queryCount(sqlId,conditions);
		ish.dispose();
		return count;
	}

	public DataTable queryData(String sqlId, Object ... conditions){
		return this.queryData(sqlId, -1, -1, conditions);
	}
	
	public DataTable queryData(String sqlId, IPageSize page, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		DataTable table = null;
			int limit = page.getLimit();
			int offset = page.getOffset();
			table = ish.getDataTable(sqlId,offset,limit,conditions);
		ish.dispose();
		return table;
	}

	public DataTable queryData(String sqlId, int pageSize, int pageNum, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		DataTable table = null;
		if(pageSize>0 && pageNum > 0){
			int limit = PageSizeHelper.getLimit(pageSize, pageNum);
			int offset = PageSizeHelper.getOffset(pageSize, pageNum);
			table = ish.getDataTable(sqlId,offset,limit,conditions);
		}
		else{
			table = ish.getDataTable(sqlId,conditions);
		}
		ish.dispose();
		return table;
	}
	
	public DataTable queryData(String sqlId,int top,Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		DataTable table = null;
		if(top>0){
			table = ish.getDataTable(sqlId,top,conditions);
		}
		else{
			table = ish.getDataTable(sqlId,conditions);
		}
		ish.dispose();
		return table;
	}
	
	/**
	 * 查询返回第一行数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Map<String,Object> queryMap(String sqlId, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		Map<String,Object> map = ish.getMap(sqlId,conditions);
		return map;
	}
	
	/**
	 * 查询返回MapList数据
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<Map<String,Object>> queryMapList(String sqlId, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		List<Map<String,Object>> list = ish.getMapList(sqlId,conditions);
		return list;
	}
	
	/**
	 * 执行一条Sql
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public int execute(String sqlId, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		int result = ish.executeNoneQuery(sqlId,conditions).getRowFirst();
		ish.dispose();
		return result;
	}
	
	/**
	 * 批量执行Sql
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public int[] executes(String sqlId, List<?> conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		int[] result = ish.executeNoneQuerys(sqlId,conditions).getResultRow();
		ish.dispose();
		return result;
	}
	
	/**
	 * 查询值
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryValue(String sqlId, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		Object result = ish.queryValue(sqlId,conditions);
		ish.dispose();
		return result;
	}
	
	/**
	 * 查询值,第一行，第一列
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Object queryFirstValue(String sqlId, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		Object result = ish.queryFirstValue(sqlId,conditions);
		ish.dispose();
		return result;
	}
	
	/**
	 * 查询整型值
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Integer queryInt(String sqlId, Object ... conditions){
		Object result = this.queryValue(sqlId, conditions);
		if(result!=null){
			return (Integer)TypeUtil.changeType(result,Integer.class);
		}
		return null;
	}
	
	/**
	 * 取第一行，第一列
	 * @return
	 */
	public Integer queryFirstInt(String sqlId, Object ... conditions){
		Object result = this.queryFirstValue(sqlId, conditions);
		if(result!=null){
			return (Integer)TypeUtil.changeType(result,Integer.class);
		}
		return null;
	}
	
	/**
	 * 查询长整型值
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public Long queryLong(String sqlId, Object ... conditions){
		Object result = this.queryValue(sqlId, conditions);
		if(result!=null){
			return (Long)TypeUtil.changeType(result,Long.class);
		}
		return null;
	}
	
	/**
	 * 查询值列表
	 * @param sqlId
	 * @param conditions
	 * @return
	 */
	public List<?> queryValueList(String sqlId, Object ... conditions){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		List<?> result = ish.queryValueList(sqlId,conditions);
		ish.dispose();
		return result;
	}
	
	/**
	 * 更新数据
	 * @param datatable
	 * @param pkey
	 */
	public void updateData(DataTable datatable, String pkey){
		saveDataTable(datatable,pkey,true);
	}
	
	/**
	 * 新增数据
	 * @param datatable
	 * @param pkey
	 */
	public void insertData(DataTable datatable, String pkey){
		saveDataTable(datatable,pkey,false);
	}
	
	/**
	 * 
	 * @param datatable
	 * @param pkey	主键字段名
	 */
	private void saveDataTable(DataTable datatable,String pkey,boolean updateFlag){
		List<SqlString> sqlStrings = new ArrayList<SqlString>();
		
		List<String> columnNames = new ArrayList<String>();
		List<Object> columnValues = new ArrayList<Object>();
		String tableName = datatable.getName();
		
		for(int i=0;i<datatable.rowSize();i++){
			
			DataRow row = datatable.getRow(i);
			Object pkVal = row.get(pkey);
			
			for(int j=0;j<datatable.colSize();j++){
				String columnName = datatable.getColumn(j).getName();
				if(!pkey.equals(columnName)){
					Object val = row.get(j);
					if(val!=null){
						columnNames.add(columnName);
						columnValues.add(val);
					}
				}
			}

			if(updateFlag){
				SqlString sqlString = buildUpdateSqlString(tableName, pkey, pkVal, columnNames.toArray(new String[0]), columnValues.toArray(new Object[0]));
				if (sqlString != null) {
					sqlStrings.add(sqlString);
				}
			}else{
				SqlString sqlString = buildInsertSqlString(tableName, pkey, pkVal, columnNames.toArray(new String[0]), columnValues.toArray(new Object[0]));
				sqlStrings.add(sqlString);
			}
			
			columnNames.clear();
			columnValues.clear();
			
		}
		
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		
		for(SqlString sqlString : sqlStrings){
			ish.executeNoneQuery(sqlString);
		}
		
		ish.dispose();
	}

	public SqlString buildInsertSqlString(String tableName,String pkName,Object pkValue,String[] columnNames,Object[] columnValues){
		SqlStringBuilder sqlBuilder = new SqlStringBuilder();
		StringBuilder columnStr = new StringBuilder();
		for(int i=0;i<columnNames.length;i++){
			columnStr.append(",");
			columnStr.append(columnNames[i]);
		}
		sqlBuilder.append("INSERT INTO "+tableName+" (");
		sqlBuilder.append(pkName);
		sqlBuilder.append(columnStr.toString());
		sqlBuilder.append(") VALUES (");
		sqlBuilder.append(new Parameter(pkValue));
		for(int i=0;i<columnValues.length;i++){
			String columnName = columnNames[i];
			Object columnValue = columnValues[i];
			Object dataValue = convertColumnValueType(tableName,columnName,columnValue);
			sqlBuilder.append(",");
			if(dataValue!=null){
				sqlBuilder.append(new Parameter(dataValue));
			}else{
				sqlBuilder.append("NULL");
			}
		}
		sqlBuilder.append(")");
		return sqlBuilder.toSqlString();
	}

	public SqlString buildUpdateSqlString(String tableName,String pkName,Object pkValue,String[] columnNames,Object[] columnValues){
		SqlStringBuilder sqlBuilder = new SqlStringBuilder();
		sqlBuilder.append("UPDATE "+tableName+" SET ");
		boolean hasParams = false;
		for(int i=0;i<columnValues.length;i++){
			hasParams = true;
			String columnName = columnNames[i];
			Object columnValue = columnValues[i];
			Object dataValue = convertColumnValueType(tableName,columnName,columnValue);
			if(i>0) {
				sqlBuilder.append(",");
			}
			sqlBuilder.append(columnName+"=");
			if(dataValue!=null){
				sqlBuilder.append(new Parameter(dataValue));
			}else{
				sqlBuilder.append("NULL");
			}
		}
		boolean pkIsStr = pkValue instanceof String;
		if(pkIsStr){
			sqlBuilder.append(" WHERE "+pkName+"='"+pkValue+"'");
		}else{
			sqlBuilder.append(" WHERE "+pkName+"="+pkValue);
		}
		//不处理可能出现 错误sql 如：UPDATE LBIZ_CREDI_HISTORY SET  WHERE ID=59
		if (!hasParams) {
			return null;
		}
		return sqlBuilder.toSqlString();
	}

	/**
	 * 新增数据,主键已有值，不需要生成
	 * @param datatable
	 */
	public void insertData(DataTable datatable){
		List<SqlString> sqlStrings = new ArrayList<SqlString>();

		List<String> columnNames = new ArrayList<String>();
		List<Object> columnValues = new ArrayList<Object>();
		String tableName = datatable.getName();

		for(int i=0;i<datatable.rowSize();i++){

			DataRow row = datatable.getRow(i);

			for(int j=0;j<datatable.colSize();j++){
				String columnName = datatable.getColumn(j).getName();
				Object val = row.get(j);
				columnNames.add(columnName);
				columnValues.add(val);
			}

			SqlString sqlString = buildInsertSqlString(tableName,columnNames.toArray(new String[0]), columnValues.toArray(new Object[0]));
			sqlStrings.add(sqlString);

			columnNames.clear();
			columnValues.clear();

		}

		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);

		for(SqlString sqlString : sqlStrings){
			ish.executeNoneQuery(sqlString);
		}

		ish.dispose();
	}

	public SqlString buildInsertSqlString(String tableName,String[] columnNames,Object[] columnValues){
		SqlStringBuilder sqlBuilder = new SqlStringBuilder();
		StringBuilder columnStr = new StringBuilder();
		for(int i=0;i<columnNames.length;i++){
			if(i>0)columnStr.append(",");
			columnStr.append(columnNames[i]);
		}
		sqlBuilder.append("INSERT INTO "+tableName+" (");
		sqlBuilder.append(columnStr.toString());
		sqlBuilder.append(") VALUES (");
		for(int i=0;i<columnValues.length;i++){
			String columnName = columnNames[i];
			Object columnValue = columnValues[i];
			Object dataValue = convertColumnValueType(tableName,columnName,columnValue);
			if(i>0)sqlBuilder.append(",");
			if(dataValue!=null){
				sqlBuilder.append(new Parameter(dataValue));
			}else{
				sqlBuilder.append("NULL");
			}
		}
		sqlBuilder.append(")");
		return sqlBuilder.toSqlString();
	}

	
	/**
	 * 转化数据库的值的数据类型
	 * @param tableName
	 * @param columnName
	 * @param value
	 * @return
	 */

	private Object convertColumnValueType(String tableName,String columnName,Object value){
		return convertColumnValueType(tableName, columnName, value, false);
	}
	private Object convertColumnValueType(String tableName,String columnName,Object value,boolean updateFlag){
		Column column = getDbMetaStore().getColumn(tableName, columnName);
		if(column!=null){
			Class<?> clazz = column.getClassType();
			if(Integer.class.isAssignableFrom(clazz)){
				if( !"".equals(value) && value!=null){
					Object changeValue = TypeUtil.changeType(value, Integer.class);
					return changeValue;
				}else{
					return null;
				}
			}else if(BigDecimal.class.isAssignableFrom(clazz)){
				if(!"".equals(value) && value!=null){
					Object changeValue = TypeUtil.changeType(value, BigDecimal.class);
					return changeValue;
				}else{
					return null;
				}
			}else if(Float.class.isAssignableFrom(clazz)){
				if(!"".equals(value) && value!=null){
					Object changeValue = TypeUtil.changeType(value, Float.class);
					return changeValue;
				}else{
					return null;
				}
			}
			else if(Double.class.isAssignableFrom(clazz)){
				if(!"".equals(value) && value!=null){
					Object changeValue = TypeUtil.changeType(value, Double.class);
					return changeValue;
				}else{
					return null;
				}
			}
			else if(Number.class.isAssignableFrom(clazz)){
				if(!"".equals(value) && value!=null){
					Object changeValue = TypeUtil.changeType(value, Number.class);
					return changeValue;
				}else{
					return null;
				}
			}
			else if(Date.class.isAssignableFrom(clazz)){
				if(!"".equals(value) && value!=null){
					Object changeValue = TypeUtil.changeType(value, Date.class);
					return changeValue;
				}else{
					return null;
				}
			}
		}
		return value;
	}

}
