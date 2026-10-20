/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2018 yzj.com. All rights reserved.
 *
 * Generated at 2018年02月28日 22:36:01
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.code.Status;
import com.yzj.jep.core.context.Context;
import com.yzj.jep.core.context.IdentityContext;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.dto.StaffDto;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.Tenant;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.TenantService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/tenant")
public class TenantController extends CrudController<Tenant> {
    private static Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    private TenantService tenantService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private StaffService staffService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/tenantList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Tenant> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        tenantService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/tenantEdit";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable String id, Model model) {

        Tenant tenant = tenantService.get(id);
        List<Staff> staffs = staffService.select(new Filter.Builder().eq("tenantId", tenant.getCode()).build());

        model.addAttribute("tenant", tenant);
        model.addAttribute("staff", staffs.get(0));
        model.addAttribute("opt", "edit");

        return "sys/tenantEdit";
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, Model model) {

        Tenant tenant = tenantService.get(id);
        model.addAttribute("tenant", tenant);

        return "sys/tenantView";
    }

    /**
     * 保存
     *
     * @param tenant
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Tenant tenant, StaffDto staff) {
        //修改context中tenantRequired为 false
        IdentityContext context = Context.getIdentityContext();
        context.setTenantRequired(false);

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = tenantService.exists(tenant.getId());
        try {
            if (isExist) {
                userProfileService.updateStaffAdm(staff, tenant);
            } else {
                userProfileService.insertStaffAdm(staff, tenant);
            }

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
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            tenantService.deleteTenant(id);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    /**
     * 查询所有租户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public List<Tenant> queryList(Model model) {
        List<Tenant> tenants = tenantService.select(new Filter.Builder().eq("status", Status.ENABLED.getValue()).build());
        return tenants;
    }

    /**
     * 启动租户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/start/{id}")
    @ResponseBody
    public Map<String, Object> start(@PathVariable String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            Tenant tenant = tenantService.get(id);
            tenant.setStatus(Status.ENABLED.getValue());
            tenantService.update(tenant);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 停止租户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop/{id}")
    @ResponseBody
    public Map<String, Object> stop(@PathVariable String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            Tenant tenant = tenantService.get(id);
            tenant.setStatus(Status.ENABLED.getValue());
            tenantService.update(tenant);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 验证提交的code是否已经存在数据库中
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String code) {

        Map<String, Object> tips = new HashMap<String, Object>();
        List<Tenant> list = tenantService.select(new Filter.Builder().eq("code", code).build());
        if (list.size() > 0) {
            tips.put("valid", false);
        } else {
            tips.put("valid", true);
        }

        return tips;
    }
}