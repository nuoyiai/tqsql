package tpsql.core.json;

import tpsql.core.json.parser.JsonParser;
import tpsql.core.util.TypeUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonObject extends JsonValue {
	private JsonPairCollection pairs;

    public JsonValue getElement(String name){
    	if (!pairs.contains(name)) {
			return null;
		} else {
			return pairs.get(name).getValue();
		}
    }
    
    public JsonObject putElement(String name, JsonValue value){
    	if(!pairs.contains(name)){
            JsonPair pair = pairs.add(name, value);
            pair.setValue(value);
        }
        pairs.get(name).setValue(value);
    	return this;
    }

    public boolean contains(String name){
        return pairs.contains(name);
    }

    public Object get(String name){
        if (!pairs.contains(name)) {
            return null;
        } else {
            return pairs.get(name).getValue().getValue();
        }
    }

    public JsonArray getArray(String name){
        if (!pairs.contains(name)) {
            return null;
        } else {
            return (JsonArray)pairs.get(name).getValue();
        }
    }

    public String getString(String name){
        if (!pairs.contains(name)) {
            return null;
        } else {
            Object val = pairs.get(name).getValue().getValue();
            return (String) TypeUtil.changeType(val,String.class);
        }
    }

    public Integer getInteger(String name){
        if (!pairs.contains(name)) {
            return null;
        } else {
            Object val = pairs.get(name).getValue().getValue();
            return (Integer)TypeUtil.changeType(val,Integer.class);
        }
    }

    public Date getDate(String name){
        if (!pairs.contains(name)) {
            return null;
        } else {
            Object val = pairs.get(name).getValue().getValue();
            return (Date) TypeUtil.changeType(val,Date.class);
        }
    }

    public Boolean getBoolean(String name){
        if (!pairs.contains(name)) {
            return null;
        } else {
            Object val = pairs.get(name).getValue().getValue();
            return (Boolean)TypeUtil.changeType(val,Boolean.class);
        }
    }

    public JsonObject getJsonObject(String name){
        if (!pairs.contains(name)) {
            return null;
        } else {
            JsonValue jsonValue = pairs.get(name).getValue();
            if(jsonValue instanceof JsonObject){
                return (JsonObject)jsonValue;
            }
            return null;
        }
    }

    public JsonObject put(String name, Object value){
        if(value == null)
            this.put(name,new JsonNull());
        else if(value instanceof JsonValue)
            this.put(name,(JsonValue)value);
        else if(value instanceof String)
            this.put(name,(String)value);
        else if(value instanceof String[])
            this.put(name,(String[])value);
        else if(value instanceof Number)
            this.put(name,(Number)value);
        else if(value instanceof Date)
            this.put(name,(Date)value);
        else if(value instanceof Boolean)
            this.put(name,(Boolean)value);
        return this;
    }
    
    private JsonObject put(String name, String value){
    	return this.putElement(name, new JsonString(value));
    }

    private JsonObject put(String name, String[] values){
        if(values!=null) {
            JsonArray ja = new JsonArray();
            for(String value : values){
                ja.add(new JsonString(value));
            }
            return this.putElement(name, ja);
        }
        return this;
    }
    
    private JsonObject put(String name, Number value){
    	return this.putElement(name, new JsonNumber(value));
    }

    private JsonObject put(String name, Date value){
        return this.putElement(name, new JsonDate(value));
    }

    private JsonObject put(String name, Boolean value){
        return this.putElement(name, new JsonBoolean(value));
    }

    private JsonObject put(String name, JsonArray jsonArray){
        return this.putElement(name, jsonArray);
    }

    private JsonObject put(String name, JsonValue jsonValue){
        return this.putElement(name, jsonValue);
    }
    
    public void put(Map<String,Object> map){
    	for(String key : map.keySet()){
    		Object value = map.get(key);
    		if(value instanceof String){
    			this.put(key,(String)value);
    		}else if(value instanceof Number){
    			this.put(key,(Number)value);
    		}
    	}
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap();
        for(JsonPair pair :this.pairs){
            map.put(pair.getText(),pair.getValue().getValue());
        }
        return map;
    }

    public static JsonObject fromObject(Object obj){
        String jsonString = (String)obj;
        IJsonParser parser = new JsonParser();
        return (JsonObject)parser.parse(jsonString);
    }
    
    public JsonPairCollection getPairs()
    {
        return this.pairs;
    }

    public JsonObject()
    {
        this.pairs = new JsonPairCollection();
        this.type = JsonValueType.Object;
        this.openQuote="{";
        this.closeQuote="}";
        this.split = ",";
    }

    @Override
	public String toString()
    {
    	StringBuffer sb=new StringBuffer();
        sb.append(this.getOpenQuote());
        boolean isFirst=true;
        JsonPair[] jps=this.pairs.getPairs();
        for(int i=0;i<jps.length;i++)
        {
        	JsonPair pair=jps[i];
            if (isFirst) {
				sb.append(pair.toString());
			} else {
				sb.append(this.getSplit() + pair.toString());
			}
            isFirst=false;
        }
        sb.append(this.getCloseQuote());
        return this.format(sb.toString());
    }
    
    @Override
    public JsonElement contact(JsonElement element)
	{
    	if(element!=null)
    	{
	    	if(element instanceof JsonPair)
	    	{
	    		JsonPair jp = (JsonPair)element;
                this.getPairs().add(jp);
	    	}
	    	else {
				throw new JsonException("JsonObject只能拼接 JsonPair对像 "+element);
			}
    	}
    	return this;
	}
}
