package tpsql.convert;

public class ConvertException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConvertException(String message)
	{
		super(message);
	}

	public ConvertException(String message, Throwable cause)
	{
		super(message+" "+cause.getMessage(),cause);
	}
}
