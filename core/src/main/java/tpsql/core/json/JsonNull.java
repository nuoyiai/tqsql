package tpsql.core.json;

public class JsonNull extends JsonValue {
	public JsonNull()
    {
        this.type = JsonValueType.Null;
    }

    @Override
	public String toString()
    {
        return "null";
    }
}
