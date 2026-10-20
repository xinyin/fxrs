/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年11月23日 16:25:34
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

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.DashLayout;
import com.yzj.jep.sys.model.DashLayoutConfig;
import com.yzj.jep.sys.service.DashLayoutConfigService;
import com.yzj.jep.sys.service.DashLayoutService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/dashLayoutConfig")
public class DashLayoutConfigController extends CrudController<DashLayoutConfig> {
    private static Logger logger = LoggerFactory.getLogger(DashLayoutConfigController.class);

    @Autowired
    private DashLayoutConfigService dashLayoutConfigService;

    @Autowired
    private DashLayoutService dashLayoutService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DashLayoutConfig> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dashLayoutConfigService.query(page, filters, orders);
    }

    /**
     * 请求布局的配置信息
     *
     * @param layoutCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/getLayoutConfigData/{layoutCode}")
    @ResponseBody
    public Map<String, Object> getLayoutConfigData(@PathVariable String layoutCode, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DashLayoutConfig config = dashLayoutConfigService.get(layoutCode);
            JSONObject json = null;
            if (config != null) {
                json = JSONObject.parseObject(config.getConfig());
                map.put("version", config.getVersion());
            }
            map.put("config", json);

            DashLayout dashLayout = dashLayoutService.get(layoutCode);
            if (dashLayout != null) {
                map.put("designType", dashLayout.getDesignType());
            }
            map.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            map.put(WebCode.SUCCESS, false);
        }
        return map;
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DashLayoutConfig dashLayoutConfig) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = dashLayoutConfigService.exists(dashLayoutConfig.getLayoutCode());
        try {
            if (isExist) {
                dashLayoutConfigService.update(dashLayoutConfig);
            } else {
                dashLayoutConfigService.insert(dashLayoutConfig);
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
     * @param layoutCode
     * @return
     */
    @RequestMapping(value = "/remove/{layoutCode}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String layoutCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            dashLayoutConfigService.deleteByPrimaryKey(layoutCode);
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