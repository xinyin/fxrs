package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.ins.mapper.ApproveAssignerMapper;
import com.yzj.jep.app.ins.service.ApproveAssignerService;
import com.yzj.jep.sys.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @title: ApproveAssignerServiceImpl
 * @Author Whx
 * @Date: 2021/7/24 13:01
 * @Version 1.0
 */
@Service
public class ApproveAssignerServiceImpl implements ApproveAssignerService {

    @Autowired
    private ApproveAssignerMapper approveAssignerMapper;

    @Override
    public List<String> queryUnitAndRole(String uintCode, String roleStdCode, String deptCode) {
        List<String> list = approveAssignerMapper.queryUnitAndRole(uintCode, roleStdCode, deptCode);
        return list;
    }

    @Override
    public List<String> queryDeptCodeByDeptName(String deptName, String unitCode) {
        List<String> list = approveAssignerMapper.queryDeptCodeByDeptName(deptName, unitCode);
        return list;
    }

    @Override
    public Boolean isHdSecondDept(String deptCode) {
        //查询该部门的父部门信息
        String parentDeptCode = approveAssignerMapper.queryParentDeptCode(deptCode);
        if (StrUtil.isNotBlank(parentDeptCode) && !"8600".equals(parentDeptCode)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Staff> selectStaffByRoleStd(String roleStdCode,String tenantId) {
        List<Staff> staffList = approveAssignerMapper.selectStaffByRoleStd(roleStdCode,tenantId);
        return staffList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(p -> p.getStaffCode() + ";" + p.getDeptCode()))), ArrayList::new));
    }

    @Override
    public Staff selectStaffByEmail(String staffCode) {
        return approveAssignerMapper.selectByEmail(staffCode);
    }
}
