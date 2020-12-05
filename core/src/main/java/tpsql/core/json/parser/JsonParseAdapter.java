package tpsql.core.json.parser;

import tpsql.core.json.IJsonParser;
import tpsql.core.json.JsonElement;

public class JsonParseAdapter {
    private static IJsonParser requestParser;

    static{
        requestParser = new RequestJsonParser();
    }

    public static JsonElement parse(Object obj){
        if(obj instanceof String){
            return requestParser.parse(obj);
        }
        return null;
    }
}
