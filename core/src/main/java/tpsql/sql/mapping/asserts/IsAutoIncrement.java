package tpsql.sql.mapping.asserts;

import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlClauseAssert;
import org.springframework.stereotype.Component;

@Component
public class IsAutoIncrement implements ISqlClauseAssert {

    @Override
    public String getName() {
        return "isAutoIncrement";
    }

    @Override
    public boolean invoke(IDialect dialect, Object... args) {
        return true;
    }

}
