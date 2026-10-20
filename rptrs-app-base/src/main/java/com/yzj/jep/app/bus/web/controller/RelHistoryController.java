package com.yzj.jep.app.bus.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelHistoryService;
import com.yzj.jep.app.bus.service.RelOrgHistoryService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.workflow.web.controller.ProcessController;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
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
import java.util.*;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;
import static com.yzj.jep.commons.StringUtils.isEmpty;

@Controller
@RequestMapping("/bus/rel/history")
public class RelHistoryController extends CrudController<RelHistory> {


    @Autowired
    private RelHistoryService relHistoryService;
    @Autowired
    private RelPsnService relPsnService;

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
        return "bus/relHistoryList";
    }

    /**
     *  为列表页面做数据准备
     */

    @Override
    protected void prepareQuery(Page<RelHistory> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        try {
            String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
            orders.add(Order.desc("createTime"));
            filters.add(new Filter("delflag", Operator.EQ, 1));
          //  filters.add(new Filter("mgerOrg", Operator.EQ, unitCode));
            relHistoryService.query(page, filters, orders);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *   跳转查看页面
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "自然人关联方历史", description = "自然人关联方历史查看")
    public String view(@PathVariable Integer pid, Model model) {
        RelHistory rel = relHistoryService.get(pid);
        //本层关联方为自然人，自然人证件号码脱敏
        if("PSN".equals(rel.getFromType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(rel.getFromIdentifyNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getFromIdentifyNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        rel.setFromIdentifyNumber(idEncrypt(rel.getFromIdentifyNumber()));
                    }else{
                        rel.setFromIdentifyNumber(otherIdEncrypt(rel.getFromIdentifyNumber()));
                    }
                }
            }
        }
        //前一层关联方为自然人，自然人证件号码脱敏
        if("PSN".equals(rel.getToType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(rel.getToIdentifyNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getToIdentifyNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        rel.setToIdentifyNumber(idEncrypt(rel.getToIdentifyNumber()));
                    }else{
                        rel.setToIdentifyNumber(otherIdEncrypt(rel.getToIdentifyNumber()));
                    }
                }
            }
        }
        model.addAttribute("rel", rel);
        return "bus/relHistoryView";
    }

    @RequestMapping(value = "/getRecordNumber")
    @ResponseBody
    public List<NameValuePair> getRecordNumber() {
        try {
            List<NameValuePair> items = new ArrayList<>();
            List<String> strings = relHistoryService.recordNumber();
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

    @RequestMapping(value = "/queryList", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> queryList(Page<RelHistory> page, String createTime, String recordType, String recordNumber){
        Map<String, Object> result = new HashMap<>(2);
        List<RelHistory> relHistories = new ArrayList<>();
        PageHelper.start(page);
        // 时点查询列表数据
        if(StrUtil.isNotBlank(createTime)){
            relHistories =relHistoryService.queryRecordNumberData(createTime);
        }else {
            relHistories = relHistoryService.queryRecordNumber(recordType, recordNumber);
        }

        relHistories.stream().forEach( e -> {
            //本层关联方为自然人，自然人证件号码脱敏
            if("PSN".equals(e.getFromType())){
                //查询关联方证件类型
                if(StringUtils.isNotEmpty(e.getFromIdentifyNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(e.getFromIdentifyNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            e.setFromIdentifyNumber(idEncrypt(e.getFromIdentifyNumber()));
                        }else{
                            e.setFromIdentifyNumber(otherIdEncrypt(e.getFromIdentifyNumber()));
                        }
                    }
                }
            }
            //前一层关联方为自然人，自然人证件号码脱敏
            if("PSN".equals(e.getToType())){
                //查询关联方证件类型
                if(StringUtils.isNotEmpty(e.getToIdentifyNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(e.getToIdentifyNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            e.setToIdentifyNumber(idEncrypt(e.getToIdentifyNumber()));
                        }else{
                            e.setToIdentifyNumber(otherIdEncrypt(e.getToIdentifyNumber()));
                        }
                    }
                }
            }
        });
        PageInfo pageInfo = new PageInfo(relHistories);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     *   跳转查看页面
     */
    @RequestMapping(value = "/allBatchImport/{pid}")
    @Log(module = "自然人关联方历史", description = "自然人关联方历史查看")
    public String allBatchImport(@PathVariable String pid, Model model) {

        return "bus/relHistoryView";
    }



}
