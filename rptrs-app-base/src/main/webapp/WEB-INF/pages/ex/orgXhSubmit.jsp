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
        .boxse .form-group .col-xs-5 {
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
                    <bc:col col="5">
                        <bc:label text="企业名称" col="4" formControl="true"/>
                        <bf:text id="orgName" name="orgName" col="8" value="${relOrg.orgName}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="企业类型" col="4" formControl="true"/>
                        <%--                        <bf:select id="orgType" name="orgType" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/SUB-BusinessType" value="${relOrg.orgType}">--%>
                        <%--                        </bf:select>--%>
                        <bf:text id="orgType" name="orgType" col="8" value="${relOrg.orgType}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="5">
                        <bc:label text="证件类型" col="4" formControl="true"/>
                        <%--                        <bf:select id="identifyType" name="identifyType" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-IdentifyType" value="${relOrg.identifyType}">--%>
                        <%--                        </bf:select>--%>
                        <bf:text id="identifyType" name="identifyType" col="8" value="${relOrg.identifyType}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="5">
                        <bc:label text="社会统一信用代码" col="4" formControl="true"/>
                        <bf:text id="uniscCode" name="uniscCode" col="8" value="${relOrg.uniscCode}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="注册资本(万元/人民币)" col="4" formControl="true"/>
                        <bf:text id="regCapital" name="regCapital" col="8" value="${relOrg.regCapital}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="5">
                        <bc:label text="注册资本币种代码" col="4" formControl="true"/>
                        <bf:text id="regCapitalCurrency" name="regCapitalCurrency" col="8"
                                 value="${relOrg.regCapitalCurrency}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="营业范围" col="4" formControl="true"/>
                        <bf:text id="businessScope" name="businessScope" col="8" value="${relOrg.businessScope}">
                        </bf:text>
                    </bc:col>

                    <bc:col col="5">
                        <bc:label text="机构状态（1-正常，2-停业）" col="4" formControl="true"/>
                        <%--                        <bf:select id="status" name="status" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-Status" value="${relOrg.status}">--%>
                        <%--                        </bf:select>--%>
                        <bf:text id="status" name="status" col="8" value="${relOrg.status}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="法定代表人" col="4" formControl="true"/>
                        <bf:text id="legalPerson" name="legalPerson" col="8" value="${relOrg.legalPerson}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="境内外标识(1-境内，2-境外)" col="4" formControl="true"/>
                        <%--                        <bf:select id="domesticOrForeign" name="domesticOrForeign" col="8" dataSource="custom"  url="sys/dictItem/getDictItems/BUS-DomesticOrForeign" value="${relOrg.domesticOrForeign}">--%>
                        <%--                        </bf:select>--%>
                        <bf:text id="domesticOrForeign" name="domesticOrForeign" col="8"
                                 value="${relOrg.domesticOrForeign}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="是否为股东" col="4" formControl="true"/>
                        <bf:checkGroup name="isShareholder" col="8" value="${relOrg.isShareholder}">
                            <bf:radio value="1" label="是" checked="true"/>
                            <bf:radio value="0" label="否"/>
                        </bf:checkGroup>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="是否国有企业" col="4" formControl="true"/>
                        <%--                        <bf:text id="stateOwned" name="stateOwned" col="8" value="${relOrg.stateOwned}">--%>
                        <%--                        </bf:text>--%>
<%--                        <bf:select id="stateOwned" name="stateOwned" col="8" dataSource="custom"--%>
<%--                                   url="sys/dictItem/getDictItems/BUS-isNo" value="${relOrg.stateOwned}">--%>
<%--                        </bf:select>--%>
                        <bf:text id="stateOwned" name="stateOwned" col="8" value="${relOrg.stateOwned}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="股东代码" col="4" formControl="true"/>
                        <bf:text id="shareholdersCode" name="shareholdersCode" col="8"
                                 value="${relOrg.shareholdersCode}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="账户关联银行账户开户行" col="4" formControl="true"/>
                        <bf:text id="openBank" name="openBank" col="8" value="${relOrg.openBank}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="关联银行户账号" col="4" formControl="true"/>
                        <bf:text id="tradeAccount" name="tradeAccount" col="8" value="${relOrg.tradeAccount}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="持股数量" col="4" formControl="true"/>
                        <bf:text id="holeShareAmount" name="holeShareAmount" col="8" value="${relOrg.holeShareAmount}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="持股占比" col="4" formControl="true"/>
                        <bf:text id="shareholdingProportion" name="politicsStatus" col="8"
                                 value="${relOrg.shareholdingProportion}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="注册地" col="4" formControl="true"/>
                        <bf:text id="regAddress" name="regAddress" col="8" value="${relOrg.regAddress}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="5">
                        <bc:label text="销户原因" col="4" formControl="true"/>
                        <bf:text id="reason" name="reason" col="8" multiple="true" value="${reason}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<be:processEvent/>
<script type="text/javascript">

</script>
</body>
</html>
