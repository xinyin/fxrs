<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <link href="${res}/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${res}/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">

    <title>公告编辑页面</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <bc:ibox cls="mailbox-content" title="" col="12">
            <div class="mail-box-header">
                <div class="pull-right tooltip-demo">
                    <bc:button icon="send" text="发送" size="sm" click="sendAnnounce()"></bc:button>
                    <bc:button icon="eye" text="预览" size="sm" click="preview()"></bc:button>
                    <bc:button icon="pencil" text="存草稿" size="sm" click="saveDraft()"></bc:button>
                    <bc:button icon="times" text="取消" size="sm" type="danger" click="back()"></bc:button>
                </div>
                <h2> 公告编辑 </h2>
            </div>
            <div class="mail-box">
                <div class="mail-body">
                    <bf:form id="announceForm" validation="true">
                        <bf:hidden id="notifyId" name="id" value="${notify.id}"/>
                        <bf:formGroup>
                            <bc:row>
                                <bc:label text="收件人：" col="2" formControl="true"/>
                                <bf:checkGroup name="radioGrp" col="3">
                                    <bf:radio id="all" label="全部用户" value="all" checked="true" change="allChange()"/>
                                    <bf:radio id="custom" label="自定义" value="custom" change="customChange()"/>
                                </bf:checkGroup>
                            </bc:row>
                            <bc:row cls="customGrp">
                                <bc:label text="" col="2" formControl="true"/>
                                <bf:unitLookup id="unit" name="unit" col="3"
                                               value="" text="" propText="unitName" propValue="unitCode"
                                               callback="result" onSelect="unitChange" mult="true" placeholder="机构搜索">
                                    <bv:notEmpty/>
                                </bf:unitLookup>
                                <bf:lookup id="role" name="role" col="3" url="${base}/sys/role/dialog"
                                           value="" text="" propText="roleName" propValue="roleCode"
                                           callback="result" onSelect="roleChange" title="选择岗位"
                                           winId="winRoleDlg" width="1000" height="500" placeholder="岗位搜索">
                                    <bv:notEmpty/>
                                </bf:lookup>
                                <bf:staffLookup id="staff" name="staff" col="3"
                                                value="" text="" propText="staffName" propValue="staffCode"
                                                callback="result" onSelect="staffChange" mult="true" placeholder="人员搜索">
                                    <bv:notEmpty/>
                                </bf:staffLookup>
                            </bc:row>
                            <bc:row>
                                <bc:label text="主题：" col="2" formControl="true"/>
                                <bf:text id="subjectAnn" name="subject" col="10" value="${notify.subject}">
                                    <bv:notEmpty/>
                                </bf:text>
                            </bc:row>
                        </bf:formGroup>
                    </bf:form>
                </div>

                <div class="mail-text h-200">
                    <div id="summernote">${notify.content}</div>
                </div>

                <div class="mail-body">
                    <bc:upload id="upload" entityType="NOTIFY" entityId="${notify.id}"/>
                </div>
                <div class="clearfix"/>
            </div>
            <div class="mail-body text-right">
                <bc:button icon="send" text="发送" size="sm" click="sendAnnounce()"></bc:button>
                <bc:button icon="times" text="取消" size="sm" type="danger" click="back()"></bc:button>
            </div>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${res}/js/plugins/summernote/summernote.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {

        /* 初始化文本编辑器 */
        $('#summernote').summernote({
            minHeight: 300,
            maxHeight: 900
        });
        $('#summernote').next().css('border-color', 'rgb(245, 245, 245)');
        $('.customGrp').hide();
    });

    /* 重新验证lookup控件 */
    function unitChange() {
        $('#announceForm').data('formValidation').resetField('unitText');
    }

    function roleChange() {
        $('#announceForm').data('formValidation').resetField('roleText');
    }

    function staffChange() {
        $('#announceForm').data('formValidation').resetField('staffText');
    }

    /* 单选框值改变 */
    function allChange() {
        $('.customGrp').hide();
    }

    function customChange() {
        $('.customGrp').show();
    }

    /* 获取收件人 */
    function getReceiver() {

        if ($('#all').prop('checked')) {
            return 'all';
        } else {
            var obj = {
                unitList: $('#unit').jUnitLookup('getValue'),
                roleList: $('#role').jLookup('getValue'),
                staffList: $('#staff').jStaffLookup('getValue')
            }
            return JSON.stringify(obj);
        }
    }

    /* 发送 */
    function sendAnnounce() {

        var unit = $('#unit').jUnitLookup('getValue');
        var role = $('#role').jLookup('getValue');
        var staff = $('#staff').jStaffLookup('getValue');
        if (unit != null || role != null || staff != null) {
            $('#announceForm').data('formValidation').removeField('unitText');
            $('#announceForm').data('formValidation').removeField('roleText');
            $('#announceForm').data('formValidation').removeField('staffText');
        }

        $('#announceForm').data('formValidation').validate();
        var isValid = $('#announceForm').data('formValidation').isValid();
        if (isValid) {
            var obj = {
                id: $('#notifyId').val(),
                content: $('#summernote').summernote('code'),
                subject: $('#subjectAnn').val(),
                receiver: getReceiver(),
            }
            $.mask.post('${base}/sys/notify/saveAnnounce', obj, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        $('#upload').jUpload('associate', data.id, function (result) {
                            if (result.count > 0) {
                                $.ajax({
                                    type: 'GET',
                                    url: '${base}/sys/notify/updateStatusOfUpload',
                                    data: {
                                        id: data.id,
                                        isUpload: 'Y'
                                    },
                                    dataType: 'json',
                                    success: function (data) {
                                        window.location = '${base}/sys/notify/announceList';
                                    }
                                });
                            } else {
                                window.location = '${base}/sys/notify/announceList';
                            }
                        });
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }
    }

    /* 预览 */
    function preview() {

        var entityIdNew = '';
        $.ajax({
            type: 'POST',
            url: '${base}/sys/attach/query',
            data: {
                entityId: $('#notifyId').val(),
                uploadSession: $('#upload').jUpload('getSession'),
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
            url: '${base}/sys/notify/preview?receiver=' + getReceiver(),
            args: {
                type: 'ANNOUNCE',
                content: $('#summernote').summernote('code'),
                subject: $('#subjectAnn').val(),
                entityId: id
            }
        });
    }

    /* 保存为草稿 */
    function saveDraft() {

        var obj = {
            id: $('#notifyId').val(),
            content: $('#summernote').summernote('code'),
            subject: $('#subjectAnn').val(),
            receiver: getReceiver(),
        }
        $.mask.post('${base}/sys/notify/saveAnnounceDraft', obj, function (data) {
            if (data.success) {
                $('#notifyId').val(data.id);
                $('#upload').jUpload('associate', data.id, function (result) {
                    if (result.count > 0) {
                        $.ajax({
                            type: 'GET',
                            url: '${base}/sys/notify/updateStatusOfUpload',
                            data: {
                                id: data.id,
                                isUpload: 'Y'
                            },
                            dataType: 'json'
                        });
                    }
                });
            }
            $.messager.toastr('提示', data.msg, 'info');
        });
    }

    /* 返回 */
    function back() {

        /* 新建公告消息时，上传了附件未保存，就取消编辑需要删除附件。 */
    ‘
	var id = $('#notifyId').val();
        if (id == '') {
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
                    console.log(data);
                    if (data.files.length > 0) {
                        $.each(data.files, function (i, v) {
                            console.log(v);
                            $.ajax({
                                type: 'POST',
                                url: '${base}/sys/attach/remove/' + v.id,
                                dataType: 'json'
                            });
                        });
                    }
                    window.location = '${base}/sys/notify/announceList?folderType=${folderType}';
                }
            });
        } else {
            window.location = '${base}/sys/notify/announceList?folderType=${folderType}';
        }
    }
</script>
</body>
</html>