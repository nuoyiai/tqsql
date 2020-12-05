package tpsql.sql.mapping.methods;

import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.sql.util.SqlType;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.core.util.TypeUtil;
import org.springframework.stereotype.Component;

@Component
public class LLike extends Like {

	public String getName() {
		return "llike";
	}

	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		if(args.length>0)
		{
			Object arg = args[0];
			if(arg!=null)
			{
				if(arg instanceof Parameter)
				{
					String val = (String)TypeUtil.changeType(((Parameter)arg).getValue(),String.class);
					return new SqlString(new Object[]{" like ",new Parameter("%"+val)} );
				}
				else
				{
					String val = SqlType.toSqlString(arg);
					if(val.charAt(0)=='\'')
					{
						val = "'%"+val.substring(1,val.length()-1);
					}
					else {
						val = "'%"+val+"'";
					}
					return new SqlString(" like "+val);
				}
			}
		}
		return null;
	}
}
