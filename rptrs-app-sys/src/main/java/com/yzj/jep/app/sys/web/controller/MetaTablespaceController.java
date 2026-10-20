/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月25日 10:52:49
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
import com.yzj.jep.sys.model.MetaTablespace;
import com.yzj.jep.sys.service.MetaTablespaceService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/metaTablespace")
public class MetaTablespaceController extends CrudController<MetaTablespace> {
    private static Logger logger = LoggerFactory.getLogger(MetaTablespaceController.class);

    @Autowired
    private MetaTablespaceService metaTablespaceService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/metaTablespaceList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MetaTablespace> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        metaTablespaceService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/metaTablespaceEdit";
    }

    /**
     * 编辑页面
     *
     * @param tablespaceId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{tablespaceId}")
    public String edit(@PathVariable String tablespaceId, Model model) {

        MetaTablespace metaTablespace = metaTablespaceService.get(tablespaceId);
        model.addAttribute("metaTablespace", metaTablespace);
        model.addAttribute("opt", "edit");

        return "sys/metaTablespaceEdit";
    }

    /**
     * 查看页面
     *
     * @param tablespaceId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{tablespaceId}")
    public String view(@PathVariable String tablespaceId, Model model) {

        MetaTablespace metaTablespace = metaTablespaceService.get(tablespaceId);
        model.addAttribute("metaTablespace", metaTablespace);

        return "sys/metaTablespaceView";
    }

    /**
     * 保存
     *
     * @param metaTablespace
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MetaTablespace metaTablespace) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = metaTablespaceService.exists(metaTablespace.getTablespaceId());
        try {
            if (isExist) {

                metaTablespaceService.update(metaTablespace);
            } else {
                metaTablespaceService.insert(metaTablespace);
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
     * @param tablespaceId
     * @return
     */
    @RequestMapping(value = "/remove/{tablespaceId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String tablespaceId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            metaTablespaceService.deleteByPrimaryKey(tablespaceId);
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
     * 验证提交的tablespaceId是否已经存在数据库中
     *
     * @param tablespaceId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String tablespaceId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = metaTablespaceService.exists(tablespaceId);
        tips.put("valid", !exist);

        return tips;
    }
}