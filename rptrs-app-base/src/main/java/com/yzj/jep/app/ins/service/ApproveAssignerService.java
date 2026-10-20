package com.yzj.jep.app.ins.service;

import com.yzj.jep.sys.model.Staff;

import java.util.List;

/**
 * @title: 查询对应岗位审批人
 * @Author Whx
 * @Date: 2021/7/24 12:56
 * @Version 1.0
 */
public interface ApproveAssignerService {

    /**
     * 根据机构和角色类型查询审批人
     */
    List<String> queryUnitAndRole(String uintCode, String roleStdCode, String deptCode);

    List<String> queryDeptCodeByDeptName(String deptName, String unitCode);

    /**
     * 判断是否是总部的二级部门
     */
    Boolean isHdSecondDept(String deptCode);

    List<Staff> selectStaffByRoleStd(String roleStdCode,String tenantId);

    //查询邮箱
    Staff selectStaffByEmail(String staffCode);

}
