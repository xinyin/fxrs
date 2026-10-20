/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月18日 17:42:12
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

import com.yzj.jep.app.bus.model.TraBalanceDeduction;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.TraBalanceDeductionService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/traBalanceDeduction")
public class TraBalanceDeductionController extends CrudController<TraBalanceDeduction> {
    private static Logger logger = LoggerFactory.getLogger(TraBalanceDeductionController.class);

    @Autowired
    private TraBalanceDeductionService traBalanceDeductionService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traBalanceDeductionList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraBalanceDeduction> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        traBalanceDeductionService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/traBalanceDeductionEdit";
    }

    /**
     * 编辑页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(Model model) {

        TraBalanceDeduction traBalanceDeduction = traBalanceDeductionService.get(1);
        model.addAttribute("traBalanceDeduction", traBalanceDeduction);
        model.addAttribute("opt", "edit");
        model.addAttribute("pid", 1);

        return "bus/traBalanceDeductionEdit";
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

        TraBalanceDeduction traBalanceDeduction = traBalanceDeductionService.get(pid);
        model.addAttribute("traBalanceDeduction", traBalanceDeduction);

        return "bus/traBalanceDeductionView";
    }

    /**
     * 保存
     *
     * @param traBalanceDeduction
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraBalanceDeduction traBalanceDeduction) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = traBalanceDeductionService.exists(traBalanceDeduction.getPid());
        try {
            if (isExist) {

                traBalanceDeductionService.update(traBalanceDeduction);
            } else {
                traBalanceDeductionService.insert(traBalanceDeduction);
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
            traBalanceDeductionService.deleteByPrimaryKey(pid);
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

        boolean exist = traBalanceDeductionService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}