/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年03月22日 16:09:52
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
@RequestMapping("/bus/traCredit")
public class TraCreditController extends CrudController<TraCredit> {
    private static Logger logger = LoggerFactory.getLogger(TraCreditController.class);

    @Autowired
    private TraCreditService traCreditService;

    @Autowired
    private TraEnclosureService traEnclosureService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private TraBalanceDeductionService traBalanceDeductionService;

    @Autowired
    private ProductService producrService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traCreditList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraCredit> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, 1));
        if (filters.stream().noneMatch(filter -> "manageOrg".equals(filter.getProperty()))) {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
            filters.add(new Filter("manageOrg", Operator.IN, codes));
        }
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
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        TraCredit traCredit = new TraCredit();
        traCredit.setManageOrg(userProfile.getUnitCode());
        traCredit.setRegulatorOth1("YBJH");
        traCredit.setTranType("1");
        model.addAttribute("traCredit", traCredit);
        return "bus/traCreditEdit";
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
        return "bus/traCreditView";
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
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            String transNum = null;
            Integer relId = traCredit.getRelId();//获取关联id  拿到值为null
            String relType = traCredit.getRelType();//获取 关联类型
            BigDecimal creditAmount = BigDecimal.valueOf(traCredit.getCreditAmount());//获取授信金额
            if (traCredit.getPid() != null) {
                TraCredit traCredit1 = traCreditService.get(traCredit.getPid());
                if ("1".equals(traCredit1.getIsEffective())) {//判断是否生效
                    creditAmount = creditAmount.subtract(BigDecimal.valueOf(traCredit1.getCreditAmount()));
                } else {
                    creditAmount = getBalanceDeduction(traCredit);
                }
            } else {
                creditAmount = getBalanceDeduction(traCredit);
            }
            Map<String, Object> stringObjectMap = TraUtil.JudgeAllCreditAmount(creditAmount);
            if (!(boolean) stringObjectMap.get("success")) {
                return stringObjectMap;
            }
            if ("PSN".equals(relType)) {
                Map<String, Object> stringObjectMap1 = TraUtil.JudgePsnCreditBalance(relId, creditAmount);
                if (!(boolean) stringObjectMap1.get("success")) {
                    return stringObjectMap1;
                }
            }
            if ("ORG".equals(relType)) {
                Map<String, Object> stringObjectMap1 = TraUtil.JudgeOrgCreditBalance(relId, creditAmount);
                if (!(boolean) stringObjectMap1.get("success")) {
                    return stringObjectMap1;
                }
            }
            if (traCredit.getPid() != null) {
                traCredit.setStatus("3");
                traCreditService.updateSelective(traCredit);
                transNum = traCreditService.get(traCredit.getPid()).getTransNum();
            } else {
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                String unitCode = userProfile.getUnitCode();
                traCredit.setManageOrg(unitCode);
                traCredit.setDelflag("1");
                traCredit.setStatus("3");
                traCredit.setDataResource("1");
                traCredit.setTranType("1");
                traCredit.setIsEffective("0");
                traCredit.setTransNum(BatchNumberUtil.makeBatchNumber());
                traCreditService.insert(traCredit);
                transNum = traCredit.getTransNum();
                String msg = "";
                List<Duty> dutys = dutyService.select(new Filter.Builder()
                        .like("positionCode", "130@" + unitCode)
                        .like("dutySource", DutySource.ROLE.toString())
                        .build());
                if ("1".equals(traCredit.getImportantTrade())) {
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
        Map<String, Object> tips = Maps.newHashMap();
        try {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            String identNumber = traCredit.getIdentNumber();
            String identifyType = traCredit.getIdentifyType();
            String relatedName = traCredit.getRelatedName();
            tips = TraUtil.getRelInfo(unitCode, identNumber, identifyType, relatedName);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 更改审批中
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid, String statusComment) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            TraCredit traCredit = new TraCredit();
            traCredit.setPid(pid);
            traCredit.setStatus("4");
            traCredit.setStatusComment(statusComment);
            traCreditService.updateSelective(traCredit);
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
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        TraCredit traCredit = traCreditService.get(pid);
        model.addAttribute("traCredit", traCredit);
        return "bus/traCreditSubmit";
    }

    @RequestMapping(value = "/getCreditBalance")
    @ResponseBody
    public Map<String, Object> getCreditBalance(TraCredit traCredit) {
        Map<String, Object> tips = Maps.newHashMap();
        String internalApplyNo = traCredit.getInternalApplyNo();
        TraCredit traCredit1 = new TraCredit();
        traCredit1.setInternalApplyNo(internalApplyNo);
        traCredit1.setIsLetter("0");
        traCredit1.setDelflag("1");
        TraCredit credit = traCreditService.get(traCredit1);
        if (credit != null) {
            BigDecimal creditBalance = credit.getCreditBalance() == null ? BigDecimal.ZERO : credit.getCreditBalance();
            tips.put("creditBalance", creditBalance);
            tips.put("credit", credit);
        }
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 判断是否重大关联交易
     */
    @RequestMapping(value = "/judgeIsImportTrade")
    @ResponseBody
    public Map<String, Object> judgeIsImportTrade(TraCredit traCredit, String relType, Integer relId, Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        boolean importantTrade;
        BigDecimal diff = null;
        BigDecimal creditAmount = BigDecimal.valueOf(traCredit.getCreditAmount());
        try {
            if (pid != null) {
                TraCredit credit = traCreditService.get(pid);
                if ("1".equals(credit.getIsEffective())) {
                    Long creditAmount1 = credit.getCreditAmount();
                    diff = creditAmount.subtract(BigDecimal.valueOf(creditAmount1));
                }
            }
            importantTrade = TraUtil.isImportantTrade(creditAmount, relId, relType, diff);
            tips.put(WebCode.SUCCESS, true);
            tips.put("isImportTrade", importantTrade);
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
    @RequestMapping(value = "/traCreditSend")
    public String traCreditSend(Model model) {

        return "bus/traCreditSend";

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

        TraCredit traCredit = traCreditService.get(filers);
        //更换管库机构为下发机构
        //traCredit.setHouseManageOrg(unitCode);
        //更新疑似机构状态为待补录

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
//                    *//*创建提醒*//*
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

    /**
     * 扣除项计算
     */
    public BigDecimal getBalanceDeduction(TraCredit traCredit) {
        BigDecimal creditAmount = BigDecimal.valueOf(traCredit.getCreditAmount());
        TraBalanceDeduction traBalanceDeduction = traBalanceDeductionService.get(1);
        if ("1".equals(traBalanceDeduction.getSecuritDeposit())) {
            BigDecimal securitDeposit = traCredit.getSecuritDeposit() == null ? BigDecimal.ZERO : traCredit.getSecuritDeposit();
            creditAmount = creditAmount.subtract(securitDeposit);
        }
        if ("1".equals(traBalanceDeduction.getNationalDebt())) {
            BigDecimal nationalDebt = traCredit.getNationalDebt() == null ? BigDecimal.ZERO : traCredit.getNationalDebt();
            creditAmount = creditAmount.subtract(nationalDebt);
        }
        if ("1".equals(traBalanceDeduction.getBankDeposit())) {
            BigDecimal bankDeposit = traCredit.getBankDeposit() == null ? BigDecimal.ZERO : traCredit.getBankDeposit();
            creditAmount = creditAmount.subtract(bankDeposit);
        }
        return creditAmount;
    }
}