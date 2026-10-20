<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <x:jsLib/>
    <br:css pack="form"/>
    <title>金融产品新增或编辑</title>
    <x:jsLib/>

    <style>
        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .has-success .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

        .MainGd {
            display: none;
        }

        .voteProportion {
            display: none;
        }
    </style>
</head>
<body class="gray-bg" style="background: white">
<div class="row">
    <bc:ibox id="projectBox" title="" subTitle="" col="12" collapsible="true">
    <bf:form id="editForm" method="post" validation="true">
        <bf:hidden id="pid" name="pid" value="${authApply.pid}"/>
    <div class="boxse">
        <bf:formGroup type="success">
            <bc:col col="4">
                <bc:label cls="required" text="申请人" col="5" formControl="true"/>
                <bf:text id="applicant" name="applicant" col="7" click="forNameSelect()" value="${authApply.applicant}">
                    <bv:notEmpty/>
                </bf:text>
            </bc:col>
            <bc:col col="4">
                <bc:label cls="required" text="所属部门" col="5" formControl="true"/>
                <bf:text id="department" name="department" col="7" disabled="true" value="${authApply.department}">
                    <bv:notEmpty/>
                </bf:text>
            </bc:col>
        </bf:formGroup>
        <bf:formGroup type="success">

            <bc:col col="4">
                <bc:label cls="required" text="工作岗位" col="5" formControl="true"/>
                <bf:text name="post" id="post" col="7" value="${authApply.post}">
                    <bv:notEmpty/>
                </bf:text>
            </bc:col>

            <bc:col col="4">
                <bc:label cls="required" text="申请角色" col="5" formControl="true"/>
                <bf:text name="role" id="role" col="7" click="roleSelect()" value="${authApply.role}">
                    <bv:notEmpty/>
                </bf:text>
            </bc:col>
        </bf:formGroup>

        <bf:formGroup>
            <bc:col col="8">
                <bc:label text="申请权限主要描述" col="3" formControl="true"/>
                <bf:text id="remark" name="remark" col="9" multiple="true" rows="5">${authApply.remark}</bf:text>
            </bc:col>
        </bf:formGroup>
        <bf:formGroup>
            <div class="col-sm-4 col-sm-offset-5">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.history.go(-1);
                    </be:event>
                </bc:button>
            </div>
        </bf:formGroup>
        </bf:form>
        </bc:ibox>
    </div>

    <br:js pack="form"/>
    <be:processEvent/>
    <script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
    <div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
    <%--<script type="text/javascript">--%>
    <%--    function getFormData() {--%>
    <%--        $('#editForm').data('formValidation').validate();--%>
    <%--        var isValid = $('#editForm').data('formValidation').isValid();--%>
    <%--        if (isValid) {--%>
    <%--            var objData = {};--%>

    <%--            for (var entry of $('#editForm').serializeArray()) {--%>
    <%--                objData[entry['name']] = entry['value'];--%>
    <%--            }--%>
    <%--            var myDate = new Date();--%>
    <%--            objData.id =  myDate.getTime();--%>
    <%--            return objData;--%>
    <%--        } else {--%>
    <%--            return false;--%>
    <%--        }--%>
    <%--    }--%>
    <%--</script>--%>
</body>
</html>
