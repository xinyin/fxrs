package com.yzj.jep.app.bus.sch;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.util.EmailSend;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import com.yzj.jep.sys.mail.IEmailSendService;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 1.6.扫描所有关联交易表是否有待确认数据，并发邮件提醒
 *
 * @author root
 */
@ScheduledJob("TraRemindTask")
public class TraRemindTask implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TraRemindTask.class);

    private IEmailSendService iEmailSendService;
    private StaffService staffService;

    TraFundsService traFundsService = SpringContextHolder.getBean(TraFundsService.class);
    TraBusinessService traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
    TraInterestService traInterestService = SpringContextHolder.getBean(TraInterestService.class);
    TraServiceService traServiceService = SpringContextHolder.getBean(TraServiceService.class);
    AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);
    EmailSend emailSend = SpringContextHolder.getBean(EmailSend.class);

    @Override
    public void execute(JobContext context) throws JobExecutionException, IOException {
        List<Filter> traFilter = new Filter.Builder()
                .eq("delflag", "1")
                .eq("status", "2")
                .build();

        iEmailSendService = SpringContextHolder.getBean(IEmailSendService.class);
        staffService = SpringContextHolder.getBean(StaffService.class);
        List<TraFunds> traFundsList = traFundsService.select(traFilter);
        List<TraBusiness> traBusinessList = traBusinessService.select(traFilter);
        List<TraInterest> traInterestList = traInterestService.select(traFilter);
        List<TraService> traServiceList = traServiceService.select(traFilter);
        try {
            //此处逻辑分开发送。各个业务分开发送邮件
            if (!CollUtil.isEmpty(traFundsList)) {
                List<Email> emailList = new ArrayList<>();
                traFundsList.forEach(traFunds -> {
                    Email email = buildEmailEntity(traFunds.getAmendId(), traFunds.getCreateTime(), traFunds.getTranName(), traFunds.getTranType(), traFunds.getContractNo(), traFunds.getDeptName());
                    emailList.add(email);
                });
                if (CollectionUtil.isNotEmpty(emailList)) {
                    emailSend.mailInformation(emailList);
                }
            }
            if (!CollUtil.isEmpty(traBusinessList)) {
                List<Email> emailList = new ArrayList<>();
                traBusinessList.forEach(traBusiness -> {
                    Email email = buildEmailEntity(traBusiness.getAmendId(), traBusiness.getCreateTime(), traBusiness.getTranName(), traBusiness.getTranType(), traBusiness.getContractNo(), traBusiness.getDeptName());
                    emailList.add(email);
                });
                if (CollectionUtil.isNotEmpty(emailList)) {
                    emailSend.mailInformation(emailList);
                }
            }
            if (CollUtil.isEmpty(traInterestList)) {
                List<Email> emailList = new ArrayList<>();
                traInterestList.forEach(traInterest -> {
                    Email email = buildEmailEntity(traInterest.getAmendId(), traInterest.getCreateTime(), traInterest.getTranName(), traInterest.getTranType(), traInterest.getContractNo(), traInterest.getDeptName());
                    emailList.add(email);
                });
                if (CollectionUtil.isNotEmpty(emailList)) {
                    emailSend.mailInformation(emailList);
                }
            }
            if (!CollUtil.isEmpty(traServiceList)) {
                List<Email> emailList = new ArrayList<>();
                traServiceList.forEach(traService -> {
                    Email email = buildEmailEntity(traService.getAmendId(), traService.getCreateTime(), traService.getTranName(), traService.getTranType(), traService.getContractNo(), traService.getDeptName());
                    emailList.add(email);
                });
                if (CollectionUtil.isNotEmpty(emailList)) {
                    emailSend.mailInformation(emailList);
                }
            }
        } catch (Exception e) {
            logger.error("发送邮件失败，失败信息为：", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 发送邮件
     * @param amendId
     * @param createTime
     * @param tranType
     * @param contractNo
     * @param deptName
     */

    private void sendEmail(String amendId, Date createTime, String tranName, String tranType, String contractNo, String deptName) {
        List<Filter> build = new Filter.Builder().eq("staffCode", amendId).build();
        Staff staff = staffService.get(build);
        String template = "合同编号为【{}】的【{}】关联交易需要上传附件信息。";
        String str = CharSequenceUtil.format(template, contractNo, changeTranType(tranType));
        logger.info(str);
        String createDate = new DateTime(createTime).toString("YYYY年MM月dd日");
        String staffName = staff.getStaffName();
        String email = staff.getEmail();
        String tranFileUploadPath = appConfigService.getConfigValue("tran_file_upload_path");
        String msg = "{}  {}，你好：\n" +
                "        {}  你发起了关联交易  {}，未上传相关证明材料，处于待确认状态，请及时登录系统补充上传相关材料。\n" +
                "        上传资料地址：{} ";

        String msgStr = CharSequenceUtil.format(msg, deptName, staffName, createDate, tranName, tranFileUploadPath);
        iEmailSendService.sendMail(email, "", "", "待确认数据提醒", msgStr);
    }

    private Email buildEmailEntity(String amendId, Date createTime, String tranName, String tranType, String contractNo, String deptName) {
        List<Filter> build = new Filter.Builder().eq("staffCode", amendId).build();
        Staff staff = staffService.get(build);
        String template = "合同编号为【{}】的【{}】关联交易需要上传附件信息。";
        String str = CharSequenceUtil.format(template, contractNo, changeTranType(tranType));
        String createDate = new DateTime(createTime).toString("YYYY年MM月dd日");
        String staffName = staff.getStaffName();
        String email = staff.getEmail();
        String tranFileUploadPath = appConfigService.getConfigValue("tran_file_upload_path");
        String msg = "{}  {}，你好：\n" +
                "        {}  你发起了关联交易  {}，未上传相关证明材料，处于待确认状态，请及时登录系统补充上传相关材料。\n" +
                "        上传资料地址：{} #";

        String msgStr = CharSequenceUtil.format(msg, deptName, staffName, createDate, tranName, tranFileUploadPath);
        if (StrUtil.isNotBlank(email)) {
            Email emailInfo = emailSend.buildEmailInfo(email, EmailSend.EmailTemplate.REMINDER, msgStr);
            return emailInfo;
        }
        return null;
    }



    /**
     * 交易类型字典值转换
     *
     * @param tranType
     * @return
     */
    private String changeTranType(String tranType) {
        switch (tranType) {
            case TranTypeConstant.TRAN_TYPE_1:
                return "资金运用类";
            case TranTypeConstant.TRAN_TYPE_2:
                return "服务类";
            case TranTypeConstant.TRAN_TYPE_3:
                return "利益转移类";
            default:
                return "保险业务类";
        }
    }
}
