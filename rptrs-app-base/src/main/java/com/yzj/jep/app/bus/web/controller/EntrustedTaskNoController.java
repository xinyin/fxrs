package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.EntrustApply;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.wf.model.ProcAutoTransfer;
import com.yzj.jep.workflow.wf.service.ProcAutoTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pat/taskNumber")
public class EntrustedTaskNoController extends CrudController<ProcAutoTransfer> {


    @Autowired
    private ProcAutoTransferService procAutoTransferService;
    /**
     * 获取列表页面
     * @return 列表页面
     */

    @RequestMapping(value = "/list")
    public String provideServicesSend(Model model) {
        return "bus/taskNumberSelect";

    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<ProcAutoTransfer> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        List<String> stringList = new ArrayList<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        stringList.add("0");
        stringList.add("1");
        filters.add(new Filter("transferStaffCode", Operator.EQ,  profile.getStaffCode()));
        filters.add(new Filter("status", Operator.IN,stringList));
        procAutoTransferService.query(page, filters, orders);
    }
}
