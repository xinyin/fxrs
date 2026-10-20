package com.yzj.jep.app.bus.model;

import com.google.common.base.Objects;
import com.yzj.jep.orm.entity.PO;

/**
 * <p>
 * 工具实体类,数据data更新字段用来接受userId;userName;userStatus;telephone;email;deptId; 与DeptUtil相对应
 * </p>
 *
 * @author yls
 * @since 2020/10/27
 */
public class DeptEntitys implements PO {
    private String userId;
    private String userName;
    private String userStatus;
    private String telephone;
    private String email;
    private String deptId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptEntitys that = (DeptEntitys) o;
        return Objects.equal(userId, that.userId) &&
                Objects.equal(userName, that.userName) &&
                Objects.equal(userStatus, that.userStatus) &&
                Objects.equal(telephone, that.telephone) &&
                Objects.equal(email, that.email) &&
                Objects.equal(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, userName, userStatus, telephone, email, deptId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeptEntitys{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userStatus='").append(userStatus).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", deptId='").append(deptId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
