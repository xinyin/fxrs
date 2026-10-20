package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "bus_message")
public class DlyInformation extends BasePO {
    //自增主键
    @Id
    @Column(name = "id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfName() {
        return infName;
    }

    public void setInfName(String infName) {
        this.infName = infName;
    }

    public String getInfType() {
        return infType;
    }

    public void setInfType(String infType) {
        this.infType = infType;
    }

    public String getInfText() {
        return infText;
    }

    public void setInfText(String infText) {
        this.infText = infText;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

//    public Timestamp getReleaseTime() {
//        return releaseTime;
//    }
//
//    public void setReleaseTime(Timestamp releaseTime) {
//        this.releaseTime = releaseTime;
//    }

    public String getTopType() {
        return topType;
    }

    public void setTopType(String topType) {
        this.topType = topType;
    }

    public Timestamp getTopTime() {
        return topTime;
    }

    public void setTopTime(Timestamp topTime) {
        this.topTime = topTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    //信息标题
    @Column(name="inf_name" ,nullable = false)
    @Length(max=50,message="标题长度不能大于25。")
    private String infName;
    //信息类型
    @Column(name="inf_type",nullable = false)
    private String infType;
    //信息内容
    @Column(name="inf_text" ,nullable = false)
    private String infText;
    //创建时间
    @Column(name="create_time")
    @Temporal(TemporalType.DATE)
    private Timestamp creatTime;
    //发布状态
    @Column(name="release_type")
    private String releaseType;
    //发布时间
    @Column(name="release_time")
    @Temporal(TemporalType.DATE)
    private Timestamp releaseTime;
    //置顶状态
    @Column(name="top_type")
    private String topType;
    //置顶时间
    @Column(name="top_time")
    private Timestamp topTime;
    //更新时间
    @Column(name="update_time")
    @Temporal(TemporalType.DATE)
    private Timestamp updateTime;
    //图片1
    @Column(name="img1")
    private String img1;
    //图片2
    @Column(name="img2")
    private String img2;
    //图片3
    @Column(name="img3")
    private String img3;
    //图片4
    @Column(name="img4")
    private String img4;
    //图片5
    @Column(name="img5")
    private String img5;
}
