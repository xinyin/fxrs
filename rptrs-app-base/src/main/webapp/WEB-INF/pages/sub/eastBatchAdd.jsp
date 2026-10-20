<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bc:table id="eastBatchAdd" idField="id" url="${base}/east/batch" queryUrl="addData" multiple="true">
                <bc:column field="fileName" title="全选" width="120"/>
            </bc:table>
            <div align="right">
                <bc:button text="取消" click="returnButton()"/>
                <bc:button type="primary" text="确认生成数据" click="addHandler()"/>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    function addHandler() {
        let ids = getId();
        if (ids === '' || ids === null || ids.length === 0) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        $.mask.show();
        $.ajax({
            url: "${base}/east/batch/distribute/" + ids,
            method: "POST",
            data: {"": ""},
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                $.mask.remove();
                if (data.success) {
                    $.messager.toastr('成功', data.msg, 'success');
                    $.messager.hide('batchAdd');
                    window.location = '${base}/east/batch/list';
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }

    function returnButton() {
        $.messager.hide('batchAdd');
    }

    function getId() {
        return $.map($('#eastBatchAdd').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }

</script>
</body>
</html>
