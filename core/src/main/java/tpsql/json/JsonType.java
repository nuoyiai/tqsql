package tpsql.json;

import tpsql.util.TypeUtil;

import java.util.Date;
import java.util.List;

public class JsonType {
	public static Class<?> getValueType(JsonValue jv) {
		switch(jv.getType())
		{
			case String:
				return String.class;
			case Boolean:
				return Boolean.class;
			case Null:
				return null;
			case Date:
				return Date.class;
			case Number:
				JsonNumber jn = (JsonNumber)jv;
				return TypeUtil.getValueType(jn.getValue());
			case Array:
				return List.class;
			case Object:
				return Object.class;
			default:
				throw new RuntimeException("此JsonValue类型不能转化为值 ");
		}
	}
	
	public static JsonValueType getJsonType(Object obj) {
		if(obj instanceof Number)
		{
			return JsonValueType.Number;
		}
        else if (obj instanceof String)
        {
        	String str = (String)obj;
            if (str != null && str.length() >= 2)
            {
                if (str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']') {
					return JsonValueType.Array;
				} else if (str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') {
					return JsonValueType.Object;
				}
            }
            return JsonValueType.String;
        }
        else if (obj instanceof Date)
        {
            return JsonValueType.Date;
        }
        else if (obj instanceof Boolean)
        {
            return JsonValueType.Boolean;
        }
        else if (obj == null)
        {
            return JsonValueType.Null;
        }
        else {
			return JsonValueType.Undefine;
		}
	}

	public static boolean isValue(JsonValue jv) {
		switch(jv.getType())
		{
			case String:
			case Boolean:
			case Date:
			case Number:
				return true;
			default:
				return false;
		}
	}
}
