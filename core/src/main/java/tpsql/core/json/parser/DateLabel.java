package tpsql.core.json.parser;

import tpsql.core.json.JsonDate;
import tpsql.core.json.JsonElement;

import java.util.Date;

public class DateLabel extends JsonDate implements IJsonLabel {

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
	
	public DateLabel()
    {
        this.startIndex = 0;
        this.endIndex = 0;
    }

	public void addChild(IJsonLabel child)
	{
		throw new RuntimeException("JsonDate不支持添加子对像 当前Json["+child.getJson().toString()+"]");
	}

	public JsonLabelType getCurrentChildType() {
		return JsonLabelType.Undefine;
	}

	public void close(int endIndex) {
		this.endIndex = endIndex;
		String text = this.getJsonString();

        if (text != null && text != "")
        {
        	Date val=this.convertToDate(text);
            this.setValue(val);
        }
	}
	
	private Date convertToDate(String text)
    {
		String value = text;
		if(text.indexOf("new Date")>-1) {
			value = value.substring(9, text.length() - 1);
		} else {
			value = text.substring(6, text.length() - 2);
		}
		 
        int index = value.indexOf('+', 1);
        if (index == -1) {
			index = value.indexOf('-', 1);
		}
        if (index != -1)
        {
            value = value.substring(0, index);
        }
		//String value=text;
        return new Date(Long.parseLong(value));
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
