package tpsql.core.json.parser;

import tpsql.core.json.IJsonParser;
import tpsql.core.json.JsonElement;

public class RequestJsonParser implements IJsonParser {
	private JsonParser jsonParser;
	private FormsJsonParser formsParser;
	
	public IJsonParser getJsonParser(){
		if(jsonParser==null) {
			jsonParser = new JsonParser();
		}
		return jsonParser;
	}
	
	public IJsonParser getFormsParser(){
		if(formsParser==null) {
			formsParser = new FormsJsonParser();
		}
		return formsParser;
	}

	public JsonElement parse(Object obj) {
		String jsonString = (String)obj;
		if(jsonString!=null && jsonString.length()>0){
			if(jsonString.charAt(0)=='[' || jsonString.charAt(0)=='{'){
				return this.getJsonParser().parse(jsonString);
			}else{
				return this.getFormsParser().parse(jsonString);
			}
		}
		return null;
	}

}
