package tpsql.sql.mapping;

import tpsql.sql.builder.SqlString;

public class SqlItemValues extends SqlItemContent implements ISqlItemClone<SqlItemValues> {

    @Override
    public SqlString toString(SqlItemArgs arg)
    {
        for(ISqlItemPart part : this.parts){
            if(part instanceof ContentTextWrapper){
                SqlString sql = part.toString(arg);
                if(sql!=null && !sql.isEmpty()) {
                    break;
                }
            }else if(part instanceof SqlItemClause){
                arg.setHidePrepend(true);
                break;
            }
        }
        SqlString sql = super.toString(arg);
        if(!sql.isEmpty())
        {
            return new SqlString(" VALUES (").contact(sql).contact(" )");
        }
        else {
            return sql;
        }
    }

    @Override
    public SqlItemValues clone(Object args){
        SqlItemValues values = new SqlItemValues();
        values.map=this.map;
        values.parts=this.cloneParts(args);
        return values;
    }

}
