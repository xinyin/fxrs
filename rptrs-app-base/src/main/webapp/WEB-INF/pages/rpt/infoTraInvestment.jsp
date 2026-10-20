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
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易主体名称" col="5" formControl="true"/>
                        <bf:select id="traSubName" name="traSubName" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-subjectName"
                                   disabled="${opt == 'view'}" value="${traInvestment.traSubName}">
                            <bf:option text="请选择"></bf:option>
                            <be:event type="select">
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <%--                    <bf:formGroup col="4">--%>
                    <%--                        <bc:label cls="required" text="交易对手名称" col="5" formControl="true"/>--%>
                    <%--                        <bf:lookup id="relName" name="relName" col="7" title="交易对手信息"--%>
                    <%--                                   url="${base}/ins/traInvestment/relNameSelect" winId="relNameSelect"--%>
                    <%--                                   propText="relName" propValue="relName"--%>
                    <%--                                   callback="result"  text="${traInvestment.relName}"--%>
                    <%--                                   value="${traInvestment.relName}" disabled="${opt == 'view'}"--%>
                    <%--                                   beforeOpen="empTraSub"   onSelect="oneSelect">--%>
                    <%--                        </bf:lookup>--%>
                    <%--                    </bf:formGroup>--%>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易对手名称" col="5" formControl="true"/>
                        <bf:text id="relName" name="relName" col="7" click="relNameSelect()"
                                 value="${traInvestment.relName}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>


                    <bf:formGroup col="4">
                        <div title="交易主体是否为中再资产的控股子公司">
                            <bc:label cls="required" text="是否为控股子公司" col="5" formControl="true"/>
                        </div>

                        <bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
                                   value="${traInvestment.isHoldingSubsidiary}" disabled="${opt == 'view'}"
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
                                 value="${traInvestment.holdingSubsidiaryName}"
                                 disabled="true">
                            <%--<bv:notEmpty/>--%>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="控股子公司证件类型" col="5" formControl="true"/>
                        <bf:select id="holdingSubsidiaryType" name="holdingSubsidiaryType" col="7"
                                   value="${traInvestment.holdingSubsidiaryType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-ORGIdentifyType"
                                   disabled="true">
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="控股子公司证件号码" col="5" formControl="true"/>
                        <bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
                                 value="${traInvestment.holdingSubsidiaryNumber}"
                                 disabled="true">
                            <%--<bv:notEmpty/>--%>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="交易对手上年末总资产（万元）" col="5" formControl="true"/>
                        <bf:text id="totalAssets" name="totalAssets" col="7" value="${traInvestment.totalAssets}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </bf:formGroup>
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
                        <bf:text id="contractNo" name="contractNo" col="7" value="${traInvestment.contractNo}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="关联交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="contractAmount" name="contractAmount" col="7" disabled="${opt == 'view'}"
                                 value="${traInvestment.contractAmount}" change='isInRoad(),isGreatTrade()'>
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="与关联方签署交易协议的日期">
                            <bc:label cls="required" text="合同/交易发生日期" col="5" formControl="true"/>
                        </div>
                        <bf:datebox id="tranDate" name="tranDate" col="7" value="${traInvestment.tranDateString}"
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
                        <bf:datebox id="busStartTime" name="busStartTime" col="7"
                                    value="${traInvestment.busStartTimeString}"
                                    disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="关联交易发生的结束日期">
                            <bc:label text="业务结束日期" col="5" formControl="true"/>
                        </div>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traInvestment.busEndTimeString}"
                                    disabled="${opt == 'view'}"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
        <div title="本年度与该关联方累计交易金额（万元）包含：单笔重大+累计金额+免于审议">
                        <bc:label text="本年度与该关联方累计交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
                                 value="${traInvestment.totolTradeAmount}" disabled="true"
                                 placeholder="本年度与该关联方发生的关联交易累计金额（万元）">
                        </bf:text>
        </div>
                    </bf:formGroup>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="交易金额（万元）" col="5" formControl="true"/>--%>
