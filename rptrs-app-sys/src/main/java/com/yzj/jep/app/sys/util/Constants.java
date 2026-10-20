package com.yzj.jep.app.sys.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟类
 *
 * @author DFD
 */
public abstract class Constants {
    /**
     * 用来模拟Redis
     */
    public static Map<String, Object> conditions = new HashMap<String, Object>();

    /**
     * 用来将Obj转换成List
     *
     * @param obj   obj对象
     * @param clazz 转换类型
     * @param <T>   泛型
     * @return
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
