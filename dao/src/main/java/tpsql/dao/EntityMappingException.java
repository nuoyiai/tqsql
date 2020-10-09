package tpsql.dao;

public class EntityMappingException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EntityMappingException(String message)
	{
		super("EntityMapping "+message);
	}
	
	public EntityMappingException(String message,Throwable cause)
	{
		super("EntityMapping "+message,cause);
	}
}
