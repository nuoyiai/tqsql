package tpsql.test.junit.data;

import org.junit.Test;
import tpsql.collection.DataRow;
import tpsql.collection.DataTable;
import tpsql.convert.DataTableConvert;
import tpsql.convert.IDataConvert;

import java.math.BigDecimal;
import java.util.Date;

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
