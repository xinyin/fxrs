<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>应用编辑</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="appConfigBox" title="应用级别配置" col="12" collapsible="false">
            <bc:col col="3">
                <bc:tree id="appConfigTree" url="${base}/sys/appConfig" edit="true" formUneditableField="configCode"
                         formId="appConfigForm" autoParam="id=parentId" formNameField="configName">
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="appConfigForm" name="appConfigFormNM" method="post" validation="true">
                    <bf:formGroup>
                        <bf:hidden name="parentId"/>
                        <bc:col col="6">
                            <bc:label text="所属分类" col="4" formControl="true"/>
                            <bf:text name="parentName" col="8" disabled="true"/>
                        </bc:col>
                        <bc:col col="6">
                            <bc:label text="配置名称" col="4" formControl="true"/>
                            <bf:text name="configName" col="8">
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="6">
                            <bc:label text="配置键" col="4" formControl="true"/>
                            <bf:text id="configCode" name="configCode" col="8">
                                <bv:notEmpty/>
                                <bv:regexp regexp="^[a-zA-Z_]{1}[a-zA-Z\d_]*" message="请输入正确的配置键"/>
                            </bf:text>
                        </bc:col>
                        <bc:col col="6" cls="configInput">
                            <bc:label text="配置值" col="4" formControl="true"/>
                            <bf:text name="configValue" col="8">
                                <bv:notEmpty/>
                                <bv:stringLength min="1"/>
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:label text="描述" col="2" formControl="true"/>
                        <bf:text name="configDesc" col="10" multiple="true"/>
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