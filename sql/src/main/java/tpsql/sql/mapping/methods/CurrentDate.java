package tpsql.sql.mapping.methods;

import tpsql.sql.builder.SqlString;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;

public class CurrentDate implements ISqlMethod {

	@Override
	public String getName() {
		return "currentDate";
	}

	@Override
	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		return new SqlString(dialect.getCurrentDate());
	}

}