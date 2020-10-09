package tpsql.querytable;

import tpsql.collection.DataTable;

import java.util.List;

public interface IQueryTable {

    String getFields();

    String getValue();

    String getText();

    String getDisplays();

    List qyeryList(Object ... conditions);

    DataTable queryTable(Object ... conditions);

}
