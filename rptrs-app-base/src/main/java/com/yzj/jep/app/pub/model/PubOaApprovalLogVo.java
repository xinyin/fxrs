package com.yzj.jep.app.pub.model;

import java.util.Objects;

public class PubOaApprovalLogVo {
    Integer Serialno;//审批意见序号
    String id;//意见id
    String title;//意见详细信息
    String userName;//意见填写人
    String creatTime;//意见填写日期

    public Integer getSerialno() {
        return Serialno;
    }

    public void setSerialno(Integer serialno) {
        Serialno = serialno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubOaApprovalLogVo that = (PubOaApprovalLogVo) o;
        return Objects.equals(Serialno, that.Serialno) &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(creatTime, that.creatTime);
    }

    @Override
    public String toString() {
        return "PubOaApprovalLogVo{" +
                "Serialno=" + Serialno +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", creatTime='" + creatTime + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(Serialno, id, title, userName, creatTime);
    }
}
