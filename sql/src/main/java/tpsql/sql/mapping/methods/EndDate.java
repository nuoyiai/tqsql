package tpsql.sql.mapping.methods;

import java.util.Date;

import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.util.DateUtil;
import tpsql.util.TypeUtil;
import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import tpsql.util.DateUtil;
import tpsql.util.TypeUtil;
import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;

public class EndDate implements ISqlMethod {

	@Override
	public String getName() {
		return "endDate";
	}

	@Override
	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		if(args.length>0)
		{
			Object arg = args[0];
			if(arg!=null)
			{
				boolean paramFlag = arg instanceof Parameter;
				Object date = TypeUtil.changeType((paramFlag)?((Parameter)arg).getValue():arg,Date.class);
				if(date instanceof Date)
				{
					Date val = DateUtil.addDay(DateUtil.clearTime((Date)date),1);
					if(paramFlag){
						return (SqlString)dialect.toDate(new Parameter(val));
					}
					else{
						return new SqlString(dialect.toDate(val));
					}
				}
			}
		}
		return null;
	}
}
