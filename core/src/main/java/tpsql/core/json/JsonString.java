package tpsql.core.json;

public class JsonString extends JsonValue {
	private String value;
    private boolean quoteEnable;

    public boolean isQuoteEnable() {
		return quoteEnable;
	}

	public void setQuoteEnable(boolean quoteEnable) {
		this.quoteEnable = quoteEnable;
	}

	public String getValue()
    {
    	return this.value;
    }
    
    public void setValue(String value){
    	this.value = value;
    }
    
    public JsonString()
    {
    	this("");
    }

    public JsonString(String text){
        this.value = (text == null) ? "" : text;
        this.type = JsonValueType.String;
        this.openQuote =this.closeQuote= "\"";
        this.quoteEnable = true;
    }

    @Override
	public String toString()
    {
    	String text = this.value.replaceAll("\"", "\\\"").replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
    	String str = "";
        if (this.isQuoteEnable()){
            str = this.getOpenQuote() + text + this.getCloseQuote();
        }else {
			str = text;
		}

        return this.format(str);
    }
}
