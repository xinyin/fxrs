/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:38
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
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.bus.service.RelPsnService;
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

import static com.yzj.jep.app.util.ConfidentialInfoUtil.*;

/**
 * 保险业务类
 */
@Controller
@RequestMapping("/ins/traBusiness")
public class TraBusinessController extends CrudController<TraBusiness> {
    private static Logger logger = LoggerFactory.getLogger(TraBusinessController.class);

    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private NotifyService notifyService;

    @Autowired
    private RuleCheckService ruleCheckService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApproveAssignerService approveAssignerService;

    @Autowired
    private TraInsenclosureService traInsenclosureService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private TraCounterpartyService traCounterpartyService;

    @Autowired
    WorkflowOperation workflowOperation;

    @Autowired
    private EmailSend emailSend;

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
        String url = "ins/traBusiness/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        //查询登陆人机构及下辖机构的信息
      /*  UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        if ("0001".equals(unitCode) || "0001-ZZHK".equals(unitCode)){
            //资产
            model.addAttribute("unitCode",unitCode);
            model.addAttribute("opt","NoMH");
        }else {
            //管户机构
            model.addAttribute("unitCode",unitCode);
            model.addAttribute("opt","MH");
        }*/
        return "ins/traBusinessList";
    }

    /**
     * 为列表页面做数据准备
     */
    @RequestMapping(value = "/queryPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareQuery(Page<TraBusiness> page, String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString,String dataSources) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.start(page);
        List<TraBusiness> resultList = traBusinessService.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,null,dataSources);
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        assert userProfile != null;
        boolean flag = UserRoleUtils.judgeRole(userProfile.getStaffCode());
        String deptCode = userProfile.getDeptCode();
        if (!flag ) {
            PageHelper.start(page);
            resultList = traBusinessService.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,deptCode,dataSources);
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

                if (StringUtils.isNotEmpty(e.getRelName())) {
                    e.setRelName(nameEncrypt(e.getRelName()));
                }

                if (StringUtils.isNotEmpty(e.getContractNo())) {
                    e.setContractNo(contractNoEncrypt(e.getContractNo(), 6));
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
        TraBusiness traBusiness = new TraBusiness();
        traBusiness.setTransNum(BatchNumberUtil.makeBatchNumber());
        //默认国家定价现金交易为否
       /* traBusiness.setIsDealPrice(Dict.SF_0);*/
        //是否涉及理赔
        traBusiness.setRelInfoListStr("[]");
        traBusiness.setIsAgreement(Dict.SF_0);
        traBusiness.setIsconsiderSituation(Dict.SF_0);
        traBusiness.setTradeAmount(new BigDecimal(0));
        //添加交易主体名称
        traBusiness.setTraSubName(Commons.getTenantName());
        model.addAttribute("traBusiness", traBusiness);
        return "ins/traBusinessEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Integer op, Model model) {
        TraBusiness traBusiness = traBusinessService.get(pid);
        //自然人证件号码脱敏
        if("PSN".equals(traBusiness.getRelType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(traBusiness.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traBusiness.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traBusiness.setRelNumber(idEncrypt(traBusiness.getRelNumber()));
                    }else{
                        traBusiness.setRelNumber(otherIdEncrypt(traBusiness.getRelNumber()));
                    }
                }
            }
        }
        model.addAttribute("traBusiness", traBusiness);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);
        return "ins/traBusinessEdit";
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
        TraBusiness traBusiness = traBusinessService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traBusiness.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traBusiness && counterpartyList != null && counterpartyList.size() > 0) {
            traBusiness.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        //自然人证件号码脱敏
        if("PSN".equals(traBusiness.getRelType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(traBusiness.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traBusiness.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traBusiness.setRelNumber(idEncrypt(traBusiness.getRelNumber()));
                    }else{
                        traBusiness.setRelNumber(otherIdEncrypt(traBusiness.getRelNumber()));
                    }
                }
            }

            if (StringUtils.isNotEmpty(traBusiness.getRelName())) {
                traBusiness.setRelName(nameEncrypt(traBusiness.getRelName()));
            }

            if (StringUtils.isNotEmpty(traBusiness.getContractNo())) {
                traBusiness.setContractNo(contractNoEncrypt(traBusiness.getContractNo(), 6));
            }

            if (StringUtils.isNotEmpty(traBusiness.getMasterAgreementNumber())) {
                traBusiness.setMasterAgreementNumber(contractNoEncrypt(traBusiness.getMasterAgreementNumber(), 6));
            }
        }
        traBusiness.setRelNumber(idEncrypt(traBusiness.getRelNumber()));
        model.addAttribute("traBusiness", traBusiness);
        model.addAttribute("opt", "view");
        //待查阅数据
        if ("9".equals(traBusiness.getStatus())) {
            model.addAttribute("isConfirm", true);
            confirm(pid);
        }
        return "ins/traBusinessEdit";
    }

    /**
     * 保存
     *
     * @param traBusiness
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraBusiness traBusiness, TraEnclosureVo traEnclosureVo, String proName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //校验是否已经存在该笔交易
        TraBusiness existTraBusiness = traBusinessService.get(traBusiness.getPid());
        //获取当前登录用户信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String relInfoStr = traBusiness.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.isEmpty()) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        boolean b = XssCheck.reflect(traBusiness);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }



        traBusiness.setRelId(relList.get(0).getRelId());
        traBusiness.setRelName(relList.get(0).getRelName());
        traBusiness.setRelNumber(relList.get(0).getRelNumber());
        traBusiness.setRelType(relList.get(0).getRelType());

        try {
            String transNum = "";
            traBusiness.setDelflag("1");//正常状态
            traBusiness.setTranType(Dict.TRAN_TYPE_03);
            traBusiness.setDataSources("1");//手工录入
            traBusiness.setTradeOrg(profile.getUnitCode());
            traBusiness.setAmendId(profile.getStaffCode());
            traBusiness.setAmendName(profile.getStaffName());
            traBusiness.setRelInfoList(relList);
            traBusiness.setTranDate(traBusiness.getContractDate());
            traBusiness.setContractDate(traBusiness.getContractDate());
            int index = 0;

            //判断交易信息已存在就更新，不存在就新建
            if (existTraBusiness != null) {
                transNum =existTraBusiness.getTransNum();
                if (Dict.APPLY_STATUS_8.equals(traBusiness.getStatus())) {

                    // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                    if (StringUtils.isNotBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate()) && ObjectUtil.isNotEmpty(traBusiness.getBusStartTime())) {
                        traBusiness.setStatus("6");
                    }
                    // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                    // 实际交易金额为空 + 合同签署日期为空 = 待签订
                    if (ObjectUtil.isEmpty(traBusiness.getContractDate()) || ObjectUtil.isEmpty(traBusiness.getBusStartTime())) {
                        traBusiness.setStatus("8");
                    }
                    // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                    if(StringUtils.isBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate())){
                        traBusiness.setStatus("7");
                    }

                    traBusinessService.updateSelective(traBusiness);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "确认成功");
                    return tips;
                } else if (Dict.APPLY_STATUS_2.equals(traBusiness.getStatus())){
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    boolean b1 = traFundsService.calibrationAccessories(transNum);
                    if(b1){
                        // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                        if (StringUtils.isNotBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate()) && ObjectUtil.isNotEmpty(traBusiness.getBusStartTime())) {
                            traBusiness.setStatus("6");
                        }
                        // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                        // 实际交易金额为空 + 合同签署日期为空 = 待签订
                        if (ObjectUtil.isEmpty(traBusiness.getContractDate()) || ObjectUtil.isEmpty(traBusiness.getBusStartTime())) {
                            traBusiness.setStatus("8");
                        }
                        // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                        if(StringUtils.isBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate())){
                            traBusiness.setStatus("7");
                        }

                        traBusinessService.updateSelective(traBusiness);
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "确认成功");
                        return tips;
                    }else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "存在必传文件未上传。");
                        return tips;
                    }
                }
                //校验审议类型
                checkConsiderIsEmpty(traBusiness);
                traBusiness.setAmendId(profile.getStaffCode());
                traBusiness.setAmendName(profile.getStaffName());
                index = traBusinessService.updateSelective(traBusiness);
                //交易流水号
                transNum = existTraBusiness.getTransNum();
            } else {
                //校验审议类型
                checkConsiderIsEmpty(traBusiness);
                transNum = traBusiness.getTransNum();
                /**
                 * 根据添加的交易对手列表，取第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traBusiness.setRelId(r.getRelId());
                traBusiness.setRelType(r.getRelType());
                traBusiness.setRelName(r.getRelName());
                traBusiness.setRelNumber(r.getRelNumber());
                // 是否参与累计清零标识
                traBusiness.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traBusiness.setDeptName(profile.getDeptName());
                traBusinessService.insertSelective(traBusiness);
            }

            if (traBusiness.getIsAgreement().equals("1") && traBusiness.getIsAgreementImpl().equals("4")) {
                if (null != traBusiness.getContractNo()) {
                    TraBusiness traBusinessNew = traBusinessService.traBusinessByAmount(traBusiness.getContractNo());
                    if (traBusinessNew != null && StringUtils.isNotEmpty(traBusinessNew.getPracTradeAmount())) {
                        String pracTradeAmount = traBusinessNew.getPracTradeAmount();
                        BigDecimal contractAmount = traBusinessNew.getContractAmount();
                        BigDecimal pracTradeAmountNumber = new BigDecimal(pracTradeAmount);
                        BigDecimal number = new BigDecimal(100);
                        BigDecimal numbers = new BigDecimal(90);
                        BigDecimal zore = new BigDecimal(0);
                        if (contractAmount.compareTo(zore) > -1) {
                            BigDecimal divideNumber = pracTradeAmountNumber.divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
                            System.out.println(divideNumber);
                            BigDecimal multiplyNumber = divideNumber.multiply(number);
                            System.out.println(multiplyNumber);
                            // 统一交易协议实际累计金额达到框架预估金额的90%时给财务部、法律部部门负责人发邮件提醒
                            if (multiplyNumber.compareTo(numbers) == 1) {
                                List list = emailSend.selectPerple();
                                emailSend.mojorEmailEighty(list, null, traBusiness.getRelName(), "3");
                            }
                        }
                    }
                }

            }
            //保存流水号到交易表
            updateDoc(traEnclosureVo, transNum);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    //签定页面的保存
    @RequestMapping(value = "/saveSign")
    @ResponseBody
    public Map<String, Object> save1(TraBusiness traBusiness, TraEnclosureVo traEnclosureVo, String proName) {

        Map<String, Object> tips = new HashMap<String, Object>();
        //校验是否已经存在该笔交易
        TraBusiness existTraBusiness = traBusinessService.get(traBusiness.getPid());
        //获取当前登录用户信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String relInfoStr = traBusiness.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        try {
            String transNum = "";
            traBusiness.setDelflag("1");//正常状态
            traBusiness.setTranType(Dict.TRAN_TYPE_03);
            traBusiness.setDataSources("1");//手工录入
            traBusiness.setTradeOrg(profile.getUnitCode());
            traBusiness.setAmendId(profile.getStaffCode());
            traBusiness.setAmendName(profile.getStaffName());
            traBusiness.setRelInfoList(relList);
            traBusiness.setTranDate(traBusiness.getContractDate());
            int index = 0;

            //判断交易信息已存在就更新，不存在就新建
            if (existTraBusiness != null) {
                //待确认状态直接修改为已归档
                if ("8".equals(traBusiness.getStatus())) {
                    //判断实际交易金额是否为空?( 备注，现不需要判断实际交易金额是否为空，直接改为已归档)
                    //修改为已归档
                    TraBusiness traBusinessForUpdate = new TraBusiness();
                    traBusinessForUpdate.setPid(traBusiness.getPid());
                    if (traBusiness.getPracTradeAmount() != null) {
                        traBusinessForUpdate.setStatus(Dict.APPLY_STATUS_6);
                        traBusinessForUpdate.setContractDate(traBusiness.getContractDate());
                    } else {
                        traBusinessForUpdate.setStatus("7");
                        traBusinessForUpdate.setContractDate(traBusiness.getContractDate());
                    }
                    traBusinessService.updateSelective(traBusinessForUpdate);
                    //交易流水号
                    transNum = existTraBusiness.getTransNum();
                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    if (traBusiness.getPracTradeAmount() != null) {
                        traCounterparty.setStatus(Dict.APPLY_STATUS_6);
                        traCounterparty.setTranDate(traBusiness.getContractDate());
                    } else {
                        traCounterparty.setStatus("7");
                        traCounterparty.setTranDate(traBusiness.getContractDate());
                    }
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "确认成功");
                    return tips;
                }
                //校验审议类型
                checkConsiderType(traBusiness);
               /* if (CollectionUtils.isNotEmpty(tips)) {
                    return tips;
                }*/
                //处理重大交易情况
                checkMajorTrade(traBusiness, relList);
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traBusiness.setRelId(r.getRelId());
                traBusiness.setRelType(r.getRelType());
                traBusiness.setRelName(r.getRelName());
                traBusiness.setRelNumber(r.getRelNumber());
                traBusiness.setTran_Opponent(r.getTranOpponent());
                traBusiness.setCertType(r.getCertType());
                traBusiness.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traBusiness.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                index = traBusinessService.updateSelective(traBusiness);
                //交易流水号
                transNum = existTraBusiness.getTransNum();
                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traBusiness.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traBusiness.getPracTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traBusiness.getStatus());
                    traCounterparty.setTranDate(traBusiness.getContractDate());
                    traCounterparty.setTraSubName(traBusiness.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterparty.setIsTradeAgreement(traBusiness.getIsAgreement());
                    traCounterpartyService.updateSelective(traCounterparty);
                }
            } else {
                //校验审议类型
                checkConsiderType(traBusiness);
               /* if (CollectionUtils.isNotEmpty(tips)) {
                    return tips;
                }*/
                //处理重大交易情况
                checkMajorTrade(traBusiness, relList);

                transNum = traBusiness.getTransNum();
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traBusiness.setRelId(r.getRelId());
                traBusiness.setRelType(r.getRelType());
                traBusiness.setRelName(r.getRelName());
                traBusiness.setRelNumber(r.getRelNumber());
                traBusiness.setTran_Opponent(r.getTranOpponent());
                traBusiness.setCertType(r.getCertType());
                traBusiness.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traBusiness.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traBusinessService.insertSelective(traBusiness);
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traBusiness.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traBusiness.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traBusiness.getStatus());
                    traCounterparty.setTranDate(traBusiness.getContractDate());
                    traCounterparty.setTraSubName(traBusiness.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterparty.setIsTradeAgreement(traBusiness.getIsAgreement());
                    traCounterpartyService.insertSelective(traCounterparty);
                }
            }
