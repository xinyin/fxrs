/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2018 yzj.com. All rights reserved.
 *
 * Generated at 2018年02月24日 11:11:39
 *
 */

package com.yzj.jep.app.rule.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.rules.drools5.dto.CompileMessage;
import com.yzj.jep.rules.drools5.model.RulesContent;
import com.yzj.jep.rules.drools5.service.*;
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
import com.yzj.jep.rules.drools5.model.Rules;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/rul/rules")
public class RulesController extends CrudController<Rules> {
    private static Logger logger = LoggerFactory.getLogger(RulesController.class);

    @Autowired
    private RulesService rulesService;

    @Autowired
    private RulesContentService rulesContentService;

    @Autowired
    private RuleObjectMapService ruleObjectMapService;

    @Autowired
    private PkgRuleMapService pkgRuleMapServic;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "rul/rulesList";
    }


    @RequestMapping(value = "/select")
    public String select(String packagesId, Model model) {
        model.addAttribute("packagesId", packagesId);
        return "rul/rulesSelect";
    }

    /**
     * 规则测试页面
     *
     * @param ruleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/testView")
    public String testView(String ruleId, Model model) {
        model.addAttribute("ruleId", ruleId);
        return "rul/rulesTest";
    }

    /**
     * 规则内容编辑界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/design")
    public String design(String ruleId, Model model) {
        Rules rules = rulesService.get(ruleId);
        RulesContent content = rulesContentService.get(new Filter.Builder().eq("ruleId", ruleId).build());

        model.addAttribute("ruleId", ruleId);
        model.addAttribute("rules", rules);
        if (null != content) {
            model.addAttribute("rulesContent", content);
        }
        return "rul/rulesDesign";
    }

    /**
     * 规则内容编辑界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/designObjs")
    public String designObjs(String ruleId, Model model) {
        model.addAttribute("ruleId", ruleId);
        return "rul/rulesDesignObjs";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Rules> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("code", OrderType.ASC));
        rulesService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "rul/rulesEdit";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable String id, Model model) {

        Rules rules = rulesService.get(id);
        model.addAttribute("rules", rules);
        model.addAttribute("opt", "edit");

        return "rul/rulesEdit";
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, Model model) {

        Rules rules = rulesService.get(id);
        model.addAttribute("rules", rules);

        return "rul/rulesView";
    }

    /**
     * 保存
     *
     * @param rules
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Rules rules) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = rulesService.exists(rules.getId());
        try {
            if (isExist) {

                rulesService.update(rules);
            } else {
                rulesService.insert(rules);
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody

    public Map<String, Object> remove(@PathVariable String id) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            boolean exist = pkgRuleMapServic.exists(new Filter.Builder().eq("ruleId",id).build());
            if(!exist)
            {

                //删除关联的对象
                ruleObjectMapService.deleteObjsByRule(id);

                //关联的内容
                rulesContentService.deleteContentByRule(id);

                rulesService.deleteByPrimaryKey(id);

                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            }
            else
            {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "该规则被模型引用不能删除");
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    /**
     * 验证提交的id是否已经存在数据库中
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = rulesService.exists(id);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping(value = "/compile", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<CompileMessage> compile(String ruleId, String content) {
        return rulesService.compile(ruleId, content);
    }


    /**
     * 测试规则
     *
     * @param requestData
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public Map<String, Object> test(String ruleId, String requestData) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            tips.putAll(rulesService.test(ruleId, requestData));
            tips.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }
}