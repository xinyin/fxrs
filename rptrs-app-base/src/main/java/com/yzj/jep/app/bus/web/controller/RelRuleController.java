/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 14:29:15
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.OrgSuspect;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.RelRule;
import com.yzj.jep.app.bus.service.RelRuleService;

@Controller
@RequestMapping("/bus/relRule")
public class RelRuleController extends CrudController<RelRule> {
    private static Logger logger = LoggerFactory.getLogger(RelRuleController.class);

    @Autowired
    private RelRuleService relRuleService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/relRuleList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelRule> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        relRuleService.query(page, filters, orders);
    }


    /**
     * 查询 自然人
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/querySusPsn")
    @ResponseBody
    public Map<String, Object> querySusPsn(Model model, Page<RelRule> page) {
        List<Filter> filter = new Filter.Builder().eq("ruleType", "SUSPSN").build();
        List<RelRule> rules = relRuleService.select(filter);
        Map<String, Object> tips = new HashMap<String, Object>();
        tips.put("rules", rules);

        page.setTotal(rules.size());
        int pages = (rules.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
//		List<RelRule> relPsnVos = new ArrayList<RelRule>();
//		for (int i = index; i < index + page.getPageSize(); i++) {
//			if (i < rules.size()) {
//				relPsnVos.add(rules.get(i));
//			}
//		}
        page.setResult(rules);
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());

//		tips.put("pid", pid);
        return tips;
    }

    /**
     * 查询 机构
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/querySusOrg")
    @ResponseBody
    public Map<String, Object> querySusOrg(Model model, Page<RelRule> page) {
        List<Filter> filter = new Filter.Builder().eq("ruleType", "SUSORG").build();
        List<RelRule> rules = relRuleService.select(filter);
        Map<String, Object> tips = new HashMap<String, Object>();
        tips.put("rules", rules);

        page.setTotal(rules.size());
        int pages = (rules.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
//		List<RelRule> relPsnVos = new ArrayList<RelRule>();
//		for (int i = index; i < index + page.getPageSize(); i++) {
//			if (i < rules.size()) {
//				relPsnVos.add(rules.get(i));
//			}
//		}
        page.setResult(rules);
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());

//		tips.put("pid", pid);
        return tips;
    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/relRuleEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}/{status}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> edit(@PathVariable Integer pid, @PathVariable String status, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RelRule relRule = relRuleService.get(pid);
        relRule.setRuleStatus(status);
        Integer res = relRuleService.updateSelective(relRule);
        if (res == 1) {
            //修改成功
            if ("1".equals(status)) {
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "启用成功");
            } else if ("0".equals(status)) {
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "停用成功");
            }
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "修改失败");
        }
//		model.addAttribute("relRule", relRule);
//		model.addAttribute("opt", "edit");

        return tips;
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

        RelRule relRule = relRuleService.get(pid);
        model.addAttribute("relRule", relRule);

        return "bus/relRuleView";
    }

    /**
     * 保存
     *
     * @param relRule
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelRule relRule) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = relRuleService.exists(relRule.getPid());
        try {
            if (isExist) {

                relRuleService.update(relRule);
            } else {
                relRuleService.insert(relRule);
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
            relRuleService.deleteByPrimaryKey(pid);
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

        boolean exist = relRuleService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}