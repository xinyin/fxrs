<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>GdTraPf</title>
    <script>


        //重置
        function frmReset() {
            window.location = '${base}/bus/gdTraPf/list';
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="派发年份" col="5" formControl="true"/>
                        <bf:text id="year" name="__LIKES_year" col="7" placeholder="派发年份"/>
                    </bf:formGroup>
                    <%--<bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                    </bc:col>--%>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="gdTraPfTable" idField="pid" url="${base}/bus/gdTraPf">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="sub" text="提交审批" type="button" handler="subHandler"/>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="name" title="派发方案名称" width="100"/>
                <bc:column field="year" title="派发年份" width="100"/>
                <bc:column field="createTime" title="文件生成时间" width="100"/>
                <bc:column field="totalShare" title="当前总股本" width="100"/>
                <bc:column field="allocationAmount" title="股利分配额" width="100"/>
                <bc:column field="allocationProportion" title="股利分配比例" width="100"/>
                <bc:column field="cashBonus" title="现金分红" width="100"/>
                <bc:column field="bonusIssue" title="派送红股" width="100"/>
                <bc:column field="status" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="route" title="路径" width="100" visible="false"/>
                <bc:operator id="view" icon="search" title="查看"/>
                <%--				<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--				<bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
<script>

    function getIdSelections() {
        return $.map($('#gdTraPfTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getStatusSelections() {
        return $.map($('#gdTraPfTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function editHandler() {
        var status = getStatusSelections();
        if ("4" == status) {
            alert('审批中不能编辑');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/gdTraPf/edit/' + pid;
    }

    function subHandler() {
        var status = getStatusSelections();
        if ("3" != status) {
            alert('不能提交审批');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/gdTraPf/submit/' + pid;
    }

    function removeHandler() {
        var status = getStatusSelections();
        if ("3" != status) {
            alert('已审批不能删除');
            return false;
        }
        if (confirm('确认删除？')) {
            var pid = getIdSelections();
            $.ajax({
                url: '${base}/bus/gdTraPf/remove/' + pid,
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        alert(data.msg);
                        window.location = '${base}/bus/gdTraPf/list';
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    }

    function exportHandler() {
        var pid = getIdSelections();
        var url = '${base}/bus/gdTraPf/selectGdTraPfExportByYear/'+pid;

        var elemIf=document.createElement("iframe");
        elemIf.src=url;
        elemIf.style.display="none";
        document.body.appendChild(elemIf);
        //window.location='${base}/bus/gdTraPf/selectGdTraPfExportByYear/'+pid;
         // window.open(url);
    }
</script>
</html>