package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.bus.model.ReportAnnual;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = StatisticalDetailExportCallBackImpl.BEANID)
public class StatisticalDetailExportCallBackImpl implements XlsExportCallback {
    public static final String BEANID = "StatisticalDetailExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "StatisticalDateil.xls";

    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraOtherService traOtherService;

    /**
     * @desc 文件路径
     * @return
     */
    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    /**
     * @desc 文件名
     * @param parameters
     *            URL参数集合
     * @return
     * @throws ServiceException
     */
    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "统计分析明细_" + sf.format(new Date()) + ".xls";
        return fileName;
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        return null;
    }

    /**
     * @desc 业务代码
     * @param parameters
     * @return
     * @throws ServiceException
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map result = new HashMap();
        //List<ReportAnnual> list = new ArrayList<>();
        String data = (String)parameters.get("id");
        String[] param = data.split(",");
        String year = param[0];
        String quarter = conversionStr(param[1]);
        String tranType = conversionStr(param[2]);
        String considerType = conversionStr(param[3]);
        String code = conversionStr(param[4]);
        //初始话交易对象
        List<TraInvestment> traInvestments = new ArrayList<>();
        List<TraFunds> traFunds = new ArrayList<>();
        List<TraBusiness> traBusinesses = new ArrayList<>();
        List<TraInterest> traInterests = new ArrayList<>();
        List<TraService> traServices = new ArrayList<>();
        List<TraOther> traOthers = new ArrayList<>();
        if (year == null || year.trim().equals("")) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR)+"";
        }

        String[] list = yearQuarter(year, quarter);
        //查询条件构建
        List<Filter> filters = new ArrayList<>();
        //如果参数数量为5个 则为单笔导出
        if (param.length >= 6){
            filters.add(new Filter("transNum",Operator.IN,param));
        }
        filters.add(new Filter("traSubName",code));
        if(!"%".equals(considerType)){filters.add(new Filter("considerType", Operator.EQ,considerType));}
        //中再(港再)资产只查询已归档的
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            filters.add(new Filter("status","6"));
        }
        //季度未选查询全年
        if("%".equals(quarter)){
            filters.add(new Filter("tranDate",Operator.LIKE , year));
        }else {
            filters.add(new Filter("tranDate",Operator.BETWEEN,list));
        }
        if (!"%".equals(tranType)){
            if("01".equals(tranType)){
                traInvestments = traInvestmentService.select(filters);
            }
            if("02".equals(tranType)){
                traFunds = traFundsService.select(filters);
            }
            if("03".equals(tranType)){
                traBusinesses = traBusinessService.select(filters);
            }
            if("04".equals(tranType)){
                traInterests = traInterestService.select(filters);
            }
            if("05".equals(tranType)){
                traServices = traServiceService.select(filters);
            }
            if("06".equals(tranType)){
                traOthers = traOtherService.select(filters);
            }
        }else {
            traInvestments = traInvestmentService.select(filters);
            traFunds = traFundsService.select(filters);
            traBusinesses = traBusinessService.select(filters);
            traInterests = traInterestService.select(filters);
            traServices = traServiceService.select(filters);
            traOthers = traOtherService.select(filters);
        }
        String yearQuarter = year + "_" + quarter;
        //把查询到得数据添加到vo对象中
        List<ReportAnnual> traInfo = getTraInfo(traInvestments, traFunds, traBusinesses, traInterests, traServices, traOthers, yearQuarter);
        result.put("detail", traInfo);
        return result;
    }


    public List<ReportAnnual> getTraInfo(List<TraInvestment> traInvestments,List<TraFunds> traFunds,List<TraBusiness> traBusinesses,
                                         List<TraInterest> traInterests,List<TraService> traServices ,List<TraOther> traOthers, String year){
        String[] yearQuarter = year.split("_");
        Map<String,Map<String, String>> dictCodeMap = obtainDictInfo("dictCode");
        List<ReportAnnual> reportAnnuals = new ArrayList<>();
        for (TraInvestment tra : traInvestments) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnual.setYear(yearQuarter[0]);
            reportAnnual.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            reportAnnual.setTraSubName(dictCodeMap.get("subjectMap").get(tra.getTraSubName()));
            reportAnnual.setRelType(dictCodeMap.get("relTypeMap").get(tra.getRelType()));
            reportAnnual.setMajorTrade(IsStatusDict.getLabel(tra.getMajorTrade()));
            reportAnnual.setConsiderType(dictCodeMap.get("considerTypeMap").get(tra.getConsiderType()));
            reportAnnual.setStatus(dictCodeMap.get("applystatusMap").get(tra.getStatus()));
            reportAnnual.setQuarter(getQuarter(yearQuarter[1]));
            //reportAnnual.setTranTime(reportAnnual.getTranDate().toString());
            reportAnnuals.add(reportAnnual);
        }
        for (TraFunds tra : traFunds) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnual.setYear(yearQuarter[0]);
            reportAnnual.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            reportAnnual.setTraSubName(dictCodeMap.get("subjectMap").get(tra.getTraSubName()));
            reportAnnual.setRelType(dictCodeMap.get("relTypeMap").get(tra.getRelType()));
            reportAnnual.setMajorTrade(IsStatusDict.getLabel(tra.getMajorTrade()));
            reportAnnual.setConsiderType(dictCodeMap.get("considerTypeMap").get(tra.getConsiderType()));
            reportAnnual.setStatus(dictCodeMap.get("applystatusMap").get(tra.getStatus()));
            reportAnnual.setQuarter(getQuarter(yearQuarter[1]));
            //reportAnnual.setTranTime(reportAnnual.getTranDate().toString());
            reportAnnuals.add(reportAnnual);
        }
        for (TraBusiness tra : traBusinesses) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnual.setYear(yearQuarter[0]);
            reportAnnual.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            reportAnnual.setTraSubName(dictCodeMap.get("subjectMap").get(tra.getTraSubName()));
            reportAnnual.setRelType(dictCodeMap.get("relTypeMap").get(tra.getRelType()));
            reportAnnual.setMajorTrade(IsStatusDict.getLabel(tra.getMajorTrade()));
            reportAnnual.setConsiderType(dictCodeMap.get("considerTypeMap").get(tra.getConsiderType()));
            reportAnnual.setStatus(dictCodeMap.get("applystatusMap").get(tra.getStatus()));
            reportAnnual.setQuarter(getQuarter(yearQuarter[1]));
            //reportAnnual.setTranTime(reportAnnual.getTranDate().toString());
            reportAnnuals.add(reportAnnual);
        }
        for (TraInterest tra : traInterests) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnual.setYear(yearQuarter[0]);
            reportAnnual.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            reportAnnual.setTraSubName(dictCodeMap.get("subjectMap").get(tra.getTraSubName()));
            reportAnnual.setRelType(dictCodeMap.get("relTypeMap").get(tra.getRelType()));
            reportAnnual.setMajorTrade(IsStatusDict.getLabel(tra.getMajorTrade()));
            reportAnnual.setConsiderType(dictCodeMap.get("considerTypeMap").get(tra.getConsiderType()));
            reportAnnual.setStatus(dictCodeMap.get("applystatusMap").get(tra.getStatus()));
            reportAnnual.setQuarter(getQuarter(yearQuarter[1]));
            //reportAnnual.setTranTime(reportAnnual.getTranDate().toString());
            reportAnnuals.add(reportAnnual);
        }
        for (TraService tra : traServices) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnual.setYear(yearQuarter[0]);
            reportAnnual.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            reportAnnual.setTraSubName(dictCodeMap.get("subjectMap").get(tra.getTraSubName()));
            reportAnnual.setRelType(dictCodeMap.get("relTypeMap").get(tra.getRelType()));
            reportAnnual.setMajorTrade(IsStatusDict.getLabel(tra.getMajorTrade()));
            reportAnnual.setConsiderType(dictCodeMap.get("considerTypeMap").get(tra.getConsiderType()));
            reportAnnual.setStatus(dictCodeMap.get("applystatusMap").get(tra.getStatus()));
            reportAnnual.setQuarter(getQuarter(yearQuarter[1]));
            //reportAnnual.setTranTime(reportAnnual.getTranDate().toString());
            reportAnnuals.add(reportAnnual);
        }
        for (TraOther tra : traOthers) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnual.setYear(yearQuarter[0]);
            reportAnnual.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            reportAnnual.setTraSubName(dictCodeMap.get("subjectMap").get(tra.getTraSubName()));
            reportAnnual.setRelType(dictCodeMap.get("relTypeMap").get(tra.getRelType()));
            reportAnnual.setMajorTrade(IsStatusDict.getLabel(tra.getMajorTrade()));
            reportAnnual.setConsiderType(dictCodeMap.get("considerTypeMap").get(tra.getConsiderType()));
            reportAnnual.setStatus(dictCodeMap.get("applystatusMap").get(tra.getStatus()));
            reportAnnual.setQuarter(getQuarter(yearQuarter[1]));
            //reportAnnual.setTranTime(reportAnnual.getTranDate().toString());
            reportAnnuals.add(reportAnnual);
        }

        return  reportAnnuals;
    }

    /**
     * @desc 季度转换
     * @param quarter
     * @return
     */
    private String getQuarter (String quarter) {
        switch (quarter) {
            case "%" :
                return "全季度";
            case "1" :
                return "第一季度";
            case "2" :
                return "第二季度";
            case "3" :
                return "第三季度";
            default:
                return null;
        }
    }

    //返回季度开始时间和结束时间
    public String[] yearQuarter(String year , String quarter){
        StringBuffer staTIme = new StringBuffer();
        StringBuffer endTIme = new StringBuffer();
        switch (quarter){
            case "1" :
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-03-31");
                break;
            case "2" :
                staTIme = staTIme.append(year).append("-04-01");
                endTIme = endTIme.append(year).append("-06-31");
                break;
            case "3" :
                staTIme = staTIme.append(year).append("-07-01");
                endTIme = endTIme.append(year).append("-09-31");
                break;
            case "4" :
                staTIme = staTIme.append(year).append("-10-01");
                endTIme = endTIme.append(year).append("-12-31");
                break;
        }
        String[] list =  {staTIme.toString(),endTIme.toString()};

        return list;
    }

    /**
     * @desc 获取字典信息
     * @param dictCode
     * @return
     */
    public Map<String,Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String,String>> dictCodeMap = new HashMap<>();
        //交易类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode,"INS-tranType"));
        Map tranTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tranTypeMap",tranTypeMap);
        //交易主体的名称
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode,"INS-subjectName"));
        Map subjectMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("subjectMap", subjectMap);
        //关联方类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode,"INS-relType"));
        Map relTypeMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relTypeMap", relTypeMap);
        //审议类型
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, "INS-considerType"));
        Map considerTypeMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("considerTypeMap", considerTypeMap);
        //交易状态
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode,"BUS-Applystatus"));
        Map applystatusMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("applystatusMap", applystatusMap);
        return dictCodeMap;
    }

    /**
     * @desc  构建查询的filter
     * @param property1
     * @param value1
     * @return
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
    }

    public String conversionStr (String str) {
        if (str.equals("ALL")) {
            return "%";
        }
        return str;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
