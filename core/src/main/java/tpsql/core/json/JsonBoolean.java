package tpsql.core.json;

public class JsonBoolean extends JsonValue {
	private Boolean b;

    public Boolean getValue()
    {
    	return b;
    }
    
    public void setValue(boolean bool)
    {
    	b=bool;
    }

    public JsonBoolean()
    {
        this.type = JsonValueType.Boolean;
    }

    public JsonBoolean(boolean f)
    {
        this.b = f;
        this.type = JsonValueType.Boolean;
    }

    @Override
	public String toString()
    {
    	String str=(b)?"true":"false";
        return this.format(str);
    }
}
