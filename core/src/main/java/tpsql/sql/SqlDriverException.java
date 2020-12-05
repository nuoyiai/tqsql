package tpsql.sql;

public class SqlDriverException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SqlDriverException(String message)
	{
		super(message);
	}
	
	public SqlDriverException(String message,Throwable cause)
	{
		super(message+" "+cause.getMessage(),cause);
	}
}
