/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月20日 09:44:17
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
import com.yzj.jep.sys.model.BizDeptStaff;
import com.yzj.jep.sys.service.BizDeptStaffService;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/bizDeptStaff")
public class BizDeptStaffController extends CrudController<BizDeptStaff> {
    Logger logger = LoggerFactory.getLogger(BizDeptStaffController.class);

    @Autowired
    private BizDeptStaffService bizDeptStaffService;


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<BizDeptStaff> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        bizDeptStaffService.query(page, filters, orders);
    }

}