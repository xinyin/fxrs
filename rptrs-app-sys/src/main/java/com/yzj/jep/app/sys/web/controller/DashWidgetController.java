/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年11月17日 14:25:59
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.code.PermitType;
import com.yzj.jep.core.code.Status;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.DashWidgetType;
import com.yzj.jep.sys.dash.provider.DataProvider;
import com.yzj.jep.sys.dash.provider.DataProviderFactory;
import com.yzj.jep.sys.dash.provider.service.IDataProviderService;
import com.yzj.jep.sys.dash.provider.spring.SpringDataProviderFactoryBean;
import com.yzj.jep.sys.dash.widget.WidgetFactory;
import com.yzj.jep.sys.dash.widget.configable.chart.ChartRenderer;
import com.yzj.jep.sys.dash.widget.configable.chart.ChartRendererFactory;
import com.yzj.jep.sys.model.DashWidget;
import com.yzj.jep.sys.model.DashWidgetConfig;
import com.yzj.jep.sys.model.DashWidgetProvider;
import com.yzj.jep.sys.service.DashWidgetConfigService;
import com.yzj.jep.sys.service.DashWidgetDakService;
import com.yzj.jep.sys.service.DashWidgetProviderService;
import com.yzj.jep.sys.service.DashWidgetService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/dashWidget")
public class DashWidgetController extends CrudController<DashWidget> {
    private static Logger logger = LoggerFactory.getLogger(DashWidgetController.class);

    @Autowired
    private DashWidgetService dashWidgetService;

    @Autowired
    private DashWidgetProviderService dashWidgetProviderService;

    @Autowired
    private DashWidgetConfigService dashWidgetConfigService;

    @Autowired
    private DashWidgetDakService dashWidgetDakService;

    @Autowired
    private SpringDataProviderFactoryBean factoryBean;

