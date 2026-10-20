package com.yzj.jep.app.base.constant;

/**
 * 权限类型
 *
 * @author mma
 * @version 3.0
 * @date 2011-12-14 下午12:12:13
 */
public enum PrivilegeType {

    DAK_ORG("DAK_ORG", "本机构", 2), DAK_ORG_ALL("DAK_ORG_ALL", "本机构及下辖机构", 4),
    DAK_DEPT("DAK_DEPT", "本部门", 1), DAK_DEPT_BIZLINE("DAK_DEPT_BIZLINE", "本条线", 3),
    DAK_FORBIDDEN("DAK_FORBIDDEN", "禁止访问", 0);

    private String code;
    private String name;
    private Integer weight;

    PrivilegeType(String code, String name, int weight) {
        this.code = code;
        this.name = name;
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
