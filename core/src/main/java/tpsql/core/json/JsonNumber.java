package tpsql.core.json;

public class JsonNumber extends JsonValue {
	private Number value;

    public Number getValue()
    {
    	return this.value;
    }
    
    public void setValue(Number value)
    {
    	this.value=value;
    }
    
    public void setValue(String value)
    {
    	this.parser(value);
    }

    public JsonNumber()
    {
        this.type = JsonValueType.Number;
    }
    
    public JsonNumber(String value)
    {
        this.parser(value);
    }

    public JsonNumber(Number value)
    {
        this.value = value;
    }

    private void parser(String value)
    {
        value = value.replaceAll("\n","").replaceAll("\r","");
    	if(value.indexOf(".")>-1)
    	{
    		Double d = Double.parseDouble(value);
    		if(d.doubleValue()>Float.MAX_VALUE) {
				this.value = d;
			} else {
				this.value = d.floatValue();
			}
    	}
    	else
    	{
    		Long l = Long.parseLong(value);
    		if(l.longValue()>Integer.MAX_VALUE) {
				this.value = l;
			} else {
				this.value = l.intValue();
			}
    	}
    }

    @Override
	public String toString()
    {
        return this.format(this.value.toString());
    }
}
