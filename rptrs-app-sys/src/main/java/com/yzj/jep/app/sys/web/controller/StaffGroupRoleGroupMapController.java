/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月13日 16:53:17
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
import com.yzj.jep.sys.model.RoleGroup;
import com.yzj.jep.sys.model.StaffGroup;
import com.yzj.jep.sys.model.StaffGroupRoleGroupMap;
import com.yzj.jep.sys.service.StaffGroupRoleGroupMapService;
import com.yzj.jep.sys.service.StaffGroupService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/staffGroupRoleGroupMap")
public class StaffGroupRoleGroupMapController extends CrudController<StaffGroupRoleGroupMap> {
    Logger logger = LoggerFactory.getLogger(StaffGroupRoleGroupMapController.class);

    @Autowired
    private StaffGroupRoleGroupMapService mapService;

    @Autowired
    private StaffGroupService staffGroupService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<StaffGroupRoleGroupMap> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        mapService.query(page, filters, orders);
    }

    /**
     * 页面跳转，为用户组设置角色组
     *
     * @param unitCode
     * @param staffCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/setRoleGroupMap")
    public String setRoleGroupMap(String staffGroupCode, Model model) {

        StaffGroup staffGroup = staffGroupService.get(staffGroupCode);
        model.addAttribute("staffGroup", staffGroup);

        List<RoleGroup> list = mapService.getRoleGroupList(staffGroupCode);
        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        model.addAttribute("roleGroups", json);

        return "sys/staffGroupSetRoleGroup";
    }

    /**
     * 保存
     *
     * @param staffGroupRoleGroupMap
     * @return
     */
    @RequestMapping(value = "/saveRoleGroup")
    @ResponseBody
    public Map<String, Object> saveRoleGroup(String staffGroupCode, String jsonRoleGroupList) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*重新添加新设定的角色*/
            StaffGroup staffGroup = staffGroupService.get(staffGroupCode);
            List<RoleGroup> list = JSON.parseArray(jsonRoleGroupList, RoleGroup.class);
            mapService.saveRoleGroup(staffGroup, list);

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