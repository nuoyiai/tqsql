package tpsql.sql.mapping;

import java.util.regex.Pattern;

import tpsql.sql.SqlMapException;
import tpsql.sql.builder.SqlString;
import tpsql.core.util.StringUtil;

public class SqlItemDynamic extends SqlItemContent implements ISqlItemClone<SqlItemDynamic> {
	
	private static final Pattern xmlP = Pattern.compile("[\\s|\\S]*?<(\\w+)[^>]*>[\\s|\\S]*?</\\1>[\\s|\\S]*?|[\\s|\\S]*?<\\w+[^>]*/>[\\s|\\S]*?");
	
	@Override
	public SqlString toString(SqlItemArgs arg){
		SqlString sql = super.toString(arg);
		
		String xml = sql.toString();
		if(xmlP.matcher(xml).matches()){
			if(sql.hasParameter()) {
				throw new SqlMapException(StringUtil.format("外部动态查询不能包含参数 xml{0}", sql.toString()));
			}
			SqlItemContent dynamtic = new SqlItemContent();
			this.getMap().parserChildContent(xml,dynamtic);
			sql = dynamtic.toString(arg);
		}
		
		return sql;
	}
	
	@Override
	public SqlItemDynamic clone(Object args) {
		SqlItemDynamic dynamtic = new SqlItemDynamic();
		dynamtic.parts = this.cloneParts(args);
		return dynamtic;
	}
	
}
