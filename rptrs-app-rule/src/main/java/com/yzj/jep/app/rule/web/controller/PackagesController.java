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
import com.yzj.jep.rules.drools5.model.Packages;
import com.yzj.jep.rules.drools5.service.PackagesService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/rul/packages")
public class PackagesController extends CrudController<Packages> {
    private static Logger logger = LoggerFactory.getLogger(PackagesController.class);

    @Autowired
    private PackagesService packagesService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "rul/packagesList";
    }

    @RequestMapping(value = "/rules")
    public String rules(String packagesId, Model model) {
        model.addAttribute("packagesId", packagesId);
        return "rul/pkgRuleMapList";
    }

    @RequestMapping(value = "/testView")
    public String testView(String pkgId, Model model) {
        Packages pkg = packagesService.get(pkgId);
        model.addAttribute("pkgId", pkgId);
        model.addAttribute("pkgCode", pkg.getCode());
        return "rul/packagesTest";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Packages> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        packagesService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "rul/packagesEdit";
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

        Packages packages = packagesService.get(id);
        model.addAttribute("packages", packages);
        model.addAttribute("opt", "edit");

        return "rul/packagesEdit";
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

        Packages packages = packagesService.get(id);
        model.addAttribute("packages", packages);

        return "rul/packagesView";
    }

    /**
     * 保存
     *
     * @param packages
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Packages packages) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = packagesService.exists(packages.getId());
        try {
            if (isExist) {

                packagesService.update(packages);
            } else {
                packagesService.insert(packages);
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
            packagesService.deleteByPrimaryKey(id);
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
     * 查询所有规则集
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public List<NameValuePair> queryList(Model model) {

        List<Packages> rules = packagesService.selectAll();
        List<NameValuePair> nameValuePairs = NVUtils.convert(rules, rules.size(), "id", "name");

        return nameValuePairs;
    }


    /**
     * 验证提交的编码是否已经存在数据库中
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String code) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = packagesService.exists(new Filter.Builder().eq("code", code).build());
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 部署模型
     *
     * @param pkgId 模型ID
     * @return
     */
    @RequestMapping(value = "/deploy")
    @ResponseBody
    public Map<String, Object> deploy(String pkgId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            packagesService.deploy(pkgId);
            tips.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }

    /**
     * 反部署模型
     *
     * @param pkgId 模型ID
     * @return
     */
    @RequestMapping(value = "/unDeploy")
    @ResponseBody
    public Map<String, Object> unDeploy(String pkgId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            packagesService.unDeploy(pkgId);
            tips.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }


    /**
     * 反部署模型
     *
     * @param pkgCode 模型ID
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public Map<String, Object> test(String pkgId,String requestData) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            tips.putAll(packagesService.execute(pkgId,requestData));
            tips.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            if(e instanceof ServiceException)
            {
                tips.put(WebCode.MSG, e.getMessage());
            }else
            {
                tips.put(WebCode.MSG,"测试失败，未知异常");
            }

        }
        return tips;
    }
}