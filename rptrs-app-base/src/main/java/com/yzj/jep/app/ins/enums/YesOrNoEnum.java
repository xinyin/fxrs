package com.yzj.jep.app.ins.enums;

import com.yzj.jep.commons.StringUtils;

import java.util.Arrays;

/**
 * @author by Liqiang
 * @Date 2022/5/17
 * @Description 是否枚举
 */
public enum YesOrNoEnum {
    /**
     * 关联交易导入时通用是否枚举
     */

    YES("1", "是"),
    NO("0", "否");


    private  String code;
    private  String desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    YesOrNoEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据desc获取code值
     */
    public static String getCode(String descStr){
        if(StringUtils.isEmpty(descStr)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.desc.equals(descStr.trim()))
                .findFirst()
                .map(v -> v.code)
                .orElse(null);
    }

    /**
     * 根据code获取desc值
     */
    public static String getDesc(String codeStr){
        if(StringUtils.isEmpty(codeStr)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.code.equals(codeStr.trim()))
                .findFirst()
                .map(v -> v.desc)
                .orElse(null);
    }
}
