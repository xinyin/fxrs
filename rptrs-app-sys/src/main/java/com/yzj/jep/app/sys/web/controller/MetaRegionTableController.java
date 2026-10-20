/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月25日 10:52:48
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
import com.yzj.jep.sys.dto.MetaRegionTableNameDto;
import com.yzj.jep.sys.model.MetaRegionTable;
import com.yzj.jep.sys.service.MetaRegionTableService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;

@Controller
@RequestMapping("/sys/metaRegionTable")
public class MetaRegionTableController extends CrudController<MetaRegionTable> {
    private static Logger logger = LoggerFactory.getLogger(MetaRegionTableController.class);

    @Autowired
    private MetaRegionTableService metaRegionTableService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/metaRegionTableList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MetaRegionTable> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        metaRegionTableService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/metaRegionTableEdit";
    }

    /**
     * 编辑页面
     *
     * @param regionTableId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{regionTableId}")
    public String edit(@PathVariable String regionTableId, Model model) {

        MetaRegionTable metaRegionTable = metaRegionTableService.get(regionTableId);
        model.addAttribute("metaRegionTable", metaRegionTable);
        model.addAttribute("opt", "edit");

        return "sys/metaRegionTableEdit";
    }

    /**
     * 查看页面
     *
     * @param regionTableId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{regionTableId}")
    public String view(@PathVariable String regionTableId, Model model) {

        MetaRegionTable metaRegionTable = metaRegionTableService.get(regionTableId);
        model.addAttribute("metaRegionTable", metaRegionTable);

        return "sys/metaRegionTableView";
    }

    /**
     * 保存
     *
     * @param metaRegionTable
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MetaRegionTable metaRegionTable) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = metaRegionTableService.exists(metaRegionTable.getRegionTableId());
        try {
            if (isExist) {

                metaRegionTableService.update(metaRegionTable);
            } else {
                metaRegionTableService.insert(metaRegionTable);
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
     * @param regionTableId
     * @return
     */
    @RequestMapping(value = "/remove/{regionTableId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String regionTableId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            metaRegionTableService.deleteByPrimaryKey(regionTableId);
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
     * 验证提交的regionTableId是否已经存在数据库中
     *
     * @param regionTableId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String regionTableId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = metaRegionTableService.exists(regionTableId);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 通过数据区域ID查询该区域ID对应的metaTable
     *
     * @param regionId 数据区域ID
     * @return
     */
    @RequestMapping(value = "/queryBusinessMetaTabe")
    @ResponseBody
    public Map<String, Object> queryBusinessMetaTabe(Model model, HttpServletRequest request, String roleCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        List<MetaRegionTableNameDto> metaTables = metaRegionTableService.queryBusinessMetaTabe(filters, orders, roleCode);
        result.put(RESULT_TOTAL, metaTables.size());
        result.put(RESULT_ROWS, metaTables);
        return result;
    }
}