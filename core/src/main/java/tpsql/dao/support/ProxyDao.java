package tpsql.dao.support;

import tpsql.core.util.ReflectorUtil;
import tpsql.core.util.StringUtil;
import tpsql.dao.IProxyDao;
import tpsql.dao.pagesize.IPageList;
import tpsql.dao.pagesize.IPageSize;
import tpsql.dao.pagesize.PageList;
import tpsql.dao.pagesize.PageSizeHelper;
import tpsql.sql.SqlMapException;
import tpsql.core.collection.DataRow;
import tpsql.core.collection.DataTable;
import tpsql.dao.entity.EntityUtil;
import tpsql.sql.mapping.SqlItem;
import tpsql.sql.util.ISqlEngine;
import tpsql.sql.util.SqlEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProxyDao extends DataDao implements IProxyDao {

    /**
     * 查询并返回实体对像集合
     * @param sqlId
     * @param conditions 条件数据
     * @return
     */
    @Override
    public List<?> queryList(String sqlId, Object ... conditions){
        return this.queryList(sqlId, -1, -1, conditions);
    }

    /**
     * 分页查询并返回实体对像集合
     * @param sqlId
     * @param pageSize 第页多少记录
     * @param pageNum 第几页
     * @param conditions 条件数据
     * @return
     */
    @Override
    public List<?> queryList(String sqlId, int pageSize, int pageNum, Object ... conditions){
        List<Object> list = new ArrayList<Object>();
        this.queryToList(list, sqlId, pageSize, pageNum, conditions);
        return list;
    }



    /**
     * 分页查询并返回实体对像集合
     * @param sqlId	查询数量的sqlId
     * @param countSqlId 查询总数的sqlId
     * @param page 分页对像
     * @param conditions 条件数据
     * @return
     */
    @Override
    public IPageList<?> queryList(String sqlId, String countSqlId, IPageSize page, Object ... conditions){
        int count = this.queryInt(countSqlId, conditions);
        IPageList<Object> pagelist = new PageList<Object>(count);
        page.setTotal(count);
        this.queryToList(pagelist,sqlId,page.getPageSize(),page.getPageNum(),conditions);
        return pagelist;
    }

    /**
     * 分页查询并返回实体对像集合
     * @param sqlId 查询数量的sqlId
     * @param page 分页对像
     * @param conditions 条件数据
     * @return
     */
    @Override
    public IPageList<?> queryList(String sqlId, IPageSize page, Object ... conditions){
        int count = this.queryCount(sqlId, conditions);
        IPageList<Object> pagelist = new PageList<Object>(count);
        page.setTotal(count);
        page.setPageSize(page.getPageSize());
        this.queryToList(pagelist,sqlId,page.getPageSize(),page.getPageNum(),conditions);
        return pagelist;
    }

    /**
     * 分页查询返回实体对像集合
     * @param list
     * @param sqlId
     * @param pageSize
     * @param pageNum
     * @param conditions
     */
    private void queryToList(List<Object> list,String sqlId,int pageSize,int pageNum,Object ... conditions){
        ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
        if(pageSize>0 && pageNum > 0){
            int limit = PageSizeHelper.getLimit(pageSize, pageNum);
            int offset = PageSizeHelper.getOffset(pageSize, pageNum);
            list.addAll(ish.getProxyList(sqlId,offset,limit,conditions));
        }
        else{
            list.addAll(ish.getProxyList(sqlId,conditions));
        }
        ish.dispose();
    }

    /**
     *
     * @param sqlId
     * @param top
     * @param conditions
     * @return
     */
    public List<?> queryList(String sqlId,int top,Object ... conditions){
        return null;
    }

    /**
     *
     * @param sqlId
     * @param conditions
     * @return
     */
    @Override
    public Object queryObject(String sqlId, Object ... conditions){
        DataTable table = this.queryData(sqlId, conditions);
        Class<?> type = this.getResultClass(sqlId);
        if (table.rowSize() > 0) {
            DataRow row = table.getRow(0);
            Object object = ReflectorUtil.newInstance(type);
            EntityUtil.copyProperties(type, object, row);
            EntityUtil.copyProperties(object, row ,this.getPropertyMap(sqlId));
            return object;
        }
        return null;
    }

    /**
     * 产生一个新的唯一id
     * @param tableName	表名
     * @return
     */
    @Override
    public Long newId(String tableName){
        return this.getIdGenerator().getId(tableName,this.dbConfig);
    }

    /**
     * 产生一个新的唯一id
     * @param tableName	表名
     * @return
     */
    @Override
    public Long newId(String tableName, String pk){
        return this.getIdGenerator().getPkId(tableName,pk,this.dbConfig);
    }

    /**
     * 产生一个新的唯一id
     * @param type 实体类
     * @return
     */
    @Override
    public Long newId(Class<?> type){
        return this.getIdGenerator().getId(type,this.dbConfig);
    }

    /**
     * 产生一批新的唯一id
     * @param tableName 表名
     * @param num 数量
     * @return
     */
    @Override
    public Long[] newId(String tableName, int num){
        return this.getIdGenerator().getId(tableName,num,this.dbConfig);
    }

    /**
     * 产生一批新的唯一id
     * @param tableName 表名
     * @param num 数量
     * @return
     */
    @Override
    public Long[] newId(String tableName,String pk,int num){
        return this.getIdGenerator().getPkId(tableName,pk,num,this.dbConfig);
    }

    /**
     * 产生一批新的唯一id
     * @param type 实体类
     * @param num 数量
     * @return
     */
    @Override
    public Long[] newId(Class<?> type, int num){
        return this.getIdGenerator().getId(type,num,this.dbConfig);
    }

    /**
     * 得到返回结果类形
     * @param sqlId
     * @return
     */
    private Class<?> getResultClass(String sqlId) {
        SqlItem item = (SqlItem) this.getSqlMap().get(sqlId);
        if (item != null) {
            if (item.getResult() != null) {
                return item.getResult().getClassForName();
            }else if(StringUtil.isNotEmpty(item.getResultClass())){
                return ReflectorUtil.getClassForName(item.getResultClass());
            }else {
                throw new SqlMapException("返回类型未定义,sql节点result属性为空");
            }
        }
        return null;
    }

    /**
     *
     * @param sqlId
     * @return
     */
    private Map<String, String> getPropertyMap(String sqlId) {
        SqlItem item = (SqlItem) this.getSqlMap().get(sqlId);
        if (item != null) {
            if (item.getResult() != null){
                if (item.getResult().getProperties() != null) {
                    return item.getResult().getProperties();
                }
            }
        }
        return null;
    }

}
