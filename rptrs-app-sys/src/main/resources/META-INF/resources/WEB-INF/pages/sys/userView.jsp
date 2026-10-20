<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>用户的登录信息</title>
    <script type="text/javascript">
        $(window).load(function () {
            jQuery.ajaxSetup({
                cache: false
            });
        });
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="userBox" title="用户登录信息" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="用户编码" col="2" formControl="true"/>
                    <bc:label text="${user.userId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="用户名称" col="2" formControl="true"/>
                    <bc:label text="${user.userName}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="密码" col="2" formControl="true"/>
                    <bc:label text="${user.password}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="密码过期天数" col="2" formControl="true"/>
                    <bc:label text="${user.passwordExpireDays}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="密码过期时间" col="2" formControl="true"/>
                    <bc:label text="${user.passwordExpireTime}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="过期天数" col="2" formControl="true"/>
                    <bc:label text="${user.accountExpireDays}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="过期日期" col="2" formControl="true"/>
                    <bc:label text="${user.accountExpireTime}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="账户过期标识" col="2" formControl="true"/>
                    <bc:label text="${user.accountNonExpired}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="账户锁定标识" col="2" formControl="true"/>
                    <bc:label text="${user.accountNonLocked}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="证书过期标识" col="2" formControl="true"/>
                    <bc:label text="${user.credentialNonExpired}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="是否启用" col="2" formControl="true"/>
                    <bc:label text="${user.enabled}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="登录失败次数" col="2" formControl="true"/>
                    <bc:label text="${user.failedCount}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="登录IP" col="2" formControl="true"/>
                    <bc:label text="${user.loginIp}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="登录时间" col="2" formControl="true"/>
                    <bc:label text="${user.loginTime}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="最大失败次数" col="2" formControl="true"/>
                    <bc:label text="${user.maxFailedCount}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="">
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