package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.service.ApproveAssignerService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@AssigneeFinderHandler(value = "hdSecondFunctionary", name = "总公司二级部门负责人")
public class HdFunctionarySecondApproveProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {

    private static final String NODE_NAME_START = "提交审批";
    private static final String NODE_NAME_CW = "财务部";
    private static final String NODE_NAME_HG = "合规部";

    private static final String ROLE_STD_CODE_3300 = "3300";//经办人
    private static final String ROLE_STD_CODE_3310 = "3310";//审核人
    private static final String ROLE_STD_CODE_3320 = "3320";//负责人
    private static final String ROLE_STD_CODE_3330 = "3330";//分管领导
    private static final String ROLE_STD_CODE_3340 = "3340";//分公司总经理

    private ApproveAssignerService approveAssignerService;

    /**
     * 查询发起流程时部门对应的分公司部门负责人
     *
     * @param pevTask
     * @param task
     * @param data
     * @return
     * @throws WorkflowException
     */
    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {

        approveAssignerService = SpringContextHolder.getBean(ApproveAssignerService.class);
        Set<String> set = new HashSet<String>();
        JSONObject json = JSONObject.parseObject(data);
        String nextApprovalPeople = (String) json.get("nextApprovalPeople");
        String pevTaskName = pevTask.getTaskName();
        if (StrUtil.isNotBlank(nextApprovalPeople) && pevTaskName.contains(NODE_NAME_START)) {
            set.add(nextApprovalPeople);
        } else {
            String taskName = task.getTaskName();
            String starterDeptCode = task.getStarterDeptCode();
            String starterUnitCode = pevTask.getStarterUnitCode();
            //关联交易审批流程分公司到总公司审批流程，获取相关字段
            String nextHeadDept = (String) json.get("nextHeadDept");
            if (StrUtil.isNotBlank(nextHeadDept)) {
                //此时分公司的重大关联关系流程
                starterDeptCode = nextHeadDept;
            }
            //获取该公司的上级部门
            String hdDeptCode = (String) json.get("hdDeptCode");
            if (StrUtil.isNotBlank(hdDeptCode) && !starterDeptCode.equals(hdDeptCode)) {
                starterDeptCode = hdDeptCode;
            }
            //查询审核人对应的用户
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd(ROLE_STD_CODE_3320,"");
            if (CollectionUtil.isNotEmpty(staffList)) {
                if (taskName.contains(NODE_NAME_CW)) {
                    //财务部审核人节点
                    for (Staff staff : staffList) {
                        if (staff.getDeptName().contains(NODE_NAME_CW) && "8600".equals(staff.getUnitCode())) {
                            set.add(staff.getStaffCode());
                        }
                    }
                } else if (taskName.contains(NODE_NAME_HG)) {
                    //合规部审核人节点
                    for (Staff staff : staffList) {
                        if (staff.getDeptName().contains(NODE_NAME_CW) && "8600".equals(staff.getUnitCode())) {
                            set.add(staff.getStaffCode());
                        }
                    }
                } else {
                    //对应本部门审核人节点
                    for (Staff staff : staffList) {
                        if (starterDeptCode.equals(staff.getDeptCode()) && "8600".equals(staff.getUnitCode())) {
                            set.add(staff.getStaffCode());
                        }
                    }
                }
            }
        }
        if (set.size() == 0) {
            //空值先临时设定一个值，否则会报错
            set.add("zhangsan");
        }
        return set;
    }
}