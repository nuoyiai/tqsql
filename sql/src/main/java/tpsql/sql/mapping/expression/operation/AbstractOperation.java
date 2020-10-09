package tpsql.sql.mapping.expression.operation;

import tpsql.sql.mapping.expression.IOperation;
import tpsql.sql.mapping.expression.IOperation;
import tpsql.sql.mapping.expression.IOperation;

public abstract class AbstractOperation implements IOperation {
	private String sign;				//
	private Integer priority;
	
	@Override
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
