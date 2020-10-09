package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;

/**
 * Created by zhusw on 2017/9/12.
 */
public class IsAllNotEmpty implements ISqlClauseAssert {

    @Override
    public String getName() {
        return "isAllNotEmpty";
    }

    @Override
    public boolean invoke(IDialect dialect, Object... args) {
        if(args.length==0) {
			return true;
		}
        for(int i=0;i<args.length;i++){
            Object arg = args[i];
            if(arg==null || "".equals(arg)){
                return false;
            }
        }
        return true;
    }
}
