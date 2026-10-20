package com.yzj.jep.app.ins.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.model.TraService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.service.TraBalanceDeductionService;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.bus.service.TraEnclosureService;
import com.yzj.jep.app.bus.service.impl.RuleInitDataServiceImpl;
import com.yzj.jep.app.enums.InsRuleCode;
import com.yzj.jep.app.ins.service.RuleCheckService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.rules.drools5.service.PackagesService;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.external.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tw
 * @date 2020/6/30 15:03
 * @remark
 */
@Service
public class RuleCheckServiceImpl implements RuleCheckService {

    private static final Logger logger = LoggerFactory.getLogger(RuleCheckServiceImpl.class);

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private RuleInitDataServiceImpl ruleInitDataServiceImpl;

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    private TraCreditService traCreditService;

    @Autowired
    private TraBalanceDeductionService traBalanceDeductionService;

    @Autowired
    private TraEnclosureService traEnclosureService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    AppConfigService appConfigService;

    @Autowired
    RootOrgInfoService rootOrgInfoService;

    /**
     * 判定交易是否合规
     * 1、 保险公司对全部关联方的未上市权益类投资余额不得超过上季末总资产 15%；
     * 2、 保险公司对全部关联方的不动产类投资余额不得超过上季末总资产 15%；
     * 3、 保险公司对全部关联方的其他金融资产投资余额不得超过上季末总资产 12.5%；
     * 4、 保险公司对全部关联方的境外投资余额不得超过上季末总资产 7.5%
     * 大家保险集团关联交易信息系统需求说明书
     * 5、 保险公司对单一关联方的全部投资余额，合计不得超过保险公司上一年度末总资产的
     * 15%；
     * 6、 保险公司投资金融产品，若底层基础资产涉及控股股东或控股股东的关联方，保险公司购
     * 买该金融产品的份额不得超过该产品发行总额的 60%；
     * 7、 持有集团 5%以上股权的股东质押股权数量不得超过其持有保险公司股权总量的 50%；
     * 8、 保险公司与其控股子公司，以及控股子公司之间发生的关联交易，不适用 1-6 项的规定；
     * 9、 保险公司与关联方及其企业集团的其他成员之间的资金运用类关联交易，应合并计算。
     * （一）保险公司对全部关联方的投资余额，合计不得超过保险公司上一年度末总资产的30%
     * 与上一年度末净资产二者中金额较低者；
     * （二）保险公司投资未上市权益类资产、不动产类资产、其他金融资产和境外投资的账面余额中，
     * 对关联方的投资金额不得超过上述各类资产投资限额的50%；
     *
     * @param tradeType  交易类型
     *                   TraBusiness - 关联交易表（保险）-保险业务类
     *                   TraFunds  - 关联交易表（保险）-资金运用类
     *                   TraInsenclosure  - 保险关联交易表-附件表
     *                   TraInterest - 关联交易表（保险）-利益转移类
     *                   TraInvestment - 关联交易表（保险）-投资入股类
     *                   TraService - 关联交易表（保险）-提供服务类
     * @param ruleParams 数据
     * @return 合规校验结果
     */

    @Override
    public List<Map<String, String>> ComPlianceCheck(String tradeType, Map ruleParams) {
        logger.info("执行（保险）交易合规校验开始... 交易类型[{}]", tradeType);

        if (StringUtils.isNotBlank(tradeType) && ruleParams != null) {
            return executeRule(ruleParams, tradeType);
        }
        logger.info("执行（保险）交易合规校验结束...");
        return null;
    }

