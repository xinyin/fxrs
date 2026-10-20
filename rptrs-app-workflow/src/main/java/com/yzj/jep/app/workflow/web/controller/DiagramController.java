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
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.model.IProcNode;
import com.yzj.jep.workflow.api.service.IDiagramService;
import com.yzj.jep.workflow.api.service.IRepositoryService;
import com.yzj.jep.workflow.wf.model.ProcTemplate;

@Controller
@RequestMapping("/workflow/diagram")
public class DiagramController extends CrudController<ProcTemplate>{
	Logger logger = LoggerFactory.getLogger(DiagramController.class);
	
	@Autowired
	IDiagramService diagramService;
	
	@Autowired
	IRepositoryService repositoryService;
	
	
	/**
	 * 获取模板的流程图
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/image/{code}")
	public void image(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
		
		InputStream in = diagramService.getProcDiagram(code);
		response.setContentType("image/png");
		
		BufferedInputStream bis = null;
        BufferedOutputStream bos = null; 
        try {
			bis = new BufferedInputStream(in);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while ((bytesRead = bis.read(buff, 0, buff.length)) != -1){
			bos.write(buff, 0, bytesRead);
			}
			bos.flush();
        } catch (Exception e) {  
        	e.printStackTrace();
        } finally {  
			try {
				if (bis != null) {
                    bis.close();
                }
				if (bos != null) {
                    bos.close();
                }
			} catch (IOException e) {
				logger.error("关闭IO流缓冲失败：" + e.getMessage());
			}  
        }
	}
	
	
	/**
	 * 获取模板的流程图
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/pointNodeImage")
	public void pointNodeImage(String code, String nodeKey, String nodeType,
			HttpServletRequest request, HttpServletResponse response) {
		
		InputStream in = diagramService.getProcDiagram(code, nodeKey, nodeType);
		response.setContentType("image/png");
		
		BufferedInputStream bis = null;
        BufferedOutputStream bos = null; 
        try {
			bis = new BufferedInputStream(in);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while ((bytesRead = bis.read(buff, 0, buff.length)) != -1){
			bos.write(buff, 0, bytesRead);
			}
			bos.flush();
        } catch (Exception e) {  
        	e.printStackTrace();
        } finally {  
			try {
				if (bis != null) {
                    bis.close();
                }
				if (bos != null) {
                    bos.close();
                }
			} catch (IOException e) {
				logger.error("关闭IO流缓冲失败：" + e.getMessage());
			}  
        }
	}
	
	@RequestMapping(value = "/nodes/{code}", method = RequestMethod.POST)
	@ResponseBody
	public List<? extends IProcNode> nodes(@PathVariable String code) {
		return repositoryService.selectProcNodes(code);
	}
	
	/**
	 * 获取模板的流程图
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exedFlowImage/{procInstId}")
	public void exedFlowImage(@PathVariable String procInstId, HttpServletRequest request, HttpServletResponse response) {
		
		InputStream in = diagramService.getExecutedProcDiagram(procInstId);
		response.setContentType("image/png");
		
		BufferedInputStream bis = null;
        BufferedOutputStream bos = null; 
        try {
			bis = new BufferedInputStream(in);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while ((bytesRead = bis.read(buff, 0, buff.length)) != -1){
			bos.write(buff, 0, bytesRead);
			}
			bos.flush();
        } catch (Exception e) {  
        	e.printStackTrace();
        } finally {  
			try {
				if (bis != null) {
                    bis.close();
                }
				if (bos != null) {
                    bos.close();
                }
			} catch (IOException e) {
				logger.error("关闭IO流缓冲失败：" + e.getMessage());
			}  
        }
	}
	
}