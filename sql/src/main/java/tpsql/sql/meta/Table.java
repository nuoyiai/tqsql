package tpsql.sql.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table {
	protected String name;
	protected String remark;
	protected String pkName;
	protected List<Column> columns;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public List<Column> getColumns(){
		return columns;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Table table = (Table) o;

		if (name != null ? !name.equals(table.name) : table.name != null) return false;
		if (remark != null ? !remark.equals(table.remark) : table.remark != null) return false;
		if (pkName != null ? !pkName.equals(table.pkName) : table.pkName != null) return false;
		return columns != null ? columns.equals(table.columns) : table.columns == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (pkName != null ? pkName.hashCode() : 0);
		result = 31 * result + (columns != null ? columns.hashCode() : 0);
		return result;
	}

	public Table clone(){
		Table table = new Table();
		table.setName(name);
		table.setRemark(remark);
		table.setPkName(pkName);
		if(columns!=null) {
			List<Column> columnList = new ArrayList<Column>();
			for (Column column : columns) {
				columnList.add(column.clone());
			}
			table.setColumns(columnList);
		}
		return table;
	}

}
