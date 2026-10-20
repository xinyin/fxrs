package com.yzj.jep.app.fil.web.controller;

import com.yzj.jep.app.fil.model.FilEntrustApply;
import com.yzj.jep.app.fil.service.FilEntrustApplyService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.controller.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @title: FilEntrustApplyController
 * @Author Whx
 * @Date: 2021/8/13 9:55
 * @Version 1.0
 */
@Controller
@RequestMapping("/ins/FilEntrustApply")
public class FilEntrustApplyController extends CrudController<FilEntrustApply> {

    @Autowired
    private FilEntrustApplyService filEntrustApplyService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilEntrustApply> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("pid", OrderType.DESC));
        //列表按pid排序 因为备案管理系统没有修改数据路径
        filEntrustApplyService.query(page, filters, orders);
    }
}
