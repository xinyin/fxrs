package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.model.Email;
import com.yzj.jep.app.ins.service.ApprovalPushService;
import com.yzj.jep.app.ins.service.ApproveAssignerService;
import com.yzj.jep.app.util.EmailSend;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.RoleService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;
import com.yzj.jep.workflow.impl.engine.AbstractActiviti5WorkflowEngineAware;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 审批人设置
 */
@AssigneeFinderHandler(value = "RelatedPartyTransactions", name = "关联交易节点审批")
@Component
public class FX_RelatedPartyTransactions extends AbstractActiviti5WorkflowEngineAware implements ProcessAssigneeFinderHandler {

    StaffService staffService;

    EmailSend emailSend;

    private ApproveAssignerService approveAssignerService;

    private ApprovalPushService approvalPushService;

    private RoleService roleService;

    private static final String NODE_NAME_HG = "合规部";
    private static final String DEPARTMENT_HEAD = "总部部门负责人";
    private static final String DEPARTMENT_COMPANY = "分公司部门负责人";
    private static final String COMPLIANCE_POST_BRANCH = "分公司合规岗";
    private static final String GENERAL_MANAGER_OF_BRANCH = "分公司总经理";
    private static final String HEADQUARTERS_RELATED_TRANSACTIONS = "总部合规部关联交易管理岗";
    private static final String HEADQUARTERS_COMPLIANCE_DEPARTMENT_HEAD = "总部合规部门负责人";
    private static final String CEO = "总部战略企划部经办人";
    private static final String BOARD_SECRETARY_OFFICE = "总部董秘办经办人";
    private static final String BOARD_SECRETARY_OFFICES = "总部董秘办负责人";
    //private static final String HR = "总部人力资源部负责人";
    private static final String HR = "人力资源部";
    private static final String BOARD_OFDIRECTORS_SECRETARY_OFFICES = "董事会秘书办公室";
    private static final String FINANCE_DEPARTMENT = "财务部";
    private static final String NODE_NAME_FL = "法律部";
    private static final String NODE_NAME_CX = "创新业务部";
    //洛阳中心支公司（筹）
    private static final String NODE_CODE_LYZXZGS = "FX00001_2_5_17";
    //江苏分公司办公室
    private static final String NODE_CODE_JSFGSBG = "FX00001_2_4_22";

    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {
        emailSend = SpringContextHolder.getBean(EmailSend.class);
        approvalPushService = SpringContextHolder.getBean(ApprovalPushService.class);
        staffService = SpringContextHolder.getBean(StaffService.class);
        approveAssignerService = SpringContextHolder.getBean(ApproveAssignerService.class);
        roleService = SpringContextHolder.getBean(RoleService.class);
        JSONObject json = JSONObject.parseObject(data);
        Set<String> set = new HashSet<String>();
        //获取角色分类
        String taskName = task.getTaskName();
        String taskKey = task.getTaskKey();
        String procCode = pevTask.getProcInst().getProcCode();
        String starterName = pevTask.getProcInst().getStarterName();
        String starterDeptName = pevTask.getProcInst().getStarterDeptName();
        String starterUnitCode = pevTask.getProcInst().getStarterUnitCode();
        String starterDeptCode = pevTask.getProcInst().getStarterDeptCode();
        //关联交易管理审批
        if ("FXBDX_GLGXBGBA".equals(procCode)) {
            Email emails = new Email();
            List list = new ArrayList();
            //总部人力资源部经办人
            if (HR.equals(starterDeptName) && taskKey.equals("usertask2")) {
                List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
                for (Staff staff : staffList) {
                    if (staff.getDeptName().contains("人力资源部")) {
                        set.add(staff.getStaffCode());
                        emails.setRecevieEmail(staff.getEmail());
                    }
                }
                list.add(emails);
                emailSend.emailSend(list, data);
                //总部合规部关联交易管理岗
            }
            Set<String> approver = this.approver(taskName, starterDeptName, data, taskKey);
            for (String str : approver) {
                set.add(str);
            }


            //重大(累计重大)统一交易总公司审批流节点
        } else if ("FXBDX_ZDTY".equals(procCode)) {
            Set<String> approver = this.approver(taskName, starterDeptName, data, taskKey);
            if (approver.size() != 0) {
                for (String str : approver) {
                    set.add(str);
                }
            }
        } else if ("FXBDX_YBGLJY".equals(procCode)) {
            Set<String> approver = this.approver(taskName, starterDeptName, data, taskKey);
            for (String str : approver) {
                set.add(str);
            }
        } else if ("FXBDX_ZDTY_BranchOffice".equals(procCode)) {
            Set<String> approver = this.approver(taskName, starterDeptName, data, taskKey);
            if (approver.size() != 0) {
                for (String str : approver) {
                    set.add(str);
                }
            }
            Set<String> strings = this.companyType(taskName, starterDeptCode, data, starterUnitCode);
            if (strings.size() != 0) {
                for (String str : strings) {
                    set.add(str);
                }
            }
        } else if ("FXBDX_YBGLJY_Company".equals(procCode)) {
            Set<String> approver = this.approver(taskName, starterDeptName, data, taskKey);
            if (approver.size() != 0) {
                for (String str : approver) {
                    set.add(str);
                }
            }
            Set<String> strings = this.companyType(taskName, starterDeptCode, data, starterUnitCode);
            if (strings.size() != 0) {
                for (String str : strings) {
                    set.add(str);
                }
            }
        }
        if (set.size() == 0) {
            //空值先临时设定一个值，否则会报错
            set.add("zhangsan");
        }
        return set;
    }

