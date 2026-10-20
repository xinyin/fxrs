<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表（保险）-提供服务类</title>
    <style>
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

        .relBtn {
            position: absolute;
            right: 5%;
            transform: translate(0%, -35%);
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
    <bc:ibox id="traServiceBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="ruleResult" name="ruleResult" value=""/>
            <bf:hidden id="relId" name="relId" value="${traService.relId}"/>
            <bf:hidden id="relNumber" name="relNumber" value="${traService.relNumber}"/>
            <bf:hidden id="relType" name="relType" value="${traService.relType}"/>
            <bf:hidden id="pid" name="pid" value="${traService.pid}"/>
            <bf:hidden id="total" name="total" value="${traService.totolTradeAmount}"/>
            <bf:hidden id="amount" name="amount" value="${traService.contractAmount}"/>
            <bf:hidden id="transNum" name="transNum" value="${traService.transNum}"/>
            <bf:hidden id="status" name="status" value="${traService.status}"/>
            <bf:hidden id="tradeZeroAmount" name="tradeZeroAmount" value="${traService.tradeZeroAmount}"/>
            <bf:hidden id="notMajorRelNumber" name="notMajorRelNumber" value="${traService.notMajorRelNumber}"/>

            <bf:hidden id="orgIsEmpty" name="orgIsEmpty" value="${orgIsEmpty}"/>
            <bf:hidden id="dataSources" name="dataSources" value="${traService.dataSources}"/>

            <bc:row cls="m-b">
                <bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
                    <bf:formGroup col="10">
                        <bc:label text="交易发起方信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="2">
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="12">
                        <div title="交易流水编号">
                            <bc:label cls="required" text="交易流水编号" col="2" formControl="true"/>
                        </div>
                        <bf:text id="fundsFlowNumber" name="fundsFlowNumber" col="7"  value="${traService.fundsFlowNumber}" disabled="${opt == 'view'}"
                                 placeholder="请输入"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="12">
                        <div title="交易概述">
                            <bc:label cls="required" text="交易概述" col="2" formControl="true"/>
                        </div>
                        <bf:text id="tranName" name="tranName" col="7" rows="3" multiple="true"
                                 value="${traService.tranName}" disabled="${opt == 'view'}"
                                 placeholder="复星保德信与（关联方名称） 签署《合同名称》，[  ]（简要描述交易内容），本次交易属于[ ]类关联交易，关联交易金额为[ ]万元"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>

            <bc:row cls="m-b">
                <bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
                    <bf:formGroup col="10">
                        <bc:label text="交易对手信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="2">
                        <bc:button id="addInfos" text="">
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
                        <bc:label text="合同信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="2">

                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="协议交易标志" col="5" formControl="true"/>
                        <bf:select id="protocolTransactionSymbol" name="protocolTransactionSymbol" col="7"
                                   value="${traService.protocolTransactionSymbol}" disabled="${opt == 'view'}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo">
                            <bv:notEmpty/>
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                                masterAgreementNumberSet();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label  text="主协议合同编号" col="5" formControl="true" cls="masterAgreementNumber"/>
                        <bf:text id="masterAgreementNumber" name="masterAgreementNumber" col="7" value="${traService.masterAgreementNumber}"  disabled="${opt == 'view'}">
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易币种" col="5" formControl="true"/>
                        <bf:select id="currencyCode" name="currencyCode" col="7"
                                   value="${traService.currencyCode}" disabled="${opt == 'view'}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-regCapitalCurrency">
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="ratio" text="原币交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="originalCurrencyAmount" name="originalCurrencyAmount" col="7"
                                 value="${traService.originalCurrencyAmount}"  disabled="${opt == 'view'}">
                            <%--<bv:notEmpty/>--%>
                            <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                            <bv:stringLength max="21" message="字段长度最大为20位"/>
                        </bf:text>
                    </bf:formGroup>

                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="本位币关联交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="relTradeAmount" name="relTradeAmount" col="7"
                                 value="${traService.relTradeAmount}" disabled="${opt == 'view'}">
                            <%--                                 change='isGreatTrade(),isConsiderType();'>--%>
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$"
                                       message="最大16位整数，4位小数！"/>
                            <bv:stringLength max="21" message="字段长度最大为20位"/>
                        </bf:text>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label cls="pracTradeAmount" text="关联交易实际金额（万元）" col="5" formControl="true"/>
                        <bf:text id="pracTradeAmount" name="pracTradeAmount" col="7"
                                 value="${traService.pracTradeAmount}">
                            <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$"
                                       message="最大16位整数，4位小数！"/>
                            <bv:stringLength max="21" message="字段长度最大为20位"/>
                        </bf:text>
                    </bf:formGroup>

                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div id="contractNo_Div" title="合同编号">
                            <bc:label cls="required" text="合同编号" col="5" formControl="true"/>
                            <bf:text id="contractNo" name="contractNo" col="7" value="${traService.contractNo}"
                                     disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="合同签署日期">
                            <bc:label cls="contractDate" text="合同签署日期" col="5" formControl="true"/>
                        </div>
                        <bf:datebox id="contractDate" name="contractDate" col="7" value="${traService.contractDateString}">
                        </bf:datebox>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div id="busStartTime_Div" title="关联交易发生的起始日期">
                            <bc:label cls="busStartTime" text="业务起始日期" col="5" formControl="true"/>
                            <bf:datebox id="busStartTime" name="busStartTime" col="7"
                                        value="${traService.busStartTimeString}">
                            </bf:datebox>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="关联交易发生的结束日期">
                            <bc:label cls="busEndTime" text="业务结束日期" col="5" formControl="true"/>
                        </div>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7"
                                    value="${traService.busEndTimeString}" />
                        <c:if test="${sign}">
                            <div style="text-align: right; color: red;">如合同为长期有效或到期自动续签，合同终止日期请填 29991231</div>
                        </c:if>
                    </bf:formGroup>

                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="合同状态" col="5" formControl="true"/>
                        <bf:select id="contractStatus" name="contractStatus" col="7"
                                   value="${traService.contractStatus}" disabled="${opt == 'view'}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-contractStatus">
                            <bv:notEmpty/>
                            <bf:option text="请选择" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <%--                    <bf:formGroup col="4" >--%>
                    <%--                        <bc:label cls="required" text="定价政策" col="5" formControl="true"/>--%>
                    <%--                        <bf:select id="pricingPolicy" name="pricingPolicy" col="7"--%>
                    <%--                                   value="${traService.pricingPolicy}" disabled="${opt == 'view'}">--%>
                    <%--                            <bf:option text="请选择" value=""></bf:option>--%>
                    <%--                            <bf:option text="无单独交易价格（担保类）" value="无单独交易价格（担保类）"></bf:option>--%>
                    <%--                            <bf:option text="产品统一费率（保险类）" value="产品统一费率（保险类）"></bf:option>--%>
                    <%--                            <bf:option text="市场公允价格" value="市场公允价格"></bf:option>--%>
                    <%--                            <bf:option text="双方协商确定" value="双方协商确定"></bf:option>--%>
                    <%--                            &lt;%&ndash;                            <bf:option text="其他" value="其他"></bf:option>&ndash;%&gt;--%>
                    <%--                            <bv:notEmpty/>--%>
                    <%--                        </bf:select>--%>
                    <%--                    </bf:formGroup>--%>

                    <bf:formGroup col="4">
                        <bc:label cls="required" text="是否可免于审议" col="5" formControl="true"/>
                        <bf:select id="isconsiderSituation" name="isconsiderSituation" col="7"
                                   value="${traService.isconsiderSituation}" disabled="${opt == 'view'}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo">
                            <be:event type="select">
                                <%--                                icsAlert();--%>
                            </be:event>
                            <bv:notEmpty/>
                            <bf:option text="请选择" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <div title="是否统一交易协议">
                            <bc:label cls="required" text="是否统一交易协议" col="5" formControl="true"/>
                            <bf:select id="isAgreement" name="isAgreement" col="7"
                                       value="${traService.isAgreement}"
                                       dataSource="fixedDict" url="YesOrNoForRelTraDict" disabled="${opt == 'view'}">
                                <be:event type="select">
                                    isAgreement(e.target.value);
                                </be:event>
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </div>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup col="4" id="considerSituationDiv">
                    <bc:label cls="required" text="免于审议情形" col="5" formControl="true"/>
                    <bf:select id="considerSituation" name="considerSituation" col="7"
                               value="${traService.considerSituation}" disabled="${opt == 'view'}"
                               dataSource="custom" url="sys/dictItem/getDictItems/INS-considerSituation">
                        <bf:option text="请选择" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div id="isAgreementImplDiv" title="统一交易协议类型">
                        <bc:label text="统一交易协议类型" col="5" formControl="true"/>
                        <bf:select id="tradeAgreementType" name="tradeAgreementType" col="7"
                                   value="${traService.tradeAgreementType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-isAgreementImpl"
                                   disabled="${opt == 'view'}">
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                            </be:event>
                        </bf:select>
                    </div>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">

                    <bf:formGroup col="12">
                        <div title="统一交易协议执行情况" id="isAgreementImplDivs">
                            <bc:label text="统一交易协议执行情况" col="2" formControl="true"/>
                            <bf:text id="isAgreementImpl" name="isAgreementImpl" col="7" rows="3" multiple="true"
                                     value="${traService.isAgreementImpl}" disabled="${opt == 'view'}"
                            ><bv:notEmpty/></bf:text>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="12">
                        <div title="交易价格">
                            <bc:label cls="required" text="交易价格" col="2" formControl="true"/>
                        </div>
                        <bf:text id="tradePrice" name="tradePrice" col="7" rows="3" multiple="true"
                                 value="${traService.tradePrice}" disabled="${opt == 'view'}"
                                 placeholder="关联交易的交易单价。"><bv:notEmpty/></bf:text>
                    </bf:formGroup>

                    <bf:formGroup col="12">
                        <div title="定价政策">
                            <bc:label cls="required" text="定价政策" col="2" formControl="true"/>
                        </div>
                        <bf:text id="pricingPolicy" name="pricingPolicy" col="7" rows="3" multiple="true"
                                 value="${traService.pricingPolicy}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </bf:formGroup>

                    <bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
                        <bf:formGroup col="10">
                            <bc:label text="业务信息：" col="2" formControl="true" cls="text-style"/>
                        </bf:formGroup>
                        <bf:formGroup col="2">
                        </bf:formGroup>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">

                    <bf:formGroup col="4">
                        <div title="交易类型">
                            <bc:label cls="required" text="交易类型" col="5" formControl="true"/>
                            <bf:select id="tranType" name="tranType" col="7" value="${traService.tranType}"
                                       dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
                            </bf:select>
                        </div>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label cls="required" text="业务类型" col="5" formControl="true"/>
                        <bf:select id="busType" name="busType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-ServiceType" disabled="${opt == 'view'}"
                                   includes="" value="${traService.busType}">
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                                busType(e.target.value);
                                financialAssstsName();
                                financialAssstsNum();
                                financialProRatio();
                            </be:event>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>

                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="交易方向" cls="required" col="5" formControl="true"/>
                        <bf:select id="tradingDirection" name="tradingDirection" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-Tra-Direction"
                                   disabled="${opt == 'view'}"
                                   includes="" value="${traService.tradingDirection}">
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>

                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="12">
                        <div title="交易标的">
                            <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                        </div>
                        <bf:text id="tradeSubject" name="tradeSubject" col="7" rows="3" multiple="true"
                                 value="${traService.tradeSubject}" disabled="${opt == 'view'}"
                                 placeholder="合同当事人权利义务所共同指向的交易对象，比如具体的物、权利。"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="12">
                        <div title="其他事项说明">
                            <bc:label text="其他事项说明" col="3" formControl="true"/>
                        </div>
                        <bf:text id="remark" name="remark" col="7" rows="3" multiple="true" value="${traService.remark}"
                                 disabled="${opt == 'view'}"> </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>
            <bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
                <bf:formGroup col="10">
                    <bc:label text="数据来源：" col="2" formControl="true" cls="text-style"/>
                </bf:formGroup>
                <bf:formGroup col="2">
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="来源系统唯一编码" col="5" formControl="true"/>
                    <bf:text id="uniqueCode" name="uniqueCode" col="7" disabled="true"
                             value="${traService.uniqueCode}">
                    </bf:text>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <bc:label text="来源系统" col="5" formControl="true"/>
                    <bf:text id="sourceSystem" name="sourceSystem" col="7" disabled="true"
                             value="${traService.sourceSystem}">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="来源模块" col="5" formControl="true"/>
                    <bf:text id="sourceModule" name="sourceModule" col="7" disabled="true"
                             value="${traService.sourceModule}">
                    </bf:text>
                </bf:formGroup>

            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="收付款标识" col="5" formControl="true"/>
                    <bf:select id="paymentId" name="paymentId" col="7" value="${traService.paymentId}"
                               dataSource="custom" url="sys/dictItem/getDictItems/INS-paymentId"
                               disabled="true">
                        <bf:option text="-" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <bc:label text="场景" col="5" formControl="true"/>
                    <bf:text id="scene" name="scene" col="7" disabled="true"
                             value="${traService.scene}">
                    </bf:text>
                </bf:formGroup>

            </bf:formGroup>

            <%-- <bf:formGroup>
                 <bf:formGroup col="6">
                     <bc:label text="风险预警" col="3" formControl="true"/>
                     <div>
                      <bf:text id="limitHint" name="limitHint" col="9" rows="5" multiple="true" value="${traService.limitHint}" disabled="true"/>
                     </div>
                 </bf:formGroup>
             </bf:formGroup>
  --%>
            <bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
                <bf:formGroup col="10">
                    <bc:label text="交易提示：" col="2" formControl="true" cls="text-style"/>
                </bf:formGroup>
                <bf:formGroup col="2">
                </bf:formGroup>

                <bf:formGroup col="12">
                    <bc:label text="单笔关联交易类型" col="3" formControl="true"/>
                    <bf:select id="singleRelatedTranType" name="singleRelatedTranType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-SingleRelatedTranType"
                               value="${traService.singleRelatedTranType}" disabled="true">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <div id="majorTypeDiv" title="达到重大关联交易的情形">
                        <bc:label text="达到重大关联交易的情形" col="3" formControl="true"/>
                        <bf:select id="majorType" name="majorType" col="7" dataSource="custom"
                                   disabled="true"
                                   url="sys/dictItem/getDictItems/INS-majorType"
                                   includes="" value="${traService.majorType}">
                            <bf:option text="无" value="0"></bf:option>
                        </bf:select>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="审议类型" col="3" formControl="true"/>
                    <bf:select id="considerType" name="considerType" col="7" value="${traService.considerType}"
                               disabled="true"
                               dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="是否需逐笔报告" col="3" formControl="true"/>
                    <bf:select id="eachReport" name="eachReport" col="7" value="${traService.eachReport}"
                               disabled="true"
                               dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="是否需逐笔披露" col="3" formControl="true"/>
                    <bf:select id="eachDisclosure" name="eachDisclosure" col="7" value="${traService.eachDisclosure}"
                               disabled="true"
                               dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="是否重大关联交易" col="3" formControl="true"/>
                    <bf:select id="majorTrade" name="majorTrade" col="7"
                               value="${traFunds.majorTrade}"
                               disabled="true"
                               dataSource="fixedDict"
                               url="YesOrNoForRelTraDict">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="是否需要补录关联方信息" col="3" formControl="true"/>
                    <bf:select id="isCollectRelated" name="isCollectRelated" col="7"
                               value="${traService.isCollectRelated}"
                               disabled="true"
                               dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
                <bf:formGroup col="10">
                    <bc:label text="附件信息：" col="2" formControl="true" cls="text-style"/>
                </bf:formGroup>
                <bf:formGroup col="2">

                </bf:formGroup>
            </bf:formGroup>

        </bf:form>
        <div id="tableDiv" style="width:95%;margin:0 auto">
            <bf:form id="queryForm" validation="true">
                <bf:formGroup col="4" hidden="true">
                    <bf:text name="isConsider" col="7" placeholder="name"/>
                </bf:formGroup>
            </bf:form>
            <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                      selectFirst="false" queryUrl="queryListDict?transNum=${traService.transNum}&type=service" pageSize="20">
                <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                <bc:toolbar id="downFile" text="下载" type="button" handler="downFileOnclick"/>
                <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                <bc:column field="type" title="附件业务类型" width="100"/>
                <bc:column field="typeCode" title="附件业务类型" width="100" visible="false"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="isMust" title="是否必传" width="100"/>
                <bc:column field="isUpload" title="上传状态" width="100"/>
                <bc:column field="beiFeng" title="备注(传附件如无特殊说明，均要求为盖章扫描版)" width="100"/>
                <bc:column field="belongOrg" title="上传部门" width="100" visible="false"/>
                <bc:column field="uploadTime" title="上传时间" width="100" visible="false"/>
                <bc:column field="fileType" title="文件类型" width="100" visible="false"/>
            </bc:table>
        </div>
        <bf:formGroup>
            <div class="col-sm-4 col-sm-offset-5">

                <bf:formGroup hrp="bottom">
                    <bc:button type="danger" text="保存" icon="save" id="save2">
                        <be:event>
                            checkSaves();
                        </be:event>
                    </bc:button>
                    <bc:button id="return" type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            <%--                                window.location = '${base}/ins/traService/list';--%>
                            window.history.back(-1);
                        </be:event>
                    </bc:button>
                </bf:formGroup>
            </div>
        </bf:formGroup>
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
    var traSubName = '复星保德信人寿保险有限公司';
    //记录添加个数；按照添加点击事件累加
    var count = 0;

    //记录页面具体添加个数，按照添加 删除按钮 动态变化
    var countTmp = 0;
    var considerName;
    function considenr(considerType){
       // var considerType = $("considerType").val();
        var name;
        if(considerType ==1 || considerType==2){
            name =null;
        }else if(considerType ==3){
            name =3;
        }
        if(name ==considerName){

        }else {
            considerName =name;
            $("input[name=isConsider]").val(name);
            $('#docTable').jTable('refresh');
        }
    };
    function considenr1(){
        var considerType = '${traService.considerType}';
        var name;
        if(considerType ==1 || considerType==2){
            name =null;
        }else if(considerType ==3){
            name =3;
            considerName =name;
            $("input[name=isConsider]").val(name);
            $('#docTable').jTable('refresh');
        }

    };

    //合同抽取待补录合同/交易发生日期不必填
    function notEmpt(value, validator, $field) {
        var fieId1 = $field[0].id;
        var value1 = value;
        var dataSources = '${traService.dataSources}';
        //生效时间
        if (fieId1 == "") {
            if ((dataSources != "3") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
    }

    function checkSaves() {
        var relType = $("#relType1").val();
        var relName = $("#relName1").val();
        var relNumber = $("#relNumber1").val();
        var relId = $("#relId1").val();

        if ("自然人" == relType) {
            relType = "PSN";
        } else if ("金融产品" == relType) {
            relType = "PRO";
        } else {
            relType = "ORG"
        }
        var isAgreement = $('#isAgreement').val();
        var isAgreementImpl = $('#isAgreementImpl').val();
        var tradeAgreementType = $('#tradeAgreementType').val();
        if ((isAgreement == '1') && (isAgreementImpl == null || isAgreementImpl == undefined || isAgreementImpl == ' ')) {
            $.messager.alert('错误', "请填写统一交易协议执行情况", 'error');
            return;
        }
        if ((isAgreement == '1') && (tradeAgreementType == null || tradeAgreementType == undefined || tradeAgreementType == ' ')) {
            $.messager.alert('错误', "请选择统一交易协议类型", 'error');
            return;
        }
        if ('${upload}' == 'true') {
            var filesList = getData();
            if (filesList.length === 0) {
                $.messager.alert('错误', "附件补充时需要上传文件！", 'error');
                return;
            }
            if (saveFiles()) {
                $.messager.alert('提示', "上传附件成功", 'info', function () {
                    window.location = '${base}/ins/traService/list';
                });
                return;
            } else {
                return;
            }
        }
        if ('${sign}' == 'true') {
            if ($("[name='contractDate']").val() == '' || $("[name='contractDate']").val() == null) {
                $.messager.alert('错误', "合同签署日期不能为空！", 'error');
                return;
            }
            if ($("[name='busStartTime']").val() == '' || $("[name='busStartTime']").val() == null) {
                $.messager.alert('错误', "业务起始日期不能为空！", 'error');
                return;
            }
            if ($("[name='busEndTime']").val() == '' || $("[name='busEndTime']").val() == null) {
                $.messager.alert('错误', "业务结束日期不能为空！", 'error');
                return;
            }
            if ($("[name='pracTradeAmount']").val() == '' || $("[name='pracTradeAmount']").val() == null) {
                $.messager.alert('错误', "关联交易实际金额不能为空！", 'error');
                return;
            }
        }
        // var transactionName =$('#transactionName').val();
        // if (transactionName == null || transactionName == undefined || transactionName == '') {
        //     $.messager.alert('错误', "交易名称为必填项，请填写", 'error');
        //     return;
        // }

        $("#tranName").val($("#tranName").val().replace(/[\r\n]/g, ""));//交易概述
        $("#isAgreementImpl").val($("#isAgreementImpl").val().replace(/[\r\n]/g, ""));//统一交易协议执行情况
        $("#tradePrice").val($("#tradePrice").val().replace(/[\r\n]/g, ""));//交易价格
        $("#pricingPolicy").val($("#pricingPolicy").val().replace(/[\r\n]/g, ""));//定价政策
        $("#tradeSubject").val($("#tradeSubject").val().replace(/[\r\n]/g, ""));//交易标的
        $("#remark").val($("#remark").val().replace(/[\r\n]/g, ""));//其他事项说明



        var relNameList = $("input[id^='relName']");
        var flag = false;
        for (var i = 0; i < relNameList.length; i++) {
            if (relNameList[i].value == '') {
                flag = true;
            }
        }
        if (flag) {
            $.messager.alert('错误', "请先添加交易对手！", 'error');
            return;
        }

        var tb = document.getElementById("docTable").innerText;
        //确认跳转页面的文件上传判断
        if ('${file}' == 'file') {
            if ((tb.search("没有找到匹配的记录") != -1)) {
                $.messager.alert('错误', "文件内容不能为空！", 'error');
                return;
            }
        }
        // $("#save2").attr("disabled", true);
        var docInnerPids = getAllIdSelections();
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        var formData = $('#editForm').serializeAll();

        if (isValid) {
            var docTableSelectItem = {
                'name': 'docTableSelectItem',
                'value': docInnerPids
            };
            formData.push(docTableSelectItem);
            if (isValid) {
                <%--$.mask.post('${base}/ins/traService/traServiceCheck', formData, function (data) {--%>
                <%--    if (data.success) {--%>
                var formData1 = $('#editForm').serializeAll();
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
                        "totalNotCleanTradeAmount": "",
                        "tranOpponent": "",
                        "certType": "",
                        // "assetsLastQuarter":"",
                        "tradeAmount": "",
                        "pid": ""
                    }

                    relItemInfo.relId = $("#relId" + i).val();
                    relItemInfo.relNumber = $("#relNumber" + i).val();
                    relItemInfo.relType = relType;
                    relItemInfo.relName = $("#relName" + i).val();
                    //relItemInfo.totalNotCleanTradeAmount = $("#totalNotCleanTradeAmount" + i).val();
                    // relItemInfo.assetsLastQuarter = $("#assetsLastQuarter"+i).val();
                    //relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
                    //relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
                    //relItemInfo.certType = $("#certType" + i).val();
                   /* relItemInfo.pid = $("#pid" + i).val();*/
                    relInfs.push(relItemInfo)
                }
                var relInfoDatas = {
                    'name': "relInfoListStr",
                    'value': JSON.stringify(relInfs)
                }
                var docTableSelectItem = {
                    'name': 'docTableSelectItem',
                    'value': docInnerPids
                };

                formData1.push(docTableSelectItem);
                formData1.push(relInfoDatas);
                $.mask.post('${base}/ins/traService/save', formData1, function (data) {
                    if (data.success) {
                        $.messager.alert('提示', data.msg, 'info', function () {
                            window.location = '${base}/ins/traService/list';
                        });
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                        $("#save2").attr("disabled", false);
                    }
                });
            } else {
                //合规检测有不通过的，需要提示用户 是否继续交易
                $.messager.confirm('提示', data.msg, function (r) {
                    if (r) {
                        $('#ruleResult').val(data.code);
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
                                "tranOpponent": "",
                                "certType": "",
                                // "assetsLastQuarter":"",
                                "tradeAmount": "",
                                "pid": ""
                            }

                            relItemInfo.relId = $("#relId" + i).val();
                            relItemInfo.relNumber = $("#relNumber" + i).val();
                            relItemInfo.relType = relType;
                            relItemInfo.relName = $("#relName" + i).val();
                            relItemInfo.pid = $("#pid" + i).val();
                            relInfs.push(relItemInfo)
                        }
                        var relInfoDatas = {
                            'name': "relInfoListStr",
                            'value': JSON.stringify(relInfs)
                        }

                        var docTableSelectItem = {
                            'name': 'docTableSelectItem',
                            'value': docInnerPids
                        };
                        formData2.push(docTableSelectItem);
                        formData2.push(relInfoDatas);
                        $.mask.post('${base}/ins/traService/save', formData2, function (data) {
                            if (data.success) {
                                $.messager.alert('提示', data.msg, 'info', function () {
                                    window.location = '${base}/ins/traService/list';
                                });
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                                $("#save2").attr("disabled", false);
                            }
                        });
                    }
                });
            }
            // });
        }
        // }

    }

    /**
     * 当是否可免于审议为否时不展示免于审议情形字段
     */
    $("#isconsiderSituation").change(function () {
        var isconsiderSituation = $("#isconsiderSituation").val();
        if ("1" == isconsiderSituation) {
            $("#considerSituationDiv").show();
        } else {
            $("#considerSituationDiv").hide();
        }
    });

    //查询交易对手相关方法
    function relNameSelect(relType, relId, relName1, totalNotCleanTradeAmount, relNumber, certTypes, certType, tradeAmount) {
        $.messager.modal({
            winId: 'relNameSelect',
            width: 700,
            height: 420,
            title: '交易对手信息',
            url: '${base}/ins/traInvestment/relNameSelect/' + traSubName + '/2',
            args: {
                result: function (obj) {
                    $("#" + relId.id).val(obj["relId"]);
                    $("#" + relName1.id).val(obj["relName"]);
                    var zeroAmount = obj["tradeZeroAmount"];
                    $("#" + tradeAmount.id).val(zeroAmount);
                    var newAount = 0;
                    var relTradeAmount = $('#relTradeAmount').val();
                    if (relTradeAmount != '' && relTradeAmount != 'undefined') {
                        zeroAmount = parseInt(zeroAmount) + parseInt(relTradeAmount);
                    }
                    $("#" + totalNotCleanTradeAmount.id).val(zeroAmount);

                    $('#total').val(obj["totolTradeAmount"]);
                    $("#" + relType.id).val(obj["relType"]);
                    $("#" + certTypes.id).val(obj["certTypes"]);
                    $("#" + certType.id).val(obj["certType"]);
                    $("#" + relNumber.id).val(obj["relNumber"]);
                    /*$("#"+tradeZeroAmount.id).val(obj["tradeZeroAmount"]);*/
                    if (obj["relType"] != '') {
                        $.ajax({
                            url: "${base}/sys/dictItem/getDictItems/INS-relType",
                            success: function (data) {
                                for (var i = 0; i < data.length; i++) {
                                    if (obj["relType"] == data[i].value) {
                                        $("#" + relType[0].id).val(data[i].value);
                                        $("#" + relType[0].id).val(data[i].name);
                                    }
                                }
                            }
                        });
                    }

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
                // isGreatTrade();
                // isInRoad();
                checkIsMajorTran();
            }
        });

    }

    $("#isconsiderSituation").change(function () {
        checkIsMajorTran();
    })
    $("#relTradeAmount").change(function () {
        checkIsMajorTran();
       // considenr();
    })

    $("#isAgreement").change(function () {
        checkIsMajorTran();
    })

    $("#tradeAgreementType").change(function () {
        checkIsMajorTran();
    })

    /**
     * 校验该笔关联交易是不是重大关联交易
     */
    function checkIsMajorTran() {
        /* 关联方类型 */
        var relType = $("#relType1").val();
        /* 关联方证件号码 */
        var relNumber = $("#relNumber1").val();
        /* 是否可免于审议 */
        var isConsiderSituation = $("#isconsiderSituation").val();
        /* 是否统一交易协议 */
        var isAgreement = $("#isAgreement").val();
        /* 关联交易金额 */
        var relTradeAmount = $("#relTradeAmount").val();
        /* 统一交易类型 */
        var tradeAgreementType = $("#tradeAgreementType").val();
        if (isNotEmpty(relType) && isNotEmpty(relNumber) && isNotEmpty(relTradeAmount)) {
            if ("自然人" === relType) {
                relType = "PSN";
            } else if ("金融产品" == relType) {
                relType = "PRO";
            } else {
                relType = "ORG"
            }
            $.ajax({
                type: 'GET',
                url: '${base}/ins/traService/checkIsMajorTran',
                data: {
                    "relType": relType,
                    "relNumber": relNumber,
                    "isConsiderSituation": isConsiderSituation,
                    "isAgreement": isAgreement,
                    "relTradeAmount": relTradeAmount,
                    "tradeAgreementType": tradeAgreementType
                },
                async: false,
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        //是否重大
                        var majorTrade = data.majorTrade;
                        //单笔关联交易类型 1.重大关联交易 2.一般关联交易
                        var singleRelatedTrantype = data.singleRelatedTrantype;
                        //达到重大关联交易的情形 1.单笔达到   2.累计达到
                        var majorType = data.majorType;
                        //审议类型(1:免予审议，2:一般审议，3逐笔审议)
                        var considerType = data.considerType;
                        considenr(considerType)
                        //是否需逐笔报告 1.是  0.否
                        var eachReport = data.eachReport;
                        //是否需逐笔披露  1.是  0.否
                        var eachDisclosure = data.eachDisclosure;
                        var isconsiderSituation = data.isconsiderSituation;
                        if (null != isconsiderSituation && isconsiderSituation != "") {
                            var considerSituation = data.considerSituation;
                            $("#isconsiderSituation").val(isconsiderSituation);
                            $('#isconsiderSituation').select2({value: isconsiderSituation});
                            $("#considerSituation").val(considerSituation);
                            $('#considerSituation').select2({value: considerSituation});
                            $("#considerSituationDiv").show();
                        }
                        var specialMajorTrade = data.specialMajorTrade;
                        $("#specialMajorTrade").val(specialMajorTrade);
                        $('#specialMajorTrade').select2({value: specialMajorTrade});

                        var isCollectRelated = data.isCollectRelated;
                        $("#isCollectRelated").val(isCollectRelated);
                        $('#isCollectRelated').select2({value: isCollectRelated});
                        /* 赋值 */
                        $("#majorTrade").val(majorTrade);
                        $('#majorTrade').select2({value: majorTrade});

                        $("#singleRelatedTranType").val(singleRelatedTrantype);
                        $('#singleRelatedTranType').select2({value: singleRelatedTrantype});

                        $("#majorType").val(majorType);
                        $('#majorType').select2({value: majorType});

                        $("#considerType").val(considerType);
                        $('#considerType').select2({value: considerType});
                        $("#eachReport").val(eachReport);
                        $('#eachReport').select2({value: eachReport});
                        $("#eachDisclosure").val(eachDisclosure);
                        $('#eachDisclosure').select2({value: eachDisclosure});
                    } else {
                        $.messager.alert('提示', data.msg, 'error');
                    }
                }
            })
        }
    }

    //判断字符是否为空的方法
    function isNotEmpty(obj) {
        if (typeof obj == "undefined" || obj == null || obj == "") {
            return false;
        } else {
            return true;
        }
    }

    // 日期插件初始化
    $('#busStartTime').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView: "month"
    }).on("changeDate", function (ev) {  //值改变事件
        // 选择的日期不能大于第二个日期控件的日期
        if (ev.date) {
            $("#busEndTime").datetimepicker('setStartDate', new Date(ev.date.valueOf()));
        } else {
            $("#busEndTime").datetimepicker('setStartDate', null);
        }
    });
    $('#busEndTime').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: "month",
        autoclose: true,

    }).on("changeDate", function (ev) {
        // 选择的日期不能小于第一个日期控件的日期
        if (ev.date) {
            $("#busStartTime").datetimepicker('setEndDate', new Date(ev.date.valueOf()));
        } else {
            $("#busStartTime").datetimepicker('setEndDate', new Date());
        }
    });

    //跟据审议类型规则给定审议类型得值
    function isConsiderType() {
        //是否为重大关联交易
        var isMajorTrade = $('#majorTrade').val();
        //关联交易金额
        var relTradeAmount = $('#relTradeAmount').val();
        //关联方类型
        var relType = $('#relType1').val();

        var isAgreement = $('#isAgreement').val();
        var isAgreementImpl = $('#isAgreementImpl').val();
        //优先判断是否统一交易协议，如果是并且是统一交易协议下进行的交易，直接判定原先的重大规则，执行情况为签订、续签、实质性变更时，判断为累计重大，审议类型为逐笔，
        if (isAgreement == '1' && isAgreementImpl != '4' && isAgreementImpl != ' ') {
            //如果是统一交易协议情况，审议类型改为免审
            /*  if (isAgreementImpl != '4') {*/
            /*    $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
            } else {*/
            $('#considerType').val("3");
            $('#considerType').select2({value: "3"});
            if ($('#contractNoDiv label font').length == 0) {
                $('#contractNoDiv label').prepend("<font color='red'>✶</font>");
                $('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
                $('#effectiveTimeDiv label').prepend("<font color='red'>✶</font>");
                $('#yield_Div label').prepend("<font color='red'>✶</font>");
                $('#approvalNumDiv label').prepend("<font color='red'>✶</font>");
                $('#securitiesTypeDiv label').prepend("<font color='red'>✶</font>");
                $('#shareNumDiv label').prepend("<font color='red'>✶</font>");
                $('#variableshareratioDiv label').prepend("<font color='red'>✶</font>");
                $('#variableshareratioAfterDiv label').prepend("<font color='red'>✶</font>");
                $('#majorTradeDiv label').prepend("<font color='red'>✶</font>");
                //  $('#majorTypeDiv label').prepend("<font color='red'>✶</font>");
                $('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
                $('#yearDiv label').prepend("<font color='red'>✶</font>");
                $('#IsAgreement label').prepend("<font color='red'>✶</font>");
            }
            /* }*/
        } else {
            //优先判断重大，如有重大一律逐笔审议
            if (isMajorTrade == '1') {
                $('#considerType').val("3");
                $('#considerType').select2({value: "3"});
                if ($('#contractNoDiv label font').length == 0) {
                    $('#contractNoDiv label').prepend("<font color='red'>✶</font>");
                    $('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
                    $('#effectiveTimeDiv label').prepend("<font color='red'>✶</font>");
                    $('#yield_Div label').prepend("<font color='red'>✶</font>");
                    $('#approvalNumDiv label').prepend("<font color='red'>✶</font>");
                    $('#securitiesTypeDiv label').prepend("<font color='red'>✶</font>");
                    $('#shareNumDiv label').prepend("<font color='red'>✶</font>");
                    $('#variableshareratioDiv label').prepend("<font color='red'>✶</font>");
                    $('#variableshareratioAfterDiv label').prepend("<font color='red'>✶</font>");
                    $('#majorTradeDiv label').prepend("<font color='red'>✶</font>");
                    //  $('#majorTypeDiv label').prepend("<font color='red'>✶</font>");
                    $('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
                    $('#yearDiv label').prepend("<font color='red'>✶</font>");
                    $('#IsAgreement label').prepend("<font color='red'>✶</font>");
                }
                return;
            } else if ((relType == "PSN" && relTradeAmount < 50) || (relType == "ORG" && relTradeAmount < 500)) {
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                if ($('#contractNoDiv label font').length != 0) {
                    $('#contractNoDiv label font').remove();
                    $('#busStartTimeDiv label font').remove();
                    $('#effectiveTimeDiv label font').remove();
                    $('#yield_Div label font').remove();
                    $('#approvalNumDiv label font').remove();
                    $('#securitiesTypeDiv label v').remove();
                    $('#shareNumDiv label font').remove();
                    $('#variableshareratioDiv label font').remove();
                    $('#variableshareratioAfterDiv label font').remove();
                    $('#majorTradeDiv label font').remove();
                    //  $('#majorTypeDiv label').prepend("<font color='red'>✶</font>");
                    $('#financialImpactDiv label font').remove();
                    $('#yearDiv label  font').remove();
                    $('#IsAgreement label font').remove();

                }
                return;
            } else {
                $('#considerType').val("2");
                $('#considerType').select2({value: "2"});
                if ($('#contractNoDiv label font').length != 0) {
                    $('#contractNoDiv label font').remove();
                    $('#busStartTimeDiv label font').remove();
                    $('#effectiveTimeDiv label font').remove();
                    $('#yield_Div label font').remove();
                    $('#approvalNumDiv label font').remove();
                    $('#securitiesTypeDiv label v').remove();
                    $('#shareNumDiv label font').remove();
                    $('#variableshareratioDiv label font').remove();
                    $('#variableshareratioAfterDiv label font').remove();
                    $('#majorTradeDiv label font').remove();
                    //  $('#majorTypeDiv label').prepend("<font color='red'>✶</font>");
                    $('#financialImpactDiv label font').remove();
                    $('#yearDiv label  font').remove();
                    $('#IsAgreement label font').remove();

                }
            }
        }

    }

    //判断是否为控股子公司来决定子公司名称证件号能不能添加
    function HoldingSubsidiarySub(value) {
        var value = value;
        if (value == '0') {
            $('#holdingSubsidiaryNumber').val("");
            $('#holdingSubsidiaryName').val("");
            $('#holdingSubsidiaryNumberDiv').css('display', 'none');
            $('#holdingSubsidiaryNameDiv').css('display', 'none');
            $('#holdingSubsidiaryNumberDiv label font').remove();
            $('#holdingSubsidiaryNameDiv label font ').remove();

        }
        if (value == '1') {
            $('#holdingSubsidiaryNumberDiv').css('display', 'block')
            $('#holdingSubsidiaryNameDiv').css('display', 'block')
            if ($('#holdingSubsidiaryNumberDiv label font').length == 0) {
                $('#holdingSubsidiaryNumberDiv label').prepend("<font color='red'>✶</font>");
                $('#holdingSubsidiaryNameDiv label').prepend("<font color='red'>✶</font>");
            }
        }

    };

    //保存时对三个隐藏字段的校验
    function aboutHoldingSubsidiary() {
        var val = $('#isHoldingSubsidiary').val();
        var num = $('#holdingSubsidiaryNumber').val();
        var name = $('#holdingSubsidiaryName').val();
        if (val == 0) {
            if (num == '') {
                $.messager.alert('提示', '请填写控股子公司全称', 'warning');
                return;
            }
            if (name == '') {
                $.messager.alert('提示', '请选择控股子公司证件类型', 'warning');
                return;
            }
            if (type) {
                $.messager.alert('提示', '请填写控股子公司证件号码', 'warning');
                return;
            }
        }
    }


    /*   //根据所选业务类型判断一些字段是否为必填
       function notEm(value, validator, $field) {
           var fieId1 = $field[0].id;
           var value1 = value;
           var unitfTradeAgrmentAbusiness = $('#unitfTradeAgrmentAbusiness').val();
           //是否为统一交易协议
           if (fieId1 == "isAgreement") {
               if ((unitfTradeAgrmentAbusiness == "01") && (value1 == null || value1 == " ")) {
                   return false;
               } else {
                   return true;
               }
           }
       }*/

    /*
    * 关联方查询
    * */
    $("#relNumber").blur(function () {
        var relNumber = $("#relNumber").val();
        var relName = $("#relName").val();
        if ((relNumber !== '' && relNumber !== undefined) || (relName !== '' && relName !== undefined)) {
            infoBlur(relNumber, relName);
        }
    });

    function onSelect() {
        var proName = $("input[name=proName]").val();
        $.ajax({
            type: "post",
            url: "${base}/ins/traService/selectbuinessL",
            data: {"unitCodes": proName},
            success: function (data) {
                if (data.success) {
                    var busType = data.busType;
                    $('#busType').val(busType);
                    $('#busType').select2({value: busType});
                } else {
                    $.messager.alert('提示', data.msg, 'error');
                }
            }
        });
    }

    //判断是否重大
    $("#tradeAmount").blur(function () {
        var tradeAmount = $("#tradeAmount").val();
        var relId = $("#relId").val();
        if ((tradeAmount !== '' && tradeAmount !== undefined) || (relId !== '' && relId !== undefined)) {
            // infoBlur(relNumber,relName);
        }
    });

    function infoBlur(relNumber, relName) {
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traInvestment/selectRel',
            data: {
                "relName": relName,
                "identifyNumber": relNumber
            },
            dataType: 'json',
            success: function (data) {
                $("#relNumber").empty();
                if (data.success) {
                    $("#relId").val(data.relId);
                    $("#relName").val(data.relName);
                    $("#totolTradeAmount").val(data.totalTradeAmount);
                    $('#total').val(data.totalTradeAmount);
                    $("#caliber").val(data.caliber);
                    $("#caliber").select2({value: (data.caliber)});
                    $("#relType").val(data.relType);
                    $("#relType").select2({value: (data.relType)});
                } else {
                    $.messager.alert('提示', '关联方不存在！', 'warning');
                }
            }
        });
    }

    /*
    * 文件上传
    * */
    function exportIn() {
        $("#hiddenBtn").click();
    }

    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键

        // alert(pids);
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
                        window.location.reload();
                    }
                }

            })
        }
    }

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        if (pid == '' || pid == undefined) {
            $.messager.alert('提示', "请选中一个文件!", 'error');
            return;
        }
        if (pid.length > 1) {
            $.messager.alert('提示', "只能选中一个文件!", 'error');
            return;
        }
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if(row[0].fileType !== 'pdf'){
            $.messager.alert('提示', "只支持查看pdf格式，doc、zip文件需下载查看！", 'error');
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/ins/traInsenclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }

    //文件表所有选中的数据
    function getselectData() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row//返回数据行中的id值
        });
    }


    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf' && fileLast !='zip') {
                $.messager.alert('提示', "请上传pdf或zip文件！", 'info');
                return;
            }
        }
        var selectRows = getselectData();//选中的数据
        if (selectRows.length != 1) {
            $.messager.alert('提示', "请先选中一条数据", 'info');
            return;
        }
        var typeCode = selectRows[0].typeCode
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/ins/traInsenclosure/uploadFilesList?businessType=04&typeCode='+typeCode, //上传文件的请求路径必须是绝对路径
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
                $.messager.alert("文件上传成功",data.msg,"info");
                if (data.success) {
                    //处理数据
                    selectRows[0].isUpload ="已上传"
                    selectRows[0].pid = data.traEnclosure[0].pid
                    selectRows[0].belongOrg =data.traEnclosure[0].uploadDept
                    selectRows[0].uploadTime =data.traEnclosure[0].uploadTime
                    selectRows[0].fileType =data.traEnclosure[0].fileType
                    var files = data.traEnclosure;
                    var count = files.length;
                    //获取原有表单的数据
                    var rows = getData();
                    //  rows = rows.concat(files);
                    //构造表格显示数据
                    //var total = rows.length;
                    var showData = {'total': '11', 'rows': rows}
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


    //表单提交数据
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

    //文件表数据
    function getData() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row
        });
    }

    function getAllIdSelections() {
        return $.map($('#docTable').bootstrapTable('getData'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function getIdSelections() {
        return $.map($('#docTable').bootstrapTable('getSelections'), function (row) {
            return row.pid//返回数据行中的id值
        });
    }

    function basicInformation() {
        var pid = $('#pid').val();
        window.location.href = "${base}/bus/gdInfo/edit/" + pid;
    }

    function affiliateInformation() {
        var pid = $('#pid').val();
        if (pid == null || pid == "") {
            alert("请先保存基本信息!")
        } else {
            window.location = '${base}/bus/gdInfo/orgRelPsnList/' + pid;
        }

    }

    function orgRelInformation() {
        var pid = $('#pid').val();
        if (pid == null || pid == "") {
            alert("请先保存基本信息!")
        } else {
            window.location = '${base}/bus/gdInfo/orgRelOrgList/' + pid;
        }

    }


    function getPorportion() {
        var holeShareAmount = $('#holeShareAmount').val();
        $.ajax({
            type: 'get',
            url: '${base}/bus/gdInfo/getPorportion/' + holeShareAmount,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var proportion = data.proportion;
                    $('#shareholdingProportion').val(proportion);
                }
            }
        });
    }

    //判断当前关联方是否拥有在途数据
    function isInRoad() {
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
                "totalNotCleanTradeAmount": "",
                //"tranOpponent": "",
                "certType": "",
                // "assetsLastQuarter":"",
                "tradeAmount": "",
                "pid": ""
            };
            relItemInfo.relId = $("#relId" + i).val();
            relItemInfo.relNumber = $("#relNumber" + i).val();
            relItemInfo.relType = $("#relType" + i).val();
            relItemInfo.relName = $("#relName" + i).val();
            relItemInfo.totalNotCleanTradeAmount = $("#totalNotCleanTradeAmount" + i).val();
            // relItemInfo.assetsLastQuarter = $("#assetsLastQuarter"+i).val();
            relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
            //relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
            relItemInfo.certType = $("#certType" + i).val();
            relItemInfo.pid = $("#pid" + i).val();
            relInfs.push(relItemInfo)
        }

        var obj = {
            "relInfoDatas": JSON.stringify(relInfs),
            "traSubName": traSubName
        };
        $.ajax({
            url: '${base}/bus/relPsn/queryRelTradeAmount',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    var flag = data.flag;
                    if (flag == '1') {
                        $.messager.alert('关联交易在途数据预警:', data.msg, 'warning');
                    }
                }
            }
        });
    }

    //判断是否重大关联交易
    function isGreatTrade() {
        //如果是统一交易协议,并且是统一交易协议下发生的交易，判断为非重大
        var isAgreement = $('#isAgreement').val();
        var isAgreementImpl = $('#isAgreementImpl').val();
        if (isAgreement == '1' && isAgreementImpl != ' ' && isAgreementImpl != '4') {
            /*if (isAgreementImpl == '4') {
                $('#majorTrade').val("0");
                $('#majorTrade').select2({value: ("0")});
                $('#majorType').val(" ");
                $('#majorType').select2({value: (" ")});
                ;
            } else*/
            $('#majorTrade').val("1");
            $('#majorTrade').select2({value: ("1")});
            $('#majorType').val("2");
            $('#majorType').select2({value: ("2")});
            ;
            /* else {
                 $('#majorTrade').val(" ");
                 $('#majorTrade').select2({value: (" ")});
                 $('#majorType').val(" ");
                 $('#majorType').select2({value: (" ")});
                 ;
             }*/
            return;
        }
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
        /**关联交易金额*/
        var relTradeAmount = $('#relTradeAmount').val();
        if (relTradeAmount == '' || relTradeAmount == null) {
            return;
        }

        /**调用重大校验接口*/
        var obj = {
            relTradeAmount: relTradeAmount,
            /* pracTradeAmount: pracTradeAmount,*/
            relInfoListStr: JSON.stringify(relInfs)
        };
        $.ajax({
            url: '${base}/ins/traService/GreatCheck',
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
        if (list[0].isGreatTrade == '1') {
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

    var opt = '${opt}';
    //页面加载初始化js函数
    $(document).ready(function () {
        //上传文件格式显示
        considenr1()
        //判断是否控股子公司，显示隐藏相关标签
        var val = $('#isHoldingSubsidiary').val();
        HoldingSubsidiarySub(val);
        //判断是否统一交易协议，显示隐藏相关标签
        var val = $('#isAgreement').val();
        isAgreement(val);
        //遍历交易对手数组并且在页面展示交易对手信息
        <%--var relList = ${traService.relInfoListStr};--%>
        <%--if (relList.length == 0) {--%>
        <%--    $("#addInfos").trigger("click");--%>
        <%--} else {--%>
        <%--    for (var i = 0; i < relList.length; i++) {--%>
        <%--        addInfos(relList[i]);--%>
        <%--        isConsiderType();--%>
        <%--        isGreatTrade();--%>
        <%--    }--%>
        <%--}--%>

        var relId =  '${traService.relId}';
        var relNumber = '${traService.relNumber}';
        var relType =  '${traService.relType}';
        var relName = '${traService.relName}';
        var relList = {
            "relId": relId,
            "relNumber": relNumber,
            "relType": relType,
            "relName": relName
        }
        if (relList.length == 0 || "null" == relList) {
            $("#addInfos").trigger("click");
        } else {
            addInfos(relList);
        }
        //审批
        if (opt == 'send') {
            $("#save2").css("display", "none");
            $("#docTable_tb_exportIn").css("display", "none");
            $("#docTable_tb_removeDoc").css("display", "none");
            $("#return").css("display", "none");
        }

        if (opt == 'view') {
            if ('${isConfirm}' != 'true' && '${sign}' != 'true') {
                $("[name='contractDate']").attr("disabled", true);
                $("[name='busStartTime']").attr("disabled", true);
                $("[name='busEndTime']").attr("disabled", true);
                $("[name='pracTradeAmount']").attr("disabled", true);
            }
            if ('${sign}' == 'true') {
                $('.pracTradeAmount').html("<font color='red'>✶</font>" +  $('.pracTradeAmount').html());
                $('.contractDate').html("<font color='red'>✶</font>" +  $('.contractDate').html());
                $('.busStartTime').html("<font color='red'>✶</font>" +  $('.busStartTime').html());
                $('.busEndTime').html("<font color='red'>✶</font>" +  $('.busEndTime').html());
                //设置为必填
                $("#pracTradeAmount").attr("data-fv-notempty","true");
                $("#contractDate").attr("data-fv-notempty","true");
                $("#busStartTime").attr("data-fv-notempty","true");
                $("#busEndTime").attr("data-fv-notempty","true");
            }
            if ('${isConfirm}' != 'true' ) {
                $("#save2").css("display", "none");
                if ('${upload}' != 'true') {
                    $("#docTable_tb_exportIn").css("display", "none");
                    $("#docTable_tb_removeDoc").css("display", "none");
                } else {
                    $("#save2").css("display", "inline-block");
                }

            }
            var num = $('#formItems div').length;
            for (var i = 1; i <= num; i++) {
                $("#" + "certTypes" + i).attr("disabled", true);
                $("#" + "relName" + i).attr("disabled", true);
                $("#" + "relNumber" + i).attr("disabled", true);
                /*$("#"+"totalassetsLateLast"+i).attr("disabled",true);*/
            }
            $("#addInfos").hide();
            $(".btn-danger1").hide();
        }


        $('#tranType').val("2");
        $('#tranType').select2({value: "2"});
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });


        /*  if ($('#considerType').val() == 3) {
              $('#contractNoDiv label').prepend("<font color='red'>✶</font>");
              $('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
              $('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
          }*/

        //审批页面隐藏返回按钮
        if ('handler' == '${optNoe}') {
            $("#docTable_tb_exportIn").css("display", "none");
            $("#docTable_tb_removeDoc").css("display", "none");
            $("#return").css("display", "none");
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
        var totalNotCleanTradeAmount = "totalNotCleanTradeAmount" + count;
        var assetsLastQuarter = "assetsLastQuarter" + count;
        var tradeAmount = "tradeAmount" + count;
        var relId = "relId" + count;
        var relType = "relType" + count;
        var pid = "pid" + count;
        var sourceSystem = '${traService.sourceSystem}';
        //formItems
        var itemStr
        console.log(sourceSystem)
        if(sourceSystem !=null && sourceSystem !=''){
            itemStr  = ' <div class="form-group " id="' + item + '" >\n' +
                '   <div class="form-group ">\n' +
                '       <div class="form-group  col-xs-4">\n' +
                '           <label  class="control-label col-xs-5"><font color="red">✶</font>关联方名称</label>\n' +
                '           <div class="  col-xs-7">\n' +
                '               <input type="text" disabled="disabled" data-fv-notempty="true"  class="jep-input form-control"  id="' + relType + '" value = "';
        }else {
             itemStr = ' <div class="form-group " id="' + item + '" >\n' +
                '   <div class="form-group ">\n' +
                '       <div class="form-group  col-xs-4">\n' +
                '           <label  class="control-label col-xs-5"><font color="red">✶</font>关联方名称</label>\n' +
                '           <div class="  col-xs-7">\n' +
                '           <input type="text" onclick="relNameSelect(' + relType + ',' + relId + ',' + relName + ',' + totalNotCleanTradeAmount + ',' + relNumber + ',' + certTypes + ',' + certType + ',' + tradeAmount + ')" class="jep-input form-control " id="' + relName + '" value = "';
        }
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relName;
        }
        itemStr = itemStr + '"  >\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-5" >\n' +
            '           <label  class="control-label col-xs-5"><font color="red">✶</font>关联方类型</label>\n' +
            '           <div class="col-xs-7">\n' +
            '               <input type="text" disabled="disabled" data-fv-notempty="true"  class="jep-input form-control"  id="' + relType + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            $.ajax({
                url: "${base}/sys/dictItem/getDictItems/INS-relType",
                async: false,
                success: function (data) {

                    for (var i = 0; i < data.length; i++) {
                        if (relInfo.relType == data[i].value) {
                            itemStr = itemStr + data[i].name;

                        }
                    }
                }
            });
        }

        itemStr = itemStr + '"  >\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-5" hidden="true">\n' +
            '           <label  class="control-label col-xs-5"><font color="red">✶</font>关联方证件类型</label>\n' +
            '           <div class="col-xs-7">\n' +
            '               <input type="text" disabled="disabled" data-fv-notempty="true"  class="jep-input form-control"  id="' + certTypes + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
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

        <%--itemStr = itemStr + '"  >\n' +--%>
        <%--    '           </div>\n' +--%>
        <%--    '       </div>\n' +--%>
        <%--    '   </div>\n' +--%>
        <%--    '       <div class="form-group  col-xs-4">\n' +--%>
        <%--    '           <label  class="control-label col-xs-5"><font color="red">✶</font>关联方类型</label>\n' +--%>
        <%--    '           <div class="col-xs-7">\n' +--%>
        <%--    '               <input disabled="true"  type="text" data-fv-notempty="true"  class="jep-input form-control"  id="' + relType + '" value = "';--%>
        <%--if (relInfo != undefined && relInfo != null && relInfo != "") {--%>
        <%--    $.ajax({--%>
        <%--        url: "${base}/sys/dictItem/getDictItems/INS-relType",--%>
        <%--        async: false,--%>
        <%--        success: function (data) {--%>

        <%--            for (var i = 0; i < data.length; i++) {--%>
        <%--                if (relInfo.relType == data[i].value) {--%>
        <%--                    itemStr = itemStr + data[i].name;--%>

        <%--                }--%>
        <%--            }--%>
        <%--        }--%>
        <%--    });--%>
        <%--}--%>
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
            '       <div class="form-group  col-xs-4">\n' +
            '           <label  class="control-label col-xs-5"><font color="red">✶</font>关联方证件号码</label>\n' +
            '           <div class="  col-xs-7">\n' +
            '           <input type="text" disabled="disabled" class="jep-input form-control " id="' + relNumber + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relNumber;
        }
        itemStr = itemStr + '">\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div id="yearDiv" class="form-group  col-xs-5" hidden="true">\n' +
            '              <label  class="control-label col-xs-5 " title="">本年度与该关联方发生的关联交易累计金额（万元）</label>\n' +
            '              <div class="  col-xs-7">\n' +
            '                   <input type="text" disabled="disabled" data-fv-notempty="true"  class="jep-input form-control" id="' + totalNotCleanTradeAmount + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.totalNotCleanTradeAmount;
        }
        itemStr = itemStr + '">\n' +
            '               </div>\n' +
            '        </div>\n' +
            // '        <div class="form-group  col-xs-1">\n' +
            // '           <div class=" btn-danger1" onclick="delItme(\'' + item + '\')">删除</div>\n' +
            // '        </div>\n' +
            '   </div>\n' +
            '   <div class="form-group ">\n' +
            '        <div class="form-group  col-xs-6">\n' +

            '       </div>\n' +
            '       <div class="form-group  col-xs-5">\n' +
            '                  <input type="text" style="display:none" class="jep-input form-control" id="' + tradeAmount + '" value ="';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.tradeAmount;
        }
        itemStr = itemStr + '">\n' +

            '       </div>\n' +
            '       <div class="form-group  col-xs-1"></div>\n' +
            '       <input type="hidden" class="form-control" id="' + relId + '" value="';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relId;
        }
        itemStr = itemStr + '">\n' +
            '       <input type="hidden" class="form-control" id="' + relType + '" value="'
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relType;
        }
        itemStr = itemStr + '">\n' +
            '       <input type="hidden" class="form-control" id="' + pid + '" value="'
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.pid;
        }
        itemStr = itemStr + '">\n' +
            '       </div>' +
            '    </div>\n' +
            '</div>';
        $("#formItems").append(itemStr);
    }

    function delItme(item) {
        if (countTmp == 1) {
            $.messager.alert('提示', "请勿全部删除", 'info');
            return
        }
        $("#" + item).remove()
        countTmp--;
        isGreatTrade();
        isConsiderType();
    }

    function toConfirm() {
        var pid = ${pid}
            $.ajax({
                url: "${base}/ins/traService/confirm/" + pid,
                method: "GET",
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        window.location = '${base}/ins/traService/list/';
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
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

    function amount() {

    }

    //判断是否是统一交易协议，统一交易协议展示统一交易协议执行情况
    function isAgreement(value) {
        if (value == '0') {
            $('#isAgreementImplDiv ').css('display', 'none');
            $('#isAgreementImplDivs ').css('display', 'none');
            $('#agreementNumDiv ').css('display', 'none');

        } else if (value == '1') {
            $('#isAgreementImplDiv ').css('display', 'block');
            $('#isAgreementImplDivs ').css('display', 'block');
            $('#agreementNumDiv ').css('display', 'block');
            if ($('#isAgreementImplDiv label font').length == 0) {
                $('#isAgreementImplDiv label').prepend("<font color='red'>✶</font>");
            }
            if ($('#isAgreementImplDivs label font').length == 0) {
                $('#isAgreementImplDivs label').prepend("<font color='red'>✶</font>");
            }
        }

        /*var value1 = $("#isAgreement").val();
        var isAgreeImpl = $("#isAgreementImpl").val();
        var value = value;
        if (value == '0' || value1 == '0') {
            $('#isAgreementImpl').val("");
            $('#isAgreementImpl').val("").trigger('change');
            $('#agreementNum').val("");
            $('#isAgreementImplDiv ').css('display', 'none');
            $('#agreementNumDiv  ').css('display', 'none');
            $('#isAgreementImplDiv label font').remove();
            $('#agreementNumDiv label font ').remove();
            isInRoad();
            isGreatTrade();
            isConsiderType();
            if ($('#considerType').val() != '3') {
                $('#contractNoDiv  label font').remove();
                $('#busStartTimeDiv label font').remove();
                $('#financialImpactDiv label font').remove();
            }
        }
        if ((value == '1' && isAgreeImpl == '0' && (isAgreeImpl == null || isAgreeImpl == " "))
            || (value1 == '1' && isAgreeImpl != '1' && (isAgreeImpl == null || isAgreeImpl == " "))) {
            $('#isAgreementImplDiv').css('display', 'block');
            $('#agreementNumDiv').css('display', 'block');
            if ($('#isAgreementImplDiv label font').length == 0) {
                $('#isAgreementImplDiv label').prepend("<font color='red'>✶</font>");
                $('#agreementNumDiv label ').prepend("<font color='red'>✶</font>");
            }
            $('#considerType').val("3");
            $('#considerType').select2({value: "3"});
            if ($('#contractNoDiv label font').length == 0) {
                $('#contractNoDiv label').prepend("<font color='red'>✶</font>");
                $('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
                $('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
            }
        }*/
    };

    /*   function isAgreementImpl(value) {
           //如果是统一交易协议下情况，需要给统一交易协议编号加上必输标记
           if(value=='4'){
               $('#agreementNumDiv label ').prepend("<font color='red'>✶</font>");
           }else{
               $('#agreementNumDiv label font').remove();
           }
           /!*var IsAgreement = $("#isAgreement").val();
           var value = value;
           if (IsAgreement == '0' && value == '0') {
               isInRoad();
               isGreatTrade();
               isConsiderType();
           } else {
               if ($('#isAgreementImplDiv label font').length == 0) {
                   $('#isAgreementImplDiv label').prepend("<font color='red'>✶</font>");
                   $('#agreementNumDiv label ').prepend("<font color='red'>✶</font>");
               }
               $('#considerType').val("3");
               $('#considerType').select2({value: "3"});
               if ($('#contractNoDiv label font').length == 0) {
                   $('#contractNoDiv label').prepend("<font color='red'>✶</font>");
                   $('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
                   $('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
               }

           }*!/
       }*/
    //v合同金额，v1实际交易金额，v2关联交易金额
    function copyAmount() {
        var v = $('#contractAmount');
        var v1 = $('#pracTradeAmount');
        var v2 = $('#relTradeAmount');
        /*  if (v.val() == undefined ||v.val() == '') {
              alert("请输入合同金额！");
              return;
          }*/
        if (v1.val() != undefined && v1.val().trim() != '') {
            v2.attr("value", v1.val());
        } else {
            v2.attr("value", v.val());
        }

        //如果是统一交易协议，则不累计
        var isAgreement = $('#isAgreement').val();
        if (isAgreement == '0') {
            //获取交易对手信息并且追加相关金额
            var relTraList = $("input[id^='tradeAmount']");
            var relTotList = $("input[id^='totalNotCleanTradeAmount']");
            for (var i = 0; i < relTraList.length; i++) {
                var relTra = relTraList[i].value;
                if (relTra != 'undefined' && relTra != '') {
                    var v2Num = v2.val();
                    var relTraNum = parseFloat(v2Num) + parseFloat(relTra);
                    relTotList[i].value = relTraNum;
                }
            }
        } else if (isAgreement == '1') {
            //获取交易对手信息并且追加相关金额
            var relTraList = $("input[id^='tradeAmount']");
            var relTotList = $("input[id^='totalNotCleanTradeAmount']");
            for (var i = 0; i < relTraList.length; i++) {
                var relTra = relTraList[i].value;
                if (relTra != 'undefined' && relTra != '') {
                    var relTraNum = parseFloat(relTra);
                    relTotList[i].value = relTraNum;
                }
            }
        }
    }

    /*
        //v合同金额，v1实际交易金额，v2关联交易金额
        function copyAmount() {
            var v = $('#contractAmount');
            var v1 = $('#pracTradeAmount');
            var v2 = $('#relTradeAmount');
          /!*  if (v.val() == undefined ||v.val() == '') {
                alert("请输入合同金额！");
                return;
            }*!/
            if (v1.val() != undefined && v1.val().trim() != '') {
                v2.attr("value", v1.val());
            } else {
                v2.attr("value", v.val());
            }

            //如果是统一交易协议，则不累计
            var isAgreement = $('#isAgreement').val();
            if (isAgreement == '0') {
                //获取交易对手信息并且追加相关金额
                var relTraList = $("input[id^='tradeAmount']");
                var relTotList = $("input[id^='totalNotCleanTradeAmount']");
                for (var i = 0; i < relTraList.length; i++) {
                    var relTra = relTraList[i].value;
                    if (relTra != 'undefined' && relTra != '') {
                        var v2Num = v2.val();
                        var relTraNum = parseFloat(v2Num) + parseFloat(relTra);
                        relTotList[i].value = relTraNum;
                    }
                }
            } else if (isAgreement == '1') {
                //获取交易对手信息并且追加相关金额
                var relTraList = $("input[id^='tradeAmount']");
                var relTotList = $("input[id^='totalNotCleanTradeAmount']");
                for (var i = 0; i < relTraList.length; i++) {
                    var relTra = relTraList[i].value;
                    if (relTra != 'undefined' && relTra != '') {
                        var relTraNum = parseFloat(relTra);
                        relTotList[i].value = relTraNum;
                    }
                }
            }
        }
    */

    //contractNo 合同编号
    //tradeAgreementNumber  统一交易协议编号
    //isAgreement  是否为统一交易协议
    function copyNo() {
        var contractNo = $('#contractNo');
        var tradeAgreementNumber = $('#tradeAgreementNumber');
        var isAgreement = $('#isAgreement').val();
        if (isAgreement == '1') {
            if (contractNo.val() != undefined && contractNo.val().trim() != '') {
                tradeAgreementNumber.attr("value", contractNo.val());
            }
        }
    }

    function saveFiles() {
        /**
         * 保存附件
         * @type {*|jQuery}
         */
        var flag = false;
        var docInnerPids = getAllIdSelections();
        var transNum = $('#transNum').val();
        var docTableSelectItem = {
            'name': 'docTableSelectItem',
            'value': docInnerPids
        };
        var formData1 = $('#transNum').serializeAll();
        formData1.push(docTableSelectItem);
        formData1.push(transNum);
        $.ajaxSettings.async = false;
        $.post('${base}/ins/traService/saveFile', formData1, function (data) {
            if (data.success) {
                flag = true;
            } else {
                $.messager.alert('错误', data.msg, 'error');
                $("#save2").attr("disabled", false);
                flag = false;
            }
        })
        return flag;
    }

    $(function () {
        $('#currencyCode').change(function () {
            let val = $('#currencyCode').val();
            if (val == 'CNY' ) {
                $('.ratio font').remove();
            } else {
                $('.ratio font').remove();
                $('.ratio').html("<font color='red'>✶</font>" +  $('.ratio').html());
            }
        });
    });

    /**
     * 协议交易标志为是的时候，主协议合同编号应显示必填
     */
    function masterAgreementNumberSet() {
        let value = $('#protocolTransactionSymbol').val();
        console.log(value);
        if (value === '1') {
            $('.masterAgreementNumber').html("<font color='red'>✶</font>" +  $('.masterAgreementNumber').html());
            //设置为必填
            $("#masterAgreementNumber").attr("data-fv-notempty","true");
        } else {
            $('.masterAgreementNumber font').remove();
            //设置为非必填
            $("#masterAgreementNumber").removeAttr("data-fv-notempty");
        }
    }

    //页面加载设置合同状态
    $(function () {
        setContractStatus();
    })
    //监听业务结束日期变动
    $('input[ name = "busEndTime"]').on('change', function () {
        setContractStatus();
    });
    /**
     * 判定合同状态
     * @param dateString
     * @returns {boolean}
     */
    function setContractStatus() {
        let busEndTime = $('input[ name = "busEndTime"]').val();
        console.log(busEndTime);
        //如果结束日期为空，则合同状态生效
        if(busEndTime == '' || busEndTime == null || busEndTime == undefined) {
            //生效
            $('#contractStatus').val('1');
            $('#contractStatus').select2({value: '1'});
        } else {
            // 将输入的日期字符串转换为日期对象
            const inputDate = new Date(busEndTime);
            // 获取今天的日期，并将时间部分设置为 00:00:00
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            console.log(today);
            console.log(inputDate);
            if(inputDate >= today) {
                //生效
                $('#contractStatus').val('1');
                $('#contractStatus').select2({value: '1'});
            } else {
                //失效
                $('#contractStatus').val('2');
                $('#contractStatus').select2({value: '2'});
            }
        }
    }
</script>
</body>
</html>
