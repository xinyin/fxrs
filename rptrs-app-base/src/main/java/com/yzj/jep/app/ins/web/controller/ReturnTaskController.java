package com.yzj.jep.app.ins.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.app.bus.service.RelOrgSuspectService;
import com.yzj.jep.app.bus.service.RelPsnSuspectService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.fil.service.*;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IProcInst;
import com.yzj.jep.workflow.impl.engine.Activiti5WorkflowEngine;
import com.yzj.jep.workflow.wf.model.ProcInst;
import com.yzj.jep.workflow.wf.service.ProcInstService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程撤销操作
 */
@Controller
@RequestMapping("/bus/return")
public class ReturnTaskController {
    private static Logger logger = LoggerFactory.getLogger(ReturnTaskController.class);
    @Autowired
    private WorkflowEngine workflowEngine;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraOtherService trainsOtherService;
    @Autowired
    BusProcService busProcService;
    @Autowired
    RelPsnSuspectService relPsnSuspectService;
    @Autowired
    FilInvestmentService filInvestmentService;
    @Autowired
    FilInterestService filInterestService;
    @Autowired
    FilBusinessService filBusinessService;
    @Autowired
    FilServiceService filServiceService;
    @Autowired
    FilFundsService filFundsService;
    @Autowired
    private RelOrgSuspectService relOrgSuspectService;
    @Autowired
    private RelService relService;
    @Autowired
    private ApprovalPushService approvalPushService;

