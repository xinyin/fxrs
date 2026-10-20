/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:50
 *
 */

package com.yzj.jep.app.ins.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.RiskRatio;
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RiskRatioService;
import com.yzj.jep.app.enums.CompanyType;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.UserRoleUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.*;
import com.yzj.jep.app.wf.service.WorkflowOperation;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;

/**
 * 资金运用类
 */
@Controller
@RequestMapping("/ins/traFunds")
public class TraFundsController extends CrudController<TraFunds> {
    private static Logger logger = LoggerFactory.getLogger(TraFundsController.class);

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private RuleCheckService ruleCheckService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TraInsenclosureService traInsenclosureService;

    @Autowired
    private ApproveAssignerService approveAssignerService;

    @Autowired
    private RiskRatioService riskRatioService;

    @Autowired
    StaffService staffService;

    @Autowired
    private TraCounterpartyService traCounterpartyService;



    @Autowired
    WorkflowOperation workflowOperation;

    @Autowired
    private RelPsnService relPsnService;

    @ModelAttribute
    public void defaultMethod(Model model) {
        model.addAttribute("flag", "flag");
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "ins/traFunds/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        //查询登陆人机构及下辖机构的信息
     /*   UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
            //管户机构
            model.addAttribute("unitCode",unitCode);
            model.addAttribute("opt","MH");*/
        return "ins/traFundsList";
    }

    /**
     * 跳转到集中审议列表页面
     *
     * @return
     */
    @RequestMapping("/batchSendList")
    public String batchSendList() {
        return "ins/traFundsBatchList";
    }

