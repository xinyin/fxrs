/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:56
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
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.bus.service.RelOrgService;
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
import com.yzj.jep.commons.StringUtils;
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
 * 利益转移类
 */
@Controller
@RequestMapping("/ins/traInterest")
public class TraInterestController extends CrudController<TraInterest> {
    private static Logger logger = LoggerFactory.getLogger(TraInterestController.class);

    @Autowired
    private TraInterestService traInterestService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private DutyService dutyService;

    @Autowired
    private RuleCheckService ruleCheckService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private TraInsenclosureService traInsenclosureService;

    @Autowired
    BaseService baseService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApproveAssignerService approveAssignerService;
    @Autowired
    private UnitService unitService;

    @Autowired
    private TraCounterpartyService traCounterpartyService;

    @Autowired
    WorkflowOperation workflowOperation;

    @Autowired
    private EmailSend emailSend;

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
        String url = "ins/traInterest/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        //查询登陆人机构及下辖机构的信息
       /* UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();

            //管户机构
            model.addAttribute("unitCode",unitCode);
            model.addAttribute("opt","MH");*/

        return "ins/traInterestList";
    }

    /**
     * 列表页面数据准备以及查询功能
     */
    @RequestMapping(value = "/queryPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareQuery(Page<TraFunds> page, String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString,String dataSources) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.start(page);
        List<TraInterest> resultList = traInterestService.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,null,dataSources);;
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        assert userProfile != null;
        boolean flag = UserRoleUtils.judgeRole(userProfile.getStaffCode());
        String deptCode = userProfile.getDeptCode();
        if (!flag ) {
            PageHelper.start(page);
            resultList = traInterestService.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,deptCode,dataSources);
        }
        resultList.forEach(e -> {
            //自然人证件号码脱敏
            if("PSN".equals(e.getRelType())){
                //查询关联方证件类型
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(e.getRelNumber())){
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
        TraInterest traInterest = new TraInterest();
        traInterest.setTransNum(BatchNumberUtil.makeBatchNumber());

        traInterest.setTradeAmount(new BigDecimal(0));
        traInterest.setRelInfoListStr("[]");
        traInterest.setIsconsiderSituation(Dict.SF_0);
        //添加交易主体名称
        traInterest.setTraSubName(Commons.getTenantName());
        model.addAttribute("traInterest", traInterest);

        return "ins/traInterestEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
//    @Log(module = "利益转移类", description = "根据pid编辑利益转移信息")
    public String edit(@PathVariable Integer pid, Model model) {

        TraInterest traInterest = traInterestService.get(pid);
        //自然人证件号码脱敏
        if("PSN".equals(traInterest.getRelType())){
            //查询关联方证件类型
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(traInterest.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traInterest.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traInterest.setRelNumber(idEncrypt(traInterest.getRelNumber()));
                    }else{
                        traInterest.setRelNumber(otherIdEncrypt(traInterest.getRelNumber()));
                    }
                }
            }
        }
        model.addAttribute("traInterest", traInterest);
        model.addAttribute("opt", "edit");
        return "ins/traInterestEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "利益转移类", description = "查看数据详细信息")
    public String view(@PathVariable Integer pid, Model model, HttpServletRequest request) {
        Commons.common2(request, model);
        TraInterest traInterest = traInterestService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traInterest.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traInterest && counterpartyList != null && counterpartyList.size() > 0) {
            traInterest.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        //自然人证件号码脱敏
        if("PSN".equals(traInterest.getRelType())){
            //查询关联方证件类型
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(traInterest.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traInterest.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traInterest.setRelNumber(idEncrypt(traInterest.getRelNumber()));
                    }else{
                        traInterest.setRelNumber(otherIdEncrypt(traInterest.getRelNumber()));
                    }
                }
            }
        }
        model.addAttribute("traInterest", traInterest);
        model.addAttribute("opt", "view");

        //待查阅数据
        if ("9".equals(traInterest.getStatus())) {
            model.addAttribute("isConfirm", true);
            confirm(pid);
        }

        return "ins/traInterestEdit";
    }

    /**
     * 保存
     *
     * @param traInterest
     * @return
     */
    @RequestMapping(value = "/saveSign")
    @ResponseBody
    @Log(module = "利益转移类", description = "保存-新增或修改信息")
    public Map<String, Object> save1(TraInterest traInterest, TraEnclosureVo traEnclosureVo, String proName) {

        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        //校验是否已经存在该笔交易
        TraInterest existInterest = traInterestService.get(traInterest.getPid());
        String relInfoStr = traInterest.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        try {
            //交易流水号
            String transNum = "";
            traInterest.setDelflag("1");
            traInterest.setTranType(Dict.TRAN_TYPE_04);
            traInterest.setDataSources("1");//手工录入
            traInterest.setTradeOrg(profile.getUnitCode());
            traInterest.setAmendId(profile.getStaffCode());
            traInterest.setAmendName(profile.getStaffName());
            traInterest.setRelInfoList(relList);
            traInterest.setTranDate(traInterest.getContractDate());
            if (existInterest != null) {
                //待确认修改为已归档
                if ("8".equals(traInterest.getStatus())) {
                    //判断实际交易金额是否为空
                    //修改为已归档
                    TraInterest traInterestForUpdate = new TraInterest();
                    traInterestForUpdate.setPid(traInterest.getPid());
                    if (traInterest.getPracTradeAmount() != null) {
                        traInterestForUpdate.setStatus("6");
                        traInterestForUpdate.setContractDate(traInterest.getContractDate());
                    } else {
                        traInterestForUpdate.setStatus("7");
                        traInterestForUpdate.setContractDate(traInterest.getContractDate());
                    }
                    traInterestService.updateSelective(traInterestForUpdate);
                    //交易流水号
                    transNum = existInterest.getTransNum();
                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    if (traInterest.getPracTradeAmount() != null) {
                        traCounterparty.setStatus("6");
                        traCounterparty.setTranDate(traInterest.getContractDate());
                    } else {
                        traCounterparty.setStatus("7");
                        traCounterparty.setTranDate(traInterest.getContractDate());
                    }
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "确认成功");
                    return tips;
                }
                checkMajorTrade(traInterest, relList);
                checkConsiderType(traInterest);
              /*  if (CollectionUtils.isNotEmpty(tips)) {
                    return tips;
                }
*/
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traInterest.setRelId(r.getRelId());
                traInterest.setRelType(r.getRelType());
                traInterest.setRelName(r.getRelName());
                traInterest.setRelNumber(r.getRelNumber());
                traInterest.setTranOpponent(r.getTranOpponent());
                traInterest.setCertType(r.getCertType());
                traInterest.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traInterest.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traInterestService.updateSelective(traInterest);
                transNum = traInterestService.get(traInterest.getPid()).getTransNum();
                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traInterest.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traInterest.getPracTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traInterest.getStatus());
                    traCounterparty.setTranDate(traInterest.getContractDate());
                    traCounterparty.setTraSubName(traInterest.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterparty.setIsTradeAgreement(traInterest.getIsAgreement());
                    traCounterpartyService.updateSelective(traCounterparty);
                }
            } else {
                checkMajorTrade(traInterest, relList);
                checkConsiderType(traInterest);
                /*if (CollectionUtils.isNotEmpty(tips)) {
                    return tips;
                }*/
                transNum = traInterest.getTransNum();
                traInterest.setModifiedTime(new Date());
                TraRelInfo r = relList.get(0);
                traInterest.setRelId(r.getRelId());
                traInterest.setRelType(r.getRelType());
                traInterest.setRelName(r.getRelName());
                traInterest.setRelNumber(r.getRelNumber());
                traInterest.setTranOpponent(r.getTranOpponent());
                traInterest.setCertType(r.getCertType());
                traInterest.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traInterest.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traInterestService.insertSelective(traInterest);
                //保存关联交易对手信息到新表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traInterest.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traInterest.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traInterest.getStatus());
                    traCounterparty.setTranDate(traInterest.getContractDate());
                    traCounterparty.setTraSubName(traInterest.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterparty.setIsTradeAgreement(traInterest.getIsAgreement());
                    traCounterpartyService.insertSelective(traCounterparty);
                }
            }

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


    @RequestMapping(value = "/save")
    @ResponseBody
    @Log(module = "利益转移类", description = "保存-新增或修改信息")
    public Map<String, Object> save(TraInterest traInterest, TraEnclosureVo traEnclosureVo, String proName) {

        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        //校验是否已经存在该笔交易
        TraInterest existInterest = traInterestService.get(traInterest.getPid());
        String relInfoStr = traInterest.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        boolean b = XssCheck.reflect(traInterest);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }

        try {
            //交易流水号
            String transNum = "";
            traInterest.setDelflag("1");
            traInterest.setTranType(Dict.TRAN_TYPE_04);
            traInterest.setDataSources("1");//手工录入
            assert profile != null;
            traInterest.setTradeOrg(profile.getUnitCode());
            traInterest.setAmendId(profile.getStaffCode());
            traInterest.setAmendName(profile.getStaffName());

            if (existInterest != null) {
                transNum =existInterest.getTransNum();
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traInterest.setRelId(r.getRelId());
                traInterest.setRelType(r.getRelType());
                traInterest.setRelName(r.getRelName());
                traInterest.setRelNumber(r.getRelNumber());
                //待确认修改为已归档
                if (Dict.APPLY_STATUS_8.equals(traInterest.getStatus())) {
                    //判断实际交易金额是否为空
                    if(null != traInterest.getPracTradeAmount() && null != traInterest.getContractDate()  && null != traInterest.getBusStartTime()){
                        traInterest.setStatus("6");
                    }else if(null == traInterest.getContractDate() || null == traInterest.getBusStartTime()){
                        traInterest.setStatus("8");
                    }else if(null != traInterest.getContractDate() && null == traInterest.getPracTradeAmount()){
                        traInterest.setStatus("7");
                    }
                    traInterestService.updateSelective(traInterest);


                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "签订成功");
                    return tips;
                }else if(Dict.APPLY_STATUS_2.equals(traInterest.getStatus())){
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    boolean b1 = traFundsService.calibrationAccessories(transNum);
                    if(b1){
                        //判断实际交易金额是否为空
                        if(null != traInterest.getPracTradeAmount() && null != traInterest.getContractDate()  && null != traInterest.getBusStartTime()){
                            traInterest.setStatus("6");
                        }else if(null == traInterest.getContractDate() || null == traInterest.getBusStartTime()){
                            traInterest.setStatus("8");
                        }else if(null != traInterest.getContractDate() && null == traInterest.getPracTradeAmount()){
                            traInterest.setStatus("7");
                        }
                        traInterestService.updateSelective(traInterest);
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "确认成功");
                        return tips;
                    }else {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "存在必传文件未上传。");
                    return tips;
                    }
                }
                checkStrIsEmpty(traInterest);



                //更新成操作人(针对第三方数据)
                traInterest.setAmendId(profile.getStaffCode());
                traInterest.setAmendName(profile.getStaffName());

                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traInterest.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traInterestService.updateSelective(traInterest);

            } else {
                checkStrIsEmpty(traInterest);

                transNum = traInterest.getTransNum();
                traInterest.setModifiedTime(new Date());
                TraRelInfo r = relList.get(0);
                traInterest.setRelId(r.getRelId());
                traInterest.setRelType(r.getRelType());
                traInterest.setRelName(r.getRelName());
                traInterest.setRelNumber(r.getRelNumber());

                // 是否参与累计清零标识
                traInterest.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traInterest.setDeptName(profile.getDeptName());
                traInterestService.insertSelective(traInterest);

            }

            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
