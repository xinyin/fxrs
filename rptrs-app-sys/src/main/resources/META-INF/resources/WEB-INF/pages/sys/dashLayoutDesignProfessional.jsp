<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>专家模式设计布局</title>
</head>
<body class="white-bg">
<div class="row">
    <bc:ibox id="professionalBox" title="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden name="layoutCode" id="layoutCode"/>
            <bf:formGroup>
                <bc:label text="设计脚本" col="2" formControl="true"/>
                <bf:text id="config" name="config" col="8" multiple="true"
                         helpText="请输入HTML样式布局，并以\${组件编码}符号代表插入的组件。如：\${a} 表示组件a。"><bv:notEmpty
                        message="设计脚本不能为空"/></bf:text>
                <bc:button text="预览" icon="preView" click="preView()"/>
            </bf:formGroup>
            <bf:formGroup id="preViewFg"></bf:formGroup>
            <div align="center">
                <bc:button type="primary" text="保存" icon="save" click="btnSave()"/>
                <bc:button text="关闭" icon="times">
                    <be:event>
                        $.messager.hide('winProfessional');
                    </be:event>
                </bc:button>
            </div>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        var args = $.messager.args('winProfessional');
        $('#layoutCode').val(args.code);

        $.ajax({
            url: '${base}/sys/dashLayoutConfig/query?__EQS_layoutCode=' + args.code,
            dataType: 'json',
            success: function (data) {
                $('#config').val(data.rows[0].config);
            }
        });

        $('#preViewFg').next('.hr-line-dashed').hide();
        $('#preViewFg').css('padding-left', '15px');
        $('#preViewFg').css('padding-right', '15px');
    });

    /* 预览 */
    function preView() {
        $('#preViewFg').empty();
        $('#preViewFg').next('.hr-line-dashed').hide();
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            $('#preViewFg').next('.hr-line-dashed').show();
            $('#editForm').data('formValidation').revalidateField('config');
            var config = $('#config').val();
            $('#preViewFg').append(config);
        }
    }

    function btnSave() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var formData = $('#editForm').serializeAll();
            $.mask.post('${base}/sys/dashLayoutConfig/save', formData, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        $.messager.hide('winProfessional');
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
