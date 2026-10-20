<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>RelOrg</title>
    <script>
        function affiliateInformation() {
            var pid = $('#pid').val();
            window.location = '${base}/bus/relOrg/listAffi/' + pid;
        }

        function orgRelInformation() {
            var pid = $('#pid').val();
            window.location = '${base}/bus/rel/list/' + pid;
        }
    </script>
    <style>
        .boxse .form-group .col-xs-4{
            margin: 0.3% 0!important;
        }
        .boxse .form-group .col-xs-12{
            margin: 0.3% 0!important;
        }
        .boxse .form-group .col-xs-12 label{
            display: inline-block;
            float: left;
            margin: 0 0 0 4%;
            padding: 0 2% 0 0;
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
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="relOrgBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${relOrg.pid}"/>
            <div class="boxse">
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="企业名称" col="5" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="7" value="${relOrg.orgName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="企业类型" col="5" formControl="true"/>
                        <bf:select id="orgType" name="orgType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/SUB-BusinessType" value="${relOrg.orgType}">
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="identifyType" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.identifyType}">
                        </bf:select>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="社会统一信用代码" col="5" formControl="true"/>
                        <bf:text id="uniscCode" name="uniscCode" col="7" value="${relOrg.uniscCode}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册资本(万元/人民币)" col="5" formControl="true"/>
                        <bf:text id="regCapital" name="regCapital" col="7" value="${relOrg.regCapital}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="注册资本币种代码" col="5" formControl="true"/>
                        <bf:text id="regCapitalCurrency" name="regCapitalCurrency" col="7" value="${relOrg.regCapitalCurrency}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="营业范围" col="5" formControl="true"/>
                        <bf:text id="businessScope" name="businessScope" col="7" value="${relOrg.businessScope}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="4">
                        <bc:label text="机构状态" col="5" formControl="true"/>
                        <bf:select id="status" name="status" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Status" value="${relOrg.status}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="法定代表人" col="5" formControl="true"/>
                        <bf:text id="legalPerson" name="legalPerson" col="7" value="${relOrg.legalPerson}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="境内外标识" col="5" formControl="true"/>
                        <bf:select id="domesticOrForeign" name="domesticOrForeign" col="7" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relOrg.domesticOrForeign}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="是否为股东" col="5" formControl="true"/>
                        <bf:checkGroup name="isShareholder" col="7" value="${relOrg.isShareholder}">
                            <bf:radio value="1" label="是" checked="true"/>
                            <bf:radio value="0" label="否"/>
                        </bf:checkGroup>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="是否国有企业" col="5" formControl="true"/>
<%--                        <bf:text id="stateOwned" name="stateOwned" col="8" value="${relOrg.stateOwned}">--%>
<%--                        </bf:text>--%>
                        <bf:select id="stateOwned" name="stateOwned" col="7" dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo" value="${relOrg.stateOwned}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="股东代码" col="5" formControl="true"/>
                        <bf:text id="shareholdersCode" name="shareholdersCode" col="7" value="${relOrg.shareholdersCode}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="账户关联银行开户行" col="6" formControl="true"/>
                        <bf:text id="openBank" name="openBank" col="6" value="${relOrg.openBank}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联银行户账号" col="5" formControl="true"/>
                        <bf:text id="tradeAccount" name="tradeAccount" col="7" value="${relOrg.tradeAccount}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="持股数量" col="5" formControl="true"/>
                        <bf:text id="holeShareAmount" name="holeShareAmount" col="7" value="${relOrg.holeShareAmount}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="持股占比" col="5" formControl="true"/>
                        <bf:text id="shareholdingProportion" name="politicsStatus" col="7" value="${relOrg.shareholdingProportion}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="注册地" col="5" formControl="true"/>
                        <bf:text id="regAddress" name="regAddress" col="7" value="${relOrg.regAddress}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="冻结/解冻原因" col="0" formControl="true"/>
                        <bf:text id="reason" name="reason" col="7" multiple="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
                    <bc:button type="success" text="确认提交" icon="arrow-circle-right" click="sub()">
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            <%--var uniscCode = $('#uniscCode').value();--%>
                            window.location = '${base}/bus/gdInfo/list';
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
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
    function sub() {
        var identifyType = $('#identifyType').find("option:selected").text();
        var domesticOrForeign = $('#domesticOrForeign').find("option:selected").text();
        var orgType = $('#orgType').find("option:selected").text();
        var status = $('#status').find("option:selected").text();
        var stateOwned = $('#stateOwned').find("option:selected").text();
        var formData = $('#editForm').serializeJson();
        var myDate = new Date();
        formData.id =  myDate.getTime();
        formData.type = "orgJD";
        formData.identifyType =identifyType;
        formData.orgType = orgType;
        formData.status = status;
        formData.stateOwned = stateOwned;
        formData.domesticOrForeign = domesticOrForeign;
        var data = JSON.stringify(formData);
        formData = $.helper.base64Encode(data);
    debugger;
        var obj = {
            procDefCode: 'trancredit',
            oldData: '',
            newData: formData,
            uploadSessions: '',
            procInstId: ''
        }
        $.mask.post('${base}/workflow/process/saveAndStart', obj, function(data){
            if(data.success)
            {
                $.messager.alert('提示', '提交成功。', 'info', function(){
                    window.location = '${base}/workflow/process/runingList';
                });
            }
            else
            {
                $.messager.alert('错误', data.msg, 'error');
            }
        });
    }
</script>
</body>
</html>
