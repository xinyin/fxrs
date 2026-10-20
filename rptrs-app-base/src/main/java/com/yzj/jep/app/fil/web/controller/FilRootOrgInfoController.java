/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年08月03日 17:04:30
 *
 */

package com.yzj.jep.app.fil.web.controller;

import com.yzj.jep.app.fil.model.FilRootOrgInfo;
import com.yzj.jep.app.fil.service.FilRootOrgInfoService;
import com.yzj.jep.core.exception.ServiceException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fil/rootOrgInfo")
public class FilRootOrgInfoController extends CrudController<FilRootOrgInfo> {
    private static Logger logger = LoggerFactory.getLogger(FilRootOrgInfoController.class);

    @Autowired
    private FilRootOrgInfoService filRootOrgInfoService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "fil/info/rootOrgInfoList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilRootOrgInfo> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        filRootOrgInfoService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "fil/info/rootOrgInfoEdit";
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

        FilRootOrgInfo filRootOrgInfo = filRootOrgInfoService.get(pid);
        model.addAttribute("rootOrgInfo", filRootOrgInfo);
        model.addAttribute("opt", "edit");

        return "fil/info/rootOrgInfoEdit";
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

        FilRootOrgInfo filRootOrgInfo = filRootOrgInfoService.get(pid);
        model.addAttribute("rootOrgInfo", filRootOrgInfo);

        return "fil/info/rootOrgInfoView";
    }

    /**
     * 保存
     *
     * @param filRootOrgInfo
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(FilRootOrgInfo filRootOrgInfo) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = filRootOrgInfoService.exists(filRootOrgInfo.getPid());
        try {
            if (isExist) {

                filRootOrgInfoService.update(filRootOrgInfo);
            } else {
                filRootOrgInfoService.insert(filRootOrgInfo);
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
            filRootOrgInfoService.deleteByPrimaryKey(pid);
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

        boolean exist = filRootOrgInfoService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    @GetMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<FilRootOrgInfo> page, @PathVariable List<Integer> pids) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        filRootOrgInfoService.query(page, filters);
        tips.put(WebCode.SUCCESS, true);
        tips.put("total", page.getTotal());
        tips.put("rows", page.getResult());
        return tips;
    }
}