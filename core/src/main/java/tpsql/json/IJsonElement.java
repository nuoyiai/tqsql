package tpsql.json;

public interface IJsonElement {

	String getOpenQuote();

	String getCloseQuote();

	boolean ignoreQuote();

	void ignoreQuote(boolean flag);

	String getSplit();
	
}
