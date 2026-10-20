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

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.dto.MetaColumnDto;
import com.yzj.jep.sys.model.MetaColumn;
import com.yzj.jep.sys.service.MetaColumnService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/metaColumn")
public class MetaColumnController extends CrudController<MetaColumn> {
    private static Logger logger = LoggerFactory.getLogger(MetaColumnController.class);

    @Autowired
    private MetaColumnService metaColumnService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model, String tableId) {
        model.addAttribute("tableId", tableId);
        return "sys/metaColumnList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MetaColumn> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        metaColumnService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/metaColumnEdit";
    }

    /**
     * 编辑页面
     *
     * @param columnId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{columnId}")
    public String edit(@PathVariable String columnId, Model model) {

        MetaColumn metaColumn = metaColumnService.get(columnId);
        model.addAttribute("metaColumn", metaColumn);
        model.addAttribute("opt", "edit");

        return "sys/metaColumnEdit";
    }

    /**
     * 查看页面
     *
     * @param columnId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{columnId}")
    public String view(@PathVariable String columnId, Model model) {

        MetaColumn metaColumn = metaColumnService.get(columnId);
        model.addAttribute("metaColumn", metaColumn);

        return "sys/metaColumnView";
    }

    /**
     * 保存
     *
     * @param metaColumn
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MetaColumn metaColumn) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = metaColumnService.exists(metaColumn.getColumnId());
        try {
            if (isExist) {

                metaColumnService.update(metaColumn);
            } else {
                metaColumnService.insert(metaColumn);
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
     * @param columnId
     * @return
     */
    @RequestMapping(value = "/remove/{columnId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String columnId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            metaColumnService.deleteByPrimaryKey(columnId);
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
     * 验证提交的columnId是否已经存在数据库中
     *
     * @param columnId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String columnId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = metaColumnService.exists(columnId);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 根据tableId查询出列
     *
     * @param tables
     * @param filter
     * @return
     */
    @RequestMapping(value = "/getAllColumns")
    @ResponseBody
    public List<MetaColumnDto> getAllColumns(String tables, String filter) {
        return metaColumnService.queryMetaColumnsByTableId(tables, filter);
    }

    /**
     * 获取主表关联字段
     *
     * @param tableId
     * @return
     */
    @RequestMapping(value = "/getRelationColumns")
    @ResponseBody
    public List<MetaColumnDto> getRelationColumns(String tableId) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        return metaColumnService.getRelationTablesByTableId(tableId, profile.getStaffCode());
    }

    /**
     * 根据tableId查询metaColumnDTO
     *
     * @param tableId tableId
     * @param isBase  是否是元数据列信息查询
     * @return
     */
    @RequestMapping(value = "/queryMetaColumnDto")
    @ResponseBody
    public List<MetaColumnDto> queryMetaColumnDto(String tableId) {
        return metaColumnService.queryMetaColumnDtoByTableId(tableId);
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/chooseMastertable")
    public String chooseMastertable(Model model, String tableId) {
        model.addAttribute("tableId", tableId);
        return "sys/metaMastertableChooseDlg";
    }

    /**
     * 同步列信息
     *
     * @param metaColumnList
     * @return
     */
    @RequestMapping(value = "/updateMetaColumn")
    @ResponseBody
    public Map<String, Object> updateMetaColumn(@RequestParam(value = "mcList") String metaColumnList, String tableId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        /*同步元数据列信息 */
        metaColumnService.updateMetaColumn(metaColumnList, tableId);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    /**
     * 同步元数据列信息
     *
     * @param tableId 元数据表ID
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String, Object> updateColumn(String tableId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        metaColumnService.updateMetaColumnByTableId(tableId);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        return tips;
    }

}