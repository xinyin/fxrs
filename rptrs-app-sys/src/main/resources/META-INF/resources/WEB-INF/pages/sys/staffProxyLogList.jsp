<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>借调管理</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="借调管理" col="12" collapsible="true">
            <bc:col col="3">
                <bc:tree id="unitTree" url="${base}/sys/unit" edit="false" autoParam="id=parentId">
                </bc:tree>
            </bc:col>
            <bc:col col="9">
                <bf:form id="queryForm" validation="true">
                    <bc:row cls="m-b">
                        <bf:formGroup col="9">
                            <bc:col col="6">
                                <bc:label text="用户编码" col="4" formControl="true"/>
                                <bf:text name="__LIKES_staffCode" col="8">
                                    <bv:jepCode/>
                                </bf:text>
                            </bc:col>
                            <bc:col col="6">
                                <bc:label text="用户姓名" col="4" formControl="true"/>
                                <bf:text name="__LIKES_staffName" col="8"/>
                            </bc:col>
                        </bf:formGroup>
                        <bc:col col="3">
                            <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>
                            <bc:button id="btnReset" text="重置" icon="refresh"/>
                        </bc:col>
                    </bc:row>
                </bf:form>
                <bc:table id="staffTable" idField="staffCode" url="${base}/sys/staff"
                          childField="deptCode" childFieldType="S" parentType="tree" parentId="unitTree"
                          parentField="unitCode">
                    <bc:toolbar id="setProxyLog" text="借调" type="button" handler="setProxyLog"/>
                    <bc:column field="staffCode" title="用户编码" width="100"/>
                    <bc:column field="staffName" title="用户名称" width="100"/>
                    <bc:column field="roleName" title="关联的角色" width="100"/>
                    <bc:column field="staffGroupName" title="所在用户组" width="100"/>
                    <bc:column field="mobile" title="移动电话" width="100"/>
                    <bc:column field="status" title="状态" width="100" formatter="fixedDict:StaffStatus"/>
                    <bc:column field="isProxy" title="是否被借调" width="100" formatter="fixedDict:YesNo"/>
                </bc:table>
            </bc:col>
        </bc:ibox>
        <bc:ibox title="过往借调记录" subTitle="" col="12" collapsible="true">
            <bc:table id="staffProxyLogTable" idField="id" url="${base}/sys/staffProxyLog"
                      childField="staffCode" childFieldType="S" parentType="table" parentId="staffTable"
                      parentField="staffCode">
                <bc:toolbar id="cancelProxy" text="取消借调" type="button" handler="cancelProxy"/>
                <bc:column field="proxyStaffCode" title="借调人编码" width="100"/>
                <bc:column field="proxyStaffName" title="借调人名称" width="100"/>
                <bc:column field="startDateString" title="借调起始时间" width="100"/>
                <bc:column field="preEndDateString" title="预计借调结束时间" width="100"/>
                <bc:column field="endDateString" title="实际结束时间" width="100"/>
                <bc:column field="deptName" title="借调部门名称" width="100"/>
                <bc:column field="status" title="状态" width="100" formatter="fixedDict:StaffProxyLogStatus"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function setProxyLog() {
        var row = $('#staffTable').bootstrapTable('getSelections');
        if (row.length == 0) {
            $.messager.toastr('提示', '请选择一个用户');
        } else {
            window.location = '${base}/sys/staffProxyLog/add?staffCode=' + row[0].staffCode;
        }
    }

    function cancelProxy() {
        var row = $('#staffProxyLogTable').bootstrapTable('getSelections');
        if (row.length == 0) {
            $.messager.toastr('提示', '请选择一个借调的用户');
        } else {
            var obj = {proxyStaffCode: row[0].proxyStaffCode}
            $.mask.post('${base}/sys/staffProxyLog/remove', obj, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        window.location = '${base}/sys/staffProxyLog/list';
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