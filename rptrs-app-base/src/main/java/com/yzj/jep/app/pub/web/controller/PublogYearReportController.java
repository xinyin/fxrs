package com.yzj.jep.app.pub.web.controller;


import com.alibaba.fastjson.JSON;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.model.TraEnclosure;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.pub.service.PublogQuarterService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.pub.service.PublogYearReportService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.WorderToNewWordUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/pub/publogYearReport")
public class PublogYearReportController extends CrudController<Publog> {
    private static Logger logger = LoggerFactory.getLogger(PublogYearReportController.class);
    @Autowired
    private PublogService publogService;
    @Autowired
    private PublogQuarterService publogQuarterService;
    @Autowired
    private PublogYearReportService publogYearReportService;
    @Autowired
    private PubFileTemplateService pubFileTemplateService;
    @Value("/home/rptrs/upload")
    private String disclosePath;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private TraOtherService traOtherService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private UnSortOutService unSortOutService;
    @Autowired
    private RelFundsService relFundsService;
    @Autowired
    private RootOrgInfoService rootOrgInfoService;
    @Autowired
    private FundsInvestmentService fundsInvestmentService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private RelPsnService relPsnService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "年度定期报告管理", description = "查询年度定期报告列表")
    public String list(Model model) {
        return "pub/publogYearReportList";
    }


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        orders.add(Order.desc("maxPercentInNetAssets"));
        List list=new ArrayList();
        list.add("5");
        list.add("6");
        filters.add(Filter.in("pubType", list));
        publogService.query(page, filters, orders);

    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String adds(Model model) {
        return "pub/publogYearReportAdd";
    }

    /**
     * 为新增定期披露文件页面做数据准备
     */

    @RequestMapping(value = "/allQuarter" , method = {RequestMethod.GET})
    @ResponseBody
    public void allQuarter( String quarter,  String years, Page<PublogRegularVo> page, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(years)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            years = sdf.format(date);
        }
        Map<String, String> map = publogQuarterService.getTime(quarter, years);

        List<PublogRegularVo> publogRegularVos = publogYearReportService.getYearReportList(map);


        page.setTotal(publogRegularVos.size());
        int pages = (publogRegularVos.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PublogRegularVo> publogRegularVoList = new ArrayList<PublogRegularVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < publogRegularVos.size()) {
                publogRegularVoList.add(publogRegularVos.get(i));
            }
        }

        page.setResult(publogRegularVoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        try {
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/toWord")
    @ResponseBody
    public Map<String, String> toword(@RequestParam Map<String,Object> data,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, String> res = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        PubFileTemplate pubFileTemplate = pubFileTemplateService.selectById(Integer.parseInt(data.get("filePid").toString()));
//        //模板文件地址
//        String inputUrl = pubFileTemplate.getTempFileUrl();
        //模板文件地址测试本地
        String inputUrl = "D:\\Temp\\中邮定期季度报告测试模板234.docx";
        //拿文件后缀 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String suffix = inputUrl.substring(inputUrl.lastIndexOf("."));
//        //新生产的模板文件
//        String outputUrl = disclosePath + '\\' + uuid + '\\' + File.separator + data.get("fileName").toString() + suffix;
        //新生产的模板文件测试本地
        String outputUrl = "D:\\westForDocFile" + '\\' + uuid + '\\' + File.separator + data.get("fileName").toString() + suffix;
        File dest = new File(outputUrl);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        //文件存放路径
        String FilerUrl = disclosePath + '\\' + uuid;
        //文件名
        String fileNameAll = File.separator + data.get("fileName").toString() + suffix;
        Map<String, String> map = new HashMap<>();
        Map<String, String> map1 = publogQuarterService.getTime(data.get("quarter").toString(), data.get("year").toString());

        map.put("year",data.get("year").toString());
        map.put("quarter",data.get("quarter").toString());
        if("5".equals(pubFileTemplate.getTempFileTepe())){
        //季度报告
            //投资入股类业务类型统计
            List<PublogRegularVo> investmentPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("01",map1);
            if(investmentPublogRegularVos.size()>0){
                //每个投资入股类的合计金额
                BigDecimal investmentSum=new BigDecimal(0);
                //每个关联方的每个业务类型合计多少钱
                StringBuffer investmentStr=new StringBuffer();
                for(int i=0;i<investmentPublogRegularVos.size();i++){
                    //投资入股类业务类型字典
                    investmentPublogRegularVos.get(i).setBusType(selectoneByValue("INS-InvestmentType", investmentPublogRegularVos.get(i).getBusType()));
                    investmentSum.add(investmentPublogRegularVos.get(i).getContractAmount());
                    investmentStr.append(investmentPublogRegularVos.get(i).getRelName()+"的"+investmentPublogRegularVos.get(i).getBusType()+"合计为："+
                            investmentPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                }
                //投资总额
                map.put("investment","投资入股类关联交易为"+investmentSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                map.put("investmentBusType",investmentStr.toString());
            }else{
            map.put("investment","投资入股类关联交易未发生交易");
            map.put("investmentBusType","");
            }
            //资金运用类
            List<PublogRegularVo> fundsPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("02",map1);
            if(fundsPublogRegularVos.size()>0){
                //每个资金运用类的合计金额
                BigDecimal fundsSum=new BigDecimal(0);
                //每个关联方的每个业务类型合计多少钱
                StringBuffer fundsStr=new StringBuffer();
                for(int i=0;i<fundsPublogRegularVos.size();i++){
                    //资金运用类业务类型字典
                    fundsPublogRegularVos.get(i).setBusType(selectoneByValue("INS-FundsType", fundsPublogRegularVos.get(i).getBusType()));
                    fundsSum.add(fundsPublogRegularVos.get(i).getContractAmount());
                    fundsStr.append(fundsPublogRegularVos.get(i).getRelName()+"的"+fundsPublogRegularVos.get(i).getBusType()+"合计为："+
                            fundsPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                }
                //投资总额
                map.put("funds","资金运用类关联交易为"+fundsSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                map.put("fundsBusType",fundsStr.toString());
            }else{
                map.put("funds","资金运用类关联交易未发生交易");
                map.put("fundsBusType","");
            }
            //保险业务类
            List<PublogRegularVo> businessPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("03",map1);
            if(businessPublogRegularVos.size()>0){
                //每个保险业务类的合计金额
                BigDecimal businessSum=new BigDecimal(0);
                //每个关联方的每个业务类型合计多少钱
                StringBuffer businessStr=new StringBuffer();
                for(int i=0;i<businessPublogRegularVos.size();i++){
                    //保险业务类业务类型字典
                    businessPublogRegularVos.get(i).setBusType(selectoneByValue("INT-InsBusinessType", businessPublogRegularVos.get(i).getBusType()));
                    businessSum.add(businessPublogRegularVos.get(i).getContractAmount());
                    businessStr.append(businessPublogRegularVos.get(i).getRelName()+"的"+businessPublogRegularVos.get(i).getBusType()+"合计为："+
                            businessPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                }
                //投资总额
                map.put("business","保险业务类关联交易为"+businessSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                map.put("businessBusType",businessStr.toString());
            }else{
                map.put("business","保险业务类关联交易未发生交易");
                map.put("businessBusType","");
            }
            //利益转移类
            List<PublogRegularVo> interestPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("04",map1);
            if(interestPublogRegularVos.size()>0){
                //每个利益转移类的合计金额
                BigDecimal interestSum=new BigDecimal(0);
                //每个关联方的每个业务类型合计多少钱
                StringBuffer interestStr=new StringBuffer();
                for(int i=0;i<businessPublogRegularVos.size();i++){
                    //利益转移类业务类型字典
                    interestPublogRegularVos.get(i).setBusType(selectoneByValue("INT-BusinessType", interestPublogRegularVos.get(i).getBusType()));
                    interestSum.add(interestPublogRegularVos.get(i).getContractAmount());
                    interestStr.append(interestPublogRegularVos.get(i).getRelName()+"的"+interestPublogRegularVos.get(i).getBusType()+"合计为："+
                            interestPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                }
                //投资总额
                map.put("interest","资金转移类关联交易为"+interestSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                map.put("interestBusType",interestStr.toString());
            }else{
                map.put("interest","资金转移类关联交易未发生交易");
                map.put("interestBusType","");
            }
            //提供货物与服务类
            List<PublogRegularVo> servicePublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("05",map1);
            if(servicePublogRegularVos.size()>0){
                //每个利益转移类的合计金额
                BigDecimal serviceSum=new BigDecimal(0);
                //每个关联方的每个业务类型合计多少钱
                StringBuffer serviceStr=new StringBuffer();
                for(int i=0;i<servicePublogRegularVos.size();i++){
                    //提供货物与服务类业务类型字典
                    servicePublogRegularVos.get(i).setBusType(selectoneByValue("INS-ServiceType", servicePublogRegularVos.get(i).getBusType()));
                    serviceSum.add(servicePublogRegularVos.get(i).getContractAmount());
                    serviceStr.append(servicePublogRegularVos.get(i).getRelName()+"的"+servicePublogRegularVos.get(i).getBusType()+"合计为："+
                            servicePublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                }
                //投资总额
                map.put("service","提供货物与服务类关联交易为"+serviceSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                map.put("serviceBusType",serviceStr.toString());
            }else{
                map.put("service","提供货物与服务类关联交易未发生交易");
                map.put("serviceBusType","");
            }
            //其他类
            List<PublogRegularVo> otherPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("06",map1);
            if(otherPublogRegularVos.size()>0){
                //每个利益转移类的合计金额
                BigDecimal otherSum=new BigDecimal(0);
                //每个关联方的每个业务类型合计多少钱
                StringBuffer otherStr=new StringBuffer();
                for(int i=0;i<otherPublogRegularVos.size();i++){
                    //提供货物与服务类业务类型字典
                    otherPublogRegularVos.get(i).setBusType(selectoneByValue("INS-OtherType", otherPublogRegularVos.get(i).getBusType()));
                    otherSum.add(otherPublogRegularVos.get(i).getContractAmount());
                    otherStr.append(otherPublogRegularVos.get(i).getRelName()+"的"+otherPublogRegularVos.get(i).getBusType()+"合计为："+
                            otherPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元 ");
                }
                //投资总额
                map.put("other","其他类关联交易未发生交易为"+otherSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString());
                map.put("otherBusType",otherStr.toString());
            }else{
                map.put("other","其他类关联交易未发生交易");
                map.put("otherBusType","");
            }

            //创建时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                //1、日期转字符串
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                String createTime = sdf.format(date);
                map.put("createTime",createTime);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("createTime","日期异常");
            }
                map.put("endTime",map1.get("endTime"));
            //同一关联方在本季度投资余额总量
            List<Filter> filters = new Filter.Builder().build();
            List<Order> orders=new Order.Builder().desc("investmentBalanceTime").build();
            filters.add(new Filter("investmentBalanceTime", Operator.GE, map1.get("startTime")));
            filters.add(new Filter("investmentBalanceTime", Operator.LE, map1.get("endTime")));
            List<RelFunds> relFunds=relFundsService.select(filters,orders);

            //上年度末总资产净资产
            List<Filter> filterss = new Filter.Builder().build();
            String yy=data.get("year").toString();
            Integer yyy=Integer.parseInt(yy)-1;
            filterss.add(new Filter("years", Operator.EQ,yyy.toString()));
            filterss.add(new Filter("quarter", Operator.EQ, "4"));
            filterss.add(new Filter("approvalStatus",Operator.EQ,"6"));
            List<RootOrgInfo> rootOrgInfos=rootOrgInfoService.select(filterss);
            if(relFunds.size()>0){
                //所有关联方投资余额
                BigDecimal subFundsInvestmentBalance=new BigDecimal(0);
                for(int i=0;i<relFunds.size();i++){
                    subFundsInvestmentBalance=subFundsInvestmentBalance.add(relFunds.get(i).getFundsInvestmentBalance());
                }
                map.put("subFundsInvestmentBalance",subFundsInvestmentBalance.toString());

                //单一关联方投资余额
                BigDecimal FundsInvestmentBalance=relFunds.get(0).getFundsInvestmentBalance();
                map.put("fundsInvestmentBalance",FundsInvestmentBalance.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()+"亿元");
                if(rootOrgInfos.size()>0){
                    //总资产
                    BigDecimal totalCapital=rootOrgInfos.get(0).getTotalCapital();
                    //净资产
                    BigDecimal netCaptial= rootOrgInfos.get(0).getNetCaptial();
//                    //单一投资余额占总资产百分比
//                    BigDecimal totalCapital1= FundsInvestmentBalance.divide(totalCapital,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    //单一投资余额占净资产百分比
//                    BigDecimal netCaptial1=FundsInvestmentBalance.divide(netCaptial,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    //全部投资余额占总资产百分比
                    BigDecimal sumTotalCapital1= subFundsInvestmentBalance.divide(totalCapital,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    //全部投资余额占净资产百分比
                    BigDecimal sumNetCaptial1=subFundsInvestmentBalance.divide(netCaptial,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
//                    map.put("totalCapital",totalCapital1.toString()+"%");
//                    map.put("netCaptial",netCaptial1.toString()+"%");
                    map.put("sumTotalCapital",sumTotalCapital1.toString()+"%");
                    map.put("sumNetCaptial",sumNetCaptial1.toString()+"%");
                    // 取上年度末总资产*30%，上年度末净资产最小的 .compareTo=-1
                    //所有关联方的投资余额<=min（上年度末总资产*30%，上年度末净资产）
                    if(totalCapital.multiply(new BigDecimal(0.03)).compareTo(netCaptial)>=0){
                        //投资余额<=上年度末净资产
                        if(subFundsInvestmentBalance.compareTo(netCaptial)<=0){
                            map.put("subProportion","未超过监管限制");
                            map.put("subUnProportion","中邮保险无不符合银保监会对于资金运用关联交易相关比例的要求，无需提交整改方案。");
                        }else{
                            map.put("subProportion","超过监管限制");
                            map.put("subUnProportion","【请补充完善】");
                        }
                    }else{
                        //投资余额<=总资产*30%
                        if(subFundsInvestmentBalance.compareTo(totalCapital.multiply(new BigDecimal(0.3)))<=0){
                            map.put("subProportion","未超过监管限制");
                            map.put("subUnProportion","中邮保险无不符合银保监会对于资金运用关联交易相关比例的要求，无需提交整改方案。");
                        }else {
                            map.put("subProportion","超过监管限制");
                            map.put("subUnProportion","【请补充完善】");
                        }
                    }


                    //存在几个关联方
                    List<PublogRegularVo> relNamePublogRegularVos = publogYearReportService.getGroupByRelName(map1);
                    StringBuffer ss=new StringBuffer();
                    for(int i=0;i<relNamePublogRegularVos.size();i++){
                        //单一投资余额占总资产百分比
                         FundsInvestmentBalance.divide(totalCapital,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                        //单个关联方的投资余额<=上年度末总资*15%
                        if(relNamePublogRegularVos.get(i).getContractAmount().compareTo(totalCapital.multiply(new BigDecimal(0.15)))<=0){
                            map.put("proportion","未超过监管限制");
                            map.put("unProportion","中邮保险无不符合银保监会对于资金运用关联交易相关比例的要求，无需提交整改方案。");
                        }else{
                            map.put("proportion","超过监管限制");
                            map.put("unProportion","【请补充完善】");
                        }
                        ss.append("中邮保险对单一关联方"+relNamePublogRegularVos.get(i).getRelName()+"全部投资余额为："+relNamePublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString()
                                    +"亿元,占中邮保险上年末总资产的"+relNamePublogRegularVos.get(i).getContractAmount().divide(totalCapital,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))+"%"
                                 );
                    }
                    map.put("relFundsMsg",ss.toString());

                }else{
                    map.put("totalCapital","无");
                    map.put("netCaptial","无");
                    map.put("proportion","");
                    map.put("unProportion","");
                    map.put("subFundsInvestmentBalance","");
                    map.put("subProportion","");
                    map.put("subUnProportion","");
                }
            }else{
                map.put("fundsInvestmentBalance","无");
                map.put("unProportion","");
                map.put("proportion","");
                map.put("totalCapital","无");
                map.put("netCaptial","无");
                map.put("subFundsInvestmentBalance","");
                map.put("subProportion","");
                map.put("subUnProportion","");
            }
            //资金运用类统计
            List<Filter> filtersss = new Filter.Builder().build();
            List<Order> ordersss=new Order.Builder().desc("investmentBalanceTime").build();
            filtersss.add(new Filter("investmentBalanceTime", Operator.GE, map1.get("startTime")));
            filtersss.add(new Filter("investmentBalanceTime", Operator.LE, map1.get("endTime")));
            filtersss.add(new Filter("status", Operator.EQ, "1"));
            List<FundsInvestment> fundsInvestments=fundsInvestmentService.select(filtersss,ordersss);
            if(fundsInvestments.size()>0){
                map.put("otherBalances","不动资产投资余额为" +fundsInvestments.get(0).getRealEstateBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,未上市权益类资产投资余额为"+fundsInvestments.get(0).getUnlistedBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,境外投资余额为"+fundsInvestments.get(0).getForeignBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,固定收益类资产投资余额为"+fundsInvestments.get(0).getFixedIncomeBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,上市权益类资产投资余额为"+fundsInvestments.get(0).getListedBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,保险资产管理产品投资余额为"+fundsInvestments.get(0).getInsuranceManagementBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,商业银行理财产品投资余额为"+fundsInvestments.get(0).getBankFinancialBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,银行业金融机构信贷资产支持证券投资余额为"+fundsInvestments.get(0).getBankNegotiableSecuritiesBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,信托公司集合资金信托计划投资余额为"+fundsInvestments.get(0).getTrustSchemeBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,证券公司专项资产管理计划投资余额为"+fundsInvestments.get(0).getNegotiableSecuritiesSpecialPlanBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,保险资管公司发行资产支持计划投资余额为"+fundsInvestments.get(0).getInsuranceFundsSupportPlanBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+
                        "亿元,其他投资余额为"+fundsInvestments.get(0).getOtherInvestmentBalance().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元");
            }else{
                map.put("otherBalances","中邮保险未向关联方投资未上市权益类资产、不动产类资产、其他金融资产和境外投资，不涉及监管比例限制");
            }

        }else{
            Map<String,String> map2=new HashMap<>();
            map2.put("startTime",data.get("year")+"-01-01");
            map2.put("endTime",data.get("year")+"-12-31");
            //年度报告
            List<PublogRegularVo> publogRegularVos =publogYearReportService.getOrderByPracTradeAmount(data.get("year").toString());
            map.put("yearReportSize",String.valueOf(publogRegularVos.size()));
            //获取交易类型
            List<PublogRegularVo> publogRegularVos1=publogYearReportService.getTranType(data.get("year").toString());
            int countMajorTrade=0;
            StringBuffer tranType=new StringBuffer();
            StringBuffer majorTradeMsg=new StringBuffer();
            for(int i=0;i<publogRegularVos1.size();i++){

                publogRegularVos1.get(i).setTranType(selectoneByValue("INS-tranType",  publogRegularVos1.get(i).getTranType()));
                tranType.append( publogRegularVos1.get(i).getTranType()+"、");
                if("1".equals(publogRegularVos1.get(i).getMajorTrade())){
                    countMajorTrade++;
                }
            }
            for(int i=0;i<publogRegularVos.size();i++){
                if("1".equals(publogRegularVos.get(i).getMajorTrade())){
                    //根据交易类型和pid查询对应的重大交易数据相关信息
                    switch (publogRegularVos.get(i).getTranType()){
                        case "01"://投资入股类
                            TraInvestment inv= traInvestmentService.get(publogRegularVos.get(i).getPid());
                            majorTradeMsg.append(i+".与"+inv.getRelName()+inv.getTranDateString()+inv.getTranName()+
                                    "预估金额"+inv.getRelTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "年度实际发生"+inv.getTotolTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "协议期限为"+inv.getBusStartTimeString()+"-"+inv.getBusEndTimeString()+
                                    "属于投资入股类交易类型。其余内容需要手工维护。");
                            break;
                        case "02"://资金运用类
                            TraFunds fun= traFundsService.get(publogRegularVos.get(i).getPid());
                            majorTradeMsg.append(i+".与"+fun.getRelName()+fun.getTranDateString()+fun.getTranName()+
                                    "预估金额"+fun.getRelTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "年度实际发生"+fun.getTotolTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "协议期限为"+fun.getBusStartTimeString()+"-"+fun.getBusEndTimeString()+
                                    "属于资金运用类交易类型。其余内容需要手工维护。");
                            break;
                        case "03"://保险业务类
                            TraBusiness bus= traBusinessService.get(publogRegularVos.get(i).getPid());
                            majorTradeMsg.append(i+".与"+bus.getRelName()+bus.getTranDateString()+bus.getTranName()+
                                    "预估金额"+bus.getRelTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "年度实际发生"+bus.getTotolTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "协议期限为"+bus.getBusStartTimeString()+"-"+bus.getBusEndTimeString()+
                                    "属于保险业务类交易类型。其余内容需要手工维护。");
                            break;
                        case "04"://利益转移类
                            TraInterest inte= traInterestService.get(publogRegularVos.get(i).getPid());
                            majorTradeMsg.append(i+".与"+inte.getRelName()+inte.getTranDateString()+inte.getTranName()+
                                    "预估金额"+inte.getRelTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "年度实际发生"+inte.getTotolTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "协议期限为"+inte.getBusStartTimeString()+"-"+inte.getBusEndTimeString()+
                                    "属于利益转移类交易类型。其余内容需要手工维护。");
                            break;
                        case "05"://提供货物与服务类
                            TraService ser= traServiceService.get(publogRegularVos.get(i).getPid());
                            majorTradeMsg.append(i+".与"+ser.getRelName()+ser.getTranDateString()+ser.getTranName()+
                                    "预估金额"+ser.getRelTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "年度实际发生"+ser.getTotolTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "协议期限为"+ser.getBusStartTimeString()+"-"+ser.getBusEndTimeString()+
                                    "属于提供货物与服务类交易类型。其余内容需要手工维护。");
                            break;
                        case "06"://其他类
                            TraOther oth= traOtherService.get(publogRegularVos.get(i).getPid());
                            majorTradeMsg.append(i+".与"+oth.getRelName()+oth.getTranDateString()+oth.getTranName()+
                                    "预估金额"+oth.getRelTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "年度实际发生"+oth.getTotolTradeAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"+
                                    "协议期限为"+oth.getBusStartTimeString()+"-"+oth.getBusEndTimeString()+
                                    "属于其他类交易类型。其余内容需要手工维护。");
                            break;
                        default:majorTradeMsg.append("数据异常");
                    }
                }
            }
            StringBuffer relName=new StringBuffer();
            BigDecimal sumPracTradeAmount=new BigDecimal(0);
            for(int i=0;i<publogRegularVos.size();i++){
                sumPracTradeAmount=sumPracTradeAmount.add(publogRegularVos.get(i).getContractAmount());
            }
            int pz=3;
            if(publogRegularVos.size()<3){
                pz=publogRegularVos.size();
            }
            for(int i=0;i<pz;i++){
                relName.append(publogRegularVos.get(i).getRelName()+"、");
            }
            sumPracTradeAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP);
            map.put("relSituation",relName+"等关联方开展关联交易，关联交易金额合计约"+sumPracTradeAmount+"亿元");
            map.put("tranType",tranType.toString());
            map.put("countMajorTrade",String.valueOf(countMajorTrade));

            //投资入股类所需要的信息
            List<PublogRegularVo> investmentPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("01",map2);
            if(investmentPublogRegularVos.size()>0){
                BigDecimal investmentSumAmount= new BigDecimal(0);
                for(PublogRegularVo p : investmentPublogRegularVos ){
                    investmentSumAmount=investmentSumAmount.add(p.getContractAmount());
                    p.setBusType(selectoneByValue("INS-InvestmentType", p.getBusType()));
                }
                int investmentpz1=3;
                if(investmentPublogRegularVos.size()<3){
                    investmentpz1=investmentPublogRegularVos.size();
                }
                StringBuffer investmentSbf=new StringBuffer();
                for(int i=0;i<investmentpz1;i++){
                    investmentSbf.append("其中"+investmentPublogRegularVos.get(i).getRelName()+"的"+
                                    investmentPublogRegularVos.get(i).getBusType()+"关联交易金额为"+
                                    investmentPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"
                            );
                }
                map.put("investment","投资入股类关联交易金额合计约"+investmentSumAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元");
                map.put("touziMsg",investmentSbf.toString());
            }else{
                map.put("investment","未发生投资入股类关联交易");
                map.put("touziMsg","");
            }
            //保险业务类所需要的信息
            List<PublogRegularVo> businessPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("03",map2);
            if(businessPublogRegularVos.size()>0){
                BigDecimal businessSumAmount= new BigDecimal(0);
                for(PublogRegularVo p : businessPublogRegularVos ){
                    businessSumAmount=businessSumAmount.add(p.getContractAmount());
                    p.setBusType(selectoneByValue("INT-InsBusinessType", p.getBusType()));
                }
                int businessPz1=3;
                if(businessPublogRegularVos.size()<3){
                    businessPz1=businessPublogRegularVos.size();
                }
                StringBuffer businessSbf=new StringBuffer();
                for(int i=0;i<businessPz1;i++){
                    businessSbf.append("其中"+businessPublogRegularVos.get(i).getRelName()+"的"+
                            businessPublogRegularVos.get(i).getBusType()+"关联交易金额为"+
                            businessPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"
                    );
                }
                map.put("business","保险业务类关联交易金额合计约"+businessSumAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元");
                map.put("businessMsg",businessSbf.toString());
            }else{
                map.put("business","未发生保险业务类关联交易");
                map.put("businessMsg","");
            }

            //资金运用类所需要的信息
            List<PublogRegularVo> fundsPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("02",map2);
            if(fundsPublogRegularVos.size()>0){
                BigDecimal fundsSumAmount= new BigDecimal(0);
                StringBuffer fundsSbf=new StringBuffer();
                for(PublogRegularVo p : fundsPublogRegularVos ) {
                    fundsSumAmount = fundsSumAmount.add(p.getContractAmount());
                    p.setBusType(selectoneByValue("INS-FundsType", p.getBusType()));
                    fundsSbf.append("其中" + p.getRelName() + "的" +
                            p.getBusType() + "关联交易金额为" +
                            p.getContractAmount().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP) + "亿元"
                    );
                }
                map.put("funds","资金运用类关联交易金额合计约"+fundsSumAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元");
                map.put("fundsMsg",fundsSbf.toString());
            }else{
                map.put("funds","未发生投资入股类关联交易");
                map.put("fundsMsg","");
            }

            //利益转移类所需要的信息
            List<PublogRegularVo> interestPublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("04",map2);
            if(interestPublogRegularVos.size()>0){
                BigDecimal interestSumAmount= new BigDecimal(0);
                for(PublogRegularVo p : interestPublogRegularVos ){
                    interestSumAmount=interestSumAmount.add(p.getContractAmount());
                    p.setBusType(selectoneByValue("INT-BusinessType", p.getBusType()));
                }
                int interestPz1=3;
                if(interestPublogRegularVos.size()<3){
                    interestPz1=interestPublogRegularVos.size();
                }
                StringBuffer interestSbf=new StringBuffer();
                for(int i=0;i<interestPz1;i++){
                    interestSbf.append("其中"+interestPublogRegularVos.get(i).getRelName()+"的"+
                            interestPublogRegularVos.get(i).getBusType()+"关联交易金额为"+
                            interestPublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"
                    );
                }
                map.put("liyi","利益转移类关联交易金额合计约"+interestSumAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元");
                map.put("liyiMsg",interestSbf.toString());
            }else{
                map.put("liyi","未发生利益转移类关联交易");
                map.put("liyiMsg","");
            }

            //提供货物或服务类所需要的信息
            List<PublogRegularVo> servicePublogRegularVos = publogYearReportService.getGroupByTranTypeAndRelNameAndBusType("05",map2);
            if(servicePublogRegularVos.size()>0){
                BigDecimal serviceSumAmount= new BigDecimal(0);
                for(PublogRegularVo p : servicePublogRegularVos ){
                    serviceSumAmount=serviceSumAmount.add(p.getContractAmount());
                    p.setBusType(selectoneByValue("INS-ServiceType", p.getBusType()));
                }
                int servicePz1=3;
                if(servicePublogRegularVos.size()<3){
                    servicePz1=servicePublogRegularVos.size();
                }
                StringBuffer interestSbf=new StringBuffer();
                for(int i=0;i<servicePz1;i++){
                    interestSbf.append("其中"+servicePublogRegularVos.get(i).getRelName()+"的"+
                            servicePublogRegularVos.get(i).getBusType()+"关联交易金额为"+
                            servicePublogRegularVos.get(i).getContractAmount().divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元"
                    );
                }
                map.put("service","提供货物或服务类关联交易金额合计约"+serviceSumAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"亿元");
                map.put("serviceMsg",interestSbf.toString());
            }else{
                map.put("service","未发生提供货物或服务类关联交易");
                map.put("serviceMsg","");
            }
            //关联方有效个数
            long org=relOrgService.count(new Filter.Builder().eq("effectRelation","1" ).build());
            long psn=relPsnService.count(new Filter.Builder().eq("effectRelation","1" ).build());
            long orgAndPsn=org+psn;
            map.put("org",String.valueOf(org));
            map.put("psn",String.valueOf(psn));
            map.put("orgAndPsn",String.valueOf(orgAndPsn));
        }
       // boolean changWord = WorderToNewWordUtils.changWord(inputUrl, outputUrl, map, new ArrayList<>());
        boolean changWord = false;
        if (changWord) {
            //查询
            Publog publog = new Publog();
            //根据relId和交易类型
            publog.setPubYear(data.get("year").toString());
            publog.setPubQuarter(data.get("quarter").toString());
            publog.setPubType(pubFileTemplate.getTempFileTepe());

            List<Publog> publogs = publogService.select(publog);
            if (CollectionUtils.isNotEmpty(publogs) && publogs.size() == 1) {
                //更新
                publog = publogs.get(0);
                publog.setPubFileName(data.get("fileName").toString() + suffix);
                publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());
                publog.setMaxPercentInNetAssets(new Date());
                publog.setDownAmt(0);
                publog.setDownStatus("1");
                publog.setFileUrl(FilerUrl);
                publogService.updateSelective(publog);
            } else if (CollectionUtils.isEmpty(publogs) && publogs.size() == 0) {
                //新增
                publog.setPubFileName(data.get("fileName").toString() + suffix);
                publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());
                publog.setMaxPercentInNetAssets(new Date());
                publog.setDownAmt(0);
                publog.setDownStatus("1");
                publog.setFileUrl(FilerUrl);
                publog.setPubYear(data.get("year").toString());
                publog.setPubQuarter(data.get("quarter").toString());
                publog.setStatus("01");
                publogService.insertSelective(publog);
            }

            res.put("code", "200");
//            res.put("fileUrl", FilerUrl);
//            res.put("fileName", data.get("fileName").toString());
            return res;
        }
        res.put("400", "错误！");
        return res;
    }

    public String selectoneByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            DictItem dictItem = new DictItem();
            dictItem.setDictCode(value);
            dictItem.setItemValue(simpleName);
            List<DictItem> dictItemList = dictItemService.select(dictItem);
            dictItem = dictItemList.get(0);
            return dictItem.getItemLabel();
        } catch (Exception e) {
        }
        return null;
    }



    /*
     * 文件上传
     * */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("pid") Integer pid, @RequestParam("fileDate") String fileDate, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraEnclosure enclosure = null;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            List<MultipartFile> multipartFileList = multipartRequest.getFiles("file");
            Publog plog = publogService.get(pid);
            String path = plog.getFileUrl();
            String quarterFlag=plog.getPubQuarter();
            for (MultipartFile file : multipartFileList) {
                if (!file.isEmpty()) {
                    String fileNameAll = file.getOriginalFilename(); //文件全名
                    String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                    String[] strArray = fileNameAll.split("\\.");
                    int suffixIndex = strArray.length - 1;
                    String fileType = strArray[suffixIndex] + ""; //文件类型
                    double size = file.getSize();
                    System.out.println("文件类型为 " + fileType);
                    double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                    System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");
                    File dest = new File(path + "/" + fileNameAll);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdir();
                    }
                    file.transferTo(dest);
                    enclosure = new TraEnclosure();
                    tips.put("fileUrl", enclosure);

                }
            }
            Publog publog = new Publog();
            publog.setStatus("02");
            publog.setPid(pid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            publog.setArchDate(sdf.parse(fileDate));
            publogService.updateSelective(publog);
            Map<String, String> map = publogQuarterService.getTime(plog.getPubQuarter(), plog.getPubYear());
            List<PublogRegularVo> publogRegularVos=new ArrayList<>();
            if("0".equals(quarterFlag)){//年度
                publogRegularVos= publogYearReportService.getYearReportList(map);
            }else{
                publogRegularVos = publogYearReportService.getQuarterReportList(map);
            }
            for (int i = 0; i < publogRegularVos.size(); i++) {
                switch (publogRegularVos.get(i).getTranType()) {
                    case "01"://投资入股类
                        TraInvestment traInterestment = new TraInvestment();
                        traInterestment.setPid(publogRegularVos.get(i).getPid());
                        if("0".equals(quarterFlag)){
                            traInterestment.setYearRepStatus("1");
                        }else{
                            traInterestment.setQuarterRepStatus("1");
                        }

                        traInvestmentService.updateSelective(traInterestment);
                        break;
                    case "02"://资金运用类
                        TraFunds traFunds = new TraFunds();
                        traFunds.setPid(publogRegularVos.get(i).getPid());
                        if("0".equals(quarterFlag)){
                            traFunds.setYearRepStatus("1");
                        }else{
                            traFunds.setQuarterRepStatus("1");
                        }
                        traFundsService.updateSelective(traFunds);
                        break;
                    case "03"://保险业务类
                        TraBusiness traBusiness = new TraBusiness();
                        traBusiness.setPid(publogRegularVos.get(i).getPid());
                        if("0".equals(quarterFlag)){
                            traBusiness.setYearRepStatus("1");
                        }else{
                            traBusiness.setQuarterRepStatus("1");
                        }
                        traBusinessService.updateSelective(traBusiness);
                        break;
                    case "04"://  利益转移类
                        TraInterest traInterest = new TraInterest();
                        traInterest.setPid(publogRegularVos.get(i).getPid());
                        if("0".equals(quarterFlag)){
                            traInterest.setYearRepStatus("1");
                        }else{
                            traInterest.setQuarterRepStatus("1");
                        }
                        traInterestService.updateSelective(traInterest);
                        break;
                    case "05": //提供服务类
                        TraService traService = new TraService();
                        traService.setPid(publogRegularVos.get(i).getPid());
                        if("0".equals(quarterFlag)){
                            traService.setYearRepStatus("1");
                        }else{
                            traService.setQuarterRepStatus("1");
                        }
                        traServiceService.updateSelective(traService);
                        break;
                    default://其他类
                        TraOther traOther = new TraOther();
                        traOther.setPid(publogRegularVos.get(i).getPid());
                        if("0".equals(quarterFlag)){
                            traOther.setYearRepStatus("1");
                        }else{
                            traOther.setQuarterRepStatus("1");
                        }
                        traOtherService.updateSelective(traOther);
                        break;
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败");
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
            publogService.deleteByPrimaryKey(pid);
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
     * 检查未归类交易数据和未确定交易类型数据存不存在
     */
    @RequestMapping("/checkData")
    @ResponseBody
    public Map<String,Object> checkData(@RequestParam Map<String,Object> data,HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String, Object> res = new HashMap<String, Object>();
        //未归类存在交易数据 返回201 不可生成文件
        long count=unSortOutService.count();
        if(count>1){
            res.put("code","201");
            return res;
        }
        //查出来的数据如果业务类型存在null 返回202 不可生成文件
        Map<String, String> map1 = publogQuarterService.getTime(data.get("quarter").toString(), data.get("year").toString());
        List<PublogRegularVo> publogRegularVos = publogYearReportService.getYearReportList(map1);
        for(PublogRegularVo v :publogRegularVos){
            if(StringUtil.isEmpty(v.getBusType())){
                res.put("code","202");
                return res;
            }
        }
        res.put("code","200");
        return res;
    }


}
