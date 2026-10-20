<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>

    <title>通知内容查看页面</title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <bc:ibox cls="mailbox-content" title="" col="12">
            <div class="mail-box-header">
                <div class="pull-right tooltip-demo">
                    <bc:button icon="mail-forward" text="转发" size="sm" click="forward()"/>
                    <bc:button icon="times" text="删除" size="sm" type="danger" click="deleteFlag()"/>
                    <bc:button icon="back" text="返回" size="sm" type="primary" outline="true" click="back()"/>
                </div>
                <h2 id="titleView"></h2>
            </div>
            <div class="mail-box">
                <bf:form id="messageForm" validation="true">
                    <div class="mail-body">
                        <bf:formGroup>
                            <bc:label text="主 题：" col="2" formControl="true"/>
                            <bc:label text="${notify.subject}" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="发件人：" col="2" formControl="true"/>
                            <bc:label text="${notify.senderName}" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="收件人：" col="2" formControl="true"/>
                            <bc:label text="${notify.receiverName}" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="时 间：" col="2" formControl="true"/>
                            <bc:label text="${notify.sendTimeString}" col="8" formControl="true"
                                      style="text-align:left"/>
                        </bf:formGroup>
                    </div>
                    <div class="mail-text h-300" style="padding: 20px;">
                            ${notify.content}
                    </div>
                    <div id="upload-mail-body" class="mail-body">
                        <bc:upload id="upload" entityType="NOTIFY" entityId="${notify.id}"
                                   uploadEnable="false" removeEnable="false"/>
                    </div>
                    <div class="clearfix"/>
                </bf:form>
            </div>
            <div class="mail-body text-right">
                <bc:button icon="times" text="删除" size="sm" type="danger" click="deleteFlag()"/>
                <bc:button icon="back" text="返回" size="sm" type="primary" outline="true" click="back()"/>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    var isUpload = '${notify.isUpload}';
    var type = '${notify.type}';
    var backUrl = '';
    var path = '${path}';

    $(document).ready(function () {
        init();
    });

    function init() {
        if (isUpload == 'Y') {
            $('#upload-mail-body').css('display', 'block');
        } else {
            $('#upload-mail-body').css('display', 'none');
        }

        if (type == 'ANNOUNCE') {
            $('#titleView').text('系统公告');
            backUrl = '${base}/sys/notify/announceList?folderType=${folderType}';
        } else if (type == 'MESSAGE') {
            $('#titleView').text('私信消息');
            backUrl = '${base}/sys/staffNotify/list?folderType=${folderType}';
        }
    }

    /* 转发 */
    function forward() {
        window.location = '${base}/sys/notify/msg?id=${notify.id}';
    }

    /* 删除 */
    function deleteFlag() {

        $.messager.confirm('删除', '确定要删除吗？', function (r) {
            if (r) {
                $.mask.post('${base}/sys/notify/deleteFlag', {ids: '${notify.id}'}, function (data) {
                    if (data.success) {
                        $.messager.alert('提示', data.msg, 'info', function () {
                            if (path == 'staffNotify') {
                                window.location = '${base}/sys/staffNotify/list?folderType=${folderType}';
                            } else if (path == 'notify') {
                                window.location = '${base}/sys/notify/announceList?folderType=${folderType}';
                            }

                        });
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                });
            }
        });
    }

    /* 返回 */
    function back() {
        window.location = backUrl;
    }
</script>
</body>
</html>