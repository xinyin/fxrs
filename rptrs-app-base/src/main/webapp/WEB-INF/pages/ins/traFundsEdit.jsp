<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title></title>
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

            <bf:hidden id="orgIsEmpty" name="orgIsEmpty" value="${orgIsEmpty}"/>
            <bf:hidden id="dataSources" name="dataSources" value="${traFunds.dataSources}"/>
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
                    <bf:formGroup col="12">
                        <div title="资金流水编号">
                            <bc:label cls="required" text="资金流水编号" col="2" formControl="true"/>
                        </div>
                        <bf:text id="fundsFlowNumber" name="fundsFlowNumber" col="7"  value="${traFunds.fundsFlowNumber}" disabled="${opt == 'view'}"
                                 placeholder=""><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="12">
                        <div title="交易概述">
                            <bc:label cls="required" text="交易概述" col="2" formControl="true"/>
                        </div>
                        <bf:text id="tranName" name="tranName" col="7" rows="3" multiple="true" value="${traFunds.tranName}" disabled="${opt == 'view'}"
                                 placeholder="复星保德信与（关联方名称） 签署《合同名称》，[  ]（简要描述交易内容），本次交易属于[ ]类关联交易，关联交易金额为[ ]万元"><bv:notEmpty/></bf:text>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>
            <bc:row cls="m-b">
                <bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
                    <bf:formGroup col="11">
                        <bc:label text="交易对手信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="1">
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
                                   value="${traFunds.protocolTransactionSymbol}" disabled="${opt == 'view'}"
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
                        <bf:text id="masterAgreementNumber" name="masterAgreementNumber" col="7" value="${traFunds.masterAgreementNumber}"  disabled="${opt == 'view'}">
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易币种" col="5" formControl="true"/>
                        <bf:select id="currencyCode" name="currencyCode" col="7"
                                   value="${traFunds.currencyCode}" disabled="${opt == 'view'}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-regCapitalCurrency">
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="ratio" text="原币交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="originalCurrencyAmount" name="originalCurrencyAmount" col="7"
                                 value="${traFunds.originalCurrencyAmount}"  disabled="${opt == 'view'}">
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
                                 value="${traFunds.relTradeAmount}"  disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                            <bv:stringLength max="21" message="字段长度最大为20位"/>
                        </bf:text>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label cls="pracTradeAmount" text="关联交易实际金额（万元）" col="5" formControl="true"/>
                        <bf:text id="pracTradeAmount" name="pracTradeAmount" col="7"
                                 value="${traFunds.pracTradeAmount}" >
                            <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                            <bv:stringLength max="21" message="字段长度最大为20位"/>
                        </bf:text>
                    </bf:formGroup>

                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div id="contractNo_Div" title="合同编号">
                            <bc:label cls="required" text="合同编号" col="5" formControl="true"/>
                            <bf:text id="contractNo" name="contractNo" col="7" value="${traFunds.contractNo}"  disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="合同签署日期">
                            <bc:label cls="tranDate" text="合同签署日期" col="5" formControl="true"/>
                        </div>
                        <bf:datebox id="tranDate" name="tranDate" col="7" value="${traFunds.tranDateString}">
                        </bf:datebox>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div id="busStartTime_Div" title="关联交易发生的起始日期">
                            <bc:label cls="busStartTime" text="业务起始日期" col="5" formControl="true"/>
                            <bf:datebox id="busStartTime" name="busStartTime" col="7"
                                        value="${traFunds.busStartTimeString}">
                            </bf:datebox>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="busEndTime" text="业务结束日期" col="5" formControl="true"/>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7"
                                    value="${traFunds.busEndTimeString}"/>
                        <c:if test="${sign}">
                            <div style="text-align: right; color: red;">如合同为长期有效或到期自动续签，合同终止日期请填 29991231</div>
                        </c:if>
                    </bf:formGroup>

                </bf:formGroup>
               <bf:formGroup hrp="bottom">
                   <bf:formGroup col="4">
                       <bc:label cls="required" text="合同状态" col="5" formControl="true"/>
                       <bf:select id="contractStatus" name="contractStatus" col="7"
                                  value="${traFunds.contractStatus}" disabled="${opt == 'view'}"
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
<%--                                   value="${traFunds.pricingPolicy}" disabled="${opt == 'view'}">--%>
<%--                            <bf:option text="请选择" value=""></bf:option>--%>
<%--                            <bf:option text="无单独交易价格（担保类）" value="无单独交易价格（担保类）"></bf:option>--%>
<%--                            <bf:option text="产品统一费率（保险类）" value="产品统一费率（保险类）"></bf:option>--%>
<%--                            <bf:option text="市场公允价格" value="市场公允价格"></bf:option>--%>
<%--                            <bf:option text="双方协商确定" value="双方协商确定"></bf:option>--%>
<%--&lt;%&ndash;                            <bf:option text="其他" value="其他"></bf:option>&ndash;%&gt;--%>
<%--                            <bv:notEmpty/>--%>
<%--                        </bf:select>--%>
<%--                    </bf:formGroup>--%>

                    <bf:formGroup col="4">
                        <bc:label cls="required" text="是否可免于审议" col="5" formControl="true"/>
                        <bf:select id="isconsiderSituation" name="isconsiderSituation" col="7"
                                   value="${traFunds.isconsiderSituation}" disabled="${opt == 'view'}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo">
                            <be:event type="select">
                                icsAlert();
                            </be:event>
                            <bv:notEmpty/>
                            <bf:option text="请选择" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4" id="considerSituationDiv">
                        <bc:label cls="required" text="免于审议情形" col="5" formControl="true"/>
                        <bf:select id="considerSituation" name="considerSituation" col="7"
                                   value="${traFunds.considerSituation}" disabled="${opt == 'view'}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerSituation">
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
               <%-- <bf:formGroup hrp="bottom">

                </bf:formGroup>--%>

                <bf:formGroup hrp="bottom">

                <bf:formGroup col="12">
                    <div title="交易价格">
                        <bc:label cls="required" text="交易价格" col="2" formControl="true"/>
                    </div>
                    <bf:text id="tradePrice" name="tradePrice" col="7" rows="3" multiple="true" value="${traFunds.tradePrice}" disabled="${opt == 'view'}"
                             placeholder="关联交易的交易单价。"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <div title="定价政策">
                        <bc:label cls="required" text="定价政策" col="2" formControl="true"/>
                    </div>
                    <bf:text id="pricingPolicy" name="pricingPolicy" col="7" rows="3" multiple="true"
                             value="${traFunds.pricingPolicy}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
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
                            <bc:label cls="required"  text="交易类型" col="5" formControl="true"/>
                            <bf:select id="tranType" name="tranType" col="7" value="${traFunds.tranType}"
                                       dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
                            </bf:select>
                        </div>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                            <bc:label cls="required" text="业务类型" col="5" formControl="true"/>
                            <bf:select id="busType" name="busType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-FundsType" disabled="${opt == 'view'}"
                                       includes="" value="${traFunds.busType}">
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
                    <bc:label text="投资方式" cls="required" col="5" formControl="true"/>
                    <bf:select id="investmentMethod" name="investmentMethod" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-FundsinvestmentMethod"
                               disabled="${opt == 'view'}"
                               includes="" value="${traFunds.investmentMethod}">
                        <bf:option text="请选择" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <bc:label cls="required" text="是否为境外投资" col="5" formControl="true"/>
                    <bf:select id="isOverseasinvestment" name="isOverseasinvestment" col="7"
                               value="${traFunds.isOverseasinvestment}" disabled="${opt == 'view'}"
                               dataSource="custom" url="sys/dictItem/getDictItems/BUS-isNo">
                        <bf:option text="请选择" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div id="financialAssstsNameDiv" title="金融产品名称">
                            <bc:label cls="required" text="金融产品名称" col="5" formControl="true"/>
                            <bf:text id="financialAssstsName" name="financialAssstsName" col="7"
                                     value="${traFunds.financialAssstsName}" disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div id="financialAssstsNumDiv" title="产品批准/注册/备案号（金融产品编号）">
                            <bc:label cls="required" text="产品批准/注册/备案号（金融产品编号）" col="5" formControl="true"/>
                            <bf:text id="financialAssstsNum" name="financialAssstsNum" col="7"
                                     value="${traFunds.financialAssstsNum}" disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </div>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="12">
                    <div id="financialProRatioDiv" title="投资金融产品份额占比（%)">
                        <bc:label text="金融产品份额占比" cls="required" col="5" formControl="true"/>
                        <bf:text id="financialProRatio" name="financialProRatio" col="7"
                                 value="${traFunds.financialProRatio}" disabled="${opt == 'view'}" placeholder="购买该金融产品的份额占该产品发行总额的比例。">
                            <bv:notEmpty/>
                        </bf:text>
                    </div>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="12">
                    <div title="交易标的">
                        <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                    </div>
                    <bf:text id="tradeSubject" name="tradeSubject" col="7" rows="3" multiple="true" value="${traFunds.tradeSubject}" disabled="${opt == 'view'}"
                             placeholder="合同当事人权利义务所共同指向的交易对象，比如具体的物、权利。"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <div title="其他事项说明">
                        <bc:label text="其他事项说明" col="3" formControl="true"/>
                    </div>
                    <bf:text id="remark" name="remark" col="7" rows="3" multiple="true" value="${traFunds.remark}"
                             disabled="${opt == 'view'}"> </bf:text>
                </bf:formGroup>
                </bf:formGroup>
                    <div id="5_1" class="Type5" style="display: none">
                    </div>
