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
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traFundsBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true" >
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="relId" name="relId" value="${traFunds.relId}"/>
            <bf:hidden id="relNumber" name="relNumber" value="${traFunds.relNumber}"/>
            <bf:hidden id="relType" name="relType" value="${traFunds.relType}"/>
            <bf:hidden id="pid" name="pid" value="${traFunds.pid}"/>
            <bf:hidden id="ruleResult" name="ruleResult" value=""/>
            <bf:hidden id="total" name="total" value="${traFunds.totolTradeAmount}"/>
            <bf:hidden id="amount" name="amount" value="${traFunds.contractAmount}"/>
            <bf:hidden id="transNum" name="transNum" value="${traFunds.transNum}"/>
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易主体名称" col="5" formControl="true"/>
                        <bf:select id="traSubName" name="traSubName" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-subjectName"
                                   disabled="${opt == 'view'}" value="${traFunds.traSubName}">
                            <bf:option text="请选择"></bf:option>
                            <be:event type="select">
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <%--                    <bf:formGroup col="4">--%>
                    <%--                        <bc:label cls="required" text="交易对手名称" col="5" formControl="true"/>--%>
                    <%--                        <bf:lookup id="relName" name="relName" col="7" title="交易对手信息"--%>
                    <%--                                   url="${base}/ins/traFunds/relNameSelect" winId="relNameSelect"--%>
                    <%--                                   propText="relName" propValue="relName"--%>
                    <%--                                   callback="result"  text="${traFunds.relName}"--%>
                    <%--                                   value="${traFunds.relName}" disabled="${opt == 'view'}"--%>
                    <%--                                   beforeOpen="empTraSub"   onSelect="oneSelect">--%>
                    <%--                        </bf:lookup>--%>
                    <%--                    </bf:formGroup>--%>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易对手名称" col="5" formControl="true"/>
                        <bf:text id="relName" name="relName" col="7" click="relNameSelect()"
                                 value="${traFunds.relName}" disabled="${opt == 'view'}"
                        >
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>


                    <bf:formGroup col="4">
                        <div title="交易主体是否为中再资产的控股子公司">
                            <bc:label cls="required" text="是否为控股子公司" col="5" formControl="true"/>
                        </div>

                        <bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
                                   value="${traFunds.isHoldingSubsidiary}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="IsStatusDict">
                            <be:event type="select">
                                HoldingSubsidiarySub(e.target.value);
                            </be:event>
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="控股子公司全称" col="5" formControl="true"/>
                        <bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
                                 value="${traFunds.holdingSubsidiaryName}"
                                 disabled = "true">
                            <%--<bv:notEmpty/>--%>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="控股子公司证件类型" col="5" formControl="true"/>
                        <bf:select id="holdingSubsidiaryType" name="holdingSubsidiaryType" col="7" value="${traFunds.holdingSubsidiaryType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-ORGIdentifyType" disabled="true">
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="控股子公司证件号码" col="5" formControl="true"/>
                        <bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
                                 value="${traFunds.holdingSubsidiaryNumber}"
                                 disabled = "true">
                            <%--<bv:notEmpty/>--%>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易对手上年末总资产（万元）" col="5" formControl="true"/>
                        <bf:text id="totalAssets" name="totalAssets" col="7" value="${traFunds.totalAssets}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </bf:formGroup>
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
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="合同/交易编号" col="5" formControl="true"/>
                        <bf:text id="contractNo" name="contractNo" col="7" value="${traFunds.contractNo}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="关联交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="contractAmount" name="contractAmount" col="7" disabled="${opt == 'view'}"
                                 value="${traFunds.contractAmount}" change='isInRoad(),isGreatTrade(),isConsiderType()'>
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="与关联方签署交易协议的日期">
                            <bc:label cls="required" text="合同/交易发生日期" col="5" formControl="true"/>
                        </div>

                        <bf:datebox id="tranDate" name="tranDate" col="7" value="${traFunds.tranDateString}"
                                    disabled="${opt == 'view'}">
                            <bv:callback message="请填写必填项目" callback="notEmpt"/>
                        </bf:datebox>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div title="关联交易发生的起始日期">
                            <bc:label cls="required" text="业务起始日期" col="5" formControl="true"/>
                        </div>

                        <bf:datebox id="busStartTime" name="busStartTime" col="7" value="${traFunds.busStartTime}"
                                    disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="关联交易发生的结束日期">
                            <bc:label text="业务结束日期" col="5" formControl="true"/>
                        </div>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traFunds.busEndTimeString}"
                                    disabled="${opt == 'view'}"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
        <div title="本年度与该关联方累计交易金额（万元）包含：单笔重大+累计金额+免于审议">
                        <bc:label text="本年度与该关联方累计交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
                                 value="${traFunds.totolTradeAmount}" disabled="true"
                                 placeholder="本年度与该关联方发生的关联交易累计金额（万元）">
                        </bf:text>
        </div>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls = "required" text="是否为境外投资" col="5" formControl="true"/>
                        <bf:select id="isOverseasinvestment" name="isOverseasinvestment" col="7"
                                   value="${traFunds.isOverseasinvestment}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="IsStatusDict">
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
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
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="是否现金认购" col="5" formControl="true"/>
                        <bf:select id="isCashSubscription" name="isCashSubscription" col="7"
                                   value="${traFunds.isCashSubscription}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="IsStatusDict">
                            <be:event type="select">
                                isConsiderType();
                            </be:event>
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="审议类型" col="5" formControl="true"/>
                        <bf:select id="considerType" name="considerType" col="7" value="${traFunds.considerType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">
                            <bv:notEmpty/>
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
                        <bc:label cls="required" text="达到重大关联交易的情形" col="5" formControl="true"/>
                        <bf:select id="majorType" name="majorType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-majorType" disabled="true"
                                   includes="" value="${traFunds.majorType}">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label cls="required" text="交易金额（万元）" col="5" formControl="true"/>--%>
