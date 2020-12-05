package tpsql.dao;

public class EntityMapException extends RuntimeException {
	private static final long serialVersionUID = -7945466704512518649L;

	public EntityMapException(String message){
		super("MAPPING ERROR "+message);
	}
	
	public EntityMapException(String message,Throwable cause){
		super("MAPPING ERROR "+message+" "+cause.getMessage(),cause);
	}
}