    public Set<String> approver(String taskName, String starterDeptName, String data, String taskKey) {
        Email email = new Email();
        Set<String> set = new HashSet<String>();
        //总部董秘办负责人
        if (BOARD_OFDIRECTORS_SECRETARY_OFFICES.equals(starterDeptName) && taskKey.equals("usertask2")) {
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
            for (Staff staff : staffList) {
                if (staff.getDeptName().contains("董事会秘书办公室")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
            //总部合规部门负责人
        } else if ((NODE_NAME_HG.equals(starterDeptName) && taskKey.equals("usertask2"))) {
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
            for (Staff staff : staffList) {
                if (staff.getDeptName().equals(NODE_NAME_HG)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }//总部法律部
        else if ((NODE_NAME_FL.equals(starterDeptName) && taskKey.equals("usertask2"))) {
            List<Staff> staffList = roleService.queryRoleStaff("1000031");
            for (Staff staff : staffList) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
        }//总部创新业务部
        else if ((NODE_NAME_CX.equals(starterDeptName) && taskKey.equals("usertask2"))) {
            List<Staff> staffList = roleService.queryRoleStaff("10000033");
            for (Staff staff : staffList) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
        }
        //总部合规部关联交易管理岗
        else if (HEADQUARTERS_RELATED_TRANSACTIONS.equals(taskName)) {
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffList) {
                if (staff.getDeptName().equals(NODE_NAME_HG)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //总部合规部门负责人
        else if (taskName.equals(HEADQUARTERS_COMPLIANCE_DEPARTMENT_HEAD)) {
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
            for (Staff staff : staffList) {
                if (staff.getDeptName().equals(NODE_NAME_HG)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //总部战略企划部经办人
        else if (taskName.equals(CEO)) {
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
            for (Staff staff : staffList) {
                if (staff.getDeptName().equals("战略企划部")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //总部董秘办经办人
        else if (taskName.equals(BOARD_SECRETARY_OFFICE)) {
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffList) {
                if (staff.getDeptName().equals(BOARD_OFDIRECTORS_SECRETARY_OFFICES)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }//总部部门负责人
        else if (taskName.equals(DEPARTMENT_HEAD)) {
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
            for (Staff staff : staffList) {
                if (staff.getDeptName().equals(starterDeptName)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        if (email.getRecevieEmail() != null) {
            List list = new ArrayList();
            list.add(email);
                emailSend.emailSend(list, data);
        }
        return set;
    }

    //分公司调用审批
    private Set<String> companyType(String taskName, String starterDeptCode, String data, String starterUnitCode) {
        Set<String> result = new HashSet<String>();
        //上海分公司
        if ("FX00001_2_6".equals(starterUnitCode)) {
            result = this.shangHaiBranch(starterDeptCode, taskName);
            //河南分公司
        } else if ("FX00001_2_5".equals(starterUnitCode)) {
            result = this.heNanBranch(starterDeptCode, taskName);
            //江苏分公司
        } else if ("FX00001_2_4".equals(starterUnitCode)) {
            result = this.jiangSuBranch(starterDeptCode, taskName);
            //山东分公司
        } else if ("FX00001_2_3".equals(starterUnitCode)) {
            result = this.shanDongBranch(starterDeptCode, taskName);
            //四川分公司
        } else if ("FX00001_2_2".equals(starterUnitCode)) {
            result = this.siChuanBranch(starterDeptCode, taskName);
            //北京分公司
        } else if ("FX00001_2_1".equals(starterUnitCode)) {
             result = this.beiJingBranch(starterDeptCode, taskName);
        }
        return result;
    }

    private Set<String> shangHaiBranch(String starterDeptCode, String taskName) {
        Set<String> set = new HashSet<String>();
        Email email = new Email();
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00023", "");
        if (taskName.equals(DEPARTMENT_COMPANY)) {
            for (Staff staff : staffList) {
                if (staff.getDeptCode().equals(starterDeptCode)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
            //分公司总经理负责人
            else if (taskName.equals(GENERAL_MANAGER_OF_BRANCH)) {
            List<Staff> staffList2 = roleService.queryRoleStaff("2000024");
            for (Staff staff : staffList2) {
                if (staff.getDeptName().equals("上海业务总部总经理室")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //分公司合规岗经办人
        if (taskName.equals(COMPLIANCE_POST_BRANCH)) {
            List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffLists) {
                if (staff.getDeptName().equals("上海业务总部合规部")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        if (email.getRecevieEmail() != null) {
            List list = new ArrayList();
            list.add(email);
            emailSend.emailSend(list, null);
        }
        return set;
    }


    private Set<String> beiJingBranch(String starterDeptCode, String taskName) {
        Set<String> set = new HashSet<String>();
        Email email = new Email();
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00023", "");
        if (taskName.equals(DEPARTMENT_COMPANY)) {
            for (Staff staff : staffList) {
                if (staff.getDeptCode().equals(starterDeptCode)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //分公司总经理负责人
        else if (taskName.equals(GENERAL_MANAGER_OF_BRANCH)) {
            List<Staff> staffList2 = roleService.queryRoleStaff("2000024");
            for (Staff staff : staffList2) {
                if (staff.getDeptName().equals("北京分公司总经理室")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //分公司合规岗经办人
        if (taskName.equals(COMPLIANCE_POST_BRANCH)) {
            List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffLists) {
                if (staff.getDeptName().equals("北京分公司合规部")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        /*for (Staff staff : staffList) {
            if (staff.getDeptCode().equals("FX00001_2_6_1")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_10")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_11")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_12")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_13")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_14")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_15")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_16")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_17")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }
            else if (staff.getDeptCode().equals("FX00001_2_6_18")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_19")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_2")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_20")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_21")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_22")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_23")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_3")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_4")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_5")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_6")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_7")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_8")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }else if (staff.getDeptCode().equals("FX00001_2_6_9")) {
                set.add(staff.getStaffCode());
                email.setRecevieEmail(staff.getEmail());
            }*/
        if (email.getRecevieEmail() != null) {
            List list = new ArrayList();
            list.add(email);
            emailSend.emailSend(list, null);
        }
        return set;
    }

    private Set<String> jiangSuBranch(String starterDeptCode, String taskName) {

        Set<String> set = new HashSet<String>();
        Email email = new Email();

        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00023", "");
        if (taskName.equals(DEPARTMENT_COMPANY)) {
            for (Staff staff : staffList) {
                if (staff.getDeptCode().equals(starterDeptCode)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
            //江苏分公司办公室
            if (starterDeptCode.equals(NODE_CODE_JSFGSBG)) {
                List<Staff> staffList1 = roleService.queryRoleStaff("20000032");
                for (Staff staff1 : staffList1) {
                    set.add(staff1.getStaffCode());
                    email.setRecevieEmail(staff1.getEmail());
                }
            }
        }
        //分公司总经理负责人
        else if (taskName.equals(GENERAL_MANAGER_OF_BRANCH)) {
            List<Staff> staffList2 = roleService.queryRoleStaff("2000024");
            for (Staff staff : staffList2) {
                if (staff.getDeptName().equals("江苏分公司总经理室")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //分公司合规岗经办人
        if (taskName.equals(COMPLIANCE_POST_BRANCH)) {
            List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffLists) {
                if (staff.getDeptName().equals("江苏分公司合规部")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        if (email.getRecevieEmail() != null) {
            List list = new ArrayList();
            list.add(email);
            emailSend.emailSend(list, null);
        }
        return set;
    }

    private Set<String> siChuanBranch(String starterDeptCode, String taskName) {
        Set<String> set = new HashSet<String>();
        Email email = new Email();
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00023", "");
        if (taskName.equals(DEPARTMENT_COMPANY)) {
            for (Staff staff : staffList) {
                if (staff.getDeptCode().equals(starterDeptCode)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //分公司总经理负责人
        else if (taskName.equals(GENERAL_MANAGER_OF_BRANCH)) {
            List<Staff> staffList2 = roleService.queryRoleStaff("2000024");
            for (Staff staff : staffList2) {
                if (staff.getDeptName().equals("四川分公司总经理室")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }

        //分公司合规岗经办人
        if (taskName.equals(COMPLIANCE_POST_BRANCH)) {
            List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffLists) {
                if (staff.getDeptName().equals("四川分公司合规部")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        if (email.getRecevieEmail() != null) {
            List list = new ArrayList();
            list.add(email);
            emailSend.emailSend(list, null);
        }
        return set;
    }

    private Set<String> shanDongBranch(String starterDeptCode, String taskName) {
        Set<String> set = new HashSet<String>();
        Email email = new Email();
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00023", "");
        if (taskName.equals(DEPARTMENT_COMPANY)) {
            for (Staff staff : staffList) {
                if (staff.getDeptCode().equals(starterDeptCode)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //分公司总经理负责人
        else if (taskName.equals(GENERAL_MANAGER_OF_BRANCH)) {
            List<Staff> staffList2 = roleService.queryRoleStaff("2000024");
            for (Staff staff : staffList2) {
                if (staff.getDeptName().equals("山东分公司总经理室")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        //分公司合规岗经办人
        if (taskName.equals(COMPLIANCE_POST_BRANCH)) {
            List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffLists) {
                if (staff.getDeptName().equals("山东分公司合规部")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }
        if (email.getRecevieEmail() != null) {
            List list = new ArrayList();
            list.add(email);
            emailSend.emailSend(list, null);
        }
        return set;
    }

    private Set<String> heNanBranch(String starterDeptCode, String taskName) {
        Set<String> set = new HashSet<String>();
        Email email = new Email();

        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00023", "");
        if (taskName.equals(DEPARTMENT_COMPANY)) {
            for (Staff staff : staffList) {
                if (staff.getDeptCode().equals(starterDeptCode)) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
            //洛阳中心支公司（筹）
            if(starterDeptCode.equals(NODE_CODE_LYZXZGS)){
                List<Staff> staffList1 = roleService.queryRoleStaff("2000032");
                for (Staff staff1 : staffList1) {
                    set.add(staff1.getStaffCode());
                    email.setRecevieEmail(staff1.getEmail());
                }
            }
        }
        //分公司总经理负责人
        else if (taskName.equals(GENERAL_MANAGER_OF_BRANCH)) {
            List<Staff> staffList2 = roleService.queryRoleStaff("2000024");
            for (Staff staff : staffList2) {
                if (staff.getDeptName().equals("河南分公司总经理室")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }

        //分公司合规岗经办人
        if (taskName.equals(COMPLIANCE_POST_BRANCH)) {
            List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("000028", "");
            for (Staff staff : staffLists) {
                if (staff.getDeptName().equals("河南分公司合规部")) {
                    set.add(staff.getStaffCode());
                    email.setRecevieEmail(staff.getEmail());
                }
            }
        }

        if (email.getRecevieEmail() != null) {
            List list = new ArrayList();
            list.add(email);
            emailSend.emailSend(list, null);
        }
        return set;
    }


}
