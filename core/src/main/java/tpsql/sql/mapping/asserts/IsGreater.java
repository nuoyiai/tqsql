package tpsql.sql.mapping.asserts;

import tpsql.sql.SqlMapException;
import tpsql.core.util.StringUtil;
import tpsql.core.util.TypeUtil;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

/**
 * 判断是否大于
 */
@Component
public class IsGreater implements ISqlClauseAssert {

	public String getName() {
		return "isGreater";
	}

	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length>1)
		{
			Object arg1 = args[0];
			Object arg2 = args[1];
			return TypeUtil.compareTo(arg1, arg2)==1;
		}
		else {
			throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}", this.getName(), args.length));
		}
	}

}
