package tpsql.test.junit.mysql;

import org.junit.Test;
import tpsql.core.util.DateUtil;
import tpsql.dao.IObjectDao;
import tpsql.dao.pagesize.IPageList;
import tpsql.dao.support.ObjectDao;
import tpsql.test.domain.CrmCustomer;
import tpsql.test.domain.PmsUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlQueryDateTest extends MySqlSupport {

    @Test
    public void testDateQuery(){
        IObjectDao dao = new ObjectDao();
        Map<String,Object> condition = new HashMap();
        condition.put("birthday", DateUtil.parser("2020-05-17",DateUtil.DEFAULT_DATE_FORMAT));
        condition.put("birthdayEnd", DateUtil.parser("2020-05-19",DateUtil.DEFAULT_DATE_FORMAT));
        List<CrmCustomer> list= dao.queryList("queryCrmCustomerList",condition);
        for(CrmCustomer cust : list){
            System.out.println("["+cust.getCustomerName()+","+cust.getContacterPhone()+"]");
        }
    }

}
