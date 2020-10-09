package tpsql.sql.condition;

import tpsql.util.StringUtil;
import tpsql.util.StringUtil;

/**
 * 左模糊查询
 */
public class FuzzyLeftCondition implements ISqlCondition  {

	@Override
	public String getCondition(Object data, Args args) {
		String property = args.getProperty();
		String column = args.getColumn();
		String alias = (StringUtil.isNotEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
		return "<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" >"+alias+"\""+column+"\" like '%["+property+"]%' </clause>";
	}
	
}
