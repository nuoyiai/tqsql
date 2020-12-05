package tpsql.test.util;

import tpsql.core.reflector.PropertyInfo;
import tpsql.core.util.ReflectorUtil;

public class PrintUtil {

    public static void printEntry(Object obj){
        if(obj!=null){
            PropertyInfo[] pis = ReflectorUtil.getProperties(obj.getClass());
            StringBuffer sb = new StringBuffer();
            sb.append("{");
            boolean first = true;
            for(PropertyInfo pi : pis){
                if(!first)sb.append(",");
                first = false;
                sb.append(pi.getName()+":"+pi.getValue(obj));
            }
            sb.append("}");
            System.out.println(sb.toString());
        }
    }

}
