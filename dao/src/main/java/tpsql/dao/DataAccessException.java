package tpsql.dao;

public class DataAccessException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataAccessException(String message)
	{
		super("DAO "+message);
	}
	
	public DataAccessException(String message,Throwable cause)
	{
		super("DAO "+message,cause);
	}
}