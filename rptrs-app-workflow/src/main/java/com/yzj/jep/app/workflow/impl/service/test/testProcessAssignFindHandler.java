package com.yzj.jep.app.workflow.impl.service.test;

import java.util.HashSet;
import java.util.Set;

import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;

/**
 * 测试流程数据处理接口
 * @author Michael
 * @time 2017年10月16日 上午10:57:25
 * @company 银之杰技术有限公司
 */
@AssigneeFinderHandler(value = "test", name = "测试")
public class testProcessAssignFindHandler implements ProcessAssigneeFinderHandler {
	
	@Override
	public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {
		Set<String> set = new HashSet<>();
		StaffService staffService = SpringContextHolder.getBean(StaffService.class);
		set.add(staffService.get("jepAdmin").getStaffCode());
		set.add(staffService.get("wxw").getStaffCode());
		return set;
	}

}
