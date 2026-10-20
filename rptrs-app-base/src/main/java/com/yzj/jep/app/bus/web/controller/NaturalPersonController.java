/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:37
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.*;
import net.sf.json.JSONObject;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Operator;
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
@RequestMapping("/bus/naturalPerson")
public class NaturalPersonController extends CrudController<NaturalPerson> {
    private static Logger logger = LoggerFactory.getLogger(NaturalPersonController.class);

    @Autowired
    private NaturalPersonService naturalPersonService;
    @Autowired
    private NaturalPersonBlackService naturalPersonBlackService;
    @Autowired
    private NaturalPersonRemoveService naturalPersonRemoveService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/naturalPersonList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<NaturalPerson> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("validStatus", Operator.EQ, "1"));
        naturalPersonService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/naturalPersonEdit";
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

        NaturalPerson naturalPerson = naturalPersonService.get(pid);
        model.addAttribute("naturalPerson", naturalPerson);
        model.addAttribute("opt", "edit");

        return "bus/naturalPersonEdit";
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

        NaturalPerson naturalPerson = naturalPersonService.get(pid);
        model.addAttribute("naturalPerson", naturalPerson);

        //详情
        NaturalRelService naturalRelService = SpringContextHolder.getBean(NaturalRelService.class);
        List<Filter> filters = new Filter.Builder().eq("naturalId", pid).build();
        List<NaturalRel> naturalRel = naturalRelService.select(filters);
        model.addAttribute("naturalRel", naturalRel);

        NaturalInviolableRightsService inviolableRightsService = SpringContextHolder.getBean(NaturalInviolableRightsService.class);
        List<Filter> filter = new Filter.Builder().eq("naturalPid", pid).build();
        List<NaturalInviolableRights> naturalInviolableRights = inviolableRightsService.select(filter);
        model.addAttribute("NaturalInviolableRights", naturalInviolableRights);

        return "bus/naturalPersonView";
    }

    /**
     * 保存
     *
     * @param naturalPerson
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(NaturalPerson naturalPerson) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = naturalPersonService.exists(naturalPerson.getPid());
        try {
            if (isExist) {

                naturalPersonService.update(naturalPerson);
            } else {
                naturalPersonService.insert(naturalPerson);
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
//			naturalPersonService.deleteByPrimaryKey(pid);
            NaturalPerson naturalPerson = naturalPersonService.get(pid);
            naturalPerson.setValidStatus("0");
            naturalPersonService.update(naturalPerson);
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

        boolean exist = naturalPersonService.exists(pid);
        tips.put("valid", !exist);

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
        NaturalPerson naturalPerson = naturalPersonService.get(pid);
        JSONObject jsonObject = JSONObject.fromObject(naturalPerson);
        jsonObject.put("blackCause", blackCause);
        jsonObject.put("commitName", commitName);
        jsonObject.put("commitDutyCode", commitDutyCode);
        jsonObject.put("blackDate", DateTime.now());
        NaturalPersonBlack naturalPersonBlack = (NaturalPersonBlack) JSONObject.toBean(jsonObject, NaturalPersonBlack.class);
        try {
            naturalPersonBlackService.insert(naturalPersonBlack);
            // 加入黑名单之后，本条数据不再展示
            naturalPerson.setValidStatus("0");
            naturalPersonService.update(naturalPerson);
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
        NaturalPerson naturalPerson = naturalPersonService.get(pid);
        JSONObject jsonObject = JSONObject.fromObject(naturalPerson);
        jsonObject.put("removeCause", blackCause);
        jsonObject.put("commitName", commitName);
        jsonObject.put("commitDutyCode", commitDutyCode);
        jsonObject.put("removeDate", DateTime.now());
        NaturalPersonRemove naturalPersonRemove = (NaturalPersonRemove) JSONObject.toBean(jsonObject, NaturalPersonRemove.class);
        try {
            naturalPersonRemoveService.insert(naturalPersonRemove);
            // 关联解除之后，本条数据不再展示
            naturalPerson.setValidStatus("0");
            naturalPersonService.update(naturalPerson);
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
            Boolean isExist = naturalPersonService.exists(pid);
            if (isExist) {
                //将原来数据改为有效
                NaturalPerson naturalPerson = naturalPersonService.get(pid);
                naturalPerson.setValidStatus("1");
                naturalPersonService.update(naturalPerson);
                // 黑名单解除之后，本条数据不再展示  --物理删除
                naturalPersonBlackService.deleteByPrimaryKey(pid);
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