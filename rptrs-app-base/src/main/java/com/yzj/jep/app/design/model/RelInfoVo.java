package com.yzj.jep.app.design.model;

/**
 * @title: RelInfoVo
 * @Author Whx
 * @Date: 2022/7/22 14:48
 * @Version 1.0
 */
public class RelInfoVo {

    /**关联关系主键*/
    private Integer pid;
    /**关联关系唯一值*/
    private String pidUnique;
    private String toPid;
    private String toType;
    private String toName;
    private String fromPid;
    private String fromType;
    private String fromName;
    private String holdings;
    private String relCategorySub;

    /**
     * 特定的关系排序
     */
    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getToPid() {
        return toPid;
    }

    public void setToPid(String toPid) {
        this.toPid = toPid;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromPid() {
        return fromPid;
    }

    public void setFromPid(String fromPid) {
        this.fromPid = fromPid;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }

    public String getPidUnique() {
        return pidUnique;
    }

    public void setPidUnique(String pidUnique) {
        this.pidUnique = pidUnique;
    }
}
