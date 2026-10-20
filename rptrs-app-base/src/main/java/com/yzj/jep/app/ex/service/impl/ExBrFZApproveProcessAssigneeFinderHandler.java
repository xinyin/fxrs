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

//@AssigneeFinderHandler(value = "brFZ", name = "部门负责人")
public class ExBrFZApproveProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {

    private static final String NODE_NAME_START = "提交审批";
    private static final String NODE_NAME_CW = "财务部";
    private static final String NODE_NAME_HG = "合规部";
    //负责对应的角色分类编码
    private static final String ROLE_STD_CODE = "3320";

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
            //查询审核人对应的用户
            List<Staff> staffList = approveAssignerService.selectStaffByRoleStd(ROLE_STD_CODE,"");
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
        }
        if (set.size() == 0) {
            set.add("zhangsan");
        }
        return set;
    }
}