    /**
     * 查询集中审议符合条件的数据
     *
     * @param pageNumber
     * @param pageSize
     * @param year
     * @param quarter
     * @return
     */
    @RequestMapping("/queryBatchSubmit")
    @ResponseBody
    public Map<String, Object> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<TraFunds> pageInfo = traFundsService.queryBatchSubmit(pageNumber, pageSize, year, quarter);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    @RequestMapping("/queryTraFundsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryTraFundsByConditions(Page<TraFunds> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        traFundsService.query(page, filters);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }

    /**
     * 查询集中审议符合条件的数据
     *
     * @param year
     * @param quarter
     * @return
     */
    @RequestMapping("/batchConditionSubmit")
    @ResponseBody
    public Map<String, Object> batchConditionSubmit(String year, String quarter) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<TraFunds> pageInfo = traFundsService.queryBatchSubmit(1, Integer.MAX_VALUE, year, quarter);
        List<TraFunds> list = pageInfo.getList();
        if (CollectionUtil.isEmpty(list)) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "无数据");
            return result;
        }
        //修改审批状态
        List<String> pids = new ArrayList<>();
        for (TraFunds traFunds : list) {
            TraFunds funds = new TraFunds();
            Integer pid = traFunds.getPid();
            funds.setPid(pid);
            funds.setStatus("4");
            funds.setConcentratedApproval("0");
            traFundsService.updateSelective(funds);
            pids.add(String.valueOf(pid));
            String relInfoListStr = traFunds.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traFunds.setRelInfoList(traRelInfos);
                traFunds.setRelInfoListStr(null);
            }
        }
        Map<String, Object> formData = new HashMap<>();
        formData.put("pids", CollectionUtil.join(pids, ","));
        formData.put("data", list);
        formData.put("tranType", Dict.TRAN_TYPE_02);
        result.put("data", formData);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        String deptCode = profile.getDeptCode();
        //保存发起人的机构信息
        result.put("unitCode", unitCode);
        result.put("deptCode", deptCode);
        String deptName = profile.getDeptName();
        result.put("deptName", deptName);
        //获取对应总部门的部门编号
        List<String> hdDeptCodeList = approveAssignerService.queryDeptCodeByDeptName(deptName, "8600");
        result.put("hdDeptCode", CollectionUtil.isNotEmpty(hdDeptCodeList) ? hdDeptCodeList.get(0) : null);
        //是否是二级部门
        Boolean isSecond = approveAssignerService.isHdSecondDept(deptCode);
        result.put("isSecond", isSecond);
        result.put(WebCode.SUCCESS, true);
        return result;
    }

    /**
     * 为列表页面做数据准备
     */
    @RequestMapping(value = "/queryPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareQuery(Page<TraFunds> page, String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString,String dataSources) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.start(page);
        List<TraFunds> resultList = traFundsService.getInit(contractNo, relNumber, relName, busType, startTranDate, endTranDate, considerType, majorTrade, status, createTimeString, null,dataSources);;
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        assert userProfile != null;
        boolean flag = UserRoleUtils.judgeRole(userProfile.getStaffCode());
        String deptCode = userProfile.getDeptCode();
        if (!flag ) {
            PageHelper.start(page);
            resultList = traFundsService.getInit(contractNo, relNumber, relName, busType, startTranDate, endTranDate, considerType, majorTrade, status, createTimeString, deptCode,dataSources);
        }
        resultList.forEach(e -> {
            //自然人证件号码脱敏
            if("PSN".equals(e.getRelType())){
                //查询关联方证件类型
                if(StringUtils.isNotEmpty(e.getRelNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(e.getRelNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            e.setRelNumber(idEncrypt(e.getRelNumber()));
                        }else{
                            e.setRelNumber(otherIdEncrypt(e.getRelNumber()));
                        }
                    }
                }

            }
        });
        PageInfo pageInfo = new PageInfo(resultList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        //生成交易编号
        TraFunds traFunds = new TraFunds();
        traFunds.setTransNum(BatchNumberUtil.makeBatchNumber());
        traFunds.setTradeAmount(new BigDecimal(0));
        traFunds.setIsconsiderSituation(Dict.SF_0);
        traFunds.setTranType(Dict.TRAN_TYPE_02);
        model.addAttribute("traFunds", traFunds);
       /* String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        Unit unit=unitService.get(unitCode);
        model.addAttribute("unitName", unit.getUnitName());
        model.addAttribute("unitCode", unit.getUnitCode());*/
        return "ins/traFundsEdit";
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

        TraFunds traFunds = traFundsService.get(pid);
        //自然人证件号码脱敏
        if("PSN".equals(traFunds.getRelType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(traFunds.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traFunds.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traFunds.setRelNumber(idEncrypt(traFunds.getRelNumber()));
                    }else{
                        traFunds.setRelNumber(otherIdEncrypt(traFunds.getRelNumber()));
                    }
                }
            }

        }

        model.addAttribute("traFunds", traFunds);
        model.addAttribute("opt", "edit");

        return "ins/traFundsEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model, HttpServletRequest request) {
        Commons.common2(request, model);
        TraFunds traFunds = traFundsService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traFunds.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traFunds && counterpartyList != null && counterpartyList.size() > 0) {
            traFunds.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        //自然人证件号码脱敏
        if("PSN".equals(traFunds.getRelType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(traFunds.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traFunds.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traFunds.setRelNumber(idEncrypt(traFunds.getRelNumber()));
                    }else{
                        traFunds.setRelNumber(otherIdEncrypt(traFunds.getRelNumber()));
                    }
                }
            }

        }
        model.addAttribute("traFunds", traFunds);
        model.addAttribute("opt", "view");

        //确认数据
        if ("2".equals(traFunds.getStatus())) {
            model.addAttribute("isConfirm", true);
            confirm(pid);
        }

        return "ins/traFundsEdit";
    }

    /**
     * 保存
     *
     * @param traFunds
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraFunds traFunds, TraEnclosureVo traEnclosureVo, String proName) {
        Map<String, Object> tips = new HashMap<>(4);
        //校验是否已经存在该笔交易
        TraFunds existTraFunds = traFundsService.get(traFunds.getPid());
        //获取当前登录用户信息
        UserProfile profile = UserProfileUtils.getUserProfile();

        boolean b = XssCheck.reflect(traFunds);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }

        //获取关联交易对手方信息列表，并判断是否存在交易对手
        String relInfoStr = traFunds.getRelInfoListStr();
        List<TraRelInfo> relList = JSON.parseArray(relInfoStr, TraRelInfo.class);
        if (relList.isEmpty()) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }
        traFunds.setRelId(relList.get(0).getRelId());
        traFunds.setRelName(relList.get(0).getRelName());
        traFunds.setRelNumber(relList.get(0).getRelNumber());
        traFunds.setRelType(relList.get(0).getRelType());
        try {
            String transNum = "";
            traFunds.setDelflag("1");
            traFunds.setTranType(Dict.TRAN_TYPE_02);
            traFunds.setDataSources("1");//手工录入
            assert profile != null;
            traFunds.setTradeOrg(profile.getUnitCode());
            traFunds.setAmendId(profile.getStaffCode());
            traFunds.setAmendName(profile.getStaffName());

            //判断交易信息已存在就更新，不存在就新建
            if (existTraFunds != null) {
                //交易流水号
                transNum = existTraFunds.getTransNum();
                //待确认状态直接修改为已归档、
                /* 关联交易状态说明
                 * value="2"，key="待确认"
                 * value="3"，key="待提交"
                 * value="4"，key="审批中"
                 * value="1"，key="待补录"
                 * value="5"，key="审批退回"
                 * value="6"，key="已归档"
                 * value="7"，key="履行中"
                 * value="8"，key="待签定"
                 */
                if (Dict.APPLY_STATUS_8.equals(traFunds.getStatus())) {
                    // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                    if (StringUtils.isNotBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate()) && ObjectUtil.isNotEmpty(traFunds.getBusStartTime())) {
                        traFunds.setStatus("6");
                    }
                    // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                    // 实际交易金额为空 + 合同签署日期为空 = 待签订
                    if (ObjectUtil.isEmpty(traFunds.getTranDate()) || ObjectUtil.isEmpty(traFunds.getBusStartTime())) {
                        traFunds.setStatus("8");
                    }
                    // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                    if(StringUtils.isBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate())){
                        traFunds.setStatus("7");
                    }
                    traFundsService.updateSelective(traFunds);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "签订成功");
                    return tips;
                } else if(Dict.APPLY_STATUS_2.equals(traFunds.getStatus())){
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    boolean b1 = traFundsService.calibrationAccessories(transNum);
                    if(b1){
                        // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                        if (StringUtils.isNotBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate()) && ObjectUtil.isNotEmpty(traFunds.getBusStartTime())) {
                            traFunds.setStatus("6");
                        }
                        // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                        // 实际交易金额为空 + 合同签署日期为空 = 待签订
                        if (ObjectUtil.isEmpty(traFunds.getTranDate()) || ObjectUtil.isEmpty(traFunds.getBusStartTime())) {
                            traFunds.setStatus("8");
                        }
                        // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                        if(StringUtils.isBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate())){
                            traFunds.setStatus("7");
                        }
                        traFundsService.updateSelective(traFunds);
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "确认成功");
                        return tips;
                    }else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "存在必传文件未上传。");
                        return tips;
                    }
                }
                // 校验必填数据
                checkStrIsEmpty(traFunds);

                traFunds.setModifiedTime(new Date());
                // 更新成操作人(针对第三方数据)
                traFunds.setAmendId(profile.getStaffCode());
                traFunds.setAmendName(profile.getStaffName());
                traFundsService.updateSelective(traFunds);
            } else {
                // 校验必填项
                checkStrIsEmpty(traFunds);
                //设置交易号
                transNum = traFunds.getTransNum();
                // 是否参与累计清零标识
                traFunds.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traFunds.setDeptName(profile.getDeptName());
                traFundsService.insertSelective(traFunds);
            }
            //更新保存文件信息
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
            //待确认状态需确认必传文件是否上传，需在更新文件后

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    @RequestMapping(value = "/saveSign")
    @ResponseBody
    public Map<String, Object> save1(TraFunds traFunds, TraEnclosureVo traEnclosureVo, String proName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //校验是否已经存在该笔交易
        TraFunds existTraFunds = traFundsService.get(traFunds.getPid());
        //获取当前登录用户信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        //获取关联交易对手方信息列表，并判断是否存在交易对手
        String relInfoStr = traFunds.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        try {
            String transNum = "";
            traFunds.setDelflag("1");
            traFunds.setTranType(Dict.TRAN_TYPE_02);
            traFunds.setDataSources("1");//手工录入
            traFunds.setTradeOrg(profile.getUnitCode());
            traFunds.setAmendId(profile.getStaffCode());
            traFunds.setAmendName(profile.getStaffName());
            traFunds.setRelInfoList(relList);

            //判断交易信息已存在就更新，不存在就新建
            if (existTraFunds != null) {
                //交易流水号
                transNum = existTraFunds.getTransNum();
                //待确认状态直接修改为已归档
                if ("8".equals(traFunds.getStatus())) {
                    //修改为已归档
                    TraFunds traFundsForUpdate = new TraFunds();
                    traFundsForUpdate.setPid(traFunds.getPid());
                    if(traFunds.getPracTradeAmount() == null) {
                        traFundsForUpdate.setStatus("7");
                        traFundsForUpdate.setTranDate(traFunds.getTranDate());
                    }else{
                        traFundsForUpdate.setStatus("6");
                        traFundsForUpdate.setTranDate(traFunds.getTranDate());
                    }
                    traFundsService.updateSelective(traFundsForUpdate);

                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    if(traFunds.getPracTradeAmount() == null) {
                        traCounterparty.setStatus("7");
                        traCounterparty.setTranDate(traFunds.getTranDate());
                    }else{
                        traCounterparty.setStatus("6");
                        traCounterparty.setTranDate(traFunds.getTranDate());
                    }
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "签定成功");
                    return tips;
                }
                //校验审议类型
                checkConsiderType(traFunds);
                //处理重大交易情况
                checkMajorTrade(traFunds, relList);

                traFunds.setModifiedTime(new Date());

                //判断是否生效
                /*if (Dict.SF_1.equals(traFunds.getIsEffective())) {
                    traFunds.setApproveTradeAmount(existTraFunds.getTradeAmount());
                }*/
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traFunds.setRelId(r.getRelId());
                traFunds.setRelType(r.getRelType());
                traFunds.setRelName(r.getRelName());
                traFunds.setRelNumber(r.getRelNumber());
                traFunds.setTranOpponent(r.getTranOpponent());
                traFunds.setCertType(r.getCertType());
                traFunds.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traFunds.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traFundsService.updateSelective(traFunds);

                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traFunds.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traFunds.getPracTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traFunds.getStatus());
                    traCounterparty.setTranDate(traFunds.getTranDate());
                    traCounterparty.setTraSubName(traFunds.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());

                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterpartyService.updateSelective(traCounterparty);
                }

            } else {
                //校验审议类型
                checkConsiderType(traFunds);
                //处理重大交易情况
                checkMajorTrade(traFunds, relList);
                //设置交易号
                transNum = traFunds.getTransNum();
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traFunds.setRelId(r.getRelId());
                traFunds.setRelType(r.getRelType());
                traFunds.setRelName(r.getRelName());
                traFunds.setRelNumber(r.getRelNumber());
                traFunds.setTranOpponent(r.getTranOpponent());
                traFunds.setCertType(r.getCertType());
                traFunds.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traFunds.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traFundsService.insertSelective(traFunds);
                //保存交易对手信息到新表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traFunds.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traFunds.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traFunds.getStatus());
                    traCounterparty.setTranDate(traFunds.getTranDate());
                    traCounterparty.setTraSubName(traFunds.getTraSubName());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterpartyService.insertSelective(traCounterparty);
                }
            }
            //更新保存文件信息
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }


    @RequestMapping(value = "/sendNotice")
    @ResponseBody
    public Map<String, Object> sendNotice(String traSubName, String relName, String
            tranType, String contractNo, String contractAmount) {
        Map<String, Object> message = new HashMap<String, Object>();
        try {
            //发送通知
            InsUtil.sendNotice(traSubName, relName, tranType,
                    contractNo, contractAmount);
            message.put(WebCode.SUCCESS, true);
            message.put(WebCode.MSG, "发送通知邮件成功");
        } catch (Exception e) {
            message.put(WebCode.SUCCESS, false);
            message.put(WebCode.MSG, "发送邮件失败");
        }
        return message;
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
            TraFunds traFunds = traFundsService.get(pid);
            traFunds.setPid(pid);
            traFunds.setDelflag("0");
            traFundsService.updateSelective(traFunds);
            //同时把交易对手表数据状态改为删除
            TraCounterparty tra = new TraCounterparty();
            tra.setTransNum(traFunds.getTransNum());
            tra.setStatus("0");
            traCounterpartyService.updataCounterpartyStatusByTransNum(tra);
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

        boolean exist = traFundsService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 查询关联方信息页面返显
     */
    @RequestMapping(value = "/getRelInfo")
    @ResponseBody
    public Map<String, Object> getRelInfo(String relNumber, String relName) {
        Map<String, Object> relInfo = baseService.getRelInfo(relNumber, relName);
        return relInfo;
    }

    /**
     * 任务下发
     */
    @RequestMapping(value = "/sendCommand")
    public String relPsnSendCommand(Model model) {

        return "ins/traFundsSend";
    }

    /**
     * 确认下发
     */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    public Map taskSend(String unitCode, String memo, String sendId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放疑似自然人给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendId).build();
        TraFunds traFunds = traFundsService.get(filers);
        //更换管库机构为下发机构
        traFunds.setTradeOrg(unitCode);
        //更新疑似自然人状态为待补录
        traFunds.setStatus("2");
        traFunds.setStatusComment(memo);
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
                    notifyService.createRemind(duty.getStaffCode(), "资金运用类交易[" + traFunds.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易资金运用类列表完成补录。", "资金运用类交易[" + traFunds.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易投资入股类列表完成补录。");
                }
                traFundsService.updateSelective(traFunds);
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
     * 更改审批状态
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            TraFunds traFunds = traFundsService.get(pid);
            TraFunds traFunds1 = new TraFunds();
            traFunds1.setStatus("4");
            traFunds1.setPid(pid);
            traFunds.setStatus("4");
            traFunds1.setConcentratedApproval("1");
            traFundsService.updateSelective(traFunds1);
            String relInfoListStr = traFunds.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traFunds.setRelInfoList(traRelInfos);
                traFunds.setRelInfoListStr(null);
            }
            UserProfile profile = UserProfileUtils.getUserProfile();
            String unitCode = profile.getUnitCode();
            String deptCode = profile.getDeptCode();
            //保存发起人的机构信息
            tips.put("unitCode", unitCode);
            tips.put("deptCode", deptCode);
            tips.put("data", traFunds);
            String deptName = profile.getDeptName();
            tips.put("deptName", deptName);
            //获取对应总部门的部门编号
            List<String> hdDeptCodeList = approveAssignerService.queryDeptCodeByDeptName(deptName, "8600");
            tips.put("hdDeptCode", CollectionUtil.isNotEmpty(hdDeptCodeList) ? hdDeptCodeList.get(0) : null);
            //是否是二级部门
            Boolean isSecond = approveAssignerService.isHdSecondDept(deptCode);
            tips.put("isSecond", isSecond);
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
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        TraFunds traFunds = traFundsService.get(pid);
        model.addAttribute("traFunds", traFunds);
        model.addAttribute("opt", "view");

        return "ins/traFundsSubmit";
    }

    /**
     * 判断是否重大关联交易
     * <p>
     * <p>
     * 关联交易发起部门录入关联交易相关信息后，系统根据模型对比该笔交易金额和重大关联交易
     * 金额限制，自动识别出为一般关联交易还是重大关联交易。随后流转至秘书处法律合规专岗，由专
     * 岗人员对关联交易情况做基本了解后，引导发起部门继续后续审批流程。
     * 集团的关联交易分为重大关联交易和一般关联交易。
     * 重大关联交易是指集团或其控股子公司与一个关联方之间单笔或年度累计交易金额达到 3000
     * 万元以上，且占集团上一年度末经审计的净资产的 1%以上的交易；保险业务类和提供货物、服务
     * 或财务资助，可签署统一交易协议，统一交易协议应按照重大关联交易进行内部管控。
     * 一个年度内集团或其控股子公司与一个关联方的累计交易金额达到前款标准后，其后发生的关
     * 联交易，如再次累计达到前款标准，则应当重新认定为重大关联交易。
     * 集团与多个关联方在同一笔交易中的金额，应合并计算进行认定。
     * 一般关联交易是指除重大关联交易以外的其他关联交易。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/GreatCheck")
    @ResponseBody
    public Map<String, Object> isGreatCheck(String relTradeAmount, String relInfoListStr) {
        HashMap<String, Object> result = Maps.newHashMap();
        //调用规则service
        Map ruleResult = null;
        String isGreate = "0";
        String majorType = "0";//0标识不重大
        String notMajorRelNumber ="";
        //调取重大判断及查询类返回重大校验结果
        try {
            BigDecimal bigRelTradeAmount = com.yzj.jep.commons.StringUtils.isEmpty(relTradeAmount) ? null : new BigDecimal(relTradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
            ruleResult = InsUtil.GreatCheckNew(Dict.TRAN_TYPE_02, bigRelTradeAmount, relInfoListStr);

            if (null != ruleResult) {
                InsTrans insTrans = (InsTrans) ruleResult.get("insTrans");
                if (com.yzj.jep.commons.StringUtils.isNotEmpty(insTrans.getMajorTrade())) {
                    isGreate = insTrans.getMajorTrade();
                }
                if (com.yzj.jep.commons.StringUtils.isNotEmpty(insTrans.getMajorType())) {
                    majorType = insTrans.getMajorType();
                }
                if (ruleResult.get("notMajorRelNumber")!=null) {
                    notMajorRelNumber = (String) ruleResult.get("notMajorRelNumber");
                }
            }

            result.put("isGreatTrade", isGreate);
            result.put("majorType", majorType);
            result.put("notMajorRelNumber", notMajorRelNumber);
            result.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("调用重大关联交易规则失败。。。 错误信息{}", e);
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "调用重大关联交易规则失败。。。");
        }
        return result;
    }

    /**
     * 限额控制规则
     *
     * @param traFunds
     * @return
     */
    @RequestMapping(value = "/traFundsCheck")
    @ResponseBody
    public Map<String, Object> tradeCheck(TraFunds traFunds) {
        Map<String, Object> result = new HashMap();
        String relInfoListStr = traFunds.getRelInfoListStr();
        List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
        //取第一条数据
        String relId = null;
        String relType = null;
        if (CollectionUtil.isNotEmpty(traRelInfos)) {
            TraRelInfo traRelInfo = traRelInfos.get(0);
            relId = String.valueOf(traRelInfo.getRelId());
            relType = traRelInfo.getRelType();
        }
        String relIds = "";
        for (TraRelInfo traRelInfo : traRelInfos) {
            relIds += traRelInfo.getRelId();
        }
        /**股权分类*/
        String stockrightType = traFunds.getStockrightType();
        /**交易金额*/
        BigDecimal contractAmount = traFunds.getContractAmount();
        /**是否为境外投资*/
        String isOverseasinvestment = traFunds.getIsOverseasinvestment();
        /**底层基础资产是否涉及控股股东或控股股东的关联方*/
        String underInvolveRel = traFunds.getUnderInvolveRel();
        /**金融产品发行规模*/
        String financialProScale = traFunds.getFinancialProScale();
        /**是否控股子公司*/
        String isHoldingSubsidiary = traFunds.getIsHoldingSubsidiary();
        /**产品批准/注册/备案号*/
        String financialAssstsNum = traFunds.getFinancialAssstsNum();
        BigDecimal relTradeAmount = traFunds.getRelTradeAmount();

        /**查询风险指标*/
        List<RiskRatio> riskRatios = riskRatioService.selectAll();
        String warningPercent = null;
        if(riskRatios.size() == 0){
           warningPercent = "100";
        }else{
            RiskRatio riskRatio = riskRatios.get(0);
             warningPercent = riskRatio.getWarningValue();
        }

        Map overLimit = InsUtil.isOverLimit(relId, String.valueOf(relTradeAmount), traFunds.getInvestmentType(),
                isOverseasinvestment, underInvolveRel, financialProScale, relIds, stockrightType, isHoldingSubsidiary,
                financialAssstsNum, relType, warningPercent, traRelInfos);
        //1：未超出限额，判断否超限预警
//        if (overLimit.get("limitCode").equals("1")) {
//            Map warningLimit = InsUtil.isWarningLimit(String.valueOf(traFunds.getRelId()), String.valueOf(traFunds.getTradeAmount()), traFunds.getInvestmentType(),
//                    traFunds.getIsOverseasinvestment(), traFunds.getUnderInvolveRel(), traFunds.getFinancialProScale());
//        } else {
//        }

        if (overLimit.get("limitCode").equals("1")) {
            result.put(WebCode.SUCCESS, true);
            result.put("isaved", true);
        } else {
            String msg = String.valueOf(overLimit.get("limitMsg"));
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "合规校验不通过：【" + msg + "】");
            result.put("isaved", false);
            result.put("code", overLimit.get("limitCode"));
            /**将数据返回，直接保存*/
            traFunds.setLimitHint((String) result.get(msg));
            traFunds.setRuleResult((String) result.get("code"));
            traFunds.setRelInfoList(traRelInfos);
            traFunds.setRelInfoListStr(null);
            result.put("traFunds", traFunds);
            return result;
        }
        return result;
    }

    /**
     * 额度使用率
     *
     * @param traService
     * @return
     */
    @RequestMapping("/quotaCheck")
    @ResponseBody
    public Map<String, Object> quotaCheck(TraService traService) {
//        String businessType = traService.getBusType();
//        BigDecimal tradeAmount = traService.getTradeAmount();
        Map<String, Object> tips = Maps.newHashMap();
//        Map<String, Object> quotaParam = InsUtil.initQuotaParam("02", businessType, tradeAmount);
//        if (quotaParam != null) {
//            Map<String, String> resultMap = ruleCheckService.quotaCheck(quotaParam);
//            if (resultMap != null) {
//                tips.put(WebCode.SUCCESS, false);
//                tips.put(WebCode.MSG, resultMap.get("msg"));
//                return tips;
//            }
//        }
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 内联树
     *
     * @return
     */
    @RequestMapping(value = "/proNameListTree")
    public String proNameListTree() {
        return "bus/limitEditTree";
    }

    /**
     * 判断选择的产品是否在资金运用类型下
     *
     * @param unitCodes
     * @return
     */
    @RequestMapping("/selectbuinessL")
    @ResponseBody
    public Map<String, Object> selectDept(String unitCodes) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] split = unitCodes.split("-");
        String level = split[0];
        Integer pid = Integer.parseInt(split[1]);
        Integer paProId = productService.get(pid).getPaProId();


        //反显交易类型和业务类型
        if ("0".equals(level)) {
            map.put(WebCode.SUCCESS, false);
            map.put(WebCode.MSG, "操作失败，请选择一个产品或者业务类型！！！");
        } else if ("1".equals(level)) {
            Integer proId = Integer.parseInt(productService.get(productService.get(pid).getPaProId()).getProId());

            //判断选择的产品是否属于用信类
            if (proId != 2) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择资金运用模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId.toString());
                map.put("investmentType", pid.toString());
                map.put(WebCode.SUCCESS, true);

            }
        } else if ("2".equals(level)) {
            Integer proId = Integer.parseInt(productService.get(productService.get(productService.get(pid).getPaProId()).getPaProId()).getProId());
            if (proId != 2) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择资金运用模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId);
                map.put("investmentType", productService.get(pid).getPaProId().toString());
                map.put(WebCode.SUCCESS, true);
            }
        }
        return map;
    }

    /**
     * 查阅页面跳转
     */
    @RequestMapping(value = "/toConfirm/{pid}")
    public String toConfirm(@PathVariable Integer pid, Model model) {
        TraFunds traFunds = traFundsService.get(pid);
        //确认归档后设置状态为以归档
        /*traFunds.setStatus("6");
        traFundsService.updateSelective(traFunds);*/
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traFunds.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traFunds && counterpartyList != null && counterpartyList.size() > 0) {
            traFunds.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traFunds", traFunds);
        model.addAttribute("opt", "view");
        model.addAttribute("file", "file");
        model.addAttribute("isConfirm", true);
        return "ins/traFundsEdit";
    }
    //签定跳转的页面
    @RequestMapping(value = "/sign/{pid}")
    public String sign(@PathVariable Integer pid, Model model) {
        TraFunds traFunds = traFundsService.get(pid);
        //确认归档后设置状态为以归档
        /*traFunds.setStatus("6");
        traFundsService.updateSelective(traFunds);*/
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traFunds.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traFunds && counterpartyList != null && counterpartyList.size() > 0) {
            traFunds.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traFunds", traFunds);
        model.addAttribute("opt", "view");
        model.addAttribute("isConfirm", true);
        model.addAttribute("sign", true);
        return "ins/traFundsEdit";
    }

    /**
     * 查阅管户机构数据
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/confirm/{pid}")
    @ResponseBody
    public Map<String, Object> confirm(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            TraFunds traFunds = traFundsService.get(pid);
            traFunds.setStatus("8");
            traFundsService.updateSelective(traFunds);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 回款回显
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/PaymentCollection/{pid}")
    @ResponseBody
    public Map<String, Object> PaymentCollection(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            TraFunds traFunds = traFundsService.get(pid);
            if (null == traFunds.getAmountReceived()) {
                traFunds.setAmountReceived(new BigDecimal(0));
            }
            tips.put("amountReceived", traFunds.getAmountReceived());
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 回款累计
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/PaymentCollectionAmount/{pid}/{paymentCollectionAmount}")
    @ResponseBody
    public Map<String, Object> PaymentCollectionAmount(@PathVariable Integer pid, @PathVariable String paymentCollectionAmount) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            TraFunds traFunds = traFundsService.get(pid);
            if (null == traFunds.getAmountReceived()) {
                traFunds.setAmountReceived(new BigDecimal(0));
            }
            traFunds.setAmountReceived(traFunds.getAmountReceived().add(new BigDecimal(paymentCollectionAmount)));
            traFundsService.updateSelective(traFunds);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> sendjz(@PathVariable Integer pid, Model model) throws Exception {
        List<TraFunds> lli = new ArrayList<>();
        Map<String, Object> tips = new HashMap<String, Object>();
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        String procInstId = null;

        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);

        TraFunds funds = traFundsService.get(pid);
        String relInfoListStr = funds.getRelInfoListStr();
        if (StrUtil.isNotBlank(relInfoListStr)) {
            //设置关联人信息
            List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
            funds.setRelInfoList(traRelInfos);
            //解析json时会报错，此处设置为空
            funds.setRelInfoListStr(null);
        }
        // 文本输入框审批时含有特殊字符需要进行特殊处理
        funds.setRelName(CharacterUtil.changeCharacter(funds.getRelName()));
        funds.setTranName(CharacterUtil.changeCharacter(funds.getTranName()));
        funds.setTradePrice(CharacterUtil.changeCharacter(funds.getTradePrice()));
        funds.setPricingPolicy(CharacterUtil.changeCharacter(funds.getPricingPolicy()));
        funds.setTradeSubject(CharacterUtil.changeCharacter(funds.getTradeSubject()));
        funds.setRemark(CharacterUtil.changeCharacter(funds.getRemark()));

        com.alibaba.fastjson.JSONObject data = JSON.parseObject(JSON.toJSONString(funds));
        if (org.apache.commons.lang.StringUtils.isNotBlank(funds.getProcInstId())) {
            procInstId = funds.getProcInstId();
        }

        data.put("startStaffCode", staff.getStaffCode());
        data.put("TransactionTtype","funds");
        data.put("tenantId", profile.getTenantId());
        data.put("type","funds");
        //判断员工所属机构
        Boolean StaffType = this.companyType(profile.getUnitCode());
        String procDefCode =null;
        String considerType = funds.getConsiderType();
        //分公司审批
        if (StaffType){
            if ("3".equals(considerType)){
                // 分公司审批流 重大统一交易审批流
                data.put("WorkflowType", "ZDTI_BranchOffice");
                procDefCode = "FXBDX_ZDTY_BranchOffice";
            }else {
                // 分公司审批流 一般交易审批流
                data.put("WorkflowType", "YBGLJY_BranchOffice");
                procDefCode = "FXBDX_YBGLJY_Company";
            }

        }else {
            // 总公司审批流 一般交易审批流
            if ("2".equals(considerType)){
                data.put("WorkflowType", "YBGLJY");
                procDefCode = "FXBDX_YBGLJY";
            }else {
                //总公司审批流 重大统一交易审批流
                data.put("WorkflowType", "ZDTI");
                procDefCode = "FXBDX_ZDTY";
            }
        }
        try {
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "funds", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:资金运用类---" + "pid" + pid, e);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
            return tips;
        }

        tips.put("data", lli);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    public Boolean companyType(String unitCode){
        Boolean type = false;
        if (unitCode.equals(CompanyType.CLIENT00.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT01.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT02.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT03.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT04.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT05.getCategory())){
            type=true;
            //总公司
        }else if (unitCode.equals(CompanyType.YLINS.getCategory())){
            type=false;
        }
        return type;
    }


    /**
     * 校验审议类型，通过审议类型归类交易状态
     */
    private void checkConsiderType(TraFunds traFunds) {
        //校验交易 审议状态，1免于审议， 2一般审议， 3逐笔审议
        boolean isEmpty = false;
        String considerType = traFunds.getConsiderType();
        if (considerType.equals("3")) {
            if (
                    StringUtils.isEmpty(traFunds.getInvestmentType()) || traFunds.getRelTradeAmount() == null ||
                            traFunds.getBusStartTime() == null || StringUtils.isEmpty(traFunds.getContractNo()) ||
                            StringUtils.isEmpty(traFunds.getIsOverseasinvestment()) || StringUtils.isEmpty(traFunds.getTradeSubject()) ||
                            StringUtils.isEmpty(traFunds.getTradePrice()) ||//交易价格
                            StringUtils.isEmpty(traFunds.getPricingPolicy()) || StringUtils.isEmpty(traFunds.getFinancialImpact())
                          /*  || com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getIsDealPrice() ) */
                            || com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getIsCashSubscription() )
            ) {
                //所有类型都必填的，缺失即为待补录
                isEmpty = true;
            } else {
                //投资类别 01股权
                if (Dict.FUNDS_INVESTMENT_TYPE_GQ.equals(traFunds.getInvestmentType())) {
                    if (StringUtils.isEmpty(traFunds.getInvestedEnterprises()) || StringUtils.isEmpty(traFunds.getEquityLastTradePrice())) {
                        isEmpty = true;
                    }
                } else if (Dict.FUNDS_INVESTMENT_TYPE_BDC.equals(traFunds.getInvestmentType())) {// 02不动产类
                    if (StringUtils.isEmpty(traFunds.getInvestmentMethod()) || traFunds.getShareDebtRatio() == null ||
                            StringUtils.isEmpty(traFunds.getEquityLastTradePrice()) || StringUtils.isEmpty(traFunds.getImSubjectMatter())) {
                        isEmpty = true;
                    }
                } else if (Dict.FUNDS_INVESTMENT_TYPE_YHCK.equals(traFunds.getInvestmentType())) {// 03银行存款
                    if (StringUtils.isEmpty(traFunds.getImSubjectMatter())) {
                        isEmpty = true;
                    }
                } else if (Dict.FUNDS_INVESTMENT_TYPE_JRCP.equals(traFunds.getInvestmentType())) {//04金融产品
                    if (StringUtils.isEmpty(traFunds.getFinancialAssstsName()) //名称
                            || StringUtils.isEmpty(traFunds.getFinancialAssstsNum()) || //备案号
                            StringUtils.isEmpty(traFunds.getFinancialProScale()) || //规模
                            traFunds.getFinancialProRatio() == null || //占比
                            StringUtils.isEmpty(traFunds.getUnderIsRel()) ||  //底层资产是否涉及关联方
                            StringUtils.isEmpty(traFunds.getUnderlyingAsset()) || //底层基础资产具体描述
                        //    StringUtils.isEmpty(traFunds.getUnderInvolveRelName()) ||  //底层基础资产涉及的关联方名称
                            StringUtils.isEmpty(traFunds.getUnderInvolveRel()) //底层基础资产是否涉及控股股东或控股股东的关联方
                    ) {
                        isEmpty = true;
                    }
                    if(traFunds.getUnderIsRel().equals("1")){
                        if(StringUtils.isEmpty(traFunds.getUnderInvolveRelName())){
                            isEmpty = true;
                        }
                    }
                } else if (Dict.FUNDS_INVESTMENT_TYPE_GTTZ.equals(traFunds.getInvestmentType())) {// 05与关联方共同投资
                    if (StringUtils.isEmpty(traFunds.getInvestedEnterprisesName()) || StringUtils.isEmpty(traFunds.getBusModel()) ||
                            StringUtils.isEmpty(traFunds.getEarningsExpectaions()) || StringUtils.isEmpty(traFunds.getInvestmentRatio()) ||
                            StringUtils.isEmpty(traFunds.getInvestmentAllocation()) || StringUtils.isEmpty(traFunds.getLossShare())) {
                        isEmpty = true;
                    }
                }

                if (isEmpty) {
                    traFunds.setStatus("1");
                } else {

                    traFunds.setStatus("3");

                }


            }

            if (isEmpty) {
                traFunds.setStatus("1");
            } else {

                traFunds.setStatus("3");

            }

        } else {
            if( com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getInvestmentType()) ||   //业务类型
                    traFunds.getRelTradeAmount() == null ||          //关联交易金额
                    com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getTradePrice()) ||//交易价格
                    com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getPricingPolicy()) ||//定价政策
                    com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getTradeSubject())//交易标的
                    ||  com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getIsOverseasinvestment()) //是否境内境外投资
                   /* || com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getIsDealPrice() ) */
                    || com.yzj.jep.commons.StringUtils.isEmpty(traFunds.getIsCashSubscription() )

            ){
                isEmpty = true;
            }else if (Dict.FUNDS_INVESTMENT_TYPE_JRCP.equals(traFunds.getInvestmentType())) {//04金融产品
                if (StringUtils.isEmpty(traFunds.getFinancialAssstsName())||
                        traFunds.getFinancialProRatio() == null || //占比
                        StringUtils.isEmpty(traFunds.getFinancialProScale()) || //规模
                        StringUtils.isEmpty(traFunds.getUnderInvolveRel()) //底层基础资产是否涉及控股股东或控股股东的关联方
                )  {
                    isEmpty = true;
                }
            }

            if (isEmpty) {
                traFunds.setStatus("1");
            } else {
                if (Dict.CONSIDER_TYPE_MYSY.equals(traFunds.getConsiderType())) {
                    //判断实际交易金额是否为空
                    if(null != traFunds.getPracTradeAmount() && null != traFunds.getTranDate()){
                        traFunds.setStatus("6");
                    }else if(null == traFunds.getTranDate()){
                        traFunds.setStatus("8");
                    }else if(null != traFunds.getTranDate() && null == traFunds.getPracTradeAmount()){
                        traFunds.setStatus("7");
                    }
                } else {
                    traFunds.setStatus("3");
                }
            }
        }
    }


    /**
     * 检验必填项
     * @param traFunds          数据源
     */
    public void checkStrIsEmpty(TraFunds traFunds) {
        if (StringUtils.isNotBlank(traFunds.getBusType()) && null != traFunds.getContractNo()
                && null != traFunds.getRelTradeAmount() && StringUtils.isNotBlank(traFunds.getTradePrice()) && StringUtils.isNotBlank(traFunds.getPricingPolicy())
                && StringUtils.isNotBlank(traFunds.getTranName()) && StringUtils.isNotBlank(traFunds.getTradeSubject()) && StringUtils.isNotBlank(traFunds.getMajorTrade())
                && StringUtils.isNotBlank(traFunds.getIsconsiderSituation()) && StringUtils.isNotBlank(traFunds.getIsOverseasinvestment())
                && StringUtils.isNotBlank(traFunds.getInvestmentMethod())) {
            traFunds.setStatus("3");
            // 金融产品
            if ("04".equals(traFunds.getBusType())) {
                if (StringUtils.isBlank(traFunds.getFinancialAssstsName()) && StringUtils.isBlank(traFunds.getFinancialAssstsNum()) && StringUtils.isBlank(traFunds.getFinancialProRatio())) {
                    traFunds.setStatus("1");
                }
            }
            /* 免于审议时，状态为已归档 */
            if ("1".equals(traFunds.getConsiderType())) {
                // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                if (StringUtils.isNotBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate()) && ObjectUtil.isNotEmpty(traFunds.getBusStartTime())) {
                    traFunds.setStatus("6");
                }
                // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                // 实际交易金额为空 + 合同签署日期为空 = 待签订
                if (ObjectUtil.isEmpty(traFunds.getTranDate()) || ObjectUtil.isEmpty(traFunds.getBusStartTime())) {
                    traFunds.setStatus("8");
                }
                // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                if(StringUtils.isBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate())){
                    traFunds.setStatus("7");
                }
            }
        }
    }



    /**
     * 判断重大情况并且处理保存信息
     *
     * @param traFunds
     * @param relList
     */
    private void checkMajorTrade(TraFunds traFunds, List<TraRelInfo> relList) {
        for (TraRelInfo tra : relList) {
            tra.setMajorTrade("0");
            tra.setMajorType("0");
            if (Dict.SF_1.equals(traFunds.getMajorTrade())) {
                if (Dict.MAJOR_TYPE_DB.equals(traFunds.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_DB);
                } else if (Dict.MAJOR_TYPE_LJ.equals(traFunds.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_LJ);
                }
            }
        }
    }

    /**
     * 编辑按钮查看权限后端查询
     * @param pid
     * @return
     */
    @RequestMapping(value = "/createPerisExist")
    @ResponseBody
    @Log(module = "关联交易管理", description = "按钮查看权限后端查询")
    public Map<String, Boolean> createPerisExist(Integer pid) {
        Map<String, Boolean> map = new HashMap<>();
        TraFunds traFunds = traFundsService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if ("System".equals(traFunds.getAmendName())) {
            map.put(WebCode.SUCCESS, true);
            return map;
        }
        if (userid.equals(traFunds.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);

        }
        return map;
    }


    /**
     * 附件上传
     */
    @RequestMapping(value = "/attachmentAdd/{pid}")
    public String attachmentAdd(@PathVariable Integer pid, Model model, HttpServletRequest request) {
        Commons.common2(request, model);
        TraFunds traFunds = traFundsService.get(pid);
        model.addAttribute("traFunds", traFunds);
        model.addAttribute("opt", "view");
        model.addAttribute("upload", true);

        return "ins/traFundsEdit";
    }

    @RequestMapping(value = "confirmArchiving/{pid}/{tranType}/{transNum}")
    @ResponseBody
    public Map<String,Object> confirmArchiving (@PathVariable Integer pid,@PathVariable String tranType,@PathVariable String transNum){
        Map<String,Object> tips = new HashMap<>();
        boolean fal = traFundsService.calibrationAccessories(transNum);
        if(fal){
            //根据主键和交易类型修改 审批状态

            if("1".equals(tranType)){
                TraFunds traFunds = new TraFunds();
                traFunds.setPid(pid);
                traFunds.setStatus("6");
                traFundsService.updateSelective(traFunds);
            }else if("4".equals(tranType)){
                TraBusiness traBusiness = new TraBusiness();
                traBusiness.setPid(pid);
                traBusiness.setStatus("6");
           //     traBusinessService.updateSelective(traBusiness);
            }else if("3".equals(tranType)){
                TraInterest traInterest = new TraInterest();
                traInterest.setPid(pid);
                traInterest.setStatus("6");
             //   traInterestService.updateSelective(traInterest);
            }else if("2".equals(tranType)){
                TraService traService = new TraService();
                traService.setPid(pid);
                traService.setStatus("6");
              //  traServiceService.updateSelective(traService);
            }else {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "交易类型不符合");
            }
            tips.put(WebCode.SUCCESS, true);
        }else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "确认失败！仍有必传附件未上传。");
        }
        return tips;
    }



}
