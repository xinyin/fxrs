<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>描述元数据的表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="元数据管理" col="12" collapsible="true">
            <bc:col col="2">
                <bc:tree id="metaRegionTree" url="${base}/sys/metaRegion" edit="false" autoParam="id=parentId">
                    <be:event type="onClick">onClick</be:event>
                </bc:tree>
            </bc:col>
            <bc:col col="10">
                <bf:form id="queryForm" validation="true">
                    <bc:row>
                        <bf:formGroup col="4">
                            <bc:label text="表名" col="5" formControl="true"/>
                            <bf:text name="__LIKES_tableCode" col="7" placeholder="表名"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="表中文名" col="5" formControl="true"/>
                            <bf:text name="__LIKES_tableName" col="7" placeholder="表中文名"/>
                        </bf:formGroup>
                        <bc:col col="3" colOffset="1">
                            <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                            <bc:button id="btnReset" text="重置" icon="refresh"/>
                        </bc:col>
                    </bc:row>
                </bf:form>
                <bc:table id="metaTableTable" idField="tableId" url="${base}/sys/metaTable" queryUrl="queryMetatable"
                          childField="regionId" childFieldType="S" parentType="tree" parentId="metaRegionTree"
                          parentField="regionId">
                    <bc:toolbar id="syn" text="同步" type="button" handler="synHandler"/>
                    <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                    <bc:column field="schema" title="表模式" width="100"/>
                    <bc:column field="tableCode" title="表名" width="100"/>
                    <bc:column field="tableName" title="表中文名" width="100"/>
                    <bc:column field="objectType" title="对象类型" formatter="fixedDict:MetaTableObjectType" width="100"/>
                    <bc:operator id="edit1" icon="edit" title="编辑" handler="editColumn"/>
                    <bc:operator id="remove1" icon="trash" title="移除" handler="removeHandler"/>
                </bc:table>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<div style="display: none;">
    <iframe name="exportIframe" src=""></iframe>
    <form id="exportForm" action="${base}/sys/metaTable/exportAll" target="exportIframe" method="post">
        <input type="hidden" id="regionId" name="regionId">
    </form>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var regionId = undefined;

    function onClick(event, treeId, treeNode) {
        regionId = treeNode.regionId;
    }

    function synHandler() {
        if (regionId) {
            $.messager.modal({
                url: '${base}/sys/metaTable/metaSynTable?regionId=' + regionId,
                closable: true,
                width: 850,
                height: 500,
                title: '同步数据表',
                winId: 'synMetatable',
                args: {},
                onClose: function () {
                    $('#metaTableTable').bootstrapTable('refresh');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择需要同步的数据区域。')
        }
    }

    function viewColumn(e, value, row, index) {
        window.location = '${base}/sys/metaColumn/list?tableId=' + row['tableId'];
    }

    function editColumn(e, value, row, index) {
        window.location = '${base}/sys/metaColumn/list?tableId=' + row['tableId'];
    }

    /* 删除事件 */
    function removeHandler(e, value, row, index) {

        $.messager.confirm('提示', '是否确定删除' + row.tableCode + '元数据表？', function (result) {
            /* 提示是否删除，如果是，提交到后台 */
            if (result) {

                var param = {
                    tableId: row.tableId,
                    regionId: regionId,
                    isDel: false
                };
                /* 判断是否是用户创建的试图或者表，如果是，就提示是否删除数据库中的对象 */
                $.mask.post('${base}/sys/metaTable/remove', param, function (data) {
                    console.info(data);
                    /* 删除成功，就给出剩余多少个关系的提示 */
                    if (data.success) {

                        if (data.tableCount == 0) {
                            $.messager.confirm('提示', row.tableCode + '在各个数据区域下已经都不存在了，是否删除该元数据表中的信息？', function (r) {
                                if (r) {
                                    param.isDel = true;
                                    $.mask.post('${base}/sys/metaTable/remove', param, function (data) {
                                        if (data.success) {
                                            $.messager.alert('系统提示', data.msg, 'info');
                                        }
                                    });
                                }
                            });
                        } else {
                            $.messager.alert('系统提示', '该表还存在其他 ' + data.tableCount + '个数据区域中。', 'info');
                        }
                        /* 刷新数据 */
                        $('#metaTableTable').bootstrapTable('refresh');
                    } else {
                        $.messager.alert('系统提示', data.msg, 'info');
                    }
                });
            }
        });
    }

    /* 导出元数据 */
    function exportHandler() {
        var metaTable = $('#metaTableTable').bootstrapTable('getData');
        /* 判断选择的数据是否为空 */
        if (!metaTable || metaTable.length < 1) {
            $.messager.alert('提示', '当前无导出数据。', 'info');
            return;
        }

        /*防止打开新页面，采用post提交，target="exportIframe" */
        /*设置参数*/
        $('#regionId').val(regionId);

        /*提交表单*/
        $("#exportForm").submit();

    }
</script>
</body>
</html>