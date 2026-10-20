package com.yzj.jep.app.workflow.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.RoleService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.workflow.api.external.service.SysUserService;

public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private StaffService staffService;
	
	@Autowired
	private DutyService dutyService;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public List<Staff> selectStaffByPositions(Set<String> positions) {
		
		return staffService.selectStaffByPositions(positions);
	}


	@Override
	public Staff getStaff(String staffCode) {
		
		return staffService.get(staffCode);
	}


	@Override
	public List<Staff> selectStaff(List<Filter> filters) {
		
		return staffService.select(filters);
	}

	@Override
	public List<Duty> selectDuty(Duty params) {
		
		return dutyService.select(params);
	}
	
	@Override
	public List<Role> selectRolesByStdcodeAndUnitCode(String unitCodes, String roleStdCodes) {
		
		return roleService.selectRolesByStdcodeAndUnitCode(unitCodes, roleStdCodes);
	}

	@Override
	public List<Role> selectRole(List<Filter> filters) {
		
		return roleService.select(filters);
	}
}
