package tpsql.sql.config;

import tpsql.sql.SqlDriverException;
import tpsql.core.util.StringUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

public class DbConfig implements IDbConfig {
	protected String dbType;					//数量库类型
	protected String name;					//名称
	protected String schema;					//数据库名
	protected DataSource dataSource;			//数据源
	protected String dialectClass;				//方言
	protected String driverClass;
	protected Map<String,String> dialects;		//
	protected Map<String,String> drivers;		//
	private Map<String,String> options;			
	protected boolean isDefault;				//
	
	@Override
	public String getDbType() {
		return dbType;
	}
	@Override
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public String getSchema() {
		return schema;
	}
	@Override
	public void setSchema(String schema) {
		this.schema = schema;
	}
	@Override
	public Connection getConnection() throws SQLException {
		if(this.dataSource!=null){
			return this.dataSource.getConnection();
		}else {
			throw new SqlDriverException("数据库连接未配置");
		}
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void setDialectClass(String dialectClass) {
		this.dialectClass = dialectClass;
	}
	public void setDialects(Map<String, String> dialects) {
		this.dialects = dialects;
	}
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	@Override
	public String getDialectClass() {
		if(StringUtil.isNotEmpty(this.dialectClass)) {
			return this.dialectClass;
		} else if(StringUtil.isNotEmpty(this.getDbType())){
			return getMapValueByKey(this.getDbType(),this.dialects);
		}
		return null;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public void setDrivers(Map<String, String> drivers) {
		this.drivers = drivers;
	}
	public String getDriverClass() {
		if(StringUtil.isNotEmpty(this.driverClass)) {
			return this.driverClass;
		} else if(StringUtil.isNotEmpty(this.getDbType())){
			return getMapValueByKey(this.getDbType(),this.drivers);
		}
		return null;
	}
	@Override
	public boolean isDefault() {
		return isDefault;
	}
	@Override
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public int compareTo(IDbConfig config) {
		return this.getName().compareTo(config.getName());
	}
	@Override
	public String getOption(String key){
		if(this.options!=null && this.options.containsKey(key)){
			return this.options.get(key);
		}
		return null;
	}

	/**
	 * 忽列大小写
	 * @param key
	 * @param map
	 * @return
	 */
	private String getMapValueByKey(String key,Map<String,String> map){
		if(map.containsKey(key)){
			return map.get(key);
		}else if(map.containsKey(key.toLowerCase())){
			return map.get(key.toLowerCase());
		}else if(map.containsKey(key.toUpperCase())){
			return map.get(key.toUpperCase());
		}
		return null;
	}
}
