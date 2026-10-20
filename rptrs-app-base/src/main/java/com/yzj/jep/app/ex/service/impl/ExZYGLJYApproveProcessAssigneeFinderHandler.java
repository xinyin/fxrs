package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.service.ApproveAssignerService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;
import com.yzj.jep.workflow.impl.engine.AbstractActiviti5WorkflowEngineAware;

import java.util.HashSet;
import java.util.Set;

//@AssigneeFinderHandler(value = "gljyhrManager", name = "关联交易管理负责人")
public class ExZYGLJYApproveProcessAssigneeFinderHandler extends AbstractActiviti5WorkflowEngineAware implements ProcessAssigneeFinderHandler {

    private static final String NODE_NAME_START = "提交审批";
    private static final String NODE_NAME_CW = "财务部";
    private static final String NODE_NAME_HG = "合规部";

    private static final String ROLE_STD_CODE_3300 = "3300";//经办人
    private static final String ROLE_STD_CODE_3310 = "3310";//审核人
    private static final String ROLE_STD_CODE_3320 = "3320";//负责人
    private static final String ROLE_STD_CODE_3330 = "3330";//分管领导
    private static final String ROLE_STD_CODE_3340 = "3340";//分公司总经理

    private ApproveAssignerService approveAssignerService;

    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {


        approveAssignerService = SpringContextHolder.getBean(ApproveAssignerService.class);
        Set<String> set = new HashSet<String>();
        JSONObject json = JSONObject.parseObject(data);
        String nextApprovalPeople = (String) json.get("nextApprovalPeople");
        String pevTaskName = pevTask.getTaskName();
        /*if (StrUtil.isNotBlank(nextApprovalPeople) && pevTaskName.contains(NODE_NAME_START)) {
            set.add(nextApprovalPeople);
        } else {
            String taskName = task.getTaskName();
            String starterDeptCode = task.getStarterDeptCode();
            String starterUnitCode = pevTask.getStarterUnitCode();
            //查询审核人对应的用户
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd(ROLE_STD_CODE_3310);
            if (CollectionUtil.isNotEmpty(staffList)) {
                if (taskName.contains(NODE_NAME_CW)) {
                    //财务部审核人节点
                    for (Staff staff : staffList) {
                        if (staff.getDeptName().contains(NODE_NAME_CW) && starterUnitCode.equals(staff.getUnitCode())) {
                            set.add(staff.getStaffCode());
                        }
                    }
                } else if (taskName.contains(NODE_NAME_HG)) {
                    //合规部审核人节点
                    for (Staff staff : staffList) {
                        if (staff.getDeptName().contains(NODE_NAME_CW) && starterUnitCode.equals(staff.getUnitCode())) {
                            set.add(staff.getStaffCode());
                        }
                    }
                } else {
                    //对应本部门审核人节点
                    for (Staff staff : staffList) {
                        if (starterDeptCode.equals(staff.getDeptCode()) && starterUnitCode.equals(staff.getUnitCode())) {
                            set.add(staff.getStaffCode());
                        }
                    }
                }
            }
        }*/
        //部门不明确，此处先设置固定人员
        if (set.size() == 0) {
            //空值先临时设定一个值，否则会报错
            set.add("zhangsan");
        }
        return set;
    }


}
