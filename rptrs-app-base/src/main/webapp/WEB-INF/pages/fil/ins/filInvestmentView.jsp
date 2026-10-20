<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表（保险）-投资入股类</title>
    <style>
        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        textarea {
            resize: none; /* Disables resizability */
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
    <bc:ibox id="traInvestmentBox" title="" subTitle="" col="12" collapsible="true">
    <bf:form id="editForm" method="post" validation="true">
    <bf:hidden id="opt" name="opt" value="${opt}"/>
    <bf:hidden id="relId" name="relId" value="${traInvestment.relId}"/>
    <bf:hidden id="relNumber" name="relNumber" value="${traInvestment.relNumber}"/>
    <bf:hidden id="relType" name="relType" value="${traInvestment.relType}"/>
    <bf:hidden id="pid" name="pid" value="${traInvestment.pid}"/>
    <bf:hidden id="ruleResult" name="ruleResult" value=""/>
    <bf:hidden id="total" name="total" value="${traInvestment.totolTradeAmount}"/>
    <bf:hidden id="amount" name="amount" value="${traInvestment.contractAmount}"/>
    <bf:hidden id="transNum" name="transNum" value="${traInvestment.transNum}"/>
    <bf:hidden id="tradeZeroAmount" name="tradeZeroAmount" value="${traInvestment.tradeZeroAmount}"/>
    <bf:hidden id="status" name="status" value="${traInvestment.status}"/>
        <%--未达到累积关联重大的关联方relNumbers,多个关联方使用，隔开--%>
    <bf:hidden id="notMajorRelNumber" name="notMajorRelNumber" value="${traInvestment.notMajorRelNumber}"/>
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
            <bf:text id="traSubName" name="traSubName" col="7" disabled="true" value="复星保德信人寿保险有限公司">
            </bf:text>
        </bf:formGroup>
        <bf:formGroup col="4">
            <bc:label text="管理机构" col="5" formControl="true"/>
            <bf:text id="managerOrg" name="managerOrg" col="7"
                     value="${traInvestment.managerOrg}"
                     disabled="true">
            </bf:text>
        </bf:formGroup>
    </bf:formGroup>
    <bf:formGroup hrp="bottom">
        <bf:formGroup col="12">
            <bc:label cls="required" text="交易名称" col="5" formControl="true"/>
            <bf:text id="tranName" name="tranName" col="10" click=""
                     value="${traInvestment.tranName}" disabled="${opt == 'view'}">
            </bf:text>
        </bf:formGroup>
    </bf:formGroup>
    <bf:formGroup hrp="bottom">
        <bf:formGroup col="4">
            <bc:label cls="required" text="发起方是否为控股子公司" col="5" formControl="true"/>
            <bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
                       value="${traInvestment.isHoldingSubsidiary}"
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
    <div id="holdings">
        <bf:formGroup col="4">
            <div id="holdingSubsidiaryNameDiv">
                <bc:label cls="required" text="发起方公司全称" col="5" formControl="true"/>
                <bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
                         value="${traInvestment.holdingSubsidiaryName}">
                </bf:text>
            </div>
        </bf:formGroup>
        <bf:formGroup col="4">
            <div id="holdingSubsidiaryNumberDiv">
                <bc:label cls="required" text="发起方证件号码" col="5" formControl="true"/>
                <bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
                         value="${traInvestment.holdingSubsidiaryNumber}">

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

            <%--                <bf:formGroup hrp="bottom">--%>
            <%--                    <bf:formGroup col="4">--%>
            <%--                        <bc:label text="关联方证件号码" col="5" formControl="true"/>--%>
            <%--                        <bf:text id="relNumber" name="relNumber" col="7"--%>
            <%--                                 value="${traInvestment.relNumber}">--%>
            <%--                        </bf:text>--%>
            <%--                    </bf:formGroup>--%>
            <%--                    <bf:formGroup col="4">--%>
            <%--                        <bc:label text="累计实际交易金额" col="5" formControl="true"/>--%>
            <%--                        <bf:text id="accuPracTradeAmount" name="accuPracTradeAmount" col="7"--%>
            <%--                                 value="${traInvestment.accuPracTradeAmount}" disabled="true">--%>
            <%--                        </bf:text>--%>
            <%--                    </bf:formGroup>--%>
            <%--                    <bf:formGroup col="4">--%>
            <%--                        <bc:label text="本年度与该关联方累计交易金额（万元/人民币）" col="5" formControl="true"/>--%>
            <%--                        <bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"--%>
            <%--                                 value="${traInvestment.totolTradeAmount}" disabled="true">--%>
            <%--                        </bf:text>--%>
            <%--                    </bf:formGroup>--%>
            <%--                </bf:formGroup>--%>
        <bc:row cls="m-b">
            <bf:formGroup cls="group-butom-2" hrp="bottom" hr="dashed">
                <bf:formGroup col="10">
                    <bc:label text="交易信息：" col="2" formControl="true" cls="text-style"/>
                </bf:formGroup>
                <bf:formGroup col="2">

                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="交易类型" col="5" formControl="true"/>
                    <bf:select id="tranType" name="tranType" col="7" value="${traInvestment.tranType}"
                               dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label cls="required" text="业务类型" col="5" formControl="true"/>
                    <bf:select id="investmentType" name="investmentType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-InvestmentType" disabled="${opt == 'view'}"
                               includes="" value="${traInvestment.investmentType}">
                        <bf:option text="请选择" value=""></bf:option>
                        <be:event type="select">
                            changeNext(e.target.value)
                        </be:event>

                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div id="contractNoDiv" title="合同编号">
                        <bc:label text="合同编号" col="5" formControl="true"/>
                        <bf:text id="contractNo" name="contractNo" col="7" value="${traInvestment.contractNo}"
                                 disabled="${opt == 'view'}">
                            <%--                                --%>
                        </bf:text>
                    </div>
                </bf:formGroup>
            </bf:formGroup>
        </bc:row>
        <bf:formGroup hrp="bottom">
            <bf:formGroup col="4">
                <div title="合同签署日期">
                    <bc:label cls="required" text="合同签署日期" col="5" formControl="true"/>
                </div>
                <bf:datebox id="tranDate" name="tranDate" col="7" value="${traInvestment.tranDateString}"
                            disabled="${opt == 'view'}">
                    <bv:callback message="请填写必填项目" callback="notEmpt"/>
                </bf:datebox>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div id="contraTotalDiv" title="合同金额">
                    <bc:label  text="合同金额（万元）" col="5" formControl="true"/>
                    <bf:text id="contractAmount" name="contractAmount" col="7" disabled="${opt == 'view'}"
                             value="${traInvestment.contractAmount}" change='isInRoad()'>

                        <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                    </bf:text>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <bc:label cls="required" text="关联交易金额（万元）" col="5" formControl="true"/>
                <bf:text id="relTradeAmount" name="relTradeAmount" col="7" disabled="${opt == 'view'}"
                         value="${traInvestment.relTradeAmount}"
                         change='isInRoad(),isGreatTrade(),isConsiderType();'>

                    <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                </bf:text>
            </bf:formGroup>
        </bf:formGroup>

        <bf:formGroup hrp="bottom">
            <bf:formGroup col="4">
                <div id="busStartTimeDiv" title="关联交易发生的起始日期">
                    <bc:label text="业务起始日期" col="5" formControl="true"/>
                    <bf:datebox id="busStartTime" name="busStartTime" col="7"
                                value="${traInvestment.busStartTimeString}"
                                disabled="${opt == 'view'}">
                        <%--                            --%>
                    </bf:datebox>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div title="关联交易发生的结束日期">
                    <bc:label text="业务结束日期" col="5" formControl="true"/>
                </div>
                <bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traInvestment.busEndTimeString}"
                            disabled="${opt == 'view'}"/>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div id="pracTradeAmountDiv" title="实际交易金额（万元）">
                    <bc:label text="实际交易金额（万元）" col="5" formControl="true"/>
                    <bf:text id="pracTradeAmount" name="pracTradeAmount" col="7" disabled="${opt == 'view'}"
                             value="${traInvestment.pracTradeAmount}" change='isInRoad(),isGreatTrade(),isConsiderType();'>
                        <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                    </bf:text>
                </div>
            </bf:formGroup>
        </bf:formGroup>

        <bf:formGroup hrp="bottom">
            <bf:formGroup col="4">
                <div id="effectiveTimeDiv" title="交易的生效时间">
                    <bc:label text="生效时间" col="5" formControl="true"/>
                    <bf:datebox id="effectiveTime" name="effectiveTime" col="7"
                                value="${traInvestment.effectiveTimeString}"
                                disabled="${opt == 'view'}">
                        <%--                            --%>
                    </bf:datebox>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div id="yield_Div" title="收益率">
                    <bc:label text="收益率" col="5" formControl="true"/>
                    <bf:text id="yield_" name="yield_" col="7" value="${traInvestment.yield_}"
                             disabled="${opt == 'view'}">
                        <bv:regexp regexp="^(100|[1-9]?\d(\.\d\d?\d?)?)$|0$" message="请填写正确格式"/>
                        <%--                            --%>
                    </bf:text>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div id="approvalNumDiv" title="行政审批文号" style="display: none">
                    <bc:label text="行政审批文号" col="5" formControl="true"/>
                    <bf:text id="approvalNum" name="approvalNum" col="7" value="${traInvestment.approvalNum}"
                             disabled="${opt == 'view'}">
                        <%--                            --%>
                    </bf:text>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div id="securitiesTypeDiv" title="证券类型" style="display: none">
                    <bc:label text="证券类型" col="5" formControl="true"/>
                    <bf:select id="securitiesType" name="securitiesType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-SecuritiesType"
                               value="${traInvestment.securitiesType}"
                               disabled="${opt == 'view'}">
                        <%--                            --%>
                    </bf:select>
                    <bf:option text="请选择"></bf:option>
                </div>
            </bf:formGroup>
        </bf:formGroup>
        <bf:formGroup hrp="bottom">
            <bf:formGroup col="4">
                <div id="shareNumDiv" title="股\份\张数">
                    <bc:label text="股\份\张数" col="5" formControl="true"/>
                    <bf:text id="shareNum" name="shareNum" col="7" value="${traInvestment.shareNum}"
                             disabled="${opt == 'view'}">
                        <%--                            --%>
                        <bv:regexp regexp="^[0-9]*$" message="请填写正确格式"/>
                    </bf:text>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div id="variableshareratioDiv" title="变动股比或份额占比">
                    <bc:label text="变动股比或份额占比（%）" col="5" formControl="true"/>
                    <bf:text id="variableshareratio" name="variableshareratio" col="7"
                             value="${traInvestment.variableshareratio}"
                             disabled="${opt == 'view'}" placeholder="">
                        <%--                            --%>
                        <bv:regexp regexp="^(-|)(100|[1-9]?\d(\.\d\d?\d?)?)$|0$" message="请填写正确格式"/>
                    </bf:text>
                </div>
            </bf:formGroup>

            <bf:formGroup col="4">
                <div id="variableshareratioAfterDiv" title="变动后股比或份额占比">
                    <bc:label text="变动后股比或份额占比（%）" col="5" formControl="true"/>
                    <bf:text id="variableshareratioAfter" name="variableshareratioAfter" col="7"
                             value="${traInvestment.variableshareratioAfter}"
                             disabled="${opt == 'view'}" placeholder="">
                        <%--                            --%>
                        <bv:regexp regexp="^(-|)(100|[1-9]?\d(\.\d\d?\d?)?)$|0$" message="请填写正确格式"/>
                    </bf:text>
                </div>
            </bf:formGroup>
        </bf:formGroup>

        <bf:formGroup hrp="bottom">
            <bf:formGroup col="4">
                <bc:label cls="required" text="交易定价是否为国家规定" col="5" formControl="true"/>
                <bf:select id="isDealPrice" name="isDealPrice" col="7" value="${traInvestment.isDealPrice}"
                           disabled="${opt == 'view'}" dataSource="fixedDict" url="IsStatusDict">
                    <be:event type="select">
                        isConsiderType();
                    </be:event>
                    <bf:option text="请选择" value=""></bf:option>

                </bf:select>
            </bf:formGroup>
            <bf:formGroup col="4">
                <bc:label cls="required" text="是否现金认购" col="5" formControl="true"/>
                <bf:select id="isCashSubscript" name="isCashSubscript" col="7"
                           value="${traInvestment.isCashSubscript}" disabled="${opt == 'view'}"
                           dataSource="fixedDict" url="IsStatusDict">
                    <be:event type="select">
                        isConsiderType();
                    </be:event>
                    <bf:option text="请选择" value=""></bf:option>

                </bf:select>
            </bf:formGroup>

        </bf:formGroup>

        <bf:formGroup hrp="bottom">
            <bf:formGroup col="4">
                <div id="majorTradeDiv" title="是否重大关联交易">
                    <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                    <bf:select id="majorTrade" name="majorTrade" col="7" value="${traInvestment.majorTrade}"
                               disabled="true" dataSource="fixedDict" url="IsStatusDict">
                        <bf:option text="-" value=""></bf:option>
                    </bf:select>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <div id="majorTypeDiv" title="">
                    <bc:label text="达到重大关联交易的情形" col="5" formControl="true"/>
                    <bf:select id="majorType" name="majorType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-majorType" disabled="true"
                               includes="" value="${traInvestment.majorType}">
                        <bf:option text="-" value=""></bf:option>
                    </bf:select>
                </div>
            </bf:formGroup>
            <bf:formGroup col="4">
                <bc:label text="审议类型" col="5" formControl="true"/>
                <bf:select id="considerType" name="considerType" col="7" value="${traInvestment.considerType}"
                           dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">

                </bf:select>
            </bf:formGroup>
        </bf:formGroup>


        <bf:formGroup hrp="bottom">
            <bf:formGroup col="6">
                <div id="financialImpactDiv" title="对公司的财务状况及经营成果的描述">
                    <bc:label text="对财务状况及经营成果的影响" col="3" formControl="true"/>
                    <bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true"
                             value="${traInvestment.financialImpact}"
                             disabled="${opt == 'view'}"></bf:text>
                </div>
            </bf:formGroup>
            <bf:formGroup col="6">
                <div title="与关联方之间关联交易的对象">
                    <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                </div>
                <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true"
                         value="${traInvestment.tradeSubject}" disabled="${opt == 'view'}"></bf:text>
            </bf:formGroup>
        </bf:formGroup>
        <bf:formGroup hrp="bottom">
            <bf:formGroup col="6">
                <div title="交易单价">
                    <bc:label cls="required" text="交易价格" col="3" type="warning" formControl="true"/>
                </div>
                <bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true"
                         value="${traInvestment.tradePrice}" disabled="${opt == 'view'}"></bf:text>
            </bf:formGroup>
            <bf:formGroup col="6">
                <div title="交易价格、收益率的定价依据">
                    <bc:label cls="required" text="定价政策" col="3" formControl="true"/>
                </div>
                <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true"
                         value="${traInvestment.pricingPolicy}" disabled="${opt == 'view'}"></bf:text>
            </bf:formGroup>
        </bf:formGroup>
        <bf:formGroup hrp="bottom">
            <bf:formGroup col="6">
                <div title="除上述关键信息外，其他需要说明的重要或特殊的事项">
                    <bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
                </div>
                <bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traInvestment.remark}"
                         disabled="${opt == 'view'}"></bf:text>
            </bf:formGroup>
        </bf:formGroup>
        <div id="tableDiv" style="width:95%;margin:0 auto">
            <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                      selectFirst="false" queryUrl="queryList?transNum=${traInvestment.transNum}">
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
                <bf:formGroup hrp="bottom">
                    <bc:button type="danger" text="保存" icon="save" id="save2">
                        <be:event>
                            checkSaves();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            <%--                                window.location = '${base}/ins/traInvestment/list';--%>
                            window.history.back(-1);
                        </be:event>
                    </bc:button>
                </bf:formGroup>
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
    var traSubName = '86';
    //记录添加个数；按照添加点击事件累加
    var count = 0;
    //记录页面具体添加个数，按照添加 删除按钮 动态变化
    var countTmp = 0;
    function checkSaves() {

        var docInnerPids = getAllIdSelections();
        var docTableSelectItem = {
            'name': 'docTableSelectItem',
            'value': docInnerPids
        };
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();

        if (isValid) {
            var formData = $('#editForm').serializeAll();
            var checkFlag = false;
            $.mask.post('${base}/ins/traInvestment/quotaCheck', function (data) {
                if (data.success) {
                    $.mask.post('${base}/ins/traInvestment/traFundsCheck', formData, function (data) {
                        if (data.success) {
                            $('#ruleResult').val(data.code);
                            debugger
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
                            var relInfoDatas = {
                                'name': "relInfoListStr",
                                'value': JSON.stringify(relInfs)
                            }
                            formData2.push(docTableSelectItem);
                            formData2.push(relInfoDatas)
                            $.mask.post('${base}/ins/traInvestment/save', formData2, function (data) {
                                if (data.success) {
                                    $.messager.alert('提示', data.msg, 'info', function () {
                                        window.location = '${base}/ins/traInvestment/list';
                                    });
                                } else {
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            });
                        } else {
                            //合规检测有不通过的，需要提示用户 是否继续交易
                            $.messager.confirm('提示', data.msg, function (r) {
                                if (r) {
                                    $('#ruleResult').val(data.code);
                                    var formData1 = $('#editForm').serializeAll();

                                    var relInfs = [];

                                    for(var i = 1; i <=count; i++){
                                        var relNumberTmp = $("#relNumber"+i).val();
                                        //过滤 i 等于已经删除的item
                                        if(relNumberTmp === undefined || relNumberTmp === ''){
                                            continue
                                        }
                                        var relItemInfo = {
                                            "relId":"",
                                            "relNumber":"",
                                            "relType":"",
                                            "relName":"",
                                            "totolTradeAmount":"",
                                            "certType":"",
                                            "tranOpponent":"",
                                            // "assetsLastQuarter":"",
                                            "tradeAmount":"",
                                            "pid":""
                                        }

                                        relItemInfo.relId = $("#relId"+i).val();
                                        relItemInfo.relNumber = $("#relNumber"+i).val();
                                        relItemInfo.relType = $("#relType"+i).val();
                                        relItemInfo.relName = $("#relName"+i).val();
                                        relItemInfo.totolTradeAmount = $("#totolTradeAmount"+i).val();
                                        relItemInfo.certType = $("#certType" + i).val();
                                        relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
                                        // relItemInfo.assetsLastQuarter = $("#assetsLastQuarter"+i).val();
                                        relItemInfo.tradeAmount = $("#tradeAmount"+i).val();
                                        relItemInfo.pid = $("#pid"+i).val();
                                        relInfs.push(relItemInfo)
                                    }

                                    var relInfoDatas = {
                                        'name':"relInfoListStr",
                                        'value': JSON.stringify(relInfs)
                                    }

                                    formData1.push(docTableSelectItem);
                                    formData1.push(relInfoDatas);
                                    $.mask.post('${base}/ins/traInvestment/save', formData1, function (data) {
                                        if (data.success) {
                                            $.messager.alert('提示', data.msg, 'info', function () {
                                                window.location = '${base}/ins/traInvestment/list';
                                            });
                                        } else {
                                            $.messager.alert('错误', data.msg, 'error');
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }
    }


    //合同抽取待补录合同/交易发生日期不必填
    function notEmpt(value, validator, $field) {
        var fieId1 = $field[0].id;
        var value1 = value;
        var dataSources = '${traInvestment.dataSources}';
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
        var isCashSubscript = $('#isCashSubscript').val();
        //是否为重大关联交易
        var isMajorTrade = $('#majorTrade').val();
        //关联交易金额
        var relTradeAmount = $('#relTradeAmount').val();
        //关联方类型
        var relType = $('#relType1').val();

        //如果是现金交易审议类型为免予审议
        if (isCashSubscript != null && isCashSubscript != undefined && isCashSubscript != '' && isCashSubscript == "0") {
            $('#considerType').val('1');
            $('#considerType').select2({value: '1'});
            $('#contractNoDiv label font').remove();
            // $('#contraTotalDiv label font').remove();
            $('#busStartTimeDiv label font').remove();
            // $('#pracTradeAmountDiv label font').remove();
            $('#effectiveTimeDiv label font').remove();
            $('#yield_Div label font').remove();
            $('#approvalNumDiv label font').remove();
            $('#securitiesTypeDiv label font').remove();
            $('#shareNumDiv label font').remove();
            $('#variableshareratioDiv label font').remove();
            $('#variableshareratioAfterDiv label font').remove();
            $('#majorTradeDiv label font').remove();
            $('#majorTypeDiv label font').remove();
            $('#financialImpactDiv label font').remove();
            return;
        } else {
            //如果是重大关联交易审议类型为逐笔审议
            if (isMajorTrade != null && isMajorTrade != undefined && isMajorTrade != '') {
                if (isMajorTrade == "0") {
                    $('#considerType').val("3");
                    $('#considerType').select2({value: "3"});
                    if ($('#contractNoDiv label font').length == 0) {
                        $('#contractNoDiv label').prepend("<font color='red'>✶</font>");
                        // $('#contraTotalDiv label font').prepend("<font color='red'>✶</font>");
                        $('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
                        // $('#pracTradeAmountDiv label').prepend("<font color='red'>✶</font>");
                        $('#effectiveTimeDiv label').prepend("<font color='red'>✶</font>");
                        $('#yield_Div label').prepend("<font color='red'>✶</font>");
                        $('#approvalNumDiv label').prepend("<font color='red'>✶</font>");
                        $('#securitiesTypeDiv label').prepend("<font color='red'>✶</font>");
                        $('#shareNumDiv label').prepend("<font color='red'>✶</font>");
                        $('#variableshareratioDiv label').prepend("<font color='red'>✶</font>");
                        $('#variableshareratioAfterDiv label').prepend("<font color='red'>✶</font>");
                        $('#majorTradeDiv label').prepend("<font color='red'>✶</font>");
                        $('#majorTypeDiv label').prepend("<font color='red'>✶</font>");
                        $('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
                    }
                    return;
                }
            }
            //当关联方为个人合同金额<=50w  机构合同金额<=500w的时候免于审议
            if (relTradeAmount != null && relTradeAmount != undefined && relTradeAmount != ''
                && relType != null && relType != undefined && relType != '') {
                if (relType == "PSN" && relTradeAmount <= 50) {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    $('#contractNoDiv label font').remove();
                    // $('#contraTotalDiv label font').remove();
                    $('#busStartTimeDiv label font').remove();
                    // $('#pracTradeAmountDiv label font').remove();
                    $('#effectiveTimeDiv label font').remove();
                    $('#yield_Div label font').remove();
                    $('#approvalNumDiv label font').remove();
                    $('#securitiesTypeDiv label font').remove();
                    $('#shareNumDiv label font').remove();
                    $('#variableshareratioDiv label font').remove();
                    $('#variableshareratioAfterDiv label font').remove();
                    $('#majorTradeDiv label font').remove();
                    $('#majorTypeDiv label font').remove();
                    $('#financialImpactDiv label font').remove();
                    return;
                }
                if (relType == "ORG" && relTradeAmount <= 500) {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    $('#contractNoDiv label font').remove();
                    // $('#contraTotalDiv label font').remove();
                    $('#busStartTimeDiv label font').remove();
                    // $('#pracTradeAmountDiv label font').remove();
                    $('#effectiveTimeDiv label font').remove();
                    $('#yield_Div label font').remove();
                    $('#approvalNumDiv label font').remove();
                    $('#securitiesTypeDiv label font').remove();
                    $('#shareNumDiv label font').remove();
                    $('#variableshareratioDiv label font').remove();
                    $('#variableshareratioAfterDiv label font').remove();
                    $('#majorTradeDiv label font').remove();
                    $('#majorTypeDiv label font').remove();
                    $('#financialImpactDiv label font').remove();
                    return;
                }
            }

            //如果是国家定价重大关联交易审议类型为免予审议
            if (isDealPrice != null && isDealPrice != undefined && isDealPrice != '') {
                if (isDealPrice == "0") {
                    $('#considerType').val("1");
                    $('#considerType').select2({value: "1"});
                    $('#contractNoDiv label font').remove();
                    // $('#contraTotalDiv label font').remove();
                    $('#busStartTimeDiv label font').remove();
                    // $('#pracTradeAmountDiv label font').remove();
                    $('#effectiveTimeDiv label font').remove();
                    $('#yield_Div label font').remove();
                    $('#approvalNumDiv label font').remove();
                    $('#securitiesTypeDiv label font').remove();
                    $('#shareNumDiv label font').remove();
                    $('#variableshareratioDiv label font').remove();
                    $('#variableshareratioAfterDiv label font').remove();
                    $('#majorTradeDiv label font').remove();
                    $('#majorTypeDiv label font').remove();
                    $('#financialImpactDiv label font').remove();
                    return;
                }
            }
        }
        $('#considerType').val("2");
        $('#considerType').select2({value: "2"});
        $('#contractNoDiv label font').remove();
        // $('#contraTotalDiv label font').remove();
        $('#busStartTimeDiv label font').remove();
        // $('#pracTradeAmountDiv label font').remove();
        $('#effectiveTimeDiv label font').remove();
        $('#yield_Div label font').remove();
        $('#approvalNumDiv label font').remove();
        $('#securitiesTypeDiv label font').remove();
        $('#shareNumDiv label font').remove();
        $('#variableshareratioDiv label font').remove();
        $('#variableshareratioAfterDiv label font').remove();
        $('#majorTradeDiv label font').remove();
        $('#majorTypeDiv label font').remove();
        $('#financialImpactDiv label font').remove();
    }

    //判断是否为控股子公司来决定子公司名称证件号能不能添加
    function HoldingSubsidiarySub(value) {
        var value = value
        if (value == '1') {
            $('#holdingSubsidiaryNumber').val("");
            $('#holdingSubsidiaryName').val("");
            $('#holdingSubsidiaryType').val("");
            $('#holdingSubsidiaryNumberDiv').css('display', 'none');
            $('#holdingSubsidiaryNameDiv').css('display', 'none');
            // $('#holdingSubsidiaryNumberDiv label font').remove();
            // $('#holdingSubsidiaryNameDiv label font ').remove();
            // $('#holdingSubsidiaryTypeDiv label font ').remove();
        }
        if (value == '0') {
            $('#holdingSubsidiaryNumberDiv').css('display', 'block')
            $('#holdingSubsidiaryNameDiv').css('display', 'block')
            // if ($('#holdingSubsidiaryNumberDiv label font').length == 0) {
            //     $('#holdingSubsidiaryNumberDiv label').prepend("<font color='red'>✶</font>");
            //     $('#holdingSubsidiaryNameDiv label').prepend("<font color='red'>✶</font>");
            //     $('#holdingSubsidiaryTypeDiv label').prepend("<font color='red'>✶</font>");
            // }
        }
    };

    var opt = '${opt}';
    var unitName='${unitName}';
    $(document).ready(function () {
        if(unitName!=null&&unitName!=''&&unitName!=undefined){
            $("#managerOrg").val(unitName);
        }
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
        var relList = ${traInvestment.relInfoListStr};
        if (relList.length == 0) {
            $("#addInfos").trigger("click");
        } else {
            for (var i = 0; i < relList.length; i++) {
                addInfos(relList[i]);
            }
        }
        if (opt == 'view') {
            debugger;
            $("#save2").css("display", "none");
            var num=$('#formItems div').length;
            for(var i=1;i<=num;i++){
                $("#"+"tranOpponent"+i).attr("disabled",true);
                $("#"+"certTypes"+i).attr("disabled",true);
                $("#"+"relName"+i).attr("disabled",true);
                $("#"+"relNumber"+i).attr("disabled",true);
                $("#"+"totalassetsLateLast"+i).attr("disabled",true);
                $("#addInfos").hide();
                $(".btn-danger1").hide();
            }
            if($('#isHoldingSubsidiary').val()=='1'){
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
            //     $("#confirm").css("display","block")
            // }else{
            //     $("#confirm").css("display","none")
        }
        // $('#investmentType').val("216")
        // $('#investmentType').select2({value: "216"});
        var investmentType = $("#investmentType").val();
        if (investmentType != null && investmentType != undefined && investmentType != '') {
            changeNext(investmentType);
        }
        if (investmentType == '' || investmentType == undefined || investmentType == null) {
            changeNext('0');
        }

    });

    //确认按钮
    function toConfirm() {
        var pid = ${pid}
            $.ajax({
                url: "${base}/ins/traInvestment/confirm/" + pid,
                method: "GET",
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        window.location = '${base}/ins/traInvestment/list/';
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
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

    function changeNext(val) {
        if (val == '01') {
            $("#approvalNumDiv").css("display", "block");
            $("#securitiesTypeDiv").css("display", "none");
            // $("#variableshareratio").removeAttr("placeholder");
        }
        if (val == '03') {
            $("#approvalNumDiv").css("display", "block");
            $("#securitiesTypeDiv").css("display", "none");
            // $("#variableshareratio").removeAttr("placeholder");
        }
        if (val == '04') {
            $("#approvalNumDiv").css("display", "none");
            $("#securitiesTypeDiv").css("display", "block");
            // $("#variableshareratio").removeAttr("placeholder");
        }
        if (val == '02') {
            $("#approvalNumDiv").css("display", "block");
            $("#securitiesTypeDiv").css("display", "none");
            // $("#variableshareratio").attr("placeholder", "减少股比请填写负数")
        }
    }

    //当业务类型选择投资入股类 控制生效时间等是否必填
    //根据所选业务类型判断一些字段是否为必填
    function notEm(value, validator, $field) {
        var fieId1 = $field[0].id;
        var value1 = value;
        var investmentType = $('#investmentType').val();
        //生效时间
        if (fieId1 == "") {
            if ((investmentType == "01") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        //变动前
        if (fieId1 == "variableshareratio") {
            if ((investmentType == "01") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        //变动后
        if (fieId1 == "variableshareratioAfter") {
            if ((investmentType == "01") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    // function checkFile() {
    //     // 0 : 是 1：否
    //     var name = $("#holdingSubsidiaryName").val() == null || $("#holdingSubsidiaryName").val() == "" ? 'no' : $("#holdingSubsidiaryName").val();
    //     var number = $("#holdingSubsidiaryNumber").val() == null || $("#holdingSubsidiaryNumber").val() == "" ? 'no' : $("#holdingSubsidiaryNumber").val();
    //     var isHoldingSubsidiary = $("#isHoldingSubsidiary").val();
    //     if (isHoldingSubsidiary == '0' && (name == 'no' || number == 'no')) {
    //         $.messager.alert('提示', '控股子公司全称和控股子公司证件号码必填', 'warning');
    //         return false;
    //     } else if (isHoldingSubsidiary == '1' && (name != 'no' || number != 'no')) {
    //         $.messager.alert('提示', '控股子公司全称和控股子公司证件号码需要置空', 'warning');
    //         return false;
    //     }
    // }

    //查询交易对手相关方法
    function relNameSelect(relType, relId, relName1, totolTradeAmount, relNumber, certTypes, certType, tradeAmount) {
        // var traSubName = $("#traSubName").val();
        // if (traSubName == null || traSubName == " " || traSubName == undefined) {
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
                isGreatTrade();
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
                    $("#relqName").val(data.relName);
                    $("#totolTradeAmount").val(data.totalTradeAmount);
                    $('#total').val(data.totalTradeAmount);
                    $("#relType").val(data.relType);
                } else {
                    $.messager.alert('提示', '关联方不存在！', 'warning');
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

    /**判断是否是重大关联交易*/
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
        /**关联交易金额*/
        var relTradeAmount = $('#relTradeAmount').val();
        if (relTradeAmount == '' || relTradeAmount == null) {
            return;
        }
        /**实际交易金额*/
        var pracTradeAmount = $('#pracTradeAmount').val();
        /**调用重大校验接口*/
        var obj = {
            relTradeAmount: relTradeAmount,
            pracTradeAmount:pracTradeAmount,
            relInfoListStr:JSON.stringify(relInfs)
        };
        $.ajax({
            url: '${base}/ins/traInvestment/GreatCheck',
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
        // onblur="initRelInfo(\'' + relNumber + '\',\'' + relName + '\',\'' + totolTradeAmount + '\',\'' + relType +'\',\''+assetsLastQuarter + '\')"
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
            '           <input type="text" onclick="relNameSelect(' + relType + ',' + relId + ',' + relName + ',' + totolTradeAmount + ',' + relNumber + ',' + certTypes + ',' + certType +','+ tradeAmount + ')" class="jep-input form-control " id="' + relName + '" value = "';
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
            return;
        }
        $("#" + item).remove()
        countTmp--;
        isGreatTrade();
        isConsiderType();
    }


</script>
</body>
</html>