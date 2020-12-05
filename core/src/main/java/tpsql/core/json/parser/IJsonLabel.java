package tpsql.core.json.parser;

import tpsql.core.json.IJsonElement;
import tpsql.core.json.JsonElement;

public interface IJsonLabel extends IJsonElement {
	JsonLabelRoot getRoot();
	
	void setRoot(JsonLabelRoot root);

    int getStartIndex();

    int getEndIndex();

    int length();

    void open(int startIndex);

    void close(int endIndex);
    
    boolean closeEnable(int endIndex);

    String getJsonString();

    IJsonLabel getParent();
    
    void setParent(IJsonLabel label);

    JsonElement getJson();

    void addChild(IJsonLabel child);

    JsonLabelType getCurrentChildType();

}
