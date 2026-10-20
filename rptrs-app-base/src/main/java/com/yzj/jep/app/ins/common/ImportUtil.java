package com.yzj.jep.app.ins.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * 导入工具类
 *
 * @author jaws
 */
public class ImportUtil {

    /**
     * 添加错误信息
     *
     * @param errorList     错误信息集合
     * @param errorMsg 错误信息
     */
    public static void addErrorMsg(List<String> errorList, String errorMsg) {
        Assert.notNull(errorList, "参数异常！");
        errorList.add(errorMsg);
    }

    /**
     * 添加错误信息
     *
     * @param condition 条件
     * @param errorList 错误信息集合
     * @param errorMsg  错误信息
     */
    public static void addErrorMsg(boolean condition, List<String> errorList, String errorMsg) {
        Assert.notNull(errorList, "参数异常！");
        if (condition) {
            errorList.add(errorMsg);
        }
    }

    /**
     * 字段是否为空校验
     *
     * @param condition 条件
     * @param list      错误信息集合
     * @param val       字段值
     * @param errorMsg  错误信息
     */
    public static void isBlank(boolean condition, List<String> list, String val, String errorMsg) {
        if (condition) {
            isBlank(list, val, errorMsg);
        }
    }

    /**
     * 字段是否为空校验
     *
     * @param errorList 错误信息集合
     * @param val       字段值
     * @param errorMsg  错误信息
     */
    public static void isBlank(List<String> errorList, String val, String errorMsg) {
        addErrorMsg(StrUtil.isBlank(val), errorList, errorMsg);
    }

    /**
     * 数值转换
     *
     * @param list
     * @param val
     * @param errorMsg
     */
    public static BigDecimal decimalConvert(List<String> list, String val, String errorMsg) {
        try {
            return new BigDecimal(val);
        } catch (Exception e) {
            addErrorMsg(list, errorMsg);
            return null;
        }
    }

}
