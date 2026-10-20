/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年03月22日 16:09:52
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/traInternaiTran")
public class TraInternaiTranController extends CrudController<TraCredit> {
    private static Logger logger = LoggerFactory.getLogger(TraInternaiTranController.class);

    @Autowired
    private TraCreditService traCreditService;


    @Autowired
    private TraCreditBzrService traCreditBzrService;

    @Autowired
    private TraEnclosureService traEnclosureService;

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    @Autowired
    private RelService relService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private InsiderOrgService insiderOrgService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traInternaiTranList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraCredit> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        filters.add(new Filter("delflag", Operator.EQ, 1));
        filters.add(new Filter("isInsider", Operator.EQ, 1));
//        filters.add(new Filter("manageOrg", Operator.EQ, userProfile.getUnitCode()));
        traCreditService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/traInternaiTranEdit";
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
        TraCredit traCredit = traCreditService.get(pid);
        model.addAttribute("traCredit", traCredit);
        model.addAttribute("opt", "edit");
        return "bus/traCreditEdit";
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
        TraCredit traCredit = traCreditService.get(pid);
        model.addAttribute("traCredit", traCredit);
        return "bus/traInternaiTranView";
    }


    /**
     * 保存
     *
     * @param traCredit
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> save(TraCredit traCredit, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<>();

        Boolean isExist = traCreditService.exists(traCredit.getPid());
        try {
            if (isExist) {

                traCreditService.update(traCredit);
            } else {
                traCredit.setIsInsider("1");
                traCredit.setDelflag("1");
                traCredit.setTranType("1");
                traCredit.setStatus("3");
                traCredit.setDataResource("1");
                traCredit.setIsEffective("0");
//                traCredit.setStatusComment(statusComment);
                traCreditService.insert(traCredit);
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
            TraCredit traCredit = new TraCredit();
            traCredit.setPid(pid);
            traCredit.setDelflag("0");
            traCreditService.updateSelective(traCredit);
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

        boolean exist = traCreditService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 根据条件查询关联方信息
     */
    @RequestMapping(value = "/getRelInfo")
    @ResponseBody
    public Map<String, Object> getRelInfo(TraCredit traCredit) {
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String unitCode = userProfile.getUnitCode();
        String identNumber = traCredit.getIdentNumber();
        String identifyType = traCredit.getIdentifyType();
        String relatedName = traCredit.getRelatedName();
        Map<String, Object> relInfo = TraUtil.getRelInfo(unitCode, identNumber, identifyType, relatedName);
        return relInfo;
    }

    /**
     * 更改审批中
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}/{statusComment}")
    @ResponseBody
    @Transactional
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid, @PathVariable String statusComment) {
        Map<String, Object> tips = Maps.newHashMap();
        TraCredit traCredit = new TraCredit();
        traCredit.setPid(pid);
        traCredit.setStatus("4");
        traCredit.setStatusComment(statusComment);
        traCreditService.updateSelective(traCredit);
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 跳转审批页面
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        TraCredit traCredit = traCreditService.get(pid);
        model.addAttribute("traCredit", traCredit);
        return "bus/traCreditSubmit";
    }

    /**
     * 内部人查询信息
     *
     * @param identNumber
     * @return
     */
    @RequestMapping(value = "/geRelOrgInfo")
    @ResponseBody
    public Map<String, Object> geRelOrgInfo(String identNumber) {
        logger.info("identNumber = {} ", identNumber);

        HashMap<String, Object> map = new HashMap<>();
        //identNumber 查询 bus_rel_org unisc_code
        List<Filter> filters = new ArrayList<>();

        filters.add(new Filter("uniscCode", Operator.EQ, identNumber));

        try {
            RelOrg relOrg = relOrgService.get(filters);
            if (null != relOrg) {
                map.put("success", true);
                map.put("orgName", relOrg.getOrgName());
                map.put("shareholdingProportion", relOrg.getShareholdingProportion());
                map.put("voteProportion", relOrg.getVoteProportion());
            } else {
                map.put("success", false);
                map.put("msg", "identNumber : " + identNumber + " 查询为空！");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "系统异常！");
            logger.error("系统异常msg ： " + e.getMessage());
            logger.error("系统异常cause ： " + e.getCause());
        }
        return map;
    }

    //查询内部人信息
    private Map<String, Object> isInsider(String identNumber) {
        HashMap<String, Object> tips = new HashMap<>();
        RelOrg relOrg = new RelOrg();
        if (null != identNumber) {
            relOrg.setUniscCode(identNumber);
            relOrg.setIsInsider("1");
            relOrg.setStatus("3");
            List<RelOrg> relOrgList = insiderOrgService.selectListNew(relOrg);
            if (relOrgList.size() == 1) {
                RootOrgInfo orgInfo = rootOrgInfoService.get(1);
                String totalShareAmount = orgInfo.getTotalShareAmount();
                relOrg = relOrgList.get(0);
                //此处判断定位码   参考 资产转移类模块 实现
                if (StringUtils.isNotEmpty(relOrg.getAligCode())) {
                    String holeShareAmount = relOrg.getHoleShareAmount();
                    if (null != holeShareAmount) {
                        Float shareholdingProportion = Float.parseFloat(holeShareAmount) / Float.parseFloat(totalShareAmount);
                        tips.put("shareholdingProportion", shareholdingProportion);
                    }
                    tips.put("voteProportion", relOrg.getVoteProportion());
                    //tips.put("relType", "ORG");
                    tips.put("relId", relOrg.getPid());
                    tips.put("orgName", relOrg.getOrgName());
                    tips.put("identifyType", relOrg.getIdentifyType());
                    tips.put("identifyNumber", relOrg.getUniscCode());
                    tips.put("isInsider", relOrg.getIsInsider());
                    //tips.put("inInsiderQuota", relOrg.getPresetLimit());
                    tips.put("data", relOrg);
                    tips.put("success", true);
                    //tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                    return tips;
                } else {
                    tips.put("success", false);
                    tips.put("msg", "无内部人");
                    return tips;
                }
            }
        }
        return tips;
    }

    @RequestMapping(value = "/getInfos")
    @ResponseBody
    public Map<String, Object> getInfos(String identNumber) {
        return this.isInsider(identNumber);
    }


    /*
     * 任务下发
     * */
    @RequestMapping(value = "/traInternaiTranSend")
    public String traInternaiTranSend(Model model) {

        return "bus/traInternaiTranSend";

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
//        RelOrg relOrg = relOrgService.get(filers);
        TraCredit traCredit = traCreditService.get(filers);
        //更换管库机构为下发机构
//        relOrg.setMgerOrg(unitCode);
        //更新疑似机构状态为待补录
//        relOrg.setApprovalStatus("2");
//        relOrg.setStatusComment(memo);
        traCredit.setStatus("2");
        traCredit.setStatusComment(memo);

        try {
            //查出指定机构角色为业务岗的人并通知补录
            List<Duty> dutys = dutyService.select(new Filter.Builder()
                    .like("positionCode", "120@" + unitCode)
                    .like("dutySource", DutySource.ROLE.toString())
                    .build());
            if (CollectionUtils.isNotEmpty(dutys)) {
                for (Duty duty : dutys) {
                    //通知补录人
                    /*创建提醒*/
                    notifyService.createRemind(duty.getStaffCode(), "机构[" + traCredit.getRelatedName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。", "机构[" + traCredit.getIdentNumber() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。");
                }
                traCreditService.update(traCredit);
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