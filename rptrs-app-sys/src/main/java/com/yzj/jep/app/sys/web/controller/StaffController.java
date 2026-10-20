/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017-06-22 14:56:56
 *
 */

package com.yzj.jep.app.sys.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.code.NotifyConfigResult;
import com.yzj.jep.sys.dto.StaffDto;
import com.yzj.jep.sys.model.*;
import com.yzj.jep.sys.service.*;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/sys/staff")
public class StaffController extends CrudController<StaffDto> {
    Logger logger = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    StaffService staffService;

    @Autowired
    StaffGroupMapService staffGroupMapService;

    @Autowired
    StaffGroupService staffGroupService;

    @Autowired
    DutyService dutyService;

    @Autowired
    StaffRoleGroupMapService staffRoleGroupMapService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleGroupService roleGroupService;

    @Autowired
    UnitService unitService;

    @Autowired
    UserService userService;

    @Autowired
    BizDeptStaffService bizDeptStaffService;

    @Autowired
    UserProfileService userLoginService;

    @Autowired
    private NotifyConfigService notifyConfigService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "sys/staffList";
    }

    @RequestMapping(value = "/ceshilist")
    public String ceshilist(Model model) {
        return "ex/ceshiList";
    }

    /**
     * 用户所属机构弹出框
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/dialog")
    public String dialog(Model model, Boolean mult) {
        model.addAttribute("mult", mult);
        return "sys/staffDlg";
    }

    /**
     * 打开更改用户所属机构页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/unitDlg")
    public String unitDlg(Model model) {
        return "sys/staffUnitDlg";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<StaffDto> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        Page<StaffDto> result = staffService.selectStaffDto(page, filters, orders);
        for (StaffDto staff : result.getResult()) {
            String roleName = combineRoleName(staff.getStaffCode());
            staff.setRoleName(roleName);

            String staffGroupName = combineStaffGroupName(staff.getStaffCode());
            staff.setStaffGroupName(staffGroupName);
        }
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(String deptCode, Model model) {

        StaffDto staff = new StaffDto();

        Unit dept = unitService.get(deptCode);
        if (dept != null) {
            staff.setDeptCode(deptCode);
            staff.setDeptName(dept.getUnitName());
            model.addAttribute("hierarchy", dept.getHierarchy());
        }

        model.addAttribute("staff", staff);

        return "sys/staffEdit";
    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/selectStaff/query"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> selectStaff(Page<Staff> page, Model model, HttpServletRequest request, String deptCode, String roleCodes) {
        Map<String, Object> result = new HashMap<String, Object>();
        staffService.selectStaffByRolesAndDept(page, deptCode, roleCodes);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }

    /**
     * 编辑页面
     *
     * @param staffCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{staffCode}")
    public String edit(@PathVariable String staffCode, Model model) {

        StaffDto staff = new StaffDto();

        BeanUtils.copyProperties(staffService.get(staffCode), staff);

        /*组装该用户所在的部门名称*/
        Unit dept = unitService.get(staff.getDeptCode());
        if (dept != null) {
            staff.setDeptName(dept.getUnitName());
            model.addAttribute("hierarchy", dept.getHierarchy());
        }

        /*组装该用户所在的所有用户组*/
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("staffCode", staffCode));
        List<StaffGroupMap> list = staffGroupMapService.select(filters);
        String staffGroupCode = "";
        if (CollectionUtils.isNotEmpty(list)) {
            for (StaffGroupMap staffGroupMap : list) {
                staffGroupCode += staffGroupMap.getStaffGroupCode() + ",";
            }
            staffGroupCode = staffGroupCode.substring(0, staffGroupCode.length() - 1);
        }
        staff.setStaffGroupCode(staffGroupCode);

        /*组装该用户所在的所有角色*/
        List<Filter> roleFilters = new ArrayList<Filter>();
        roleFilters.add(new Filter("staffCode", staffCode));
        roleFilters.add(new Filter("dutySource", DutySource.ROLE.toString()));
        List<Duty> dutyList = dutyService.select(roleFilters);

        String roleCode = "";
        for (Duty duty : dutyList) {
            roleCode += duty.getPositionCode().split("@")[0] + ",";
        }
        if (StringUtils.isNotBlank(roleCode)) {
            roleCode = roleCode.substring(0, roleCode.length() - 1);
        }
        staff.setRoleCode(roleCode);

        /*组装该用户所在的所有角色组*/
        List<Filter> roleGroupFilters = new ArrayList<Filter>();
        roleGroupFilters.add(new Filter("staffCode", staffCode));
        List<StaffRoleGroupMap> mapList = staffRoleGroupMapService.select(roleGroupFilters);

        String roleGroupCode = "";
        for (StaffRoleGroupMap map : mapList) {
            roleGroupCode += map.getRoleGroupCode() + ",";
        }
        if (StringUtils.isNotBlank(roleGroupCode)) {
            roleGroupCode = roleGroupCode.substring(0, roleGroupCode.length() - 1);
        }
        staff.setRoleGroupCode(roleGroupCode);

        /*判断是否为借调人, 是借调人其基本信息不可编辑*/
        String editable = "Y";
        if (StringUtils.isNotBlank(staff.getProxyStaffCode())) {
            editable = "N";
        }

        model.addAttribute("staff", staff);
        model.addAttribute("opt", "edit");
        model.addAttribute("editable", editable);

        return "sys/staffEdit";
    }

    /**
     * 查看页面
     *
     * @param staffCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{staffCode}")
    public String view(@PathVariable String staffCode, Model model) {

        StaffDto staff = new StaffDto();

        BeanUtils.copyProperties(staffService.get(staffCode), staff);


        /*组装该用户所在的部门名称*/
        Unit dept = unitService.get(staff.getDeptCode());
        if (dept != null) {
            staff.setDeptName(dept.getUnitName());
        }

        /*组装该用户所在的用户组*/
        staff.setStaffGroupName(combineStaffGroupName(staffCode));

        /*组装该用户所拥有的角色*/
        staff.setRoleName(combineRoleName(staffCode));

        /*组装该用户所拥有的角色组*/
        List<Filter> roleGroupFilters = new ArrayList<Filter>();
        roleGroupFilters.add(new Filter("staffCode", staffCode));
        List<StaffRoleGroupMap> mapList = staffRoleGroupMapService.select(roleGroupFilters);

        if (CollectionUtils.isNotEmpty(mapList)) {
            String roleGroupName = "";
            for (StaffRoleGroupMap map : mapList) {
                RoleGroup roleGroup = roleGroupService.get(map.getRoleGroupCode());
                roleGroupName += roleGroup.getRoleGroupName() + ", ";
            }
            roleGroupName = roleGroupName.substring(0, roleGroupName.length() - 2);
            staff.setRoleGroupName(roleGroupName);
        }

        model.addAttribute("staff", staff);

        return "sys/staffView";
    }

    /**
     * 组装该用户所在的用户组
     *
     * @param staffCode 当前用户编码
     * @return
     */
    protected String combineStaffGroupName(String staffCode) {
        String staffGroupName = "";

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("staffCode", staffCode));
        List<StaffGroupMap> list = staffGroupMapService.select(filters);

        if (CollectionUtils.isNotEmpty(list)) {
            for (StaffGroupMap staffGroupMap : list) {
                String name = staffGroupService.get(staffGroupMap.getStaffGroupCode()).getStaffGroupName();
                staffGroupName += name + ", ";
            }
            staffGroupName = staffGroupName.substring(0, staffGroupName.length() - 2);
        }

        return staffGroupName;
    }

    /**
     * 组装该用户所拥有的角色名称
     *
     * @param staffCode 当前用户编码
     * @return 角色名称组
     */
    protected String combineRoleName(String staffCode) {
        String roleName = "";

        List<Role> rolelist = dutyService.getStaffRoleList(staffCode, null);
        if (CollectionUtils.isNotEmpty(rolelist)) {
            for (Role role : rolelist) {
                roleName += role.getRoleName() + ", ";
            }
            roleName = roleName.substring(0, roleName.length() - 2);
        }

        return roleName;
    }

    /**
     * 保存
     *
     * @param staff
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(StaffDto staff) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(staff);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        try {
            Boolean isExist = staffService.exists(staff.getStaffCode());
            if (isExist) {
                staffService.updateStaff(staff);
            } else {
                userLoginService.saveStaff(staff);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            //配置消息配置功能-审批流程通知
            try {
                if (StringUtils.isNotBlank(staff.getEmail())) {
                    NotifyConfig notifyConfig = new NotifyConfig();
                    notifyConfig.setStaffCode(staff.getStaffCode());
                    notifyConfig = notifyConfigService.get(notifyConfig);
                    if (notifyConfig != null) {
                        String isReceiveEmail = notifyConfig.getIsReceiveEmail();
                        if (StringUtils.isBlank(isReceiveEmail) || isReceiveEmail.equals(NotifyConfigResult.REFUSE.getValue())) {
                            notifyConfig.setStaffCode(staff.getStaffCode());
                            notifyConfig.setStaffName(staff.getStaffName());
                            notifyConfig.setStaffEmail(staff.getEmail());
                            notifyConfig.setEmailReceiveType("ANNOUNCE,TODO,REMIND,MESSAGE");
                            notifyConfig.setIsReceiveEmail(NotifyConfigResult.RECEIVE.getValue());
                            notifyConfig.setIsReceiveRemind(NotifyConfigResult.RECEIVE.getValue());
                            notifyConfig.setIsReceiveMsg(NotifyConfigResult.RECEIVE.getValue());
                            notifyConfig.setInboxRetentionTime("30");
                            notifyConfig.setTrashRetentionTime("30");
                            //notifyConfigService.updateSelective(notifyConfig);
                        }
                    } else {
                        NotifyConfig config = new NotifyConfig();
                        config.setId(UUID.randomUUID().toString());
                        config.setStaffCode(staff.getStaffCode());
                        config.setStaffName(staff.getStaffName());
                        config.setStaffEmail(staff.getEmail());
                        config.setEmailReceiveType("ANNOUNCE,TODO,REMIND,MESSAGE");
                        config.setIsReceiveEmail(NotifyConfigResult.RECEIVE.getValue());
                        config.setIsReceiveRemind(NotifyConfigResult.RECEIVE.getValue());
                        config.setIsReceiveMsg(NotifyConfigResult.RECEIVE.getValue());
                        config.setInboxRetentionTime("30");
                        config.setTrashRetentionTime("30");
                        //notifyConfigService.insertSelective(config);
                    }
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 删除
     *
     * @param staffCode
     * @return
     */
    @RequestMapping(value = "/remove/{staffCode}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String staffCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            staffService.deleteStaff(staffCode);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    /**
     * 验证提交的用户编码是否已经存在数据库中
     *
     * @param staffCode 用户编码
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String staffCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Staff exist = staffService.get(staffCode);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }

    /**
     * 页面跳转，为用户设置角色
     *
     * @param unitCode
     * @param staffCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/setRoleStaff")
    public String setRoleStaff(String unitCode, String staffCode, Model model) {

        Unit unit = unitService.get(unitCode);
        if (unit != null) {
            model.addAttribute("hierarchy", unit.getHierarchy());
        }
        model.addAttribute("unitCode", unitCode);
        Staff staff = staffService.get(staffCode);
        model.addAttribute("staff", staff);

        List<Role> list = dutyService.getStaffRoleList(staffCode, DutySource.ROLE.toString());
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        model.addAttribute("roleList", json);

        return "sys/staffSetRole";
    }

    /**
     * 页面跳转，为用户设置角色组
     *
     * @param unitCode
     * @param staffCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/setRoleGroupStaff")
    public String setRoleGroupStaff(String unitCode, String staffCode, Model model) {

        Unit unit = unitService.get(unitCode);
        if (unit != null) {
            model.addAttribute("hierarchy", unit.getHierarchy());
        }
        model.addAttribute("unitCode", unitCode);

        Staff staff = staffService.get(staffCode);
        model.addAttribute("staff", staff);

        List<RoleGroup> list = staffRoleGroupMapService.getStaffRoleGroupList(staffCode);
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        model.addAttribute("roleGroups", json);

        return "sys/staffSetRoleGroup";
    }

    /**
     * 根据以“,”分割的用户编码查询，对应的用户
     *
     * @param staffCodes
     * @return
     */
    @RequestMapping(value = "/selectStaffByCodes/{staffCodes}")
    @ResponseBody
    public List<NameValuePair> selectStaffByCodes(@PathVariable String staffCodes) {
        if (StringUtils.isBlank(staffCodes)) {
            return null;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("staffCode", Operator.IN, staffCodes.split(",")));
        List<Staff> staffs = staffService.select(filters);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Staff staff : staffs) {
            nameValuePairs.add(new BasicNameValuePair(staff.getStaffName(), staff.getStaffCode()));
        }
        return nameValuePairs;

    }

    /**
     * 弹出框-选择人员页面
     * @return 列表页面
     */
    @RequestMapping(value = "/staffDlg1")
    public String staffDlg(Model model) {

        return "sys/staffDlgs";
    }


    /**
     * 查询角色包含的用户
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryStaff")
    @ResponseBody
    public Map<String, Object> queryStaff(Page<Staff> page, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();

        List<Filter> filters = RequestUtils.buildFilters(request);

        PageHelper.start(page);
        List<Staff> list = staffService.select(filters);
        PageHelper.end(page, list);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }



}
