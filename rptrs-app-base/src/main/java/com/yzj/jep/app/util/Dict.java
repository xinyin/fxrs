package com.yzj.jep.app.util;

public class Dict {

    /**
     * 关联交易-审批状态，1-待补录，2-待归档，3-待提交，4-审批中，5-审批退回，6-已归档
     * 对接OA增加驳回状态，本地存储为 7失效
     */
    public static final String APPLY_STATUS_1 ="1";
    public static final String APPLY_STATUS_2 ="2";
    public static final String APPLY_STATUS_3 ="3";
    public static final String APPLY_STATUS_4 ="4";
    public static final String APPLY_STATUS_5 ="5";
    public static final String APPLY_STATUS_6 ="6";
    public static final String APPLY_STATUS_7 ="7";
    public static final String APPLY_STATUS_8 ="8";
    /**
     * 关联交易-交易类型，01-投资入股类，02-资金运用类，03-保险业务类，04-利益转移类，05-提供货物或服务类，06-其他类
     */
    public static final String TRAN_TYPE_01 ="01";
    public static final String TRAN_TYPE_02 ="1";
    public static final String TRAN_TYPE_03 ="4";
    public static final String TRAN_TYPE_04 ="3";
    public static final String TRAN_TYPE_05 ="2";
    public static final String TRAN_TYPE_06 ="06";
    /**
     * 逻辑判断，1是0否
     */
    public static final String SF_0 ="0";
    public static final String SF_1 ="1";

    //关联交易投资入股业务类型,InvestmentType业务类型，01投资入股或增资，02减资，03收购合并，04保险公司发行的优先股、债券、或其他证券
    public static final String INS_INVESTMENT_TYPE_01 ="01";
    public static final String INS_INVESTMENT_TYPE_02 ="02";
    public static final String INS_INVESTMENT_TYPE_03 ="03";
    public static final String INS_INVESTMENT_TYPE_04 ="04";

    //关联交易-交易类型，简称
    public static final String TRAN_TYPE_NAME_INVESTMENT ="investment";
    public static final String TRAN_TYPE_NAME_FUNDS ="funds";
    public static final String TRAN_TYPE_NAME_BUSINESS ="business";
    public static final String TRAN_TYPE_NAME_INTEREST ="interest";
    public static final String TRAN_TYPE_NAME_SERVICE ="service";
    public static final String TRAN_TYPE_NAME_OTHER ="other";

    //审议类型，1免于审议，2一般审议，3逐笔审议
    public static final String CONSIDER_TYPE_MYSY ="1";
    public static final String CONSIDER_TYPE_YBSY ="2";
    public static final String CONSIDER_TYPE_ZBSY ="3";

    //重大交易类型,0，非重大，1单笔重大，2累计重大
    public static final String MAJOR_TYPE_F ="0";
    public static final String MAJOR_TYPE_DB ="1";
    public static final String MAJOR_TYPE_LJ ="2";

    /*
     *     审批流程流转状态
     *
     *     WF_DRAFT("草稿", "10"),
     *     WF_RUN("运行中", "30"),
     *     WF_REJECT("已退回", "20"),
     *     WF_COMPLETE("已完成", "40"),
     *     WF_SUSPEND("挂起", "50"),
     *     WF_TERMINATE("终止", "60");
     */
    public static final String WF_DRAFT ="10";
    public static final String WF_RUN ="20";
    public static final String WF_REJECT ="30";
    public static final String WF_COMPLETE ="40";
    public static final String WF_SUSPEND ="50";
    public static final String WF_TERMINATE ="60";
    //OA数据推送方式,全部推送ALL,单主体推送SINGLE
    public static final String PUSH_TYPE_ALL ="ALL";
    public static final String PUSH_TYPE_SINGLE ="SINGLE";

    //资金运用业务类型 01股权  02不动产类  03银行存款  04金融产品  05与关联方共同投资  06其他投资
    public static final String FUNDS_INVESTMENT_TYPE_GQ  ="01";
    public static final String FUNDS_INVESTMENT_TYPE_BDC  ="02";
    public static final String FUNDS_INVESTMENT_TYPE_YHCK  ="03";
    public static final String FUNDS_INVESTMENT_TYPE_JRCP  ="04";
    public static final String FUNDS_INVESTMENT_TYPE_GTTZ  ="05";
    public static final String FUNDS_INVESTMENT_TYPE_QT  ="06";
    /**
     * 交易对手类型，PSN自然人，ORG法人
     */
    public static final String REL_TYPE_PSN ="PSN";
    public static final String REL_TYPE_ORG ="ORG";

    /**
     * 关联方、关联关系备份类型
     */
    public static final String REL_ADD ="1";
    public static final String REL_UPDATE ="2";
    public static final String REL_DELETE ="3";
    public static final String REL_RESERVED ="4";
    public static final String REL_ALL ="5";

    /**判断接口返回是否成功 0000 成功返回 **/
    public static final String INTERFACE_RETURN_CODE ="0000";

    /**判断系统是否为OA */
    public static final String SOURCE_SYSTEM_OA ="OA系统";
}
