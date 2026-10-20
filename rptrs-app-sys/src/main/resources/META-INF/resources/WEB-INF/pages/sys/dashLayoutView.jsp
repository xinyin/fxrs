<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>布局的基本信息</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="dashLayoutBox" title="布局的基本信息" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="布局编码" col="6" formControl="true"/>
                    <bc:label text="${dashLayout.code}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="布局名称" col="6" formControl="true"/>
                    <bc:label text="${dashLayout.name}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="优先级" col="6" formControl="true"/>
                    <bc:label text="${dashLayout.priority}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="设计模式" col="6" formControl="true"/>
                    <bc:label text="${dashLayout.designType}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="布局描述" col="6" formControl="true"/>
                    <bc:label text="${dashLayout.desc}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/dashLayout/list';
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