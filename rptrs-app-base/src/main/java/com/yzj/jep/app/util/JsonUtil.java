package com.yzj.jep.app.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : zhangfei
 * @date : 2020/7/9 10:57 上午
 */

public class JsonUtil {


    @SuppressWarnings("unchecked")
    public static Map compareJson(JSONObject json1, JSONObject json2, String key) {
        Map result = new HashMap<>();
        Iterator<String> i = json1.keySet().iterator();
        while (i.hasNext()) {
            key = i.next();
            compareJson(json1.get(key), json2.get(key), key, result);
        }
        return result;
//        return sb.toString();
    }

    public static void compareJson(Object json1, Object json2, String key, Map result) {
        if (json1 == null || json2 == null) {
            if (json1 != json2) {
                result.put(key, json2);
                System.err.println("不一致key:" + key + ",json1:" + json1 + ",json2:" + json2);
            }
        } else if (json1 instanceof JSONObject) {
//            System.out.println("this JSONObject----" + key);
            compareJson((JSONObject) json1, (JSONObject) json2, key, result);
        } else if (json1 instanceof JSONArray) {
//            System.out.println("this JSONArray----" + key);
            compareJson((JSONArray) json1, (JSONArray) json2, key, result);
        } else if (json1 instanceof String) {
//            System.out.println("this String----" + key);
//            compareJson((String) json1, (String) json2, key);
            try {
                String json1ToStr = json1.toString();
                String json2ToStr = json2.toString();
                compareJson(json1ToStr, json2ToStr, key, result);
            } catch (Exception e) {
                System.out.println("转换发生异常 key:" + key);
                e.printStackTrace();
            }

        } else {
//            System.out.println("this other----" + key);
            compareJson(json1.toString(), json2.toString(), key, result);
        }
    }

    public static void compareJson(String str1, String str2, String key, Map result) {
        if (!str1.equals(str2)) {
//            sb.append("key:"+key+ ",json1:"+ str1 +",json2:"+str2);
            result.put(key, str2);
            System.err.println("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2);
        } else {
            System.out.println("一致：key:" + key + ",json1:" + str1 + ",json2:" + str2);
        }
    }

    public static void compareJson(JSONArray json1, JSONArray json2, String key, Map result) {
        if (json1 != null && json2 != null) {
            Iterator i1 = json1.iterator();
            Iterator i2 = json2.iterator();
            while (i1.hasNext()) {
                compareJson(i1.next(), i2.next(), key, result);
            }
        } else {
            if (json1 == null && json2 == null) {
                System.err.println("不一致：key:" + key + "  在json1和json2中均不存在");
            } else if (json1 == null) {
                System.err.println("不一致：key:" + key + "  在json1中不存在");
            } else if (json2 == null) {
                System.err.println("不一致：key:" + key + "  在json2中不存在");
            } else {
                System.err.println("不一致：key:" + key + "  未知原因");
            }

        }
    }

    private final static String st1 = "{\"username\":\"tom\",\"age\":18,\"address\":[{\"province\":\"上海市\"},{\"city\":\"上海市\"},{\"disrtict\":\"静安区\"}]}";
    private final static String st2 = "{\"username\":\"tom\",\"age\":18,\"address\":[{\"province\":\"北京市\"},{\"city\":\"上海市\"},{\"disrtict\":\"静安区\"}]}";
    ;

    public static void main(String[] args) {
        System.out.println(st1);
        System.out.println(st2);
        JSONObject jsonObject1 = JSONObject.parseObject(st1);
        JSONObject jsonObject2 = JSONObject.parseObject(st2);
        compareJson(jsonObject1, jsonObject2, null);
    }

}
