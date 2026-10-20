<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>用户信息</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="staffBox" title="查看用户信息" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="true">
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="用户编码" col="6" formControl="true"/>
                        <bc:label text="${staff.staffCode}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="用户名称" col="6" formControl="true"/>
                        <bc:label text="${staff.staffName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="所属部门" col="6" formControl="true"/>
                        <bc:label text="${staff.deptName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="所属用户组" col="6" formControl="true"/>
                        <bc:label text="${staff.staffGroupName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="关联角色" col="6" formControl="true"/>
                        <bc:label text="${staff.roleName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="关联角色组" col="6" formControl="true"/>
                        <bc:label text="${staff.roleGroupName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="用户性别" col="6" formControl="true"/>
                        <bc:label text="fixedDict:Gender/${staff.gender}" col="6" formControl="true"
                                  style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="出生日期" col="6" formControl="true"/>
                        <bc:label text="${staff.birthdayString}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="用户邮件" col="6" formControl="true"/>
                        <bc:label text="${staff.email}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="移动电话" col="6" formControl="true"/>
                        <bc:label text="${staff.mobile}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="用户地址" col="6" formControl="true"/>
                        <bc:label text="${staff.address}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="用户状态" col="6" formControl="true"/>
                        <bc:label text="fixedDict:StaffStatus/${staff.status}" col="6" formControl="true"
                                  style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/staff/list';
                            </be:event>
                        </bc:button>
                    </div>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>