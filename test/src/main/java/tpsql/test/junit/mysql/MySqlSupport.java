package tpsql.test.junit.mysql;

import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.config.IDbConfigFactory;
import tpsql.test.util.DbConfigUtil;
import tpsql.core.spring.SpringContext;
import tpsql.test.spring.*;


public class MySqlSupport {

    @Before
    public void init(){
        new AnnotationConfigApplicationContext(SpringConfig.class);
        IDbConfigFactory dbConfigFactory = (IDbConfigFactory)SpringContext.instance().get(IDbConfigFactory.class);
        IDbConfig dbConfig = DbConfigUtil.getDbConfig("mysql");
        dbConfig.setDefault(true);
        dbConfigFactory.add(dbConfig);
    }

}
