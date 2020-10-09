package tpsql.json.parser;

import tpsql.json.IJsonParser;
import tpsql.json.JsonElement;
import tpsql.json.JsonParserException;

public class JsonParser implements IJsonParser {

	public JsonElement parse(Object obj){
		String jsonString = (String)obj;
		try{
	    	LabelBlock block = new LabelBlock();
	        block.clear();
	        block.setJsonString(jsonString);
	
	        for(int i=0;i<jsonString.length();i++){
	        	char c=jsonString.charAt(i);
	            block.read(c);
	        }
	
	        return block.getResult();
		}catch(Exception e){
			throw new JsonParserException("解析json字符串出错 "+jsonString,e);
		}
    }
	
}
