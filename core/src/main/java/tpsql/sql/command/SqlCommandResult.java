package tpsql.sql.command;

import java.util.List;

public class SqlCommandResult {
    /**
     * 响行行
     */
    protected int[] resultRow;
    /**
     * 第一行第一例
     */
    protected List resultList;

    public int[] getResultRow() {
        return resultRow;
    }

    public void setResultRow(int[] resultRow) {
        this.resultRow = resultRow;
    }

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

    public int getRowFirst(){
        if(resultRow!=null && resultRow.length>0){
            return resultRow[0];
        }
        return -1;
    }
}
