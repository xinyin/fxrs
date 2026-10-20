package com.yzj.jep.app.contract.model;

public class ApiResponse {

    /**
     * 0000代表交易成功，其它代码代表交易失败
     */
    private String resultCode;
    /**
     * 接口交易成功提示/错误提示
     */
    private String msg;
    /**
     * json格式的业务数据
     */
    private Object data;

    private String CHNL_ID;

    private String TRANS_CODE;


    public String getCHNL_ID() {
        return CHNL_ID;
    }

    public void setCHNL_ID(String CHNL_ID) {
        this.CHNL_ID = CHNL_ID;
    }

    public String getTRANS_CODE() {
        return TRANS_CODE;
    }

    public void setTRANS_CODE(String TRANS_CODE) {
        this.TRANS_CODE = TRANS_CODE;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
