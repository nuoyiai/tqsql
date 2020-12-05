package tpsql.core.json;

public class JsonPair extends JsonElement {
	private JsonString name;
    private JsonValue value;

	public JsonString getName() {
		return name;
	}

	public void setName(JsonString name) {
		this.name = name;
	}

	public JsonValue getValue() {
		return value;
	}

	public void setValue(JsonValue value) {
		this.value = value;
	}

	public String getText()
    {
        return (this.getName() == null) ? "" : this.getName().getValue();
    }


    public JsonPair()
    {
        this.split = ":";
    }
    
    public JsonPair(String name,JsonValue jv)
    {
        this.split = ":";
        this.name = new JsonString(name);
        this.value = jv;
    }

    @Override
	public String toString()
    {
    	String str=this.getName().toString()+this.getSplit()+this.getValue().toString();
        return this.format(str);
    }
    
    @Override
    public JsonElement contact(JsonElement element)
	{
    	if(element!=null)
    	{
    		if(element instanceof JsonPair)
    		{
    			JsonPair jp = (JsonPair)element;
    			if(this.getValue() instanceof JsonObject)
    			{
    				JsonObject jo = (JsonObject)this.getValue();
    				jo.getPairs().add(jp);
    			}
    			else
    			{
    				if(jp.getValue()!=null) {
						this.setValue(jp.getValue());
					}
    			}
    		}
    		else if(element instanceof JsonValue)
	    	{
	    		JsonValue jv = (JsonValue)element;
                this.setValue(jv);
	    	}
	    	else {
				throw new JsonException("JsonPair只能拼接 JsonPair或JsonValue对像 "+element);
			}
    	}
    	return this;
	}
}