<%--                </bf:formGroup>--%>
            </bc:row>
            <bf:formGroup hrp="bottom">

            </bf:formGroup>


            <bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
                <bf:formGroup col="10">
                    <bc:label text="数据来源：" col="2" formControl="true" cls="text-style"/>
                </bf:formGroup>
                <bf:formGroup col="2">
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label  text="来源系统唯一编码" col="5" formControl="true"/>
                    <bf:text id="uniqueCode" name="uniqueCode" col="7" disabled="true"
                             value="${traFunds.uniqueCode}">
                    </bf:text>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <bc:label  text="来源系统" col="5" formControl="true"/>
                    <bf:text id="sourceSystem" name="sourceSystem" col="7" disabled="true"
                             value="${traFunds.sourceSystem}">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label  text="来源模块" col="5" formControl="true"/>
                    <bf:text id="sourceModule" name="sourceModule" col="7" disabled="true"
                             value="${traFunds.sourceModule}">
                    </bf:text>
                </bf:formGroup>

            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label  text="收付款标识" col="5" formControl="true"/>
                    <bf:select id="paymentId" name="paymentId" col="7" value="${traFunds.paymentId}"
                               dataSource="custom" url="sys/dictItem/getDictItems/INS-paymentId"
                               disabled="true">
                        <bf:option text="-" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <bc:label  text="场景" col="5" formControl="true"/>
                    <bf:text id="scene" name="scene" col="7" disabled="true"
                             value="${traFunds.scene}">
                    </bf:text>
                </bf:formGroup>

            </bf:formGroup>

            <%-- <bf:formGroup>
                 <bf:formGroup col="6">
                     <bc:label text="风险预警" col="3" formControl="true"/>
                     <div>
                      <bf:text id="limitHint" name="limitHint" col="9" rows="5" multiple="true" value="${traFunds.limitHint}" disabled="true"/>
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
                               value="${traFunds.singleRelatedTranType}" disabled="true">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <div id="majorTypeDiv" title="达到重大关联交易的情形">
                        <bc:label text="达到重大关联交易的情形" col="3" formControl="true"/>
                        <bf:select id="majorType" name="majorType" col="7" dataSource="custom"
                                   disabled="true"
                                   url="sys/dictItem/getDictItems/INS-majorType"
                                   includes="" value="${traFunds.majorType}">
                            <bf:option text="无" value="0"></bf:option>
                        </bf:select>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="审议类型" col="3" formControl="true"/>
                    <bf:select id="considerType" name="considerType" col="7" value="${traFunds.considerType}"
                               disabled="true"
                               dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="是否需逐笔报告" col="3" formControl="true"/>
                    <bf:select id="eachReport" name="eachReport" col="7" value="${traFunds.eachReport}"
                               disabled="true"
                               dataSource="custom" url="sys/dictItem/getDictItems/INS-FundsEachReport">
                        <bf:option text="无" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="12">
                    <bc:label text="是否需逐笔披露" col="3" formControl="true"/>
                    <bf:select id="eachDisclosure" name="eachDisclosure" col="7" value="${traFunds.eachDisclosure}"
                               disabled="true"
                               dataSource="custom" url="sys/dictItem/getDictItems/INS-FundsEachDisclosure">
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
                               value="${traFunds.isCollectRelated}"
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
<%--            <div id="tableDiv" style="width:95%;margin:0 auto">--%>
<%--                <label style="color: red; font : 6px">1.新增或编辑交易时，附件需上传关联交易价格公允性说明（所有交易）、重大关联交易及统一交易协议还需上传议案、关联交易分类监管报表等材料；</label>--%>
<%--                <label style="color: red; font : 6px">2.合同签署之后，在首页->关联交易管理->“附件补充”按键处上传已签署的合同。</label>--%>
<%--                <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"--%>
<%--                          selectFirst="true" queryUrl="queryList?transNum=${traFunds.transNum}">--%>
<%--                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>--%>
<%--                    <bc:toolbar id="downFile" text="下载" type="button" handler="downFileOnclick"/>--%>
<%--                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>--%>
<%--                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>--%>
<%--                    <bc:column field="pid" title="主键" width="100" visible="false"/>--%>
<%--                    <bc:column field="relatedName" title="文件名" width="100"/>--%>
<%--                    <bc:column field="fileType" title="文件类型" width="100"/>--%>
<%--                    <bc:column field="fileSize" title="大小" width="100"/>--%>
<%--                    <bc:column field="uploadOrg" title="上传机构" width="100"--%>
<%--                               formatter="custom:sys/unit/selectUnit"/>--%>
<%--                    <bc:column field="uploadDept" title="上传部门" width="100"/>--%>
<%--                    <bc:column field="uploadTime" title="上传时间" width="100"/>--%>
<%--                </bc:table>--%>
<%--            </div>--%>

        </bf:form>
        <div id="tableDiv" style="width:95%;margin:0 auto">
            <bf:form id="queryForm" validation="true">
                <bf:formGroup col="4" hidden="true">
                    <bf:text name="isConsider" col="7" placeholder="name"/>
                </bf:formGroup>
            </bf:form>
            <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                      selectFirst="false" queryUrl="queryListDict?transNum=${traFunds.transNum}&type=fund" pageSize="20">
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
                <bc:column field="belongOrg" title="上传部门" width="100"/>
                <bc:column field="uploadTime" title="上传时间" width="100"/>
                <bc:column field="fileType" title="文件类型" width="100" visible="false"/>
            </bc:table>
        </div>
        <bf:formGroup>
            <div class="col-sm-12 col-sm-offset-5">
                <bc:button type="danger" text="保存" icon="save" id="save2">
                    <be:event>
                        save();
                    </be:event>
                </bc:button>
                <bc:button type="primary" text="返回" icon="arrow-circle-left" id="return">
                    <be:event>
                        <%--                            window.location = '${base}/ins/traFunds/list';--%>
                        window.history.back(-1);
                    </be:event>
                </bc:button>
            </div>
        </bf:formGroup>
    </bc:ibox>
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
    var contractNo = $('#contractNo').val();

    //记录页面具体添加个数，按照添加 删除按钮 动态变化
    var countTmp = 0;

    var considerName;
    function considenr(considerType){
        //var considerType = $("considerType").val();
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
        var considerType = '${traFunds.considerType}';
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

    function icsAlert(){
        $.messager.alert('提示', '请慎重修改，如有疑问，请联系法律合规部关联交易管理专员!', 'warning');
    }

    /*    debugger;
        //业务类型为金融产品时弹出金融产品信息
        function busType(value) {
            var value = value
           /!* var financialAssstsName =$('#financialAssstsName').val();
            var financialAssstsNum =$('#financialAssstsNum').val();
            var financialProRatio =$('#financialProRatio').val();*!/
            if (value == '04') {
                $('#financialAssstsName').val();
                $('#financialAssstsNum').val();
                $('#financialProRatio').val();
                $('#financialAssstsName').css("display", "none");
                $('#financialAssstsNum').css("display", "none");
                $('#financialProRatio').css("display", "none");
                $('#financialAssstsName label font').remove();
                $('#financialAssstsNum label font').remove();
                $('#financialProRatio label font ').remove();

            } else  {
                $('#financialAssstsName').css("display", "block");
                $('#financialAssstsNum').css("display", "block");
                $('#financialProRatio').css("display", "block");
                if ($('#financialAssstsName label font').length == 0) {
                    $('#financialAssstsNum label').prepend("<font color='red'>✶</font>");
                    $('#financialProRatio label').prepend("<font color='red'>✶</font>");
                }
            }

        }*/
    //业务类型为金融产品时弹出金融产品信息
    $("#busType").change(function () {
        var busType = $("#busType").val();
        if ("04" == busType) {
            $("#financialAssstsNameDiv").show();
            $("#financialAssstsNumDiv").show();
            $("#financialProRatioDiv").show();
        } else {
            $("#financialAssstsNameDiv").hide();
            $("#financialAssstsNumDiv").hide();
            $("#financialProRatioDiv").hide();
        }
    });
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

    $("#isconsiderSituation").click(function () {
        var isconsiderSituation = $("#isconsiderSituation").val();
        if ("1" == isconsiderSituation) {
            $("#considerSituationDiv").show();
        } else {
            $("#considerSituationDiv").hide();
        }
    });
    /*
        function HoldingSubsidiarySub(value) {
            var value = value
            if (value == '1') {
                $('#holdingSubsidiaryNumber').val("");
                $('#holdingSubsidiaryName').val("");
                $('#holdingSubsidiaryType').val("");
                $('#holdingSubsidiaryNumberDiv').css('display', 'none');
                $('#holdingSubsidiaryNameDiv').css('display', 'none');
                $('#holdingSubsidiaryNumberDiv label font').remove();
                $('#holdingSubsidiaryNameDiv label font ').remove();
            }
            if (value == '0') {
                $('#holdingSubsidiaryNumberDiv').css('display', 'block')
                $('#holdingSubsidiaryNameDiv').css('display', 'block')
                if ($('#holdingSubsidiaryNumberDiv label font').length == 0) {
                    $('#holdingSubsidiaryNumberDiv label').prepend("<font color='red'>✶</font>");
                    $('#holdingSubsidiaryNameDiv label').prepend("<font color='red'>✶</font>");
                }
            }
        };
    */

    /** 审议类型
     * 1.逐笔审议：根据业务规则判定为重大关联交易和统一交易协议的关联交易；
     * 2.一般审议：根据业务规则与关联自然人单笔交易金额超过50万元（含50万）但未达到重大关联交易标准或与关联法人或其他组织超过500万元（含500万）但未达到重大关联交易标准的交易；
     * 3.免予审议：根据以下业务规则判定为免予审议交易：
     * 1)与关联自然人单笔交易额在 50 万元以下或与关联法人单笔交易额在 500 万元以下的关联交易；
     * 2)一方以现金认购另一方公开发行的股票、公司债券或企业债券、可转换债券或其他衍生品种；
     * 3)同一自然人同时担任银行保险机构和其他法人的独立董事且不存在其他构成关联方情形的，该法人与银行保险机构进行的交易；
     * 4)活期存款业务；
     * 5)交易的定价为国家规定的；
     * 6)银保监会认可的其他情形。
     * */
    function isConsiderType() {
        //是否为重大关联交易
        var isMajorTrade = $('#majorTrade').val();
        //合同金额
        var relTradeAmount = $('#relTradeAmount').val();
        //关联方类型
        var relType = $('#relType1').val();

        //如果是重大关联交易审议类型为逐笔审议
        if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != '') {
            if (isMajorTrade == "1") {
                $('#considerType').val("3");
                $('#considerType').select2({value: "3"});
                addRedStar();
                return;
            } else {
                //当关联方为个人合同金额<50w  机构合同金额<500w的时候免于审议
                //免于审议时需要给是否可免于审议和免于审议情形字段赋值
                if (relTradeAmount != null && relTradeAmount != undefined && relTradeAmount != ''
                    && relType != null && relType != undefined && relType != '') {
                    if (relType == "PSN" && relTradeAmount < 50) {
                        $('#considerType').val("1");
                        $('#considerType').select2({value: "1"});
                        $('#isconsiderSituation').val("1");
                        $('#isconsiderSituation').select2({value: "1"});
                        $('#considerSituation').val("4");
                        $('#considerSituation').select2({value: "4"});

                        return;
                    }
                    if (relType == "ORG" && relTradeAmount < 500) {
                        $('#considerType').val("1");
                        $('#considerType').select2({value: "1"});
                        $('#isconsiderSituation').val("1");
                        $('#isconsiderSituation').select2({value: "1"});
                        $('#considerSituation').val("4");
                        $('#considerSituation').select2({value: "4"});
                        // reRedStar();
                        return;
                    }
                }
            }
        }

        $('#considerType').val("2");
        $('#considerType').select2({value: "2"});
        // reRedStar();
    }

    function HoldingSubsidiarySub(value) {
        var value = value
        if (value == '0') {
            $('#holdingSubsidiaryNumber').val("");
            $('#holdingSubsidiaryName').val("");
            $('#holdingSubsidiaryType').val("");
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
    }


    var opt = '${opt}';
    var sendOpt = '${sendOpt}';
    var unitName = '${unitName}';
    var busType = '${traFunds.investmentType}';
    var underInvolveRel = '${traFunds.underInvolveRel}';
    var addNew = '${considerType}';
    var approve = '${approve}';
    $(document).ready(function () {

        changeNext(busType);
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });

        var opt = $("#opt").val();
        var dataSources = $("#dataSources").val();
        if(opt === 'edit'){
            if(dataSources !== '1'){
                $("#contractNo").attr('disabled',true)
            }
        }

        // 动态添加 按钮 自动触发
        <%--var relList = ${traFunds.relInfoListStr};--%>
        var relId =  '${traFunds.relId}';
        var relNumber = '${traFunds.relNumber}';
        var relType =  '${traFunds.relType}';
        var relName = '${traFunds.relName}';
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
        if (busType == '01') {
            $("#stockrightType_Div").css("display", "block");
        } else {
            $("#stockrightType_Div").css("display", "none");
        }
        if (underInvolveRel == '1') {
            $('#underInvolveRelName_04_Div').css("display", "none");
        } else {
            $('#underInvolveRelName_04_Div').css("display", "block");
        }

        if (unitName != null && unitName != '' && unitName != undefined) {
            $("#managerOrg").val(unitName);
        }
        // alert(opt);
        if (opt == 'view') {
            if ('${isConfirm}' != 'true' && '${sign}' != 'true') {
                $("[name='tranDate']").attr("disabled", true);
                $("[name='busStartTime']").attr("disabled", true);
                $("[name='busEndTime']").attr("disabled", true);
                $("[name='pracTradeAmount']").attr("disabled", true);
            }
            if ('${sign}' == 'true') {
                $('.pracTradeAmount').html("<font color='red'>✶</font>" +  $('.pracTradeAmount').html());
                $('.tranDate').html("<font color='red'>✶</font>" +  $('.tranDate').html());
                $('.busStartTime').html("<font color='red'>✶</font>" +  $('.busStartTime').html());
                $('.busEndTime').html("<font color='red'>✶</font>" +  $('.busEndTime').html());
                //设置为必填
                $("#pracTradeAmount").attr("data-fv-notempty","true");
                $("#tranDate").attr("data-fv-notempty","true");
                $("#busStartTime").attr("data-fv-notempty","true");
                $("#busEndTime").attr("data-fv-notempty","true");
            }
            $("#save2").css("display", "none");
            if(sendOpt == 'view'){
                $("#return").css("display", "none");
            }
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
            if ($('#isHoldingSubsidiary').val() == '0') {
                HoldingSubsidiarySub('0');
            }
            var file = '${file}';
            if(file ==null || sendOpt == 'view'){
                $("#docTable_tb_exportIn").css("display", "none");
                $("#docTable_tb_removeDoc").css("display", "none");
            }
            $("#confirmSubmit").css("display", "inline-block");
        } else {
            var val = $('#isHoldingSubsidiary').val();
            if (val != null && val != undefined && val != '') {
                HoldingSubsidiarySub(val)
            }
            /*if (opt === 'edit') {
                var dataSources = $("#dataSources").val();
                if (dataSources == '2') {
                    $("#contractNo").attr('disabled', true);
                    $("#relTradeAmount").attr('disabled', true);
                    $("[name='tranDate']").attr("disabled", true);
                    $("[name='busStartTime']").attr("disabled", true);
                    $("#tradeSubject").attr('disabled', true);
                } else if (dataSources == '4') {
                    $("#contractNo").attr('disabled', true);
                    $("#relTradeAmount").attr('disabled', true);
                    $("#tranName").attr('disabled', true);
                } else if (dataSources == '5') {
                    $("#contractNo").attr('disabled', true);
                    $("#relTradeAmount").attr('disabled', true);
                    $("[name='tranDate']").attr("disabled", true);
                    $("[name='busStartTime']").attr("disabled", true);
                    // $("[name='tranTarget']").attr("disabled", true);
                    $("#tranName").attr('disabled', true);
                    $("#tradeSubject").attr('disabled', true);
                } else if (dataSources == '7') {
                    $("[name='busType']").attr("disabled", true);
                    $("#contractNo").attr('disabled', true);
                    $("#relTradeAmount").attr('disabled', true);
                    $("#tradePrice").attr('disabled', true);
                    $("[name='pricingPolicy']").attr("disabled", true);
                    $("#tranName").attr('disabled', true);
                    $("[name='isHoldingSubsidiary']").attr("disabled", true);
                    if ($('#isHoldingSubsidiary').val() == '1') {
                        $("#holdingSubsidiaryName").attr('disabled', true);
                        $("#holdingSubsidiaryNumber").attr('disabled', true);
                    }
                    $("[name='isconsiderSituation']").attr("disabled", true);
                    if ($("#isconsiderSituation").val() == '1') {
                        $("[name='considerSituation']").attr("disabled", true);
                    }
                    $("#tradeSubject").attr('disabled', true);
                    $("[name='isOverseasinvestment']").attr("disabled", true);//
                    $("[name='investmentMethod']").attr("disabled", true);//
                }
            }*/
        }
        if (approve != null && approve != '' && approve != undefined) {
            $("#save2").css("display", "none");
            $("#confirmSubmit").css("display", "inline-block");
            $("#docTable_tb_exportIn").css("display", "none");
            $("#docTable_tb_removeDoc").css("display", "none");
        } else {
            $("#confirmSubmit").css("display", "none");
        }
        if ('${isConfirm}' == 'true') {
            $("#save2").css("display", "inline-block");
            // $("#docTable_tb_exportIn").css("display", "none");
            // $("#docTable_tb_removeDoc").css("display", "none");
        }
        if ('${upload}' == 'true') {
            $("#docTable_tb_exportIn").css("display", "");
            $("#docTable_tb_removeDoc").css("display", "");
            $("#save2").css("display", "inline-block");
        }
        $('#tranType').val("1");
        $('#tranType').select2({value: "1"});
        var investmentType = $('#investmentType').val();
        // var count = 1;
        for (var i = 1; i < 6; i++) {
            if (investmentType == '0' + i) {
                $('.Type' + i).show();
            }
        }
        //上传文件格式显示
        considenr1()
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
        $.messager.modal({
            winId: 'relNameSelect',
            width: 700,
            height: 420,
            title: '交易对手信息',
            url: '${base}/ins/traInvestment/relNameSelect/' + traSubName + '/' + '1',
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
                checkIsMajorTran();
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
            $("#stockrightType_Div").css("display", "block");
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
            $("#stockrightType_Div").css("display", "none");
            $("#equityLastTradePrice_Div").css("display", "block");
            $("#imSubjectMatter_Div").css("display", "block");
            $("#investedEnterprises_Div").css("display", "none");
        }
        if (val == '03') {
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "block");
            $(".Type4").css("display", "none");
            $(".Type5").css("display", "none");
            $("#stockrightType_Div").css("display", "none");
            $("#equityLastTradePrice_Div").css("display", "none");
            $("#imSubjectMatter_Div").css("display", "block");
            $("#depositType_Div").css("display", "block");
            $("#investedEnterprises_Div").css("display", "none");
        }
        if (val == '04') {
            $(".Type2").css("display", "none");
            $(".Type3").css("display", "none");
            $(".Type4").css("display", "block");
            $("#underInvolveRelName_04_Div").css("display", "none");
            $(".Type5").css("display", "none");
            $("#stockrightType_Div").css("display", "none");
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
            $("#stockrightType_Div").css("display", "none");
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
            $("#stockrightType_Div").css("display", "none");
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
            $.messager.alert('提示', "请选中一个文件!", 'error');
            return;
        }
        if (pid.length > 1) {
            $.messager.alert('提示', "只能选中一个文件!", 'error');
            return;
        }
        //获得该条数据
        var row = getselectData();
        console.log(row)
        var fileName = row[0].relatedName;
        if(row[0].fileType !== 'pdf'){
            $.messager.alert('提示', "只支持查看pdf格式，doc、zip文件需下载查看！", 'error');
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/ins/traInsenclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }

    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            $.messager.alert('错误', "请先选中数据!", 'error');
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/ins/traInsenclosure/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    $.messager.alert('提示', res.msg, 'info');
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
        if(obj.files.length === 0){
            $.messager.alert('提示','请选择要上传的文件','error')
            return;
        }
        // for (var i = 0; i < obj.files.length; i++) {
        //     var temp = obj.files[i].name;
        //     var temArr = temp.split('.');
        //     var fileLast = temArr[temArr.length - 1];
        //     if (fileLast != 'pdf' && fileLast != 'zip') {
        //         $.messager.alert('提示', "请上传pdf或zip文件", 'error');
        //         return;
        //     }
        // }

        var selectRows = getselectData();//选中的数据
        if (selectRows.length != 1) {
            alert("请先选中一条数据");
            return;
        }
        var typeCode = selectRows[0].typeCode
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/ins/traInsenclosure/uploadFilesList?businessType=1&typeCode='+typeCode, //上传文件的请求路径必须是绝对路劲
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
                $.messager.alert("上传失败,请重试");
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
                    $.messager.alert(data.msg);
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
        var relId = "relId" + count;
        var relType = "relType" + count;
        var pid = "pid" + count;
        var sourceSystem = '${traFunds.sourceSystem}';

        //formItems
        var itemStr = ' <div class="form-group " id="' + item + '" >\n' +
            '   <div class="form-group " hidden="true">\n' +
            '       <div class="form-group  col-xs-6">\n' +
            '           <label  class="control-label col-xs-4 "><font color="red">✶</font>交易对手名称</label>\n' +
            '           <div class="  col-xs-8">\n' +
            '               <input type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + tranOpponent + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.tranOpponent;
        }//onblur="initRelInfo(\'' + relNumber + '\',\'' + relName + '\',\'' + totolTradeAmount + '\',\'' + relType + '\',\'' + assetsLastQuarter + '\')"

        itemStr = itemStr + '">\n' +
            '<input   type="hidden" data-fv-notempty="true"  class="jep-input form-control"  id="' + certType + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.certType;
        }
        if(sourceSystem !='' && sourceSystem !=null){
            itemStr = itemStr + '">\n' +
                '           </div>\n' +
                '       </div>\n' +
                '       <div class="form-group  col-xs-1"></div>\n' +
                '   </div>\n' +
                '   <div class="form-group ">\n' +
                '       <div class="form-group  col-xs-4">\n' +
                '           <label  class="control-label col-xs-5"> <font color="red">✶</font> 关联方名称</label>\n' +
                '           <div class="  col-xs-7">\n' +
                '           <input disabled="true" type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + relName + '" value = "';
        }else {
            itemStr = itemStr + '">\n' +
                '           </div>\n' +
                '       </div>\n' +
                '       <div class="form-group  col-xs-1"></div>\n' +
                '   </div>\n' +
                '   <div class="form-group ">\n' +
                '       <div class="form-group  col-xs-4">\n' +
                '           <label  class="control-label col-xs-5"> <font color="red">✶</font> 关联方名称</label>\n' +
                '           <div class="  col-xs-7">\n' +
                '           <input type="text" onclick="relNameSelect(' + relType + ',' + relId + ',' + relName + ',' + totolTradeAmount + ',' + relNumber + ',' + certTypes + ',' + certType + ',' + tradeAmount + ')" class="jep-input form-control " id="' + relName + '" value = "';
        }
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relName;
        }
        itemStr = itemStr + '">\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-4">\n' +
            '              <label  class="control-label col-xs-5 " title="关联方证件号码"><font color="red">✶</font>关联方证件号码</label>\n' +
            '              <div class="  col-xs-7">\n' +
            '                   <input disabled="true" type="text" data-fv-notempty="true"  class="jep-input form-control" id="' + relNumber + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relNumber;
        }
        itemStr = itemStr + '"  >\n' +
            '           </div>\n' +
            '       </div>\n' +
            /*    '        <div class="form-group  col-xs-1">\n' +
                '           <div class=" btn-danger1" onclick="delItme(\'' + item + '\')">删除</div>\n' +
                '        </div>\n' +*/
            '   </div>\n' +
            '       <div class="form-group  col-xs-5" hidden="true">\n' +
            '           <label  class="control-label col-xs-4">关联方证件类型</label>\n' +
            '           <div class="col-xs-6">\n' +
            '               <input disabled="true"  type="text" data-fv-notempty="true"  class="jep-input form-control"  id="' + certTypes + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
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

        itemStr = itemStr + '"  >\n' +
            '           </div>\n' +
            '       </div>\n' +
            '   </div>\n' +
            '       <div class="form-group  col-xs-4">\n' +
            '           <label  class="control-label col-xs-5"><font color="red">✶</font>关联方类型</label>\n' +
            '           <div class="col-xs-7">\n' +
            '               <input disabled="true"  type="text" data-fv-notempty="true"  class="jep-input form-control"  id="' + relType + '" value = "';
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

        itemStr = itemStr + '">\n' +
            '               </div>\n' +
            '        </div>\n' +

            '   <div class="form-group ">\n' +
            '        <div class="form-group  col-xs-4" hidden="true">\n' +
            '             <label  class="control-label col-xs-5" title="本年度与该关联方累计交易金额(万元)">本年度与该关联方累计交易金额(万元)</label>\n' +
            '             <div class="  col-xs-7">\n' +
            '                   <input type="text" disabled="disabled" class="jep-input form-control" id="' + totolTradeAmount + '" value="';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.totolTradeAmount;
        }
        itemStr = itemStr + '">\n' +
            '             </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-4" hidden="true">\n' +
            '            <label  class="control-label col-xs-5" title="与该关联方的交易金额(万元,请保留6位小数)">累计实际交易金额</label>\n' +
            '            <div class="  col-xs-7">\n' +
            '                  <input disabled="true" type="text" disabled="disabled" class="jep-input form-control" id="' + tradeAmount + '" value ="';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.tradeAmount;
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
            '       <input type="hidden" class="form-control" id="' + relType + '" value="'
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

    function delItme(item) {
        if (countTmp == 1) {
            $.messager.alert('提示', "请勿全部删除", 'info');
            return
        }
        $("#" + item).remove()
        countTmp--;
    }

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

    //保存
    function checkSaves() {
        debugger

        $("#tranName").val($("#tranName").val().replace(/[\r\n]/g, ""));//交易概述
        $("#tradePrice").val($("#tradePrice").val().replace(/[\r\n]/g, ""));//交易价格
        $("#pricingPolicy").val($("#pricingPolicy").val().replace(/[\r\n]/g, ""));//定价政策
        $("#tradeSubject").val($("#tradeSubject").val().replace(/[\r\n]/g, ""));//交易标的
        $("#remark").val($("#remark").val().replace(/[\r\n]/g, ""));//其他事项说明
        $("#financialAssstsName").val($("#financialAssstsName").val().replace(/[\r\n]/g, ""));//金融产品名称
        $("#financialAssstsNum").val($("#financialAssstsNum").val().replace(/[\r\n]/g, ""));//产品批准/注册/备案号
        $("#financialProRatio").val($("#financialProRatio").val().replace(/[\r\n]/g, ""));//金融产品份额占比

        var holdName = $('#holdingSubsidiaryName').val();
        var holdNum = $('#holdingSubsidiaryNumber').val();

        // $("#save2").attr("disabled", true);
        if ($('#isHoldingSubsidiary').val() == '1' && (holdName == '' || holdNum == '')) {
            $.messager.alert('错误', "是控股子公司，控股子公司全称、证件号码为必填项", 'error');
            return;
        }
        if ('${isConfirm}' == 'true') {
            if ($("#file").val() == ' ' || $("#file").val() == '' || $("#file").val() == null) {
                $.messager.alert('错误', "待确认状态时需要上传文件", 'error');
                return;
            }
        }
        $("#save2").attr("disabled", true);
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
                    $("#limitHint").val('');
                    $('#ruleResult').val('');
                    var formData = $('#editForm').serializeAll();
                    formData.push(docTableSelectItem);
                    formData.push(relInfoDatas)
                    $.mask.post('${base}/ins/traFunds/save', formData, function (data) {
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
                    $('#ruleResult').val('');
                    var limitHint = data.msg;
                    var code = data.code;
                    if (limitHint != null || limitHint != undefined || limitHint != "") {
                        $("#limitHint").val(limitHint);
                    }
                    $('#ruleResult').val(code);
                    var formData = $('#editForm').serializeAll();
                    formData.push(docTableSelectItem);
                    formData.push(relInfoDatas)
                    $.messager.confirm('提示', data.msg + ',是否保存数据？', function (r) {
                        debugger;
                        if (r) {
                            /!*取出限额字段*!/
                            // var limitHint = data.limitHint;
                            // var limitHint = data.msg;
                            $.mask.post('${base}/ins/traFunds/save', formData, function (data) {
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
                            $("#save2").attr("disabled", false);
                        }
                    });
                }
            });
        } else {
            $.messager.alert('错误', data.msg, 'error');
            // $("#save2").attr("disabled", false);
        }
    }


    //判断底层基础资产是否涉及控股股东或控股股东的关联方--控制--底层资产涉及的关联方名称是否 必填
    function underInvolveRel(value) {
        debugger;
        var value = value
        if (value == '1') {
            $('#underInvolveRelName').val("");
            $('#underInvolveRelName_04_Div').css("display", "none");
            $('#underInvolveRelName_04_Div label font').remove();
        }
        if (value == '0') {
            $('#underInvolveRelName_04_Div').css("display", "block");
            if ($('#underInvolveRelName_04_Div label font').length == 0) {
                $('#underInvolveRelName_04_Div label').prepend("<font color='red'>✶</font>");
            }
        }
    }

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

    var nextApprovalPeople;


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
        var no = year + month + day + hour + minutes + seconds;
        return no + (Math.round(Math.random() * 89 + 100)).toString();
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


    /**
     * 保存数据
     */
    function save() {
        var holdName = $('#holdingSubsidiaryName').val();
        var holdNum = $('#holdingSubsidiaryNumber').val();
        var relType = $("#relType1").val();
        var relName = $("#relName1").val();
        var relNumber = $("#relNumber1").val();
        var relId = $("#relId1").val();
        if(null == relName){
            $.messager.alert('错误', "请选择交易对手！", 'error');
            return;
        }
        if("自然人" == relType){
            relType = "PSN";
        }else if("金融产品" == relType){
            relType = "PRO";
        }else {
            relType = "ORG"
        }

        $("#relType").val(relType);

        if('${upload}' == 'true'){
            var filesList = getData();
            if (filesList.length === 0) {
                $.messager.alert('错误', "附件补充时需要上传文件！", 'error');
                return;
            }
            if(saveFiles()){
                $.messager.alert('提示', "上传附件成功", 'info', function () {
                    window.location = '${base}/ins/traFunds/list';
                });
                return;
            }else {
                return;
            }
        }

        if ('${sign}' == 'true') {
            if ($("[name='tranDate']").val() == '' || $("[name='tranDate']").val() == null) {
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

        $("#tranName").val($("#tranName").val().replace(/[\r\n]/g, ""));//交易概述
        $("#tradePrice").val($("#tradePrice").val().replace(/[\r\n]/g, ""));//交易价格
        $("#pricingPolicy").val($("#pricingPolicy").val().replace(/[\r\n]/g, ""));//定价政策
        $("#tradeSubject").val($("#tradeSubject").val().replace(/[\r\n]/g, ""));//交易标的
        $("#remark").val($("#remark").val().replace(/[\r\n]/g, ""));//其他事项说明

        var docInnerPids = getAllIdSelections();
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            // var flag = checkContractNo();
            // if (flag) {
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
                    "totolTradeAmount": "",
                    "tranOpponent": "",
                    "certType": "",
                    // "assetsLastQuarter":"",
                    "tradeAmount": "",
                    "pid": ""
                }

                relItemInfo.relId = relId;
                relItemInfo.relNumber = relNumber;
                relItemInfo.relType = relType;
                relItemInfo.relName = relName;
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
            console.log("---" + formData1)
            $.mask.post('${base}/ins/traFunds/save', formData1, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        window.location = '${base}/ins/traFunds/list';
                    });
                } else {
                    // $("#save2").attr("disabled", false);
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        // }
        }
    }

    /**
     * 获取通过链接访问是的参数
     * @param variable
     * @returns {string|boolean}
     */
    function getQueryVariable(variable)
    {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
        return(false);
    }

    $("#isconsiderSituation").change(function () {
        checkIsMajorTran();
    })
    $("#relTradeAmount").change(function () {
        checkIsMajorTran();
       // considenr();
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
        var isAgreement = "";
        /* 统一交易类型 */
        var tradeAgreementType =  "";
        /* 关联交易金额 */
        var relTradeAmount = $("#relTradeAmount").val();
        if (isNotEmpty(relType) && isNotEmpty(relNumber) && isNotEmpty(relTradeAmount)) {
            if("自然人" === relType){
                relType = "PSN";
            }else if("金融产品" == relType){
                relType = "PRO";
            }else {
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
                    "tradeAgreementType" : tradeAgreementType
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
                        if(null != isconsiderSituation && isconsiderSituation != ""){
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
                        // considerType 不是免于审议时的资金御用类时；是否需逐笔报告-是；是否需逐笔披露-是
                        if ("1" != considerType) {
                            $("#eachReport").val("1");
                            $('#eachReport').select2({value: "1"});
                            $("#eachDisclosure").val("1");
                            $('#eachDisclosure').select2({value: "1"});
                        } else {
                            $("#eachReport").val(eachReport);
                            $('#eachReport').select2({value: eachReport});
                            $("#eachDisclosure").val(eachDisclosure);
                            $('#eachDisclosure').select2({value: eachDisclosure});
                        }
                    }else {
                        $.messager.alert('提示', data.msg , 'error');
                    }
                }
            });
        }
    }

    //判断字符是否为空的方法
    function isNotEmpty(obj){
        if(typeof obj == "undefined" || obj == null || obj == ""){
            return false;
        }else{
            return true;
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

    function checkContractNo() {
        /**
         * 校验合同编号是否存在
         * @type {*|jQuery}
         */

        var flag = false;
        var contractNoNew = $("#contractNo").val();
        if (contractNoNew == contractNo) {
            flag = true;
        } else {
            $.ajax({
                type: 'GET',
                url: '${base}/ins/traFunds/checkContractNo?contractNo=' + contractNoNew,
                dataType: 'json',
                async: false,
                delay: 200,
                success: function (data) {
                    if (data.success) {
                        flag = true;
                    } else {
                        $.messager.alert('提示', "合同编号已存在，请确认！", 'error');
                        flag = false;
                    }
                }
            });
        }
        return flag;
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
