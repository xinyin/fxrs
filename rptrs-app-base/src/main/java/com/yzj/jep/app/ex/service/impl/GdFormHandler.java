package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.GdExportFileService;
import com.yzj.jep.app.bus.service.GdTraPfService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import io.swagger.models.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

//流程编码


@FormHandler("gd")
public class GdFormHandler implements ProcessFormHandler {


    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {
        JSONObject json = JSONObject.parseObject(data);
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            String type = json.getString("type");
            if ("open".equals(type)) {
                String gdType = json.getString("gdType");
                if ("PSN".equals(gdType)) {
                    RelPsn relPsn = JSON.parseObject(data, RelPsn.class);
                    model.addObject("relPsn", relPsn);
                    model.setViewName("ex/psnOpenSubmit");
                } else {
                    RelOrg relOrg = JSON.parseObject(data, RelOrg.class);
                    model.addObject("relOrg", relOrg);
                    model.setViewName("ex/orgOpenSubmit");
                }
            }
            if ("xh".equals(type) || "black".equals(type) || "dj".equals(type) || "jd".equals(type)) {
                String relType = json.getString("relType");
                String reason = json.getString("reason");
                model.addObject("reason", reason);
                if ("PSN".equals(relType)) {
                    RelPsn relPsn = JSON.parseObject(data, RelPsn.class);
                    model.addObject("relPsn", relPsn);
                    model.setViewName("ex/psnGdSubmit");
                } else {
                    RelOrg relOrg = JSON.parseObject(data, RelOrg.class);
                    model.addObject("relOrg", relOrg);
                    model.setViewName("ex/orgGdSubmit");
                }
            }
            if ("zrTra".equals(type)) {
                zrTra(model, data);
            }
            if ("jcTra".equals(type)) {
                jcTra(model, data);
            }
            if ("giftTra".equals(type)) {
                giftTra(model, data);
            }
            if ("djTra".equals(type)) {
                djTra(model, data);
            }
            if ("jdTra".equals(type)) {
                String frozenAmount = json.getString("frozenAmount");
                jdTra(model, data, frozenAmount);
            }
            if ("zyTra".equals(type)) {
                zyTra(model, data);
            }
            if ("jyTra".equals(type)) {
                String pledgedAmount = json.getString("pledgedAmount");
                jyTra(model, data, pledgedAmount);
            }
            if ("pfTra".equals(type)) {
                JSONArray pidArray = json.getJSONArray("pid");
                Integer pid = pidArray.getInteger(0);
                GdTraPfService gdTraPfService = SpringContextHolder.getBean(GdTraPfService.class);
                GdTraPf gdTraPf = gdTraPfService.get(pid);
                model.addObject("gdTraPf", gdTraPf);
                model.setViewName("ex/gdTraPfSubmit");
            }
            if ("export".equals(type)) {
                JSONArray pidArray = json.getJSONArray("pid");
                Integer pid = pidArray.getInteger(0);
                model.addObject("pid", pid);
                model.setViewName("ex/gdExportFileSubmit");
            }
        }
        return model;
    }

    private void jyTra(ModelAndView model, String data, String pledgedAmount) {
        GdTraZy gdTraZy = JSON.parseObject(data, GdTraZy.class);
        model.addObject("gdTraZy", gdTraZy);
        model.addObject("pledgedAmount", pledgedAmount);
        model.setViewName("ex/gdTraJySubmit");
    }

    private void zyTra(ModelAndView model, String data) {
        GdTraZy gdTraZy = JSON.parseObject(data, GdTraZy.class);
        model.addObject("gdTraZy", gdTraZy);
        model.setViewName("ex/gdTraZySubmit");
    }

    private void jdTra(ModelAndView model, String data, String frozenAmount) {
        GdTraDj gdTraDj = JSON.parseObject(data, GdTraDj.class);
        model.addObject("gdTraDj", gdTraDj);
        model.setViewName("ex/gdTraJdSubmit");
    }

    private void djTra(ModelAndView model, String data) {
        GdTraDj gdTraDj = JSON.parseObject(data, GdTraDj.class);
        model.addObject("gdTraDj", gdTraDj);
        model.setViewName("ex/gdTraDjSubmit");
    }

    private void zrTra(ModelAndView model, String data) {
        GdTraZr gdTraZr = JSON.parseObject(data, GdTraZr.class);
        model.addObject("gdTraZr", gdTraZr);
        model.setViewName("ex/gdTraZrSubmit");
    }

    private void jcTra(ModelAndView model, String data) {
        GdTraZr gdTraZr = JSON.parseObject(data, GdTraZr.class);
        model.addObject("gdTraZr", gdTraZr);
        model.setViewName("ex/gdTraJcSubmit");
    }

    private void giftTra(ModelAndView model, String data) {
        GdTraZr gdTraZr = JSON.parseObject(data, GdTraZr.class);
        model.addObject("gdTraZr", gdTraZr);
        model.setViewName("ex/gdTraGiftSubmit");
    }

}
