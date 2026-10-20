package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.model.RoleGroup;
import com.yzj.jep.sys.model.RoleGroupMap;
import com.yzj.jep.sys.service.RoleGroupMapService;
import com.yzj.jep.sys.service.RoleGroupService;
import com.yzj.jep.sys.service.RoleService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

/**
 * 角色组关联的角色Controller
 *
 * @author ZhangYan
 * @time 2017年5月31日 下午2:31:50
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/roleGroupMap")
public class RoleGroupMapController extends CrudController<RoleGroupMap> {

    Logger logger = LoggerFactory.getLogger(RoleGroupMapController.class);

    @Autowired
    RoleGroupMapService roleGroupMapService;

    @Autowired
    RoleGroupService roleGroupService;

    @Autowired
    RoleService roleService;

    @Override
    protected void prepareQuery(Page<RoleGroupMap> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        roleGroupMapService.query(page, filters, orders);
    }

    /**
     * 返回角色组选择角色的页面
     *
     * @param roleGroupCode
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/setRoleMap")
    public String setRoleMap(String roleGroupCode, String hierarchy, Model model, HttpServletRequest request) {

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("roleGroupCode", roleGroupCode));
        List<RoleGroupMap> list = roleGroupMapService.select(filters);
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        RoleGroup roleGroup = roleGroupService.get(roleGroupCode);


        model.addAttribute("roleCodes", json);
        model.addAttribute("roleGroup", roleGroup);
        model.addAttribute("hierarchy", hierarchy);

        return "sys/roleGroupSetRole";
    }

    /**
     * 保存 - 角色组添加的角色
     *
     * @param roleGroupCode 角色组编码
     * @param jsonRoleList  被选中的角色列表JSON字符串
     * @return
     */
    @RequestMapping(value = "/saveRoleMap")
    @ResponseBody
    public Map<String, Object> saveRoleMap(String roleGroupCode, String jsonRoleList) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*删除已有的关联*/
            List<Filter> filters = new ArrayList<Filter>();
            filters.add(new Filter("roleGroupCode", roleGroupCode));
            roleGroupMapService.deleteByFilter(filters);

            /*添加新增的关联*/
            List<Role> list = JSON.parseArray(jsonRoleList, Role.class);
            roleGroupMapService.saveRoleMap(list, roleGroupCode);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

}
