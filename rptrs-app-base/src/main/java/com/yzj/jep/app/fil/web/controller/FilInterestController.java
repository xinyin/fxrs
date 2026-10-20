/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:57
 *
 */

package com.yzj.jep.app.fil.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.fil.model.FilInterest;
import com.yzj.jep.app.fil.service.FilInterestService;
import com.yzj.jep.app.util.JsonUtil;
import com.yzj.jep.core.exception.ServiceException;
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
@RequestMapping("/ins/filInterest")
public class FilInterestController extends CrudController<FilInterest> {
    private static Logger logger = LoggerFactory.getLogger(FilInterestController.class);

    @Autowired
    private FilInterestService filInterestService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "fil/ins/filInterestList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilInterest> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("pid", OrderType.DESC));
        //列表按pid排序 因为备案管理系统没有修改数据路径

        filInterestService.query(page, filters, orders);
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
        FilInterest filInterest = filInterestService.get(pid);
        model.addAttribute("traInterest", filInterest);
        model.addAttribute("opt", "view");
        model.addAttribute("wt", "1");

        return "fil/ins/filInterestView";
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
        boolean exists = filInterestService.exists(filters);
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
        FilInterest filInterest = filInterestService.get(filters);
        model.addAttribute("filInterest", filInterest);
        model.addAttribute("wt", wt);

        return "fil/ins/filInterestView";
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
            filInterestService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    @RequestMapping(value = "/compared/{pid}")
    public String compared(@PathVariable String pid, Model model) {
//		String[] pids = pid.split(",");
//		FilInterest relPsn1 = filInterestService.get(Integer.valueOf(pids[0]));
//		FilInterest relPsn2 = filInterestService.get(Integer.valueOf(pids[1]));
        //根据pid查出选出所选数据
        FilInterest filInterest = filInterestService.get(Integer.valueOf(pid));
        //根据查出对象flagPid属性查出同一自然人/机构得备案记录
        List<FilInterest> filInterestList = filInterestService.selectfilInterestByfalgPid(filInterest.getFlagPid());
        //取出所选数据得上一条数据
        int index = filInterestList.indexOf(filInterest);
        if (index >= 1) {
            FilInterest filInterest1 = filInterestList.get(index - 1);
            //实体类转json
            JSONObject psn1 = (JSONObject) JSON.toJSON(filInterest);
            JSONObject psn2 = (JSONObject) JSON.toJSON(filInterest1);
            System.out.println("PSN1" + psn1 + "PSN2" + psn2);
            Map result = JsonUtil.compareJson(psn1, psn2, null);
            model.addAttribute("filInterest", psn1);
            model.addAttribute("filInterest2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", "1");
            return "fil/ins/filInterestView";
        } else {
            JSONObject psn1 = (JSONObject) JSON.toJSON(filInterest);
            Map result = JsonUtil.compareJson(psn1, psn1, null);
            model.addAttribute("filInterest", psn1);
            model.addAttribute("filInterest2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", "1");
            return "fil/ins/filInterestView";
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
    public Map<String, Object> queryPidsByConditions(Page<FilInterest> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        filInterestService.query(page, filters);
        result.put(WebCode.SUCCESS, true);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }
}