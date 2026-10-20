package com.yzj.jep.app.bus.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.enums.Submitted;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.code.Type;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = QuarterDetailExportCallBackImpl.BEANID)
public class QuarterDetailExportCallBackImpl implements XlsExportCallback {
    public static final String BEANID = "QuarterDetailExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "TradQuarterDetail.xls";

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

    @Resource
    private RelService relService;

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
        String fileName = "关联交易汇总表" + sf.format(new Date()) + ".xls";
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
        List<QuarterListVo> quarterInfo = null;
        String year ="";
        String quarter ="%";
        Map<String,Map<String, String>> dictCodeMap = obtainDictInfo("dictCode");
        int type = (int)parameters.get("type");
        if(Type.ONE.getLabel() == type){
            quarterInfo = JSONArray.parseArray((String) parameters.get("id"),QuarterListVo.class);
            year = (String) parameters.get("year");
            if (StringUtils.isEmpty(year)){
                year = String.valueOf(LocalDate.now().getYear());
            }
            quarter = (String) parameters.get("quarter");
        }else {
            String data = (String) parameters.get("id");
            String[] param = data.split(",");
            year = "%".equals(param[0]) ? null :param[0];
            quarter = param[1];
            String staTime = param[2];
            String endTime = param[3];
            //初始话交易对象
            List<QuarterListVo> traInvestments = new ArrayList<>();
            List<QuarterListVo> traFunds = new ArrayList<>();
            List<QuarterListVo> traBusinesses = new ArrayList<>();
            List<QuarterListVo> traInterests = new ArrayList<>();
            List<QuarterListVo> traServices = new ArrayList<>();
            List<QuarterListVo> traOthers = new ArrayList<>();
            if("%".equals(staTime) || "%".equals(endTime)){
                //默认年份
                if (year == null || year.trim().equals("")) {
                    Calendar cal = Calendar.getInstance();
                    year = cal.get(Calendar.YEAR)+"";
                }
                String[] list = yearQuarter(year, quarter);
                staTime = list[0];
                endTime = list[1];
            }
            Map<String,Object> map = new HashMap<>(16);
            map.put("staTime",staTime);
            map.put("endTime",endTime);
            traInvestments = traInvestmentService.getInvestment(map);
            traFunds = traFundsService.getFundsService(map);
            traBusinesses = traBusinessService.getBusiness(map);
            traInterests = traInterestService.getInterest(map);
            traServices = traServiceService.getService(map);
            traOthers = traOtherService.getOther(map);
            //把查询到得数据添加到vo对象中
            quarterInfo = getTraInfo(traInvestments,traFunds,traBusinesses,traInterests,traServices,traOthers);
        }
        for (QuarterListVo vo:quarterInfo){
            StringBuffer sb=new StringBuffer();
            List<Rel> rel = relService.queryRelForOrgAndPsn(vo.getRelId());
            for (Rel r:rel){
                sb.append(r.getRelStatement());
                sb.append(",");
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.lastIndexOf(","));
                vo.setRelStatement(sb.toString());
            }
            vo.setQuarter(quarterTime(vo.getTranDate()));
        }
        //计算 总金额
        BigDecimal totalMoney = quarterInfo.stream().map(QuarterListVo::getContractAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer[] arr = {1};
        quarterInfo = quarterInfo.stream().peek(e->e.setNo(arr[0] ++)).collect(Collectors.toList());
        result.put("detail", quarterInfo);
        result.put("quartertTitle", getQuarter(quarter));
        result.put("traSubName", Submitted.ORGNUMBER.getName());
        result.put("year", year);
        result.put("totalAmount", totalMoney);
        return result;
    }
    public List<QuarterListVo> getTraInfo(List<QuarterListVo> traInvestments,List<QuarterListVo> traFunds,List<QuarterListVo> traBusinesses,
                                         List<QuarterListVo> traInterests,List<QuarterListVo> traServices ,List<QuarterListVo> traOthers){
        List<QuarterListVo> reportAnnuals = new ArrayList<>();
        Map<String,Map<String, String>> dictCodeMap = obtainDictInfo("dictCode");

        for (QuarterListVo tra : traInvestments) {
            tra.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            tra.setTranOverView(dictCodeMap.get("businessTypeMap").get(tra.getTranOverView()));
            reportAnnuals.add(tra);
        }
        for (QuarterListVo tra : traFunds) {
            tra.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            tra.setTranOverView(dictCodeMap.get("businessTypeMap").get(tra.getTranOverView()));
            reportAnnuals.add(tra);
        }
        for (QuarterListVo tra : traBusinesses) {
            tra.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            tra.setTranOverView(dictCodeMap.get("businessTypeMap").get(tra.getTranOverView()));
            reportAnnuals.add(tra);
        }
        for (QuarterListVo tra : traInterests) {
            tra.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            tra.setTranOverView(dictCodeMap.get("businessTypeMap").get(tra.getTranOverView()));
            reportAnnuals.add(tra);
        }
        for (QuarterListVo tra : traServices) {
            tra.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            tra.setTranOverView(dictCodeMap.get("businessTypeMap").get(tra.getTranOverView()));
            reportAnnuals.add(tra);
        }
        for (QuarterListVo tra : traOthers) {
            tra.setTranType(dictCodeMap.get("tranTypeMap").get(tra.getTranType()));
            tra.setTranOverView(dictCodeMap.get("businessTypeMap").get(tra.getTranOverView()));
            reportAnnuals.add(tra);
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
                return "";
            case "1" :
                return "第一季度";
            case "2" :
                return "第二季度";
            case "3" :
                return "第三季度";
            case "4" :
                return "第四季度";
            default:
                return null;
        }
    }

    //返回季度开始时间和结束时间
    public String[] yearQuarter(String year , String quarter){
        StringBuffer staTIme = new StringBuffer();
        StringBuffer endTIme = new StringBuffer();
        if ("5".equals(quarter)) {
            staTIme = staTIme.append(year).append("-01-01");
            endTIme = endTIme.append(year).append("-12-31");
            String[] list =  {staTIme.toString(),endTIme.toString()};
            return list;
        }
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
    public String quarterTime(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(time);
        Date datePar = null;
        String quarter= "";
        try {
            datePar = sdf.parse(s);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(datePar);
            int mouth = datePar.getMonth()+1;
            if(mouth>=1 && mouth<=3){
                quarter = "第一季度";
            }else if(mouth>=4 && mouth<=6){
                quarter =  "第二季度";
            }else if(mouth>=7 && mouth<=9){
                quarter = "第三季度";
            }else if(mouth>=10 && mouth<=12){
                quarter =  "第四季度";
            }
        } catch (ParseException e) {
        }
        return quarter;
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

        //交易状态
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode,"SUB-BusinessType"));
        Map businessTypeMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("businessTypeMap", applystatusMap);
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
