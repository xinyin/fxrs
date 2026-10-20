package com.yzj.jep.app.util;

import java.lang.reflect.Field;

public class TrimUtil {
    public static void trimAllFields(Object obj) {
        if (obj == null) {
            return;
        }

        // 获取对象的类对象
        Class<?> clazz = obj.getClass();
        // 获取所有字段（包括私有、保护、默认和公共字段）
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // 如果字段是字符串类型，则进行判空和去除空格处理
            if (field.getType().equals(String.class)) {
                try {
                    // 设置可访问性，因为可能是私有字段
                    field.setAccessible(true);
                    // 获取字段的值
                    String value = (String) field.get(obj);
                    // 判空和去除空格
                    if (value != null) {
                        value = value.trim();
                        // 设置去除空格后的值回字段
                        field.set(obj, value);
                    }
                    // 恢复字段的可访问性
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    // 处理异常，比如打印日志等
                    e.printStackTrace();
                }
            }
        }
    }
}