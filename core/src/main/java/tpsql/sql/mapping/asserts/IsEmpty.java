package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

/**
 * 是否为空或空字符串
 */
@Component
public class IsEmpty implements ISqlClauseAssert {

	public String getName() {
		return "isEmpty";
	}

	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length==0) {
			return true;
		}
		Object arg = args[0];
		if(arg==null || "".equals(arg)){
			return true;
		}
		return false;
	}

}
