package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.sys.model.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.service.RoleGroupMapService;
import com.yzj.jep.sys.service.RoleGroupService;
import com.yzj.jep.sys.service.RoleService;
import com.yzj.jep.sys.service.RoleStdService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import com.yzj.jep.web.util.RequestUtils;

/**
 * 角色Controller
 *
 * @author ZhangYan
 * @time 2017年5月24日 上午9:28:34
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends CrudController<Role> {

    Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
     * 结果中的记录总数
     */
    private static final String RESULT_TOTAL = "total";

    /**
     * 结果中的记录行数
     */
    private static final String RESULT_ROWS = "rows";

    @Autowired
    RoleService roleService;

    @Autowired
    RoleStdService roleStdService;

    @Autowired
    RoleGroupMapService roleGroupMapService;

    @Autowired
    RoleGroupService roleGroupService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Role> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        roleService.query(page, filters, orders);
    }

    /**
     * 列表页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        return "sys/roleList";
    }

    /**
     * 新增页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model, HttpServletRequest request) {
        model.addAttribute("opt", "add");
        return "sys/roleEdit";
    }

    @RequestMapping(value = "/dialog")
    public String dialog(Model model) {

        return "sys/roleDlg";
    }

    /**
     * 编辑页面
     *
     * @param code
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit/{code}")
    public String edit(@PathVariable String code, Model model, HttpServletRequest request) {

        Role role = roleService.get(code);

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("roleCode", code));
        List<RoleGroupMap> list = roleGroupMapService.select(filters);
        String roleGroupCode = "";
        if (CollectionUtils.isNotEmpty(list)) {
            for (RoleGroupMap roleGroupMap : list) {
                roleGroupCode += roleGroupMap.getRoleGroupCode() + ",";
            }
            roleGroupCode = roleGroupCode.substring(0, roleGroupCode.length() - 1);
        }

        model.addAttribute("role", role);
        model.addAttribute("roleGroupCode", roleGroupCode);
        model.addAttribute("opt", "edit");

        return "sys/roleEdit";
    }

    /**
     * 查看页面
     *
     * @param code
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/view/{code}")
    public String view(@PathVariable String code, Model model, HttpServletRequest request) {

        Role role = roleService.get(code);
        RoleStd roleStd = roleStdService.get(role.getRoleStdCode());

        /*组装该角色所在的所有角色组*/
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("roleCode", code));
        List<RoleGroupMap> list = roleGroupMapService.select(filters);
        String roleGroupName = "";
        if (CollectionUtils.isNotEmpty(list)) {
            for (RoleGroupMap roleGroupMap : list) {
                String name = roleGroupService.get(roleGroupMap.getRoleGroupCode()).getRoleGroupName();
                roleGroupName += name + ", ";
            }
            roleGroupName = roleGroupName.substring(0, roleGroupName.length() - 2);
        }

        model.addAttribute("role", role);
        model.addAttribute("roleStdName", roleStd.getRoleStdName());
        model.addAttribute("roleGroupName", roleGroupName);

        return "sys/roleView";
    }

    /**
     * 保存
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Role role, String roleGroupCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            roleService.saveRole(role, roleGroupCode);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
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
     * @param code
     * @return
     */
    @RequestMapping(value = "/remove/{code}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String code) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            roleService.removeRole(code);
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
     * 验证提交的roleCode是否已经存在数据库中
     *
     * @param roleCode
     * @return
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public Map<String, Object> check(String roleCode, HttpServletRequest request) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Role exist = roleService.get(roleCode);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }
        return tips;
    }


    /**
     * 查询角色包含的用户
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryRoleStaff")
    @ResponseBody
    public Map<String, Object> queryRoleStaff(Page<Staff> page, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();

        List<Filter> filters = RequestUtils.buildFilters(request);
        String roleCode = (String) filters.get(0).getValue();

        PageHelper.start(page);
        List<Staff> list = roleService.queryRoleStaff(roleCode);
        PageHelper.end(page, list);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }


    /**
     * 以特殊格式获取某一层级的所有角色
     *
     * @return json格式返回
     */
    @RequestMapping(value = "/queryAll")
    @ResponseBody
    public List<NameValuePair> queryAll(String hierarchy) {

        List<Filter> filters = new ArrayList<Filter>();
        if (StringUtils.isNotEmpty(hierarchy)) {
            filters.add(new Filter("hierarchy", hierarchy));
        }
        List<Role> result = roleService.select(filters);

        return NVUtils.convert(result, result.size(), "roleCode", "roleName");
    }

    /**
     * 以特殊格式获取所有用户组类型
     *
     * @return json格式返回
     */
    @RequestMapping(value = "/queryAlls")
    @ResponseBody
    public List<NameValuePair> queryAlls() {

        List<Role> result = roleService.selectAll();

        return NVUtils.convert(result, result.size(), "roleCode", "roleName");
    }

    /**
     * 根据角色组编码查询角色信息
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryByRoleGroupCode")
    @ResponseBody
    public Map<String, Object> queryRole(Page<Role> page, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();

        List<Filter> filters = RequestUtils.buildFilters(request);
        String roleGroupCode = (String) filters.get(0).getValue();

        PageHelper.start(page);
        List<Role> list = roleService.queryRoleByRoleGroup(roleGroupCode);
        PageHelper.end(page, list);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 查询用户信息
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryRoleList")
    @ResponseBody
    public Map<String, Object> queryRoleList(Page<Role> page, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        PageHelper.start(page);
        List<Staff> list = roleService.queryRoleStaff(null);
        PageHelper.end(page, list);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 弹出框-选择人员页面
     * @return 列表页面
     */
    @RequestMapping(value = "/roleDlgs")
    public String staffDlg(Model model) {

        return "sys/roleDlgs";
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
    public Map<String, Object> queryStaff(Page<Role> page, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();

        List<Filter> filters = RequestUtils.buildFilters(request);

        PageHelper.start(page);
        List<Role> list = roleService.select(filters);
        PageHelper.end(page, list);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }


}