    /**
     * 执行规则
     *
     * @param tradeType
     * @param ruleParams 数据
     * @return
     */
    public List<Map<String, String>> executeRule(Map ruleParams, String tradeType) {
        List<Map<String, String>> results = new ArrayList<>();
//        TraFunds traFunds = null;
////        if(data instanceof TraFunds){
////            traFunds = (TraFunds)data;
////        }else if(data instanceof TraBusiness){
////
////        }else if (data instanceof TraInterest){
////
////        }else if (data instanceof TraService){
////
////        }else if (data instanceof TraInvestment){
////
////        }

        //初始化数据
//        Map ruleParams =  InsUtil.initRuleData(reId,reType,tradeAmount);

        AppConfig insRule = appConfigService.get("INS_RULE_01"); //配置的保险类合规校验规则编码
        String ruleId = insRule.getConfigValue(); //规则集ID
        //配置业务类型和规则集ID的映射 通过调用规则集执行结果去获取对应合规校验规则集的id
        if (StringUtils.isNotBlank(ruleId)) {
            Map tradeTypeParam = new HashMap();
            tradeTypeParam.put("tradeType", tradeType);
            String ruleID_ = "";
            Map resMap = null;
            try {
                resMap = packagesService.execute(ruleId, JSONObject.toJSONString(tradeTypeParam));
            } catch (Exception e) {
                logger.info("执行规则异常... 异常信息：[{}]", e);
                Map<String, String> result = Maps.newHashMap();
                result.put("code", "999999");
                result.put("msg", "校验规则集配置异常，请检查规则配置后重试。");
                results.add(result);
                return results;
            }
            List<Map> resultList = (List) resMap.get("resultList");//规则集执行结果
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (Map<String, String> ruleResMap : resultList) {
                    if (StringUtils.isNotBlank(ruleResMap.get("RULE_ID"))) {
                        ruleID_ = ruleResMap.get("RULE_ID"); //拿到规则集执行结果id 然后执行对应的合规校验
                        break;
                    }
                }
            }
            //执行合规校验规则集
            Map resMap01 = null;
            if (StringUtils.isNotBlank(ruleID_)) { //如果合规校验规则集ID不为空 则执行规则集 合规校验
                try {
                    //执行规则
                    resMap01 = packagesService.execute(ruleID_, JSONObject.toJSONString(ruleParams));
                } catch (Exception e) {
                    logger.info("执行（保险）交易合规校验规则集异常... 错误信息：[{}]", e);
                    Map<String, String> result = Maps.newHashMap();
                    result.put("code", "999999");
                    result.put("msg", "执行合规校验规则异常。");
                    results.add(result);
                    return results;
                }
                List<Map> resultList1 = (List) resMap01.get("resultList");//规则集执行结果
                String returnCode = "";
                if (CollectionUtils.isNotEmpty(resultList1)) {
                    for (Map<String, String> ruleResMap : resultList1) {
                        if (StringUtils.isNotBlank(ruleResMap.get("returnCode"))) {
                            returnCode = ruleResMap.get("returnCode"); //拿到规则集执行结果id 然后执行对应的合规校验
                            Map<String, String> result = Maps.newHashMap();
                            result.put("code", returnCode);
                            result.put("msg", InsRuleCode.valueOf(returnCode).getDesc());
                            boolean isDu = false;
                            for (int i = 0; i < results.size(); i++) {
                                Map res = results.get(i);
                                if (returnCode.equals(res.get("code"))) { //如果已有code  则继续遍历结果集
                                    isDu = true;
                                    break;
                                }
                            }
                            if (!isDu) {
                                results.add(result);
                            }
                        }
                    }
                }

            } else {
                logger.info("合规校验规则集配置异常...");
                Map<String, String> result = Maps.newHashMap();
                result.put("code", "999999");
                result.put("msg", "合规校验规则集配置异常，请检查规则配置后重试。");
                results.add(result);
                return results;
            }
        } else {
            logger.info("规则集配置异常...找不到路由规则集");
            Map<String, String> result = Maps.newHashMap();
            result.put("code", "999999");
            result.put("msg", "校验规则集配置异常，请检查规则配置后重试。");
            results.add(result);
            return results;
        }


//        //调起通知
//        StringBuffer sb_code =   new StringBuffer();
//
//        //遍历规则集执行结果
//        if(results.size()>0) {
//            for (int i = 0; i < results.size(); i++) {
//                if(StringUtils.isNotBlank(results.get(i).get("code"))){
//                    if(i<results.size()-1) {
//                        sb_code.append("[" + results.get(i).get("code") + "]，");
//                    }else{
//                        sb_code.append("[" + results.get(i).get("code") + "] ");
//                    }
//                }
//            }
//        }
//        //错误码不为空调起通知
//        if(StringUtils.isNotBlank(sb_code.toString())){
//            try {
////                Map<String, String> map = JSONObject.parseObject(data, HashMap.class);
////                String name = map.get("relName");
////                String id = map.get("relId");
////                String tradeamt = map.get("tradeAmount");
//                notifyService.createRemind(UserProfileUtils.getUserProfile().getUnitCode(), "警告" + "[合规检查未通过] 关联方名称[" + relName + "] 交易金额[" + tradeAmount + "]" + "触发合规校验规则码："+sb_code.toString()+"，请到[关联方交易管理]菜单查看", "警告" + "[合规检查未通过] 关联方名称[" + relName + "] 交易金额[" + tradeAmount + "]" + "触发合规校验规则码："+sb_code.toString()+"，请到[关联方交易管理]菜单查看");
//            } catch (Exception e) {
//                logger.info("调起告警通知失败。。。 失败原因：[{}]", e);
//            }
//        }
        //错误码不为空 且非处理异常 则发送到公告栏
//        if (StringUtils.isNotBlank(result.get("code")) && !"999999".equals(result.get("code"))) {
//            try {
////                Map<String, String> map = JSONObject.parseObject(data, HashMap.class);
////                String name = map.get("relName");
////                String id = map.get("relId");
////                String tradeamt = map.get("tradeAmount");
//                notifyService.createRemind(UserProfileUtils.getUserProfile().getUnitCode(), "警告" + "[合规检查未通过] 关联方名称[" + relName + "] 交易金额[" + tradeAmount + "]" + result.get("msg"), "警告" + "[合规检查未通过] 关联方名称[" + relName + "] 交易金额[" + tradeAmount + "]" + result.get("msg"));
//            } catch (Exception e) {
//                logger.info("调起告警通知失败。。。 失败原因：[{}]", e);
//            }
//        }


