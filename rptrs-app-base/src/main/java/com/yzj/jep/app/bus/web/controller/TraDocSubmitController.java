/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月29日 14:02:55
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.TraSubmitVo;
import com.yzj.jep.app.rule.submit.PsnDocSubmitRuler;
import com.yzj.jep.app.rule.submit.TraDocSubmitRuler;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import net.sf.jxls.transformer.XLSTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bus/traDocSubmit")
@PropertySource("classpath:submit.properties")
public class TraDocSubmitController extends CrudController<DocSubmit> {
    private static Logger logger = LoggerFactory.getLogger(TraDocSubmitController.class);

    @Autowired
    private DocSubmitService docSubmitService;

    @Autowired
    private RelOrgSuspectService relOrgSuspectService;

    @Autowired
    private SubmitRecordService submitRecordService;
    /*
     * 授信类
     * */
    @Autowired
    private TraCreditService traCreditService;
    /*
     * 资产转移类
     * */
    @Autowired
    private TraAssetService traAssetService;
    /*
     * 提供服务类
     * */
    @Autowired
    private TraServiceService traServiceService;
    /*
     * 其他类
     * */
    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private TraDocSubmitRuler traDocSubmitRuler;

    @Value("${traSubmitFilePath}")
    private String filePath;
    @Value("${traSubmitTemplatePath}")
    private String traSubmitTemplatePath;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traDocSubmitList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DocSubmit> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("generateTime", OrderType.DESC));
        Iterator<Filter> it = filters.iterator();
        //删除all
        while (it.hasNext()) {
            Filter x = it.next();
            if ("all".equals(x.getValue())) {
                it.remove();
            }
        }

        //做日期判断
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < filters.size(); i++) {
            if ("beginDate".equals(filters.get(i).getProperty())) {
                startIndex = i;
                String startDate = filters.get(i).getValue().toString();
                filters.add(new Filter("generateTime", Operator.GE, startDate));
            }
            if ("endDate".equals(filters.get(i).getProperty())) {
                endIndex = i;
                String endDate = filters.get(i).getValue().toString();
                filters.add(new Filter("generateTime", Operator.LE, endDate));
            }
        }
        //删除两个过滤器  --注意：此处先删除endDate  本着从后往前删除的原则
        if (endIndex != -1) {
            filters.remove(endIndex);
        }
        if (startIndex != -1) {
            filters.remove(startIndex);
        }


        //逻辑删除标记
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        for (int i = 0; i < filters.size(); i++) {

        }
        filters.add(new Filter("submitType", Operator.EQ, "0003"));
        docSubmitService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/traDocSubmitEdit";
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

        DocSubmit docSubmit = docSubmitService.get(pid);
        model.addAttribute("docSubmit", docSubmit);
        model.addAttribute("opt", "edit");

        return "bus/docSubmitEdit";
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

        DocSubmit docSubmit = docSubmitService.get(pid);
        model.addAttribute("docSubmit", docSubmit);
        //查询制度的详情信息
        List<Filter> traFilter = new ArrayList<>();
        traFilter.add(new Filter("batchNo", Operator.EQ, docSubmit.getBatchNo()));
        List<SubmitRecord> submitRecordList = submitRecordService.select(traFilter);
        List<TraSubmitVo> submitVoList = new ArrayList<>();
        TraSubmitVo traSubmitVo;
