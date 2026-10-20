package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import com.yzj.jep.orm.entity.PO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 有效关联方对象
 */

@Entity
@Table(name = "bus_rel_list")
public class Bus_rel_list  {
//    @Id
//    @Column(name = "ID", nullable = false, length = 11)
    private Integer id;
    /**
     * 关联方类型
     */

//    @Column(name = "RelType", nullable = false, precision = 100, scale = 0)
    private String relType;
    /**
     * 关联方名称
     */
//    @Column(name = "RelName", nullable = false, precision = 60, scale = 0)
    private String relName;
    /**
     * 证件类型
     */
//    @Column(name = "RelUscType", nullable = false, precision = 10, scale = 0)
    private String relUscType;
    /**
     * 证件号
     */
//    @Column(name = "RelUscCode", nullable = false, precision = 50, scale = 0)
    private String relUscCode;
    /**
     * 版本号
     */
//    @Column(name = "Version", nullable = false, precision = 50, scale = 0)
    private String Version;

    public Bus_rel_list() {
        super();
    }

    public Bus_rel_list(Integer id, String relType, String relName, String relUscType, String relUscCode, String version) {
        this.id = id;
        this.relType = relType;
        this.relName = relName;
        this.relUscType = relUscType;
        this.relUscCode = relUscCode;
        Version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelUscType() {
        return relUscType;
    }

    public void setRelUscType(String relUscType) {
        this.relUscType = relUscType;
    }

    public String getRelUscCode() {
        return relUscCode;
    }

    public void setRelUscCode(String relUscCode) {
        this.relUscCode = relUscCode;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
