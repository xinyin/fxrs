package com.yzj.jep.app.sub.vo;

public class OrganInfoVo {

    private Integer no;
    private String name;//法人
    private String domesticOrForeign;//境内 外
    private String legalIdentifyNumber;//社会统一信用代码

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getLegalIdentifyNumber() {
        return legalIdentifyNumber;
    }

    public void setLegalIdentifyNumber(String legalIdentifyNumber) {
        this.legalIdentifyNumber = legalIdentifyNumber;
    }
}
