package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.DelRel;
import com.yzj.jep.app.bus.model.DelRelHistory;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.DelRelHistoryService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/delRelHistory")
public class DelRelHistoryController extends CrudController<DelRelHistory> {

    private static Logger logger = LoggerFactory.getLogger(DelRelHistoryController.class);

    @Autowired
    private DelRelHistoryService delRelHistoryService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/delRelHistoryList";
    }


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DelRelHistory> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

//        orders.add(Order.desc("modifiedTime"));
        delRelHistoryService.query(page, filters, orders);

        List<DelRelHistory> result = page.getResult();
        //获取法人或其他组织证件类型字典
        List<DictItem> orgDicItems = TraUtil.getDictItems("BUS-ORGIdentifyType");
        //获取自然人关联方证件类型字典
        List<DictItem> psnDicItems = TraUtil.getDictItems("BUS-IdentifyType");
        //根据关联方类型，查询证件类型
        for (DelRelHistory delRelHistory : result) {
            delRelHistory.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(delRelHistory.getIdentifyNumber()));
            //关联方类型为自然人
            if (delRelHistory.getType().equals("PSN")) {
                String identifyType = TraUtil.getIdentifyType(psnDicItems, delRelHistory.getIdentifyType());
                delRelHistory.setIdentifyType(identifyType);
            } else if (delRelHistory.getType().equals("ORG")) {
                //关联方类型为法人或其他组织
                String identifyType = TraUtil.getIdentifyType(orgDicItems, delRelHistory.getIdentifyType());
                delRelHistory.setIdentifyType(identifyType);
            }
        }
        page.setResult(result);
    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> query(Page<DelRelHistory> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        prepareQuery(page, filters, orders, request);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    /**
     * eye操作/查看
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        DelRelHistory delRelHistory = delRelHistoryService.get(pid);
        String type = delRelHistory.getType();
        Integer relPid = delRelHistory.getRelPid();
        if ("PSN".equalsIgnoreCase(type)) {
            RelPsn relPsn = relPsnService.get(relPid);
            if(null!=relPsn){
                relPsn.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
            }
            model.addAttribute("relPsn", relPsn);
            return "bus/delRelPsnView";
        } else {
            RelOrg relOrg = relOrgService.get(relPid);
            if(null!=relOrg){
                relOrg.setUniscCode(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getUniscCode()));
            }
            model.addAttribute("relOrg", relOrg);
            return "bus/delRelOrgView";
        }
    }


}