//        Doc doc;
        for (int i = 0; i < submitRecordList.size(); i++) {
            if ("提供服务类".equals(submitRecordList.get(i).getFromType())) {
                TraService traService = traServiceService.get(Integer.parseInt(submitRecordList.get(i).getFromPid()));
                if (traService == null) {
                    continue;
                }
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traService.getRelatedName());
                traSubmitVo.setValidityContract(traService.getValidityContract());
                traSubmitVo.setFromPid(traService.getPid().toString());
                traSubmitVo.setManageOrg(traService.getHouseManageOrg());
                traSubmitVo.setIdentifyType(traService.getIdentlfyType());
                traSubmitVo.setIdentNumber(traService.getIdentNumber());
                traSubmitVo.setTranAmount(traService.getTranAmount().toString());
                traSubmitVo.setTranType(traService.getTranType());
                traSubmitVo.setBusinessType(traService.getBusinessType());
                traSubmitVo.setRelatedTranType(traService.getRelatedTranType());
                submitVoList.add(traSubmitVo);
            } else if ("资产转移类".equals(submitRecordList.get(i).getFromType())) {
                TraAsset traAsset = traAssetService.get(Integer.parseInt(submitRecordList.get(i).getFromPid()));
                if (traAsset == null) {
                    continue;
                }
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traAsset.getRelatedName());
                traSubmitVo.setValidityContract(traAsset.getValidityContract());
                traSubmitVo.setFromPid(traAsset.getPid().toString());
                traSubmitVo.setManageOrg(traAsset.getManageOrg());
                traSubmitVo.setIdentifyType(traAsset.getIdentifyType());
                traSubmitVo.setIdentNumber(traAsset.getIdentNumber());
                traSubmitVo.setTranAmount(traAsset.getTranAmount().toString());
                traSubmitVo.setTranType(traAsset.getTranType());
                traSubmitVo.setBusinessType(traAsset.getBusinessType());
                traSubmitVo.setRelatedTranType(traAsset.getRelatedTranType());
                submitVoList.add(traSubmitVo);
            } else if ("授信类".equals(submitRecordList.get(i).getFromType())) {
                TraCredit traCredit = traCreditService.get(Integer.parseInt(submitRecordList.get(i).getFromPid()));
                if (traCredit == null) {
                    continue;
                }
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traCredit.getRelatedName());
                traSubmitVo.setValidityContract(traCredit.getValidityContract());
                traSubmitVo.setFromPid(traCredit.getPid().toString());
                traSubmitVo.setManageOrg(traCredit.getManageOrg());
                traSubmitVo.setIdentifyType(traCredit.getIdentifyType());
                traSubmitVo.setIdentNumber(traCredit.getIdentNumber());
                traSubmitVo.setTranAmount(traCredit.getTransNum());
                traSubmitVo.setTranType(traCredit.getTranType());
                traSubmitVo.setBusinessType(traCredit.getBusinessType());
                traSubmitVo.setRelatedTranType(traCredit.getRelatedTranType());
                submitVoList.add(traSubmitVo);
            } else {
                TraOther traOther = traOtherService.get(Integer.parseInt(submitRecordList.get(i).getFromPid()));
                if (traOther == null) {
                    continue;
                }
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traOther.getRelatedName());
                traSubmitVo.setValidityContract(traOther.getValidityContract());
                traSubmitVo.setFromPid(traOther.getPid().toString());
                traSubmitVo.setManageOrg(traOther.getHouseManageOrg());
                traSubmitVo.setIdentifyType(traOther.getIdentlfyType());
                traSubmitVo.setIdentNumber(traOther.getIdentNumber());
                traSubmitVo.setTranAmount(traOther.getTranAmount().toString());
                traSubmitVo.setTranType(traOther.getTranType());
                traSubmitVo.setBusinessType(traOther.getBusinessType());
                traSubmitVo.setRelatedTranType(traOther.getRelatedTranType());
                submitVoList.add(traSubmitVo);
            }
        }
        model.addAttribute("submitVo", submitVoList);
        return "bus/traDocSubmitView";
    }

    /**
     * 保存
     *
     * @param docSubmit
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DocSubmit docSubmit) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = docSubmitService.exists(docSubmit.getPid());
        try {
            if (isExist) {

                docSubmitService.updateSelective(docSubmit);
            } else {
                docSubmitService.insertSelective(docSubmit);
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
    public String remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            docSubmitService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return "bus/traDocSubmitList";
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

        boolean exist = docSubmitService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 统计
     *
     * @return
     */
    @RequestMapping(value = "/census", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> census(DocSubmit docSubmit, String beginDate, String endDate) {

        Map<String, Object> tips = new HashMap<String, Object>();
        List<TraSubmitVo> traSubmitVoList = new ArrayList<>();
        TraSubmitVo traSubmitVo;
        List<Filter> creditFilter = new ArrayList<>();
        List<Filter> assetFilter = new ArrayList<>();
        List<Filter> srviceFilter = new ArrayList<>();
        List<Filter> otherFilter = new ArrayList<>();
        creditFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
        assetFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
        srviceFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
        otherFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
        List<TraCredit> creditList = traCreditService.select(creditFilter);
        List<TraAsset> assetList = traAssetService.select(assetFilter);
        List<TraService> serviceList = traServiceService.select(srviceFilter);
        List<TraOther> otherList = traOtherService.select(otherFilter);
        for (int i = 0; i < creditList.size(); i++) {
            traSubmitVo = new TraSubmitVo();
            traSubmitVo.setName(creditList.get(i).getRelatedName());
            traSubmitVo.setValidityContract(creditList.get(i).getValidityContract());
            traSubmitVo.setFromPid(creditList.get(i).getPid().toString());
            traSubmitVo.setManageOrg(creditList.get(i).getManageOrg());
            traSubmitVo.setIdentifyType(creditList.get(i).getIdentifyType());
            traSubmitVo.setIdentNumber(creditList.get(i).getIdentNumber());
            traSubmitVo.setTranAmount(creditList.get(i).getTransNum());
            traSubmitVo.setTranType(creditList.get(i).getTranType());
            traSubmitVo.setBusinessType(creditList.get(i).getBusinessType());
            traSubmitVo.setRelatedTranType(creditList.get(i).getRelatedTranType());
            traSubmitVoList.add(traSubmitVo);
        }
        for (int j = 0; j < assetList.size(); j++) {
            traSubmitVo = new TraSubmitVo();
            traSubmitVo.setName(assetList.get(j).getRelatedName());
            traSubmitVo.setValidityContract(assetList.get(j).getValidityContract());
            traSubmitVo.setFromPid(assetList.get(j).getPid().toString());
            traSubmitVo.setManageOrg(assetList.get(j).getManageOrg());
            traSubmitVo.setIdentifyType(assetList.get(j).getIdentifyType());
            traSubmitVo.setIdentNumber(assetList.get(j).getIdentNumber());
            traSubmitVo.setTranAmount(assetList.get(j).getTranAmount().toString());
            traSubmitVo.setTranType(assetList.get(j).getTranType());
            traSubmitVo.setBusinessType(assetList.get(j).getBusinessType());
            traSubmitVo.setRelatedTranType(assetList.get(j).getRelatedTranType());
            traSubmitVoList.add(traSubmitVo);
        }
        for (int m = 0; m < serviceList.size(); m++) {
            traSubmitVo = new TraSubmitVo();
            traSubmitVo.setName(serviceList.get(m).getRelatedName());
            traSubmitVo.setValidityContract(serviceList.get(m).getValidityContract());
            traSubmitVo.setFromPid(serviceList.get(m).getPid().toString());
            traSubmitVo.setManageOrg(serviceList.get(m).getHouseManageOrg());
            traSubmitVo.setIdentifyType(serviceList.get(m).getIdentlfyType());
            traSubmitVo.setIdentNumber(serviceList.get(m).getIdentNumber());
            traSubmitVo.setTranAmount(serviceList.get(m).getTranAmount().toString());
            traSubmitVo.setTranType(serviceList.get(m).getTranType());
            traSubmitVo.setBusinessType(serviceList.get(m).getBusinessType());
            traSubmitVo.setRelatedTranType(serviceList.get(m).getRelatedTranType());
            traSubmitVoList.add(traSubmitVo);
        }
        for (int n = 0; n < otherList.size(); n++) {
            traSubmitVo = new TraSubmitVo();
            traSubmitVo.setName(otherList.get(n).getRelatedName());
            traSubmitVo.setValidityContract(otherList.get(n).getValidityContract());
            traSubmitVo.setFromPid(otherList.get(n).getPid().toString());
            traSubmitVo.setManageOrg(otherList.get(n).getHouseManageOrg());
            traSubmitVo.setIdentifyType(otherList.get(n).getIdentlfyType());
            traSubmitVo.setIdentNumber(otherList.get(n).getIdentNumber());
            traSubmitVo.setTranAmount(otherList.get(n).getTranAmount().toString());
            traSubmitVo.setTranType(otherList.get(n).getTranType());
            traSubmitVo.setBusinessType(otherList.get(n).getBusinessType());
            traSubmitVo.setRelatedTranType(otherList.get(n).getRelatedTranType());
            traSubmitVoList.add(traSubmitVo);
        }

        /*
         * 进行日期筛选
         * */
        Date begin = null;
        Date end = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("YYYY-MM-DD");
        if (StringUtil.isNotEmpty(beginDate)) {
            Iterator<TraSubmitVo> it = traSubmitVoList.iterator();
            try {
                begin = formater.parse(beginDate);
                if (StringUtil.isNotEmpty(endDate)) {
                    end = formater.parse(endDate);
                    while (it.hasNext()) {
                        TraSubmitVo x = it.next();
                        // 如果大于开始日期  小于结束日期则不处理
                        if (x.getValidityContract() != null && x.getValidityContract().compareTo(begin) == 1 && x.getValidityContract().compareTo(end) == -1) {

                        } else {
                            it.remove();
                            continue;
                        }
                    }
                } else {
                    //只有开始日期    大于开始日期不做处理
                    while (it.hasNext()) {
                        TraSubmitVo x = it.next();
                        // 如果大于开始日期  小于结束日期则不处理
                        if (x.getValidityContract() != null && x.getValidityContract().compareTo(begin) == 1) {

                        } else {
                            it.remove();
                            continue;
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
//筛选完日期后  剩下的便是统计出的数据数据

        tips.put("traSubmitVo", traSubmitVoList);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "统计完成,共" + traSubmitVoList.size() + "条！");
        return tips;
    }


    /**
     * 验证
     *
     * @return
     */
    @RequestMapping(value = "/checkSubmit/{pid}/{businessType}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> checkSubmit(DocSubmit docSubmit, String beginDate, String endDate, @PathVariable Integer pid, @PathVariable String businessType) {

        Map<String, Object> tips = new HashMap<String, Object>();
        List<TraSubmitVo> traSubmitVoList = new ArrayList<>();
        TraSubmitVo traSubmitVo;
        // pid 为空代表验证全部  否则验证单条
        if ("null".equals(pid) || "null".equals(businessType)) {
            List<Filter> creditFilter = new ArrayList<>();
            List<Filter> assetFilter = new ArrayList<>();
            List<Filter> srviceFilter = new ArrayList<>();
            List<Filter> otherFilter = new ArrayList<>();
            creditFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            assetFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            srviceFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            otherFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            List<TraCredit> creditList = traCreditService.select(creditFilter);
            List<TraAsset> assetList = traAssetService.select(assetFilter);
            List<TraService> serviceList = traServiceService.select(srviceFilter);
            List<TraOther> otherList = traOtherService.select(otherFilter);
            for (int i = 0; i < creditList.size(); i++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(creditList.get(i).getRelatedName());
                traSubmitVo.setValidityContract(creditList.get(i).getValidityContract());
                traSubmitVo.setFromPid(creditList.get(i).getPid().toString());
                traSubmitVo.setManageOrg(creditList.get(i).getManageOrg());
                traSubmitVo.setIdentifyType(creditList.get(i).getIdentifyType());
                traSubmitVo.setIdentNumber(creditList.get(i).getIdentNumber());
                traSubmitVo.setTranAmount(creditList.get(i).getTransNum());
                traSubmitVo.setTranType(creditList.get(i).getTranType());
                traSubmitVo.setBusinessType(creditList.get(i).getBusinessType());
                traSubmitVo.setRelatedTranType(creditList.get(i).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }
            for (int j = 0; j < assetList.size(); j++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(assetList.get(j).getRelatedName());
                traSubmitVo.setValidityContract(assetList.get(j).getValidityContract());
                traSubmitVo.setFromPid(assetList.get(j).getPid().toString());
                traSubmitVo.setManageOrg(assetList.get(j).getManageOrg());
                traSubmitVo.setIdentifyType(assetList.get(j).getIdentifyType());
                traSubmitVo.setIdentNumber(assetList.get(j).getIdentNumber());
                traSubmitVo.setTranAmount(assetList.get(j).getTranAmount().toString());
                traSubmitVo.setTranType(assetList.get(j).getTranType());
                traSubmitVo.setBusinessType(assetList.get(j).getBusinessType());
                traSubmitVo.setRelatedTranType(assetList.get(j).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }
            for (int m = 0; m < serviceList.size(); m++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(serviceList.get(m).getRelatedName());
                traSubmitVo.setValidityContract(serviceList.get(m).getValidityContract());
                traSubmitVo.setFromPid(serviceList.get(m).getPid().toString());
                traSubmitVo.setManageOrg(serviceList.get(m).getHouseManageOrg());
                traSubmitVo.setIdentifyType(serviceList.get(m).getIdentlfyType());
                traSubmitVo.setIdentNumber(serviceList.get(m).getIdentNumber());
                traSubmitVo.setTranAmount(serviceList.get(m).getTranAmount().toString());
                traSubmitVo.setTranType(serviceList.get(m).getTranType());
                traSubmitVo.setBusinessType(serviceList.get(m).getBusinessType());
                traSubmitVo.setRelatedTranType(serviceList.get(m).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }
            for (int n = 0; n < otherList.size(); n++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(otherList.get(n).getRelatedName());
                traSubmitVo.setValidityContract(otherList.get(n).getValidityContract());
                traSubmitVo.setFromPid(otherList.get(n).getPid().toString());
                traSubmitVo.setManageOrg(otherList.get(n).getHouseManageOrg());
                traSubmitVo.setIdentifyType(otherList.get(n).getIdentlfyType());
                traSubmitVo.setIdentNumber(otherList.get(n).getIdentNumber());
                traSubmitVo.setTranAmount(otherList.get(n).getTranAmount().toString());
                traSubmitVo.setTranType(otherList.get(n).getTranType());
                traSubmitVo.setBusinessType(otherList.get(n).getBusinessType());
                traSubmitVo.setRelatedTranType(otherList.get(n).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }

            /*
             * 进行日期筛选
             * */
            Date begin = null;
            Date end = null;
            SimpleDateFormat formater = new SimpleDateFormat();
            formater.applyPattern("YYYY-MM-DD");
            if (StringUtil.isNotEmpty(beginDate)) {
                Iterator<TraSubmitVo> it = traSubmitVoList.iterator();
                try {
                    begin = formater.parse(beginDate);
                    if (StringUtil.isNotEmpty(endDate)) {
                        end = formater.parse(endDate);
                        while (it.hasNext()) {
                            TraSubmitVo x = it.next();
                            // 如果大于开始日期  小于结束日期则不处理
                            if (x.getValidityContract() != null && x.getValidityContract().compareTo(begin) == 1 && x.getValidityContract().compareTo(end) == -1) {

                            } else {
                                it.remove();
                                continue;
                            }
                        }
                    } else {
                        //只有开始日期    大于开始日期不做处理
                        while (it.hasNext()) {
                            TraSubmitVo x = it.next();
                            // 如果大于开始日期  小于结束日期则不处理
                            if (x.getValidityContract() != null && x.getValidityContract().compareTo(begin) == 1) {
                            } else {
                                it.remove();
                                continue;
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //开始规则验证
            List<String> list = new ArrayList<>();
            for (int j = 0; j < traSubmitVoList.size(); j++) {
                String str = traDocSubmitRuler.checkTraDocSubmit(traSubmitVoList.get(j));
                if (str.length() > 0) {
                    list.add(str);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (list.size() == 0) {
                //代表数据没有问题
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "共" + traSubmitVoList.size() + "条记录，全部无问题");
            } else {
                //数据存在问题
                for (int i = 0; i < list.size(); i++) {
                    stringBuilder.append(list.get(i) + "<br/>");
                }
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "数据存在问题");
                tips.put("check", stringBuilder.toString());
            }
        } else {
            if ("提供服务类".equals(businessType)) {
                TraService traService = traServiceService.get(pid);
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traService.getRelatedName());
                traSubmitVo.setValidityContract(traService.getValidityContract());
                traSubmitVo.setFromPid(traService.getPid().toString());
                traSubmitVo.setManageOrg(traService.getHouseManageOrg());
                traSubmitVo.setIdentifyType(traService.getIdentNumber());
                traSubmitVo.setIdentNumber(traService.getIdentNumber());
                traSubmitVo.setTranAmount(traService.getTranAmount().toString());
                traSubmitVo.setTranType(traService.getTranType());
                traSubmitVo.setBusinessType(traService.getBusinessType());
                traSubmitVo.setRelatedTranType(traService.getRelatedTranType());
            } else if ("资产转移类".equals(businessType)) {
                TraAsset traAsset = traAssetService.get(pid);
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traAsset.getRelatedName());
                traSubmitVo.setValidityContract(traAsset.getValidityContract());
                traSubmitVo.setFromPid(traAsset.getPid().toString());
                traSubmitVo.setManageOrg(traAsset.getManageOrg());
                traSubmitVo.setIdentifyType(traAsset.getIdentNumber());
                traSubmitVo.setIdentNumber(traAsset.getIdentNumber());
                traSubmitVo.setTranAmount(traAsset.getTranAmount().toString());
                traSubmitVo.setTranType(traAsset.getTranType());
                traSubmitVo.setBusinessType(traAsset.getBusinessType());
                traSubmitVo.setRelatedTranType(traAsset.getRelatedTranType());
            } else if ("授信类".equals(businessType)) {
                TraCredit traCredit = traCreditService.get(pid);
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traCredit.getRelatedName());
                traSubmitVo.setValidityContract(traCredit.getValidityContract());
                traSubmitVo.setFromPid(traCredit.getPid().toString());
                traSubmitVo.setManageOrg(traCredit.getManageOrg());
                traSubmitVo.setIdentifyType(traCredit.getIdentNumber());
                traSubmitVo.setIdentNumber(traCredit.getIdentNumber());
                traSubmitVo.setTranAmount(traCredit.getTransNum());
                traSubmitVo.setTranType(traCredit.getTranType());
                traSubmitVo.setBusinessType(traCredit.getBusinessType());
                traSubmitVo.setRelatedTranType(traCredit.getRelatedTranType());
            } else {
                TraOther traOther = traOtherService.get(pid);
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(traOther.getRelatedName());
                traSubmitVo.setValidityContract(traOther.getValidityContract());
                traSubmitVo.setFromPid(traOther.getPid().toString());
                traSubmitVo.setManageOrg(traOther.getHouseManageOrg());
                traSubmitVo.setIdentifyType(traOther.getIdentNumber());
                traSubmitVo.setIdentNumber(traOther.getIdentNumber());
                traSubmitVo.setTranAmount(traOther.getTranAmount().toString());
                traSubmitVo.setTranType(traOther.getTranType());
                traSubmitVo.setBusinessType(traOther.getBusinessType());
                traSubmitVo.setRelatedTranType(traOther.getRelatedTranType());
            }
            //校验数据
            String info = traDocSubmitRuler.checkTraDocSubmit(traSubmitVo);
            if (StringUtil.isNotEmpty(info)) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "数据存在问题");
                tips.put("check", info + "<br/>");
            } else {
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "数据无问题");
            }

        }
        return tips;
    }


    /*
     * 生成报送文件
     * */

    @RequestMapping(value = "/makeSubmitFile/{pids}/{businessTypes}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> makeSubmitFile(DocSubmit docSubmit, @PathVariable String pids, @PathVariable String businessTypes, String beginDate, String endDate) {

        Map<String, Object> tips = new HashMap<String, Object>();
        TraSubmitVo traSubmitVo;
        List<TraSubmitVo> traSubmitVoList = new ArrayList<>();

        Integer docYear;
        Integer docMonth;
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");


        Integer batchMaxPid = relOrgSuspectService.getMaxId("bus_submit_batch");
        Integer recordMaxPid = relOrgSuspectService.getMaxId("bus_submit_record");
        if (batchMaxPid == null) {
            batchMaxPid = 0;
        }
        if (recordMaxPid == null) {
            recordMaxPid = 0;
        }
        //入报送
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String formatStr = formatter.format(date);
        String fileName = "";
        String path = filePath;
        docSubmit.setPid(batchMaxPid + 1);
        docSubmit.setBatchNo(formatStr);
        docSubmit.setGenerateTime(date);
        docSubmit.setSubmitType("0003");
        //头次生成默认下载次数为0
        docSubmit.setDownloadCount(0);
        docSubmit.setDelflag("1");

        String srcFilePath = traSubmitTemplatePath;
        Calendar now = Calendar.getInstance();
        //月份
        docSubmit.setMonth((now.get(Calendar.MONTH) + 1) + "");
        //年份
        docSubmit.setYear(now.get(Calendar.YEAR) + "");
        //季度
        Integer mon = Integer.parseInt(docSubmit.getMonth());
        if (mon == 1 || mon == 2 || mon == 3) {
            docSubmit.setQuarter("one");
        } else if (mon == 4 || mon == 5 || mon == 6) {
            docSubmit.setQuarter("two");
        } else if (mon == 7 || mon == 8 || mon == 9) {
            docSubmit.setQuarter("three");
        } else if (mon == 10 || mon == 11 || mon == 12) {
            docSubmit.setQuarter("four");
        }
        if ("null".equals(pids) || pids == null || "null".equals(businessTypes)) {
            fileName = "重大关联交易_" + formatStr + ".xls";
            docSubmit.setFileName(fileName);
            String destFilePath = path + "/" + fileName;
            docSubmit.setFilePath(destFilePath);
            //生成全部信息
            List<Filter> creditFilter = new ArrayList<>();
            List<Filter> assetFilter = new ArrayList<>();
            List<Filter> srviceFilter = new ArrayList<>();
            List<Filter> otherFilter = new ArrayList<>();
            creditFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            assetFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            srviceFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            otherFilter.add(new Filter("relatedTranType", Operator.EQ, "重大关联交易"));
            List<TraCredit> creditList = traCreditService.select(creditFilter);
            List<TraAsset> assetList = traAssetService.select(assetFilter);
            List<TraService> serviceList = traServiceService.select(srviceFilter);
            List<TraOther> otherList = traOtherService.select(otherFilter);
            for (int i = 0; i < creditList.size(); i++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(creditList.get(i).getRelatedName());
                traSubmitVo.setValidityContract(creditList.get(i).getValidityContract());
                traSubmitVo.setFromPid(creditList.get(i).getPid().toString());
                traSubmitVo.setManageOrg(creditList.get(i).getManageOrg());
                traSubmitVo.setIdentifyType(creditList.get(i).getIdentifyType());
                traSubmitVo.setIdentNumber(creditList.get(i).getIdentNumber());
                traSubmitVo.setTranAmount(creditList.get(i).getTransNum());
                traSubmitVo.setTranType(creditList.get(i).getTranType());
                traSubmitVo.setBusinessType(creditList.get(i).getBusinessType());
                traSubmitVo.setRelatedTranType(creditList.get(i).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }
            for (int j = 0; j < assetList.size(); j++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(assetList.get(j).getRelatedName());
                traSubmitVo.setValidityContract(assetList.get(j).getValidityContract());
                traSubmitVo.setFromPid(assetList.get(j).getPid().toString());
                traSubmitVo.setManageOrg(assetList.get(j).getManageOrg());
                traSubmitVo.setIdentifyType(assetList.get(j).getIdentifyType());
                traSubmitVo.setIdentNumber(assetList.get(j).getIdentNumber());
                traSubmitVo.setTranAmount(assetList.get(j).getTranAmount().toString());
                traSubmitVo.setTranType(assetList.get(j).getTranType());
                traSubmitVo.setBusinessType(assetList.get(j).getBusinessType());
                traSubmitVo.setRelatedTranType(assetList.get(j).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }
            for (int m = 0; m < serviceList.size(); m++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(serviceList.get(m).getRelatedName());
                traSubmitVo.setValidityContract(serviceList.get(m).getValidityContract());
                traSubmitVo.setFromPid(serviceList.get(m).getPid().toString());
                traSubmitVo.setManageOrg(serviceList.get(m).getHouseManageOrg());
                traSubmitVo.setIdentifyType(serviceList.get(m).getIdentlfyType());
                traSubmitVo.setIdentNumber(serviceList.get(m).getIdentNumber());
                traSubmitVo.setTranAmount(serviceList.get(m).getTranAmount().toString());
                traSubmitVo.setTranType(serviceList.get(m).getTranType());
                traSubmitVo.setBusinessType(serviceList.get(m).getBusinessType());
                traSubmitVo.setRelatedTranType(serviceList.get(m).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }
            for (int n = 0; n < otherList.size(); n++) {
                traSubmitVo = new TraSubmitVo();
                traSubmitVo.setName(otherList.get(n).getRelatedName());
                traSubmitVo.setValidityContract(otherList.get(n).getValidityContract());
                traSubmitVo.setFromPid(otherList.get(n).getPid().toString());
                traSubmitVo.setManageOrg(otherList.get(n).getHouseManageOrg());
                traSubmitVo.setIdentifyType(otherList.get(n).getIdentlfyType());
                traSubmitVo.setIdentNumber(otherList.get(n).getIdentNumber());
                traSubmitVo.setTranAmount(otherList.get(n).getTranAmount().toString());
                traSubmitVo.setTranType(otherList.get(n).getTranType());
                traSubmitVo.setBusinessType(otherList.get(n).getBusinessType());
                traSubmitVo.setRelatedTranType(otherList.get(n).getRelatedTranType());
                traSubmitVoList.add(traSubmitVo);
            }

            /*
             * 进行日期筛选
             * */
            Date begin = null;
            Date end = null;
            SimpleDateFormat formater = new SimpleDateFormat();
            formater.applyPattern("YYYY-MM-DD");
            if (StringUtil.isNotEmpty(beginDate)) {
                Iterator<TraSubmitVo> it = traSubmitVoList.iterator();
                try {
                    begin = formater.parse(beginDate);
                    if (StringUtil.isNotEmpty(endDate)) {
                        end = formater.parse(endDate);
                        while (it.hasNext()) {
                            TraSubmitVo x = it.next();
                            // 如果大于开始日期  小于结束日期则不处理
                            if (x.getValidityContract().compareTo(begin) == 1 && x.getValidityContract().compareTo(end) == -1) {

                            } else {
                                it.remove();
                                continue;
                            }
                        }
                    } else {
                        //只有开始日期    大于开始日期不做处理
                        while (it.hasNext()) {
                            TraSubmitVo x = it.next();
                            if (x.getValidityContract() != null && x.getValidityContract().compareTo(begin) == 1) {
                            } else {
                                it.remove();
                                continue;
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Map<String, List<TraSubmitVo>> beanParams = new HashMap<String, List<TraSubmitVo>>();
            beanParams.put("traSubmitVo", traSubmitVoList);
            //入报送记录
            SubmitRecord submitRecord = null;
            TraSubmitVo traSubmitVo1 = null;
            //校验数据
            StringBuilder infos = new StringBuilder();
            int count = 0;
            for (int b = 0; b < traSubmitVoList.size(); b++) {
                traSubmitVo1 = traSubmitVoList.get(b);
                submitRecord = new SubmitRecord();
                recordMaxPid++;
                submitRecord.setPid(recordMaxPid);
                submitRecord.setBatchNo(formatStr);
                submitRecord.setFromPid(traSubmitVo1.getFromPid());
                submitRecord.setDelflag("1");
                submitRecord.setSubmitType("0003");
                submitRecord.setFromType(traSubmitVo1.getBusinessType());
                String info = traDocSubmitRuler.checkTraDocSubmit(traSubmitVo1);
                if (info.length() > 0) {
                    count++;
                    submitRecord.setIsSuccess("0");
                } else {
                    submitRecord.setIsSuccess("1");
                }
                submitRecord.setFailCause(info);
                submitRecordService.insertSelective(submitRecord);
                infos.append(info);
            }

            docSubmit.setFailCause(infos.toString());

            if (count == traSubmitVoList.size()) {
                //代表全部存在问题
                docSubmit.setIsSuccess("03");
            } else if (count < traSubmitVoList.size() && count > 0) {
                //代表部分存在问题
                docSubmit.setIsSuccess("02");
            } else if (count == 0) {
                //代表没有问题
                docSubmit.setIsSuccess("01");
            }

            XLSTransformer former = new XLSTransformer();
            try {
                former.transformXLS(srcFilePath, beanParams, destFilePath);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "生成成功");
                docSubmitService.insertSelective(docSubmit);

            } catch (Exception e) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "生成失败");
                e.printStackTrace();
            }

        } else {
            //根据businessType来判断数据来自哪儿
            Integer pid = null;
            String businessType = "";
            String[] pidArr = pids.split(",");
            String[] businessTypeArr = businessTypes.split(",");
            for (int i = 0; i < pidArr.length; i++) {
                pid = Integer.parseInt(pidArr[i]);
                businessType = businessTypeArr[i];
                if ("提供服务类".equals(businessType)) {
                    TraService traService = traServiceService.get(pid);
                    traSubmitVo = new TraSubmitVo();
                    traSubmitVo.setName(traService.getRelatedName());
                    traSubmitVo.setValidityContract(traService.getValidityContract());
                    traSubmitVo.setFromPid(traService.getPid().toString());
                    traSubmitVo.setManageOrg(traService.getHouseManageOrg());
                    traSubmitVo.setIdentifyType(traService.getIdentNumber());
                    traSubmitVo.setIdentNumber(traService.getIdentNumber());
                    traSubmitVo.setTranAmount(traService.getTranAmount().toString());
                    traSubmitVo.setTranType(traService.getTranType());
                    traSubmitVo.setBusinessType(traService.getBusinessType());
                    traSubmitVo.setRelatedTranType(traService.getRelatedTranType());
                } else if ("资产转移类".equals(businessType)) {
                    TraAsset traAsset = traAssetService.get(pid);
                    traSubmitVo = new TraSubmitVo();
                    traSubmitVo.setName(traAsset.getRelatedName());
                    traSubmitVo.setValidityContract(traAsset.getValidityContract());
                    traSubmitVo.setFromPid(traAsset.getPid().toString());
                    traSubmitVo.setManageOrg(traAsset.getManageOrg());
                    traSubmitVo.setIdentifyType(traAsset.getIdentNumber());
                    traSubmitVo.setIdentNumber(traAsset.getIdentNumber());
                    traSubmitVo.setTranAmount(traAsset.getTranAmount().toString());
                    traSubmitVo.setTranType(traAsset.getTranType());
                    traSubmitVo.setBusinessType(traAsset.getBusinessType());
                    traSubmitVo.setRelatedTranType(traAsset.getRelatedTranType());
                } else if ("授信类".equals(businessType)) {
                    TraCredit traCredit = traCreditService.get(pid);
                    traSubmitVo = new TraSubmitVo();
                    traSubmitVo.setName(traCredit.getRelatedName());
                    traSubmitVo.setValidityContract(traCredit.getValidityContract());
                    traSubmitVo.setFromPid(traCredit.getPid().toString());
                    traSubmitVo.setManageOrg(traCredit.getManageOrg());
                    traSubmitVo.setIdentifyType(traCredit.getIdentNumber());
                    traSubmitVo.setIdentNumber(traCredit.getIdentNumber());
                    traSubmitVo.setTranAmount(traCredit.getTransNum());
                    traSubmitVo.setTranType(traCredit.getTranType());
                    traSubmitVo.setBusinessType(traCredit.getBusinessType());
                    traSubmitVo.setRelatedTranType(traCredit.getRelatedTranType());
                } else {
                    TraOther traOther = traOtherService.get(pid);
                    traSubmitVo = new TraSubmitVo();
                    traSubmitVo.setName(traOther.getRelatedName());
                    traSubmitVo.setValidityContract(traOther.getValidityContract());
                    traSubmitVo.setFromPid(traOther.getPid().toString());
                    traSubmitVo.setManageOrg(traOther.getHouseManageOrg());
                    traSubmitVo.setIdentifyType(traOther.getIdentNumber());
                    traSubmitVo.setIdentNumber(traOther.getIdentNumber());
                    traSubmitVo.setTranAmount(traOther.getTranAmount().toString());
                    traSubmitVo.setTranType(traOther.getTranType());
                    traSubmitVo.setBusinessType(traOther.getBusinessType());
                    traSubmitVo.setRelatedTranType(traOther.getRelatedTranType());
                }

                Map<String, TraSubmitVo> beanParams = new HashMap<String, TraSubmitVo>();
                fileName = traSubmitVo.getName() + formatStr + ".xls";
                docSubmit.setFileName(fileName);
                String destFilePath = path + "/" + fileName;
                docSubmit.setFilePath(destFilePath);
                beanParams.put("traSubmitVo", traSubmitVo);
                //入报送记录
                SubmitRecord submitRecord = new SubmitRecord();
                recordMaxPid++;
                submitRecord.setPid(recordMaxPid);
                submitRecord.setBatchNo(formatStr);
                submitRecord.setFromPid(pid.toString());
                submitRecord.setDelflag("1");
                submitRecord.setSubmitType("0003");
                submitRecord.setFromType(traSubmitVo.getBusinessType());
                //校验数据
                String info = traDocSubmitRuler.checkTraDocSubmit(traSubmitVo);
                docSubmit.setFailCause(info);
                submitRecord.setFailCause(info);
                if (StringUtil.isNotEmpty(info)) {
                    docSubmit.setIsSuccess("0");
                } else {
                    docSubmit.setIsSuccess("1");
                }

                XLSTransformer former = new XLSTransformer();
                try {
                    former.transformXLS(srcFilePath, beanParams, destFilePath);
                    submitRecordService.insertSelective(submitRecord);
                } catch (Exception e) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "生成失败");
                    e.printStackTrace();
                    return tips;
                }
            }
            docSubmitService.insertSelective(docSubmit);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "生成成功");
        }
        return tips;
    }


    /**
     * @param pid
     * @return
     */
    @RequestMapping(value = "/download/{pid}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer pid) {
        DocSubmit docSubmit = docSubmitService.get(pid);
        Map<String, Object> tips = new HashMap<String, Object>();
        String filename = docSubmit.getFileName();
        String filePath = docSubmit.getFilePath();
        Integer count = docSubmit.getDownloadCount();
        if (count == null) {
            count = 0;
        }
        File file = new File(filePath);

        if (file.exists()) {
            OutputStream out = null;
            FileInputStream in = null;
            try {
                // 1.读取要下载的内容
                in = new FileInputStream(file);

                // 2. 告诉浏览器下载的方式以及一些设置
                // 解决文件名乱码问题，获取浏览器类型，转换对应文件名编码格式，IE要求文件名必须是utf-8, firefo要求是iso-8859-1编码
                String agent = request.getHeader("user-agent");
                if (agent.contains("FireFox")) {
                    filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                } else {
                    filename = URLEncoder.encode(filename, "UTF-8");
                }
                // 设置下载文件的mineType，告诉浏览器下载文件类型
                String mineType = request.getServletContext().getMimeType(filename);
                response.setContentType(mineType);
                // 设置一个响应头，无论是否被浏览器解析，都下载
                response.setHeader("Content-disposition", "attachment; filename=" + filename);
                // 将要下载的文件内容通过输出流写到浏览器
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                count++;
                docSubmit.setDownloadCount(count);
                docSubmitService.updateSelective(docSubmit);
                tips.put("valid", "下载成功");
            } catch (Exception e) {
                e.printStackTrace();
                tips.put("valid", "下载失败");
            } finally {

                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

            try {
                PrintWriter out = response.getWriter();
                out.write("文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
            }
            tips.put("valid", "文件不存在");
        }

    }
}