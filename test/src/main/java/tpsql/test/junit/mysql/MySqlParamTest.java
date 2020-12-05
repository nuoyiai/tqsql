package tpsql.test.junit.mysql;

import org.junit.Test;
import tpsql.core.collection.DataTable;
import tpsql.dao.support.DataDao;
import tpsql.dao.IDataDao;
import tpsql.test.domain.test.TestEntry3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlParamTest extends MySqlSupport {

    @Test
    public void testSqlParams1(){
        IDataDao dao = new DataDao();
        TestEntry3 testEntry3 = buildTestEntry3();
        DataTable table = dao.queryData("testParam1",testEntry3);
        System.out.println(table);
    }

    @Test
    public void testSqlParams2(){
        IDataDao dao = new DataDao();
        TestEntry3 testEntry3 = buildTestEntry3();
        DataTable table = dao.queryData("testParam2",testEntry3);
        System.out.println(table);
    }

    @Test
    public void testSqlParams3(){
        IDataDao dao = new DataDao();
        TestEntry3 testEntry3 = buildTestEntry3();
        TestEntry3 testEntry4 = buildTestEntry4();
        DataTable table = dao.queryData("testParam3",testEntry3,testEntry4);
        System.out.println(table);
    }

    public TestEntry3 buildTestEntry3(){
        TestEntry3 testEntry3 = new TestEntry3();
        testEntry3.setParam1("1");
        testEntry3.setParam2(new String[]{"2","3"});
        List<String> param3 = new ArrayList();
        param3.add("4");
        param3.add("5");
        testEntry3.setParam3(param3);
        Map<String,String> param4 = new HashMap();
        param4.put("key1","6");
        param4.put("key2","7");
        testEntry3.setParam4(param4);

        List<TestEntry3> param5 = new ArrayList();
        param5.add(new TestEntry3());
        param5.get(0).setParam1("8");
        testEntry3.setParam5(param5);

        TestEntry3 param6 = new TestEntry3();
        param6.setParam4(new HashMap<String, String>());
        param6.getParam4().put("key1","9");
        testEntry3.setParam6(param6);

        return testEntry3;
    }

    public TestEntry3 buildTestEntry4(){
        TestEntry3 testEntry3 = new TestEntry3();
        testEntry3.setParam1("11");
        testEntry3.setParam2(new String[]{"22","33"});
        List<String> param3 = new ArrayList();
        param3.add("44");
        param3.add("55");
        testEntry3.setParam3(param3);
        Map<String,String> param4 = new HashMap();
        param4.put("key1","66");
        param4.put("key2","77");
        testEntry3.setParam4(param4);

        List<TestEntry3> param5 = new ArrayList();
        param5.add(new TestEntry3());
        param5.get(0).setParam1("88");
        testEntry3.setParam5(param5);

        TestEntry3 param6 = new TestEntry3();
        param6.setParam4(new HashMap<String, String>());
        param6.getParam4().put("key1","99");
        testEntry3.setParam6(param6);

        return testEntry3;
    }

}
