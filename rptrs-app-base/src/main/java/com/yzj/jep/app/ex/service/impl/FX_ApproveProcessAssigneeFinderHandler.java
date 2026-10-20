package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.model.Email;
import com.yzj.jep.app.ins.service.ApproveAssignerService;
import com.yzj.jep.app.util.EmailSend;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;
import com.yzj.jep.workflow.impl.engine.AbstractActiviti5WorkflowEngineAware;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 会签审批人设置
 * @author duanliqiang
 */
@AssigneeFinderHandler(value = "gljyhqManager", name = "关联交易会签")
public class FX_ApproveProcessAssigneeFinderHandler extends AbstractActiviti5WorkflowEngineAware implements ProcessAssigneeFinderHandler {

    StaffService staffService;

    private ApproveAssignerService approveAssignerService;

    FX_RelatedPartyTransactions fxRelatedPartyTransactions;

    EmailSend emailSend;

    private static final String NODE_NAME_HG = "合规部";
    private static final String ASSET_MANAGEMENT_CENTER = "资产管理中心";
    private static final String BOARD_OFDIRECTORS_SECRETARY_OFFICES = "董事会秘书办公室";
    private static final String HR = "人力资源部";
    /**
     * 重大审批流程关联交易管理办公室-人力资源部负责人改成风险合规部负责人待评估
      */
    private static final String RISK_COMPLIANCE_DEPT = "风险管理部";
    private static final String FINANCE_DEPARTMENT = "财务部";
    private static final String RELATED_PARTY_TRA_OFF = "关联交易办公室";

    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {
        emailSend = SpringContextHolder.getBean(EmailSend.class);
        fxRelatedPartyTransactions = SpringContextHolder.getBean(FX_RelatedPartyTransactions.class);
        staffService = SpringContextHolder.getBean(StaffService.class);
        approveAssignerService = SpringContextHolder.getBean(ApproveAssignerService.class);
        JSONObject json = JSON.parseObject(data);
        String starterUnitCode = pevTask.getStarterUnitCode();
        // 流程编码
        String procCode = pevTask.getProcInst().getProcCode();

        List list = new ArrayList();
        Set<String> set = new HashSet<>();
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00027", "");
        for (Staff staff : staffList) {
            if ("总裁室".equals(staff.getDeptName())) {
                set.add(staff.getStaffCode());
                Email email = new Email();
                email.setRecevieEmail(staff.getEmail());
                list.add(email);
            }
        }
        List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("00026", "");
        for (Staff staff : staffLists) {

            if (staff.getDeptName().equals(ASSET_MANAGEMENT_CENTER)) {
                set.add(staff.getStaffCode());
                Email email = new Email();
                email.setRecevieEmail(staff.getEmail());
                list.add(email);
            }else if (staff.getDeptName().equals(BOARD_OFDIRECTORS_SECRETARY_OFFICES)) {
                set.add(staff.getStaffCode());
                Email email = new Email();
                email.setRecevieEmail(staff.getEmail());
                list.add(email);
            }else if (staff.getDeptName().equals(FINANCE_DEPARTMENT)) {
                set.add(staff.getStaffCode());
                Email email = new Email();
                email.setRecevieEmail(staff.getEmail());
                list.add(email);
            } else if (staff.getDeptName().equals(RISK_COMPLIANCE_DEPT)) {
                    set.add(staff.getStaffCode());
                Email email = new Email();
                    email.setRecevieEmail(staff.getEmail());
                    list.add(email);
            }else if (staff.getDeptName().equals(NODE_NAME_HG)) {
                set.add(staff.getStaffCode());
                Email email = new Email();
                email.setRecevieEmail(staff.getEmail());
                list.add(email);
            }
        }

        if (set.isEmpty()) {
            //空值先临时设定一个值，否则会报错
            set.add("zhangsan");
        }
        emailSend.emailSend(list, data);
        return set;
    }


}
