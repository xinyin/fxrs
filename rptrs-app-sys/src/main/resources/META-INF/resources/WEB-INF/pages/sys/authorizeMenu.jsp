<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>授权管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">

        <bc:ibox title="${name}" subTitle="授权菜单、功能及数据权限" col="12" collapsible="true">
            <%--<bf:form id="queryForm">
                <bf:hidden name="reqMenuCode" id="reqMenuCode"></bf:hidden>
            </bf:form>--%>
            <bc:row cls="m-b">
                <bc:col col="5">
                    <bc:tree id="menuTree" url="${base}/sys/menu" edit="false" check="true"
                             formId="menuForm" autoParam="id=parentId">
                        <be:event type="onClick">onClick</be:event>
                    </bc:tree>
                </bc:col>
                <bc:col col="7">
                    <bc:table id="permitSet" idField="value" url="${base}/permit" queryUrl="permitTypes"
                              selectFirst="false" multFormatter="isChecked"
                              childField="menuCode" childFieldType="S" parentType="tree" parentId="menuTree"
                              parentField="menuCode" multiple="true">
                        <bc:toolbar id="authorizePermit" text="确定" type="button" icon=""
                                    handler="authorizePermitToRole"/>
                        <%--                        <bc:column field="functionId" title="自增长主键" width="100" visible="false"/>--%>
                        <bc:column field="value" title="功能编码" width="100"/>
                        <bc:column field="name" title="功能名称" width="100"/>
                    </bc:table>
                </bc:col>
            </bc:row>
            <bc:row cls="m-b">

                <div align="center">
                    <bc:button type="primary" text="保存" icon="save">
                        <be:event>
                            var changeAllSelectNodes=[];
                            var changeCancelNodes=[];
                            var changePartSelectNodes=[];

                            var treeObj = $.fn.zTree.getZTreeObj("_jTree_menuTree");
                            var changeRows = treeObj.getChangeCheckedNodes();
                            $.each(changeRows,function(i,item){
                            if(item.checked){
                            if(item.check_Child_State==2){
                            changeAllSelectNodes.push(item);
                            }else if(item.check_Child_State==1||item.check_Child_State==-1){
                            changePartSelectNodes.push(item);
                            }
                            }else{
                            changeCancelNodes.push(item);
                            }
                            });

                            var obj = {
                            changeAllSelectNodes: JSON.stringify(changeAllSelectNodes),
                            changePartSelectNodes: JSON.stringify(changePartSelectNodes),
                            changeCancelNodes: JSON.stringify(changeCancelNodes),
                            code: '${code}',
                            type: '${type}'
                            }

                            $.mask.post('${base}/sys/authorization/save', obj, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/sys/authorization/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                        </be:event>
                    </bc:button>
                    <bc:button text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/sys/authorization/list';
                        </be:event>
                    </bc:button>
                </div>
            </bc:row>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function onClick(event, treeId, treeNode) {
        // alert(treeNode.menuCode);
        // $('#reqMenuCode').val(treeNode.menuCode);
        $('#permitSet').jTable('clearCheckData');
        $('#permitSet').jTable('setExtQueryParams', {
            'menuCode': treeNode.menuCode,
            'type': '${type}',
            'roleCode': '${code}'
        });
        $('#permitSet').jTable('refresh');

    }

    //功能编码
    function getIdSelections() {
        return $.map($('#permitSet').bootstrapTable('getSelections'), function (row) {
            return row.value;
        });
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

    function authorizePermitToRole(e) {
        var permitTypes = getIdSelections();
        // var permitTypes = $('#permitSet').jTable('getCheckData');
        var menus = $('#menuTree').jTree('getSelectedNodes');
        // alert(permitTypes);
        // console.info(permitTypes);
        if (permitTypes.length > 0 && menus.length > 0) {
            var menuCode = menus[0]['menuCode'];
            // var permitType = permitTypes[0]['value'];
            // var permitType = permitTypes[0];
            var roleCode = '${code}', type = '${type}';
            var data = {
                menuCode: menuCode,
                permitType: permitTypes.toString(),
                roleCode: roleCode,
                type: type
            }

            $.mask.post('${base}/sys/authorization/savePermit', data, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info');
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        } else {
            $.messager.alert('提示', '请选择需要授权的数据权限。', 'info');
        }
    }

    $(document).ready(function () {


        var treeObj = $.fn.zTree.getZTreeObj("_jTree_menuTree");

        /*禁用树节点的URL*/
        treeObj.setting.data.key.url = "fasle";

        /*异步加载数据成功后，勾选中已设置的菜单*/
        treeObj.setting.callback.onAsyncSuccess = function (event, treeId, treeNode, msg) {

            var jsonMsg = JSON.parse(msg);

            var nodes = '${nodes}'
            var json = JSON.parse(nodes);

            for (var i = 0; i < json.length; i++) {
                for (var j = 0; j < jsonMsg.length; j++) {
                    if (json[i].id == jsonMsg[j].id) {
                        var node = treeObj.getNodeByParam('id', json[i].id);
                        if (node != null) {
                            treeObj.checkNode(node);
                            if (node.checked) {
                                node.checkedOld = true;
                            } else {
                                node.checkedOld = false;
                            }
                        }
                    }
                }
            }
        }
    });
</script>
</body>
</html>