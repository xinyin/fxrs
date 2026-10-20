package com.yzj.jep.app.ex.service.impl;

import java.util.HashSet;
import java.util.Set;

//import com.yzj.jep.app.pm.model.Project;
//import com.yzj.jep.app.pm.model.Projectstaff;
//import com.yzj.jep.app.pm.service.ProjectService;
//import com.yzj.jep.app.pm.service.ProjectstaffService;
//import com.yzj.jep.app.pub.model.Depbizsupport;
//import com.yzj.jep.app.pub.model.PubStaff;
//import com.yzj.jep.app.pub.service.DepbizsupportService;
//import com.yzj.jep.app.pub.service.PubStaffService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;

//@AssigneeFinderHandler(value = "sybManager", name = "事业部经理")
public class ExCOOApproveProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {

//	PubStaffService pubStaffService;
//	DepbizsupportService depbizsupportService;
//	ProjectService projectService;

    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {

//		pubStaffService = SpringContextHolder.getBean(PubStaffService.class);
//		depbizsupportService = SpringContextHolder.getBean(DepbizsupportService.class);
//		projectService = SpringContextHolder.getBean(ProjectService.class);

//		JSONObject json = JSONObject.parseObject(data);
//		String entityId = json.getString("entityId");
//		String eid = json.getString("eid");
//		String eid = UserProfileUtils.getUserProfile().getUnitCode();
        Set<String> set = new HashSet<String>();
		/*String staffId = pevTask.getStarterCode();
		PubStaff staff  = new PubStaff();
		staff.setEid(eid);
		staff.setStaffId(staffId);
		PubStaff pubStaff = pubStaffService.get(staff);
		String depid = pubStaff.getDepId();
		if(depid != null) {
			List<Filter> filters = new Filter.Builder().eq("eid", eid).eq("bizSupType","COO").build();
			List<Depbizsupport> depbizsupports = depbizsupportService.select(filters);
			if(depbizsupports != null && depbizsupports.size()>0) {
				Depbizsupport depbizsupport =  depbizsupports.get(0);
				set.add(depbizsupport.getStaffId());
			}
		}*/
        set.add("odn");
        return set;
    }

}
