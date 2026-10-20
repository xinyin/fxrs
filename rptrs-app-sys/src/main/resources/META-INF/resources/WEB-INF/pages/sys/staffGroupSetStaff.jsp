<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>添加用户到用户组</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="staffGroupBox" title="${staffGroup.staffGroupName}" subTitle="选定用户" col="12" collapsible="true">
            <bc:table id="staffTable" idField="staffCode" multiple="true" url="${base}/sys/staff"
                      selectFirst="false" initCheckData="${staffList}">
                <bc:toolbar id="finished" text="完成设置" type="button" handler="finishHandler"/>
                <bc:toolbar id="backward" text="返回" type="button" icon="arrow-circle-left" handler="backwardHandler"/>
                <bc:column field="staffCode" title="用户编码" width="100"/>
                <bc:column field="staffName" title="用户名称" width="100"/>
                <bc:column field="mobile" title="电话" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    /* 保存 */
    function finishHandler() {

        var data = $('#staffTable').jTable('getCheckData');

        var obj =
            {
                jsonStaffList: JSON.stringify(data),
                staffGroupCode: '${staffGroup.staffGroupCode}'
            }

        $.mask.post('${base}/sys/staffGroupMap/saveStaffMap', obj, function (data) {
            if (data.success) {
                $.messager.alert('提示', '设置成功。', 'info', function () {
                    window.location = '${base}/sys/staffGroup/list';
                });
            } else {
                $.messager.alert('错误', data.msg, 'error');
            }
        });
    }

    /* 返回 */
    function backwardHandler() {
        window.location = '${base}/sys/staffGroup/list';
    }
</script>
</body>
</html>