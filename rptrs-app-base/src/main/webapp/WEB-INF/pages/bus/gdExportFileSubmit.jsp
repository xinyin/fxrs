<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>股东导出文件记录表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <%--					<bf:formGroup col="4">--%>
                    <%--						<bc:label text="文件名称" col="5" formControl="true"/>--%>
                    <%--						<bf:text name="__LIKES_name" col="7" placeholder="主键"/>--%>
                    <%--					</bf:formGroup>--%>
                    <%--					<bf:formGroup col="4">--%>
                    <%--						<bc:label text="文件生产时间" col="5" formControl="true"/>--%>
                    <%--						<bf:datebox name="__GES_beginDate" col="4" format="date">--%>
                    <%--						</bf:datebox>--%>
                    <%--						<bf:datebox name="__LES_endDate" col="4" format="date">--%>
                    <%--						</bf:datebox>--%>
                    <%--					</bf:formGroup>--%>
                    <%--					<bf:formGroup col="4">--%>
                    <%--						<bc:label text="审批状态" col="5" formControl="true"/>--%>
                    <%--						<bf:select name="__LIKES_status" col="7" dataSource="custom"--%>
                    <%--								   url="sys/dictItem/getDictItems/BUS-Applystatus">--%>
                    <%--							<bf:option text="ALL" value="%"></bf:option>--%>
                    <%--							<be:event type="select">--%>
                    <%--								var input = $("#__LIKES_status");--%>
                    <%--								input.focus();--%>
                    <%--								input.blur();--%>
                    <%--							</be:event>--%>
                    <%--						</bf:select>--%>
                    <%--					</bf:formGroup>--%>
                    <%--					<bc:col col="3" colOffset="1">--%>
                    <%--						<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
                    <%--						<bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>--%>
                    <%--					</bc:col>--%>
                </bc:row>
            </bf:form>
            <bc:table id="gdExportFileTable" idField="pid" url="${base}/bus/gdExportFile" queryUrl="submitInfo/${pid}">
                <%--				<bc:toolbar id="addNewFile" text="新增导出文件" type="button" handler="addHandler"/>--%>
                <%--				<bc:toolbar id="sub" text="提交审批" type="button" handler="subHandler"/>--%>
                <%--				<bc:toolbar id="explore" text="导出" type="button" handler="exploreHandler"/>--%>
                <%--				<bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
                <%--				<bc:toolbar id="view" text="查看" type="button" handler="viewHandler"/>--%>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="name" title="文件名称" width="100"/>
                <bc:column field="fileCreateTime" title="文件生成时间" width="100"/>
                <bc:column field="status" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="batchNumber" title="数据批次" width="100" visible="false"/>
                <bc:column field="url" title="文件路径" width="100" visible="false"/>
                <bc:operator id="viewData" icon="eye" title="查看" handler="viewHandler"/>
                <%--				<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--				<bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
            <bc:buttonGroup cls="btn-group">
                <bc:button type="success" outline="true" text="确认提交" icon="save" click="sub()">
                    <be:event>

                    </be:event>
                </bc:button>
                <bc:button type="info" outline="true" text="返回">
                    <be:event>
                        window.location = '${base}/bus/gdExportFile/list';
                    </be:event>
                </bc:button>
            </bc:buttonGroup>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
</body>
<script>
    function addHandler() {
        window.location = '${base}/bus/gdFileData/list';
    }

    function getBatchNumberSelections() {
        return $.map($('#gdExportFileTable').bootstrapTable('getSelections'), function (row) {
            return row.batchNumber;
        });
    }

    function getIdSelections() {
        return $.map($('#gdExportFileTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function viewHandler() {
        var batchNumber = getBatchNumberSelections();
        window.location = '${base}/bus/gdFileData/view/' + batchNumber;
    }

    function removeHandler() {
        var pid = getIdSelections();
        $.ajax({
            url: '${base}/bus/gdExportFile/remove/' + pid,
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if (data.success) {
                    alert(data.msg);
                    window.location = '${base}/bus/gdExportFile/list';
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    function subHandler() {
        var pid = getIdSelections();
        window.location = '${base}/bus/gdExportFile/submit/' + pid;
    }

    function sub() {
        var pid = getIdSelections();
        $.ajax({
            url: "${base}/bus/gdExportFile/changeApplyStatus/" + pid,
            type: "post",
            data: "json",
            success: function (data) {
                if (data.success) {
                    var objData = {
                        pid: pid
                    };
                    var myDate = new Date();
                    objData.id = myDate.getTime();
                    objData.type = "export";
                    data = JSON.stringify(objData);
                    var formData = $.helper.base64Encode(data);
                    var obj = {
                        procDefCode: 'gd',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }
                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/workflow/process/runingList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else {
                    alert(data.msg);
                }
            }
        })
    }
</script>
</html>