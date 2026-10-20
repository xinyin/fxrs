<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>提供服务交易类</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="provideServicesBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="金额" col="5" formControl="true"/>
                    <bf:text id="amount" name="amount" col="7" value="${amount}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>

        </bf:form>
    </bc:ibox>
</div>
</div>

<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    function getFormData() {
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var objData = {};

            for (var entry of $('#editForm').serializeArray()) {
                objData[entry['name']] = entry['value'];
            }
            var myDate = new Date();
            objData.id =  myDate.getTime();
            return objData;
        } else {
            return false;
        }
    }
</script>
</body>
</html>
