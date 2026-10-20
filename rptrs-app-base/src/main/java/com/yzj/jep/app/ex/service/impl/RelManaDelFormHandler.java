package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@FormHandler("YZJ_REL_MANA_DEL")
public class RelManaDelFormHandler implements ProcessFormHandler {
    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ClientCodes.OPT_VIEW.equals(opt)) {

            model.addObject("toRelCategory", json.getString("toRelCategory"));
            model.addObject("shareholdingRatio", json.getString("shareholdingRatio"));
            //关联方的pid
            model.addObject("pid", json.getString("toPid"));
            //自身类型
            model.addObject("toType", json.getString("toType"));

            String cognizaReason = json.getString("cognizaReason");
            if (StringUtils.isEmpty(cognizaReason)) {
                model.addObject("cognizaReason", "暂无意见");
            } else {
                model.addObject("cognizaReason", json.getString("cognizaReason").trim());
            }
            model.addObject("shareholdersCode", json.getString("shareholdersCode"));
            model.addObject("shareholdingStrand", json.getString("shareholdingStrand"));
            model.addObject("identifyType", json.getString("identifyType"));
            model.addObject("identifyNumber", json.getString("identifyNumber"));
            model.addObject("name", json.getString("name"));

            model.setViewName("ex/relManaDel");
        }

        return model;
    }
}
