package tpsql.dao.querytable;

import tpsql.core.collection.DataTable;
import tpsql.dao.support.DataDao;
import tpsql.dao.IDataDao;
import tpsql.dao.IProxyDao;
import tpsql.dao.support.ProxyDao;
import tpsql.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DataQueryTable extends AbstractQueryTable implements IQueryTable {
    private IDataDao dataDao;
    private IProxyDao proxyDao;

    public IDataDao getDataDao(){
        if(this.dataDao==null)
            this.dataDao = new DataDao();
        return this.dataDao;
    }

    public IProxyDao getProxyDao(){
        if(this.proxyDao==null)
            this.proxyDao = new ProxyDao();
        return this.proxyDao;
    }

    public List qyeryList(Object ... conditions) {
        if(StringUtil.isNotEmpty(sqlId)) {
            List list = getProxyDao().queryList(sqlId, conditions);
            return list;
        }
        return new ArrayList();
    }

    public DataTable queryTable(Object ... conditions) {
        if(StringUtil.isNotEmpty(sqlId)) {
            DataTable table = getDataDao().queryData(sqlId, conditions);
            return table;
        }
        return new DataTable();
    }

}
