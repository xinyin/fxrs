package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.fil.service.FilRelOrgService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@FormHandler("zjcc_ORG001")
public class OrgFormHandler implements ProcessFormHandler {
    FilRelOrgService filRelOrgService;

    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        filRelOrgService = SpringContextHolder.getBean(FilRelOrgService.class);
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Rel rel = JSON.parseObject(data, Rel.class);
        //relOrg.setLegalIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getLegalIdentifyNumber()));
        //拿到data数据里面的pid判断备案表里面是否有备案数据,若有,拿到最近 的一条去和原数据表里的数据去做比对
        List<Filter> filters = new Filter.Builder().eq("flagPid", json.getString("pid")).build();
//        List<FilRelOrg> list = filRelOrgService.select(filters);
//        if (list.size() >= 1) {
//            FilRelOrg filRelOrg = list.get(0);
//            RelOrg relOrgL = new RelOrg();
//            BeanUtils.copyProperties(filRelOrg, relOrgL);
//            //将证件号码隐藏
////            relOrgL.setUniscCode(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getUniscCode()));   //机构编号
//            relOrgL.setLegalIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getLegalIdentifyNumber()));   //法人编号
//            //实体类转json
//            JSONObject relPsn1 = (JSONObject) JSON.toJSON(relOrg);
//            JSONObject relPsn2 = (JSONObject) JSON.toJSON(relOrgL);
//            Map result = JsonUtil.compareJson(relPsn1, relPsn2, null);
//            model.addObject("relOrg", relOrg);
//            model.addObject("relOrg2", result);
//            model.addObject("opt", "view");
//            model.setViewName("ex/orgForm");
//        } else {
//        Map result = new HashMap<>();
//        model.addObject("relOrg2", result);
        model.addObject("rel", rel);
        model.addObject("opt", "view");
        model.setViewName("ex/relForm");
//        }
        return model;
    }
}