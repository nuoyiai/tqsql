package tpsql.convert;

import tpsql.collection.DataTable;
import tpsql.util.ConvertUtil;

import java.util.List;

public class DataConvert implements IDataConvert<Object> {
    private IDataConvert<DataTable> dataTableConvert = new DataTableConvert();
    private IDataConvert<Object> dataObjectConvert = new DataObjectConvert();
    private IDataConvert<List> dataListConvert = new DataListConvert();

    @Override
    public byte[] toBytes(Object o) {
        if(o!=null) {
            boolean valueFlag = ConvertUtil.isValueType(o);
            if (valueFlag) {
                byte[] b = ConvertUtil.toByte(o);
                byte type = ConvertUtil.getType(o);
                byte[] bytes = new byte[b.length + 2];
                bytes[0] = 1;
                bytes[1] = type;
                System.arraycopy(b, 0, bytes, 2, b.length);
                return bytes;
            } else if (o instanceof DataTable) {
                byte[] b = dataTableConvert.toBytes((DataTable) o);
                byte[] bytes = new byte[b.length + 1];
                bytes[0] = 2;
                System.arraycopy(b, 0, bytes, 1, b.length);
                return bytes;
            } else if(o instanceof List){
                byte[] b = dataListConvert.toBytes((List)o);
                byte[] bytes = new byte[b.length + 1];
                bytes[0] = 3;
                System.arraycopy(b, 0, bytes, 1, b.length);
                return bytes;
            }else {
                byte[] b = dataObjectConvert.toBytes(o);
                byte[] bytes = new byte[b.length + 1];
                bytes[0] = 9;
                System.arraycopy(b, 0, bytes, 1, b.length);
                return bytes;
            }
        }else {
            return new byte[0];
        }
    }

    @Override
    public Object toData(byte[] bytes) {
        if(bytes!=null && bytes.length>0) {
            byte type = bytes[0];
            switch (type) {
                case 1: {
                    return ConvertUtil.toObject(bytes[1], bytes, 2);
                }
                case 2: {
                    byte[] b = new byte[bytes.length - 1];
                    System.arraycopy(bytes, 1, b, 0, b.length);
                    return dataTableConvert.toData(b);
                }
                case 3: {
                    byte[] b = new byte[bytes.length - 1];
                    System.arraycopy(bytes, 1, b, 0, b.length);
                    return dataListConvert.toData(b);
                }
                case 9: {
                    byte[] b = new byte[bytes.length - 1];
                    System.arraycopy(bytes, 1, b, 0, b.length);
                    return dataObjectConvert.toData(b);
                }
                default:
                    return null;
            }
        }
        return null;
    }

}
