/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:58
 *
 */

package com.yzj.jep.app.fil.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.fil.model.FilInvestment;
import com.yzj.jep.app.fil.service.FilInvestmentService;
import com.yzj.jep.app.util.JsonUtil;
import com.yzj.jep.orm.entity.*;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/ins/filInvestment")
public class FilInvestmentController extends CrudController<FilInvestment> {
    private static Logger logger = LoggerFactory.getLogger(FilInvestmentController.class);

    @Autowired
    private FilInvestmentService filInvestmentService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "fil/ins/filInvestmentList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilInvestment> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("pid", OrderType.DESC));
        //列表按pid排序 因为备案管理系统没有修改数据路径
        filInvestmentService.query(page, filters, orders);
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

        FilInvestment filInvestment = filInvestmentService.get(pid);
        model.addAttribute("traInvestment", filInvestment);
        model.addAttribute("opt", "view");
        model.addAttribute("wt", "1");
        return "fil/ins/filInvestmentView";
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
        boolean exists = filInvestmentService.exists(filters);
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
        FilInvestment filInvestment = filInvestmentService.get(filters);
        model.addAttribute("filInvestment", filInvestment);
        model.addAttribute("opt", "view");
        model.addAttribute("wt", wt);
        return "fil/ins/filInvestmentView";
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

        boolean exist = filInvestmentService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping(value = "/compared/{pid}")
    public String compared(@PathVariable String pid, Model model) {
//		String [] pids=pid.split(",");
//		FilInvestment relPsn1 = filInvestmentService.get(Integer.valueOf(pids[0]));
//		FilInvestment relPsn2 = filInvestmentService.get(Integer.valueOf(pids[1]));
        //实体类转json
        //根据pid查出选出所选数据
        FilInvestment filInvestment = filInvestmentService.get(Integer.valueOf(pid));
        //根据查出对象flagPid属性查出同一自然人/机构得备案记录
        List<FilInvestment> filInvestments = filInvestmentService.selectFilInvestmentByfalgPid(filInvestment.getFlagPid());
        //取出所选数据得上一条数据
        int index = filInvestments.indexOf(filInvestment);
        if (index >= 1) {
            FilInvestment filInvestment1 = filInvestments.get(index - 1);
            JSONObject psn1 = (JSONObject) JSON.toJSON(filInvestment);
            JSONObject psn2 = (JSONObject) JSON.toJSON(filInvestment1);
            Map result = JsonUtil.compareJson(psn1, psn2, null);
            model.addAttribute("filInvestment", psn1);
            model.addAttribute("filInvestment2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", "1");
            return "fil/ins/filInvestmentView";
        } else {
            JSONObject psn1 = (JSONObject) JSON.toJSON(filInvestment);
            Map result = JsonUtil.compareJson(psn1, psn1, null);
            model.addAttribute("filInvestment", psn1);
            model.addAttribute("filInvestment2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", "1");
            return "fil/ins/filInvestmentView";
        }
    }

    /**
     * 多条查询
     *
     * @param pids
     * @return
     */
    @RequestMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<FilInvestment> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        filInvestmentService.query(page, filters);
        result.put(WebCode.SUCCESS, true);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }
}