<%--                        <bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traFunds.tradeAmount}"--%>
<%--                                 disabled="true">--%>
<%--                            <bv:notEmpty/>--%>
<%--                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>--%>
<%--                        </bf:text>--%>
<%--                    </bf:formGroup>--%>
                    <div id="1_1" class="Type1" style="display: none" >
                        <bf:formGroup col="4">
                            <bc:label cls="required" text="股权分类" col="5" formControl="true"/>
                            <bf:select id="stockrightType" name="stockrightType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-FundsStockrightType" disabled="${opt == 'view'}"
                                       includes="" value="${traFunds.stockrightType}">
                            </bf:select>
                        </bf:formGroup>
                    </div>
                    <div id="2_1" class="Type2" style="display: none" >
                        <bf:formGroup col="4">
                            <bc:label cls = "required" text="投资方式" col="5" formControl="true"/>
                            <bf:select id="investmentMethod" name="investmentMethod" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-FundsinvestmentMethod" disabled="${opt == 'view'}"
                                       includes="" value="${traFunds.investmentMethod}">
                                <bv:notEmpty/>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <div title="投资不动产类交易时的股权占比">
                                <bc:label cls = "required" text="股权比例（%）" col="5" formControl="true"/>
                            </div>
                            <bf:text id="shareDebtRatio" name="shareDebtRatio" col="7" value="${traFunds.shareDebtRatio}" disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bf:formGroup>
                    </div>
                    <div id="4_1" class="Type4" style="display: none">
                        <bf:formGroup hrp="bottom">
                            <bf:formGroup col="4">
                                <bc:label cls = "required" text="金融产品名称" col="5" formControl="true"/>
                                <bf:text id="financialAssstsName" name="financialAssstsName" col="7"
                                         value="${traFunds.financialAssstsName}" disabled="${opt == 'view'}">
                                    <bv:notEmpty/>
                                </bf:text>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label cls = "required" text="产品批准/注册/备案号" col="5" formControl="true"/>
                                <bf:text id="financialAssstsNum" name="financialAssstsNum" col="7"
                                         value="${traFunds.financialAssstsNum}" disabled="${opt == 'view'}">
                                    <bv:notEmpty/>
                                </bf:text>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                            <div title="投资金融产品份额占比（%)">
                                <bc:label cls = "required" text="金融产品份额占比" col="5" formControl="true"/>
                                <bf:text id="financialProRatio" name="financialProRatio" col="7"
                                         value="${traFunds.financialProRatio}" disabled="true">
                                    <bv:notEmpty/>
                                </bf:text>
                            </div>
                            </bf:formGroup>
                            <bf:formGroup hrp="bottom">
                                <bf:formGroup col="4">
                                    <div title="底层资产是否涉及关联方">
                                        <bc:label cls = "required" text="资产是否为关联方" col="5" formControl="true"/>
                                        <bf:select id="underIsRel" name="underIsRel" col="7" dataSource="custom"
                                                   url="sys/dictItem/getDictItems/BUS-isNo" value="${traFunds.underIsRel}" disabled="${opt == 'view'}">
                                            <bf:option text="请选择" value=""></bf:option>
                                            <bv:notEmpty/>
                                        </bf:select>
                                    </div>
                                </bf:formGroup>
                                <bf:formGroup col="4">
                                    <div title="底层基础资产是否涉及控股股东或控股股东的关联方">
                                        <bc:label cls = "required" text="是否涉及控股股东" col="5" formControl="true"/>
                                        <bf:select id="underInvolveRel" name="underInvolveRel" col="7" dataSource="custom"
                                                   url="sys/dictItem/getDictItems/BUS-isNo" value="${traFunds.underInvolveRel}" disabled="${opt == 'view'}">
                                            <bf:option text="请选择" value=""></bf:option>
                                            <bv:notEmpty/>
                                        </bf:select>
                                    </div>
                                </bf:formGroup>
                                <bf:formGroup col="4">
                                        <bc:label cls = "required" text="金融产品分类" col="5" formControl="true"/>
                                        <bf:select id="financialAssstsType" name="financialAssstsType" col="7" dataSource="custom"
                                                   url="sys/dictItem/getDictItems/INS-FundsProType" value="${traFunds.financialAssstsType}" disabled="${opt == 'view'}">
                                            <bf:option text="请选择" value=""></bf:option>
                                            <bv:notEmpty/>
                                        </bf:select>
                                </bf:formGroup>
                            </bf:formGroup>
                        </bf:formGroup>
                    </div>
                    <div id="5_1" class="Type5" style="display: none">
                        <bf:formGroup col="4">
                            <bc:label cls = "required" text="被投企业名称" col="5" formControl="true"/>
                            <bf:text id="investedEnterprisesName" name="investedEnterprisesName" col="7"
                                     value="${traFunds.investedEnterprisesName}" disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bf:formGroup>
                        <bf:formGroup  col="4">
                            <bc:label cls = "required" text="投资比例（%）" col="5" formControl="true"/>
                            <bf:text id="investmentRatio" name="investmentRatio" col="7"
                                     value="${traFunds.investmentRatio}" disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bf:formGroup>
                    </div>
                </bf:formGroup>
            </bc:row>
            <bf:formGroup hrp="bottom">
                <div id="5_2" class="Type5" style="display: none">
            <bf:formGroup hrp="bottom">
                    <bf:formGroup col="6">
                        <div title="与关联方共同投资企业的业务模式简要描述">
                            <bc:label cls="required" text="业务模式" col="3" type="warning" formControl="true"/>
                        </div>
                        <bf:text id="busModel" name="busModel" col="9" rows="2" multiple="true" value="${traFunds.busModel}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <div title="关联方共同投资企业的盈利预期的简要描述">
                            <bc:label cls="required" text="盈利预期" col="3" type="warning" formControl="true"/>
                        </div>
                        <bf:text id="earningsExpectaions" name="earningsExpectaions" col="9" rows="2" multiple="true" value="${traFunds.earningsExpectaions}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                    <bf:formGroup col="6">
                        <div title="与关联方共同投资时，与关联方投资收益的分配情况">
                            <bc:label cls="required" text="投资收益分配情况" col="3" type="warning" formControl="true"/>
                        </div>
                        <bf:text id="investmentAllocation" name="investmentAllocation" col="9" rows="2" multiple="true" value="${traFunds.investmentAllocation}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <div title="与关联方共同投资时，与关联方损失分担情况">
                            <bc:label cls="required" text="损失分担情况" col="3" type="warning" formControl="true"/>
                        </div>

                        <bf:text id="lossShare" name="lossShare" col="9" rows="2" multiple="true" value="${traFunds.lossShare}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
            </bf:formGroup>
                </div>
                <div id="4_2" class="Type4" style="display: none" >
            <bf:formGroup hrp="bottom">
                    <bf:formGroup col="6">
                        <div title="金融产品设立时的募集规模">
                            <bc:label cls="required" text="金融产品发行规模" col="3" type="warning" formControl="true"/>
                        </div>
                        <bf:text id="financialProScale" name="financialProScale" col="9" rows="2" multiple="true" change='isGreatTrade()' value="${traFunds.financialProScale}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <div title="简要描述底层资产的具体情况或投向">
                            <bc:label cls="required" text="底层资产具体描述" col="3" type="warning" formControl="true"/>
                        </div>
                        <bf:text id="underlyingAsset" name="underlyingAsset" col="9" rows="2" multiple="true" value="${traFunds.underlyingAsset}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
            </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="底层资产涉及关联名称" col="3" type="warning" formControl="true"/>
                        <bf:text id="underRel" name="underRel" col="9" rows="2" multiple="true" value="${traFunds.underRel}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                </div>
                <bf:formGroup col="6">
                    <div title="交易单价">
                        <bc:label cls="required" text="交易价格" col="3" type="warning" formControl="true"/>
                    </div>

                    <bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true" value="${traFunds.tradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="交易价格、收益率的定价依据">
                        <bc:label cls="required" text="定价政策" col="3" formControl="true"/>
                    </div>
                    <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true" value="${traFunds.pricingPolicy}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="对公司的财务状况及经营成果的描述">
                        <bc:label cls="required" text="对财务状况及经营成果的影响" col="3" formControl="true"/>
                    </div>

                    <bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true" value="${traFunds.financialImpact}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="与关联方之间关联交易的对象">
                        <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                    </div>

                    <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true" value="${traFunds.tradeSubject}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text></bf:formGroup>
                <div  id="1_2" class="Type1" style="display: none">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <bc:label cls="required" text="被投资企业情况" col="3" formControl="true"/>
                    <bf:text id="investedEnterprises" name="investedEnterprises" col="9" rows="2" multiple="true" value="${traFunds.investedEnterprises}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="前次交易价格" col="3" formControl="true"/>
                    <bf:text id="equityLastTradePrice" name="equityLastTradePrice" col="9" rows="2" multiple="true" value="${traFunds.equityLastTradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/> </bf:text>
                </bf:formGroup>
            </bf:formGroup>
                </div>
                <div id="2_2" class="Type2" style="display: none">
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="前次交易价格" col="3" formControl="true"/>
                        <bf:text id="imLastTradePrice" name="imLastTradePrice" col="9" rows="2" multiple="true" value="${traFunds.imLastTradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/> </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="标的情况" col="3" formControl="true"/>
                        <bf:text id="imSubjectMatter" name="imSubjectMatter" col="9" rows="2" multiple="true" value="${traFunds.equityLastTradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/> </bf:text>
                    </bf:formGroup>

                </div>
                <div id="3_2" class="Type3" style="display: none">
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="标的情况" col="3" formControl="true"/>
                        <bf:text id="outSubjectMatter" name="outSubjectMatter" col="9" rows="2" multiple="true" value="${traFunds.equityLastTradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/> </bf:text>
                    </bf:formGroup>

                </div>
                <bf:formGroup col="6">
                    <div title="除上述关键信息外，其他需要说明的重要或特殊的事项">
                        <bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
                    </div>
                    <bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traFunds.remark}" disabled="${opt == 'view'}"><bv:notEmpty/> </bf:text>
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

