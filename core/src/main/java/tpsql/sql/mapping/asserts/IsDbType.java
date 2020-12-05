package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/4.
 */
@Component
public class IsDbType implements ISqlClauseAssert {

    public String getName() {
        return "isDbType";
    }

    public boolean invoke(IDialect dialect, Object... args) {
        if(args.length>0){
            if(args[0]!=null && args[0] instanceof String){
                String dbType = dialect.getDbType();
                return dbType.equalsIgnoreCase((String)args[0]);
            }
        }
        return false;
    }
}
