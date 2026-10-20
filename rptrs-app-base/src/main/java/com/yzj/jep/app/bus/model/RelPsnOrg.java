package com.yzj.jep.app.bus.model;

public class RelPsnOrg {
    private Integer pid;
    private String fromMemo;
    private String fromType;
    private String identifyNumber;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getFromMemo() {
        return fromMemo;
    }

    public void setFromMemo(String fromMemo) {
        this.fromMemo = fromMemo;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }
}
