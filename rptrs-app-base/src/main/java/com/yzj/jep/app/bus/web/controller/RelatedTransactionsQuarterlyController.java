/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:42
 *
 */

package com.yzj.jep.app.bus.web.controller;

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

import com.yzj.jep.app.bus.model.RelatedTransactionsQuarterly;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.RelatedTransactionsQuarterlyService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/relatedTransactionsQuarterly")
public class RelatedTransactionsQuarterlyController extends CrudController<RelatedTransactionsQuarterly> {
    private static Logger logger = LoggerFactory.getLogger(RelatedTransactionsQuarterlyController.class);

    @Autowired
    private RelatedTransactionsQuarterlyService relatedTransactionsQuarterlyService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/relatedTransactionsQuarterlyList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelatedTransactionsQuarterly> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        relatedTransactionsQuarterlyService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/relatedTransactionsQuarterlyEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        RelatedTransactionsQuarterly relatedTransactionsQuarterly = relatedTransactionsQuarterlyService.get(pid);
        model.addAttribute("relatedTransactionsQuarterly", relatedTransactionsQuarterly);
        model.addAttribute("opt", "edit");

        return "bus/relatedTransactionsQuarterlyEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        RelatedTransactionsQuarterly relatedTransactionsQuarterly = relatedTransactionsQuarterlyService.get(pid);
        model.addAttribute("relatedTransactionsQuarterly", relatedTransactionsQuarterly);

        return "bus/relatedTransactionsQuarterlyView";
    }

    /**
     * 保存
     *
     * @param relatedTransactionsQuarterly
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelatedTransactionsQuarterly relatedTransactionsQuarterly) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = relatedTransactionsQuarterlyService.exists(relatedTransactionsQuarterly.getPid());
        try {
            if (isExist) {

                relatedTransactionsQuarterlyService.update(relatedTransactionsQuarterly);
            } else {
                relatedTransactionsQuarterlyService.insert(relatedTransactionsQuarterly);
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
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            relatedTransactionsQuarterlyService.deleteByPrimaryKey(pid);
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
     * 验证提交的pid是否已经存在数据库中
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = relatedTransactionsQuarterlyService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}