package com.yzj.jep.app.contract.model;

public class ApiRequest {
    /**
     * 随机字符串，建议使用UUID
     */
    private String nonce;
    /**
     * 请求方唯一凭证，请求方要提前获得服务方分配好的appId
     */
    private String appId;
    /**
     * 当前时间戳，用于校验请求时效
     */
    private long timestamp;
    /**
     * 签名字符串
     */
    private String sig;
    /**
     * 请求权限校验数据，除白名单系统(目前OA系统在白名单中)外都为必填，
     * OAuth默认json串要有userId（用户id）、dept（部门代码）、
     * authMsg（权限信息串）
     */
    private Object OAuth;
    /**
     * json格式的业务数据，具体数据内容定义参见各个接口具体说明
     */
    private Object data;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public Object getOAuth() {
        return OAuth;
    }

    public void setOAuth(Object OAuth) {
        this.OAuth = OAuth;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
