<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>

    <title>通知内容预览页面</title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <bc:ibox cls="mailbox-content" title="" col="12">
            <div class="mail-box-header">
                <div class="pull-right tooltip-demo">
                    <bc:button icon="back" text="关闭" size="sm" type="primary" outline="true" click="back()"/>
                </div>
                <h2 id="titleView"></h2>
            </div>
            <div class="mail-box">
                <bf:form id="messageForm" validation="true">
                    <div class="mail-body">
                        <bf:formGroup>
                            <bc:label text="主 题：" col="2" formControl="true"/>
                            <bc:label cls="subject" text="" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="发件人：" col="2" formControl="true"/>
                            <bc:label text="${sender}" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="收件人：" col="2" formControl="true"/>
                            <bc:label cls="receiver" text="" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                    </div>
                    <div id="context" class="mail-text h-300" style="padding: 20px;">
                    </div>
                    <div id="upload-mail-body" class="mail-body">
                        <bc:upload id="upload" entityType="NOTIFY" uploadEnable="false" removeEnable="false"/>
                    </div>
                    <div class="clearfix"/>
                </bf:form>
            </div>
            <div class="mail-body text-right">
                <bc:button icon="back" text="关闭" size="sm" type="primary" outline="true" click="back()"/>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

        init();
    });

    function init() {

        var args = $.messager.args('winNotifyPreview');
        if (args.type == 'MESSAGE') {
            $('#titleView').text('私信消息');
            $('.receiver').text(args.receiver);
        } else if (args.type == 'ANNOUNCE') {
            $('#titleView').text('系统公告');
            $('.receiver').text('${receiver}');
        }

        $('.subject').text(args.subject);
        $('#context').html(args.content);
        console.log(args);
        $('#upload').jUpload('setEntityId', args.entityId);
    }

    /* 返回 */
    function back() {
        $.messager.hide('winNotifyPreview');
    }
</script>
</body>
</html>