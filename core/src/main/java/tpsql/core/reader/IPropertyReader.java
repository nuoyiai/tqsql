package tpsql.core.reader;

public interface IPropertyReader<T> {

	T getValue(Object entity, String propertyName);

	T getValue(Object entity, String propertyName,T substitute);

	T getValue(Object entity, int index);

	T getValue(Object entity, int index,T substitute);

}
