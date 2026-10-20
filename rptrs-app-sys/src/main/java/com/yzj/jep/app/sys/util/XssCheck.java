package com.yzj.jep.app.sys.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class XssCheck {

    Logger logger = LoggerFactory.getLogger(XssCheck.class);

    //传实体
    public static boolean reflect(Object e) {
        try{
            Class cls = e.getClass();
            Field[] fields = cls.getDeclaredFields();
            // 判断黑名单
            String[] inj_stra = {"script", "mid", "master", "truncate", "insert", "select", "delete", "update", "declare", "payload",
                    "iframe", "onreadystatechange", "alert", "atestu", "xss", "</", "svg", "confirm", "prompt", "onload", "onmouseover", "onfocus", "onerror","'"};
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if("name".equals(f.getName()) || "orgName".equals(f.getName())){
                    continue;
                }
                f.setAccessible(true);
                System.out.println(f.get(e));
                for (int j = 0; j < inj_stra.length; j++) {
                    if (String.valueOf(f.get(e)).contains(inj_stra[j])) {
                        return false;
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
    }

    //传字符串
    public static boolean reflectStr(Object e) {
        try{
            // 判断黑名单
            String[] inj_stra = {"script", "mid", "master", "truncate", "insert", "select", "delete", "update", "declare", "payload",
                    "iframe", "onreadystatechange", "alert", "atestu", "xss", "</", "svg", "confirm", "prompt", "onload", "onmouseover", "onfocus", "onerror","'"};
            for (int j = 0; j < inj_stra.length; j++) {
                if (String.valueOf(e).contains(inj_stra[j])) {
                    return false;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
}
