<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>定义系统中的附件上传类型。</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row animated fadeInRight">
    <bc:ibox title="定义系统中的附件上传类型。" subTitle="请输入定义系统中的附件上传类型。查询信息" col="12" collapsible="true">
        <bf:form id="queryForm" validation="true">
            <bc:row>
                <bc:col col="4">
                    <bc:label text="上传类型编码" col="4" formControl="true"/>
                    <bf:text name="__LIKES_attachTypeCode" col="8" placeholder="上传类型编码"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="上传类型名称" col="4" formControl="true"/>
                    <bf:text name="__LIKES_attachTypeName" col="8" placeholder="上传类型名称"/>
                </bc:col>
                <bc:col col="4">
                    <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                    <bc:button id="btnReset" text="重置" icon="refresh"/>
                </bc:col>
            </bc:row>
        </bf:form>
        <bc:table id="attachmentTypeTable" idField="attachTypeCode" url="${base}/sys/attachmentType">
            <bc:toolbar id="add" text="新增" type="button"/>
            <bc:column field="attachTypeCode" title="上传类型编码" width="100"/>
            <bc:column field="attachTypeName" title="上传类型名称" width="100"/>
            <bc:column field="attachPath" title="定义该类型的附件在系统中的保存路径，相对于附件上传路径（系统配置项）" width="100"/>
            <bc:operator id="edit" icon="edit" title="编辑"/>
            <bc:operator id="remove" icon="trash" title="移除"/>
            <bc:operator id="view" icon="file-o" title="查看"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>