/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年08月07日 09:01:03
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.BizDeptProxy;
import com.yzj.jep.sys.model.RoleStd;
import com.yzj.jep.sys.service.BizDeptProxyService;
import com.yzj.jep.sys.service.RoleStdService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/sys/bizDeptProxy")
public class BizDeptProxyController extends CrudController<BizDeptProxy> {
    Logger logger = LoggerFactory.getLogger(BizDeptProxyController.class);

    @Autowired
    private BizDeptProxyService bizDeptProxyService;

    @Autowired
    private RoleStdService roleStdService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/bizDeptProxyList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<BizDeptProxy> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        bizDeptProxyService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/bizDeptProxyEdit";
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

        BizDeptProxy bizDeptProxy = bizDeptProxyService.get(id);
        model.addAttribute("bizDeptProxy", bizDeptProxy);
        model.addAttribute("opt", "edit");

        return "sys/bizDeptProxyEdit";
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

        BizDeptProxy bizDeptProxy = bizDeptProxyService.get(id);
        model.addAttribute("bizDeptProxy", bizDeptProxy);

        return "sys/bizDeptProxyView";
    }

    /**
     * 保存
     *
     * @param bizDeptProxy
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(BizDeptProxy bizDeptProxy) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = bizDeptProxyService.exists(bizDeptProxy.getId());
        try {
            if (isExist) {
                bizDeptProxyService.update(bizDeptProxy);
            } else {
                bizDeptProxyService.insert(bizDeptProxy);
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
            bizDeptProxyService.deleteByPrimaryKey(id);
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
     * 验证提交的id是否已经存在数据库中
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        BizDeptProxy exist = bizDeptProxyService.get(id);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }

    /**
     * 验证提交的被代管部门和标准角色是否已经存在数据库中（相同的部门和相同的角色不能重复）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/checkRepeat", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> checkRepeat(String deptCode, String stdRoleCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        /*查询部门的标准角色是否已经存在*/
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("deptCode", Operator.EQ, deptCode));
        filters.add(new Filter("stdRoleCode", Operator.EQ, stdRoleCode));
        List<BizDeptProxy> exist = bizDeptProxyService.select(filters);
        if (CollectionUtils.isEmpty(exist)) {
            tips.put("valid", true);
        } else {
            tips.put("bizDeptProxyID", exist.get(0).getId());
            tips.put("valid", false);
        }

        return tips;
    }


    /**
     * 查询当前系统的标准角色
     *
     * @return 当前系统的标准角色
     */
    @RequestMapping(value = "/queryStdRole")
    @ResponseBody
    public List<NameValuePair> queryStdRole() {
        List<RoleStd> result = roleStdService.selectAll();
        List<NameValuePair> nameValuePairs = NVUtils.convert(result, result.size(), "roleStdCode", "roleStdName");
        return nameValuePairs;
    }
}