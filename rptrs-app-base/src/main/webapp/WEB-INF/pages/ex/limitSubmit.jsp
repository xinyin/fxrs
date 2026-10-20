<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ taglib prefix="be" uri="http://www.yzj.com/ui/bootstrap/event" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>Limit</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="productBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:hidden id="pid" name="pid" value="${product.pid}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="5">
                    <bc:label text="交易主体" col="4" formControl="true"/>
                    <bf:select id="tranOrg" name="tranOrg" col="8" dataSource="custom"
                               url="bus/limit/getTranOrgItems" value="${product.tranOrg}"
                               excludes="" disabled="true">
                        <bf:option text="All" value="%"></bf:option>
                        <bv:notEmpty/>
                    </bf:select>
                    <c:if test="${not empty product2.tranOrg}">
                        <div class="col-md-8 col-md-offset-4">
                            <bf:select id="tranOrg" name="tranOrg" dataSource="custom"
                                       url="bus/limit/getTranOrgItems" col="8"
                                       includes="" value="${product2.tranOrg}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bc:col>
                <bc:col col="5">
                    <bc:label text="产品名称" col="4" formControl="true"/>
                    <bf:lookup id="proName" name="proName" col="8" title="业务级别名称"
                               url="${base}/bus/limit/proNameListTree" winId="productViewId"
                               propText="proName" propValue="level"
                               callback="result" onSelect="onSelect" text="${product.proPath}"
                               value="${product.proPath}" disabled="true" helpText="${product2.proPath}">
                    </bf:lookup>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="5">
                    <bc:label text="交易类型" col="4" formControl="true"/>
                    <bf:select id="tradeType" name="tradeType" col="8" dataSource="custom"
                               url="bus/limit/getTradeTypeItems" value="${product.tradeType}" disabled="true">
                        <bv:notEmpty/>
                    </bf:select>
                    <c:if test="${not empty product2.tradeType}">
                        <div class="col-md-8 col-md-offset-4">
                            <bf:select id="tradeType" name="tradeType" col="8" dataSource="custom"
                                       url="bus/limit/getTradeTypeItems" value="${product2.tradeType}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bc:col>
                <bc:col col="5">
                    <bc:label text="业务类型" col="4" formControl="true"/>
                    <bf:select id="businessType" name="businessType" col="8" dataSource="custom"
                               url="bus/limit/getBuinessTypeItems" value="${product.businessType}" disabled="true">
                    </bf:select>
                    <c:if test="${not empty product2.businessType}">
                        <div class="col-md-8 col-md-offset-4">
                            <bf:select id="businessType" name="businessType" col="8" dataSource="custom"
                                       url="bus/limit/getBuinessTypeItems" value="${product2.businessType}" disabled="true">
                            </bf:select>
                        </div>
                    </c:if>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="5">
                    <bc:label text="预计额度（元）" col="4" formControl="true"/>
                    <bf:text id="preLimit" name="preLimit" col="6" value="${product.preLimit}"
                             disabled="true" helpText="${product2.preLimit}">
                    </bf:text>
                </bc:col>
                <bc:col col="5">
                    <bc:label text="额度使用率" col="4" formControl="true"/>
                    <bf:text id="limitRate" name="limitRate" col="8" value="${product.limitRate}"
                             disabled="true" helpText="${product2.limitRate}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="5">
                    <bc:label text="已用额度" col="4" formControl="true"/>
                    <bf:text id="pasLimit" name="pasLimit" col="8" value="${product.pasLimit}"
                             disabled="true" helpText="${product2.pasLimit}">
                        <bv:notEmpty/>
                    </bf:text>
                </bc:col>
                <bc:col col="5">
                    <bc:label text="剩余额度" col="4" formControl="true"/>
                    <bf:text id="remLimit" name="remLimit" col="8" value="${product.remLimit}"
                             disabled="true" helpText="${product2.remLimit}">
                        <bv:notEmpty/>
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
<script type="text/javascript">
    var tradeType = '${product.tradeType}';
    var businessType = '${product.businessType}';
    $(document).ready(function () {
    });

    function onSelect() {
        var proName = $("input[name=proName]").val();
        // alert(proName);
        $.ajax({
            type: "post",
            url: "${base}/bus/limit/selectbuinessL",
            data: {"unitCodes": proName},
            success: function (data) {
                var nums = data.preLimitReturn;
                var preLimitReturn = '参考值:' + nums;
                var tradeType = data.tradesType;
                var businessType = data.businessType;
                $('#tradeType').val(tradeType);
                $('#tradeType').select2({value: tradeType});
                $('#businessType').val(businessType);
                $('#businessType').select2({value: businessType});
                $("#preLimitLS").html(preLimitReturn)


            }
        });
    }


    $("#preLimit").blur(function preLimitQuery() {

        var formData = $('#editForm').serializeAll();
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/bus/limit/preLimitQuery",
            data: formData,
            success: function (data) {

                $("#limitRate").val(data.limitRate);
                $("#pasLimit").val(data.pasLimit);
                $("#remLimit").val(data.remLimit);

                /*alert("预计额度超过参考值！请重设预计额度。");
                $("#preLimit").empty();
                $('#tradeType').empty();
                $('#businessType').empty();*/


            }

        });
    });


</script>
</body>
</html>