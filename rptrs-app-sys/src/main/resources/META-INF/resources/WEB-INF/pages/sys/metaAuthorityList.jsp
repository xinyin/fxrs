<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>元数据授权，包括操作权限与数据权限。</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bc:col col="3">
                <bc:table id="roleTable" idField="roleCode" url="${base}/sys/role" showRefresh="false"
                          showColumns="flase">
                    <bc:column field="roleCode" title="角色编码" width="100"/>
                    <bc:column field="roleName" title="角色名称" width="100"/>
                    <be:event type="onClickRow">roleSelect</be:event>
                    <be:event type="onCheck">roleSelect</be:event>
                </bc:table>
            </bc:col>
            <bc:col col="6">
                <bc:row cls="m-b">
                    <bf:formGroup></bf:formGroup>
                    <div class="col-sm-10">
                        <bc:button type="primary" text="设置">
                            <be:event>
                                $.messager.modal({
                                url : '${base}/sys/metaAuthority/metaAuthorityDlg?roleCode=' + roleCode,
                                closable : true,
                                width : 850,
                                height : 500,
                                title : '设置权限',
                                winId: 'metaAuthorityDlg',
                                args: {},
                                onClose: function(){
                                $('#metaRegionTree').jTree('reload');
                                $('#permitSet').jTable('setExtQueryParams');
                                $('#permitSet').jTable('refresh');
                                }
                                });
                            </be:event>
                        </bc:button>
                    </div>
                </bc:row>
                <div class="row" style="height: 350px; overflow: scroll; overflow-x:hidden ">
                    <bc:tree id="metaRegionTree" url="${base}/sys/metaRegion" queryUrl="getRegionTreeByRoleCode"
                             edit="false" autoParam="id=parentId">
                        <be:event type="onClick">onClick</be:event>
                    </bc:tree>
                </div>
            </bc:col>
            <bc:col col="3">
                <bc:row cls="m-b">
                    <bf:formGroup></bf:formGroup>
                    <div class="col-sm-10">
                        <bc:button type="primary" text="保存" icon="save">
                            <be:event>
                                var permitTypes = $('#permitSet').jTable('getSelections');
                                if(permitTypes.length > 0){
                                $.ajax({
                                type : 'POST',
                                url : '${base}/sys/metaAuthority/saveAuthorityBelongDAK',
                                data:{'roleCode':roleCode,'tableId':tableId,'idStr': permitTypes[0]['value']},
                                dataType : 'json',
                                success : function(data) {
                                $.messager.alert('系统提示', data.msg, 'info');
                                }
                                });
                                }
                                else
                                {
                                $.messager.alert('系统提示', '请选择要赋值的数据权限。', 'info');
                                }

                            </be:event>
                        </bc:button>
                    </div>
                </bc:row>
                <bc:table id="permitSet" idField="value" url="${base}/sys/metaAuthority" queryUrl="permitTypes"
                          selectFirst="false" multFormatter="isChecked"
                          childField="value" childFieldType="S" parentType="tree" parentId="metaRegionTree"
                          parentField="id" pagination="flase" showRefresh="false" showColumns="flase">
                    <bc:column field="value" title="数据权限代码" width="100"/>
                    <bc:column field="name" title="数据权限名称" width="100"/>
                </bc:table>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var roleCode;
    var tableId;

    function roleSelect(row) {
        roleCode = row['roleCode'];
        /* 初始化属性集*/
        var options = {
            toggle: "jTree",
            otherParam: ['roleCode', row['roleCode']]
        };
        $('#metaRegionTree').jTree('reload', options);
        $('#permitSet').jTable('setExtQueryParams');
        $('#permitSet').jTable('refresh');
    }

    function onClick(e, obj, node) {
        tableId = node.id;
        if (!roleCode) {
            $.messager.alert('系统提示', "请选择对应的角色。", 'info');
            return;
        }

        var data = {
            roleCode: roleCode,
            tableId: node.id,
            type: node.type
        };
        $('#permitSet').jTable('clearCheckData');
        $('#permitSet').jTable('setExtQueryParams', data);
        $('#permitSet').jTable('refresh');
    }

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

</script>
</body>
</html>