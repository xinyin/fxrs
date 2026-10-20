<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>关联交易表-用信类</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="traUsecreditBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="relId" name="relId" value="${traUsecredit.relId}"/>
            <bf:hidden id="manageOrg" name="manageOrg" value="${traUsecredit.manageOrg}"/>
            <bf:hidden id="isEffective" name="isEffective" value="${traUsecredit.isEffective}"/>
            <bf:hidden id="pid" name="pid" value="${traUsecredit.pid}"/>
            <bf:hidden id="productCode" name="productCode" value="${traUsecredit.productCode}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="关联方证件号码" col="5" formControl="true"/>
                    <bf:text id="identNumber" name="identNumber" col="7" value="${traUsecredit.identNumber}"
                             disabled="true">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联方类型" col="5" formControl="true"/>
                    <bf:select id="relType" name="relType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-RelatedPartiesType" value="${traUsecredit.relType}"
                               disabled="true">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="关联方名称" col="5" formControl="true"/>
                    <bf:text id="relatedName" name="relatedName" col="7" value="${traUsecredit.relatedName}"
                             disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="银行内部审批编号" col="5" formControl="true"/>
                    <bf:text id="internalApplyNo" name="internalApplyNo" col="7"
                             value="${traUsecredit.internalApplyNo}" change="getRelInfo()">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="用信编号" col="5" formControl="true"/>
                    <bf:text id="useCreditNum" name="useCreditNum" col="7" value="${traUsecredit.useCreditNum}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="合同签署日期" col="5" formControl="true"/>
                    <bf:datebox id="contractSignDate" name="contractSignDate" col="7"
                                value="${traUsecredit.contractSignDate}">
                    </bf:datebox>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="业务起始时间" col="5" formControl="true"/>
                    <bf:datebox id="businessStartTime" name="businessStartTime" col="7"
                                value="${traUsecredit.businessStartTime}">
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务终止时间" col="5" formControl="true"/>
                    <bf:datebox id="businessEndTime" name="businessEndTime" col="7"
                                value="${traUsecredit.businessEndTime}">
                    </bf:datebox>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="业务类型" col="5" formControl="true"/>
                    <bf:select name="businessType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-UseCredit" value="${traUsecredit.businessType}">
                        <bf:option text="ALL" value="%"></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="交易价格类型" col="5" formControl="true"/>
                    <bf:select id="traPriceType" name="traPriceType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-traPrice" col="7"
                               value="${traUsecredit.traPriceType}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="利率类型" col="5" formControl="true"/>
                    <bf:select id="rateType" name="rateType" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-rateType" col="7"
                               value="${traUsecredit.rateType}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="交易价格" col="5" formControl="true"/>
                    <bf:text id="traPrice" name="traPrice" col="7" value="${traUsecredit.traPrice}" change="cal()">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="浮动利率基准" col="5" formControl="true"/>
                    <bf:text id="floatRateBench" name="floatRateBench" col="5" value="${traUsecredit.floatRateBench}"
                             change="cal()">
                    </bf:text>
                    <bc:label text="%" col="1" formControl="true"/>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="浮动利率利差" col="5" formControl="true"/>
                    <bf:text id="floatInterestRateSpread" name="floatInterestRateSpread" col="7" disabled="true"
                             value="${traUsecredit.floatInterestRateSpread}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="浮动利率折扣率" col="5" formControl="true"/>
                    <bf:text id="floatRateDiscountRate" name="floatRateDiscountRate" col="5" disabled="true"
                             value="${traUsecredit.floatRateDiscountRate}">
                    </bf:text>
                    <bc:label text="%" col="1" formControl="true"/>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label cls="required" text="产品名称" col="5" formControl="true"/>
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
                    <bc:label text="本笔用信金额" col="5" formControl="true"/>
                    <bf:text id="thisTraAmount" name="thisTraAmount" col="7" value="${traUsecredit.thisTraAmount}"
                             change="getBalance()">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="本笔交易余额" col="5" formControl="true"/>
                    <bf:text id="thisTraBalance" name="thisTraBalance" col="7" value="${traUsecredit.thisTraBalance}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="金融产品名称" col="5" formControl="true"/>
                    <bf:text id="financialProduct" name="financialProduct" col="7"
                             value="${traUsecredit.financialProduct}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="产品批注/注册/备案号" col="5" formControl="true"/>
                    <bf:text id="productRegNum" name="productRegNum" col="7" value="${traUsecredit.productRegNum}">
                    </bf:text>
                </bc:col>
                <bc:col col="4">
                    <bc:label text="资金投向" col="5" formControl="true"/>
                    <bf:select id="capitalInvestment" name="capitalInvestment" dataSource="custom"
                               url="sys/dictItem/getDictItems/BUS-Capitalinvestment" col="7"
                               value="${traUsecredit.capitalInvestment}">
                        <bf:option text="ALL" value=""></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="4">
                    <bc:label text="投资关联方金额" col="5" formControl="true"/>
                    <bf:text id="investmentAmount" name="investmentAmount" col="7"
                             value="${traUsecredit.investmentAmount}">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="定价政策" col="5" formControl="true"/>
                    <bf:text id="pricingStrategy" name="pricingStrategy" col="7"
                             value="${traUsecredit.pricingStrategy}" multiple="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="资金用途" col="5" formControl="true"/>
                    <bf:text id="fundsUse" name="fundsUse" col="7" value="${traUsecredit.fundsUse}" multiple="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="投资标的的描述" col="5" formControl="true"/>
                    <bf:text id="targetsInvestmentDesc" name="targetsInvestmentDesc" col="7"
                             value="${traUsecredit.targetsInvestmentDesc}" multiple="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="其他说明事项" col="5" formControl="true"/>
                    <bf:text id="remark" name="remark" col="7" value="${traUsecredit.remark}" multiple="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            $('#editForm').data('formValidation').validate();
                            var isValid = $('#editForm').data('formValidation').isValid();
                            if(isValid)
                            {
                            var formData = $('#editForm').serializeAll();
                            $.mask.post('${base}/bus/traUsecredit/save', formData, function(data){
                            if(data.success)
                            {
                            $.messager.alert('提示', data.msg, 'info', function(){
                            window.location = '${base}/bus/traUsecredit/list';
                            });
                            }
                            else
                            {
                            $.messager.alert('错误', data.msg, 'error');
                            }
                            });
                            }
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/traUsecredit/list';
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

    });

    function getRelInfo() {
        var internalApplyNo = $('#internalApplyNo').val();
        var obj = {
            internalApplyNo: internalApplyNo
        };
        $.ajax({
            url: '${base}/bus/traUsecredit/getRelInfo',
            type: 'post',
            data: obj,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var traCredit = data.traCredit;
                    $('#identNumber').val(traCredit.identNumber);
                    $('#relatedName').val(traCredit.relatedName);
                    $('#relType').val(traCredit.relType);
                    $('#relType').select2({value: traCredit.relType});
                    $('#relId').val(traCredit.relId);
                    $('#manageOrg').val(traCredit.manageOrg);
                } else {
                    $.messager.alert(data.msg);
                }
            }
        });
    }

    function cal() {
        var traPrice = $('#traPrice').val();
        var floatRateBench = $('#floatRateBench').val();
        if (traPrice != '' && floatRateBench != '') {
            var floatInterestRateSpread = (traPrice - floatRateBench) * 100;
            $('#floatInterestRateSpread').val(floatInterestRateSpread);
            var floatRateDiscountRate = traPrice / floatRateBench;
            $('#floatRateDiscountRate').val(floatRateDiscountRate);
        }
    }

    function getBalance() {
        var thisTraAmount = $('#thisTraAmount').val();
        $('#thisTraBalance').val(thisTraAmount);
        <%--var creditAmount = ${traUsecredit.creditAmount};--%>
        <%--if(thisTraAmount>creditAmount){--%>
        <%--alert("大于");--%>
        <%--}--%>
    }

    function getProInfo() {
        var productName = $('#productName').val();
        var obj = {
            productName: productName
        };
        $.ajax({
            type: 'post',
            url: '${base}/bus/product/proInfo',
            dataType: 'json',
            data: obj,
            success: function (data) {
                var proId = data.productId;
                var busLine = data['01'];
                var typeOneBus = data['02'];
                var typeTwoBus = data['03'];
                var typeThreeBus = data['04'];
                var typeFourBus = data['05'];
                $('#busLine').val(busLine);
                $('#typeOneBus').val(typeOneBus);
                $('#typeTwoBus').val(typeTwoBus);
                $('#typeThreeBus').val(typeThreeBus);
                $('#typeFourBus').val(typeFourBus);
                $('#productCode').val(proId);
            }
        });
    }
    //在必填字段前面加上红星
    $(document).ready(function(){
        $(".required").each(function() {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>"+$this.html());
        });
    });


</script>
</body>
</html>





