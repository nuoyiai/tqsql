package tpsql.core.writer;

import tpsql.core.util.ReflectorUtil;
import tpsql.core.util.StringUtil;
import tpsql.core.util.TypeUtil;
import tpsql.core.collection.DataRow;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zhusw on 2017/9/26.
 */
@Component
public class DefaultObjectWriter implements IPropertyWriter<Object> {

	public void setValue(Object obj, String propertyName, Object value) {
        if(obj instanceof Map){
            Map<Object,Object> map = (Map<Object,Object>)obj;
            map.put(propertyName,value);
        }else if(obj instanceof DataRow){
            DataRow row = (DataRow)obj;
            row.set(propertyName,value);
        }else if(TypeUtil.isValue(obj)){
            //nothing
        }else{
            ReflectorUtil.setPropertyValue(obj, propertyName, value);
        }
    }

	public void setValue(Object obj, int propertyIndex, Object value) {
        if(obj instanceof List){
            List<Object> list = (List<Object>)obj;
            if(propertyIndex<list.size()) {
				list.set(propertyIndex, value);
			} else {
				throw new RuntimeException(StringUtil.format("索引超出集合长度范围 {0} {1}", Arrays.toString(list.toArray()), propertyIndex));
			}
        }else if(TypeUtil.isArray(obj)){
            Object[] objs = ((Object[])obj);
            if(propertyIndex<objs.length){
                objs[propertyIndex]=value;
            }
            else {
				throw new RuntimeException(StringUtil.format("索引超出数组长度范围 {0} {1}", Arrays.toString(objs), propertyIndex));
			}
        }else if(obj instanceof DataRow){
            DataRow row = (DataRow)obj;
            row.get(propertyIndex);
        }else if(TypeUtil.isValue(obj)){
            //nothing
        }else {
            if (obj != null) {
				throw new RuntimeException("对像属性不支持,索引读取");
			}
        }
    }

}
