package tpsql.sql.mapping.asserts;

import tpsql.sql.SqlMapException;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import tpsql.core.util.StringUtil;
import tpsql.core.util.TypeUtil;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by zhusw on 2017/9/13.
 */
@Component
public class HasValue implements ISqlClauseAssert {

    public String getName() {
        return "hasValue";
    }

    public boolean invoke(IDialect dialect, Object... args) {
        if(args.length>1){
            Object obj = args[0];
            if(obj!=null){
                for(int i=1;i<args.length;i++){
                    if(TypeUtil.isValue(obj)){
                        if(obj.equals(args[i])) {
							return true;
						}
                    }else if(TypeUtil.isArray(obj)){
                        for(Object v : (Object[]) obj){
                            if(v!=null && v.equals(args[i])) {
								return true;
							}
                        }
                    }else if(TypeUtil.isCollection(obj)){
                        for(Object v : (Collection<?>) obj){
                            if(v!=null && v.equals(args[i])) {
								return true;
							}
                        }
                    }
                    if(args[0].equals(args[i])) {
						return true;
					}
                }
            }
            return false;
        }
        else {
			throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}", this.getName(), args.length));
		}
    }

}
