package com.yzj.jep.app.bus.web.controller;


import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.FundsVo;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.sub.model.InsAllfunds;
import com.yzj.jep.app.sub.model.InsAllfundsView;
import com.yzj.jep.app.sub.model.InsRelfunds;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.sub.vo.AllFundsVo;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.rules.drools5.model.Objects;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.math.RoundingMode.HALF_UP;

@Controller
@RequestMapping("/report/warning")
public class ReportWarningController extends CrudController<Objects> {
    private static Logger logger = LoggerFactory.getLogger(com.yzj.jep.app.ex.web.controller.WFEngineController.class);

    @Autowired
    DictItemService dictItemService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    @Autowired
    private InsRelfundsViewService insRelfundsViewService;

    @Autowired
    private InsRelfundsService insRelfundsService;

    @Autowired
    private FundsInvestmentService fundsInvestmentService;

    @Autowired
    private InsAllfundsService insAllfundsService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private SublogService sublogService;

    @Autowired
    private InsAllfundsViewService insAllfundsViewService;


    /**
     * 关联交易比例控制预警
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/relTranPreWraning")
    public String relTranPreWraning(Model model) {
        //获取上年度总资产、净资产
        Map<String, Object> totalAssetsMap = getTotalAssetsMap();
        BigDecimal allYear = (BigDecimal) totalAssetsMap.get("totalCapital");
        BigDecimal allNet = (BigDecimal) totalAssetsMap.get("netCaptial");
        model.addAttribute("allYear", allYear);
        model.addAttribute("allNet", allNet);
        //获取上季度末总资产、净资产
        Map<String, Object> lastYearAssetMap = getLastYearAssetMap();
        BigDecimal lastQuarter = (BigDecimal) lastYearAssetMap.get("totalCapital");
        BigDecimal lastQuarterNet = (BigDecimal) lastYearAssetMap.get("netCaptial");
        model.addAttribute("lastQuarter", lastQuarter);
        model.addAttribute("lastQuarterNet", lastQuarterNet);

        return "rpt/relTranPreWraning";
    }

    /**
     * 获上年度总资产和净资产
     *
     * @param request
     * @return
     */
    /*@RequestMapping(value = "/getTotalAssets")
    public String getTotalAssets(HttpServletRequest request,Model model) {
        Map<String, Object> totalAssetsMap = getTotalAssetsMap();
        BigDecimal allYear = (BigDecimal) totalAssetsMap.get("totalCapital");
        BigDecimal allNet = (BigDecimal) totalAssetsMap.get("netCaptial");
        Map<String,Object> mapYears = new HashMap<>();
        mapYears.put("allYear",allYear);
        mapYears.put("allNet",allNet);
        model.addAttribute("allYear",allYear);
        model.addAttribute("allNet",allNet);



        return "rpt/relTranPreWraning";
    }*/