//            if (traInterest.getIsAgreement().equals("1") && traInterest.getIsAgreementImpl().equals("4")) {
//                if (null != traInterest.getContractNo()) {
//                    TraInterest traInterestNew = traInterestService.traBusinessByAmount(traInterest.getContractNo(), traInterest.getContractAmount().toString());
//                    if (null != traInterestNew) {
//                        String pracTradeAmount = traInterestNew.getPracTradeAmount();
//                        BigDecimal contractAmount = traInterestNew.getContractAmount();
//                        BigDecimal pracTradeAmountNumber = new BigDecimal(pracTradeAmount);
//                        BigDecimal number = new BigDecimal(100);
//                        BigDecimal numbers = new BigDecimal(90);
//                        BigDecimal zore = new BigDecimal(0);
//                        if (contractAmount.compareTo(zore) > -1) {
//                            BigDecimal divideNumber = pracTradeAmountNumber.divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
//                            System.out.println(divideNumber);
//                            BigDecimal multiplyNumber = divideNumber.multiply(number);
//                            System.out.println(multiplyNumber);
//                            if (multiplyNumber.compareTo(numbers) == 1) {
//                                List list = emailSend.selectPerple();
////                           emailSend.emailSend(list, null);
//                                emailSend.mojorEmailEighty(list, null, traInterest.getRelName(), "3");
//                            }
//                        }
//                    }
//                }
//            }

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
     * 检验必填项
     * @param traInterest          数据源
     */
    public void checkStrIsEmpty(TraInterest traInterest) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(traInterest.getBusType()) && null != traInterest.getContractNo()
                && null != traInterest.getRelTradeAmount() && org.apache.commons.lang3.StringUtils.isNotBlank(traInterest.getTradePrice()) && org.apache.commons.lang3.StringUtils.isNotBlank(traInterest.getPricingPolicy())
                && org.apache.commons.lang3.StringUtils.isNotBlank(traInterest.getTranName()) && org.apache.commons.lang3.StringUtils.isNotBlank(traInterest.getTradeSubject())
                && org.apache.commons.lang3.StringUtils.isNotBlank(traInterest.getIsconsiderSituation()) ) {
            traInterest.setStatus("3");

            /* 免于审议时，状态为已归档 */
            if ("1".equals(traInterest.getConsiderType())) {
                // 实际交易金额不为空 + 合同签署日期不为空 = 已归档
                if(org.apache.commons.lang3.StringUtils.isNotBlank(traInterest.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traInterest.getContractDate())){
                    traInterest.setStatus("6");
                }
                // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                // 实际交易金额为空 + 合同签署日期为空 = 待签订
                if (ObjectUtil.isEmpty(traInterest.getContractDate()) || ObjectUtil.isEmpty(traInterest.getBusStartTime())) {
                    traInterest.setStatus("8");
                }
                // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                if(org.apache.commons.lang3.StringUtils.isBlank(traInterest.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traInterest.getContractDate())){
                    traInterest.setStatus("7");
                }
                /* 是统一交易协议时，统一交易审议类型不能为空 */
                if ("1".equals(traInterest.getIsAgreement())) {
                    if (org.apache.commons.lang3.StringUtils.isBlank(traInterest.getIsAgreementImpl())) {
                        traInterest.setStatus("1");
                    }
                }
            }
        }
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
     * 附件上传
     */
    @RequestMapping(value = "/attachmentAdd/{pid}")
    public String attachmentAdd(@PathVariable Integer pid, Model model, HttpServletRequest request) {
        Commons.common2(request, model);
        TraInterest traInterest = traInterestService.get(pid);
        model.addAttribute("traInterest", traInterest);
        model.addAttribute("opt", "view");
        model.addAttribute("upload", true);

        return "ins/traInterestEdit";
    }

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    @Log(module = "利益转移类", description = "删除相关pid信息")
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            TraInterest traInterest = traInterestService.get(pid);
            traInterest.setDelflag("0");
            traInterestService.updateSelective(traInterest);
            //同时把交易对手表数据状态改为删除
            TraCounterparty tra = new TraCounterparty();
            tra.setTransNum(traInterest.getTransNum());
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
        TraInterest traInterest = traInterestService.get(pid);
        //确认归档后设置状态为以归档
