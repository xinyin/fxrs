/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:56:00
 *
 */

package com.yzj.jep.app.fil.web.controller;

import com.yzj.jep.app.fil.model.FilOther;
import com.yzj.jep.app.fil.service.FilOtherService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/ins/filOther")
public class FilOtherController extends CrudController<FilOther> {
    private static Logger logger = LoggerFactory.getLogger(FilOtherController.class);

    @Autowired
    private FilOtherService filOtherService;

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {
        FilOther filOther = filOtherService.get(pid);
        model.addAttribute("traOther", filOther);
        model.addAttribute("opt", "view");
        model.addAttribute("wt", "1");


        return "fil/ins/filOtherView";
    }


    /**
     * 判断是否存在备案号
     *
     * @param taskid
     * @return
     */
    @RequestMapping(value = "/viewA/{taskid}")
    @ResponseBody
    public Map<String, Object> viewexits(@PathVariable String taskid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = new Filter.Builder().eq("filNum", taskid).build();
        boolean exists = filOtherService.exists(filters);
        if (exists) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);

        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "该条数据审批未通过！");
        }
        return tips;
    }

    /**
     * 我的工作台查看页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewB/{taskid}")
    public String viewbeian(@PathVariable String taskid, String wt, Model model) {
        List<Filter> filters = new Filter.Builder().eq("filNum", taskid).build();
        FilOther filOther = filOtherService.get(filters);
        model.addAttribute("filOther", filOther);
        model.addAttribute("opt", "edit");
        model.addAttribute("wt", wt);
        return "fil/ins/filOtherView";
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

        boolean exist = filOtherService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 多条查询
     *
     * @param pids
     * @return
     */
    @RequestMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<FilOther> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        filOtherService.query(page, filters);
        result.put(WebCode.SUCCESS, true);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }
}