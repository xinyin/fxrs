package com.yzj.jep.app.bus.web.controller;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.DlyInformation;
import com.yzj.jep.app.bus.service.DlyInformationService;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/bus/dlyformation")
public class DlyInformationController  extends CrudController<DlyInformation> {
    private static Logger logger = LoggerFactory.getLogger(DlyInformationController.class);

    @Autowired
    private DlyInformationService dlyInformationService;
    @Autowired
    private UnitService unitService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "msgDeployList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    @Log(module = "信息发布管理", description = "信息发布列表数据")
    protected void prepareQuery(Page<DlyInformation> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        dlyInformationService.query(page, filters, orders);
    }

    @Override
    @Log(module = "信息发布管理", description = "信息发布机构关联方列表数据")
    public Map<String, Object> query(Page<DlyInformation> page, Model model, HttpServletRequest request) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        // prepareQuery(page, filters, orders, request);
        DlyInformation dlyInformation = new DlyInformation();
        PageHelper.start(page);
        List<DlyInformation> dlyInformationList = dlyInformationService.selectList(dlyInformation);
        PageInfo pageInfo = new PageInfo(dlyInformationList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

}
