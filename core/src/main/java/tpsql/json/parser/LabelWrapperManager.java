package tpsql.json.parser;

import tpsql.json.IJsonElement;

import java.util.HashMap;
import java.util.Map;

public class LabelWrapperManager {
	private Map<JsonLabelType,IJsonLabel> labs;

    public LabelWrapperManager()
    {
        labs = new HashMap<JsonLabelType,IJsonLabel>();
    }

    public void regedit(JsonLabelType type)
    {
        if (!this.labs.containsKey(type))
        {
            IJsonLabel ijl = JsonLabelFactroy.create(type);
            this.labs.put(type, ijl);
        }
    }

    public IJsonElement getJsonElement(JsonLabelType type)
    {
        return this.labs.get(type);
    }
}
