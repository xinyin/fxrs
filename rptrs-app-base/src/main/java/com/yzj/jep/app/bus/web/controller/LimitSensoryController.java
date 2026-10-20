/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月03日 11:00:21
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.Tenant;
import com.yzj.jep.sys.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.LimitSensory;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.LimitSensoryService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/limitSensory")
public class LimitSensoryController extends CrudController<LimitSensory> {
    private static Logger logger = LoggerFactory.getLogger(LimitSensoryController.class);

    @Autowired
    private LimitSensoryService limitSensoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TenantService tenantService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "阈值管理", description = "跳转到阈值管理")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("tranOrg", "");
        } else {
            model.addAttribute("tranOrg", tenantId);
        }
        return "bus/limitSensoryList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<LimitSensory> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        limitSensoryService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    @Log(module = "阈值管理", description = "阈值管理新增")
    public String add(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("tendId", "");
        } else {
            model.addAttribute("tendId", tenantId);
        }
        return "bus/limitSensoryAdd";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    @Log(module = "阈值管理", description = "阈值管理编辑")
    public String edit(@PathVariable Integer pid, Model model) {
        LimitSensory limitSensory = limitSensoryService.get(pid);
        model.addAttribute("limitSensory", limitSensory);
        model.addAttribute("opt", "edit");
        return "bus/limitSensoryEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "阈值管理", description = "阈值管理查看")
    public String view(@PathVariable Integer pid, Model model) {
        LimitSensory limitSensory = limitSensoryService.get(pid);
        model.addAttribute("limitSensory", limitSensory);
        model.addAttribute("opt", "view");
        return "bus/limitSensoryEdit";
    }

    /**
     * 保存
     *
     * @param limitSensory
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Log(module = "阈值管理", description = "阈值管理保存")
    public Map<String, Object> save(LimitSensory limitSensory) {
        Map<String, Object> tips = new HashMap<String, Object>();
        limitSensory.setDelflag("1");
        limitSensory.setIsActive("1");
        //获取创建人id和name
        UserProfile profile = UserProfileUtils.getUserProfile();
        String staffCode = profile.getStaffCode();
        String staffName = profile.getStaffName();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            limitSensory.setTenantId("");
        } else {
            limitSensory.setTenantId(tenantId);
        }
        limitSensory.setModifiedById(staffCode);
        limitSensory.setModifiedByName(staffName);
        Boolean isExist = limitSensoryService.exists(limitSensory.getPid());
        try {
            if (isExist) {
                limitSensoryService.update(limitSensory);
            } else {
                String busLeveNameL = limitSensory.getBusLeveName();
                String[] split = busLeveNameL.split("-");
                String level = split[0];
                if ("1".equals(level)) {
                    limitSensory.setBusLeveName(null);
                } else {
                    String busLeveName = split[2];
                    limitSensory.setBusLeveName(busLeveName);
                }
                String pidL = split[1];
                List<Filter> filters = new Filter.Builder().eq("busLeve", pidL).build();
                boolean exists = limitSensoryService.exists(filters);
                if (exists) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "该级别的阈值已设置！");
                    return tips;
                }

                limitSensory.setBusLeve(pidL);
                limitSensoryService.insert(limitSensory);
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
    @Log(module = "阈值管理", description = "阈值管理删除")
    public String remove(@PathVariable Integer pid, Model model) {

        try {
            LimitSensory limitSensory = limitSensoryService.get(pid);
            limitSensory.setBusLeve("");
            limitSensory.setDelflag("0");
            limitSensoryService.update(limitSensory);
            model.addAttribute(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            model.addAttribute(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return "bus/limitSensoryList";
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
        boolean exist = limitSensoryService.exists(pid);
        tips.put("valid", !exist);
        return tips;
    }

    /**
     * 根据产品树反显
     *
     * @param unitCodes
     * @return
     */

    @RequestMapping("/selectbuinessL")
    @ResponseBody
    @Log(module = "阈值管理", description = "根据产品树反显")

    public Map<String, String> selectDept(String unitCodes) {
        System.out.println(unitCodes);
        Map<String, String> map = new HashMap<String, String>();
        String[] split = unitCodes.split("-");
        String level = split[0];
        Integer pid = Integer.parseInt(split[1]);
        String proName = split[2];
        Product product = productService.get(pid);
        String preLimit = product.getPreLimit();
        Integer limitRate = product.getLimitRate();
        if (preLimit == null || "".equals(preLimit)) {
            preLimit = "0";
        }
        if (limitRate == null) {
            limitRate = 0;
        }
        map.put("preLimit", preLimit);
        map.put("limitRate", limitRate.toString());
        if ("1".equals(level)) {
            map.put("tradesType", productService.get(pid).getPaProId().toString());
            map.put("businessType", pid.toString());

        } else if ("2".equals(level)) {
            map.put("tradesType", productService.get(productService.get(pid).getPaProId()).getPaProId().toString());
            map.put("businessType", productService.get(pid).getPaProId().toString());
        }
        return map;
    }
}