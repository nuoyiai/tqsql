package tpsql.json;

import tpsql.json.finder.JsonFinder;
import tpsql.json.parser.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class JsonArray extends JsonValue {
	private List<JsonValue> values;
	
	public int size(){
		return this.values.size();
	}
	
	public List<JsonValue> getValues() {
		return values;
	}

	public JsonValue get(int index){
		if(index>-1 && index<this.values.size()){
			return this.values.get(index);
		}
		return null;
	}

	public JsonObject getJsonObject(int index){
		JsonValue jsonValue = this.get(index);
		if(jsonValue instanceof JsonObject){
			return (JsonObject)jsonValue;
		}
		return null;
	}

	public static JsonArray fromObject(Object obj){
		String jsonString = (String)obj;
		IJsonParser parser = new JsonParser();
		return (JsonArray)parser.parse(jsonString);
	}
	
	public JsonArray()
    {
		this.type = JsonValueType.Array;
        this.values = new ArrayList<JsonValue>();
        this.openQuote = "[";
        this.closeQuote = "]";
        this.split = ",";
    }
	
	public JsonArray(JsonValue jv)
	{
		this(new JsonValue[]{jv});
	}
	
	public JsonArray(JsonValue[] jvs){
		this();
		if(jvs!=null)
		{
			for(JsonValue jv : jvs)
			{
				if(jv!=null) {
					this.getValues().add(jv);
				}
			}
		}
	}
	
	public void add(JsonValue value){
		this.values.add(value);
	}
	
	public void add(String value){
		this.add(new JsonString(value));
	}
	
	public void add(Number value){
		this.add(new JsonNumber(value));
	}

	public void add(Map<String,Object> map){
		JsonObject jo = new JsonObject();
		jo.put(map);
		this.add(jo);
	}
	
	public JsonArray contact(JsonArray ja){
		for(JsonValue jv : ja.getValues()){
			this.getValues().add(jv);
		}
		return this;
	}

	public JsonObject findObject(String key,String value){
		return findObject(key+"="+value);
	}

	public JsonObject findObject(String selector){
		IJsonFinder jsonFinder = new JsonFinder();
		return jsonFinder.findObject(this,selector);
	}

	public JsonArray findArray(String key,String value){
		IJsonFinder jsonFinder = new JsonFinder();
		return jsonFinder.findArray(this,key+"="+value);
	}

	public JsonArray findArray(Map<String,String> map){
		IJsonFinder jsonFinder = new JsonFinder();
		return jsonFinder.findArray(this,map);
	}

	public JsonArray findArray(Map<String,String> map,FinderType type){
		IJsonFinder jsonFinder = new JsonFinder();
		return jsonFinder.findArray(this,map,type);
	}

	public JsonArray pageArray(int page,int size){
		int total = this.values.size();
		int offset = (page-1)*size;
		int limit = page*size;
		if(offset<total){
			if(limit>total)limit=total;
			JsonArray jsonArray = new JsonArray();
			for(int i=offset;i<limit;i++){
				jsonArray.add(this.get(i));
			}
			return jsonArray;
		}else{
			return new JsonArray();
		}
	}

	public void eachObject(Consumer<? super JsonObject> action){
		for(JsonValue jv : values){
			if(jv instanceof JsonObject) {
				action.accept((JsonObject)jv);
			}
		}
	}

	public boolean findObject(String key, String value, Consumer<? super JsonObject> action){
		JsonObject jo = findObject(key+"="+value);
		if(jo!=null){
			action.accept(jo);
			return true;
		}
		return false;
	}

	public boolean findObject(String selector, Consumer<? super JsonObject> action){
		IJsonFinder jsonFinder = new JsonFinder();
		JsonObject jo = jsonFinder.findObject(this,selector);
		if(jo!=null){
			action.accept(jo);
			return true;
		}
		return false;
	}

	public void remove(JsonValue jsonValue){
		this.values.remove(jsonValue);
	}
	
	@Override
	public String toString()
    {
		StringBuffer sb = new StringBuffer();
        sb.append(this.getOpenQuote());
        boolean isFirst = true;
        for(int i=0;i<this.values.size();i++)
        {
        	JsonValue val = this.get(i);
            if (isFirst) {
				sb.append(val.toString());
			} else {
				sb.append(this.getSplit() + val.toString());
			}
            isFirst = false;
        }
        sb.append(this.getCloseQuote());
        return this.format(sb.toString());
    }
	
	@Override
    public JsonElement contact(JsonElement element)
	{
    	if(element!=null)
    	{
	    	if(element instanceof JsonValue)
	    	{
	    		JsonValue jv = (JsonValue)element;
                this.getValues().add(jv);
	    	}
	    	else {
				throw new JsonException("JsonObject只能拼接 JsonValue对像 "+element);
			}
    	}
    	return this;
	}
}
