package com.yzj.jep.app.workflow.sms.notify;

import com.yzj.jep.app.workflow.sms.service.SmsNotifyService;
import com.yzj.jep.commons.RequestHolder;
import com.yzj.jep.core.factory.ServiceFactory;
import com.yzj.jep.core.security.token.Token;
import com.yzj.jep.core.security.token.TokenService;
import com.yzj.jep.core.security.token.impl.TokenBuilder;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.api.model.IStaff;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.api.notify.Notifier;
import com.yzj.jep.workflow.api.notify.event.NotifyEvent;
import com.yzj.jep.workflow.api.notify.event.NotifyEventType;
import com.yzj.jep.workflow.api.notify.event.impl.*;
import com.yzj.jep.workflow.impl.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title: SmsNotifyNotifier
 * @Author Whx
 * @Date: 2021/8/24 16:32
 * @Version 1.0
 */
@Service
public class SmsNotifyNotifier implements Notifier {
    private Logger logger = LoggerFactory.getLogger(SmsNotifyNotifier.class);

    @Autowired
    SmsNotifyService notifyService;

    @Autowired
    TokenService tokenService;

    @Override
    public void notify(NotifyEvent event) {
        if (this.notifyService == null) {
            this.notifyService = (SmsNotifyService) ServiceFactory.createService(SmsNotifyService.class);
        }

        if (this.tokenService == null) {
            this.tokenService = (TokenService) ServiceFactory.createService(TokenService.class);
        }

        String url;
        if (RequestHolder.get() != null) {
            url = RequestUtils.getServerUrl(RequestHolder.get(), "workflow/shortcut");
        } else {
            url = UrlUtil.mergeUrl(UrlUtil.getServiceUrl(), "workflow/shortcut");
        }

        NotifyEventType type = event.getType();


        // 此处根据不同的任务创建不同的系统通知消息，需调用SYS模块的相关接口。
        /*签领任务，只需要发送一条提醒数据给签领，任务创建者*/
        if (type == NotifyEventType.TASK_CLAIM) {
            TaskClaimNotifyEvent e = (TaskClaimNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            logger.info("用户：" + task.getStarterName() + " 完成了任务[" + task.getTaskKey() + "].");
            notifyService.createRemind(task.getProcInst().getStarterCode(), "业务[" + task.getProcInst().getProcName() + "]已被签领", operator.getStaffName() + "签领了业务[" + task.getProcInst().getProcName() + "]的任务[" + task.getTaskName() + "]");
        } else if (type == NotifyEventType.TASK_COMPLETE) {
            StringBuilder todoReceiver = new StringBuilder();
            TaskCompleteNotifyEvent e = (TaskCompleteNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            List<IStaff> staffs = e.getCandidateStaffs();

            /*组装待审批人*/
            for (IStaff s : staffs) {
                if (StringUtils.isNotBlank(todoReceiver)) {
                    todoReceiver.append(",");
                }
                todoReceiver.append(s.getStaffCode());
            }

            /*创建通知内容*/
            /*组装访问的token*/
            Token token = TokenBuilder.newTokenBuilder(task.getTaskId(), ClientCodes.TOKEN_SUBJECT_WORKFLOW, ClientCodes.OPS_DATA)
                    .expirationInHours(24)
                    .property("procInstId", task.getProcInst().getId())
                    .build();
            //TODO 招商银行临时修改
//			String url = RequestUtils.getServerUrl(RequestHolder.get(), "workflow/shortcut");
            //String url = "http://localhost:8080/jep-app/workflow/shortcut";
            String tokenString = tokenService.encode(token);
            //String tokenString = "cmbchina";

            StringBuilder sb = new StringBuilder();
            sb.append("业务[" + task.getProcInst().getProcName() + "]的需要您审批，请尽快处理。");
            sb.append(" 请点击<a href=\"").append(url).append("/view?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
                    .append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
                    .append("查看详情").append("</a>");

			/*.append(" <a href=\"").append(url).append("/approve?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("通过").append("</a>")
			.append(" <a href=\"").append(url).append("/reject?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("退回").append("</a>");*/


            notifyService.createTodo("业务[" + task.getProcInst().getProcName() + "]需要您审批", sb.toString(), todoReceiver.toString(), task.getTaskId(), "WORKFLOW", NotifyEventType.TASK_COMPLETE.toString());
            /*创建提醒*/
            notifyService.createRemind(task.getProcInst().getStarterCode(), "业务[" + task.getProcInst().getProcName() + "]的任务[" + task.getTaskName() + "]已完成。", operator.getStaffName() + "完成了业务[" + task.getProcInst().getProcName() + "]的任务[" + task.getTaskName() + "]");
        } else if (type == NotifyEventType.TASK_REJECT) {
            TaskRejectNotifyEvent e = (TaskRejectNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            List<IStaff> staffs = e.getCandidateStaffs();
            StringBuffer sb = new StringBuffer();
            for (IStaff staff : staffs) {
                if (sb.length() == 0) {
                    sb.append(staff.getStaffCode());
                } else {
                    sb.append(",").append(staff.getStaffCode());
                }
            }
            notifyService.createRemind(sb.toString(), "业务[" + task.getProcInst().getProcName() + "]已被退回。", operator.getStaffName() + "退回了业务[" + task.getProcInst().getProcName() + "]， 请尽快处理。");
        } else if (type == NotifyEventType.TASK_MOVETO) {
            StringBuilder todoReceiver = new StringBuilder();
            TaskMoveToNotifyEvent e = (TaskMoveToNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            List<IStaff> staffs = e.getCandidateStaffs();

            /*组装待审批人*/
            for (IStaff s : staffs) {
                if (StringUtils.isNotBlank(todoReceiver)) {
                    todoReceiver.append(",");
                }
                todoReceiver.append(s.getStaffCode());
            }

            /*创建通知内容*/
            /*组装访问的token*/
            Token token = TokenBuilder.newTokenBuilder(task.getTaskId(), ClientCodes.TOKEN_SUBJECT_WORKFLOW, ClientCodes.OPS_DATA)
                    .expirationInHours(24)
                    .property("procInstId", task.getProcInst().getId())
                    .build();
            //TODO 招商银行临时修改
//			String url = RequestUtils.getServerUrl(RequestHolder.get(), "workflow/shortcut");
            //String url = "http://localhost:8080/jep-app/workflow/shortcut";
            String tokenString = tokenService.encode(token);
            //String tokenString = "cmbchina";

            StringBuilder sb = new StringBuilder();
            sb.append("业务[" + task.getProcInst().getProcName() + "]的需要您审批，请尽快处理。");
            sb.append(" 请点击<a href=\"").append(url).append("/view?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
                    .append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
                    .append("查看详情").append("</a>");

			/*.append(" <a href=\"").append(url).append("/approve?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("通过").append("</a>")
			.append(" <a href=\"").append(url).append("/reject?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("退回").append("</a>");*/

            notifyService.createTodo("业务[" + task.getProcInst().getProcName() + "]需要您审批", sb.toString(), todoReceiver.toString(), task.getTaskId(), "WORKFLOW", NotifyEventType.TASK_COMPLETE.toString());
            /*创建提醒*/
            notifyService.createRemind(task.getProcInst().getStarterCode(), "业务[" + task.getProcInst().getProcName() + "]的任务被移动到[" + task.getTaskName() + "]。", operator.getStaffName() + "移动了业务[" + task.getProcInst().getProcName() + "]的任务到[" + task.getTaskName() + "]");
        } else if (type == NotifyEventType.TASK_END) {
            TaskEndNotifyEvent e = (TaskEndNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            notifyService.createRemind(task.getProcInst().getStarterCode(), "业务[" + task.getProcInst().getProcName() + "]已被" + operator.getStaffName() + "完成。", operator.getStaffName() + "完成了业务[" + task.getProcInst().getProcName() + "]");
        } else if (type == NotifyEventType.TASK_TERMINATE) {
            TaskTerminateNotifyEvent e = (TaskTerminateNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            notifyService.createRemind(task.getProcInst().getStarterCode(), "业务[" + task.getProcInst().getProcName() + "]已被" + operator.getStaffName() + "终止。", operator.getStaffName() + "终止了业务[" + task.getProcInst().getProcName() + "]");
        } else if (type == NotifyEventType.TASK_INSERTBEFORE) {
            TaskInsertBeforeNotifyEvent e = (TaskInsertBeforeNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            IStaff assignee = e.getAssignee();

            /*创建通知内容*/
            /*组装访问的token*/
            Token token = TokenBuilder.newTokenBuilder(task.getTaskId(), ClientCodes.TOKEN_SUBJECT_WORKFLOW, ClientCodes.OPS_DATA)
                    .expirationInHours(24)
                    .property("procInstId", task.getProcInst().getId())
                    .build();
            //TODO 招商银行临时修改
//			String url = RequestUtils.getServerUrl(RequestHolder.get(), "workflow/shortcut");
//			String url = "http://localhost:8080/jep-app/workflow/shortcut";
            String tokenString = tokenService.encode(token);
//			String tokenString = "cmbchina";

            StringBuilder sb = new StringBuilder();
            sb.append("业务[" + task.getProcInst().getProcName() + "]的需要您审批，请尽快处理。");
            sb.append(" 请点击<a href=\"").append(url).append("/view?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
                    .append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
                    .append("查看详情").append("</a>");


			/*.append(" <a href=\"").append(url).append("/approve?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("通过").append("</a>")
			.append(" <a href=\"").append(url).append("/reject?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("退回").append("</a>");*/


            notifyService.createTodo("业务[" + task.getProcInst().getProcName() + "]需要您审批", sb.toString(), assignee.getStaffCode(), task.getTaskId(), "WORKFLOW", NotifyEventType.TASK_COMPLETE.toString());
            /*创建提醒*/
            notifyService.createRemind(task.getProcInst().getStarterCode(), "业务[" + task.getProcInst().getProcName() + "]的任务前加了一个处理节点。", operator.getStaffName() + "在业务[" + task.getProcInst().getProcName() + "]的任务前加了一个处理节点，处理人是[" + assignee.getStaffName() + "]");

        } else if (type == NotifyEventType.TASK_TRANSFER) {
            TaskTransferNotifyEvent e = (TaskTransferNotifyEvent) event;
            ITaskInst task = e.getTask();
            IStaff operator = e.getOperator();
            IStaff assignee = e.getAssignee();

            /*创建通知内容*/
            /*组装访问的token*/
            Token token = TokenBuilder.newTokenBuilder(task.getTaskId(), ClientCodes.TOKEN_SUBJECT_WORKFLOW, ClientCodes.OPS_DATA)
                    .expirationInHours(24)
                    .property("procInstId", task.getProcInst().getId())
                    .build();
            //TODO 招商银行临时修改
//			String url = RequestUtils.getServerUrl(RequestHolder.get(), "workflow/shortcut");
//			String url = "http://localhost:8080/jep-app/workflow/shortcut";
            String tokenString = tokenService.encode(token);
//			String tokenString = "cmbchina";

            StringBuilder sb = new StringBuilder();
            sb.append("业务[" + task.getProcInst().getProcName() + "]的需要您审批，请尽快处理。");
            sb.append(" 请点击<a href=\"").append(url).append("/view?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
                    .append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
                    .append("查看详情").append("</a>");

			/*.append(" <a href=\"").append(url).append("/approve?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("通过").append("</a>")
			.append(" <a href=\"").append(url).append("/reject?token=").append(tokenString).append("&staffCode=${staffCode}&staffName=${staffName}")
			.append("\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"color:blue\">")
			.append("退回").append("</a>");*/

            notifyService.createTodo("业务[" + task.getProcInst().getProcName() + "]需要您审批", sb.toString(), assignee.getStaffCode(), task.getTaskId(), "WORKFLOW", NotifyEventType.TASK_COMPLETE.toString());
            /*创建提醒*/
            notifyService.createRemind(task.getProcInst().getStarterCode(), "业务[" + task.getProcInst().getProcName() + "]的任务[" + task.getTaskName() + "]指定了处理人为" + assignee.getStaffName(), operator.getStaffName() + "在业务[" + task.getProcInst().getProcName() + "]的任务[" + task.getTaskName() + "]指定了处理人为" + assignee.getStaffName());

        }
    }

    @Override
    public boolean interruptOnFailure() {
        return false;
    }
}
