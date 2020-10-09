package tpsql.codetable;

import tpsql.reader.Reader;
import tpsql.util.StringUtil;

public abstract class AbstractCodeTable implements ICodeTable {
	protected String value;
	protected String text;
	protected String order;
	protected String icon;
	protected String sqlId;
    protected String check;
	
	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public String getOrder() {
		return order;
	}

	public String getIcon() {
		return icon;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    protected IItem getItem(Object data)
	{
	    return new ItemWrapper(this,data);
	}
	
	class ItemWrapper implements IItem
	{
		private AbstractCodeTable codeTable;
		private Object data;
		private String checked;
		
		public ItemWrapper(AbstractCodeTable codeTable,Object data)
		{
			this.codeTable = codeTable;
			this.data = data;
		}
		
		@Override
		public String getValue() {
			if(StringUtil.isNotEmpty(codeTable.getValue())) {
				return Reader.stringReader().getValue(data, codeTable.getValue());
			} else {
				return null;
			}
		}

		@Override
		public String getText() {
			if(StringUtil.isNotEmpty(codeTable.getText())) {
				return Reader.stringReader().getValue(data, codeTable.getText());
			} else {
				return null;
			}
		}

		@Override
		public int getOrder() {
			if(StringUtil.isNotEmpty(codeTable.getOrder())) {
				return Reader.intReader().getValue(data, codeTable.getOrder());
			} else {
				return -1;
			}
		}

		@Override
		public String getIcon() {
			if(StringUtil.isNotEmpty(codeTable.getIcon())) {
				return Reader.stringReader().getValue(data, codeTable.getIcon());
			} else {
				return null;
			}
		} 
		
		@Override
		public String checked(){
            if(StringUtil.isNotEmpty(codeTable.getCheck())){
                return Reader.stringReader().getValue(data, codeTable.getCheck());
            }else{
			    return this.checked;
            }
		}
		
		@Override
		public void checked(String checked){
			this.checked = checked;
		}
	}
}
