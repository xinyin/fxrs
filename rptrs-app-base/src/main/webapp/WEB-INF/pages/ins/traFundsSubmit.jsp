<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表（保险）-资金运用类</title>
    <style>
        .form-group .input-group.date,
        .form-group .select2{
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
            <bf:hidden id="amount" name="amount" value="${traFunds.tradeAmount}"/>
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
                        <bc:label text="交易对手名称" col="5" formControl="true"/>
                        <bf:text id="relName" name="relName" col="7" click="relNameSelect()"
                                 value="${traFunds.relName}" disabled="${opt == 'view'}"
                        >
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>


                    <bf:formGroup col="4">
                        <bc:label cls="required" text="是否为控股子公司" col="5" formControl="true"/>
                        <bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
                                   value="${traFunds.isHoldingSubsidiary}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="IsStatusDict">
                            <be:event type="select">
                                HoldingSubsidiarySub(e.target.value);
isConsiderType();
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
                            <bv:notEmpty/>
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
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易对手上年末总资产（万元/人民币）" col="5" formControl="true"/>
                        <bf:text id="totalAssets" name="totalAssets" col="7" value="${traFunds.totalAssets}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
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
                        <bc:label cls="required" text="合同金额（万元/人民币）" col="5" formControl="true"/>
                        <bf:text id="contractAmount" name="contractAmount" col="7" disabled="${opt == 'view'}"
                                 value="${traFunds.contractAmount}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="合同/交易发生日期" col="5" formControl="true"/>
                        <bf:datebox id="tranDate" name="tranDate" col="7" value="${traFunds.tranDateString}"
                                    disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="业务起始日期" col="5" formControl="true"/>
                        <bf:datebox id="busStartTime" name="busStartTime" col="7" value="${traFunds.busStartTime}"
                                    disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务结束日期" col="5" formControl="true"/>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traFunds.busEndTimeString}"
                                    disabled="${opt == 'view'}"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="本年度与该关联方累计交易金额（万元/人民币）" col="5" formControl="true"/>
                        <bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
                                 value="${traFunds.totolTradeAmount}" disabled="true"
                                 placeholder="本年度与该关联方发生的关联交易累计金额（万元/人民币）">
                        </bf:text>
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
                        <bc:label text="是否现金认购" col="5" formControl="true"/>
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
                                   url="sys/dictItem/getDictItems/INS-majorType" disabled="${opt == 'view'}"
                                   includes="" value="${traFunds.majorType}">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="交易金额（万元/人民币）" col="5" formControl="true"/>--%>
<%--                        <bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traFunds.tradeAmount}"--%>
<%--                                 disabled="${opt == 'view'}" change='isGreatTrade()'>--%>
<%--                            <bv:notEmpty/>--%>
<%--                        </bf:text>--%>
<%--                    </bf:formGroup>--%>
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
                            <bc:label cls = "required" text="股债比" col="5" formControl="true"/>
                            <bf:text id="shareDebtRatio" name="shareDebtRatio" col="7" value="${traFunds.shareDebtRatio}" disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bf:formGroup>
                    </div>
                    <div id="4_1" class="Type4" style="display: none">
                        <bf:formGroup hrp="bottom">
                            <bf:formGroup col="4">
                                <bc:label cls = "required" text="金融资产名称" col="5" formControl="true"/>
                                <bf:text id="financialAssstsType" name="financialAssstsType" col="7"
                                         value="${traFunds.financialAssstsType}" disabled="${opt == 'view'}">
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
                                             value="${traFunds.financialProRatio}" disabled="${opt == 'view'}">
                                        <bv:notEmpty/>
                                    </bf:text>
                                </div>
                            </bf:formGroup>
                            <bf:formGroup hrp="bottom">
                                <bf:formGroup col="4">
                                    <div title="底层资产是否为关联方">
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
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="业务模式" col="3" type="warning" formControl="true"/>
                        <bf:text id="busModel" name="busModel" col="9" rows="2" multiple="true"
                                 value="${traFunds.busModel}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="盈利预期" col="3" type="warning" formControl="true"/>
                        <bf:text id="earningsExpectaions" name="earningsExpectaions" col="9" rows="2" multiple="true"
                                 value="${traFunds.earningsExpectaions}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="投资收益分配情况" col="3" type="warning" formControl="true"/>
                        <bf:text id="investmentAllocation" name="investmentAllocation" col="9" rows="2" multiple="true"
                                 value="${traFunds.investmentAllocation}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="损失分担情况" col="3" type="warning" formControl="true"/>
                        <bf:text id="lossShare" name="lossShare" col="9" rows="2" multiple="true"
                                 value="${traFunds.lossShare}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </div>
                <div id="4_2" class="Type4" style="display: none" >
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="金融产品发行规模" col="3" type="warning" formControl="true"/>
                        <bf:text id="financialProScale" name="financialProScale" col="9" rows="2" multiple="true"
                                 value="${traFunds.financialProScale}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="底层资产具体描述" col="3" type="warning" formControl="true"/>
                        <bf:text id="underlyingAsset" name="underlyingAsset" col="9" rows="2" multiple="true"
                                 value="${traFunds.underlyingAsset}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="底层资产涉及关联名称" col="3" type="warning" formControl="true"/>
                        <bf:text id="underRel" name="underRel" col="9" rows="2" multiple="true"
                                 value="${traFunds.underRel}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </div>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="交易价格" col="3" type="warning" formControl="true"/>
                    <bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true"
                             value="${traFunds.tradePrice}" disabled="${opt == 'view'}">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="定价政策" col="3" formControl="true"/>
                    <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true"
                             value="${traFunds.pricingPolicy}" disabled="${opt == 'view'}">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="对财务状况及经营成果的影响" col="3" formControl="true"/>
                    <bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true"
                             value="${traFunds.financialImpact}" disabled="${opt == 'view'}">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                    <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true" value="${traFunds.tradeSubject}"
                             disabled="${opt == 'view'}">
                    </bf:text>
                </bf:formGroup>
                <div  id="1_2" class="Type1" style="display: none">
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="被投资企业情况" col="3" formControl="true"/>
                        <bf:text id="investedEnterprises" name="investedEnterprises" col="9" rows="2" multiple="true" value="${traFunds.investedEnterprises}"
                                 disabled="${opt == 'view'}">
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="6">
                        <bc:label cls="required" text="前次交易价格" col="3" formControl="true"/>
                        <bf:text id="equityLastTradePrice" name="equityLastTradePrice" col="9" rows="2" multiple="true" value="${traFunds.equityLastTradePrice}"
                                 disabled="${opt == 'view'}">
                        </bf:text>
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
                    <bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
                    <bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traFunds.remark}"
                             disabled="${opt == 'view'}">
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traFunds.transNum}">
                    <%--                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
                    <%--                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
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
                <div class="col-sm-4 col-sm-offset-2">
                    <bc:button type="success" id="confirmSubmit" text="确认提交" icon="arrow-circle-right" click="Approved()">
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/ins/traFunds/list';
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

    var nextApprovalPeople ;
    //选择下一节点审批人
    function Approved() {
        var traSubName =$('#traSubName').val();
        //临时修改2021-04-30
        if (false) {
            var relName = $('#relName').val();
            var tranType = $('#tranType').val();
            var contractNo = $('#contractNo').val();
            var contractAmount = $('#contractAmount').val();
            $("#confirmSubmit").attr("disabled","disabled");
            var obj = {
                traSubName:traSubName,
                relName:relName,
                tranType:tranType,
                contractNo:contractNo,
                contractAmount:contractAmount
            };
            $.ajax({
                    url: '${base}/ins/traFunds/sendNotice',
                    data: obj,
                    type: 'post',
                    jsonType: 'json',
                    success: function (data) {
                        if (data.success) {
                            var msg = data.msg;
                            $.messager.alert('提示', msg, 'info');
                        }
                        window.location = '${base}/ins/traFunds/list';
                    }
                }
            );
        }else{
            /*$.messager.modal({
                winId: 'Approved',
                width: 1200,
                height: 420,
                title: '选择下一节点审批人',
                url: '${base}/ins/traInvestment/Approved?mult=true',
                args: {
                    result: function (obj) {
                        nextApprovalPeople = obj["staffCode"];
                        sub()
                    }
                },
                onClose: function(){

                }
            });*/
            sub()
        }
        /*$.messager.modal({
            winId: 'Approved',
            width: 1200,
            height: 420,
            title: '选择下一节点审批人',
            url: '${base}/ins/traInvestment/Approved?mult=true',
            args: {
                result: function (obj) {
                    nextApprovalPeople = obj["staffCode"];
                    sub()
                }
            },
            onClose: function(){

            }
        });*/
    }

    var opt = '${opt}';
    $(document).ready(function () {
        if (opt == 'view'){
            $("#save1").css("display", "none");
        }else {
            var val = $('#isHoldingSubsidiary').val();
            if (val != null && val != undefined && val != ''){
                HoldingSubsidiarySub(val)
            }
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

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }

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

    /*
    根据当前时间和随机数生成备案号
    * */
    function randomNumber() {
        var now = new Date();
        var year = now.getFullYear().toString();
        var month = now.getMonth() + 1;
        var day = now.getDate();
        var hour = now.getHours();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        var no = year+month+day+hour+minutes+seconds;
        return no+(Math.round(Math.random()*89+100)).toString();
    }

    //objData.filNum = randomNumber()+'.'+data.pid

    function sub() {
        var pid = $('#pid').val();
        //判断一般流程或是重大流程
        var procDefCode ;
        var majorTrade = $('#majorTrade').val();
        if (majorTrade == "0"){
            procDefCode = 'zybr_relGreat';
        }else {
            procDefCode = 'zybr_relGeneral';
        }
        $.ajax({
            url: "${base}/ins/traFunds/changeApplyStatus/" + pid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var formData = data.data;
                    var myDate = new Date();
                    formData.id = myDate.getTime();
                    formData.filNum = '2o'+randomNumber()
                    formData.nextApprovalPeople = nextApprovalPeople;
                    //机构信息
                    var unitCode = data.unitCode;
                    //部门信息
                    var deptCode = data.deptCode;
                    formData.id = myDate.getTime();
                    formData.unitCode=unitCode;
                    formData.deptCode=deptCode;
                    var deptName = data.deptName;
                    var hdDeptCode = data.hdDeptCode;
                    formData.deptName=deptName;
                    formData.hdDeptCode=hdDeptCode;
                    var data = JSON.stringify(formData);
                    formData = $.helper.base64Encode(data);
                    var isSecond = data.isSecond;
                    //区分总部一级部门和二级部门
                    if (majorTrade == "0"){
                        //重大关联交易
                        if(unitCode=='8600'){
                            //总公司
                            if(isSecond){
                                //二级部门
                                procDefCode = 'zyzbejzdrel'
                            }else {
                                //一级部门
                                procDefCode='zyzbyizdrel'
                            }
                        }else {
                            //分公司
                            procDefCode = 'zybr_relGreat';
                        }
                    }else {
                        if(unitCode=='8600'){
                            //总公司
                            if(isSecond){
                                //二级部门
                                procDefCode = 'zyzbejybrel'
                            }else {
                                //一级部门
                                procDefCode='zyzbyjybrel'
                            }
                        }else {
                            //分公司
                            procDefCode = 'zybr_relGeneral';
                        }
                    }
                    var obj = {
                        procDefCode: procDefCode,
                        oldData: formData,
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    }
                    $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/workflow/process/runingList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });

                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        });
    }

    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeAll();
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
</script>
</body>
</html>
