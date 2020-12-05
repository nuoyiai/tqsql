package tpsql.sql.dialect;

import tpsql.sql.builder.SqlString;
import tpsql.sql.command.ValueType;
import tpsql.core.util.StringUtil;

/**
 * SqlLite数据库方言
 */
public class SqliteDialect extends AbstractDialect {

	
	public  boolean supportsLimit()
	{
		return true;
	}

	
	public boolean supportsLimitOffset()
	{
		return true;
	}

	/** 对查询前几条数据 */
	
	public String getTopString(String querySqlString, int limit){
		return querySqlString + " limit 0,"+limit+" ";
	}

	/** 对查询前几条数据 */
	
	public SqlString getTopString(SqlString querySqlString, int limit){
		return querySqlString.contact(" limit 0,"+limit+" ");
	}

	/** 对Sql进行分页包装 */
	
	public  String getLimitString(String querySqlString, int offset, int limit)
	{
		String sizeSql = "";
		if (offset > 0)
		{
			sizeSql = StringUtil.format("{0} limit {1},{2}", querySqlString,offset,limit - offset);
		}
		else
		{
			sizeSql = StringUtil.format("{0} limit 0,{1}",querySqlString,limit);
		}
		return sizeSql;
	}

	/** 对Sql进行分页包装 */
	
	public  SqlString getLimitString(SqlString querySqlString, int offset, int limit)
	{
		if (offset > 0)
		{
			return querySqlString.contact(new SqlString(StringUtil.format(" limit {0},{1}",offset,limit - offset)));
		}
		else
		{
			return querySqlString.contact(new SqlString(StringUtil.format(" limit {0}",limit)));
		}
	}
	
	/** 当前日期 */
    
	public String getCurrentDate(){
    	return "";
    }
    
    /** 当前日期时间 */
    
	public String getCurrentDateTime(){
    	return "";
    }
    
    /** 当前时间 */
    
	public String getCurrentTime(){
    	return "";
    }
    
	
	public IMetaSql getMetaSql() {
		// TODO Auto-generated method stub
		return new SqliteMeta();
	}

	
	public String getDbType() {
		return "SqlLite";
	}

	class SqliteMeta extends BaseSqlMeta
	{

		
		public String[] getCreateSequenceSql(String sequenceName, Long value) {
			return new String[0];
		}

		
		public String getSequencesSql() {
			return null;
		}

		
		public String getSequenceSql(String sequenceName) {
			return null;
		}

		
		public String getSequenceNextSql(String sequenceName) {
			return null;
		}

		
		public String getSequenceNextSql(String sequenceName, int num) {
			return null;
		}

		
		public String getDropSequenceSql(String sequenceName) {
			return null;
		}

		
		public String getTablesSql() {
			return "SELECT NAME AS TABLE_NAME,'' AS TABLE_REMARK,'' AS TABLE_SCHEMA FROM SQLITE_MASTER WHERE TYPE = 'table'";
		}

		
		public String getTablesSql(String tableName) {
			return null;
		}

		
		public String getTablesSql(String tableName, String schemaName) {
			return null;
		}

		
		public String getCreateTableSql(String tableName, String pk, String[] fks) {
			return null;
		}

		
		public String getFieldsSql(String tableName) {
			return null;
		}

		
		public String getRenameTableColumnSql(String tableName, String oldColumnName, String newColumnName, String fieldType) {
			return null;
		}

		
		public String getAddTableColumnSql(String tableName, String columnName, Integer length, Integer scale, ValueType type) {
			return null;
		}

		
		public String getDeteleTableColumnSql(String tableName, String columnName) {
			return null;
		}

		
		public String getCommentTableColumnSql(String tableName, String columnName, String comments) {
			return null;
		}

		
		public String getFunctionsSql() {
			return null;
		}

		
		public String getFunctionsSql(String functionName) {
			return null;
		}

		
		public ValueType getType(int fieldType) {
			return null;
		}
	}

}
