<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>用户的登录信息</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="角色类型" subTitle="请输入角色类型查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="用户编码" col="5" formControl="true"></bc:label>
                        <bf:text name="__LIKES_userId" col="7" placeholder="用户编码">
                            <bv:jepCode/>
                        </bf:text>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="userTable" idField="userId" url="${base}/sys/user">
                <bc:toolbar id="add" text="新增" type="button">
                <bc:column field="userId" title="用户编码" width="100"/>
                <bc:column field="userName" title="用户名称" width="100"/>
                <bc:column field="password" title="密码" width="100"/>
                <bc:column field="passwordExpireDays" title="密码过期天数" width="100"/>
                <bc:column field="passwordExpireTime" title="密码过期时间" width="100"/>
                <bc:column field="accountExpireDays" title="过期天数" width="100"/>
                <bc:column field="accountExpireTime" title="过期日期" width="100"/>
                <bc:column field="accountNonExpired" title="账户过期标识" width="100"/>
                <bc:column field="accountNonLocked" title="账户锁定标识" width="100"/>
                <bc:column field="credentialNonExpired" title="证书过期标识" width="100"/>
                <bc:column field="enabled" title="是否启用" width="100"/>
                <bc:column field="failedCount" title="登录失败次数" width="100"/>
                <bc:column field="loginIp" title="登录IP" width="100"/>
                <bc:column field="loginTime" title="登录时间" width="100"/>
                <bc:column field="maxFailedCount" title="最大失败次数" width="100"/>
                <bc:operator id="edit" icon="edit" title="编辑"/>
                <bc:operator id="remove" icon="trash" title="移除"/>
                <bc:operator id="view" icon="file-o" title="查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>