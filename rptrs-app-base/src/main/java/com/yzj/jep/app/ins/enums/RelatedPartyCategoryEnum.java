package com.yzj.jep.app.ins.enums;

import com.yzj.jep.commons.StringUtils;

import java.util.Arrays;

/**
 * @author by Liqiang
 * @Date 2022/10/17
 * @Description 关联方类型枚举
 */
public enum RelatedPartyCategoryEnum {
    /**
     * 关联交易导入中的关联方类型
     */
    NATURALPERSON("1", "ORG", "法人或非法人组织"),
    LEGALPERSON("2", "PSN","自然人"),
    FINANCIALPRODUCTS("3", "PRO","金融产品");

    private final String code;

    private final String sysCode;
    private  final String desc;

    RelatedPartyCategoryEnum(String code, String sysCode, String desc) {
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
        if(StringUtils.isEmpty(descStr)) {
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
        if(StringUtils.isEmpty(codeStr)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.code.equals(codeStr))
                .findFirst()
                .map(a -> a.desc)
                .orElse(null);
    }

    /**
     * 根据sysCode获取code值
     */
    public static String getCodeStr(String sysCodeStr) {
        if(StringUtils.isEmpty(sysCodeStr)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.sysCode.equals(sysCodeStr))
                .findFirst()
                .map(a -> a.code)
                .orElse(null);
    }

}
