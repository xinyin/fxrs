<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <title>流程提交</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="iboxform" title="" col="12" collapsible="false">
            <bf:form id="workflowform" method="post" validation="true">
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="页面跳转中..." col="4" formControl="true"/>
                    </bc:col>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>

<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        //控制跳转移动端页面（nginx静态页面）
        window.location = 'http://${url}?procInstId=' + '${procInstId}' + '&taskId=${taskId}' + '&staffCode=${staffCode}';
    });
</script>
</body>
</html>