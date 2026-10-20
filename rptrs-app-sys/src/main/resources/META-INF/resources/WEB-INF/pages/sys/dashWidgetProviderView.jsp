<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>组件所包含的数据提供程序，可能有多个。</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="dashWidegtProviderBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="true">
                <bf:formGroup>
                    <bc:label text="提供程序ID" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetProvider.provierId}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="数据提供程序编码" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetProvider.providerCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="数据提供程序参数，JSON 格式，不同的数据提供程序有不同格式。" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetProvider.providerParams}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="组件编码" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetProvider.widgetCode}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="是否被引用，Y/N。一旦被引用，该数据提供程序将不能被移除。" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetProvider.beenUsed}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="租户" col="2" formControl="true"/>
                    <bc:label text="${dashWidgetProvider.tenantId}" col="10" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div class="col-sm-4 col-sm-offset-2">
                        <bc:button outline="true" text="返回">
                            <be:event>
                                window.location = '${base}/sys/dashWidgetProvider/list';
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