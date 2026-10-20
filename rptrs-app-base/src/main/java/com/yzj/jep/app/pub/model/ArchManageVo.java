package com.yzj.jep.app.pub.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;


/**
 * 归档文件管理Vo
 * @author wll
 */
public class ArchManageVo extends BasePO implements Serializable {

    //对应文件名称
    private String pubFileName;
    //更新部门
    private String pubSubject;
    //操作员
    private String createByName;
    //归档日期
    private Date archDate;

    private String startArchDate;
    private String endArchDate;

    //年
    private String pubYear;
    //季度
    private String pubQuarter;
    //月
    private String month;

    private String path;

    public String getStartArchDate() {
        return startArchDate;
    }

    public void setStartArchDate(String startArchDate) {
        this.startArchDate = startArchDate;
    }

    public String getEndArchDate() {
        return endArchDate;
    }

    public void setEndArchDate(String endArchDate) {
        this.endArchDate = endArchDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public String getPubQuarter() {
        return pubQuarter;
    }

    public void setPubQuarter(String pubQuarter) {
        this.pubQuarter = pubQuarter;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setArchDateString(String value){
        if (value != null){
            setArchDate(DateUtils.parseDate(value));
        }
    }

    public String getArchDateString(){
        if (Optional.ofNullable(archDate).isPresent()) {
            return DateUtils.formatDate(archDate);
        }
        return "";
    }

    public String getPubFileName() {
        return pubFileName;
    }

    public void setPubFileName(String pubFileName) {
        this.pubFileName = pubFileName;
    }

    public String getPubSubject() {
        return pubSubject;
    }

    public void setPubSubject(String pubSubject) {
        this.pubSubject = pubSubject;
    }

    @Override
    public String getCreateByName() {
        return createByName;
    }

    @Override
    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public Date getArchDate() {
        return archDate;
    }

    public void setArchDate(Date archDate) {
        this.archDate = archDate;
    }
}
