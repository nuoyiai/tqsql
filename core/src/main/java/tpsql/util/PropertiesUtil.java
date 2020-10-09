package tpsql.util;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 读取配置文件信息
     * @param filename
     * @return
     */
    public static Properties loadPropertyMapByFileName(String filename){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * 读取配置文件信息
     * @param resource
     * @return
     */
    public static Properties loadPropertyMapByResource(String resource){
        Properties props = new Properties();
        try {
            InputStream inputStream = PropertiesUtil.class.getResourceAsStream(resource);
            if(inputStream!=null) {
                props.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

}
