package tpsql.sql.mapping;

import java.util.ArrayList;
import java.util.List;

public class SqlItemInclude extends SqlItemContent implements ISqlItemClone<SqlItemInclude> {
	private String refId;
	private List<SqlJoin> joins;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public List<SqlJoin> getJoins() {
		return joins;
	}

	public void setJoins(List<SqlJoin> joins) {
		this.joins = joins;
	}

	public SqlItemInclude(){
		this.refId = "";
		this.joins = new ArrayList<SqlJoin>();
	}

	@Override
	public SqlItemInclude clone(Object args) {
		SqlItemInclude include = new SqlItemInclude();
		include.parts=this.cloneParts(args);
		return include;
	}

}
