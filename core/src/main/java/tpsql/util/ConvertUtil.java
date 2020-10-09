package tpsql.util;

import java.math.BigDecimal;
import java.util.Date;

public class ConvertUtil {

    public static byte getType(Object val){
        if(val instanceof String) {
            int len = ((String)val).length();
            if(len==0){
                return 1;
            }else {
                byte[] b = ((String)val).getBytes();
                if(b.length<Short.MAX_VALUE){
                    return 2;
                }else{
                    return 3;
                }
            }
        }else if(val instanceof Character){
            byte[] b = String.valueOf(val).getBytes();
            return (byte)(3+b.length);
        }else if(val instanceof Boolean)
            return 7;
        else if(val instanceof Integer)
            return 8;
        else if(val instanceof Long)
            return 9;
        else if(val instanceof Float)
            return 10;
        else if(val instanceof Double)
            return 11;
        else if(val instanceof Date)
            return 12;
        else if(val instanceof BigDecimal)
            return 13;
        return 0;
    }

    public static Object toObject(byte type,byte[] bytes,int pos){
        switch (type){
            case 1:
                return "";
            case 2:
            case 3:{
                int size = type==2?2:4;
                byte[] size_byte = new byte[size];
                System.arraycopy(bytes,pos,size_byte,0,size_byte.length);
                int data_size = ByteUtil.byteToShort(size_byte);
                byte[] data_byte = new byte[data_size];
                System.arraycopy(bytes,pos+2,data_byte,0,data_byte.length);
                return new String(data_byte);
            }
            case 4:
            case 5:
            case 6:{
                int data_size = type-3;
                byte[] data_byte = new byte[data_size];
                System.arraycopy(bytes,pos,data_byte,0,data_byte.length);
                return new String(data_byte).toCharArray()[0];
            }
            case 7:{
                return ByteUtil.byteToBool(bytes,pos);
            }
            case 8:{
                return ByteUtil.byteToInt(bytes,pos);
            }
            case 9:{
                return ByteUtil.byteToLong(bytes,pos);
            }
            case 10:{
                return ByteUtil.byteToFloat(bytes,pos);
            }
            case 11:{
                return ByteUtil.byteToDouble(bytes,pos);
            }
            case 12:{
                return ByteUtil.byteToDate(bytes,pos);
            }
            case 13:{
                return ByteUtil.byteToDecimal(bytes,pos);
            }
            default:
                return null;
        }
    }

    public static int getSize(Object val){
        if(val instanceof String) {
            int len = ((String)val).length();
            if(len==0){
                return 0;
            }else {
                byte[] b = ((String)val).getBytes();
                if(b.length<Short.MAX_VALUE){
                    return b.length+2;
                }else{
                    return b.length+4;
                }
            }
        }else if(val instanceof Character){
            char c = (Character)val;
            byte[] b = String.valueOf(c).getBytes();
            return b.length;
        }else if(val instanceof Integer)
            return 4;
        else if(val instanceof Long)
            return 8;
        else if(val instanceof Float)
            return 4;
        else if(val instanceof Double)
            return 8;
        else if(val instanceof Date)
            return 8;
        else if(val instanceof Boolean)
            return 1;
        else if(val instanceof BigDecimal)
            return 9;
        return 0;
    }

    public static byte[] toByte(Object val){
        if(val instanceof String) {
            byte[] b = ((String)val).getBytes();
            if(b.length<Short.MAX_VALUE){
                byte[] nb = new byte[b.length+2];
                byte[] sb = ByteUtil.shortToByte((short)b.length);
                System.arraycopy(sb,0,nb,0,2);
                System.arraycopy(b,0,nb,2,b.length);
                return nb;
            }else{
                byte[] nb = new byte[b.length+4];
                byte[] sb = ByteUtil.intToByte(b.length);
                System.arraycopy(sb,0,nb,0,4);
                System.arraycopy(b,0,nb,4,b.length);
                return nb;
            }
        }else if(val instanceof Character){
            return String.valueOf(val).getBytes();
        }else if(val instanceof Integer)
            return ByteUtil.intToByte((Integer)val);
        else if(val instanceof Long)
            return ByteUtil.longToByte((Long)val);
        else if(val instanceof Float)
            return ByteUtil.floatToByte((Float)val);
        else if(val instanceof Double)
            return ByteUtil.doubleToByte((Double)val);
        else if(val instanceof BigDecimal)
            return ByteUtil.decimalToByte((BigDecimal)val);
        else if(val instanceof Date)
            return ByteUtil.dateToByte((Date)val);
        else if(val instanceof Boolean)
            return ByteUtil.boolToByte((Boolean)val);
        return null;
    }

    public static boolean isValueType(Object val){
        if(val instanceof String) {
            return true;
        }else if(val instanceof Integer)
            return true;
        else if(val instanceof Long)
            return true;
        else if(val instanceof Float)
            return true;
        else if(val instanceof Double)
            return true;
        else if(val instanceof Date)
            return true;
        else if(val instanceof Boolean)
            return true;
        else if(val instanceof Character)
            return true;
        else if(val instanceof BigDecimal)
            return true;
        return false;
    }

}
