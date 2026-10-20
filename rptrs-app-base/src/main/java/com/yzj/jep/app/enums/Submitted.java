package com.yzj.jep.app.enums;

/**
 * @author zyk
 * 用来记录交易类型对应的数据库中的id
 * @create 2020-08-12 16:53
 */
public enum Submitted {
    //第一季度
    FIRSTQUARTER("Q1", "1"),

    SECONDQUARTER("Q2", "2"),

    THIRDQUARTER("Q3", "3"),

    FOURTHQUARTER("Q4", "4"),
    //其他类
    ORGNUMBER("复星保德信人寿保险有限公司", "000141"),

    NOTAPPLICABLE("不适用", "not"),
    E5("关联交易汇总", "E5"),
    E1("关联方信息", "E1"),
    E2("E2", "E2"),
    E3("关联关系", "E3"),
    E4("重大关联交易", "E4");
    String name;
    String id;

    //为了方便使用时的判断，id使用String类型
    private Submitted(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public static String getLabel(String value) {
        if (value != null) {
            Submitted[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Submitted s = var1[var3];
                if (s.getId().equals(value)) {
                    return s.getName();
                }
            }
        }

        return "";
    }
}
