<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>组件详细配置信息。</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleStdBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="true">
                <bf:formGroup>
                    <bc:label text="组件编码" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetConfig.widgetCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="组件详细配置，JSON 格式" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetConfig.config}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="租户" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetConfig.tenantId}" col="10" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button outline="true" text="返回" icon="">
                            <be:event>
                                window.location = '${base}/sys/dashWidgetConfig/list';
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