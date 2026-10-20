/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月28日 14:27:32
 *
 */

package com.yzj.jep.app.bus.web.controller;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.LimitSensory;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.service.LimitSensoryService;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yzj.jep.orm.entity.Filter;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.Tenant;
import com.yzj.jep.sys.service.TenantService;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/bus/limit")
public class LimitController extends CrudController<Product> {
    private static Logger logger = LoggerFactory.getLogger(LimitSensoryController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private LimitSensoryService limitSensoryService;
    @Autowired
    private TenantService tenantService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "预计额度列表", description = "获取列表页面")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("tranOrg", "");
        } else {
            model.addAttribute("tranOrg", tenantId);
        }
        return "bus/limitList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Product> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        filters.add(new Filter("preLimit", Operator.NNL, ""));
        productService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    @Log(module = "预计额度列表", description = "新增页面")
    public String add(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("tranOrg", "");
        } else {
            model.addAttribute("tranOrg", tenantId);
        }
        return "bus/limitAdd";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    @Log(module = "预计额度列表", description = "编辑页面")
    public String edit(@PathVariable Integer pid, Model model) {

        Product product = productService.get(pid);
        model.addAttribute("pid", pid);
        model.addAttribute("product", product);
        model.addAttribute("opt", "edit");

        return "bus/limitEdit";
    }

    /**
     * 内联树
     *
     * @return
     */
    @RequestMapping(value = "/proNameListTree")
    @Log(module = "预计额度列表", description = "内联树")
    public String proNameListTree() {
        return "bus/limitEditTree";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "预计额度列表", description = "查看页面")
    public String view(@PathVariable Integer pid, Model model) {

        Product product = productService.get(pid);
        model.addAttribute("product", product);
        model.addAttribute("opt", "view");
        return "bus/limitView";
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    @Log(module = "预计额度列表", description = "提交审批")
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Product product = productService.get(pid);
        if ("3".equals(product.getApproveStatus()) || "5".equals(product.getApproveStatus())) {
            //4 代表审批中
            product.setApproveStatus("4");
            productService.updateSelective(product);
            tips.put("data", product);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }


    /**
     * 保存
     *
     * @param product
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Log(module = "预计额度列表", description = "保存")
    public Map<String, Object> save(Product product) {
        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String staffCode = profile.getStaffCode();
        String staffName = profile.getStaffName();
        Integer rate = product.getLimitRate();            //额度使用率

        //根据pid判断是编辑还是新增
        boolean qisExist = productService.exists(product.getPid());
        try {
            if (qisExist) {
                //编辑
                //查询是否存在阈值
                List<Filter> filters = new Filter.Builder()
                        .eq("busLeve", product.getPid())
                        .eq("delflag", "1").build();
                boolean exists1 = limitSensoryService.exists(filters);
                String warn = "0";
                Integer warnThreshold1l = 0;
                Integer warnThreshold2l = 0;
                Integer warnThreshold3l = 0;
                if (exists1) {
                    LimitSensory limitSensory = limitSensoryService.get(filters);
                    if (limitSensory != null) {
                        warnThreshold1l = limitSensory.getWarnThreshold1l();
                        warnThreshold2l = limitSensory.getWarnThreshold2l();
                        warnThreshold3l = limitSensory.getWarnThreshold3l();
                    }
                }
                if (warnThreshold3l > rate) {
                    warn = "3";
                } else if (rate > warnThreshold2l) {
                    warn = "2";
                } else if (rate > warnThreshold1l) {
                    warn = "1";
                }
                Product productL = productService.get(product.getPid());
                productL.setWarnLeve(warn);
                productL.setLimitRate(product.getLimitRate());
                productL.setRemLimit(product.getRemLimit());
                productL.setPasLimit(product.getPasLimit());
                productL.setPreLimit(product.getPreLimit());
                productL.setApproveStatus("3");
                productL.setProId("0");
                productL.setCreateById(staffCode);
                productL.setCreateByName(staffName);
                productL.setWarnLeve(warn);
                productService.updateSelective(productL);
            } else {
                String proNameL = product.getProName();
                String[] split = proNameL.split("-");
                Integer pid = Integer.parseInt(split[1]);
                Product productS = productService.get(pid);
                String preLimit = productS.getPreLimit();
                if (preLimit != null) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "该节点下预计额度已经设置！");
                    return tips;
                }
//                String tradeType1 = product.getTradeType();//交易类型
                if (Integer.parseInt(split[0]) == 2) {
                    productS.setProPath(productService.get(pid).getProName());
                }
                productS.setBusinessType(product.getBusinessType());
                productS.setLimitRate(product.getLimitRate());
                productS.setRemLimit(product.getRemLimit());
                productS.setPasLimit(product.getPasLimit());
                productS.setTranOrg(product.getTranOrg());
                productS.setTradeType(product.getTradeType());
                productS.setPreLimit(product.getPreLimit());
                productS.setApproveStatus("3");

                //新增
                //获取业务类型
                //查询是否存在阈值
                List<Filter> filters = new Filter.Builder()
                        .eq("busLeve", pid)
                        .eq("delflag", "1").build();
                Integer pidL = product.getPid();
                boolean exists1 = limitSensoryService.exists(filters);
                String warn = "0";
                Integer warnThreshold1l = 0;
                Integer warnThreshold2l = 0;
                Integer warnThreshold3l = 0;
                if (exists1) {
                    LimitSensory limitSensory = limitSensoryService.get(filters);
                    warnThreshold1l = limitSensory.getWarnThreshold1l();
                    warnThreshold2l = limitSensory.getWarnThreshold2l();
                    warnThreshold3l = limitSensory.getWarnThreshold3l();
                }
                if (warnThreshold3l > rate) {
                    warn = "3";
                } else if (rate > warnThreshold2l) {
                    warn = "2";
                } else if (rate > warnThreshold1l) {
                    warn = "1";
                }
                productS.setWarnLeve(warn);
                productS.setCreateById(staffCode);
                productS.setCreateByName(staffName);
                productService.updateSelective(productS);
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
    @Log(module = "预计额度列表", description = "删除")
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Product product = productService.get(pid);
        product.setPreLimit(null);
        product.setPasLimit(null);
        product.setLimitRate(null);
        try {
            productService.update(product);
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
        boolean exist = productService.exists(pid);
        tips.put("valid", !exist);
        return tips;
    }

    /**
     * 根据交易类型铲鲟业务类型
     *
     * @return
     */
    @RequestMapping(value = "/getTradeTypeToBuinussType")
    @ResponseBody
    @Log(module = "预计额度列表", description = "根据交易类型铲鲟业务类型")
    public List<NameValuePair> getTradeTypeToBuinussType(Integer unitCodes) {
        List<Filter> filters = new Filter.Builder().eq("paProId", unitCodes).eq("delflag", "1").build();
        List<Product> list = productService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(list, list.size() + 1, "pid", "proName");
        return nameValuePairs;
    }

    /**
     * 查询交易类型getTradeTypeItems
     *
     * @return
     */
    @RequestMapping(value = "/getTradeTypeItems")
    @ResponseBody
    @Log(module = "预计额度列表", description = "查询交易类型getTradeTypeItems")
    public List<NameValuePair> getTradeTypeItems() {
        List<Filter> filters = new Filter.Builder().eq("paProId", 0).eq("delflag", "1").build();
        List<Product> list = productService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(list, list.size() + 1, "pid", "proName");
        return nameValuePairs;
    }

    /**
     * 查询交易主体
     *
     * @return
     */
    @RequestMapping(value = "/getTranOrgItems")
    @ResponseBody
    @Log(module = "预计额度列表", description = "查询交易主体")
    public List<NameValuePair> getTranOrgItems() {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            List<Tenant> list = tenantService.selectAll();
            nameValuePairs = NVUtils.convert(list, list.size() + 1, "code", "name");
        } else {
            List<Filter> filters = new Filter.Builder()
                    .eq("code", tenantId).build();
           /* Tenant tenant = new Tenant();
            tenant.setDelflag("1");
            tenant.setCode(tenantId);*/
            List<Tenant> list = tenantService.select(filters);
            nameValuePairs = NVUtils.convert(list, list.size() + 1, "code", "name");
        }
        return nameValuePairs;
    }

    /**
     * 查询业务类型
     *
     * @return
     */
    @RequestMapping(value = "/getBuinessTypeItems")
    @ResponseBody
    @Log(module = "预计额度列表", description = "查询交易类型")
    public List<NameValuePair> getBuinessTypeItems() {

        List<Filter> filters = new Filter.Builder().eq("busLevel", "1").eq("delflag", "1").build();
        List<Product> list = productService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(list, list.size() + 1, "pid", "proName");
        return nameValuePairs;

    }

    /**
     * 通过交易类型查询所对应的业务类型
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/queryBusinessType/{tradeType}")
    @ResponseBody
    @Log(module = "预计额度管理", description = "通过交易类型查询所对应的业务类型")
    public List<NameValuePair> queryBusinessType(@PathVariable String tradeType) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("proId", 0)
                    .eq("delflag", "1")
                    .eq("paProId", tradeType)
                    .build();
            List<Product> list = productService.select(filters);
            items = NVUtils.convert(list, list.size() + 1, "pid", "proName");
            return items;
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }

    /**
     * 反显交易类型和业务类型
     *
     * @param unitCodes
     * @return
     */

    @RequestMapping("/selectbuinessL")
    @ResponseBody
    public Map<String, String> selectDept(String unitCodes) {
        System.out.println(unitCodes);
        Map<String, String> map = new HashMap<String, String>();
        String[] split = unitCodes.split("-");
        String level = split[0];
        Integer pid = Integer.parseInt(split[1]);
        Integer paProId = productService.get(pid).getPaProId();


//反显交易类型和业务类型
        if ("0".equals(level)) {
            map.put("tradesType", pid.toString());
            map.put("businessType", "0");
        } else if ("1".equals(level)) {
            map.put("tradesType", productService.get(pid).getPaProId().toString());
            map.put("businessType", pid.toString());
        } else if ("2".equals(level)) {
            map.put("tradesType", productService.get(productService.get(pid).getPaProId()).getPaProId().toString());
            map.put("businessType", productService.get(pid).getPaProId().toString());
        }
        return map;
    }

    @RequestMapping("/selectbuinessLS")
    @ResponseBody
    public Map<String, String> selectDeptss(String unitCodes) {
        System.out.println(unitCodes);
        Map<String, String> map = new HashMap<String, String>();
        String[] split = unitCodes.split("-");
        String level = split[0];
        Integer pid = Integer.parseInt(split[1]);
        Integer paProId = productService.get(pid).getPaProId();
//反显交易类型和业务类型
        if ("0".equals(level)) {
            map.put("tradesType", pid.toString());
            map.put("businessType", "0");
            map.put("proName", split[2]);
        } else if ("1".equals(level)) {
            map.put("tradesType", productService.get(pid).getPaProId().toString());
            map.put("businessType", pid.toString());
            map.put("proName", split[2]);
        } else if ("2".equals(level)) {
            map.put("tradesType", productService.get(productService.get(pid).getPaProId()).getPaProId().toString());
            map.put("businessType", productService.get(pid).getPaProId().toString());
            map.put("proName", split[2]);
        }
        return map;
    }

    /**
     * 预计额度数据反显
     *
     * @param product
     * @return
     */
    @RequestMapping(value = "/preLimitQuery")
    @ResponseBody
    @Log(module = "预计额度列表", description = "预计额度数据反显")
    public Map<String, Object> preLimitQuery(Product product) {
        Map<String, Object> tips = new HashMap<String, Object>();
        String tradeType = product.getTradeType();//交易类型
        String businessType = product.getBusinessType();//业务类型
        String preLimit = product.getPreLimit();//预计额度


        if ("1".equals(tradeType)) {
            List<Filter> filters = new Filter.Builder().eq("investmentType", businessType).eq("delflag", "1").build();
            boolean exists = traInvestmentService.exists(filters);
            if (!exists) {
                tips.put("pasLimit", "0");
                tips.put("remLimit", preLimit);
                tips.put("limitRate", "0");
                return tips;
            }
            BigDecimal pasLimits = new BigDecimal("0");//已用额度
            List<TraInvestment> select = traInvestmentService.select(filters);
            for (TraInvestment tra : select) {
                BigDecimal tradeAmount = tra.getTradeAmount();
                pasLimits = pasLimits.add(tradeAmount);
            }
            BigDecimal preLimits = new BigDecimal(preLimit);
            BigDecimal subtract = preLimits.subtract(pasLimits);//剩余额度
            BigDecimal qur = pasLimits.divide(preLimits, 2, RoundingMode.HALF_UP);//额度使用率
            BigDecimal ten = new BigDecimal("100");
            BigDecimal multiply = qur.multiply(ten);
            tips.put("pasLimit", pasLimits);
            tips.put("remLimit", subtract);
            tips.put("limitRate", multiply);
        } else if ("2".equals(tradeType)) {
            List<Filter> filters = new Filter.Builder().eq("investmentType", businessType).eq("delflag", "1").build();
            boolean exists = traFundsService.exists(filters);
            if (!exists) {
                tips.put("pasLimit", "0");
                tips.put("remLimit", preLimit);
                tips.put("limitRate", "0");
                return tips;
            }
            BigDecimal pasLimits = new BigDecimal("0");//已用额度
            List<TraFunds> select = traFundsService.select(filters);
            for (TraFunds tra : select) {
                BigDecimal tradeAmount = tra.getTradeAmount();
                pasLimits = pasLimits.add(tradeAmount);
            }
            BigDecimal preLimits = new BigDecimal(preLimit);
            BigDecimal subtract = preLimits.subtract(pasLimits);//剩余额度
            BigDecimal qur = pasLimits.divide(preLimits, 2, RoundingMode.HALF_UP);//额度使用率
            BigDecimal ten = new BigDecimal("100");
            BigDecimal multiply = qur.multiply(ten);
            tips.put("pasLimit", pasLimits);
            tips.put("remLimit", subtract);
            tips.put("limitRate", multiply);
        } else if ("3".equals(tradeType)) {
            List<Filter> filters = new Filter.Builder().eq("busType", businessType).eq("delflag", "1").build();
            boolean exists = traBusinessService.exists(filters);
            if (!exists) {
                tips.put("pasLimit", "0");
                tips.put("remLimit", preLimit);
                tips.put("limitRate", "0");
                return tips;
            }
            BigDecimal pasLimits = new BigDecimal("0");//已用额度
            List<TraBusiness> select = traBusinessService.select(filters);
            for (TraBusiness tra : select) {
                BigDecimal tradeAmount = tra.getTradeAmount();
                pasLimits = pasLimits.add(tradeAmount);
            }
            BigDecimal preLimits = new BigDecimal(preLimit);
            BigDecimal subtract = preLimits.subtract(pasLimits);//剩余额度
            BigDecimal qur = pasLimits.divide(preLimits, 2, RoundingMode.HALF_UP);//额度使用率
            BigDecimal ten = new BigDecimal("100");
            BigDecimal multiply = qur.multiply(ten);
            tips.put("pasLimit", pasLimits);
            tips.put("remLimit", subtract);
            tips.put("limitRate", multiply);
        } else if ("4".equals(tradeType)) {
            List<Filter> filters = new Filter.Builder().eq("busType", businessType).eq("delflag", "1").build();
            boolean exists = traInterestService.exists(filters);
            if (!exists) {
                tips.put("pasLimit", "0");
                tips.put("remLimit", preLimit);
                tips.put("limitRate", "0");
                return tips;
            }
            BigDecimal pasLimits = new BigDecimal("0");//已用额度
            List<TraInterest> select = traInterestService.select(filters);
            for (TraInterest tra : select) {
                BigDecimal tradeAmount = tra.getTradeAmount();
                pasLimits = pasLimits.add(tradeAmount);
            }
            BigDecimal preLimits = new BigDecimal(preLimit);
            BigDecimal subtract = preLimits.subtract(pasLimits);//剩余额度
            BigDecimal qur = pasLimits.divide(preLimits, 2, RoundingMode.HALF_UP);//额度使用率
            BigDecimal ten = new BigDecimal("100");
            BigDecimal multiply = qur.multiply(ten);
            tips.put("pasLimit", pasLimits);
            tips.put("remLimit", subtract);
            tips.put("limitRate", multiply);
        } else if ("5".equals(tradeType)) {
            List<Filter> filters = new Filter.Builder().eq("busType", businessType).eq("delflag", "1").build();
            boolean exists = traServiceService.exists(filters);
            if (!exists) {
                tips.put("pasLimit", "0");
                tips.put("remLimit", preLimit);
                tips.put("limitRate", "0");
                return tips;
            }
            BigDecimal pasLimits = new BigDecimal("0");//已用额度
            List<TraService> select = traServiceService.select(filters);
            for (TraService tra : select) {
                BigDecimal tradeAmount = tra.getTradeAmount();
                pasLimits = pasLimits.add(tradeAmount);
            }
            BigDecimal preLimits = new BigDecimal(preLimit);
            BigDecimal subtract = preLimits.subtract(pasLimits);//剩余额度
            BigDecimal qur = pasLimits.divide(preLimits, 2, RoundingMode.HALF_UP);//额度使用率
            BigDecimal ten = new BigDecimal("100");
            BigDecimal multiply = qur.multiply(ten);
            tips.put("pasLimit", pasLimits);
            tips.put("remLimit", subtract);
            tips.put("limitRate", multiply);
        } else {
            tips.put("pasLimit", "0");
            tips.put("remLimit", preLimit);
            tips.put("limitRate", "0");
        }
        return tips;
    }

    /**
     * 预计额度数据反显
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/warnAboutPrelimit")
    @ResponseBody
    @Log(module = "预计额度列表", description = "预计额度数据反显")
    public Map<String, Object> warnAboutPrelimit(String proName, String preLimit) {
        Map<String, Object> tips = new HashMap<String, Object>();
        String[] split = proName.split("-");
        Integer paProId = Integer.parseInt(split[3]);
        Integer pid = Integer.parseInt(split[1]);
        String level = split[0];
        if ("1".equals(level)) {
            tips.put(WebCode.SUCCESS, true);
        } else if ("2".equals(level) || "3".equals(level)) {
            if (productService.get(paProId).getPreLimit() == null || "".equals(productService.get(paProId).getPreLimit())) {
                tips.put(WebCode.SUCCESS, true);
            } else {
                //jsp页签传过来的预计额度
                BigDecimal jspPreLimits = new BigDecimal(preLimit);
                //父级的预计额度
                String preLimit1 = productService.get(paProId).getPreLimit();
                BigDecimal paPreLimit = new BigDecimal(preLimit1);
                //计算子级节点下所有预计额度总和
                List<Filter> filters = new Filter.Builder()
                        .eq("paProId", paProId)
                        .eq("delflag", "1").build();
                filters.add(new Filter("preLimit", Operator.NNL, ""));
                List<Product> select = productService.select(filters);
                BigDecimal sonPreLimits = new BigDecimal("0");
                for (Product pro : select) {
                    BigDecimal sonPreLimit = new BigDecimal(pro.getPreLimit());
                    sonPreLimits.add(sonPreLimit);
                }
                BigDecimal subtract = paPreLimit.subtract(sonPreLimits);
                //判断输入值是否大于剩余预计额度，如果大于，则提示
                if (subtract.compareTo(jspPreLimits) == 1 || subtract.compareTo(jspPreLimits) == 0) {//如果输入小于或等于剩余预计额度，则继续
                    tips.put(WebCode.SUCCESS, true);
                } else {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, subtract);
                }


            }
        }
        return tips;
    }

    /**
     * 编辑预计额度告警提示
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/warnAboutPrelimits")
    @ResponseBody
    @Log(module = "预计额度列表", description = "编辑预计额度告警提示")
    public Map<String, Object> warnAboutPrelimits(Integer pid, String preLimit) {
        Map<String, Object> tips = new HashMap<String, Object>();

        Integer paProId = productService.get(pid).getPaProId();
        Product product = productService.get(paProId);
        String preLimit2 = product.getPreLimit();
        if (preLimit2 == null || "".equals(preLimit2) || "0".equals(preLimit2)) {
            tips.put(WebCode.SUCCESS, true);
        } else {
            //jsp页签传过来的预计额度
            BigDecimal jspPreLimits = new BigDecimal(preLimit);
            //父级的预计额度
            String preLimit1 = productService.get(paProId).getPreLimit();
            BigDecimal paPreLimit = new BigDecimal(preLimit1);
            //计算子级节点下所有预计额度总和
            List<Filter> filters = new Filter.Builder()
                    .eq("paProId", paProId)
                    .eq("delflag", "1").build();
            filters.add(new Filter("preLimit", Operator.NNL, ""));
            List<Product> select = productService.select(filters);
            BigDecimal sonPreLimits = new BigDecimal("0");
            for (Product pro : select) {
                BigDecimal sonPreLimit = new BigDecimal(pro.getPreLimit());
                sonPreLimits.add(sonPreLimit);
            }
            BigDecimal subtract = paPreLimit.subtract(sonPreLimits);
            //判断输入值是否大于剩余预计额度，如果大于，则提示
            if (subtract.compareTo(jspPreLimits) == 1 || subtract.compareTo(jspPreLimits) == 0) {//如果输入小于或等于剩余预计额度，则继续
                tips.put(WebCode.SUCCESS, true);
            } else {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, subtract);
            }
        }
        return tips;
    }


}

