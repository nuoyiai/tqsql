package tpsql.sql.mapping.methods;

import java.util.Date;

import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.core.util.DateUtil;
import tpsql.core.util.TypeUtil;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import org.springframework.stereotype.Component;

@Component
public class BeginDate implements ISqlMethod {

	public String getName() {
		return "beginDate";
	}

	public SqlString invoke(IDbConfig dbConfig, IDialect dialect, Object... args) {
		if(args.length>0){
			Object arg = args[0];
			if(arg!=null){
				boolean paramFlag = arg instanceof Parameter;
				Object date = TypeUtil.changeType((paramFlag)?((Parameter)arg).getValue():arg,Date.class);
				if(date instanceof Date){
					Date val = DateUtil.clearTime((Date)date);
					if(paramFlag){
						return (SqlString)dialect.toDate(new Parameter(val));
					}else{
						return new SqlString(dialect.toDate(val));
					}
				}
			}
		}
		return null;
	}
}
