<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <link href="${res}/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${res}/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">

    <title>通知查看页面</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <bc:ibox cls="mailbox-content" title="" col="12">
            <div class="mail-box-header">
                <div class="pull-right tooltip-demo">
                    <bc:button icon="mail-forward" text="转发" size="sm" id="forwardBtn" click="forward()"/>
                    <bc:button icon="list-alt" text="黑名单" size="sm" id="msgBtn" click="blacklistClick()"/>
                    <bc:button icon="times" text="删除" size="sm" type="danger" click="deleteFlag()"/>
                    <bc:button icon="back" text="返回" size="sm" type="primary" outline="true" click="back()"/>
                </div>
                <h2 id="titleView"></h2>
            </div>
            <div class="mail-box">
                <bf:form id="messageForm" validation="true">
                    <div class="mail-body">
                        <bf:formGroup>
                            <bc:label text="主题：" col="2" formControl="true"/>
                            <bc:label text="${notify.subject}" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="发件人：" col="2" formControl="true"/>
                            <bc:label text="${notify.senderName}" col="8" formControl="true" style="text-align:left"/>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="时间：" col="2" formControl="true"/>
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
                <bc:button icon="times" text="删除" size="sm" click="deleteFlag()" type="danger"></bc:button>
                <bc:button icon="back" text="返回" size="sm" type="primary" outline="true" click="back()"></bc:button>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${res}/js/plugins/summernote/summernote.min.js"></script>
<script type="text/javascript">
    var isUpload = '${notify.isUpload}';
    var type = '${notify.type}';
    var folderType = '${folderType}';

    $(document).ready(function () {
        init();
    });

    /* 初始化数据 */
    function init() {
        if (isUpload == 'Y') {
            $('#upload-mail-body').css('display', 'block');
        } else {
            $('#upload-mail-body').css('display', 'none');
        }

        $('#msgBtn').hide();

        if (type == 'ANNOUNCE') {
            $('#titleView').text('系统公告');
        } else if (type == 'MESSAGE') {
            $('#msgBtn').show();
            $('#titleView').text('私信消息');
        } else if (type == 'TODO') {
            $('#titleView').text('代办任务');
        } else if (type == 'REMIND') {
            $('#titleView').text('提醒信息');
        }

        if (folderType == 'trashbox') {
            $('#msgBtn').hide();
            $('#forwardBtn').hide();
        }
    }

    /* 转发 */
    function forward() {
        window.location = '${base}/sys/notify/msg?id=${notify.id}';
    }

    /* 加入黑名单 */
    function blacklistClick() {

        $.ajax({
            type: 'GET',
            url: '${base}/sys/notifyConfig/getBlacklistByStaffCode',
            data: {blackList: '${notify.sender}'},
            dataType: 'json',
            success: function (data) {
                if (data.isExist) {
                    $.messager.alert('提示', '${notify.senderName}已存在您的黑名单中。', 'info');
                } else {
                    $.messager.confirm('加入黑名单', '确认将${notify.senderName}加入黑名单？', function (r) {
                        if (r) {
                            var obj = {
                                blacklist: '${notify.sender}',
                                blacklistName: '${notify.senderName}'
                            }
                            $.mask.post('${base}/sys/notifyConfig/addBlacklist', obj, function (data) {
                                if (data.success) {
                                    $.messager.alert('提示', data.msg, 'info', function () {
                                        window.location = '${base}/sys/staffNotify/list?folderType=' + folderType;
                                    });
                                } else {
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    /* 删除 */
    function deleteFlag() {

        $.messager.confirm('删除', '确定要删除吗？', function (r) {
            if (r) {
                if (folderType == 'inbox') //逻辑删除
                {
                    $.mask.post('${base}/sys/staffNotify/deleteFlag', {ids: '${staffNotifyId}'}, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', data.msg, 'info', function () {
                                window.location = '${base}/sys/staffNotify/list?folderType=' + folderType;
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else if (folderType == 'trashbox') //彻底删除
                {
                    $.mask.post('${base}/sys/staffNotify/deleteCompletely', {ids: '${staffNotifyId}'}, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', data.msg, 'info', function () {
                                window.location = '${base}/sys/staffNotify/list?folderType=' + folderType;
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                }
            }
        });
    }

    /* 返回 */
    function back() {
        window.location = '${base}/sys/staffNotify/list?folderType=' + folderType;
    }
</script>
</body>
</html>