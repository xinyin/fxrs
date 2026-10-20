<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>信息发布</title>
    <style>
        .form-group select {
            width: 100% !important;
        }

        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
    </style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="创建时间" col="5" formControl="true"/>
                        <bf:datebox id="createTime" name="__EQS_createTime" col="7"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="发布状态" col="5" formControl="true"/>
                        <bf:select name="__EQS_releaseType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Release">
                            <bf:option text="全部" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_releaseType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="置顶状态" col="5" formControl="true"/>
                        <bf:select name="__EQS_topType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS_TopStatus">
                            <bf:option text="全部" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_topType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>

                </bc:row>
            </bf:form>
            <bc:table id="dlyTable" idField="pid" url="${base}/bus/msgDeploy">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="release" text="发布" type="button" handler="inRelease"/>
                <bc:toolbar id="unrelease" text="取消发布" type="button" handler="unRelease"/>
                <bc:toolbar id="top" text="置顶" type="button" handler="inTop"/>
                <bc:toolbar id="untop" text="取消置顶" type="button" handler="unTop"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="busName" title="信息标题" width="100"/>
                <bc:column field="busWay" title="信息类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-MsgType"/>
                <bc:column field="createTimeString" title="创建时间" width="100"/>
                <bc:column field="releaseType" title="发布状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Release"/>
                <bc:column field="releaseTimeString" title="发布时间" width="100"/>
                <bc:column field="topType" title="置顶状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS_TopStatus"/>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //编辑
    function editHandler() {

        var pid = getIdSelections();
        var releaseType = getReleaseTypeSelections()
        if (releaseType == 'YFB') {
            $.messager.alert('错误', "只能编辑未发布状态的数据", 'error');
            return;
        }

        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            window.location = '${base}/bus/msgDeploy/edit/' + pid;
        }


    }

    /*获取pid*/
    function getIdSelections() {
        return $.map($('#dlyTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    //获取发布状态
    function getReleaseTypeSelections() {
        return $.map($('#dlyTable').bootstrapTable('getSelections'), function (row) {
            return row.releaseType;
        });
    }

    //获取置顶状态
    function getTopTypeSelections() {
        return $.map($('#dlyTable').bootstrapTable('getSelections'), function (row) {
            return row.topType;
        });
    }

    //删除
    function removeHandler() {
        var pid = getIdSelections();
        var releaseType = getReleaseTypeSelections()
        if (releaseType == 'YFB') {
            $.messager.alert('错误', "只能删除未发布状态的数据", 'error');
            return;
        }
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            $.ajax({
                url: '${base}/bus/msgDeploy/remove/' + pid,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/msgDeploy/list';
                    } else {
                        $.messager.alert(data.msg);
                    }
                }
            });

        }

        <%--window.location = "${base}/bus/rel/removeOrg/" + relId;--%>
    }

    //发布状态
    function inRelease() {
        var pid = getIdSelections();
        var releaseType = getReleaseTypeSelections()
        if (releaseType == 'YFB') {
            $.messager.alert('错误', "该数据已发布", 'error');
            return;
        }
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            $.ajax({
                url: '${base}/bus/msgDeploy/inRelease/' + pid,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/msgDeploy/list';
                    } else {
                        $.messager.alert(data.msg);
                    }
                }
            });

        }
    };

    //取消发布状态
    function unRelease() {
        var pid = getIdSelections();
        var releaseType = getReleaseTypeSelections()
        if (releaseType == 'WFB') {
            $.messager.alert('错误', "该数据已经为未发布状态", 'error');
            return;
        }
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            $.ajax({
                url: '${base}/bus/msgDeploy/unRelease/' + pid,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/msgDeploy/list';
                    } else {
                        $.messager.alert(data.msg);
                    }
                }
            });
        }
    }

    //置顶状态
    function inTop() {
        var pid = getIdSelections();
        var releaseType = getReleaseTypeSelections();
        if (releaseType == 'WFB') {
            $.messager.alert('错误', "只能置顶已发布状态的数据", 'error');
            return;
        }
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            $.ajax({
                url: '${base}/bus/msgDeploy/inTop/' + pid,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/msgDeploy/list';
                    } else {
                        $.messager.alert(data.msg);
                    }
                }
            });
        }
    }

    //取消置顶状态
    function unTop() {
        var pid = getIdSelections();
        var topType = getTopTypeSelections();
        var releaseType = getReleaseTypeSelections()
        if (topType == 'WZD' || releaseType=='WFB') {
            $.messager.alert('错误', "该数据已为未置顶状态", 'error');
            return;
        }
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        } else {
            $.ajax({
                url: '${base}/bus/msgDeploy/unTop/' + pid,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/msgDeploy/list';
                    } else {
                        $.messager.alert(data.msg);
                    }
                }
            });
        }
    }
    function frmReset() {
    $("#queryForm")[0].reset();
    }


</script>
</body>
</html>