<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>组件表基本信息表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="dashWidgetBox" title="组件表基本信息表" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="组件编码" col="4" formControl="true"/>
                        <bc:label text="${dashWidget.code}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="组件名称" col="4" formControl="true"/>
                        <bc:label text="${dashWidget.name}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="类型" col="4" formControl="true"/>
                        <bc:label text="${dashWidget.type}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="组件实现类" col="4" formControl="true"/>
                        <bc:label text="${dashWidget.implClass}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="状态" col="4" formControl="true"/>
                        <bc:label text="${dashWidget.status}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="组件描述" col="4" formControl="true"/>
                        <bc:label text="${dashWidget.desc}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="备注" col="4" formControl="true"/>
                        <bc:label text="${dashWidget.remark}" col="8" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/dashWidget/list';
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