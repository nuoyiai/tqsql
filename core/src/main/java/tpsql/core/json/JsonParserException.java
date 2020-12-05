package tpsql.core.json;

public class JsonParserException extends RuntimeException {
	private static final long serialVersionUID = 6277402285230957368L;

	public JsonParserException(String message){
		super("Json "+message);
	}
	
	public JsonParserException(String message,Throwable cause){
		super("Json "+message,cause);
	}
}
