package com.yzj.jep.app.tag;

/**
 * 选项类型定义
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-19 上午9:29:55
 */
public interface OptionTypeConstant {

    /**
     * 选项类型 - 自动判断
     */
    public static final String TYPE_AUTO = "auto";

    /**
     * 选项类型 - 对象
     */
    public static final String TYPE_OBJECT = "object";

    /**
     * 选项类型 - 未定义
     */
    public static final String TYPE_UNDEFINED = "undefined";

    /**
     * 选项类型 - 字符串
     * 该类型表示选项值为字符串类型
     */
    public static final String TYPE_STRING = "string";

    /**
     * 选项类型 - 数字
     */
    public static final String TYPE_NUMBER = "number";

    /**
     * 选项类型 - 布尔
     */
    public static final String TYPE_BOOLEAN = "boolean";

    /**
     * 选项类型 - 数组
     */
    public static final String TYPE_ARRAY = "array";

    /**
     * 选项类型 - 函数
     */
    public static final String TYPE_FUNCTION = "function";

}
