package com.yzj.jep.app.sys.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yzj.jep.app.sys.dto.UserAuthItem;

/**
 * 用户信息<br/>
 * gyzhang@20140424@ChengDu，补充group业务部门及角色组属性
 *
 * @author yzhou
 * @version 3.0
 * @date 2011-9-26 下午3:59:14
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -6402504738916128725L;

    public static final String USER_SESSION_KEY = "currentUser";

    private String userid;
    private String username;                                        //用户名,登陆名=staffCode
    private String staffCode;                                        //人员编号
    private String staffName;                                        //人员姓名

    private String loginType;                                        //预留
    private String ip;                                                //登录IP

    private String tenantId;                                        //所属帐套号，租户id

    private String deptCode;                                        //所在部门
    private String deptName;                                        //所在部门名称
    private String deptBizType;                                    //所在部门的业务类型
    private String deptDisplayName;                                //所在部门的显示名称
    private String unitHierarchy;                                    //所在机构层级：总行、分支行等
    private String unitCode;                                        //所属机构编码
    private String unitName;                                        //所属机构名称
    private String unitDisplayName;                                //所在机构的显示名称
    private Map<String, List<UserAuthItem>> authItems;                            //存在的数据权限

    private String telphone;                                        //电话，一般都是手机，用来发送短信提醒用
    private String email;                                            //邮件
    private String mobile;

    public UserProfile() {
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptBizType() {
        return deptBizType;
    }

    public void setDeptBizType(String deptBizType) {
        this.deptBizType = deptBizType;
    }

    public String getUnitDisplayName() {
        return unitDisplayName;
    }

    public void setUnitDisplayName(String unitDisplayName) {
        this.unitDisplayName = unitDisplayName;
    }

    public String getUnitHierarchy() {
        return unitHierarchy;
    }

    public void setUnitHierarchy(String unitHierarchy) {
        this.unitHierarchy = unitHierarchy;
    }

    public String getDeptDisplayName() {
        return deptDisplayName;
    }

    public void setDeptDisplayName(String deptDisplayName) {
        this.deptDisplayName = deptDisplayName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Map<String, List<UserAuthItem>> getAuthItems() {
        return authItems;
    }

    public void setAuthItems(Map<String, List<UserAuthItem>> authItems) {
        this.authItems = authItems;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
