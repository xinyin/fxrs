package com.yzj.jep.app.ins.model;

import com.yzj.jep.app.contract.model.ApiResponse;

import java.io.Serializable;

/**
 * 邮件发送
 */
public class Email extends ApiResponse implements Serializable {

    //订单类型  固定值0002
    private String tranType;
    //订单第三方
    private String businessCode;
    //订单号  订单唯一标示，不可以重复
    private String orderNo;
    //订单日期
    private String orderDate;
    //第三方账户
    private String userName;
    //第三方密码
    private String passWord;
    //订单业务号
    private String businessNo;
    //签名
    private String sign;
    //邮件list
    private String emailList;
    //模板代码
    private String templateCode;
    //收件邮箱地址
    private String recevieEmail;
    //发送邮箱代码
    private String sendEmailCode;
    //邮件内容 邮件短信内容#区分
    private String content;
    //抄送邮箱地址 抄送邮箱以#区分
    private String copyEmail;
    //期望发送时间
    private String expectTime;
    //自动重发 失败是否自动重发，1重发，0不重发
    private String repeat;
    //附件地址
    private String url;
    //是否实时 1实时，0非实时，如果为非实时邮件，期望时间不能为空
    private String actual;


    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEmailList() {
        return emailList;
    }

    public void setEmailList(String emailList) {
        this.emailList = emailList;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getRecevieEmail() {
        return recevieEmail;
    }

    public void setRecevieEmail(String recevieEmail) {
        this.recevieEmail = recevieEmail;
    }

    public String getSendEmailCode() {
        return sendEmailCode;
    }

    public void setSendEmailCode(String sendEmailCode) {
        this.sendEmailCode = sendEmailCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCopyEmail() {
        return copyEmail;
    }

    public void setCopyEmail(String copyEmail) {
        this.copyEmail = copyEmail;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }
}
