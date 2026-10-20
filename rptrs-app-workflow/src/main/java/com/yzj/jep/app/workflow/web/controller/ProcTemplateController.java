/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月19日 08:37:26
 *
 */


package com.yzj.jep.app.workflow.web.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.entity.DeployResource;
import com.yzj.jep.workflow.api.entity.DeployResult;
import com.yzj.jep.workflow.api.model.IProcTemplate;
import com.yzj.jep.workflow.impl.builder.DeployResourceBuilder;
import com.yzj.jep.workflow.wf.model.ProcTemplate;

/**
 * 流程模板操作界面
 * @author yinke.hou
 * @time 2017年10月16日 下午4:50:55
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/procTemplate")
public class ProcTemplateController extends CrudController<ProcTemplate>{
	Logger logger = LoggerFactory.getLogger(ProcTemplateController.class);
	
	@Autowired 
	private WorkflowEngine 		workflowEngine;
	
    @Autowired
    RepositoryService 			repositoryService;
	
	
	/**
	 * 页面跳转到procTemplateList
	 * 
	 * @param model
	 * @return 列表显示页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "wf/procTemplateList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<ProcTemplate> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		workflowEngine.getRepositoryService().queryProcTemplate(page, filters, orders);
	}
	
	
	/**
	 *	新建流程图
	 * @param model
	 * @return 列表显示页面
	 */
	@RequestMapping(value = "/add")
	public String add(Model model) {
		
		return "wf/procTemplateAdd";
	}
	
	/**
	 * 上传流程图
	 * @param model
	 * @return 列表显示页面
	 */
	@RequestMapping(value = "/upload")
	public String upload(Model model) {
		
		return "wf/procTemplateUpload";
	}
	
	@RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> create(ProcTemplate procTemplate, HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	try 
    	{
    		
    		workflowEngine.getRepositoryService().insertProcTemplate(procTemplate);
    		result.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            logger.error("创建模型失败：", e);
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "创建模型失败。");
        }
    	
    	return result;
    }
	
	 /**
	  * 发布流程
	  * @param code
	  * @return
	  */
    @RequestMapping(value = "/deploy/{code}")
    @ResponseBody
    public Map<String, Object>  deploy(@PathVariable("code") String code) {
    	Map<String, Object> tips = new HashMap<String, Object>();
    	try 
    	{
    		DeployResult deployResult =  this.workflowEngine.getRepositoryService().deployProcTemplate(code);
    		//等于 0 流程部署失败
    		if(deployResult.getCode() == DeployResult.CODE_FAILED)
    		{
    			tips.put(WebCode.SUCCESS, false);
    			tips.put(WebCode.MSG, deployResult.getErrors().toString());	
    		}
    		else
    		{
    			tips.put(WebCode.SUCCESS, true);
    			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
    		}
        } 
    	catch (Exception e) 
    	{
            logger.error("根据模板部署流程失败：", code, e);
            tips.put(WebCode.SUCCESS, false);
    		tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        
        return tips;
    }

	
	
	/**
	 * 部署单个流程模板
	 * @param request
	 * @param response
	 * @param remark
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deploy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  deploy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> tips = new HashMap<String, Object>();
		try {
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest =
                        commonsMultipartResolver.resolveMultipart(request);
                /*获取备注属性*/
                String remark = multipartRequest.getParameter("remark");
                
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                if (fileMap.size() == 0) {
                    tips.put("res", false);
                    tips.put("msg", "部署失败,请上传文件。");
                    return tips;
                }
                /* 判断文件大小及类型 */
                for (Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                    MultipartFile file = entry.getValue();
                    BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
                    String fileName = file.getOriginalFilename();
                    
                    /* 这里使用数组缓存住流，方便后面作验证和部署 */
                    byte[] bytes = IoUtil.readInputStream(bis, fileName);
                    ByteArrayInputStream bts = new ByteArrayInputStream(bytes);
                    
                    /*先对上传的流程图进行验证*/
                    DeployResource resource = DeployResourceBuilder.newDeployResourceBuilder(fileName).remark(remark).resource(fileName, bts).build();
                    DeployResult result = workflowEngine.getRepositoryService().uploadProcTemplate(resource);
                    if(DeployResult.CODE_FAILED == result.getCode()){
                    	tips.put(WebCode.SUCCESS, false);
            			tips.put(WebCode.MSG, errorsAnalysis(result.getErrors()));
                    }
                    else
                    {
                    	tips.put(WebCode.SUCCESS, true);
            			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
                    }
                }
            }
			
		} catch (Exception e) {
			e.printStackTrace();
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	/**
	 * 把所有的错误信息进行拼接
	 * @param errors 错误信息
	 * @return
	 */
	private String errorsAnalysis(List<String> errors) {
		StringBuffer sb = new StringBuffer();
		for(String error : errors){
			sb.append(error).append("<br>");
		}
		return sb.toString();
	}
	
	/**
	 *  卸载单个主流程
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/undeploy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  undeploy(String code) throws Exception {
		Map<String, Object> tips = new HashMap<String, Object>();
		try {
			/*卸载模板*/
			workflowEngine.getRepositoryService().undeployProcTemplate(code);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		} catch (Exception e) {
			e.printStackTrace();
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	/**
	 *  启动流程模板
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  enable(String code) throws Exception {
		Map<String, Object> tips = new HashMap<String, Object>();
		try {
			/*启动模板*/
			workflowEngine.getRepositoryService().enableProcTemplate(code);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		} catch (Exception e) {
			e.printStackTrace();
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	
	/**
	 *  停用流程模板
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  disable(String code) throws Exception {
		Map<String, Object> tips = new HashMap<String, Object>();
		try {
			/*停用模板*/
			workflowEngine.getRepositoryService().disableProcTemplate(code);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		} catch (Exception e) {
			e.printStackTrace();
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
    /**
	 * 以特殊格式获取所有模板数据
	 * @return json格式返回
	 */
	@RequestMapping(value = "/selectAll")
	@ResponseBody
	public List<NameValuePair> selectAll(){
		
		List<? extends IProcTemplate> result = workflowEngine.getRepositoryService().selectProcTemplate();
		
		return NVUtils.convert(result, result.size(), "code", "name");
	}
	
	/**
	 * 验证提交的code是否已经存在数据库中
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = this.workflowEngine.getRepositoryService().existsProcTemplate(code);
		tips.put("valid", !exist);
		
		return tips;
	}
	
	/**
	 * 页面跳转到procTemplateEdit
	 * 
	 * @param model
	 * @return 列表显示页面
	 */
	@RequestMapping(value = "/test")
	public String test(Model model) {
		
		return "bizModuleTree2";
	}
	
}