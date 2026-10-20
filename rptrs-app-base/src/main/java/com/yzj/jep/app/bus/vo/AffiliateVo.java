package com.yzj.jep.app.bus.vo;

public class AffiliateVo {

    private Integer pid;

    private String name;

    private String shareholdersCode;

    private String identifyType;

    private String identifyNumber;

    private String type;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShareholdersCode() {
        return shareholdersCode;
    }

    public void setShareholdersCode(String shareholdersCode) {
        this.shareholdersCode = shareholdersCode;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AffiliateVo{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", shareholdersCode='" + shareholdersCode + '\'' +
                ", identifyType='" + identifyType + '\'' +
                ", identifyNumber='" + identifyNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
