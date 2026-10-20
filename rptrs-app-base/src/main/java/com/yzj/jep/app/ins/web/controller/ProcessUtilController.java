package com.yzj.jep.app.ins.web.controller;

import com.yzj.jep.app.ins.common.UnitLevelMap;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.AttachmentService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.sys.service.UserService;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IProcInst;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProcessFileController
 * @Author Whx
 * @Date: 2021/7/27 10:34
 * @Version 1.0
 * 流程附件处理
 */
@Controller
@RequestMapping("/ins/process")
public class ProcessUtilController {

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    private UnitService unitService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * 查询分公司部门对应总公司的部门code
     *
     * @return
     */
    @GetMapping("/queryParentDeptCode")
    @ResponseBody
    public List<Map<String, Object>> queryParentDeptCode(String procInstId, String taskId) {
        List<Map<String, Object>> list = new ArrayList<>();

        IProcInst procInst = workflowEngine.getRuntimeService().getProcInst(procInstId);
        //activiti原始的任务查询
        TaskService taskService = SpringContextHolder.getBean(TaskService.class);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String taskName = task.getName();
        String procCode = procInst.getProcCode();
        //关联交易重大审批流程需要跨机构,并且是部门总经理才需要跨部门
        if ("zybr_relGreat".equals(procCode) && taskName.contains("分公司总经理")) {
            //做部门映射逻辑
            String starterDeptCode = procInst.getStarterDeptCode();
            String starterDeptName = procInst.getStarterDeptName();
            String starterUnitCode = procInst.getStarterUnitCode();
            //目前只加了江西分公司，测试寻找对应部门
            String level = (String) UnitLevelMap.unitMap.get(starterUnitCode);
            List<String> deptNameList = new ArrayList<>();
            if ("A".equals(level)) {
                deptNameList = (List<String>) UnitLevelMap.deptA.get(starterDeptName);
            } else if ("B".equals(level)) {
                deptNameList = (List<String>) UnitLevelMap.deptB.get(starterDeptName);
            } else {
                deptNameList = (List<String>) UnitLevelMap.deptD.get(starterDeptName);
            }
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter("unitName", Operator.IN, deptNameList));
            filters.add(new Filter("kind", Operator.EQ, "DEPT"));
            filters.add(new Filter("owner_", Operator.EQ, "8600"));
            List<Unit> unitList = unitService.select(filters);
            for (Unit unit : unitList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", unit.getUnitCode());
                map.put("text", unit.getUnitName());
                list.add(map);
            }
        }
        return list;
    }
}
