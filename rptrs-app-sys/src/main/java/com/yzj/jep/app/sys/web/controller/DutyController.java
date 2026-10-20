/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年06月26日 10:56:02
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.model.RoleGroup;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/duty")
public class DutyController extends CrudController<Duty> {
    private static Logger logger = LoggerFactory.getLogger(DutyController.class);

    @Autowired
    private DutyService dutyService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private StaffService staffService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Duty> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dutyService.query(page, filters, orders);
    }

    /**
     * 保存为用户设定的角色
     *
     * @param unitCode     当前用户所在的机构编码
     * @param staffCode    当前用户编码
     * @param jsonRoleList 为用户设定的角色组json字符串
     * @return
     */
    @RequestMapping(value = "/saveRoleStaff")
    @ResponseBody
    public Map<String, Object> saveRoleStaff(String unitCode, String staffCode, String jsonRoleList) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            /*重新添加新设定的角色*/
            List<Role> roleList = JSON.parseArray(jsonRoleList, Role.class);
            Unit unit = unitService.get(unitCode);
            Staff staff = staffService.get(staffCode);
            dutyService.saveRoleList(roleList, staff, unit);


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
     * 保存为用户设定的角色组
     *
     * @param unitCode          当前用户所在的机构编码
     * @param staffCode         当前用户编码
     * @param jsonRoleGroupList 为用户设定的角色组json字符串
     * @return
     */
    @RequestMapping(value = "/saveRoleGroupStaff")
    @ResponseBody
    public Map<String, Object> saveRoleGroupStaff(String unitCode, String staffCode, String jsonRoleGroupList) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*删除之前设定的角色组*/
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter("staffCode", staffCode));
            filters.add(new Filter("dutySource", DutySource.ROLEGROUP.toString()));
            dutyService.deleteByFilter(filters);

            /*重新添加新设定的角色组*/
            List<RoleGroup> roleGroupList = JSON.parseArray(jsonRoleGroupList, RoleGroup.class);
            Unit unit = unitService.get(unitCode);
            Staff staff = staffService.get(staffCode);

            dutyService.saveRoleGroupList(roleGroupList, staff, unit);


            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

}