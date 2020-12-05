package tpsql.core.json;

import tpsql.core.collection.SortHashMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonPairCollection implements Iterable<JsonPair> {
	private Map<String,JsonPair> map;
	
	public JsonPair get(String name)
	{
		return map.get(name);
	}

	public JsonPair get(int index)
	{
		if(index>=0 && index<map.values().size())
		{
			return (JsonPair)map.values().toArray()[index];
		}
		return null;
	}
	
	public void set(JsonPair pair)
	{
		map.put(pair.getText(), pair);
	}
	
	public JsonPair[] getPairs()
	{
		Collection<JsonPair> vals=map.values();
		JsonPair[] pairs=new JsonPair[vals.size()];
		vals.toArray(pairs);
		return pairs;
	}
	
	public String[] getPropertyNames()
	{
		Set<String> keySet = map.keySet();
		String[] keys=new String[keySet.size()];
		keySet.toArray(keys);
		return keys;		
	}
	
	public int size()
	{
		return map.size();
	}
	
	public JsonPairCollection()
	{
		this.map=new SortHashMap<String,JsonPair>();
	}
	
	public JsonPair add(String name, JsonValue value)
    {
        if(!this.map.containsKey(name)){
            JsonPair pair = new JsonPair();
            pair.setName(new JsonString(name));
            pair.setValue(value);
            this.map.put(name, pair);
            return pair;
        }
        else {
			return this.get(name);
		}
    }

    public JsonPair add(JsonPair pair)
    {
        if (!this.map.containsKey(pair.getText()))
        {
            this.map.put(pair.getText(), pair);
        }
        return pair;
    }

    public JsonPair remove(String name)
    {
        if (!this.map.containsKey(name))
        {
            return this.map.remove(name);
        }
        return null;
    }

    public boolean contains(String name)
    {
        return this.map.containsKey(name);
    }

	public Iterator<JsonPair> iterator() {
		return this.map.values().iterator();
	}
}