    private static final Map<String, String> subjectProcesses = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectProcesses.put("REL", "FXBDX_GLGXBGBA");
        subjectProcesses.put("ZDTI_BranchOffice", "FXBDX_ZDTY_BranchOffice");
        subjectProcesses.put("YBGLJY", "FXBDX_YBGLJY");
        subjectProcesses.put("YBGLJY_BranchOffice", "FXBDX_YBGLJY_Company");
        subjectProcesses.put("ZDTI", "FXBDX_ZDTY");
    }

    @RequestMapping(value = "/dealTask")
    @ResponseBody
    public Map<String, Object> complete(String taskId, String formData, String procInstId, String memo, String nodeKey) {

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> json = null;
        try {
            json = workflowEngine.getRuntimeService().getData(taskId, formData, procInstId, memo);
            logger.debug("获取JSON成功");
        } catch (Exception e) {
            logger.debug("获取JSON失败");
            e.printStackTrace();
        }
        //获取审批数据
        JSONObject data = (JSONObject) json.get("data");
        String code = json.get("code").toString();
        String TYPE = (String) data.get("TransactionTtype");
        String workflowType =(String) data.get("WorkflowType");
        int pid = data.getInteger("pid");
        String transNum = data.getString("transNum");
        String uniqueCode = data.getString("uniqueCode");
        String sourceSystem = data.getString("sourceSystem");
        //根据不同的流程模板去做不同退回处理
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        String procDefCode = subjectProcesses.get(workflowType);
        //退回操作，修改数据状态，改为审批退回
        if (procDefCode.equals(code)) {
            //疑似自然人
            try {
                if (TYPE.equals("REL")) {
                    //关联关系
                    Rel rel = new Rel();
                    rel.setPid(pid);
                    rel.setApprovalStatus("5");
                    relService.updateSelective(rel);
                } else if ("investment".equals(TYPE)) {
                    //投资入股类
                    TraInvestment traInvestment = new TraInvestment();
                    traInvestment.setPid(pid);
                    traInvestment.setStatus("5");
                    traInvestmentService.updateSelective(traInvestment);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_5);
                } else if ("funds".equals(TYPE)) {
                    //资金运用类
                    TraFunds traFunds = new TraFunds();
                    traFunds.setPid(pid);
                    traFunds.setStatus("5");
                    traFundsService.updateSelective(traFunds);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_5);
                } else if ("business".equals(TYPE)) {
                    //保险业务类
                    TraBusiness traBusiness = new TraBusiness();
                    traBusiness.setPid(pid);
                    traBusiness.setStatus("5");
                    traBusinessService.updateSelective(traBusiness);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_5);
                } else if ("interest".equals(TYPE)) {
                    //利益转移类
                    TraInterest traInterest = new TraInterest();
                    traInterest.setPid(pid);
                    traInterest.setStatus("5");
                    traInterestService.updateSelective(traInterest);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_5);
                } else if ("service".equals(TYPE)) {
                    //提供服务类
                    TraService traService = new TraService();
                    traService.setPid(pid);
                    traService.setStatus("5");
                    traServiceService.updateSelective(traService);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_5);
                } else if ("other".equals(TYPE)) {
                    //其他类
                    TraOther traOther = new TraOther();
                    traOther.setPid(pid);
                    traOther.setStatus("5");
                    trainsOtherService.updateSelective(traOther);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_5);
                }
                if (!"FXBDX_GLGXBGBA".equals(procDefCode)){
                    if (nodeKey.equals("usertask1")){
                        if(Dict.SOURCE_SYSTEM_OA.equals(sourceSystem)) {
                            approvalPushService.pushResult(uniqueCode, "1");
                        }
                    }
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        result.put(WebCode.SUCCESS, true);
        result.put(WebCode.MSG, "退回处理成功");
        return result;
    }

    @RequestMapping(value = "/returnTask")
    @ResponseBody
    public Map<String, Object> returnComplete(String taskId, String formData, String procInstId, String memo,String nodeKey) {
        Map<String, Object> result = new HashMap<String, Object>();

//        Map<String, Object> json=returnTaskService.getData(taskId, formData, procInstId,memo);
        Map<String, Object> json = workflowEngine.getRuntimeService().getData(taskId, formData, procInstId, memo);
        //获取审批数据
        JSONObject data = (JSONObject) json.get("data");
        String code = json.get("code").toString();
        String TYPE = (String) data.get("TransactionTtype");
        String workflowType =(String) data.get("WorkflowType");
        int pid = data.getInteger("pid");
        String transNum = data.getString("transNum");
        String uniqueCode = data.getString("uniqueCode");
        String sourceSystem = data.getString("sourceSystem");
        //根据不同的流程模板去做不同退回处理
        UserProfile profile = UserProfileUtils.getUserProfile();
        String procDefCode = null;
        procDefCode = subjectProcesses.get(workflowType);
        if (procDefCode.equals(code)) {
            /*
             * 撤回是修改数据状态：疑似关联方，关联交易 关联关系，状态改为待提交
             * */
            try {
                if ("PSN".equals(TYPE)) {
                    RelPsnSuspect relPsnSuspect = new RelPsnSuspect();
                    relPsnSuspect.setPid(pid);
                    relPsnSuspect.setStatus("3");
                    relPsnSuspect.setIsAssoParty("0");
                    relPsnSuspectService.updateSelective(relPsnSuspect);
                } else if ("ORG".equals(TYPE)) {
                    RelOrgSuspect relOrgSuspect = new RelOrgSuspect();
                    relOrgSuspect.setPid(pid);
                    relOrgSuspect.setApprovalStatus("3");
                    relOrgSuspect.setIsAssoParty("0");
                    relOrgSuspectService.updateSelective(relOrgSuspect);
                } else if ("REL".equals(TYPE)) {
                    Rel rel = new Rel();
                    rel.setPid(pid);
                    rel.setApprovalStatus("3");
                    relService.updateSelective(rel);
                } else if ("investment".equals(TYPE)) {
                    TraInvestment traInvestment = new TraInvestment();
                    traInvestment.setPid(pid);
                    traInvestment.setStatus("3");
                    traInvestmentService.updateSelective(traInvestment);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_3);
                } else if ("funds".equals(TYPE)) {
                    TraFunds traFunds = new TraFunds();
                    traFunds.setPid(pid);
                    traFunds.setStatus("3");
                    traFundsService.updateSelective(traFunds);
                } else if ("business".equals(TYPE)) {
                    TraBusiness traBusiness = new TraBusiness();
                    traBusiness.setPid(pid);
                    traBusiness.setStatus("3");
                    traBusinessService.updateSelective(traBusiness);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_3);
                } else if ("interest".equals(TYPE)) {
                    TraInterest traInterest = new TraInterest();
                    traInterest.setPid(pid);
                    traInterest.setStatus("3");
                    traInterestService.updateSelective(traInterest);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_3);
                } else if ("service".equals(TYPE)) {
                    TraService traService = new TraService();
                    traService.setPid(pid);
                    traService.setStatus("3");
                    traServiceService.updateSelective(traService);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_3);
                } else if ("other".equals(TYPE)) {
                    TraOther traOther = new TraOther();
                    traOther.setPid(pid);
                    traOther.setStatus("3");
                    trainsOtherService.updateSelective(traOther);
                    InsUtil.updateTraCounterpartyStatus(transNum, Dict.APPLY_STATUS_3);
                }
                if (!"FXBDX_GLGXBGBA".equals(procDefCode)){
                    if (nodeKey.equals("usertask1")){
                        if(Dict.SOURCE_SYSTEM_OA.equals(sourceSystem)) {
                            approvalPushService.pushResult(uniqueCode, "1");
                        }
                    }
                }
            } catch (Exception e) {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, "撤回处理失败");
            }
        }
        result.put(WebCode.SUCCESS, true);
        result.put(WebCode.MSG, "撤回处理成功");
        return result;
    }

    public void saveMidBusProc(String tenantId, String procInstId, String pid, String filPid, String type, BusProcService busProcService) {
        BusProc busProc = new BusProc();
        busProc.setProcInstId(procInstId);
        busProc.setFlagPid(pid);
        busProc.setFilPid(filPid);
        busProc.setBusType(type);
        busProc.setTenantId(tenantId);
        busProcService.insertSelective(busProc);
    }

}
