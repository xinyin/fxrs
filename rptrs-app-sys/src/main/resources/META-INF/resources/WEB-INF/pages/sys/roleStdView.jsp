<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>查看角色类型</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleStdIbox" title="查看角色类型详细信息" subTitle="" col="12" collapsible="false">
            <bf:form id="viewForm" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="角色类型编码" col="6" formControl="true"/>
                    <bc:label text="${roleStd.roleStdCode}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:label text="角色类型名称" col="6" formControl="true"/>
                    <bc:label text="${roleStd.roleStdName}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:label text="角色类型描述" col="6" formControl="true"/>
                    <bc:label text="${roleStd.roleStdDesc}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>

                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/roleStd/list';
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