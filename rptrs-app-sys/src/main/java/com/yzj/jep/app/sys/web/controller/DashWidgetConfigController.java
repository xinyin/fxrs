/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年11月22日 10:21:39
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.DashWidgetConfig;
import com.yzj.jep.sys.service.DashWidgetConfigService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/dashWidgetConfig")
public class DashWidgetConfigController extends CrudController<DashWidgetConfig> {
    private static Logger logger = LoggerFactory.getLogger(DashWidgetConfigController.class);

    @Autowired
    private DashWidgetConfigService dashWidgetConfigService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dashWidgetConfigList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DashWidgetConfig> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dashWidgetConfigService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/dashWidgetConfigEdit";
    }

    /**
     * 编辑页面
     *
     * @param widgetCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{widgetCode}")
    public String edit(@PathVariable String widgetCode, Model model) {

        DashWidgetConfig dashWidgetConfig = dashWidgetConfigService.get(widgetCode);
        model.addAttribute("dashWidgetConfig", dashWidgetConfig);
        model.addAttribute("opt", "edit");

        return "sys/dashWidgetConfigEdit";
    }

    /**
     * 查看页面
     *
     * @param widgetCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{widgetCode}")
    public String view(@PathVariable String widgetCode, Model model) {

        DashWidgetConfig dashWidgetConfig = dashWidgetConfigService.get(widgetCode);
        model.addAttribute("dashWidgetConfig", dashWidgetConfig);

        return "sys/dashWidgetConfigView";
    }

    /**
     * 保存
     *
     * @param dashWidgetConfig
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DashWidgetConfig dashWidgetConfig) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = dashWidgetConfigService.exists(dashWidgetConfig.getWidgetCode());
        try {
            if (isExist) {

                dashWidgetConfigService.update(dashWidgetConfig);
            } else {
                dashWidgetConfigService.insert(dashWidgetConfig);
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
     * @param widgetCode
     * @return
     */
    @RequestMapping(value = "/remove/{widgetCode}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String widgetCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            dashWidgetConfigService.deleteByPrimaryKey(widgetCode);
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
     * 验证提交的widgetCode是否已经存在数据库中
     *
     * @param widgetCode
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String widgetCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = dashWidgetConfigService.exists(widgetCode);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 根据组件编码查询出组件设计
     *
     * @param widgetCode
     * @return
     */
    @RequestMapping("/getWidgetConfigByWidgetCode")
    @ResponseBody
    public Map<String, Object> getWidgetConfigByWidgetCode(String widgetCode) {
        Map<String, Object> data = new HashMap<String, Object>();
        DashWidgetConfig dashWidgetConfig = dashWidgetConfigService.get(widgetCode);
        if (dashWidgetConfig != null) {
            data.put("dashWidgetConfig", dashWidgetConfig);
            data.put(WebCode.SUCCESS, true);
        } else {
            data.put(WebCode.SUCCESS, false);
        }
        return data;
    }


}