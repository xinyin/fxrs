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
                        <bc:label cls="required" text="发起方是否为控股子公司" col="5" formControl="true"/>
                        <bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
                                   value="${traService.isHoldingSubsidiary}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="YesOrNoForRelTraDict">
                            <be:event type="select">
                                HoldingSubsidiarySub(e.target.value);
                                <%--isInRoad();--%>
                                isGreatTrade();
                                isConsiderType();
                                isAgreement($('#isAgreement').val())
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4" id="j">
                        <div id="holdingSubsidiaryNameDiv" title="控股子公司全称">
                            <bc:label text="控股子公司全称" col="5" formControl="true"/>
                            <bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
                                     value="${traService.holdingSubsidiaryName}" disabled="${opt == 'view'}">
                            </bf:text>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4" id="k">
                        <div id="holdingSubsidiaryNumberDiv" title="控股子公司证件号码">
                            <bc:label text="控股子公司证件号码" col="5" formControl="true"/>
                            <bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
                                     value="${traService.holdingSubsidiaryNumber}" disabled="${opt == 'view'}">
                                <%--<bv:notEmpty/>--%>
                            </bf:text>
                        </div>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>

            <bc:row cls="m-b">
                <bf:formGroup cls="group-butom-1" hrp="bottom" hr="dashed">
                    <bf:formGroup col="10">
                        <bc:label text="交易对手信息：" col="2" formControl="true" cls="text-style"/>
                    </bf:formGroup>
                    <bf:formGroup col="2">
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

                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div title="交易类型">
                            <bc:label cls="required" text="交易类型" col="5" formControl="true"/>
                            <bf:select id="tranType" name="tranType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-tranType" disabled="true"
                                       value="${traService.tranType}">
                            </bf:select>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="业务类型">
                            <bc:label cls="required" text="业务类型" col="5" formControl="true"/>
                            <bf:select id="busType" name="busType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-ServiceType" value="${traService.busType}"
                                       disabled="${opt == 'view'}">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div id="contractNoDiv" title="合同编号">
                            <bc:label  text="合同编号" col="5" formControl="true"/>
                            <bf:text id="contractNo" name="contractNo" col="7" value="${traService.contractNo}"
                                     disabled="${opt == 'view'}">
                            </bf:text>
                        </div>
                    </bf:formGroup>
                </bf:formGroup>
            </bc:row>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label col="5"  cls="required"  text="合同签署日期" formControl="true"/>
                    <bf:datebox id="contractDate" name="contractDate" col="7"
                                value="${traService.contractDateString}" disabled="${opt == 'edit'}">
                    </bf:datebox>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div id="busStartTimeDiv" title="关联交易发生的起始日期">
                        <bc:label text="业务起始日期" col="5" formControl="true"/>
                        <bf:datebox id="busStartTime" name="busStartTime" col="7" value="${traService.busStartTime}"
                                    disabled="${opt == 'view'}">
                        </bf:datebox>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="关联交易发生的结束日期">
                        <bc:label text="业务结束日期" col="5" formControl="true"/>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traService.busEndTime}"
                                    disabled="${opt == 'view'}"/>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div id="contraTotalDiv" title="合同金额(万元)">
                        <bc:label col="5"  text="合同金额(万元)" formControl="true"/>
                        <bf:text col="7" id="contractAmount" name="contractAmount" value="${traService.contractAmount}"
                                 change="isInRoad(),copyAmount(),isGreatTrade()"  disabled="${opt == 'view'}">
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="关联交易金额（万元/人民币）">
                        <bc:label cls="required" text="关联交易金额（万元/人民币）" col="5" formControl="true"/>
                        <bf:text id="relTradeAmount" name="relTradeAmount" col="7" value="${traService.relTradeAmount}"
                                 disabled="true"
                                 change='isGreatTrade(),isConsiderType(),isAgreement(2)'>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="实际交易金额(万元)" col="5" formControl="true"/>
                    <bf:text col="7" id="pracTradeAmount" name="pracTradeAmount" value="${traService.pracTradeAmount}"
                             disabled="${opt == 'view'}" change='isInRoad(),copyAmount(),isGreatTrade(),isConsiderType(),isAgreement(2)'>
                        <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
              <%--  <bf:formGroup col="4">
                    <div title="交易定价是否为国家规定">
                        <bc:label cls="required" text="交易定价是否为国家规定" col="5" formControl="true"/>
                        <bf:select id="isDealPrice" name="isDealPrice" col="7" value="${traService.isDealPrice}"
                                   dataSource="fixedDict" url="YesOrNoForRelTraDict" disabled="${opt == 'view'}">
                            <be:event type="select">
                                isConsiderType();
                            </be:event>
                        </bf:select>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="是否现金认购">
                        <bc:label  cls="required" text="是否现金认购" col="5" formControl="true"/>
                        <bf:select id="iscashsubscribe" name="iscashsubscribe" col="7"
                                   value="${traService.iscashsubscribe}"
                                   dataSource="fixedDict" url="YesOrNoForRelTraDict" disabled="${opt == 'view'}">
                            <be:event type="select">
                                isConsiderType();
                                isAgreement($("#isAgreement").val());
                            </be:event>
                        </bf:select>
                    </div>
                </bf:formGroup>--%>
                <bf:formGroup col="4">
                    <div title="是否统一交易协议">
                        <bc:label cls="required" text="是否统一交易协议" col="5" formControl="true"/>
                        <bf:select id="isAgreement" name="isAgreement" col="7"
                                   value="${traService.isAgreement}"
                                   dataSource="fixedDict" url="YesOrNoForRelTraDict" disabled="${opt == 'view'}">
                            <be:event type="select">
                                isAgreement(e.target.value);
                                copyAmount();
                                isInRoad();
                                copyNo();
                            </be:event>
                        </bf:select>
                    </div>
                </bf:formGroup>

            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div id="isAgreementImplDiv" title="统一交易协议执行情况">
                        <bc:label text="统一交易协议执行情况" col="5" formControl="true"/>
                        <bf:select id="isAgreementImpl" name="isAgreementImpl" col="7"
                                   value="${traService.isAgreementImpl}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-isAgreementImpl" disabled="${opt == 'view'}">
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                                isAgreementImpl(e.target.value);
                                isInRoad();
                                isGreatTrade();
                                isConsiderType();
                            </be:event>
                        </bf:select>
                    </div>
                </bf:formGroup>
                <%--<bf:formGroup col="4">
                    <div id="agreementNumDiv" title="统一交易协议编号">
                        <bc:label text="统一交易协议编号" col="5" formControl="true"/>
                        <bf:text id="tradeAgreementNumber" name="tradeAgreementNumber" col="7"
                                 value="${traService.tradeAgreementNumber}" disabled="${opt == 'view'}">
                        </bf:text>
                    </div>
                </bf:formGroup>--%>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div id="majorTypeDiv" title="是否重大关联交易">
                        <bc:label  text="是否重大关联交易" col="5" formControl="true"/>
                        <bf:select id="majorTrade" name="majorTrade" col="7" value="${traService.majorTrade}"
                                   disabled="true" dataSource="fixedDict"
                                   url="YesOrNoForRelTraDict">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div id="majorTradeDiv" title="达到重大关联交易的情形">
                        <bc:label  text="达到重大关联交易的情形" col="5" formControl="true"/>
                        <bf:select id="majorType" name="majorType" col="7" value="${traService.majorType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-majorType" disabled="true">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="审议类型">
                        <bc:label  text="审议类型" col="5" formControl="true"/>
                        <bf:select id="considerType" name="considerType" col="7" value="${traService.considerType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">
                        </bf:select>
                    </div>
                </bf:formGroup>
            </bf:formGroup>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label  text="来源系统唯一编码" col="5" formControl="true"/>
                    <bf:text id="uniqueCode" name="uniqueCode" col="7" disabled="true"
                             value="${traService.uniqueCode}">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label  text="来源系统" col="5" formControl="true"/>
                    <bf:text id="sourceSystem" name="sourceSystem" col="7" disabled="true"
                             value="${traService.sourceSystem}">
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label  text="来源模块" col="5" formControl="true"/>
                    <bf:text id="sourceModule" name="sourceModule" col="7" disabled="true"
                             value="${traService.sourceModule}">
                    </bf:text>
                </bf:formGroup>


            </bf:formGroup>

            <bf:formGroup hrp="bottom">

                <bf:formGroup col="4">
                    <bc:label  text="收付款标识" col="5" formControl="true"/>
                    <bf:text id="paymentId" name="paymentId" col="7" disabled="true"
                             value="${traService.paymentId}">
                    </bf:text>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <bc:label  text="场景" col="5" formControl="true"/>
                    <bf:text id="scene" name="scene" col="7" disabled="true"
                             value="${traService.scene}">
                    </bf:text>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="交易名称：">
                        <bc:label  cls="required" text="交易名称" col="3" formControl="true"/>
                    </div>
                    <bf:text id="transactionName" name="transactionName" col="9" rows="2" multiple="true"
                             value="${traService.transactionName}" disabled="${opt == 'view'}"></bf:text>
                </bf:formGroup>



                <bf:formGroup col="6">
                    <div title="交易描述：">
                        <bc:label  cls="required" text="交易描述" col="3" formControl="true"/>
                    </div>
                    <bf:text id="transactionDescription" name="transactionDescription" col="9" rows="2" multiple="true"
                             value="${traService.transactionDescription}" disabled="${opt == 'view'}"></bf:text>
                </bf:formGroup>

            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="交易单价">
                        <bc:label cls="required" text="交易价格" col="3" formControl="true"/>
                        <bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true"
                                 value="${traService.tradePrice}" disabled="${opt == 'view'}"></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="交易价格、收益率的定价依据">
                        <bc:label cls="required" text="定价政策" col="3" formControl="true"/>
                        <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true"
                                 value="${traService.pricingPolicy}"
                                 disabled="${opt == 'view'}"></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="与关联方之间关联交易的对象">
                        <bc:label  cls="required" text="交易标的" col="3" formControl="true"/>
                        <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true"
                                 value="${traService.tradeSubject}" disabled="${opt == 'view'}"></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div id="financialImpactDiv" title="对公司的财务状况及经营成果的描述">
                        <bc:label  text="对财务状况及经营成果的影响" col="3" formControl="true"/>
                        <bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true"
                                 value="${traService.financialImpact}"
                                 disabled="${opt == 'view'}"></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="除上述关键信息外，其他需要说明的重要或特殊的事项">
                        <bc:label text="其他事项说明" col="3" formControl="true"/>
                        <bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traService.remark}"
                                 disabled="${opt == 'view'}"> </bf:text>
                    </div>
                </bf:formGroup>



            </bf:formGroup>



            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traService.transNum}">
                    <bc:toolbar id="downFile" text="下载" type="button" handler="downFileOnclick"/>
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
    var traSubName ='复星保德信人寿保险有限公司';
    //记录添加个数；按照添加点击事件累加
    var count = 0;

    //记录页面具体添加个数，按照添加 删除按钮 动态变化
    var countTmp = 0;

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
        var holdName = $('#holdingSubsidiaryName').val();
        var holdNum = $('#holdingSubsidiaryNumber').val();

        // $("#save2").attr("disabled", true);
        if ($('#isHoldingSubsidiary').val() == '1' && (holdName == '' || holdNum == '')) {
            $.messager.alert('错误', "是控股子公司，控股子公司全称、证件号码为必填项", 'error');
            return;
        }
        var relNameList = $("input[id^='relName']");
        var flag=false;
        for(var i=0;i<relNameList.length;i++){
            if(relNameList[i].value==''){
                flag=true;
            }
        }
        if(flag){
            $.messager.alert('错误', "请先添加交易对手！", 'error');
            return;
        }


        if ($("input[name='contractDate']").val() == undefined || '' == $("input[name='contractDate']").val()) {
            $.messager.alert('错误', "合同签署日期不能为空！", 'error');
            return;
        }

        /*if ($('#investmentType').val() == undefined || '' == $('#investmentType').val().trim()) {
            $.messager.alert('错误', "业务类型不能为空！", 'error');
            return;
        }
        if ($('#contractNo').val() == undefined || '' == $('#contractNo').val().trim()) {
            $.messager.alert('错误', "合同编号不能为空！", 'error');
            return;
        }

        if ($('#transactionName').val() == undefined || '' == $('#transactionName').val().trim()) {
            $.messager.alert('错误', "交易名称不能为空！", 'error');
            return;
        }

        if ($('#transactionDescription').val() == undefined || '' == $('#transactionDescription').val().trim()) {
            $.messager.alert('错误', "交易描述不能为空！", 'error');
            return;
        }



        if ($('#yield_').val() == undefined || '' == $('#yield_').val().trim()) {
            $.messager.alert('错误', "收益率不能为空！", 'error');
            return;
        }
        if ($('#shareNum').val() == undefined || '' == $('#shareNum').val().trim()) {
            $.messager.alert('错误', "股/份/张数不能为空！", 'error');
            return;
        }if ($('#financialImpact').val() == undefined || '' == $('#financialImpact').val().trim()) {
            $.messager.alert('错误', "对财务状况及经营成果的影响不能为空！", 'error');
            return;
        }if ($('#tradeSubject').val() == undefined || '' == $('#tradeSubject').val().trim()) {
            $.messager.alert('错误', "交易标的不能为空！", 'error');
            return;
        }if ($('#tradePrice').val() == undefined || '' == $('#tradePrice').val().trim()) {
            $.messager.alert('错误', "交易价格不能为空！", 'error');
            return;
        }

        if ($('#pricingPolicy').val() == undefined || '' == $('#pricingPolicy').val().trim()) {
            $.messager.alert('错误', "定价政策不能为空！", 'error');
            return;
        }

        if ($('#approvalNum').val() == undefined || '' == $('#approvalNum').val().trim()) {
            $.messager.alert('错误', "行政审批文号不能为空！", 'error');
            return;
        }

        if ($("input[name='busStartTime']").val() == undefined || '' == $("input[name='busStartTime']").val()) {
            $.messager.alert('错误', "业务起始日期不能为空！", 'error');
            return;
        }

        if($('#contractAmount').val()==''){
            $.messager.alert('错误', "合同金额不能为空！", 'error');
            return;
        }*/

        var tb=document.getElementById("docTable").innerText;
        if ((tb.search("没有找到匹配的记录") != -1 )) {
            $.messager.alert('错误', "文件内容不能为空！", 'error');
            return;
        }

        $("#save2").attr("disabled", true);
        var docInnerPids = getAllIdSelections();
        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        var formData = $('#editForm').serializeAll();
        $.mask.post('${base}/ins/traService/quotaCheck', formData, function (data) {
            if (data.success) {
                var docTableSelectItem = {
                    'name': 'docTableSelectItem',
                    'value': docInnerPids
                };
                formData.push(docTableSelectItem);
                if (isValid) {
                    $.mask.post('${base}/ins/traService/traServiceCheck', formData, function (data) {
                        if (data.success) {
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
                                relItemInfo.relType = $("#relType" + i).val();
                                relItemInfo.relName = $("#relName" + i).val();
                                relItemInfo.totalNotCleanTradeAmount = $("#totalNotCleanTradeAmount" + i).val();
                                // relItemInfo.assetsLastQuarter = $("#assetsLastQuarter"+i).val();
                                relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
                                relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
                                relItemInfo.certType = $("#certType" + i).val();
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

                            formData1.push(docTableSelectItem);
                            formData1.push(relInfoDatas);
                            $.mask.post('${base}/ins/traService/saveSign', formData1, function (data) {
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
                                        relItemInfo.relType = $("#relType" + i).val();
                                        relItemInfo.relName = $("#relName" + i).val();
                                        relItemInfo.totolTradeAmount = $("#totolTradeAmount" + i).val();
                                        // relItemInfo.assetsLastQuarter = $("#assetsLastQuarter"+i).val();
                                        relItemInfo.tradeAmount = $("#tradeAmount" + i).val();
                                        relItemInfo.tranOpponent = $("#tranOpponent" + i).val();
                                        relItemInfo.certType = $("#certType" + i).val();
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
                    });
                }
            } else {
                $.messager.alert('错误', data.msg, 'error');
                $("#save2").attr("disabled", false);
            }
        });

    }

    //查询交易对手相关方法
    function relNameSelect(relType, relId, relName1, totalNotCleanTradeAmount, relNumber,certTypes, certType,tradeAmount) {
        $.messager.modal({
            winId: 'relNameSelect',
            width: 700,
            height: 420,
            title: '交易对手信息',
            url: '${base}/ins/traInvestment/relNameSelect/' + traSubName+'/05',
            args: {
                result: function (obj) {
                    $("#" + relId.id).val(obj["relId"]);
                    $("#" + relName1.id).val(obj["relName"]);
                    var zeroAmount = obj["tradeZeroAmount"];
                    $("#" + tradeAmount.id).val(zeroAmount);
                    var newAount = 0;
                    var relTradeAmount = $('#relTradeAmount').val();
                    if (relTradeAmount != '' && relTradeAmount != 'undefined') {
                        zeroAmount = parseInt(zeroAmount)+parseInt(relTradeAmount);
                    }
                    $("#" + totalNotCleanTradeAmount.id).val(zeroAmount);

                    $('#total').val(obj["totolTradeAmount"]);
                    $("#" + relType.id).val(obj["relType"]);
                    $("#"+certTypes.id).val(obj["certTypes"]);
                    $("#"+certType.id).val(obj["certType"]);
                    $("#" + relNumber.id).val(obj["relNumber"]);
                    /*$("#"+tradeZeroAmount.id).val(obj["tradeZeroAmount"]);*/
                }
            },
            onClose: function () {
                isGreatTrade();
                isInRoad();
            }
        });

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

        var isAgreement = $('#isAgreement').val();
        var isAgreementImpl = $('#isAgreementImpl').val();
     if(isDealPrice=="1"||isCashSubscript == "1"){
            //其次，如果是国家规定或者现金认购，或者交易金额企业小于500万，个人小于50万，一律免于审议
            $('#considerType').val("1");
            $('#considerType').select2({value: "1"});
            return;
        }
        //优先判断是否统一交易协议，如果是并且是统一交易协议下进行的交易，直接判定原先重大规则，执行情况为签订、续签、实质性变更时，判断为累计重大，审议类型为逐笔，
     else   if (isAgreement == '1' && isAgreementImpl != '4' && isAgreementImpl != ' ') {
            //如果是统一交易协议情况，审议类型改为免审
            /*  if (isAgreementImpl != '4') {*/
            /*    $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
            } else {*/
            $('#considerType').val("3");
            $('#considerType').select2({value: "3"});
            /* }*/
        }else{
            //优先判断重大，如有重大一律逐笔审议
            if(isMajorTrade == '1'){
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
                }
                return;
            }else if((relType == "PSN" && relTradeAmount < 50)||(relType == "ORG" && relTradeAmount < 500)){
                $('#considerType').val("1");
                $('#considerType').select2({value: "1"});
                return;
            }else{
                $('#considerType').val("2");
                $('#considerType').select2({value: "2"});
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


    //根据所选业务类型判断一些字段是否为必填
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
    }

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
            if (fileLast != 'pdf') {
                alert("请上传pdf文件");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/ins/traInsenclosure/uploadFiles?businessType=05', //上传文件的请求路径必须是绝对路径
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function (XMLHttpRequest) {
                //alert('远程调用开始...');
                $("#loading").html("<p style=\"color: #1e90ff\">正在上传，请稍后操作...</p>");
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
            "traSubName":traSubName
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
        /**实际交易金额*/
        /*var pracTradeAmount = $('#pracTradeAmount').val();*/
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
        //判断是否控股子公司，显示隐藏相关标签
        var val = $('#isHoldingSubsidiary').val();
        HoldingSubsidiarySub(val);
        //判断是否统一交易协议，显示隐藏相关标签
        var val = $('#isAgreement').val();
        isAgreement(val);
        //遍历交易对手数组并且在页面展示交易对手信息
        var relList = ${traService.relInfoListStr};
        if (relList.length == 0) {
            $("#addInfos").trigger("click");
        } else {
            for (var i = 0; i < relList.length; i++) {
                addInfos(relList[i]);
                isConsiderType();
                isGreatTrade();
            }
        }
        //审批
        if (opt == 'send') {
            $("#save2").css("display", "none");
            $("#docTable_tb_exportIn").css("display", "none");
            $("#docTable_tb_removeDoc").css("display", "none");
            $("#return").css("display", "none");
        }

        if (opt == 'view') {
            if ('${isConfirm}' != 'true') {
                $("#docTable_tb_exportIn").css("display", "none");
                $("#docTable_tb_removeDoc").css("display", "none");
                $("#save2").css("display", "none");
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

        $('#tranType').val("05");
        $('#tranType').select2({value: "05"});

        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });

       /* if ($('#considerType').val() == 3) {
            $('#contractNoDiv label').prepend("<font color='red'>✶</font>");
            $('#busStartTimeDiv label').prepend("<font color='red'>✶</font>");
            $('#financialImpactDiv label').prepend("<font color='red'>✶</font>");
        }*/

        //审批页面隐藏返回按钮
        if('handler' == '${optNoe}'){
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
        //formItems
        var itemStr = ' <div class="form-group " id="' + item + '" >\n' +
            '   <div class="form-group ">\n' +
            '       <div class="form-group  col-xs-6">\n' +
            '           <label  class="control-label col-xs-4"><font color="red">✶</font>关联方名称</label>\n' +
            '           <div class="  col-xs-8">\n' +
            '           <input type="text" onclick="relNameSelect(' + relType + ',' + relId + ',' + relName + ',' + totalNotCleanTradeAmount + ',' + relNumber + ','+ certTypes + ',' + certType + ','+ tradeAmount + ')" class="jep-input form-control " id="' + relName + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relName;
        }
        itemStr = itemStr + '"  >\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div class="form-group  col-xs-5">\n' +
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
            '           <label  class="control-label col-xs-4"><font color="red">✶</font>关联方证件号码</label>\n' +
            '           <div class="  col-xs-8">\n' +
            '           <input type="text" disabled="disabled" class="jep-input form-control " id="' + relNumber + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.relNumber;
        }
        itemStr = itemStr + '">\n' +
            '           </div>\n' +
            '       </div>\n' +
            '       <div id="yearDiv" class="form-group  col-xs-5">\n' +
            '              <label  class="control-label col-xs-5 " title="">本年度与该关联方发生的关联交易累计金额（万元）</label>\n' +
            '              <div class="  col-xs-7">\n' +
            '                   <input type="text" disabled="disabled" data-fv-notempty="true"  class="jep-input form-control" id="' + totalNotCleanTradeAmount + '" value = "';
        if (relInfo != undefined && relInfo != null && relInfo != "") {
            itemStr = itemStr + relInfo.totalNotCleanTradeAmount;
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
        if(relInfo != undefined && relInfo != null && relInfo != ""){
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

    function amount(){

    }

    //判断是否是统一交易协议，统一交易协议展示统一交易协议执行情况
    function isAgreement(value) {
        if(value=='0'){
            $('#isAgreementImplDiv ').css('display', 'none');
            $('#agreementNumDiv ').css('display', 'none');

        }else if(value=='1'){
            $('#isAgreementImplDiv ').css('display', 'block');
            $('#agreementNumDiv ').css('display', 'block');
            if ($('#isAgreementImplDiv label font').length == 0) {
                $('#isAgreementImplDiv label').prepend("<font color='red'>✶</font>");
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

    function isAgreementImpl(value) {
        //如果是统一交易协议下情况，需要给统一交易协议编号加上必输标记
        if(value=='4'){
            $('#agreementNumDiv label ').prepend("<font color='red'>✶</font>");
        }else{
            $('#agreementNumDiv label font').remove();
        }
        /*var IsAgreement = $("#isAgreement").val();
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

        }*/
    }

    //v合同金额，v1实际交易金额，v2关联交易金额
    function copyAmount() {
        var v = $('#contractAmount');
        var v1 = $('#pracTradeAmount');
        var v2 = $('#relTradeAmount');
        if (v.val() == undefined ||v.val() == '') {
            alert("请输入合同金额！");
            return;
        }
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
</script>
</body>
</html>
