package com.yzj.jep.app.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.workflow.api.external.service.SysUnitService;

public class SysUnitServiceImpl implements SysUnitService {
	@Autowired
	private UnitService unitService;
	
	@Override
	public List<Unit> selectPUnitDept(String unitCode) {
		
		return unitService.selectPUnitDept(unitCode);
	}


	@Override
	public List<Unit> selectUnitDept(String unitCode) {
		
		return unitService.selectUnitDept(unitCode);
	}


	@Override
	public List<Unit> selectUnitAllOrDeptAll(String unitCode) {
		
		return unitService.selectUnitAllOrDeptAll(unitCode);
	}


	@Override
	public List<Unit> selectHeadDept(String unitCode) {
		
		return unitService.selectHeadDept(unitCode);
	}


	@Override
	public List<String> selectExistedHierarchy() {
		
		return unitService.selectExistedHierarchy();
	}


	@Override
	public List<Unit> select(List<Filter> unitFilters) {
		
		return unitService.select(unitFilters);
	}
}
