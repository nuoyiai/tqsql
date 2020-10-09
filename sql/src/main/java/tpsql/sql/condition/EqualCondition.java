package tpsql.sql.condition;

import tpsql.util.StringUtil;
import tpsql.util.StringUtil;

public class EqualCondition implements ISqlCondition {

	@Override
	public String getCondition(Object data, Args args) {
		String property = args.getProperty();
		String column = args.getColumn();
		String alias = (StringUtil.isNotEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
		String quote = ("string".equalsIgnoreCase(args.getValueType()))?"'":"";
		String nullAssert = (quote.length()>0)?"isNullOrEmpty":"isNull";
		return "<clause prepend=\"and\" assert=\"!"+nullAssert+"(["+property+"])\" >"+alias+"\""+column+"\" = "+quote+"["+property+"]"+quote+" </clause>";
	}
	
}
