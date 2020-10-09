package tpsql.sql.mapping;

import tpsql.sql.SqlMapException;
import tpsql.sql.builder.SqlString;
import tpsql.util.StringUtil;
import tpsql.sql.SqlMapException;
import tpsql.sql.builder.SqlString;
import tpsql.util.StringUtil;
import tpsql.sql.SqlMapException;
import tpsql.sql.builder.SqlString;

/**
 * 自定义函数调用
 */
public class SqlItemMethod implements ISqlItemPart,ISqlItemClone<SqlItemMethod> {
	/** 函数名 */
	private String name;
	/** 函数参数名集合 */
	private String methodParams;
	/** 配置对像 */
	private SqlMap map;
	/** 输出变量 */
	private String output;
	
	protected SqlItemMethod() {
	}

	public SqlMap getMap()
	{
		return this.map;
	}
	
	public void setMap(SqlMap map)
	{
		this.map = map;
	}
	
	/** 调用函数名 */
	public String getName()
	{
		return this.name;
	}
	
	public void setParams(String params)
	{
		this.methodParams = params;
	}

	/** 输出变量 */
	public String getOutput() {
		return output;
	}

	/** 输出变量 */
	public void setOutput(String output) {
		this.output = output;
	}

	public SqlItemMethod(String name, String params)
	{
		this.name = name;
		this.methodParams = params;
	}
	
	@Override
	public SqlItemMethod clone(Object args){
		SqlItemMethod method = new SqlItemMethod();
		method.map = this.map;
		method.name = this.name;
		method.output = this.output;
		if(args instanceof ISqlParamMacro){
			ISqlParamMacro macro = (ISqlParamMacro)args;
			method.methodParams = macro.format(this.methodParams);
		}else{
			method.methodParams = this.methodParams;
		}
		return method;
	}
	
	@Override
	public SqlString toString(SqlItemArgs arg)
	{
		ISqlMethod method = this.getMap().getMethod(this.name);
		if(method!=null)
		{
			if(StringUtil.isNotEmpty(this.methodParams))
			{
				String[] params = this.methodParams.split("\\,");
				Object[] args = new Object[params.length];
				for(int i=0;i<params.length;i++)
				{
					if(StringUtil.isNotEmpty(params[i]))
					{
						args[i] = arg.parserParam(params[i]);
					}
					else {
						throw new SqlMapException(StringUtil.format("自定义函数参数 为空 {0} {1}", this.name, this.methodParams));
					}
				}
				SqlString result = method.invoke(arg.getDbConfig(),arg.getDialect(),args);
				if(StringUtil.isNotEmpty(this.getOutput())){
					if(result!=null && result.getParts()!=null && result.getParts().length>0) {
						arg.getVariables().put(this.getOutput(),result.getParts());
					}
					return new SqlString("");
				}else{
					return result;
				}
			}else{
				SqlString result = method.invoke(arg.getDbConfig(),arg.getDialect(),new Object[0]);
				if(StringUtil.isNotEmpty(this.getOutput())){
					if(result!=null && result.getParts()!=null && result.getParts().length>0) {
						arg.getVariables().put(this.getOutput(),result.getParts());
					}
					return new SqlString("");
				}else{
					return result;
				}
			}
		}else {
			throw new SqlMapException(StringUtil.format("自定义函数为空 {0}", this.name));
		}
	}
}
