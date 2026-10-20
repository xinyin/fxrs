/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2018 yzj.com. All rights reserved.
 *
 * Generated at 2018年01月11日 16:25:26
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.MetaQueryStatus;
import com.yzj.jep.sys.code.MetaQueryType;
import com.yzj.jep.sys.model.DataProvider;
import com.yzj.jep.sys.service.DataProviderService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/sys/dataProvider")
public class DataProviderController extends CrudController<DataProvider> {
    private static Logger logger = LoggerFactory.getLogger(DataProviderController.class);

    @Autowired
    private DataProviderService dataProviderService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dataProviderList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DataProvider> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        dataProviderService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/sync")
    public String sync(Model model) {
        return "sys/dataProviderSync";
    }

    /**
     * 编辑页面
     *
     * @param queryId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{queryId}")
    public String edit(@PathVariable String queryId, Model model) {

        DataProvider dataProvider = dataProviderService.get(queryId);
        model.addAttribute("dataProvider", dataProvider);
        model.addAttribute("opt", "edit");

        return "sys/dataProviderEdit";
    }

    /**
     * 查看页面
     *
     * @param queryId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{queryId}")
    public String view(@PathVariable String queryId, Model model) {

        DataProvider dataProvider = dataProviderService.get(queryId);
        dataProvider.setQueryType(MetaQueryType.getLabel(dataProvider.getQueryType()));
        dataProvider.setStatus(MetaQueryStatus.getLabel(dataProvider.getStatus()));
        model.addAttribute("dataProvider", dataProvider);
        return "sys/dataProviderView";
    }

    /**
     * 保存
     *
     * @param dataProvider
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(String dataProviderJson) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            List<DataProvider> data = JSON.parseArray(dataProviderJson, DataProvider.class);
            dataProviderService.saveDataProvider(data);
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
     * @param queryId
     * @return
     */
    @RequestMapping(value = "/remove/{queryId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String queryId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            dataProviderService.deleteByPrimaryKey(queryId);
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
     * 获取所有的数据提供程序
     *
     * @return
     */
    @RequestMapping("/getDataProvider")
    @ResponseBody
    public List<NameValuePair> getDataProvider() {
        List<DataProvider> list = dataProviderService.selectAll();
        return NVUtils.convert(list, list.size(), "queryId", "queryName");
    }

}