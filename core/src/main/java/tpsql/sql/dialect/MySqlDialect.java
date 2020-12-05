package tpsql.sql.dialect;

import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.util.SqlType;
import tpsql.core.util.StringUtil;
import tpsql.sql.command.ValueType;
import tpsql.core.util.DateUtil;

import java.util.Date;

/**
 * mysql数据库方言
 */
public class MySqlDialect extends AbstractDialect {
	
	public MySqlDialect()
	{
		super();
		dateFormat = "%Y-%m-%d";
		timestampForamt = "%Y-%m-%d %H:%i:%s.%f";
		dateTimeFormat = "%Y-%m-%d %H:%i:%s";
	}
	
	
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
    
    /** 日期格式化 */
	
	public Object toDate(Object date){
		if(date instanceof Parameter){
			Parameter p = (Parameter)date;
			String format = DateUtil.hasTime((Date)p.getValue())?dateTimeFormat:dateFormat;
			SqlString part1 = new SqlString("str_to_date(");
			SqlString part2 = part1.contact(new SqlString(p));
			SqlString part3 = part2.contact(new SqlString(",'"+format+"')"));
			return part3;
		}else if(date instanceof Date){
			Date d = (Date)date;
			String format = DateUtil.hasTime(d)?dateTimeFormat:dateFormat;
			String dateStr = SqlType.toSqlString(d);
			if(dateStr.indexOf(".")>-1){
				return StringUtil.format("str_to_date('{0}','{1}')",dateStr,timestampForamt);
			}else{
				return StringUtil.format("str_to_date('{0}','{1}')",dateStr,format);
			}
		}else if(date instanceof String){
			String dateStr = (String)date;
			if(dateStr.indexOf(":")>-1){
				return StringUtil.format("str_to_date('{0}','{1}')",dateStr,dateTimeFormat);
			}else{
				return StringUtil.format("str_to_date('{0}','{1}')",dateStr,timestampForamt);
			}
		}
		return null;
	}
    
    /** 日期格式化 */
	
	public Object toChar(Object date){
		if(date instanceof Parameter){
			Parameter p = (Parameter)date;
			String format = DateUtil.hasTime((Date)p.getValue())?dateTimeFormat:dateFormat;
			SqlString part1 = new SqlString("date_format(");
			SqlString part2 = part1.contact(new SqlString(p));
			SqlString part3 = part2.contact(new SqlString(",'"+format+"')"));
			return part3;
		}else if(date instanceof Date){
			Date d = (Date)date;
			String format = DateUtil.hasTime(d)?dateTimeFormat:dateFormat;
			String dateStr = SqlType.toSqlString(d);
			return StringUtil.format("date_format({0},'{1}')",dateStr,format);
		}else if(date instanceof String){
			String dateStr = (String)date;
			String format = (dateStr.indexOf(":")>-1)?dateTimeFormat:dateFormat;
			return StringUtil.format("date_format({0},'{1}')",dateStr,format);
		}
		return null;
	}
	
	/** 日期格式化 */
	
	public Object toChar(Object date, String format){
		if(date instanceof Parameter){
			Parameter p = (Parameter)date;
			SqlString part1 = new SqlString("date_format(");
			SqlString part2 = part1.contact(new SqlString(p));
			SqlString part3 = part2.contact(new SqlString(",'"+format+"')"));
			return part3;
		}else if(date instanceof Date){
			Date d = (Date)date;
			String dateStr = SqlType.toSqlString(d);
			return StringUtil.format("date_format({0},'{1}')",dateStr,format);
		}else if(date instanceof String){
			String dateStr = (String)date;
			return StringUtil.format("date_format({0},'{1}')",dateStr,format);
		}
		return null;
	}
    
    /** 当前日期 */
    
	public String getCurrentDate(){
    	return "CURRENT_DATE";
    }
    
    /** 当前日期时间 */
    
	public String getCurrentDateTime(){
    	return "NOW()";
    }
    
    /** 当前时间 */
    
	public String getCurrentTime(){
    	return "CURRENT_TIME";
    }

	
	public IMetaSql getMetaSql() {
		if(this.meta==null) {
			this.meta = new MySqlMeta();
		}
		return this.meta;
	}

	
	public String getDbType() {
		return "MySql";
	}

	class MySqlMeta extends BaseSqlMeta
	{

		
		public String[] getCreateSequenceSql(String sequenceName, Long value) {
			return null;
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
			return "SELECT TABLE_NAME,TABLE_COMMENT TABLE_REMARK,TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES "
					+" WHERE TABLE_SCHEMA NOT IN ('performance_schema','mysql','information_schema')";
		}

		
		public String getTablesSql(String tableName) {
			return "SELECT\n" +
					"\tTABLE_NAME,\n" +
					"\tTABLE_COMMENT TABLE_REMARK,\n" +
					"\tTABLE_SCHEMA \n"+
					"FROM\n" +
					"\tINFORMATION_SCHEMA.TABLES t\n" +
					"WHERE TABLE_NAME='"+tableName+"' AND \n" +
					"\tt.TABLE_SCHEMA NOT IN (\n" +
					"\t\t'performance_schema',\n" +
					"\t\t'mysql',\n" +
					"\t\t'information_schema'\n" +
					"\t)";
		}

