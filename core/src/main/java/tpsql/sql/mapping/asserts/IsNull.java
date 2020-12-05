package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

/**
 * �ж�Ϊ��
 */
@Component
public class IsNull implements ISqlClauseAssert {

	public String getName() {
		return "isNull";
	}

	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length==0) {
			return true;
		}
		return args[0]==null;
	}

}
