<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>流程实例表</title>
    <style>
        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
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
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden name="__INS_status" value="50,30"/>
                <bf:hidden name="__EQS_starterCode" value="${userCode}"/>
<%--                <bc:row cls="m-b">--%>
<%--                    <bf:formGroup hrp="bottom">--%>
<%--                        <bf:formGroup col="4">--%>
<%--                            <bc:label text="业务流程名称" col="5" formControl="true"></bc:label>--%>
<%--                            <bf:text name="__LIKES_procName" col="7" placeholder="业务流程名称"></bf:text>--%>
<%--                        </bf:formGroup>--%>
<%--                        <bf:formGroup col="4">--%>
<%--                            <bc:label text="模块名称" col="5" formControl="true"></bc:label>--%>
<%--                            <bf:text name="__LIKES_moduleName" col="7" placeholder="模块名称"></bf:text>--%>
<%--                        </bf:formGroup>--%>
<%--                    </bf:formGroup>--%>
<%--                    <div class="col-sm-12 col-sm-offset-5">--%>
<%--                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>--%>
<%--                        <bc:button id="btnReset" text="重置" icon="refresh"/>--%>
<%--                    </div>--%>
<%--                </bc:row>--%>
            </bf:form>
            <bc:table id="procInstTable" idField="id" url="${base}/workflow/process" queryUrl="queryHistory">
                <bc:toolbar id="viewBiz" text="查看业务数据" type="button" handler="viewBizHandler"/>
                <bc:toolbar id="reject" text="撤回" type="button" handler="rejectStartHandler"/>
                <bc:column field="procCode" title="业务流程编码" width="100" visible="false"/>
                <bc:column field="procName" title="流程名称" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="transType" title="交易类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="businessType" title="业务类型" width="100"/>
                <bc:column field="transactionAmount" title="关联交易金额（万元）" width="100"/>
                <%--				<bc:column field="formName" title="表单名称" width="100"/>--%>
                <bc:column field="starterDeptName" title="发起部门" width="100"/>
                <bc:column field="starterName" title="发起人" width="100"/>
                <bc:column field="startTimeString" title="开始时间" width="100"/>
                <bc:column field="status" title="状态" width="100" formatter="fixedDict:ProcInstStatus"/>
                <%--				<bc:column field="bizNameString" title="相关信息" width="100"/>--%>
                <be:event type="onClickRow">onClickRow</be:event>
                <be:event type="onCheck">onClickRow</be:event>
            </bc:table>

            <bc:tabs id="tabContent">
                <bc:tab id="tab1" title="审批记录" visible="true">
                    <div style="overflow:auto;">
                        <div id="logContent"></div>
                    </div>
                </bc:tab>
                <bc:tab id="tab2" title="流程模板">
                    <div style="overflow:auto;">
                        <img id="img" alt="流程模板"/>
                    </div>
                </bc:tab>
            </bc:tabs>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    function viewBizHandler() {
        var tables = $('#procInstTable').jTable('getCheckData');
        if (tables.length == 1) {
            window.location = '${base}/workflow/process/view?passToken=${passToken}&procInstId=' + tables[0].id + '&option=process/runingList';
        } else {
            $.messager.toastr('提示', '请先选择需要查看的数据。');
        }
    }

    function rejectStartHandler() {
        var tables = $('#procInstTable').jTable('getCheckData');
        var procInstId = tables[0].id;
        $.ajax({
            url: "${base}/workflow/process/queryProcInstTaskId",
            method: "POST",
            data: {
                processInstantId: tables[0].processInstantId,
            },
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                if (data == undefined || data == null) {
                    $.messager.alert('提示', '未找到任务', 'info');
                    return;
                } else {
                    rejectHandler(data, procInstId);
                }
            }
        });
    }



    //表单提交 , 先执行验证表单事件
    function rejectHandler(taskId, procInstId) {
        var nodeKey;
        $.ajax({
            url: "${base}/workflow/approve/selectCompleteNode",
            method: "POST",
            data: {
                taskId: taskId,
                procInstId: procInstId,
            },
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                nodeKey = data[0].value;
                if (nodeKey == undefined || nodeKey == null) {
                    $.messager.alert('提示', '未找到原始节点', 'info');
                    return;
                } else {
                    rejectHandlerBiz(taskId, procInstId, nodeKey);
                }
            }
        });
    }

    function rejectHandlerBiz(taskId, procInstId, nodeKey) {
        $.messager.confirm('提示', '确定要撤回该记录吗？', function (r) {
            if (r) {
                //添加撤回标志
                $.ajax({
                    url: "${base}/bus/return/returnTask",
                    method: "POST",
                    data: {
                        nodeKey: nodeKey,
                        taskId: taskId,
                        procInstId: procInstId,
                        backFlag: '0',
                        memo: "交易撤回"
                    },
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        if (data.success) {
                            $.mask.post('${base}/workflow/approve/reject',
                                {
                                    nodeKey: nodeKey,
                                    taskId: taskId,
                                    procInstId: procInstId,
                                    memo: $('#memo').val()
                                }, function (data) {
                                    if (data.success) {
                                        $.messager.alert('提示', '处理成功。', 'info', function () {
                                            window.location = '${base}/workflow/process/runingList?passToken=${passToken}';
                                        })
                                    } else {
                                        $.messager.alert('错误', data.msg, 'error');
                                    }
                                })
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                });

            }
        })
    }

    /* 选中模板，展示模板内容 */
    function onClickRow(row) {
        /*图片路径*/
        var imgUrl = '${base}/workflow/diagram/exedFlowImage/' + row['id'];
        /*加载流程图片  */
        document.getElementById("img").src = imgUrl;

        var logUrl = '${base}/workflow/procLog/runLog/' + row['id'];
        $.mask.post(logUrl, {}, function (data) {
            if (data.success) {
                $('#logContent').html(data.logContent);
            }
        });
    }
</script>
</body>
</html>
