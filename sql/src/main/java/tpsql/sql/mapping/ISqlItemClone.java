package tpsql.sql.mapping;

public interface ISqlItemClone<T> {
	
	T clone(Object args);
	
}
