/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:16:10
 *
 */

package com.yzj.jep.app.ins.web.controller;

import cn.hutool.core.collection.CollUtil;
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
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
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
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;

/**
 * 提供货物或服务类交易逻辑控制层
 */
@Controller
@RequestMapping("/ins/traService")
public class TraServiceController extends CrudController<TraService> {
    private static Logger logger = LoggerFactory.getLogger(TraServiceController.class);

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private TraInsenclosureService traInsenclosureService;

    @Autowired
    private ProductService productService;

    @Autowired
    BaseService baseService;
    @Autowired
    private ApproveAssignerService approveAssignerService;
    @Autowired
    private UnitService unitService;

    @Autowired
    private RuleCheckService ruleCheckService;
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
        String url = "ins/traService/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);

        return "ins/traServiceList";
    }

    /**
     * 列表页面数据准备以及查询功能
     */
    @RequestMapping(value = "/queryPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareQuery(Page<TraFunds> page, String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString,String dataSources) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.start(page);
        List<TraService> resultList = traServiceService.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,null,dataSources);
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        assert userProfile != null;
        boolean flag = UserRoleUtils.judgeRole(userProfile.getStaffCode());
        String deptCode = userProfile.getDeptCode();
        if (!flag ) {
            PageHelper.start(page);
            resultList = traServiceService.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,deptCode,dataSources);
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
        TraService traService = new TraService();
        traService.setTransNum(BatchNumberUtil.makeBatchNumber());
        //默认国家定价以及现金交易为否
       /* traService.setIsDealPrice(Dict.SF_0);*/
       /* traService.setIscashsubscribe(Dict.SF_0);*/
        traService.setRelInfoListStr("[]");
        traService.setIsAgreement(Dict.SF_0);
        traService.setIsconsiderSituation(Dict.SF_0);
        traService.setTradeAmount(new BigDecimal(0));
        //添加交易主体名称
        traService.setTraSubName(Commons.getTenantName());
        model.addAttribute("traService", traService);
       /* String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        Unit unit = unitService.get(unitCode);
        model.addAttribute("unitName", unit.getUnitName());
        model.addAttribute("unitCode", unit.getUnitCode());*/
        return "ins/traServiceEdit";
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
        TraService traService = traServiceService.get(pid);
        //自然人证件号码脱敏
        if("PSN".equals(traService.getRelType())){
            //查询关联方证件类型
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(traService.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traService.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traService.setRelNumber(idEncrypt(traService.getRelNumber()));
                    }else{
                        traService.setRelNumber(otherIdEncrypt(traService.getRelNumber()));
                    }
                }
            }
        }
        model.addAttribute("traService", traService);
        model.addAttribute("opt", "edit");
        return "ins/traServiceEdit";
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
        TraService traService = traServiceService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traService.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traService && counterpartyList != null && counterpartyList.size() > 0) {
            traService.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        //自然人证件号码脱敏
        if("PSN".equals(traService.getRelType())){
            //查询关联方证件类型
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(traService.getRelNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(traService.getRelNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        traService.setRelNumber(idEncrypt(traService.getRelNumber()));
                    }else{
                        traService.setRelNumber(otherIdEncrypt(traService.getRelNumber()));
                    }
                }
            }
        }
        model.addAttribute("traService", traService);
        model.addAttribute("opt", "view");
        //待查阅数据
        if ("9".equals(traService.getStatus())) {
            model.addAttribute("isConfirm", true);
            confirm(pid);
        }

        return "ins/traServiceEdit";
    }

    /**
     * 判断对象中属性是否有空值
     *
     * @param obj
     * @return
     */
    public static boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {
        boolean flag = false;
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(obj) == null || "".equals(f.get(obj))) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }


    /**
     * 保存
     *
     * @param traService
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraService traService, TraEnclosureVo traEnclosureVo, String proName) {

        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String relInfoStr = traService.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        boolean b = XssCheck.reflect(traService);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }

        //校验是否已经存在该笔交易
        TraService existService = traServiceService.get(traService.getPid());
        try {
            //交易流水号
            String transNum = "";
            traService.setDelflag("1");
            traService.setTranType(Dict.TRAN_TYPE_05);
            traService.setDataSources("1");//手工录入
            assert profile != null;
            traService.setTradeOrg(profile.getUnitCode());
            traService.setAmendId(profile.getStaffCode());
            traService.setAmendName(profile.getStaffName());

            if (existService != null) {
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traService.setRelId(r.getRelId());
                traService.setRelType(r.getRelType());
                traService.setRelName(r.getRelName());
                traService.setRelNumber(r.getRelNumber());
                transNum =existService.getTransNum();
                //待确认修改为已归档
                if (Dict.APPLY_STATUS_8.equals(traService.getStatus())) {
                    //判断实际交易金额是否为空
                    if(null != traService.getPracTradeAmount() && null != traService.getContractDate() && null != traService.getBusStartTime()){
                        traService.setStatus("6");
                    }else if(ObjectUtil.isEmpty(traService.getContractDate())  || ObjectUtil.isEmpty(traService.getBusStartTime())){
                        traService.setStatus("8");
                    }else if(null != traService.getContractDate() && null == traService.getPracTradeAmount()){
                        traService.setStatus("7");
                    }
                    traServiceService.updateSelective(traService);

                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "签订成功");
                    return tips;
                }else if(Dict.APPLY_STATUS_2.equals(traService.getStatus())){
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    boolean b1 = traFundsService.calibrationAccessories(transNum);
                    if(b1){
                        //判断实际交易金额是否为空
                        if(null != traService.getPracTradeAmount() && null != traService.getContractDate() && null != traService.getBusStartTime()){
                            traService.setStatus("6");
                        }else if(ObjectUtil.isEmpty(traService.getContractDate())  || ObjectUtil.isEmpty(traService.getBusStartTime())){
                            traService.setStatus("8");
                        }else if(null != traService.getContractDate() && null == traService.getPracTradeAmount()){
                            traService.setStatus("7");
                        }
                        traServiceService.updateSelective(traService);
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "确认成功");
                        return tips;
                    }else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "存在必传文件未上传。");
                        return tips;                    }
                }
                checkStrIsEmpty(traService);



                //更新成操作人(针对第三方数据)
                traService.setAmendId(profile.getStaffCode());
                traService.setAmendName(profile.getStaffName());

                traServiceService.updateSelective(traService);
                transNum = traServiceService.get(traService.getPid()).getTransNum();

            } else {
                checkStrIsEmpty(traService);


                transNum = traService.getTransNum();
                traService.setModifiedTime(new Date());
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traService.setRelId(r.getRelId());
                traService.setRelType(r.getRelType());
                traService.setRelName(r.getRelName());
                traService.setRelNumber(r.getRelNumber());
                // 是否参与累计清零标识
                traService.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traService.setDeptName(profile.getDeptName());
                traServiceService.insertSelective(traService);

            }

            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traInsenclosureService.updateTransNum(docTableSelectItem, transNum);

            if (traService.getIsAgreement().equals("1") && traService.getIsAgreementImpl().equals("4")) {
                if (null != traService.getContractNo()) {
                    TraService traServiceNew = traServiceService.traServiceByAmount(traService.getContractNo(),traService.getContractAmount().toString());
                    if (traServiceNew != null) {
                        String pracTradeAmount = traServiceNew.getPracTradeAmount();
                        BigDecimal contractAmount = traServiceNew.getContractAmount();
                        BigDecimal pracTradeAmountNumber = new BigDecimal(pracTradeAmount);
                        BigDecimal number = new BigDecimal(100);
                        BigDecimal numbers = new BigDecimal(90);
                        BigDecimal zore = new BigDecimal(0);
                        if (contractAmount.compareTo(zore) > -1) {
                            BigDecimal divideNumber = pracTradeAmountNumber.divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
                            System.out.println(divideNumber);
                            BigDecimal multiplyNumber = divideNumber.multiply(number);
                            System.out.println(multiplyNumber);
                            if (multiplyNumber.compareTo(numbers) == 1) {
                                List list = emailSend.selectPerple();
//                           emailSend.emailSend(list, null);
                                emailSend.mojorEmailEighty(list, null, traService.getRelName(), "3");
                            }
                        }
                    }
                }
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
     * 检验必填项
     * @param traService          数据源
     */
    public void checkStrIsEmpty(TraService traService) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(traService.getBusType()) && null != traService.getContractNo()
                && null != traService.getRelTradeAmount() && org.apache.commons.lang3.StringUtils.isNotBlank(traService.getTradePrice()) && org.apache.commons.lang3.StringUtils.isNotBlank(traService.getPricingPolicy())
                && org.apache.commons.lang3.StringUtils.isNotBlank(traService.getTranName()) && org.apache.commons.lang3.StringUtils.isNotBlank(traService.getTradeSubject())
                && org.apache.commons.lang3.StringUtils.isNotBlank(traService.getIsconsiderSituation()) ) {
            traService.setStatus("3");

            /* 免于审议时，状态为已归档 */
            if ("1".equals(traService.getConsiderType())) {
                // 实际交易金额不为空 + 合同签署日期不为空 = 已归档
                if(org.apache.commons.lang3.StringUtils.isNotBlank(traService.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traService.getContractDate())){
                    traService.setStatus("6");
                }
                // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                // 实际交易金额为空 + 合同签署日期为空 = 待签订
                if (ObjectUtil.isEmpty(traService.getContractDate()) || ObjectUtil.isEmpty(traService.getBusStartTime())) {
                    traService.setStatus("8");
                }
                // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                if(org.apache.commons.lang3.StringUtils.isBlank(traService.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traService.getContractDate())){
                    traService.setStatus("7");
                }
                /* 是统一交易协议时，统一交易审议类型不能为空 */
                if ("1".equals(traService.getIsAgreement())) {
                    if (org.apache.commons.lang3.StringUtils.isBlank(traService.getIsAgreementImpl())) {
                        traService.setStatus("1");
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/saveSign")
    @ResponseBody
    public Map<String, Object> save1(TraService traService, TraEnclosureVo traEnclosureVo, String proName) {

        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String relInfoStr = traService.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        //校验是否已经存在该笔交易
        TraService existService = traServiceService.get(traService.getPid());
        try {
            //交易流水号
            String transNum = "";
            traService.setDelflag("1");
            traService.setTranType(Dict.TRAN_TYPE_05);
            traService.setDataSources("1");//手工录入
            traService.setTradeOrg(profile.getUnitCode());
            traService.setAmendId(profile.getStaffCode());
            traService.setAmendName(profile.getStaffName());
            traService.setRelInfoList(relList);
            traService.setTranDate(traService.getContractDate());
            if (existService != null) {
                //待确认修改为已归档
                if ("8".equals(traService.getStatus())) {
                    //判断实际交易金额是否为空
                    //修改为已归档
                    TraService traServiceForUpdate = new TraService();
                    traServiceForUpdate.setPid(traService.getPid());
                    if (traService.getPracTradeAmount() != null) {
                        traServiceForUpdate.setStatus("6");
                        traServiceForUpdate.setContractDate(traService.getContractDate());
                    } else {
                        traServiceForUpdate.setStatus("7");
                        traServiceForUpdate.setContractDate(traService.getContractDate());

                    }
                    traServiceService.updateSelective(traServiceForUpdate);
                    //交易流水号
                    transNum = existService.getTransNum();
                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    if (traService.getPracTradeAmount() != null) {
                        traCounterparty.setStatus("6");
                        traCounterparty.setTranDate(traService.getContractDate());
                    } else {
                        traCounterparty.setStatus("7");
                        traCounterparty.setTranDate(traService.getContractDate());
                    }
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "确认成功");
                    return tips;
                }
                checkMajorTrade(traService, relList);
                checkConsiderType(traService);
               /* if (CollectionUtils.isNotEmpty(tips)) {
                    return tips;
                }*/

                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traService.setRelId(r.getRelId());
                traService.setRelType(r.getRelType());
                traService.setRelName(r.getRelName());
                traService.setRelNumber(r.getRelNumber());
                traService.setTranOpponent(r.getTranOpponent());
                traService.setCertType(r.getCertType());
                traService.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traService.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traServiceService.updateSelective(traService);
                transNum = traServiceService.get(traService.getPid()).getTransNum();
                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traService.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traService.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traService.getStatus());
                    traCounterparty.setTranDate(traService.getContractDate());
                    traCounterparty.setTraSubName(traService.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setIsTradeAgreement(traService.getIsAgreement());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());

                    traCounterpartyService.updateSelective(traCounterparty);
                }
            } else {
                checkMajorTrade(traService, relList);
                checkConsiderType(traService);
               /* if (CollectionUtils.isNotEmpty(tips)) {
                    return tips;
                }
*/
                transNum = traService.getTransNum();
                traService.setModifiedTime(new Date());
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traService.setRelId(r.getRelId());
                traService.setRelType(r.getRelType());
                traService.setRelName(r.getRelName());
                traService.setRelNumber(r.getRelNumber());
                traService.setTranOpponent(r.getTranOpponent());
                traService.setCertType(r.getCertType());
                traService.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traService.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traServiceService.insertSelective(traService);
                //保存交易对手到新表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traService.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traService.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traService.getStatus());
                    traCounterparty.setTranDate(traService.getContractDate());
                    traCounterparty.setTraSubName(traService.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setIsTradeAgreement(traService.getIsAgreement());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
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
            TraService traService = traServiceService.get(pid);
            traService.setDelflag("0");
            traServiceService.updateSelective(traService);
            //同时把交易对手表数据状态改为删除
            TraCounterparty tra = new TraCounterparty();
            tra.setTransNum(traService.getTransNum());
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

        boolean exist = traServiceService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 反显，查询所属关联方信息（旧）
     */
    @RequestMapping(value = "/selectRelPsn")
    @ResponseBody
    public Map selectRelPsn(String relType, String identifyNumber) {
        Map result = new HashMap();
        result.put("code", 1);
        RelPsn relPsn = new RelPsn();
        RelOrg relOrg = new RelOrg();
        TraService traService = new TraService();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").nnl("aligCode").build();
        if ("PSN".equals(relType)) {
            if (!StringUtils.isEmpty(identifyNumber)) {
                filters.add(new Filter("identifyNumber", identifyNumber));
            }
            relPsn = relPsnService.get(filters);
            if (relPsn != null) {
                result.put("code", 0);
                traService.setRelId(relPsn.getPid());
                traService.setRelName(relPsn.getName());
                traService.setTotolTradeAmount(new BigDecimal(100));
                traService.setMajorTrade("0");
                traService.setCaliber(relPsn.getRegulatorOth1());
                result.put("tra", traService);
            }
        } else {
            if (!StringUtils.isEmpty(identifyNumber)) {
                filters.add(new Filter("uniscCode", identifyNumber));
            }
            relOrg = relOrgService.get(filters);
            if (relOrg != null) {
                result.put("code", 0);
                traService.setRelId(relOrg.getPid());
                traService.setRelName(relOrg.getOrgName());
                traService.setTotolTradeAmount(new BigDecimal(100));
                traService.setMajorTrade("0");
                traService.setCaliber(relOrg.getRegulatorOth1());
                result.put("tra", traService);
            }
        }

        return result;
    }

    /**
     * 查阅页面跳转
     */
    @RequestMapping(value = "/toConfirm/{pid}")
    public String toConfirm(@PathVariable Integer pid, Model model) {
        TraService traService = traServiceService.get(pid);
        //确认归档后设置状态为以归档
//        traService.setStatus("6");
//        traServiceService.updateSelective(traService);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traService.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traService && counterpartyList != null && counterpartyList.size() > 0) {
            traService.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traService", traService);
        model.addAttribute("opt", "view");
        model.addAttribute("file", "file");
        model.addAttribute("isConfirm", true);
        return "ins/traServiceEdit";
    }

    @RequestMapping(value = "/sign/{pid}")
    public String sign(@PathVariable Integer pid, Model model) {
        TraService traService = traServiceService.get(pid);
        //确认归档后设置状态为以归档
//        traService.setStatus("6");
//        traServiceService.updateSelective(traService);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traService.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traService && counterpartyList != null && counterpartyList.size() > 0) {
            traService.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traService", traService);
        model.addAttribute("opt", "view");
        model.addAttribute("isConfirm", true);
        model.addAttribute("sign", true);
        return "ins/traServiceEdit";
    }




    /**
     * 确认管户机构数据
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/confirm/{pid}")
    @ResponseBody
    public Map<String, Object> confirm(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            TraService traService = traServiceService.get(pid);
            traService.setStatus("8");
            traServiceService.updateSelective(traService);
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
     * 反显，查询所属关联方信息
     */
    @RequestMapping(value = "/selectRel")
    @ResponseBody
    public Map selectRel(String relName, String identifyNumber) {
        Map result = new HashMap();
        result = baseService.getRelInfo(identifyNumber, relName);
        return result;
    }

    /**
     * 任务下发
     */
    @RequestMapping(value = "/sendCommand")
    public String relPsnSendCommand(Model model) {

        return "ins/traServiceSend";
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
        TraService traService = traServiceService.get(filers);
        //更换管库机构为下发机构
        traService.setTradeOrg(unitCode);
        //更新疑似自然人状态为待补录
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
                    /*创建提醒*/
                    notifyService.createRemind(duty.getStaffCode(), "提供服务类类交易[" + traService.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易投资入股类列表完成补录。", "提供服务类交易[" + traService.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易投资入股类列表完成补录。");
                }
                traServiceService.update(traService);
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
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraService traService = traServiceService.get(pid);
        if ("3".equals(traService.getStatus()) || "5".equals(traService.getStatus())) {
            //4 代表审批中
            TraService traServiceUpdate = new TraService();
            traServiceUpdate.setPid(pid);
            traServiceUpdate.setStatus("4");
            traService.setStatus("4");
            traServiceService.updateSelective(traServiceUpdate);
            String relInfoListStr = traService.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traService.setRelInfoList(traRelInfos);
                traService.setRelInfoListStr(null);
            }
            UserProfile profile = UserProfileUtils.getUserProfile();
            String unitCode = profile.getUnitCode();
            String deptCode = profile.getDeptCode();
            //保存发起人的机构信息
            tips.put("unitCode", unitCode);
            tips.put("deptCode", deptCode);
            tips.put("data", traService);
            String deptName = profile.getDeptName();
            tips.put("deptName", deptName);
            //获取对应总部门的部门编号
            List<String> hdDeptCodeList = approveAssignerService.queryDeptCodeByDeptName(deptName, "8600");
            tips.put("hdDeptCode", CollectionUtil.isNotEmpty(hdDeptCodeList) ? hdDeptCodeList.get(0) : null);
            //是否是二级部门
            Boolean isSecond = approveAssignerService.isHdSecondDept(deptCode);
            tips.put("isSecond", isSecond);
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
        TraService traService = traServiceService.get(pid);
        model.addAttribute("traService", traService);
        model.addAttribute("opt", "view");

        return "ins/traServiceApprove";
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
//	traFunds
    @RequestMapping(value = "/GreatCheck")
    @ResponseBody
    public Map<String, Object> isGreatCheck(String relTradeAmount, String pracTradeAmount, String relInfoListStr) {
        HashMap<String, Object> result = Maps.newHashMap();
        //调用规则service
        Map ruleResult = null;
        String isGreate = "0";
        String majorType = "0";//0标识不重大
        String notMajorRelNumber = "";
        try {
            BigDecimal bigRelTradeAmount = com.yzj.jep.commons.StringUtils.isEmpty(relTradeAmount) ? null : new BigDecimal(relTradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
            ruleResult = InsUtil.GreatCheckNew(Dict.TRAN_TYPE_05, bigRelTradeAmount, relInfoListStr);

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
            logger.info("调用重大关联交易规则失败。。。 错误信息{}", e);
            result.put(WebCode.SUCCESS, false);
        }
        return result;
    }


    @RequestMapping(value = "/traServiceCheck")
    @ResponseBody
    public Map<String, Object> tradeCheck(TraService obj) {
        Map<String, Object> result = new HashMap();
//        StringBuffer sb_msg = new StringBuffer();
//        StringBuffer sb_msg2 = new StringBuffer();
//        StringBuffer sb_code = new StringBuffer();
//        Map params = InsUtil.initRuleData(obj.getRelNumber(), obj.getRelId() + "", obj.getRelType(), obj.getTradeAmount() + "", "05", null, obj.getBusType());
//        List<Map<String, String>> ruleResult = ruleCheckService.ComPlianceCheck("05", params);
//        if (ruleResult != null && ruleResult.size() > 0) {
//            if (ruleResult.size() == 1 && "999999".equals(ruleResult.get(0).get("code"))) { //规则执行出错， 不影响流程
//                result.put(WebCode.SUCCESS, true);
//            } else if (ruleResult.size() == 1 && "C910".equals(ruleResult.get(0).get("code"))) {
//                result.put(WebCode.SUCCESS, true);
//                result.put("msg", ruleResult.get(0).get("msg"));
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
//        Map<String, Object> quotaParam = InsUtil.initQuotaParam("05", businessType, tradeAmount);
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
     * 判断选择的产品是否在提供服务类类型下
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
            //判断选择的产品是否属于提供服务类
            if (proId != 5) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择提供服务类模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId.toString());
                map.put("busType", pid.toString());
                map.put(WebCode.SUCCESS, true);

            }
        } else if ("2".equals(level)) {
            Integer proId = Integer.parseInt(productService.get(productService.get(productService.get(pid).getPaProId()).getPaProId()).getProId());
            if (proId != 5) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择提供服务类模块下的产品或者业务类型！！！");
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
        return "ins/traServiceBatchList";
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
        PageInfo<TraService> pageInfo = traServiceService.queryBatchSubmit(pageNumber, pageSize, year, quarter);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    @RequestMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<TraService> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        traServiceService.query(page, filters);
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
        PageInfo<TraService> pageInfo = traServiceService.queryBatchSubmit(1, Integer.MAX_VALUE, year, quarter);
        List<TraService> list = pageInfo.getList();
        if (CollectionUtil.isEmpty(list)) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "无数据");
            return result;
        }
        //修改审批状态
        List<String> pids = new ArrayList<>();
        for (TraService traService : list) {
            TraService service = new TraService();
            Integer pid = traService.getPid();
            service.setPid(pid);
            service.setStatus("4");
            traServiceService.updateSelective(service);
            pids.add(String.valueOf(pid));
            String relInfoListStr = traService.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traService.setRelInfoList(traRelInfos);
                traService.setRelInfoListStr(null);
            }
        }
        Map<String, Object> formData = new HashMap<>();
        formData.put("pids", CollectionUtil.join(pids, ","));
        formData.put("data", list);
        formData.put("tranType", Dict.TRAN_TYPE_05);
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

        TraService service = traServiceService.get(pid);

        String relInfoListStr = service.getRelInfoListStr();
        if (StrUtil.isNotBlank(relInfoListStr)) {
            //设置关联人信息
            List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
            service.setRelInfoList(traRelInfos);
            //解析json时会报错，此处设置为空
            service.setRelInfoListStr(null);
        }
        // 文本输入框审批时含有特殊字符需要进行特殊处理
        service.setRelName(CharacterUtil.changeCharacter(service.getRelName()));
        service.setTranName(CharacterUtil.changeCharacter(service.getTranName()));
        service.setTradePrice(CharacterUtil.changeCharacter(service.getTradePrice()));
        service.setPricingPolicy(CharacterUtil.changeCharacter(service.getPricingPolicy()));
        service.setTradeSubject(CharacterUtil.changeCharacter(service.getTradeSubject()));
        service.setRemark(CharacterUtil.changeCharacter(service.getRemark()));
        service.setIsAgreementImpl(CharacterUtil.changeCharacter(service.getIsAgreementImpl()));
        com.alibaba.fastjson.JSONObject data = JSON.parseObject(JSON.toJSONString(service));
        if (org.apache.commons.lang3.StringUtils.isNotBlank(service.getProcInstId())) {
            procInstId = service.getProcInstId();
        }
        data.put("type", "service");
        data.put("TransactionTtype", "service");
        data.put("startStaffCode", staff.getStaffCode());
        data.put("tenantId", profile.getTenantId());
        String procDefCode = null;
        //判断员工所属机构
        Boolean StaffType = this.companyType(profile.getUnitCode());
        String considerType = service.getConsiderType();
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
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "service", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:提供服务类---" + "pid" + pid, e);
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
    private void checkConsiderType(TraService traService) {
        Map<String, Object> map = new HashMap<>();
        //校验交易 审议状态，1免于审议， 2一般审议， 3逐笔审议
        boolean isEmpty = false;
        String considerType = traService.getConsiderType();
        String isAgreement = traService.getIsAgreement();
        if (considerType.equals("3")) {
            if (
                    StringUtils.isEmpty(traService.getBusType()) || //业务类型
                            StringUtils.isEmpty(traService.getTradePrice()) || //交易价格
                            StringUtils.isEmpty(traService.getTradeSubject()) || //交易标的
                            StringUtils.isEmpty(traService.getPricingPolicy()) || //定价政策
                            StringUtils.isEmpty(traService.getRelTradeAmount().toString()) ||  //关联交易金额
                            StringUtils.isEmpty(traService.getContractNo()) ||
                            StringUtils.isEmpty(traService.getTradeSubject()) || //交易标的
                            traService.getBusStartTime() == null || //业务起始日期
                            StringUtils.isEmpty(traService.getFinancialImpact()) //对财务状况及经营成果的影响
            ) {
                //所有类型都必填的，缺失即为待补录
                isEmpty = true;
            } else {
                //发起方是否为控股子公司
                if ("1".equals(traService.getIsHoldingSubsidiary()) && (StringUtils.isEmpty(traService.getHoldingSubsidiaryName()) ||
                        StringUtils.isEmpty(traService.getHoldingSubsidiaryNumber()))) {
                    //待补录
                    isEmpty = true;
                }

            }
            if (isAgreement.equals("1")) {
                if (com.yzj.jep.commons.StringUtils.isEmpty(traService.getIsAgreementImpl())
                ) {
                    isEmpty = true;
                }
            }
            if (isEmpty) {
                traService.setStatus("1");
            } else {

                traService.setStatus("3");

            }
        } else {
            if (com.yzj.jep.commons.StringUtils.isEmpty(traService.getBusType()) ||   //业务类型
                    traService.getRelTradeAmount() == null ||          //关联交易金额
                    com.yzj.jep.commons.StringUtils.isEmpty(traService.getTradePrice()) ||//交易价格
                    com.yzj.jep.commons.StringUtils.isEmpty(traService.getPricingPolicy()) ||//定价政策
                    com.yzj.jep.commons.StringUtils.isEmpty(traService.getTradeSubject())//交易标的
            ) {
                isEmpty = true;
            } else {
                //发起方是否为控股子公司
                if ("1".equals(traService.getIsHoldingSubsidiary()) && (StringUtils.isEmpty(traService.getHoldingSubsidiaryName()) ||
                        StringUtils.isEmpty(traService.getHoldingSubsidiaryNumber()))) {
                    //待补录
                    isEmpty = true;
                }

            }


            if (isAgreement.equals("1")) {
                if (com.yzj.jep.commons.StringUtils.isEmpty(traService.getIsAgreementImpl())
                ) {
                    isEmpty = true;
                }
            }

            if (isEmpty) {
                traService.setStatus("1");
            } else {
                if (Dict.CONSIDER_TYPE_MYSY.equals(traService.getConsiderType())) {
                    //判断实际交易金额是否为空
                    if(null != traService.getPracTradeAmount() && null != traService.getTranDate()){
                        traService.setStatus("6");
                    }else if(null == traService.getTranDate()){
                        traService.setStatus("8");
                    }else if(null != traService.getTranDate() && null == traService.getPracTradeAmount()){
                        traService.setStatus("7");
                    }
                } else {
                    traService.setStatus("3");
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
     * @param traService
     * @param relList
     */
    private void checkMajorTrade(TraService traService, List<TraRelInfo> relList) {
        for (TraRelInfo tra : relList) {
            tra.setMajorTrade("0");
            tra.setMajorType("0");
            if (Dict.SF_1.equals(traService.getMajorTrade())) {
                if (Dict.MAJOR_TYPE_DB.equals(traService.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_DB);
                } else if (Dict.MAJOR_TYPE_LJ.equals(traService.getMajorType())) {
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
        TraService traService = traServiceService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if ("System".equals(traService.getAmendName())) {
            map.put(WebCode.SUCCESS, true);
            return map;
        }
        if (userid.equals(traService.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);

        }
        return map;
    }

    /*@RequestMapping(value = "/checkIsMajorTran1")
    @ResponseBody
    public void checkIsMajorTran1() {
        List<TraBusiness> select = traBusinessService.selectAll();

        for (TraBusiness traBusiness : select) {
            Map<String, Object> stringObjectMap = checkIsMajorTran(traBusiness.getRelType(), traBusiness.getRelNumber(), traBusiness.getIsconsiderSituation(), traBusiness.getIsAgreement(), String.valueOf(traBusiness.getRelTradeAmount()), traBusiness.getTradeAgreementType());

            String majorTrade = (String) stringObjectMap.get("majorTrade");
            String singleRelatedTrantype = (String) stringObjectMap.get("singleRelatedTrantype");
            String majorType = (String) stringObjectMap.get("majorType");
            String considerType = (String) stringObjectMap.get("considerType");
            String eachReport = (String) stringObjectMap.get("eachReport");
            String eachDisclosure = (String) stringObjectMap.get("eachDisclosure");
            String isconsiderSituation = (String) stringObjectMap.get("isconsiderSituation");
            if (StringUtils.isNotEmpty(isconsiderSituation)) {
                String considerSituation = (String) stringObjectMap.get("considerSituation");
                traBusiness.setIsconsiderSituation(isconsiderSituation);
                traBusiness.setConsiderSituation(considerSituation);
            }

            String isCollectRelated = (String) stringObjectMap.get("isCollectRelated");
            traBusiness.setIsCollectRelated(isCollectRelated);
            traBusiness.setMajorTrade(majorTrade);
            traBusiness.setSingleRelatedTranType(singleRelatedTrantype);
            traBusiness.setMajorType(majorType);
            traBusiness.setConsiderType(considerType);
            traBusiness.setEachReport(eachReport);
            traBusiness.setEachDisclosure(eachDisclosure);
            traBusinessService.update(traBusiness);
        }
        System.out.println("-------------------------> 已完成！！！");
    }*/

    /**
     * 校验管关联交易是否为重大关联交易
     */

    @RequestMapping(value = "/checkIsMajorTran")
    @ResponseBody
    public Map<String, Object> checkIsMajorTran(String relType, String relNumber, String isConsiderSituation, String isAgreement, String relTradeAmount, String tradeAgreementType) {
        Map<String, Object> tips = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(relType) && org.apache.commons.lang3.StringUtils.isNotBlank(relNumber) && org.apache.commons.lang3.StringUtils.isNotBlank(relTradeAmount)){
            param.put("relType",relType);
            param.put("relNumber",relNumber);
            param.put("isConsiderSituation",isConsiderSituation);
            param.put("isAgreement",isAgreement);
            param.put("relTranAmount", relTradeAmount);
            param.put("tradeAgreementType", tradeAgreementType);
            // 如果统一交易协议类型为4时审议类型为免于审议
//            if ("1".equals(isAgreement)) {
//                if ("4".equals(tradeAgreementType)) {
//                    param.put("isAgreement", null);
//                    param.put("isConsiderSituation", "1");
//                    isConsiderSituation = "1";
//                }
//            }
            Map<String, String> result = null;
            try {
                result = InsUtil.judgeMajor1(param);
                if ("1".equals(isConsiderSituation)) {
                    result.put("considerType", "1");
                }
            } catch (Exception e) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, e.getMessage());
                return tips;
            }
            if (CollUtil.isNotEmpty(result)) {
                tips.put("majorTrade", result.get("majorTrade"));
                tips.put("singleRelatedTrantype", result.get("singleRelatedTrantype"));
                tips.put("majorType", result.get("majorType"));
                tips.put("considerType", result.get("considerType"));
                tips.put("eachReport", result.get("eachReport"));
                tips.put("eachDisclosure", result.get("eachDisclosure"));
                // 是否达到特别重大 1-是、0-否
                tips.put("specialMajorTrade", result.get("specialMajorTrade"));
                tips.put("isCollectRelated", result.get("isCollectRelated"));
                /* 是否免于审议字段没值时需要赋值 */
                if (org.apache.commons.lang3.StringUtils.isBlank(isConsiderSituation) && "1".equals(result.get("considerType"))) {
                    tips.put("isconsiderSituation", "1");
                    if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(relType)) {
                        tips.put("considerSituation", "3");
                    }
                    if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(relType)) {
                        tips.put("considerSituation", "4");
                    }
                }
//                if(org.apache.commons.lang3.StringUtils.isNotBlank(isConsiderSituation) && "0".equals(isConsiderSituation)){
//                    if("0".equals(result.get("majorTrade")) && "1".equals(result.get("considerType"))){
//                        /* 免于审议类型为否时，系统算出来不是重大且审议类型为免于审议时，已用户选择为准，审议类型重新赋值 */
//                        tips.put("considerType", "2");
//                    }
//                }
                tips.put(WebCode.SUCCESS, true);
                return tips;
            }
            tips.put(WebCode.SUCCESS, false);
            return tips;
        }
        tips.put(WebCode.SUCCESS, false);
        tips.put(WebCode.MSG, "参数信息不全，无法计算是否重大关联交易。");
        return tips;
    }

    /**
     * 关联交易审议类型为逐笔时需要校验法人关联方的必填数据
     * @param relType           关联方类型
     * @param relId             关联方ID
     * @return                  返回结果
     */
    @RequestMapping(value = "/jungleRelatedDate")
    @ResponseBody
    @Log(module = "关联交易管理", description = "关联交易审议类型为逐笔时需要校验法人关联方的必填数据")
    public Map<String, Boolean> jungleRelatedDate(String relType, int pid, String tranType) {
        Map<String ,Boolean> map = new HashMap<>(1);
        map.put(WebCode.SUCCESS, false);
        String relNumber = "";
        if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(relType)) {
            switch (tranType){
                case "1":
                    TraFunds traFunds = traFundsService.get(pid);
                    relNumber = traFunds.getRelNumber();
                    break;
                case "2":
                    TraService traService = traServiceService.get(pid);
                    relNumber = traService.getRelNumber();
                    break;
                case  "3":
                    TraInterest traInterest = traInterestService.get(pid);
                    relNumber = traInterest.getRelNumber();
                    break;
                case "4":
                    TraBusiness traBusiness = traBusinessService.get(pid);
                    relNumber = traBusiness.getRelNumber();
                    break;
                default:
                    relNumber = null;
                    break;
            }

            List<Filter> filters = new Filter.Builder().eq("delflag", "1").eq("uniscCode", relNumber).build();
            List<RelOrg> relOrgList = relOrgService.select(filters);
            if(CollUtil.isNotEmpty(relOrgList)){
                RelOrg relOrg = relOrgList.get(0);
                if (StringUtils.isEmpty(relOrg.getLegalPerson())
                        || StringUtils.isEmpty(relOrg.getOrgNature())
                        || ObjectUtil.isEmpty(relOrg.getRegCapital())
                        || StringUtils.isEmpty(relOrg.getBusinessScope())
                        || StringUtils.isEmpty(relOrg.getRegAddress())){
                    map.put(WebCode.SUCCESS, true);
                }
            }
        }
        return map;
    }
    /**
     * 附件上传
     */
    @RequestMapping(value = "/attachmentAdd/{pid}")
    public String attachmentAdd(@PathVariable Integer pid, Model model, HttpServletRequest request) {
        Commons.common2(request, model);
        TraService traService = traServiceService.get(pid);
        model.addAttribute("traService", traService);
        model.addAttribute("opt", "view");
        model.addAttribute("upload", true);

        return "ins/traServiceEdit";
    }


    @RequestMapping(value = "/saveFile")
    @ResponseBody
    public Map<String, Object> saveFile(String transNum, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<>();
        if(org.apache.commons.lang3.StringUtils.isBlank(transNum)){
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传附件失败，字段【transNum】 为空!");
            return tips;
        }
        //保存文件信息
        try {
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
        } catch (ServiceException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传附件失败:" + e.getMessage());
            return tips;
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "上传附件成功");
        return tips;
    }
}
