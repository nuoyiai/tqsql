package tpsql.test.junit.mysql;

import org.junit.Test;
import tpsql.dao.IObjectDao;
import tpsql.dao.support.ObjectDao;
import tpsql.test.domain.CrmCustomer;
import tpsql.test.domain.PmsUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlBatchInsertTest extends MySqlSupport {

    @Test
    public void testBatchInsertCustomers(){
        IObjectDao dao = new ObjectDao();
        List<CrmCustomer> customerList = new ArrayList();
        int cust_count = 2;
        Long[] role_ids = dao.newId(PmsUser.class,cust_count);
        for(int i=0;i<cust_count;i++){
            CrmCustomer crmCustomer = new CrmCustomer();
            crmCustomer.setCustId(role_ids[i].intValue());
            crmCustomer.setCustomerName("测试"+i);
            crmCustomer.setBirthday(new Date());
            customerList.add(crmCustomer);
        }
        dao.execute("batchInsertCustomer",customerList);
    }

    @Test
    public void testInsertCustomerList(){
        IObjectDao dao = new ObjectDao();
        List<CrmCustomer> customerList = new ArrayList();
        int cust_count = 2;
        Long[] role_ids = dao.newId(PmsUser.class,cust_count);
        for(int i=0;i<cust_count;i++){
            CrmCustomer crmCustomer = new CrmCustomer();
            crmCustomer.setCustId(role_ids[i].intValue());
            crmCustomer.setCustomerName("测试"+i);
            crmCustomer.setBirthday(new Date());
            customerList.add(crmCustomer);
        }
        dao.executes("insertCrmCustomer",customerList);
    }

}