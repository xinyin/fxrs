<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>用户加入用户组</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="${staff.staffName}" subTitle="加入用户组" col="12" collapsible="true">
            <bc:table id="staffGroupTable" idField="staffGroupCode" url="${base}/sys/staffGroup"
                      multiple="true" selectFirst="false" initCheckData="${staffGroups }">
                <bc:toolbar id="save" text="保存设定" type="button" handler="save"/>
                <bc:toolbar id="back" text="返回" type="button" icon="arrow-circle-left" handler="back"/>
                <bc:column field="staffGroupCode" title="用户组编码" width="100"/>
                <bc:column field="staffGroupName" title="用户组名称" width="100"/>
                <bc:column field="staffGroupDesc" title="用户组描述" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function save() {
        var data = $('#staffGroupTable').jTable('getCheckData');
        var obj = {
            jsonData: JSON.stringify(data),
            staffCode: '${staff.staffCode}'
        }

        $.mask.post('${base}/sys/staffGroupMap/saveStaffGroup', obj, function (data) {
            if (data.success) {
                $.messager.alert('提示', '设置成功。', 'info', function () {
                    window.location = '${base}/sys/staff/list';
                });
            } else {
                $.messager.alert('错误', data.msg, 'error');
            }
        });
    }

    function back() {
        window.location = '${base}/sys/staff/list';
    }
</script>
</body>
</html>