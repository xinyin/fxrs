/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:52:10
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.NaturalRel;
import com.yzj.jep.app.bus.service.NaturalRelService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.NaturalPersonBlack;
import com.yzj.jep.app.bus.service.NaturalPersonBlackService;

@Controller
@RequestMapping("/bus/naturalPersonBlack")
public class NaturalPersonBlackController extends CrudController<NaturalPersonBlack> {
    private static Logger logger = LoggerFactory.getLogger(NaturalPersonBlackController.class);

    @Autowired
    private NaturalPersonBlackService naturalPersonBlackService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/naturalPersonBlackList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<NaturalPersonBlack> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        //做日期判断
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < filters.size(); i++) {
            if ("beginDate".equals(filters.get(i).getProperty())) {
                startIndex = i;
                String startDate = filters.get(i).getValue().toString();
                filters.add(new Filter("blackDate", Operator.GE, startDate));
            }
            if ("endDate".equals(filters.get(i).getProperty())) {
                endIndex = i;
                String endDate = filters.get(i).getValue().toString();
                filters.add(new Filter("blackDate", Operator.LE, endDate));
            }
        }
        //删除两个过滤器  --注意：此处先删除endDate  本着从后往前删除的原则
        if (endIndex != -1) {
            filters.remove(endIndex);
        }
        if (startIndex != -1) {
            filters.remove(startIndex);
        }
        naturalPersonBlackService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/naturalPersonBlackEdit";
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

        NaturalPersonBlack naturalPersonBlack = naturalPersonBlackService.get(pid);
        model.addAttribute("naturalPersonBlack", naturalPersonBlack);
        model.addAttribute("opt", "edit");

        return "bus/naturalPersonBlackEdit";
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
        // 自然人黑名单详情
        NaturalPersonBlack naturalPersonBlack = naturalPersonBlackService.get(pid);
        model.addAttribute("naturalPersonBlack", naturalPersonBlack);
        // 自然人附属信息详情
        NaturalRelService naturalRelService = SpringContextHolder.getBean(NaturalRelService.class);
        List<Filter> filters = new Filter.Builder().eq("naturalId", pid).build();
        List<NaturalRel> naturalRel = naturalRelService.select(filters);
        model.addAttribute("naturalRel", naturalRel);
        return "bus/naturalPersonBlackView";
    }

    /**
     * 保存
     *
     * @param naturalPersonBlack
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(NaturalPersonBlack naturalPersonBlack) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = naturalPersonBlackService.exists(naturalPersonBlack.getPid());
        try {
            if (isExist) {

                naturalPersonBlackService.update(naturalPersonBlack);
            } else {
                naturalPersonBlackService.insert(naturalPersonBlack);
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
            naturalPersonBlackService.deleteByPrimaryKey(pid);
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

        boolean exist = naturalPersonBlackService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}