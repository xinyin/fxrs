/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月19日 16:05:09
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.BizDeptType;
import com.yzj.jep.sys.service.BizDeptTypeService;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/bizDeptType")
public class BizDeptTypeController extends CrudController<BizDeptType> {
    Logger logger = LoggerFactory.getLogger(BizDeptTypeController.class);

    @Autowired
    private BizDeptTypeService bizDeptTypeService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<BizDeptType> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        bizDeptTypeService.query(page, filters, orders);
    }

}