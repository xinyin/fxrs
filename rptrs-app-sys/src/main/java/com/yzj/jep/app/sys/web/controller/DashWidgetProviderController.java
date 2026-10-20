/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年11月20日 16:16:48
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.code.YesNo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.dash.provider.DataProvider;
import com.yzj.jep.sys.dash.provider.DataProviderFactory;
import com.yzj.jep.sys.dash.provider.spring.SpringDataProviderFactoryBean;
import com.yzj.jep.sys.dash.widget.utils.mvel.MVELUtils;
import com.yzj.jep.sys.model.DashWidget;
import com.yzj.jep.sys.model.DashWidgetProvider;
import com.yzj.jep.sys.service.DashWidgetProviderService;
import com.yzj.jep.sys.service.DashWidgetService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/sys/dashWidgetProvider")
public class DashWidgetProviderController extends CrudController<DashWidgetProvider> {
    private static Logger logger = LoggerFactory.getLogger(DashWidgetProviderController.class);

    @Autowired
    private DashWidgetProviderService dashWidgetProviderService;

    @Autowired
    private DashWidgetService dashWidgetService;//组件的service

    @Autowired
    private SpringDataProviderFactoryBean factoryBean;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dashWidgetProviderList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DashWidgetProvider> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dashWidgetProviderService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/dashWidgetProviderEdit";
    }

    /**
     * 编辑页面
     *
     * @param provierId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{provierId}")
    public String edit(@PathVariable String provierId, Model model) {

        DashWidgetProvider dashWidgetProvider = dashWidgetProviderService.get(provierId);
        model.addAttribute("dashWidgetProvider", dashWidgetProvider);
        model.addAttribute("opt", "edit");

        return "sys/dashWidgetProviderEdit";
    }

    /**
     * 查看页面
     *
     * @param provierId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{provierId}")
    public String view(@PathVariable String provierId, Model model) {

        DashWidgetProvider dashWidgetProvider = dashWidgetProviderService.get(provierId);
        model.addAttribute("dashWidgetProvider", dashWidgetProvider);

        return "sys/dashWidgetProviderView";
    }

    /**
     * 保存
     *
     * @param dashWidgetProvider
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DashWidgetProvider dashWidgetProvider) {

        //公用字段统一处理
//		UserProfile userProfile = UserProfileUtils.getUserProfile();
//		dashWidgetProvider.setCreateById(userProfile.getUserid());
//		dashWidgetProvider.setCreateByName(userProfile.getStaffName());
//		dashWidgetProvider.setCreateTime(new Date());
//		dashWidgetProvider.setDelflag("N");

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = dashWidgetProviderService.exists(dashWidgetProvider.getProvierId());
        try {
            if (isExist) {

                dashWidgetProviderService.update(dashWidgetProvider);
            } else {
                dashWidgetProvider.setBeenUsed(YesNo.NO.getValue());
                dashWidgetProviderService.insert(dashWidgetProvider);
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
     * @param provierId
     * @return
     */
    @RequestMapping(value = "/remove/{provierId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String provierId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            dashWidgetProviderService.deleteByPrimaryKey(provierId);
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
     * 验证提交的provierId是否已经存在数据库中
     *
     * @param provierId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String provierId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = dashWidgetProviderService.exists(provierId);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 进入绑定数据提供程序页面
     *
     * @param title         页面的标题
     * @param componentCode 组件编码
     * @return
     */
    @RequestMapping(value = "/bingDataProviderProgram")
    public String bingDataProviderProgram(String title, String componentCode, Model model) {
        DashWidget dashWidget = dashWidgetService.get(componentCode);
        model.addAttribute("dashWidget", dashWidget);
        return "sys/dashWidgetProviderEdit";
    }

    /**
     * 验证组件是否绑定数据程序
     *
     * @param widgetCode
     * @return
     */
    @RequestMapping(value = "/verifyForDashWidgetProvider")
    @ResponseBody
    public Map<String, Object> verifyForDashWidgetProvider(String widgetCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        long count = dashWidgetProviderService.count(new Filter.Builder().eq("widgetCode", widgetCode).build());
        if (count > 0) {
            tips.put(WebCode.SUCCESS, true);
        } else {
            tips.put(WebCode.SUCCESS, false);
        }

        return tips;
    }

    /**
     * 获得组件的列数据
     *
     * @param widgetProvierId
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getDataProviderByWidgetProvierId")
    @ResponseBody
    public Map<String, Object> getDataProviderByWidgetProvierId(String widgetProvierId) {
        Map<String, Object> data = new HashMap<String, Object>();
        DashWidgetProvider dashWidgetProvider = dashWidgetProviderService.get(widgetProvierId);
        Page page = new Page(1, 10);

        try {
            DataProviderFactory dataProviderFactory = factoryBean.getObject();
            DataProvider provider = dataProviderFactory.createDataProvider(dashWidgetProvider.getProviderCode(), page,
                    dashWidgetProvider.getProviderParams(), null);
            data.put("columns", provider.getDescriptor().getColumns());
            data.put("name", provider.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * 验证表达式结果
     *
     * @param expr
     * @param widgetProvierId
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/verifyExpr")
    @ResponseBody
    public Map<String, Object> verifyExpr(String expr, String widgetProvierId) {
        Map<String, Object> data = new HashMap<String, Object>();
        DashWidgetProvider dashWidgetProvider = dashWidgetProviderService.get(widgetProvierId);
        Page page = new Page(1, 10);

//		DataProvider3 dataProvider = DataProviderFactory2.createDataProvider(provider.getProviderCode(), 
//				provider.getProviderParams(),DataProvider3.MODE_DESIGN, page,PermitType.UNIT_ALL);

        DataProvider provider = null;
        try {
            DataProviderFactory dataProviderFactory = factoryBean.getObject();
            provider = dataProviderFactory.createDataProvider(dashWidgetProvider.getProviderCode(), page,
                    dashWidgetProvider.getProviderParams(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> dataRows = provider.getData();
        Map<String, Object> params = new HashMap<String, Object>();

        /*如果数据不为空的话，就直接取出第一条数据*/
        if (!CollectionUtils.isEmpty(dataRows)) {
            params.put("dataRow", dataRows.get(0));
        }

        try {
            MVELUtils.eval(expr, params);
            data.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
        }
        return data;
    }

    /**
     * 根据组件编码，获取该组件的数据提供程序
     *
     * @param widgetCode
     * @return
     */
    @RequestMapping("/getWidgetProvidersByWidgetCode")
    @ResponseBody
    public List<NameValuePair> getWidgetProvidersByWidgetCode(String widgetCode) {
        DashWidgetProvider widgetProvider = new DashWidgetProvider();
        widgetProvider.setWidgetCode(widgetCode);
        List<DashWidgetProvider> list = dashWidgetProviderService.select(widgetProvider);
        return NVUtils.convert(list, list.size(), "provierId", "providerName");
    }


}