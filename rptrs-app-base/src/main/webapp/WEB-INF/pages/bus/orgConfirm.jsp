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
<input id="orgPid" type="hidden" value="${pid}"/>
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <iframe id="myiframe" style="width: 100%;" onload="changeFrameHeight()" frameborder="0"
                    src="${base}/bus/relOrg/confirmView/${pid}">
            </iframe>
        </bc:ibox>
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup>
                        <bc:col col="2" colOffset="2">
                            <bc:button type="primary" id="btnPass" text="确认">
                                <be:event>affirm();</be:event>
                            </bc:button>
                        </bc:col>
                        <bc:col col="3">
                            <bc:button text="返回">
                                <be:event>
                                    window.location = '${base}/bus/relOrg/list';
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
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
    function changeFrameHeight() {
        var ifm = document.getElementById("myiframe");
        ifm.height = 480;
        // ifm.height = document.documentElement.clientHeight;
    }
    window.onresize = function () {
        changeFrameHeight();
    }

    /*确认*/
    function affirm() {
        var pid = $('#orgPid').val();
        $.messager.confirm('提示', '请再次确认！', function (r) {
            if (r) {
                $.ajax({
                    type: 'GET',
                    url: '${base}/bus/relOrg/affirm?pid=' + pid,
                    dataType: 'json',
                    delay: 200,
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert('提示', data.msg, 'info');
                            window.location = '${base}/bus/relOrg/list';

                        } else {
                            $.messager.alert('错误', data.msg, 'error');

                        }
                    }
                });
            }
        });
    }



</script>
</body>
</html>