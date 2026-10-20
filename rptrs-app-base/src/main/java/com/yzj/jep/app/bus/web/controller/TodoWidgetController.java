package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/TodoWidget")
public class TodoWidgetController extends CrudController {


    @Autowired
    private WorkflowEngine workflowEngine;

    /*未审批*/
    @RequestMapping(value = {"/todowidget"})
    @ResponseBody
    public List<Map> prepareQuery(HttpServletRequest request) {
        List<Map> resultList = new ArrayList<>();
        Map dMap = new HashMap();
        Map yMap = new HashMap();
        Page<Object> page = new Page<>();
        Page<Object> page1 = new Page<>();

        UserProfile user = UserProfileUtils.getUserProfile();
        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(user, staff);
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        workflowEngine.getRuntimeService().queryTaskInst(page, staff, filters, orders);

        dMap.put("value", page.getTotal());
        dMap.put("name", "待审批");
        dMap.put("url","workflow/approve/todoList");
        resultList.add(dMap);

        workflowEngine.getHistoryService().queryDoneProcInst(page1, user.getStaffCode(), filters, orders);

        yMap.put("value", page1.getTotal());
        yMap.put("name", "已审批");
        yMap.put("url","workflow/approve/doneList");
        resultList.add(yMap);
        return resultList;
    }

    /*  *//*已审批*//*
    @RequestMapping(value = {"/dowidget"})
    @ResponseBody
    public  List<Long>  queryHis(HttpServletRequest request)
    {

        Page<Object> page= new Page<>();
        List<Long> result = new ArrayList<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        UserProfile user = UserProfileUtils.getUserProfile();
        workflowEngine.getHistoryService().queryDoneProcInst(page, user.getStaffCode(), filters, orders);
        result.add( page.getTotal());
        return result;
    }
*/
}
