package com.shaun.commons.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/1.
 */
public class SystemConfigUtil {

    private static SystemConfigUtil instance = new SystemConfigUtil();
    private static Properties props = new Properties();
    private static Log log = LogFactory.getLog(SystemConfigUtil.class);

    static {
        InputStream fis;
        try{
            fis = instance.getClass().getResourceAsStream("/config.properties");
            props.load(fis);
        }catch (IOException e){
            log.error(e);
        }
    }

    public static String getProperties(String key){
        return props.getProperty(key);
    }

    public static String getProperties(String key,String value){
        String propValue = props.getProperty(key);
        return propValue!=null&&!"未配置".equals(propValue)?propValue:value;
    }
}
