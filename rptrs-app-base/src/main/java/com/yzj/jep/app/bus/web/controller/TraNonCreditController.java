/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:35
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.bus.model.TraOther;
import com.yzj.jep.app.bus.service.InsiderOrgService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.service.TraEnclosureService;
import com.yzj.jep.app.bus.service.TraOtherService;
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
@RequestMapping("/bus/traNoCredit")
public class TraNonCreditController extends CrudController<TraOther> {
    private static Logger logger = LoggerFactory.getLogger(TraNonCreditController.class);

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private TraEnclosureService traEnclosureService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    @Autowired
    private InsiderOrgService insiderOrgService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traNoCreditList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraOther> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String unitCode = userProfile.getUnitCode();
//        filters.add(new Filter("houseManageOrg", Operator.EQ, unitCode));
        filters.add(new Filter("delflag", Operator.EQ, 1));

        filters.add(new Filter("isInsider", Operator.EQ, 1));

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

        return "bus/traNoCreditEdit";
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

        return "bus/traNoCreditEdit";
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
        return "bus/traNoCreditView";
    }


    /**
     * 保存
     *
     * @param traNonCredit
     * @return
     */
    /*@RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public Map<String, Object> save(TraOther traNonCredit, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            String transNum = null;

            UserProfile userProfile = UserProfileUtils.getUserProfile();
            traNonCredit.setHouseManageOrg(userProfile.getUnitCode());

            Integer relId = traNonCredit.getRelId();
            String relType = traNonCredit.getRelType();
            BigDecimal transationAmount = traNonCredit.getTransationAmount();
            boolean importantTrade = TraUtil.isImportantTrade(transationAmount, relId, relType);
            if (importantTrade) {
                traNonCredit.setImportantTrade("1");
            } else {
                traNonCredit.setImportantTrade("0");
            }
            if (traNonCredit.getPid() != null) {
                traNonCredit.setStatus("3");
                traOtherService.updateSelective(traNonCredit);
                transNum = traOtherService.get(traNonCredit.getPid()).getTransNum();
            } else {
                traNonCredit.setIsInsider("1");
                traNonCredit.setDelflag("1");
                traNonCredit.setTranType("4");
                traNonCredit.setStatus("3");
                traNonCredit.setDataResource("1");
                traNonCredit.setIsEffective("0");
                traNonCredit.setTransNum(BatchNumberUtil.makeBatchNumber());
                traOtherService.insert(traNonCredit);
                transNum = traNonCredit.getTransNum();
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
    }*/


    /**
     * 保存
     *
     * @param traOther
     * @param traEnclosureVo
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> save(TraOther traOther, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<>();

        Boolean isExist = traOtherService.exists(traOther.getPid());
        try {
            if (isExist) {

                traOtherService.update(traOther);
            } else {
                traOther.setIsInsider("1");
                traOther.setDelflag("1");
                traOther.setTranType("4");
                traOther.setStatus("3");
                traOther.setDataResource("1");
                traOther.setIsEffective("0");
                traOtherService.insert(traOther);
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
                    tips.put("identlfyType", relOrg.getIdentifyType());
                    tips.put("identNumber", relOrg.getUniscCode());
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
    @RequestMapping(value = "/traNoCreditSend")
    public String traNoCreditSend(Model model) {

        return "bus/traNoCreditSend";

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
//        TraCredit traCredit = traCreditService.get(filers);
        TraOther traOther = traOtherService.get(filers);
        //更换管库机构为下发机构
//        relOrg.setMgerOrg(unitCode);
        //更新疑似机构状态为待补录
//        traCredit.setStatus("2");
//        traCredit.setStatusComment(memo);
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
                    /*创建提醒*/
                    notifyService.createRemind(duty.getStaffCode(), "机构[" + traOther.getRelatedName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。", "机构[" + traOther.getIdentNumber() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。");
                }
//                traCreditService.update(traCredit);
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