package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.RoleGroup;
import com.yzj.jep.sys.service.RoleGroupService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

/**
 * 角色组Controller
 *
 * @author ZhangYan
 * @time 2017年5月31日 上午10:16:47
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/roleGroup")
public class RoleGroupController extends CrudController<RoleGroup> {

    Logger logger = LoggerFactory.getLogger(RoleGroupController.class);

    @Autowired
    RoleGroupService roleGroupService;

    /**
     * 列表页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        return "sys/roleGroupList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RoleGroup> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        roleGroupService.query(page, filters, orders);
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
        return "sys/roleGroupEdit";
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

        RoleGroup roleGroup = roleGroupService.get(code);
        model.addAttribute("roleGroup", roleGroup);
        model.addAttribute("opt", "edit");

        return "sys/roleGroupEdit";
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

        RoleGroup roleGroup = roleGroupService.get(code);
        model.addAttribute("roleGroup", roleGroup);

        return "sys/roleGroupView";
    }

    /**
     * 保存
     *
     * @param roleGroup
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RoleGroup roleGroup) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            RoleGroup exist = roleGroupService.get(roleGroup.getRoleGroupCode());
            if (exist == null) {
                roleGroupService.insert(roleGroup);
            } else {
                roleGroupService.update(roleGroup);
            }

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
            this.roleGroupService.removeRoleGroup(code);
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
     * 验证提交的roleGroupCode是否已经存在数据库中
     *
     * @param roleGroupCode
     * @return
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public Map<String, Object> check(String roleGroupCode, HttpServletRequest request) {

        Map<String, Object> tips = new HashMap<String, Object>();

        RoleGroup exist = roleGroupService.get(roleGroupCode);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }
        return tips;
    }

    /**
     * 以特殊格式获取所有角色组类型
     *
     * @return json格式返回
     */
    @RequestMapping(value = "/queryAll")
    @ResponseBody
    public List<NameValuePair> queryAll(String hierarchy) {

        List<Filter> filters = new ArrayList<Filter>();
        if (StringUtils.isNotBlank(hierarchy)) {
            filters.add(new Filter("hierarchy", hierarchy));
        }
        List<RoleGroup> result = roleGroupService.select(filters);

        return NVUtils.convert(result, result.size(), "roleGroupCode", "roleGroupName");
    }
}
