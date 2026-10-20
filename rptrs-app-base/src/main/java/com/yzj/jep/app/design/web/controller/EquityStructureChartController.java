package com.yzj.jep.app.design.web.controller;

import com.yzj.jep.app.design.model.RootNode;
import com.yzj.jep.app.design.service.EquityStructureChartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author duanliqiang
 */
@Controller
@RequestMapping("/design/equityFigure")
public class EquityStructureChartController {

    @Resource
    public EquityStructureChartService equityStructureChartService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */


    @RequestMapping(value = "/selectJson")
    @ResponseBody
    public RootNode selectJson(String relId, String relType) {
        return equityStructureChartService.selectBypid(relId, relType);

    }

    @RequestMapping(value = "/listNew")
    public String listNew(Model model, HttpServletRequest request) {
        String relName = request.getParameter("relName");
        String relId = request.getParameter("relId");
        String relType = request.getParameter("relType");
        String identifyNumber = request.getParameter("identifyNumber");
        model.addAttribute("relName", relName);
        model.addAttribute("relId", relId);
        model.addAttribute("relType", relType);
        model.addAttribute("identifyNumber", identifyNumber);
        return "bus/TreeGeneratorNew";
    }


    @RequestMapping(value = "/selectAllJson")
    @ResponseBody
    public RootNode selectAllJson(String relId, String relType) {
        return equityStructureChartService.selectAllJson(relId, relType);
    }


}
