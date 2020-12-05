package tpsql.sql.mapping;

import java.util.Map;

import tpsql.sql.SqlMapException;
import tpsql.core.util.TypeUtil;
import tpsql.core.reader.Reader;
import tpsql.sql.config.IDbConfig;
import tpsql.sql.dialect.IDialect;
import tpsql.core.util.StringUtil;

/**
 * 构造Sql语句参数对像
 */
public class SqlItemArgs {
	
	/** SqlMap 外部传入参数 */
	private Object[] datas;
	/** 判断外部参数是对像，还是值,或空  */
	private Boolean isValue;
	/** 局部变量  **/
	private Map<String,Object> variables;
	private boolean hidePrepend;
	private boolean isSelect;			//是查询sql语句
	private IDialect dialect;
	private IDbConfig dbConfig;
	
	protected SqlItemArgs() {
	}

	public Object[] getData()
	{
		return this.datas;
	}
	
	public boolean getHidePrepend()
	{
		return this.hidePrepend;
	}
	
	public IDialect getDialect()
	{
		return this.dialect;
	}
	
	public void setDialect(IDialect dialect)
	{
		this.dialect = dialect;
	}

	public IDbConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(IDbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public void setHidePrepend(boolean isHide)
	{
		this.hidePrepend = isHide;
	}
	
	public void setIsSelect(boolean isSelect){
		this.isSelect = isSelect;
	}
	
	public boolean getIsSelect(){
		return this.isSelect;
	}
	
	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	/**
	 * 判断外部参数是对像，还是值,或空
	 * @return
	 */
	public Boolean paramIsValues()
	{
		if(isValue==null)
		{
			if(datas.length==1)
			{
				if(TypeUtil.isValue(datas[0]))this.isValue = new Boolean(true);
				else this.isValue = new Boolean(false);
			}
			else if(datas.length>1)
			{
				this.isValue = new Boolean(true);
			}
		}
		return isValue;
	}
	
	public SqlItemArgs(Object[] datas)
	{
		this.datas = datas;
		this.isValue = null;
		this.hidePrepend = false;
		this.isSelect = false;
	}
	
	public Object parserParam(String text){
		return this.parserParam(new String[]{text}, text);
	}
	
	public Object parserParam(String[] params,String text){
		String str = params[0];
		if(str.charAt(0)=='[' && str.charAt(str.length()-1)==']')
		{
			String param = str.substring(1,str.length()-1);
			boolean hasParamFlag = param.charAt(0)=='$' || param.charAt(0)=='#';
			String paramName = hasParamFlag?param.substring(1):param;
			Object value=null;
			
			if(StringUtil.isNumber(paramName))
			{
				int index = Integer.parseInt(paramName);
				if(index<this.getData().length)
				{
					value = this.getData()[index];
				}
				else throw new SqlMapException(StringUtil.format("参数索引超出界限  {0}",text));
			}
			else
			{
				if(this.getData().length>0 && this.getData()[0]!=null)
				{
					value = getParamValue(this.getData()[0],paramName);
				}else if(this.getData().length==0){
					value = null;
				}
				else throw new SqlMapException(StringUtil.format("传入参数为空 {0}",text));
			}
			
			int num = 1;
			while(num!=params.length){
				param = params[num].substring(1,params[num].length()-1);
				hasParamFlag = param.charAt(0)=='$' || param.charAt(0)=='#';
				paramName = hasParamFlag?param.substring(1):param;
				if(value!=null){
					return getParamValue(value,paramName);
				}
				else throw new SqlMapException(StringUtil.format("对传入参数取值时,遇到空值 {0}",text));
			}

			if(!hasParamFlag && value instanceof String){			//防SQL注入
				String valStr = (String)value;
				if(StringUtil.isNotEmpty(valStr)){
					value = valStr.replaceAll("'","").replaceAll(";","");
				}
			}
			return value;
		}
		else if(str.charAt(0)=='\'' && str.charAt(str.length()-1)=='\'')
		{
			return str.substring(1,str.length()-1);
		}
		else if(str.charAt(0)=='{' && str.charAt(str.length()-1)=='}'){
			String param = str.substring(1,str.length()-1);
			boolean hasParamFlag = param.charAt(0)=='$' || param.charAt(0)=='#';
			String paramName = hasParamFlag?param.substring(1):param;
			return getParamValue(this.variables,paramName);
		}
		else
		{
			if(StringUtil.isNumber(str))return Integer.parseInt(str);
			else return str;
		}
	}

	private Object getParamValue(Map<String,Object> valueMap,String param){
		if(valueMap!=null) {
			int n = param.indexOf(".");
			if (n == -1) {
				String key = param;
				if (valueMap.containsKey(key)) {
					return valueMap.get(key);
				}
				return null;
			} else {
				String key = param.substring(0, n).toLowerCase();
				if (valueMap.containsKey(key)) {
					Object value = valueMap.get(key);
					if (value != null) {
						return getParamValue(value, param.substring(n+1));
					}
				}
				return null;
			}
		}
		return null;
	}

	private Object getParamValue(Object value,String param){
		int n = param.indexOf(".");
		if(n==-1){
			return getParamValueAndCheck(value,param);
		}else{
			Object val = getParamValueAndCheck(value,param.substring(0,n));
			if(val!=null){
				return getParamValue(val,param.substring(n+1));
			}else{
				return val;
			}
		}
	}

	private Object getParamValueAndCheck(Object value,String param){
		if(!TypeUtil.isValue(value)){
			if(TypeUtil.isCollection(value)){
				if(StringUtil.isNumber(param)){
					return Reader.objectReader().getValue(value,Integer.parseInt(param));
				}else{
					throw new SqlMapException(StringUtil.format("取参数值出错,参数是集合类型，不能通过{0}取参",param));
				}
			}else{
				return Reader.objectReader().getValue(value,param);
			}
		}else{
			//throw new SqlMapException(StringUtil.format("取参数值出错,参数是值类型，不能通过{0}取参",param));
			return value;
		}
	}
	
	/**
	 * 判断Sql语句参数符串
	 * @param param
	 */
	public void checkParam(String param)
	{
		char c = param.charAt(0);
		if(c=='~' || c=='!' || c=='@' || c=='%' || c=='^' || c=='&' || c=='*' || c=='+' || c=='-' || c=='|' || c=='?')
		{
			throw new SqlMapException(StringUtil.format("参数字符不正确请用 [${1}] 或 [#{1}] 不要用 [{0}{1}]",c,param.substring(1,param.length())));
		}
	}
}
