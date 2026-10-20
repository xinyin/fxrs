package com.yzj.jep.app.bus.vo;

public class SaveTreeMsgVo {

    private Integer fromPid;

    private String fromType;

    private Integer toPid;

    private String toType;

    private String fromRelCategory;

    private String toRelCategory;

    private String relCategorySub;

    private String shareholdingStrand;

    private Float shareholdingRatio;

    private String cognizaReason;

    private Integer relPid;

    public Integer getRelPid() {
        return relPid;
    }

    public void setRelPid(Integer relPid) {
        this.relPid = relPid;
    }

    public String getCognizaReason() {
        return cognizaReason;
    }

    public void setCognizaReason(String cognizaReason) {
        this.cognizaReason = cognizaReason;
    }

    public Integer getFromPid() {
        return fromPid;
    }

    public void setFromPid(Integer fromPid) {
        this.fromPid = fromPid;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public Integer getToPid() {
        return toPid;
    }

    public void setToPid(Integer toPid) {
        this.toPid = toPid;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getFromRelCategory() {
        return fromRelCategory;
    }

    public void setFromRelCategory(String fromRelCategory) {
        this.fromRelCategory = fromRelCategory;
    }

    public String getToRelCategory() {
        return toRelCategory;
    }

    public void setToRelCategory(String toRelCategory) {
        this.toRelCategory = toRelCategory;
    }

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }

    public String getShareholdingStrand() {
        return shareholdingStrand;
    }

    public void setShareholdingStrand(String shareholdingStrand) {
        this.shareholdingStrand = shareholdingStrand;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    @Override
    public String toString() {
        return "SaveTreeMsgVo{" +
                "fromPid=" + fromPid +
                ", fromType='" + fromType + '\'' +
                ", toPid=" + toPid +
                ", toType='" + toType + '\'' +
                ", fromRelCategory='" + fromRelCategory + '\'' +
                ", toRelCategory='" + toRelCategory + '\'' +
                ", relCategorySub='" + relCategorySub + '\'' +
                ", shareholdingStrand='" + shareholdingStrand + '\'' +
                ", shareholdingRatio=" + shareholdingRatio +
                '}';
    }
}
