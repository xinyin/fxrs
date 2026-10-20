package com.yzj.jep.app.sub.vo;

import java.util.Date;

public class RelateInfoVo {

    private Integer no;
    private String superEntityName; //上级关联方名称

    private String superEntityType; //上级关联方类型

    private String superEntityUniscCode;//上级关联方统一社会信用代码/证件号码

    private String entityName;//关联方名称

    private String entityType;//关联方类型

    private String entityUniscUode;//关联方统一社会信用代码/证件号码

    private String relationType;//与前一层的关联关系类型

    private String regulator;//统计口径

    private String isShareHolding;//是否持股

    private String holdingPercent;//持股比例

    private String relationInfo;//关联关系说明

    private String validStatus;//该关系是否仍然有效

    private Date activeTime;//生效日期

    private Date invalidTime;//失效日期

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getSuperEntityName() {
        return superEntityName;
    }

    public void setSuperEntityName(String superEntityName) {
        this.superEntityName = superEntityName;
    }

    public String getSuperEntityType() {
        return superEntityType;
    }

    public void setSuperEntityType(String superEntityType) {
        this.superEntityType = superEntityType;
    }

    public String getSuperEntityUniscCode() {
        return superEntityUniscCode;
    }

    public void setSuperEntityUniscCode(String superEntityUniscCode) {
        this.superEntityUniscCode = superEntityUniscCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityUniscUode() {
        return entityUniscUode;
    }

    public void setEntityUniscUode(String entityUniscUode) {
        this.entityUniscUode = entityUniscUode;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getRegulator() {
        return regulator;
    }

    public void setRegulator(String regulator) {
        this.regulator = regulator;
    }

    public String getIsShareHolding() {
        return isShareHolding;
    }

    public void setIsShareHolding(String isShareHolding) {
        this.isShareHolding = isShareHolding;
    }

    public String getHoldingPercent() {
        return holdingPercent;
    }

    public void setHoldingPercent(String holdingPercent) {
        this.holdingPercent = holdingPercent;
    }

    public String getRelationInfo() {
        return relationInfo;
    }

    public void setRelationInfo(String relationInfo) {
        this.relationInfo = relationInfo;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }
}
