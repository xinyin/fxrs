package com.yzj.jep.app.wf.service.impl;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.sub.service.InsOtherService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.wf.service.WorkflowOperation;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IProcInst;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.service.HisProcInstService;
import com.yzj.jep.workflow.wf.service.ProcInstService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WorkflowOperationImpl implements WorkflowOperation {

    @Autowired
    HisProcInstService hisProcInstService;

    @Autowired
    WorkflowEngine workflowEngine;

    @Autowired
    RelPsnService relPsnService;

    @Autowired
    RelOrgService relOrgService;

    @Autowired
    TraInvestmentService traInvestmentService;
    @Autowired
    RelService relService;

    @Autowired
    TraFundsService traFundsService;

    @Autowired
    TraBusinessService traBusinessService;

    @Autowired
    TraInterestService traInterestService;

    @Autowired
    TraServiceService traServiceService;

    @Autowired
    TraOtherService traOtherService;

    @Autowired
    StaffService staffService;

    @Autowired
    ProcInstService procInstService;

    @Autowired
    InsOtherService insOtherService;

    @Autowired
    RelPsnSuspectService relPsnSuspectService;

    @Autowired
    RelOrgSuspectService relOrgSuspectService;

    /**
     * 提交审批
     * @param pid
     * @param procDefCode
     * @param staff
     * @param oldData
     * @param newData
     * @param type
     * @param procInstId
     * @return
     * @throws WorkflowException
     */
    @Override
    public Map<String, Object> sendSubmit(Integer pid, String procDefCode, StaffImpl staff, String oldData, String newData, String type, String procInstId) throws WorkflowException {
        Map<String,Object> rs = new HashMap<>();
        Boolean status = false;
        try {
            HisProcInst his = hisProcInstService.get(procInstId);
            if(null != his && Dict.WF_RUN.equals(his.getStatus())){
                status = true;
            }
            IProcInst procInst = null;
            //发起流程
            if(org.apache.commons.lang.StringUtils.isNotBlank(procInstId)&&status){
                procInst = workflowEngine.getRuntimeService().startProcess(procInstId, newData, staff);
            }else {
                procInst = workflowEngine.getRuntimeService().startProcess(procDefCode, staff, oldData, newData);
            }

            //更新状态值
            if("PSN".equals(type)){
                RelPsnSuspect relPsnSuspect = new RelPsnSuspect();
                relPsnSuspect.setPid(pid);
                relPsnSuspect.setStatus("4");
                relPsnSuspect.setProcInstId(procInst.getId());
                relPsnSuspectService.updateSelective(relPsnSuspect);
            }
            if("REL".equals(type)){
                Rel rel = new Rel();
                rel.setPid(pid);
                rel.setApprovalStatus("4");
                rel.setProcInstId(procInst.getId());
                relService.updateSelective(rel);
            }
            if("ORG".equals(type)){
                RelOrgSuspect relOrgSuspect = new RelOrgSuspect();
                relOrgSuspect.setPid(pid);
                relOrgSuspect.setApprovalStatus("4");
                relOrgSuspect.setProcInstId(procInst.getId());
                relOrgSuspectService.updateSelective(relOrgSuspect);
            }
            if("investment".equals(type)){
                TraInvestment traInvestment = new TraInvestment();
                traInvestment.setPid(pid);
                traInvestment.setStatus("4");
                traInvestment.setProcInstId(procInst.getId());
                traInvestmentService.updateSelective(traInvestment);
            }
            if("funds".equals(type)){
                TraFunds traFunds = new TraFunds();
                traFunds.setPid(pid);
                traFunds.setStatus("4");
                traFunds.setProcInstId(procInst.getId());
                traFundsService.updateSelective(traFunds);
            }
            if("business".equals(type)){
                TraBusiness traBusiness = new TraBusiness();
                traBusiness.setPid(pid);
                traBusiness.setStatus("4");
                traBusiness.setProcInstId(procInst.getId());
                traBusinessService.updateSelective(traBusiness);
            }
            if("interest".equals(type)){
                TraInterest traInterest = new TraInterest();
                traInterest.setPid(pid);
                traInterest.setStatus("4");
                traInterest.setProcInstId(procInst.getId());
                traInterestService.updateSelective(traInterest);
            }
            if("service".equals(type)){
                TraService traService = new TraService();
                traService.setPid(pid);
                traService.setStatus("4");
                traService.setProcInstId(procInst.getId());
                traServiceService.updateSelective(traService);
            }
            if("other".equals(type)){
                TraOther traOther = new TraOther();
                traOther.setPid(pid);
                traOther.setStatus("4");
                traOther.setProcInstId(procInst.getId());
                traOtherService.updateSelective(traOther);
            }
        }catch (WorkflowException e){
            e.printStackTrace();
            throw new  WorkflowException(e.getMessage());
        }

        rs.put(WebCode.SUCCESS, true);
        rs.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        return rs;
    }


    public Map<String, Object> reject(String taskId, String nodeKey, String procInstId, String memo) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            workflowEngine.getRuntimeService().reject(procInstId, taskId, nodeKey, staff, memo);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

}
