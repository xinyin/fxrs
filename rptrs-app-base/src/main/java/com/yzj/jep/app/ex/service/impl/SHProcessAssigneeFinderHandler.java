package com.yzj.jep.app.ex.service.impl;

import com.yzj.jep.app.bus.service.TraServiceService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//审核岗

//BranchOperator 分公司经办人
//BranchFunctionary 分公司负责人
//BranchLeader 分公司部门分管领导
//BranchAuditor 分公司部门审核人

//财务部门
//BranchFinanceLeader 分公司财务部门分管领导
//BranchFinanceOperator 分公司财务部门经办人
//BranchFinanceFunctionary 分公司财务部门负责人
//BranchFinanceAuditor 分公司财务部门审核人

//合规部门
//BranchComplianceLeader  分公司合规部门分管领导
//BranchComplianceOperator  分公司合规部门经办人
//BranchComplianceFunctionary 分公司合规部门负责人

//总经理
//BranchGeneralManager  分公司总经理



//@AssigneeFinderHandler(value = "shenHe", name = "审核岗")
public class SHProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {
    TraServiceService traServiceService;

    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {

        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        Set<String> set = new HashSet<String>();
        String name = "";
        List<String> list = traServiceService.selStaffSH();
        if (list == null) {
            set.add("brm");
        }
        for (int i = 0; i < list.size(); i++) {
            name = (String) list.get(i);
            set.add(name);
        }
        return set;
    }
}
