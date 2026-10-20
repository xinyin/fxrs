package com.yzj.jep.app.bus.web.controller;

import cn.hutool.core.date.DateUtil;
import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.FundsInvestmentStaVo;
import com.yzj.jep.app.sub.model.InsAllfunds;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wll
 */
@Controller
@RequestMapping("/bus/fundsInvestmentSta")
public class FundsInvestmentStaController extends CrudController<FundsInvestment> {

    @Autowired
    private FundsInvestmentService fundsInvestmentService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/fundsInvestmentSta";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FundsInvestment> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
//        filters.add(new Filter("delflag", Operator.EQ, "1"));

        fundsInvestmentService.query(page, filters, orders);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query1"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> query1( String quarter, String investmentBalanceTime, Page<FundsInvestment> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Page<FundsInvestment> pf = new Page();
        //查全年的数据
        if(StringUtils.isEmpty(quarter)){
            if(StringUtils.isEmpty(investmentBalanceTime)){
              //若年度为空，则查询当前年份
                Calendar cal=Calendar.getInstance();
                investmentBalanceTime=String.valueOf(cal.get(Calendar.YEAR));
            }
            List<FundsInvestment>fu= fundsInvestmentService.queryYearFunds(investmentBalanceTime);
            pf.setTotal(fu.size());
            pf.setResult(fu);
            result.put(RESULT_TOTAL, pf.getTotal());
            result.put(RESULT_ROWS, pf.getResult());
            return result;

        }
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("investmentBalanceTime", OrderType.DESC));
        FundsInvestmentStaVo fundsInvestmentStaVo = fundsInvestmentService.queryStaList(quarter, investmentBalanceTime);
        filters.add(new Filter("investmentBalanceTime", Operator.GE, fundsInvestmentStaVo.getStartInvestmentBalanceTime()));
        filters.add(new Filter("investmentBalanceTime", Operator.LE, fundsInvestmentStaVo.getEndInvestmentBalanceTime()));
        prepareQuery(page, filters, orders, request);
        if(!page.getResult().isEmpty()){
            FundsInvestment fundsInvestments = page.getResult().get(0);
            List<FundsInvestment> fundsInvestmentStaVoList = new ArrayList<>();
            fundsInvestmentStaVoList.add(fundsInvestments);
            pf.setTotal(fundsInvestmentStaVoList.size());
            pf.setResult(fundsInvestmentStaVoList);
        }
        result.put(RESULT_TOTAL, pf.getTotal());
        result.put(RESULT_ROWS, pf.getResult());
        return result;
    }

    //计算各类比例
    private FundsInvestmentStaVo computeInfo(FundsInvestment fundsInvestmentList) {
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.selectAll();
        RootOrgInfo rootOrgInfo = null;
        if (CollectionUtils.isNotEmpty(rootOrgInfos)) {
            rootOrgInfo = rootOrgInfos.get(0);
        }

            FundsInvestmentStaVo fundsInvestmentStaVo = new FundsInvestmentStaVo();
            BeanUtils.copyProperties(fundsInvestmentList, fundsInvestmentStaVo);
            if (rootOrgInfo != null) {
                //总资产(上年末)
                fundsInvestmentStaVo.setTotalCapital(rootOrgInfo.getTotalCapital());
                //净资产(上年末)
                fundsInvestmentStaVo.setNetCaptial(rootOrgInfo.getNetCaptial().longValue());
                //未上市权益类资产占总资产比例
                fundsInvestmentStaVo.setUnlistedPro(fundsInvestmentStaVo.getUnlistedBalance().divide(rootOrgInfo.getTotalCapital(),4,BigDecimal.ROUND_HALF_UP));
                //不动产类资产占总资产比例
                fundsInvestmentStaVo.setRealEstatePro(fundsInvestmentStaVo.getRealEstateBalance().divide(rootOrgInfo.getTotalCapital(),4,BigDecimal.ROUND_HALF_UP));
                //其他金融资产-合计
                fundsInvestmentStaVo.setOtherTotal(fundsInvestmentStaVo.getBankFinancialBalance()
                                    .add(fundsInvestmentStaVo.getBankNegotiableSecuritiesBalance()
                                    .add(fundsInvestmentStaVo.getTrustSchemeBalance()
                                    .add(fundsInvestmentStaVo.getNegotiableSecuritiesSpecialPlanBalance()
                                    .add(fundsInvestmentStaVo.getOtherFinanceBalance())))));
                //其他金融资产占总资产比例
                fundsInvestmentStaVo.setOtherTotalPro(fundsInvestmentStaVo.getOtherTotal().divide(rootOrgInfo.getTotalCapital(),4,BigDecimal.ROUND_HALF_UP));
                //境外投资占总资产比例
                fundsInvestmentStaVo.setForeignPro(fundsInvestmentStaVo.getForeignBalance().divide(rootOrgInfo.getTotalCapital(),4,BigDecimal.ROUND_HALF_UP));
                //全部投资余额（含活期存款）
                fundsInvestmentStaVo.setAllBalance(fundsInvestmentStaVo.getRealEstateBalance()
                                    .add(fundsInvestmentStaVo.getUnlistedBalance()
                                    .add(fundsInvestmentStaVo.getForeignBalance()
                                    .add(fundsInvestmentStaVo.getOtherFinanceBalance()
                                    .add(fundsInvestmentStaVo.getFixedIncomeBalance()
                                    .add(fundsInvestmentStaVo.getListedBalance()
                                    .add(fundsInvestmentStaVo.getInsuranceManagementBalance()
                                    .add(fundsInvestmentStaVo.getBankFinancialBalance()
                                    .add(fundsInvestmentStaVo.getBankNegotiableSecuritiesBalance()
                                    .add(fundsInvestmentStaVo.getTrustSchemeBalance()
                                    .add(fundsInvestmentStaVo.getNegotiableSecuritiesSpecialPlanBalance()
                                    .add(fundsInvestmentStaVo.getInsuranceFundsSupportPlanBalance()
                                    .add(fundsInvestmentStaVo.getOtherInvestmentBalance())))))))))))));
                //全部投资余额占总资产比例allInvestProForTotal
                fundsInvestmentStaVo.setAllInvestProForTotal(fundsInvestmentStaVo.getAllBalance().divide(rootOrgInfo.getTotalCapital(),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
                //全部投资余额占净资产比例allInvestProForNetAsset
                fundsInvestmentStaVo.setAllInvestProForNetAsset(fundsInvestmentStaVo.getAllBalance().divide(rootOrgInfo.getNetCaptial(),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            }

        return fundsInvestmentStaVo;
    }


}
