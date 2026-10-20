package com.yzj.jep.app.tag;

import org.apache.commons.lang.StringUtils;


/**
 * Option 工具类
 * 用于生成 option 对象
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-19 上午9:27:03
 */
public final class OptionUtils {

    /**
     * 创建一个 Option
     *
     * @param name
     * @param value
     * @return
     */
    public static final Option createOption(String name, String value) {
        return createOption(name, value, OptionTypeConstant.TYPE_AUTO);
    }

    /**
     * 创建一个 Option，根据类型生成表达式，放入Option的expr中
     *
     * @param name
     * @param value
     * @param type
     * @return
     */
    public static final Option createOption(String name, String value, String type) {
        StringBuffer expr = new StringBuffer();
        expr.append("'" + name + "'").append(": ");
        if (OptionTypeConstant.TYPE_UNDEFINED.equals(type)) {
            expr.append("undefined");
        }
        /*不加引号*/
        else if (OptionTypeConstant.TYPE_ARRAY.equals(type)
                || OptionTypeConstant.TYPE_FUNCTION.equals(type)
                || OptionTypeConstant.TYPE_NUMBER.equals(type)
                || OptionTypeConstant.TYPE_BOOLEAN.equals(type)
                || OptionTypeConstant.TYPE_OBJECT.equals(type)) {
            expr.append(value.trim());
        }

        /*字符串，强制加引号*/
        else if (OptionTypeConstant.TYPE_STRING.equals(type)) {
            expr.append("'" + value.trim() + "'");
        }
        /*自动判断*/
        else {
            value = StringUtils.trim(value);
            if (!StringUtils.isEmpty((value))) {
                if (StringUtils.isNumeric(value)) {
                    expr.append(value);
                } else if (value.startsWith("function")) {
                    expr.append(value);
                } else if ("true".equals(value) || "false".equals(value)) {
                    expr.append(value);
                } else if (value.startsWith("[") && value.endsWith("]")) {
                    expr.append(value);
                } else if (value.startsWith("{") && value.endsWith("}")) {
                    expr.append(value);
                } else if (value.startsWith("$") && value.endsWith(")"))//调用jQuery函数时，不要加引号
                {
                    expr.append(value);
                } else {
                    expr.append("'" + value + "'");
                }
            } else {
                expr.append("''");
            }
        }
        return new Option(name, expr.toString());
    }

}
