package tpsql.json.finder;

import tpsql.json.*;

import java.util.HashMap;
import java.util.Map;

public class JsonFinder implements IJsonFinder {

    public JsonObject findObject(JsonObject jsonObject,String selector) {
        return findObject(jsonObject,getMapOfSelector(selector));
    }

    public JsonObject findObject(JsonArray jsonArray,String selector) {
        return findObject(jsonArray,getMapOfSelector(selector));
    }

    private Map<String,String> getMapOfSelector(String selector){
        String[] parts = selector.split(";");
        Map<String,String> propMap = new HashMap<String, String>();
        for(String part : parts){
            String[] pair = part.split("=");
            if(pair.length==2) {
                propMap.put(pair[0], pair[1]);
            }
        }
        return propMap;
    }

    private JsonObject findObject(JsonArray jsonArray,Map<String,String> propMap){
        for(JsonValue jsonValue : jsonArray.getValues()){
            if(jsonValue instanceof JsonObject){
                JsonObject jo = findObject((JsonObject)jsonValue,propMap);
                if(jo!=null){
                    return jo;
                }
            }else if(jsonValue instanceof JsonArray){
                JsonObject jo = findObject((JsonObject)jsonValue,propMap);
                if(jo!=null){
                    return jo;
                }
            }
        }
        return null;
    }

    private JsonObject findObject(JsonObject jsonObject,Map<String,String> propMap){
        int count=propMap.size();
        int hitCount = 0;
        for(JsonPair jsonPair : jsonObject.getPairs()){
            if(JsonType.isValue(jsonPair.getValue())){
                String key = jsonPair.getName().getValue();
                String val = jsonPair.getValue().getValue().toString();
                if(propMap.containsKey(key) && propMap.get(key).equals(val)){
                    hitCount++;
                }
            }
        }
        if(hitCount>=count){
            return jsonObject;
        }
        for(JsonPair jsonPair : jsonObject.getPairs()){
            if(jsonPair.getValue() instanceof JsonArray){
                JsonObject jo = findObject((JsonArray)jsonPair.getValue(),propMap);
                if(jo!=null){
                    return jo;
                }
            }else if(jsonPair.getValue() instanceof JsonObject){
                JsonObject jo = findObject((JsonObject)jsonPair.getValue(),propMap);
                if(jo!=null){
                    return jo;
                }
            }
        }
        return null;
    }

    public JsonArray findArray(JsonArray jsonArray,String selector){
        return findArray(jsonArray,getMapOfSelector(selector));
    }

    public JsonArray findArray(JsonArray jsonArray, Map<String,String> propMap){
        return findArray(jsonArray,propMap,FinderType.Equal);
    }

    public JsonArray findArray(JsonArray jsonArray,Map<String,String> propMap,FinderType type){
        int count=propMap.size();

        JsonArray ja = new JsonArray();
        for(JsonValue jsonValue : jsonArray.getValues()){
            if(jsonValue instanceof JsonObject){
                int hitCount = 0;
                JsonObject jsonObject = (JsonObject)jsonValue;
                for(JsonPair jsonPair : jsonObject.getPairs()){
                    if(JsonType.isValue(jsonPair.getValue())){
                        String key = jsonPair.getName().getValue();
                        String val = jsonPair.getValue().getValue().toString();
                        if (propMap.containsKey(key)){
                            if(type==FinderType.Equal) {
                                if(propMap.get(key).equals(val)) {
                                    hitCount++;
                                }
                            }else if(type==FinderType.Fuzzy){
                                if (val.indexOf(propMap.get(key))>-1){
                                    hitCount++;
                                }
                            }
                        }
                    }
                }
                if(hitCount>=count){
                    ja.add(jsonObject);
                }
            }
        }

        return ja;
    }

}
