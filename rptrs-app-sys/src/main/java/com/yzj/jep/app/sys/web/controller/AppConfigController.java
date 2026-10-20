package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.util.XssCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.TreeUtils;

/**
 * 应用配置
 *
 * @author ZhangYan
 * @time 2017年6月12日 上午11:07:22
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/appConfig")
public class AppConfigController extends CrudController<AppConfig> {

    Logger logger = LoggerFactory.getLogger(AppConfigController.class);

    @Autowired
    AppConfigService appConfigService;

    //============================系统级别配置============================

    /**
     * 系统级别配置页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/sysList")
    public String sysList(Model model, HttpServletRequest request) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("configLevel", "SYS"));

        List<AppConfig> result = appConfigService.select(filters);
        for (AppConfig appConfig : result) {
            model.addAttribute(appConfig.getConfigCode(), appConfig.getConfigValue());
        }
        return "sys/sysConfigEdit";
    }

    /**
     * 保存
     *
     * @param configs
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(String configs, HttpServletRequest request) {

        Map<String, Object> tips = new HashMap<String, Object>();
        logger.info(configs.toString());
        JSONArray cfgs = (JSONArray) JSON.parse(configs);
        List<AppConfig> list = new ArrayList<>();
        AppConfig config = null;
        for (int i = 0; i < cfgs.size(); i++) {
            JSONObject cfg = cfgs.getJSONObject(i);
            config = new AppConfig();
            config.setConfigCode(cfg.getString("name"));
            config.setConfigValue(cfg.getString("value"));
            list.add(config);
        }
        appConfigService.update(list);
        tips.put("success", true);

        return tips;
    }

    //============================应用级别配置============================

    /**
     * 应用配置页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/appList")
    public String appList(Model model, HttpServletRequest request) {
        return "sys/appConfigTree";
    }

    @RequestMapping(value = "/removeNode/{id}")
    @ResponseBody
    public Map<String, Object> removeNode(@PathVariable String id) {
        Map<String, Object> tips = new HashMap<String, Object>();
        long count = appConfigService.count(new Filter.Builder().eq("parentId", id).build());
        if (count > 0) {
            tips.put("success", false);
            tips.put("msg", "请先删除子节点。");
        } else {
            this.appConfigService.deleteByPrimaryKey(id);
            tips.put("success", true);
            tips.put("msg", "删除成功。");
        }
        return tips;
    }

    @RequestMapping(value = "/renameNode/{id}")
    @ResponseBody
    public Map<String, Object> renameNode(@PathVariable String configCode, String newName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            AppConfig appConfig = new AppConfig();
            appConfig.setConfigCode(configCode);
            appConfig.setConfigName(newName);
            appConfigService.update(appConfig, new String[]{"configName"});
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "重命名成功。");
        } catch (Exception ex) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "重命名失败。");
        }
        return tips;
    }

    @RequestMapping(value = "/updateNode")
    @ResponseBody
    public Map<String, Object> updateNode(AppConfig appConfig) {
        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(appConfig);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        try {
            AppConfig exist = appConfigService.get(appConfig.getConfigCode());
            appConfig.setConfigLevel("APP");
            if (exist == null) {
                appConfigService.insert(appConfig);
            } else {
                appConfigService.update(appConfig);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(Treeable.TREE_ID, appConfig.getConfigCode());
            tips.put(WebCode.MSG, "更新成功");
        } catch (Exception ex) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "新增类别失败。");
        }
        return tips;
    }


    @RequestMapping(value = "/treeNode")
    @ResponseBody
    public List<TreeNode> treeNode(String parentId, Model model, HttpServletRequest request) {
        List<Filter> filters = null;
        if (parentId == null) {
            filters = new Filter.Builder().nl("parentId").build();
        } else {
            filters = new Filter.Builder().eq("parentId", parentId).build();
        }
        filters.add(new Filter("configLevel", "APP"));
        List<AppConfig> list = appConfigService.select(filters);

        List<TreeNode> nodes = TreeUtils.parse(list, "parentId", "configCode", "configName", "inputType",
                "valueType", "configValue", "configDesc");
        return nodes;
    }

}
