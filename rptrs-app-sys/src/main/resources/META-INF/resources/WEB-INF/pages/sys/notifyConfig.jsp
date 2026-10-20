<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>通知配置</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="notifyConfigBox" title="消息配置" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="id" value="${notifyConfig.id}"/>
            <bf:formGroup>
                <bc:label text="是否接收短信" col="2" formControl="true"/>
                <bf:checkGroup name="isReceiveSms" col="6" value="${notifyConfig.isReceiveSms}">
                    <bf:radio id="isReceiveSms" url="NotifyConfigResult"
                              dataSource="fixedDict" change="smsChange(e.target.value)" excludes="RECEIVENOREMIND">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup id="smsOpen" cls="hideFormGrp">
                <bc:label text="短信接收类型" col="2" formControl="true"/>
                <bf:checkGroup id="smsReceiveType" name="smsReceiveType" col="6">
                    <bf:check dataSource="fixedDict" url="NotifyConfigType" value="${notifyConfig.smsReceiveType}">
                        <bv:notEmpty/>
                    </bf:check>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup id="smsPhone" cls="hideFormGrp">
                <bc:label text="用户手机号" col="2" formControl="true"/>
                <bf:text id="staffPhone" name="staffPhone" col="6" value="${notifyConfig.staffPhone}">
                    <bv:phone country="CN"/>
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="是否接收邮件" col="2" formControl="true"/>
                <bf:checkGroup id="isReceiveEmail" name="isReceiveEmail" col="6" value="${notifyConfig.isReceiveEmail}">
                    <bf:radio url="NotifyConfigResult" dataSource="fixedDict" change="emailChange(e.target.value)"
                              excludes="RECEIVENOREMIND">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup id="emailOpen" cls="hideFormGrp">
                <bc:label text="邮件接收类型 " col="2" formControl="true"/>
                <bf:checkGroup name="emailReceiveType" col="6">
                    <bf:check dataSource="fixedDict" url="NotifyConfigType" value="${notifyConfig.emailReceiveType}">
                        <bv:notEmpty/>
                    </bf:check>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup id="emailNo" cls="hideFormGrp">
                <bc:label text="用户邮箱" col="2" formControl="true"/>
                <bf:text id="staffEmail" name="staffEmail" col="6" value="${notifyConfig.staffEmail}">
                    <bv:emailAddress/>
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="是否接收提醒" col="2" formControl="true"/>
                <bf:checkGroup id="isReceiveRemind" name="isReceiveRemind" col="6"
                               value="${notifyConfig.isReceiveRemind}">
                    <bf:radio url="NotifyConfigResult" dataSource="fixedDict">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="是否接受私信" col="2" formControl="true"/>
                <bf:checkGroup id="isReceiveMsg" name="isReceiveMsg" col="6" value="${notifyConfig.isReceiveMsg}">
                    <bf:radio url="NotifyConfigResult" dataSource="fixedDict" change="msgChange(e.target.value)">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup id="msgOpen" cls="hideFormGrp">
                <bc:label text="黑名单" col="2" formControl="true"/>
                <bf:staffLookup id="blacklist" name="blacklist" col="6"
                                text="${notifyConfig.blacklistText}" value="${notifyConfig.blacklist}"
                                propText="staffName" propValue="staffCode"
                                callback="result" onSelect="staffChange" mult="true" placeholder="人员搜索">
                </bf:staffLookup>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="收件箱保留时长" col="2" formControl="true"/>
                <bf:checkGroup id="inboxRetentionTime" name="inboxRetentionTime"
                               col="6" value="${notifyConfig.inboxRetentionTime}">
                    <bf:radio url="NotifyConfigTime" dataSource="fixedDict">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup>
                <bc:label text="垃圾箱保留时长" col="2" formControl="true"/>
                <bf:checkGroup id="trashRetentionTime" name="trashRetentionTime"
                               col="6" value="${notifyConfig.trashRetentionTime}">
                    <bf:radio url="NotifyConfigTime" dataSource="fixedDict">
                        <bv:notEmpty/>
                    </bf:radio>
                </bf:checkGroup>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="primary" text="保存" icon="save" click="btnSave()">
                    </bc:button>
                    <bc:button outline="true" text="返回">
                        <be:event>
                            window.location = '${base}/sys/staffNotify/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {

        /* 控件展示与隐藏控制 */
        $('.hideFormGrp').hide();
        smsChange('${notifyConfig.isReceiveSms}');
        emailChange('${notifyConfig.isReceiveEmail}');
        msgChange('${notifyConfig.isReceiveMsg}');

        init();

    });

    function init() {
        var smsBox = $('input[name="smsReceiveType"]');
        var smsReceiveType = '${notifyConfig.smsReceiveType}';
        var valSmsArr = smsReceiveType.split(",");
        $.each(smsBox, function (i, v) {
            $.each(valSmsArr, function (j, n) {
                if (v.value == n) {
                    smsBox[i].checked = true;
                    return false;
                }
            });
        });

        var emailBox = $('input[name="emailReceiveType"]');
        var emailReceiveType = '${notifyConfig.emailReceiveType}';
        var valEmailArr = emailReceiveType.split(",");
        $.each(emailBox, function (i, v) {
            $.each(valEmailArr, function (j, n) {
                if (v.value == n) {
                    emailBox[i].checked = true;
                    return false;
                }
            });
        });

        if ($('#staffPhone').val() == null || $('#staffPhone').val() == '') {
            $('#staffPhone').val('${staff.mobile}');
        }
        if ($('#staffEmail').val() == null || $('#staffEmail').val() == '') {
            $('#staffEmail').val('${staff.email}');
        }

    }

    /* 重新验证lookup控件 */
    function staffChange() {
        $('#editForm').data('formValidation').resetField('blacklistText');
    }

    /* 类型展开控制 */
    function smsChange(val) {
        if (val == 'RECEIVE' || val == 'RECEIVENOREMIND') {
            $('#smsOpen').show();
            $('#smsPhone').show();
        } else if (val == 'REFUSE') {
            $('input[name="smsReceiveType"]').prop('checked', false);
            $('#editForm').data('formValidation').revalidateField('smsReceiveType');
            $('#smsOpen').hide();
            $('#smsPhone').hide();
        }
    }

    function emailChange(val) {
        if (val == 'RECEIVE' || val == 'RECEIVENOREMIND') {
            $('#emailOpen').show();
            $('#emailNo').show();
        } else if (val == 'REFUSE') {
            $('input[name="emailReceiveType"]').prop('checked', false);
            $('#editForm').data('formValidation').revalidateField('emailReceiveType');
            $('#emailOpen').hide();
            $('#emailNo').hide();
        }
    }

    function msgChange(val) {
        if (val == 'RECEIVE' || val == 'RECEIVENOREMIND') {
            $('#msgOpen').show();
        } else if (val == 'REFUSE') {
            $('#msgOpen').hide();
            $('#blacklist').jStaffLookup('cleanData');
        }
    }

    function btnSave() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var text = $('input[name="blacklistText"]').val();
            if (text == '') {
                $('#blacklist').jStaffLookup('cleanData');
            }
            var formData = $('#editForm').serializeAll();

            $.mask.post('${base}/sys/notifyConfig/saveConfig', formData, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        window.location = '${base}/sys/staffNotify/list';
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }
    }
</script>
</body>
</html>
