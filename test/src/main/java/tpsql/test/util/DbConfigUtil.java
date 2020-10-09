package tpsql.test.util;

import tpsql.sql.config.DriverDbConfig;

import java.util.Map;

public class DbConfigUtil {

    public static DriverDbConfig getDbConfig(String dbType){
        Map map = PropertiesUtil.loadPropertyMapByResource("/dbconfig/"+dbType+".properties");
        String url = (String)map.get("jdbc.url");
        String driverClassName = (String)map.get("jdbc.driverClassName");
        String username = (String)map.get("jdbc.username");
        String password = (String)map.get("jdbc.password");
        String dialectClass = getDialectClassByDbType(dbType);
        DriverDbConfig dbConfig = new DriverDbConfig();
        dbConfig.setDbType(dbType);
        dbConfig.setDialectClass(dialectClass);
        dbConfig.setUrl(url);
        dbConfig.setDriverClass(driverClassName);
        dbConfig.setUsername(username);
        dbConfig.setPassword(password);
        return dbConfig;
    }

    protected static String getDialectClassByDbType(String dbType){
        if(dbType.contains("mysql"))
            return "tpsql.sql.dialect.MySqlDialect";
        else if(dbType.contains("db2"))
            return "tpsql.sql.dialect.DB2Dialect";
        else if(dbType.contains("oracle"))
            return "tpsql.sql.dialect.OracleDialect";
        else if(dbType.contains("sqlite"))
            return "tpsql.sql.dialect.SqliteDialect";
        return "";
    }

}
