package com.cvte.waimai.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件类
 */
public class SystemConfig {
    private static final String CONFIG_PROPERTIES = "oss.properties";

    public static String getConfigProperties(String key) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream in = loader.getResourceAsStream(CONFIG_PROPERTIES);
        properties.load(in);
        String value = properties.getProperty(key);
        value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
        in.close();
        return value;
    }
}
