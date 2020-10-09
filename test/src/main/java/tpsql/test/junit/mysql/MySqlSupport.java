package tpsql.test.junit.mysql;

import org.junit.Before;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import tpsql.spring.SpringContext;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.config.IDbConfigFactory;
import tpsql.test.util.DbConfigUtil;

public class MySqlSupport {

    @Before
    public void init(){
        new FileSystemXmlApplicationContext(new String[] {"classpath:/spring/tpsql.test.xml"});
        IDbConfigFactory dbConfigFactory = (IDbConfigFactory)SpringContext.instance().get("DbConfigFactory");
        IDbConfig dbConfig = DbConfigUtil.getDbConfig("mysql");
        dbConfig.setDefault(true);
        dbConfigFactory.add(dbConfig);
    }

}
