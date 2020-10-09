package tpsql.sql.mapping;

public class SqlLink extends SqlJoin {
    private String id;		//ID,复用时用到
    private String column;    //返回列

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public SqlLink() {
        this.id = "";
        this.column = "";
    }

}
