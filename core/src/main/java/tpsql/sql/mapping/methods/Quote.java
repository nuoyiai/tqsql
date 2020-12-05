package tpsql.sql.mapping.methods;

import tpsql.sql.builder.Parameter;
import tpsql.sql.builder.SqlString;
import tpsql.core.util.StringUtil;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.sql.mapping.ISqlMethod;
import org.springframework.stereotype.Component;

@Component
public class Quote implements ISqlMethod {

	public String getName() {
		return "quote";
	}

	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		Object arg = args[0];
		if(arg!=null){
			String quoteChar = "'";
			if(args.length>1 && args[1]!=null && "".equals(args[1])){
				quoteChar = args[1].toString();
			}
			if(arg instanceof Parameter){
				Parameter param = ((Parameter)arg);
				if(param.getValue() instanceof String){
					String str = (String)param.getValue();
					String quote = getQuoteString(str,quoteChar);
					param.setValue(quote);
				}
				return new SqlString(param);
			}else if(arg instanceof String){
				String str = (String)arg;
				String quote = getQuoteString(str,quoteChar);
				return new SqlString(quote);
			}
		}
		return null;
	}
	
	private String getQuoteString(String str,String quote){
		if(StringUtil.isNotEmpty(str)){
			String quoteStr = "";
			String[] parts = str.split(",",-1);
			for(int i=0;i<parts.length;i++){
				quoteStr+=("".equals(quoteStr))?quote+parts[i]+quote:","+quote+parts[i]+quote;
			}
			return quoteStr;
		}
		return str;
	}
	
}
