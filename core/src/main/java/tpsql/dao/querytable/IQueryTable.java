package tpsql.dao.querytable;

import tpsql.core.collection.DataTable;

import java.util.List;

public interface IQueryTable {

    String getFields();

    String getValue();

    String getText();

    String getDisplays();

    List qyeryList(Object ... conditions);

    DataTable queryTable(Object ... conditions);

}
