package tpsql.sql.mapping;

public abstract class SqlJoin {
    protected String sqlId; 	// 连接的SqlId
    protected String pkey; 	// 主键
    protected String fkey; 	// 外键
    protected String split;	//主键分隔符,主键为字符集合

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public SqlJoin(){
        this.sqlId = "";
        this.pkey = "";
        this.fkey = "";
        this.split = "";
    }
}
