<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>FundsInvestment</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="fundsInvestmentBox" title="" subTitle="" col="12" collapsible="false">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${fundsInvestment.pid}"/>
            <bf:hidden id="limitRadio" name="limitRadio"/>
            <div class="panel panel-default">
                <div class="panel-heading">基本投资余额信息</div>
                <div class="panel-body">
                    <bf:formGroup hrp="bottom">

                        <bc:col col="6">
                            <bc:label cls="required" text="投资余额统计时间" col="4" formControl="true"/>
                            <bf:datebox id="investmentBalanceTime" name="investmentBalanceTime" col="8"
                                        value="${fundsInvestment.investmentBalanceTimeString}">
                                <bv:date format="YYYY-MM-DD" message="只能输入合理的日期，类型为YYYY-MM-DD类型"/>
                                <bv:notEmpty/>
                            </bf:datebox>
                        </bc:col>

                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">

                        <bc:col col="6">
                            <bc:label cls="required" text="流动性资产投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="mobilityBalance" name="mobilityBalance" col="8"
                                     value="${fundsInvestment.mobilityBalance}">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>

                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label cls="required" text="固定收益类资产投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="fixedIncomeBalance" name="fixedIncomeBalance" col="8"
                                     value="${fundsInvestment.fixedIncomeBalance}">
                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                    </bf:formGroup>

                    <bf:formGroup hrp="bottom">

                        <bc:col col="6">
                            <bc:label cls="required" text="不动产类资产投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="realEstateBalance" name="realEstateBalance" col="8"
                                     value="${fundsInvestment.realEstateBalance}" change="">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label text="不动产类资产投资限额(万元)" col="4" formControl="true"/>
                            <bf:text id="realEstateLimit" name="realEstateLimit" col="8"
                                     value="${fundsInvestment.realEstateLimit}" disabled="true">
                            </bf:text>
                        </bc:col>

                    </bf:formGroup>

                    <bf:formGroup hrp="bottom">
                        <bc:col col="6">
                            <bc:label cls="required" text="境外投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="foreignBalance" name="foreignBalance" col="8"
                                     value="${fundsInvestment.foreignBalance}">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label text="境外投资限额(万元)" col="4" formControl="true"/>
                            <bf:text id="foreignLimit" name="foreignLimit" col="8"
                                     value="${fundsInvestment.foreignLimit}" disabled="true">
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>

                    <bf:formGroup hrp="bottom">

                        <bc:col col="6">
                            <bc:label cls="required" text="权益类资产投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="balanceInvestment" name="balanceInvestment" col="8"
                                     value="${fundsInvestment.balanceInvestment}">
                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label text="权益类资产投资限额(万元)" col="4" formControl="true"/>
                            <bf:text id="balanceInvestmentLimit" name="balanceInvestmentLimit" col="8"
                                     value="${fundsInvestment.balanceInvestmentLimit}" disabled="true">
                            </bf:text>
                        </bc:col>

                    </bf:formGroup>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">金融产品投资余额信息</div>
                <div class="panel-body">

                    <bf:formGroup hrp="bottom">

                        <bc:col col="6">
                            <bc:label cls="required" text="保险资产管理产品投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="insuranceManagementBalance" name="insuranceManagementBalance" col="8"
                                     value="${fundsInvestment.insuranceManagementBalance}" change="financialChange()">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label cls="required" text="商业银行理财产品投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="bankFinancialBalance" name="bankFinancialBalance" col="8"
                                     value="${fundsInvestment.bankFinancialBalance}" change="financialChange()">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                    </bf:formGroup>

                    <bf:formGroup hrp="bottom">

                        <bc:col col="6">
                            <bc:label cls="required" text="银行业金融机构信资产支持证券投资余额(万元)" col="4"
                                      formControl="true"/>
                            <bf:text id="bankNegotiableSecuritiesBalance" name="bankNegotiableSecuritiesBalance" col="8"
                                     value="${fundsInvestment.bankNegotiableSecuritiesBalance}" change="financialChange()">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label cls="required" text="信托公司集合资金信托计划投资余额(万元)" col="4"
                                      formControl="true"/>
                            <bf:text id="trustSchemeBalance" name="trustSchemeBalance" col="8"
                                     value="${fundsInvestment.trustSchemeBalance}" change="financialChange()">
                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                    </bf:formGroup>

                    <bf:formGroup hrp="bottom">

                        <bc:col col="6">
                            <bc:label cls="required" text="证券公司专项资产管理计划投资余额(万元)" col="4"
                                      formControl="true"/>
                            <bf:text id="negotiableSecuritiesSpecialPlanBalance"
                                     name="negotiableSecuritiesSpecialPlanBalance"
                                     col="8" value="${fundsInvestment.negotiableSecuritiesSpecialPlanBalance}" change="financialChange()">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label cls="required" text="保险资管公司发行资产支持计划投资余额(万元)" col="4"
                                      formControl="true"/>
                            <bf:text id="insuranceFundsSupportPlanBalance" name="insuranceFundsSupportPlanBalance"
                                     col="8"
                                     value="${fundsInvestment.insuranceFundsSupportPlanBalance}" change="financialChange()">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                    </bf:formGroup>

                    <bf:formGroup hrp="bottom">
                        <bc:col col="6">
                            <bc:label cls="required" text="其他投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="otherInvestmentBalance" name="otherInvestmentBalance" col="8"
                                     value="${fundsInvestment.otherInvestmentBalance}" change="financialChange()">

                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>

                        <bc:col col="6">
                            <bc:label text="金融产品投资限额(万元)" col="4" formControl="true"/>
                            <bf:text id="financialProducts" name="financialProducts" col="8"
                                     value="${fundsInvestment.financialProducts}" disabled="true">
                            </bf:text>
                        </bc:col>

                    </bf:formGroup>

                    <bf:formGroup hrp="bottom">
                        <bc:col col="6">
                            <bc:label cls="required" text="全部投资余额(万元)" col="4" formControl="true"/>
                            <bf:text id="allInvestmentBalance" name="allInvestmentBalance" col="8"
                                     value="${fundsInvestment.allInvestmentBalance}" disabled="true">
                                <bv:regexp regexp="^[1-9]\d{0,15}(\.\d{1,4})?$|^0(\.\d{1,4})?$" message="最大16位整数，4位小数！"/>
                                <bv:stringLength max="21" message="字段长度最大为20位"/>
                                <bv:notEmpty/>
                            </bf:text>
                        </bc:col>
                    </bf:formGroup>
                </div>
            </div>
            <bf:formGroup>
                <div class="col-sm-4 col-sm-offset-5">
                    <bc:button type="danger" text="保存" icon="save">
                        <be:event>
                            save();
                        </be:event>
                    </bc:button>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/fundsInvestment/list';
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
        debugger;
        $(".required").each(function () {
            var $this = $(this);
            $(this).html("<font color='red'>✶</font>" + $this.html());
        });
    });
    var limitRadio;
    $("#investmentBalanceTime").change(function () {
        debugger;
        var iTime = parseInt($("#investmentBalanceTime").find("input").val().substring(5, 7));
        var years = $("#investmentBalanceTime").find("input").val().substring(0, 4);
        var quarter;
        if (1 <= iTime && iTime <= 3) {
            quarter = "1";
        } else if (4 <= iTime && iTime <= 6) {
            quarter = "2";
        } else if (7 <= iTime && iTime <= 9) {
            quarter = "3";
        } else if (10 <= iTime && iTime <= 12) {
            quarter = "4";
        } else {
            return false;
        }
        $.ajax({
            type: "GET",
            url: "${base}/bus/fundsInvestment/getLimit",
            contentType: "application/json; charset=utf-8",
            data: {
                years: years,
                quarter: quarter
            },
            dataType: 'text',
            success: function (data) {
                var ob = eval("(" + data + ")")
                $("#realEstateLimit").val(ob.realEstateLimit);
                $("#foreignLimit").val(ob.foreignLimit);
                $("#balanceInvestmentLimit").val(ob.balanceInvestmentLimit);
                $("#financialProducts").val(ob.financialProducts);
                limitRadio = ob.limitRadio;
                $("#limitRadio").val(ob.limitRadio);
                // $("#otherFinanceLimit").val(ob.otherFinanceLimit);
            }
        });
    });


    // $("#realEstateBalance").change(function () {
    //     var realEstateBalance = $('#realEstateBalance').val();
    //     var realEstateLimit = $('#realEstateLimit').val();
    //     var limitA = division(realEstateLimit, limitRadio)
    //     var limitB = division(realEstateLimit, 1)
    //     document.getElementById("realEstateBalance").style.color = "#000000";
    //     if(compareNumber(realEstateBalance, limitA)){
    //         // $('.realEstateBalance').html("<font color='yellow'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("realEstateBalance").style.color = "#FFFF00";
    //     }
    //
    //     if(compareNumber(realEstateBalance, limitB)){
    //         // $('.realEstateBalance').html("<font color='red'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("realEstateBalance").style.color = "#ff0000";
    //
    //     }
    //
    // });
    //
    // $("#foreignBalance").change(function () {
    //     var foreignBalance = $('#foreignBalance').val();
    //     var foreignLimit = $('#foreignLimit').val();
    //     var limitA = division(foreignLimit, limitRadio)
    //     var limitB = division(foreignLimit, 1)
    //     document.getElementById("foreignBalance").style.color = "#000000";
    //     if(compareNumber(foreignBalance, limitA)){
    //         // $('.realEstateBalance').html("<font color='yellow'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("foreignBalance").style.color = "#FFFF00";
    //     }
    //
    //     if(compareNumber(foreignBalance, limitB)){
    //         // $('.realEstateBalance').html("<font color='red'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("foreignBalance").style.color = "#ff0000";
    //
    //     }
    // });
    //
    // $("#balanceInvestment").change(function () {
    //     var balanceInvestment = $('#balanceInvestment').val();
    //     var balanceInvestmentLimit = $('#balanceInvestmentLimit').val();
    //     var limitA = division(balanceInvestmentLimit, limitRadio)
    //     var limitB = division(balanceInvestmentLimit, 1)
    //     document.getElementById("balanceInvestment").style.color = "#000000";
    //     if(compareNumber(balanceInvestment, limitA)){
    //         // $('.realEstateBalance').html("<font color='yellow'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("balanceInvestment").style.color = "#FFFF00";
    //     }
    //
    //     if(compareNumber(balanceInvestment, limitB)){
    //         // $('.realEstateBalance').html("<font color='red'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("balanceInvestment").style.color = "#ff0000";
    //
    //     }
    // });
    //
    // $("#financialProducts").change(function () {
    //     var financialProducts = $('#financialProducts').val();
    //     var realEstateLimit = $('#realEstateLimit').val();
    //     var limitA = division(realEstateLimit, limitRadio)
    //     var limitB = division(realEstateLimit, 1)
    //     document.getElementById("financialProducts").style.color = "#000000";
    //     if(compareNumber(financialProducts, limitA)){
    //         // $('.realEstateBalance').html("<font color='yellow'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("financialProducts").style.color = "#FFFF00";
    //     }
    //
    //     if(compareNumber(financialProducts, limitB)){
    //         // $('.realEstateBalance').html("<font color='red'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("financialProducts").style.color = "#ff0000";
    //
    //     }
    // });
    //
    // function financialChange() {
    //     var financialProducts = $('#financialProducts').val();
    //     var insuranceManagementBalance = $('#insuranceManagementBalance').val();
    //     var bankFinancialBalance = $('#bankFinancialBalance').val();
    //     var bankNegotiableSecuritiesBalance = $('#bankNegotiableSecuritiesBalance').val();
    //     var trustSchemeBalance = $('#trustSchemeBalance').val();
    //     var negotiableSecuritiesSpecialPlanBalance = $('#negotiableSecuritiesSpecialPlanBalance').val();
    //     var insuranceFundsSupportPlanBalance = $('#insuranceFundsSupportPlanBalance').val();
    //     var otherInvestmentBalance = $('#otherInvestmentBalance').val();
    //     var allfinancial = Number(insuranceManagementBalance) + Number(bankNegotiableSecuritiesBalance)
    //         + Number(trustSchemeBalance) + Number(negotiableSecuritiesSpecialPlanBalance)
    //         + Number(bankFinancialBalance) + Number(insuranceFundsSupportPlanBalance) + Number(otherInvestmentBalance);
    //     var limitA = division(financialProducts, limitRadio)
    //     var limitB = division(financialProducts, 1)
    //     document.getElementById("financialProducts").style.color = "#000000";
    //     if(compareNumber(allfinancial, limitA)){
    //         // $('.realEstateBalance').html("<font color='yellow'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("insuranceManagementBalance").style.color = "#FFFF00";
    //         document.getElementById("bankFinancialBalance").style.color = "#FFFF00";
    //         document.getElementById("bankNegotiableSecuritiesBalance").style.color = "#FFFF00";
    //         document.getElementById("trustSchemeBalance").style.color = "#FFFF00";
    //         document.getElementById("negotiableSecuritiesSpecialPlanBalance").style.color = "#FFFF00";
    //         document.getElementById("insuranceFundsSupportPlanBalance").style.color = "#FFFF00";
    //         document.getElementById("otherInvestmentBalance").style.color = "#FFFF00";
    //
    //     }
    //
    //     if(compareNumber(allfinancial, limitB)){
    //         // $('.realEstateBalance').html("<font color='red'>✶</font>" +  $('.relationTyp').html());
    //         document.getElementById("insuranceManagementBalance").style.color = "#ff0000";
    //         document.getElementById("bankFinancialBalance").style.color = "#ff0000";
    //         document.getElementById("bankNegotiableSecuritiesBalance").style.color = "#ff0000";
    //         document.getElementById("trustSchemeBalance").style.color = "#ff0000";
    //         document.getElementById("negotiableSecuritiesSpecialPlanBalance").style.color = "#ff0000";
    //         document.getElementById("insuranceFundsSupportPlanBalance").style.color = "#ff0000";
    //         document.getElementById("otherInvestmentBalance").style.color = "#ff0000";
    //
    //     }
    //
    // }


    /**
     *  两数相除
     * @param {Object} param1  除数
     * @param {Object} param2  被除数
     */
    function division(param1, param2) {
        if (!isNotEmpty(param1) && isNotEmpty(param2)) {
            return 0;
        } else if (isNotEmpty(param1) && !isNotEmpty(param2)) {
            return "";
        } else if (isNotEmpty(param1) && isNotEmpty(param2)){
            return parseFloat(param1) * parseFloat(param2);
        } else {
            return "";
        }
    }

    /**
     * 两个数字比较大小
     * */
    function compareNumber(param1,param2){
        if(isNotEmpty(param1) && (!isNotEmpty(param2) || param2==0)){
            return true;
        }else if(!isNotEmpty(param1)&&(isNotEmpty(param2)||param2>0)){
            return false;
        }else if(isNotEmpty(param1)&&(isNotEmpty(param2)||param2>0)){
            return  parseFloat(param1) > parseFloat(param2) || parseFloat(param1) == parseFloat(param2);
        }else {
            return  true;
        }
    }

    //判断字符是否为空的方法
    function isNotEmpty(obj){
        return !(typeof obj == "undefined" || obj == null || obj == "");
    }




    function save() {
        var mobilityBalance = $('#mobilityBalance').val();
        var fixedIncomeBalance = $('#fixedIncomeBalance').val();
        var realEstateBalance = $('#realEstateBalance').val();
        var foreignBalance = $('#foreignBalance').val();
        var balanceInvestment = $('#balanceInvestment').val();
        var insuranceManagementBalance = $('#insuranceManagementBalance').val();
        var bankFinancialBalance = $('#bankFinancialBalance').val();
        var bankNegotiableSecuritiesBalance = $('#bankNegotiableSecuritiesBalance').val();
        var trustSchemeBalance = $('#trustSchemeBalance').val();
        var negotiableSecuritiesSpecialPlanBalance = $('#negotiableSecuritiesSpecialPlanBalance').val();
        var insuranceFundsSupportPlanBalance = $('#insuranceFundsSupportPlanBalance').val();
        var otherInvestmentBalance = $('#otherInvestmentBalance').val();


        $('#editForm').data('formValidation').validate();
        var isValid = $('#editForm').data('formValidation').isValid();
        if (isValid) {
            var allInvestmentBalance = Number(mobilityBalance) + Number(fixedIncomeBalance) + Number(realEstateBalance) + Number(foreignBalance) + Number(balanceInvestment)
                + Number(insuranceManagementBalance) + Number(bankNegotiableSecuritiesBalance) + Number(trustSchemeBalance) + Number(negotiableSecuritiesSpecialPlanBalance)
                + Number(bankFinancialBalance) + Number(insuranceFundsSupportPlanBalance) + Number(otherInvestmentBalance);
            $('#allInvestmentBalance').val(allInvestmentBalance);
            var formData = $('#editForm').serializeAll();
            $.mask.post('${base}/bus/fundsInvestment/save', formData, function (data) {
                if (data.success) {
                    $.messager.alert('提示', data.msg, 'info', function () {
                        window.location = '${base}/bus/fundsInvestment/list';
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            });
        }
    }

</script>
</body>
</html>
