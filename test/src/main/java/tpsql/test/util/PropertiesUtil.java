package tpsql.test.util;

import tpsql.core.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * @param filename
     * @return
     */
    public static void savePropertyMapByFileName(String filename,Properties prop){
        FileOutputStream oFile = null;
        try {
            if(!new File(filename).exists()) {
                String path = FileUtil.getParentPath(filename);
                FileUtil.createDirectory(path);
                new File(filename).createNewFile();
            }
            oFile = new FileOutputStream(new File(filename));
            prop.store(oFile, "Comment");
            oFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件信息
     * @param resource
     * @return
     */
    public static Properties loadPropertyMapByResource(String resource){
        Properties props = new Properties();
        try {
            props.load(PropertiesUtil.class.getResourceAsStream(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

}
