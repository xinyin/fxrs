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
        .form-group .select2{
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
    <bc:ibox id="traFundsBox" title="" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${traFunds.pid}"/>
            <bf:hidden id="relId" name="relId" value="${traFunds.relId}"/>
            <bc:row cls="m-b">
                <bf:formGroup hrp="bottom">
            <bf:formGroup col="4">
                <bc:label text="关联方证件号码" col="5" formControl="true"/>
                <bf:text id="relNumber" name="relNumber" col="7" value="${traFunds.relNumber}"
                         disabled="true">
                    <bv:notEmpty/>
                </bf:text>
            </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="交易对手类型" col="5" formControl="true"/>
                    <bf:select id="relType" name="relType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" value="${traFunds.relType}"
                               disabled="true">
                        <bf:option text="请选择" value=""></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="交易对手名称" col="5" formControl="true"/>
                    <bf:text id="relName" name="relName" col="7" value="${traFunds.relName}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <div title="本年度与该关联方发生的关联交易累计金额（万元/人民币）">
                        <bc:label text="关联交易累计金额" col="5" formControl="true"/>
                        <bf:text id="totolTradeAmount" name="totolTradeAmount" col="7" value="${traFunds.totolTradeAmount}"
                                 disabled="true">
                        </bf:text>
                    </div>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                    <bf:select id="majorTrade" name="majorTrade" col="7" dataSource="fixedDict"
                               url="IsStatusDict" value="${traFunds.majorTrade}" disabled="true">
                        <bf:option text="-" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
<%--                multiple="${opt == 'edit'}"--%>
<%--                <bf:formGroup col="4">--%>
<%--                    <bc:label text="统计口径" col="5" formControl="true"/>--%>
<%--                    <bf:select id="caliber" name="caliber" col="7" dataSource="custom"--%>
<%--                               url="sys/dictItem/getDictItems/BUS-Caliber" value="${traFunds.caliber}" disabled="true">--%>
<%--                        <bf:option text="请选择" value=""></bf:option>--%>
<%--                    </bf:select>--%>
<%--                </bf:formGroup>--%>
                <bf:formGroup col="4">
                    <bc:label text="统计口径" col="5" formControl="true"/>
                    <bf:select id="caliber" name="caliber" col="7" value="${traFunds.caliber}" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Caliber" disabled="true">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="交易日期" col="5" formControl="true"/>
                    <bf:datebox id="tranDate" name="tranDate" col="7" value="${traFunds.tranDateString}"
                                disabled="true"/>
                    <bv:notEmpty/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="合同/交易编号" col="5" formControl="true"/>
                    <bf:text id="contractNo" name="contractNo" col="7" value="${traFunds.contractNo}" disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="交易金额（万元/人民币）" col="5" formControl="true"/>
                    <bf:text id="tradeAmount" name="tradeAmount" col="7" value="${traFunds.tradeAmount}"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="业务起始日期" col="5" formControl="true"/>
                    <bf:datebox id="busStartTime" name="busStartTime" col="7" value="${traFunds.busStartTimeString}"
                                disabled="true">
                        <bv:notEmpty/>
                    </bf:datebox>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="业务终止日期" col="5" formControl="true"/>
                    <bf:datebox id="busEndTime" name="busEndTime" col="7" value="${traFunds.busEndTimeString}"
                                disabled="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="投资类别" col="5" formControl="true"/>
                    <bf:select id="investmentType" name="investmentType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-InvestmentType" value="${traFunds.investmentType}"
                               disabled="true">
                        <bf:option text="请选择" value=""></bf:option>
                        <bv:notEmpty/>
                        <be:event type="select">
                            selectInvestmentType(e.target.value);
                        </be:event>
                    </bf:select>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
             <%--   <bf:formGroup col="4">
                    <div title="交易定价是否为国家规定">
                    <bc:label text="定价是否国家规定" col="5" formControl="true"/>
                    <bf:select id="isDealPrice" name="isDealPrice" col="7" dataSource="fixedDict"
                               url="IsStatusDict" value="${traFunds.isDealPrice}" disabled="true">
                        <bf:option text="请选择" value=""></bf:option>
                    </bf:select>
                    </div>
                </bf:formGroup>--%>
<%--                    <div id="6" style="display: none">--%>
                        <bf:formGroup col="4">
                                <bc:label text="存款期限" col="5" formControl="true"/>
                                <bf:select id="depositTerms" name="depositTerms" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/INS-DepositTerms" value="${traFunds.depositTerms}" disabled="true">
                                    <bf:option text="ALL" value=""></bf:option>
                                    <bv:notEmpty/>
                                </bf:select>
                        </bf:formGroup>
<%--                    </div>--%>
<%--            <div id="1" style="display: none">--%>
                    <bf:formGroup col="4">
                        <bc:label text="被投资企业情况" col="5" formControl="true"/>
                        <bf:text id="investedEnterprises" name="investedEnterprises" col="7" disabled="true"
                                 value="${traFunds.investedEnterprises}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="前次交易价格" col="5" formControl="true"/>
                        <bf:text id="equityLastTradePrice" name="equityLastTradePrice" col="7"
                                 value="${traFunds.equityLastTradePrice}"
                                 disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                </bf:formGroup>
<%--            </div>--%>
<%--            <div id="2" style="display: none">--%>
                    <bf:formGroup col="4">
                        <bc:label text="标的情况" col="5" formControl="true"/>
                        <bf:text id="imSubjectMatter" name="imSubjectMatter" col="7" value="${traFunds.imSubjectMatter}"
                                 disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="前次交易价格" col="5" formControl="true"/>
                        <bf:text id="imLastTradePrice" name="imLastTradePrice" col="7"
                                 value="${traFunds.imLastTradePrice}"
                                 disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="投资方式" col="5" formControl="true"/>
                        <bf:text id="investmentMethod" name="investmentMethod" col="7" disabled="true"
                                 value="${traFunds.investmentMethod}">
                            <bv:notEmpty/>
                        </bf:text>
                </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="股债比" col="5" formControl="true"/>
                        <bf:text id="shareDebtRatio" name="shareDebtRatio" col="7" value="${traFunds.shareDebtRatio}"
                                 disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                </bf:formGroup>
<%--            </div>--%>
<%--            <div id="3" style="display: none">--%>
                    <bf:formGroup col="4">
                        <bc:label text="标的情况" col="5" formControl="true"/>
                        <bf:text id="outSubjectMatter" name="outSubjectMatter" col="7"
                                 value="${traFunds.outSubjectMatter}"
                                 disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
<%--            </div>--%>
<%--            <div id="4" style="display: none">--%>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="金融资产类别" col="5" formControl="true"/>
                        <bf:text id="financialAssstsType" name="financialAssstsType" col="7" disabled="true"
                                 value="${traFunds.financialAssstsType}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="金融资产编号" col="5" formControl="true"/>
                        <bf:text id="financialAssstsNum" name="financialAssstsNum" col="7" disabled="true"
                                 value="${traFunds.financialAssstsNum}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="金融产品发行规模" col="5" formControl="true"/>
                        <bf:text id="financialProScale" name="financialProScale" col="7" disabled="true"
                                 value="${traFunds.financialProScale}">
                            <bv:notEmpty/>
                        </bf:text>
                </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div title="购买金融产品份额占比（%）">
                        <bc:label text="购买金融产品占比" col="5" formControl="true"/>
                        <bf:text id="financialProRatio" name="financialProRatio" col="7" disabled="true"
                                 value="${traFunds.financialProRatio}">
                            <bv:notEmpty/>
                        </bf:text>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="底层资产" col="5" formControl="true"/>
                        <bf:text id="underlyingAsset" name="underlyingAsset" col="7" disabled="true"
                                 value="${traFunds.underlyingAsset}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <div title="底层资产是否为关联方">
                        <bc:label text="资产是否为关联方" col="5" formControl="true"/>
                        <bf:select id="underIsRel" name="underIsRel" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo" value="${traFunds.underIsRel}"
                                   disabled="true">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                        </div>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <div title="底层基础资产是否涉及控股股东或控股股东的关联方">
                        <bc:label text="底层资产是否涉及" col="5" formControl="true"/>
                        <bf:select id="underInvolveRel" name="underInvolveRel" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo" value="${traFunds.underInvolveRel}"
                                   disabled="true">
                            <bf:option text="ALL" value=""></bf:option>
                            <bv:notEmpty/>
                        </bf:select>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="底层资产关联关系" col="5" formControl="true"/>
                        <bf:text id="underRel" name="underRel" col="7" value="${traFunds.underRel}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                </bf:formGroup>
<%--            </div>--%>
<%--            <div id="5" style="display: none">--%>
                    <bf:formGroup col="4">
                    <bc:label text="被投企业名称" col="5" formControl="true"/>
                        <bf:text id="investedEnterprisesName" name="investedEnterprisesName" col="7" disabled="true"
                                 value="${traFunds.investedEnterprisesName}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="业务模式" col="5" formControl="true"/>
                        <bf:text id="busModel" name="busModel" col="7" value="${traFunds.busModel}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="盈利预期" col="5" formControl="true"/>
                        <bf:text id="earningsExpectaions" name="earningsExpectaions" col="7" disabled="true"
                                 value="${traFunds.earningsExpectaions}">
                            <bv:notEmpty/>
                        </bf:text>
                </bf:formGroup>
                    <bf:formGroup col="4">
                    <bc:label text="投资比例（%）" col="5" formControl="true"/>
                        <bf:text id="investmentRatio" name="investmentRatio" col="7" disabled="true"
                                 value="${traFunds.investmentRatio}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                </bf:formGroup>
                <bf:formGroup hrp="bottom">
                    <bf:formGroup col="4">
                        <bc:label text="投资收益分配情况" col="5" formControl="true"/>
                        <bf:text id="investmentAllocation" name="investmentAllocation" col="7" disabled="true"
                                 value="${traFunds.investmentAllocation}">
                            <bv:notEmpty/>
                        </bf:text>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="损失分担情况" col="5" formControl="true"/>
                        <bf:text id="lossShare" name="lossShare" col="7" value="${traFunds.lossShare}" disabled="true">
                            <bv:notEmpty/>
                        </bf:text>
                </bf:formGroup>
                </bf:formGroup>
<%--                </div>--%>
            </bc:row>
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="6">
                    <bc:label text="交易价格" col="3" formControl="true"/>
                    <bf:text id="tradePrice" name="tradePrice" col="9" rows="2" value="${traFunds.tradePrice}" multiple="true"
                             disabled="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label text="定价政策" col="3" formControl="true"/>
                    <bf:text id="pricingPolicy" name="pricingPolicy" col="9" rows="2" value="${traFunds.pricingPolicy}"
                             disabled="true"
                             multiple="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label text="交易标的" col="3" formControl="true"/>
                    <bf:text id="tradeSubject" name="tradeSubject" col="9" rows="2" value="${traFunds.tradeSubject}"
                             disabled="true"
                             multiple="true">
                        <bv:notEmpty/>
                    </bf:text>
                </bf:formGroup>
                <bf:formGroup col="6">
                    <bc:label text="其他事项说明" col="3" formControl="true"/>
                    <bf:text id="remark" name="remark" col="9" rows="2" value="${traFunds.remark}" multiple="true"
                             disabled="true">
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
                <div class="col-sm-12 col-sm-offset-5">
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
    $(document).ready(function () {
        var investmentType = $('#investmentType').val();
        for (var i = 1; i < 7; i++) {
            if (investmentType == i) {
                $('#' + i).show();
            }
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
</script>
</body>
</html>
