package tpsql.sql.driver;

import tpsql.core.IDisposable;
import tpsql.sql.command.IDbCommand;
import tpsql.sql.command.IDbConnection;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;

/**
 * 数组库组件，供应接口
 */
public interface IDataProvider extends IDisposable {
	/**
	 * 得到数据库连接接口
	 * @return
	 */
	IDbConnection getConnection();

	/**
	 * 得到数据库命令接口
	 * @return
	 */
	IDbCommand getCommand();

	/**
	 * 得到数据库方言接口
	 * @return
	 */
    IDialect getDialect();

    /**
     * 得到数据库类型
     * @return
     */
    String getDBType();
    
    /**
     * 得到数据库配置
     * @return
     */
    IDbConfig getConfig();
}
