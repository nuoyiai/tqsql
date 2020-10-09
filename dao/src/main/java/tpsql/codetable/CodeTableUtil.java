package tpsql.codetable;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tpsql.spring.SpringContext;
import tpsql.util.StringUtil;

/**
 * 类解释
 *
 * @Version 1.0 2017/3/30
 * @Author Merlin
 */
public class CodeTableUtil {

    private static Log log = LogFactory.getLog(CodeTableUtil.class);

    /**
     * 得到代码表组件
     * @param codetable
     * @param params
     * @return
     */
    public static List<ICodeTable.IItem> getCodeTable(String codetable, String params){
        try{
            if(StringUtil.isNotEmpty(codetable)){
                Object bean = SpringContext.instance().get(codetable);
                if(bean instanceof ICodeTable){
                    Object[] args = (StringUtil.isNotEmpty(params))?params.split(",",-1):new Object[0];
                    return ((ICodeTable)bean).getItems(args);
                }
            }
        }catch(Exception e){
            logError(e);
        }
        return new ArrayList<ICodeTable.IItem>();
    }

    /**
     * 得到代码表的值
     * @param codeTable
     * @param params
     * @param value
     * @return
     */
    public static String getCodeTableValue(String codeTable,String params,Object value){
        try{
            List<ICodeTable.IItem> items = getCodeTable(codeTable, params);
            if(items.size()>0){
                for(ICodeTable.IItem item : items){
                    if(value!=null && value.toString().equals(item.getValue())){
                        return item.getText();
                    }
                }
            }
        }catch(Exception e){
            logError(e);
        }
        return "";
    }

    /**
     * 得到代码表json数据
     * @param codetable
     * @param params
     * @return
     */
    public static String getCodeTableJson(String codetable,String params){
        StringBuffer json = new StringBuffer();
        json.append("[");
        try{
            if(StringUtil.isNotEmpty(codetable)){
                Object bean = SpringContext.instance().get(codetable);
                if(bean instanceof ICodeTable){
                    Object[] args = (StringUtil.isNotEmpty(params))?params.split(",",-1):new Object[0];
                    List<ICodeTable.IItem> items = ((ICodeTable)bean).getItems(args);
                    for(int i=0;i< items.size();i++){
                        ICodeTable.IItem item = items.get(i);
                        if(i>0)json.append(",");
                        json.append("{\"value\":\""+item.getValue()+"\",\"text\":\""+item.getText()+"\",\"checked\":\"+item.getValue()+\"}");
                    }
                }
            }
        }catch(Exception e){
            logError(e);
        }
        json.append("]");
        return json.toString();
    }

    public static String test(String codetable){
        return "test" + codetable;
    }

    private static void logError(Throwable e){
        log.error("error:"+e.getMessage(),e);
    }


}
