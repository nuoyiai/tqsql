package tpsql.json;

public abstract class JsonElement implements IJsonElement {
	protected String openQuote;
    protected String closeQuote;
    protected String split;
    protected String format;
    protected boolean ignoreQuote;
    
    public JsonElement()
    {
    	this.openQuote = "";
    	this.closeQuote = "";
    	this.split = "";
    	this.format = "";
		this.ignoreQuote = false;
    }

	public String getCloseQuote() {
		return this.closeQuote;
	}

	public String getOpenQuote() {
		return this.openQuote;
	}

	public String getSplit() {
		return this.split;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public String format(String str)
	{
		return str;
	}
	
	public JsonElement contact(JsonElement element)
	{
		if(element!=null) {
			throw new JsonException("不能拼连该Json对像 "+element);
		}
		return this;
	}

	public boolean ignoreQuote(){
		return ignoreQuote;
	}

	public void ignoreQuote(boolean flag){
		ignoreQuote = flag;
	}
}
