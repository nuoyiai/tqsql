package tpsql.json;

import java.util.Date;

public class JsonDate extends JsonValue {
	private Date time;
    private boolean quoteEnable;

    public Date getValue()
    {
    	return this.time;
    }
    
    public void setValue(Date value)
    {
    	this.time=value;
    }

    public boolean isQuoteEnable() {
		return quoteEnable;
	}

	public void setQuoteEnable(boolean quoteEnable) {
		this.quoteEnable = quoteEnable;
	}

	public JsonDate()
    {
		this.type = JsonValueType.Date;
    }

    public JsonDate(Date t)
    {
        this.type = JsonValueType.Date;
        this.openQuote =this.closeQuote= "/";
        this.quoteEnable = true;
        this.time = t;
    }

    @Override
	public String toString()
    {
    	String d = "";
        if (this.time != null)
        {
        	this.time.getTime();
        	/*
        	Calendar cal = Calendar.getInstance(); 
        	cal.setTime(this.time);
        	int year=cal.get(Calendar.YEAR);
        	int month=cal.get(Calendar.MONTH);
        	int day=cal.get(Calendar.DATE);
        	d = "new Date("+year+","+month+","+day+")";
        	*/
        	d = "new Date("+this.time.getTime()+")";
        }
        return this.format(d);
    }
}
