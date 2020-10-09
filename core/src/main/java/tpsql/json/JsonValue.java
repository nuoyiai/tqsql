package tpsql.json;

public class JsonValue extends JsonElement {
	protected JsonValueType type;
	protected Object value;

	public JsonValueType getType()
	{
		return this.type;
	}

	public Object getValue(){
		return value;
	}

	public void setValue(Object value){
		this.value = value;
	}
	
	public JsonValue()
	{
		this.type= JsonValueType.Undefine;
	}

	public JsonValue(Object value){
		this.value = value;
		this.type= JsonValueType.Undefine;
	}

	@Override
	public String toString(){
		return value.toString();
	}
}
