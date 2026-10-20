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
        <bc:ibox id="roleBox" title="角色类型信息" subTitle="" col="12" collapsible="false">
            <bf:form id="formView" method="post" validation="false">
                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="机构层级" col="6" formControl="true"/>
                        <bc:label text="fixedDict:InsUnitHierarchy/${role.hierarchy}" col="6" formControl="true"
                                  style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="角色类型" col="6" formControl="true"/>
                        <bc:label text="${roleStdName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="所在角色组" col="6" formControl="true"/>
                        <bc:label text="${roleGroupName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="角色编码" col="6" formControl="true"/>
                        <bc:label text="${role.roleCode}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <bc:col col="6">
                        <bc:label text="角色名称" col="6" formControl="true"/>
                        <bc:label text="${role.roleName}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="角色描述" col="6" formControl="true"/>
                        <bc:label text="${role.roleDesc}" col="6" formControl="true" style="text-align:left"/>
                    </bc:col>
                </bf:formGroup>

                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/role/list';
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