package tpsql.sql.mapping.methods;

import tpsql.sql.builder.SqlString;
import tpsql.sql.util.ISqlEngine;
import tpsql.sql.util.SqlEngine;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import org.springframework.stereotype.Component;

@Component
public class GetSqlValue implements ISqlMethod {

	public String getName() {
		return "getSqlValue";
	}

	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		ISqlEngine ish = SqlEngine.instance(config.getName());
		if(args.length>0){
			String sqlId = (String)args[0];
			Object[] sqlArgs = new Object[args.length-1];
			for(int i=1;i<args.length;i++) {
				sqlArgs[i - 1] = args[i];
			}
			Object val = ish.queryValue(sqlId, sqlArgs);
			if(val!=null) {
				return new SqlString(val.toString());
			}
		}
		return null;
	}
	

}
