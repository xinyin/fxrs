<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>选择关联表</title>
    <style type="text/css">
        #iboxTable {
            margin-bottom: 0px;
        }

        .ibox-content {
            padding-bottom: 0px;
            height: 490px;
        }

        .fixed-table-toolbar {
            display: none;
        }
    </style>
</head>
<body class="gray-bg" style="overflow: hidden">
<div class="row">
    <bc:ibox id="iboxTable" title="" col="12" collapsible="false">
        <div class="row">
            <bc:col col="3">
                <bc:tree id="metaRegionTree" url="${base}/sys/metaRegion" edit="false" autoParam="id=parentId">
                    <be:event type="onClick">onClick</be:event>
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="queryForm" validation="true">
                    <bf:formGroup>
                        <bf:text name="__LIKES_tableCode" col="4" placeholder="表名"/>
                        <bf:text name="__LIKES_tableName" col="4" placeholder="表中文名"/>
                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                        <bc:button id="btnReset" text="重置" icon="refresh"/>
                    </bf:formGroup>
                </bf:form>
                <div style="overflow-y: auto; overflow-x: hidden; height: 300px;">
                    <bc:table id="metaTable" idField="tableId" url="${base}/sys/metaTable" queryUrl="queryMetatable"
                              childField="regionId" childFieldType="S" parentType="tree" parentId="metaRegionTree"
                              parentField="regionId"
                              showColumns="false" showRefresh="false" pagination="true">
                        <bc:column field="schema" title="表模式" width="100"/>
                        <bc:column field="tableCode" title="表名" width="100"/>
                        <bc:column field="tableName" title="表中文名" width="100"/>
                        <bc:column field="objectType" title="对象类型" formatter="fixedDict:MetaTableObjectType"
                                   width="100"/>
                    </bc:table>
                </div>
            </bc:col>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="row" align="center">
            <bc:button id="btnSave" type="info" text="确定" click="okHandler()"/>
            <bc:button id="btnBack" text="关闭">
                <be:event>
                    $.messager.hide('winJoinTable');
                </be:event>
            </bc:button>
        </div>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    /* 确定 */
    function okHandler() {
        var nodes = $('#metaTable').jTable('getCheckData');
        if (nodes.length == 0) {
            $.messager.toastr('提示', '请选择一个元数据表', 'warning');
        } else {
            $.messager.args('winJoinTable').result({
                'code': nodes[0].tableCode,
                'name': nodes[0].tableName,
                'schema': nodes[0].schema,
                'id': nodes[0].tableId,
                'alias': '${joinAlias}',
                'tableType': nodes[0].tableType,
                'objectSource': nodes[0].objectSource,
                'objectType': nodes[0].objectType
            });
            $.messager.hide('winJoinTable');
        }
    }
</script>
</body>
</html>