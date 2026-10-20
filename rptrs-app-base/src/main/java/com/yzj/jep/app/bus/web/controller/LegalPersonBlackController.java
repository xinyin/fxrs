/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:52:04
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.NaturalPerson;
import com.yzj.jep.app.bus.service.NaturalPersonService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
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

import com.yzj.jep.app.bus.model.LegalPersonBlack;
import com.yzj.jep.app.bus.service.LegalPersonBlackService;

@Controller
@RequestMapping("/bus/legalPersonBlack")
public class LegalPersonBlackController extends CrudController<LegalPersonBlack> {
    private static Logger logger = LoggerFactory.getLogger(LegalPersonBlackController.class);

    @Autowired
    private LegalPersonBlackService legalPersonBlackService;
    @Autowired
    private NaturalPersonService naturalPersonService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/legalPersonBlackList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<LegalPersonBlack> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        legalPersonBlackService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/legalPersonBlackEdit";
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

        LegalPersonBlack legalPersonBlack = legalPersonBlackService.get(pid);
        model.addAttribute("legalPersonBlack", legalPersonBlack);
        model.addAttribute("opt", "edit");

        return "bus/legalPersonBlackEdit";
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
        //获取机构黑名单详情信息
        LegalPersonBlack legalPersonBlack = legalPersonBlackService.get(pid);
        model.addAttribute("legalPersonBlack", legalPersonBlack);
        //获取机构黑名单的附属信息
        List<NaturalPerson> naturalPersonList = naturalPersonService.serachNaturalByPid(pid);
        model.addAttribute("naturalPersonList", naturalPersonList);
        return "bus/legalPersonBlackView";
    }

    /**
     * 保存
     *
     * @param legalPersonBlack
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(LegalPersonBlack legalPersonBlack) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = legalPersonBlackService.exists(legalPersonBlack.getPid());
        try {
            if (isExist) {

                legalPersonBlackService.update(legalPersonBlack);
            } else {
                legalPersonBlackService.insert(legalPersonBlack);
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
            legalPersonBlackService.deleteByPrimaryKey(pid);
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

        boolean exist = legalPersonBlackService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}