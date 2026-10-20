package com.yzj.jep.app.sys.model;

public class SingleVerification {
    private String msg;
    private String usercode;
    private String loginname;
    private String username;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SingleVerification{" +
                "msg='" + msg + '\'' +
                ", usercode='" + usercode + '\'' +
                ", loginname='" + loginname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
