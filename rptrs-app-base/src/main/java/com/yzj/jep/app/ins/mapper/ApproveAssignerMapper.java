package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.sys.model.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title: ApproveAssignerMapper
 * @Author Whx
 * @Date: 2021/7/24 13:02
 * @Version 1.0
 */
@MyBatisMapper
public interface ApproveAssignerMapper {

    List<String> queryUnitAndRole(@Param("uintCode") String uintCode, @Param("roleStdCode") String roleStdCode, @Param("deptCode") String deptCode);

    List<String> queryDeptCodeByDeptName(@Param("deptName") String deptName, @Param("unitCode") String unitCode);

    String queryParentDeptCode(String deptCode);

    List<Staff> selectStaffByRoleStd(@Param("roleStdCode") String roleStdCode,@Param("tenantId") String tenantId);

    Staff selectStaffByRoleStd(@Param("roleStdCode") String roleStdCode);

    Staff selectByEmail(@Param("staffCode") String staffCode);

}
