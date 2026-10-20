<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>字典管理</title>
    <script type="text/javascript">

        /* 新增 字典 */
        var dictCateCode;

        function onClick(event, treeId, treeNode) {
            dictCateCode = treeNode.dictCateCode
        }

        function addDict() {
            if (dictCateCode != undefined) {
                $.messager.modal({
                    winId: 'winDictEdit',
                    width: 800,
                    height: 300,
                    title: '新增字典',
                    url: '${base}/sys/dict/add?dictCateCode=' + dictCateCode,
                    callback: result,
                    onClose: function () {
                        $('#dictTable').bootstrapTable('refresh');
                    }
                });
            } else {
                $.messager.toastr('提示', '请先选择字典树类型');
            }
        }

        /* 编辑字典 */
        function editDict(e, value, row, index) {
            $.messager.modal({
                winId: 'winDictEdit',
                width: 800,
                height: 300,
                title: '编辑字典',
                url: '${base}/sys/dict/edit/' + row.dictCode,
                callback: result,
                onClose: function () {
                    $('#dictTable').bootstrapTable('refresh');
                }
            });
        }

        function result() {
            console.log('result fn');
        }

        /* 添加字典项 */
        function addDictItem() {
            var row = $('#dictTable').bootstrapTable('getSelections');
            if (row.length != 0) {
                $.messager.modal({
                    winId: 'winDictItemEdit',
                    width: 800,
                    height: 330,
                    title: '编辑字典项',
                    url: '${base}/sys/dictItem/add?dictCode=' + row[0].dictCode + '&dictCateCode=' + row[0].dictCateCode,
                    callback: result,
                    onClose: function () {
                        $('#dictItemTable').bootstrapTable('refresh');
                    }
                });
            } else {
                $.messager.toastr('提示', '请先选择一条字典数据');
            }
        }

        /* 编辑字典项 */
        function editDictItem(e, value, row, index) {

            $.messager.modal({
                winId: 'winDictItemEdit',
                width: 800,
                height: 330,
                title: '编辑字典项',
                url: '${base}/sys/dictItem/edit/' + row.itemId,
                callback: result,
                onClose: function () {
                    $('#dictItemTable').bootstrapTable('refresh');
                }
            });
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxDict" title="字典管理" col="12" collapsible="true">
            <bc:col col="3">
                <bc:tree id="dictCateTree" url="${base}/sys/dictCate" edit="false" autoParam="id=parentId">
                    <be:event type="onClick">onClick</be:event>
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bc:row cls="m-b">
                    <bf:form id="queryForm" validation="true">
                        <bc:row>
                            <bf:formGroup col="4">
                                <bc:label text="字典编码" col="4" formControl="true"/>
                                <bf:text name="__LIKES_dictCode" col="8">
                                    <bv:jepCode/>
                                </bf:text>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="字典名称" col="4" formControl="true"/>
                                <bf:text name="__LIKES_dictName" col="8"/>
                            </bf:formGroup>
                            <bc:col col="3" colOffset="1">
                                <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                                <bc:button id="btnReset" text="重置" icon="refresh"/>
                            </bc:col>
                        </bc:row>
                    </bf:form>
                    <bc:table id="dictTable" idField="dictCode" url="${base}/sys/dict"
                              childField="dictCateCode" childFieldType="S" parentType="tree"
                              parentId="dictCateTree" parentField="dictCateCode">
                        <bc:toolbar id="addDict" text="新增" type="button" handler="addDict"/>
                        <bc:column field="dictCode" title="字典编码" width="100"/>
                        <bc:column field="dictName" title="字典名称" width="100"/>
                        <bc:column field="dictDesc" title="字典描述" width="100"/>
                        <bc:operator id="editDict" icon="edit" title="编辑" handler="editDict"/>
                        <bc:operator id="remove" icon="trash" title="移除"/>
                    </bc:table>
                </bc:row>
                <bc:row cls="m-b">
                    <bc:table id="dictItemTable" idField="itemId" url="${base}/sys/dictItem"
                              childField="dictCode" childFieldType="S" parentType="table" parentId="dictTable"
                              parentField="dictCode">
                        <bc:toolbar id="addDictItem" text="新增" type="button" handler="addDictItem"/>
                        <bc:column field="itemLabel" title="字典项名称" width="100"/>
                        <bc:column field="itemValue" title="字典项值" width="100"/>
                        <bc:column field="itemDesc" title="字典项描述" width="100"/>
                        <bc:operator id="editDictItem" icon="edit" title="编辑" handler="editDictItem"/>
                        <bc:operator id="remove" icon="trash" title="移除"/>
                    </bc:table>
                </bc:row>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>