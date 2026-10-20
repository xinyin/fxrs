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
        <bc:ibox title="" subTitle="" col="12">
            <div style="font-weight: bold; color: red;">请先设置区域权限，再设置表权限。区域权限将优于表权限，如设置表权限后再设置区域权限，表权限将有可能被覆盖。</div>
            <bc:tabs id="tabContent">
                <bc:tab id="tab1" title="区域权限设置" visible="true">
                    <div class="row" style="height: 350px;">
                        <bc:col col="12">
                            <bc:tree id="tree" url="${base}/sys/metaRegion" edit="false"
                                     autoParam="id=parentId" alertType="toastr" check="true">
                            </bc:tree>
                        </bc:col>
                    </div>
                    <div class="row" style="padding-top: 10px;">
                        <bc:col col="6" colOffset="6">
                            <bc:button text="关闭" outline="true">
                                <be:event>
                                    $.messager.hide('metaAuthorityDlg');
                                </be:event>
                            </bc:button>
                            <bc:button text="确定" type="primary">
                                <be:event>
                                    saveRegionAuth();
                                </be:event>
                            </bc:button>
                        </bc:col>
                    </div>
                </bc:tab>
                <bc:tab id="tab2" title="表权限设置">
                    <div class="row" style="height: 350px;">
                        <bc:col col="4">
                            <bc:tree id="metaRegionTree" url="${base}/sys/metaRegion" edit="false"
                                     autoParam="id=parentId">
                                <be:event type="onClick">onClick</be:event>
                            </bc:tree>
                        </bc:col>
                        <bc:col col="8">
                            <div class="row" style="height: 350px; overflow: scroll; overflow-x:hidden ">
                                <bc:table id="metaTableTable" idField="tableId" url="${base}/sys/metaRegionTable"
                                          queryUrl="queryBusinessMetaTabe?roleCode=${roleCode}" pagination="false"
                                          showRefresh="false" showColumns="flase"
                                          childField="regionId" selectFirst="false" multiple="true"
                                          multFormatter="isChecked" childFieldType="S" parentType="tree"
                                          parentId="metaRegionTree" parentField="regionId">
                                    <bc:column field="schema" title="表模式" width="80"/>
                                    <bc:column field="tableCode" title="表名" width="100"/>
                                    <bc:column field="tableName" title="表中文名" width="100"/>
                                    <bc:column field="objectType" title="对象类型" formatter="fixedDict:MetaTableObjectType"
                                               width="60"/>
                                </bc:table>
                            </div>
                        </bc:col>
                    </div>
                    <div class="row" style="padding-top: 10px;">
                        <bc:col col="6" colOffset="6">
                            <bc:button text="关闭" outline="true">
                                <be:event>
                                    $.messager.hide('metaAuthorityDlg');
                                </be:event>
                            </bc:button>
                            <bc:button text="确定" type="primary">
                                <be:event>
                                    saveTable();
                                </be:event>
                            </bc:button>
                        </bc:col>
                    </div>
                </bc:tab>
            </bc:tabs>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var roleCode = '${roleCode}';

    /* 判断是否选中 */
    function isChecked(value, row, index) {
        if (row.state == true) {
            return {
                checked: true//设置选中
            };
        } else {
            return {
                checked: false//设置选中
            };
        }

    }

    function onClick(event, treeId, treeNode) {
        $('#metaTableTable').jTable('clearCheckData');
    }

    //保存按钮
    function saveRegionAuth() {
        var nodes = $('#tree').jTree('getCheckedNodes');
        var idStr = '';
        //对选中的节点进行拼接，选中的状态为 ”勾中“状态。
        for (var i = 0; i < nodes.length; i++) {
            if (idStr != '')
                idStr += ',';
            idStr += nodes[i].id;
        }

        var data = {
            'idStr': idStr,
            'roleCode': roleCode
        };
        $.mask.post("${base}/sys/metaAuthority/saveRegionAuth", data, function (data) {
            $.messager.alert('系统提示', data.msg, 'info', function () {
                if (data.success) {
                    $.messager.hide('metaAuthorityDlg');
                }
            });
        });
    }


    function saveTable() {
        var rows = $('#metaTableTable').jTable('getSelections');
        //$('#metaTableTable').bootstrapTable('method', parameter);

        /* 获取选中的表 */
        var regionTableIds = '';
        for (var i = 0; i < rows.length; i++) {
            regionTableIds += rows[i].regionTableId + ',';
        }

        var nodes = $('#metaRegionTree').jTree('getSelectedNodes');
        /* 判断是否选中数据区域 */
        if (!nodes || nodes.length == 0) {
            $.messager.alert('系统提示', '请选择需要同步元数据表所到的数据区域。', 'info');
        }

        /* 封装传入后台数据 */
        var formData = {
            roleCode: roleCode,
            regionTableIds: regionTableIds,
            regionId: nodes[0].id
        };

        $.mask.post("${base}/sys/metaAuthority/saveTableAuth", formData, function (data) {
            $.messager.alert('系统提示', data.msg, 'info');
        });
    }

    $(document).ready(function () {


        var treeObj = $.fn.zTree.getZTreeObj("_jTree_tree");

        /*禁用树节点的URL*/
        treeObj.setting.data.key.url = "fasle";

        /*异步加载数据成功后，勾选中已设置的菜单*/
        treeObj.setting.callback.onAsyncSuccess = function (event, treeId, treeNode, msg) {

            var jsonMsg = JSON.parse(msg);

            var nodes = '${nodes}';
            var json = JSON.parse(nodes);

            for (var i = 0; i < json.length; i++) {
                for (var j = 0; j < jsonMsg.length; j++) {
                    if (json[i].id == jsonMsg[j].id) {
                        var node = treeObj.getNodeByParam('id', json[i].id);
                        if (node != null) {
                            treeObj.checkNode(node);
                        }
                    }
                }
            }
        }
    });
</script>
</body>
</html>