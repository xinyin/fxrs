<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>查看角色组类型</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="roleGroupBox" title="角色组详细信息" col="12" collapsible="false">
            <bf:form id="formView" method="post" validation="false">
                <bf:formGroup>
                    <bc:label text="机构层级" col="6" formControl="true"/>
                    <bc:label text="fixedDict:InsUnitHierarchy/${roleGroup.hierarchy}" col="6" formControl="true"
                              style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色组编码" col="6" formControl="true"/>
                    <bc:label text="${roleGroup.roleGroupCode}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色组名称" col="6" formControl="true"/>
                    <bc:label text="${roleGroup.roleGroupName}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <bc:label text="角色组描述" col="6" formControl="true"/>
                    <bc:label text="${roleGroup.roleGroupDesc}" col="6" formControl="true" style="text-align:left"/>
                </bf:formGroup>
                <bf:formGroup>
                    <div align="center">
                        <bc:button text="返回" icon="arrow-circle-left">
                            <be:event>
                                window.location = '${base}/sys/roleGroup/list';
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