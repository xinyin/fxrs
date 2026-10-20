package com.yzj.jep.app.record.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @title: InterfaceRecord
 * 接口调用记录
 * @Author Whx
 * @Date: 2023/3/31 9:37
 * @Version 1.0
 */
@Entity
@Table(name = "ins_tra_interface_record")
public class InterfaceRecord extends BasePO {

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    /**
     * 接口类型 1、其他系统调用关联交易系统 1 ， 2、关联交易系统调用其他系统
     */
    @Column(name = "type", nullable = true, length = 100)
    private String type;

    /**
     * 业务名称，保存汉字即可
     */
    @Column(name = "bus_name", nullable = true, length = 100)
    private String busName;

    /**
     * 服务编码 作为服务方必填字段
     */
    @Column(name = "trans_code", nullable = true, length = 100)
    private String transCode;

    /**
     * 作为服务方时接口的唯一标识
     */
    @Column(name = "chnl_id", nullable = true, length = 100)
    private String chnlId;

    /**
     * 响应代码 （接收方与发送方）
     */
    @Column(name = "code", nullable = true, length = 100)
    private String code;

    /**
     * 调用结果 1 成功 2 失败
     */
    @Column(name = "result", nullable = true, length = 100)
    private String result;

    /**
     * 调用服务器地址
     */
    @Column(name = "host_ip", nullable = true, length = 100)
    private String hostIp;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getChnlId() {
        return chnlId;
    }

    public void setChnlId(String chnlId) {
        this.chnlId = chnlId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }
}
