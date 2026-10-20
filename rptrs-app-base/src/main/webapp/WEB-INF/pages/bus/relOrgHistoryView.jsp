<%@ taglib prefix="bev" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
    <br:css pack="form"/>
    <title>RelOrg</title>
    <x:jsLib/>
    <style>
        /*.boxse .form-group .col-xs-5 {*/
        /*    margin: 0.3% 0 !important;*/
        /*}*/
        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }

        .gray-bg {
            background: white !important;
        }

        .ismainhoder {
            display: none;
        }

        .voteProportion {
            display: none;
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

        .ismainhoder {
            display: none;
        }

        .voteProportion {
            display: none;
        }

        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

    </style>


</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
    <bf:form id="editForm" method="post" validation="true">
    <bf:hidden id="pid" name="pid" value="${relOrgHistory.pid}"/>
        <bf:formGroup>
            <bc:col col="6">
                <bc:button type="primary" text="基本信息">
                    <be:event>
                    </be:event>
                </bc:button>
            </bc:col>
        </bf:formGroup>
        <div class="boxse">
            <bf:formGroup>
                <bc:col col="4">
                    <bc:label cls="required" text="法人或非法人组织名称" col="5" formControl="true"/>
                    <bf:text id="orgName" name="orgName" col="7" value="${relOrgHistory.orgName}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label cls="required" text="证件类型" col="5" formControl="true"/>
                    <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" value="${relOrgHistory.identifyType}">
                        <bv:notEmpty/>
                        <bf:option text="请选择" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                    <bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrgHistory.uniscCode}">
                        <bv:notEmpty/>
                        <bv:stringLength min="8" max="18" trim="true" message="请输入正确的证件号码"/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <bc:col col="4">
                    <bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
                    <bf:checkGroup name="domesticOrForeign" col="7" disabled="" value="${relOrgHistory.domesticOrForeign}">
                        <bf:radio id="dOF" value="1" label="境内"  checked="true"
                                  click="domesticOrForeign(e.target.value)"/>
                        <bf:radio id="dOF1" value="2" label="境外" click="domesticOrForeign(e.target.value)"/>
                    </bf:checkGroup>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="法定代表人" col="5" formControl="true"/>
                    <bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrgHistory.legalPerson}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册资本及其变化（万元）" col="5" formControl="true"/>
                    <bf:text id="regCapital" name="regCapital" col="7" value="${relOrgHistory.regCapital}">
                        <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                        <bv:stringLength max="21" message="字段长度最大为20位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="经济性质或类型" col="5" formControl="true"/>
                    <bf:text id="orgNature" name="orgNature" col="7"  value="${relOrgHistory.orgNature}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册地" col="5" formControl="true"/>
                    <bf:text id="regAddress" name="regAddress" col="7" value="${relOrgHistory.regAddress}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="注册资本币种代码" col="5" formControl="true"/>
                    <bf:select id="regCapitalCurrency" name="regCapitalCurrency" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-regCapitalCurrency" value="${relOrgHistory.regCapitalCurrency}">
                        <bv:notEmpty/>
                        <bf:option text="请选择" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="12">
                    <bc:label text="主营业务或经营范围" col="5" formControl="true"/>
                    <bf:text id="businessScope" name="businessScope" col="9" rows="3" multiple="true" value="${relOrgHistory.businessScope}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
        </div>
        <bf:formGroup>
            <div class="col-sm-4 col-sm-offset-5" style="padding-top:3%">
                <bc:button type="primary" text="返回" icon="arrow-circle-left">
                    <be:event>
                        window.location = '${base}/bus/relOrg/history/list';
                    </be:event>
                </bc:button>
            </div>
        </bf:formGroup>
    </bf:form>
    </bc:ibox>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        x();
    });
    function x() {
        localStorage.clear();
        $('#orgName').attr('disabled', 'true');//姓名
        $('#identifyType').attr('disabled', 'true');
        $('#uniscCode').attr('disabled', 'true');
        $('#domesticOrForeign').attr('disabled', 'true');
        $('#legalPerson').attr('disabled', 'true');
        $('#regCapital').attr('disabled', 'true');
        $('#orgNature').attr('disabled', 'true');
        $('#regAddress').attr('disabled', 'true');
        $('#regCapitalCurrency').attr('disabled', 'true');
        $('#businessScope').attr('disabled', 'true');
        $('#dOF').attr('disabled', 'true');
        $('#dOF1').attr('disabled', 'true');
    }
</script>
</body>
</html>
