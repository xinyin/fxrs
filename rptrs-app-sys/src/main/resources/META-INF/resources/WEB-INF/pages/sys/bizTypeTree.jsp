<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>业务种类表管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxbizType" title="业务种类表配置" col="12" collapsible="false">
            <bc:col col="3">
                <bc:tree id="bizTypeTree" url="${base}/sys/bizType" edit="true"
                         formId="bizTypeForm" autoParam="id=parentId"
                         formUneditableField="bizTypeCode" formNameField="bizTypeName">
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="bizTypeForm" method="post" validation="true">
                    <bf:formGroup>
                        <bc:label text="父菜单编码" col="2" formControl="true"/>
                        <bf:text name="parentId" col="6" disabled="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="父菜单名称" col="2" formControl="true"/>
                        <bf:text name="parentName" col="6" disabled="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="业务种类编码" col="2" formControl="true"/>
                        <bf:text id="bizTypeCode" name="bizTypeCode" col="6">
                            <bv:regexp regexp="^[\w\s]+$" message="请输入正确的编码"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="业务种类名称" col="2" formControl="true"/>
                        <bf:text id="bizTypeName" name="bizTypeName" col="6"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="状态" col="2" formControl="true"/>
                        <bf:select name="status" dataSource="fixedDict" url="BizTypeStatus" col="6"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="备注" col="2" formControl="true"/>
                        <bf:text id="remark" name="remark" col="6" multiple="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <div align="center">
                            <bc:button id="btnSave" type="primary" text="保存" icon="save"/>
                        </div>
                    </bf:formGroup>
                </bf:form>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>