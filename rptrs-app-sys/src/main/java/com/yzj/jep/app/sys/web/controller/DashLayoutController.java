/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年11月17日 10:08:50
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

import com.yzj.jep.sys.code.DashLayoutDesignType;
import com.yzj.jep.sys.model.DashLayout;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.sys.service.DashLayoutService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/sys/dashLayout")
public class DashLayoutController extends CrudController<DashLayout> {
    private static Logger logger = LoggerFactory.getLogger(DashLayoutController.class);

    @Autowired
    private DashLayoutService dashLayoutService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dashLayoutList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DashLayout> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(Order.desc("priority"));
        dashLayoutService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/dashLayoutEdit";
    }

    /**
     * 编辑页面
     *
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{code}")
    public String edit(@PathVariable String code, Model model) {

        DashLayout dashLayout = dashLayoutService.get(code);
        model.addAttribute("dashLayout", dashLayout);
        model.addAttribute("opt", "edit");

        return "sys/dashLayoutEdit";
    }

    /**
     * 查看页面
     *
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{code}")
    public String view(@PathVariable String code, Model model) {

        DashLayout dashLayout = dashLayoutService.get(code);
        dashLayout.setDesignType(DashLayoutDesignType.getLabel(dashLayout.getDesignType()));
        model.addAttribute("dashLayout", dashLayout);

        return "sys/dashLayoutView";
    }

    /**
     * 专家设计模式页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/professional")
    public String professional(Model model) {
        return "sys/dashLayoutDesignProfessional";
    }

    /**
     * 保存
     *
     * @param dashLayout
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DashLayout dashLayout) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = dashLayoutService.exists(dashLayout.getCode());
        try {
            if (isExist) {

                dashLayoutService.update(dashLayout);
            } else {
                dashLayoutService.insert(dashLayout);
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
     * @param code
     * @return
     */
    @RequestMapping(value = "/remove/{code}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String code) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            dashLayoutService.deleteByPrimaryKey(code);
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
     * 验证提交的code是否已经存在数据库中
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String code) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = dashLayoutService.exists(code);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<NameValuePair> getAll() {
        List<DashLayout> dashLayouts = new ArrayList<DashLayout>();
        try {
            dashLayouts = dashLayoutService.selectAll();
            DashLayout dashLayout = new DashLayout();
            dashLayout.setCode("_RESET_DELETE_");
            dashLayout.setName("[取消关联，重置为空]");
            dashLayouts.add(dashLayout);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return NVUtils.convert(dashLayouts, dashLayouts.size(), "code", "name");
    }

}