		/**
		 * 得到数据库表的Sql语句
		 * @return
		 */
		public String getTablesSql(String tableName,String schemaName){
			return "SELECT TABLE_NAME,TABLE_COMMENT TABLE_REMARK,TABLE_SCHEMA "
					+" FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='"+tableName+"'"
					+" AND TABLE_SCHEMA = '"+schemaName+"'"; }
		
		
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

		
		public String getFieldsSql(String tableName) {
			String sqlStr = StringUtil.format("SELECT c.TABLE_NAME,c.COLUMN_NAME FIELD_NAME,c.DATA_TYPE FIELD_TYPE,\n" +
					"\tCASE\n" +
					"WHEN ISNULL(CHARACTER_MAXIMUM_LENGTH) THEN\n" +
					"\tNUMERIC_PRECISION\n" +
					"ELSE\n" +
					"\tCHARACTER_MAXIMUM_LENGTH\n" +
					"END FIELD_LENGTH,\n" +
					" c.NUMERIC_SCALE FIELD_SCALE,\n" +
					" '' FIELD_PRECISION,\n" +
					" CASE\n" +
					"WHEN c.IS_NULLABLE = 'YES' THEN\n" +
					"\t'Y'\n" +
					"ELSE\n" +
					"\t'N'\n" +
					"END NULLABLE,\n" +
					" CASE\n" +
					"WHEN c.COLUMN_KEY = 'PRI' THEN\n" +
					"\t'Y'\n" +
					"ELSE\n" +
					"\t'N'\n" +
					"END IS_PK,\n" +
					" c.COLUMN_COMMENT FIELD_REMARK\n" +
					"FROM\n" +
					"\tINFORMATION_SCHEMA. COLUMNS c\n" +
					"WHERE\n" +
					"\tc.TABLE_SCHEMA NOT IN (\n" +
					"\t\t'performance_schema',\n" +
					"\t\t'mysql',\n" +
					"\t\t'information_schema'\n" +
					"\t)\n" +
					"AND c.TABLE_NAME = '{0}'", new Object[] {
					tableName
			});
			return sqlStr;
		}

		
		public String getRenameTableColumnSql(String tableName, String oldColumnName, String newColumnName, String fieldType) {
			String columnType = "";
			if("Text".equals(fieldType)){
				columnType = getColumnType(90, 0, ValueType.Varchar);
			}else if("TextArea".equals(fieldType)){
				columnType = getColumnType(900, 0, ValueType.Varchar);
			}else if("Number".equals(fieldType)){
				columnType = getColumnType(20, 0, ValueType.Decimal);
			}else if("Date".equals(fieldType)){
				columnType = getColumnType(null, null, ValueType.TimeStamp);
			}else if("YesNo".equals(fieldType)){
				columnType = getColumnType(10, 0, ValueType.Varchar);
			}else if("Select".equals(fieldType)){
				columnType = getColumnType(100, 0, ValueType.Varchar);
			}else if("MultipleSelect".equals(fieldType)){
				columnType = getColumnType(300, 0, ValueType.Varchar);
			}else{
				throw new RuntimeException("生成添加数据库表字段SQL出错,没有实现该类型:"+fieldType);
			}
			return "ALTER TABLE " + tableName +" CHANGE " + oldColumnName +" "+ newColumnName +" "+columnType;
		}

		
		public String getAddTableColumnSql(String tableName, String columnName, Integer length, Integer scale, ValueType type) {
			return "ALTER TABLE "+tableName+" ADD "+columnName+" "+getColumnType(length, scale, type) ;
		}

		private String getColumnType(Integer length, Integer scale, ValueType type){
			switch (type) {
				case Varchar:
					return "varchar("+length+")";
				case Integer:
					return "bigint";
				case Date:
				case TimeStamp:
					return "timestamp null default null";
				case Numeric:
				case Decimal:
					return "decimal("+length+","+scale+")";
				case Double:
					return "double("+length+","+scale+")";
				default:
					throw new RuntimeException("生成添加数据库表字段SQL出错,没有实现该类型:"+type);
			}
		}

		
		public String getDeteleTableColumnSql(String tableName, String columnName) {
			return "ALTER TABLE "+tableName+" DROP "+columnName;
		}

		
		public String getCommentTableColumnSql(String tableName, String columnName, String comments) {
			//mysql ALTER table_name modify column_name tinyint(4) NOT NULL COMMENT 'xxxxxxxxxx' ; 我还要去查类型啊啊啊？？？？
			return "";
		}

		/**
		 * 得到函数
		 * @return
		 */
		public String getFunctionsSql(){
			return "SELECT ROUTINE_NAME,ROUTINE_SCHEMA,ROUTINE_TYPE FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_TYPE = 'FUNCTION'";
		}

		/**
		 * 得到函数信息
		 * @param functionName
		 * @return
		 */
		public String getFunctionsSql(String functionName){
			return "SELECT ROUTINE_NAME,ROUTINE_SCHEMA,ROUTINE_TYPE FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_TYPE = 'FUNCTION' AND ROUTINE_NAME = '"+functionName+"'";
		}

		
		public ValueType getType(int fieldType) {
			return null;
		}
	}
}
