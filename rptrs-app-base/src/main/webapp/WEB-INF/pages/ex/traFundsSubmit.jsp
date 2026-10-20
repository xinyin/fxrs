<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表（保险）-资金运用类</title>
    <style>
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

        textarea {
            resize: none; /* Disables resizability */
        }

        #addInfos {
            color: #B48732;
            background: none;
            border: none;
            padding: 6px 16px;
        }

        .text-style {
            font-weight: bold;
            padding: 0;
            text-align: left !important;
            color: #404040;
            font-size: 16px;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traFundsBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="relId" name="relId" value="${traFunds.relId}"/>
            <bf:hidden id="relNumber" name="relNumber" value="${traFunds.relNumber}"/>
            <bf:hidden id="relType" name="relType" value="${traFunds.relType}"/>
            <bf:hidden id="pid" name="pid" value="${traFunds.pid}"/>
            <bf:hidden id="ruleResult" name="ruleResult" value=""/>
            <bf:hidden id="total" name="total" value="${traFunds.totolTradeAmount}"/>
            <bf:hidden id="amount" name="amount" value="${traFunds.contractAmount}"/>
            <bf:hidden id="transNum" name="transNum" value="${traFunds.transNum}"/>
            <bf:hidden id="tradeZeroAmount" name="tradeZeroAmount" value="${traFunds.tradeZeroAmount}"/>
            <bf:hidden id="status" name="status" value="${traFunds.status}"/>
            <%--未达到累积关联重大的关联方relNumbers,多个关联方使用，隔开--%>
            <bf:hidden id="notMajorRelNumber" name="notMajorRelNumber" value="${traFunds.notMajorRelNumber}"/>
            <bc:row cls="m-b">
                <bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
                    <bf:formGroup col="10">
                        <bc:label text="交易发起方信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="2">
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易主体名称" col="5" formControl="true"/>
                        <bf:text id="traSubName" name="traSubName" col="7" disabled="true"
                                 value="复星保德信人寿保险有限公司"></bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="管理机构" col="5" formControl="true"/>
                        <bf:text id="managerOrg" name="managerOrg" col="7"
                                 value="${traFunds.managerOrg}"
                                 disabled="true">
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="12">
                        <bc:label cls="required" text="交易名称" col="2" formControl="true"/>
                        <bf:text id="tranName" name="tranName" col="10" click=""
                                 value="${traFunds.tranName}" disabled="${opt == 'view'}">

                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="发起方是否为控股子公司" col="5" formControl="true"/>
                        <bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
                                   value="${traFunds.isHoldingSubsidiary}"
                                   disabled="${opt == 'view'}" dataSource="fixedDict" url="IsStatusDict">
                            <be:event type="select">
                                HoldingSubsidiarySub(e.target.value);
                                isInRoad();
                                <%--                                isGreatTrade();--%>
                                isConsiderType();
                            </be:event>
                            <bf:option text="请选择" value=""></bf:option>

                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div id="holdingSubsidiaryNameDiv" title="控股子公司全称">
                            <bc:label text="发起方公司全称" col="5" formControl="true"/>
                            <bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
                                     value="${traFunds.holdingSubsidiaryName}">

                            </bf:text>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div id="holdingSubsidiaryTypeDiv" title="控股子公司证件类型">
                            <bc:label text="发起方公司证件类型" col="5" formControl="true"/>
                            <bf:select id="holdingSubsidiaryType" name="holdingSubsidiaryType" col="7"
                                       value="${traFunds.holdingSubsidiaryType}"
                                       dataSource="custom" url="sys/dictItem/getDictItems/BUS-ORGIdentifyType">
                            </bf:select>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div id="holdingSubsidiaryNumberDiv" title="控股子公司证件号码">
                            <bc:label text="发起方公司证件号码" col="5" formControl="true"/>
                            <bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
                                     value="${traFunds.holdingSubsidiaryNumber}">

                            </bf:text>
                        </div>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>
            <bc:row cls="m-b">
                <bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
                    <bf:formGroup col="11">
                        <bc:label text="交易对手信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="1">
                        <bc:button id="addInfos" text="添加">
                            <be:event>
                                addInfos()
                            </be:event>
                        </bc:button>
                    </bf:formGroup>
                </bf:formGroup>
                <%--第二行--%>
                <bf:formGroup id="formItems">

                </bf:formGroup>
            </bc:row>
            <bc:row cls="m-b">
                <bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
                    <bf:formGroup col="10">
                        <bc:label text="交易信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="2">

                    </bf:formGroup>
                </bf:formGroup>
                <%--                <bf:formGroup hrp="bottom">--%>
                <%--                    <bf:formGroup col="4">--%>
                <%--                        <bc:label cls="required" text="关联方上年末总资产(万元)" col="5" formControl="true"/>--%>
                <%--                        <bf:text id="totalassetsLateLast" name="totalassetsLateLast" col="7"--%>
                <%--                                 value="${traFunds.totalassetsLateLast}">--%>
                <%--                        </bf:text>--%>
                <%--                    </bf:formGroup>--%>
                <%--                </bf:formGroup>--%>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="交易类型" col="5" formControl="true"/>
                        <bf:select id="tranType" name="tranType" col="7" value="${traFunds.tranType}"
                                   dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="业务类型" col="5" formControl="true"/>
                        <bf:select id="investmentType" name="investmentType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-FundsType" disabled="${opt == 'view'}"
                                   includes="" value="${traFunds.investmentType}">
                            <bf:option text="请选择"></bf:option>
                            <be:event type="select">
                                changeNext(e.target.value)
                            </be:event>

                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div id="stockrightType_Div" title="股权分类">
                            <bc:label cls="required" text="股权分类" col="5" formControl="true"/>
                            <bf:select id="stockrightType" name="stockrightType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-FundsStockrightType"
                                       disabled="${opt == 'view'}"
                                       includes="" value="${traFunds.stockrightType}">
                                <bf:option text="请选择"></bf:option>
                                <be:event type="select">
                                    changeNext(e.target.value)
                                </be:event>

                            </bf:select>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                        <bf:formGroup col="4">
                            <div id="contractNo_Div" title="合同编号">
                                <bc:label text="合同编号" col="5" formControl="true"/>
                                <bf:text id="contractNo" name="contractNo" col="7" value="${traFunds.contractNo}"
                                         disabled="${opt == 'view'}">
                                    <%--                                    --%>
                                </bf:text>
                            </div>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <div title="合同签署日期">
                                <bc:label cls="required" text="合同签署日期" col="5" formControl="true"/>
                            </div>
                            <bf:datebox id="tranDate" name="tranDate" col="7" value="${traFunds.tranDateString}"
                                        disabled="${opt == 'view'}">
                                <bv:callback message="请填写必填项目" callback="notEmpt"/>
                            </bf:datebox>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="合同金额（万元）" cls="required" col="5" formControl="true"/>
                            <bf:text id="contractAmount" name="contractAmount" col="7" disabled="${opt == 'view'}"
                                     value="${traFunds.contractAmount}" change='isInRoad(),isGreatTrade()'>
                                <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                            </bf:text>
                        </bf:formGroup>
                    </bf:formGroup>
                </bf:formGroup>


                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="关联交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="relTradeAmount" name="relTradeAmount" col="7" disabled="${opt == 'view'}"
                                 value="${traFunds.relTradeAmount}"
                                 change='isInRoad(),isGreatTrade(),isConsiderType();'>

                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div id="busStartTime_Div" title="关联交易发生的起始日期">
                            <bc:label text="业务起始日期" col="5" formControl="true"/>
                            <bf:datebox id="busStartTime" name="busStartTime" col="7"
                                        value="${traFunds.busStartTimeString}"
                                        disabled="${opt == 'view'}">
                                <%--                                --%>
                            </bf:datebox>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="关联交易发生的结束日期">
                            <bc:label text="业务结束日期" col="5" formControl="true"/>
                        </div>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traFunds.busEndTimeString}"
                                    disabled="${opt == 'view'}"/>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="实际交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="pracTradeAmount" name="pracTradeAmount" col="7" disabled="${opt == 'view'}"
                                 value="${traFunds.pracTradeAmount}"
                                 change='isInRoad(),isGreatTrade(),isConsiderType();'>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="是否为境外投资" col="5" formControl="true"/>
                        <bf:select id="isOverseasinvestment" name="isOverseasinvestment" col="7"
                                   value="${traFunds.isOverseasinvestment}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="IsStatusDict">
                            <bf:option text="请选择" value=""></bf:option>

                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易定价是否为国家规定" col="5" formControl="true"/>
                        <bf:select id="isDealPrice" name="isDealPrice" col="7" value="${traFunds.isDealPrice}"
                                   disabled="${opt == 'view'}" dataSource="fixedDict" url="IsStatusDict">
                            <be:event type="select">
                                isConsiderType();
                            </be:event>
                            <bf:option text="请选择" value=""></bf:option>

                        </bf:select>
                    </bf:formGroup>

                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="是否现金认购" col="5" formControl="true"/>
                        <bf:select id="isCashSubscription" name="isCashSubscription" col="7"
                                   value="${traFunds.isCashSubscription}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="IsStatusDict">
                            <be:event type="select">
                                isConsiderType();
                            </be:event>
                            <bf:option text="请选择" value=""></bf:option>

                        </bf:select>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                        <bf:select id="majorTrade" name="majorTrade" col="7" value="${traFunds.majorTrade}"
                                   disabled="true" dataSource="fixedDict"
                                   url="IsStatusDict">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="达到重大关联交易的情形" col="5" formControl="true"/>
                        <bf:select id="majorType" name="majorType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-majorType" disabled="true"
                                   includes="" value="${traFunds.majorType}">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="审议类型" col="5" formControl="true"/>
                        <bf:select id="considerType" name="considerType" col="7" value="${traFunds.considerType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">

                        </bf:select>
                    </bf:formGroup>
                    <%--                    <div id="depositType_Div" title="存款类型" style="display: none">--%>
                    <%--                        <bf:formGroup col="4">--%>
                    <%--                            <bc:label text="存款类型" col="5" formControl="true"/>--%>
                    <%--                            <bf:select id="depositType" name="depositType" col="7" dataSource="custom"--%>
                    <%--                                       url="sys/dictItem/getDictItems/INS-DepositType"--%>
                    <%--                                       disabled="${opt == 'view'}"--%>
                    <%--                                       includes="" value="${traFunds.investmentMethod}">--%>
                    <%--                                --%>
                    <%--                            </bf:select>--%>
                    <%--                        </bf:formGroup>--%>
                    <%--                    </div>--%>
                </bf:formGroup>


                <bf:formGroup hrp="bottom">
                    <div id="investmentMethod_Div" class="Type2" style="display: none">
                        <bf:formGroup col="4">
                            <bc:label text="投资方式" col="5" formControl="true"/>
                            <bf:select id="investmentMethod" name="investmentMethod" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-FundsinvestmentMethod"
                                       disabled="${opt == 'view'}"
                                       includes="" value="${traFunds.investmentMethod}">

                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <div id="shareDebtRatio_Div" title="投资不动产类交易时的股权占比">
                                <bc:label text="股权比例（%）" col="5" formControl="true"/>
                                <bf:text id="shareDebtRatio" name="shareDebtRatio" col="7"
                                         value="${traFunds.shareDebtRatio}" disabled="${opt == 'view'}">
                                    <%--                                --%>
                                </bf:text>
                            </div>
                        </bf:formGroup>
                    </div>
                    <div id="4_1" class="Type4" style="display: none">
                        <bf:formGroup hrp="bottom">
                            <bf:formGroup col="4">
                                <bc:label cls="required" text="金融产品名称" col="5" formControl="true"/>
                                <bf:text id="financialAssstsName" name="financialAssstsName" col="7"
                                         value="${traFunds.financialAssstsName}" disabled="${opt == 'view'}">

                                </bf:text>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label cls="required" text="产品批准/注册/备案号" col="5" formControl="true"/>
                                <bf:text id="financialAssstsNum" name="financialAssstsNum" col="7"
                                         value="${traFunds.financialAssstsNum}" disabled="${opt == 'view'}">

                                </bf:text>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <div id="financialProRatio_04_Div" title="投资金融产品份额占比（%)">
                                    <bc:label text="金融产品份额占比" col="5" formControl="true"/>
                                    <bf:text id="financialProRatio" name="financialProRatio" col="7"
                                             value="${traFunds.financialProRatio}" disabled="${opt == 'view'}">
                                        <%--                                        --%>
                                    </bf:text>
                                </div>
                            </bf:formGroup>
                            <bf:formGroup hrp="bottom">
                                <bf:formGroup col="4">
                                    <div id="underIsRel_04_Div" title="底层资产是否涉及关联方">
                                        <bc:label text="资产是否涉及关联方" col="5" formControl="true"/>
                                        <bf:select id="underIsRel" name="underIsRel" col="7" dataSource="custom"
                                                   url="sys/dictItem/getDictItems/BUS-isNo"
                                                   value="${traFunds.underIsRel}" disabled="${opt == 'view'}">
                                            <bf:option text="请选择" value=""></bf:option>
                                            <%--                                            --%>
                                        </bf:select>
                                    </div>
                                </bf:formGroup>
                                <bf:formGroup col="4">
                                    <div id="underInvolveRel_04_Div" title="底层基础资产是否涉及控股股东或控股股东的关联方">
                                        <bc:label cls="required" text="是否涉及控股股东" col="5" formControl="true"/>
                                        <bf:select id="underInvolveRel" name="underInvolveRel" col="7"
                                                   dataSource="fixedDict"
                                                   url="IsStatusDict"
                                                   value="${traFunds.underInvolveRel}" disabled="${opt == 'view'}">
                                            <bf:option text="请选择" value=""></bf:option>
                                            <%--                                            --%>
                                            <be:event type="select">
                                                underInvolveRel(e.target.value);
                                            </be:event>
                                        </bf:select>
                                    </div>
                                </bf:formGroup>
                                <bf:formGroup col="4">
                                    <div id="underInvolveRelName_04_Div" title="底层资产涉及的关联方名称">
                                        <bc:label text="涉及关联方名称" col="5" formControl="true"/>
                                    </div>
                                    <bf:text id="underInvolveRelName" name="underInvolveRelName" col="7"
                                             value="${traFunds.underInvolveRelName}" disabled="${opt == 'view'}">
                                    </bf:text>
                                </bf:formGroup>
                                <bf:formGroup col="4">
                                    <div title="金融产品设立时的募集规模">
                                        <bc:label cls="required" text="金融产品发行规模" col="5" type="warning"
                                                  formControl="true"/>
                                    </div>
                                    <bf:text id="financialProScale" name="financialProScale" col="7" rows="1"
                                             multiple="true"
                                             change='isGreatTrade()' value="${traFunds.financialProScale}"
                                             disabled="${opt == 'view'}"></bf:text>
                                </bf:formGroup>
                            </bf:formGroup>
                        </bf:formGroup>
                    </div>
                    <div id="5_1" class="Type5" style="display: none">
                        <bf:formGroup col="4">
                            <div id="investedEnterprisesName_05_Div" title="被投资企业名称">
                                <bc:label text="被投企业名称" col="5" formControl="true"/>
                                <bf:text id="investedEnterprisesName" name="investedEnterprisesName" col="7"
                                         value="${traFunds.investedEnterprisesName}" disabled="${opt == 'view'}">
                                    <%--                                    --%>
                                </bf:text>
                            </div>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <div id="investmentRatio_05_Div">
                                <bc:label text="投资比例（%）" col="5" formControl="true"/>
                                <bf:text id="investmentRatio" name="investmentRatio" col="7"
                                         value="${traFunds.investmentRatio}" disabled="${opt == 'view'}">
                                    <%--                                --%>
                                </bf:text>
                            </div>
                        </bf:formGroup>
                    </div>
                </bf:formGroup>
            </bc:row>
            <bf:formGroup hrp="bottom">

                <bf:formGroup col="6">
                    <div title="交易价格、收益率的定价依据">
                        <bc:label cls="required" text="定价政策" col="3" formControl="true"/>
                    </div>
                    <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true"
                             value="${traFunds.pricingPolicy}" disabled="${opt == 'view'}"></bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div id="financialImpact_Div" title="对公司的财务状况及经营成果的描述">
                        <bc:label text="对财务状况及经营成果的影响" col="3" formControl="true"/>
                        <bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true"
                                 value="${traFunds.financialImpact}"
                                 disabled="${opt == 'view'}"></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="与关联方之间关联交易的对象">
                        <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                    </div>
                    <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true"
                             value="${traFunds.tradeSubject}" disabled="${opt == 'view'}"></bf:text>
                </bf:formGroup>
                <div id="1_2" class="">
                    <bf:formGroup hrp="bottom">
                        <bf:formGroup col="6">
                            <div id="investedEnterprises_Div" title="被投资企业情况" style="display: none">
                                <bc:label text="被投资企业情况" col="3" formControl="true"/>
                                <bf:text id="investedEnterprises" name="investedEnterprises" col="9" rows="2"
                                         multiple="true" value="${traFunds.investedEnterprises}"
                                         disabled="${opt == 'view'}"></bf:text>
                            </div>
                        </bf:formGroup>
                        <bf:formGroup col="6">
                            <div id="equityLastTradePrice_Div" title="前次交易价格" style="display: none">
                                <bc:label text="前次交易价格" col="3" formControl="true"/>
                                <bf:text id="equityLastTradePrice" name="equityLastTradePrice" col="9" rows="2"
                                         multiple="true" value="${traFunds.equityLastTradePrice}"
                                         disabled="${opt == 'view'}">
                                    <%--                                --%>
                                </bf:text>
                            </div>
                        </bf:formGroup>
                    </bf:formGroup>
                </div>
                <div id="imSubjectMatter_Div" title="标的情况" style="display: none">
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="标的情况" col="3" formControl="true"/>
                        <bf:text id="imSubjectMatter" name="imSubjectMatter" col="9" rows="2" multiple="true"
                                 value="${traFunds.imSubjectMatter}" disabled="${opt == 'view'}">
                        </bf:text>
                    </bf:formGroup>
                </div>
                <bf:formGroup col="6">
                    <div title="除上述关键信息外，其他需要说明的重要或特殊的事项">
                        <bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
                    </div>
                    <bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traFunds.remark}"
                             disabled="${opt == 'view'}"> </bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label text="风险预警" col="3" formControl="true"/>
                    <div>
                        <bf:text id="limitHint" name="limitHint" col="9" rows="5" multiple="true"
                                 value="${traFunds.limitHint}" disabled="true"/>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traFunds.transNum}">
                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                    <bc:toolbar id="downFile" text="下载" type="button" handler="downFileOnclick"/>
                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="relatedName" title="文件名" width="100"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileSize" title="大小" width="100"/>
                    <bc:column field="uploadOrg" title="上传机构" width="100"
                               formatter="custom:sys/unit/selectUnit"/>
                    <bc:column field="uploadDept" title="上传部门" width="100"/>
                    <bc:column field="uploadTime" title="上传时间" width="100"/>
                </bc:table>
            </div>
            <bf:formGroup>
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button text="返回" icon="arrow-circle-left">
                        <be:event>
                            <%--                            window.location = '${base}/ins/traFunds/list';--%>
                            window.history.back(-1);
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        文件上传按钮
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div style="text-align: center" id="loading"></div>
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="${base}/static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a>
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                        <button type="submit" class="btn btn-primary" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script type="text/javascript">
    var traSubName = '86';
    //记录添加个数；按照添加点击事件累加
    var count = 0;

    //记录页面具体添加个数，按照添加 删除按钮 动态变化
    var countTmp = 0;

    //合同抽取待补录合同/交易发生日期不必填
    function notEmpt(value, validator, $field) {
        var fieId1 = $field[0].id;
        var value1 = value;
        var dataSources = '${traFunds.dataSources}';
        //生效时间
        if (fieId1 == "") {
            if ((dataSources != "3") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
    }


    //跟据审议类型规则给定审议类型得值
    function isConsiderType() {
        //是否为国家定价
        var isDealPrice = $('#isDealPrice').val();
        //是否为现金认购
        var isCashSubscription = $('#isCashSubscription').val();
        //是否为重大关联交易
        var isMajorTrade = $('#majorTrade').val();
        //关联交易金额
        var relTradeAmount = $('#relTradeAmount').val();
        //关联方类型
        var relType = $('#relType1').val();

        //如果是现金交易审议类型为免予审议
        if (isCashSubscription != null && isCashSubscription != undefined && isCashSubscription != '' && isCashSubscription == "0") {
            $('#considerType').val('1');
            $('#considerType').select2({value: '1'});
            //合同编号  contractNo_Div
            $('#contractNo_Div label font').remove();
            //业务起始日期 busStartTime_Div
            $('#busStartTime_Div label font').remove();
            //对财务状况及经营成果的影响 financialImpact_Div
            $('#financialImpact_Div label font').remove();
            //被投资企业情况 investedEnterprises_Div
            $('#investedEnterprises_Div label font').remove();
            //前次交易价格 equityLastTradePrice_Div
            $('#equityLastTradePrice_Div label font').remove();
            //标的情况 imSubjectMatter_Div
            $('#imSubjectMatter_Div label font').remove();
            //投资方式  investmentMethod_Div
            $('#investmentMethod_Div label font').remove();
            //股权比例 shareDebtRatio_Div
            $('#shareDebtRatio_Div label font').remove();
            //投资金融产品份额占比（%）
            $('#financialProRatio_04_Div label font').remove();
            //底层资产是否涉及关联方
            $('#underIsRel_04_Div label font').remove();
            //底层资产具体描述
            $('#underlyingAsset_04_Div label font').remove();
            //被投资企业名称
            $('#investedEnterprisesName_05_Div label font').remove();
            //业务模式
            $('#busModel_05_Div label font').remove();
            //盈利预期
            $('#earningsExpectaions_05_Div label font').remove();
            //投资比例（%）
            $('#investmentRatio_05_Div label font').remove();
            //投资收益分配情况
            $('#investmentAllocation_05_Div label font').remove();
            //损失分担情况
            $('#lossShare_05_Div label font').remove();
            return;
        } else {
            //如果是重大关联交易审议类型为逐笔审议
            if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != '') {
                if (isMajorTrade == "0") {
                    $('#considerType').val("3");
                    $('#considerType').select2({value: "3"});
                    if ($('#contractNo_Div label font').length == 0) {
                        $('#contractNo_Div label').prepend("<font color='red'>✶</font>");
                        $('#busStartTime_Div label').prepend("<font color='red'>✶</font>");
                        $('#financialImpact_Div label').prepend("<font color='red'>✶</font>");
                        $('#investedEnterprises_Div label').prepend("<font color='red'>✶</font>");
                        $('#equityLastTradePrice_Div label').prepend("<font color='red'>✶</font>");
                        $('#imSubjectMatter_Div label').prepend("<font color='red'>✶</font>");
                        $('#investmentMethod_Div label').prepend("<font color='red'>✶</font>");
                        $('#shareDebtRatio_Div label').prepend("<font color='red'>✶</font>");
                        $('#financialProRatio_04_Div label').prepend("<font color='red'>✶</font>");
                        $('#underIsRel_04_Div label').prepend("<font color='red'>✶</font>");
                        $('#underlyingAsset_04_Div label').prepend("<font color='red'>✶</font>");
                        $('#investedEnterprisesName_05_Div label').prepend("<font color='red'>✶</font>");
                        $('#busModel_05_Div label').prepend("<font color='red'>✶</font>");
                        $('#earningsExpectaions_05_Div label').prepend("<font color='red'>✶</font>");
                        $('#investmentRatio_05_Div label').prepend("<font color='red'>✶</font>");
                        $('#investmentAllocation_05_Div label').prepend("<font color='red'>✶</font>");
                        $('#lossShare_05_Div label').prepend("<font color='red'>✶</font>");
                    }
                    return;
                }
            }
            //当关联方为个人合同金额<=50w  机构合同金额<=500w的时候免于审议
            if (relTradeAmount != null && relTradeAmount != undefined && relTradeAmount != '' && relType != null && relType != undefined && relType != '') {
                if (relType == "PSN" && relTradeAmount <= 50) {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    $('#contractNo_Div label font').remove();
                    $('#busStartTime_Div label font').remove();
                    $('#financialImpact_Div label font').remove();
                    $('#investedEnterprises_Div label font').remove();
                    $('#equityLastTradePrice_Div label font').remove();
                    $('#imSubjectMatter_Div label font').remove();
                    $('#investmentMethod_Div label font').remove();
                    $('#shareDebtRatio_Div label font').remove();
                    $('#financialProRatio_04_Div label font').remove();
                    $('#underIsRel_04_Div label font').remove();
                    $('#underlyingAsset_04_Div label font').remove();
                    $('#investedEnterprisesName_05_Div label font').remove();
                    $('#busModel_05_Div label font').remove();
                    $('#earningsExpectaions_05_Div label font').remove();
                    $('#investmentRatio_05_Div label font').remove();
                    $('#investmentAllocation_05_Div label font').remove();
                    $('#lossShare_05_Div label font').remove();
                    return;
                }
                if (relType == "ORG" && relTradeAmount <= 500) {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    $('#contractNo_Div label font').remove();
                    $('#busStartTime_Div label font').remove();
                    $('#financialImpact_Div label font').remove();
                    $('#investedEnterprises_Div label font').remove();
                    $('#equityLastTradePrice_Div label font').remove();
                    $('#imSubjectMatter_Div label font').remove();
                    $('#investmentMethod_Div label font').remove();
                    $('#shareDebtRatio_Div label font').remove();
                    $('#financialProRatio_04_Div label font').remove();
                    $('#underIsRel_04_Div label font').remove();
                    $('#underlyingAsset_04_Div label font').remove();
                    $('#investedEnterprisesName_05_Div label font').remove();
                    $('#busModel_05_Div label font').remove();
                    $('#earningsExpectaions_05_Div label font').remove();
                    $('#investmentRatio_05_Div label font').remove();
                    $('#investmentAllocation_05_Div label font').remove();
                    $('#lossShare_05_Div label font').remove();
                    return;
                }
            }

            //如果是国家定价重大关联交易审议类型为免予审议
            if (isDealPrice != null && isDealPrice != undefined && isDealPrice != '') {
                if (isDealPrice == "0") {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    $('#contractNo_Div label font').remove();
                    $('#busStartTime_Div label font').remove();
                    $('#financialImpact_Div label font').remove();
                    $('#investedEnterprises_Div label font').remove();
                    $('#equityLastTradePrice_Div label font').remove();
                    $('#imSubjectMatter_Div label font').remove();
                    $('#investmentMethod_Div label font').remove();
                    $('#shareDebtRatio_Div label font').remove();
                    $('#financialProRatio_04_Div label font').remove();
                    $('#underIsRel_04_Div label font').remove();
                    $('#underlyingAsset_04_Div label font').remove();
                    $('#investedEnterprisesName_05_Div label font').remove();
                    $('#busModel_05_Div label font').remove();
                    $('#earningsExpectaions_05_Div label font').remove();
                    $('#investmentRatio_05_Div label font').remove();
                    $('#investmentAllocation_05_Div label font').remove();
                    $('#lossShare_05_Div label font').remove();
                    return;
                }
            }
        }
        $('#considerType').val("2");
        $('#considerType').select2({value: "2"});
        $('#contractNo_Div label font').remove();
        $('#busStartTime_Div label font').remove();
        $('#financialImpact_Div label font').remove();
        $('#investedEnterprises_Div label font').remove();
        $('#equityLastTradePrice_Div label font').remove();
        $('#imSubjectMatter_Div label font').remove();
        $('#investmentMethod_Div label font').remove();
        $('#shareDebtRatio_Div label font').remove();
        $('#financialProRatio_04_Div label font').remove();
        $('#underIsRel_04_Div label font').remove();
        $('#underlyingAsset_04_Div label font').remove();
        $('#investedEnterprisesName_05_Div label font').remove();
        $('#busModel_05_Div label font').remove();
        $('#earningsExpectaions_05_Div label font').remove();
        $('#investmentRatio_05_Div label font').remove();
        $('#investmentAllocation_05_Div label font').remove();
        $('#lossShare_05_Div label font').remove();
    }


    function HoldingSubsidiarySub(value) {
        var value = value
        if (value == '1') {
            $('#holdingSubsidiaryNumber').val("");
            $('#holdingSubsidiaryName').val("");
            $('#holdingSubsidiaryType').val("");
            $('#holdingSubsidiaryNumberDiv').css('display', 'none');
            $('#holdingSubsidiaryNameDiv').css('display', 'none');
            $('#holdingSubsidiaryTypeDiv').css('display', 'none');
            $('#holdingSubsidiaryNumberDiv label font').remove();
            $('#holdingSubsidiaryNameDiv label font ').remove();
            $('#holdingSubsidiaryTypeDiv label font ').remove();
        }
        if (value == '0') {
            $('#holdingSubsidiaryNumberDiv').css('display', 'block')
            $('#holdingSubsidiaryNameDiv').css('display', 'block')
            $('#holdingSubsidiaryTypeDiv').css('display', 'block')
            if ($('#holdingSubsidiaryNumberDiv label font').length == 0) {
                $('#holdingSubsidiaryNumberDiv label').prepend("<font color='red'>✶</font>");
                $('#holdingSubsidiaryNameDiv label').prepend("<font color='red'>✶</font>");
                $('#holdingSubsidiaryTypeDiv label').prepend("<font color='red'>✶</font>");
            }
        }
    };


    var opt = '${opt}';
    var unitName = '${unitName}';
    $(document).ready(function () {
        if (unitName != null && unitName != '' && unitName != undefined) {
            $("#managerOrg").val(unitName);
        }
        // alert(opt);
        if (opt == 'view') {
            $("#save2").css("display", "none");
            var num = $('#formItems div').length;
            for (var i = 1; i <= num; i++) {
                $("#" + "tranOpponent" + i).attr("disabled", true);
                $("#" + "certTypes" + i).attr("disabled", true);
                $("#" + "relName" + i).attr("disabled", true);
                $("#" + "relNumber" + i).attr("disabled", true);
                $("#" + "totalassetsLateLast" + i).attr("disabled", true);
                $("#addInfos").hide();
                $(".btn-danger1").hide();
            }
            if ($('#isHoldingSubsidiary').val() == '1') {
                HoldingSubsidiarySub('1');
            }
        } else {
            var val = $('#isHoldingSubsidiary').val();
            if (val != null && val != undefined && val != '') {
                HoldingSubsidiarySub(val)
            }
        }
        if ('${isConfirm}' == 'true') {
            $("#save1").css("display", "none");
            $("#docTable_tb_exportIn").css("display", "none");
            $("#docTable_tb_removeDoc").css("display", "none");
        }
        $('#tranType').val("02");
        $('#tranType').select2({value: "02"});
        var investmentType = $('#investmentType').val();
        // var count = 1;
        for (var i = 1; i < 6; i++) {
            if (investmentType == '0' + i) {
                $('.Type' + i).show();
            }
        }
    });

    function toConfirm() {
        var pid = ${pid}
            $.ajax({
                url: "${base}/ins/traFunds/confirm/" + pid,
                method: "GET",
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        window.location = '${base}/ins/traFunds/list/';
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
    }

    //查询交易对手相关方法
    function relNameSelect(relType, relId, relName1, totolTradeAmount, relNumber, certTypes, certType, tradeAmount) {

        // var relName = $("#traSubName").val();
        // if (relName == null || relName == " " || relName == undefined) {
        //     $.messager.alert('提示', '请先选择交易主体', 'warning');
        //     return false;
        // } else {
        $.messager.modal({
            winId: 'relNameSelect',
            width: 700,
            height: 420,
            title: '交易对手信息',
            url: '${base}/ins/traInvestment/relNameSelect/' + traSubName,
            args: {
                result: function (obj) {
                    $("#" + relId.id).val(obj["relId"]);
                    $("#" + relName1.id).val(obj["relName"]);
                    $("#" + totolTradeAmount.id).val(obj["totolTradeAmount"]);
                    $("#" + tradeAmount.id).val(obj["tradeZeroAmount"]);
                    $('#total').val(obj["totolTradeAmount"]);
                    $("#" + relType.id).val(obj["relType"]);
                    $("#" + relNumber.id).val(obj["relNumber"]);
                    $("#tradeZeroAmount").val(obj["tradeZeroAmount"]);
                    if (obj["identifyTypePSN"] != '') {
                        $.ajax({
                            url: "${base}/sys/dictItem/getDictItems/BUS-IdentifyType",
                            success: function (data) {
                                for (var i = 0; i < data.length; i++) {
                                    if (obj["identifyTypePSN"] == data[i].value) {
                                        $("#" + certType.id).val(data[i].value);
                                        $("#" + certTypes.id).val(data[i].name);
                                    }
                                }
                            }
                        });
                    } else {
                        $.ajax({
                            url: "${base}/sys/dictItem/getDictItems/BUS-ORGIdentifyType",
                            success: function (data) {
                                for (var i = 0; i < data.length; i++) {
                                    if (obj["identifyTypeORG"] == data[i].value) {
                                        $("#" + certType.id).val(data[i].value);
                                        $("#" + certTypes.id).val(data[i].name);
                                    }
                                }
                            }
                        });
                    }
                }
            },
            onClose: function () {
            }
        });
    }

    //根据业务类型关联显示对应字段
    function changeNext(val) {
        if (val == '01') {
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
            // $("#stockrightType_Div").css("display", "block");
            $("#equityLastTradePrice_Div").css("display", "block");
            $("#imSubjectMatter_Div").css("display", "none");
            $("#depositType_Div").css("display", "none");
            $("#investedEnterprises_Div").css("display", "block");
        }
        if (val == '02') {
            $(".Type2").css("display", "block");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
            // $("#stockrightType_Div").css("display", "none");
            $("#equityLastTradePrice_Div").css("display", "block");
            $("#imSubjectMatter_Div").css("display", "block");
            $("#investedEnterprises_Div").css("display", "none");
        }
        if (val == '03') {
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "block");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
            // $("#stockrightType_Div").css("display", "none");
            $("#equityLastTradePrice_Div").css("display", "none");
            $("#imSubjectMatter_Div").css("display", "block");
            $("#depositType_Div").css("display", "block");
            $("#investedEnterprises_Div").css("display", "none");
        }
        if (val == '04') {
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "block");
            $(".Type5").css("display", "none");
            // $("#stockrightType_Div").css("display", "none");
            $("#equityLastTradePrice_Div").css("display", "none");
            $("#imSubjectMatter_Div").css("display", "none");
            $("#depositType_Div").css("display", "none");
            $("#investedEnterprises_Div").css("display", "none");
        }
        if (val == '05') {
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "block");
            // $("#stockrightType_Div").css("display", "none");
            $("#equityLastTradePrice_Div").css("display", "none");
            $("#imSubjectMatter_Div").css("display", "none");
            $("#depositType_Div").css("display", "none");
            $("#investedEnterprises_Div").css("display", "none");
        }
        if (val == '06') {
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
            // $("#stockrightType_Div").css("display", "none");
            $("#equityLastTradePrice_Div").css("display", "none");
            $("#imSubjectMatter_Div").css("display", "none");
            $("#depositType_Div").css("display", "none");
            $("#investedEnterprises_Div").css("display", "none");
        }
    }

    function onSelect() {
        var proName = $("input[name=proName]").val();
        $.ajax({
            type: "post",
            url: "${base}/ins/traFunds/selectbuinessL",
            data: {"unitCodes": proName},
            success: function (data) {
                if (data.success) {
                    var investmentType = data.investmentType;
                    $('#investmentType').val(investmentType);
                    $('#investmentType').select2({value: investmentType});
                    var val = investmentType
                    if (val == '224') {
                        $("#1").css("display", "block");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '225') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "block");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '226') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "block");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '227') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "block");
                        $("#5").css("display", "none");
                        $("#6").css("display", "none");
                    }
                    if (val == '228') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "block");
                        $("#6").css("display", "none");
                    }
                    if (val == '229') {
                        $("#1").css("display", "none");
                        $("#2").css("display", "none");
                        $("#3").css("display", "none");
                        $("#4").css("display", "none");
                        $("#5").css("display", "none");
                        $("#6").css("display", "block");
                    }
                } else {
                    $.messager.alert('提示', data.msg, 'error');
                }
            }
        });
    }

    function exportIn() {
        $("#hiddenBtn").click();
    }

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getAllIdSelections() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        if (pid == '' || pid == undefined) {
            $.message.alert("请选中一个文件");
            return;
        }
        if (pid.length > 1) {
            $.message.alert("只能选中一个文件");
            return;
        }
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/ins/traInsenclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }

    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            $.message.alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/ins/traInsenclosure/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    $.message.alert(res.msg);
                    if (res.success) {
                        //从数据中移除
                        for (var i = 0; i < selectRows.length; i++) {
                            var row = selectRows[i];
                            rows.splice(rows.indexOf(row), 1);
                        }
                        var showData = {'total': rows.length, 'rows': rows};
                        $('#docTable').bootstrapTable('load', showData);
                    }
                }

            })
        }
    }

    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf') {
                $.message.alert("请上传pdf文件");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/ins/traInsenclosure/uploadFiles?businessType=0', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function (XMLHttpRequest) {
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: dodgerblue\">正在上传，请稍后操作...</p>");
            },
            success: function (data) {
                $("#close").click();
                $.message.alert(data.msg);
                if (data.success) {
                    //处理数据
                    var files = data.traEnclosure;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    rows = rows.concat(files);
                    //构造表格显示数据
                    var total = rows.length;
                    var showData = {'total': total, 'rows': rows}
                    $('#docTable').bootstrapTable('load', showData);
                    $("#loading").empty();
                }
            },
            error: function () {
                $("#close").click();
                $.message.alert("上传失败,请重试");
                $("#loading").empty();
            }
        })
    });

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    function selectInvestmentType(value) {
        var investmentType = $('#investmentType').val();
        for (var i = 1; i < 7; i++) {
            if (investmentType == i) {
                $('#' + i).show();
            } else {
                $('#' + i).hide();
            }
        }
    }

    function getRelInfo() {
        var relNumber = $('#relNumber').val();
        var relName = $('#relName').val();
        var obj = {
            relNumber: relNumber,
            relName: relName
        };
        $.ajax({
            url: '${base}/ins/traFunds/getRelInfo',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    var relName = data.relName;
                    var relType = data.relType;
                    var relId = data.relId;
                    var relNumber = data.relNumber;
                    var caliber = data.caliber;
                    var totalTradeAmount = data.totalTradeAmount;
                    $('#relName').val(relName);
                    $('#relNumber').val(relNumber);
                    $('#relId').val(relId);
                    $('#relType').val(relType);
                    $('#relType').select2({value: relType});
                    $('#caliber').val(caliber);
                    $('#caliber').select2({value: caliber});
                    $('#totolTradeAmount').val(totalTradeAmount);
                    $('#total').val(totalTradeAmount);
                } else {
                    $.message.alert(data.msg);
                }
            }
        });
    }

    //判断当前关联方是否拥有在途数据
    function isInRoad() {
        // var traSubName = $("#traSubName").val();
        var relId = $('#relId').val();
        var totolTradeAmount = $('#total').val();
        if (traSubName == '' || traSubName == null) {
            return;
        }
        if (relId == '' || relId == null) {
            return;
        }
        if (totolTradeAmount == '' || totolTradeAmount == null) {
            return;
        }
        var obj = {
            relId: relId,
            traSubName: traSubName,
            totolTradeAmount: totolTradeAmount
        };
        $.ajax({
            url: '${base}/bus/relPsn/queryRelNameAmount',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    var inRoldAmount = data.inRoldAmount;
                    var amount = data.amount;
                    if (amount != 0) {
                        $.messager.alert('在途交易金额提示', inRoldAmount, 'warning');
                    }
                }
            }
        });
    }

    /**判断是否重大关联交易*/
    function isGreatTrade() {
        var list = new Array();
        var falg;
        var relInfs = [];
        for (var i = 1; i <= $("#formItems div").length; i++) {
            //委托机构不判断是否重大
            // var traSubName = $("#traSubName").val();
            var relNumberTmp = $("#relNumber" + i).val();
            //过滤 i 等于已经删除的item
            if (relNumberTmp === undefined || relNumberTmp === '') {
                continue
            }
            /**封装关联交易对手信息*/
            var relItemInfo = {
                "relId": "",
                "relNumber": "",
                "relType": "",
                "relName": "",
                "totolTradeAmount": "",
                "certType": "",
                "tranOpponent": "",
                // "assetsLastQuarter": "",
                "tradeAmount": "",
                "pid": ""
            }
            relItemInfo.relId = $("#relId" + i).val();
            relItemInfo.relNumber = $("#relNumber" + i).val();
            relItemInfo.relType = $("#relType" + i).val();
            relItemInfo.relName = $("#relName" + i).val();
            relItemInfo.totolTradeAmount = $("#totolTradeAmount" + i).val();
            relItemInfo.certType = $("#certType" + i).val();
            relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
            // relItemInfo.assetsLastQuarter = $("#assetsLastQuarter" + i).val();
            relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
            relItemInfo.pid = $("#pid" + i).val();
            relInfs.push(relItemInfo)
        }
        /**合同交易金额*/
        var relTradeAmount = $('#relTradeAmount').val();
        if (relTradeAmount == '' || relTradeAmount == null) {
            return;
        }
        /**实际交易金额*/
        var pracTradeAmount = $('#pracTradeAmount').val();
        /**调用重大校验接口*/
        var obj = {
            relTradeAmount: relTradeAmount,
            pracTradeAmount: pracTradeAmount,
            relInfoListStr: JSON.stringify(relInfs)
        };
        $.ajax({
            url: '${base}/ins/traFunds/GreatCheck',
            data: obj,
            type: 'post',
            async: false,
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    var notMajorRelNumber = data.notMajorRelNumber;
                    /**替换值*/
                    console.log(data)
                    $('#notMajorRelNumber').val(notMajorRelNumber);
                    list.push(data);
                } else {
                    $.message.alert(data.msg);
                }
            }
        });
        console.log(list);
        $('#majorTrade').val(list[0].isGreatTrade);
        $('#majorTrade').select2({value: (list[0].isGreatTrade)})
        if (list[0].isGreatTrade == '0') {
            $('#majorType').val(list[0].majorType);
            $('#majorType').select2({value: (list[0].majorType)})
            isConsiderType();
            return false;
        } else {
            $('#majorType').val(" ");
            $('#majorType').select2({value: (" ")})
            isConsiderType();
        }
    }

    //加上红点
    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });

        // 动态添加 按钮 自动触发
        var relList = ${traFunds.relInfoListStr};
        if (relList.length == 0) {
            $("#addInfos").trigger("click");
        } else {
            for (var i = 0; i < relList.length; i++) {
                addInfos(relList[i]);
            }
        }
    });

    //动态添加交易对手信息
    function addInfos(relInfo) {
        count++;
        countTmp++;
        var item = "itme" + count
        var tranOpponent = "tranOpponent" + count;
        var certType = "certType" + count;
        var certTypes = "certTypes" + count;
        var relName = "relName" + count;
        var relNumber = "relNumber" + count;
        var totolTradeAmount = "totolTradeAmount" + count;
        var assetsLastQuarter = "assetsLastQuarter" + count;
        var tradeAmount = "tradeAmount" + count;
        var totalassetsLateLast = "totalassetsLateLast" + count;
        var relId = "relId" + count;
        var relType = "relType" + count;
        var pid = "pid" + count;
        //formItems
        var itemStr = ' <div class="form-group " id="' + item + '" >\n' +
            '   <div class="form-group ">\n' +
            '       <div class="form-group  col-xs-6">\n' +
            '           <label  class="control-label col-xs-4 "><font color="red">✶</font>交易对手名称</label>\n' +
            '           <div class="  col-xs-8">\n' +
            '               <input type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + tranOpponent + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.tranOpponent;
        }
        //onblur="initRelInfo(\'' + relNumber + '\',\'' + relName + '\',\'' + totolTradeAmount + '\',\'' + relType + '\',\'' + assetsLastQuarter + '\')"
        itemStr = itemStr + '"  >\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-5">\n' +
            '           <label  class="control-label col-xs-5">关联方证件类型</label>\n' +
            '           <div class="col-xs-7">\n' +
            '               <input  type="text" data-fv-notempty="true"  class="jep-input form-control"  id="' + certTypes + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            // itemStr = itemStr + relInfo.certType;
            console.log(relType + ":" + relInfo.relType);
            if (relInfo.relType == 'PSN') {
                $.ajax({
                    url: "${base}/sys/dictItem/getDictItems/BUS-IdentifyType",
                    async: false,
                    success: function (data) {

                        for (var i = 0; i < data.length; i++) {
                            if (relInfo.certType == data[i].value) {
                                itemStr = itemStr + data[i].name;

                            }
                        }
                    }
                });
            } else if (relInfo.relType == 'ORG') {
                $.ajax({
                    url: "${base}/sys/dictItem/getDictItems/BUS-ORGIdentifyType",
                    async: false,
                    success: function (data) {

                        for (var i = 0; i < data.length; i++) {
                            if (relInfo.certType == data[i].value) {
                                itemStr = itemStr + data[i].name;
                            }
                        }
                    }
                });
            }
        }
        itemStr = itemStr + '">\n' +
            '<input   type="hidden" data-fv-notempty="true"  class="jep-input form-control"  id="' + certType + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.certType;
        }
        itemStr = itemStr + '">\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-1"></div>\n' +
            '   </div>\n' +
            '   <div class="form-group ">\n' +
            '       <div class="form-group  col-xs-6">\n' +
            '           <label  class="control-label col-xs-4">关联方名称</label>\n' +
            '           <div class="  col-xs-8">\n' +
            '           <input type="text" onclick="relNameSelect(' + relType + ',' + relId + ',' + relName + ',' + totolTradeAmount + ',' + relNumber + ',' + certTypes + ',' + certType + ',' + tradeAmount + ')" class="jep-input form-control " id="' + relName + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relName;
        }
        itemStr = itemStr + '">\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-5">\n' +
            '              <label  class="control-label col-xs-5 " title="关联方证件号码"><font color="red">✶</font>关联方证件号码</label>\n' +
            '              <div class="  col-xs-7">\n' +
            '                   <input type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + relNumber + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relNumber;
        }
        itemStr = itemStr + '">\n' +
            '               </div>\n' +
            '        </div>\n' +
            '        <div class="form-group  col-xs-1">\n' +
            '           <div class=" btn-danger1" onclick="delItme(\'' + item + '\')">删除</div>\n' +
            '        </div>\n' +
            '   </div>\n' +
            '   <div class="form-group ">\n' +
            '        <div class="form-group  col-xs-6">\n' +
            '             <label  class="control-label col-xs-4" title="本年度与该关联方累计交易金额(万元)">年度累计金额(万元)</label>\n' +
            '             <div class="  col-xs-8">\n' +
            '                   <input type="text" disabled="disabled" class="jep-input form-control" id="' + totolTradeAmount + '" value="';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.totolTradeAmount;
        }
        itemStr = itemStr + '" placeholder="本年度与该关联方发生的关联交易累计金额(万元)">\n' +
            '             </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-5">\n' +
            '            <label  class="control-label col-xs-5" title="与该关联方的交易金额(万元,请保留6位小数)">累计实际交易金额</label>\n' +
            '            <div class="  col-xs-7">\n' +
            '                  <input type="text" disabled="disabled" class="jep-input form-control" id="' + tradeAmount + '" value ="';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.tradeAmount;
        }
        itemStr = itemStr + '">\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-6">\n' +
            '              <label  class="control-label col-xs-4" title="关联方上年末总资产(万元)"><font color="red">✶</font>关联方上年末总资产(万元)</label>\n' +
            '              <div class="  col-xs-8">\n' +
            '                   <input type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + totalassetsLateLast + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.totalassetsLateLast;
        }
        itemStr = itemStr + '" >\n' +
            '            </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-1"></div>\n' +
            '       <input type="hidden" class="form-control" id="' + relId + '" value="';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relId;
        }
        itemStr = itemStr + '">\n' +
            '           <input type="hidden" class="form-control" id="' + relType + '" value="'
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relType;
        }
        itemStr = itemStr + '">\n' +
            '       <input type="hidden" class="form-control" id="' + pid + '">\n' +
            '       </div>' +
            '    </div>\n' +
            '</div>';
        $("#formItems").append(itemStr);
    }

    //文件下载
    function downFileOnclick() {
        //选中的文件
        var pids = getIdSelections();
        if (pids == '' || pids == undefined) {
            $.messager.alert('错误', "请选择需要下载的文件!", 'error');
            return;
        }
        var url = '${base}/ins/traInsenclosure/downLoadFile/' + pids;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    function delItme(item) {
        if (countTmp == 1) {
            $.messager.alert('提示', "请勿全部删除", 'info');
            return
        }
        $("#" + item).remove()
        countTmp--;
        isConsiderType();
    }

    //保存
    function checkSaves() {
        var holdName = $('#holdingSubsidiaryName').val();
        var holdType = $('#holdingSubsidiaryType').val();
        var holdNum = $('#holdingSubsidiaryNumber').val();
        var isAgreementImpl = $('#isAgreementImpl').val();
        var agreementNum = $('#agreementNum').val();

        // $("#save2").attr("disabled", true);
        if ($('#isHoldingSubsidiary').val == '0' &&
            (holdName == null && holdName == undefined && holdName == '') ||
            (holdType == null && holdType == undefined && holdType == '') ||
            (holdNum == null && holdNum == undefined && holdNum == '')

        ) {
            $.messager.alert('错误', "是控股子公司，请填写控股子公司全称、证件类型、证件号码", 'error');
            return;
        }
        if ($('#isAgreement').val == '0' &&
            (isAgreementImpl == null && isAgreementImpl == undefined && isAgreementImpl == '') ||
            (agreementNum == null && agreementNum == undefined && agreementNum == '')

        ) {
            $.messager.alert('错误', "是统一交易协议，属于统一交易协议执行情况、主协议编号为必填项", 'error');
            return;
        }

        $("#save2").attr("disabled", true);
        checkFile();
        var docInnerPids = getAllIdSelections();//接受选中数据行 id
        var docTableSelectItem = {
            'name': 'docTableSelectItem',
            'value': docInnerPids
        };
        var investmentType = $('#investmentType').val(); //获取 业务类型
        if (investmentType != 0) {
            for (var i = 1; i < 7; i++) {
                var isHidden = $('#' + i).is(':hidden');
                if (isHidden) {
                    $('#' + i).html('');
                }
            }
        }
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();

        if (isValid) {
            var formData = $('#editForm').serializeAll();

            var relInfs1 = [];

            for (var i = 1; i <= count; i++) {
                var relNumberTmp = $("#relNumber" + i).val();
                //过滤 i 等于已经删除的item
                if (relNumberTmp === undefined || relNumberTmp === '') {
                    continue
                }
                var relItemInfo = {
                    "relId": "",
                    "relNumber": "",
                    "relType": "",
                    "relName": "",
                    "totolTradeAmount": "",
                    "certType": "",
                    "tranOpponent": "",
                    "totalassetsLateLast": "",
                    "tradeAmount": "",
                    "pid": ""
                }

                relItemInfo.relId = $("#relId" + i).val();
                relItemInfo.relNumber = $("#relNumber" + i).val();
                relItemInfo.relType = $("#relType" + i).val();
                relItemInfo.relName = $("#relName" + i).val();
                relItemInfo.totolTradeAmount = $("#totolTradeAmount" + i).val();
                relItemInfo.certType = $("#certType" + i).val();
                relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
                relItemInfo.totalassetsLateLast = $("#totalassetsLateLast" + i).val();
                relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
                relItemInfo.pid = $("#pid" + i).val();
                relInfs1.push(relItemInfo)
            }
            var relInfoDatas = {
                'name': "relInfoListStr",
                'value': JSON.stringify(relInfs1)
            }
            formData.push(relInfoDatas)
            var formData2 = $('#editForm').serializeAll();

            var relInfs = [];

            for (var i = 1; i <= count; i++) {
                var relNumberTmp = $("#relNumber" + i).val();
                //过滤 i 等于已经删除的item
                if (relNumberTmp === undefined || relNumberTmp === '') {
                    continue
                }
                var relItemInfo = {
                    "relId": "",
                    "relNumber": "",
                    "relType": "",
                    "relName": "",
                    "totolTradeAmount": "",
                    "certType": "",
                    "tranOpponent": "",
                    "totalassetsLateLast": "",
                    "tradeAmount": "",
                    "pid": ""
                }

                relItemInfo.relId = $("#relId" + i).val();
                relItemInfo.relNumber = $("#relNumber" + i).val();
                relItemInfo.relType = $("#relType" + i).val();
                relItemInfo.relName = $("#relName" + i).val();
                relItemInfo.totolTradeAmount = $("#totolTradeAmount" + i).val();
                relItemInfo.certType = $("#certType" + i).val();
                relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
                relItemInfo.totalassetsLateLast = $("#totalassetsLateLast" + i).val();
                relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
                relItemInfo.pid = $("#pid" + i).val();
                relInfs.push(relItemInfo)
            }
            var relInfoDatas = {
                'name': "relInfoListStr",
                'value': JSON.stringify(relInfs)
            }
            formData2.push(docTableSelectItem);
            formData2.push(relInfoDatas)
            $.mask.post('${base}/ins/traFunds/traFundsCheck', formData2, function (data) {
                console.log(data)
                if (data.success) {
                    $.mask.post('${base}/ins/traFunds/save', formData2, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', data.msg, 'info', function () {
                                window.location = '${base}/ins/traFunds/list';
                            });
                        } else {
                            // $("#save2").attr("disabled", false);
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else {
                    /**设置预警字段提示信息并填充到表单中*/
                    $("#limitHint").val('');
                    var limitHint = data.msg;
                    var code = data.code;
                    if (limitHint != null || limitHint != undefined || limitHint != "") {
                        $("#limitHint").val(limitHint);
                    }
                    $('#ruleResult').val(code);
                    var codeObj = {
                        'name': "code",
                        'value': code
                    }
                    var limitHintObj = {
                        'name': "limitHint",
                        'value': limitHint
                    }
                    formData2.push(codeObj)
                    formData2.push(limitHintObj)
                    $.messager.confirm('提示', data.msg + ',是否保存数据？', function (r) {
                        if (r) {
                            /!*取出限额字段*!/
                            // var limitHint = data.limitHint;
                            // var limitHint = data.msg;
                            $.mask.post('${base}/ins/traFunds/save', formData2, function (data) {
                                if (data.success) {
                                    $.messager.alert('提示', data.msg, 'info', function () {
                                        window.location = '${base}/ins/traFunds/list';
                                    });
                                } else {
                                    // $("#save2").attr("disabled", false);
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            });
                        }
                    });
                }
            });
        } else {
            $.messager.alert('错误', data.msg, 'error');
            // $("#save2").attr("disabled", false);
        }
    }

    function checkFile() {
        // 0 : 是 1：否
        var name = $("#holdingSubsidiaryName").val() == null || $("#holdingSubsidiaryName").val() == "" ? 'no' : $("#holdingSubsidiaryName").val();
        var number = $("#holdingSubsidiaryNumber").val() == null || $("#holdingSubsidiaryNumber").val() == "" ? 'no' : $("#holdingSubsidiaryNumber").val();
        var isHoldingSubsidiary = $("#isHoldingSubsidiary").val();
        if (isHoldingSubsidiary == '0' && (name == 'no' || number == 'no')) {
            $.messager.alert('提示', '控股子公司全称和控股子公司证件号码必填', 'warning');
            return false;
        } else if (isHoldingSubsidiary == '1' && (name != 'no' || number != 'no')) {
            $.messager.alert('提示', '控股子公司全称和控股子公司证件号码需要置空', 'warning');
            return false;
        }
    }

    //判断底层基础资产是否涉及控股股东或控股股东的关联方--控制--底层资产涉及的关联方名称是否 必填
    function underInvolveRel(value) {
        var value = value
        if (value == '1') {
            $('#underInvolveRelName').attr("disabled", "false");
            $('#underInvolveRelName_04_Div label font').remove();
        }
        if (value == '0') {
            $('#underInvolveRelName').removeAttr("disabled");
            if ($('#underInvolveRelName_04_Div label font').length == 0) {
                $('#underInvolveRelName_04_Div label').prepend("<font color='red'>✶</font>");
            }
        }
    };

    /**风险预警校验*/
    function checkRisk(fromData) {
        $.mask.post('${base}/ins/traFunds/traFundsCheck', fromData, function (data) {
            console.log(data)
            if (data.success) {
                return JSON.stringify(data);
            } else {
                /*$("#save2").attr("disabled", false);*/
                return JSON.stringify(data);
                // $.messager.alert('错误', data.msg, 'error');
            }
        });
    }

</script>
</body>
</html>
