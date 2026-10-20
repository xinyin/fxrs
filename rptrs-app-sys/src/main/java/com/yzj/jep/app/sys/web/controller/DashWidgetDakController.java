/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年11月29日 15:28:40
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

import com.yzj.jep.app.sys.dto.PermitTypeDto;
import com.yzj.jep.core.code.PermitType;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.DashWidget;
import com.yzj.jep.sys.model.DashWidgetDak;
import com.yzj.jep.sys.service.DashWidgetDakService;
import com.yzj.jep.sys.service.DashWidgetService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

/**
 * 组件授权控制器
 *
 * @author zy
 * @time 2017年12月18日 上午9:25:56
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/dashWidgetDak")
public class DashWidgetDakController extends CrudController<DashWidgetDak> {
    private static Logger logger = LoggerFactory.getLogger(DashWidgetDakController.class);

    @Autowired
    private DashWidgetDakService dashWidgetDakService;

    @Autowired
    private DashWidgetService dashWidgetService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dashWidgetDakList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DashWidgetDak> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dashWidgetDakService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/dashWidgetDakEdit";
    }

    /**
     * 保存
     *
     * @param dashWidgetDak
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DashWidgetDak dashWidgetDak) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = dashWidgetDakService.exists(dashWidgetDak.getDakId());
        try {
            if (isExist) {
                dashWidgetDakService.update(dashWidgetDak);
            } else {
                dashWidgetDakService.insert(dashWidgetDak);
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
     * 获取数据权限数据
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/permitTypes")
    @ResponseBody
    public Map<String, Object> permitTypes() throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<PermitTypeDto> items = new ArrayList<PermitTypeDto>();

        for (PermitType permitType : PermitType.values()) {
            items.add(new PermitTypeDto(permitType.getName(), permitType.getCode(), null));
        }
        data.put("rows", items);
        return data;
    }

    /**
     * 获取选中的组件数据
     *
     * @param dashWidgetDak
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getWidgetData")
    @ResponseBody
    public List<DashWidget> getWidgetData(DashWidgetDak dashWidgetDak) throws Exception {
        List<DashWidget> items = new ArrayList<DashWidget>();

        List<DashWidgetDak> resultList = dashWidgetDakService.select(new Filter.Builder()
                .eq("dakUsingCode", dashWidgetDak.getDakUsingCode())
                .eq("dakMapType", dashWidgetDak.getDakMapType())
                .build());

        for (DashWidgetDak result : resultList) {
            items.add(dashWidgetService.get(result.getWidgetCode()));
        }

        return items;
    }

    /**
     * 获取选中的数据权限数据
     *
     * @param dashWidgetDak
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPermitData")
    @ResponseBody
    public Map<String, Object> getPermitData(DashWidgetDak dashWidgetDak) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("dakId", null);
        List<PermitTypeDto> items = new ArrayList<PermitTypeDto>();

        List<DashWidgetDak> resultList = dashWidgetDakService.select(new Filter.Builder()
                .eq("widgetCode", dashWidgetDak.getWidgetCode())
                .eq("dakUsingCode", dashWidgetDak.getDakUsingCode())
                .eq("dakMapType", dashWidgetDak.getDakMapType())
                .build());

        for (DashWidgetDak result : resultList) {
            for (PermitType permitType : PermitType.values()) {
                if (result.getDakCode().equals(permitType.getCode())) {
                    data.put("dakId", result.getDakId());
                    items.add(new PermitTypeDto(permitType.getName(), permitType.getCode(), true));
                }
            }
        }
        data.put("permitType", items);
        return data;
    }
}