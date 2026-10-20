package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.core.code.PermitType;
import com.yzj.jep.core.code.Status;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.code.DashLayoutDesignType;
import com.yzj.jep.sys.dash.widget.Widget;
import com.yzj.jep.sys.dash.widget.WidgetFactory;
import com.yzj.jep.sys.model.DashLayout;
import com.yzj.jep.sys.model.DashLayoutConfig;
import com.yzj.jep.sys.model.DashWidget;
import com.yzj.jep.sys.model.Position;
import com.yzj.jep.sys.service.DashLayoutConfigService;
import com.yzj.jep.sys.service.DashLayoutMapService;
import com.yzj.jep.sys.service.DashLayoutService;
import com.yzj.jep.sys.service.DashWidgetDakService;
import com.yzj.jep.sys.service.DashWidgetService;
import com.yzj.jep.sys.service.PositionService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

/**
 * 布局设计
 *
 * @author xupan
 */
@Controller
@RequestMapping("/sys/dashLayoutDesign")
public class DashLayoutDesignController {
    private static Logger logger = LoggerFactory.getLogger(DashLayoutDesignController.class);

    @Autowired
    private DashWidgetService dashWidgetService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DashWidgetDakService dashWidgetDakService;

    @Autowired
    private DashLayoutConfigService dashLayoutConfigService;

    @Autowired
    private DashLayoutMapService dashLayoutMapService;

    @Autowired
    private DashLayoutService dashLayoutService;

    /**
     * 进入布局设计页面
     *
     * @return
     */
    @RequestMapping(value = "/design")
    public String list(String title, String layoutCode, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("layoutCode", layoutCode);
        return "sys/dashLayoutDesign";
    }

    /**
     * 布局设计页面，下拉框显示启用的组件
     *
     * @return
     */
    @RequestMapping("/getAllSelectWidget")
    @ResponseBody
    public List<NameValuePair> getAllSelectWidget() {
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("status", Status.ENABLED.getValue()));
        List<DashWidget> dashWidgets = dashWidgetService.select(filters);
        return NVUtils.convert(dashWidgets, dashWidgets.size(), "code", "name");
    }

    /**
     * 请求经过解析的组件内容,html代码，该方法应该处理组件的数据权限
     *
     * @param widgetCode
     * @return
     */
    @RequestMapping(value = "/getWidgetContent/{widgetCode}")
    @ResponseBody
    public Map<String, Object> getWidgetContent(@PathVariable String widgetCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        //封装查询的角色编码和角色组编码
        List<String> dakUsingCodes = new ArrayList<String>();
        try {
            //获取当前登录用户
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            //获取当前用户所属的角色,角色组
            List<Position> positions = positionService.getAllPositionByStaffCode(userProfile.getStaffCode());
            for (Position position : positions) {
                dakUsingCodes.add(position.getRoleCode());
                dakUsingCodes.add(position.getRoleGroupCode());
            }
            //获取当前用户最大权限
            PermitType permitType = dashWidgetDakService.queryMaxDashWidgetDak(dakUsingCodes, widgetCode);
            Widget widget = WidgetFactory.createWidget(widgetCode, permitType);
            String content = widget.content();
            result.put("content", content);
            result.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            result.put(WebCode.SUCCESS, false);
        }
        return result;
    }

    /**
     * 保存布局设计页面的数据
     *
     * @param dashLayoutConfig
     * @return
     */
    @RequestMapping(value = "/saveLayoutDesignData")
    @ResponseBody
    public Map<String, Object> saveLayoutDesignData(DashLayoutConfig dashLayoutConfig) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String result = dashLayoutConfigService.saveDashLayoutConfig(dashLayoutConfig);
            //如果需要在不关闭窗口的情况下多次更新，则需要向页面回写version，打开下面的代码
            int version = dashLayoutConfigService.getVersion(dashLayoutConfig);
            if (WebCode.OUT_OF_DATE.equals(result)) {
                resultMap.put(WebCode.SUCCESS, false);
                resultMap.put(WebCode.MSG, WebCode.FAILURE_OUT_OF_DATE);
            } else {
                resultMap.put(WebCode.SUCCESS, true);
                resultMap.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                //如果需要在不关闭窗口的情况下多次更新，则需要向页面回写version，打开下面的代码
                resultMap.put("version", version);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            resultMap.put(WebCode.SUCCESS, false);
            resultMap.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return resultMap;
    }

    /**
     * 首页获取布局的配置信息
     *
     * @param layoutCode
     * @return
     */
    @RequestMapping(value = "/getHomePageLayoutConfig")
    @ResponseBody
    public Map<String, Object> getHomePageLayoutConfig(Model model) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            //获取当前登录用户最优先的布局
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            DashLayoutConfig layoutConfig = dashLayoutMapService.getLayoutConfig(userProfile.getStaffCode());
            if (layoutConfig != null) {
                DashLayout dashLayout = dashLayoutService.get(layoutConfig.getLayoutCode());
                if (DashLayoutDesignType.NORMAL.getValue().equals(dashLayout.getDesignType())) {
                    JSONObject json = null;
                    json = JSONObject.parseObject(layoutConfig.getConfig());
                    data.put("config", json);
                } else {
                    data.put("config", layoutConfig.getConfig());
                }

                data.put("version", layoutConfig.getVersion());
                data.put("designType", dashLayout.getDesignType());
                data.put(WebCode.SUCCESS, true);
            }
            data.put(WebCode.SUCCESS, false);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            data.put(WebCode.SUCCESS, false);
        }
        return data;
    }
}