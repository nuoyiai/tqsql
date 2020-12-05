package tpsql.core.json;

public class JsonException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public JsonException(String message)
	{
		super("Json "+message);
	}
	
	public JsonException(String message,Throwable cause)
	{
		super("Json "+message,cause);
	}
}
