/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月25日 10:52:48
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.MetaRegion;
import com.yzj.jep.sys.model.MetaTable;
import com.yzj.jep.sys.query.utils.JdbcUtil;
import com.yzj.jep.sys.service.MetaRegionService;
import com.yzj.jep.sys.service.MetaTableService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;

@Controller
@RequestMapping("/sys/metaTable")
public class MetaTableController extends CrudController<MetaTable> {
    private static Logger logger = LoggerFactory.getLogger(MetaTableController.class);

    @Autowired
    private MetaTableService metaTableService;

    @Autowired
    private MetaRegionService metaRegionService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/metaTableList";
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/metaSynTable")
    public String metaSynTable(Model model, String regionId) {
        /*获取当前模式*/
        model.addAttribute("currentSchema", JdbcUtil.getCurrentSchema());
        model.addAttribute("regionId", regionId);

        return "sys/metaSynTable";
    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryMetatable"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryMetatable(Page<MetaTable> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        metaTableService.queryMetatable(page, filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryMetatableNoPage"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryMetatableNoPage(Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        List<MetaTable> metaTables = metaTableService.queryMetatableNoPage(filters, orders);
        result.put(RESULT_TOTAL, metaTables.size());
        result.put(RESULT_ROWS, metaTables);
        return result;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryTables"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryTables(Model model, String schema, String tableCodes) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<MetaTable> metaTables = metaTableService.queryTables(schema, tableCodes);
        result.put(RESULT_ROWS, metaTables);
        result.put(RESULT_TOTAL, metaTables.size());
        return result;
    }


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MetaTable> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        metaTableService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/metaTableEdit";
    }

    /**
     * 编辑页面
     *
     * @param tableId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{tableId}")
    public String edit(@PathVariable String tableId, Model model) {

        MetaTable metaTable = metaTableService.get(tableId);
        model.addAttribute("metaTable", metaTable);
        model.addAttribute("opt", "edit");

        return "sys/metaTableEdit";
    }

    /**
     * 查看页面
     *
     * @param tableId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{tableId}")
    public String view(@PathVariable String tableId, Model model) {

        MetaTable metaTable = metaTableService.get(tableId);
        model.addAttribute("metaTable", metaTable);

        return "sys/metaTableView";
    }

    /**
     * 保存
     *
     * @param metaTable
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MetaTable metaTable) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = metaTableService.exists(metaTable.getTableId());
        try {
            if (isExist) {

                metaTableService.update(metaTable);
            } else {
                metaTableService.insert(metaTable);
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
     * @param tableId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Map<String, Object> remove(String regionId, String tableId, boolean isDel) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /* 删除元数据表和元数据字段，先删除字段，再删除表 */
            metaTableService.delMetaTableAndDBObject(regionId, tableId, isDel);
            if (!isDel) {
                Long metaRegionTableCount = metaTableService.countTableByTableId(tableId);
                tips.put("tableCount", metaRegionTableCount);
            }
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
     * 验证提交的tableId是否已经存在数据库中
     *
     * @param tableId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String tableId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = metaTableService.exists(tableId);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 查询表字段
     *
     * @return 表字段
     */
    @RequestMapping(value = "/querySchema")
    @ResponseBody
    public List<NameValuePair> querySchema() {
        List<String> schemas = metaTableService.querySchema();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String schema : schemas) {
            nameValuePairs.add(new BasicNameValuePair(schema, schema));
        }
        return nameValuePairs;
    }

    @RequestMapping(value = "/syncCheckMetaTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> syncCheckMetaTable(String mts, String mId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            /*判断传过来的元数据表是否为空*/
            if (!StringUtils.isBlank(mts)) {
                /* 同步元数据表 */
                metaTableService.syncCheckMetaTable(mts, mId);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryMetatableExclude"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryMetatableExclude(Page<MetaTable> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        metaTableService.queryMetatable(page, filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 导出数据区域下面所有数据
     *
     * @param request
     * @param response
     * @param regionId
     */
    @RequestMapping(value = "/exportAll")
    public void exportAll(HttpServletRequest request, HttpServletResponse response, String regionId) {
        response.setContentType("text/html;charset=utf-8");
        response.setContentType("application/x-msdownload;");
        BufferedOutputStream bos = null;
        try {

            /*查询regionCode*/
            MetaRegion metaRegion = metaRegionService.get(regionId);
            String fileName = metaRegion.getRegionCode() + ".xlsx";

            /*查询导出的数据*/
            byte[] outBody = metaTableService.toXlsDataAll(regionId, fileName, metaRegion);


            response.setHeader("Content-disposition", "attachment; filename=" + fileName);

            response.setHeader("Content-Length", String.valueOf(outBody.length));
            bos = new BufferedOutputStream(response.getOutputStream());
            bos.write(outBody, 0, outBody.length);
        } catch (IOException e) {
            logger.error("下载失败：" + e.getMessage());
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                logger.error("关闭IO流缓冲失败：" + e.getMessage());
            }
        }
    }
}