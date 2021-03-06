package tpsql.sql.mapping.asserts;

import tpsql.sql.SqlMapException;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import tpsql.core.util.StringUtil;
import org.springframework.stereotype.Component;

/**
 * 判断是否小于
 */
@Component
public class IsLess implements ISqlClauseAssert {

	public String getName() {
		return "isLess";
	}

	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length>1)
		{
			return false;
		}
		else {
			throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}", this.getName(), args.length));
		}
	}

}
