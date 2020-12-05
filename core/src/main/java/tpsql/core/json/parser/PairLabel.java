package tpsql.core.json.parser;

import tpsql.core.json.JsonElement;
import tpsql.core.json.JsonPair;
import tpsql.core.json.JsonString;
import tpsql.core.json.JsonValue;

public class PairLabel extends JsonPair implements IJsonLabel {
	private IJsonLabel parent;
    private int startIndex;
    private int endIndex;
    private JsonLabelRoot root;

	public IJsonLabel getParent() {
		return parent;
	}

	public void setParent(IJsonLabel parent) {
		this.parent = parent;
	}
	public PairLabel()
    {
        this.startIndex = 0;
        this.endIndex = 0;
    }

	public void addChild(IJsonLabel child)
	{
		if (this.getName() == null)
        {
            JsonString js = (JsonString)child.getJson();
            if (js != null)
            {
                this.setName(js);
                child.setParent(this);
            }
            else {
				throw new RuntimeException("JsonPair属性类型为JsonString 当前Json[" + child.getJson().toString() + "]");
			}
        }
        else
        {
            JsonValue jv = (JsonValue)child.getJson();
            if (jv != null)
            {
                this.setValue(jv);
                child.setParent(this);
            }
            else {
				throw new RuntimeException("JsonPair值类型为JsonValue 当前Json[" + child.getJson().toString() + "]");
			}
        }
	}

	public JsonLabelType getCurrentChildType()
    {
        if (this.getName() == null) {
			return JsonLabelType.String;
		} else if (this.getValue() == null) {
			return JsonLabelType.Value;
		} else {
			return JsonLabelType.Undefine;
		}
    }

	public void close(int endIndex) {
		this.endIndex = endIndex;
	}

	public void open(int startIndex) {
		this.startIndex = startIndex;
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
