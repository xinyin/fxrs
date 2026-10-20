package com.yzj.jep.app.bus.model;

import com.google.common.base.Objects;
import com.yzj.jep.orm.entity.PO;

import java.util.List;

/**
 * <p>
 * 工具实体类,数据data更新字段用来接受id,name,parentid 与UnitUtil相对应
 * </p>
 *
 * @author yls
 * @since 2020/10/21
 */

public class UnitEntitys implements PO {

    private String deptId;
    private String deptName;
    private String parentId;
    private List<UnitEntitys> sysDeptInfos;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<UnitEntitys> getSysDeptInfos() {
        return sysDeptInfos;
    }

    public void setSysDeptInfos(List<UnitEntitys> sysDeptInfos) {
        this.sysDeptInfos = sysDeptInfos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UnitUtils{");
        sb.append("deptId='").append(deptId).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", sysDeptInfos=").append(sysDeptInfos);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitEntitys that = (UnitEntitys) o;
        return Objects.equal(deptId, that.deptId) &&
                Objects.equal(deptName, that.deptName) &&
                Objects.equal(parentId, that.parentId) &&
                Objects.equal(sysDeptInfos, that.sysDeptInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(deptId, deptName, parentId, sysDeptInfos);
    }
}