        return results;
    }


    /**
     * 判定该交易是否是重大关联交易
     * <p>
     * 关联交易发起部门录入关联交易相关信息后，系统根据模型对比该笔交易金额和重大关联交易
     * 金额限制，自动识别出为一般关联交易还是重大关联交易。随后流转至秘书处法律合规专岗，由专
     * 岗人员对关联交易情况做基本了解后，引导发起部门继续后续审批流程。
     * 集团的关联交易分为重大关联交易和一般关联交易。
     * 重大关联交易是指集团或其控股子公司与一个关联方之间单笔或年度累计交易金额达到 3000
     * 万元以上，且占集团上一年度末经审计的净资产的 1%以上的交易；保险业务类和提供货物、服务
     * 或财务资助，可签署统一交易协议，统一交易协议应按照重大关联交易进行内部管控。
     * 一个年度内集团或其控股子公司与一个关联方的累计交易金额达到前款标准后，其后发生的关
     * 联交易，如再次累计达到前款标准，则应当重新认定为重大关联交易。
     * 集团与多个关联方在同一笔交易中的金额，应合并计算进行认定。
     * 一般关联交易是指除重大关联交易以外的其他关联交易。
     *
     * @param tradeType 交易类型
     * @param ruleParam 表单数据
     * @return 重大关联交易检测结果  0 - 一般关联交易  1 - 重大关联交易
     */
    @Override
    public Map GreatCheck(String ruleParam, String tradeType) {

        HashMap<String, String> result = Maps.newHashMap();


        if (StringUtils.isNotBlank(tradeType)) {

            if ("03".equals(tradeType) || "05".equals(tradeType)) {
                logger.info("提供服务类和保险类业务交易为重大关联交易...");
                result.put("code", "0");
                result.put("msg", "重大关联交易");
                return result;
            }

            AppConfig insRule = appConfigService.get("INS_RULE_02"); //重大关联交易的规则配置
            if (insRule == null) {
                logger.info("执行（保险）交易重大关联交易判定规则集异常... 错误信息：未找到配置信息");
                result.put("code", "999999");
                result.put("msg", "执行重大关联交易判定规则异常。");
                return result;
            }

            String pkgId = insRule.getConfigValue();
            Map<String, Object> resMap = null;

            try {
                resMap = packagesService.execute(pkgId, ruleParam);
            } catch (Exception e) {
                logger.info("执行（保险）交易重大关联交易判定规则集异常... 错误信息：[{}]", e);
                result.put("code", "999999");
                result.put("msg", "执行合重大关联交易判定规则异常。");
                return result;
            }

            List<Map> resultList = (List) resMap.get("resultList");//规则集执行结果
            String returnCode = "";
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (Map<String, String> ruleResMap : resultList) {
                    if (StringUtils.isNotBlank(ruleResMap.get("returnCode"))) {
                        returnCode = ruleResMap.get("returnCode"); //拿到规则集执行结果id 然后执行对应的合规校验
                        break;
                    }
                }
            }
            if (StringUtils.isNotBlank(returnCode) && "C908".equals(returnCode)) {
                result.put("code", "0");
                result.put("msg", "重大关联交易");
            } else {
                result.put("code", "1");
                result.put("msg", "一般关联交易");
            }
        }
        return result;
    }

    @Override
    public String warnLevelCheck(Map params) {
        AppConfig insRule = appConfigService.get("INS_RULE_03");
        String ruleId = insRule.getConfigValue();
        Map<String, Object> resultMap = packagesService.execute(ruleId, JSONObject.toJSONString(params));
        if (resultMap != null) {
            Map<String, String> map = (Map) resultMap.get("resultMap");
            String returnCode = map.get("returnCode");
            if (returnCode != null) {
                return returnCode;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> quotaCheck(Map ruleParams) {
        Map<String, String> result = Maps.newHashMap();
        //预计额度规则
        AppConfig quotaRule = appConfigService.get("INS_RULE_00");
        String rule = quotaRule.getConfigValue();
        Map<String, Object> quotaResult = packagesService.execute(rule, JSONObject.toJSONString(ruleParams));
        List<Map> resultList = (List) quotaResult.get("resultList");
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map<String, String> resultMap : resultList) {
                String returnCode = resultMap.get("returnCode");
                result.put("code", returnCode);
                result.put("msg", InsRuleCode.valueOf(returnCode).getDesc());
            }
            return result;
        }
        return null;
    }
}