//        traInterest.setStatus("6");
//        traInterestService.updateSelective(traInterest);
        model.addAttribute("traInterest", traInterest);
        model.addAttribute("opt", "view");
        model.addAttribute("file", "file");
        model.addAttribute("isConfirm", true);
        return "ins/traInterestEdit";
    }

    @RequestMapping(value = "/sign/{pid}")
    public String sign(@PathVariable Integer pid, Model model) {
        TraInterest traInterest = traInterestService.get(pid);
        //确认归档后设置状态为以归档
//        traInterest.setStatus("6");
//        traInterestService.updateSelective(traInterest);
        model.addAttribute("traInterest", traInterest);
        model.addAttribute("opt", "view");
        model.addAttribute("isConfirm", true);
        model.addAttribute("sign", true);
        return "ins/traInterestEdit";
    }


    @RequestMapping(value = "/toBatchConfirm/{pids}")
    public String toBatchConfirm(@PathVariable("pids") String pids, Model model) {
        //获取所有的选中的数据
        String[] pidArr = pids.split(pids);
        model.addAttribute("traInterest", null);
        model.addAttribute("opt", "view");
        model.addAttribute("isConfirm", true);
        return "ins/traInterestBatchEdit";
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
            TraInterest traInterest = traInterestService.get(pid);
            traInterest.setStatus("8");
            traInterestService.updateSelective(traInterest);
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
    @Log(module = "利益转移类", description = "验证提交数是否存在")
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = traInterestService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 获取关联方信息
     */
    @RequestMapping(value = "/getRelInfo")
    @ResponseBody
    @Log(module = "利益转移类", description = "获取关联方信息")
    public Map<String, Object> getRelInfo(TraInterest traInterest) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            String relNumber = traInterest.getRelNumber();
            String relType = traInterest.getRelType();
            String relName = traInterest.getRelName();
            tips = TraUtil.getRelInfo(unitCode, relNumber, relType, relName);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 反显，查询所属自然人信息
     */
    @RequestMapping(value = "/selectRelPsn")
    @ResponseBody
    @Log(module = "利益转移类", description = "反显查询所属自然人信息")
    public Map selectRelPsn(String relType, String identifyNumber) {
        Map result = new HashMap();
        result.put("code", 1);
        RelPsn relPsn = new RelPsn();
        RelOrg relOrg = new RelOrg();
        TraInterest traInterest = new TraInterest();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").nnl("aligCode").build();
        if ("PSN".equals(relType)) {
            if (!StringUtils.isEmpty(identifyNumber)) {
                filters.add(new Filter("identifyNumber", identifyNumber));
            }
            relPsn = relPsnService.get(filters);
            if (relPsn != null) {
                result.put("code", 0);
                traInterest.setRelId(relPsn.getPid());
                traInterest.setRelName(relPsn.getName());
                traInterest.setTotolTradeAmount(new BigDecimal(100));
                traInterest.setMajorTrade("0");
                result.put("tra", traInterest);
            }
        } else {
            if (!StringUtils.isEmpty(identifyNumber)) {
                filters.add(new Filter("uniscCode", identifyNumber));
            }
            relOrg = relOrgService.get(filters);
            if (relOrg != null) {
                result.put("code", 0);
                traInterest.setRelId(relOrg.getPid());
                traInterest.setRelName(relOrg.getOrgName());
                traInterest.setTotolTradeAmount(new BigDecimal(100));
                traInterest.setMajorTrade("0");
                result.put("tra", traInterest);
            }
        }

        return result;
    }


    /*
     * 任务下发
     * */
    @RequestMapping(value = "/traInterestSend")
    @Log(module = "利益转移类", description = "任务下发")
    public String traAssetSend(Model model) {


        return "ins/traInterestSend";

    }

    /**
     * 确认下发
     */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    @Log(module = "利益转移类", description = "任务下发确定")
    public Map taskSend(String unitCode, String memo, String sendOrgId) {

        Map<String, Object> tips = new HashMap<String, Object>();
        //发放疑似机构给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendOrgId).build();

        TraInterest traInterest = traInterestService.get(filers);

        //更换管库机构为下发机构
        traInterest.setTradeOrg(unitCode);
        //更新疑似机构状态为待补录

        traInterest.setStatus("2");
        traInterest.setStatusComment(memo);

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
                    notifyService.createRemind(duty.getStaffCode(), "机构[" + traInterest.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。", "机构[" + traInterest.getRelNumber() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。");
                }
                traInterestService.update(traInterest);
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

    /*
     * 提交审批
     * */

/*    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    @Log(module = "利益转移类", description = "提交审批")
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraInterest traInterest = traInterestService.get(pid);
        if ("3".equals(traInterest.getStatus()) || "5".equals(traInterest.getStatus())) {
            //4 代表审批中
            TraInterest traInterest1 = new TraInterest();
            traInterest1.setPid(pid);
            traInterest1.setStatus("4");
            traInterest1.setConcentratedApproval("1");
            traInterest.setStatus("4");
            traInterestService.updateSelective(traInterest1);
            String relInfoListStr = traInterest.getRelInfoListStr();
            if(StrUtil.isNotBlank(relInfoListStr)){
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traInterest.setRelInfoList(traRelInfos);
                traInterest.setRelInfoListStr(null);
            }
            UserProfile profile = UserProfileUtils.getUserProfile();
            String unitCode = profile.getUnitCode();
            String deptCode = profile.getDeptCode();
            //保存发起人的机构信息
            tips.put("unitCode",unitCode);
            tips.put("deptCode",deptCode);
            tips.put("data", traInterest);
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
        TraInterest traInterest = traInterestService.get(pid);
        model.addAttribute("traInterest", traInterest);
        model.addAttribute("opt", "view");
        return "ins/traInterestApprove";
    }

    /**
     * 反显，查询所属关联方信息
     */
    @RequestMapping(value = "/selectRel")
    @ResponseBody
    @Log(module = "利益转移类", description = "反显查询所属关联方信息")
    public Map selectRel(String relName, String identifyNumber) {
        Map result = new HashMap();
        result = baseService.getRelInfo(identifyNumber, relName);
        return result;
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
    @Log(module = "利益转移类", description = "判断是否重大关联交易")
    public Map<String, Object> isGreatCheck(String relTradeAmount, String relInfoListStr) {
        HashMap<String, Object> result = Maps.newHashMap();
        //调用规则service
        Map ruleResult = null;
        String isGreate = "0";
        String majorType = "0";//0标识不重大
        String notMajorRelNumber = "";
        try {
            BigDecimal bigRelTradeAmount = com.yzj.jep.commons.StringUtils.isEmpty(relTradeAmount) ? null : new BigDecimal(relTradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
            ruleResult = InsUtil.GreatCheckNew(Dict.TRAN_TYPE_04, bigRelTradeAmount, relInfoListStr);

            if (null != ruleResult) {
                InsTrans insTrans = (InsTrans) ruleResult.get("insTrans");
                if (StringUtils.isNotEmpty(insTrans.getMajorTrade())) {
                    isGreate = insTrans.getMajorTrade();
                }
                if (StringUtils.isNotEmpty(insTrans.getMajorType())) {
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
            logger.error("利益转移调用重大关联交易规则失败。。。 错误信息{}", e);
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "调用重大关联交易规则失败。。。");
        }
        return result;
    }


    @RequestMapping(value = "/traInterestCheck")
    @ResponseBody
    public Map<String, Object> tradeCheck(TraInterest obj) {
        Map<String, Object> result = new HashMap();
//        StringBuffer sb_msg = new StringBuffer();
//        StringBuffer sb_msg2 = new StringBuffer();
//        StringBuffer sb_code = new StringBuffer();
//        Map params = InsUtil.initRuleData(obj.getRelNumber(), obj.getRelId() + "", obj.getRelType(), obj.getTradeAmount() + "", "TraInterest", null, obj.getBusType());

//        Map overLimit = InsUtil.isOverLimit(traFunds.getRelNumber(), String.valueOf(traFunds.getTradeAmount()), traFunds.getInvestmentType(),
//                traFunds.getIsOverseasinvestment(), traFunds.getUnderInvolveRel(), traFunds.getFinancialProScale());

//        if(overLimit.get("limitCode").equals("1")){
        result.put(WebCode.SUCCESS, true);
//        }else{
//            String msg = String.valueOf(overLimit.get("limitMsg"));
//            result.put(WebCode.SUCCESS, false);
//            result.put(WebCode.MSG,"合规校验失败：【"+msg+"】，确定要保存吗？");
//            return result;
//        }


//        List<Map<String, String>> ruleResult = ruleCheckService.ComPlianceCheck("04", params);
//        if (ruleResult != null && ruleResult.size() > 0) {
//            if (ruleResult.size() == 1 && "999999".equals(ruleResult.get(0).get("code"))) { //规则执行出错， 不影响流程
//                result.put(WebCode.SUCCESS, true);
//            } else {
//                for (int i = 0; i < ruleResult.size(); i++) {
//                    if (org.apache.commons.lang.StringUtils.isNotBlank(ruleResult.get(i).get("code")) && org.apache.commons.lang.StringUtils.isNotBlank(ruleResult.get(i).get("msg"))) {
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
//            result.put(WebCode.SUCCESS, true);
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
//        Map<String, Object> quotaParam = InsUtil.initQuotaParam("04", businessType, tradeAmount);
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
     * 判断选择的产品是否在利益转移类型下
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

            //判断选择的产品是否属于利益转移类
            if (proId != 4) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择利益转移模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId.toString());
                map.put("busType", pid.toString());
                map.put(WebCode.SUCCESS, true);

            }
        } else if ("2".equals(level)) {
            Integer proId = Integer.parseInt(productService.get(productService.get(productService.get(pid).getPaProId()).getPaProId()).getProId());
            if (proId != 4) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择利益转移模块下的产品或者业务类型！！！");
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
        return "ins/traInterestBatchList";
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
        PageInfo<TraInterest> pageInfo = traInterestService.queryBatchSubmit(pageNumber, pageSize, year, quarter);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    @RequestMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<TraInterest> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        traInterestService.query(page, filters);
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
        PageInfo<TraInterest> pageInfo = traInterestService.queryBatchSubmit(1, Integer.MAX_VALUE, year, quarter);
        List<TraInterest> list = pageInfo.getList();
        if (CollectionUtil.isEmpty(list)) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "无数据");
            return result;
        }
        //修改审批状态
        List<String> pids = new ArrayList<>();
        for (TraInterest traInterest : list) {
            TraInterest interest = new TraInterest();
            Integer pid = traInterest.getPid();
            interest.setPid(pid);
            interest.setStatus("4");
            interest.setConcentratedApproval("0");
            traInterestService.updateSelective(interest);
            pids.add(String.valueOf(pid));
            String relInfoListStr = traInterest.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traInterest.setRelInfoList(traRelInfos);
                traInterest.setRelInfoListStr(null);
            }
        }
        Map<String, Object> formData = new HashMap<>();
        formData.put("pids", CollectionUtil.join(pids, ","));
        formData.put("data", list);
        formData.put("tranType", Dict.TRAN_TYPE_04);
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

        TraInterest interest = traInterestService.get(pid);

        String relInfoListStr = interest.getRelInfoListStr();
        if (StrUtil.isNotBlank(relInfoListStr)) {
            //设置关联人信息
            List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
            interest.setRelInfoList(traRelInfos);
            //解析json时会报错，此处设置为空
            interest.setRelInfoListStr(null);
        }
        // 文本输入框审批时含有特殊字符需要进行特殊处理
        interest.setRelName(CharacterUtil.changeCharacter(interest.getRelName()));
        interest.setTranName(CharacterUtil.changeCharacter(interest.getTranName()));
        interest.setTradePrice(CharacterUtil.changeCharacter(interest.getTradePrice()));
        interest.setPricingPolicy(CharacterUtil.changeCharacter(interest.getPricingPolicy()));
        interest.setTradeSubject(CharacterUtil.changeCharacter(interest.getTradeSubject()));
        interest.setRemark(CharacterUtil.changeCharacter(interest.getRemark()));
        com.alibaba.fastjson.JSONObject data = JSON.parseObject(JSON.toJSONString(interest));
        if (org.apache.commons.lang.StringUtils.isNotBlank(interest.getProcInstId())) {
            procInstId = interest.getProcInstId();
        }
        data.put("TransactionTtype", "interest");
        data.put("startStaffCode", staff.getStaffCode());
        data.put("tenantId", profile.getTenantId());
        data.put("type", "interest");
        String procDefCode = null;
        //判断员工所属机构
        Boolean StaffType = this.companyType(profile.getUnitCode());
        String considerType = interest.getConsiderType();
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
//                procDefCode = "";
            }
        }
        data.put("procDefCode", procDefCode);
        try {
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "interest", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:利益转移类---" + "pid" + pid, e);
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
    private void checkConsiderType(TraInterest traInterest) {
        Map<String, Object> map = new HashMap<>();
        //校验交易 审议状态，1免于审议， 2一般审议， 3逐笔审议
        boolean isEmpty = false;
        String considerType = traInterest.getConsiderType();
        String isAgreement = traInterest.getIsAgreement();
        if (considerType.equals("3")) {
            if (
                    StringUtils.isEmpty(traInterest.getBusType()) || //业务类型
                            StringUtils.isEmpty(traInterest.getContractNo()) || //合同编号
                            // traInterest.getContractAmount() ==null || //合同金额
                            // StringUtils.isEmpty(traInterest.getContractDate().toString()) || //合同签订日期
                            traInterest.getBusStartTime() == null ||
                            traInterest.getRelTradeAmount() == null || //关联交易金额为空
/*                            StringUtils.isEmpty(traInterest.getIsCashSubscription()) || //是否现金认购
                            StringUtils.isEmpty(traInterest.getIsDealPrice()) || //价格是否国家规定*/
                            StringUtils.isEmpty(traInterest.getTradePrice()) || //交易价格
                            StringUtils.isEmpty(traInterest.getTradeSubject()) || //交易标的
                            StringUtils.isEmpty(traInterest.getPricingPolicy()) || //定价政策
                            /* StringUtils.isEmpty(traInterest.getRemark())||*/
                            // traInterest.getBusStartTimeString() == null ||  //业务起始日期
                            StringUtils.isEmpty(traInterest.getFinancialImpact()) //对财务影响

            ) {
                //所有类型都必填的，缺失即为待补录
                isEmpty = true;
            } else {
                //业务类型为1出售资产时，标的资产情况，前次交易价格，前次交易时间
                if ("1".equals(traInterest.getBusType())) {
                    if (StringUtils.isEmpty(traInterest.getLastTradePrice()) || StringUtils.isEmpty(traInterest.getTradeSubjectAssets()) || traInterest.getLastTradeTime() == null) {
                        isEmpty = true;
                    }
                } else if ("2".equals(traInterest.getBusType())) {// 02 租赁资产时必填，标的资产情况，前次交易价格
                    if (StringUtils.isEmpty(traInterest.getLastTradePrice()) || StringUtils.isEmpty(traInterest.getTradeSubjectAssets())) {
                        isEmpty = true;
                    }
                } else if ("3".equals(traInterest.getBusType()) || "4".equals(traInterest.getBusType()) || "8".equals(traInterest.getBusType()) || "9".equals(traInterest.getBusType()) || "10".equals(traInterest.getBusType())) {
                    if (StringUtils.isEmpty(traInterest.getSubjectSituation())) {
                        isEmpty = true;//业务类型为3权利转让，4担保 8债权转移，9债务转移10签订许可协议 必填 标的情况
                    }
                } else if ("4".equals(traInterest.getBusType())) {//业务类型为4担保，担保条件必填
                    if (StringUtils.isEmpty(traInterest.getGuaranteeConditions())) {
                        isEmpty = true;
                    }
                }
                //发起方是否为控股子公司
                if ("1".equals(traInterest.getIsHoldingSubsidiary()) && (StringUtils.isEmpty(traInterest.getHoldingSubsidiaryName()) ||
                        StringUtils.isEmpty(traInterest.getHoldingSubsidiaryNumber()))) {
                    //待补录
                    isEmpty = true;
                }

          /*  if ("1".equals(traInterest.getIsAgreement()) && (StringUtils.isEmpty(traInterest.getIsAgreementImpl()) ||
                    StringUtils.isEmpty(traInterest.getTradeAgreementNumber()))) {
                //待补录
                isEmpty = true;
            }*/



            /*else {
                    List<Filter> filters = new ArrayList<>();
                    filters.add(Filter.eq("tradeAgreementNumber", traInterest.getTradeAgreementNumber()));
                    filters.add(Filter.ne("transNum",traInterest.getTransNum()));
                    if ("1".equals(traInterest.getIsAgreement()) && (traInterest.getIsAgreementImpl().equals("4"))) {
                        boolean exists = checkTradeAgreementNumber(filters);
                        if (!exists) {
                            map.put(WebCode.SUCCESS, false);
                            map.put(WebCode.MSG, "不存在此笔统一交易协议，请重新输入");
                            return map;
                        }
                    } else {
                        boolean exists = checkTradeAgreementNumber(filters);
                        if (exists) {
                            map.put(WebCode.SUCCESS, false);
                            map.put(WebCode.MSG, "该编号已存在，请重新输入");
                            return map;
                        }
                    }
            }*/

            }

            if (isAgreement.equals("1")) {
                if (com.yzj.jep.commons.StringUtils.isEmpty(traInterest.getIsAgreementImpl())
                ) {
                    isEmpty = true;
                }
            }

            if (isEmpty) {
                traInterest.setStatus("1");
            } else {

                traInterest.setStatus("3");

            }


        } else {
            if (com.yzj.jep.commons.StringUtils.isEmpty(traInterest.getBusType()) ||   //业务类型
                    traInterest.getRelTradeAmount() == null ||          //关联交易金额
                    com.yzj.jep.commons.StringUtils.isEmpty(traInterest.getTradePrice()) ||//交易价格
                    com.yzj.jep.commons.StringUtils.isEmpty(traInterest.getPricingPolicy()) ||//定价政策
                    com.yzj.jep.commons.StringUtils.isEmpty(traInterest.getTradeSubject()) //交易标的
/*                    ||StringUtils.isEmpty(traInterest.getIsCashSubscription()) || //是否现金认购
                    StringUtils.isEmpty(traInterest.getIsDealPrice())  //价格是否国家规定*/
            ) {
                isEmpty = true;
            }
            if (isAgreement.equals("1")) {
                if (com.yzj.jep.commons.StringUtils.isEmpty(traInterest.getIsAgreementImpl())
                ) {
                    isEmpty = true;
                }
            }

            if (isEmpty) {
                traInterest.setStatus("1");
            } else {
                if (Dict.CONSIDER_TYPE_MYSY.equals(traInterest.getConsiderType())) {
                    //判断实际交易金额是否为空
                    if(null != traInterest.getPracTradeAmount() && null != traInterest.getContractDate()){
                        traInterest.setStatus("6");
                    }else if(null == traInterest.getContractDate()){
                        traInterest.setStatus("8");
                    }else if(null != traInterest.getContractDate() && null == traInterest.getPracTradeAmount()){
                        traInterest.setStatus("7");
                    }

                } else {
                    traInterest.setStatus("3");
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
     * @param traInterest
     * @param relList
     */
    private void checkMajorTrade(TraInterest traInterest, List<TraRelInfo> relList) {
        for (TraRelInfo tra : relList) {
            tra.setMajorTrade("0");
            tra.setMajorType("0");
            if (Dict.SF_1.equals(traInterest.getMajorTrade())) {
                if (Dict.MAJOR_TYPE_DB.equals(traInterest.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_DB);
                } else if (Dict.MAJOR_TYPE_LJ.equals(traInterest.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_LJ);
                }
            }
        }
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
        TraInterest traInterest = traInterestService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if ("System".equals(traInterest.getAmendName())) {
            map.put(WebCode.SUCCESS, true);
            return map;
        }
        if (userid.equals(traInterest.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);

        }
        return map;
    }

}
