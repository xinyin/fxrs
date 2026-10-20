<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>TraCredit</title>
    <style>
        .form-group .col-xs-4 {
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

        .select2-dropdown, .has-success .select2-selection {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
        }
    </style>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traCreditBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${traCredit.pid}"/>
            <bf:hidden id="status" name="status" value="${traCredit.status}"/>
            <bf:hidden id="creditId" name="creditId" value="${traCreditBzr.creditId}"/>
            <bf:hidden id="bzrId" name="bzrId" value="${traCreditBzr.pid}"/>
            <bf:hidden id="transNum" name="transNum" value="${traCredit.transNum}"/>
            <bf:hidden id="relId" name="relId" value="${relId}"/>
            <bf:hidden id="relType" name="relType" value="${relType}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="内部人名称" col="5" formControl="true"/>
                    <bf:text id="relatedName" name="relatedName" col="7" value="${traCredit.relatedName}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件类型" col="5" formControl="true"/>
                    <bf:select id="identifyType" name="identifyType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" col="7"
                               value="${traCredit.identifyType}">
                        <be:event type="select">
                            var input = $("#__LIKES_identifyType");
                            input.focus();
                            input.blur();
                        </be:event>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="证件号码" col="5" formControl="true"/>
                    <bf:text id="identNumber" name="identNumber" col="7" value="${traCredit.identNumber}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="持股占比" col="5" formControl="true"/>
                    <bf:text id="shareholdingProportion" name="shareholdingProportion" col="7"
                             value="${traCredit.shareholdingProportion}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="表决权占比" col="5" formControl="true"/>
                    <bf:text id="voteProportion" name="voteProportion" col="7" value="${traCredit.voteProportion}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="产品名称" col="5" formControl="true"/>
                    <bf:text id="productCode" name="productCode" col="7" value="${traCredit.productCode}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="一级业务种类" col="5" formControl="true"/>
                    <bf:text id="manageOrg" name="manageOrg" col="7" value="${traCredit.manageOrg}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="二级业务种类" col="5" formControl="true"/>
                    <bf:text id="manageOrg" name="manageOrg" col="7" value="${traCredit.manageOrg}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="三级业务种类" col="5" formControl="true"/>
                    <bf:text id="manageOrg" name="manageOrg" col="7" value="${traCredit.manageOrg}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="四级业务种类" col="5" formControl="true"/>
                    <bf:text id="manageOrg" name="manageOrg" col="7" value="${traCredit.manageOrg}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务表内外属性" col="5" formControl="true"/>
                    <bf:text id="manageOrg" name="manageOrg" col="7" value="${traCredit.manageOrg}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务条线" col="5" formControl="true"/>
                    <bf:text id="manageOrg" name="manageOrg" col="7" value="${traCredit.manageOrg}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易机构" col="5" formControl="true"/>
                    <bf:text id="manageOrg" name="manageOrg" col="7" value="${traCredit.manageOrg}" disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易类型" col="5" formControl="true"/>
                    <bf:select id="tranType" name="tranType" dataSource="custom" includes="1,4"
                               url="sys/dictItem/getDictItems/BUS-BusinessType" col="7" value="${traCredit.businessType}">
                        <%--<bf:option text="ALL" value=""></bf:option>--%>
                        <be:event type="select">funName(e.target.value);</be:event>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务类型" col="5" formControl="true"/>
                    <bf:select id="businessType" name="businessType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-CreditCategory" col="7" value="${traCredit.businessType}">
                        <be:event type="select">funName(e.target.value);</be:event>
                        <bv:notEmpty/>
                    </bf:select>
                </bc:col>
                <div id="shouxindiv">
                <div id="tranScopeDiv">
                    <bc:col col="4">
                        <bc:label text="授信范围" col="5" formControl="true"/>
                        <bf:select id="tranScope" name="tranScope" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-CreditCategory" col="7"
                                   value="${traCredit.tranScope}"
                                   multiple="true" excludes="comprehensive">
                        </bf:select>
                    </bc:col>
                </div>
                <bc:col col="4">
                    <bc:label text="授信额度" col="5" formControl="true"/>
                    <bf:text id="creditAmount" name="creditAmount" col="7" value="${traCredit.creditAmount}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易金额" col="5" formControl="true"/>
                    <bf:text id="tranPrice" name="tranPrice" col="7" value="${traCredit.tranPrice}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务交易余额" col="5" formControl="true"/>
                    <bf:text id="busTraBalance" name="busTraBalance" col="7" value="${traCredit.busTraBalance}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="授信编号" col="5" formControl="true"/>
                    <bf:text id="creditNumber" name="creditNumber" col="7" value="${traCredit.creditNumber}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="是否为用信业务" col="5" formControl="true"/>
                    <bf:select id="isLetter" name="isLetter" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-isNo" col="7"
                               value="${traCredit.isLetter}">
                        <bv:notEmpty/>
                        <be:event type="select">credit(e.target.value);</be:event>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易合同金额" col="5" formControl="true"/>
                    <bf:text id="traContractAmount" name="traContractAmount" col="7"
                             value="${traCredit.traContractAmount}">
                    </bf:text>
                </bc:col>
                <div id="letter">
                        <%--                    <bc:col col="4">--%>
                        <%--                        <bc:label text="用信额度" col="5" formControl="true"/>--%>
                        <%--                        <bf:text id="creditLimit" name="creditLimit" col="7" value="${traCredit.creditLimit}">--%>
                        <%--                        </bf:text>--%>
                        <%--                    </bc:col>--%>
                    <bc:col col="4">
                        <bc:label text="交易价格类型" col="5" formControl="true"/>
                        <bf:select id="traPriceType" name="traPriceType" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-traPrice" col="7"
                                   value="${traCredit.traPriceType}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="利率类型" col="5" formControl="true"/>
                        <bf:select id="rateType" name="rateType" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-rateType" col="7"
                                   value="${traCredit.rateType}">
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="交易价格" col="5" formControl="true"/>
                        <bf:text id="traPrice" name="traPrice" col="7" value="${traCredit.traPrice}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="浮动利率基准" col="5" formControl="true"/>
                        <bf:text id="floatRateBench" name="floatRateBench" col="7" value="${traCredit.floatRateBench}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="浮动利率利差" col="5" formControl="true"/>
                        <bf:text id="floatInterestRateSpread" name="floatInterestRateSpread" col="7"
                                 value="${traCredit.floatInterestRateSpread}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="浮动利率折扣率" col="5" formControl="true"/>
                        <bf:text id="floatRateDiscountRate" name="floatRateDiscountRate" col="7"
                                 value="${traCredit.floatRateDiscountRate}">
                        </bf:text>
                    </bc:col>
                </div>
                <bc:col col="4">
                    <bc:label text="银行内部授信审批编号" col="5" formControl="true"/>
                    <bf:text id="internalApplyNo" name="internalApplyNo" col="7" value="${traCredit.internalApplyNo}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="授信批准日期" col="5" formControl="true"/>
                    <bf:datebox id="applyOverTime" name="applyOverTime" col="7"
                                value="${traCredit.applyOverTime}">
                        <bv:notEmpty/>
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="授信期限" col="5" formControl="true"/>
                    <bf:text id="applyTime" name="applyTime" col="7" value="${traCredit.applyTime}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="授信起始日期" col="5" formControl="true"/>
                    <bf:datebox id="creditStartDate" name="creditStartDate" col="7"
                                value="${traCredit.creditStartDate}">
                        <bv:notEmpty/>
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="授信到期日" col="5" formControl="true"/>
                    <bf:datebox id="creditEndDate" name="creditEndDate" col="7" value="${traCredit.creditEndDate}"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="年利率/费率%" col="5" formControl="true"/>
                    <bf:text id="rate" name="rate" col="7" value="${traCredit.rate}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="合同号/借据号" col="5" formControl="true"/>
                    <bf:text id="contractNo" name="contractNo" col="7" value="${traCredit.contractNo}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="合同签署日期" col="5" formControl="true"/>
                    <bf:datebox id="contractSignDate" name="contractSignDate" col="7" format="date"
                                value="${traCredit.contractSignDate}">
                        <bv:notEmpty/>
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="合同有效期" col="5" formControl="true"/>
                    <bf:datebox id="validityContract" name="validityContract" col="7"
                                value="${traCredit.validityContract}">
                        <bv:notEmpty/>
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="担保方式" col="5" formControl="true"/>
                    <bf:select id="guaraMode" name="guaraMode" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-GuaranteeMode" col="7"
                               value="${traCredit.guaraMode}">
                        <%--<bf:option text="ALL" value=""></bf:option>--%>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人名称" col="5" formControl="true"/>
                    <bf:text id="guarantor" name="guarantor" col="7" value="${traCredit.guarantor}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人证件类型" col="5" formControl="true"/>
                    <bf:select id="bzrIdentifyType" name="bzrIdentifyType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-IdentifyType" col="7"
                               value="${traCredit.bzrIdentifyType}">
                        <%--<bf:option text="ALL" value=""></bf:option>--%>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证人证件号" col="5" formControl="true"/>
                    <bf:text id="bzrIdentNumber" name="bzrIdentNumber" col="7"
                             value="${traCredit.bzrIdentNumber}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证金金额" col="5" formControl="true"/>
                    <bf:text id="securitDeposit" name="securitDeposit" col="7" value="${traCredit.securitDeposit}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="保证金比例" col="5" formControl="true"/>
                    <bf:text id="marginRatio" name="marginRatio" col="7" value="${traCredit.marginRatio}"><span>%</span>
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="质押的银行存单金额" col="5" formControl="true"/>
                    <bf:text id="bankDeposit" name="bankDeposit" col="7" value="${traCredit.bankDeposit}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="国债金额" col="5" formControl="true"/>
                    <bf:text id="nationalDebt" name="nationalDebt" col="7" value="${traCredit.nationalDebt}">
                    </bf:text>
                </bc:col>

                <%--<bf:formGroup hr="dashed" hrp="bottom">--%>
                <bc:col col="12">
                    <bc:label text="担保品信息" col="0" formControl="true"/>
                    <bf:text id="collateral" name="collateral" col="7" value="${traCredit.collateral}" multiple="true">
                    </bf:text>
                </bc:col>
                <bc:col col="12">
                    <bc:label text="授信方案" col="0" formControl="true"/>
                    <bf:text id="creditPlan" name="creditPlan" col="7"
                             multiple="true" value="${traCredit.creditPlan}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            </div>
            <div id="othersdiv">
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="交易发生金额" col="4" formControl="true"/>
                        <bf:text id="tranAmount" name="tranAmount" col="5" value="${tranAmount}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="交易价格" col="5" formControl="true"/>
                        <bf:text id="tranPrice" name="tranPrice" col="7" value="${traCredit.tranPrice}"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="交易标的" col="4" formControl="true"/>
                        <bf:text id="tranTarget" name="tranTarget" col="5" value="${tranTarget}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="合同号/借据号" col="4" formControl="true"/>
                        <bf:text id="contractNo" name="contractNo" col="5" value="${contractNo}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="合同签署日期" col="5" formControl="true"/>
                        <bf:datebox id="contractSignDate1" name="contractSignDate" col="7" format="date"
                                    value="${traCredit.contractSignDate}">
                        </bf:datebox>
                    </bc:col>
                    <%--<bc:col col="4">
                        <bc:label text="合同签署日期" col="5" formControl="true"/>
                        <bf:datebox id="contractSignDate" name="contractSignDate" col="7" value="${traCredit.contractSignDate}"/>
                    </bc:col>--%>
                    <%--<bc:col col="4">
                        <bc:label text="合同有效期" col="5" formControl="true"/>
                        <bf:datebox id="validityContract" name="validityContract" col="7" value="${traCredit.validityContract}"/>
                    </bc:col>--%>
                    <bc:col col="4">
                        <bc:label text="合同有效期" col="5" formControl="true"/>
                        <bf:datebox id="validityContract1" name="validityContract" col="7"
                                    value="${traCredit.validityContract}">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="年利率/费率%" col="5" formControl="true"/>
                        <bf:text id="rate" name="rate" col="7" value="${traCredit.rate}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="业务起始时间" col="5" formControl="true"/>
                        <bf:datebox id="businessStartTime" name="businessStartTime" col="7" value="${traCredit.businessStartTime}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="审批通过时间" col="5" formControl="true"/>
                        <bf:datebox id="approvalTime" name="approvalTime" col="7" value="${traCredit.approvalTime}"/>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="业务结束时间" col="5" formControl="true"/>
                        <bf:datebox id="businessEndTime" name="businessEndTime" col="7" value="${traCredit.businessEndTime}"/>
                    </bc:col>
                    <bc:col col="6">
                        <bc:label text="资产评估价" col="4" formControl="true"/>
                        <bf:text id="appraisalAssets" name="appraisalAssets" col="5" value="${appraisalAssets}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="定价政策" col="0" formControl="true"/>
                        <bf:text id="tranTarget" name="tranTarget" col="7"
                                 multiple="true" value="${traCredit.tranTarget}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="12">
                        <bc:label text="其他交易信息" col="0" formControl="true"/>
                        <bf:text id="remark" name="remark" col="7"
                                 multiple="true" value="${traCredit.remark}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </div>
            <div id="tableDiv" style="width:95%;margin:0 auto">
                <bc:table id="docTable" idField="pid" url="${base}/bus/traEnclosure" multiple="true"
                          selectFirst="false" queryUrl="queryList?transNum=${traCredit.transNum}">
                    <bc:toolbar id="exportIn" text="上传文件" type="button" handler="exportIn"/>
                    <bc:toolbar id="removeDoc" text="删除" type="button" handler="removeDoc"/>
                    <bc:toolbar id="viewFile" text="查看文件" type="button" handler="viewFile"/>
                    <bc:column field="pid" title="主键" width="100" visible="false"/>
                    <bc:column field="relatedName" title="文件名" width="100"/>
                    <bc:column field="fileType" title="文件类型" width="100"/>
                    <bc:column field="fileSize" title="大小" width="100"/>
                    <bc:column field="uploadOrg" title="上传机构" width="100"/>
                    <bc:column field="uploadDep" title="上传部门" width="100"/>
                    <bc:column field="createTime" title="上传时间" width="100"/>
                </bc:table>
            </div>

            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5" style="padding: 3% 0 0 0">
                    <bc:button type="danger" text="确认提交" icon="save" click="sub()">
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/traCredit/list';
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
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="../../static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">选择文件
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
    $(document).ready(function () {
        var opt = $('#opt').val();
        var businessType = $('#businessType').val();
        if (opt == 'edit') {
            if (businessType == 'comprehensive') {
                $('#tranScopeDiv').show();
            } else {
                $('#tranScopeDiv').hide();
            }
        } else {
            $('#tranScopeDiv').hide();
        }

        var isLetter = $('#isLetter').val();
        if (isLetter != '1') {
            $('#letter').hide();
        }
    });
    //交易类型（授信业务、非授信业务）隐藏
    $('#tranType').change(function(){
        var val = $('#tranType').val();
        // alert(val)
        if(val === '1'){
            //授信业务 隐藏非授信业务
            $('#othersdiv').hide()
            $('#shouxindiv').show()
        }else if(val === '4'){
            //非授信业务隐藏授信业务
            $('#shouxindiv').hide()
            $('#othersdiv').show()
        }

    });

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

    $("#upload").click(function () {
        var obj = document.getElementById("file");
        for (var i = 0; i < obj.files.length; i++) {
            var temp = obj.files[i].name;
            var temArr = temp.split('.');
            var fileLast = temArr[temArr.length - 1];
            if (fileLast != 'pdf') {
                $.messager.alert("请上传pdf文件");
                return;
            }
        }
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            type: 'post',
            url: '${base}/bus/traEnclosure/uploadFile', //上传文件的请求路径必须是绝对路劲
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                $("#close").click();
                $.messager.alert(data.msg);
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
                }
            },
            error: function () {
                $("#close").click();
                $.messager.alert("上传失败,请重试");
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

    //查看文件
    viewFile = function () {
        //选中的文件
        var pid = getIdSelections();
        //获得该条数据
        var row = getselectData();
        var fileName = row[0].fileName;
        if (pid == '' || pid == undefined) {
            $.messager.alert("请选中一个文件");
            return;
        }
        if (pid.indexOf(',') != -1) {
            $.messager.alert("只能选中一个文件");
            return;
        }
        window.open("${base}/static/js/pdfjs-2.3.200-dist/web/viewer.html?file="
            + encodeURIComponent('${base}/bus/traEnclosure/preview?pid=' + pid + '&fileName=' + fileName + '.pdf'));
    }
    //移除文件
    removeDoc = function () {
        var rows = getData();//删除前数据
        var pids = getIdSelections();//删除数据主键
        var selectRows = getselectData();//选中的数据
        if (selectRows.length == 0) {
            $.messager.alert("请先选中数据");
            return;
        }
        if (confirm("确认删除？")) {
            $.ajax({
                type: 'get',
                url: '${base}/bus/traEnclosure/removeFile/' + pids,
                dataType: 'json',
                success: function (res) {
                    $.messager.alert(res.msg);
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

    function funName(val) {
        if ('comprehensive' == val) {
            $('#tranScopeDiv').show();

        } else {
            $('#tranScopeDiv').hide();
        }
    }

    function credit(val) {
        if ('1' == val) {
            $('#letter').show();
        } else {
            $('#letter').hide();
        }
    }

    //输入身份证反显内部人

    $("#identNumber").blur(function(){
        var identNumber = $(this).val();
        // console.log(identNumber)
        var obj = {
            'identNumber': identNumber
        }
        $.ajax({
            type: 'post',
            url: '${base}/bus/traInternaiTran/geRelOrgInfo',
            dataType: 'json',
            data: obj,
            success: function (data) {
                console.log(data)
                if (data.success != undefined) {
                    if (data.success) {
                        var orgName = data.orgName;
                        var shareholdingProportion = data.shareholdingProportion;
                        var voteProportion = data.voteProportion;
                        $('#relatedName').val(orgName);
                        $('#shareholdingProportion').val(shareholdingProportion);
                        $('#voteProportion').val(voteProportion);
                        /*$('#shareholdingProportion').val(shareholdingProportion);*/
                    }
                    if (!data.success) {
                        $.messager.alert(data.msg);
                        $("#relatedName").val('');
                    }
                }
            }
        })
    });


    function getInfo() {

        var identNumber = $("#identNumber").val();
        var obj = {
            'identNumber': identNumber
        }
        $.ajax({
            type: 'post',
            url: '${base}/bus/traInternaiTran/geRelOrgInfo',
            dataType: 'json',
            data: obj,
            success: function (data) {
                if (data.success != undefined) {
                    if (data.success) {
                        var orgName = data.orgName;
                        var shareholdingProportion = data.shareholdingProportion;
                        var voteProportion = data.voteProportion;
                        $('#relatedName').val(orgName);
                        $('#shareholdingProportion').val(shareholdingProportion);
                        $('#voteProportion').val(voteProportion);
                        /*$('#shareholdingProportion').val(shareholdingProportion);*/
                    }
                    if (!data.success) {
                        $.messager.alert(data.msg);
                        $("#identNumber").val('');
                        $("#relatedName").val('');
                    }
                }
            }
        })
    }

    function sub() {
        var pid = $('#pid').val();
        var statusComment = $('#statusComment').val();
        $.ajax({
            url: '${base}/bus/traInternaiTran/changeApplyStatus/' + pid + '/' + statusComment,
            type: 'post',
            data: 'json',
            success: function (data) {
                if (data.success) {

                    var objData = {
                        pid: pid
                    };
                    var myDate = new Date();
                    objData.id = myDate.getTime();
                    objData.type = "CREDIT";
                    var data = JSON.stringify(objData);
                    var formData = $.helper.base64Encode(data);
                    var obj = {
                        procDefCode: 'trancredit',
                        oldData: '',
                        newData: formData,
                        uploadSessions: '',
                        procInstId: ''
                    };
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
                    $.messager.alert(data.msg);
                }
            }
        });
    }


</script>
<script type="text/javascript">
    $("#approvalTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    $("#applicaSubTime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });


    $("#tranDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        language: "zh-CN",
        minuteStep: 30,
        autoclose: 2,
        todayBtn: "linked",
        minView: "hour"
    });

    // $("#validityContract").datetimepicker({
    //     format: "yyyy-mm-dd hh:ii:00",
    //     language: "zh-CN",
    //     minuteStep: 30,
    //     autoclose: 2,
    //     todayBtn: "linked",
    //     minView: "hour"
    // });
    // var a =$("#pid1").val();
    // alert(a);
</script>
</body>
</html>
