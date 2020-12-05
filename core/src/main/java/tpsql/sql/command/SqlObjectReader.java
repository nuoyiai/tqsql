package tpsql.sql.command;

import tpsql.core.reader.DefaultObjectReader;
import org.springframework.stereotype.Component;

public class SqlObjectReader extends DefaultObjectReader {

    @Override
    public Object getValue(Object obj, String propertyName) {
        if(obj instanceof IDataReader){
            IDataReader dataReader = (IDataReader)obj;
            if(dataReader.current()<0)
                dataReader.read();
            return dataReader.get(propertyName);
        }else{
            return super.getValue(obj,propertyName);
        }
    }

    @Override
    public Object getValue(Object obj, int propertyIndex) {
        if(obj instanceof IDataReader){
            IDataReader dataReader = (IDataReader)obj;
            if(dataReader.current()<0)
                dataReader.read();
            return dataReader.get(propertyIndex);
        }else{
            return super.getValue(obj,propertyIndex);
        }
    }
}
