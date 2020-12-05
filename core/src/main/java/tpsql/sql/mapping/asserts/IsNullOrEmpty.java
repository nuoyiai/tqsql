package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

/**
 * �ж�Ϊ�ջ���ַ�
 */
@Component
public class IsNullOrEmpty implements ISqlClauseAssert {

	public String getName() {
		return "isNullOrEmpty";
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
