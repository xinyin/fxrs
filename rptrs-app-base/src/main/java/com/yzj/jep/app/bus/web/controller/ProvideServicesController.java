/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:38
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
import com.yzj.jep.app.bus.service.TraEnclosureService;
import com.yzj.jep.app.bus.service.TraServiceService;
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
@RequestMapping("/bus/provideServices")
public class ProvideServicesController extends CrudController<TraService> {
    private static Logger logger = LoggerFactory.getLogger(ProvideServicesController.class);

    @Autowired
    private TraServiceService provideServicesService;

    @Autowired
    private TraEnclosureService traEnclosureService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private UnitService unitService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/provideServicesList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraService> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, 1));
        if (filters.stream().noneMatch(filter -> "houseManageOrg".equals(filter.getProperty()))) {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
            filters.add(new Filter("houseManageOrg", Operator.IN, codes));
        }
        provideServicesService.query(page, filters, orders);
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
        TraService traService = new TraService();

        traService.setStatusComment(unitCode);

        traService.setRegulatorOth1("YBJH");
        model.addAttribute("provideServices", traService);

        return "bus/provideServicesEdit";
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

        TraService provideServices = provideServicesService.get(pid);
        model.addAttribute("provideServices", provideServices);
        model.addAttribute("opt", "edit");

        return "bus/provideServicesEdit";
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

        TraService provideServices = provideServicesService.get(pid);
        model.addAttribute("provideServices", provideServices);

        return "bus/provideServicesView";
    }

    /**
     * 保存
     *
     * @param provideServices
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public Map<String, Object> save(TraService provideServices, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            String transNum = null;

            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            provideServices.setHouseManageOrg(unitCode);
            Integer relId = provideServices.getRelId();
            String relType = provideServices.getRelType();
            BigDecimal transationAmount = provideServices.getTransationAmount();
            BigDecimal diff = null;
            if (provideServices.getPid() != null) {
                TraService traService = provideServicesService.get(provideServices.getPid());
                if ("1".equals(traService.getIsEffective())) {
                    BigDecimal transationAmount1 = traService.getTransationAmount();
                    diff = transationAmount.subtract(transationAmount1);
                }
            }
            boolean importantTrade = TraUtil.isImportantTrade(transationAmount, relId, relType, diff);
            if (importantTrade) {
                provideServices.setImportantTrade("1");
            } else {
                provideServices.setImportantTrade("0");
            }
            if (provideServices.getPid() != null) {
                provideServices.setStatus("3");
                provideServicesService.updateSelective(provideServices);
                transNum = provideServicesService.get(provideServices.getPid()).getTransNum();
            } else {
                provideServices.setStatus("3");
//                provideServices.setStatusComment("1");
                provideServices.setDelflag("1");
                provideServices.setDataResource("1");
                provideServices.setTranType("3");
                provideServices.setIsEffective("0");
                provideServices.setRegulator("YBJH");
                provideServices.setTransNum(BatchNumberUtil.makeBatchNumber());
                provideServicesService.insert(provideServices);
                transNum = provideServices.getTransNum();
                String msg = "";
                List<Duty> dutys = dutyService.select(new Filter.Builder()
                        .like("positionCode", "130@" + unitCode)
                        .like("dutySource", DutySource.ROLE.toString())
                        .build());
                if ("1".equals(provideServices.getImportantTrade())) {
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
            TraService traService = new TraService();
            traService.setPid(pid);
            traService.setDelflag("0");
            provideServicesService.updateSelective(traService);
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

        boolean exist = provideServicesService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 更改审批状态
     */
    @RequestMapping(value = "changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid, String statusComment) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            TraService traService = new TraService();
            traService.setPid(pid);
            traService.setStatus("4");
            traService.setStatusComment(statusComment);
            provideServicesService.updateSelective(traService);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 跳转审批页面
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/submit/{pid}")
    public String subAudit(@PathVariable Integer pid, Model model) {
        TraService traService = provideServicesService.get(pid);
        model.addAttribute("provideServices", traService);
        return "bus/provideServicesSubmit";
    }

    /**
     *
     */
    @RequestMapping(value = "getRelInfo")
    @ResponseBody
    public Map<String, Object> getRelInfo(TraAsset traAsset) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            String identNumber = traAsset.getIdentNumber();
            String identifyType = traAsset.getIdentifyType();
            String relatedName = traAsset.getRelatedName();
            tips = TraUtil.getRelInfo(unitCode, identNumber, identifyType, relatedName);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /*
     * 任务下发
     * */
    @RequestMapping(value = "/provideServicesSend")
    public String provideServicesSend(Model model) {

        return "bus/provideServicesSend";

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
        TraService traService = provideServicesService.get(filers);

        //更换管库机构为下发机构
        traService.setHouseManageOrg(unitCode);
        //更新疑似机构状态为待补录

        traService.setStatus("2");
        traService.setStatusComment(memo);

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
                    notifyService.createRemind(duty.getStaffCode(), "机构[" + traService.getRelatedName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。", "机构[" + traService.getIdentNumber() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。");
                }

                provideServicesService.update(traService);
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