package com.yzj.jep.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigFileUtil {
    private static Logger logger = LoggerFactory.getLogger(ConfigFileUtil.class);

    public static Object getParamFromProp(String key) {
        InputStream is = ConfigFileUtil.class.getClassLoader().getResourceAsStream("Configuration.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Properties props = new Properties();
        try {
            props.load(br);
            return props.get(key);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
