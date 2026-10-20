package com.yzj.jep.app.util;

import cn.hutool.core.util.ObjectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Liqiang
 * @Date 2022/5/23
 * @Description TODO
 */
public class CheckEntityFiledEmpty {

    /**
     * 判断一个实体类对象实例的所有成员变量是否为空
     *
     * @param obj 校验的类对象实例
     * @return List
     * @throws Exception 异常信息
     */
    public static List<String> isObjectFieldEmpty(Object obj) throws Exception {
        List<String> list = new ArrayList<>();
        // 如果传入的对象为空则返回
        if (null == obj) {
            list.add("数据异常，请检查传入的报文格式!");
            return list;
        }
        //得到类对象
        Class<?> clazz = obj.getClass();
        //得到属性集合
        Field[] fs = clazz.getDeclaredFields();
        //遍历属性
        for (Field field : fs) {
            //设置属性是可以访问的（私有的也可以）
            field.setAccessible(true);
            if (ObjectUtil.isEmpty(field.get(obj)) || "null".equalsIgnoreCase(field.get(obj).toString())) {
                String name = field.getName();
                list.add(name);
            }
        }
        return list;
    }
}
