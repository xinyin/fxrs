package com.yzj.jep.app.enums;

public enum PerSystemCode {
    //成功
    SUCCESS("0000", "成功"),
    //请求参数缺失
    EX_1001("1001", "请求参数缺失"),
    //没有相关文件
    EX_1002("1002", "没有相关文件"),
    //未知异常
    EX_9999("9999", "未知异常");



    String code;
    String msg;

    private PerSystemCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
