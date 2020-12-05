package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/14.
 */
@Component
public class IsNotEmpty implements ISqlClauseAssert {

    public String getName() {
        return "isNotEmpty";
    }

    public boolean invoke(IDialect dialect, Object... args) {
        if(args.length==0) {
			return false;
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
