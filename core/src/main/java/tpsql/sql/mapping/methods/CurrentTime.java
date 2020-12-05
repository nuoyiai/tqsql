package tpsql.sql.mapping.methods;

import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import org.springframework.stereotype.Component;

@Component
public class CurrentTime implements ISqlMethod {

	public String getName() {
		return "currentTime";
	}

	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		return new SqlString(dialect.getCurrentTime());
	}

}
