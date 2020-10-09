package tpsql.test.junit.data;

import org.junit.Test;
import tpsql.collection.DataTable;
import tpsql.convert.*;
import tpsql.test.domain.test.TestEntry1;
import tpsql.test.domain.test.TestEntry2;
import tpsql.test.util.PrintUtil;
import tpsql.util.ByteUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataConvertTest {

    @Test
    public void testTypeConvert(){
        Object i = 123;
        if(i instanceof Integer){
            byte[] b = ByteUtil.intToByte((Integer)i);
            System.out.print(b.length);
        }
    }

    @Test
    public void testDataTableConvert(){
        IDataConvert<DataTable> dataTableConvert = new DataTableConvert();
        DataTable dataTable = getTestTable();
        byte[] bytes = dataTableConvert.toBytes(dataTable);
        DataTable table = dataTableConvert.toData(bytes);
        System.out.println(table);
    }

    private DataTable getTestTable(){
        DataTable dataTable = new DataTable();
        dataTable.addColumn("STRING","LONG","FLOAT","INTEGER","DOUBLE","DECIMAL","DATE","BOOL","CHAR");
        for(int i=0;i<2;i++) {
            dataTable.newRow().set("STRING", "小李").set("LONG", 1093223432434L).set("FLOAT", 02323432.233F).set("INTEGER", 123)
                    .set("DOUBLE", 23423323424334.32434434).set("DECIMAL", new BigDecimal(1230123123233.3234))
                    .set("DATE", new Date()).set("BOOL",true).set("CHAR",'中');
        }
        return dataTable;
    }

    @Test
    public void testDataObjectConvert(){
        IDataConvert<Object> dataObjectConvert = new DataObjectConvert();
        byte[] bytes = dataObjectConvert.toBytes(buildTestEntry1());
        Object obj = dataObjectConvert.toData(bytes);
        PrintUtil.printEntry(obj);

        bytes = dataObjectConvert.toBytes(buildTestEntry2());
        obj = dataObjectConvert.toData(bytes);
        PrintUtil.printEntry(obj);
    }

    @Test
    public void testDataListConvert(){
        IDataConvert<List> dataListConvert = new DataListConvert();
        List list = getTestList();
        byte[] bytes = dataListConvert.toBytes(list);
        list = dataListConvert.toData(bytes);
        for(Object obj : list){
            PrintUtil.printEntry(obj);
        }
    }

    private List getTestList(){
        List list = new ArrayList();
        for(int i=0;i<2;i++){
            list.add(buildTestEntry1());
        }
        return list;
    }

    @Test
    public void testDataConvert(){
        IDataConvert<Object> dataConvert = new DataConvert();
        Object intVal = 123;
        DataTable dataTable = getTestTable();
        TestEntry1 entry1 = buildTestEntry1();
        List list = getTestList();

        byte[] bytes = dataConvert.toBytes(intVal);
        intVal = dataConvert.toData(bytes);
        System.out.println(intVal);

        bytes = dataConvert.toBytes(dataTable);
        dataTable = (DataTable) dataConvert.toData(bytes);
        System.out.println(dataTable);

        bytes = dataConvert.toBytes(entry1);
        Object obj = dataConvert.toData(bytes);
        PrintUtil.printEntry(obj);

        bytes = dataConvert.toBytes(list);
        list = (List)dataConvert.toData(bytes);
        for(Object o : list){
            PrintUtil.printEntry(o);
        }
    }

    private TestEntry1 buildTestEntry1(){
        TestEntry1 entry1 = new TestEntry1();
        entry1.setFbigdecimal(new BigDecimal(1230123123233.3234));
        entry1.setFboolean(true);
        entry1.setFcharacter('男');
        entry1.setFdate(new Date());
        entry1.setFdouble(23423455445.444);
        entry1.setFfloat(23024344.33F);
        entry1.setFlong(23498234843L);
        entry1.setFinteger(3333);
        entry1.setFstring("测试Object 行不行");
        return entry1;
    }

    private TestEntry2 buildTestEntry2(){
        TestEntry2 entry2 = new TestEntry2();
        entry2.setFboolean(true);
        entry2.setFcharacter('男');
        entry2.setFdouble(23423455445.444);
        entry2.setFfloat(23024344.33F);
        entry2.setFlong(23498234843L);
        entry2.setFinteger(3333);
        return entry2;
    }

}
