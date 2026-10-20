package com.yzj.jep.app.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.model.Email;
import com.yzj.jep.app.ins.model.EmailToSendVo;
import com.yzj.jep.app.ins.service.ApprovalPushService;
import com.yzj.jep.app.ins.service.ApproveAssignerService;
import com.yzj.jep.app.ins.service.impl.ApprovalPushServiceImpl;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class EmailSend {

    Logger logger = LoggerFactory.getLogger(ApprovalPushServiceImpl.class);
    @Autowired
    private ApprovalPushService approvalPushService;

    @Autowired
    private ApproveAssignerService approveAssignerService;

    @Value("${email.SwitchOn}")
    private String emailSwitchOn;
    AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);

    private static final String FINANCE_DEPARTMENT = "财务部";
    private static final String NODE_NAME_HG = "合规部";
    private static final String HEADQUARTERS_RELATED_TRANSACTIONS = "总部合规部关联交易管理岗";

    //所有的 关联交易   关联关系变更备案  重大/累计重大   审批邮件提醒。
    public void emailSend(List<Email> emailList, String data) {
        System.out.println("开始参数封装=================");
        JSONObject json = JSONObject.parseObject(data);
        List<Email> listEmail = new ArrayList<>();
        Email emailNew = null;
        JSONObject param = new JSONObject();
        if (emailList == null || emailList.size() == 0) {
            param.put("type", "3");
            Staff staff = approveAssignerService.selectStaffByEmail(json.getString("startStaffCode"));
            emailNew = AddLoop(staff.getEmail(), param);
            listEmail.add(emailNew);
        } else {
            for (Email el : emailList) {
                param.put("type", "1");
                emailNew = AddLoop(el.getRecevieEmail(), param);
                listEmail.add(emailNew);
            }
        }
        this.mailInformation(listEmail);
    }

    //披露完成通知
    public void emailSends(List<Email> emailList, String type, String fileName) {
        System.out.println("开始参数封装=================");
        List<Email> listEmail = new ArrayList<>();
        Email emailNew = null;
        JSONObject param = new JSONObject();
        param.put("type", type);
        param.put("fileName", fileName);
        for (Email el : emailList) {
            emailNew = AddLoop(el.getRecevieEmail(), param);
            listEmail.add(emailNew);
        }
        this.mailInformation(listEmail);
    }


    //重大/累计重大
    public void mojorEmail(List<Email> emailList, String type) {
        System.out.println("开始参数封装=================");
        List<Email> listEmail = new ArrayList<>();
        Email emailNew = null;
        JSONObject param = new JSONObject();
        param.put("type", type);
        for (Email el : emailList) {
            emailNew = AddLoop(el.getRecevieEmail(), param);
            listEmail.add(emailNew);
        }
        this.mailInformation(listEmail);
    }


    // 累计重大达到80%后每一笔邮件提醒
    public void mojorEmailEighty(List<Email> emailList, String majorNumber, String relName, String type) {
        System.out.println("开始参数封装=================");
        List<Email> listEmail = new ArrayList<>();
        Email emailNew = null;
        JSONObject param = new JSONObject();
        param.put("relName", relName);
        param.put("type", type);
        param.put("majorNumber", majorNumber);
        for (Email el : emailList) {
            emailNew = AddLoop(el.getRecevieEmail(), param);
            listEmail.add(emailNew);
        }
        this.mailInformation(listEmail);

    }

    //当前时间
    public String newDateTime() {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = tempDate.format(new java.util.Date());
        return datetime;
    }

    //年
    public String newDateYa() {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy");
        String datetime = tempDate.format(new java.util.Date());
        return datetime;
    }

    private Email AddLoop(String emailNew, JSONObject param) {
        Email emai = new Email();
        String templateCode = this.titleType(param);
        emai.setTemplateCode(templateCode);
        String content = this.mailTemplate(param);
        emai.setRecevieEmail(emailNew);
        emai.setSendEmailCode("1001");
        emai.setContent(content);
        emai.setCopyEmail("");
        emai.setRepeat("1");
        emai.setUrl("");
        emai.setActual("1");
        return emai;
    }

    private String titleType(JSONObject param){
        String result = null;
        String type = param.getString("type");
        if (type.equals("1")) {
            result = "reminder";
        } else if (type.equals("3")) {
            result = "submission";
        } else if (type.equals("4")) {
            result = "eighty";
        } else if (type.equals("5")) {
            result = "ninety";
        } else if (type.equals("6")) {
            result = "supervise";
        } else if (type.equals("7")) {
            result = "quarter";
        } else if (type.equals("8")) {
            result = "coreNotice";
        }
        return result;
    }

    public void mailInformation(List listEmail) {
        JSONObject param = new JSONObject();
        param.put("emailList", listEmail);
        param.put("tranType", "0002");
        param.put("businessCode", "A084");
        param.put("orderNo", UUID.randomUUID());
        param.put("orderDate", this.newDateTime());
        param.put("userName", "");
        param.put("passWord", "");
        param.put("businessNo", UUID.randomUUID());
        param.put("sign", "pflife0084");
        //开启发送邮件
        logger.info("发送邮件参数"+"================="+param);
        if (emailSwitchOn.equals("1")) {
            approvalPushService.emailSends(param.toJSONString());
        }
    }

    /**
     *  使用抄送Email 接口调用
     */
    public void mailInformationCopy(List listEmail,String copyEmail) {
        JSONObject param = new JSONObject();
        param.put("emailList", listEmail);
        param.put("tranType", "0002");
        param.put("businessCode", "A084");
        param.put("orderNo", UUID.randomUUID());
        param.put("orderDate", this.newDateTime());
        param.put("userName", "");
        param.put("passWord", "");
        param.put("businessNo", UUID.randomUUID());
        param.put("sign", "pflife0084");
        param.put("copyEmail", copyEmail);
        //开启发送邮件
        if (emailSwitchOn.equals("1")) {
            approvalPushService.emailSends(param.toJSONString());
        }
    }


    private String mailTemplate(JSONObject param) {
        String relName = param.getString("relName");
        String type = param.getString("type");
        String majorNumber = param.getString("majorNumber");
        String fileName = param.getString("fileName");
        String result = null;
        if (type.equals("1")) {
            result = "您有一条待审批事项，请及时登录“http://gljy.pflife.com.cn:8080/rptrs/index/”关联交易管理信息系统进行处理。#";
            //result = "您有一条待审批事项，请及时登录“http://10.3.20.64:8080/rptrs/index/”关联交易管理信息系统进行处理。#";
        } else if (type.equals("2")) {
            result = "您有一条待审批事项，请及时登录“http://gljy.pflife.com.cn:8080/rptrs/index/”关联交易管理信息系统进行处理。#";
            //result = "您有一条待审批事项，请及时登录“http://10.3.20.64:8080/rptrs/index/”关联交易管理信息系统进行处理。#";
        } else if (type.equals("3")) {
            result = "您提交的关联交易事项已完成审批，请于合同签订后15个工作日内完成监管报送和披露。#";
        } else if (type.equals("4")) {
            result = relName + "关联交易" + newDateYa() + "年度实际累计金额已达到" + majorNumber + "万，超过2400万，请注意后期该交易是否达到重大关联交易（累计）限额。#";
        } else if (type.equals("5")) {
            result = relName + "统一交易协议" + newDateYa() + "年度实际累计金额已达到框架预估金额的90%，请注意后期该交易是否超过框架预估金额，构成统一交易协议的实质性变更。#";
        } else if (type.equals("6")) {
            result = fileName + "季度关联交易监管报告已完成上传，详情请登录关联交易信息管理系统进行查看。#";
        } else if (type.equals("7")) {
            result = fileName + "季度关联交易控制委员会报告已完成上传，详情请登录关联交易信息管理系统进行查看。#";
        } else if (type.equals("8")) {
            result = "运营管理部发生一笔待补录关联交易，烦请经办人至关联交易系统进行补录并提交。#";
        }
        return result;
    }


    //给关联交易管理岗、部门负责人发邮件
    public List selectPerpleHg() {
        List list = new ArrayList();
        Email emailList = new Email();
        List<Staff> staffLists = approveAssignerService.selectStaffByRoleStd("000028", "");
        for (Staff staff : staffLists) {
            if (staff.getDeptName().contains(NODE_NAME_HG)) {
                String email = staff.getEmail();
                emailList.setRecevieEmail(email);
                list.add(emailList);
            }
        }
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
        for (Staff staff : staffList) {
            if (staff.getDeptName().contains(NODE_NAME_HG)) {
                String email = staff.getEmail();
                emailList.setRecevieEmail(email);
                list.add(emailList);
            }
        }
        return list;
    }
    //财务部，法律部部门负责人
    public List selectPerple() {
        List list = new ArrayList();
        Email emailList = new Email();
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00026", "");
        for (Staff staff : staffList) {
            //财务部
            if (staff.getDeptName().contains(FINANCE_DEPARTMENT)) {
                String email = staff.getEmail();
                emailList.setRecevieEmail(email);
                list.add(emailList);
                //法律部
            }else if (staff.getDeptName().contains("法律部")){
                String email = staff.getEmail();
                emailList.setRecevieEmail(email);
                list.add(emailList);
            }
        }
        return list;
    }

    //运营部经办人
    public List selectPerpleYy() {
        List list = new ArrayList();
        Email emailList = new Email();
        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd("00030", "");
        for (Staff staff : staffList) {
            if (staff.getDeptName().contains("运营管理部")) {
                String email = staff.getEmail();
                emailList.setRecevieEmail(email);
                list.add(emailList);
            }
        }
        return list;
    }

    /**
     * 构造邮件内容等
     *
     * @param receiveEmail 接收邮件邮箱
     * @param templateCode 邮件模板
     * @param content  邮件内容
     * @return
     */
    public Email buildEmailInfo(String receiveEmail, String templateCode, String content) {
        Email email = new Email();
        email.setTemplateCode(templateCode);
        email.setRecevieEmail(receiveEmail);
        email.setSendEmailCode("1001");
        email.setContent(content);
        email.setCopyEmail("");
        email.setRepeat("1");
        email.setUrl("");
        email.setActual("1");
        return email;
    }

    /**
     * 构造邮件内容等
     *
     * @param receiveEmail 接收邮件邮箱
     * @param templateCode 邮件模板
     * @param content  邮件内容
     * @return
     */
    public Email buildEmailInfoCopyEmail(String receiveEmail, String templateCode, String content,String copyEmail) {
        Email email = new Email();
        email.setTemplateCode(templateCode);
        email.setRecevieEmail(receiveEmail);
        email.setSendEmailCode("1001");
        email.setContent(content);
        email.setCopyEmail("");
        email.setRepeat("1");
        email.setUrl("");
        email.setActual("1");
        email.setCopyEmail(copyEmail);
        return email;
    }

    /**
     * 邮件模板code
     */
    public static class EmailTemplate {
        public static final String REMINDER = "reminder";
        public static final String SUBMISSION = "submission";
        public static final String EIGHTY = "eighty";
        public static final String NINETY = "ninety";
        public static final String SUPERVISE = "supervise";
        public static final String QUARTER = "quarter";
        public static final String CORENOTICE = "coreNotice";
    }

}
