package tpsql.json.parser;

import tpsql.json.JsonBoolean;
import tpsql.json.JsonElement;

class BooleanLabel extends JsonBoolean implements IJsonLabel {

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
	public BooleanLabel()
    {
        this.startIndex = 0;
        this.endIndex = 0;
    }

	public void addChild(IJsonLabel child)
	{
		throw new RuntimeException("JsonBoolen不支持添加子对像 当前Json["+child.getJson()+"]");
	}

	public JsonLabelType getCurrentChildType() {
		return JsonLabelType.Undefine;
	}

	public void close(int endIndex) {
		this.endIndex = endIndex;
		boolean val = (this.getJsonString().toUpperCase() == "TRUE") ? true : false;
		this.setValue(val);
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