    @Autowired
    private IDataProviderService dataProviderService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dashWidgetList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DashWidget> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dashWidgetService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/dashWidgetEdit";
    }

    /**
     * 编辑页面
     *
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{code}")
    public String edit(@PathVariable String code, Model model) {

        DashWidget dashWidget = dashWidgetService.get(code);
        model.addAttribute("dashWidget", dashWidget);
        model.addAttribute("opt", "edit");

        return "sys/dashWidgetEdit";
    }

    /**
     * 查看页面
     *
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{code}")
    public String view(@PathVariable String code, Model model) {

        DashWidget dashWidget = dashWidgetService.get(code);
        dashWidget.setType(DashWidgetType.getLabel(dashWidget.getType()));
        dashWidget.setStatus(Status.getLabel(dashWidget.getStatus()));
        model.addAttribute("dashWidget", dashWidget);

        return "sys/dashWidgetView";
    }

    /**
     * 保存
     *
     * @param dashWidget
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DashWidget dashWidget) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = dashWidgetService.exists(dashWidget.getCode());
        try {
            if (isExist) {

                dashWidgetService.updateSelective(dashWidget);
            } else {
                dashWidgetService.saveWidget(dashWidget);
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
     * 删除组件及其配置
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/remove/{code}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String code) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            dashWidgetService.deleteWidget(code);
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
     * 验证提交的code是否已经存在数据库中
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String code) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = dashWidgetService.exists(code);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 判断能否启用组件
     *
     * @param widgetCode
     * @return
     */
    @RequestMapping(value = "/canEnable")
    @ResponseBody
    public Map<String, Object> canEnable(String code) {
        Map<String, Object> tips = new HashMap<String, Object>();

        DashWidgetConfig dashWidgetConfig = dashWidgetConfigService.get(code);
        String widgetConfig = dashWidgetConfig.getConfig();

        /*当配置数据存在的话，进行进一步初始化，若无异常，则能启用*/
        if (!StringUtils.isEmpty(widgetConfig)) {
            try {
                /*获取当前用户在该组件上的权限*/
                UserProfile user = UserProfileUtils.getUserProfile();
                PermitType permitType = dashWidgetDakService.queryMaxDashWidgetDak(user.getStaffCode(), code);

                WidgetFactory.createWidget(code, permitType);
                tips.put(WebCode.SUCCESS, true);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                tips.put(WebCode.SUCCESS, false);
            }
        } else {
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }

    /**
     * 启用
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/enable")
    @ResponseBody
    public Map<String, Object> enable(String code) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            DashWidget dashWidget = dashWidgetService.get(code);
            dashWidget.setStatus(Status.ENABLED.getValue());
            dashWidgetService.update(dashWidget);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_ENABLE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_ENABLE);
        }

        return tips;
    }

    /**
     * 停用
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public Map<String, Object> disable(String code) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            DashWidget dashWidget = dashWidgetService.get(code);
            dashWidget.setStatus(Status.DISABLED.getValue());
            dashWidgetService.update(dashWidget);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DISABLE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DISABLE);
        }

        return tips;
    }

    /**
     * 组件设计页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/design")
    public String design(String code, Model model) {
        String page = "";

        DashWidget dashWidget = dashWidgetService.get(code);
        model.addAttribute("dashWidget", dashWidget);

        if (DashWidgetType.CHART.getValue().equals(dashWidget.getType())) {
            page = "sys/dashWidgetDesignChart";

            /*获取图表组件数据绑定程序*/
            List<DashWidgetProvider> dashWidgetProviders = dashWidgetProviderService.select(new Filter.Builder().eq("widgetCode", code).build());
            if (CollectionUtils.isNotEmpty(dashWidgetProviders)) {
                model.addAttribute("dashWidgetProvider", dashWidgetProviders.get(0));
            }
        } else if (DashWidgetType.DATA.getValue().equals(dashWidget.getType())) {
            page = "sys/dashWidgetDesignData";
        }

        return page;
    }

    /**
     * 初始化数据提供器
     *
     * @param code 组件编码
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/initProvider")
    @ResponseBody
    public Map<String, Object> initProvider(Page page, String code) {
        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*获取图表组件数据绑定程序*/
            List<DashWidgetProvider> dashWidgetProviders = dashWidgetProviderService.select(new Filter.Builder().eq("widgetCode", code).build());
            if (CollectionUtils.isNotEmpty(dashWidgetProviders)) {
                /*获取组件配置*/
                DashWidgetConfig dashWidgetConfig = dashWidgetConfigService.get(code);
                tips.put("widgetConfig", dashWidgetConfig.getConfig());
                tips.put("version", dashWidgetConfig.getVersion());

                /*获取当前用户在该组件上的权限*/
                UserProfile user = UserProfileUtils.getUserProfile();
                PermitType permitType = dashWidgetDakService.queryMaxDashWidgetDak(user.getStaffCode(), code);

                /*初始化provider*/
                DashWidgetProvider dashWidgetProvider = dashWidgetProviders.get(0);
                String id = dashWidgetProvider.getProviderCode();
                String params = dashWidgetProvider.getProviderParams();

                try {
                    DataProviderFactory dataProviderFactory = factoryBean.getObject();
                    DataProvider provider = dataProviderFactory.createDataProvider(id, page, params, permitType);
                    tips.put("columns", provider.getDescriptor().getColumns());
                    tips.put("sql", provider.getSql());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, e.getMessage());
        }

        return tips;
    }

    @RequestMapping(value = "/initChartOptions")
    @ResponseBody
    public Map<String, Object> initChartOptions(Model model, String widgetCode, String chartType, String config) {
        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            /*获取图表组件数据绑定程序*/
            List<DashWidgetProvider> dashWidgetProviders = dashWidgetProviderService.select(new Filter.Builder().eq("widgetCode", widgetCode).build());

            if (dashWidgetProviders.size() > 0) {
                /*初始化provider*/
                DashWidgetProvider dashWidgetProvider = dashWidgetProviders.get(0);
                DataProviderFactory dataProviderFactory = factoryBean.getObject();
                DataProvider provider = dataProviderFactory.createDataProvider(dashWidgetProvider.getProviderCode(), dashWidgetProvider.getProviderParams());

                //图表渲染
                ChartRenderer renderer = ChartRendererFactory.createChartRenderer(chartType);
                String chartOptions = renderer.render(provider, config);
                tips.put("chartOptions", chartOptions);
                tips.put(WebCode.SUCCESS, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }

    /**
     * 表达式页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/expr")
    public String expr(Model model) {
        return "sys/dashWidgetExpr";
    }

    @RequestMapping("/dashWidgetHyperlink")
    public String dashWidgetHyperlink(Model model) {
        return "sys/dashWidgetHyperlink";
    }

    /**
     * 数据提供程序表分页查询
     *
     * @param page
     * @param sql
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getDataByPage")
    @ResponseBody
    public Map<String, Object> getDataByPage(Page page, String sql, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = dataProviderService.query(sql, page);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, list);

        return result;
    }

}