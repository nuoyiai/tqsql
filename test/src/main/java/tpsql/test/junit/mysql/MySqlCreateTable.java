package tpsql.test.junit.mysql;

import tpsql.sql.meta.Column;
import tpsql.sql.meta.Table;
import tpsql.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlCreateTable {

    public String getCreateTableSqlString(Table table){
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE "+table.getName()+" (\r\n");
        for(int i=0;i<table.getColumns().size();i++) {
            Column column = table.getColumns().get(i);
            String comma = ((i==table.getColumns().size()-1) && StringUtil.isNullOrEmpty(table.getPkName()))?"":",";
            String columnStr = getColumnSql(column);
            sql.append("\t"+columnStr+comma+"\r\n");
        }
        if(StringUtil.isNotEmpty(table.getPkName())) {
            sql.append("\tPRIMARY KEY (" + table.getPkName() + ")\r\n");
        }
        if(StringUtil.isNotEmpty(table.getRemark())) {
            sql.append(") COMMENT='" + table.getRemark() + "'");
        }else{
            sql.append(") ");
        }
        return sql.toString();
    }

    private String getColumnSql(Column column){
        String columnName = column.getName().toUpperCase();
        String typeName = column.getTypeName();
        String commentStr = StringUtil.isNotEmpty(column.getComment())?" COMMENT '"+column.getComment()+"'":"";
        String notNullStr = column.isNullable()?"":" NOT NULL";
        String autoIncrementStr = column.isPrimaryKey()?" AUTO_INCREMENT":"";
        if("VARCHAR".equals(typeName)) {
            return columnName + " " + typeName + "("+column.getLength()+")"+notNullStr+commentStr;
        }else if("DECIMAL".equals(typeName)){
            return columnName + " " + typeName + "("+column.getLength()+","+column.getScale()+")"+notNullStr+commentStr;
        }else if("DATETIME".equals(typeName) || "TIMESTAMP".equals(typeName)) {
            if("".equals(notNullStr))
                notNullStr = " NULL DEFAULT NULL";
            return columnName + " " + typeName+notNullStr+commentStr;
        }else{
            return columnName + " " + typeName+notNullStr+autoIncrementStr+commentStr;
        }
    }

    public List<String> getAlterTableSqlString(Table oldTable, Table newTable){
        return getAlterTableSqlString(oldTable,newTable,true);
    }

    public List<String> getAlterTableSqlString(Table oldTable,Table newTable,boolean dropColumnFlag){
        StringBuffer sql = new StringBuffer();
        Map<String,Column> columnMap = new HashMap<String,Column>();
        for(Column column : oldTable.getColumns()){
            columnMap.put(column.getName().toUpperCase(),column);
        }
        List<Column> addList = new ArrayList<Column>();
        List<Column> modifyList = new ArrayList<Column>();
        for(Column column : newTable.getColumns()){
            if(!columnMap.containsKey(column.getName().toUpperCase())){
                addList.add(column);
            }else{
                if(!column.equals(columnMap.get(column.getName().toUpperCase()))) {
                    modifyList.add(column);
                }
            }
        }

        List<String> sqlList = new ArrayList<String>();
        for(Column column : addList){
            String columnStr = getColumnSql(column);
            sqlList.add("ALTER TABLE "+oldTable.getName()+" ADD ("+columnStr+")");
        }
        for(Column column : modifyList){
            String columnStr = getColumnSql(column);
            sqlList.add("ALTER TABLE "+oldTable.getName()+" MODIFY COLUMN "+columnStr);
        }

        if(dropColumnFlag) {
            columnMap.clear();
            List<Column> dropList = new ArrayList<Column>();
            for (Column column : newTable.getColumns()) {
                columnMap.put(column.getName().toUpperCase(), column);
            }
            for (Column column : oldTable.getColumns()) {
                if (!columnMap.containsKey(column.getName().toUpperCase())) {
                    dropList.add(column);
                }
            }
            for(Column column : dropList){
                sqlList.add("ALTER TABLE "+oldTable.getName()+" DROP COLUMN "+column.getName().toUpperCase());
            }
        }

        return sqlList;

    }

    public String getDropTableSqlString(String tableName){
        return "DROP TABLE "+tableName;
    }

}
