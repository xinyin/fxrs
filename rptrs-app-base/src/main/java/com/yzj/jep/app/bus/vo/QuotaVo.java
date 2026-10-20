package com.yzj.jep.app.bus.vo;

public class QuotaVo {
    private String orgName;
    private String identifyType;
    private String uniscCode;
    private String presetLimitCurrency;
    private String presetLimit;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
    }

    public String getPresetLimitCurrency() {
        return presetLimitCurrency;
    }

    public void setPresetLimitCurrency(String presetLimitCurrency) {
        this.presetLimitCurrency = presetLimitCurrency;
    }

    public String getPresetLimit() {
        return presetLimit;
    }

    public void setPresetLimit(String presetLimit) {
        this.presetLimit = presetLimit;
    }

    @Override
    public String toString() {
        return "QuotaVo{" +
                "orgName='" + orgName + '\'' +
                ", identifyType='" + identifyType + '\'' +
                ", uniscCode='" + uniscCode + '\'' +
                ", presetLimitCurrency='" + presetLimitCurrency + '\'' +
                ", presetLimit='" + presetLimit + '\'' +
                '}';
    }
}
