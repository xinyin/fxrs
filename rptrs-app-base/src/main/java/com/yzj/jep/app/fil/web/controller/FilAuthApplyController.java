package com.yzj.jep.app.fil.web.controller;

import com.yzj.jep.app.fil.model.FilAuthApply;
import com.yzj.jep.app.fil.service.FilAuthApplyService;
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
 * @title: FilAuthApply
 * @Author Whx
 * @Date: 2021/8/13 8:59
 * @Version 1.0
 */
@Controller
@RequestMapping("/ins/FilAuthApply")
public class FilAuthApplyController extends CrudController<FilAuthApply> {

    @Autowired
    private FilAuthApplyService filAuthApplyService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilAuthApply> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("pid", OrderType.DESC));
        //列表按pid排序 因为备案管理系统没有修改数据路径
        filAuthApplyService.query(page, filters, orders);
    }
}
