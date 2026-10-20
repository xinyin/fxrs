package com.yzj.jep.app.util;

import java.util.HashMap;
import java.util.Map;

public enum EastSubTypeEnum {
    /** 关联方信息报送  */
    RELATED("RELATED", "关联方信息报送"),
    /** 金融产品报送 */
    PRODUCT("PRODUCT", "金融产品报送"),
    /** 关联关系图谱报送 */
    RELATION("RELATION", "关联关系图谱报送"),
    TRANSACTION("TRANSACTION", "关联交易明细报送"),
    FUNDS("FUNDS", "资金运用类交易协议"),
    INTEREST("INTEREST", "利益转移类交易协议"),
    BUSINESS("BUSINESS", "保险业务类交易协议"),
    SERVICE("SERVICE", "服务类交易协议");



    private final String code;
    private final String desc;

    EastSubTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
        EastSubTypeEnum.Holder.MAP.put(this.code, this.desc);
    }


    private static class Holder {
        static Map<String, String> MAP = new HashMap<>(10);
    }

    /**
     * 通过code获取desc
     *
     * @param code 枚举值code
     * @return 枚举中文
     */
    public static String getDesc(String code) {
        String desc = EastSubTypeEnum.Holder.MAP.get(code);
        if (desc == null) {
            desc = code;
        }
        return desc;
    }
}
