/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.fil.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.fil.model.*;
import com.yzj.jep.app.fil.service.*;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.yzj.jep.app.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/fil/relOrg")
public class FilRelOrgController extends CrudController<FilRelOrg> {
    private static Logger logger = LoggerFactory.getLogger(FilRelOrgController.class);

    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private FilRelOrgService filRelOrgService;

    @Autowired
    private RelService relService;

    @Autowired
    private FilRelService filRelService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private FilRelPsnService filRelPsnService;

    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private RelDictService relDictService;

    @Autowired
    private AligMapService aligMapService;
    @Autowired
    private FilRelProPsnandorgService filRelProPsnandorgService;

    @Autowired
    private FilRelPsnAndOrgOfProService filRelPsnAndOrgOfProService;

    @Autowired
    private FilRelProService filRelProService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "fil/org/filRelOrgList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilRelOrg> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("pid", OrderType.DESC));
        filRelOrgService.query(page, filters, orders);
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

        FilRelOrg filRelOrg = filRelOrgService.get(pid);
        model.addAttribute("relOrg", filRelOrg);
        model.addAttribute("opt", "edit");
        model.addAttribute("wt", "1");
        model.addAttribute("op", "1");
        return "fil/org/filRelOrgEditEye";
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
        boolean exists = filRelOrgService.exists(filters);
        if (exists) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);

        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "该条数据已被删除或审批未通过!！");
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
        FilRelOrg filRelOrg = filRelOrgService.get(filters);
        model.addAttribute("relOrg", filRelOrg);
        model.addAttribute("opt", "edit");
        model.addAttribute("wt", wt);
        model.addAttribute("op", "1");
        return "fil/org/filRelOrgEditEye";
    }

    /**
     * 编辑页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, int op, String wt, Model model) {

        FilRelOrg org = filRelOrgService.get(pid);
        FilRelOrgVo relOrg = new FilRelOrgVo();
        BeanUtils.copyProperties(org, relOrg);
        if ("1".equals(org.getIsBlack())) {
            relOrg.setBlackReason("好多原因");
        }
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);
        model.addAttribute("wt", wt);
        return "fil/org/filRelOrgEdit";
    }

    /**
     * 自然人信息跳转页面
     */
    @RequestMapping(value = "/listAffi/{orgid}")
    public String listAffi(Model model, int op, String wt, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);
        model.addAttribute("op", op);
        model.addAttribute("wt", wt);
        return "fil/org/filRelPsnListAffi";
    }

    /**
     * 归属自然人信息页面加载
     */
    @RequestMapping(value = {"/queryPsnList"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryPsnList(Page<FilRel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<FilRelPsnVo> relPsnVos = filRelOrgService.getOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(relPsnVos);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 下级自然人信息页面加载
     */
    @RequestMapping(value = {"/queryDownPsnList"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryDownPsnList(Page<FilRel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<FilRelPsnVo> relPsnVos = filRelOrgService.getDownOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(relPsnVos);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 查看关系自然人信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewAffiliate/{relId}/{fromPid}/{type}")
    public String addAffiliate(@PathVariable Integer relId, @PathVariable Integer fromPid, @PathVariable String type, Model model) {
        model.addAttribute("opt", "edit");
        List<FilRelPsnVo> relPsnVos = new ArrayList<>();
        if ("UP".equals(type)) {
            //查找所属关联方
            relPsnVos = filRelOrgService.getOrgPsn(String.valueOf(fromPid));
        } else if ("DOWN".equals(type)) {
            //查找下级所属关联方
            relPsnVos = filRelOrgService.getDownOrgPsn(String.valueOf(fromPid));
        } else {
            model.addAttribute("fromPid", fromPid);
            return "fil/org/FilOrgRelPsnView";
        }
        //取出符合条件的数据
        List<FilRelPsnVo> vos = relPsnVos.stream().filter(item -> item.getRelId().equals(relId)).collect(Collectors.toList());
        if (vos.size() > 0) {
            FilRelPsnVo relPsnVo = vos.get(0);
            model.addAttribute("relPsnVo", relPsnVo);
        }
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "fil/Org/FilOrgRelPsnView";
    }

    /**
     * 查看机构信息
     *
     * @param model
     * @param op
     * @param fromPid
     * @return
     */

    @RequestMapping(value = "/orgRelList/{fromPid}")
    public String orgRelList(Model model, int op, String wt, @PathVariable Integer fromPid) {
        model.addAttribute("orgid", fromPid);
        model.addAttribute("op", op);
        model.addAttribute("wt", wt);

        return "fil/org/filRelList";
    }

    /**
     * 所属关联自然人 机构
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryVo"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryVo(Page<FilRel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<FilRelOrgVo> tzOrgPsn = filRelOrgService.getOrgOrg(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 下级关联自然人  机构
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryDownVo"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryDownVo(Page<FilRel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<FilRelOrgVo> tzOrgPsn = filRelOrgService.getDownOrgOrg(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 查看关系机构信息页面
     */
    @RequestMapping(value = "viewOrg/{relId}/{fromPid}/{type}")
    public String viewOrg(@PathVariable Integer relId, @PathVariable Integer fromPid, @PathVariable String type, Model model) {
        model.addAttribute("opt", "edit");
        model.addAttribute("fromPid", fromPid);
        List<FilRelOrgVo> UpOrg = new ArrayList<>();
        if ("UP".equals(type)) {
            //查找所属关联方
            UpOrg = filRelOrgService.selecUpOrg(String.valueOf(fromPid));
        } else if ("DOWN".equals(type)) {
            //查找下级所属关联方
            UpOrg = filRelOrgService.selecDownOrg(String.valueOf(fromPid));
        } else {
            model.addAttribute("fromPid", fromPid);
            return "bus/relOrgViewEqu";
        }
        List<FilRelOrgVo> orgs = UpOrg.stream().filter(item -> item.getRelId().equals(relId)).collect(Collectors.toList());
        if (orgs.size() > 0) {
            FilRelOrgVo relOrg = orgs.get(0);
            model.addAttribute("relOrg", relOrg);
        }
        model.addAttribute("fromPid", fromPid);
        return "bus/relOrgViewEqu";
    }

    /**
     * 机构关联金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proOfOrgList/{pid}")
    public String proOfOrgList(@PathVariable String pid, int op, String wt, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
        model.addAttribute("wt", wt);
        return "fil/org/filProOfOrgList";
    }

    /**
     * 自然人归属的金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proUpOfOrgList/{pid}")
    @ResponseBody
    public Map<String, Object> proUpOfOrgList(@PathVariable String pid, Model model, Page<FilRelProVo> page) {
        List<FilRelProVo> relProVoList = filRelPsnAndOrgOfProService.selectRelProUpOfOrgInformation(pid);
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
    @RequestMapping(value = "/proDownOfOrgList/{pid}")
    @ResponseBody
    public Map<String, Object> proDownOfOrgList(@PathVariable String pid, Model model, Page<FilRelProVo> page) {
        List<FilRelProVo> relProVoList = filRelPsnAndOrgOfProService.selectRelProDownOfOrgInformation(pid);
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
     * 编辑机构关联方所属金融产品
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/editProUpOfOrg/{relId}/{fromPid}/{type}")
    public String editProUpOfOrg(@PathVariable Integer relId, @PathVariable Integer fromPid, @PathVariable String type, Model model, int op) {
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);
        List<FilRelProVo> relProVoList = new ArrayList<>();
        if ("UP".equals(type)) {

            relProVoList = filRelProService.selectRelProOrg(fromPid.toString());
        } else if ("DOWN".equals(type)) {

            relProVoList = filRelProService.selectDownRelProOrg(fromPid.toString());
        } else {
            model.addAttribute("fromPid", fromPid);
            return "bus/proOfOrgEdit";
        }
        List<FilRelProVo> relProVos = relProVoList.stream().filter(item -> item.getRelId().equals(relId)).collect(Collectors.toList());
        if (relProVos.size() > 0) {
            FilRelProVo relProVo = relProVos.get(0);
            model.addAttribute("relProVo", relProVo);
        }
        // 机构pid
        model.addAttribute("fromPid", fromPid);
        return "bus/proOfOrgEdit";
    }

    /*
    数据对比
     */
    @RequestMapping(value = "/compared/{pid}")
    public String compared(@PathVariable String pid, Model model) {

        //根据pid查出选出所选数据
        FilRelOrg filRelOrg = filRelOrgService.get(Integer.valueOf(pid));
        //根据查出对象flagPid属性查出同一自然人/机构得备案记录
        List<FilRelOrg> filRelOrgs = filRelOrgService.selectFilRelOrgByfalgPid(filRelOrg.getFlagPid());
        int index = filRelOrgs.indexOf(filRelOrg);
        //判断有没有变更记录
        if (index >= 1) {
            //取出所选数据得上一条数据
            FilRelOrg filRelOrg1 = filRelOrgs.get(index - 1);
            //实体类转json
            JSONObject psn1 = (JSONObject) JSON.toJSON(filRelOrg);
            JSONObject psn2 = (JSONObject) JSON.toJSON(filRelOrg1);
            Map result = JsonUtil.compareJson(psn1, psn2, null);
            model.addAttribute("relOrg", psn1);
            model.addAttribute("relOrg2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");

            return "fil/org/filRelOrgEditEye";
        } else {
            JSONObject psn1 = (JSONObject) JSON.toJSON(filRelOrg);
            Map result = JsonUtil.compareJson(psn1, psn1, null);
            model.addAttribute("relOrg", psn1);
            model.addAttribute("relOrg2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            return "fil/org/filRelOrgEditEye";
        }


    }

}