package tpsql.sql.mapping;

import tpsql.sql.SqlMapException;
import tpsql.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主从表连接分组，把SqlId和主键都一样的分一组，减少SQL查询次数
 * Created by zhusw on 2017/9/13.
 */
public class SqlJoinGroup {
    private String sqlId;               //SQL ID
    private String fkey;                //从表的连接字段
    private List<SqlJoin> joins;        //组内连接

    /**
     * 连接分组构造函数
     * @param sqlId SQL ID
     * @param fkey 从表的连接字段
     */
    public SqlJoinGroup(String sqlId, String fkey){
        this.sqlId = sqlId;
        this.fkey = fkey;
        this.joins = new ArrayList<SqlJoin>();
    }

    /**
     * 把连接对像添加到分组
     * @param sqlJoin 连接对像
     */
    public void addSqlJoin(SqlJoin sqlJoin){
        if(sqlJoin !=null) {
            if (sqlId.equals(sqlJoin.getSqlId()) && fkey.equals(sqlJoin.getFkey())) {
                this.joins.add(sqlJoin);
            }else{
                throw new SqlMapException(StringUtil.format("添加到分组的SQLID和FKEY必需相同 {0} {1} {2} {3} ",sqlId,fkey, sqlJoin.getSqlId(), sqlJoin.getFkey()));
            }
        }
    }

    public String getKey(){
        return sqlId+"_"+fkey;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }

    public List<SqlJoin> getJoins() {
        return joins;
    }

    public void setJoins(List<SqlJoin> joins) {
        this.joins = joins;
    }
}
