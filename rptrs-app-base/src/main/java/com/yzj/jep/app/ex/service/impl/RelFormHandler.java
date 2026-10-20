package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

//流程编码


@FormHandler("YZJ_REL")
public class RelFormHandler implements ProcessFormHandler {


    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {


        JSONObject json = JSONObject.parseObject(data);
			/*model.addObject("uninId",(json.getString("startDate")+json.getString("endDate")+json.getString("mobile")+json.getString("hours")).replace(" ", ""));
			model.addObject("flag","1");*/
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            if ("PSN".equals(json.getString("type"))) {
                RelPsn relPsn = JSON.parseObject(data, RelPsn.class);
                model.addObject("relPsn", relPsn);
                if ("0".equals(relPsn.getIsBlack())) {
                    model.addObject("addOrRemoveBlack", "加入黑名单原因");
                } else {
                    model.addObject("addOrRemoveBlack", "移出黑名单原因");
                }
                model.addObject("relPsn", relPsn);
                model.addObject("blackReason", json.getString("blackReason"));
                model.addObject("opt", "edit");
                //不展示内容
                model.addObject("op", 1);
                model.setViewName("ex/relPsnBlack");
            }
            if ("ORG".equals(json.getString("type"))) {
                RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
                RelOrg relOrg = new RelOrg();
                relOrg = relOrgService.get(json.getInteger("pid"));
                if ("0".equals(relOrg.getIsBlack())) {
                    model.addObject("addOrRemoveBlack", "加入黑名单原因");
                } else {
                    model.addObject("addOrRemoveBlack", "移出黑名单原因");
                }
                model.addObject("relOrg", relOrg);
                model.addObject("blackReason", json.getString("blackReason"));
                model.addObject("op", 1);
                model.setViewName("ex/relOrgBlack");
            }
        }
        if (ClientCodes.OPT_EDIT.equals(opt)) {
            if ("PSN".equals(json.getString("type"))) {
                RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
                RelPsn relPsn = new RelPsn();
                relPsn = relPsnService.get(json.getInteger("pid"));
                if ("0".equals(relPsn.getIsBlack())) {
                    model.addObject("addOrRemoveBlack", "加入黑名单原因");
                } else {
                    model.addObject("addOrRemoveBlack", "移出黑名单原因");
                }
                model.addObject("relPsn", relPsn);
                model.addObject("blackReason", json.getString("blackReason"));
                model.setViewName("ex/relPsnBlack");
            }
            if ("ORG".equals(json.getString("type"))) {
                RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
                RelOrg relOrg = new RelOrg();
                relOrg = relOrgService.get(json.getInteger("pid"));
                if ("0".equals(relOrg.getIsBlack())) {
                    model.addObject("addOrRemoveBlack", "加入黑名单原因");
                } else {
                    model.addObject("addOrRemoveBlack", "移出黑名单原因");
                }
                model.addObject("relOrg", relOrg);
                model.addObject("blackReason", json.getString("blackReason"));
                model.setViewName("ex/relOrgBlack");
            }
        }

        return model;
    }
}
