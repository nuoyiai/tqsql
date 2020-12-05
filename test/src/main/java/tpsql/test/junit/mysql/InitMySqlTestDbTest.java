package tpsql.test.junit.mysql;

import org.junit.Test;
import tpsql.core.collection.DataRow;
import tpsql.core.collection.DataTable;
import tpsql.dao.support.DataDao;
import tpsql.dao.IDataDao;
import tpsql.dao.IObjectDao;
import tpsql.dao.support.ObjectDao;
import tpsql.sql.util.ISqlEngine;
import tpsql.sql.util.SqlEngine;
import tpsql.test.domain.PmsRole;
import tpsql.test.domain.PmsUser;
import tpsql.test.domain.PmsUserRoles;
import tpsql.test.query.PmsUserQuery;

import java.util.*;


public class InitMySqlTestDbTest extends MySqlSupport {

    @Test
    public void testInitMySqlDataBase(){
        ISqlEngine ise = SqlEngine.instance();

        ise.executeNoneQuery("mysql_drop_table_crm_customer","");
        ise.executeNoneQuery("mysql_drop_table_pms_user","");
        ise.executeNoneQuery("mysql_drop_table_pms_user_roles","");
        ise.executeNoneQuery("mysql_drop_table_pms_role","");
        ise.executeNoneQuery("mysql_drop_table_test_a","");

        ise.executeNoneQuery("mysql_create_table_crm_customer","");
        ise.executeNoneQuery("mysql_create_table_pms_user","");
        ise.executeNoneQuery("mysql_create_table_pms_user_roles","");
        ise.executeNoneQuery("mysql_create_table_pms_role","");
        ise.executeNoneQuery("mysql_create_table_test_a","");

        ise.dispose();
    }

    @Test
    public void testBatchInsertMySqlTableData(){

        IObjectDao dao = new ObjectDao();

        List<PmsUser> userList = new ArrayList();
        int user_count = 3;
        Long[] user_ids = dao.newId(PmsUser.class,user_count);
        for(int i=0;i<user_count;i++){
            PmsUser pmsUser = new PmsUser();
            pmsUser.setUserId(user_ids[i].intValue());
            pmsUser.setUserName("测试"+(i+1));
            pmsUser.setUserAccount("TEST_"+(i+1));
            pmsUser.setIsDel(false);
            userList.add(pmsUser);
        }
        dao.execute("deleteAllPmsUser","");
        dao.executes("insertPmsUser",userList);

        List<PmsRole> roleList = new ArrayList();
        int role_count = 2;
        Long[] role_ids = dao.newId(PmsUser.class,role_count);
        for(int i=0;i<role_count;i++){
            PmsRole pmsRole = new PmsRole();
            pmsRole.setRoleId(role_ids[i].intValue());
            pmsRole.setRoleName("角色"+(i+1));
            pmsRole.setRoleCreateDate(new Date());
            roleList.add(pmsRole);
        }
        dao.execute("deleteAllPmsRole","");
        dao.executes("insertPmsRole",roleList);

        int user_role_count = userList.size() * roleList.size();
        Long[] user_role_ids = dao.newId(PmsUser.class,user_role_count);
        List<PmsUserRoles> userRoleList = new ArrayList();
        int index = 0;
        for(int i=0;i<userList.size();i++){
            for(int j=0;j<roleList.size();j++){
                PmsUserRoles pmsUserRoles = new PmsUserRoles();
                pmsUserRoles.setPurId(user_role_ids[index].intValue());
                pmsUserRoles.setPurRoleId(roleList.get(j).getRoleId());
                pmsUserRoles.setPurUserId(userList.get(i).getUserId());
                userRoleList.add(pmsUserRoles);
                index++;
            }
        }
        dao.execute("deleteAllPmsUserRoles","");
        dao.executes("insertPmsUserRoles",userRoleList);

        Map<String,Object> condition = new HashMap();
        List<PmsUserQuery> list = dao.queryList("testQueryPmsUser",condition);
        for(PmsUserQuery pmsUserQuery : list){
            System.out.println("{userName:\""+pmsUserQuery.getUserName()+"\",roleIds:\""+pmsUserQuery.getRoleIds()+"\",roldNames:\""+pmsUserQuery.getRoleNames()+"\"}");
        }
    }

    @Test
    public void testReturnDataTable(){
        IDataDao dao = new DataDao();
        DataTable dataTable = dao.queryData("queryPmsRoleList");
        for(DataRow row : dataTable){
            System.out.println("["+row.get("ROLE_ID")+","+row.get("ROLE_NAME")+"]");
        }
    }

    @Test
    public void testLinkQuery(){
        IObjectDao dao = new ObjectDao();
        Map<String,Object> condition = new HashMap();
        List<PmsUserQuery> list = dao.queryList("testQueryPmsUser",condition);
        for(PmsUserQuery pmsUserQuery : list){
            System.out.println("{userName:\""+pmsUserQuery.getUserName()+"\"}");
            for(PmsRole role : pmsUserQuery.getRoleList()){
                System.out.println("    {roleId:"+role.getRoleId()+",roleName:\""+role.getRoleName()+"\",createTime:"+role.getRoleCreateDate()+"}");
            }
        }
    }

    @Test
    public void testLinkFisrtOne(){
        IObjectDao dao = new ObjectDao();
        Map<String,Object> condition = new HashMap();
        List<PmsUserQuery> list = dao.queryList("testQueryPmsUser",condition);
        for(PmsUserQuery pmsUserQuery : list){
            System.out.println("{userName:\""+pmsUserQuery.getUserName()+"\"}");
            System.out.println("    {roleId:"+pmsUserQuery.getRole().getRoleId()+",roleName:\""+pmsUserQuery.getRole().getRoleName()+"\"}");
        }
    }

}
