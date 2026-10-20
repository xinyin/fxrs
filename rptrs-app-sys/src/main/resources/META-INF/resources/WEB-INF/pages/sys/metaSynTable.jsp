<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>描述元数据的表</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox title="" col="12">
        <bf:form id="queryForm" validation="true">
            <bf:formGroup>
                <bc:label text="表模式" col="2" formControl="true"/>
                <bf:select name="schema" col="3" id="schema" dataSource="custom" url="sys/metaTable/querySchema"
                           value="${currentSchema}">
                    <bv:notEmpty/>
                </bf:select>
                <bc:label text="表名" col="1" formControl="true"/>
                <bf:text name="tableName" id="tableName" col="6" placeholder="表名"
                         helpText=" 百分号“%”代表多个字符，下划线“_”代表一个字符。如需要查询多个表用“,”分隔。"/>
            </bf:formGroup>
        </bf:form>
        <bc:table id="metaTableTable" multiple="true" pagination="false"
                  idField="tableId" url="${base}/sys/metaTable" queryUrl="queryTables">
            <bc:toolbar id="btnQuery1" text="查询" icon="search" handler="searchHanlder"/>
            <bc:toolbar id="btnSyn" text="同步" icon="refresh" handler="synHanlder"/>
            <bc:column field="schema" title="表模式" width="100"/>
            <bc:column field="tableCode" title="表名" width="100"/>
            <bc:column field="tableName" title="表中文名" width="100"/>
            <bc:column field="objectType" title="对象类型" formatter="fixedDict:MetaTableObjectType" width="100"/>
        </bc:table>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    /* 查询 */
    function searchHanlder() {
        $('#metaTableTable').jTable('setExtQueryParams', {
            schema: $('#schema').val(),
            tableCodes: $('#tableName').val()
        });
        $('#metaTableTable').jTable('refresh');
        $('#metaTableTable').bootstrapTable('resetView', {height: 360});
    }

    /* 同步 */
    function synHanlder() {
        var tables = $('#metaTableTable').bootstrapTable('getSelections');
        if (tables.length > 0) {
            var tableJSON = JSON.stringify(tables);
            var obj = {
                mId: '${regionId}',
                mts: tableJSON
            }

            $.mask.post('${base}/sys/metaTable/syncCheckMetaTable', obj, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        $.messager.hide('synMetatable');
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }
    }
</script>
</body>
</html>