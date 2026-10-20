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
import com.yzj.jep.sys.model.MetaRelation;
import com.yzj.jep.sys.service.MetaRelationService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/metaRelation")
public class MetaRelationController extends CrudController<MetaRelation> {
    private static Logger logger = LoggerFactory.getLogger(MetaRelationController.class);

    @Autowired
    private MetaRelationService metaRelationService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/metaRelationList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MetaRelation> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        metaRelationService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/metaRelationEdit";
    }

    /**
     * 编辑页面
     *
     * @param relationId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{relationId}")
    public String edit(@PathVariable String relationId, Model model) {

        MetaRelation metaRelation = metaRelationService.get(relationId);
        model.addAttribute("metaRelation", metaRelation);
        model.addAttribute("opt", "edit");

        return "sys/metaRelationEdit";
    }

    /**
     * 查看页面
     *
     * @param relationId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{relationId}")
    public String view(@PathVariable String relationId, Model model) {

        MetaRelation metaRelation = metaRelationService.get(relationId);
        model.addAttribute("metaRelation", metaRelation);

        return "sys/metaRelationView";
    }

    /**
     * 保存
     *
     * @param metaRelation
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MetaRelation metaRelation) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = metaRelationService.exists(metaRelation.getRelationId());
        try {
            if (isExist) {

                metaRelationService.update(metaRelation);
            } else {
                metaRelationService.insert(metaRelation);
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
     * @param relationId
     * @return
     */
    @RequestMapping(value = "/remove/{relationId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String relationId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            metaRelationService.deleteByPrimaryKey(relationId);
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
     * 验证提交的relationId是否已经存在数据库中
     *
     * @param relationId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String relationId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = metaRelationService.exists(relationId);
        tips.put("valid", !exist);

        return tips;
    }
}