package tpsql.core.reader;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DefaultCollectionReader implements ICollectionReader {

	public Iterable<Object> getCollection(Object data) {
		
		if(data.getClass().isArray()){   
			return Arrays.asList((Object[])data);
		}
		else if(data instanceof Iterable){
			return (Iterable<Object>)data;
		}
		return null;
	}
}
