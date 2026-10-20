<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>元数据表空间</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="元数据表空间" subTitle="请输入元数据表空间查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row>
                    <bf:formGroup col="4">
                        <bc:label text="表空间ID" col="5" formControl="true"/>
                        <bf:text name="__LIKES_tablespaceId" col="7" placeholder="表空间ID"/>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="metaTablespaceTable" idField="tablespaceId" url="${base}/sys/metaTablespace">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:column field="tablespaceId" title="表空间ID" width="100"/>
                <bc:column field="tablespaceCode" title="表空间编码" width="100"/>
                <bc:column field="tablespaceName" title="表空间名称" width="100"/>
                <bc:column field="tablespaceDesc" title="表空间描述" width="100"/>
                <bc:column field="dataFileLoc" title="数据文件" width="100"/>
                <bc:column field="initSize" title="初始大小(M)" width="100"/>
                <bc:column field="pageSize" title="页面大小(K)" width="100"/>
                <bc:column field="autoExtend" title="是否扩展: Y/N" width="100"/>
                <bc:column field="extendSize" title="扩展大小(M)" width="100"/>
                <bc:column field="maxSize" title="最大容量(M)" width="100"/>
                <bc:column field="status" title="表空间状态:             10 - 草稿             30 - 启用             50 - 停用"
                           width="100"/>
                <bc:column field="remark" title="备注" width="100"/>
                <bc:operator id="view" icon="search" title="查看"/>
                <bc:operator id="edit" icon="edit" title="编辑"/>
                <bc:operator id="remove" icon="trash" title="移除"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>