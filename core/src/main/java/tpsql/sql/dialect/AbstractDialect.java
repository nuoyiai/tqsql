package tpsql.sql.dialect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.builder.SqlStringBuilder;
import tpsql.core.util.DateUtil;
import tpsql.core.util.StringUtil;

/**
 * 数据库方言基类
 */
public abstract class AbstractDialect implements IDialect {
	/** 数据库函数 */
	protected Map<String,String> sqlFunctions;
	/** 拼音查询,中文开始字符 */
    protected String[] pyStartChars;
    /** 拼音查询,中文结束字符 */
    protected String[] pyEndChars;
    
    protected final int SQLBUILDERSIZE = 32;
    
    protected IMetaSql meta;
	protected String dateFormat ="yyyy-MM-dd";
	protected String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	protected String timestampForamt = "yyyy-MM-dd HH:mm:ss.SSS";

    protected AbstractDialect()
    {
        this.sqlFunctions = new HashMap<String,String>();
        this.pyStartChars = null;
        this.pyEndChars = null;
    }

    /**
     * 数据库函数列表
     */
	public Map<String,String> getSqlFuns()
    {
        return this.sqlFunctions;
    }

    /** 得到拼音查询,中文开始字符 */
	public String[] getPYStartChars()
    {
        return this.pyStartChars;
    }
    
    /** 设置拼音查询,中文开始字符 */
    public void setPYStartChars(String[] startPYChars)
    {
    	this.pyStartChars = startPYChars;
    }

    /** 得到拼音查询,中文结束字符 */
	public String[] getPYEndChars()
    {
        return this.pyEndChars;
    }
    
    /** 设置拼音查询,中文结束字符 */
    public void setPYEndChars(String[] endPYChars)
    {
        this.pyEndChars = endPYChars;
    }
    
    /**
     * 是否需要开闭符
     */
	public boolean hasQuote()
    {
    	return false;
    }

    /** 开闭符,开头 */
	public char getOpenQuote()
    {
        return '"';
    }

    /** 开闭符,结尾 */
	public char getCloseQuote()
    {
        return '"';
    }

    /** 判断是否包函开闭符 */
	public boolean isQuoted(String name)
    {
        return (name.charAt(0) == this.getOpenQuote() && name.charAt(name.length() - 1) == this.getCloseQuote());
    }

    /** 解开闭符 */
	public String unQuote(String quoted)
    {
        String unquoted;

        if (this.isQuoted(quoted))
        {
            unquoted = quoted.substring(1);
        }
        else
        {
            unquoted = quoted;
        }

        unquoted = unquoted.replace(StringUtil.getString(this.getOpenQuote(),2), this.getOpenQuote()+"");

        if (this.getOpenQuote() != this.getCloseQuote())
        {
            unquoted = unquoted.replace(StringUtil.getString(this.getCloseQuote(),2), this.getCloseQuote()+"");
        }

        return unquoted;
    }

    /** 解开闭符 */
	public String[] unQuote(String[] quoted)
    {
    	String[] unquoted = new String[quoted.length];

        for (int i = 0; i < quoted.length; i++)
        {
            unquoted[i] = this.unQuote(quoted[i]);
        }

        return unquoted;
    }

    /** 加开闭符 */
	public String quote(String name)
    {
    	String quotedName = name.replace(this.getOpenQuote()+"", StringUtil.getString(this.getOpenQuote(), 2));

        if (this.getOpenQuote() != this.getCloseQuote())
        {
            quotedName = name.replace(this.getCloseQuote()+"", StringUtil.getString(this.getCloseQuote(), 2));
        }

        return this.getOpenQuote() + quotedName + this.getCloseQuote();
    }

    /** 是否支持Seguences */
	public boolean supportsSequences()
    {
        return false;
    }

    /** 支持索引列 */
	public boolean supportsIdentityColumns()
    {
        return false;
    }

    /** 是否支持分页Limit */
	public boolean supportsLimit()
    {
        return false;
    }

    /** 是否支持分页,limit和offser */
	public boolean supportsLimitOffset()
    {
        return supportsLimit();
    }

    /** 对Sql语句进去分页包装 */
	public String getLimitString(String querySqlString, int offset, int limit)
    {
        throw new RuntimeException("当前数据库不支持此分页功能");
    }
    
    /** 对Sql语句进去分页包装 */
	public SqlString getLimitString(SqlString querySqlString, int offset, int limit)
    {
        throw new RuntimeException("当前数据库不支持此分页功能");
    }

    /** 是否支持子查询 */
	public boolean supportsSubSelects()
    {
        return true;
    }

	public int getMaxAliasLength()
    {
        return 10;
    }

    /** 对名别名 */
	public String quoteForAliasName(String aliasName)
    {
        return isQuoted(aliasName) ?
        aliasName :
            quote(aliasName);
    }

    /** 给列名添加开闭合 */
	public String quoteForColumnName(String columnName)
    {
        return isQuoted(columnName) ?
        columnName :
            quote(columnName);
    }

    /** 给表名添加开闭符 */
	public String quoteForTableName(String tableName)
    {
        return isQuoted(tableName) ?
        tableName :
            quote(tableName);
    }

    /** 得到求总数的 */
	public String getCountSqlString(String sqlString)
    {
    	String sql = sqlString.replaceAll("order\\ {1,}by.*\\)|Order\\ {1,}By.*\\)|ORDER\\ {1,}BY.*\\)",")").replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*","");
    	return StringUtil.format("select count(*) from ({0}) row_", sql);
    }

    /** 把Sql包装为求总数的SqlString */
	public SqlString getCountSqlString(SqlString sqlString)
    {
    	SqlStringBuilder sb = new SqlStringBuilder(SQLBUILDERSIZE);
    	sb.append("select count(*) from (");
    	SqlString sql = sqlString.replaceAll("order\\ {1,}by.*\\)|Order\\ {1,}By.*\\)|ORDER\\ {1,}BY.*\\)",")").replaceAll("order\\ {1,}by.*|Order\\ {1,}By.*|ORDER\\ {1,}BY.*","");
    	sb.append(sql);
    	sb.append(") row_");
        return sb.toSqlString();
    }

    /** 得到参数标记 */
	public String getParamSign()
    {
        return "@";
    }

	/** 日期格式化 */

	public Object toDate(Object date)
	{
		if(date instanceof Parameter){
			return new SqlString((Parameter)date);
		}else if(date instanceof Date){
			String format = DateUtil.hasTime((Date)date)?dateTimeFormat:dateFormat;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return "'"+sdf.format((Date)date)+"'";
		}else if(date instanceof String){
			return "'"+date+"'";
		}
		return null;
	}
	
	/** 日期格式化 */

	public Object toChar(Object date){
		return date;
	}
	
	/** 日期格式化 */

	public Object toChar(Object date, String format){
		return date;
	}
	
    /** 得到翻译者 */
	public ITranslator getTranslator(){
    	return null;
    }

    /** 判断表是否存在 */

	public String getExistTableSql(String tableName)
    {
        return "";
    }

    public abstract class BaseSqlMeta implements IMetaSql {

        /**
         * 得到查询自增ID
         * @return
         */
        public String getAutoIncrementSql(){
            return "SELECT @@IDENTITY";
        }

    }
}
