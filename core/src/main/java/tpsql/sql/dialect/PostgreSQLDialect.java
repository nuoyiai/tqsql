package tpsql.sql.dialect;

import tpsql.sql.builder.SqlString;
import tpsql.core.util.StringUtil;
import tpsql.sql.command.ValueType;

/**
 * PostgreSql数据库方言
 */
public class PostgreSQLDialect extends AbstractDialect {
	public PostgreSQLDialect()
	{
    	super();
	}

	/** 开闭符,结尾 */
    
	public  char getCloseQuote()
	{
		return '"';
	}

    /** 开闭符，开头 */
    
	public char getOpenQuote()
	{
		return '"';
	}
    
    /**
     * 是否需要开闭符
     */
    
	public boolean hasQuote()
	{
		return true;
	}
    
	
	public boolean supportsSequences()
    {
        return true;
    }

    /** 是否支持分页Limit */
    
	public boolean supportsLimit()
	{
		return true;
	}

    /** 是否支持分页Limit和Offset */
    
	public boolean supportsLimitOffset()
	{
		return true;
	}
    
    /** 对查询前几条数据 */
    
	public String getTopString(String querySqlString, int limit){
    	return querySqlString + " limit "+limit+" ";
    }
    
    /** 对查询前几条数据 */
    
	public SqlString getTopString(SqlString querySqlString, int limit){
    	return querySqlString.contact(" limit "+limit+" ");
    }

    /** 对Sql进行分页包装 */
    
    public  String getLimitString(String querySqlString, int offset, int limit)
    {
        String sizeSql = "";
        if (offset > 0)
        {
            sizeSql = StringUtil.format("{0} limit {1} offset {2}", querySqlString, limit - offset, offset);
        }
        else
        {
            sizeSql = StringUtil.format("{0} limit {1}",querySqlString, limit);
        }
        return sizeSql;
    }
    
    /** 对Sql进行分页包装 */
    
    public  SqlString getLimitString(SqlString querySqlString, int offset, int limit)
    {
        if (offset > 0)
        {
            return querySqlString.contact(new SqlString(StringUtil.format(" limit {0} offset {1}",limit - offset, offset)));
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
		if(this.meta==null) {
			this.meta = new PgsqlMeta();
		}
		return this.meta;
	}

	
	public String getDbType() {
		return "PostgreSql";
	}

	class PgsqlMeta extends BaseSqlMeta
	{
		
		public String[] getCreateSequenceSql(String sequenceName, Long value) {
			return new String[]{StringUtil.format("CREATE SEQUENCE \"{0}\" START {1}",sequenceName,value)};
		}
		
		public String getSequencesSql() {
			return "SELECT sequence_name as SNAME,currval(Cast('\"'||sequence_name||'\"' as text)) as SVALUE FROM information_schema.sequences";
		}
		
		public String getSequenceSql(String sequenceName) {
			return StringUtil.format("SELECT sequence_name as SNAME,currval(Cast('\"'||sequence_name||'\"' as text)) as SVALUE FROM information_schema.sequences where sequence_name='{0}'",sequenceName);
		}
		
		public String getSequenceNextSql(String sequenceName) {
			return StringUtil.format("SELECT nextval('\"{0}\"')",sequenceName);
		}
		
		public String getSequenceNextSql(String sequenceName,int num) {
			return StringUtil.format("SELECT nextval('\"{0}\"')",sequenceName);
		}
		
		public String getDropSequenceSql(String sequenceName) {
			return StringUtil.format("DROP SEQUENCE \"{0}\"",sequenceName);
		}
		
		public String getTablesSql() {
			return null;
		}
		
		public String getTablesSql(String tableName) {
			return null;
		}
		
		public String getTablesSql(String tableName,String schemaName) {
			return null;
		}
		
		public String getCreateTableSql(String tableName,String pk,String[] exts){
			String pkStr = pk+" INTEGER NOT NULL,";
			String extStr = "";
			if(exts!=null && exts.length>0){
				for(String ext : exts){
					extStr+=ext+",";
				}
			}
			return "CREATE TABLE "+tableName+" ("+pkStr+extStr+"PRIMARY KEY ("+pk+"))";
		}
		
		public String getFieldsSql(String tableName){
			return null;
		}

		/**
		 * 修改表字段名
		 * @param tableName
		 * @param oldColumnName
		 * @param newColumnName
		 * @param fieldType
		 * @return
		 */
		
		public String getRenameTableColumnSql(String tableName, String oldColumnName, String newColumnName, String fieldType) {
			return null;
		}
		/**
		 * 添加数据库字段
		 * @param tableName
		 * @param columnName
		 * @param length
		 * @param type
		 * @return
		 */
		
		public String getAddTableColumnSql(String tableName,String columnName,Integer length,Integer scale,ValueType type){
			return null;
		}
		
		/**
		 * 修改例注释
		 * @param tableName
		 * @param oldColumnName
		 * @param newColumnName
		 * @return
		 */
		
		public String getCommentTableColumnSql(String tableName,String columnName,String comments){
			return null;
		}
		
		/**
		 * 删除数据库字段
		 * @return
		 */
		
		public String getDeteleTableColumnSql(String tableName,String columnName){
			return null;
		}

		/**
		 * 得到函数
		 * @return
		 */
		public String getFunctionsSql(){
			return "";
		}

		/**
		 * 得到函数信息
		 * @param functionName
		 * @return
		 */
		public String getFunctionsSql(String functionName){
			return "";
		}

		
		public ValueType getType(int fieldType){
			return null;
		}
	}
}
