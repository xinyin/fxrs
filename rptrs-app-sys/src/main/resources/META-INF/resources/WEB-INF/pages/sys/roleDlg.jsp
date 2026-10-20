<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>选择角色</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <bc:ibox id="roleDlg" title="" col="12" collapsible="false">
        <div class="row">
            <bc:col col="6">
                <bc:table id="roleTable" idField="roleCode" url="${base}/sys/role"
                          detailView="false" multiple="true" showRefresh="false" showColumns="false"
                          pagination="false">
                    <bc:column field="roleCode" title="角色编码" width="50"/>
                    <bc:column field="roleName" title="角色名称" width="60"/>
                    <bc:column field="roleStdCode" title="角色类型" width="50" formatter="custom:sys/roleStd/queryAll"/>
                </bc:table>
            </bc:col>
            <bc:col col="6">
                <bc:table id="staffTable" idField="staffCode" url="${base}/sys/role" queryUrl="queryRoleStaff"
                          childField="roleCode" childFieldType="S" parentField="roleCode" parentId="roleTable"
                          parentType="table"
                          showRefresh="false" showColumns="false" multipleVisible="false" pagination="false">
                    <bc:column field="unitName" title="部门" width="70"/>
                    <bc:column field="staffCode" title="用户编码" width="50"/>
                    <bc:column field="staffName" title="用户名称" width="50"/>
                </bc:table>
            </bc:col>

        </div>
        <div class="row">
            <bc:col col="4" colOffset="8">
                <bc:button text="取消" outline="true">
                    <be:event>
                        $.messager.hide('winRoleDlg');
                    </be:event>
                </bc:button>
                <bc:button text="确定" type="primary">
                    <be:event>
                        var nodes = $('#roleTable').jTable('getCheckData');
                        var codeList = new Array();
                        var nameList = new Array();
                        $.each(nodes, function(i, val){
                        codeList.push(val.roleCode);
                        nameList.push(val.roleName);
                        });

                        $.messager.args('winRoleDlg').result([{
                        'roleCode': codeList,
                        'roleName': nameList,
                        }]);
                        $.messager.hide('winRoleDlg');
                    </be:event>
                </bc:button>
            </bc:col>
        </div>
    </bc:ibox>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>