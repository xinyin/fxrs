<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Staff</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="用户管理" col="12" collapsible="true">
            <bc:col col="3">
                <bc:tree id="unitTree" url="${base}/sys/unit" edit="false" autoParam="id=parentId">
                    <be:event type="onClick">onClick</be:event>
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="queryForm" validation="true">
                    <bf:hidden name="__LIKES_status" value="ENABLE"/>
                    <bc:row cls="m-b">
                        <bf:formGroup col="8">
                            <bc:col col="6">
                                <bc:label text="用户编码" col="4" formControl="true"></bc:label>
                                <bf:text name="__LIKES_staffCode" col="8">
                                    <bv:jepCode/>
                                </bf:text>
                            </bc:col>
                            <bc:col col="6">
                                <bc:label text="用户姓名" col="4" formControl="true"></bc:label>
                                <bf:text name="__LIKES_staffName" col="8"/>
                            </bc:col>
                        </bf:formGroup>
                        <bc:col col="3" colOffset="1">
                            <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                            <bc:button id="btnReset" text="重置" icon="refresh"/>
                        </bc:col>
                    </bc:row>
                </bf:form>
                <bc:table id="staffTable" idField="staffCode" url="${base}/sys/staff"
                          childField="deptCode" childFieldType="S" parentType="tree" parentId="unitTree"
                          parentField="unitCode">
                    <bc:toolbar id="addStaff" text="新增" type="button" handler="addStaff"/>
                    <bc:toolbar id="setRole" text="设定角色" type="button" handler="setRole"/>
                    <bc:toolbar id="setRoleGroup" text="设定角色组" type="button" handler="setRoleGroup"/>
                    <bc:toolbar id="setStaffGroup" text="加入用户组" type="button" handler="setStaffGroup"/>
                    <bc:toolbar id="setProxy" text="借调" type="button" handler="setProxy"/>
                    <bc:column field="staffCode" title="用户编码" width="100"/>
                    <bc:column field="staffName" title="用户名称" width="100"/>
                    <bc:column field="roleName" title="关联的角色" width="100"/>
                    <bc:column field="staffGroupName" title="所在用户组" width="100"/>
                    <bc:column field="mobile" title="移动电话" width="80"/>
                    <bc:column field="status" title="状态" width="80" formatter="fixedDict:StaffStatus"/>
                    <bc:column field="isProxy" title="借调" width="80" formatter="fixedDict:YesNo"/>
                    <bc:operator id="edit" icon="edit" title="编辑"/>
                    <bc:operator id="view" icon="file-o" title="查看"/>
                </bc:table>
            </bc:col>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    var unitCode = undefined;

    function onClick(event, treeId, treeNode) {
        unitCode = treeNode.unitCode
    }

    function addStaff() {
        if (unitCode != undefined) {
            $.ajax({
                type: 'GET',
                url: '${base}/sys/unit/isOrg?unitCode=' + unitCode,
                dataType: 'json',
                success: function (data) {
                    if (data.valid) {
                        $.messager.toastr('提示', '请选择具体的部门添加用户');
                    } else {
                        window.location = '${base}/sys/staff/add?deptCode=' + unitCode;
                    }
                }
            });
        } else {
            $.messager.toastr('提示', '请先选择机构');
        }
    }

    /* 设定角色 */
    function setRole() {
        var row = $('#staffTable').bootstrapTable('getSelections');
        if (row.length != 0) {
            window.location = '${base}/sys/staff/setRoleStaff?unitCode=' + unitCode + '&staffCode=' + row[0].staffCode;
        } else {
            $.messager.toastr('提示', '请选择一个用户');
        }
    }

    /* 设定角色组*/
    function setRoleGroup() {
        var row = $('#staffTable').bootstrapTable('getSelections');
        if (row.length != 0) {
            window.location = '${base}/sys/staff/setRoleGroupStaff?unitCode=' + unitCode + '&staffCode=' + row[0].staffCode;
        } else {
            $.messager.toastr('提示', '请选择一个用户');
        }
    }

    /*加入用户组*/
    function setStaffGroup() {
        var row = $('#staffTable').bootstrapTable('getSelections');
        if (row.length != 0) {
            window.location = '${base}/sys/staffGroupMap/setStaffGroup?staffCode=' + row[0].staffCode;
        } else {
            $.messager.toastr('提示', '请选择一个用户');
        }
    }

    function setProxy() {
        var row = $('#staffTable').bootstrapTable('getSelections');
        if (row.length == 0) {
            $.messager.toastr('提示', '请选择一个用户');
        } else {
            window.location = '${base}/sys/staffProxyLog/list?staffCode=' + row[0].staffCode;
            /* $.messager.modal({
                winId: 'winSecondment',
                width: 600,
                height: 300,
                title: '选择借调部门',
                url: '
            ${base}/sys/staff/secondment',
			args: {
				'staffCode': row[0].staffCode,
				'deptCode': unitCode
			},
			buttons: [
				{
					text : '关闭',
					icon : 'times',
					type : 'danger',
					value : false
				}
			],
			callback: result,
			onClose: function(){
				$('#staffTable').bootstrapTable('refresh');
			}
		}); */
        }
    }

    function result() {
    }

</script>
</body>
</html>