<%--                        <bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traInvestment.tradeAmount}"--%>
<%--                                 disabled="true">--%>
<%--                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>--%>
<%--                        </bf:text>--%>
<%--                    </bf:formGroup>--%>
                    <bf:formGroup col="4">
                        <div title="交易的生效时间">
                            <bc:label cls="required" text="生效时间" col="5" formControl="true"/>
                        </div>
                        <bf:datebox  id="effectiveTime" name="effectiveTime" col="7"
                                    value="${traInvestment.effectiveTimeString}"
                                    disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="收益率" col="5" formControl="true"/>
                        <bf:text id="yield_" name="yield_" col="7" value="${traInvestment.yield_}"
                                 disabled="${opt == 'view'}">
                            <bv:regexp regexp="^(100|[1-9]?\d(\.\d\d?\d?)?)$|0$" message="请填写正确格式"/>
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <div id="approvalNumDiv" style="display: none">
                        <bf:formGroup col="4">
                            <bc:label cls="required" text="行政审批文号" col="5" formControl="true"/>
                            <bf:text id="approvalNum" name="approvalNum" col="7" value="${traInvestment.approvalNum}"
                                     disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:text>
                        </bf:formGroup>
                    </div>
                    <div id="securitiesTypeDiv" style="display: none">
                        <bf:formGroup col="4">
                            <bc:label cls="required" text="证券类型" col="5" formControl="true"/>
                            <bf:select id="securitiesType" name="securitiesType" col="7"
                                       value="${traInvestment.securitiesType}" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-SecuritiesType" disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                    </div>
                </bf:formGroup>

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="股\份\张数" col="5" formControl="true"/>
                        <bf:text id="shareNum" name="shareNum" col="7" value="${traInvestment.shareNum}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[0-9]*$" message="请填写正确格式"/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="变动股比或份额占比（%）" col="5" formControl="true"/>
                        <bf:text id="variableshareratio" name="variableshareratio" col="7"
                                 value="${traInvestment.variableshareratio}"
                                 disabled="${opt == 'view'}" placeholder="">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^(-|)(100|[1-9]?\d(\.\d\d?\d?)?)$|0$" message="请填写正确格式"/>
                        </bf:text>
                    </bf:formGroup>

                    <bf:formGroup col="4">
                        <bc:label cls="required" text="变动后股比或份额占比（%）" col="5" formControl="true"/>
                        <bf:text id="variableshareratioAfter" name="variableshareratioAfter" col="7"
                                 value="${traInvestment.variableshareratioAfter}"
                                 disabled="${opt == 'view'}" placeholder="">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^(-|)(100|[1-9]?\d(\.\d\d?\d?)?)$|0$" message="请填写正确格式"/>
                        </bf:text>
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
                            <bv:notEmpty/>
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
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="审议类型" col="5" formControl="true"/>
                        <bf:select id="considerType" name="considerType" col="7" value="${traInvestment.considerType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">
                            <bv:notEmpty/>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                        <bf:select id="majorTrade" name="majorTrade" col="7" value="${traInvestment.majorTrade}"
                                   disabled="true" dataSource="fixedDict"
                                   url="IsStatusDict">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label cls="required"  text="达到重大关联交易的情形" col="5" formControl="true"/>
                        <bf:select id="majorType" name="majorType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-majorType" disabled="true" placeholder="未达到"
                                   includes="" value="${traInvestment.majorType}">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="交易单价">
                        <bc:label cls="required" text="交易价格" col="3" type="warning" formControl="true"/>
                    </div>
                    <bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true"
                             value="${traInvestment.tradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="交易价格、收益率的定价依据">
                        <bc:label cls="required" text="定价政策" col="3" formControl="true"/>
                    </div>
                    <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true"
                             value="${traInvestment.pricingPolicy}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="对公司的财务状况及经营成果的描述">
                        <bc:label cls="required" text="对财务状况及经营成果的影响" col="3" formControl="true"/>
                    </div>
                    <bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true"
                             value="${traInvestment.financialImpact}"
                             disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="与关联方之间关联交易的对象">
                        <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                    </div>
                    <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true"
                             value="${traInvestment.tradeSubject}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="除上述关键信息外，其他需要说明的重要或特殊的事项">
                        <bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
                    </div>
                    <bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traInvestment.remark}"
                             disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
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
        //合同金额
        var contractAmount = $('#contractAmount').val();
        //关联方类型
        var relType = $('#relType').val();

        //如果是现金交易审议类型为免予审议
        if (isCashSubscript != null && isCashSubscript != undefined && isCashSubscript != '' && isCashSubscript == "0") {
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
        if (value == '1') {
            $('#holdingSubsidiaryNumber').attr("disabled", "false");
            $('#holdingSubsidiaryName').attr("disabled", "false");
            $('#holdingSubsidiaryType').attr("disabled", "false");
        }
        if (value == '0') {
            $('#holdingSubsidiaryNumber').removeAttr("disabled");
            $('#holdingSubsidiaryName').removeAttr("disabled");
            $('#holdingSubsidiaryType').removeAttr("disabled");
        }

    };

    var opt = '${opt}';
    $(document).ready(function () {
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

    function changeNext(val) {
        if (val == '04') {
            $("#approvalNumDiv").css("display", "none");
            $("#securitiesTypeDiv").css("display", "block");
            $("#variableshareratio").removeAttr("placeholder");
        }
        if (val == '01') {
            $("#approvalNumDiv").css("display", "block");
            $("#securitiesTypeDiv").css("display", "none");
            $("#variableshareratio").removeAttr("placeholder");
        }
        if (val == '02') {
            $("#approvalNumDiv").css("display", "block");
            $("#securitiesTypeDiv").css("display", "none");
            $("#variableshareratio").attr("placeholder", "减少股比请填写负数")
        }
        if (val == '03') {
            $("#approvalNumDiv").css("display", "block");
            $("#securitiesTypeDiv").css("display", "none");
            $("#variableshareratio").removeAttr("placeholder");
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

    function checkFile () {
        debugger;
        // 0 : 是 1：否
        var name = $("#holdingSubsidiaryName").val() == null ||  $("#holdingSubsidiaryName").val() == "" ? 'no' : $("#holdingSubsidiaryName").val();
        var number = $("#holdingSubsidiaryNumber").val() == null || $("#holdingSubsidiaryNumber").val() == "" ? 'no' : $("#holdingSubsidiaryNumber").val();
        var isHoldingSubsidiary = $("#isHoldingSubsidiary").val();
        if (isHoldingSubsidiary == '0' && (name == 'no' || number == 'no')) {
            $.messager.alert('提示', '控股子公司全称和控股子公司证件号码必填', 'warning');
            return false;
        } else if (isHoldingSubsidiary == '1' && (name != 'no' || number != 'no')) {
            $.messager.alert('提示', '控股子公司全称和控股子公司证件号码需要置空', 'warning');
            return false;
        }

        /*else {
            $.messager.alert('提', '控股子公司全称和控股子公司证件号码必填', 'warning');
            return false;
            /!*if (name == null) {
                $.messager.alert('提示', '控股子公司全称和控股子公司证件号码必填', 'warning');
                return false;
            }*!/
        }*/
    }

    //查询交易对手相关方法
    function relNameSelect() {
        var traSubName = $("#traSubName").val();
        if (traSubName == null || traSubName == " " || traSubName == undefined) {
            $.messager.alert('提示', '请先选择交易主体', 'warning');
            return false;
        } else {
            $.messager.modal({
                winId: 'relNameSelect',
                width: 700,
                height: 420,
                title: '交易对手信息',
                url: '${base}/ins/traInvestment/relNameSelect/' + traSubName,
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
                onClose: function () {
                }
            });
        }
    }

    // function oneSelect(){
    //     var relName =  $("input[name=relName]").val();
    //     var relNumber = $("#relNumber").val();
    //     if ((relNumber !== '' && relNumber !== undefined) || (relName !== '' && relName !== undefined)) {
    //         infoBlur(relNumber, relName);
    //     }
    // }
    // function empTraSub(){
    //     var traSubName = $("#traSubName").val();
    //     if (traSubName == null || traSubName == " " || traSubName == undefined){
    //         $.messager.alert('提示', '请先选择交易主体', 'warning');
    //         return false;
    //     }
    // }
    // $("#relName").blur(function () {
    //     var relNumber = $("#relNumber").val();
    //     var relName = $("#relName").val();
    //     if ((relNumber !== '' && relNumber !== undefined) || (relName !== '' && relName !== undefined)) {
    //         infoBlur(relNumber, relName);
    //     }
    // });

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

    //判断是否重大关联交易
    function isGreatTrade() {
        //委托机构不判断是否重大
        var traSubName = $("#traSubName").val();
        // if (traSubName != '0001' && traSubName != '0001-ZZHK') {
        //     return;
        // }
        var relId = $('#relId').val();
        var relType = $('#relType').val();
        var tradeAmount = $('#contractAmount').val();
        var totolTradeAmount = $('#total').val();
        //是否是现金认购
        var isCashSubscript = $('#isCashSubscript').val();

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
        var obj = {
            relId: relId,
            relType: relType,
            tradeAmount: tradeAmount,
            totolTradeAmount: totolTradeAmount,
            isCashSubscript: isCashSubscript
        };
        $.ajax({
            url: '${base}/ins/traInvestment/GreatCheck',
            data: obj,
            type: 'post',
            jsonType: 'json',
            success: function (data) {
                if (data.success) {
                    $('#majorTrade').val(data.isGreatTrade);
                    $('#majorTrade').select2({value: (data.isGreatTrade)})
                    if (data.isGreatTrade == "0") {
                        $('#majorType').val(data.majorType);
                        $('#majorType').select2({value: (data.majorType)})
                    } else {
                        $('#majorType').val(" ");
                        $('#majorType').select2({value: (" ")})
                    }

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