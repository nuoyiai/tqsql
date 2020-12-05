package tpsql.test.junit.data;

import org.junit.Test;
import tpsql.core.collection.DataRow;
import tpsql.core.collection.DataTable;
import tpsql.core.convert.DataTableConvert;
import tpsql.core.convert.IDataConvert;

public class DataTableTest {

    @Test
    public void testDataTableOperation(){
        DataTable dataTable = new DataTable();
        dataTable.addColumn("NAME","AGE","SEX");
        dataTable.newRow().set("NAME","小李").set("AGE",30).set("SEX","男");
        dataTable.newRow().set("NAME","小红").set("AGE",25).set("SEX","女");

        for(DataRow row : dataTable){
            System.out.println(row);
        }
    }



}
