<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表-用信类</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox id="traUsecreditBox" title="关联交易表-用信类" subTitle="" col="12" collapsible="false">
            <bf:form id="editForm" method="post" validation="false">
                <bf:hidden id="pid" name="pid" value="${traUsecredit.pid}"/>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="关联方证件号码" col="4" formControl="true"/>
                        <bf:text id="identNumber" name="identNumber" col="8" value="${traUsecredit.identNumber}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联方类型" col="4" formControl="true"/>
                        <bf:select id="relType" name="relType" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType"
                                   value="${traUsecredit.relType}"
                                   disabled="true">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="关联方名称" col="4" formControl="true"/>
                        <bf:text id="relatedName" name="relatedName" col="8" value="${traUsecredit.relatedName}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="银行内部审批编号" col="4" formControl="true"/>
                        <bf:text id="internalApplyNo" name="internalApplyNo" col="8"
                                 value="${traUsecredit.internalApplyNo}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="用信编号" col="4" formControl="true"/>
                        <bf:text id="useCreditNum" name="useCreditNum" col="8" value="${traUsecredit.useCreditNum}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="合同/交易发生日期" col="4" formControl="true"/>
                        <bf:datebox id="contractSignDate" name="contractSignDate" col="8"
                                    value="${traUsecredit.contractSignDate}" disabled="true">
                        </bf:datebox>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="业务起始时间" col="4" formControl="true"/>
                        <bf:datebox id="businessStartTime" name="businessStartTime" col="8"
                                    value="${traUsecredit.businessStartTime}" disabled="true">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="业务终止时间" col="4" formControl="true"/>
                        <bf:datebox id="businessEndTime" name="businessEndTime" col="8"
                                    value="${traUsecredit.businessEndTime}" disabled="true">
                        </bf:datebox>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="业务类型" col="4" formControl="true"/>
                        <bf:select name="businessType" col="8" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-UseCredit" value="${traUsecredit.businessType}"
                                   disabled="true">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="产品名称" col="5" formControl="true"/>
                        <bf:text id="productName" name="productName" col="7" value="${traUsecredit.productName}"
                                 change="getProInfo()">
                            <bv:notEmpty/>
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="一级业务种类" col="5" formControl="true"/>
                        <bf:text id="typeOneBus" name="typeOneBus" col="7" value="${traUsecredit.typeOneBus}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="二级业务种类" col="5" formControl="true"/>
                        <bf:text id="typeTwoBus" name="typeTwoBus" col="7" value="${traUsecredit.typeTwoBus}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="三级业务种类" col="5" formControl="true"/>
                        <bf:text id="typeThreeBus" name="typeThreeBus" col="7" value="${traUsecredit.typeThreeBus}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="四级业务种类" col="5" formControl="true"/>
                        <bf:text id="typeFourBus" name="typeFourBus" col="7" value="${traUsecredit.typeFourBus}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="业务表内外属性" col="5" formControl="true"/>
                        <bf:text id="tableAttrBus" name="tableAttrBus" col="7" value="${traUsecredit.tableAttrBus}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="业务条线" col="5" formControl="true"/>
                        <bf:text id="busLine" name="busLine" col="7" value="${traUsecredit.busLine}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="交易价格类型" col="4" formControl="true"/>
                        <bf:select id="traPriceType" name="traPriceType" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-traPrice" col="8" disabled="true"
                                   value="${traUsecredit.traPriceType}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="利率类型" col="4" formControl="true"/>
                        <bf:select id="rateType" name="rateType" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-rateType" col="8" disabled="true"
                                   value="${traUsecredit.rateType}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="交易价格" col="4" formControl="true"/>
                        <bf:text id="traPrice" name="traPrice" col="8" value="${traUsecredit.traPrice}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="浮动利率基准" col="4" formControl="true"/>
                        <bf:text id="floatRateBench" name="floatRateBench" col="8"
                                 value="${traUsecredit.floatRateBench}" disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="浮动利率利差" col="4" formControl="true"/>
                        <bf:text id="floatInterestRateSpread" name="floatInterestRateSpread" col="8" disabled="true"
                                 value="${traUsecredit.floatInterestRateSpread}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="浮动利率折扣率" col="4" formControl="true"/>
                        <bf:text id="floatRateDiscountRate" name="floatRateDiscountRate" col="8" disabled="true"
                                 value="${traUsecredit.floatRateDiscountRate}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="本笔用信金额" col="4" formControl="true"/>
                        <bf:text id="thisTraAmount" name="thisTraAmount" col="8" value="${traUsecredit.thisTraAmount}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="本笔交易余额" col="4" formControl="true"/>
                        <bf:text id="thisTraBalance" name="thisTraBalance" col="8"
                                 value="${traUsecredit.thisTraBalance}" disabled="true">
                        </bf:text>
                    </bc:col
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="金融产品名称" col="4" formControl="true"/>
                        <bf:text id="financialProduct" name="financialProduct" col="8" disabled="true"
                                 value="${traUsecredit.financialProduct}">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="产品批注/注册/备案号" col="4" formControl="true"/>
                        <bf:text id="productRegNum" name="productRegNum" col="8" value="${traUsecredit.productRegNum}"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                    <bc:col col="4">
                        <bc:label text="资金投向" col="4" formControl="true"/>
                        <bf:select id="capitalInvestment" name="capitalInvestment" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Capitalinvestment" col="8" disabled="true"
                                   value="${traUsecredit.capitalInvestment}">
                            <bf:option text="ALL" value=""></bf:option>
                        </bf:select>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="4">
                        <bc:label text="投资关联方金额" col="4" formControl="true"/>
                        <bf:text id="investmentAmount" name="investmentAmount" col="8" disabled="true"
                                 value="${traUsecredit.investmentAmount}">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="定价政策" col="4" formControl="true"/>
                        <bf:text id="pricingStrategy" name="pricingStrategy" col="8" disabled="true"
                                 value="${traUsecredit.pricingStrategy}" multiple="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="资金用途" col="4" formControl="true"/>
                        <bf:text id="fundsUse" name="fundsUse" col="8" value="${traUsecredit.fundsUse}" multiple="true"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="投资标的的描述" col="4" formControl="true"/>
                        <bf:text id="targetsInvestmentDesc" name="targetsInvestmentDesc" col="8"
                                 value="${traUsecredit.targetsInvestmentDesc}" multiple="true" disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="其他说明事项" col="4" formControl="true"/>
                        <bf:text id="remark" name="remark" col="8" value="${traUsecredit.remark}" multiple="true"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
                <bf:formGroup hr="dashed" hrp="bottom">
                    <bc:col col="6">
                        <bc:label text="提交备注" col="4" formControl="true"/>
                        <bf:text id="subRemark" name="subRemark" col="8" value="${subRemark}" multiple="true"
                                 disabled="true">
                        </bf:text>
                    </bc:col>
                </bf:formGroup>
            </bf:form>
        </bc:ibox>
    </div>
</div>
<br:js pack="form"/>
<be:processEvent/>
<script src="${base}/static/workflow/js/jWorkflowClient.js" type="text/javascript"></script>
<div id="flowForm" data-toggle="jWFClientTransferData" data-on-form-data="getFormData"></div>
<script>
</script>
</body>
</html>