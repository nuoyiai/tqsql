package tpsql.core.json.parser;

import tpsql.core.json.IJsonElement;
import tpsql.core.json.JsonElement;

import java.util.Stack;

public class LabelBlock {
	private JsonLabelRoot root;
    private Stack<IJsonLabel> labs;
    private int index;
    private LabelWrapperManager manager;
    private IJsonLabel fisrt;

    public IJsonLabel getCurrent()
    {
        return (labs.size() > 0) ? (IJsonLabel)labs.peek() : null;
    }

    public String getJsonString()
    {
        return this.root.getJsonString();
    }
    
    public void setJsonString(String value)
    {
    	this.root.setJsonString(value);
    }

    public Stack<IJsonLabel> getQuery()
    {
        return this.labs;
    }

    public JsonElement getResult()
    {
        if (this.fisrt != null)
        {
            return this.fisrt.getJson();
        }
        else {
			return null;
		}
    }

    public LabelBlock()
    {
        this.root = new JsonLabelRoot("");
        this.index = 0;
        this.labs = new Stack<IJsonLabel>();
        this.manager = new LabelWrapperManager();

        this.manager.regedit(JsonLabelType.Array);
        this.manager.regedit(JsonLabelType.Boolean);
        this.manager.regedit(JsonLabelType.Number);
        this.manager.regedit(JsonLabelType.Object);
        this.manager.regedit(JsonLabelType.Pair);
        this.manager.regedit(JsonLabelType.String);
        this.manager.regedit(JsonLabelType.Value);
        this.manager.regedit(JsonLabelType.Date);
        this.manager.regedit(JsonLabelType.Null);
    }

    public void clear()
    {
        this.root = new JsonLabelRoot("");
        this.index = 0;
        this.labs.clear();
        this.fisrt = null;
    }

    public void read(char c)
    {
    	if (this.getCurrent() == null && !isWhitespace(c)){
            IJsonLabel f = this.getFirstJsonLabel(c);
            this.fisrt = f;
            f.open(index);
            this.getQuery().push(f);
            index++;return;
        }

        if (this.getCurrent() != null){
            if (!isWhitespace(c)){
                if (this.getCurrent().getCloseQuote().equals(String.valueOf(c))){
                    if(this.getCurrent().closeEnable(index)){
                    	this.getQuery().pop().close(index);
                    	index++;return;
                    }
                }

                if (this.getCurrent().getCloseQuote().equals("") || this.getCurrent().ignoreQuote()){
                    if (!this.getCurrent().getParent().getCloseQuote().equals("")){
                        if (this.getCurrent().getParent().getCloseQuote().equals(String.valueOf(c))){
                        	this.getQuery().pop().close(index);
                        	this.getQuery().pop().close(index);
                        	index++;return;
                        }
                    }else{
                        if (this.getCurrent().getParent().getParent().getCloseQuote().equals(String.valueOf(c))){
                        	this.getQuery().pop().close(index);
                        	this.getQuery().pop().close(index);
                        	this.getQuery().pop().close(index);
                        	index++;return;
                        }
                    }

                    if(this.getCurrent().getParent().getSplit().equals(String.valueOf(c))){
                    	this.getQuery().pop().close(index);
                    	index++;return;
                    }

                    if (this.getCurrent().getParent().getCloseQuote().equals("")){
                        if (this.getCurrent().getParent().getParent().getSplit().equals(String.valueOf(c))){
                        	this.getQuery().pop().close(index);
                        	this.getQuery().pop().close(index);
                        	index++;return;
                        }
                    }
                }

                if (!this.getCurrent().getSplit().equals("") && this.getCurrent().getSplit().equals(String.valueOf(c))){
                	index++;return;
                }

                JsonLabelType jlt = this.getJsonValueType(c);
                IJsonElement ije = this.manager.getJsonElement(jlt);
                if (ije != null){
                    if (!ije.getOpenQuote().equals("")){
                        if (ije.getOpenQuote().equals(String.valueOf(c))){
                            IJsonLabel lab = JsonLabelFactroy.create(jlt);
                            lab.setRoot(this.root);
                            lab.open(index);
                            this.getCurrent().addChild(lab);
                            this.getQuery().push(lab);
                            index++;return;
                        }
                    }else{
                        IJsonLabel lab = JsonLabelFactroy.create(jlt);
                        lab.setRoot(this.root);
                        lab.open(index);
                        this.getCurrent().addChild(lab);
                        this.getQuery().push(lab);

                        JsonLabelType cjlt = this.getJsonValueType(c);
                        IJsonElement cije = this.manager.getJsonElement(cjlt);
                        if (cije != null){
                            if (cije.getOpenQuote().equals(String.valueOf(c))){
                                IJsonLabel clab = JsonLabelFactroy.create(cjlt);
                                clab.setRoot(this.root);
                                clab.open(index);
                                lab.addChild(clab);
                                this.getQuery().push(clab);
                            }else if(!isWhitespace(c)){
                                IJsonLabel clab = JsonLabelFactroy.create(cjlt);
                                clab.setRoot(this.root);
                                clab.open(index);
                                clab.ignoreQuote(true);
                                lab.addChild(clab);
                                this.getQuery().push(clab);
                            }
                        }
                        index++;return;
                    }
                }
            }
        }
        index++;
    }

    private boolean isWhitespace(char c){
        if(c==' ' || c=='\t' || c=='\n' || c=='\r'){
            return true;
        }
        return false;
    }

    private JsonLabelType getJsonValueType(char c)
    {
        JsonLabelType jlt=this.getCurrent().getCurrentChildType();
        if (jlt != JsonLabelType.Value) {
			return jlt;
		} else
        {
            switch (c)
            {
                case '{':
                    return JsonLabelType.Object;
                case '[':
                    return JsonLabelType.Array;
                case '"':
                    return JsonLabelType.String;
                case '/':
                    return JsonLabelType.Date;
                default:
                    {
                        if (Character.isDigit(c))
                        {
                            return JsonLabelType.Number;
                        }
                        else if (c == 'n')  // IE 8.0
                        {
                            return JsonLabelType.Null;
                        }
                        else {
							return JsonLabelType.Boolean;
						}
                    }
            }
        }
    }

    private IJsonLabel getFirstJsonLabel(char c)
    {
        switch (c)
        {
            case '{':
                return JsonLabelFactroy.create(JsonLabelType.Object);
            case '[':
                return JsonLabelFactroy.create(JsonLabelType.Array);
            default:
                throw new RuntimeException("无效的JsonString ["+this.getJsonString()+"]");
        }
    }
}
