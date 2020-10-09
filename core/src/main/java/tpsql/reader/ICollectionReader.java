package tpsql.reader;

public interface ICollectionReader {
	Iterable<Object> getCollection(Object data);
}
