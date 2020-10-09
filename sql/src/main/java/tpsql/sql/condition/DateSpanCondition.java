package tpsql.sql.condition;

import tpsql.util.StringUtil;
import tpsql.util.StringUtil;

public class DateSpanCondition implements ISqlCondition {

	@Override
	public String getCondition(Object data, Args args) {
		String property = args.getProperty();
		String propertyEnd = property+"End";
		String column = args.getColumn();
		String alias = (StringUtil.isNotEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
		return "<clause prepend=\"and\" assert=\"!isNull(["+property+"])\" > <![CDATA["+alias+"\""+column+"\" >= ]]><method name=\"beginDate\">["+property+"]</method> </clause>"
				+"<clause prepend=\"and\" assert=\"!isNull(["+propertyEnd+"])\" > <![CDATA["+alias+"\""+column+"\" < ]]><method name=\"endDate\">["+propertyEnd+"]</method> </clause>";
	}
	
}