    /**
     * 获取上年末总资产和净资产
     *
     * @param
     * @return
     */
    private Map<String, Object> getTotalAssetsMap() {
        Map<String, Object> map = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        Map<String, Object> yearAndQuarter = getYearAndQuarter();
        String years = (String) yearAndQuarter.get("lastYears");
        filters.add(new Filter("years", years));
        filters.add(new Filter("quarter", 5));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filters);
        RootOrgInfo rootOrgInfo = null;
        if (CollectionUtils.isEmpty(rootOrgInfos)) {
            map.put("totalCapital", new BigDecimal(0));
            map.put("netCaptial", new BigDecimal(0));
            return map;
        }
        rootOrgInfo = rootOrgInfos.get(0);
        BigDecimal totalCapital = rootOrgInfo.getTotalCapital();
        BigDecimal netCaptial = rootOrgInfo.getNetCaptial();
        //总资产
        map.put("totalCapital", totalCapital);
        //净资产
        map.put("netCaptial", netCaptial);
        return map;
    }

    /**
     * 获取上季末的总资产和净资产
     *
     * @param request
     * @return
     */
   /* @RequestMapping(value = "/getLastYearAsset")
    @ResponseBody
    public Map<String, Object> getLastYearAsset(HttpServletRequest request) {
        return getLastYearAssetMap();
    }*/

    /**
     * 获取上季末末总资产和净资产
     *
     * @return
     */
    private Map<String, Object> getLastYearAssetMap() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> yearAndQuarter = getYearAndQuarter();
        List<Filter> filters = new ArrayList<>();
        String quarter = (String) yearAndQuarter.get("quarter");
        String years = (String) yearAndQuarter.get("years");
        filters.add(new Filter("years", years));
        filters.add(new Filter("quarter", quarter));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filters);
        RootOrgInfo rootOrgInfo = null;
        if (CollectionUtils.isEmpty(rootOrgInfos)) {
            map.put("totalCapital", new BigDecimal(0));
            map.put("netCaptial", new BigDecimal(0));
            return map;
        }
        rootOrgInfo = rootOrgInfos.get(0);
        BigDecimal totalCapital = rootOrgInfo.getTotalCapital();
        BigDecimal netCaptial = rootOrgInfo.getNetCaptial();
        //总资产(上季末netCaptial)
        map.put("totalCapital", totalCapital);
        //净资产(上季末)
        map.put("netCaptial", netCaptial);
        return map;
    }

    /**
     * 对单一关联方的全部投资余额合计
     * 对上一年度末的数据操作
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/relTotalRat")
    @ResponseBody
    public List<Map<String, Object>> relTotalRat(HttpServletRequest request) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> totalAssetsMap = getTotalAssetsMap();
        //总资产
        BigDecimal totalCapital = (BigDecimal) totalAssetsMap.get("totalCapital");
        BigDecimal warningNumRed = totalCapital.multiply(new BigDecimal("0.15")).setScale(2, HALF_UP);
        BigDecimal warningNumYellow = totalCapital.multiply(new BigDecimal("0.135")).setScale(2, HALF_UP);
        //净资产
        BigDecimal netCaptial = (BigDecimal) totalAssetsMap.get("netCaptial");
        Map<String, Object> yearAndQuarter = getYearAndQuarter();
        String subQuarter = (String) yearAndQuarter.get("quarter");
        String subYear = (String) yearAndQuarter.get("years");
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(new Filter("subYear", subYear));
        filters.add(new Filter("subQuarter", subQuarter));
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("corpInvestFund", OrderType.DESC));
        List<InsRelfunds> insRelfunds = insRelfundsService.select(filters, orders);
        int size = Math.min(insRelfunds.size(), 5);
        for (int i = 0; i < size; i++) {
            //只取前五条数据
            Map<String, Object> map = new HashMap<>();
            //1关联方名称
            map.put("relName", insRelfunds.get(i).getRelName());
            //2投资金额
            map.put("corpInvestFund", insRelfunds.get(i).getCorpInvestFund());
            //3上一年度总金额
            map.put("totalCapital", totalCapital);
            //4红色预警临界值
            map.put("warningNumRed", warningNumRed);
            //5黄色预警临界值
            map.put("warningNumYellow", warningNumYellow);
            list.add(map);
        }

        return list;
    }

    /**
     * 资金运用类投资限额规则 操作上季度末总资产
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/fundsTotalRat")
    @ResponseBody
    public List<Map<String, Object>> fundsTotalRat(HttpServletRequest request) {
        List<Map<String, Object>> list = new ArrayList<>();
        FundsVo fundsVo = new FundsVo();
        FundsVo fundsVo2 = new FundsVo();
        FundsVo fundsVo3 = new FundsVo();
        FundsVo fundsVo4 = new FundsVo();
        List<Filter> filters = RequestUtils.buildFilters(request);
        Map<String, Object> lastYearAsset = getLastYearAssetMap();
        //总资产
        BigDecimal lastTotalCapital = (BigDecimal) lastYearAsset.get("totalCapital");
        /**
         * 未上市权益类：∑协议（合同）金额<=上季度末总资产*15%
         *
         * 不动产类：∑协议（合同）金额<=上季度末总资产*15%
         *
         * 其他金融资产类：∑协议（合同）金额<=上季度末总资*12.5%
         *
         * 境外投资：∑协议（合同）金额<=上季度末总资*7.5%
         */
        BigDecimal maxNum = lastTotalCapital.multiply(new BigDecimal("0.15")).setScale(2, HALF_UP);
        BigDecimal middleNum = lastTotalCapital.multiply(new BigDecimal("0.125")).setScale(2, HALF_UP);
        BigDecimal minNum = lastTotalCapital.multiply(new BigDecimal("0.075")).setScale(2, HALF_UP);
        //未上市权利类
        fundsVo.setLimit(maxNum);
        fundsVo.setName("未上市权益类");
        fundsVo2.setLimit(maxNum);
        fundsVo2.setName("不动产类资产");
        fundsVo3.setLimit(middleNum);
        fundsVo3.setName("其他金融资产");
        fundsVo4.setLimit(minNum);
        fundsVo4.setName("境外投资");
        /**
         * 红：R>各类投资限额
         *
         * 黄：R=< 各类投资限额的100% and R>= 各类投资限额的90%
         *
         * 蓝：R<各类投资限额的90%
         */
        //未上市类权益类资产
        Map<String, Object> yearAndQuarter = getYearAndQuarter();
        String subYear = (String) yearAndQuarter.get("years");
        String subQuarter = (String) yearAndQuarter.get("quarter");
        filters.add(new Filter("subYear", subYear));
        filters.add(new Filter("subQuarter", subQuarter));
        List<InsAllfunds> insAllfunds = insAllfundsService.select(filters);
        List<AllFundsVo> allFundsVos = new ArrayList<>();
        for (int i = 0; i < insAllfunds.size(); i++) {
            allFundsVos.add(computeInfo(insAllfunds.get(i)));
        }
        BigDecimal unlistedInvestBalance = allFundsVos.stream().map(AllFundsVo::getUnlistedInvestBalance).reduce(BigDecimal.ZERO, BigDecimal::add);//为上市权益类
        BigDecimal imbsInvestBalance = allFundsVos.stream().map(AllFundsVo::getImvbsInvestBalance).reduce(BigDecimal.ZERO, BigDecimal::add);//不动产类
        BigDecimal abroadFunds = allFundsVos.stream().map(AllFundsVo::getAbroadFund).reduce(BigDecimal.ZERO, BigDecimal::add);//境外投资
        BigDecimal otherTotal = allFundsVos.stream().map(AllFundsVo::getAllOtherFunds).reduce(BigDecimal.ZERO, BigDecimal::add);//其他类
        //未上市权益类
        fundsVo.setBalance(unlistedInvestBalance);
        //不动产类
        fundsVo2.setBalance(imbsInvestBalance);
        //其他类
        fundsVo3.setBalance(otherTotal);
        //境外投资类
        fundsVo4.setBalance(abroadFunds);
        List<FundsVo> fundsVoList = new ArrayList<>();
        fundsVoList.add(fundsVo);
        fundsVoList.add(fundsVo2);
        fundsVoList.add(fundsVo3);
        fundsVoList.add(fundsVo4);
        for (FundsVo vo : fundsVoList) {
            Map<String, Object> map = new HashMap<>();
            map.put("relName", vo.getName());
            map.put("balanceFunds", vo.getBalance());
            map.put("limitFunds", vo.getLimit());
            list.add(map);
        }
        return list;
    }

    /**
     * 底层基础资产涉及关联方产品限额规则
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/baseTotal")
    @ResponseBody
    public List<Map<String, Object>> baseTotal(HttpServletRequest request) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<TraFunds> traFundsList = new ArrayList<>();
        //financialProRatio 投资的比例 underIsRel 是否为底层关联方
        filters.add(new Filter("underIsRel", 1));
        filters.add(new Filter("investmentType", 04));
        Map<String, Object> yearAndQuarter = getYearAndQuarter();
        String years = (String) yearAndQuarter.get("years");
        String quarter = (String) yearAndQuarter.get("quarter");
        String[] strings = yearQuarter(years, quarter);
        filters.add(new Filter("tranDate", Operator.BETWEEN, strings));
        List<TraFunds> fundsList = traFundsService.select(filters);
        for (TraFunds traFunds : fundsList) {
            Map<String, Object> map = new HashMap<>();
            map.put("relName", traFunds.getRelName());
            map.put("limitFunds", new BigDecimal("0.60"));
            String financialProRatio = traFunds.getFinancialProRatio();
            BigDecimal divide = new BigDecimal(financialProRatio.trim().replace("%", "")).divide(BigDecimal.valueOf(100));
            map.put("FinancialProRatio", divide);
            mapList.add(map);


        }
        return mapList;
    }

    /**
     * 获取全部关联方投资余监控预警信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getAllFunds")
    @ResponseBody
    public Map<String, Object> getAllFunds(HttpServletRequest request, Model model) {
        Map<String, Object> mapRes = new HashMap<>();
        //获取上年度总资产、净资产
        Map<String, Object> totalAssetsMap = getTotalAssetsMap();
        //获取总资产(上年度末)
        BigDecimal totalCapital = (BigDecimal) totalAssetsMap.get("totalCapital");
        //净资产(上年度末)
        BigDecimal netCaptial = (BigDecimal) totalAssetsMap.get("netCaptial");

        /**
         * 红：R>上一年度末总资产的30%与上一年度末净资产二者中金额较低者
         *
         * 黄：R=<上一年度末总资产的30%与上一年度末净资产二者中金额较低者 and
         *
         * R>=上一年度末总资产的27%与上一年度末净资产的90%二者中金额较低者
         *
         * 蓝：R<上一年度末总资产的27%与上一年度末净资产的90%二者中金额较低者
         */
        BigDecimal num1 = totalCapital.multiply(new BigDecimal("0.30")).setScale(6, HALF_UP);
        BigDecimal num2 = totalCapital.multiply(new BigDecimal("0.27")).setScale(6, HALF_UP);
        BigDecimal num3 = netCaptial.multiply(new BigDecimal("0.90")).setScale(6, HALF_UP);
        //红色预警临界值
        BigDecimal redNum = (num1.compareTo(netCaptial) < 0) ? num1 : netCaptial;
        //黄色预警临界值
        BigDecimal yellowNum = (num2.compareTo(num3) < 0) ? num2 : num3;


        //获取全部的投资余额
        BigDecimal allInvestmentBalance = ObtainAllInvestmentBalance();
        //占上年末总资产比例
        //BigDecimal totalCap = allInvestmentBalance.divide(totalCapital).setScale(2, HALF_UP);
        //占上年末净资产比例
        //BigDecimal netCap = allInvestmentBalance.divide(netCaptial).setScale(2, HALF_UP);

        /*
         * 判断红黄蓝三色预警
         * 1、当年累计的全部关联方资金运用类投资金额 > redNum  ； 属于红色预警
         * 2、redNum >= 当年累计的全部关联方资金运用类投资金额 >= yellowNum； 属于黄色预警
         * 3、当年累计的全部关联方资金运用类投资金额 < yellowNum； 属于蓝色预警
         * */
        String rColor ="B";//默认是蓝色

        if(allInvestmentBalance.compareTo(redNum)==1){
            rColor ="R";//红色
        }else if(allInvestmentBalance.compareTo(yellowNum)==-1){
            rColor ="B";//蓝色
        }else{
            rColor ="Y";//黄色
        }

        /*返回数据*/
        //上年度总资产-全部投资余额后额度
        if(totalCapital.compareTo(allInvestmentBalance)<1){
            mapRes.put("totalCapital",new BigDecimal(0));
        }else{
            mapRes.put("totalCapital",totalCapital.subtract(allInvestmentBalance).setScale(6,HALF_UP));
        }
        //上年度净资产-全部投资余额后额度
        if(netCaptial.compareTo(allInvestmentBalance)<1){
            mapRes.put("netCaptial",new BigDecimal(0));
        }else{
            mapRes.put("netCaptial",netCaptial.subtract(allInvestmentBalance).setScale(6,HALF_UP));
        }
        //全部投资余额
        mapRes.put("allInvestmentBalance",allInvestmentBalance);
        mapRes.put("rColor",rColor);
        mapRes.put(WebCode.SUCCESS, true);
        return mapRes;
    }

    /**
     * 获取全部关联方投资余额
     * <br>通过查询资金运用类关联交易统计表获取
     *
     * @return
     */
    private BigDecimal ObtainAllInvestmentBalance() {
        BigDecimal allInvestmentBalance = new BigDecimal(0).setScale(6, HALF_UP);
        try {
            //获取年份
            Map<String, Object> yearAndQuarter = getYearAndQuarter();
            String year = (String) yearAndQuarter.get("years");
            String quarter = (String) yearAndQuarter.get("quarter");
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter("subType", "07"));
            filters.add(new Filter("subYear", year));
            //filters.add(new Filter("status","02"));
             filters.add(new Filter("subQuarter",quarter));
            //报送的时候只有一条数据
            List<Sublog> sublogs = sublogService.select(filters);
            if (CollectionUtils.isNotEmpty(sublogs)) {
                String batchDecimal =  sublogs.get(0).getBatchDecimal();
                //获取资金运用类关联交易统计表的备份数据
                List<Filter> filters1 = new Filter.Builder().eq("allfundsSeq", batchDecimal).build();
                List<InsAllfundsView> insAllfundsViewList = insAllfundsViewService.select(filters1);
                if (CollectionUtils.isNotEmpty(insAllfundsViewList)) {
                    Integer portId = insAllfundsViewList.get(0).getPortPid();
                    //通过portid 获取资金运用类关联交易统计表数据
                    List<Filter> allFundsfilters = new Filter.Builder().eq("pid", portId).build();
                    List<InsAllfunds> insAllfundsList = insAllfundsService.select(allFundsfilters);
                    if (CollectionUtils.isNotEmpty(insAllfundsList)) {
                        allInvestmentBalance = insAllfundsViewList.get(0).getAllInvestmentBalance();
                    }else{
                        return  allInvestmentBalance;
                    }
                }else{
                    return  allInvestmentBalance;
                }
            }else{
                return  allInvestmentBalance;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取资金运用类全部关联方投资余额出错：" + e);
        }
        return  allInvestmentBalance;
    }


    //计算数据 商业银行理财产品投资余额 银行业金融机构信贷资产支持证券投资余额  信托公司集合资金信托计划投资余额
    //证券公司专项资产管理计划投资余额  其他类金融资产投资余额
    private AllFundsVo computeInfo(InsAllfunds allfunds) {
        AllFundsVo allFundsVo = new AllFundsVo();
        BeanUtils.copyProperties(allfunds, allFundsVo);
        //其他金融资产-合计
        allFundsVo.setAllOtherFunds(allFundsVo.getBankCreditFund()
                .add(allFundsVo.getComBankFinFund()
                        .add(allFundsVo.getTrustFund()
                                .add(allFundsVo.getBondSpecialFund()
                                        .add(allFundsVo.getOtherFund())))));
        return allFundsVo;
    }

    private Map<String, Object> getYearAndQuarter() {
        Map<String, Object> map = new HashMap<>();
        //获取当前年份的上一年
        String lastYears = null;
        String quarter = null;
        String years = null;
        String quarterStr = null;
        String quarterEnd = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 0);
        years = String.valueOf(cal.get(Calendar.YEAR));
        cal.add(Calendar.YEAR, -1);
        lastYears = String.valueOf(cal.get(Calendar.YEAR));
        //获取当前月的上个季度
        cal.add(Calendar.MONTH, 0);
        int month = cal.get(Calendar.MONTH);
        if (month == 1 || month == 2 || month == 3) {
            quarter = "4";
        } else {
            if (month != 3 && month % 3 == 0) {
                quarter = String.valueOf((month / 3) - 1);
            } else {
                quarter = String.valueOf(month / 3);
            }
        }
        //获取当前月所处的季度的第一天和最后一天
        if (quarter.equals("1")) {
            quarterStr = years + "-01-01 00:00:00";
            quarterEnd = years + "-03-31 23:59:59";
        } else if (quarter.equals("2")) {
            quarterStr = years + "-04-01 00:00:00";
            quarterEnd = years + "-06-30 23:59:59";
        } else if (quarter.equals("3")) {
            quarterStr = years + "-07-01 00:00:00";
            quarterEnd = years + "-09-30 23:59:59";
        } else {
            quarterStr = years + "-10-01 00:00:00";
            quarterEnd = years + "-12-31 23:59:59";
        }
        map.put("years", years);
        map.put("lastYears", lastYears);
        map.put("quarter", quarter);
        map.put("quarterSta", quarterStr);
        map.put("quarterEnd", quarterEnd);
        return map;
    }

    //返回季度开始时间和结束时间
    public String[] yearQuarter(String year, String quarter) {
        StringBuffer staTIme = new StringBuffer();
        StringBuffer endTIme = new StringBuffer();
        if ("%".equals(quarter)) {
            staTIme = staTIme.append(year).append("-01-01");
            endTIme = endTIme.append(year).append("-12-31");
            String[] list = {staTIme.toString(), endTIme.toString()};
            return list;
        }
        switch (quarter) {
            case "1":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-03-31");
                break;
            case "2":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-06-31");
                break;
            case "3":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-09-31");
                break;
            case "4":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-12-31");
                break;
        }
        String[] list = {staTIme.toString(), endTIme.toString()};

        return list;
    }


}