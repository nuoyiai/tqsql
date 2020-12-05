package tpsql.sql.condition;

import tpsql.core.util.StringUtil;
import org.springframework.stereotype.Component;

/**
 * 左模糊查询
 */
@Component
public class FuzzyLeftCondition implements ISqlCondition  {

	public String getCondition(Object data, Args args) {
		String property = args.getProperty();
		String column = args.getColumn();
		String alias = (StringUtil.isNotEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
		return "<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" >"+alias+"\""+column+"\" like '%["+property+"]%' </clause>";
	}
	
}
