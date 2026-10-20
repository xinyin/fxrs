/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月13日 16:57:49
 *
 */

package com.yzj.jep.app.sys.web.controller;

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
import com.yzj.jep.sys.model.StaffGroup;
import com.yzj.jep.sys.model.StaffGroupRoleMap;
import com.yzj.jep.sys.service.StaffGroupRoleMapService;
import com.yzj.jep.sys.service.StaffGroupService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/staffGroupRoleMap")
public class StaffGroupRoleMapController extends CrudController<StaffGroupRoleMap> {
    Logger logger = LoggerFactory.getLogger(StaffGroupRoleMapController.class);

    @Autowired
    private StaffGroupRoleMapService mapService;

    @Autowired
    private StaffGroupService staffGroupService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<StaffGroupRoleMap> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        mapService.query(page, filters, orders);
    }

    /**
     * 页面跳转，为用户组设置角色
     *
     * @param staffGroupCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/setRoleMap")
    public String setRoleMap(String staffGroupCode, Model model) {

        StaffGroup staffGroup = staffGroupService.get(staffGroupCode);
        model.addAttribute("staffGroup", staffGroup);

        List<Role> list = mapService.getRoleList(staffGroupCode);
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));

        model.addAttribute("roleList", json);

        return "sys/staffGroupSetRole";
    }

    /**
     * 保存
     *
     * @param staffGroupRoleMap
     * @return
     */
    @RequestMapping(value = "/saveRole")
    @ResponseBody
    public Map<String, Object> saveRole(String staffGroupCode, String jsonRoleList) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*重新添加新设定的角色*/
            StaffGroup staffGroup = staffGroupService.get(staffGroupCode);
            List<Role> list = JSON.parseArray(jsonRoleList, Role.class);
            mapService.saveRole(staffGroup, list);

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