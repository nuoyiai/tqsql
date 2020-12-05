package tpsql.test.junit.mysql;

import org.junit.Test;
import tpsql.dao.IObjectDao;
import tpsql.dao.support.ObjectDao;
import tpsql.test.domain.PmsRole;
import tpsql.test.query.PmsUserQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlCacheTest extends MySqlSupport {

    @Test
    public void testCacheReturnDataTable(){
        IObjectDao dao = new ObjectDao();
        Map<String,Object> condition = new HashMap();
        List<PmsUserQuery> list = dao.queryList("testQueryPmsUserByCache",condition);
        for(PmsUserQuery pmsUserQuery : list){
            System.out.println("{userName:\""+pmsUserQuery.getUserName()+"\",roleIds:\""+pmsUserQuery.getRoleIds()+"\",roldNames:\""+pmsUserQuery.getRoleNames()+"\"}");
            for(PmsRole role : pmsUserQuery.getRoleList()){
                System.out.println("    {roleId:"+role.getRoleId()+",roleName:\""+role.getRoleName()+"\",createTime:"+role.getRoleCreateDate()+"}");
            }
        }
    }

}