<%--                    <bc:button id="confirm" type="primary" text="确认查阅" icon="arrow-circle-left" click="toConfirm()"></bc:button>--%>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/report/design/traInfoList';
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
<script type="text/javascript">
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
    // function isConsiderType() {
    //     //是否为重大关联交易
    //     var isMajorTrade = $('#majorTrade').val();
    //     //是否为国家定价
    //     var isDealPrice = $('#isDealPrice').val();
    //     //交易金额
    //     var tradeAmount = $('#tradeAmount').val();
    //     //关联方类型
    //     var relType = $('#relType').val();
    //     //如果是重大关联交易审议类型为逐笔审议
    //     if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != '') {
    //         if (isMajorTrade == "0") {
    //             $('#considerType').val("3");
    //             $('#considerType').select2({value: "3"});
    //             return;
    //         }
    //     }
    //     //如果是国家定价重大关联交易审议类型为免予审议
    //     if (isDealPrice != null && isDealPrice != undefined && isDealPrice != '') {
    //         if (isDealPrice == "0") {
    //             $('#considerType').val("1");
    //             $('#considerType').select2({value: "1"});
    //             return;
    //         }
    //     }
    //     //根据关联方规则判断审议类型
    //     if (tradeAmount != null && tradeAmount != undefined && tradeAmount != '' && relType != null && relType != undefined && relType != '') {
    //         if (relType == "PSN" && tradeAmount <= "50") {
    //             $('#considerType').val("1");
    //             $('#considerType').select2({value: "1"});
    //             return;
    //         }
    //         if (relType == "ORG" && tradeAmount <= "500") {
    //             $('#considerType').val("1");
    //             $('#considerType').select2({value: "1"});
    //             return;
    //         }
    //     }
    //     $('#considerType').val("2");
    //     $('#considerType').select2({value: "2"});
    // }

    //跟据审议类型规则给定审议类型得值
    function isConsiderType() {
        //是否为国家定价
        var isDealPrice = $('#isDealPrice').val();
        //是否为现金认购
        var isCashSubscription = $('#isCashSubscription').val();
        //是否为重大关联交易
        var isMajorTrade = $('#majorTrade').val();
        //合同金额
        var contractAmount = $('#contractAmount').val();
        //关联方类型
        var relType = $('#relType').val();

        //如果是现金交易审议类型为免予审议
        if (isCashSubscription != null && isCashSubscription != undefined && isCashSubscription != '' && isCashSubscription == "0") {
            $('#considerType').val('1');
            $('#considerType').select2({value: '1'});
            return;
        } else {
            //当关联方为个人合同金额<=50w  机构合同金额<=500w的时候免于审议
            if (contractAmount != null && contractAmount != undefined && contractAmount != ''
                && relType != null && relType != undefined && relType != '') {
                if (relType == "PSN" && contractAmount <= 50) {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    return;
                }
                if (relType == "ORG" && contractAmount <= 500) {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    return;
                }
            }
            //如果是重大关联交易审议类型为逐笔审议
            if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != '') {
                if (isMajorTrade == "0") {
                    $('#considerType').val("3");
                    $('#considerType').select2({value: "3"});
                    return;
                }
            }
            //如果是国家定价重大关联交易审议类型为免予审议
            if (isDealPrice != null && isDealPrice != undefined && isDealPrice != '') {
                if (isDealPrice == "0") {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    return;
                }
            }
        }
        $('#considerType').val("2");
        $('#considerType').select2({value: "2"});
    }

    //判断是否为控股子公司来决定子公司名称证件号能不能添加
    function HoldingSubsidiarySub(value) {
        var value = value
        if(value == '1'){
            $('#holdingSubsidiaryNumber').attr("disabled","false");
            $('#holdingSubsidiaryName').attr("disabled","false");
            $('#holdingSubsidiaryType').attr("disabled","false");

        }
        if(value == '0'){
            $('#holdingSubsidiaryNumber').removeAttr("disabled");
            $('#holdingSubsidiaryName').removeAttr("disabled");
            $('#holdingSubsidiaryType').removeAttr("disabled");

        }

    };


    var opt = '${opt}';
    $(document).ready(function () {
        // //  是否为国家定价
        // var isDealPrice = $('#isDealPrice').val();
        // //是否为现金认购
        // var isCashSubscript = $('#isCashSubscript').val();
        // if(isDealPrice == " " || isDealPrice == null || isDealPrice == undefined){
        //     $('#isDealPrice').val('1')
        //     $('#isDealPrice').select2({value: '1'});
        // }
        // if(isCashSubscript == " " || isCashSubscript == null || isCashSubscript == undefined){
        //     $('#isCashSubscript').val('1')
        //     $('#isCashSubscript').select2({value: '1'});
        // }

        if (opt == 'view') {
            // $("#save1").css("display", "none");
             $("#docTable_tb_exportIn").css("display", "none");
             $("#docTable_tb_removeDoc").css("display", "none");
        } else {
            var val = $('#isHoldingSubsidiary').val();
            if (val != null && val != undefined && val != '') {
                HoldingSubsidiarySub(val)
            }
        }
        if('${isConfirm}' == 'true' ){
            $("#save1").css("display", "none");
            $("#docTable_tb_exportIn").css("display", "none");
            $("#docTable_tb_removeDoc").css("display", "none");
        //     $("#confirm").css("display","block")
        // }else{
        //     $("#confirm").css("display","none")
        }
        $('#tranType').val("02");
        $('#tranType').select2({value: "02"});
        var investmentType = $('#investmentType').val();
        // var count = 1;
        for (var i = 1; i < 6; i++) {
            if (investmentType == '0'+i) {
                $('.Type' + i).show();
            }
            //count++;
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

    function relNameSelect(){
        var traSubName = $("#traSubName").val();
        if (traSubName == null || traSubName == " " || traSubName == undefined){
            $.messager.alert('提示', '请先选择交易主体', 'warning');
            return false;
        }else {
            $.messager.modal({
                winId: 'relNameSelect',
                width: 700,
                height: 420,
                title: '交易对手信息',
                url: '${base}/ins/traInvestment/relNameSelect/'+ traSubName +'?tranType=02',
                args: {
                    result: function (obj) {
                        $("#relId").val(obj["relId"]);
                        $("#relName").val(obj["relName"]);
                        $("#totolTradeAmount").val(obj["totolTradeAmount"]);
                        $('#total').val(obj["totolTradeAmount"]);
                        $("#relType").val(obj["relType"]);
                        $("#relNumber").val(obj["relNumber"]);
                    }
                },
                onClose: function(){
                }
            });
        }
    }
    //根据业务类型关联显示对应字段
    function changeNext(val) {
        if (val == '01') {
            $(".Type1").css("display", "block");
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
        }
        if (val == '02') {
            $(".Type1").css("display", "none");
            $(".Type2").css("display", "block");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
        }
        if (val == '04') {
            $(".Type1").css("display", "none");
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "block");
            $(".Type5").css("display", "none");
        }
        if (val == '03') {
            $(".Type1").css("display", "none");
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "block");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
        }

        if (val == '05') {
            $(".Type1").css("display", "none");
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "block");
        }if (val == '06') {
            $(".Type1").css("display", "none");
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
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
            alert("请选中一个文件");
            return;
        }
        if (pid.length > 1) {
            alert("只能选中一个文件");
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
            alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/ins/traInsenclosure/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    alert(res.msg);
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
                alert("请上传pdf文件");
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
                alert(data.msg);
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
                alert("上传失败,请重试");
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
                    alert(data.msg);
                }
            }
        });
    }

    //判断当前关联方是否拥有在途数据
    function isInRoad() {
        var traSubName = $("#traSubName").val();
        // if (traSubName != '0001' && traSubName != '0001-ZZHK') {
        //     return;
        // }
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

    //判断是否重大关联交易  限额以及限额预警
    function isGreatTrade() {
        //委托机构不判断是否重大
        var traSubName = $("#traSubName").val();
        // if(traSubName != '0001' && traSubName != '0001-ZZHK'){
        //     return;
        // }
        var relId = $('#relId').val();
        var relType = $('#relType').val();
        var tradeAmount = $('#contractAmount').val();
        var totolTradeAmount = $('#total').val();
        //业务类型
        var investmentType = $('#investmentType').val();
        //是否为境外投资
        var isOverseasinvestment = $('#isOverseasinvestment').val();
        //底层基础资产是否涉及控股股东或控股股东的关联方
        var underInvolveRel = $('#underInvolveRel').val();
        //金融产品发行规模
        var financialProScale = $('#financialProScale').val();
        //是否现金认购
        var isCashSubscription =$('#isCashSubscription').val();

        /*$('#totolTradeAmount').val(parseFloat(totolTradeAmount) + parseFloat(tradeAmount));*/
        if (${opt == 'edit'}) {
            var trade = $('#amount').val();
            totolTradeAmount = $('#total').val();
            // $('#totolTradeAmount').val(parseFloat(totolTradeAmount) + (parseFloat(tradeAmount) - parseFloat(trade)));
            $('#totolTradeAmount').val(parseFloat(totolTradeAmount) + parseFloat(tradeAmount));
        } else {
            var total = $('#total').val();
            $('#totolTradeAmount').val(parseFloat(total) + parseFloat(tradeAmount));
        }

        if (tradeAmount == '' || relNumber == null) {
            return;
        }
        if (relType == '' || relType == null) {
            return;
        }
        if (relId == '' || relId == null) {
            return;
        }
        if (totolTradeAmount == '' || totolTradeAmount == null) {
            return;
        }
        if (investmentType == '' || investmentType == null ||isOverseasinvestment == '' || isOverseasinvestment == null) {
            return;
        }
        // if(investmentType == '04'){
        //     if (underInvolveRel == '' || underInvolveRel == null || financialProScale == '' || financialProScale == null ) {
        //         return;
        //     }
        // }
        var obj = {
            relId: relId,
            relType: relType,
            tradeAmount: tradeAmount,
            totolTradeAmount: totolTradeAmount,
            investmentType: investmentType,
            isOverseasinvestment: isOverseasinvestment,
            underInvolveRel: underInvolveRel,
            financialProScale: financialProScale,
            isCashSubscription: isCashSubscription
        };
        $.ajax({
            url: '${base}/ins/traFunds/GreatCheck',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    $('#majorTrade').val(data.isGreatTrade);
                    $('#majorTrade').select2({value: (data.isGreatTrade)})
                    if(data.isGreatTrade == "0"){
                        $('#majorType').val(data.majorType);
                        $('#majorType').select2({value: (data.majorType)})
                    }else {
                        $('#majorType').val(" ");
                        $('#majorType').select2({value: (" ")})
                    }
                    if(data.limitCode == "0"){//超限
                        $("#saveInfo").attr("disabled","disabled");
                        $.messager.alert('超限提示', data.limitMsg, 'warning');
                    }else{
                        $("#saveInfo").removeAttr("disabled");
                    }
                    if(data.limitWarningCode == "0"){//超限-预警
                        $.messager.alert('超限预警提示', data.limitWarningMsg, 'warning');
                    }
                    $('#financialProRatio').val(data.sharePercent);//金融产品份额占比
                    isConsiderType();
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    //加上红点
    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });

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

</script>
</body>
</html>
