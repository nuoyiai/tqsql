package tpsql.sql.mapping.methods;

import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.util.SqlType;
import tpsql.util.TypeUtil;
import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.util.SqlType;
import tpsql.util.TypeUtil;

public class RLike extends Like {
	@Override
	public String getName() {
		return "rlike";
	}

	@Override
	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		if(args.length>0)
		{
			Object arg = args[0];
			if(arg!=null)
			{
				if(arg instanceof Parameter)
				{
					String val = (String)TypeUtil.changeType(((Parameter)arg).getValue(),String.class);
					return new SqlString(new Object[]{" like ",new Parameter(val+"%")} );
				}
				else
				{
					String val = SqlType.toSqlString(arg);
					if(val.charAt(val.length()-1)=='\'')
					{
						val = val.substring(0,val.length()-1)+"%'";
					}
					else {
						val = "'"+val+"%'";
					}
					return new SqlString(" like "+val);
				}
			}
		}
		return null;
	}
}
