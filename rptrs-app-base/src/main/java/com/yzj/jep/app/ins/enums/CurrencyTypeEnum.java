package com.yzj.jep.app.ins.enums;

import com.yzj.jep.commons.StringUtils;

import java.util.Arrays;

public enum CurrencyTypeEnum {

    NATURALPERSON("1", "CNY", "人民币"),
    LEGALPERSON("2", "美元", "美元"),
    FINANCIALPRODUCTS("3", "HKD", "港币");

    private final String code;
    private final String sysCode;
    private final String desc;

    CurrencyTypeEnum(String code, String sysCode, String desc) {
        this.code = code;
        this.sysCode = sysCode;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getSysCode() {
        return sysCode;
    }

    /**
     * 根据desc获取code值
     */
    public static String getCode(String descStr) {
        if (StringUtils.isEmpty(descStr)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.desc.equals(descStr))
                .findFirst()
                .map(a -> a.code)
                .orElse(null);
    }

    /**
     * 根据code获取desc值
     */
    public static String getDesc(String codeStr) {
        if (StringUtils.isEmpty(codeStr)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.code.equals(codeStr))
                .findFirst()
                .map(a -> a.desc)
                .orElse(null);
    }

    /**
     * 根据code获取sysCode值
     */
    public static String getSysCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .map(a -> a.sysCode)
                .orElse(null);
    }

}
