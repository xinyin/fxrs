/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.fil.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.fil.model.*;
import com.yzj.jep.app.fil.service.FilRelOrgService;
import com.yzj.jep.app.fil.service.FilRelProPsnandorgService;
import com.yzj.jep.app.fil.service.FilRelPsnService;
import com.yzj.jep.app.util.JsonUtil;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
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
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/fil/filRelPsn")
public class FilRelPsnController extends CrudController<FilRelPsn> {
    private static Logger logger = LoggerFactory.getLogger(FilRelPsnController.class);

    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private FilRelPsnService filRelPsnService;
    @Autowired
    private FilRelOrgService filRelOrgService;
    @Autowired
    private FilRelProPsnandorgService filRelProPsnandorgService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "fil/psn/filRelPsnList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilRelPsn> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("pid", OrderType.DESC));
        filRelPsnService.query(page, filters, orders);
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
        FilRelPsn relPsn = filRelPsnService.get(pid);
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");
        model.addAttribute("wt", "1");
        return "fil/psn/filRelPsnAddOrEdit";
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
        boolean exists = filRelPsnService.exists(filters);
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
        FilRelPsn filRelPsn = filRelPsnService.get(filters);
        model.addAttribute("relPsn", filRelPsn);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");
        model.addAttribute("wt", wt);
        return "fil/psn/filRelPsnAddOrEdit";
    }


    /**
     * 基本信息表
     *
     * @param pid
     * @param op
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, int op, Model model) {

        FilRelPsn relPsn = filRelPsnService.get(pid);
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);
        return "fil/psn/filRelPsnAddOrEdit";
    }

    /**
     * 自然人关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateList/{pid}")
    public String affiliateView(@PathVariable String pid, int op, String wt, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
        model.addAttribute("wt", wt);
        return "fil/psn/filAffiliateList";
    }


    /**
     * 自然人关系信息列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliate/{pid}")
    @ResponseBody
    public Map<String, Object> affiliate(@PathVariable String pid, Model model, Page<FilRelPsnVo> page) {
        List<FilRelPsnVo> relPsnVoList = filRelPsnService.selectRelPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relPsnVoList.size());
        int pages = (relPsnVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<FilRelPsnVo> relPsnVos = new ArrayList<FilRelPsnVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnVoList.size()) {
                relPsnVos.add(relPsnVoList.get(i));
            }
        }
        page.setResult(relPsnVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 自然人关系信息列表   下级关联人
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateDown/{pid}")
    @ResponseBody
    public Map<String, Object> affiliateDown(@PathVariable String pid, Model model, Page<FilRelPsnVo> page) {
        List<FilRelPsnVo> relPsnVoList = filRelPsnService.selectDownRelPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relPsnVoList.size());
        int pages = (relPsnVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<FilRelPsnVo> relPsnVos = new ArrayList<FilRelPsnVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnVoList.size()) {
                relPsnVos.add(relPsnVoList.get(i));
            }
        }
        page.setResult(relPsnVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 查看自然人关系信息页面
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/viewAffiliate/{relId}/{fromPid}/{type}")
    public String viewAffiliate(@PathVariable Integer relId, @PathVariable Integer fromPid, @PathVariable String type, Model model, int op) {
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);
        // 自然人pid
        List<RelPsnVo> relPsnVoList = new ArrayList<>();
        if ("UP".equals(type)) {
            //查找所属关联方
            relPsnVoList = relPsnService.selectRelPsnInformation(fromPid.toString());
        } else if ("DOWN".equals(type)) {
            //查找下级所属关联方
            relPsnVoList = relPsnService.selectDownRelPsnInformation(fromPid.toString());
        } else {
            model.addAttribute("fromPid", fromPid);
            return "fil/psn/filPsnUpAddOrView";
        }
        //取出 某一条关系
        List<RelPsnVo> relPsns = relPsnVoList.stream().filter(item -> item.getRelId().equals(relId)).collect(Collectors.toList());
        if (relPsns.size() > 0) {
            RelPsnVo relPsnVo = relPsns.get(0);
            model.addAttribute("relPsnVo", relPsnVo);
        }
        model.addAttribute("fromPid", fromPid);
        return "fil/psn/filPsnUpAddOrView";
    }

    /**
     * 机构关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnRelOrgList/{pid}")
    public String rightsList(@PathVariable String pid, int op, String wt, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
        model.addAttribute("wt", wt);
        return "fil/psn/filPsnRelOrgList";
    }

    /**
     * 所属机构关系信息列表
     *
     * @param orgid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnRelOrg/{orgid}")
    @ResponseBody
    public Map<String, Object> psnRelOrg(@PathVariable String orgid, Model model, Page<FilRelPsnVo> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        FilRelOrgService relOrgService = SpringContextHolder.getBean(FilRelOrgService.class);
        List<FilRelOrgVo> tzOrgPsn = relOrgService.getTZOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());

        return result;
    }

    /**
     * 下一级 机构关系信息列表
     *
     * @param orgid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnDownRelOrg/{orgid}")
    @ResponseBody
    public Map<String, Object> psnDownRelOrg(@PathVariable String orgid, Model model, Page<FilRelPsnVo> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        FilRelOrgService relOrgService = SpringContextHolder.getBean(FilRelOrgService.class);
        List<FilRelOrgVo> tzOrgPsn = relOrgService.getDownTZOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 编辑机构关系信息页面
     */
    @RequestMapping(value = "viewPsnRelOrg/{relId}/{fromPid}/{type}")
    public String viewPsnRelOrg(@PathVariable Integer relId, @PathVariable Integer fromPid, @PathVariable String type, int op, Model model) {
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("op", op);
        List<FilRelOrgVo> tzOrgPsn = new ArrayList<FilRelOrgVo>();
        if ("UP".equals(type)) {
            //查找所属关联方
            tzOrgPsn = filRelOrgService.getTZOrgPsn(String.valueOf(fromPid));
        } else if ("DOWN".equals(type)) {
            //查找下级所属关联方
            tzOrgPsn = filRelOrgService.getDownTZOrgPsn(String.valueOf(fromPid));
        } else {
            model.addAttribute("fromPid", fromPid);
            return "fil/psn/filPsnRelOrgAddOrView";
        }
        List<FilRelOrgVo> orgs = tzOrgPsn.stream().filter(item -> item.getRelId().equals(relId)).collect(Collectors.toList());
        if (orgs.size() > 0) {
            FilRelOrgVo filRelOrgVo = orgs.get(0);
            model.addAttribute("relOrg", filRelOrgVo);
        }
        model.addAttribute("fromPid", fromPid);
        return "fil/psn/filPsnRelOrgAddOrView";
    }


    /**
     * 自然人关联金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proOfPsnList/{pid}")
    public String psnRelProList(@PathVariable String pid, int op, String wt, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
        model.addAttribute("wt", wt);
        return "fil/psn/filProOfPsnList";
    }

    /**
     * 自然人归属的金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proUpOfPsnList/{pid}")
    @ResponseBody
    public Map<String, Object> proUpOfPsnList(@PathVariable String pid, Model model, Page<FilRelProVo> page) {
        List<FilRelProVo> relProVoList = filRelProPsnandorgService.selectRelProUpOfPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relProVoList.size());
        int pages = (relProVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<FilRelProVo> relProVos = new ArrayList<FilRelProVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relProVoList.size()) {
                relProVos.add(relProVoList.get(i));
            }
        }
        page.setResult(relProVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 自然人关联下级金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proDownOfPsnList/{pid}")
    @ResponseBody
    public Map<String, Object> proDownOfPsnList(@PathVariable String pid, Model model, Page<FilRelProVo> page) {
        List<FilRelProVo> relProVoList = filRelProPsnandorgService.selectRelProDownOfPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relProVoList.size());
        int pages = (relProVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<FilRelProVo> relProVos = new ArrayList<FilRelProVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relProVoList.size()) {
                relProVos.add(relProVoList.get(i));
            }
        }
        page.setResult(relProVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    @RequestMapping(value = "/compared/{pid}")
    public String compared(@PathVariable String pid, int op, String wt, Model model) {
        //String[] pids = pid.split(",");
        //FilRelPsn relPsn1 = filRelPsnService.get(Integer.valueOf(pids[0]));
        //FilRelPsn relPsn2 = filRelPsnService.get(Integer.valueOf(pids[1]));
        //根据pid查出选出所选数据
        FilRelPsn filRelPsn = filRelPsnService.get(Integer.valueOf(pid));
        //根据查出对象flagPid属性查出同一自然人/机构得备案记录
        List<FilRelPsn> filRelPsns = filRelPsnService.selectFilRelPsnByfalgPid(filRelPsn.getFlagPid());
        //取出所选数据得上一条数据
        int index = filRelPsns.indexOf(filRelPsn);
        if (index >= 1) {
            FilRelPsn filRelPsn1 = filRelPsns.get(index - 1);
            //实体类转json
            JSONObject psn1 = (JSONObject) JSON.toJSON(filRelPsn);
            JSONObject psn2 = (JSONObject) JSON.toJSON(filRelPsn1);
            System.out.println("PSN1" + psn1 + "PSN2" + psn2);
            Map result = JsonUtil.compareJson(psn1, psn2, null);
            model.addAttribute("relPsn", psn1);
            model.addAttribute("relPsn2", result);
//        model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", wt);
            return "fil/psn/filRelPsnAddOrEdit";
        } else {
            //实体类转json
            JSONObject psn1 = (JSONObject) JSON.toJSON(filRelPsn);
            Map result = JsonUtil.compareJson(psn1, psn1, null);
            model.addAttribute("relPsn", psn1);
            //model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", wt);
            return "fil/psn/filRelPsnAddOrEdit";
        }

    }


}