<%@ taglib prefix="bev" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>quota</title>

    <style>
        .boxse .form-group .col-xs-5{
            margin: 0.3% 0!important;
        }
        .boxse .has-success .form-control,.boxse .has-success .form-control:focus {
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
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relOrg.pid}"/>
            <div class="boxse">
                <bf:formGroup   hrp="bottom">
                    <bc:col col="5">
                        <bc:label text="内部人名称" col="4" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="7" value="${relOrg.orgName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="证件类型" col="4" formControl="true"/>
                        <bf:select id="identifyType1" name="identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.identifyType}">
                            <%--<z:select name="identifyType1" id="identifyType" value="${identifyType}" method="BiddingInfo.getBidCompany" defaultBlank="true" οnchange=""></z:select>--%>
                        </bf:select>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="证件号码" col="4" formControl="true"/>
                        <bf:text id="uniscCode1" name="uniscCode" col="7" value="${relOrg.uniscCode}">
                            <%--<bv:remote url="${base}/bus/quota/uniscCode" name="uniscCode" message="dd"/>--%>

                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="法定代表人" col="4" formControl="true"/>
                        <bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrg.legalPerson}" disabled="true">
                        </bf:text>
                    </bc:col>

                    <bc:col col="5">
                    <bc:label text="证件类型" col="4" formControl="true"/>
                    <bf:select id="legalIdentifyType" name="legalIdentifyType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.legalIdentifyType}" disabled="true">
                    </bf:select>
                </bc:col>
                    <bc:col col="5">
                        <bc:label text="证件号码" col="4" formControl="true"/>
                        <bf:text id="legalIdentifyNumber" name="legalIdentifyNumber" col="7" value="${relOrg.legalIdentifyNumber}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="机构名称" col="4" formControl="true"/>
                        <bf:select id="mgerOrg" name="mgerOrg" col="7" disabled="true" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-GHOrg" value="${relOrg.mgerOrg}">
                        </bf:select>
                    </bc:col>

                    <bc:col col="5">
                        <bc:label text="预设额度币种" col="4" formControl="true"/>
                        <bf:select id="presetLimitCurrency" name="presetLimitCurrency" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Currency" value="${quota.presetLimitCurrency}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="预设额度" col="4" formControl="true"/>
                        <bf:text id="presetLimit" name="presetLimit" col="7" value="${quota.presetLimit}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="5">
                        <bc:label text="可用额度" col="4" formControl="true"/>
                        <bf:text id="availableLimit" name="availableLimit" col="7" disabled="true" value="${quota.availableLimit}">
                        </bf:text>
                    </bc:col>
                    <bf:formGroup col="5">
                        <bc:label text="是否生效" col="4" formControl="true"/>
                        <bf:select name="validStatus" col="7" dataSource="custom">
                            <bf:option text="是" value="1"></bf:option>
                            <bf:option text="否" value="0"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_validStatus");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
            </div class="boxse" id="ismianhd">
            <bf:formGroup>
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/quota/update', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/quota/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/quota/list';
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
    $("#uniscCode1").blur(function () {
        var uniscCode = $(this).val();
        console.log("证件号码: "+ uniscCode)
        $.ajax({
            type: 'GET',
            url: '${base}/bus/quota/uniscCode',
            data: {
                "uniscCode": uniscCode
            },
            dataType: 'json',
            success: function (data) {
                console.log(data)
                // var type = data.type;
                $('#legalPerson').val(data.name);
                $('#uniscCode').val(data.number);
                // $('#identifyType').val(data.type);

            }
        });
    });
</script>
</body>
</html>
