package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.collection.CollectionUtil;
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

//@AssigneeFinderHandler(value = "brZJL", name = "分公司总经理")
public class ExBrZJLApproveProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {

    //分管领导对应的角色分类编码
    private static final String ROLE_STD_CODE = "3340";

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
        String taskName = task.getTaskName();
        String starterDeptCode = task.getStarterDeptCode();
        String starterUnitCode = pevTask.getStarterUnitCode();
        //查询审核人对应的用户
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd(ROLE_STD_CODE,"");
        if (CollectionUtil.isNotEmpty(staffList)) {
            //对应本部门审核人节点
            for (Staff staff : staffList) {
                if (starterUnitCode.equals(staff.getUnitCode())) {
                    set.add(staff.getStaffCode());
                }
            }
        }
        if (set.size() == 0) {
            set.add("zhangsan");
        }
        return set;
    }
}