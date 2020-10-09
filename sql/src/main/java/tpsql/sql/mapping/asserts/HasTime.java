package tpsql.sql.mapping.asserts;

import java.util.Date;

import tpsql.util.DateUtil;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import tpsql.util.DateUtil;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;

public class HasTime implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "hasTime";
	}

	@Override
	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length>0)
		{
			Object arg = args[0];
			if(arg==null) {
				return false;
			} else
			{
				if(arg instanceof Number)
				{
					return ((Number)arg).longValue()>0;
				}
				else if(arg instanceof Date)
				{
					return DateUtil.hasTime((Date)arg);
				}
			}
		}
		return false;
	}

}
