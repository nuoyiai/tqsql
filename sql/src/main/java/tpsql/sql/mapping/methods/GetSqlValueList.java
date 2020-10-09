package tpsql.sql.mapping.methods;

import tpsql.sql.builder.SqlString;
import tpsql.sql.util.ISqlEngine;
import tpsql.sql.util.SqlEngine;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import tpsql.sql.util.ISqlEngine;
import tpsql.sql.util.SqlEngine;
import tpsql.sql.builder.SqlString;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import tpsql.sql.util.ISqlEngine;
import tpsql.sql.util.SqlEngine;

import java.util.List;

public class GetSqlValueList implements ISqlMethod {

    @Override
    public String getName() {
        return "getSqlValueList";
    }

    @Override
    public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
        ISqlEngine ish = SqlEngine.instance(config.getName());
        if (args.length > 0) {
            String sqlId = (String) args[0];
            Object[] sqlArgs = new Object[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                sqlArgs[i - 1] = args[i];
            }
            List<?> valList = ish.queryValueList(sqlId, sqlArgs);
            if (valList != null && valList.size()>0) {
                return new SqlString(valList);
            }
        }
        return null;
    }
}
