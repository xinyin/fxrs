<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>租户表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="tenantBox" title="租户表" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="主键" col="2" formControl="true"/>
                    <bc:label text="${tenant.id}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="编码" col="2" formControl="true"/>
                    <bc:label text="${tenant.code}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="名称" col="2" formControl="true"/>
                    <bc:label text="${tenant.name}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="是否根租户" col="2" formControl="true"/>
                    <bc:label text="${tenant.isRoot}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:label text="备注" col="2" formControl="true"/>
                    <bc:label text="${tenant.remark}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button type="primary" text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/tenant/list';
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