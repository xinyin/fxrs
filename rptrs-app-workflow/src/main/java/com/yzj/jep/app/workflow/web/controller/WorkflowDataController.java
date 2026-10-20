package com.yzj.jep.app.workflow.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.core.security.token.Token;
import com.yzj.jep.core.security.token.TokenService;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UserService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.api.code.ClientErrorCode;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IHisProcInst;
import com.yzj.jep.workflow.api.model.IHisProcLog;
import com.yzj.jep.workflow.api.model.IProcDef;
import com.yzj.jep.workflow.api.model.IProcInst;
import com.yzj.jep.workflow.api.model.IProcNode;
import com.yzj.jep.workflow.api.model.IStaff;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.wf.model.ProcInst;

/**
 * 工作流对外提供的接口
 *
 * @author liuy
 */
@Controller
@RequestMapping("/workflow/workflowData")
public class WorkflowDataController extends CrudController<ProcInst> {
    Logger logger = LoggerFactory.getLogger(WorkflowDataController.class);

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/getApprovingData")
    @ResponseBody
    public Map<String, Object> getApprovingData(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String procDefCode = (String) t.getProperty(ClientCodes.OPS_PROC_CODE);
            String nodeKey = (String) t.getProperty(ClientCodes.OPS_NODE_KEY);
            Integer pageNo = (Integer) t.getProperty(ClientCodes.OPS_PAGE_NO);
            Integer pageSize = (Integer) t.getProperty(ClientCodes.OPS_PAGE_SIZE);

            JSONObject json = new JSONObject();
            if (pageNo != null && pageSize != null && pageNo > 0 && pageSize > 0) {
                Page page = new Page();
                page.setPageNumber(pageNo);
                page.setPageSize(pageSize);
                Staff staff = staffService.get(userName);
                StaffImpl staffImpl = new StaffImpl();
                BeanUtils.copyProperties(staff, staffImpl);
                workflowEngine.getRuntimeService().queryTaskInst(page, staffImpl, procDefCode, nodeKey);
                json.put(ClientCodes.OPS_ROWS, page.getResult());
                json.put(ClientCodes.OPS_TOTAL, page.getTotal());
            } else {
                Staff staff = staffService.get(userName);
                StaffImpl staffImpl = new StaffImpl();
                BeanUtils.copyProperties(staff, staffImpl);
                List<? extends ITaskInst> list = workflowEngine.getRuntimeService().queryTaskInst(staffImpl, procDefCode, nodeKey);

                json.put(ClientCodes.OPS_ROWS, list);
                json.put(ClientCodes.OPS_TOTAL, list.size());
            }

            data.put(ClientCodes.OPS_RESULT_CODE, 0);
            data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
            data.put(ClientCodes.OPS_RESULT_DATA, json);

        } else {
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, "用户名和密码不匹配，请检查。");
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/getApprovedData")
    @ResponseBody
    public Map<String, Object> getApprovedData(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String procDefCode = (String) t.getProperty(ClientCodes.OPS_PROC_CODE);
            String nodeKey = (String) t.getProperty(ClientCodes.OPS_NODE_KEY);
            Integer pageNo = (Integer) t.getProperty(ClientCodes.OPS_PAGE_NO);
            Integer pageSize = (Integer) t.getProperty(ClientCodes.OPS_PAGE_SIZE);

            JSONObject json = new JSONObject();
            if (pageNo != null && pageSize != null && pageNo > 0 && pageSize > 0) {
                Page page = new Page();
                page.setPageNumber(pageNo);
                page.setPageSize(pageSize);
                workflowEngine.getHistoryService().queryDoneProcInst(page, userName, procDefCode, nodeKey);
                json.put(ClientCodes.OPS_ROWS, page.getResult());
                json.put(ClientCodes.OPS_TOTAL, page.getTotal());
            } else {
                List<? extends IHisProcInst> list = workflowEngine.getHistoryService().selectDoneProcInst(userName, procDefCode, nodeKey);
                json.put(ClientCodes.OPS_ROWS, list);
                json.put(ClientCodes.OPS_TOTAL, list.size());
            }
            data.put(ClientCodes.OPS_RESULT_CODE, 0);
            data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
            data.put(ClientCodes.OPS_RESULT_DATA, json);

        } else {
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, "用户名和密码不匹配，请检查。");
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/getApproveLog")
    @ResponseBody
    public Map<String, Object> getApproveLog(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String procInstId = t.getId();
            JSONObject json = new JSONObject();
            List<? extends IHisProcLog> logs = workflowEngine.getLogService().selectHistoryProcLog(procInstId);
            json.put(ClientCodes.OPS_ROWS, logs);
            json.put(ClientCodes.OPS_TOTAL, logs.size());
            data.put(ClientCodes.OPS_RESULT_CODE, 0);
            data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
            data.put(ClientCodes.OPS_RESULT_DATA, json);

        } else {
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, "用户名和密码不匹配，请检查。");
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     * @throws IOException
     */
    @RequestMapping(value = "/getProcInstDiagram")
    @ResponseBody
    public Map<String, Object> getProcInstDiagram(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String procInstId = t.getId();
            /*判断是否存在对应的流程实例*/
            boolean isExists = workflowEngine.getHistoryService().existsHistory(procInstId);
            if (isExists) {
                try {
                    /*获得当前业务流程*/
                    InputStream in = workflowEngine.getDiagramService().getExecutedProcDiagram(procInstId);
                    data.put(ClientCodes.OPS_RESULT_CODE, 0);
                    data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
                    data.put(ClientCodes.OPS_RESULT_DATA, Base64.encodeBase64String(IOUtils.toByteArray(in)));
                } catch (IOException e) {
                    e.printStackTrace();
                    data.put(ClientCodes.OPS_RESULT_CODE, -1);
                    data.put(ClientCodes.OPS_RESULT_MSG, "IOException：" + e.getMessage());
                }
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例" + ClientErrorCode.getLabel(4));
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/getProcTemplateDiagram")
    @ResponseBody
    public Map<String, Object> getProcTemplateDiagram(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String procCode = t.getId();
            /*判断是否存在对应的流程实例*/
            /*获得当前业务流程*/
            IProcDef procdef = workflowEngine.getProcessService().getLastProcDef(procCode);
            if (procdef == null) {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程定义" + ClientErrorCode.getLabel(4));
            } else {
                try {
                    /*获得当前业务流程*/
                    InputStream in = workflowEngine.getDiagramService().getProcDiagram(procCode);
                    data.put(ClientCodes.OPS_RESULT_CODE, 0);
                    data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
                    data.put(ClientCodes.OPS_RESULT_DATA, Base64.encodeBase64String(IOUtils.toByteArray(in)));
                } catch (IOException e) {
                    e.printStackTrace();
                    data.put(ClientCodes.OPS_RESULT_CODE, -1);
                    data.put(ClientCodes.OPS_RESULT_MSG, "IOException：" + e.getMessage());
                }
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/getProcInstData")
    @ResponseBody
    public Map<String, Object> getProcInstData(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String procInstId = t.getId();
            /*判断是否存在对应的流程实例*/
            IProcInst procInst = workflowEngine.getRuntimeService().getProcInst(procInstId);
            if (procInst != null) {
                /*获得当前业务流程*/
                String bizData = workflowEngine.getRuntimeService().getBizData(procInstId);
                data.put(ClientCodes.OPS_RESULT_CODE, 0);
                data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
                data.put(ClientCodes.OPS_RESULT_DATA, bizData);
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在，或者业务数据已经通知到客户端了。");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/getRejectNode")
    @ResponseBody
    public Map<String, Object> getRejectNode(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String taskId = t.getId();
            String procInstId = (String) t.getProperty(ClientCodes.OPS_PROCINST_ID);
            boolean flag = false;
            /*判断是否存在对应的流程实例*/
            List<? extends ITaskInst> taskInsts = workflowEngine.getRuntimeService().selectTaskInst(procInstId);
            for (ITaskInst taskInst : taskInsts) {
                if (taskId.equals(taskInst.getTaskId())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                /*获得当前能回退的节点*/
                List<IProcNode> procNodes = workflowEngine.getRuntimeService().selectRejectableNodes(procInstId, taskId);
                data.put(ClientCodes.OPS_RESULT_CODE, 0);
                data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
                data.put(ClientCodes.OPS_RESULT_DATA, procNodes);
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/getCadidate")
    @ResponseBody
    public Map<String, Object> getCadidate(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String taskId = t.getId();
            boolean flag = workflowEngine.getRuntimeService().existsTask(taskId);
            if (flag) {
                /*获得当前能回退的节点*/
                List<IStaff> staffs = workflowEngine.getRuntimeService().selectCadidates(taskId);
                data.put(ClientCodes.OPS_RESULT_CODE, 0);
                data.put(ClientCodes.OPS_RESULT_MSG, "获取成功.");
                data.put(ClientCodes.OPS_RESULT_DATA, staffs);
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/approveNode")
    @ResponseBody
    public Map<String, Object> approveNode(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String taskId = t.getId();
            boolean flag = workflowEngine.getRuntimeService().existsTask(taskId);
            if (flag) {
                String procInstId = (String) t.getProperty(ClientCodes.OPS_PROCINST_ID);
                String formData = (String) t.getProperty(ClientCodes.OPS_FORM_DATA);
                String memo = (String) t.getProperty(ClientCodes.OPS_MEMO);
                Staff staff = staffService.get(userName);
                StaffImpl staffImpl = new StaffImpl();
                BeanUtils.copyProperties(staff, staffImpl);
                //List<IStaff>  staffs = workflowEngine.getRuntimeService().selectCadidates(taskId);
                try {
                    workflowEngine.getRuntimeService().complete(procInstId, taskId, staffImpl, memo, formData);
                    data.put(ClientCodes.OPS_RESULT_CODE, 0);
                    data.put(ClientCodes.OPS_RESULT_MSG, "处理成功.");
                } catch (WorkflowException e) {
                    e.printStackTrace();
                    data.put(ClientCodes.OPS_RESULT_CODE, -1);
                    data.put(ClientCodes.OPS_RESULT_MSG, "流程处理出错：" + e.getMessage());
                }
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/rejectNode")
    @ResponseBody
    public Map<String, Object> rejectNode(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String taskId = t.getId();
            boolean flag = workflowEngine.getRuntimeService().existsTask(taskId);
            if (flag) {
                String procInstId = (String) t.getProperty(ClientCodes.OPS_PROCINST_ID);
                String nodeKey = (String) t.getProperty(ClientCodes.OPS_NODE_KEY);
                String memo = (String) t.getProperty(ClientCodes.OPS_MEMO);
                Staff staff = staffService.get(userName);
                StaffImpl staffImpl = new StaffImpl();
                BeanUtils.copyProperties(staff, staffImpl);
                //List<IStaff>  staffs = workflowEngine.getRuntimeService().selectCadidates(taskId);
                try {
                    workflowEngine.getRuntimeService().reject(procInstId, taskId, nodeKey, staffImpl, memo);
                    data.put(ClientCodes.OPS_RESULT_CODE, 0);
                    data.put(ClientCodes.OPS_RESULT_MSG, "处理成功.");
                } catch (WorkflowException e) {
                    e.printStackTrace();
                    data.put(ClientCodes.OPS_RESULT_CODE, -1);
                    data.put(ClientCodes.OPS_RESULT_MSG, "流程退回出错：" + e.getMessage());
                }
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/insertNodeBefore")
    @ResponseBody
    public Map<String, Object> insertNodeBefore(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String taskId = t.getId();
            boolean flag = workflowEngine.getRuntimeService().existsTask(taskId);
            if (flag) {
                String procInstId = (String) t.getProperty(ClientCodes.OPS_PROCINST_ID);
                String memo = (String) t.getProperty(ClientCodes.OPS_MEMO);
                String assigneeId = (String) t.getProperty(ClientCodes.OPS_ASSIGNEE);
                Staff assignee = staffService.get(assigneeId);
                StaffImpl assigneeImpl = new StaffImpl();
                BeanUtils.copyProperties(assignee, assigneeImpl);
                Staff staff = staffService.get(userName);
                StaffImpl staffImpl = new StaffImpl();
                BeanUtils.copyProperties(staff, staffImpl);
                //List<IStaff>  staffs = workflowEngine.getRuntimeService().selectCadidates(taskId);
                try {
                    workflowEngine.getRuntimeService().insertTasksBefore(procInstId, taskId, staffImpl, assigneeImpl, memo);
                    data.put(ClientCodes.OPS_RESULT_CODE, 0);
                    data.put(ClientCodes.OPS_RESULT_MSG, "处理成功.");
                } catch (WorkflowException e) {
                    e.printStackTrace();
                    data.put(ClientCodes.OPS_RESULT_CODE, -1);
                    data.put(ClientCodes.OPS_RESULT_MSG, "流程处理出错：" + e.getMessage());
                }
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/insertNodeAfter")
    @ResponseBody
    public Map<String, Object> insertNodeAfter(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String taskId = t.getId();
            boolean flag = workflowEngine.getRuntimeService().existsTask(taskId);
            if (flag) {
                String procInstId = (String) t.getProperty(ClientCodes.OPS_PROCINST_ID);
                String memo = (String) t.getProperty(ClientCodes.OPS_MEMO);
                String assigneeId = (String) t.getProperty(ClientCodes.OPS_ASSIGNEE);
                Staff assignee = staffService.get(assigneeId);
                StaffImpl assigneeImpl = new StaffImpl();
                BeanUtils.copyProperties(assignee, assigneeImpl);
                Staff staff = staffService.get(userName);
                StaffImpl staffImpl = new StaffImpl();
                BeanUtils.copyProperties(staff, staffImpl);

                try {
                    workflowEngine.getRuntimeService().insertTasksAfter(procInstId, taskId, staffImpl, assigneeImpl, memo);
                    data.put(ClientCodes.OPS_RESULT_CODE, 0);
                    data.put(ClientCodes.OPS_RESULT_MSG, "加签成功.");
                } catch (WorkflowException e) {
                    e.printStackTrace();
                    data.put(ClientCodes.OPS_RESULT_CODE, -1);
                    data.put(ClientCodes.OPS_RESULT_MSG, "流程处理出错：" + e.getMessage());
                }
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/transferTaskAssignee")
    @ResponseBody
    public Map<String, Object> transferTaskAssignee(String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String userName = t.getUsername();
        String password = t.getPassword();
        User user = userService.get(userName);
        if (user != null && password.equals(user.getPassword())) {
            String taskId = t.getId();
            boolean flag = workflowEngine.getRuntimeService().existsTask(taskId);
            if (flag) {
                String procInstId = (String) t.getProperty(ClientCodes.OPS_PROCINST_ID);
                String memo = (String) t.getProperty(ClientCodes.OPS_MEMO);
                String assigneeId = (String) t.getProperty(ClientCodes.OPS_ASSIGNEE);
                Staff assignee = staffService.get(assigneeId);
                StaffImpl assigneeImpl = new StaffImpl();
                BeanUtils.copyProperties(assignee, assigneeImpl);
                Staff staff = staffService.get(userName);
                StaffImpl staffImpl = new StaffImpl();
                BeanUtils.copyProperties(staff, staffImpl);

                try {
                    workflowEngine.getRuntimeService().transfer(procInstId, staffImpl, taskId, assigneeImpl, memo);
                    data.put(ClientCodes.OPS_RESULT_CODE, 0);
                    data.put(ClientCodes.OPS_RESULT_MSG, "转办成功.");
                } catch (WorkflowException e) {
                    e.printStackTrace();
                    data.put(ClientCodes.OPS_RESULT_CODE, -1);
                    data.put(ClientCodes.OPS_RESULT_MSG, "流程处理出错：" + e.getMessage());
                }
            } else {
                data.put(ClientCodes.OPS_RESULT_CODE, 4);
                data.put(ClientCodes.OPS_RESULT_MSG, "流程实例不存在");
            }
        } else {
            data.put(ClientCodes.OPS_RESULT_CODE, 3);
            data.put(ClientCodes.OPS_RESULT_MSG, ClientErrorCode.getLabel(3));
        }
        return data;
    }
}