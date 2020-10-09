package tpsql.json.parser;

import tpsql.json.JsonElement;
import tpsql.json.JsonObject;
import tpsql.json.JsonPair;

import java.util.ArrayList;
import java.util.List;

public class ObjectLabel extends JsonObject implements IJsonLabel {
	private IJsonLabel parent;
    private int startIndex;
    private int endIndex;
    private JsonLabelRoot root;
    private List<JsonPair> tempPairs;

	public IJsonLabel getParent() {
		return parent;
	}

	public void setParent(IJsonLabel parent) {
		this.parent = parent;
	}
	public ObjectLabel()
    {
        this.startIndex = 0;
        this.endIndex = 0;
    }

	public void addChild(IJsonLabel child)
	{
		JsonPair jp=(JsonPair)child.getJson();
        if (jp != null)
        {
            this.tempPairs.add(jp);
            child.setParent(this);
        }
        else {
			throw new RuntimeException("JsonObject只能添加JsonPair 当前Json[" + child.getJson() + "]");
		}
	}

	public JsonLabelType getCurrentChildType() {
		return JsonLabelType.Pair;
	}

	public void close(int endIndex) {
		this.endIndex = endIndex;
		for(int i=0;i<this.tempPairs.size();i++)
		{
			this.getPairs().add(this.tempPairs.get(i));
		}
	}

	public void open(int startIndex) {
		this.startIndex = startIndex;
		this.tempPairs = new ArrayList<JsonPair>();
	}

	public int getStartIndex() {
		return this.startIndex;
	}

	public int getEndIndex() {
		return this.endIndex;
	}

	public JsonElement getJson() {
		return this;
	}

	public String getJsonString() {
		if (this.getRoot() != null)
        {
            return this.getRoot().getJsonString().substring(this.getStartIndex(), this.getEndIndex());
        }
        else {
			return "";
		}
	}

	public void setRoot(JsonLabelRoot root) {
		this.root=root;
	}

	public JsonLabelRoot getRoot() {
		return this.root;
	}

	public int length() {
		return this.getStartIndex() - this.getEndIndex();
	}

	public boolean closeEnable(int endIndex) {
		return true;
	}
}
