package com.yzj.jep.app.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.AccumulatType;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.ins.mapper.BaseServiceMapper;
import com.yzj.jep.app.ins.mapper.MajorTraCheckMapper;
import com.yzj.jep.app.ins.model.InsTrans;
import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraRelInfo;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.sys.mail.IEmailSendService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class InsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsUtil.class);
    //    private static TraBusinessService traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
    private static TraFundsService traFundsService = SpringContextHolder.getBean(TraFundsService.class);
    private static TraInterestService traInterestService = SpringContextHolder.getBean(TraInterestService.class);
    private static TraInvestmentService traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
    private static TraServiceService traServiceService = SpringContextHolder.getBean(TraServiceService.class);
    private static TraBusinessService traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);

    private static RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
    //    private static RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
//
    private static RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);

//    private static RootOrgInfoService rootOrgInfoService = SpringContextHolder.getBean(RootOrgInfoService.class);

    private static BaseService baseService = SpringContextHolder.getBean(BaseService.class);

    private static RootOrgInfoService rootOrgInfoService = SpringContextHolder.getBean(RootOrgInfoService.class);

    private static AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);

    private static ProductService productService = SpringContextHolder.getBean(ProductService.class);

    private static LimitSensoryService limitSensoryService = SpringContextHolder.getBean(LimitSensoryService.class);

    private static RuleCheckService ruleCheckService = SpringContextHolder.getBean(RuleCheckService.class);

    private static WarnDeployService warnDeployService = SpringContextHolder.getBean(WarnDeployService.class);

    private static IEmailSendService iEmailSendService = SpringContextHolder.getBean(IEmailSendService.class);

    private static NotifyService notifyService = SpringContextHolder.getBean(NotifyService.class);

    private static RoleService roleService = SpringContextHolder.getBean(RoleService.class);

    private static DictItemService dictItemService = SpringContextHolder.getBean(DictItemService.class);

    private static StaffService staffService = SpringContextHolder.getBean(StaffService.class);

    private static RelFundsService relFundsService = SpringContextHolder.getBean(RelFundsService.class);

    private static FundsInvestmentService fundsInvestmentService = SpringContextHolder.getBean(FundsInvestmentService.class);

    private static MajorTraCheckService majorTraCheckService = SpringContextHolder.getBean(MajorTraCheckService.class);

    private static MajorTraCheckMapper majorTraCheckMapper = SpringContextHolder.getBean(MajorTraCheckMapper.class);

    private static TraCounterpartyService traCounterpartyService = SpringContextHolder.getBean(TraCounterpartyService.class);

    private static BaseServiceMapper baseServiceMapper =  SpringContextHolder.getBean(BaseServiceMapper.class);

    /**
     * 获取关联方投资余额
     *
     * @param
     * @param
     * @return
     */

   /* public static BigDecimal getTradeBalance(Integer relpid, String reltype) {
        BigDecimal balance = BigDecimal.ZERO;
        TraFunds traFunds = new TraFunds();
        traFunds.setRelId(relpid);
        traFunds.setRelType(reltype);
        traFunds.setInvestmentType("1");
        BigDecimal tradeBalance = traFundsService.getTradeBalance(traFunds);
        tradeBalance = tradeBalance == null ? BigDecimal.ZERO : tradeBalance;
        BigDecimal relBalance = balance.add(tradeBalance);
        return relBalance;

    }*/
    public static Map<String, Object> initRuleData(String relNumber, String reId, String reType, String tradeAmout, String tradeType, TraFunds traFunds, String businessType) {
        Map<String, Object> ruleParams = Maps.newHashMap();

        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(new RootOrgInfo());
        //法人G relID PSN_G_REL_ID
//        String Psn_G = appConfigService.get("PSN_G_REL_ID").getConfigValue();
        //法人I relID PSN_I_REL_ID
//        String Psn_I = appConfigService.get("PSN_I_REL_ID").getConfigValue();
//        //法人A relID PSN_A_REL_ID
//        String Psn_A = appConfigService.get("PSN_A_REL_ID").getConfigValue();
//        //法人H relID PSN_H_REL_ID
//        String Psn_H = appConfigService.get("PSN_H_REL_ID").getConfigValue();
//        //法人P relID PSN_P_REL_ID
//        String Psn_P = appConfigService.get("PSN_P_REL_ID").getConfigValue();
//        //法人L relID PSN_L_REL_ID
//        String Psn_L = appConfigService.get("PSN_L_RLE_ID").getConfigValue();
//        //法人M relID PSN_M_REL_ID
//        String Psn_M = appConfigService.get("PSN_M_REL_ID").getConfigValue();
//        //法人D relID PSN_D_REL_ID
//        String Psn_D = appConfigService.get("PSN_D_REL_ID").getConfigValue();


//        RootOrgInfo rootOrgInfo = rootOrgInfos.get(0);
        //上季末
        String endLastQ = appConfigService.getConfigValue("endLastQuarter");
        //上年末
        String endLastYear = appConfigService.getConfigValue("endLastYear");
        //上季末总资产
//        String endLastQ = rootOrgInfo.getEndLastQuarter();

//        String endLastYear = rootOrgInfo.getEndLastYear();
        //上季末总资产的15%
        BigDecimal endLastQ15 = new BigDecimal(endLastQ).multiply(new BigDecimal(15L)).divide(new BigDecimal(100L));
        //上季末总资产的15%
        //BigDecimal p15 = new BigDecimal(endLastQ).multiply(new BigDecimal(15L)).divide(new BigDecimal(100L));
        //上季末总资产的12.5%c
        BigDecimal endLastQ12 = new BigDecimal(endLastQ).multiply(new BigDecimal(125L)).divide(new BigDecimal(1000L));

        //上季末总资产的7.5%
        BigDecimal endLastQ7 = new BigDecimal(endLastQ).multiply(new BigDecimal(75L)).divide(new BigDecimal(1000L));
        //未上市权益类余额
        Map paramsTra = new HashMap();
        paramsTra.put("tradeType", 179);
        String tradeType01Sum = "";
        //法人G的未上市权益类余额+ 1000W 法人I未上市股权+500W 法人P 未上市股权 +10W 法人D 未上市股权 +5W 法人M 未上市股权 +9000
        Map tradeType01SumMap = traFundsService.getSumByTradeType(paramsTra);
//        tradeType01Sum = tradeType01SumMap == null ? "0" : tradeType01SumMap.get("tradeAmountSum") + "";
        //因为mysql查询到字段为大小写 而oracle查询到字段也就是map得key值全是大写所以做如下修改
        if (tradeType01SumMap != null) {
            if (tradeType01SumMap.get("tradeAmountSum") != null && tradeType01SumMap.get("tradeAmountSum") != "") {
                tradeType01Sum = tradeType01SumMap.get("tradeAmountSum") + "";
            }
            if (tradeType01SumMap.get("tradeAmountSum".toUpperCase()) != null && tradeType01SumMap.get("tradeAmountSum".toUpperCase()) != "") {
                tradeType01Sum = tradeType01SumMap.get("tradeAmountSum".toUpperCase()) + "";
            }
        } else {
            tradeType01Sum = "0";
        }
//        tradeType01Sum = new BigDecimal(tradeType01Sum).add(new BigDecimal(1000L)).add(new BigDecimal(500L)).add(new BigDecimal(10L)).add(new BigDecimal(5L)).add(new BigDecimal(9000L)).toString();

        //	不动产类
        Map paramsTra2 = new HashMap();
        paramsTra2.put("tradeType", 180);
        Map tradeType02SumMap = traFundsService.getSumByTradeType(paramsTra2);
        String tradeType02Sum = "";
//                tradeType02SumMap == null ? "0" : tradeType02SumMap.get("tradeAmountSum") + "";
        if (tradeType02SumMap != null) {
            if (tradeType02SumMap.get("tradeAmountSum") != null && tradeType02SumMap.get("tradeAmountSum") != "") {
                tradeType02Sum = tradeType02SumMap.get("tradeAmountSum") + "";
            }
            if (tradeType02SumMap.get("tradeAmountSum".toUpperCase()) != null && tradeType02SumMap.get("tradeAmountSum".toUpperCase()) != "") {
                tradeType02Sum = tradeType02SumMap.get("tradeAmountSum".toUpperCase()) + "";
            }
        } else {
            tradeType02Sum = "0";
        }
        //法人A 不动产+70000
//        tradeType02Sum = new BigDecimal(tradeType02Sum).add(new BigDecimal(70000L)).toString();
        //	境外投资
        Map paramsTra3 = new HashMap();
        paramsTra3.put("tradeType", 181);
        Map tradeType03SumMap = traFundsService.getSumByTradeType(paramsTra3);
        String tradeType03Sum = "";
//                tradeType03SumMap == null ? "0" : tradeType03SumMap.get("tradeAmountSum") + "";
        if (tradeType03SumMap != null) {
            if (tradeType03SumMap.get("tradeAmountSum") != null && tradeType03SumMap.get("tradeAmountSum") != "") {
                tradeType03Sum = tradeType03SumMap.get("tradeAmountSum") + "";
            }
            if (tradeType03SumMap.get("tradeAmountSum".toUpperCase()) != null && tradeType03SumMap.get("tradeAmountSum".toUpperCase()) != "") {
                tradeType03Sum = tradeType03SumMap.get("tradeAmountSum".toUpperCase()) + "";
            }
        } else {
            tradeType03Sum = "0";
        }
        //	其他金融资产
        Map paramsTra4 = new HashMap();
        paramsTra4.put("tradeType", 220);
        Map tradeType04SumMap = traFundsService.getSumByTradeType(paramsTra4);
        String tradeType04Sum = "";
//                tradeType04SumMap == null ? "0" : tradeType04SumMap.get("tradeAmountSum") + "";
        if (tradeType04SumMap != null) {
            if (tradeType04SumMap.get("tradeAmountSum") != null && tradeType04SumMap.get("tradeAmountSum") != "") {
                tradeType04Sum = tradeType04SumMap.get("tradeAmountSum") + "";
            }
            if (tradeType04SumMap.get("tradeAmountSum".toUpperCase()) != null && tradeType04SumMap.get("tradeAmountSum".toUpperCase()) != "") {
                tradeType04Sum = tradeType04SumMap.get("tradeAmountSum".toUpperCase()) + "";
            }
        } else {
            tradeType04Sum = "0";
        }

        //	与关联方共同投资
        Map paramsTra5 = new HashMap();
        paramsTra5.put("tradeType", 5);
        Map tradeType05SumMap = traFundsService.getSumByTradeType(paramsTra5);
        String tradeType05Sum = tradeType05SumMap == null ? "0" : tradeType05SumMap.get("tradeAmountSum") + "";


        //定期存款  法人H +100000W
        Map paramsTra6 = new HashMap();
        paramsTra5.put("tradeType", 6);
        Map tradeType06SumMap = traFundsService.getSumByTradeType(paramsTra6);
        String tradeType06Sum = tradeType05SumMap == null ? "0" : tradeType06SumMap.get("tradeAmountSum") + "";
//        tradeType06Sum = new BigDecimal(tradeType06Sum).add(new BigDecimal(100000L)).toString();
        //活期存款 法人H +3000W
        Map paramsTra7 = new HashMap();
        paramsTra5.put("tradeType", 5);
        Map tradeType07SumMap = traFundsService.getSumByTradeType(paramsTra7);
        String tradeType07Sum = tradeType05SumMap == null ? "0" : tradeType07SumMap.get("tradeAmountSum") + "";
//        tradeType07Sum = new BigDecimal(tradeType07Sum).add(new BigDecimal(3000L)).toString();

        //上季末总资产的15%
        ruleParams.put("endLastQ15", Double.valueOf(endLastQ15.toString()));
        //上季末总资产的12.5%
        ruleParams.put("endLastQ12", Double.valueOf(endLastQ12.toString()));
        //上季末总资产的7.5%
        ruleParams.put("endLastQ7", Double.valueOf(endLastQ7.toString()));
        //未上市权益类余额
        ruleParams.put("tradeType01Sum", Double.valueOf(tradeType01Sum));
        //	不动产类余额
        ruleParams.put("tradeType02Sum", Double.valueOf(tradeType02Sum));
        //	境外投资余额
        ruleParams.put("tradeType03Sum", Double.valueOf(tradeType03Sum));
        //	其他金融资产余额
        ruleParams.put("tradeType04Sum", Double.valueOf(tradeType04Sum));

        //上年末总资产
        ruleParams.put("endLastYear", Double.valueOf(endLastYear));

        String endLastYear15 = new BigDecimal(endLastYear).multiply(new BigDecimal(15L)).divide(new BigDecimal(100L)) + "";
        //上年末总资产15%
        ruleParams.put("endLastYear15", Double.valueOf(endLastYear15));
        //全部投资余额 + 现交易金额
//        if (relNumber.equals(Psn_G)) {
//            BigDecimal sumG = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(1000L)).add(new BigDecimal(13000L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumG.toString()));
//        } else if (relNumber.equals(Psn_A)) {
//            BigDecimal sumA = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(70000L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumA.toString()));
//        } else if (relNumber.equals(Psn_D)) {
//            BigDecimal sumD = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(5L)).add(new BigDecimal(660000L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumD.toString()));
//        } else if (relNumber.equals(Psn_I)) {
//            BigDecimal sumI = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(500L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumI.toString()));
//        } else if (relNumber.equals(Psn_P)) {
//            BigDecimal sumP = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(10L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumP.toString()));
//        } else if (relNumber.equals(Psn_M)) {
//            BigDecimal sumM = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(9000L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumM.toString()));
//        } else if (relNumber.equals(Psn_L)) {
//            BigDecimal sumL = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(280000L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumL.toString()));
//        } else if (relNumber.equals(Psn_H)) {
//            BigDecimal sumH = new BigDecimal(getSumAmount(reId, reType, tradeAmout)).add(new BigDecimal(650000L)).add(new BigDecimal(3000L)).add(new BigDecimal(100000L));
//            ruleParams.put("relIdSumAmount", Double.valueOf(sumH.toString()));
//        } else {
        BigDecimal sum = new BigDecimal(getSumAmount(reId, reType, tradeAmout));
        ruleParams.put("relIdSumAmount", Double.valueOf(sum.toString()));
//        }

        //如果是资金运用类 则 处理金融投资产品
        if ("TraFunds".equals(tradeType) && traFunds != null) {
            //资金运用类投资余额加本笔
            if ("179".equals(traFunds.getInvestmentType())) {
                tradeType01Sum = new BigDecimal(tradeType01Sum).add(traFunds.getTradeAmount()).toString();
                ruleParams.put("tradeType01Sum", Double.valueOf(tradeType01Sum));
            } else if ("180".equals(traFunds.getInvestmentType())) {
                tradeType02Sum = new BigDecimal(tradeType02Sum).add(traFunds.getTradeAmount()).toString();
                ruleParams.put("tradeType02Sum", Double.valueOf(tradeType02Sum));
            } else if ("181".equals(traFunds.getInvestmentType())) {
                tradeType03Sum = new BigDecimal(tradeType03Sum).add(traFunds.getTradeAmount()).toString();
                ruleParams.put("tradeType03Sum", Double.valueOf(tradeType03Sum));
            } else if ("220".equals(traFunds.getInvestmentType())) {
                tradeType04Sum = new BigDecimal(tradeType04Sum).add(traFunds.getTradeAmount()).toString();
                ruleParams.put("tradeType04Sum", Double.valueOf(tradeType04Sum));
            }

            // 规则校验单独处理  保险公司投资金融产品，若底层基础资产涉及控股股东或控股股东的关联方，保险公司购
            //买该金融产品的份额不得超过该产品发行总额的 60%；
            if ("4".equals(traFunds.getInvestmentType()) && "1".equals(traFunds.getUnderInvolveRel())) { //投资类别为其他资产类别 校验规则，则交易不合规 返回保存失败
                String proRatio = traFunds.getFinancialProRatio(); //购买金融产品份额占比 不能超过该产品发行总额的百分之60
                if (StringUtils.isNotBlank(proRatio)) {
//                    int res =  new BigDecimal(60L).compareTo(new BigDecimal(proRatio));
                    ruleParams.put("proRatio60", Double.valueOf(proRatio));
                } else {
                    ruleParams.put("proRatio60", Double.valueOf(0));
                }
            } else {
                ruleParams.put("proRatio60", Double.valueOf(0));
            }
        } else {
            ruleParams.put("proRatio60", Double.valueOf(0));
        }

        //获取百分之5以上股份股东和质押股权比例
        BigDecimal pp = BigDecimal.ZERO;   //质押股数占总股数 比
        if ("ORG".equals(reType)) {// 如果是机构 则查看机构持股占比
//            获取机构信息
            List<Filter> filters = new Filter.Builder().eq("uniscCode", reId).build();
            List<RelOrg> RelOrgs = relOrgService.select(filters);
            if (RelOrgs.size() > 0) {


                RelOrg relOrg = RelOrgs.get(0);
                if (relOrg != null) {
                    if ("1".equals(relOrg.getIsShareholder())) { // 如果是股东 判断股份占比 大于5%
                        int res = new BigDecimal(5L).compareTo(new BigDecimal(relOrg.getShareholdingProportion()));
                        if (res < 0) {//持股占比大于5%  计算 质押/持股数量 不大于50%
                            pp = new BigDecimal(relOrg.getPledgedShare()).divide(new BigDecimal(relOrg.getHoleShareAmount()));
                        }
                    }
                }
            }
        } else {
            //获取自然人信息
            List<Filter> filters = new Filter.Builder().eq("identifyNumber", reId).build();
            List<RelPsn> relPsns = relPsnService.select(filters);
            if (relPsns.size() > 0) {


                RelPsn relPsn = relPsns.get(0);
                if (relPsn != null) {
                    if ("1".equals(relPsn.getIsShareholder())) {
                        int res = new BigDecimal(5L).compareTo(new BigDecimal(relPsn.getShareholdingProportion()));
                        if (res < 0) {//持股占比大于5%  计算 质押/持股数量 不大于50%
                            pp = new BigDecimal(relPsn.getPledgedShareAmount()).divide(new BigDecimal(relPsn.getHoleShareAmount()));
                        }
                    }
                }
            }
        }
        //质押持股占比
        ruleParams.put("ZYShareProportion", Double.parseDouble(pp.multiply(new BigDecimal(100L)).toString()));

        //上年度总资产30%
        BigDecimal endLastYear30 = new BigDecimal(endLastYear).multiply(new BigDecimal(30L)).divide(new BigDecimal(100L));
        //上年度净资产
        BigDecimal CaptialLastYear = new BigDecimal(appConfigService.getConfigValue("netCaptialLastQuarter"));
        BigDecimal capTial = BigDecimal.ZERO;
        //比较总资产余额
        if (CaptialLastYear.compareTo(endLastYear30) > 0) {
            capTial = endLastYear30;
        } else {
            capTial = CaptialLastYear;
        }
        //获取相比较 小的值  跟投资余额相比
        ruleParams.put("capTial_", Double.valueOf(capTial.toString()));
        //获取资产运用类的总额  投资余额
        BigDecimal tzSumAmt = new BigDecimal(tradeType01Sum).add(new BigDecimal(tradeType02Sum)).add(new BigDecimal(tradeType03Sum)).add(new BigDecimal(tradeType04Sum)).add(new BigDecimal(tradeType05Sum)).add(new BigDecimal(tradeType06Sum)).add(new BigDecimal(tradeType07Sum));
        ruleParams.put("TraFundsSumAmt", Double.valueOf(tzSumAmt.toString()));
        return ruleParams;
    }

    public static Map<String, Object> initQuotaParam(String tradeType, String businessType, BigDecimal tradeAmout) {
        Map<String, Object> ruleParams = Maps.newHashMap();
        Map<String, Object> availableQuota = getAvailableQuota(tradeType, businessType);
        if (availableQuota == null) {
            return null;
        }
        ruleParams.put("availableQuota", availableQuota.get("quota"));
        ruleParams.put("currTradeAmount", tradeAmout);
        return ruleParams;
    }

    public static Map<String, Object> initWarnParam(String tradeType, String businessType, BigDecimal tradeAmount) {
        Map<String, Object> ruleParams = Maps.newHashMap();
        Map<String, Integer> quotaWarn = getQuotaWarn(tradeType, businessType);
        if (quotaWarn == null) {
            return null;
        } else {
            ruleParams.put("firstWarn", quotaWarn.get("firstWarn"));
            ruleParams.put("secondWarn", quotaWarn.get("secondWarn"));
            ruleParams.put("thirdWarn", quotaWarn.get("thirdWarn"));
            List<Filter> filters = new Filter.Builder().eq("businessType", tradeType + "-" + businessType).eq("tradeType", tradeType).eq("tranOrg", UserProfileUtils.getUserProfile().getUnitCode()).build();
            BigDecimal preLimit = BigDecimal.ZERO;
            BigDecimal pasLimit = BigDecimal.ZERO;
            BigDecimal useQuotaRatio = BigDecimal.ZERO;
            Product product = productService.get(filters);
            if (product == null) {
                return null;
            } else {
                if (StringUtils.isNotBlank(product.getPreLimit())) {
                    preLimit = new BigDecimal(product.getPreLimit());
                }
                if (StringUtils.isNotBlank(product.getPasLimit())) {
                    pasLimit = new BigDecimal(product.getPasLimit());
                }
                useQuotaRatio = (pasLimit.add(tradeAmount)).divide(preLimit).multiply(BigDecimal.valueOf(100L));
            }
            ruleParams.put("useQuotaRatio", useQuotaRatio);
            return ruleParams;
        }
    }

    //获取交易类型预计额度剩余额度,额度使用率
    private static Map<String, Object> getAvailableQuota(String tradeType, String businessType) {
        Map<String, Object> map = Maps.newHashMap();
        List<Filter> filters = new Filter.Builder().eq("businessType", tradeType + "-" + businessType).eq("tradeType", tradeType).eq("tranOrg", UserProfileUtils.getUserProfile().getUnitCode()).build();
        BigDecimal quota = BigDecimal.ZERO;
        Product product = productService.get(filters);
        if (product != null) {
            if (StringUtils.isNotBlank(product.getRemLimit())) {
                quota = new BigDecimal(product.getRemLimit());
                map.put("quota", quota);
                return map;
            }
        }
        return null;
    }

    //获取预警阈值
    private static Map<String, Integer> getQuotaWarn(String tradeType, String businessType) {
        List<Filter> filters = new Filter.Builder().eq("sensoryIndex", UserProfileUtils.getUserProfile().getUnitCode()).eq("tradeType", tradeType).eq("businessType", tradeType + "-" + businessType).build();
        LimitSensory limitSensory = limitSensoryService.get(filters);
        if (limitSensory != null) {
            Map<String, Integer> map = Maps.newHashMap();
            map.put("firstWarn", limitSensory.getWarnThreshold1l() == null ? 0 : limitSensory.getWarnThreshold1l());
            map.put("secondWarn", limitSensory.getWarnThreshold2l() == null ? 0 : limitSensory.getWarnThreshold2l());
            map.put("thirdWarn", limitSensory.getWarnThreshold3l() == null ? 0 : limitSensory.getWarnThreshold3l());
            return map;
        }
        return null;
    }

    //获取交易总额
    public static String getSumAmount(String reId, String reType, String tradeAmount) {
        Map param = Maps.newHashMap();
        param.put("relId", reId);
        param.put("relType", reType);
        BigDecimal FundsAmount = traFundsService.getCurrYearTotalTradeAmount(param) == null ? BigDecimal.ZERO : traFundsService.getCurrYearTotalTradeAmount(param);
        BigDecimal InterestAmount = traInterestService.getCurrYearTotalTradeAmount(param) == null ? BigDecimal.ZERO : traInterestService.getCurrYearTotalTradeAmount(param);
        BigDecimal InvestmentAmount = traInvestmentService.getCurrYearTotalTradeAmount(param) == null ? BigDecimal.ZERO : traInvestmentService.getCurrYearTotalTradeAmount(param);
        BigDecimal ServiceAmount = traServiceService.getCurrYearTotalTradeAmount(param) == null ? BigDecimal.ZERO : traServiceService.getCurrYearTotalTradeAmount(param);
        BigDecimal BusinessAmount = traBusinessService.getCurrYearTotalTradeAmount(param) == null ? BigDecimal.ZERO : traBusinessService.getCurrYearTotalTradeAmount(param);
        return new BigDecimal(tradeAmount).add(FundsAmount).add(InterestAmount).add(InvestmentAmount).add(ServiceAmount).add(BusinessAmount).toString();
    }


    //重大关联交易的数据初始化
    public static Map<String, Object> initGreatData(String reId, String reType, String tradeAmount) {
        Map<String, Object> tips = Maps.newHashMap();
        BigDecimal currYearTotalTradeAmount = baseService.getCurrYearTotalTradeAmount(Integer.parseInt(reId), reType);
        Long majorTradeCount = baseService.getMajorTradeCount(Integer.parseInt(reId), reType);
        String netAssetsValue = appConfigService.getConfigValue("netAssets");
        BigDecimal netAssetsNum = new BigDecimal(netAssetsValue);
        tips.put("tradeAmount", Double.valueOf(tradeAmount));
        tips.put("currYearTotalTradeAmount", Double.valueOf(currYearTotalTradeAmount.add(new BigDecimal(tradeAmount)).toString()));
        tips.put("onePersentNetCaptialLastYear", Double.valueOf(netAssetsNum.multiply(new BigDecimal(0.01)).toString()));
        tips.put("totalMajorTradeCount", Double.valueOf((majorTradeCount + 1) * 3000));
        return tips;
    }

    /**
     * 合并计算
     */
    public static String getMergeFunds(String reId, String reType) {
        BigDecimal result = BigDecimal.ZERO;
        RelOrg relOrg = relOrgService.get(Integer.parseInt(reId));
        if (relOrg != null) {
            if (StringUtils.isNotBlank(relOrg.getMemGroup())) {
                List<Filter> filters = new Filter.Builder().eq("memGroup", relOrg.getMemGroup()).eq("delflag", "1").build();
                List<RelOrg> relOrgs = relOrgService.select(filters);
                for (RelOrg org : relOrgs) {
                    result = result.add(mergeCal(org));
                }
            } else {
                result = mergeCal(relOrg);
            }
        }
        return result.toString();
    }

    public static void warnLevel(String tradeType, String businessType, BigDecimal tradeAmount) {
        Map<String, Object> warnParam = InsUtil.initWarnParam(tradeType, businessType, tradeAmount);
        if (warnParam != null) {
            String returnCode = ruleCheckService.warnLevelCheck(warnParam);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(returnCode)) {
                List<Filter> filters = new Filter.Builder().eq("tradeType", tradeType).eq("businessType", businessType).eq("warnLevel", returnCode).build();
                WarnDeploy warnDeploy = warnDeployService.get(filters);
                String warnId = warnDeploy.getWayId();
                String[] strings = warnId.split(",");
                for (String str : strings) {
                    if ("email".equals(str)) {
                        iEmailSendService.sendMail("905942980@qq.com", "", "", "交易预警提醒", "交易预警");
                    }
                    if ("notice".equals(str)) {
                        notifyService.createRemind(UserProfileUtils.getUserProfile().getStaffCode(), "交易预警", "交易预警");
                    }
                }
            }
        }
    }

    private static BigDecimal mergeCal(RelOrg org) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal sumFundsTradeAmount = traFundsService.getSumTradeAmount(org);
        BigDecimal sumBusinessTradeAmount = traBusinessService.getSumTradeAmount(org);
        BigDecimal sumInterestTradeAmount = traInterestService.getSumTradeAmount(org);
        BigDecimal sumInvestmentTradeAmount = traInvestmentService.getSumTradeAmount(org);
        BigDecimal sumServiceTradeAmount = traServiceService.getSumTradeAmount(org);
        sumFundsTradeAmount = sumFundsTradeAmount == null ? BigDecimal.ZERO : sumFundsTradeAmount;
        sumBusinessTradeAmount = sumBusinessTradeAmount == null ? BigDecimal.ZERO : sumBusinessTradeAmount;
        sumInterestTradeAmount = sumInterestTradeAmount == null ? BigDecimal.ZERO : sumInterestTradeAmount;
        sumInvestmentTradeAmount = sumInvestmentTradeAmount == null ? BigDecimal.ZERO : sumInvestmentTradeAmount;
        sumServiceTradeAmount = sumServiceTradeAmount == null ? BigDecimal.ZERO : sumServiceTradeAmount;
        result = result.add(sumFundsTradeAmount).add(sumBusinessTradeAmount).add(sumInterestTradeAmount).add(sumInvestmentTradeAmount).add(sumServiceTradeAmount);
        return result;
    }


    /**
     * 是否重大交易判定
     *
     * @param tradeAmount              交易金额
     * @param lastYearTotalTradeAmount 本上年末净资产
     * @param gear                     档位
     * @param baseAmount               基础档位金额 3000W
     * @return
     */
    private static boolean isGreatTrade(BigDecimal tradeAmount, BigDecimal lastYearTotalTradeAmount, BigDecimal gear, BigDecimal baseAmount) {
        if (tradeAmount.compareTo(baseAmount.multiply(gear)) >= 0 && tradeAmount.compareTo(lastYearTotalTradeAmount.multiply(gear)
                .multiply(BigDecimal.valueOf(0.01)).setScale(6, BigDecimal.ROUND_HALF_UP)) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否重大交易判定
     * contractAmount :关联交易金额
     * pracTradeAmount：实际交易金额
     *
     * @param tradeType 交易类型
     * @return
     */
    public static Map GreatCheck(String tradeType, String contractAmount, String pracTradeAmount, String relInfoListStr) {
        HashMap<String, String> result = Maps.newHashMap();
        //是否重大关联交易基数
        String baseAmt = appConfigService.getConfigValue("major_base_amt");
        //上年末净资产(万元)
//        String lastYearNetAsset = appConfigService.getConfigValue("netCaptialLastYear");
        int year = DateUtil.year(new Date());
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("delflag", "1"));
        filterList.add(new Filter("years", String.valueOf(year - 1)));
        filterList.add(new Filter("quarter", "5"));
        filterList.add(new Filter("approvalStatus", "6"));
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("modifiedTime", OrderType.DESC));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList, orders);
        String lastYearNetAsset = null;
        if (CollectionUtil.isNotEmpty(rootOrgInfos)) {
            lastYearNetAsset = String.valueOf(rootOrgInfos.get(0).getNetCaptial());
        }
        //当前年度
        String contractSigningDate = new SimpleDateFormat("yyyy").format(new Date());
        if (StringUtils.isBlank(baseAmt) || StringUtils.isBlank(lastYearNetAsset)) {
            LOGGER.info("执行（保险）交易重大关联交易判定规则集异常...错误信息:{是否重大关联交易基数,上年末净资产(万元)预置参数缺失}");
            result.put("code", "999999");
            result.put("msg", "执行（保险）交易重大关联交易判定规则集异常...错误信息:{是否重大关联交易基数,上年末净资产(万元)预置参数缺失}");
            return result;
        }
        if (StringUtils.isBlank(relInfoListStr) || StringUtils.isBlank(contractAmount)) {
            LOGGER.info("执行（保险）交易重大关联交易判定参数异常... 错误信息：参数缺失{请检查关联方,交易金额," +
                    "本年度与该关联方累计交易金额,交易类型,是否现金认购等参数}");
            result.put("code", "999999");
            result.put("msg", "执行（保险）交易重大关联交易判定参数异常... 错误信息：参数缺失{请检查关联方,交易金额," +
                    "本年度与该关联方累计交易金额,交易类型,是否现金认购等参数}");
            return result;
        }
        /**解析json*/
        List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
        if (CollectionUtil.isEmpty(traRelInfos)) {
            LOGGER.info("执行（保险）交易重大关联交易判定参数异常... 错误信息：参数缺失{请检查关联方,交易金额," +
                    "本年度与该关联方累计交易金额,交易类型,是否现金认购等参数}");
            result.put("code", "999999");
            result.put("msg", "执行（保险）交易重大关联交易判定参数异常... 错误信息：参数缺失{请检查关联方,交易金额," +
                    "本年度与该关联方累计交易金额,交易类型,是否现金认购等参数}");
            return result;
        }
        boolean flag = false;
        //基数3000W
        BigDecimal baseAmtBD = new BigDecimal(baseAmt).setScale(6, BigDecimal.ROUND_HALF_UP);
        //单笔交易金额
        BigDecimal tradeAmountBD = new BigDecimal(contractAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
        //上年度末总资产
        BigDecimal lastYearNetAssetBD = new BigDecimal(lastYearNetAsset).setScale(6, BigDecimal.ROUND_HALF_UP);

        //单笔重大判断 大于等于3000W 且占保险公司上一年度末经审计的净资产的1%以上
        if (tradeAmountBD.compareTo(baseAmtBD) >= 0) {
            flag = isGreatTrade(tradeAmountBD, lastYearNetAssetBD, BigDecimal.valueOf(1), baseAmtBD);
        }
        //累积重大判断
        String notMajorRelNumber = "";
        if (flag) {
            LOGGER.info("单笔重大关联交易...");
            result.put("code", "1");
            result.put("majorTrade", "1");
            result.put("majorType", "1");
            result.put("msg", "重大关联交易");
            return result;
        } else {
            /**判断是否存在实际交易金额，有实际交易金额的业务数据不做累积重大判定，只做单笔重大确认*/
            /*if (StrUtil.isNotBlank(pracTradeAmount)) {
             *//**直接返回判定信息，此时未达到重大，将未达到重大的信息关联方relNumber保存到对应字段中*//*
                for (TraRelInfo traRelInfo : traRelInfos) {
                    String relNumber = traRelInfo.getRelNumber();
                    if ("".equals(notMajorRelNumber)) {
                        notMajorRelNumber = notMajorRelNumber.concat(relNumber);
                    } else {
                        notMajorRelNumber = ",".concat(notMajorRelNumber).concat(relNumber);
                    }
                }
                *//**返回判定结果*//*
                LOGGER.info("一般关联交易...");
                result.put("code", "1");
                result.put("majorTrade", "0");
                result.put("msg", "一般关联交易");
                result.put("notMajorRelNumber", notMajorRelNumber);
                return result;
            }*/
            //档位
            BigDecimal percentNum = new BigDecimal(1);
            /**循环判断所有关联交易对手*/
            for (TraRelInfo traRelInfo : traRelInfos) {
                /**该关联方年度累计重大交易金额*/
                BigDecimal tradeAmount = traRelInfo.getTradeAmount();
                if (tradeAmount == null) {
                    tradeAmount = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
                }
                /**资金运用类计算集团企业其他的资金累加*/
                Integer relId = traRelInfo.getRelId();
                String relType = traRelInfo.getRelType();
                if ("02".equals(tradeType) && "ORG".equals(relType)) {
                    /**查询该公司的所有企业成员，去除自身*/
                    RelOrg relOrg = relOrgService.get(Integer.valueOf(relId));
                    String memGroup = relOrg.getMemGroup();
                    List<RelOrg> relOrgList = new ArrayList<>();
                    if (StrUtil.isNotBlank(memGroup)) {
                        List<Filter> filters = new ArrayList<>();
                        filters.add(new Filter("memGroup", Operator.EQ, memGroup));
                        filters.add(new Filter("delflag", Operator.EQ, "1"));
                        filters.add(new Filter("pid", Operator.NE, Integer.valueOf(relId)));
                        filters.add(new Filter("approvalStatus", Operator.EQ, "6"));
                        relOrgList = relOrgService.select(filters);
                    }
                    if (CollectionUtil.isNotEmpty(relOrgList)) {
                        for (RelOrg org : relOrgList) {
                            /**计算所有的累计重大金额*/
                            Map<String, Object> map = majorTraCheckService.sumRealTradeAmount(org.getPid(), "ORG");
                            tradeAmount.add((BigDecimal) map.get("tradeZeroAmount"));
                        }
                    }
                }
                flag = isGreatTrade(tradeAmountBD.add(tradeAmount), lastYearNetAssetBD, percentNum, baseAmtBD);
                if (flag) {
                    LOGGER.info("累积重大关联交易...");
                    result.put("code", "1");
                    result.put("majorTrade", "1");
                    result.put("majorType", "2");
                    result.put("msg", "重大关联交易");
                } else {
                    if ("".equals(notMajorRelNumber)) {
                        notMajorRelNumber = notMajorRelNumber.concat(traRelInfo.getRelNumber());
                    } else {
                        notMajorRelNumber = notMajorRelNumber.concat(",").concat(traRelInfo.getRelNumber());
                    }
                }
            }
            if (result.isEmpty()) {
                LOGGER.info("一般关联交易...");
                result.put("code", "0");
                result.put("msg", "一般关联交易");
            }
        }
        result.put("notMajorRelNumber", notMajorRelNumber);
        return result;
    }

    /**
     * 合同接口判断是否是重大关联交易
     *
     * @param conJson 合同接口传递来的请求报文
     * @return
     */
    public static String GreatCheck(JSONObject conJson) {
        boolean flag = false;
        String state = "0"; //交易状态：0 一般关联交易  1 单一重大关联交易
        try {
            BigDecimal baseAmtBD = new BigDecimal(appConfigService.getConfigValue("major_base_amt"))
                    .setScale(6, BigDecimal.ROUND_HALF_UP);//是否重大关联交易基数
            BigDecimal lastYearNetAssetBD = new BigDecimal(appConfigService.getConfigValue("netCaptialLastYear"))
                    .setScale(6, BigDecimal.ROUND_HALF_UP);//上年度末总资产   上年末净资产(万元)
            JSONArray anchorList = conJson.getJSONArray("anchorList");//业务数据信息集合
            JSONArray array = new JSONArray();
            for (int i = 0; i < anchorList.size(); i++) {
                JSONObject anchor = anchorList.getJSONObject(i);
                String anchorType = anchor.getString("anchorType");
                if (anchorType.equals("M0")) {//单笔重大判断
                    array.add(anchor);
                    BigDecimal relAmount = new BigDecimal(0);
                    JSONArray relList = anchor.getJSONArray("relList");
                    for (int j = 0; j < relList.size(); j++) {
                        JSONObject rel = relList.getJSONObject(j);//关联方信息
                        String relTranType = rel.getString("relTranType");
                        String relType = rel.getString("relType");//关联方类别（1-自然人，2-法人或机构组织）
                        String relName = rel.getString("relName");//关联方名称
                        String rUscCode = rel.getString("uscCode");//证件号码
                        String rUscType = rel.getString("uscType");//证件类型
                        BigDecimal relTranAmt = rel.getBigDecimal("relTranAmt").setScale(6, BigDecimal.ROUND_HALF_UP);//合同金额
                        switch (relType) {
                            case "1"://1-自然人
                                String relPsnID = getRelPsnID(rUscType, rUscCode);
                                if (StringUtils.isNotBlank(relPsnID) && relTranAmt.compareTo(BigDecimal.valueOf(50)) >= 0) {
                                    relAmount = relAmount.add(relTranAmt);
                                }
                                break;
                            case "2"://2-法人或机构组织
                                String relOrgID = getRelOrgID(rUscType, rUscCode);
                                if (StringUtils.isNotBlank(relOrgID) && relTranAmt.compareTo(BigDecimal.valueOf(500)) >= 0) {
                                    relAmount = relAmount.add(relTranAmt);
                                }
                                break;
                            default:
                        }
                    }
                    if (relAmount.compareTo(baseAmtBD) >= 0) {//1.判断是否是单笔重大关联交易
                        flag = isGreatTrade(relAmount, lastYearNetAssetBD, new BigDecimal(1), baseAmtBD);//单笔大于等于3000W
                    }
                    if (flag) {
                        state = "1";
                    }
                }
            }
            if (!flag) {//如果不是单笔重大，判断是否是累积重大
                for (int i = 0; i < array.size(); i++) {
                    JSONObject anchor = array.getJSONObject(i);
                    String uscCode = anchor.getString("uscCode");//证件号码
                    String uscType = anchor.getString("uscType");//证件类型
                    JSONArray relList = anchor.getJSONArray("relList");//关联交易类型
                    for (int j = 0; j < relList.size(); j++) {
                        JSONObject rel = relList.getJSONObject(j);
                        String relTranType = rel.getString("relTranType");//关联交易类型
                        String relType = rel.getString("relType");//关联方类别（1-自然人，2-法人或机构组织）
                        String relName = rel.getString("relName");//关联方名称
                        String rUscCode = rel.getString("uscCode");//证件号码
                        String rUscType = rel.getString("uscType");//证件类型
                        BigDecimal relTranAmt = rel.getBigDecimal("relTranAmt").setScale(6, BigDecimal.ROUND_HALF_UP);
                        ;//关联交易金额，保留小数点后6位
                        Integer pid = null;
                        switch (relType) {
                            case "1"://1-自然人
                                String relPsnID = getRelPsnID(rUscType, rUscCode);
                                pid = StringUtils.isBlank(relPsnID) ? null : Integer.valueOf(relPsnID);
                                break;
                            case "2"://2-法人或机构组织
                                String relOrgID = getRelOrgID(rUscType, rUscCode);
                                pid = StringUtils.isBlank(relOrgID) ? null : Integer.valueOf(relOrgID);
                                break;
                            default:
                        }
                        BigDecimal currYearTotalTradeAmountBD = new BigDecimal(0);//全年累计交易金额
                        if (pid != null) {
                            if (relTranType.equals("02")) {
                                currYearTotalTradeAmountBD = baseService.getCurrYearTotalTradeAmount(pid, relTranType, relName, relTranType, AccumulatType.ZERO_YES.getHandler(), AccumulatType.COUNT_PART.getHandler());
                            } else {
                                currYearTotalTradeAmountBD = baseService.getCurrYearTotalTradeAmount(pid, relTranType, relName, AccumulatType.ZERO_YES.getHandler(), AccumulatType.COUNT_PART.getHandler());
                            }
                        }
                        BigDecimal sumTransAmount = currYearTotalTradeAmountBD.add(relTranAmt);//累计关联交易额加上本次关联交易额
                        BigDecimal percentNum = new BigDecimal(1);
//                        BigDecimal lastYearNetAssertPercent = lastYearNetAssetBD.multiply(BigDecimal.valueOf(0.01));
//                        if (lastYearNetAssertPercent.compareTo(baseAmtBD) >= 0) {
//                            percentNum = sumTransAmount.divide(lastYearNetAssertPercent, 0, BigDecimal.ROUND_UP);//上年末的百分一大于等于3000W
//                        } else {
//                            percentNum = sumTransAmount.divide(baseAmtBD, 0, BigDecimal.ROUND_UP);//上年末的百分一小于3000W
//                        }
                        flag = isGreatTrade(sumTransAmount, lastYearNetAssetBD, percentNum, baseAmtBD);
                        if (flag) {
                            state = "1";
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * 合同接口判断是否是重大关联交易
     *
     * @param conJson
     * @return
     */
    public static Map<String, String> checkGreatCheck(JSONObject conJson) {
        boolean flag = false;
        Map<String, String> result = new HashMap<>();
        result.put("isGreat", "0");
        String state = "0"; //交易状态：0 一般关联交易  1 单一重大关联交易
        try {
            BigDecimal baseAmtBD = new BigDecimal(appConfigService.getConfigValue("major_base_amt"))
                    .setScale(6, BigDecimal.ROUND_HALF_UP);//是否重大关联交易基数
            BigDecimal lastYearNetAssetBD = new BigDecimal(appConfigService.getConfigValue("netCaptialLastYear"))
                    .setScale(6, BigDecimal.ROUND_HALF_UP);//上年度末总资产   上年末净资产(万元)
            JSONArray anchorList = conJson.getJSONArray("anchorList");//业务数据信息集合
            JSONArray array = new JSONArray();
            for (int i = 0; i < anchorList.size(); i++) {
                JSONObject anchor = anchorList.getJSONObject(i);
                String anchorType = anchor.getString("anchorType");
                if (anchorType.equals("M0")) {//单笔重大判断
                    array.add(anchor);
                    BigDecimal relAmount = new BigDecimal(0);
                    JSONArray relList = anchor.getJSONArray("relList");
                    for (int j = 0; j < relList.size(); j++) {
                        JSONObject rel = relList.getJSONObject(j);//关联方信息
                        String relTranType = rel.getString("relTranType");
                        String relType = rel.getString("relType");//关联方类别（1-自然人，2-法人或机构组织）
                        String relName = rel.getString("relName");//关联方名称
                        String rUscCode = rel.getString("uscCode");//证件号码
                        String rUscType = rel.getString("uscType");//证件类型
                        BigDecimal relTranAmt = rel.getBigDecimal("relTranAmt").setScale(6, BigDecimal.ROUND_HALF_UP);//合同金额
                        switch (relType) {
                            case "1"://1-自然人
                                String relPsnID = getRelPsnID(rUscType, rUscCode);
                                if (StringUtils.isNotBlank(relPsnID) && relTranAmt.compareTo(BigDecimal.valueOf(50)) >= 0) {
                                    relAmount = relAmount.add(relTranAmt);
                                }
                                break;
                            case "2"://2-法人或机构组织
                                String relOrgID = getRelOrgID(rUscType, rUscCode);
                                if (StringUtils.isNotBlank(relOrgID) && relTranAmt.compareTo(BigDecimal.valueOf(500)) >= 0) {
                                    relAmount = relAmount.add(relTranAmt);
                                }
                                break;
                            default:
                        }
                    }
                    if (relAmount.compareTo(baseAmtBD) >= 0) {//1.判断是否是单笔重大关联交易
                        flag = isGreatTrade(relAmount, lastYearNetAssetBD, new BigDecimal(1), baseAmtBD);//单笔大于等于3000W
                    }
                    if (flag) {
                        result.put("isGreat", "1");
                        result.put("majorType", "1");
                    }
                }
            }
            if (!flag) {//如果不是单笔重大，判断是否是累积重大
                for (int i = 0; i < array.size(); i++) {
                    JSONObject anchor = array.getJSONObject(i);
                    String uscCode = anchor.getString("uscCode");//证件号码
                    String uscType = anchor.getString("uscType");//证件类型
                    JSONArray relList = anchor.getJSONArray("relList");//关联交易类型
                    for (int j = 0; j < relList.size(); j++) {
                        JSONObject rel = relList.getJSONObject(j);
                        String relTranType = rel.getString("relTranType");//关联交易类型
                        String relType = rel.getString("relType");//关联方类别（1-自然人，2-法人或机构组织）
                        String relName = rel.getString("relName");//关联方名称
                        String rUscCode = rel.getString("uscCode");//证件号码
                        String rUscType = rel.getString("uscType");//证件类型
                        BigDecimal relTranAmt = rel.getBigDecimal("relTranAmt").setScale(6, BigDecimal.ROUND_HALF_UP);
                        ;//关联交易金额，保留小数点后6位
                        Integer pid = null;
                        switch (relType) {
                            case "1"://1-自然人
                                String relPsnID = getRelPsnID(rUscType, rUscCode);
                                pid = StringUtils.isBlank(relPsnID) ? null : Integer.valueOf(relPsnID);
                                break;
                            case "2"://2-法人或机构组织
                                String relOrgID = getRelOrgID(rUscType, rUscCode);
                                pid = StringUtils.isBlank(relOrgID) ? null : Integer.valueOf(relOrgID);
                                break;
                            default:
                        }
                        BigDecimal currYearTotalTradeAmountBD = new BigDecimal(0);//全年累计交易金额
                        if (pid != null) {
                            if (relTranType.equals("02")) {
                                currYearTotalTradeAmountBD = baseService.getCurrYearTotalTradeAmount(pid, relTranType, relName, relTranType, AccumulatType.ZERO_YES.getHandler(), AccumulatType.COUNT_PART.getHandler());
                            } else {
                                currYearTotalTradeAmountBD = baseService.getCurrYearTotalTradeAmount(pid, relTranType, relName, AccumulatType.ZERO_YES.getHandler(), AccumulatType.COUNT_PART.getHandler());
                            }
                        }
                        BigDecimal sumTransAmount = currYearTotalTradeAmountBD.add(relTranAmt);//累计关联交易额加上本次关联交易额
                        BigDecimal percentNum = new BigDecimal(1);
//                        BigDecimal lastYearNetAssertPercent = lastYearNetAssetBD.multiply(BigDecimal.valueOf(0.01));
//                        if (lastYearNetAssertPercent.compareTo(baseAmtBD) >= 0) {
//                            percentNum = sumTransAmount.divide(lastYearNetAssertPercent, 0, BigDecimal.ROUND_UP);//上年末的百分一大于等于3000W
//                        } else {
//                            percentNum = sumTransAmount.divide(baseAmtBD, 0, BigDecimal.ROUND_UP);//上年末的百分一小于3000W
//                        }
                        flag = isGreatTrade(sumTransAmount, lastYearNetAssetBD, percentNum, baseAmtBD);
                        if (flag) {
                            result.put("isGreat", "1");
                            result.put("majorType", "2");
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 合同接口 - 是否超过监管上限比例判定
     *
     * @param conJson
     * @return
     */
    public static Map<String, String> isOverLimit(JSONObject conJson) {
        Map<String, String> overLimit = Maps.newHashMap();
        overLimit.put("limitCode", "1");
        overLimit.put("limitMsg", "满足限额规则");
        String funds_switch_onoff = appConfigService.getConfigValue("FUNDS_SWITCH_ONOFF");
        if (funds_switch_onoff == null || funds_switch_onoff.equals("OFF")) {
            overLimit.put("limitCode", "1");
            overLimit.put("limitMsg", "资金运用类交易的限额控制规则关闭");
            LOGGER.info("资金运用类交易的限额控制规则关闭");
            return overLimit;
        }
        try {
            JSONArray anchorList = conJson.getJSONArray("anchorList");//获取锚点信息集
            for (int i = 0; i < anchorList.size(); i++) {
                JSONObject anchor = anchorList.getJSONObject(i);
                //获取M0:资产，M1:委托方 类型
                String anchorType = anchor.getString("anchorType");
                if (anchorType.equals("M0")) {
                    String anchorUscType = anchor.getString("uscType");
                    String anchorUscCode = anchor.getString("uscCode");
                    String relIds = "";
                    String anchorContSubType = anchor.getString("contSubType");
                    if (anchorContSubType.equals("2")) {
                        JSONArray relList = anchor.getJSONArray("relList");//获取关联方列表
                        for (int j = 0; j < relList.size(); j++) {
                            JSONObject rel = relList.getJSONObject(j);
                            String relType = rel.getString("relType");//关联方类别（1-自然人，2-法人或机构组织）
                            String relName = rel.getString("relName");//关联方名称
                            String uscType = rel.getString("uscType");//证件类型(字典参考：证件类型_ uscType)
                            String uscCode = rel.getString("uscCode");//证件号码（统一社会信用代码）
                            String relTranAmt = String.valueOf(rel.getBigDecimal("relTranAmt").setScale(6, BigDecimal.ROUND_HALF_UP));//关联交易金额，保留小数点后6位
                            String relTranType = rel.getString("relTranType");//关联交易类型
                            if (relTranType.equals("02") && relType.equals("2")) {//只判断02:资金运用类是否超过监管比例
                                String relId = null;
                                switch (relType) {
                                    case "1"://个人
                                        relId = getRelPsnID(uscType, uscCode);
                                        break;
                                    case "2"://2-法人或机构组织
                                        relId = getRelOrgID(uscType, uscCode);
                                        break;
                                    default:
                                }
                                overLimit = isOverLimit(relId, relTranAmt, null, null, null, null, relIds, null, null, null, null, null, null);
                                String limitCode = String.valueOf(overLimit.get("limitCode"));
                                if (limitCode.equals("0")) {
                                    // 原有 1 满足条件 0 不满足规定
                                    // 改为 0 超限 1 不超限
                                    overLimit.put("limitCode", "0");
                                    overLimit.put("limitMsg", String.valueOf(overLimit.get("limitMsg")));
                                    return overLimit;
                                } else {
                                    //isWarningLimit(relId, relTranAmt,null,null,null,null);
                                    //0 达到预警 1 没有达到
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return overLimit;
    }

    /**
     * 根据机构证件类型，证件号码获取ID
     *
     * @param uscType
     * @param uscCode
     * @return
     */
    private static String getRelOrgID(String uscType, String uscCode) {
        List<Filter> filters = new Filter.Builder().eq("uniscCode", uscCode).
                eq("identifyType", uscType).build();
        List<RelOrg> relOrgs = relOrgService.select(filters);
        if (relOrgs != null && !relOrgs.isEmpty()) {
            return String.valueOf(relOrgs.get(0).getPid());
        }
        return null;
    }

    /**
     * 根据个人证件类型，证件号码获取ID
     *
     * @param uscType
     * @param uscCode
     * @return
     */
    private static String getRelPsnID(String uscType, String uscCode) {
        List<Filter> filters = new Filter.Builder().eq("identifyNumber", uscCode).
                eq("identifyType", uscType).build();
        List<RelPsn> relPsns = relPsnService.select(filters);
        if (relPsns != null && !relPsns.isEmpty()) {
            return String.valueOf(relPsns.get(0).getPid());
        }
        return null;
    }

    /**
     * 是否超限
     *
     * @param relId                关联方ID
     * @param tradeAmount          交易金额
     * @param investmentType       业务类型  股权01 不动产02 银行存款03 金融产品04 与关联方共同投资05 其他资产06
     * @param isOverseasinvestment 是否境外  是0 否1
     * @param underInvolveRel      底层基础资产是否涉及控股股东或控股股东的关联方  是1 否0
     * @param financialProScale    金融产品发行规模
     * @param stockrightType       股权分类  01 未上市  02 上市
     * @param isHoldingSubsidiary  是否控股子公司  0 是 ，1 否
     * @param financialAssstsNum   金融产品备案号
     * @param traRelInfos          多关联交易对手信息
     * @return
     */
    public static Map isOverLimit(String relId, String tradeAmount, String investmentType, String isOverseasinvestment,
                                  String underInvolveRel, String financialProScale, String relIds, String stockrightType,
                                  String isHoldingSubsidiary, String financialAssstsNum, String relType, String warnPercent, List<TraRelInfo> traRelInfos) {
        if (StrUtil.isBlank(warnPercent)) {
            warnPercent = "100";
        }
        BigDecimal warnPercentDefault = new BigDecimal(warnPercent).multiply(new BigDecimal(0.01));
        HashMap<String, String> result = Maps.newHashMap();
        String funds_switch_onoff = appConfigService.getConfigValue("FUNDS_SWITCH_ONOFF");
        if (funds_switch_onoff == null || funds_switch_onoff.equals("OFF")) {
            result.put("limitCode", "1");
            result.put("limitMsg", "资金运用类交易的限额控制规则关闭");
            LOGGER.info("资金运用类交易的限额控制规则关闭");
            return result;
        }
        /**查询上年末机构基础数据*/
        /**获取当前年份*/
        int year = DateUtil.year(new Date());
        /**获取当前季度*/
        int quarter = DateUtil.quarter(new Date());
        /**获取上年末数据*/
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("delflag", "1"));
        filterList.add(new Filter("years", String.valueOf(year - 1)));
        filterList.add(new Filter("quarter", "4"));
        filterList.add(new Filter("approvalStatus", "6"));
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("modifiedTime", OrderType.DESC));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList, orders);
        String endLastYear = null;//上年末总资产
        String netCaptialLastYear = null;//上年末净资产
        if (CollectionUtil.isNotEmpty(rootOrgInfos)) {
            /**上年末总资产*/
            endLastYear = String.valueOf(rootOrgInfos.get(0).getTotalCapital());
            /**上年末净资产*/
            netCaptialLastYear = String.valueOf(rootOrgInfos.get(0).getNetCaptial());
        }
        /**获取上季末数据*/
        String endLastQuarter = null;//上季末总资产
        String lastQuarterRatio = null;//上季末综合偿付能力充足率
        List<Filter> filterList1 = new ArrayList<>();
        filterList1.add(new Filter("delflag", "1"));
        if (quarter - 1 == 0) {
            filterList1.add(new Filter("years", String.valueOf(year - 1)));
            filterList1.add(new Filter("quarter", "4"));
        } else {
            filterList1.add(new Filter("years", year));
            filterList1.add(new Filter("quarter", quarter - 1));
        }
        filterList1.add(new Filter("approvalStatus", "6"));
        List<Order> orders1 = new ArrayList<>();
        orders.add(new Order("modifiedTime", OrderType.DESC));
        List<RootOrgInfo> rootOrgInfos1 = rootOrgInfoService.select(filterList1, orders1);
        if (CollectionUtil.isNotEmpty(rootOrgInfos1)) {
            endLastQuarter = String.valueOf(rootOrgInfos1.get(0).getTotalCapital());
            lastQuarterRatio = rootOrgInfos1.get(0).getQuarterCompreSolvencyRate();
        }
        if (StringUtils.isBlank(endLastYear) || StringUtils.isBlank(netCaptialLastYear) ||
                StringUtils.isBlank(endLastQuarter) || StringUtils.isBlank(lastQuarterRatio)) {
            LOGGER.info("交易是否超限判功能预置参数异常参数缺失:请检查上年末总资产,上年末净资产,上季末总资产," +
                    "上季末综合偿付能力充足率等参数是否设置");
            result.put("limitCode", "999999");
            result.put("limitMsg", "执行交易是否超限判定异常");
            return result;
        }
        boolean flag;//是否满足限额规则
        BigDecimal tradeAmountBD = new BigDecimal(tradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
        ;//单笔交易金额
        BigDecimal tradeAmountTotal = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
        ;//累积交易金额

        BigDecimal endLastYearBD = new BigDecimal(endLastYear).setScale(6, BigDecimal.ROUND_HALF_UP).multiply(warnPercentDefault);//上年末总资产*警告配置比例
        BigDecimal netCaptialLastYearBD = new BigDecimal(netCaptialLastYear).setScale(6, BigDecimal.ROUND_HALF_UP).multiply(warnPercentDefault);//上年末净资产
        BigDecimal endLastQuarterBD = new BigDecimal(endLastQuarter).setScale(6, BigDecimal.ROUND_HALF_UP).multiply(warnPercentDefault);//上季末总资产
        BigDecimal lastQuarterRatioBD = new BigDecimal(lastQuarterRatio).setScale(6, BigDecimal.ROUND_HALF_UP);//上季末综合偿付能力充足率

        //∑交易金额<=min（上年度末总资产*25%，上年度末净资产）

        /**∑所有关联方的投资余额<=min（上年度末总资产*25%，上年度末净资产）*/
        /**统计所有关联方的交易余额*/
        tradeAmountTotal = relFundsService.sumRelTraBalance();
//        Map map = Maps.newHashMap();
//        tradeAmountTotal = getTradeAmountTotal(map);
        if (!(tradeAmountBD.add(tradeAmountTotal).compareTo(netCaptialLastYearBD) <= 0 &&
                tradeAmountBD.add(tradeAmountTotal).compareTo(endLastYearBD.multiply(BigDecimal.valueOf(0.25))) <= 0)) {
            LOGGER.info("资金运用类交易的限额规则不满足...");
            BigDecimal amount_01 = endLastYearBD.multiply(BigDecimal.valueOf(0.25)).setScale(6, BigDecimal.ROUND_HALF_UP);
            //交易金额
            BigDecimal amount_02 = tradeAmountBD.add(tradeAmountTotal);
            result.put("limitCode", "0");
            if (netCaptialLastYearBD.compareTo(amount_01) > 0) {
                //上年末净资产 > 上年度末总资产*25%
                if (amount_02.compareTo(amount_01) > 0 && amount_02.compareTo(netCaptialLastYearBD) < 0) {
                    result.put("limitMsg", "资金运用类交易的限额规则不满足，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年度末总资产X25%" + "X" + warnPercent + "%" + "(" + amount_01 + "万元)");
                } else {
                    result.put("limitMsg", "资金运用类交易的限额规则不满足，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年末净资产" + "X" + warnPercent + "%" + "(" + netCaptialLastYearBD + "万元)");
                }
            } else {
                // 上年度末总资产*25%>上年末净资产
                if (amount_02.compareTo(netCaptialLastYearBD) > 0 && amount_02.compareTo(amount_01) < 0) {
                    result.put("limitMsg", "资金运用类交易的限额规则不满足，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年末净资产" + "X" + warnPercent + "%" + "(" + netCaptialLastYearBD + "万元)");
                } else {
                    result.put("limitMsg", "资金运用类交易的限额规则不满足，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年度末总资产X25%" + "X" + warnPercent + "%" + "(" + amount_01 + "万元)");
                }
            }
            return result;
        }
        /*查询最新的交易余额*/
        FundsInvestment fundsInvestment = fundsInvestmentService.queryFundsInvestment();
        if (fundsInvestment == null) {
            return result;
        }
        /**未上市类别*/
        if ("01".equals(stockrightType)) {
            /**
             * 1. 经与监管侧确认，资产公司不涉及偿付能力，没有承保业务，故根据《保监发[2014]13号文》，资产公司的‘权益类资产投资上限比例’为30%，对未上市权益类投资余额限额计算规则为：未上市权益类投资余额<=上季度末总资产*30%*50%
             * 2. 监管要求保险公司报送‘权益类资产投资上限比例’，系统中支持维护‘上季末综合偿付能力充足率’，根据维护的数值自动适配‘权益类资产投资上限比例’用于计算对关联方股权投资的金额限额。
             * 3.计算规则调整为：
             * 公司上季末综合偿付能力充足率不足100%的，权益类投资余额<=上季度末总资*10%*30%
             * 公司上季末综合偿付能力充足率为100%以上（含此数，下同）但不足150%的，所有关联方权益类投资余额<=上季度末总资*20%*30%
             * 公司上季末综合偿付能力充足率为150%以上但不足200%的，权益类投资余额<=上季度末总资*25%*30%
             * 公司上季末综合偿付能力充足率为200%以上但不足250%的，权益类投资余额<=上季度末总资*30%*30%
             * 公司上季末综合偿付能力充足率为250%以上但不足300%的，权益类投资余额<=上季度末总资*35%*30%
             * 公司上季末综合偿付能力充足率为300%以上但不足350%的，权益类投资余额<=上季度末总资*40%*30%
             * 公司上季末综合偿付能力充足率为350%以上的，权益类投资余额<=上季度末总资*45%*30%
             * 4.经办人员通过资金运用类数据管理-资金运用类投资余额管理，维护已发生的权益类资产投资余额。
             * 5.对于法律合规APP推送和手工新增的交易数据，如事前判断当前本笔交易是否触发比例上限。需使用：未上市权益类资产投资余额（最新时间）+本笔关联交易金额（审批金额），与监管的限额进行对比计算。
             */
            /**含左不含右*/
            BigDecimal limitAmount = new BigDecimal(0);
            if (lastQuarterRatioBD.compareTo(new BigDecimal(1)) < 0) {
                //<1
                limitAmount = endLastQuarterBD.multiply(new BigDecimal(0.1)).multiply(new BigDecimal(0.3));
            } else if (lastQuarterRatioBD.compareTo(new BigDecimal(1.5)) < 0) {
                //1~1.5
                limitAmount = endLastQuarterBD.multiply(new BigDecimal(0.2)).multiply(new BigDecimal(0.3));
            } else if (lastQuarterRatioBD.compareTo(new BigDecimal(2)) < 0) {
                //1.5~2
                limitAmount = endLastQuarterBD.multiply(new BigDecimal(0.25)).multiply(new BigDecimal(0.3));
            } else if (lastQuarterRatioBD.compareTo(new BigDecimal(2.5)) < 0) {
                limitAmount = endLastQuarterBD.multiply(new BigDecimal(0.3)).multiply(new BigDecimal(0.3));
            } else if (lastQuarterRatioBD.compareTo(new BigDecimal(3)) < 0) {
                limitAmount = endLastQuarterBD.multiply(new BigDecimal(0.35)).multiply(new BigDecimal(0.3));
            } else if (lastQuarterRatioBD.compareTo(new BigDecimal(3.5)) < 0) {
                limitAmount = endLastQuarterBD.multiply(new BigDecimal(0.4)).multiply(new BigDecimal(0.3));
            } else {
                //大于等于3.5
                limitAmount = endLastQuarterBD.multiply(new BigDecimal(0.45)).multiply(new BigDecimal(0.3));
            }
            //未上市类投资余额
            BigDecimal unlistedBalance = fundsInvestment.getUnlistedBalance();
            if (unlistedBalance == null) {
                unlistedBalance = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            BigDecimal total = unlistedBalance.add(tradeAmountBD);
            //对比金额
            if (total.compareTo(limitAmount) > 0) {
                //需要提示限额
                LOGGER.info("未上市权益类投资限额不满足...");
                result.put("limitCode", "0");
                result.put("limitMsg", "未上市权益类关联交易投资限额规则不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类业务类型为【未上市权益类】的关联交易为" +
                        "(" + total + "万元)大于上季度末总资限制" + "X" + warnPercent + "%" + "(" + limitAmount + "万元)");
                return result;
            }
        }
        /**不动产类关联交易投资限额*/
        if (StringUtils.isNotBlank(investmentType) && "02".equals(investmentType)) {
            /**
             * 1不动产类投资余额<=上季度末总资*30%*30%
             * 2.经办人员通过资金运用类数据管理-资金运用类投资余额管理，维护已发生的不动产类资产投资余额。
             * 3.对于法律合规APP推送和手工新增的交易数据，如事前判断当前本笔交易是否触发比例上限。需使用：不动产类投资余额（最新时间）+本笔关联交易金额（审批金额），与监管的限额进行对比计算。
             */
            BigDecimal realEstateBalance = fundsInvestment.getRealEstateBalance();
            if (realEstateBalance == null) {
                realEstateBalance = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            /*if(!"0".equals(isHoldingSubsidiary)){
             *//**控股子公司无需加上本笔的交易金额*//*
            }*/
            // TODO: 2021/8/17 判断交易对手是否是控股子公司
            realEstateBalance = realEstateBalance.add(tradeAmountBD);
            /**规则限额*/
            BigDecimal limit = endLastQuarterBD.multiply(BigDecimal.valueOf(0.3)).multiply(BigDecimal.valueOf(0.3)).setScale(6, BigDecimal.ROUND_HALF_UP);
            flag = realEstateBalance.compareTo(limit) <= 0;
            if (!flag) {
                result.put("limitCode", "0");
                result.put("limitMsg", "不动产类关联交易投资限额不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类业务类型为" +
                        "【不动产】的关联交易金额(" + realEstateBalance + "万元)大于上季度末总资X30%X50%" + "X" + warnPercent + "%" + "(" + limit + "万元)");
                return result;
            }
        }
        /**金融资产类关联交易投资限额*/
        if (StringUtils.isNotBlank(investmentType) && "04".equals(investmentType)) {
            /**
             * 1.金融资产投资余额<=上季度末总资*25%*30%
             * 2.经办人员通过资金运用类数据管理-资金运用类投资余额管理，维护已发生的金融资产投资余额。
             * 3.对于法律合规APP推送和手工新增的交易数据，如事前判断当前本笔交易是否触发比例上限。需使用：金融资产投资余额（最新时间）+本笔关联交易金额（审批金额），与监管的限额进行对比计算。
             */
            BigDecimal otherFinanceBalance = fundsInvestment.getOtherFinanceBalance();
            if (otherFinanceBalance == null) {
                otherFinanceBalance = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            /*if(!"0".equals(isHoldingSubsidiary)){
             *//**控股子公司无需加上本笔的交易金额*//*
            }*/
            // TODO: 2021/8/17 判断交易对手是否是控股子公司
            otherFinanceBalance = otherFinanceBalance.add(tradeAmountBD);
            BigDecimal limit = endLastQuarterBD.multiply(BigDecimal.valueOf(0.25)).multiply(BigDecimal.valueOf(0.3)).setScale(6, BigDecimal.ROUND_HALF_UP);
            flag = otherFinanceBalance.compareTo(limit) <= 0;
            if (flag) {
                /**投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额*/
                /**
                 * 1.本笔交易“投资金融产品的份额占比”<=50%；
                 * 2.投资金融产品的份额占比=同一产品投资余额/金融产品发行规模*100%
                 * 3.对于法律合规APP推送和手工新增的交易数据，如事前判断当前本笔交易是否触发比例上限，需累加该金融产品的审批金额金额之和+本笔关联交易金额（审批金额）
                 */
                if ("1".equals(underInvolveRel)) {
                    /**查询资金运用类该类金融产品的投资总额*/
                    if (StrUtil.isNotBlank(financialAssstsNum) && StrUtil.isNotBlank(financialProScale)) {
                        BigDecimal proAmount = traFundsService.sumTradeAmountByProNum(financialAssstsNum);
                        proAmount = proAmount.setScale(6, BigDecimal.ROUND_HALF_UP);
                        /*if(!"0".equals(isHoldingSubsidiary)){

                        }*/
                        // TODO: 2021/8/17 判断交易对手是否是控股子公司
                        proAmount = proAmount.add(tradeAmountBD);
                        /**金融产品发行规模*/
                        BigDecimal financialProScaleBD = new BigDecimal(financialProScale).setScale(6, BigDecimal.ROUND_HALF_UP);
                        BigDecimal percent = proAmount.divide(financialProScaleBD);
                        if (percent.compareTo(BigDecimal.valueOf(0.5)) > 0) {
                            LOGGER.info("投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额不满足...");
                            result.put("limitCode", "0");
                            result.put("limitMsg", "投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额不满足，详情如下：投资金融产品的份额占比>50%，本次投" +
                                    "资金融产品的份额比为" + percent.doubleValue() * 100 + "%X" + warnPercent + "%");
                            result.put("percent", String.valueOf(percent.doubleValue() * 100));
                            return result;
                        }
                    }
                }
            } else {
                LOGGER.info("金融资产类关联交易投资限额不满足...");
                result.put("limitCode", "0");
                result.put("limitMsg", "金融资产类关联交易投资限额规则不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类业务类型为【金融资产】的关联交易为" +
                        "(" + otherFinanceBalance + "万元)大于上季度末总资X25%X30%" + "X" + warnPercent + "%" + "(" + limit + "万元)");
                return result;
            }
        }

        /**境外投资的投资限额*/
        if (StringUtils.isNotBlank(isOverseasinvestment) && "0".equals(isOverseasinvestment)) {
            /**
             * 1.境外投资投资余额<=上季度末总资*15%*30%
             * 2.对于法律合规APP推送和手工新增的交易数据，如事前判断当前本笔交易是否触发比例上限，需使用：境外投资余额（最新）+本笔关联交易金额（审批金额），与监管的限额进行对比计算。
             */
            BigDecimal foreignBalance = fundsInvestment.getForeignBalance();
            if (foreignBalance == null) {
                foreignBalance = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
            }

            /* if(!"0".equals(isHoldingSubsidiary)){
             *//**控股子公司无需加上本笔的交易金额*//*
            }*/
            // TODO: 2021/8/17 判断交易对手是否是控股子公司
            foreignBalance = foreignBalance.add(tradeAmountBD);
            BigDecimal limit = endLastQuarterBD.multiply(BigDecimal.valueOf(0.15)).multiply(BigDecimal.valueOf(0.3)).setScale(6, BigDecimal.ROUND_HALF_UP);
            flag = foreignBalance.compareTo(limit) <= 0;
            if (!flag) {
                LOGGER.info("境外投资的投资限额不满足...");
                result.put("limitCode", "0");
                result.put("limitMsg", "境外投资的投资限额不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类“是否为境外投资”字段为【是】的关联交易金额为" +
                        "(" + foreignBalance + "万元)大于上季度末总资X15%X30%" + "X" + warnPercent + "%" + "(" + limit + "万元)");
                return result;
            }
        }

        /**对单一关联方全部投资的投资限额*/
        if (CollectionUtil.isNotEmpty(traRelInfos)) {
            /**
             * 单一关联方余额计算规则
             * 1.资金运用类投资余额=保险公司与关联方A发生的资金运用类关联交易投资余额+保险公司与关联方A的集团成员B发生的资金运用类关联交易投资余额
             * 2.识别集团成员规则：关联方基本信息页‘所属集团证件号码’相同的不同关联方，即集团成员企业
             * 3.目前资金运用类投资余额为系统内手工维护，系统应判断已维护的关联方投资余额，关联方之间是否为企业成员，如是企业成员，应进行投资余额的合并计算。
             */
            /**统计当前关联方的投资余额   需要加上本公司的集团成员的余额*/
            //查询关联方信息
            for (TraRelInfo traRelInfo : traRelInfos) {
                BigDecimal relAmount = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
                /**自然人关联方*/
                relId = String.valueOf(traRelInfo.getRelId());
                relType = traRelInfo.getRelType();
                if ("PSN".equals(relType)) {
                    relAmount = relFundsService.sumAmountByRelId(relId, relType);
                }
                /**机构关联方，需要合并集团成员的投资余额*/
                if ("ORG".equals(relType)) {
                    /**查询该机构的所有的成员包含自身*/
                    RelOrg relOrg = relOrgService.get(Integer.valueOf(relId));
                    String memGroup = relOrg.getMemGroup();
                    List<RelOrg> relOrgList = new ArrayList<>();
                    if (StrUtil.isNotBlank(memGroup)) {
                        List<Filter> filters = new ArrayList<>();
                        filters.add(new Filter("memGroup", Operator.EQ, memGroup));
                        filters.add(new Filter("delflag", Operator.EQ, "1"));
                        filters.add(new Filter("approveStatus", Operator.EQ, "6"));
                        relOrgList = relOrgService.select(filters);
                    }
                    for (RelOrg org : relOrgList) {
                        BigDecimal memberAmount = relFundsService.sumAmountByRelId(String.valueOf(org.getPid()), relType);
                        relAmount.add(memberAmount);
                    }
                }
                // TODO: 2021/8/17 判断控股子公司剔除本笔数据
                relAmount = relAmount.add(tradeAmountBD);
                BigDecimal limit = netCaptialLastYearBD.multiply(BigDecimal.valueOf(0.3)).setScale(6, BigDecimal.ROUND_HALF_UP);
                flag = relAmount.compareTo(limit) <= 0;
                if (!flag) {
                    LOGGER.info("对单一关联方全部投资的投资限额不满足...");
                    result.put("limitCode", "0");
                    result.put("limitMsg", "对单一关联方全部投资的投资限额不满足，详情如下：保险公司本年度对单一关联方发生的全部资金运用类关联交易金额为" +
                            "(" + relAmount + "万元)大于上年度净资产X30%" + "X" + warnPercent + "%" + "(" + limit + "万元)");
                    return result;
                }
            }
        }

        /*if (StringUtils.isNotBlank(investmentType) && "01".equals(investmentType)) {//股权
            BigDecimal ratio = BigDecimal.valueOf(0.3);//getRatio(lastQuarterRatioBD);//限额比例
//            map.put("investmentType", investmentType);
//            tradeAmountTotal = getTradeAmountTotal(map);
            flag = isOverrunByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, ratio);
            if (!flag) {
                LOGGER.info("股权类关联交易投资限额限额不满足...");
                BigDecimal amount_03_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_03 = endLastQuarterBD.multiply(ratio).multiply(BigDecimal.valueOf(0.5)).setScale(6,
                        BigDecimal.ROUND_HALF_UP);
                BigDecimal amount_03_02 = ratio.multiply(BigDecimal.valueOf(100));
                result.put("limitCode", "0");
//                result.put("limitMsg", "股权类关联交易投资限额不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类业" +
//                        "务类型为【股权】的关联交易总金额为(" + amount_03_01 + "万元)，上季末综合偿付能力充足率为(" + lastQuarterRatioBD + "%)," +
//                        "关联交易总金额大于上季度末总资产X" + amount_03_02 + "%X50%(" + amount_03 + "万元))");

                result.put("limitMsg", "股权类关联交易投资限额不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类业" +
                        "务类型为【股权】的关联交易总金额为(" + amount_03_01 + "万元)，" +
                        "关联交易总金额大于上季度末总资产X" + amount_03_02 + "%X50%(" + amount_03 + "万元))");

                return result;
            }
        } else if (StringUtils.isNotBlank(investmentType) && "02".equals(investmentType)) {//不动产
//            map.put("investmentType", investmentType);
//            tradeAmountTotal = getTradeAmountTotal(map);
            *//*不动产余额*//*
            BigDecimal realEstateBalance = fundsInvestment.getRealEstateBalance();
            if(realEstateBalance == null) {
                realEstateBalance = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            flag = realEstateBalance.add(tradeAmountBD).compareTo(endLastQuarterBD.multiply(new BigDecimal(0.3)).multiply(new BigDecimal(0.5))) <= 0;
//            flag = isOverrunByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, BigDecimal.valueOf(0.3));
            if (!flag) {
                LOGGER.info("不动产类关联交易投资限额不满足...");
//                BigDecimal amount_04_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_04_01 = realEstateBalance.add(tradeAmountBD);
                BigDecimal amount_04 = endLastQuarterBD.multiply(BigDecimal.valueOf(0.3)).multiply(BigDecimal.valueOf(0.5)).
                        setScale(6, BigDecimal.ROUND_HALF_UP);
                result.put("limitCode", "0");
                result.put("limitMsg", "不动产类关联交易投资限额不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类业务类型为" +
                        "【不动产】的关联交易金额(" + amount_04_01 + "万元)大于上季度末总资X30%X50%(" + amount_04 + "万元)");
                return result;
            }
        } else if (StringUtils.isNotBlank(investmentType) && "04".equals(investmentType)) {//金融产品
//            map.put("investmentType", investmentType);
//            tradeAmountTotal = getTradeAmountTotal(map);
//            flag = isOverrunByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, BigDecimal.valueOf(0.25));
            //其他类金融资产
            BigDecimal otherFinanceBalance = fundsInvestment.getOtherFinanceBalance();
            if(otherFinanceBalance == null) {
                otherFinanceBalance = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            flag = otherFinanceBalance.add(tradeAmountBD).compareTo(endLastQuarterBD.multiply(new BigDecimal(0.3)).multiply(new BigDecimal(0.5))) <= 0;
            if (flag) {
                *//*if (StringUtils.isNotBlank(underInvolveRel) && StringUtils.isNotBlank(financialProScale) && "1".equals(underInvolveRel)) {//投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额
                    map.put("underInvolveRel", underInvolveRel);
                    tradeAmountTotal = getTradeAmountTotal(map);
                    BigDecimal financialProScaleBD = new BigDecimal(financialProScale).setScale(6, BigDecimal.ROUND_HALF_UP);//金融产品发行规模
                    BigDecimal sharePercent = tradeAmountTotal.add(tradeAmountBD).divide(financialProScaleBD, 4, BigDecimal.ROUND_HALF_UP);
                    result.put("sharePercent", String.valueOf(sharePercent.doubleValue() * 100));
                    if (sharePercent.compareTo(BigDecimal.valueOf(0.6)) > 0) {
                        LOGGER.info("投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额不满足...");
                        result.put("limitCode", "0");
                        result.put("limitMsg", "投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额不满足，详情如下：投资金融产品的份额占比>60%，本次投" +
                                "资金融产品的份额比为" + sharePercent.doubleValue() * 100 + "%");
                        result.put("sharePercent", String.valueOf(sharePercent.doubleValue() * 100));
                        return result;
                    }
                }*//*
            } else {
                LOGGER.info("金融资产类关联交易投资限额不满足...");
//                BigDecimal amount_05_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_05_01 = otherFinanceBalance.add(tradeAmountBD);
                BigDecimal amount_05 = endLastQuarterBD.multiply(BigDecimal.valueOf(0.25)).multiply(BigDecimal.valueOf(0.5));
                result.put("limitCode", "0");
                result.put("limitMsg", "金融资产类关联交易投资限额规则不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类业务类型为【金融资产】的关联交易为" +
                        "(" + amount_05_01 + "万元)大于上季度末总资X25%X50%(" + amount_05 + "万元)");
                return result;
            }
        }
        if (StringUtils.isNotBlank(isOverseasinvestment) && "0".equals(isOverseasinvestment)) {//境外投资的投资限额
//            map.clear();
//            map.put("isOverseasinvestment", isOverseasinvestment);
//            tradeAmountTotal = getTradeAmountTotal(map);
            BigDecimal foreignBalance = fundsInvestment.getForeignBalance();
            if(foreignBalance == null) {
                foreignBalance = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
            }
            flag = foreignBalance.add(tradeAmountBD).compareTo(endLastQuarterBD.multiply(new BigDecimal(0.15)).multiply(new BigDecimal(0.5))) <= 0;
//            flag = isOverrunByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, BigDecimal.valueOf(0.15));
            if (!flag) {
                LOGGER.info("境外投资的投资限额不满足...");
                BigDecimal amount_06_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_06 = endLastQuarterBD.multiply(BigDecimal.valueOf(0.15)).multiply(BigDecimal.valueOf(0.5));
                result.put("limitCode", "0");
                result.put("limitMsg", "境外投资的投资限额不满足，详情如下：保险公司本年度对全部关联方发生的资金运用类“是否为境外投资”字段为【是】的关联交易金额为" +
                        "(" + amount_06_01 + "万元)大于上季度末总资X15%X50%(" + amount_06 + "万元)");
                return result;
            }
        }
        //对单一关联方全部投资的投资限额
        *//*if (StringUtils.isNotBlank(relId)) {
            map.clear();
            map.put("relId", relId);
            tradeAmountTotal = getTradeAmountTotalByRelId(map);
            if (tradeAmountTotal.add(tradeAmountBD).compareTo(endLastYearBD.multiply(BigDecimal.valueOf(0.15))) > 0) {
                LOGGER.info("对单一关联方全部投资的投资限额不满足...");
                result.put("limitCode", "0");
                BigDecimal amount_07_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_07 = endLastYearBD.multiply(BigDecimal.valueOf(0.15));
                result.put("limitMsg", "对单一关联方全部投资的投资限额不满足，详情如下：保险公司本年度对单一关联方发生的全部资金运用类关联交易金额为" +
                        "(" + amount_07_01 + "万元)大于上年度末总资X15%(" + amount_07 + "万元)");
                return result;
            }
        }*/
        LOGGER.info("满足限额规则...");
        result.put("limitCode", "1");
        result.put("limitMsg", "满足限额规则");
        return result;
    }

    /**
     * 是否超限预警  限额的80%
     *
     * @param relId                关联方ID
     * @param tradeAmount          交易金额
     * @param investmentType       业务类型  股权01 不动产02 银行存款03 金融产品04 与关联方共同投资05 其他资产06
     * @param isOverseasinvestment 是否境外  是0 否1
     * @param underInvolveRel      底层基础资产是否涉及控股股东或控股股东的关联方  是1 否0
     * @param financialProScale    金融产品发行规模
     * @return
     */
    public static Map isWarningLimit(String relId, String tradeAmount, String investmentType, String isOverseasinvestment,
                                     String underInvolveRel, String financialProScale) {
        HashMap<String, String> result = Maps.newHashMap();
        String funds_switch_onoff = appConfigService.getConfigValue("FUNDS_SWITCH_ONOFF");
        if (funds_switch_onoff == null || funds_switch_onoff.equals("OFF")) {
            result.put("limitCode", "1");
            result.put("limitMsg", "资金运用类交易的限额控制规则关闭");
            LOGGER.info("资金运用类交易的限额控制规则关闭");
            return result;
        }
        String endLastYear = appConfigService.getConfigValue("endLastYear"); //上年末总资产
        String netCaptialLastYear = appConfigService.getConfigValue("netCaptialLastYear");//上年末净资产
        String endLastQuarter = appConfigService.getConfigValue("endLastQuarter");//上季末总资产
//        String lastQuarterRatio = appConfigService.getConfigValue("lastQuarterRatio");//上季末综合偿付能力充足率

        if (StringUtils.isBlank(endLastYear) || StringUtils.isBlank(netCaptialLastYear) ||
                StringUtils.isBlank(endLastQuarter)) {
            LOGGER.info("交易是否超限判定参数异常... 错误信息：参数缺失");
            result.put("limitWarningCode", "999999");
            result.put("limitWarningMsg", "执行交易是否达到预警值判定异常。");
            return result;
        }
        //是否满足限额规则
        boolean flag;
        BigDecimal tradeAmountBD = new BigDecimal(tradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);//单笔交易金额
        BigDecimal tradeAmountTotal = new BigDecimal(0);//累积交易金额
        BigDecimal endLastYearBD = new BigDecimal(endLastYear).setScale(6, BigDecimal.ROUND_HALF_UP);//上年末总资产
        BigDecimal netCaptialLastYearBD = new BigDecimal(netCaptialLastYear).setScale(6, BigDecimal.ROUND_HALF_UP);//上年末净资产
        BigDecimal endLastQuarterBD = new BigDecimal(endLastQuarter).setScale(6, BigDecimal.ROUND_HALF_UP);//上季末总资产
//        BigDecimal lastQuarterRatioBD = new BigDecimal(lastQuarterRatio).setScale(6, BigDecimal.ROUND_HALF_UP); //上季末综合偿付能力充足率
        //∑交易金额<=min（上年度末总资产*30%，上年度末净资产）
        Map map = Maps.newHashMap();
        tradeAmountTotal = getTradeAmountTotal(map);
        if (!(tradeAmountBD.add(tradeAmountTotal).compareTo(netCaptialLastYearBD.multiply(BigDecimal.valueOf(0.8))) <= 0 &&
                tradeAmountBD.add(tradeAmountTotal).compareTo(endLastYearBD.multiply(BigDecimal.valueOf(0.3)).multiply(BigDecimal.valueOf(0.8))) <= 0)) {
            LOGGER.info("资金运用类交易的限额达到预警值...");
            BigDecimal amount_01 = endLastYearBD.multiply(BigDecimal.valueOf(0.3)).multiply(BigDecimal.valueOf(0.8)).
                    setScale(6, BigDecimal.ROUND_HALF_UP);//上年末总资产*30%*80%
            BigDecimal amount_02 = tradeAmountBD.add(tradeAmountTotal);//交易金额
            BigDecimal amount_02_01 = netCaptialLastYearBD.multiply(BigDecimal.valueOf(0.8));//上年末净资产*80%
            result.put("limitWarningCode", "0");
            //上年末净资产>上年度末总资产
            if (amount_02_01.compareTo(amount_01) > 0) {
                //交易总金额<上年末净资产*80% && 交易总金额>上年度末总资产*30%*80%
                if (amount_02.compareTo(amount_01) > 0 && amount_02.compareTo(amount_02_01) < 0) {
                    result.put("limitWarningMsg", "资金运用类交易的限额达到预警值，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年度末总资产X30%X80%(" + amount_01 + "万元)");
                } else {
                    result.put("limitWarningMsg", "资金运用类交易的限额达到预警值，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年末净资产X80%(" + amount_02_01 + "万元)");
                }
            } else {//上年末净资产<上年度末总资产
                //交易总金额>上年末净资产*80% && 交易总金额<上年度末总资产*30%*80%
                if (amount_02.compareTo(amount_02_01) > 0 && amount_02.compareTo(amount_01) < 0) {
                    result.put("limitWarningMsg", "资金运用类交易的限额达到预警值，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年末净资产X80%(" + amount_02_01 + "万元)");
                } else {
                    result.put("limitWarningMsg", "资金运用类交易的限额达到预警值，详情如下：保险公司本年度对全部关" +
                            "联方发生的所有资金运用类交易总金额(" + amount_02 + "万元)大于上年度末总资产X30%X80%(" + amount_01 + "万元)");
                }
            }
            return result;
        }
        if (StringUtils.isNotBlank(investmentType) && "01".equals(investmentType)) {//股权
            BigDecimal ratio = BigDecimal.valueOf(0.3);//getRatio(lastQuarterRatioBD);//限额比例
            map.put("investmentType", investmentType);
            tradeAmountTotal = getTradeAmountTotal(map);
            flag = isWarningByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, ratio);
            if (!flag) {
                LOGGER.info("股权类关联交易投资限额限额达到预警值...");
                BigDecimal amount_03_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_03 = endLastQuarterBD.multiply(ratio).multiply(BigDecimal.valueOf(0.5)).multiply(BigDecimal.valueOf(0.8)).
                        setScale(6, BigDecimal.ROUND_HALF_UP);
                BigDecimal amount_03_02 = ratio.multiply(BigDecimal.valueOf(100));
                result.put("limitWarningCode", "0");
//                result.put("limitWarningMsg", "股权类关联交易投资限额限额达到预警值，详情如下：保险公司本年度对全部关联方发生的资金运用类业" +
//                        "务类型为【股权】的关联交易总金额为(" + amount_03_01 + "万元),上季末综合偿付能力充足率为(" + lastQuarterRatioBD + "%)," +
//                        "关联交易总金额大于上季度末总资" + amount_03_02 + "%X50%X80%(" + amount_03 + "万元))");

                result.put("limitWarningMsg", "股权类关联交易投资限额限额达到预警值，详情如下：保险公司本年度对全部关联方发生的资金运用类业" +
                        "务类型为【股权】的关联交易总金额为(" + amount_03_01 + "万元)，" +
                        "关联交易总金额大于上季度末总资" + amount_03_02 + "%X50%X80%(" + amount_03 + "万元))");

                return result;
            }
        } else if ("02".equals(investmentType)) {//不动产
            map.put("investmentType", investmentType);
            tradeAmountTotal = getTradeAmountTotal(map);
            flag = isWarningByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, BigDecimal.valueOf(0.3));
            if (!flag) {
                LOGGER.info("不动产类关联交易投资限额达到预警值...");
                BigDecimal amount_04_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_04 = endLastQuarterBD.multiply(BigDecimal.valueOf(0.3)).multiply(BigDecimal.valueOf(0.5)).
                        multiply(BigDecimal.valueOf(0.8)).setScale(6, BigDecimal.ROUND_HALF_UP);
                result.put("limitWarningCode", "0");
                result.put("limitWarningMsg", "不动产类关联交易投资限额达到预警值，详情如下：保险公司本年度对全部关联方发生的资金运用类业务类型为" +
                        "【不动产】的关联交易金额(" + amount_04_01 + "万元)大于上季度末总资X30%X50%X80%(" + amount_04 + "万元)");
                return result;
            }
        }
//        else if ("04".equals(investmentType)) {//金融产品
//            map.put("investmentType", investmentType);
//            tradeAmountTotal = getTradeAmountTotal(map);
//            flag = isWarningByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, BigDecimal.valueOf(0.25));
//            if (flag) {
//                if (StringUtils.isNotBlank(underInvolveRel) && StringUtils.isNotBlank(financialProScale) && "1".equals(underInvolveRel)) {//投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额
//                    map.put("underInvolveRel", underInvolveRel);
//                    tradeAmountTotal = getTradeAmountTotal(map);
//                    BigDecimal financialProScaleBD = new BigDecimal(financialProScale);//金融产品发行规模
//                    BigDecimal sharePercent = tradeAmountTotal.add(tradeAmountBD).divide(financialProScaleBD, 4, BigDecimal.ROUND_HALF_UP);
//                    result.put("sharePercent", sharePercent.doubleValue() * 100 + "%");
//                    if (sharePercent.compareTo(BigDecimal.valueOf(0.6).multiply(BigDecimal.valueOf(0.8))) > 0) {
//                        LOGGER.info("投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额达到预警值...");
//                        result.put("limitWarningCode", "0");
//                        result.put("limitWarningMsg", "投资金融产品涉及底层资产及控股股东或控制股东的关联方的投资限额达到预警值:详情如下:(投资金融产品的份额占比>60%*80%，本次投" +
//                                "资金融产品的份额比为" + sharePercent.doubleValue() * 100 + "%");
//                        result.put("sharePercent", String.valueOf(sharePercent.doubleValue() * 100));
//                    }
//                }
//            } else {
//                LOGGER.info("金融资产类关联交易投资限额达到预警值...");
//                BigDecimal amount_05_01 = tradeAmountTotal.add(tradeAmountBD);
//                BigDecimal amount_05 = endLastQuarterBD.multiply(BigDecimal.valueOf(0.25)).multiply(BigDecimal.valueOf(0.5).multiply(BigDecimal.valueOf(0.8)));
//                result.put("limitWarningCode", "0");
//                result.put("limitWarningMsg", "金融资产类关联交易投资限额达到预警值:详情如下:(保险公司本年度对全部关联方发生的资金运用类业务类型为【金融资产】的关联交易为" +
//                        "(" + amount_05_01 + "万元)大于上季度末总资*25%*50%*80%(" + amount_05 + "万元)");
//                return result;
//            }
//        }
        if (StringUtils.isNotBlank(isOverseasinvestment) && "0".equals(isOverseasinvestment)) {//境外投资的投资限额
            map.clear();
            map.put("isOverseasinvestment", isOverseasinvestment);
            tradeAmountTotal = getTradeAmountTotal(map);
            flag = isWarningByLastQuarter(tradeAmountTotal.add(tradeAmountBD), endLastQuarterBD, BigDecimal.valueOf(0.15));
            if (!flag) {
                LOGGER.info("境外投资的投资限额达到预警值...");
                BigDecimal amount_06_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_06 = endLastQuarterBD.multiply(BigDecimal.valueOf(0.15)).multiply(BigDecimal.valueOf(0.5)).multiply(BigDecimal.valueOf(0.8));
                result.put("limitWarningCode", "0");
                result.put("limitWarningMsg", "境外投资的投资限额达到预警值，详情如下：保险公司本年度对全部关联方发生的资金运用类“是否为境外投资”字段为【是】的关联交易金额为" +
                        "(" + amount_06_01 + "万元)大于上季度末总资X15%X50%X80%(" + amount_06 + "万元)");
                return result;
            }
        }
        //对单一关联方全部投资的投资限额
        if (StringUtils.isNotBlank(relId)) {
            map.clear();
            map.put("relId", relId);
            tradeAmountTotal = getTradeAmountTotalByRelId(map);
            if (tradeAmountTotal.add(tradeAmountBD).compareTo(endLastYearBD.multiply(BigDecimal.valueOf(0.15)).multiply(BigDecimal.valueOf(0.8))) > 0) {
                LOGGER.info("对单一关联方全部投资的投资限额达到预警值...");
                result.put("limitWarningCode", "0");
                BigDecimal amount_07_01 = tradeAmountTotal.add(tradeAmountBD);
                BigDecimal amount_07 = endLastYearBD.multiply(BigDecimal.valueOf(0.15)).multiply(BigDecimal.valueOf(0.8));
                result.put("limitWarningMsg", "对单一关联方全部投资的投资限额达到预警值，详情如下：保险公司本年度对单一关联方发生的全部资金运用类关联交易金额为" +
                        "(" + amount_07_01 + "万元)大于上年度末总资X15%X80%(" + amount_07 + "万元)");
                return result;
            }
        }
        LOGGER.info("满足限额预警规则...");
        result.put("limitWarningCode", "1");
        result.put("limitWarningMsg", "满足限额预警规则");
        return result;
    }

    /**
     * 计算单个关联方累积交易金额
     *
     * @return
     */
    private static BigDecimal getTradeAmountTotalByRelId(Map map) {
        BigDecimal tradeAmountTotal = new BigDecimal(0);
        List<String> traSubNameList = new ArrayList<String>();
        traSubNameList.add("0001");//中再资产
        traSubNameList.add("0001-ZZHK");//中再资产香港
        map.put("year", DateUtils.getCurrentYear());
        for (String str : traSubNameList) {
            map.put("traSubName", str);
            tradeAmountTotal = tradeAmountTotal.add(traFundsService.getLimitTotalTradeAmount(map));
        }
        return tradeAmountTotal;
    }

    /**
     * 计算全部关联方累积交易金额
     *
     * @return
     */
    public static BigDecimal getTradeAmountTotal(Map map) {
        BigDecimal tradeAmountTotal = new BigDecimal(0);
        List<String> traSubNameList = new ArrayList<String>();
        //traSubNameList.add("Z001");//紫金资产
//        traSubNameList.add("86");
        /*traSubNameList.add("0001");//中再资产
        traSubNameList.add("0001-ZZHK");//中再资产香港*/
        map.put("year", DateUtils.getCurrentYear());
        for (String str : traSubNameList) {
            if (map.containsKey("relId")) {
                map.remove("relId");
            }
            map.put("traSubName", str);
            tradeAmountTotal = tradeAmountTotal.add(traFundsService.getLimitTotalTradeAmount(map));
            for (String strSub : traSubNameList) {
                if (!str.equals(strSub)) {
                    map.put("relId", strSub);//剔除与子公司 或者子公司与子公司之间的交易
                    tradeAmountTotal = tradeAmountTotal.subtract(traFundsService.getLimitTotalTradeAmount(map));
                }
            }
        }
        return tradeAmountTotal;
    }

    /**
     * 根据上季末综合偿付能力充足率计算限额比例
     *
     * @param lastQuarterRatioBD 上季末综合偿付能力充足率
     * @return 限额比例
     */
    public static BigDecimal getRatio(BigDecimal lastQuarterRatioBD) {
        if (lastQuarterRatioBD.compareTo(BigDecimal.valueOf(100)) < 0) {
            return BigDecimal.valueOf(0.1);
        } else if (lastQuarterRatioBD.compareTo(BigDecimal.valueOf(150)) < 0) {
            return BigDecimal.valueOf(0.2);
        } else if (lastQuarterRatioBD.compareTo(BigDecimal.valueOf(200)) < 0) {
            return BigDecimal.valueOf(0.25);
        } else if (lastQuarterRatioBD.compareTo(BigDecimal.valueOf(250)) < 0) {
            return BigDecimal.valueOf(0.3);
        } else if (lastQuarterRatioBD.compareTo(BigDecimal.valueOf(300)) < 0) {
            return BigDecimal.valueOf(0.35);
        } else if (lastQuarterRatioBD.compareTo(BigDecimal.valueOf(350)) < 0) {
            return BigDecimal.valueOf(0.4);
        } else {
            return BigDecimal.valueOf(0.45);
        }
    }

    /**
     * 是否超限-与上季末比较
     *
     * @param tradeAmount    交易金额
     * @param endLastQuarter 上季末总资产
     * @param ratio          限额比例
     * @return
     */
    private static boolean isOverrunByLastQuarter(BigDecimal tradeAmount, BigDecimal endLastQuarter, BigDecimal ratio) {
        if (tradeAmount.compareTo(endLastQuarter.multiply(ratio).multiply(BigDecimal.valueOf(0.5))) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否超限预警-与上季末比较 限额的80%
     *
     * @param tradeAmount    交易金额
     * @param endLastQuarter 上季末总资产
     * @param ratio          限额比例
     * @return
     */
    private static boolean isWarningByLastQuarter(BigDecimal tradeAmount, BigDecimal endLastQuarter, BigDecimal ratio) {
        if (tradeAmount.compareTo(endLastQuarter.multiply(ratio).multiply(BigDecimal.valueOf(0.5)).multiply(BigDecimal.valueOf(0.8))) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 发送邮件通知
     *
     * @param traSubName
     * @param relName
     * @param tranType
     * @param contractNo
     * @param contractAmount
     * @throws Exception
     */
    public static void sendNotice(String traSubName, String relName, String tranType, String
            contractNo, String contractAmount) throws Exception {
        //向委托机构发送通知
        if (!traSubName.equals("0001") && !traSubName.equals("0001-ZZHK")) {
            List<Filter> filters = new Filter.Builder().eq("dictCode", "INS-subjectName").build();
            List<DictItem> dictItems = dictItemService.select(filters);
            Map traSubNames = new HashMap();
            for (DictItem dic : dictItems) {
                traSubNames.put(dic.getItemValue(), dic.getItemLabel());
            }
            List<Filter> filters_1 = new Filter.Builder().eq("dictCode", "INS-tranType").build();
            List<DictItem> dictItems_1 = dictItemService.select(filters_1);
            Map tranTypes = new HashMap();
            for (DictItem dic : dictItems_1) {
                tranTypes.put(dic.getItemValue(), dic.getItemLabel());
            }
            List<Filter> filters_2 = new Filter.Builder().eq("unitCode", traSubName).build();
            List<Staff> staffs = staffService.select(filters_2);
            if (staffs != null && staffs.size() > 0) {
                Staff staff = staffs.get(0);
                //构建发送报文
                StringBuffer subject = new StringBuffer();
                subject.append(traSubNames.get(traSubName)).append("与").append(relName).
                        append("发生了关联交易提醒");
                StringBuffer content = new StringBuffer();
                content.append("交易主体为:(").append(traSubNames.get(traSubName)).
                        append(")与交易对手:(").append(relName).
                        append(")发生了:(").append(tranTypes.get(tranType)).
                        append(")的交易,").append("交易的合同号为:(").append(contractNo).
                        append("),交易金额为:(").append(contractAmount).append("万元)");
                notifyService.createRemind(staff.getStaffCode(), subject.toString(), content.toString());
            }
        }
    }

    /**
     * 根据累计交易规则，对累计数据进行归零处理
     *
     * @param data
     */
    public static void grandTradeZero(String data) {
        try {
            if (data != null) {
                JSONObject object = JSONObject.parseObject(data);
                String majorTrade = object.getString("majorTrade");//是否重大： 0 是  1 否
                String majorType = object.getString("majorType");//majorType：1 单笔达到	2 累计达到
                if (StringUtils.isNotBlank(majorTrade) && StringUtils.isNotBlank(majorType) &&
                        majorTrade.equals("0") && majorType.equals("2")) {
                    String relId = object.getString("relId");
                    String relType = object.getString("relType");
                    String traSubName = object.getString("traSubName");
                    String tranType = object.getString("tranType");
                    if (StringUtils.isNotBlank(relId) && StringUtils.isNotBlank(relType) &&
                            StringUtils.isNotBlank(traSubName) && StringUtils.isNotBlank(tranType)) {
                        baseService.grandTradeZero(Integer.valueOf(relId), relType, traSubName, tranType);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否重大交易判定(新)
     *
     * @param tradeType      交易类型
     * @param relTradeAmount :关联交易金额
     * @param relInfoListStr 交易对手列表
     * @return
     */
    public static Map GreatCheckNew(String tradeType, BigDecimal relTradeAmount, String relInfoListStr) {
        HashMap<String, Object> result = Maps.newHashMap();
        String majorTrade = Dict.SF_0;
        String majorType = Dict.MAJOR_TYPE_F;
        StringBuffer notMajorRelNumber = new StringBuffer("");
        if (relTradeAmount == null) {
            LOGGER.info("错误信息：参数缺失{关联交易金额缺失}");
            result.put("code", "999999");
            result.put("msg", "错误信息：参数缺失{关联交易金额缺失}");
            return result;
        }

        /**解析json*/
        List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
        if (CollectionUtil.isEmpty(traRelInfos)) {
            LOGGER.info("错误信息：参数缺失{请检查交易对手信息参数}");
            result.put("code", "999999");
            result.put("msg", "错误信息：参数缺失{请检查交易对手信息参数}");
            return result;
        }
        try {
            /**
             * 1、先判断单笔重大
             * 2、判断是否是累计重大
             */

            //上年末净资产
            int year = DateUtil.year(new Date());
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("delflag", "1"));
            filterList.add(new Filter("years", String.valueOf(year - 1)));
            filterList.add(new Filter("quarter", "5"));
            // filterList.add(new Filter("approvalStatus", "6")); 基础数据不走审批了
            List<Order> orders = new ArrayList<>();
            orders.add(new Order("modifiedTime", OrderType.DESC));
            List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList, orders);
            String lastYearNetAsset = null;
            if (CollectionUtil.isNotEmpty(rootOrgInfos)) {
                lastYearNetAsset = String.valueOf(rootOrgInfos.get(0).getNetCaptial());
            }

            //重大关联交易基数
            String baseAmt = appConfigService.getConfigValue("major_base_amt");
            if (StringUtils.isBlank(baseAmt) || StringUtils.isBlank(lastYearNetAsset)) {
                LOGGER.info("错误信息:{是否重大关联交易基数,上年末净资产(万元)预置参数缺失}");
                result.put("code", "999999");
                result.put("msg", "错误信息:{是否重大关联交易基数,上年末净资产(万元)预置参数缺失}");
                return result;
            }


            boolean flag = false;
            //基数3000W
            BigDecimal baseAmtBD = new BigDecimal(baseAmt).setScale(6, BigDecimal.ROUND_HALF_UP);
            //单笔交易金额
            BigDecimal tradeAmountBD = relTradeAmount.setScale(6, BigDecimal.ROUND_HALF_UP);
            //上年度末净资产
            BigDecimal lastYearNetAssetBD = new BigDecimal(lastYearNetAsset).setScale(6, BigDecimal.ROUND_HALF_UP);

            //用于保存哪个关联方造成重大的数据类
            InsTrans insTrans = new InsTrans();
            //1、单笔重大判断 大于等于3000W 且占保险公司上一年度末经审计的净资产的1%以上
            if (tradeAmountBD.compareTo(baseAmtBD) >= 0) {
                flag = isGreatTrade(tradeAmountBD, lastYearNetAssetBD, BigDecimal.valueOf(1), baseAmtBD);
                if (flag) {
                    BeanUtils.copyProperties(traRelInfos.get(0), insTrans);//单笔重大，如果是多交易对手默认按照第一个数据来显示以及报送
                    insTrans.setMajorTrade(Dict.SF_1);
                    insTrans.setMajorType(Dict.MAJOR_TYPE_DB);
                    traRelInfos.get(0).setThisIsMajor(Dict.SF_1);
                }

            }

            if (flag) {//单笔重大
                LOGGER.info("当前交易为单笔重大关联交易...");
                result.put("insTrans", insTrans);
                result.put("traRelInfos", traRelInfos);
            } else {// 2、不是单笔重大，则需要判断是否为累计重大
                /**循环判断所有关联交易对手*/
                flag = false;
                for (TraRelInfo traRelInfo : traRelInfos) {
                    /**该关联方年度累计未清零关联交易金额*/
                    //为配合前端显示，总未清零累计关联交易金额 改为取值tradeAmount

                    BigDecimal totalNotCleanTradeAmount = traRelInfo.getTradeAmount();
                    if (totalNotCleanTradeAmount != null) {

                        //判断累计重大情况
                        BigDecimal percentNum = new BigDecimal(1);//档位
                        flag = isGreatTrade(tradeAmountBD.add(totalNotCleanTradeAmount), lastYearNetAssetBD, percentNum, baseAmtBD);
                        if (flag) {
                            BeanUtils.copyProperties(traRelInfo, insTrans);//多交易对手，默认按照最后一个产生重大的数据来显示以及报送
                            insTrans.setMajorTrade(Dict.SF_1);
                            insTrans.setMajorType(Dict.MAJOR_TYPE_LJ);
                            traRelInfo.setThisIsMajor(Dict.SF_1);
                            LOGGER.info("达到累积重大关联交易...证件号码为：" + traRelInfo.getRelNumber());
                        } else {
                            //非累计重大
                            if (notMajorRelNumber.equals("")) {
                                notMajorRelNumber.append(traRelInfo.getRelNumber());
                            } else {
                                notMajorRelNumber.append("," + traRelInfo.getRelNumber());
                            }
                        }
                    }
                }

                if (flag) {
                    LOGGER.info("当前交易为累计重大关联交易...");
                    result.put("insTrans", insTrans);
                    result.put("traRelInfos", traRelInfos);
                    result.put("notMajorRelNumber", notMajorRelNumber.toString());
                } else {
                    LOGGER.info("当前交易为一般关联交易...");
                    result.put("insTrans", insTrans);
                    result.put("traRelInfos", traRelInfos);
                }
            }

        } catch (ServiceException se) {
            se.printStackTrace();
        }
        return result;
    }

    /**
     * 是否重大交易判定(新) 返回列表
     *
     * @param tranType 交易类型
     * @param relTradeAmount 关联交易金额
     * @param traRelInfoList 交易对手列表
     * @return Map
     */
    public static Map GreatCheckNew2(String tranType, BigDecimal relTradeAmount, List<TraRelInfo> traRelInfoList) {
        Map resultMap = new HashMap();
        String majorTrade = Dict.SF_0;
        String majorType = Dict.MAJOR_TYPE_F;

        //是否重大关联交易基数
        String baseAmt = appConfigService.getConfigValue("major_base_amt");
        //上年末净资产
        int year = DateUtil.year(new Date());
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("delflag", "1"));
        filterList.add(new Filter("years", String.valueOf(year - 1)));
        filterList.add(new Filter("quarter", "5"));
        //filterList.add(new Filter("approvalStatus", "6"));
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("modifiedTime", OrderType.DESC));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList, orders);
        String lastYearNetAsset = null;
        if (CollectionUtil.isNotEmpty(rootOrgInfos)) {
            lastYearNetAsset = String.valueOf(rootOrgInfos.get(0).getNetCaptial());
        }
        if (StringUtils.isBlank(baseAmt) || StringUtils.isBlank(lastYearNetAsset)) {
            LOGGER.info("执行（资金运用）交易重大关联交易判定规则集异常...错误信息:{是否重大关联交易基数,上年末净资产(万元)预置参数缺失}");
        }
        boolean flag = false;
        //基数3000W
        BigDecimal baseAmtBD = new BigDecimal(baseAmt).setScale(6, BigDecimal.ROUND_HALF_UP);
        //单笔交易金额
        BigDecimal tradeAmountBD = relTradeAmount.setScale(6, BigDecimal.ROUND_HALF_UP);
        //上年度末总资产
        BigDecimal lastYearNetAssetBD = new BigDecimal(lastYearNetAsset).setScale(6, BigDecimal.ROUND_HALF_UP);

        //单笔重大判断 大于等于3000W 且占保险公司上一年度末经审计的净资产的1%以上
        if (tradeAmountBD.compareTo(baseAmtBD) >= 0) {
            flag = isGreatTrade(tradeAmountBD, lastYearNetAssetBD, BigDecimal.valueOf(1), baseAmtBD);
        }
        //累积重大判断
        if (flag) {
            for (TraRelInfo traRelInfo : traRelInfoList) {
                majorTrade = Dict.SF_1;
                majorType = Dict.MAJOR_TYPE_DB;
                traRelInfo.setMajorTrade(majorTrade);
                traRelInfo.setMajorType(majorType);
            }
            resultMap.put("majorTrade", majorTrade);
            resultMap.put("majorType", majorType);
        } else {
            //档位
            BigDecimal percentNum = new BigDecimal(1);
            /**循环判断所有关联交易对手*/
            for (TraRelInfo traRelInfo : traRelInfoList) {
                //统计年度未清零状态的关联交易金额,（排除单笔重大和统一交易协议的）
                int nowYear = DateUtil.year(new Date());
                Map<String, Object> param = new HashMap<>();
                /**年度*/
                param.put("year", nowYear);
                param.put("isAccumulated", Dict.SF_0);
                param.put("majorTrade", Dict.SF_0);
                param.put("isTradeAgreement", Dict.SF_0);

                param.put("relType",traRelInfo.getRelType());
                param.put("tranType",tranType);
                param.put("relNumber", traRelInfo.getRelNumber());
                BigDecimal tradeZeroAmount = majorTraCheckMapper.sumRelTradeAmount(param);

                if (tradeZeroAmount == null) {
                    tradeZeroAmount = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
                }

                //判断累计重大情况
                boolean flag1 = isGreatTrade(tradeAmountBD.add(tradeZeroAmount), lastYearNetAssetBD, percentNum, baseAmtBD);
                if (flag1) {
                    traRelInfo.setMajorType(Dict.MAJOR_TYPE_LJ);
                    traRelInfo.setMajorTrade(Dict.SF_1);
                    flag = true;
                }
            }
            if (flag) {
                majorTrade = Dict.SF_1;
                majorType = Dict.MAJOR_TYPE_LJ;
            }
            resultMap.put("majorTrade", majorTrade);
            resultMap.put("majorType", majorType);
        }
        return resultMap;
    }

    /**
     * 重大交易，审批结束后，查询遍历交易对手，把交易对手历史累计重大清零
     *
     * @param transNum 交易编号
     * @param type     交易类型
     */
    public static void grandTradeZero(String transNum, String type) {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", transNum));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        for (TraCounterparty tra : counterpartyList) {
            // 执行达到累计重大清零操作,对累计重大关联交易数据进行归零处理
            String majorTrade = tra.getMajorTrade();//是否重大： 1 是  0 否
            String majorType = tra.getMajorType(); //majorType：1 单笔达到	2 累计达到
            if (Dict.SF_1.equals(majorTrade) && Dict.MAJOR_TYPE_LJ.equals(majorType)) {
                // 执行达到累计重大清零操作,对累计重大关联交易数据进行归零处理,1交易对手是达到累计重大的
                Map map = Maps.newHashMap();
                //map.put("relId", tra.getRelId());
                map.put("relNumber", tra.getRelNumber());//关联方ID改为证件号码
                map.put("year", DateUtils.getCurrentYear());
                map.put("traSubName", tra.getTraSubName());
                map.put("relNumber",tra.getRelNumber());
                traCounterpartyService.grandTradeZeroNew(map);
            }/*else if(Dict.SF_1.equals(majorTrade) && Dict.MAJOR_TYPE_F.equals(majorType)) {
                //非达到累计重大的交易对手只清除本笔交易
                Map map = Maps.newHashMap();
                map.put("pid", tra.getPid());
                traCounterpartyService.grandTradeZeroNew(map);
            }*/
        }
    }

    /**
     * 只更改交易对手表状态
     *
     * @param transNum
     * @param status
     */
    public static void updateTraCounterpartyStatus(String transNum, String status) {
        //把所有交易对手状态都改为已归档
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", transNum));
        TraCounterparty traCounterparty = new TraCounterparty();
        traCounterparty.setStatus(status);
        traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
    }

    /**
     * 校验多交易对手在途交易数据
     *
     * @param relList
     * @param traSubName
     * @return
     */
    public static Map checkInRoadData(List<TraRelInfo> relList, String traSubName) {
        Map resultMap = new HashMap();
        try {
            String flag = "0";
            StringBuffer stringBuffer = new StringBuffer();
            for (TraRelInfo rel : relList) {
                BigDecimal totalTradeAmount = new BigDecimal(0);
                Map<String, Object> map = new HashMap();
                map.put("relId", rel.getRelId());
                map.put("year", DateUtils.getCurrentYear() + "%");
                map.put("traSubName", traSubName);
                List<TraCounterparty> traList = traCounterpartyService.queryInRoadTransList(map);
                for (TraCounterparty tra : traList) {
                    totalTradeAmount = totalTradeAmount.add(tra.getRelTradeAmount());
                }
                if(totalTradeAmount.compareTo(new BigDecimal("0"))>0){
                    stringBuffer.append("交易主体:(").append(traSubName).append("),");
                    stringBuffer.append("交易对手名称:(").append(rel.getRelName()).append("),");
                    stringBuffer.append("交易对手在途金额:(").append(totalTradeAmount).append("万元);");
                    flag = "1";
                }

            }
            resultMap.put("flag", flag);
            resultMap.put("msg", stringBuffer);
        } catch (Exception e) {
            LOGGER.error("查询交易对手在途数据出现异常，错误信息为：", e);
        }
        return resultMap;
    }

    /**
     * 通过条件查询关联方信息
     * @param tenantId
     * @param relType
     * @param relNumber
     * @param certType
     * @param relName
     * @return
     */
    public static Map findRelPsnOrOrgBy(String tenantId,String relType,String relNumber,String certType,String relName){
        Map map = new HashMap();
        List<Filter> filters = new ArrayList<>();
        if(Dict.REL_TYPE_PSN.equals(relType)){
            filters.add(new Filter("name",relName));
            filters.add(new Filter("identifyType",certType));
            filters.add(new Filter("identifyNumber",relNumber));
            filters.add(new Filter("tenantId",tenantId));
            filters.add(new Filter("delflag","1"));
            List<RelPsn> relPsnList = relPsnService.select(filters);
            if(relPsnList.size()>0){
                map.put("relPsn",relPsnList.get(0));
            }
        }
        if(Dict.REL_TYPE_ORG.equals(relType)){
            filters.add(new Filter("orgName",relName));
            filters.add(new Filter("identifyType",certType));
            filters.add(new Filter("uniscCode",relNumber));
            filters.add(new Filter("tenantId",tenantId));
            filters.add(new Filter("delflag","1"));
            List<RelOrg> relOrgList = relOrgService.select(filters);
            if(relOrgList.size()>0){
                map.put("relOrg",relOrgList.get(0));
            }
        }
        return map;
    }


    public static Map findRelPsnOrOrgBy1(String relType,String relNumber,String certType,String relName){
        Map map = new HashMap();
        List<Filter> filters = new ArrayList<>();
        if(Dict.REL_TYPE_PSN.equals(relType)){
            filters.add(new Filter("name",relName));
            filters.add(new Filter("identifyType",certType));
            filters.add(new Filter("identifyNumber",relNumber));
            filters.add(new Filter("delflag","1"));
            List<RelPsn> relPsnList = relPsnService.select(filters);
            if(relPsnList.size()>0){
                map.put("relPsn",relPsnList.get(0));
            }
        }
        if(Dict.REL_TYPE_ORG.equals(relType)){
            filters.add(new Filter("orgName",relName));
            filters.add(new Filter("identifyType",certType));
            filters.add(new Filter("uniscCode",relNumber));
            filters.add(new Filter("delflag","1"));
            List<RelOrg> relOrgList = relOrgService.select(filters);
            if(relOrgList.size()>0){
                map.put("relOrg",relOrgList.get(0));
            }
        }
        return map;
    }

    /**
     * 是否重大交易判定(新) 返回列表
     *
     * @param tranType 交易类型
     * @param relTradeAmount 关联交易金额
     * @param traRelInfoList 交易对手列表
     * @return Map
     */
    public static Map GreatCheckNew3(String tranType, BigDecimal relTradeAmount, TraRelInfo traRelInfoList) {
        Map resultMap = new HashMap();
        String majorTrade = Dict.SF_0;
        String majorType = Dict.MAJOR_TYPE_F;
        String majorTradeCritical = Dict.SF_0;
        BigDecimal majorNumber = null;
        //是否重大关联交易基数
        String baseAmt = appConfigService.getConfigValue("major_base_amt");
        //上年末净资产
        int year = DateUtil.year(new Date());
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("delflag", "1"));
        filterList.add(new Filter("years", String.valueOf(year - 1)));
        filterList.add(new Filter("quarter", "5"));
        //filterList.add(new Filter("approvalStatus", "6"));
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("modifiedTime", OrderType.DESC));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList, orders);
        String lastYearNetAsset = null;
        if (CollectionUtil.isNotEmpty(rootOrgInfos)) {
            lastYearNetAsset = String.valueOf(rootOrgInfos.get(0).getNetCaptial());
        }
        if (StringUtils.isBlank(baseAmt) || StringUtils.isBlank(lastYearNetAsset)) {
            LOGGER.info("执行（资金运用）交易重大关联交易判定规则集异常...错误信息:{是否重大关联交易基数,上年末净资产(万元)预置参数缺失}");
        }
        boolean flag = false;
        //基数3000W
        BigDecimal baseAmtBD = new BigDecimal(baseAmt).setScale(6, BigDecimal.ROUND_HALF_UP);
        //单笔交易金额
        BigDecimal tradeAmountBD = relTradeAmount.setScale(6, BigDecimal.ROUND_HALF_UP);
        //上年度末总资产
        BigDecimal lastYearNetAssetBD = new BigDecimal(lastYearNetAsset).setScale(6, BigDecimal.ROUND_HALF_UP);

        //单笔重大判断 大于等于3000W 且占保险公司上一年度末经审计的净资产的1%以上
        if (tradeAmountBD.compareTo(baseAmtBD) >= 0) {
            flag = isGreatTrade(tradeAmountBD, lastYearNetAssetBD, BigDecimal.valueOf(1), baseAmtBD);
        }
        //累积重大判断
        if (flag) {
                majorTrade = Dict.SF_1;
                majorType = Dict.MAJOR_TYPE_DB;
            traRelInfoList.setMajorTrade(majorTrade);
            traRelInfoList.setMajorType(majorType);
            resultMap.put("majorTrade", majorTrade);
            resultMap.put("majorType", majorType);
        } else {
            //档位
            BigDecimal percentNum = new BigDecimal(1);
                //统计年度未清零状态的关联交易金额,（排除单笔重大和统一交易协议的）
                int nowYear = DateUtil.year(new Date());
                Map<String, Object> param = new HashMap<>();
                /**年度*/
                param.put("year", nowYear);
                param.put("isAccumulated", Dict.SF_0);
                param.put("majorTrade", Dict.SF_0);
                param.put("isTradeAgreement", Dict.SF_0);

                param.put("relType",traRelInfoList.getRelType());
                param.put("tranType",tranType);
                param.put("relNumber", traRelInfoList.getRelNumber());
                BigDecimal tradeZeroAmount = majorTraCheckMapper.sumRelTradeAmount(param);

                if (tradeZeroAmount == null) {
                    tradeZeroAmount = new BigDecimal(0).setScale(6, BigDecimal.ROUND_HALF_UP);
                }

                //判断累计重大情况
                boolean flag1 = isGreatTrade(tradeAmountBD.add(tradeZeroAmount), lastYearNetAssetBD, percentNum, baseAmtBD);
                if (flag1) {
                    traRelInfoList.setMajorType(Dict.MAJOR_TYPE_LJ);
                    traRelInfoList.setMajorTrade(Dict.SF_1);
                    flag = true;
                }else{
                    //判断是否达到累计重大的80%
                    percentNum = new BigDecimal(0.8);
                    boolean flag2 = isGreatTrade(tradeAmountBD.add(tradeZeroAmount), lastYearNetAssetBD, percentNum, baseAmtBD);
                    if (flag2) {
                        traRelInfoList.setMajorTradeCritical(Dict.SF_1);
                        majorNumber = tradeAmountBD.add(tradeZeroAmount);
                        majorTradeCritical = Dict.SF_1;
                    }
                }

            if (flag) {
                majorTrade = Dict.SF_1;
                majorType = Dict.MAJOR_TYPE_LJ;
            }
            resultMap.put("majorTrade", majorTrade);
            resultMap.put("majorType", majorType);
            resultMap.put("majorTradeCritical",majorTradeCritical);
            resultMap.put("majorNumber",majorNumber);
        }
        return resultMap;
    }


    /**
     *判定关联交易是否为重大关联交易
     */
    public static Map<String, String> judgeMajor(Map<String, Object> ruleMap) {
        Map<String, String> tips = new HashMap<>();
        // 是否达到特别重大 1-是、0-否；默认否
        tips.put("specialMajorTrade", "0");
        // 是否需要补录关联方信息 1-是 0-否 默认否
        tips.put("isCollectRelated", "0");
        //关联方类型
        String relType = String.valueOf(ruleMap.get("relType"));
        //关联方证件号码
        String relNumber = String.valueOf(ruleMap.get("relNumber"));
        //是否免于审议
        String isConsiderSituation = String.valueOf(ruleMap.get("isConsiderSituation"));
        //是否统一交易协议
        String isAgreement = String.valueOf(ruleMap.get("isAgreement"));
        BigDecimal relTranAmount = new BigDecimal(String.valueOf(ruleMap.get("relTranAmount")));
        //1、判断是不是统一交易协议
        if ("1".equals(isAgreement)) {
            //是否重大
            tips.put("majorTrade", "0");
            //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
            tips.put("singleRelatedTrantype", "2");
            //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
            tips.put("majorType", "0");
            //审议类型(1:免予审议，2:一般审议，3逐笔审议)
            tips.put("considerType", "3");
            //是否需逐笔报告 1.是  0.否
            tips.put("eachReport", "1");
            //是否需逐笔披露  1.是  0.否
            tips.put("eachDisclosure", "1");
            // 是否需要补录关联方信息 1-是 0-否
            tips.put("isCollectRelated", "1");

            return tips;
        }
        //2、先判断是不是手动选择了免审
        if ("1".equals(isConsiderSituation)) {
            //是否重大
            tips.put("majorTrade", "0");
            //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
            tips.put("singleRelatedTrantype", "0");
            //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
            tips.put("majorType", "0");
            //审议类型(1:免予审议，2:一般审议，3逐笔审议)
            tips.put("considerType", "1");
            //是否需逐笔报告 1.是  0.否
            tips.put("eachReport", "0");
            //是否需逐笔披露  1.是  0.否
            tips.put("eachDisclosure", "0");
            return tips;
        } else {
            //先判断单笔
            tips = checkMajor(relTranAmount, relType, "1");
            if ("1".equals(tips.get("majorTrade"))) {
                //如果判断为重大直接返回
                return tips;
            }
            //判断累计是否为重大
            //通过关联方证件号码获取当前交易对手所有交易的清零后累计金额(不包含当前这笔数据)
            BigDecimal currYearTotalTradeAmount = getCurrYearTotalTradeAmount(relNumber);
            //当前交易对手所有交易的清零后累计金额(包含当前这笔数据)、获取当前关联方全部的关联交易金额
            currYearTotalTradeAmount = relTranAmount.add(currYearTotalTradeAmount);
            tips = checkMajor(currYearTotalTradeAmount, relType, "2");
            if ("1".equals(tips.get("majorTrade"))) {
                return tips;
            } else {
                // 是否需要补录关联方信息 1-是 0-否 默认否
                tips.put("isCollectRelated", "0");
                BigDecimal threshold = null;
                //判断关联交易审议类型的金额阈值
                if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(relType)) {
                    threshold = new BigDecimal(50);
                }
                if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(relType)) {
                    threshold = new BigDecimal(500);
                }
                if (relTranAmount.compareTo(threshold) < 0) {
                    //是否重大
                    tips.put("majorTrade", "0");
                    //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
                    tips.put("singleRelatedTrantype", "0");
                    //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
                    tips.put("majorType", "0");
                    //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                    tips.put("considerType", "1");
                    //是否需逐笔报告 1.是  0.否
                    tips.put("eachReport", "0");
                    //是否需逐笔披露  1.是  0.否
                    tips.put("eachDisclosure", "0");
                    return tips;
                } else if (relTranAmount.compareTo(threshold) >= 0) {
                    //是否重大
                    tips.put("majorTrade", "0");
                    //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
                    tips.put("singleRelatedTrantype", "0");
                    //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
                    tips.put("majorType", "0");
                    //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                    tips.put("considerType", "2");
                    //是否需逐笔报告 1.是  0.否
                    tips.put("eachReport", "0");
                    //是否需逐笔披露  1.是  0.否
                    tips.put("eachDisclosure", "0");
                    return tips;
                }
            }

        }
        return tips;
    }

    /**
     * 根据关联交易金额判断是否重大
     */
    private static Map<String, String> checkMajor(BigDecimal relTradeAmount, String relType, String type) {
        Map<String, String> tips = new HashMap<>();
        //获取上年度末资产信息
        RootOrgInfo rootOrgInfo = obtainLastYearAssetsInfo();
        BigDecimal netCapital = rootOrgInfo.getNetCaptial();
        BigDecimal totalCapital = rootOrgInfo.getTotalCapital();
        // 是否达到特别重大 1-是、0-否；默认否
        tips.put("specialMajorTrade", "0");
        // 是否需要补录关联方信息 1-是 0-否
        tips.put("isCollectRelated", "0");
        if (relTradeAmount.compareTo(new BigDecimal(3000)) >= 0 && relTradeAmount.compareTo(netCapital.divide(new BigDecimal(100))) >= 0) {

            //判断是否达到累计重大的80%
            // 关联交易金额基数
            String baseAmt = appConfigService.getConfigValue("major_base_amt");
            BigDecimal percentNum = BigDecimal.valueOf(0.8);
            boolean flag2 = isGreatTrade(relTradeAmount, totalCapital, percentNum, new BigDecimal(baseAmt));
            if (flag2) {
                tips.put("majorNumber", relTradeAmount.toString());
                tips.put("majorTradeCritical", Dict.SF_1);
            }
            //单笔重大
            if ("1".equals(type)) {
                if (relTradeAmount.compareTo(totalCapital.divide(new BigDecimal(100)).multiply(new BigDecimal(5))) >= 0) {
                    tips.put("specialMajorTrade", "1");
                }
                //是否重大
                tips.put("majorTrade", "1");
                //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
                tips.put("singleRelatedTrantype", "1");
                //达到重大关联交易的情形 1.单笔达到   2.累计达到
                tips.put("majorType", "1");
                //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                tips.put("considerType", "3");
                //是否需逐笔报告 1.是  0.否
                tips.put("eachReport", "1");
                //是否需逐笔披露  1.是  0.否
                tips.put("eachDisclosure", "1");
                // 是否需要补录关联方信息 1-是 0-否
                if ("ORG".equals(relType)) {
                    tips.put("isCollectRelated", "1");
                }
                return tips;
                //累计重大
            } else if ("2".equals(type)) {
                if (relTradeAmount.compareTo(totalCapital.divide(new BigDecimal(100)).multiply(new BigDecimal(10))) >= 0) {
                    tips.put("specialMajorTrade", "1");
                }
                //是否重大
                tips.put("majorTrade", "1");
                //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
                tips.put("singleRelatedTrantype", "1");
                //达到重大关联交易的情形 1.单笔达到   2.累计达到
                tips.put("majorType", "2");
                //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                tips.put("considerType", "3");
                //是否需逐笔报告 1.是  0.否
                tips.put("eachReport", "1");
                //是否需逐笔披露  1.是  0.否
                tips.put("eachDisclosure", "1");
                // 是否需要补录关联方信息 1-是 0-否
                if ("ORG".equals(relType)) {
                    tips.put("isCollectRelated", "1");
                }
                return tips;
            }
        }
        return tips;
    }

    /**
     * 获取上年度末净资产
     */
    private static RootOrgInfo obtainLastYearAssetsInfo() {

        RootOrgInfo rootOrgInfo = new RootOrgInfo();

        //上年末净资产
        int year = DateUtil.year(new Date());
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("delflag", "1"));
        filterList.add(new Filter("years", String.valueOf(year - 1)));
        filterList.add(new Filter("quarter", "5"));
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("modifiedTime", OrderType.DESC));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList, orders);
        if (CollUtil.isNotEmpty(rootOrgInfos)) {
            rootOrgInfo = rootOrgInfos.get(0);
        }
        if (null == rootOrgInfo) {
            String msg = "执行重大关联交易判定规则集异常...错误信息:{是否重大关联交易基数," + (year - 1) + "基础数据预置参数缺失}";
            throw new NullPointerException(msg);
        }

        return rootOrgInfo;
    }

    /**
     * 根据关联方证件号码获取关联方累计金额
     */
    private static BigDecimal getCurrYearTotalTradeAmount(String relNumber) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        Map<String, Object> param = Maps.newHashMap();
        param.put("year", year);
        param.put("relNumber", relNumber);
        return queryAgreementAmountSum(param);
    }

    /**
     * 查询关联方累计金额
     */
    private static BigDecimal queryAgreementAmountSum(Map<String, Object> param) {
        return baseServiceMapper.queryAgreementAmountSum(param);
    }


    /**
     *判定关联交易是否为重大关联交易
     */
    public static Map<String, String> judgeMajor1(Map<String, Object> ruleMap) {
        Map<String, String> tips = new HashMap<>();
        // 是否达到特别重大 1-是、0-否；默认否
        tips.put("specialMajorTrade", "0");
        // 是否需要补录关联方信息 1-是 0-否 默认否
        tips.put("isCollectRelated", "0");
        //关联方类型
        String relType = String.valueOf(ruleMap.get("relType"));
        //关联方证件号码
        String relNumber = String.valueOf(ruleMap.get("relNumber"));
        //是否免于审议
        String isConsiderSituation = String.valueOf(ruleMap.get("isConsiderSituation"));
        //是否统一交易协议
        String isAgreement = String.valueOf(ruleMap.get("isAgreement"));
        //是否统一交易协议
        String tradeAgreementType = String.valueOf(ruleMap.get("tradeAgreementType"));
        BigDecimal relTranAmount = new BigDecimal(String.valueOf(ruleMap.get("relTranAmount")));

        //2、先判断是不是手动选择了免审
        if ("1".equals(isConsiderSituation)) {
            //是否重大
            tips.put("majorTrade", "0");
            //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
            tips.put("singleRelatedTrantype", "0");
            //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
            tips.put("majorType", "0");
            //审议类型(1:免予审议，2:一般审议，3逐笔审议)
            tips.put("considerType", "1");
            //是否需逐笔报告 1.是  0.否
            tips.put("eachReport", "0");
            //是否需逐笔披露  1.是  0.否
            tips.put("eachDisclosure", "0");
            return tips;
        }
        //1、判断是不是统一交易协议
        if ("1".equals(isAgreement)) {
            //是否重大
            tips.put("majorTrade", "0");
            //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
            tips.put("singleRelatedTrantype", "2");
            //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
            tips.put("majorType", "0");
            //审议类型(1:免予审议，2:一般审议，3逐笔审议)
            tips.put("considerType", "3");
            //是否需逐笔报告 1.是  0.否
            tips.put("eachReport", "1");
            //是否需逐笔披露  1.是  0.否
            tips.put("eachDisclosure", "1");
            // 是否需要补录关联方信息 1-是 0-否
            tips.put("isCollectRelated", "1");
            /**统一交易协议是，且类型为 统一交易协议下发生的交易 时设为免审 */
            if("4".equals(tradeAgreementType)){
                //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                tips.put("considerType", "1");
            }
            return tips;
        }

            //先判断单笔
            tips = checkMajor(relTranAmount, relType, "1");
            if ("1".equals(tips.get("majorTrade"))) {
                //如果判断为重大直接返回
                return tips;
            }
            //判断累计是否为重大
            //通过关联方证件号码获取当前交易对手所有交易的清零后累计金额(不包含当前这笔数据)
            BigDecimal currYearTotalTradeAmount = getCurrYearTotalTradeAmount(relNumber);
            //当前交易对手所有交易的清零后累计金额(包含当前这笔数据)、获取当前关联方全部的关联交易金额
            currYearTotalTradeAmount = relTranAmount.add(currYearTotalTradeAmount);
            tips = checkMajor(currYearTotalTradeAmount, relType, "2");
            if ("1".equals(tips.get("majorTrade"))) {
                return tips;
            } else {
                // 是否需要补录关联方信息 1-是 0-否 默认否
                tips.put("isCollectRelated", "0");
                BigDecimal threshold = null;
                //判断关联交易审议类型的金额阈值
                if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(relType)) {
                    threshold = new BigDecimal(50);
                }
                if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(relType)) {
                    threshold = new BigDecimal(500);
                }
                if (relTranAmount.compareTo(threshold) < 0) {
                    //是否重大
                    tips.put("majorTrade", "0");
                    //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
                    tips.put("singleRelatedTrantype", "0");
                    //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
                    tips.put("majorType", "0");
                    //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                    tips.put("considerType", "1");
                    //是否需逐笔报告 1.是  0.否
                    tips.put("eachReport", "0");
                    //是否需逐笔披露  1.是  0.否
                    tips.put("eachDisclosure", "0");
                    return tips;
                } else if (relTranAmount.compareTo(threshold) >= 0) {
                    //是否重大
                    tips.put("majorTrade", "0");
                    //单笔关联交易类型 1.重大关联交易 2.一般关联交易 0.一般关联交易
                    tips.put("singleRelatedTrantype", "0");
                    //达到重大关联交易的情形 1.单笔达到   2.累计达到 0 默认
                    tips.put("majorType", "0");
                    //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                    tips.put("considerType", "2");
                    //是否需逐笔报告 1.是  0.否
                    tips.put("eachReport", "0");
                    //是否需逐笔披露  1.是  0.否
                    tips.put("eachDisclosure", "0");
                    return tips;
                }
            }


        return tips;
    }
}


