<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>用户的登录信息</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="userBox" title="编辑用户登录信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="opt" id="opt"/>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="用户编码" col="4" formControl="true"/>
                    <bf:text id="userId" name="userId" col="8" value="${user.userId}" disabled="${opt == 'edit'}">
                        <bv:jepCode/>
                        <bv:remote url="${base}/sys/user/check" name="userId" message="用户编码"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="用户名称" col="4" formControl="true"/>
                    <bf:text id="userName" name="userName" col="8" value="${user.userName}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="密码" col="4" formControl="true"/>
                    <bf:text id="password" name="password" col="8" value="${user.password}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="密码过期天数" col="4" formControl="true"/>
                    <bf:text id="passwordExpireDays" name="passwordExpireDays" col="8"
                             value="${user.passwordExpireDays}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="密码过期时间" col="4" formControl="true"/>
                    <bf:datebox id="passwordExpireTime" name="passwordExpireTime" col="8"/>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="过期天数" col="4" formControl="true"/>
                    <bf:text id="accountExpireDays" name="accountExpireDays" col="8" value="${user.accountExpireDays}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="过期日期" col="4" formControl="true"/>
                    <bf:datebox id="accountExpireTime" name="accountExpireTime" col="8" foramt="date"/>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="账户过期标识" col="4" formControl="true"/>
                    <bf:text id="accountNonExpired" name="accountNonExpired" col="8" value="${user.accountNonExpired}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="账户锁定标识" col="4" formControl="true"/>
                    <bf:text id="accountNonLocked" name="accountNonLocked" col="8" value="${user.accountNonLocked}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="证书过期标识" col="4" formControl="true"/>
                    <bf:text id="credentialNonExpired" name="credentialNonExpired" col="8"
                             value="${user.credentialNonExpired}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="是否启用" col="4" formControl="true"/>
                    <bf:text id="enabled" name="enabled" col="8" value="${user.enabled}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="登录失败次数" col="4" formControl="true"/>
                    <bf:text id="failedCount" name="failedCount" col="8" value="${user.failedCount}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="登录IP" col="4" formControl="true"/>
                    <bf:text id="loginIp" name="loginIp" col="8" value="${user.loginIp}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="登录时间" col="4" formControl="true"/>
                    <bf:datebox id="loginTime" name="loginTime" col="8" foramt="date"/>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="6">
                    <bc:label text="最大失败次数" col="4" formControl="true"/>
                    <bf:text id="maxFailedCount" name="maxFailedCount" col="8" value="${user.maxFailedCount}">
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/sys/user/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/user/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button outline="true" text="返回">
                        <be:event>
                            window.location = '${base}/sys/user/list';
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
