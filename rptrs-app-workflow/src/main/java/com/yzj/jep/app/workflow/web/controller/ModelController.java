package com.yzj.jep.app.workflow.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.wf.model.ReModel;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程模型控制器
 * @author Kevin
 */
@Controller
@RequestMapping(value = "/workflow/model")
public class ModelController extends CrudController<ReModel> implements ModelDataJsonConstants{

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    RepositoryService 			repositoryService;
    
	@Autowired 
	private WorkflowEngine 		workflowEngine;

    /**
     * 编辑
     * @param id
     * @return
     */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, org.springframework.ui.Model model){
		
		model.addAttribute("modelId", id);
		return "redirect:/modeler";
	}
	
	
	  
	/**
	 * 编辑流程保存
	 * @param modelId
	 * @param values
	 */
	@RequestMapping(value="/{modelId}/save", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
		try 
		{
			this.workflowEngine.getRepositoryService().saveModel(modelId, values);
		} catch (Exception e) {
			logger.error("Error saving model", e);
			throw new ActivitiException("Error saving model", e);
		}
	}
	
    /**
     * 根据Model部署流程
     */
    @RequestMapping(value = "/deploy/{id}")
    @ResponseBody
    public String deploy(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            Model modelData = repositoryService.getModel(id);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
            redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", id, e);
        }
        return "redirect:/workflow/model/list";
    }

    /**
     * 导出model对象为指定类型
     *
     * @param modelId 模型ID
     * @param type    导出文件类型(bpmn\json)
     */
    @RequestMapping(value = "export/{modelId}/{type}")
    public void export(@PathVariable("modelId") String modelId,
                       @PathVariable("type") String type,
                       HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export for type: " + type);
                response.flushBuffer();
                return;
            }

            String filename = "";
            byte[] exportBytes = null;

            String mainProcessId = bpmnModel.getMainProcess().getId();

            if ("bpmn".equals(type)) {

                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                exportBytes = xmlConverter.convertToXML(bpmnModel);

                filename = mainProcessId + ".bpmn20.xml";
            } else if ("json".equals(type)) {

                exportBytes = modelEditorSource;
                filename = mainProcessId + ".json";

            }

            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
        }
    }

	@RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("id") String id) {
    	Map<String, Object> tips = new HashMap<String, Object>();
    	repositoryService.deleteModel(id);
        tips.put(WebCode.SUCCESS, true);
		tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
		return tips;
    }

}
