/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年06月29日 15:41:26
 *
 */

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
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.StaffGroup;
import com.yzj.jep.sys.model.StaffGroupMap;
import com.yzj.jep.sys.service.StaffGroupMapService;
import com.yzj.jep.sys.service.StaffGroupService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;

@Controller
@RequestMapping("/sys/staffGroupMap")
public class StaffGroupMapController extends CrudController<StaffGroupMap> {
    private static Logger logger = LoggerFactory.getLogger(StaffGroupMapController.class);

    @Autowired
    private StaffGroupMapService staffGroupMapService;

    @Autowired
    private StaffGroupService staffGroupService;

    @Autowired
    private StaffService staffService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<StaffGroupMap> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        staffGroupMapService.query(page, filters, orders);
    }

    /**
     * 页面跳转，用户组页面
     *
     * @param staffCode 当前用户编码
     * @param model
     * @return
     */
    @RequestMapping(value = "/setStaffGroup")
    public String setStaffGroup(String staffCode, Model model) {

        Staff staff = staffService.get(staffCode);
        model.addAttribute("staff", staff);

        /*组装用户关联的用户组*/
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("staffCode", staffCode));
        List<StaffGroupMap> list = staffGroupMapService.select(filters);

        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        model.addAttribute("staffGroups", json);

        return "sys/staffSetStaffGroup";
    }

    /**
     * 保存，用户设定的用户组
     *
     * @param staffGroupMap
     * @return
     */
    @RequestMapping(value = "/saveStaffGroup")
    @ResponseBody
    public Map<String, Object> saveStaffGroup(String staffCode, String jsonData) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*添加新设定的用户*/
            List<StaffGroup> list = JSON.parseArray(jsonData, StaffGroup.class);
            staffGroupMapService.save(list, staffCode);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 页面跳转，用户选择页面
     *
     * @param staffGroupCode 用户组编码
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/setStaffMap")
    public String setRoleMap(String staffGroupCode, Model model, HttpServletRequest request) {

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("staffGroupCode", staffGroupCode));
        List<StaffGroupMap> list = staffGroupMapService.select(filters);

        JSONArray json = JSONArray.parseArray(JSON.toJSONString(list));
        StaffGroup staffGroup = staffGroupService.get(staffGroupCode);

        model.addAttribute("staffList", json);
        model.addAttribute("staffGroup", staffGroup);

        return "sys/staffGroupSetStaff";
    }

    /**
     * 保存，用户组设定的用户
     *
     * @param staffGroupCode 用户组编码
     * @param jsonStaffList  用户列
     * @return
     */
    @RequestMapping(value = "/saveStaffMap")
    @ResponseBody
    public Map<String, Object> saveStaffMap(String staffGroupCode, String jsonStaffList) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*添加关联*/
            List<Staff> list = JSON.parseArray(jsonStaffList, Staff.class);
            staffGroupMapService.saveStaffMap(list, staffGroupCode);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

    /**
     * 根据用户组编码获取用户列表信息
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
        String staffGroupCode = (String) filters.get(0).getValue();

        PageHelper.start(page);
        List<Staff> list = staffGroupMapService.queryStaff(staffGroupCode);
        PageHelper.end(page, list);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

}