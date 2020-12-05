package tpsql.test.junit.mysql;

import org.junit.Test;
import tpsql.dao.IObjectDao;
import tpsql.dao.support.ObjectDao;
import tpsql.dao.pagesize.IPageList;
import tpsql.dao.pagesize.IPageSize;
import tpsql.dao.pagesize.PageSize;
import tpsql.test.domain.PmsUser;

import java.util.HashMap;
import java.util.Map;

public class MySqlPageSizeTest extends MySqlSupport {

    @Test
    public void testPageQuery(){
        IObjectDao dao = new ObjectDao();
        IPageSize pageSize = new PageSize();
        pageSize.setPageSize(2);
        pageSize.setPageNum(2);
        Map<String,Object> condition = new HashMap();
        IPageList<PmsUser> userList= (IPageList<PmsUser>)dao.queryList("queryPmsUserList",pageSize,condition);
        System.out.println("总数:"+userList.getTotal());
        for(PmsUser pmsUser : userList){
            System.out.println("["+pmsUser.getUserName()+","+pmsUser.getUserAccount()+"]");
        }
    }

    @Test
    public void testGenAutoPkId(){
        IObjectDao dao = new ObjectDao();
        int newId1= dao.newId("PMS_USER").intValue();
        int newId2 = dao.newId(PmsUser.class).intValue();

        System.out.println(newId1);
        System.out.println(newId2);
    }

}
