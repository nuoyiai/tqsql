package tpsql.sql.condition;

import tpsql.core.reader.Reader;
import tpsql.core.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class EqualsCondition implements ISqlCondition {

	public String getCondition(Object data, Args args) {
		if(data!=null){
			String property = args.getProperty();
			String column = args.getColumn();
			String alias = (StringUtil.isNotEmpty(args.getTableAlias()))?args.getTableAlias()+".":"";
			String quote = ("string".equalsIgnoreCase(args.getValueType()))?"'":"";
			String val = Reader.stringReader().getValue(data, property);
			if(val!=null){
				String[] vs = val.split("\\,",-1);
				if(vs.length>0){
					StringBuilder sql = new StringBuilder();
					String nullAssert = (quote.length()>0)?"isNullOrEmpty":"isNull";
					sql.append("<clause prepend=\"and\" assert=\"!"+nullAssert+"(["+property+"])\" >");
					sql.append(" (");
					for(int i=0;i<vs.length;i++){
						if(StringUtil.isNotEmpty(vs[i])){
							sql.append(((i>0)?" or ":"")+alias+"\""+column+"\" = "+quote+vs[i]+quote);
						}
					}
					sql.append(") ");
					sql.append(" </clause>");
					return sql.toString();
				}
			} 
		}
		return "";
	}
	
}
