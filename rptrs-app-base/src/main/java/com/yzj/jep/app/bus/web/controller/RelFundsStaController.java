package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.FundsInvestmentStaVo;
import com.yzj.jep.app.bus.vo.RelFundsVo;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.*;
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
import java.util.*;

/**
 * @author wll
 */

@Controller
@RequestMapping("/bus/relFundsSta")
public class RelFundsStaController extends CrudController<RelFunds> {

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    @Autowired
    private RelFundsService relFundsService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/relFundsSta";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelFunds> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        relFundsService.query(page, filters, orders);

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
    public Map<String, Object> query1( String quarter,  String investmentBalanceTime, Page<RelFunds> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Page<RelFundsVo> pr = new Page();
        //查全年的数据
        if(StringUtils.isEmpty(quarter)){
            if(StringUtils.isEmpty(investmentBalanceTime)){
                //若年度为空，则查询当前年份
                Calendar cal=Calendar.getInstance();
                investmentBalanceTime=String.valueOf(cal.get(Calendar.YEAR));
            }
            List<RelFunds> rf= relFundsService.queryYearFunds(investmentBalanceTime);
            List<RelFundsVo> allYearFundsList=new ArrayList<>();
            for(int i=0;i<rf.size();i++){
                allYearFundsList.add(computeInfo(rf.get(i)));
            }
            pr.setTotal(allYearFundsList.size());
            pr.setResult(allYearFundsList);
            result.put(RESULT_TOTAL, pr.getTotal());
            result.put(RESULT_ROWS, pr.getResult());
            return result;

        }





        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        RelFundsVo relfundsVo = relFundsService.queryList(investmentBalanceTime, quarter);
        filters.add(new Filter("investmentBalanceTime", Operator.GE, relfundsVo.getStartInvestmentBalanceTime()));
        filters.add(new Filter("investmentBalanceTime", Operator.LE, relfundsVo.getEndInvestmentBalanceTime()));
//        filters.add(new Filter("relName",Operator.LIKE,relfundsVo.getRelName()));
        orders.add(new Order("investmentBalanceTime", OrderType.DESC));
        prepareQuery(page, filters, orders, request);
        if(page.getResult().size()>0){
            RelFunds relFunds = page.getResult().get(0);
            List<RelFundsVo> relFundsVos = new ArrayList<>();
            relFundsVos.add(computeInfo(relFunds));
            pr.setTotal(relFundsVos.size());
            pr.setResult(relFundsVos);
        }
        result.put(RESULT_TOTAL, pr.getTotal());
        result.put(RESULT_ROWS, pr.getResult());
        return result;
    }

    //比例计算
    private RelFundsVo computeInfo(RelFunds relFunds) {
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.selectAll();
        RootOrgInfo rootOrgInfo = null;
        if (CollectionUtils.isNotEmpty(rootOrgInfos)) {
            rootOrgInfo = rootOrgInfos.get(0);
        }
            RelFundsVo relFundsVo = new RelFundsVo();
            BeanUtils.copyProperties(relFunds, relFundsVo);
            if (rootOrgInfo != null) {
                //占总资产比例(为该法人投资余额占中邮保险上年末总资产的比例。计算公式为：该法人的投资余额/中邮上年末总资产*100%)allInvestProForTotal
                relFundsVo.setAllInvestProForTotal(relFundsVo.getFundsInvestmentBalance().divide(rootOrgInfo.getTotalCapital(),6,BigDecimal.ROUND_HALF_UP));
                //占法人主体总资产比例(该法人的投资余额/该法人上年末总资产*100%)allInvestProForOrgTotal
                relFundsVo.setAllInvestProForOrgTotal(relFundsVo.getFundsInvestmentBalance().divide(relFundsVo.getRelTotalAssets(),6,BigDecimal.ROUND_HALF_UP));
            }
        return relFundsVo;
    }


}
