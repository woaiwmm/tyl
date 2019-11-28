package com.tyl.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 *
 * @author Administrator
 * @date 2019-10-26 17:16
 */
public class PropertiesUtil {
    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties properties;
//工具类的根据标准一般是静态方法，不提供外部实例化，最好将构造器私有化
    private PropertiesUtil() { }

    //    一般用于初始化静态变量
    static {
        String fileName = "tyl.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常");
        }
    }

    public static String getProperty(String key){
        //去掉字符串两端多余的空格
        String value=properties.getProperty(key.trim());
        //看源码可知，如果是null或者""返回true
        if (StringUtils.isBlank(value)){
            return null;
        }
        return value;
    }

    public static String getProperty(String key,String defauValue){
        String value=properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return defauValue;
        }
        return value;
    }
}
