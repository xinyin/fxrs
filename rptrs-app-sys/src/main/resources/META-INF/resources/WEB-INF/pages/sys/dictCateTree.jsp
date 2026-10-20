<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>DictCate管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxdictCate" title="字典类别配置" col="12" collapsible="false">
            <bc:col col="3">
                <bc:tree id="dictCateTree" url="${base}/sys/dictCate" edit="true"
                         formId="dictCateForm" autoParam="id=parentId"
                         formUneditableField="dictCateCode" formNameField="dictCateName">
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="dictCateForm" method="post" validation="true">
                    <bf:formGroup>
                        <bc:label text="父菜单编码" col="4" formControl="true"/>
                        <bf:text name="parentId" col="4" disabled="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="父菜单名称" col="4" formControl="true"/>
                        <bf:text name="parentName" col="4" disabled="true"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="类别编码" col="4" formControl="true"/>
                        <bf:text id="dictCateCode" name="dictCateCode" col="4">
                            <bv:regexp regexp="^[a-zA-Z_]{1}[a-zA-Z\d_]*" message="请输入正确的编码"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="类别名称" col="4" formControl="true"/>
                        <bf:text id="dictCateName" name="dictCateName" col="4"/>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="类别描述" col="4" formControl="true"/>
                        <bf:text id="dictCateDesc" name="dictCateDesc" col="4" multiple="true"/>
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