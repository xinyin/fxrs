<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>元数据区域关系表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="元数据区域关系表" subTitle="请输入元数据区域关系表查询信息" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row>
                    <bf:formGroup col="4">
                        <bc:label text="关系ID" col="5" formControl="true"/>
                        <bf:text name="__LIKES_regionTableId" col="7" placeholder="关系ID"/>
                    </bf:formGroup>
                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="metaRegionTableTable" idField="regionTableId" url="${base}/sys/metaRegionTable">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:column field="regionTableId" title="关系ID" width="100"/>
                <bc:column field="regionId" title="区域ID" width="100"/>
                <bc:column field="tableId" title="元数据ID" width="100"/>
                <bc:column field="schema" title="模式" width="100"/>
                <bc:column field="tableCode" title="元数据编码" width="100"/>
                <bc:column field="tableType" title="M - 模型结果表             S - 步骤结果表             B - 业务数据表" width="100"/>
                <bc:column field="objectType" title="对象类型：             T - 表             V  -  视图" width="100"/>
                <bc:column field="objectSource"
                           title="对象来源：             S - 系统同步             C - 用户创建             M - 模型创建" width="100"/>
                <bc:column field="unitCode" title="机构编码" width="100"/>
                <bc:column field="unitName" title="机构名称" width="100"/>
                <bc:column field="deptCode" title="部门编码" width="100"/>
                <bc:column field="deptName" title="部门名称" width="100"/>
                <bc:column field="staffCode" title="用户编码" width="100"/>
                <bc:column field="staffName" title="用户名称" width="100"/>
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