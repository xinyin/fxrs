<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <link href="${res}/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${res}/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">

    <title>私信编辑页面</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row animated fadeInRight">
        <bc:ibox cls="mailbox-content" title="" col="12">
            <div class="mail-box-header">
                <div class="pull-right tooltip-demo">
                    <bc:button icon="send" text="发送" size="sm" click="sendMsg()" type="primary"></bc:button>
                    <bc:button icon="eye" text="预览" size="sm" click="preview()"></bc:button>
                    <bc:button icon="pencil" text="存草稿" size="sm" click="saveDraft()"></bc:button>
                    <bc:button icon="times" text="取消" size="sm" type="danger" click="back()"></bc:button>
                </div>
                <h2> 私信编辑 </h2>
            </div>
            <div class="mail-box">
                <bf:form id="messageForm" validation="true">
                    <bf:hidden id="notifyId" name="id" value="${notify.id}"/>
                    <div class="mail-body">
                        <bf:formGroup>
                            <bc:label text="收件人：" col="2" formControl="true"/>
                            <bf:staffLookup id="receiver" name="receiver" col="10"
                                            value="${notify.receiver}" text="${notify.receiverName}"
                                            propText="staffName" propValue="staffCode"
                                            callback="result" onSelect="receiverChange" mult="true">
                                <bv:notEmpty/>
                            </bf:staffLookup>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:label text="主题：" col="2" formControl="true"/>
                            <bf:text id="subjectMsg" name="subject" col="10" value="${notify.subject}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bf:formGroup>
                    </div>
                    <div class="mail-text h-200">
                        <div id="summernote">${notify.content}</div>
                    </div>
                    <div class="mail-body">
                        <bc:upload id="upload" entityType="NOTIFY"/>
                    </div>
                    <div class="clearfix"/>
                </bf:form>
            </div>
            <div class="mail-body text-right">
                <bc:button icon="send" text="发送" size="sm" click="sendMsg()" type="primary"></bc:button>
                <bc:button icon="times" text="取消" size="sm" click="back()"></bc:button>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${res}/js/plugins/summernote/summernote.min.js"></script>
<script type="text/javascript">
    var opt = '${opt}';

    $(document).ready(function () {

        init();

    });

    function init() {

        /* 初始化文本编辑器 */
        $('#summernote').summernote({
            minHeight: 300,
            maxHeight: 900
        });
        $('#summernote').next().css('border-color', 'rgb(245, 245, 245)');

        /* 转发 */
        if (opt == 'forward') {
            $('#notifyId').val(''); //转发，清除id
            $('#upload').jUpload('setEntityId', '${newEntityId}'); //重新设置 entityId
        } else if (opt == 'draft') {
            $('#upload').jUpload('setEntityId', '${notify.id}');
        }
    }

    function receiverChange() {
        $('#messageForm').data('formValidation').resetField('receiverText');
    }

    /* 发送 */
    function sendMsg() {
        $('#messageForm').data('formValidation').validate();
        var isValid = $('#messageForm').data('formValidation').isValid();
        if (isValid) {
            var obj = {
                id: $('#notifyId').val(),
                content: $('#summernote').summernote('code'),
                subject: $('#subjectMsg').val(),
                receiver: $('#receiver').jStaffLookup('getValue')
            }
            $.mask.post('${base}/sys/notify/saveMessage', obj, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        associate(data.id);
                        window.location = '${base}/sys/staffNotify/list';
                    });
                } else {
                    $.messager.toastr('错误', data.msg, 'error');
                }
            });
        }
    }

    /* 预览 */
    function preview() {

        var entityIdNew = '';
        var uploadSession = '';
        var entityId = '';
        if (opt == 'forward') {
            uploadSession = '${uploadSession}';
            entityId = '${newEntityId}';
        } else {
            uploadSession = $('#upload').jUpload('getSession');
            entityId = $('#notifyId').val();
        }
        $.ajax({
            type: 'POST',
            url: '${base}/sys/attach/query',
            data: {
                entityId: entityId,
                uploadSession: uploadSession,
                entityType: 'NOTIFY'
            },
            dataType: 'json',
            success: function (data) {
                if (data.files.length > 0) {
                    modalPreview(data.files[0].entityId);
                } else {
                    modalPreview('');
                }
            }
        });
    }

    function modalPreview(id) {
        $.messager.modal({
            winId: 'winNotifyPreview',
            width: 1100,
            height: 560,
            title: '预览',
            url: '${base}/sys/notify/preview',
            args: {
                type: 'MESSAGE',
                content: $('#summernote').summernote('code'),
                subject: $('#subjectMsg').val(),
                receiver: $('#receiver').jStaffLookup('getText'),
                entityId: id
            }
        });
    }

    /* 保存为草稿 */
    function saveDraft() {
        var obj = {
            id: $('#notifyId').val(),
            content: $('#summernote').summernote('code'),
            subject: $('#subjectMsg').val(),
            receiver: $('#receiver').jStaffLookup('getValue')
        }
        $.mask.post('${base}/sys/notify/saveMessageDraft', obj, function (data) {
            if (data.success) {
                $('#notifyId').val(data.id);
                associate(data.id);
            }
            $.messager.toastr('提示', data.msg, 'info');
        });
    }

    /* 关联文件 */
    function associate(id) {

        if (opt == 'forward') {
            $.ajax({
                type: 'POST',
                url: '${base}/sys/attach/associate',
                data: {
                    entityId: '${newEntityId}',
                    uploadSession: '${uploadSession}'
                },
                dataType: 'json',
                success: function (result) {
                    if (result.count > 0) {
                        $.ajax({
                            type: 'GET',
                            url: '${base}/sys/notify/updateStatusOfUpload',
                            data: {
                                id: id,
                                isUpload: 'Y'
                            },
                            dataType: 'json'
                        });
                    }
                }
            });
        } else if (opt == 'draft') {
            $('#upload').jUpload('associate', id, function (result) {
                if (result.count > 0) {
                    $.ajax({
                        type: 'GET',
                        url: '${base}/sys/notify/updateStatusOfUpload',
                        data: {
                            id: id,
                            isUpload: 'Y'
                        },
                        dataType: 'json'
                    });
                }
            });
        }
    }

    /* 返回 */
    function back() {

        /* 新建私信或转发消息时，上传了附件未保存，就取消编辑需要删除附件。 */
        var id = $('#notifyId').val();
        if (id == '') {
            console.log('111');
            $.ajax({
                type: 'POST',
                url: '${base}/sys/attach/query',
                data: {
                    entityId: '${newEntityId}',
                    uploadSession: $('#upload').jUpload('getSession'),
                    entityType: 'NOTIFY'
                },
                dataType: 'json',
                success: function (data) {

                    if (data.files.length > 0) {
                        $.each(data.files, function (i, v) {
                            $.ajax({
                                type: 'POST',
                                url: '${base}/sys/attach/remove/' + v.id,
                                dataType: 'json'
                            });
                        });
                    }
                    window.location = '${base}/sys/staffNotify/list?folderType=${folderType}';
                }
            });
        } else {
            window.location = '${base}/sys/staffNotify/list?folderType=${folderType}';
        }
    }
</script>
</body>
</html>