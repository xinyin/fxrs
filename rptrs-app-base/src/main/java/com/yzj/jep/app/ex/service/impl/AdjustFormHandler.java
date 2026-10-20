package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

@FormHandler("ADJUST_LIMIT")
public class AdjustFormHandler implements ProcessFormHandler {
    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addObject("pid", json.getString("pid"));
        model.addObject("befPid", json.getString("befPid"));
        model.addObject("latPid", json.getString("latPid"));
        model.addObject("tranOrg", json.getString("tranOrg"));
        model.addObject("tradeType", json.getString("tradeType"));
        model.addObject("businessType", json.getString("businessType"));
        model.addObject("proName", json.getString("proName"));
        model.addObject("preLimit", json.getString("preLimit"));
        model.addObject("preLimitLater", json.getString("preLimitLater"));
        model.addObject("pasLimit", json.getString("pasLimit"));
        model.addObject("pasLimitLater", json.getString("pasLimitLater"));
        model.addObject("warnLeve", json.getString("warnLeve"));
        model.addObject("warnLeveLater", json.getString("warnLeveLater"));
        model.addObject("tranAmountL", json.getString("tranAmount"));
        model.addObject("tranOrgL", json.getString("tranOrgL"));
        model.addObject("tradeTypeL", json.getString("tradeTypeL"));
        model.addObject("businessTypeL", json.getString("businessTypeL"));
        model.addObject("proNameL", json.getString("proNameL"));
        model.addObject("preLimitL", json.getString("preLimitL"));
        model.addObject("preLimitLaterL", json.getString("preLimitLaterL"));
        model.addObject("pasLimitL", json.getString("pasLimitL"));
        model.addObject("pasLimitLaterL", json.getString("pasLimitLaterL"));
        model.addObject("warnLeveL", json.getString("warnLeveL"));
        model.addObject("warnLeveLaterL", json.getString("warnLeveLaterL"));
        model.addObject("seqTrade", json.getString("seqTrade"));
        String cognizaReason = json.getString("cognizaReason");
        if (StringUtils.isEmpty(cognizaReason)) {
            model.addObject("cognizaReason", "暂无意见");
        } else {
            model.addObject("cognizaReason", json.getString("cognizaReason").trim());
        }
        model.setViewName("ex/limitTransSubmit");

        return model;
    }
}
