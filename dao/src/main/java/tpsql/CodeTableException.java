package tpsql;

public class CodeTableException extends RuntimeException {
	private static final long serialVersionUID = 6356272210136981729L;

	public CodeTableException(String message){
		super("CODETABLE ERROR "+message);
	}
	
	public CodeTableException(String message,Throwable cause){
		super("CODETABLE ERROR "+message+" "+cause.getMessage(),cause);
	}
}
