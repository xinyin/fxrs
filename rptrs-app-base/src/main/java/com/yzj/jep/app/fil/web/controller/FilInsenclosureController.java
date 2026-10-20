/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月31日 15:57:15
 *
 */

package com.yzj.jep.app.fil.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.fil.model.FilInsenclosure;
import com.yzj.jep.app.fil.service.FilInsenclosureService;
import com.yzj.jep.app.ins.model.TraInsenclosure;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ins/filInsenclosure")
public class FilInsenclosureController extends CrudController<FilInsenclosure> {
    private static Logger logger = LoggerFactory.getLogger(FilInsenclosureController.class);

    @Autowired
    private FilInsenclosureService filInsenclosureService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "ins/filInsenclosureList";
    }

    @RequestMapping(value = "queryList")
    @ResponseBody
    public Map<String, Object> query(Page<FilInsenclosure> page, Model model, HttpServletRequest request, String transNum) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        filters.add(new Filter("transNum", Operator.EQ, transNum));
        prepareQuery(page, filters, orders, request);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilInsenclosure> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filInsenclosureService.query(page, filters, orders);
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

        FilInsenclosure filInsenclosure = filInsenclosureService.get(pid);
        model.addAttribute("filInsenclosure", filInsenclosure);

        return "ins/filInsenclosureView";
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
            filInsenclosureService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

}