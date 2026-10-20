package com.yzj.jep.app.enums;

public enum DictionaryEnum {

    BUS_IDENTIFYTYPE("BUS-IdentifyType", "自然人关联方证件类型"),

    BUS_ORGIDENTIFYTYPE("BUS-ORGIdentifyType", "机构关联方证件类型"),

    BUS_DOMESTICORFOREIGN("BUS-DomesticOrForeign", "境内外标识"),

    BUS_SEX("BUS-Sex", "性别"),

    BUS_RACE("BUS-Race", "民族"),

    BUS_POLITICALLY("BUS_politically", "政治面貌"),

    BUS_MARITALSTATUS("BUS-MaritalStatus", "婚姻状况"),

    BUS_EDUCATION("BUS-Education", "学历"),

    BUS_RELCATEGORY("BUS-RelCategory", "关联关系类型"),

    BUS_REL("BUS-Rel", "关系"),

    BUS_PREDICATE("BUS_Predicate", "关联关系判定"),

    BUS_STATUS("BUS-Status", "机构状态"),


    BUS_VALID("BUS-valid", "该关系是否有效"),

    WHETHERTOSHARE("WhetherToShare", "是否有权益份额"),

    BUS_PRO("BUS-pro", "产品分类"),

    BUS_ORGANIZATIONSTATUS("BUS-OrganizationStatus", "机构状态"),

    BUS_COMPANYTYPE("BUS-CompanyType", "企业类型"),

    BUS_ORGORPSN("BUS-OrgOrPsn", "关联方类型"),

    BUS_RELZZD("BUS-RelZZD", "关联关系"),

    BUS_RELEQUITYSHARE("BUS-RelequityShare", "是否有权益份额"),

    BUS_ISNO("BUS-isNo", "是否"),

    BUS_CONTRACTSTATUS("BUS-contractStatus", "合同状态"),

    BUS_APPLYSINGLESTATUS("BUS-ApplySingleStatus", "数据状态"),

    BUS_CURRENCY("BUS-Currency", "币种代码"),

    BUS_RELATIONTYPE("BUS-RelationType", "关系类型"),

    BUS_ORGNATURE("BUS-orgNature", "机构性质"),

    BUS_ISSUBSIDIARY("BUS-isSubsidiary", "是否控股子公司"),

    ENTITY_TYPE("Rel_POP","关联方/关联方中间节点类型"),

    INS_RELTYPE("INS-relType", "关联方类型"),

    INS_MAJORTYPE("INS-majorType", "重大交易类型"),

    INS_FUNDSINVMETHOD("INS-FundsinvestmentMethod", "投资方式"),

    INS_CONSIDERTYPE("INS-considerType", "审议类型"),

    INS_TRANTYPE("INS-tranType", "交易类型"),

    INS_INVESTMENTTYPE("INS-InvestmentType", "投资入股类业务类型"),

    INS_FUNDSTYPE("INS-FundsType", "资金运用类投资类别"),

    INS_BUSINESSTYPE("INT-InsBusinessType","保险业务类业务类型"),

    INS_INTERESTTYPE("INT-BusinessType","利益转移类业务类型"),

    INS_SERVICETYPE("INS-ServiceType", "提供货物或服务类业务类型"),

    INS_OTHERBUSTYPE("INS-otherType", "其他交易类型"),

    INS_SECURITIESTYPE("INS-SecuritiesType", "证券类型"),

    INS_INSBUSINESSTYPE("INT-InsBusinessType","业务类型"),

    INS_OPERATIONTYPE("INS-operationType", "备案操作类型"),

    INS_ISAGREEMENTIMPL("INS-isAgreementImpl","统一交易协议执行情况"),

    INS_TRA_DIRECTION("INS-Tra-Direction","交易方向"),

    INS_CONSIDERSITUATION("INS-considerSituation","免于审议情形");




    String code;
    String desc;

    private DictionaryEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
