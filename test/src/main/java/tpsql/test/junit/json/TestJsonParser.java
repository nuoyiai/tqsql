package tpsql.test.junit.json;

import org.junit.Test;
import tpsql.core.json.JsonObject;

public class TestJsonParser {

    @Test
    public void test1(){
        String json = "{data:[{recordId:8024,recordNo:\"xxd11\"},{recordId:2,recordNo:\"xxd12\"}]}";
        JsonObject jo = JsonObject.fromObject(json);
        System.out.println(jo);
    }

}
