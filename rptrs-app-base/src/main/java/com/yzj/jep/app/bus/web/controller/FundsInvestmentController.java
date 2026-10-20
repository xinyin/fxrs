package com.yzj.jep.app.bus.web.controller;


import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.model.RiskRatio;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.bus.service.RiskRatioService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.FundsInvestmentVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.ObtainFundsLimit;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/fundsInvestment")
public class FundsInvestmentController extends CrudController<FundsInvestment> {
    private static Logger logger = LoggerFactory.getLogger(FundsInvestmentController.class);

    @Autowired
    private FundsInvestmentService fundsInvestmentService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    @Resource
    private RiskRatioService riskRatioService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/fundsInvestment/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "bus/fundsInvestmentList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FundsInvestment> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(Order.desc("investmentBalanceTime"));
        fundsInvestmentService.query(page, filters, orders);

    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/fundsInvestmentEdit";
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

        FundsInvestment fundsInvestment = fundsInvestmentService.get(pid);
        model.addAttribute("fundsInvestment", fundsInvestment);
        model.addAttribute("opt", "edit");

        return "bus/fundsInvestmentEdit";
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

        FundsInvestment fundsInvestment = fundsInvestmentService.get(pid);
        model.addAttribute("fundsInvestment", fundsInvestment);

        return "bus/fundsInvestmentView";
    }

    /**
     * 保存
     *
     * @param fundsInvestment
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(FundsInvestment fundsInvestment) {
        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(fundsInvestment);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        if (fundsInvestment.getRealEstateBalance() == null ||
//                fundsInvestment.getUnlistedBalance()==null ||
                fundsInvestment.getForeignBalance() == null ||
//                fundsInvestment.getOtherFinanceBalance()==null ||
                fundsInvestment.getFixedIncomeBalance() == null ||
//                fundsInvestment.getListedBalance()==null ||
                fundsInvestment.getInsuranceManagementBalance() == null ||
                fundsInvestment.getBankFinancialBalance() == null ||
                fundsInvestment.getBankNegotiableSecuritiesBalance() == null ||
                fundsInvestment.getTrustSchemeBalance() == null ||
                fundsInvestment.getNegotiableSecuritiesSpecialPlanBalance() == null ||
                fundsInvestment.getInsuranceFundsSupportPlanBalance() == null ||
                fundsInvestment.getOtherInvestmentBalance() == null ||
                fundsInvestment.getAllInvestmentBalance() == null ||
                fundsInvestment.getMobilityBalance() == null ||
                fundsInvestment.getBalanceInvestment() == null
        ) {
            fundsInvestment.setStatus("0");
        } else {
            fundsInvestment.setStatus("1");
        }
        UserProfile profile = UserProfileUtils.getUserProfile();
        String a = profile.getUserid();
        fundsInvestment.setCreateById(a);

        Boolean isExist = fundsInvestmentService.exists(fundsInvestment.getPid());
        try {
            if (Boolean.TRUE.equals(isExist)) {

                fundsInvestmentService.updateSelective(fundsInvestment);
            } else {
                List<Filter> filters = new ArrayList<>();
                filters.add(new Filter("investmentBalanceTime",Operator.EQ, fundsInvestment.getInvestmentBalanceTime()));
                filters.add(new Filter("delflag",Operator.EQ, "1"));
                List<FundsInvestment> fundsInvestments = fundsInvestmentService.select(filters);

                if (!fundsInvestments.isEmpty()) {
                    fundsInvestment.setPid(fundsInvestments.get(0).getPid());
                    fundsInvestmentService.updateSelective(fundsInvestment);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.SUCCESS_UPDATE);
                    return tips;
                }
                fundsInvestmentService.insertSelective(fundsInvestment);
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
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            fundsInvestmentService.deleteByPrimaryKey(pid);
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

        boolean exist = fundsInvestmentService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query1"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> query1(String quarter, String investmentBalanceTime, Page<FundsInvestment> page, Model model, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        FundsInvestmentVo fundsInvestmentVo = new FundsInvestmentVo();
        fundsInvestmentVo = fundsInvestmentService.queryList(quarter, investmentBalanceTime);
        filters.add(new Filter("investmentBalanceTime", Operator.GE, fundsInvestmentVo.getStartInvestmentBalanceTime()));
        filters.add(new Filter("investmentBalanceTime", Operator.LE, fundsInvestmentVo.getEndInvestmentBalanceTime()));
        orders.add(new Order("investmentBalanceTime", OrderType.DESC));
        PageHelper.start(page);
        List<FundsInvestment> fundsInvestments = fundsInvestmentService.select(filters,orders);
        PageHelper.end(page, fundsInvestments);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    @RequestMapping("/getLimit")
    @ResponseBody
    public Map<String, Object> getLimit(@RequestParam("years") String year, @RequestParam(value = "quarter") String quarter, Model model, HttpServletRequest request) {
        Map<String, Object> tips = new HashMap<>(4);
        Map<String,BigDecimal> result = ObtainFundsLimit.getLimitRule(Integer.parseInt(year), Integer.parseInt(quarter));

        /* 获取风险指标配置中的预警比例(%) */
        List<Filter> riskFilterList = new ArrayList<>();
        riskFilterList.add(new Filter("delflag", "1 "));
        List<RiskRatio> ratioList = riskRatioService.select(riskFilterList);
        BigDecimal limitRadio = BigDecimal.ZERO;
        if (CollUtil.isNotEmpty(ratioList)) {
            limitRadio = StringUtils.isEmpty(ratioList.get(0).getWarningValue()) ? BigDecimal.ZERO : new BigDecimal(ratioList.get(0).getWarningValue()).divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP);
        }
        tips.put("limitRadio", limitRadio);
        tips.put("realEstateLimit", result.get("limit04"));
        tips.put("foreignLimit", result.get("limit06"));
        tips.put("balanceInvestmentLimit", result.get("limit03"));
        tips.put("financialProducts", result.get("limit05"));
        return tips;

    }
}
