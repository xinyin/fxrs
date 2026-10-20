package com.yzj.jep.app.bus.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.RelPsnHistory;
import com.yzj.jep.app.bus.service.RelPsnHistoryService;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.workflow.web.controller.ProcessController;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
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
import java.util.*;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;
import static com.yzj.jep.commons.StringUtils.isEmpty;

@Controller
@RequestMapping("/bus/relPsn/history")
public class RelPsnHistoryController extends CrudController<RelPsnHistory> {


    @Autowired
    private RelPsnHistoryService relPsnHistoryService;

    Logger logger = LoggerFactory.getLogger(ProcessController.class);

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }
        return "bus/relPsnHistoryList";
    }

    /**
     *  为列表页面做数据准备
     */

    @Override
    protected void prepareQuery(Page<RelPsnHistory> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        try {
            String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
            orders.add(Order.desc("createTime"));
            filters.add(new Filter("delflag", Operator.EQ, 1));
            relPsnHistoryService.query(page, filters, orders);
            List<RelPsnHistory> list = page.getResult();
            page.setResult(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/queryPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareQuery(Page<TraFunds> page, String createTime, String recordType, String recordNumber){
        Map<String, Object> result = new HashMap<>(2);
        List<RelPsnHistory> relPsnHistoryList = new ArrayList<>();
        PageHelper.start(page);
        // 时点查询列表数据
        if(StrUtil.isNotBlank(createTime)){
            relPsnHistoryList = relPsnHistoryService.queryRecordNumberData(createTime);
        }else {
            relPsnHistoryList = relPsnHistoryService.queryRecordNumber(recordType, recordNumber);
        }
        relPsnHistoryList.stream().forEach( e -> {
            if(e.getIdentifyType().equals("1")) {
                e.setIdentifyNumber(idEncrypt(e.getIdentifyNumber()));
            }else{
                e.setIdentifyNumber(otherIdEncrypt(e.getIdentifyNumber()));
            }
        });
        PageInfo pageInfo = new PageInfo(relPsnHistoryList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }


    /**
     *   跳转查看页面
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "自然人关联方历史", description = "自然人关联方历史查看")
    public String view(@PathVariable Integer pid,Model model) {
        RelPsnHistory rel = relPsnHistoryService.get(pid);
        //证件号码脱敏，身份证保留前6后3位，其余保留前3后2位
        if (rel.getIdentifyType().equals("1")) {
            rel.setIdentifyNumber(idEncrypt(rel.getIdentifyNumber()));
        }else{
            rel.setIdentifyNumber(otherIdEncrypt(rel.getIdentifyNumber()));
        }
        model.addAttribute("relPsnHistroy", rel);
        return "bus/relPsnHistoryView";
    }

    @RequestMapping(value = "/getRecordNumber")
    @ResponseBody
    public List<NameValuePair> getRecordNumber() {
        try {
            List<NameValuePair> items = new ArrayList<>();
            List<String> strings = relPsnHistoryService.recordNumber();
            for (String string : strings) {
                BasicNameValuePair nv = new BasicNameValuePair(string, string);
                items.add(nv);
            }
            return items;
        }catch (Exception e){
            logger.error("系统出现异常：",e);
            throw e;
        }
    }
}
