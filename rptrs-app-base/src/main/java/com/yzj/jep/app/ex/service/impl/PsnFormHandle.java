package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.fil.service.FilRelPsnService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 关联关系审批流程
 */
//@FormHandler("zjcc_Psn001")
@FormHandler("zyRel003")
public class PsnFormHandle implements ProcessFormHandler {
    RelService relService;
    FilRelPsnService filRelPsnService;

    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        filRelPsnService = SpringContextHolder.getBean(FilRelPsnService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = JSON.parseObject(data, Map.class);
        //取出pid拼成的字符串
        String pids = (String) map.get("pids");
        /**以下为原来的代码*/
//        Rel rel = JSON.parseObject(data, Rel.class);
//        List<Filter> filters = new Filter.Builder().eq("flagPid", json.getString("pid")).build();
        /*List<FilRelPsn> list = filRelPsnService.select(filters);
        if (list.size() >= 1) {
            FilRelPsn filRelPsn = list.get(0);
            RelPsn relPsnL = new RelPsn();
            BeanUtils.copyProperties(filRelPsn, relPsnL);
            //给证件号码加密
            relPsnL.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsnL.getIdentifyNumber()));
            relPsnL.setMobile(new ConfidentialInfoUtil().hideAllPhoneNum(relPsnL.getMobile()));
            JSONObject relPsn1 = (JSONObject) JSON.toJSON(relPsn);
            JSONObject relPsn2 = (JSONObject) JSON.toJSON(relPsnL);
            Map result = JsonUtil.compareJson(relPsn1, relPsn2, null);
            model.addObject("relPsn", relPsn);
            model.addObject("relPsn2", result);
            model.addObject("opt", "view");
            model.setViewName("ex/psnForm");
        } else {*/
//            JSONObject relPsn1 = (JSONObject) JSON.toJSON(rel);
//            JSONObject relPsn2 = (JSONObject) JSON.toJSON(rel);
//            Map result = JsonUtil.compareJson(relPsn1, relPsn2, null);
//            model.addObject("rel", rel);
//            model.addObject("opt", "view");
//            model.setViewName("ex/relForm");
//        }

        String[] strings = pids.split(",");
        if (strings.length < 2) {
            //单条数据跳转到form展示表单
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter("pid", Operator.IN, strings));
            List<Rel> list = relService.select(filters);
            model.addObject("pid", pids);
            model.addObject("rel", list.get(0));
            model.addObject("opt", "view");
            model.setViewName("ex/relForm");
        } else {
            //展示table表格
            model.addObject("pids", pids);
            model.addObject("opt", "view");
            model.setViewName("ex/relTableForm");
        }
        return model;
    }
}
