package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.TraBalanceDeduction;
import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.bus.service.RuleLibRestCallback;
import com.yzj.jep.app.bus.service.TraBalanceDeductionService;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.bus.service.TraEnclosureService;
import com.yzj.jep.app.bus.service.impl.RuleInitDataServiceImpl;
import com.yzj.jep.app.enums.RuleCode;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.rules.drools5.service.PackagesService;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.model.Notify;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.external.service.SysUserService;
import com.yzj.jep.workflow.api.model.IProcInst;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JudgeImportantTradeHandler implements IFPaaS1Handler<Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JudgeImportantTradeHandler.class);

    private PackagesService packagesService;

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

    @Override
    public Message handle(Message message) {
        LOGGER.info("调用规则开始...");
        Map<String, String> result = Maps.newHashMap();
        try {
            packagesService = SpringHolder.getBean(PackagesService.class);
            Map map = (Map) message.getTarget().getBodys();
            JSONObject json = new JSONObject(map);
            LOGGER.info("参数:[{}]", json);

            //规则ID
            String pkgId = "4d3f5f20-a9f6-11ea-960e-8cec4b4b2170";
            //初始化数据
            pkgId = (String) map.get("PKGID");
            ruleInitDataServiceImpl = SpringContextHolder.getBean("RuleInitDataServiceImpl");
            Map<String, Object> stringObjectMap = ruleInitDataServiceImpl.initData(map);
            if (stringObjectMap == null) {
                result.put("code", "00");
                result.put("msg", "不是关联交易，交易放行");
                message.getTarget().setBody(result);
                return message;
            }
            String isCredit = (String) map.get("isCredit");
            if (StringUtils.isNotBlank(isCredit)) {
                if ("1".equals(isCredit)) { //是授信
                    pkgId = (String) map.get("PKGID"); //todo 规则集id需要改为可配置
                } else {
                    pkgId = (String) map.get("PKGID2");//todo 规则集id需要改为可配置
                }
            } else {
                result.put("code", "999");
                result.put("msg", "是否授信标识为空，停止交易");
                message.getTarget().setBody(result);
            }
            Map resMap = packagesService.execute(pkgId, JSONObject.toJSONString(stringObjectMap));
            List<Map> resultList = (List) resMap.get("resultList");
            String retCode = "";
            String retMsg = "";
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (Map<String, String> ruleRetMap : resultList) {

                    if (StringUtils.isNotBlank(ruleRetMap.get("returnCode"))) {
                        retCode = ruleRetMap.get("returnCode");
                        retMsg = RuleCode.valueOf(retCode).getDesc();
                        break;
                    }


//                    //调用规则实现类
//                    String ruleClass = (String) ruleRetMap.get("retClass");
//                    String retCode1 = ruleRetMap.get("retCode");
//                    if (ruleClass != null) {
//                        RuleLibRestCallback rCallback = (RuleLibRestCallback) SpringContextHolder.getBean(ruleClass);
//                        result = rCallback.checkRule(map);
//                        retCode = result.get("code");
//                        retMsg = result.get("msg");
//                        break;
//                    }

                }
                if (StringUtils.isNotBlank(retCode)) {
                    result.put("code", retCode);
                    result.put("msg", retMsg);
                    message.getTarget().setBody(result);
                    return message;
                }
            }
            //通知预警
            //判断是否重大关联交易 调起审批工作流
            ruleInitDataServiceImpl = SpringContextHolder.getBean("RuleInitDataServiceImpl");
            String pkgId2 = (String) map.get("PKGID2"); //todo 规则集id需要改为可配置
            if (StringUtils.isNotBlank(pkgId2)) {
//                Map<String, Object> stringObjectMap2 = ruleInitDataServiceImpl.initData(map);
                Map resMap2 = packagesService.execute(pkgId2, JSONObject.toJSONString(stringObjectMap));
                List<Map> resultList2 = (List) resMap2.get("resultList");
                if (CollectionUtils.isNotEmpty(resultList2)) {
                    for (Map<String, String> ruleRetMap : resultList2) {
                        if (StringUtils.isNotBlank(ruleRetMap.get("returnCode"))) {
                            retCode = (String) ruleRetMap.get("returnCode");
                            retMsg = RuleCode.valueOf(retCode).getDesc();
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(retCode)) {
                    if (retCode.equals(RuleCode.CODE_08.name())) {
                        sysUserService = SpringContextHolder.getBean(SysUserService.class);
                        appConfigService = SpringContextHolder.getBean(AppConfigService.class);
                        //获取配置参数
                        AppConfig appConfig = appConfigService.get("FLOW_RULE_");
                        //是重大关联交易 ...  调起工作流
                        Staff staff = sysUserService.getStaff(appConfig.getConfigValue());
                        StaffImpl staffimpl = new StaffImpl();
                        BeanUtils.copyProperties(staff, staffimpl);
//                        staff.setStaffCode("jepAdmin");
//                        staff.setDeptName("总行");
//                        staff.setStaffName("超级管理员");
//                        staff.setUnitCode("02");
//                        staff.setUnitName("XXX银行");
//                        staff.setDeptCode("0201");
//                        staff.setUnitHierarchy("30");
//                        staff.setPositions(new ArrayList<>());
                        workflowEngine = SpringContextHolder.getBean(WorkflowEngine.class);
                        String procDefCode = "trancredit";
                        try {
                            LOGGER.info("保存信息");
                            save(json.toJSONString(), new TraEnclosureVo());
                            LOGGER.info("保存成功");
                        } catch (Exception e) {
                            LOGGER.debug(e.getMessage());
                            result.put("busCode", "999999999");
                            message.getTarget().setBody(result);
                            return message;
                        }
                        IProcInst procInst = workflowEngine.getRuntimeService().startProcess(procDefCode, staffimpl, null, json.toJSONString());
                        result.put("code", retCode);
                        result.put("msg", retMsg + "，已调起重大关联交易审批流程...");
                    } else {
                        result.put("code", retCode);
                        result.put("msg", retMsg);
                    }
                    message.getTarget().setBody(result);
                    return message;
                }
            }

        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            result.put("busCode", "999999999");
        }
        message.getTarget().setBody(result);
        return message;
    }


    //重大关联交易调起前需要执行save将流程信息保存
    public Map<String, Object> save(String traCreditS, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraCredit traCredit = JSONObject.parseObject(traCreditS, TraCredit.class);
        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        try {
            String transNum = null;
            Integer relId = traCredit.getRelId();
            String relType = traCredit.getRelType();
            BigDecimal creditAmount = BigDecimal.valueOf(traCredit.getCreditAmount());
            if (traCredit.getPid() != null) {
                TraCredit traCredit1 = traCreditService.get(traCredit.getPid());
                if ("1".equals(traCredit1.getIsEffective())) {
                    creditAmount = creditAmount.subtract(BigDecimal.valueOf(traCredit1.getCreditAmount()));
                } else {
                    creditAmount = getBalanceDeduction(traCredit);
                }
            } else {
                creditAmount = getBalanceDeduction(traCredit);
            }
            Map<String, Object> stringObjectMap = TraUtil.JudgeAllCreditAmount(creditAmount);
            if (!(boolean) stringObjectMap.get("success")) {
                return stringObjectMap;
            }
            if ("PSN".equals(relType)) {
                Map<String, Object> stringObjectMap1 = TraUtil.JudgePsnCreditBalance(relId, creditAmount);
                if (!(boolean) stringObjectMap1.get("success")) {
                    return stringObjectMap1;
                }
            }
            if ("ORG".equals(relType)) {
                Map<String, Object> stringObjectMap1 = TraUtil.JudgeOrgCreditBalance(relId, creditAmount);
                if (!(boolean) stringObjectMap1.get("success")) {
                    return stringObjectMap1;
                }
            }
            if (traCredit.getPid() != null) {
                traCredit.setStatus("3");
                traCreditService.updateSelective(traCredit);
                transNum = traCreditService.get(traCredit.getPid()).getTransNum();
            } else {
//                UserProfile userProfile = UserProfileUtils.getUserProfile();
//                String unitCode = userProfile.getUnitCode();
                traCredit.setManageOrg("02");
                traCredit.setDelflag("1");
                traCredit.setStatus("3");
                traCredit.setDataResource("1");
                traCredit.setTranType("1");
                traCredit.setIsEffective("0");
                traCredit.setTransNum(BatchNumberUtil.makeBatchNumber());
                traCreditService.insert(traCredit);
                transNum = traCredit.getTransNum();
                String msg = "";
                dutyService = SpringContextHolder.getBean(DutyService.class);
                List<Duty> dutys = dutyService.select(new Filter.Builder()
                        .like("positionCode", "130@" + "02")
                        .like("dutySource", DutySource.ROLE.toString())
                        .build());
                if ("1".equals(traCredit.getImportantTrade())) {
                    msg = "重大关联交易";
                } else {
                    msg = "一般关联交易";
                }
                if (com.yzj.jep.commons.CollectionUtils.isNotEmpty(dutys)) {
                    for (Duty duty : dutys) {
                        notifyService = SpringContextHolder.getBean(NotifyService.class);
                        notifyService.createRemind(duty.getStaffCode(), "发生" + msg + ",请处理", "发生" + msg + ",请处理");
                    }
                }
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traEnclosureService = SpringContextHolder.getBean(TraEnclosureService.class);
            traEnclosureService.updateTransNum(docTableSelectItem, transNum);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }


    /**
     * 扣除项计算
     */
    public BigDecimal getBalanceDeduction(TraCredit traCredit) {
        BigDecimal creditAmount = BigDecimal.valueOf(traCredit.getCreditAmount());
        traBalanceDeductionService = SpringContextHolder.getBean(TraBalanceDeductionService.class);
        TraBalanceDeduction traBalanceDeduction = traBalanceDeductionService.get(1);
        if ("1".equals(traBalanceDeduction.getSecuritDeposit())) {
            BigDecimal securitDeposit = traCredit.getSecuritDeposit() == null ? BigDecimal.ZERO : traCredit.getSecuritDeposit();
            creditAmount = creditAmount.subtract(securitDeposit);
        }
        if ("1".equals(traBalanceDeduction.getNationalDebt())) {
            BigDecimal nationalDebt = traCredit.getNationalDebt() == null ? BigDecimal.ZERO : traCredit.getNationalDebt();
            creditAmount = creditAmount.subtract(nationalDebt);
        }
        if ("1".equals(traBalanceDeduction.getBankDeposit())) {
            BigDecimal bankDeposit = traCredit.getBankDeposit() == null ? BigDecimal.ZERO : traCredit.getBankDeposit();
            creditAmount = creditAmount.subtract(bankDeposit);
        }
        return creditAmount;
    }
}
