package com.yzj.jep.app.enums;

/**
 * @author zhangyu
 * 数据字典项：
 * 1.法人或非法人组织
 * 2.自然人
 * @create 2020-08-12 16:53
 */
public enum RelTypeCode {

    //授信类
    ORG("法人或非法人组织", "1"),
    //用信类
    PSN("自然人", "2");

    String name;
    String id;

    //为了方便使用时的判断，id使用String类型
    private RelTypeCode(String name, String id) {
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
}
