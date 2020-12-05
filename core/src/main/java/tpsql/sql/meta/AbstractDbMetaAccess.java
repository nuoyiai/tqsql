package tpsql.sql.meta;

import java.util.ArrayList;
import java.util.List;

import tpsql.sql.command.IDataReader;
import tpsql.sql.dialect.IMetaSql;
import tpsql.core.util.TypeUtil;

public abstract class AbstractDbMetaAccess implements IDbMetaAccess {
	private IMetaSql metaSql;
	
	public AbstractDbMetaAccess(IMetaSql metaSql)
	{
		this.metaSql = metaSql;
	}
	
	protected abstract void execute(String[] sqlStrings);
	
	protected abstract IDataReader getResult(String sqlString);
	
	protected abstract Object getValue(String sqlString);
	
	protected abstract List<Object> getValues(String sqlString);

	public List<Table> getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	public Table getTable(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createTable(Table tableName) {
		// TODO Auto-generated method stub
		
	}

	public void dropTable(String tableName) {
		// TODO Auto-generated method stub
		
	}

	public List<Sequence> getSequences() {
		String sqlString = this.metaSql.getSequencesSql();
		IDataReader reader = this.getResult(sqlString);
		List<Sequence> sequences = new ArrayList<Sequence>();
		while(reader.read())
		{
			String name = (String)TypeUtil.changeType(reader.get("sname"),String.class);
			Long value = (Long)TypeUtil.changeType(reader.get("svalue"),Long.class);
			sequences.add(new Sequence(name,value));
		}
		reader.close();
		return sequences;
	}

	public Sequence getSequence(String sequenceName) {
		String sqlString = this.metaSql.getSequenceSql(sequenceName);
		IDataReader reader = this.getResult(sqlString);
		while(reader.read())
		{
			String name = (String)TypeUtil.changeType(reader.get("SNAME"),String.class);
			Long value = (Long)TypeUtil.changeType(reader.get("SVALUE"),Long.class);
			return new Sequence(name,value);
		}
		reader.close();
		return null;
	}

	public Long getSequenceNextValue(String sequenceName) {
		String sqlString = this.metaSql.getSequenceNextSql(sequenceName);
		Object value = this.getValue(sqlString);
		return (Long)TypeUtil.changeType(value,Long.class);
	}

	public Long[] getSequenceNextValue(String sequenceName,int num) {
		String sqlString = this.metaSql.getSequenceNextSql(sequenceName,num);
		List<Object> values = this.getValues(sqlString);
		Long[] ids = new Long[num];
		for(int i=0;i<values.size();i++){
			ids[i] = ((Long)TypeUtil.changeType(values.get(i),Long.class));
		}
		return ids;
	}

	public void createSequence(Sequence sequence) {
		this.createSequence(sequence.getName(), sequence.getValue());
	}

	public void createSequence(String sequenceName, Long sequeneValue) {
		String[] sqlStrings = this.metaSql.getCreateSequenceSql(sequenceName, sequeneValue);
		this.execute(sqlStrings);
	}

	public void dropSequence(String sequenceName) {
		String sqlString = this.metaSql.getDropSequenceSql(sequenceName);
		this.execute(new String[]{sqlString});
	}
}
