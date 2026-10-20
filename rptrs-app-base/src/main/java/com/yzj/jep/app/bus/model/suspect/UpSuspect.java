package com.yzj.jep.app.bus.model.suspect;

public class UpSuspect {
    private Integer pid;
    private String fromPid;
    private String toPid;
    private String name;
    private String shareholdersCode;
    private String identifyType;
    private String uniscCode;
    private String relType;
    private String relation;
    private Float shareholdingRatio;
    private String holeShareAmount;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getFromPid() {
        return fromPid;
    }

    public void setFromPid(String fromPid) {
        this.fromPid = fromPid;
    }

    public String getToPid() {
        return toPid;
    }

    public void setToPid(String toPid) {
        this.toPid = toPid;
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

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getHoleShareAmount() {
        return holeShareAmount;
    }

    public void setHoleShareAmount(String holeShareAmount) {
        this.holeShareAmount = holeShareAmount;
    }
}
