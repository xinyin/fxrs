/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月01日 14:17:53
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
import com.yzj.jep.sys.model.DashLayoutMap;
import com.yzj.jep.sys.model.DashLayoutRoleGroupMap;
import com.yzj.jep.sys.model.DashLayoutRoleMap;
import com.yzj.jep.sys.service.DashLayoutMapService;
import com.yzj.jep.sys.service.IDashLayoutRoleGroupMapService;
import com.yzj.jep.sys.service.IDashLayoutRoleMapService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/dashLayoutMap")
public class DashLayoutMapController extends CrudController<DashLayoutMap> {
    private static Logger logger = LoggerFactory.getLogger(DashLayoutMapController.class);

    @Autowired
    private DashLayoutMapService dashLayoutMapService;

    @Autowired
    private IDashLayoutRoleGroupMapService dashLayoutRoleGroupMapService;

    @Autowired
    private IDashLayoutRoleMapService dashLayoutRoleMapService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dashLayoutMapList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DashLayoutMap> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dashLayoutMapService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/dashLayoutMapEdit";
    }

    /**
     * 编辑页面
     *
     * @param mapId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{mapId}")
    public String edit(@PathVariable String mapId, Model model) {

        DashLayoutMap dashLayoutMap = dashLayoutMapService.get(mapId);
        model.addAttribute("dashLayoutMap", dashLayoutMap);
        model.addAttribute("opt", "edit");

        return "sys/dashLayoutMapEdit";
    }

    /**
     * 查看页面
     *
     * @param mapId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{mapId}")
    public String view(@PathVariable String mapId, Model model) {

        DashLayoutMap dashLayoutMap = dashLayoutMapService.get(mapId);
        model.addAttribute("dashLayoutMap", dashLayoutMap);

        return "sys/dashLayoutMapView";
    }

    /**
     * 保存
     *
     * @param dashLayoutMap
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DashLayoutMap dashLayoutMap) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = dashLayoutMapService.exists(dashLayoutMap.getMapId());
        try {
            if (isExist) {

                dashLayoutMapService.update(dashLayoutMap);
            } else {
                dashLayoutMapService.insert(dashLayoutMap);
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
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/remove/{mapId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String mapId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            dashLayoutMapService.deleteByPrimaryKey(mapId);
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
     * 验证提交的mapId是否已经存在数据库中
     *
     * @param mapId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String mapId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = dashLayoutMapService.exists(mapId);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 分页查询角色组授权数据
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchDashLayoutRoleGroupMap")
    @ResponseBody
    public Map<String, Object> searchDashLayoutRoleGroupMap(Page<DashLayoutRoleGroupMap> page, Model model, HttpServletRequest request, DashLayoutRoleGroupMap dashLayoutRoleGroupMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<DashLayoutRoleGroupMap> pages = dashLayoutRoleGroupMapService.searchDashLayoutRoleGroupMap(page, dashLayoutRoleGroupMap);
        map.put(RESULT_TOTAL, pages.getTotal());
        map.put(RESULT_ROWS, pages.getResult());
        return map;
    }

    /**
     * 查看角色组布局授权
     *
     * @return
     */
    @RequestMapping("/viewRoleGroppMap")
    public String viewRoleGroppMap() {
        return "sys/dashLayoutMapView";
    }

    /**
     * 显示角色组布局设置窗口
     *
     * @param dashLayoutRoleGroupMap
     * @param model
     * @return
     */
    @RequestMapping("/roleGroupEdit")
    public String roleGroupEdit(DashLayoutRoleGroupMap dashLayoutRoleGroupMap, Model model) {
        model.addAttribute("dashLayoutRoleGroupMap", dashLayoutRoleGroupMap);
        return "sys/dashLayoutMapEdit";
    }


    /**
     * 角色组授权设置界面数据保存
     *
     * @param dashLayoutRoleGroupMap
     * @return
     */
    @RequestMapping("/roleGroupSave")
    @ResponseBody
    public Map<String, Object> roleGroupSave(DashLayoutRoleGroupMap dashLayoutRoleGroupMap) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            dashLayoutRoleGroupMapService.saveDashLayoutRoleGroupMap(dashLayoutRoleGroupMap);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return data;
    }


    /**
     * 分页查询角色授权数据
     *
     * @param page
     * @param model
     * @param request
     * @param dashLayoutRoleGroupMap
     * @return
     */
    @RequestMapping(value = "/searchDashLayoutRoleMap")
    @ResponseBody
    public Map<String, Object> searchDashLayoutRoleMap(Page<DashLayoutRoleMap> page, Model model, DashLayoutRoleMap dashLayoutRoleMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<DashLayoutRoleMap> pages = dashLayoutRoleMapService.searchDashLayoutRoleMap(page, dashLayoutRoleMap);
        map.put(RESULT_TOTAL, pages.getTotal());
        map.put(RESULT_ROWS, pages.getResult());
        return map;
    }

    /**
     * 进入角色布局映射查看页面
     *
     * @return
     */
    @RequestMapping("/viewRoleMap")
    public String viewRoleMap() {
        return "sys/dashLayoutRoleMapView";
    }

    @RequestMapping("/dashLayoutRoleMapEdit")
    public String dashLayoutRoleMapEdit(DashLayoutRoleMap dashLayoutRoleMap, Model model) {
        model.addAttribute("dashLayoutRoleMap", dashLayoutRoleMap);
        return "sys/dashLayoutRoleMapEdit";
    }


    /**
     * 角色授权数据保存
     *
     * @param dashLayoutRoleMap
     * @param model
     * @return
     */
    @RequestMapping("/roleLayoutMapSave")
    @ResponseBody
    public Map<String, Object> roleLayoutMapSave(DashLayoutRoleMap dashLayoutRoleMap, Model model) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            dashLayoutRoleMapService.saveLayoutRoleMap(dashLayoutRoleMap);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return data;
    }
}