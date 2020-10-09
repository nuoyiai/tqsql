package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;

/**
 * �ж�Ϊ�ջ���ַ�
 */
public class IsNullOrEmpty implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isNullOrEmpty";
	}

	@Override
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
