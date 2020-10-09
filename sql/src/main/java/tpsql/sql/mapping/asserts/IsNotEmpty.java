package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;

/**
 * Created by Administrator on 2017/7/14.
 */
public class IsNotEmpty implements ISqlClauseAssert {
    @Override
    public String getName() {
        return "isNotEmpty";
    }

    @Override
    public boolean invoke(IDialect dialect, Object... args) {
        if(args.length==0) {
			return true;
		}
        for(int i=0;i<args.length;i++){
            Object arg = args[i];
            if(arg!=null && !"".equals(arg)){
                return true;
            }
        }
        return false;
    }
}
