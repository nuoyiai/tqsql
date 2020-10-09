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

public class ToTimeSecond implements ISqlMethod {

	@Override
	public String getName() {
		return "toTimeSecond";
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
					Integer t = DateUtil.getTimeTotalSecond((Date)date);
					if(paramFlag)
					{
						return new SqlString(new Parameter(t));
					}
					else
					{
						return new SqlString(t.toString());
					}
				}
			}
		}
		return null;
	}
}