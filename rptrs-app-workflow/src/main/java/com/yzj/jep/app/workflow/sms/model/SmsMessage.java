package com.yzj.jep.app.workflow.sms.model;

import java.io.Serializable;

/**
 * @title: SmsMessage
 * @Author Whx
 * @Date: 2021/8/24 15:34
 * @Version 1.0
 */
public class SmsMessage implements Serializable {

    /**手机号码*/
    private String phone;
    /**文本短信内容*/
    private String content;
    /**签名*/
    private String Signature;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
