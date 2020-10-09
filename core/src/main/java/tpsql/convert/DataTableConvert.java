package tpsql.convert;

import tpsql.collection.DataColumn;
import tpsql.collection.DataRow;
import tpsql.collection.DataTable;
import tpsql.util.ByteUtil;
import tpsql.util.ConvertUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataTableConvert implements IDataConvert<DataTable> {
    private static final int ROW_SIZE_POS = 0;
    private static final int COL_SIZE_POS = 4;
    private static final int HEAD_SIZE_POS = 8;

    /**
     * 转化成二进制数组
     * @param dataTable
     * @return
     */
    @Override
    public byte[] toBytes(DataTable dataTable) {
        return tableToByte(dataTable);
    }

    /**
     * 二进制数组转化成数据对象
     * @param bytes
     * @return
     */
    @Override
    public DataTable toData(byte[] bytes) {
        byte[] row_size_byte = new byte[4];
        byte[] col_size_byte = new byte[4];
        byte[] head_size_byte = new byte[4];
        System.arraycopy(bytes,ROW_SIZE_POS,row_size_byte,0,row_size_byte.length);
        System.arraycopy(bytes,COL_SIZE_POS,col_size_byte,0,col_size_byte.length);
        System.arraycopy(bytes,HEAD_SIZE_POS,head_size_byte,0,head_size_byte.length);
        int rowSize = ByteUtil.byteToInt(row_size_byte);
        int colSize = ByteUtil.byteToInt(col_size_byte);
        int headSize = ByteUtil.byteToInt(head_size_byte);

        byte[] head_data_byte = new byte[headSize];
        int head_data_pos =  HEAD_SIZE_POS+head_size_byte.length;
        System.arraycopy(bytes,head_data_pos,head_data_byte,0,head_data_byte.length);
        String[] headNames = byteToHead(head_data_byte);
        DataTable table = new DataTable();
        table.addColumn(headNames);
        DataRow row;
        byte type;
        Object val;
        int pos = head_data_pos+headSize;
        for (int r = 0; r < rowSize; r++) {
            row = table.newRow();
            for (int c = 0; c < colSize; c++) {
                type = bytes[pos];
                pos++;
                val = ConvertUtil.toObject(type,bytes,pos);
                int byte_size = ConvertUtil.getSize(val);
                pos+=byte_size;
                row.set(c,val);
            }
        }
        return table;
    }

    private byte[] tableToByte(DataTable table){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object val;
        DataRow row;
        try {
            baos.write(ByteUtil.intToByte(table.rowSize()));
            baos.write(ByteUtil.intToByte(table.colSize()));

            byte[] h_bytes = headToByte(table);
            baos.write(ByteUtil.intToByte(h_bytes.length));
            baos.write(h_bytes);

            byte[] val_byte;
            for (int r = 0; r < table.rowSize(); r++) {
                row = table.getRow(r);
                for (int c = 0; c < table.colSize(); c++) {
                    val = row.get(c);
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
            }
        }catch(IOException e){

        }
        return baos.toByteArray();
    }

    private byte[] headToByte(DataTable table){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            for(DataColumn column : table.getColumns()){
                String columnName = column.getName();
                byte b_type = ConvertUtil.getType(columnName);
                baos.write(b_type);
                baos.write(ConvertUtil.toByte(columnName));
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private String[] byteToHead(byte[] bytes) {
        byte type;
        int pos = 0;
        List<String> list = new ArrayList();
        while (pos < bytes.length) {
            type = bytes[pos];
            pos++;
            Object val = ConvertUtil.toObject(type, bytes, pos);
            int byte_size = ConvertUtil.getSize(val);
            pos += byte_size;
            list.add((String) val);
        }
        return list.toArray(new String[0]);
    }

}
