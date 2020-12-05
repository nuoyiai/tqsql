package tpsql.core.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tpsql.core.collection.DataRow;
import tpsql.core.util.ReflectorUtil;
import tpsql.core.util.StringUtil;
import tpsql.core.util.TypeUtil;
import org.springframework.stereotype.Component;

public class DefaultObjectReader implements IPropertyReader<Object> {

	public Object getValue(Object obj, String propertyName) {
		if(obj!=null) {
			if (obj instanceof Map) {
				Map<?, ?> map = (Map<?, ?>) obj;
				if (map.containsKey(propertyName)) {
					return map.get(propertyName);
				} else {
					return null;
				}
			} else if (obj instanceof DataRow) {
				DataRow row = (DataRow) obj;
				return row.get(propertyName);
			} else if (TypeUtil.isValue(obj)) {
				return obj;
			} else {
				return ReflectorUtil.getPropertyValue(obj, propertyName);
			}
		}
		return null;
	}

	public Object getValue(Object entity, String propertyName, Object substitute) {
		Object val = getValue(entity,propertyName);
		return val!=null?val:substitute;
	}

	public Object getValue(Object obj, int propertyIndex) {
		if(obj instanceof List){
			List<?> list = (List<?>)obj;
			if(propertyIndex<list.size()) {
				return list.get(propertyIndex);
			} else {
				throw new RuntimeException(StringUtil.format("索引超出集合长度范围 {0} {1}", Arrays.toString(list.toArray()), propertyIndex));
			}
		}else if(TypeUtil.isArray(obj)){
			Object[] objs = ((Object[])obj);
			if(propertyIndex<objs.length){
				return objs[propertyIndex];
			}
			else {
				throw new RuntimeException(StringUtil.format("索引超出数组长度范围 {0} {1}", Arrays.toString(objs), propertyIndex));
			}
		}else if(obj instanceof DataRow){
			DataRow row = (DataRow)obj;
			row.get(propertyIndex);
		}else if(TypeUtil.isValue(obj)){
			return obj;
		}
		if(obj!=null) {
			throw new RuntimeException("对像属性不支持,索引读取");
		}
		return null;
	}

	public Object getValue(Object entity, int index, Object substitute) {
		Object val = getValue(entity,index);
		return val!=null?val:substitute;
	}

}
