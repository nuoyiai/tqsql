package tpsql.sql.mapping;

import java.util.ArrayList;
import java.util.List;

import tpsql.sql.SqlMapException;
import tpsql.util.StringUtil;
import tpsql.sql.SqlMapException;
import tpsql.util.StringUtil;
import tpsql.sql.SqlMapException;

/**
 * 主从表连接
 * Created by zhusw on 2017/9/13.
 */
public class SqlPoly extends SqlJoin {
	private String id;		//ID,复用时用到
	private List<PolyColumn> columns;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<PolyColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<PolyColumn> columns) {
		this.columns = columns;
	}

	public SqlPoly() {
		this.columns = new ArrayList<PolyColumn>();
	}
	
	public PolyColumn addColumn(String name,String op){
		return this.addColumn(name, op,"",null);
	}
	
	public PolyColumn addColumn(String name,String op,String split,String alias){
		if(StringUtil.isNotEmpty(op) && OP.get(op)==null) {
			throw new SqlMapException(StringUtil.format("poly.Column节点 op 不存在类型  {0}", op));
		}
		PolyColumn column = new PolyColumn(name,OP.get(op));
		column.setSplit(split);
		column.setAlias(alias);
		this.columns.add(column);
		return column;
	}

	public class PolyColumn {
		private OP op;
		private String split;
		private String name;
		private String alias;

		public OP getOp() {
			return op;
		}

		public void setOp(OP op) {
			this.op = op;
		}

		public String getSplit() {
			return split;
		}

		public void setSplit(String split) {
			this.split = split;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public PolyColumn() {
			this("",null);
		}
		
		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}
		
		public String getColumnName(){
			if(StringUtil.isNotEmpty(this.alias)){
				return this.alias;
			}else{
				return this.name;
			}
		}

		public PolyColumn(String name, OP op) {
			this.name = name;
			this.op = op;
			this.split = "";
			this.alias = null;
		}
	}

	public enum OP {
		JOIN, SUM, AVERAGE, MAX, MIN, FIRST, LAST;

		public static OP get(String name) {
			if ("join".equalsIgnoreCase(name)) {
				return JOIN;
			} else if ("sum".equalsIgnoreCase(name)) {
				return SUM;
			} else if ("average".equalsIgnoreCase(name)) {
				return AVERAGE;
			} else if ("max".equalsIgnoreCase(name)) {
				return MAX;
			} else if ("min".equalsIgnoreCase(name)) {
				return MIN;
			} else if ("first".equalsIgnoreCase(name)) {
				return FIRST;
			} else if ("last".equalsIgnoreCase(name)) {
				return LAST;
			} else {
				return null;
			}
		}
	}

	public String toXmlString(){
		StringBuffer sb = new StringBuffer();
		String idStr = (id!=null)?"id=\""+this.id+"\"":"";
		sb.append("<poly "+idStr+" pkey=\""+this.pkey+"\" fkey=\""+this.fkey+"\" sqlId=\""+this.sqlId+"\" >\r\n");
		for(PolyColumn column : this.columns){
			String aliasStr = column.getAlias()!=null?"alias=\""+column.getAlias()+"\"":"";
			sb.append("<column name=\""+column.getName()+"\" "+aliasStr+" op=\""+column.getOp().toString().toLowerCase()+"\" split=\""+column.getSplit()+"\" />\r\n");
		}
		sb.append("</poly>");
		return sb.toString();
	}
}
