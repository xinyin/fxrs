/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月05日 15:57:31
 *
 */

package com.yzj.jep.app.sys.service.impl;

import com.yzj.jep.app.sys.dto.UserAuthItem;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.core.code.Status;
import com.yzj.jep.core.code.YesNo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.sys.code.*;
import com.yzj.jep.sys.dto.StaffDto;
import com.yzj.jep.sys.model.*;
import com.yzj.jep.sys.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    //private final static String INIT_PASSWORD = "123456";
    private final static String INIT_PASSWORD = "Abcd1234@";

    @Autowired
    private StaffService staffService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private BizDeptStaffService bizDeptStaffService;

    @Autowired
    private StaffGroupMapService staffGroupMapService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private UserService userService;

    @Autowired
    private TenantService tenantService;


    /**
     * 根据User，填充UserProfile数据
     */
    @Override
    public UserProfile getUserProfileByUser(User user, String secondedStaff) throws ServiceException {

        String staffCode = user.getUserId();

        if (!StringUtils.isEmpty(secondedStaff)) {
            staffCode = secondedStaff;
        }

        //员工信息
        Staff staff = staffService.get(staffCode);
        //所属机构
        String org = staff.getUnitCode();
        //所属部门
        String dept = staff.getDeptCode();

        UserProfile userProfile = new UserProfile();

        //userid 主键   32位uuid
        userProfile.setUserid(user.getUserId());
        //staffCode 员工编码
        userProfile.setUsername(staff.getStaffCode());
        userProfile.setStaffCode(staff.getStaffCode());
        //员工名称
        userProfile.setStaffName(staff.getStaffName());

        //租户ID，modified by michael
        userProfile.setTenantId(staff.getTenantId());

        //所属机构部门代码
        userProfile.setDeptCode(dept);

        userProfile.setDeptName(staff.getDeptName());

        userProfile.setUnitCode(org);

        userProfile.setUnitName(staff.getUnitName());

        Unit unit = unitService.get(dept);
        userProfile.setDeptBizType(unit.getBizTypeCode());

        //所属机构显示名称
//		userProfile.setOwnerDisplayName(org != null ? org.getDisplayName() : null);
        //用户所在机构(ORG)的层级
        userProfile.setUnitHierarchy(org != null ? unit.getHierarchy() : null);

        userProfile.setTelphone(staff.getTelephone());
        userProfile.setMobile(staff.getMobile());
        userProfile.setTenantId(staff.getTenantId());
        userProfile.setEmail(staff.getEmail());

        /*设置当前登录用户拥有的数据权限*/
        List<Authorization> authorizations = authorizationService.selectPermitAuthorization4StaffCode(staffCode);

        Map<String, List<UserAuthItem>> authItems = new HashMap<>();
        /*循环组装对应的数据权限*/
        for (Authorization authorization : authorizations) {
            String menuCode = authorization.getMenuCode();

            /*组装一个数据权限*/
            UserAuthItem uai = new UserAuthItem();
            uai.setKind(ResourceKind.PERMIT);
            uai.setResourceCode(authorization.getResourceCode());
            uai.setResourceData(menuCode);

            if (authItems.containsKey(menuCode)) {
                authItems.get(menuCode).add(uai);
            } else {
                List<UserAuthItem> uais = new ArrayList<>();
                uais.add(uai);
                authItems.put(menuCode, uais);
            }
        }
        userProfile.setAuthItems(authItems);

        return userProfile;
    }

    @Override
    @Transactional
    public boolean changePWD(UserProfile userProfile, String newPassword) {
        if (userProfile != null && !StringUtils.isEmpty(newPassword)) {
            User user = userService.get(userProfile.getStaffCode());
            ByteSource salt = new SimpleByteSource(user.getUserId());//使用userId（平台中和staffCode保持一致）
            Object md5Password = new Md5Hash(newPassword, salt, 1);

            user.setPassword(md5Password.toString());

            userService.update(user);
            return true;
        }

        return false;
    }

    @Override
    public int saveUser(Staff staff) {

        /*保存完用户后，初始化用户登录信息 根据用户数据，同步登录用户表数据. 只有新增用户的时候才执行此方法*/
        //对初始密码数据进行加密操作
        ByteSource salt = new SimpleByteSource(staff.getStaffCode());
        Object md5Password = new Md5Hash(INIT_PASSWORD, salt, 1);

        User user = new User();
        user.setUserId(staff.getStaffCode());
        user.setUserName(staff.getStaffName());
        user.setTenantId(staff.getTenantId());
        user.setPassword(md5Password.toString());
        user.setAccountNonExpired(1);
        user.setAccountNonLocked(0);
        user.setCredentialNonExpired(1);
        user.setEnabled(1);

        //TODO 	其他初始化数据，根据需要再进行添加
        userService.insert(user);

        return 0;
    }

    @Override
    public int saveStaff(StaffDto staff) {

        /*当所属部门为业务部门时，添加用户信息到人员条线表中*/
        Unit dept = unitService.get(staff.getDeptCode());
        if (YesNo.YES.toString().equals(dept.getIsBizDept())) {
            bizDeptStaffService.saveStaff(staff);
        }

        /*设置所属机构（unitCode）*/
        staff.setDeptName(dept.getUnitName());
        staff.setUnitCode(dept.getOwner_());
        staff.setUnitName(unitService.get(staff.getDeptCode()).getUnitName());
        staffService.insert(staff);

        staffGroupMapService.saveStaffGroupList(staff);
        dutyService.saveRoleByStaff(staff, dept);
        dutyService.saveRoleByStaff(staff, dept);

        /*创建一个登录用户*/
        this.saveUser(staff);

        return 0;
    }

    @Override
    public void insertStaffAdm(StaffDto staff, Tenant tenant) {
        //新增机构
        Unit unit = new Unit();
        unit.setUnitCode(tenant.getCode());
        unit.setUnitName(tenant.getName());
        unit.setHierarchy(UnitHierarchy.HEAD.getValue());
        unit.setKind(UnitKind.UNIT.getValue());
        unit.setStatus(UnitStatus.OPEN.getValue());
        unit.setIsBizDept(YesNo.NO.getValue());
//        unit.setOwner_(tenant.getCode());
        unit.setPathCode("." + tenant.getCode() + ".");
        unit.setPathName(tenant.getName());
        unit.setTenantId(tenant.getCode());
        unitService.insert(unit);

        //保存租户
        tenant.setStatus(Status.DRAFT.getValue());
        tenantService.insert(tenant);

        /*设置所属机构（unitCode）*/
        staff.setUnitCode(unit.getUnitCode());
        staff.setUnitName(unit.getUnitName());
        staff.setDeptCode(unit.getUnitCode());
        staff.setDeptName(unit.getUnitName());
        staff.setTenantId(tenant.getCode());
        staff.setStatus(StaffStatus.ENABLE.getValue());
        staff.setStaffType(StaffType1.ADMIN.getValue());
        staff.setStaffType2(StaffType.BACK.getValue());
        staff.setGender(Gender.MAN.getValue());
        staffService.insert(staff);

        /*创建一个登录用户*/
        saveUser(staff);
    }

    @Override
    public void updateStaffAdm(StaffDto staff, Tenant tenant) {
        //新增机构
        Unit unit = unitService.get(tenant.getCode());
        unit.setUnitName(tenant.getName());
        unitService.update(unit);
        //保存租户
        tenantService.update(tenant);

        //修改员工
        Staff model = staffService.get(staff.getStaffCode());
        model.setStaffName(staff.getStaffName());
        staffService.update(model);

        //修改登录用户
        User user = userService.get(staff.getStaffCode());
        user.setUserName(staff.getStaffName());
        userService.update(user);
    }

    public static void main(String[] args) {
        //对初始密码数据进行加密操作
        ByteSource salt = new SimpleByteSource("PFWAdmin001");
            Object md5Password = new Md5Hash("5w8aIrl&$8pA.L1", salt, 1);
            System.out.println(md5Password);
    }

}