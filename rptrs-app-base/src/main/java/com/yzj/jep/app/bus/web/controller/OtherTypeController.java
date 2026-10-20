/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:35
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/bus/otherType")
public class OtherTypeController extends CrudController<TraOther> {
    private static Logger logger = LoggerFactory.getLogger(OtherTypeController.class);

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private TraEnclosureService traEnclosureService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/otherTypeList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraOther> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, 1));
        if (filters.stream().noneMatch(filter -> "houseManageOrg".equals(filter.getProperty()))) {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
            filters.add(new Filter("houseManageOrg", Operator.IN, codes));
        }
        traOtherService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String unitCode = userProfile.getUnitCode();
        TraOther traOther = new TraOther();
        traOther.setStatusComment(unitCode);
        traOther.setRegulatorOth1("YBJH");
        model.addAttribute("otherType", traOther);

        return "bus/otherTypeEdit";
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

        TraOther otherType = traOtherService.get(pid);
        model.addAttribute("otherType", otherType);
        model.addAttribute("opt", "edit");

        return "bus/otherTypeEdit";
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

        TraOther otherType = traOtherService.get(pid);
        model.addAttribute("otherType", otherType);
        return "bus/otherTypeView";
    }

    /**
     * 保存
     *
     * @param otherType
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public Map<String, Object> save(TraOther otherType, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            String transNum = null;

            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            otherType.setHouseManageOrg(userProfile.getUnitCode());

            Integer relId = otherType.getRelId();
            String relType = otherType.getRelType();
            BigDecimal transationAmount = otherType.getTransationAmount();
            BigDecimal diff = null;
            if (otherType.getPid() != null) {
                TraOther traOther = traOtherService.get(otherType.getPid());
                if ("1".equals(traOther.getIsEffective())) {
                    BigDecimal transationAmount1 = traOther.getTransationAmount();
                    diff = transationAmount.subtract(transationAmount1);
                }
            }
            boolean importantTrade = TraUtil.isImportantTrade(transationAmount, relId, relType, diff);
            if (importantTrade) {
                otherType.setImportantTrade("1");
            } else {
                otherType.setImportantTrade("0");
            }
            if (otherType.getPid() != null) {
                otherType.setStatus("3");
                traOtherService.updateSelective(otherType);
                transNum = traOtherService.get(otherType.getPid()).getTransNum();
            } else {
                otherType.setDelflag("1");
                otherType.setStatus("3");
                otherType.setDataResource("1");
                otherType.setTranType("4");
                otherType.setIsEffective("0");
                otherType.setRegulatorOth1("YBJH");
                otherType.setTransNum(BatchNumberUtil.makeBatchNumber());
                traOtherService.insert(otherType);
                transNum = otherType.getTransNum();
                String msg = "";
                List<Duty> dutys = dutyService.select(new Filter.Builder()
                        .like("positionCode", "130@" + unitCode)
                        .like("dutySource", DutySource.ROLE.toString())
                        .build());
                if ("1".equals(otherType.getImportantTrade())) {
                    msg = "重大关联交易";
                } else {
                    msg = "一般关联交易";
                }
                if (CollectionUtils.isNotEmpty(dutys)) {
                    for (Duty duty : dutys) {
                        notifyService.createRemind(duty.getStaffCode(), "发生" + msg + ",请处理", "发生" + msg + ",请处理");
                    }
                }
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traEnclosureService.updateTransNum(docTableSelectItem, transNum);

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
            TraOther traOther = new TraOther();
            traOther.setPid(pid);
            traOther.setDelflag("0");
            traOtherService.updateSelective(traOther);
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

        boolean exist = traOtherService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 更改审批状态
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid, String statusComment) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            TraOther traOther = new TraOther();
            traOther.setPid(pid);
            traOther.setStatus("4");
            traOther.setStatusComment(statusComment);
            traOtherService.updateSelective(traOther);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

    /**
     * 审批页面跳转
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/submit/{pid}")
    public String subAudit(@PathVariable Integer pid, Model model) {
        TraOther traOther = traOtherService.get(pid);
        model.addAttribute("otherType", traOther);
        return "bus/otherTypeSubmit";
    }

    /**
     * 根据条件查询关联方信息
     */
    @RequestMapping(value = "/getRelInfo")
    @ResponseBody
    public Map<String, Object> getRelInfo(TraOther traOther) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            String relatedName = traOther.getRelatedName();
            String identNumber = traOther.getIdentNumber();
            String identlfyType = traOther.getIdentlfyType();
            tips = TraUtil.getRelInfo(unitCode, identNumber, identlfyType, relatedName);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }


    /*
     * 任务下发
     * */
    @RequestMapping(value = "/otherTypeSend")
    public String otherTypeSend(Model model) {

        return "bus/otherTypeSend";

    }


    /*
     * 确认下发
     * */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    public Map taskSend(String unitCode, String memo, String sendOrgId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放疑似机构给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendOrgId).build();

        TraOther traOther = traOtherService.get(filers);
        //更换管库机构为下发机构
        traOther.setHouseManageOrg(unitCode);
        //更新疑似机构状态为待补录

        traOther.setStatus("2");
        traOther.setStatusComment(memo);

        try {
            //查出指定机构角色为业务岗的人并通知补录
            List<Duty> dutys = dutyService.select(new Filter.Builder()
                    .like("positionCode", "120@" + unitCode)
                    .like("dutySource", DutySource.ROLE.toString())
                    .build());
            if (CollectionUtils.isNotEmpty(dutys)) {
                for (Duty duty : dutys) {
                    //通知补录人
//                    *//*创建提醒*//*
                    notifyService.createRemind(duty.getStaffCode(), "机构[" + traOther.getRelatedName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。", "机构[" + traOther.getIdentNumber() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。");
                }
                traOtherService.update(traOther);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_TASKSEND);
            } else {
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "该机构未设业务岗");
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

}