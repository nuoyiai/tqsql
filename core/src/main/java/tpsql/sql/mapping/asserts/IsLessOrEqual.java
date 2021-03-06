package tpsql.sql.mapping.asserts;

import tpsql.sql.SqlMapException;
import tpsql.core.util.StringUtil;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

/**
 * 判断是否小于或等于
 */
@Component
public class IsLessOrEqual implements ISqlClauseAssert {

	public String getName() {
		return "isLessOrEqual";
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
