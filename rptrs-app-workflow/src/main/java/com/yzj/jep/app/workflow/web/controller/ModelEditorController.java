package com.yzj.jep.app.workflow.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 流程图编辑页面跳转
 * 说明: 由于流程图编辑页面使用的是html， 当中获取资源直接是上级路径， 所以需要单独写一个方法来实现
 * @author yinke.hou
 * @time 2017年10月16日 下午4:49:49
 * @company 银之杰技术有限公司
 */
@Controller
public class ModelEditorController {
	
	/**
	 * 编辑跳转
	 * @param model
	 * @param modelId
	 * @return
	 */
    @RequestMapping(value = "/modeler")
    public String modeler(Model model, String modelId){
    	model.addAttribute("modelId", modelId);
    	return "wf/modeler";
    }
	
}
