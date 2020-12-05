package tpsql.core.convert;

import tpsql.core.json.*;
import tpsql.core.util.ReflectorUtil;

import java.util.*;

/**
 * 用动态代理把json转化为一个实体对像
 */
public class JsonProxyConvert implements IProxyConvert<JsonValue> {

    public Object toObj(JsonValue jsonValue) {
        if(jsonValue instanceof JsonObject){
            return toObj((JsonObject)jsonValue);
        }else if(jsonValue instanceof JsonArray){
            return toObj((JsonArray)jsonValue);
        }else if(jsonValue!=null){
            return jsonValue.getValue();
        }
        return null;
    }

    private Object toObj(JsonObject jsonObject){
        Map<String, Class<?>> propertyMap = new HashMap<String, Class<?>>();
        for(JsonPair pair : jsonObject.getPairs()){
            propertyMap.put(pair.getName().getValue(),getJsonValueType(pair.getValue()));
        }
        Object proxy = ReflectorUtil.newProxy(propertyMap);
        for(JsonPair pair : jsonObject.getPairs()){
            if(pair.getValue() instanceof JsonObject){
                Object obj = toObj((JsonObject)pair.getValue());
                ReflectorUtil.setPropertyValue(proxy,pair.getName().getValue(),obj);
            }else if(pair.getValue() instanceof JsonArray){
                Object obj = toObj((JsonArray)pair.getValue());
                ReflectorUtil.setPropertyValue(proxy,pair.getName().getValue(),obj);
            }else{
                ReflectorUtil.setPropertyValue(proxy,pair.getName().getValue(),pair.getValue().getValue());
            }
        }
        return proxy;
    }

    private Object toObj(JsonArray jsonArray){
        List list = new ArrayList();
        for(JsonValue jsonValue : jsonArray.getValues()){
            if(jsonValue instanceof JsonObject){
                list.add(toObj((JsonObject)jsonValue));
            }else{
                list.add(jsonValue.getValue());
            }
        }
        return list;
    }

    private Class<?> getJsonValueType(JsonValue jsonValue){
        if(jsonValue instanceof JsonNumber){
            Number number = ((JsonNumber)jsonValue).getValue();
            if(number!=null){
                return number.getClass();
            }
        }else if(jsonValue instanceof JsonBoolean){
            return Boolean.class;
        }else if(jsonValue instanceof JsonString){
            return String.class;
        }else if(jsonValue instanceof JsonDate){
            return Date.class;
        }else if(jsonValue instanceof JsonArray){
            return List.class;
        }
        return Object.class;
    }


}
