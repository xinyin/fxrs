<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>流程实例列表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="业务信息" subTitle="" col="12" collapsible="true">
            <bf:form id="workflowform" method="post" validation="true">
                ${content}
            </bf:form>
        </bc:ibox>

        <bc:ibox title="处理" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup>
                        <bc:label text="意见" col="2" formControl="true"/>
                        <bf:text id="memo" name="memo" col="8" multiple="true"></bf:text>
                    </bf:formGroup>
                    <bf:formGroup>
                        <bc:col col="2" colOffset="2">
                            <bc:button id="btnPass" type="primary" text="通过">
                                <be:event>save();</be:event>
                            </bc:button>
                        </bc:col>
                        <bc:col col="4">
                            <bc:col col="5">
                                <bc:button id="btnReject" type="primary" text="退回">
                                    <be:event>reject();</be:event>
                                </bc:button>
                            </bc:col>
                            <bc:col col="7">
                                <bf:select id="nodeKeySelect" name="nodeKeySelect" dataSource="custom"
                                           url="workflow/approve/selectCompleteNode?procInstId=${procInstId}&taskId=${taskId}">
                                </bf:select>
                            </bc:col>
                        </bc:col>
                        <bc:col col="3">
                            <bc:button text="返回">
                                <be:event>
                                    <%--									window.location = '${base}/workflow/approve/todoList';--%>
                                    window.history.go(-1);
                                </be:event>
                            </bc:button>
                        </bc:col>
                    </bf:formGroup>
                </bc:row>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jFlowForm/jFlowForm.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jFlowForm" data-proc-inst-id="${procInstId}" data-task-id="${taskId}"
     data-form-id="workflowform"></div>
<script type="text/javascript">
    var procInstId = '${procInstId}';
    var taskId = '${taskId}';

    //表单提交 , 先执行验证表单事件
    function reject() {
        var nodeKey = $('#nodeKeySelect').val();
        if (!nodeKey) {
            $.messager.alert('提示', '请选择要回退到的节点。', 'info');
            return;
        }
        var memo = $('#memo').val();
        if (!memo) {
            $.messager.alert('提示', '回退意见不能为空。', 'info');
            return;
        }

        $.messager.confirm('提示', '确定要回退该记录吗？', function (r) {
            if (r) {
                $.mask.post('${base}/workflow/approve/reject',
                    {
                        nodeKey: nodeKey,
                        taskId: taskId,
                        procInstId: procInstId,
                        memo: $('#memo').val()
                    },
                    function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '处理成功。', 'info', function () {
                                window.location = '${base}/workflow/approve/todoList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                );
            }

        })
    }

    //表单提交 , 先执行验证表单事件
    function save() {
        $('#workflowform').data('formValidation').validate();
        var isValid = $('#workflowform').data('formValidation').isValid();
        if (isValid) {
            /*调用用户定义的表单提交事件*/
            $('#flowForm').jFlowForm('onFormSubmit');
            /*获取表单数据*/
            var data = $('#flowForm').jFlowForm('getFormData');
            debugger;
            var formData = data.formData;
            $.mask.post('${base}/workflow/approve/complete',
                {
                    taskId: taskId,
                    formData: formData,
                    procInstId: procInstId,
                    memo: $('#memo').val()
                },
                function (data) {
                    if (data.success) {
                        $.messager.alert('提示', '处理成功。', 'info', function () {
                            window.location = '${base}/workflow/approve/todoList';
                        });
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            );
        }
    }

    function viewDetails(instId, propCode) {
        $.messager.modal({
            url: "${base}/workflow/formProp/viewDetails?instId=" + instId + "&propCode=" + propCode,
            closable: true,
            width: 800,
            height: 500,
            title: '数据变动内容',
            winId: 'w_viewDetails',
            args: {instId: instId, propCode: propCode}
        });
    }
</script>
</body>
</html>