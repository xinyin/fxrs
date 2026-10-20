package com.yzj.jep.app.stat.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @title: TaskInfo
 * @Author Whx
 * @Date: 2021/7/13 16:47
 * @Version 1.0
 */
public class TaskInfo implements Serializable {
    private String id;
    private String assignee;
    private Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
