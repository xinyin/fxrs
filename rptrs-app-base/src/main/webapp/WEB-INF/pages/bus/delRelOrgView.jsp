<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <br:css pack="form"/>
    <title>RelOrg</title>
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
<body class="gray-bg" style="background:white ">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="false">
            <bf:form id="form" method="post" validation="false">
                <div class="boxse">
                    <bf:formGroup>
                        <bf:formGroup>
                            <bc:col col="4">
                                <bc:label cls="required" text="机构名称" col="5" formControl="true"/>
                                <bf:text id="orgName" name="orgName" col="7" value="${relOrg.orgName}" disabled="true">
                                    <bv:notEmpty/>
                                </bf:text>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label cls="required" text="证件类型" col="5" formControl="true"/>
                                <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" value="${relOrg.identifyType}" disabled="true">
                                    <bf:option text="请选择" value=""/>
                                    <bv:notEmpty/>
                                </bf:select>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label cls="required" text="证件号码" col="5" formControl="true"/>
                                <bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrg.uniscCode}" disabled="true">
                                    <bv:notEmpty/>
                                </bf:text>
                            </bc:col>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:col col="4">
                                <bc:label cls="required" text="境内/境外" col="5" formControl="true"/>
                                <bf:checkGroup id="domesticOrForeign" name="domesticOrForeign" col="7" value="${relOrg.domesticOrForeign}" disabled="true">
                                    <bf:radio id="chk" value="1" label="境内" checked="true">
                                        <bv:notEmpty/>
                                    </bf:radio>
                                    <bf:radio id="chk1" value="2" label="境外" />
                                </bf:checkGroup>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label cls="required" text="是否为本主体控股公司" col="5" formControl="true"/>
                                <bf:select id="isSubsidiary" name="isSubsidiary" col="7" dataSource="custom" disabled="true"
                                           url="sys/dictItem/getDictItems/BUS-isSubsidiary" value="${relOrg.isSubsidiary}">
                                    <bf:option text="请选择" value=""></bf:option>
                                    <bv:notEmpty/>
                                </bf:select>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label text="机构状态" col="5" formControl="true"/>
                                <bf:select id="status" name="status" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-Status" value="${relOrg.status}" disabled="true">
                                    <bf:option text="请选择" value=""></bf:option>
                                </bf:select>
                            </bc:col>

                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:col col="4">
                                <bc:label text="企业类型" col="5" formControl="true"/>
                                <bf:select id="orgType" name="orgType" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-CompanyType" value="${relOrg.orgType}" disabled="true">
                                    <bf:option text="请选择" value=""></bf:option>
                                </bf:select>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label text="注册地址" col="5" formControl="true"/>
                                <bf:text id="regAddress" name="regAddress" col="7" value="${relOrg.regAddress}" disabled="true">
                                </bf:text>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
                                <bf:text id="regCapital" name="regCapital" col="7" value="${relOrg.regCapital}" disabled="true">
                                </bf:text>
                            </bc:col>
                        </bf:formGroup>

                        <bf:formGroup>
                            <bc:col col="4">
                                <bc:label text="法定代表人" col="5" formControl="true"/>
                                <bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrg.legalPerson}" disabled="true">
                                </bf:text>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label text="法定代表人证件类型" col="5" formControl="true"/>
                                <bf:select id="legalIdentifyType" name="legalIdentifyType" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.legalIdentifyType}" disabled="true">
                                    <bf:option text="请选择" value=""/>
                                </bf:select>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label text="法定代表人证件号码" col="5" formControl="true"/>
                                <bf:text id="legalIdentifyNumber" name="legalIdentifyNumber" col="7"
                                         value="${relOrg.legalIdentifyNumber}" disabled="true">
                                    <bv:stringLength min="5" max="18" trim="true" message="请输入正确的证件号码"/>
                                </bf:text>
                            </bc:col>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:col col="4">
                                <bc:label text="统计口径" col="5" formControl="true"/>
                                <bf:text id="regulatorYbj" name="regulatorYbj" col="7"
                                         value="银保监会" disabled="true">
                                </bf:text>
                            </bc:col>
                            <bc:col col="4">
                                <bc:label text="集团证件号" col="5" formControl="true"/>
                                <bf:text id="memGroup" name="memGroup" col="7" value="${relOrg.memGroup}" disabled="true">
                                </bf:text>
                            </bc:col>
                            <bc:col col="4">
                                <div id="note" style="float: right;margin: 0.2% 0 !important;">
                                    <p style="color: red;margin: auto;text-align: center;font-weight: 700;line-height: 2rem;font-size: smaller">根据监管要求，若两个关联方或多个关联方属于同一集团时，需要合并计算。企业所属集团时，集团证件号必填，按照统一社会信用代码填写。</p>
                                </div>
                            </bc:col>
                        </bf:formGroup>
                        <bf:formGroup>
                            <bc:col col="12">
                                <bc:label text="经营范围" col="1" formControl="true"/>
                                <bf:text id="businessScope" name="businessScope" col="10" multiple="true" disabled="true" value="${relOrg.businessScope}"></bf:text>
                            </bc:col>
                        </bf:formGroup>
                    </bf:formGroup>
                </div>
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
</div>
<br:js pack="form"/>
<be:processEvent/>
<script>
    $(document).ready(function () {
        var val = "${relPsn.isShareholder}";
        if (val == '1') {
            isShareholder('1')
        }else{
            isShareholder('0')
        }
    });
    function isShareholder(val) {
        if (val == '1') {
            $('#aboutAandP').css('display', 'block')
            localStorage.vals = val;
        }
        if (val == '0') {
            $('#aboutAandP').css('display', 'none')
            localStorage.vals = val;
        }
        return val;
    }
</script>
</body>
</html>