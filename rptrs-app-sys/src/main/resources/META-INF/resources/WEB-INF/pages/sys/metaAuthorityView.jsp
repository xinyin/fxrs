<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>元数据授权，包括操作权限与数据权限。</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="metaAuthorityBox" title="元数据授权，包括操作权限与数据权限。" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="授权ID" col="2" formControl="true"/>
                    <bc:label text="${metaAuthority.authId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色编码" col="2" formControl="true"/>
                    <bc:label text="${metaAuthority.roleCode}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="OPER - 操作权限             DAK - 数据权限             " col="2" formControl="true"/>
                    <bc:label text="${metaAuthority.authType}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="授权对象类型             M - 数据集市             T - 元数据             C - 元数据列" col="2"
                              formControl="true"/>
                    <bc:label text="${metaAuthority.authTargetType}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="授权对象" col="2" formControl="true"/>
                    <bc:label text="${metaAuthority.authTarget}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label
                            text="授权内容：             操作权限： Y - 允许，N - 拒绝             数据权限： DAK_ORG, DAK_ORG_ALL, DAK_DEPT, DAK_DEPT_ALL, DAK_FORBIDDEN"
                            col="2" formControl="true"/>
                    <bc:label text="${metaAuthority.authData}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/metaAuthority/list';
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