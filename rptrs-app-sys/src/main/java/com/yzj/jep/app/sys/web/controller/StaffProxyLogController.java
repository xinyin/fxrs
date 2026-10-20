/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年08月07日 14:28:58
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.dto.StaffDto;
import com.yzj.jep.sys.model.StaffProxyLog;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.StaffProxyLogService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/staffProxyLog")
public class StaffProxyLogController extends CrudController<StaffProxyLog> {
    Logger logger = LoggerFactory.getLogger(StaffProxyLogController.class);

    @Autowired
    private StaffProxyLogService staffProxyLogService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private UnitService unitService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/staffProxyLogList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<StaffProxyLog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        staffProxyLogService.query(page, filters, orders);
    }

    /**
     * 用户借调部门弹出框
     *
     * @param deptCode 部门编码
     * @param model
     * @return
     */
    @RequestMapping(value = "/proxyLogDlg")
    public String proxyLogDlg(String deptCode, Model model) {

        model.addAttribute("deptCode", deptCode);

        return "sys/staffProxyLogDlg";
    }

    /**
     * 新增页面
     *
     * @param staffCode 用户编码
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(String staffCode, Model model) {

        StaffDto staff = new StaffDto();

        BeanUtils.copyProperties(staffService.get(staffCode), staff);

        Unit dept = unitService.get(staff.getDeptCode());
        if (dept != null) {
            staff.setDeptName(dept.getUnitName());
        }

        model.addAttribute("staff", staff);

        return "sys/staffProxyLogEdit";
    }

    /**
     * 保存
     *
     * @param staffProxyLog
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(StaffProxyLog staffProxyLog) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            staffProxyLogService.saveProxyLog(staffProxyLog);
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
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Map<String, Object> remove(String proxyStaffCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            List<Filter> filters = new ArrayList<Filter>();
            filters.add(new Filter("proxyStaffCode", proxyStaffCode));
            List<StaffProxyLog> proxyList = staffProxyLogService.select(filters);

            staffProxyLogService.remove(proxyList.get(0));
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }
}