package tpsql.core.convert;

import tpsql.core.util.ByteUtil;
import tpsql.core.util.ConvertUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataListConvert implements IDataConvert<List> {
    private IDataConvert<Object> dataObjectConvert = new DataObjectConvert();

    public byte[] toBytes(List list) {
        if(list!=null){
            int size = list.size();
            byte[] size_byte = ByteUtil.intToByte(size);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                baos.write(size_byte);
                byte[] bytes;
                boolean valueFlag;
                for(Object o : list){
                    valueFlag = ConvertUtil.isValueType(o);
                    if (valueFlag) {
                        baos.write((byte)1);
                        bytes = ConvertUtil.toByte(o);
                        baos.write(bytes);
                    }else {
                        baos.write((byte)9);
                        bytes = dataObjectConvert.toBytes(o);
                        size_byte = ByteUtil.intToByte(bytes.length);
                        baos.write(size_byte);
                        baos.write(bytes);
                    }
                }
            }catch(Exception e){

            }
            return baos.toByteArray();
        }
        return new byte[0];
    }

    public List toData(byte[] bytes) {
        if(bytes!=null && bytes.length>0) {
            int size = ByteUtil.byteToInt(bytes,0);
            byte type;
            int pos = 4;
            Object val;
            List list = new ArrayList();
            for(int i=0;i<size;i++) {
                type = bytes[pos];pos++;
                switch (type) {
                    case 1: {
                        val = ConvertUtil.toObject(type, bytes, pos);
                        pos+=ConvertUtil.getSize(val);
                        list.add(val);
                        break;
                    }
                    case 9: {
                        int data_size = ByteUtil.byteToInt(bytes,pos);pos+=4;
                        byte[] b = new byte[data_size];
                        System.arraycopy(bytes, pos, b, 0, b.length);
                        pos+=b.length;
                        val = dataObjectConvert.toData(b);
                        list.add(val);
                        break;
                    }
                }
            }
            return list;
        }
        return null;
    }

}