/*            if (index != 0) {
                List<TraBusiness> traBusinessList = traBusinessService.select(traBusiness);
                tips.put("pid", traBusinessList.get(0).getPid());
            }*/
            //保存流水号到交易表
            updateDoc(traEnclosureVo, transNum);
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
    @RequestMapping(value = "/removeTraBusiness/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            TraBusiness traBusiness = traBusinessService.get(pid);
            traBusiness.setPid(pid);
            traBusiness.setDelflag("0");
            traBusinessService.updateSelective(traBusiness);
            //同时把交易对手表数据状态改为删除
            TraCounterparty tra = new TraCounterparty();
            tra.setTransNum(traBusiness.getTransNum());
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
     * 查阅页面跳转
     */
    @RequestMapping(value = "/toConfirm/{pid}")
    public String toConfirm(@PathVariable Integer pid, Model model) {
        TraBusiness traBusiness = traBusinessService.get(pid);
        //确认归档后设置状态为以归档
//        traBusiness.setStatus("6");
//        traBusinessService.updateSelective(traBusiness);
        model.addAttribute("traBusiness", traBusiness);
        model.addAttribute("opt", "view");
        model.addAttribute("file", "file");
        model.addAttribute("isConfirm", true);
        return "ins/traBusinessEdit";
    }

    @RequestMapping(value = "/sign/{pid}")
    public String toConfirm1(@PathVariable Integer pid, Model model) {
        TraBusiness traBusiness = traBusinessService.get(pid);
        //确认归档后设置状态为以归档
//        traBusiness.setStatus("6");
//        traBusinessService.updateSelective(traBusiness);
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traBusiness.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traBusiness && counterpartyList != null && counterpartyList.size() > 0) {
            traBusiness.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traBusiness", traBusiness);
        model.addAttribute("opt", "view");
        model.addAttribute("isConfirm", true);
        model.addAttribute("sign", true);
        return "ins/traBusinessEdit";
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
            TraBusiness traBusiness = traBusinessService.get(pid);
            traBusiness.setDelflag("0");
            traBusinessService.updateSelective(traBusiness);
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
     * 验证提交的pid是否已经存在数据库中
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = traBusinessService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = {"/transList/{pid}"}, method = {RequestMethod.GET})
    public String transList(Model model, @PathVariable String pid) {
        model.addAttribute("name", pid);
        return "ins/transList";
    }

    /**
     * 通过点击图谱查询业务交易
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/transBalanceQuery/{pid}"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> transBalanceQuery(Model model, @PathVariable String pid, Page<TraBusiness> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        if (StringUtils.isNotBlank(pid)) {
            List<InsTrans> trans = traBusinessService.selectBusiness(pid);
            PageHelper.end(page, trans);
            result.put(RESULT_TOTAL, page.getTotal());
            result.put(RESULT_ROWS, page.getResult());
        }
        return result;
    }

    /*
     * 任务下发
     * */
    @RequestMapping(value = "/traBusinessSendCommand")
    public String traBusinessSendCommand(Model model) {

        return "ins/traBusinessSend";
    }

    /*
     * 确认下发
     * */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    public Map taskSend(String unitCode, String memo, String traBusinessId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放保险类交易给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", traBusinessId).build();
        TraBusiness traBusiness = traBusinessService.get(filers);
        //更换管库机构为下发机构
        traBusiness.setTradeOrg(unitCode);
        //更新疑似自然人状态为待补录
        traBusiness.setStatus("2");
        traBusiness.setStatusComment(memo);
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
                    notifyService.createRemind(duty.getStaffCode(), "保险业务类交易[" + traBusiness.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到保险业务类交易列表完成补录。", "保险业务类交易[" + traBusiness.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到保险业务类交易列表完成补录。");
                }
                traBusinessService.update(traBusiness);
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

/*    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraBusiness traBusiness = traBusinessService.get(pid);
        if ("3".equals(traBusiness.getStatus()) || "5".equals(traBusiness.getStatus())) {
            TraBusiness traBusiness1 = new TraBusiness();
            traBusiness1.setPid(pid);
            traBusiness1.setStatus("4");
            traBusiness1.setConcentratedApproval("1");
            //4 代表审批中
            traBusiness.setStatus("4");
            traBusinessService.updateSelective(traBusiness1);
            UserProfile profile = UserProfileUtils.getUserProfile();
            String unitCode = profile.getUnitCode();
            String deptCode = profile.getDeptCode();
            //处理数据
            String relInfoListStr = traBusiness.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)){
                //设置关联人信息
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traBusiness.setRelInfoList(traRelInfos);
                //解析json时会报错，此处设置为空
                traBusiness.setRelInfoListStr(null);
            }
            //保存发起人的机构信息
            tips.put("unitCode",unitCode);
            tips.put("deptCode",deptCode);
            tips.put("data", traBusiness);
            String deptName = profile.getDeptName();
            tips.put("deptName",deptName);
            //获取对应总部门的部门编号
            List<String> hdDeptCodeList = approveAssignerService.queryDeptCodeByDeptName(deptName, "8600");
            tips.put("hdDeptCode",CollectionUtil.isNotEmpty(hdDeptCodeList)?hdDeptCodeList.get(0):null);
            //是否是二级部门
            Boolean isSecond = approveAssignerService.isHdSecondDept(deptCode);
            tips.put("isSecond",isSecond);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }*/

    /**
     * 审批页面跳转
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        TraBusiness traBusiness = traBusinessService.get(pid);
        model.addAttribute("traBusiness", traBusiness);
        model.addAttribute("opt", "view");
        return "ins/traBusinessForm";
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
        String notMajorRelNumber = "";
        try {
            BigDecimal bigRelTradeAmount = com.yzj.jep.commons.StringUtils.isEmpty(relTradeAmount) ? null : new BigDecimal(relTradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
            ruleResult = InsUtil.GreatCheckNew(Dict.TRAN_TYPE_03, bigRelTradeAmount, relInfoListStr);

            if (null != ruleResult) {
                InsTrans insTrans = (InsTrans) ruleResult.get("insTrans");
                if (com.yzj.jep.commons.StringUtils.isNotEmpty(insTrans.getMajorTrade())) {
                    isGreate = insTrans.getMajorTrade();
                }
                if (com.yzj.jep.commons.StringUtils.isNotEmpty(insTrans.getMajorType())) {
                    majorType = insTrans.getMajorType();
                }
                if (ruleResult.get("notMajorRelNumber") != null) {
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


    @RequestMapping(value = "/traBusinessCheck")
    @ResponseBody
    public Map<String, Object> tradeCheck(TraBusiness traBusiness) {
        Map<String, Object> result = new HashMap();
//        StringBuffer sb_msg = new StringBuffer();
//        StringBuffer sb_msg2 = new StringBuffer();
//        StringBuffer sb_code = new StringBuffer();
//        Map params = InsUtil.initRuleData(obj.getRelNumber(), obj.getRelId() + "", obj.getRelType(), obj.getTradeAmount() + "", "TraBusiness", null, obj.getBusType());

//        Map overLimit = InsUtil.isOverLimit(traBusiness.getRelNumber(), String.valueOf(traBusiness.getTradeAmount()), traBusiness.getInvestmentType(),
//                null   , traBusiness.getUnderInvolveRel(), traBusiness.getFinancialProScale());

//        List<Map<String, String>> ruleResult = ruleCheckService.ComPlianceCheck("03", params);
//        if (ruleResult != null && ruleResult.size() > 0) {
//            if (ruleResult.size() == 1 && "999999".equals(ruleResult.get(0).get("code"))) { //规则执行出错， 不影响流程
//                result.put(WebCode.SUCCESS, true);
//            } else {
//                for (int i = 0; i < ruleResult.size(); i++) {
//                    if (StringUtils.isNotBlank(ruleResult.get(i).get("code")) && StringUtils.isNotBlank(ruleResult.get(i).get("msg"))) {
//                        String code = ruleResult.get(i).get("code");
//                        String msg = ruleResult.get(i).get("msg");
//                        sb_code.append(code + "|");
//                        sb_msg2.append(msg + "|");
//                        if (i < ruleResult.size() - 1) {
//                            sb_msg.append("[" + msg + "]，");
//                        } else {
//                            sb_msg.append("[" + msg + "]");
//                        }
//                    }
//                }
//                result.put(WebCode.SUCCESS, false);
//                result.put("code", sb_msg2.toString());
//                result.put("msg", "合规检查异常：【" + sb_msg.toString() + "】，您是否继续提交保存？");
//            }
//
//        } else {
        result.put(WebCode.SUCCESS, true);
//        }


//        if(null != ruleResult){ //合规校验结果
//            String code = ruleResult.get("code");
//            String msg = ruleResult.get("msg");
//            if(StringUtils.isNotBlank(code)){//合规校验错误码不为空 出发合规校验规则，返回前端提示语
//                result.put(WebCode.SUCCESS, false);
//                result.put(WebCode.MSG,"合规校验失败：【"+msg+"】，确定要保存吗？");
//                return result;
//            }
//        }

        return result;
    }

    @RequestMapping("/quotaCheck")
    @ResponseBody
    public Map<String, Object> quotaCheck(TraService traService) {
//        String businessType = traService.getBusType();
//        BigDecimal tradeAmount = traService.getTradeAmount();
        Map<String, Object> tips = Maps.newHashMap();
//        Map<String, Object> quotaParam = InsUtil.initQuotaParam("03", businessType, tradeAmount);
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
     * 判断选择的产品是否在保险业务类型下
     *
     * @param unitCodes
     * @return
     */
    @RequestMapping("/selectbuinessL")
    @ResponseBody
    public Map<String, Object> selectDept(String unitCodes) {
        System.out.println(unitCodes);
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
            if (proId != 3) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择保险业务模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId.toString());
                map.put("busType", pid.toString());
                map.put(WebCode.SUCCESS, true);

            }
        } else if ("2".equals(level)) {
            Integer proId = Integer.parseInt(productService.get(productService.get(productService.get(pid).getPaProId()).getPaProId()).getProId());
            if (proId != 3) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择保险业务模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId);
                map.put("busType", productService.get(pid).getPaProId().toString());
                map.put(WebCode.SUCCESS, true);
            }
        }
        return map;
    }

    /**
     * 跳转到集中审议列表页面
     *
     * @return
     */
    @RequestMapping("/batchSendList")
    public String batchSendList() {
        return "ins/traBusinessBatchList";
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
        PageInfo<TraBusiness> pageInfo = traBusinessService.queryBatchSubmit(pageNumber, pageSize, year, quarter);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    @RequestMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<TraBusiness> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        traBusinessService.query(page, filters);
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
        PageInfo<TraBusiness> pageInfo = traBusinessService.queryBatchSubmit(1, Integer.MAX_VALUE, year, quarter);
        List<TraBusiness> list = pageInfo.getList();
        if (CollectionUtil.isEmpty(list)) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "无数据");
            return result;
        }
        //修改审批状态
        List<String> pids = new ArrayList<>();
        for (TraBusiness traBusiness : list) {
            TraBusiness business = new TraBusiness();
            Integer pid = traBusiness.getPid();
            business.setPid(pid);
            business.setStatus("4");
            business.setConcentratedApproval("0");
            traBusinessService.updateSelective(business);
            pids.add(String.valueOf(pid));
            //处理数据
            String relInfoListStr = traBusiness.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                //设置关联人信息
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traBusiness.setRelInfoList(traRelInfos);
                //解析json时会报错，此处设置为空
                traBusiness.setRelInfoListStr(null);
            }
        }
        Map<String, Object> formData = new HashMap<>();
        formData.put("pids", CollectionUtil.join(pids, ","));
        formData.put("data", list);
        formData.put("tranType", Dict.TRAN_TYPE_03);
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

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> sendjz(@PathVariable Integer pid, Model model) throws Exception {
        List<TraFunds> lli = new ArrayList<>();
        Map<String, Object> tips = new HashMap<String, Object>();
        /*List<String> li= Arrays.asList(pid.split(","));*/
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //String staffCode = profile.getStaffCode();
        String procInstId = null;

        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);

        TraBusiness business = traBusinessService.get(pid);
        String relInfoListStr = business.getRelInfoListStr();
        if (StrUtil.isNotBlank(relInfoListStr)) {
            //设置关联人信息
            List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
            business.setRelInfoList(traRelInfos);
            //解析json时会报错，此处设置为空
            business.setRelInfoListStr(null);
        }
        // 文本输入框审批时含有特殊字符需要进行特殊处理
        business.setRelName(CharacterUtil.changeCharacter(business.getRelName()));
        business.setTranName(CharacterUtil.changeCharacter(business.getTranName()));
        business.setTradePrice(CharacterUtil.changeCharacter(business.getTradePrice()));
        business.setPricingPolicy(CharacterUtil.changeCharacter(business.getPricingPolicy()));
        business.setTradeSubject(CharacterUtil.changeCharacter(business.getTradeSubject()));
        business.setRemark(CharacterUtil.changeCharacter(business.getRemark()));
        business.setIsAgreementImpl(CharacterUtil.changeCharacter(business.getIsAgreementImpl()));
        com.alibaba.fastjson.JSONObject data = JSON.parseObject(JSON.toJSONString(business));
        if (org.apache.commons.lang.StringUtils.isNotBlank(business.getProcInstId())) {
            procInstId = business.getProcInstId();
        }
        data.put("TransactionTtype", "business");
        data.put("startStaffCode", staff.getStaffCode());
        data.put("tenantId", profile.getTenantId());
        data.put("type", "business");
        //判断员工所属机构
        Boolean StaffType = this.companyType(profile.getUnitCode());
        String procDefCode = null;
        String considerType = business.getConsiderType();
        //分公司审批
        if (StaffType) {
            if ("3".equals(considerType)) {
                // 分公司审批流 重大统一交易审批流
                data.put("WorkflowType", "ZDTI_BranchOffice");
                procDefCode = "FXBDX_ZDTY_BranchOffice";
            } else {
                // 分公司审批流 一般交易审批流
                data.put("WorkflowType", "YBGLJY_BranchOffice");
                procDefCode = "FXBDX_YBGLJY_Company";
            }

        } else {
            // 总公司审批流 一般交易审批流
            if ("2".equals(considerType)) {
                data.put("WorkflowType", "YBGLJY");
                procDefCode = "FXBDX_YBGLJY";
            } else {
                //总公司审批流 重大统一交易审批流
                data.put("WorkflowType", "ZDTI");
                procDefCode = "FXBDX_ZDTY";
            }
        }
        data.put("procDefCode", procDefCode);
        try {
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "business", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:保险业务类---" + "pid" + pid, e);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
            return tips;
        }


        tips.put("data", lli);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }


    public Boolean companyType(String unitCode) {
        Boolean type = false;
        if (unitCode.equals(CompanyType.CLIENT00.getCategory())) {
            type = true;
        } else if (unitCode.equals(CompanyType.CLIENT01.getCategory())) {
            type = true;
        } else if (unitCode.equals(CompanyType.CLIENT02.getCategory())) {
            type = true;
        } else if (unitCode.equals(CompanyType.CLIENT03.getCategory())) {
            type = true;
        } else if (unitCode.equals(CompanyType.CLIENT04.getCategory())) {
            type = true;
        } else if (unitCode.equals(CompanyType.CLIENT05.getCategory())) {
            type = true;
            //总公司
        } else if (unitCode.equals(CompanyType.YLINS.getCategory())) {
            type = false;
        }
        return type;
    }



    /**
     * 校验审议类型，通过审议类型归类交易状态
     */
    private void checkConsiderType(TraBusiness traBusiness) {
        Map<String, Object> map = new HashMap<>();
        //校验交易 审议状态，1免于审议， 2一般审议， 3逐笔审议
        boolean isEmpty = false;
        String considerType = traBusiness.getConsiderType();
        String isAgreement = traBusiness.getIsAgreement();
        if (considerType.equals("3")) {
            if (com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getBusType()) ||
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getTradePrice()) ||
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getTradeSubject()) ||
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getPricingPolicy()) ||
                    traBusiness.getRelTradeAmount() == null ||          //关联交易金额
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getContractNo()) ||
                    traBusiness.getBusStartTime() == null ||
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getFinancialImpact())||
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getIsCashSubscript())
                   /* || com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getIsDealPrice())*/
            ) {
                isEmpty = true;
            }
            if (isAgreement.equals("1")) {
                if (com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getIsAgreementImpl())
                ) {
                    isEmpty = true;
                }
            }
            if (isEmpty) {
                traBusiness.setStatus("1");
            } else {
                traBusiness.setStatus("3");

            }
        } else {
            if (com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getBusType()) ||   //业务类型
                    traBusiness.getRelTradeAmount() == null ||          //关联交易金额
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getTradePrice()) ||//交易价格
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getPricingPolicy()) ||//定价政策
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getTradeSubject()) ||//交易标的
                    com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getIsCashSubscript())
                /*       || com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getIsDealPrice())*/
            ) {
                isEmpty = true;
            }
            if (isAgreement.equals("1")) {
                if (com.yzj.jep.commons.StringUtils.isEmpty(traBusiness.getIsAgreementImpl())
                ) {
                    isEmpty = true;
                }
            }

            if (isEmpty) {
                traBusiness.setStatus("1");
            } else {
                if (Dict.CONSIDER_TYPE_MYSY.equals(traBusiness.getConsiderType())) {
                    //判断实际交易金额是否为空
                    if(null != traBusiness.getPracTradeAmount() && null != traBusiness.getContractDate()){
                        traBusiness.setStatus("6");
                    }else if(null == traBusiness.getContractDate()){
                        traBusiness.setStatus("8");
                    }else if(null != traBusiness.getContractDate() && null == traBusiness.getPracTradeAmount()){
                        traBusiness.setStatus("7");
                    }
                } else {
                }
                traBusiness.setStatus("3");
            }


        }


    }




    /**
     * 检验必填项
     * @param TraBusiness          数据源
     * 待提交	3
     * 审批中	4
     * 待补录	1
     * 审批退回	5
     * 已归档	6
     * 履行中	7
     * 待签定	8
     */
    public void checkConsiderIsEmpty(TraBusiness traBusiness) {
        if ( StringUtils.isNotBlank(traBusiness.getTranName()) && StringUtils.isNotBlank(traBusiness.getRelName())
                && StringUtils.isNotBlank(traBusiness.getRelNumber()) && StringUtils.isNotBlank(traBusiness.getRelType())
                && StringUtils.isNotBlank(traBusiness.getIsAgreement()) && StringUtils.isNotBlank(traBusiness.getContractNo())
                && null != traBusiness.getRelTradeAmount() && StringUtils.isNotBlank(traBusiness.getIsconsiderSituation())
                    && StringUtils.isNotBlank(traBusiness.getPricingPolicy()) && StringUtils.isNotBlank(traBusiness.getTradePrice())
                && StringUtils.isNotBlank(traBusiness.getTranType()) && StringUtils.isNotBlank(traBusiness.getBusType())
                && StringUtils.isNotBlank(traBusiness.getTradeSubject())) {

            traBusiness.setStatus("3");

            // 金融产品
           /* if ("04".equals(traBusiness.getBusType())) {
                if (StringUtils.isBlank(traBusiness.getFinancialAssstsName()) && StringUtils.isBlank(traBusiness.getFinancialAssstsNum()) && StringUtils.isBlank(traBusiness.getFinancialProRatio())) {
                    traBusiness.setStatus("1");
                }
            }*/
            /* 免于审议时，状态为已归档 */
            if ("1".equals(traBusiness.getConsiderType())) {
                // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                if (StringUtils.isNotBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate()) && ObjectUtil.isNotEmpty(traBusiness.getBusStartTime())) {
                    traBusiness.setStatus("6");
                }
                // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                // 实际交易金额为空 + 合同签署日期为空 = 待签订
                if (ObjectUtil.isEmpty(traBusiness.getContractDate()) || ObjectUtil.isEmpty(traBusiness.getBusStartTime())) {
                    traBusiness.setStatus("8");
                }
                // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                if(StringUtils.isBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate())){
                    traBusiness.setStatus("7");
                }
            }
        }
    }


    /**
     * 检查是否有统一编号
     *
     * @param filters
     * @return
     */
    public boolean checkTradeAgreementNumber(List<Filter> filters) {
        boolean flag = false;
        boolean exists = traBusinessService.exists(filters);
        if (exists) {
            flag = true;
        } else if (traInterestService.exists(filters)) {
            flag = true;
        } else if (traServiceService.exists(filters)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断重大情况并且处理保存信息
     *
     * @param
     * @param relList
     */
    private void checkMajorTrade(TraBusiness traBusiness, List<TraRelInfo> relList) {
        for (TraRelInfo tra : relList) {
            tra.setMajorTrade("0");
            tra.setMajorType("0");
            if (Dict.SF_1.equals(traBusiness.getMajorTrade())) {
                if (Dict.MAJOR_TYPE_DB.equals(traBusiness.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_DB);
                } else if (Dict.MAJOR_TYPE_LJ.equals(traBusiness.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_LJ);
                }
            }
        }
    }

    /**
     * 保存更新文件信息
     *
     * @param traEnclosureVo
     * @param transNum
     */
    private void updateDoc(TraEnclosureVo traEnclosureVo, String transNum) {
        List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
        traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
    }

    /**
     * 编辑按钮查看权限后端查询
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/createPerisExist")
    @ResponseBody
    @Log(module = "关联交易管理", description = "按钮查看权限后端查询")
    public Map<String, Boolean> createPerisExist(Integer pid) {
        Map<String, Boolean> map = new HashMap<>();
        TraBusiness traBusiness = traBusinessService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if ("System".equals(traBusiness.getAmendName())) {
            map.put(WebCode.SUCCESS, true);
            return map;
        }
        if (userid.equals(traBusiness.getAmendId())) {
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
        TraBusiness traBusiness = traBusinessService.get(pid);
        model.addAttribute("traBusiness", traBusiness);
        model.addAttribute("opt", "view");
        model.addAttribute("upload", true);

        return "ins/traBusinessEdit";
    }



}
