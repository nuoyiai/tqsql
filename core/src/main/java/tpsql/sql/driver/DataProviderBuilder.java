package tpsql.sql.driver;

import tpsql.sql.config.IDbConfig;
import tpsql.sql.config.IDbConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 数据库操作组件供应构造器
 */
@Component
public class DataProviderBuilder implements IDataProviderBuilder {

	/** 数据库配置工厂 */
	@Autowired
	private IDbConfigFactory configFactory;
    
    /** 构造数据库组件供应对像 */
	public IDataProvider build()
    {
        IDbConfig config = this.configFactory.getConfig();
        return this.build(config);
    }

    /** 构造得到数据库组件供应对像 */
	public IDataProvider build(String configName)
    {
        IDbConfig config = this.configFactory.getConfig(configName);
        return this.build(config);
    }

    /**
     * 构造得到数据库组件供应对像
     */
	public IDataProvider build(IDbConfig config)
    {
        return new SqlDataProvider(config);
    }
	
}
