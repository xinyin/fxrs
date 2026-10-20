<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>选择关联字段</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bc:col col="3">
                <bc:tree id="metaRegionTree" url="${base}/sys/metaRegion" edit="false" autoParam="id=parentId">
                    <be:event type="onClick">onClick</be:event>
                </bc:tree>
            </bc:col>
            <bc:col col="4">
                <bc:table id="metaTableTable" idField="tableId" url="${base}/sys/metaTable"
                          queryUrl="queryMetatableExclude" showColumns="false" showRefresh="false"
                          childField="regionId" childFieldType="S" parentType="tree" parentId="metaRegionTree"
                          parentField="regionId">
                    <bc:column field="schema" title="表模式" width="80"/>
                    <bc:column field="tableCode" title="表名" width="100"/>
                    <bc:column field="tableName" title="表中文名" width="100"/>
                </bc:table>
            </bc:col>
            <bc:col col="5">
                <bc:table id="metaColumnTable" idField="columnId" url="${base}/sys/metaColumn" showRefresh="false"
                          showColumns="false"
                          childField="tableId" childFieldType="S" parentType="table" parentId="metaTableTable"
                          parentField="tableId">
                    <bc:toolbar id="choose" text="选择" type="button" handler="chooseHandler"/>
                    <bc:column field="columnCode" title="列名" width="100"/>
                    <bc:column field="columnName" title="列中文名" width="100"/>
                </bc:table>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function onClick(event, treeId, treeNode) {
        $('#metaTableTable').jTable('clearCheckData');
        $('#metaTableTable').jTable('setExtQueryParams', {
            '__NES_tableId': '${tableId}',
            '__EQS_regionId': treeNode.id
        });
        $('#metaTableTable').jTable('refresh');
    }

    function chooseHandler() {
        var table = $('#metaTableTable').bootstrapTable('getSelections');
        var col = $('#metaColumnTable').bootstrapTable('getSelections');
        if (table.length == 1 && col.length == 1) {
            var tableName = table[0]['tableName'];
            if (!tableName) {
                tableName = table[0]['tableCode'];
            }
            $.messager.args('wChooseMastertable').callback(table[0]['tableId'], table[0]['tableCode'], col[0]['columnCode'], tableName);
            $.messager.hide('wChooseMastertable');
        } else {
            $.messager.toastr('系统提示', '请选择关联的字段。', 'info');
        }

    }
</script>
</body>
</html>