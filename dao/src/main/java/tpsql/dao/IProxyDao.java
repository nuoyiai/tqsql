package tpsql.dao;

import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;
import tpsql.pagesize.IPageList;
import tpsql.pagesize.IPageSize;

import java.util.List;

public interface IProxyDao extends IDataDao {

    /**
     * 查询并返回实体对像集合
     * @param sqlId
     * @param conditions 条件数据
     * @return
     */
    List<?> queryList(String sqlId,Object ... conditions);

    /**
     * 查询并返回实体对像集合
     * @param sqlId
     * @param pageSize 第页多少记录
     * @param pageNum 第几页
     * @param conditions 条件数据
     * @return
     */
    List<?> queryList(String sqlId,int pageSize,int pageNum,Object ... conditions);

    /**
     * 分页查询并返回实体对像集合
     * @param sqlId 查询数量的sqlId
     * @param page 分页对像
     * @param conditions 条件数据
     * @return
     */
    IPageList<?> queryList(String sqlId, IPageSize page, Object ... conditions);

    /**
     * 分页查询并返回实体对像集合
     * @param sqlId	查询数量的sqlId
     * @param countSqlId 查询总数的sqlId
     * @param page 分页对像
     * @param conditions 条件数据
     * @return
     */
    IPageList<?> queryList(String sqlId,String countSqlId,IPageSize page,Object ... conditions);

    /**
     *
     * @param sqlId
     * @param conditions
     * @return
     */
    Object queryObject(String sqlId,Object ... conditions);

    /**
     * 产生一个新的唯一id
     * @param tableName	表名
     * @return
     */
    Long newId(String tableName);

    /**
     * 产生一个新的唯一id
     * @param tableName	表名
     * @return
     */
    Long newId(String tableName,String pk);

    /**
     * 产生一个新的唯一id
     * @param type 实体类
     * @return
     */
    Long newId(Class<?> type);

    /**
     * 产生一批新的唯一id
     * @param tableName 表名
     * @param num 数量
     * @return
     */
    Long[] newId(String tableName,int num);

    /**
     * 产生一批新的唯一id
     * @param tableName 表名
     * @param num 数量
     * @param pk 主键
     * @return
     */
    Long[] newId(String tableName,String pk,int num);

    /**
     * 产生一批新的唯一id
     * @param type 实体类
     * @param num 数量
     * @return
     */
    Long[] newId(Class<?> type,int num);

}
