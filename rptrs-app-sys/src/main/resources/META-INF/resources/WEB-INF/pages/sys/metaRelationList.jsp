<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>描述元数据表之间的关联</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="描述元数据表之间的关联" subTitle="请输入描述元数据表之间的关联查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row>
                    <bf:formGroup col="4">
                        <bc:label text="表关系ID" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relationId" col="7" placeholder="表关系ID"/>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="metaRelationTable" idField="relationId" url="${base}/sys/metaRelation">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:column field="relationId" title="表关系ID" width="100"/>
                <bc:column field="masterTableId" title="主表ID" width="100"/>
                <bc:column field="masterTableSchema" title="主表模式" width="100"/>
                <bc:column field="masterTableCode" title="主表编码" width="100"/>
                <bc:column field="masterColumnCode" title="主表列编码" width="100"/>
                <bc:column field="joinTableId" title="joinTableId" width="100"/>
                <bc:column field="joinTableSchema" title="关联表模式" width="100"/>
                <bc:column field="joinTableCode" title="关联表编码" width="100"/>
                <bc:column field="joinColumnCode" title="关联表列编码" width="100"/>
                <bc:column field="joinDetails" title="关联关系" width="100"/>
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