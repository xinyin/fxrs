/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:36
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.orm.entity.Operator;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/legalPerson")
public class LegalPersonController extends CrudController<LegalPerson> {
    private static Logger logger = LoggerFactory.getLogger(LegalPersonController.class);
    @Autowired
    private LegalPersonService legalPersonService;
    @Autowired
    private LegalInviolableRightsService legalInviolableRightsService;
    @Autowired
    private NaturalPersonService naturalPersonService;
    @Autowired
    private LegalPersonBlackService legalPersonBlackService;
    @Autowired
    private LegalPersonRemoveService legalPersonRemoveService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/legalPersonList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<LegalPerson> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("validStatus", Operator.EQ, "1"));
        legalPersonService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/legalPersonEdit";
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

        LegalPerson legalPerson = legalPersonService.get(pid);
        model.addAttribute("legalPerson", legalPerson);
        model.addAttribute("opt", "edit");

        return "bus/legalPersonEdit";
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

        //获取 机构的详情信息
        LegalPerson legalPerson = legalPersonService.get(pid);
        model.addAttribute("legalPerson", legalPerson);
        //获取附属信息
        List<NaturalPerson> naturalPersonList = naturalPersonService.serachNaturalByPid(pid);
        model.addAttribute("naturalPersonList", naturalPersonList);
        //获取权益信息
        List<LegalInviolableRights> inviolableRights = legalInviolableRightsService.searchByPid(pid);
        model.addAttribute("inviolableRights", inviolableRights);
        return "bus/legalPersonView";
    }

    /**
     * 保存
     *
     * @param legalPerson
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(LegalPerson legalPerson) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = legalPersonService.exists(legalPerson.getPid());
        try {
            if (isExist) {

                legalPersonService.update(legalPerson);
            } else {
                legalPersonService.insert(legalPerson);
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
            //此处为软删除  将状态改为0
//			legalPersonService.deleteByPrimaryKey(pid);
            LegalPerson legalPerson = legalPersonService.get(pid);
            legalPerson.setValidStatus("0");
            legalPersonService.update(legalPerson);
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

        boolean exist = legalPersonService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 获取机构的详情信息
     *
     * @return
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    public Map<String, Object> detail(LegalPerson legalPerson) {

        Map<String, Object> tips = new HashMap<String, Object>();
        //查找机构信息
        List<LegalPerson> legalPersonList = legalPersonService.search(legalPerson);
        tips.put("list", legalPersonList);
        return tips;
    }

    /**
     * 机构加入 黑名单
     *
     * @return
     */
    @RequestMapping(value = "/black")
    @ResponseBody
    public Map<String, Object> enterBlack(@RequestBody Map map) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //加入黑名单的pid
        int pid = Integer.parseInt(map.get("pid").toString());
        //加入黑名单原因
        String blackCause = map.get("blackCause").toString();
        //提交人
        String commitName = map.get("commitName").toString();
        //职位
        String commitDutyCode = map.get("commitDutyCode").toString();
        // 机构黑名单新增一条数据
        LegalPerson legalPerson = legalPersonService.get(pid);
        JSONObject jsonObject = JSONObject.fromObject(legalPerson);
        jsonObject.put("blackCause", blackCause);
        jsonObject.put("commitName", commitName);
        jsonObject.put("commitDutyCode", commitDutyCode);
        jsonObject.put("blackDate", DateTime.now());
        LegalPersonBlack legalPersonBlack = (LegalPersonBlack) JSONObject.toBean(jsonObject, LegalPersonBlack.class);
        try {
            legalPersonBlackService.insert(legalPersonBlack);
            // 加入黑名单之后，本条数据不再展示
            legalPerson.setValidStatus("0");
            legalPersonService.update(legalPerson);
            tips.put("Msg", "success");
        } catch (Exception e) {
            tips.put("Msg", "fail");
            System.out.println(e.getMessage());
        }
        return tips;
    }

    /**
     * 机构  关联解除
     *
     * @return
     */
    @RequestMapping(value = "/detach")
    @ResponseBody
    public Map<String, Object> enterRemove(@RequestBody Map map) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //关联解除机构pid
        int pid = Integer.parseInt(map.get("pid").toString());
        //关联解除机构原因
        String blackCause = map.get("removeCause").toString();
        //提交人
        String commitName = map.get("commitName").toString();
        //职位
        String commitDutyCode = map.get("commitDutyCode").toString();
        // 关联解除机构新增一条数据
        LegalPerson legalPerson = legalPersonService.get(pid);
        JSONObject jsonObject = JSONObject.fromObject(legalPerson);
        jsonObject.put("removeCause", blackCause);
        jsonObject.put("commitName", commitName);
        jsonObject.put("commitDutyCode", commitDutyCode);
        jsonObject.put("removeDate", DateTime.now());
        LegalPersonRemove legalPersonRemove = (LegalPersonRemove) JSONObject.toBean(jsonObject, LegalPersonRemove.class);
        try {
            legalPersonRemoveService.insert(legalPersonRemove);
            // 关联解除之后，本条数据不再展示  --物理删除
            legalPersonService.deleteByPrimaryKey(pid);
            tips.put("Msg", "success");
        } catch (Exception e) {
            tips.put("Msg", "fail");
            System.out.println(e.getMessage());
        }
        return tips;
    }

    /**
     * 机构 黑名单解除
     *
     * @return
     */
    @RequestMapping(value = "/blackRemove/{pid}")
    @ResponseBody
    public Map<String, Object> blackRemove(@PathVariable Integer pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Boolean isExist = legalPersonService.exists(pid);
            if (isExist) {
                //将原来数据改为有效
                LegalPerson legalPerson = legalPersonService.get(pid);
                legalPerson.setValidStatus("1");
                legalPersonService.update(legalPerson);
                // 黑名单解除之后，本条数据不再展示  --物理删除
                legalPersonBlackService.deleteByPrimaryKey(pid);
            } else {
                logger.info(pid + "数据不存在");
            }
            tips.put("Msg", "success");
        } catch (Exception e) {
            tips.put("Msg", "fail");
            System.out.println(e.getMessage());
        }
        return tips;
    }

}