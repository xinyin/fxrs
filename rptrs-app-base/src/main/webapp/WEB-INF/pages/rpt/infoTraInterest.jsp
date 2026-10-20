<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表（保险）-利益转移类</title>
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

        textarea {
            resize: none; /* Disables resizability */
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
    <bc:ibox id="traInterestBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="relId" name="relId" value="${traInterest.relId}"/>
            <bf:hidden id="relNumber" name="relNumber" value="${traInterest.relNumber}"/>
            <bf:hidden id="relType" name="relType" value="${traInterest.relType}"/>
            <bf:hidden id="pid" name="pid" value="${traInterest.pid}"/>
            <bf:hidden id="ruleResult" name="ruleResult" value=""/>
            <bf:hidden id="total" name="total" value="${traInterest.totolTradeAmount}"/>
            <bf:hidden id="amount" name="amount" value="${traInterest.contractAmount}"/>
            <bf:hidden id="transNum" name="transNum" value="${traInterest.transNum}"/>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label cls="required" text="交易主体名称" col="5" formControl="true"/>
                    <bf:select id="traSubName" name="traSubName" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-subjectName"
                               disabled="${opt == 'view'}" value="${traInterest.traSubName}">
                        <bf:option text="请选择"></bf:option>
                        <be:event type="select">
                        </be:event>
                    </bf:select>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <bc:label cls="required" text="交易对手名称" col="5" formControl="true"/>
                    <bf:text id="relName" name="relName" col="7" click="relNameSelect()"
                             value="${traInterest.relName}" disabled="${opt == 'view'}">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="交易主体是否为中再资产的控股子公司">
                        <bc:label cls="required" text="是否为控股子公司" col="5" formControl="true"/>
                        <bf:select id="isHoldingSubsidiary" name="isHoldingSubsidiary" col="7"
                                   value="${traInterest.isHoldingSubsidiary}" disabled="${opt == 'view'}"
                                   dataSource="fixedDict" url="IsStatusDict">
                            <be:event type="select">
                                HoldingSubsidiarySub(e.target.value);
                            </be:event>
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4" id="j">
                    <div title="控股子公司全称" >
                        <bc:label cls="required" text="控股子公司全称" col="5" formControl="true"/>
                        <bf:text id="holdingSubsidiaryName" name="holdingSubsidiaryName" col="7"
                                 value="${traInterest.holdingSubsidiaryName}" disabled="true">
                            <%--<bv:notEmpty/>--%>
                        </bf:text>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4" id="q">
                    <div title="控股子公司证件类型" >
                        <bc:label cls="required" text="控股子公司证件类型" col="5" formControl="true"/>
                        <bf:select id="holdingSubsidiaryType" name="holdingSubsidiaryType" col="7"
                                   value="${traInterest.holdingSubsidiaryType}" disabled="true"
                                   dataSource="custom" url="sys/dictItem/getDictItems/BUS-ORGIdentifyType">
                        </bf:select>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4"  id="k">
                    <div title="控股子公司证件号码">
                        <bc:label cls="required" text="控股子公司证件号码" col="5" formControl="true"/>
                        <bf:text id="holdingSubsidiaryNumber" name="holdingSubsidiaryNumber" col="7"
                                 value="${traInterest.holdingSubsidiaryNumber}" disabled="true">
                            <%--<bv:notEmpty/>--%>
                        </bf:text>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div title="交易对手上年末总资产（万元）">
                        <bc:label cls="required" text="交易对手上年末总资产（万元）" col="5" formControl="true"/>
                        <bf:text id="totalAssets" name="totalAssets" col="7" value="${traInterest.totalAssets}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="交易类型">
                        <bc:label text="交易类型" col="5" formControl="true"/>
                        <bf:select id="tranType" name="tranType" col="7" value="04"
                                   dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-tranType" disabled="true">
                        </bf:select>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="业务类型">
                        <bc:label cls="required" text="业务类型" col="5" formControl="true"/>
                        <bf:select id="busType" name="busType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INT-BusinessType"
                                   includes="" value="${traInterest.busType}" disabled="${opt == 'view'}">
                            <be:event type="select">
                                showOrHidden(e.target.value)
                            </be:event>
                            <%--<bf:option text="请选择" value="0"/>--%>
                            <bf:option text="请选择" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div title="合同/交易编号">
                        <bc:label cls="required" text="合同/交易编号" col="5" formControl="true"/>
                        <bf:text id="contractNo" name="contractNo" col="7" value="${traInterest.contractNo}"
                                 disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="关联交易金额（万元）">
                        <bc:label cls="required" text="关联交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="contractAmount" name="contractAmount" col="7" change="isInRoad(),isGreatTrade()"
                                 value="${traInterest.contractAmount}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>
                        </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="与关联方签署交易协议的日期">
                        <bc:label cls="required"  text="合同/交易发生日期" col="5" formControl="true"/>
                        <bf:datebox id="tranDate" name="tranDate" col="7" value="${traInterest.tranDateString}"
                                    disabled="${opt == 'view'}">
                            <bv:callback message="请填写必填项目" callback="notEmpt"/>
                        </bf:datebox>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div title="关联交易发生的起始日期">
                        <bc:label cls="required" text="业务起始日期" col="5" formControl="true"/>
                        <bf:datebox id="busStartTime" name="busStartTime" col="7"
                                    value="${traInterest.busStartTimeString}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="关联交易发生的结束日期">
                        <bc:label text="业务终止日期" col="5" formControl="true"/>
                        <bf:datebox id="busEndTime" name="busEndTime" col="7"
                                    value="${traInterest.busEndTimeString}" disabled="${opt == 'view'}">
                            <%--<bv:notEmpty/>--%>
                        </bf:datebox>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="本年度与该关联方累计交易金额（万元）包含：单笔重大+累计金额+免于审议">
                        <bc:label text="本年度与该关联方累计交易金额（万元）" col="5" formControl="true"/>
                        <bf:text id="totolTradeAmount" name="totolTradeAmount" col="7"
                                 value="${traInterest.totolTradeAmount}" disabled="true"
                                 mouseover="">
                        </bf:text>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
<%--                <bf:formGroup col="4">--%>
<%--                    <div title="交易金额（万元）">--%>
<%--                        <bc:label cls="required" text="交易金额（万元）" col="5" formControl="true"/>--%>
<%--                        <bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traInterest.tradeAmount}"--%>
<%--                                 disabled="true">--%>
<%--                            <bv:regexp regexp="^[0-9]+(.[0-9]{1,6})?$" message="请填写正确金额"/>--%>
<%--                        </bf:text>--%>
<%--                    </div>--%>
<%--                </bf:formGroup>--%>

                <bf:formGroup col="4">
                    <div title="交易定价是否为国家规定">
                        <bc:label cls="required" text="交易定价是否为国家规定" col="5" formControl="true"/>
                        <bf:select id="isDealPrice" name="isDealPrice" col="7" dataSource="fixedDict" url="IsStatusDict"
                                   value="${traInterest.isDealPrice}" disabled="${opt == 'view'}">
                            <be:event type="select">
                                isConsiderType();
                            </be:event>
                            <bv:notEmpty/>
                        </bf:select>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="是否现金认购">
                        <bc:label cls="required" text="是否现金认购" col="5" formControl="true"/>
                        <bf:select id="isCashSubscription" name="isCashSubscription" col="7"
                                   dataSource="fixedDict" url="IsStatusDict" value="${traInterest.isCashSubscription}"
                                   disabled="${opt == 'view'}">
                            <be:event type="select">
                                isConsiderType();
                            </be:event>
                        </bf:select>
                        <bv:notEmpty/>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <div title="达到重大关联交易的情形">
                        <bc:label cls="required" text="达到重大关联交易的情形" col="5" formControl="true"/>
                        <bf:select id="majorType" name="majorType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-majorType"
                                   includes=""  value="${traInterest.majorType}" disabled="true">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div title="是否重大关联交易">
                        <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                        <bf:select id="majorTrade" name="majorTrade" col="7" dataSource="fixedDict"
                                   url="IsStatusDict" value="${traInterest.majorTrade}" disabled="true">
                            <bf:option text="-" value=""></bf:option>
                        </bf:select>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="审议类型">
                        <!-- <font color="red" size="5">*</font> -->
                        <bc:label text="审议类型" col="5" formControl="true"/>
                        <bf:select id="considerType" name="considerType" col="7" value="${traInterest.considerType}"
                                   dataSource="custom" url="sys/dictItem/getDictItems/INS-considerType" disabled="true">
                            <bv:notEmpty/>
                        </bf:select>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="前次交易时间" id=1_1 class="Type1" style="display: none">
                        <bc:label cls="required" text="前次交易时间" col="5" formControl="true"/>
                        <bf:datebox id="lastTradeTime" name="lastTradeTime" col="7"
                                    value="${traInterest.lastTradeTime}" disabled="${opt == 'view'}">
                            <bv:notEmpty/>
                        </bf:datebox>
                    </div>
                </bf:formGroup>

                <bf:formGroup col="4">
                    <div title="是否为统一交易协议">
                        <!-- <font color="red" size="5">*</font> -->
                        <bc:label cls="required" text="是否为统一交易协议" col="5" formControl="true"/>
                        <bf:select id="isAgreement" name="isAgreement" col="7" value="${traInterest.isAgreement}"
                                   dataSource="fixedDict" url="IsStatusDict" disabled="${opt == 'view'}">
                            <bf:option text="请选择" value=""></bf:option>
                        </bf:select>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="交易单价">
                        <bc:label cls="required" text="交易价格" col="3" formControl="true"/>
                        <bf:text id="tradePrice" name="tradePrice" col="9" rows="2" multiple="true" value="${traInterest.tradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="交易价格、收益率的定价依据">
                        <bc:label cls="required" text="定价政策" col="3" formControl="true"/>
                        <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" multiple="true" value="${traInterest.pricingPolicy}" disabled="${opt == 'view'}"><bv:notEmpty/><bv:notEmpty/> </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="与关联方之间关联交易的对象">
                        <bc:label cls="required" text="交易标的" col="3" formControl="true"/>
                        <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" multiple="true" value="${traInterest.tradeSubject}" disabled="${opt == 'view'}"><%--<bv:callback message="请填写必填项目" callback="notEm"/>--%><bv:notEmpty/></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="对公司的财务状况及经营成果的描述">
                        <bc:label cls="required" text="对财务状况及经营成果的影响" col="3" formControl="true"/>
                        <bf:text id="financialImpact" name="financialImpact" col="9" rows="2" multiple="true" value="${traInterest.financialImpact}" disabled="${opt == 'view'}"><%--<bv:callback message="请填写必填项目" callback="notEm"/>--%><bv:notEmpty/></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="关联方之间发生关联交易的标的或其资产情况。" id="1_3" class="Type1" style="display: none">
                        <bc:label cls="required" text="标的资产情况" col="3" formControl="true"/>
                        <bf:text id="tradeSubjectAssets" name="tradeSubjectAssets" col="9" rows="2" multiple="true" value="${traInterest.tradeSubjectAssets}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="前次交易价格" id="1_2" class="Type1" style="display: none">
                        <bc:label cls="required" text="前次交易价格" col="3" formControl="true"/>
                        <bf:text id="lastTradePrice" name="lastTradePrice" col="9" rows="2" multiple="true" value="${traInterest.lastTradePrice}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="标的情况" id="3_1" class="Type3" style="display: none">
                        <bc:label cls="required" text="标的情况" col="3" formControl="true"/>
                        <bf:text id="subjectSituation" name="subjectSituation" col="9" rows="2" multiple="true" value="${traInterest.subjectSituation}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="与关联方之间提供担保时的担保条件。" id="2_1" class="Type2" style="display: none">
                        <bc:label cls="required" text="担保条件" col="3" formControl="true"/>
                        <bf:text id="guaranteeConditions" name="guaranteeConditions" col="9" rows="2" multiple="true" value="${traInterest.guaranteeConditions}" disabled="${opt == 'view'}"><bv:notEmpty/></bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <div title="除上述关键信息外，其他需要说明的重要或特殊的事项" >
                        <bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
                        <bf:text id="remark" name="remark" col="9" rows="2" multiple="true" value="${traInterest.remark}" disabled="${opt == 'view'}"><bv:notEmpty/> </bf:text>
                    </div>
                </bf:formGroup>
            </bf:formGroup>

            <%--<bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <div title="其他事项说明" id="1_3" class="Type6" style="display: block">
                        <bc:label cls="required" text="其他事项说明" col="3" formControl="true"/>
                        <bf:text id="remark" name="remark" col="9" rows="2"
                                 multiple="true" value="${traInterest.remark}" disabled="${opt == 'view'}">
                        </bf:text>
                    </div>
                </bf:formGroup>
            </bf:formGroup>--%>

            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/ins/traInsenclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traInterest.transNum}">
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
                <div class="col-sm-4 col-sm-offset-5">
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
        var dataSources = '${traInterest.dataSources}';
        //生效时间
        if (fieId1 == "") {
            if ((dataSources != "3") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
    }

    //根据业务类型显示对应字段
    function showOrHidden(value) {
       /* var val = $('#busType option:selected').val();//选中的值*/

        if (value == '1' || value == '2') {
            $(".Type1").css("display", "block");
        } else {
            $(".Type1").css("display", "none");
        }

        if (value == '4') {
            $(".Type2").css("display", "block");
            $(".Type6").css("display", "none");
        } else {
            $(".Type2").css("display", "none");
        }

        if (value == '3' || value == '4' || value == '8' || value == '9' || value == '10') {
            $(".Type3").css("display", "block");
            $(".Type6").css("display", "none");
        } else {
            $(".Type3").css("display", "none");
            $(".Type6").css("display", "block");
        }

        /*if (val == 1){
            //如果选择为"出售资产"
            /!*$('#box').html("");
            var clone = $('#1').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#1').css("display", "block");*!/
            $("#befortime").show();
        }else if (val == 2){
            //如果选择为"租赁资产"
            /!*$('#box').html("");
            var clone = $('#2').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#2').css("display", "block");*!/
            $("#befortime").show();
        } else if (val == 3){
            //如果选择为"权力转让"
            /!*$('#box').html("");
            var clone = $('#3').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#3').css("display", "block");*!/
            $("#befortime").hide();
        } else if (val == 4){
            //如果选择为"担保"
            /!*$('#box').html("");
            var clone = $('#4').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#4').css("display", "block");*!/
            $("#befortime").hide();
        } else {
            //如果为请选择的话就显示默认
            /!*$('#box').html("");
            var clone = $('#0').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#0').css("display", "block");*!/
            $("#befortime").hide();
        }*/
    }

    /*$(function () {
        var val = $('#busType option:selected').val();//选中的值
        if (val == 1){
            //如果选择为"出售资产"
            $('#box').html("");
            var clone = $('#1').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#1').css("display", "block");
        }else if (val == 2){
            //如果选择为"租赁资产"
            $('#box').html("");
            var clone = $('#2').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#2').css("display", "block");
        } else if (val == 3){
            //如果选择为"权力转让"
            $('#box').html("");
            var clone = $('#3').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#3').css("display", "block");
        } else if (val == 4){
            //如果选择为"担保"
            $('#box').html("");
            var clone = $('#4').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#4').css("display", "block");
        } else {
            //如果为请选择的话就显示默认
            $('#box').html("");
            var clone = $('#0').clone(true) //结构、事件与数据都克隆
            $('#box').append(clone);
            $('#0').css("display", "block");
        }
    });*/

    //查询交易对手相关方法
    function relNameSelect() {
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
                url: '${base}/ins/traInvestment/relNameSelect/'+ traSubName,
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
    //     //统一交易协议业务
    //     var unitfTradeAgrmentInterest = $('#unitfTradeAgrmentInterest').val();
    //     //统一交易协议的签订、续签、实质性变更也是逐笔审议
    //     if (unitfTradeAgrmentInterest != null && unitfTradeAgrmentInterest != undefined && unitfTradeAgrmentInterest != '') {
    //         if (unitfTradeAgrmentInterest == "01") {
    //             $('#considerType').val("3");
    //             $('#considerType').select2({value: "3"});
    //             return;
    //         }
    //     }
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
    /*function HoldingSubsidiarySub(value) {
        var value = value
        if (value == '1') {
            $('#holdingSubsidiaryNumber').attr("disabled", "false");
            $('#holdingSubsidiaryName').attr("disabled", "false");
            return ;
        }
        if (value == '0') {
            $('#holdingSubsidiaryNumber').removeAttr("disabled");
            $('#holdingSubsidiaryName').removeAttr("disabled");
        }

    };*/

    //判断是否为控股子公司来决定子公司名称证件号能不能添加
    function HoldingSubsidiarySub(value) {
        var value = value
        if(value == '1'){
            $('#holdingSubsidiaryNumber').attr("disabled","false");
            $('#holdingSubsidiaryName').attr("disabled","false");
            $('#holdingSubsidiaryType').attr("disabled","false");

        }
        if(value == '0') {
            $('#holdingSubsidiaryNumber').removeAttr("disabled");
            $('#holdingSubsidiaryName').removeAttr("disabled");
            $('#holdingSubsidiaryType').removeAttr("disabled");

        }

    };
    //根据所选业务类型判断一些字段是否为必填
    function notEm(value, validator, $field) {
        var fieId1 = $field[0].id;
        var value1 = value;
        var busType = $('#busType').val();
        var unitfTradeAgrmentInterest = $('#unitfTradeAgrmentInterest').val();
        //交易标的
        if (fieId1 == "tradeSubject") {
            if ((busType == "240" || busType == "244"
                || busType == "245" || busType == "246") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        //前次交易价格
        if (fieId1 == "lastTradePrice") {
            if ((busType == "237" || busType == "238") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        //是否为统一交易协议
        if (fieId1 == "isAgreement") {
            if ((unitfTradeAgrmentInterest == "01") && (value1 == null || value1 == " ")) {
                return false;
            } else {
                return true;
            }
        }
        //标的资产情况
        if (fieId1 == "tradeSubjectAssets") {
            if ((busType == "237" || busType == "238" || busType == "239") && (value1 == null || value1 == "")) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

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
        $('#tranType').val("04");
        $('#tranType').select2({value: "04"});
        var busType = $('#busType').val();
        showOrHidden(busType)

    });

    function onSelect() {
        var proName = $("input[name=proName]").val();
        $.ajax({
            type: "post",
            url: "${base}/ins/traInterest/selectbuinessL",
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

    function toConfirm() {
        var pid = ${pid}
            $.ajax({
                url: "${base}/ins/traInterest/confirm/" + pid,
                method: "GET",
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        window.location = '${base}/ins/traInterest/list/';
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
            url: '${base}/ins/traInsenclosure/uploadFiles?businessType=04', //上传文件的请求路径必须是绝对路劲
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

    /*function changeNext(val) {
        if (val == '0') {
            $("#approvalNumDiv").css("display", "none")
            $("#securitiesTypeDiv").css("display", "block")
        } else if (val == '1') {
            $("#approvalNumDiv").css("display", "block")
            $("#securitiesTypeDiv").css("display", "none")
        }
    }*/

    $("#relNumber").blur(function () {
        var relNumber = $("#relNumber").val();
        var relName = $("#relName").val();
        if ((relNumber !== '' && relNumber !== undefined) || (relName !== '' && relName !== undefined)) {
            infoBlur(relNumber, relName);
        }
    });

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
                    // $("#majorTrade").val(majorTrade);
                    // $("#majorTrade").select2({value:(majorTrade)});
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
        // if(traSubName != '0001' && traSubName != '0001-ZZHK'){
        //     return;
        // }
        var relId = $('#relId').val();
        var relType = $('#relType').val();
        var tradeAmount = $('#contractAmount').val();
        var totolTradeAmount = $('#total').val();
        //是否是现金认购
        var isCashSubscription = $('#isCashSubscription').val();

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
            isCashSubscription:isCashSubscription
        };
        $.ajax({
            url: '${base}/ins/traInterest/GreatCheck',
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
                    isConsiderType();
                } else {
                    alert(data.msg);
                }
            }
        });
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

</script>
<script type="text/javascript">

    //加上红点
    $(document).ready(function () {
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
</script>
</body>
</html>
