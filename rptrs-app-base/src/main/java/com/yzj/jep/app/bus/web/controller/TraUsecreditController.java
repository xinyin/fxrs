/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月27日 16:35:32
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.service.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.TraUsecreditService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/traUsecredit")
public class TraUsecreditController extends CrudController<TraUsecredit> {
    private static Logger logger = LoggerFactory.getLogger(TraUsecreditController.class);

    @Autowired
    private TraUsecreditService traUsecreditService;

    @Autowired
    private TraCreditService traCreditService;

    @Autowired
    private UnitService unitService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traUsecreditList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraUsecredit> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        if (filters.stream().noneMatch(filter -> "manageOrg".equals(filter.getProperty()))) {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
            filters.add(new Filter("manageOrg", Operator.IN, codes));
        }
        traUsecreditService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/traUsecreditEdit";
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

        TraUsecredit traUsecredit = traUsecreditService.get(pid);
        model.addAttribute("traUsecredit", traUsecredit);
        model.addAttribute("opt", "edit");

        return "bus/traUsecreditEdit";
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

        TraUsecredit traUsecredit = traUsecreditService.get(pid);
        model.addAttribute("traUsecredit", traUsecredit);

        return "bus/traUsecreditView";
    }

    /**
     * 保存
     *
     * @param traUsecredit
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraUsecredit traUsecredit) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = traUsecreditService.exists(traUsecredit.getPid());
        TraCredit traCredit = new TraCredit();
        traCredit.setDelflag("1");
        traCredit.setIsEffective("1");
        traCredit.setInternalApplyNo(traUsecredit.getInternalApplyNo());
        TraCredit credit = traCreditService.get(traCredit);
        if (credit == null) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "不存在的内部审批编号");
            return tips;
        } else {
            Long creditAmount = credit.getCreditAmount();
            if (traUsecredit.getThisTraAmount().compareTo(BigDecimal.valueOf(creditAmount)) > 0) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "用信金额大于授信额度");
                return tips;
            }
        }
        try {
            if (isExist) {
                if ("1".equals(traUsecredit.getIsEffective())) {
                    TraUsecredit usecredit = traUsecreditService.get(traUsecredit.getPid());
                    BigDecimal thisTraAmount = usecredit.getThisTraAmount();
                    BigDecimal thisTraDiff = traUsecredit.getThisTraAmount().subtract(thisTraAmount);
                    traUsecredit.setThisTraDiff(thisTraDiff);
                }
                traUsecredit.setStatus("3");
                traUsecreditService.updateSelective(traUsecredit);
            } else {
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                String unitCode = userProfile.getUnitCode();
                traUsecredit.setManageOrg(unitCode);
                traUsecredit.setDataSources("1");
                traUsecredit.setIsEffective("0");
                traUsecredit.setStatus("3");
                traUsecredit.setDelflag("1");
                traUsecredit.setTransNum(BatchNumberUtil.makeBatchNumber());
                traUsecreditService.insert(traUsecredit);
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
            TraUsecredit traUsecredit = new TraUsecredit();
            traUsecredit.setPid(pid);
            traUsecredit.setDelflag("0");
            traUsecreditService.updateSelective(traUsecredit);
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

        boolean exist = traUsecreditService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 通过审批编号查询关联方信息
     */
    @RequestMapping(value = "/getRelInfo")
    @ResponseBody
    public Map<String, Object> getRelInfo(String internalApplyNo) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            List<Filter> filters = Lists.newArrayList();
            filters.add(Filter.eq("internalApplyNo", internalApplyNo));
            filters.add(Filter.eq("delflag", "1"));
            TraCredit traCredit = traCreditService.get(filters);
            if (traCredit != null) {
                tips.put("traCredit", traCredit);
                tips.put(WebCode.SUCCESS, true);
            } else {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "该审批编号不存在");
            }

        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }

    /**
     * 审批页面跳转
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        TraUsecredit traUsecredit = traUsecreditService.get(pid);
        model.addAttribute("traUsecredit", traUsecredit);
        return "bus/traUsecreditSubmit";
    }

    /**
     * 更改审批状态
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            TraUsecredit traUsecredit = new TraUsecredit();
            traUsecredit.setPid(pid);
            traUsecredit.setStatus("4");
            traUsecreditService.updateSelective(traUsecredit);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }
}