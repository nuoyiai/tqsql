package tpsql.core.convert;

import tpsql.core.util.ByteUtil;
import tpsql.core.util.ConvertUtil;
import tpsql.core.util.ReflectorUtil;

import tpsql.core.reflector.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DataObjectConvert implements IDataConvert<Object> {

    public byte[] toBytes(Object o) {
        if(o!=null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String className = o.getClass().getName();
            byte type = ConvertUtil.getType(className);
            byte[] class_byte = ConvertUtil.toByte(className);
            PropertyInfo[] pis = ReflectorUtil.getProperties(o.getClass());
            try {
                baos.write(type);
                baos.write(class_byte);
                byte[] prop_size = ByteUtil.shortToByte((short) pis.length);
                baos.write(prop_size);
                byte[] name_byte;
                byte[] val_byte;
                Object val;
                for(PropertyInfo pi : pis) {
                    String propName = pi.getName();
                    type = ConvertUtil.getType(propName);
                    baos.write(type);
                    name_byte = ConvertUtil.toByte(propName);
                    baos.write(name_byte);
                    val = pi.getValue(o);
                    byte b_type = ConvertUtil.getType(val);
                    baos.write(b_type);
                    if(b_type>1){
                        val_byte = ConvertUtil.toByte(val);
                        if(val_byte!=null) {
                            baos.write(val_byte);
                        }else{
                            throw new ConvertException("ConvertUtil 未实现的处理类型:"+val.getClass());
                        }
                    }
                }
            } catch (IOException e) {

            }
            return baos.toByteArray();
        }
        return new byte[0];
    }
    
    public Object toData(byte[] bytes) {
        int pos = 0;
        byte type = bytes[pos];pos++;
        String className = (String)ConvertUtil.toObject(type,bytes,pos);
        pos += ConvertUtil.getSize(className);
        int prop_size = (int)ByteUtil.byteToShort(bytes,pos);
        pos +=2;
        try {
            Object obj = ReflectorUtil.newInstance(Class.forName(className));
            Object val;
            String propName;
            for (int i = 0; i < prop_size; i++) {
                type = bytes[pos];pos++;
                propName = (String)ConvertUtil.toObject(type,bytes,pos);
                pos += ConvertUtil.getSize(propName);
                type = bytes[pos];pos++;
                val = ConvertUtil.toObject(type,bytes,pos);
                int byte_size = ConvertUtil.getSize(val);
                pos+=byte_size;
                ReflectorUtil.setPropertyValue(obj,propName,val);
            }
            return obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
