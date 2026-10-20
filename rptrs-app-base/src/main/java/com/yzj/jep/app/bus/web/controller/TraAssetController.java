/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月22日 16:09:49
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

import com.yzj.jep.app.bus.service.TraAssetService;

@Controller
@RequestMapping("/bus/traAsset")
public class TraAssetController extends CrudController<TraAsset> {
    private static Logger logger = LoggerFactory.getLogger(TraAssetController.class);

    @Autowired
    private TraAssetService traAssetService;

    @Autowired
    private TraEnclosureService traEnclosureService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private DutyService dutyService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traAssetList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraAsset> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, 1));
        if (filters.stream().noneMatch(filter -> "manageOrg".equals(filter.getProperty()))) {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
            filters.add(new Filter("manageOrg", Operator.IN, codes));
        }
        traAssetService.query(page, filters, orders);
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
        TraAsset traAsset = new TraAsset();
        traAsset.setManageOrg(unitCode);
        traAsset.setRegulatorOth1("YBJH");
        model.addAttribute("traAsset", traAsset);
        return "bus/traAssetEdit";
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

        TraAsset traAsset = traAssetService.get(pid);
        model.addAttribute("traAsset", traAsset);
        model.addAttribute("opt", "edit");
        return "bus/traAssetEdit";
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
        TraAsset traAsset = traAssetService.get(pid);
        model.addAttribute("traAsset", traAsset);
        return "bus/traAssetView";
    }

    /**
     * 保存
     *
     * @param traAsset
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public Map<String, Object> save(TraAsset traAsset, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            String transNum = null;
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            traAsset.setManageOrg(unitCode);

            Integer relId = traAsset.getRelId();
            String relType = traAsset.getRelType();
            BigDecimal transationAmount = traAsset.getTransationAmount();
            BigDecimal diff = null;
            if (traAsset.getPid() != null) {
                TraAsset traAsset1 = traAssetService.get(traAsset.getPid());
                if ("1".equals(traAsset1.getIsEffective())) {
                    BigDecimal transationAmount1 = traAsset1.getTransationAmount();
                    diff = transationAmount.subtract(transationAmount1);
                }
            }
            boolean importantTrade = TraUtil.isImportantTrade(transationAmount, relId, relType, diff);
            if (importantTrade) {
                traAsset.setImportantTrade("1");
            } else {
                traAsset.setImportantTrade("0");
            }
            if (traAsset.getPid() != null) {
                traAsset.setStatus("3");
                traAssetService.updateSelective(traAsset);
                transNum = traAssetService.get(traAsset.getPid()).getTransNum();
            } else {
                traAsset.setDataResource("1");
                traAsset.setTranType("2");
                traAsset.setDelflag("1");
                traAsset.setStatus("3");
                traAsset.setIsEffective("0");
                traAsset.setTransNum(BatchNumberUtil.makeBatchNumber());
                traAsset.setRegulatorOth1("YBJH");
                traAssetService.insert(traAsset);
                transNum = traAsset.getTransNum();
                String msg = "";
                List<Duty> dutys = dutyService.select(new Filter.Builder()
                        .like("positionCode", "130@" + unitCode)
                        .like("dutySource", DutySource.ROLE.toString())
                        .build());
                if ("1".equals(traAsset.getImportantTrade())) {
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
            TraAsset traAsset = new TraAsset();
            traAsset.setPid(pid);
            traAsset.setDelflag("0");
            traAssetService.updateSelective(traAsset);
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

        boolean exist = traAssetService.exists(pid);
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
            TraAsset traAsset = new TraAsset();
            traAsset.setPid(pid);
            traAsset.setStatus("4");
            traAsset.setStatusComment(statusComment);
            traAssetService.updateSelective(traAsset);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
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
        TraAsset traAsset = traAssetService.get(pid);
        model.addAttribute("traAsset", traAsset);
        return "bus/traAssetSubmit";
    }

    /**
     * 获取关联方信息
     */
    @RequestMapping(value = "/getRelInfo")
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
    @RequestMapping(value = "/traAssetSend")
    public String traAssetSend(Model model) {

        return "bus/traAssetSend";

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

        TraAsset traAsset = traAssetService.get(filers);

        //更换管库机构为下发机构
//        traAsset.setHouseManageOrg(unitCode);
        //更新疑似机构状态为待补录

        traAsset.setStatus("2");
        traAsset.setStatusComment(memo);

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
                    notifyService.createRemind(duty.getStaffCode(), "机构[" + traAsset.getRelatedName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。", "机构[" + traAsset.getIdentNumber() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。");
                }
                traAssetService.update(traAsset);
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