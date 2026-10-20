package com.yzj.jep.app.util;

import com.yzj.jep.app.enums.InsRoleStaff;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Authorization;
import com.yzj.jep.sys.model.Menu;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorityUtil {
    @Autowired
    private DutyService dutyService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;

    /**
     * 根据用户编号和菜单路径，获取用户对应此菜单的功能权限
     *
     * @param staffCode
     * @param url
     * @return
     */
    public String buttonPerm(String staffCode, String url) {
        dutyService = SpringContextHolder.getBean(DutyService.class);
        authorizationService = SpringContextHolder.getBean(AuthorizationService.class);
        menuService = SpringContextHolder.getBean(MenuService.class);
        // 查找角色编码
        List<Role> rolelist = dutyService.getStaffRoleList(staffCode, null);
        // 通过路径查找菜单编码
        List<Filter> urlfilters = new Filter.Builder().eq("url", url).build();
        List<Menu> menuList = menuService.select(urlfilters);
        // 查找已经给角色授权的功能权限
        String code = "";
        for (Role role : rolelist) {
            for (Menu menu : menuList) {
                List<Filter> filters = new Filter.Builder().eq("auzTarget", role.getRoleCode())
                        .eq("menuCode", menu.getMenuCode())
                        .eq("kind", "FP").build();
                List<Authorization> authorizations = authorizationService.select(filters);
                for (Authorization auth : authorizations) {
                    code += auth.getResourceCode() + ",";
                }
            }
        }
        return code;
    }

    public boolean isEditAndView() {
        dutyService = SpringContextHolder.getBean(DutyService.class);
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        // 查找角色编码
        List<Role> rolelist = dutyService.getStaffRoleList(staffCode, null);
        List<String> roleStrlist = new ArrayList<>();
        for (Role role : rolelist) {
            roleStrlist.add(role.getRoleCode());
        }
        if (roleStrlist.contains("30530") || roleStrlist.contains("30540")) {//关联交易管理经办人/负责人 查看全部中再数据
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据当前用户角色添加交易类数据权限
     *
     * @param filters
     * @return
     */
    public List<Filter> getFilterByRole(List<Filter> filters) {
        //角色映射 负责人-经办人
        Map<String, String> insRole = new HashMap<>();
        insRole.put(InsRoleStaff.A_30750.getCode(), InsRoleStaff.A_30750.getDesc());
        insRole.put(InsRoleStaff.A_30760.getCode(), InsRoleStaff.A_30760.getDesc());
        insRole.put(InsRoleStaff.A_30780.getCode(), InsRoleStaff.A_30780.getDesc());
        insRole.put(InsRoleStaff.A_30800.getCode(), InsRoleStaff.A_30800.getDesc());
        insRole.put(InsRoleStaff.A_30820.getCode(), InsRoleStaff.A_30820.getDesc());
        insRole.put(InsRoleStaff.A_30850.getCode(), InsRoleStaff.A_30850.getDesc());
        insRole.put(InsRoleStaff.A_30860.getCode(), InsRoleStaff.A_30860.getDesc());
        insRole.put(InsRoleStaff.A_30880.getCode(), InsRoleStaff.A_30880.getDesc());
        insRole.put(InsRoleStaff.A_30900.getCode(), InsRoleStaff.A_30900.getDesc());
        insRole.put(InsRoleStaff.A_30920.getCode(), InsRoleStaff.A_30920.getDesc());
        insRole.put(InsRoleStaff.A_30940.getCode(), InsRoleStaff.A_30940.getDesc());
        insRole.put(InsRoleStaff.A_30960.getCode(), InsRoleStaff.A_30960.getDesc());
        insRole.put(InsRoleStaff.A_30980.getCode(), InsRoleStaff.A_30980.getDesc());
        insRole.put(InsRoleStaff.A_301000.getCode(), InsRoleStaff.A_301000.getDesc());
        insRole.put(InsRoleStaff.A_301020.getCode(), InsRoleStaff.A_301020.getDesc());

        dutyService = SpringContextHolder.getBean(DutyService.class);
        roleService = SpringContextHolder.getBean(RoleService.class);
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        // 查找角色编码
        List<Role> rolelist = dutyService.getStaffRoleList(staffCode, null);
        List<String> roleStrlist = new ArrayList<>();

        for (Role role : rolelist) {
            roleStrlist.add(role.getRoleCode());
        }
        if (roleStrlist.contains("30530") || roleStrlist.contains("30540") || roleStrlist.contains("30580")
                || roleStrlist.contains("30550") || roleStrlist.contains("30560") || roleStrlist.contains("30570")
                || roleStrlist.contains("30590")) {//关联交易管理经办人/负责人 查看全部中再数据
            return filters;
        }
        String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        if ("0001".equals(unitCode)) {
            for (String s : roleStrlist) {//判断用户是不是负责人
                //是负责人查看本角色下所有数据
                if (InsRoleStaff.All_Charge.getDesc().contains(s)) {
                    String role = insRole.get(s);
                    String[] roles = role.split(",");
                    //根据编码查出角色下人员列表
                    List<String> staffStrList = new ArrayList<>();
                    for (String r : roles) {
                        List<Staff> list = roleService.queryRoleStaff(r);
                        for (Staff staff : list) {
                            staffStrList.add(staff.getStaffCode());
                        }
                    }
                    staffStrList.add("contract");
                    filters.add(Filter.in("amendId", staffStrList));

                } else {
                    List<String> staffStrList = new ArrayList<>();
                    staffStrList.add("contract");
                    staffStrList.add(staffCode);
                    filters.add(Filter.in("amendId", staffStrList));
                }

            }
        }
        //管户机构
        if (!"0001".equals(unitCode) && !"0001-ZZHK".equals(unitCode)) {
            filters.add(Filter.in("status", new String[]{"8", "9"}));
        }


        //else if(roleStrlist.contains("30510") || roleStrlist.contains("30520")){//承办部门负责人/综合岗 查看本部门数据
//            AuthorityUtil authorityUtil = new AuthorityUtil();
//            List<String> staffStrList = getStaffCodeListByStaff();
//            //合同抽取得数据所有人都能查到
//            staffStrList.add("contract");
//            filters.add(Filter.in("amendId", staffStrList));
//        }else if(roleStrlist.contains("30500")){//承办部门经办人 查看本人数据
//            List<String> staffStrList = new ArrayList<>();
//            staffStrList.add("contract");
//            staffStrList.add(staffCode);
//            filters.add(Filter.in("amendId", staffStrList));
//        }
//        String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
//        if ("0001".equals(unitCode)){
//        //角色名称
//        for (String roleName: roleNames) {
//            if(roleName.indexOf("负责人") != -1){//如果角色为负责人  查询自己和经办人的所有数据
//                roleName = roleName.substring(0,roleName.length()-3);
//                //查出角色编码集合
//                List<Filter> filterList = new Filter.Builder().like("roleName",roleName).build();
//                List<Role> roles = roleService.select(filterList);
////
//
//            }
//        }
//        }
        return filters;
    }

    /**
     * 根据登录用户查询登录用户部门全部员工
     *
     * @return
     */
    public List<String> getStaffCodeListByStaff() {
        staffService = SpringContextHolder.getBean(StaffService.class);
        String depCode = UserProfileUtils.getUserProfile().getDeptCode();
        List<Filter> staffFilters = new Filter.Builder().eq("deptCode", depCode).build();
        List<Staff> staffList = staffService.select(staffFilters);
        List<String> staffStrList = new ArrayList<>();
        for (Staff staff : staffList) {
            staffStrList.add(staff.getStaffCode());
        }
        return staffStrList;
    }

    /**
     * 查询用户是否只有经办人或者审核人2中角色类型
     *
     * @return
     */
    public boolean getRoleType() {
        dutyService = SpringContextHolder.getBean(DutyService.class);
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        // 查找角色编码
        List<Role> rolelist = dutyService.getStaffRoleList(staffCode, null);
        for (Role role : rolelist) {
            if (!role.getRoleName().contains("经办人") && !role.getRoleName().contains("审核人")) {
                return true;
            }
        }
        return false;
    }
}
