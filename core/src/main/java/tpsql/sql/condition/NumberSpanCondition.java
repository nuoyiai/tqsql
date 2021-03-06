package tpsql.sql.condition;

import tpsql.core.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class NumberSpanCondition implements ISqlCondition {

	public String getCondition(Object data, Args args) {
		String property = args.getProperty();
		String propertyEnd = property+"End";
		String column = args.getColumn();
		String alias = (StringUtil.isNotEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
		return "<clause prepend=\"and\" assert=\"!isNull(["+property+"])\" > <![CDATA["+alias+"\""+column+"\" >= ]]>["+property+"] </clause>"
				+"<clause prepend=\"and\" assert=\"!isNull(["+propertyEnd+"])\" > <![CDATA["+alias+"\""+column+"\" <= ]]>["+propertyEnd+"] </clause>";
	}
	
}
